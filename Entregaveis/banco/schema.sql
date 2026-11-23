-- Criação do banco
-- Requer MySQL 8.0.16+ (antes disso o banco aceita a sintaxe CHECK mas NAO a aplica).
CREATE DATABASE IF NOT EXISTS bebidas_db
    DEFAULT CHARACTER SET utf8mb4;
USE bebidas_db;

DROP TABLE IF EXISTS movimentacoes;
DROP TABLE IF EXISTS insumos;
DROP TABLE IF EXISTS fornecedores;
DROP TABLE IF EXISTS setores;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
    id          BIGINT        NOT NULL AUTO_INCREMENT,
    nome        VARCHAR(150)  NOT NULL,
    login       VARCHAR(50)   NOT NULL,
    senha_hash  VARCHAR(255)  NOT NULL,               -- BCrypt, nunca senha em texto puro
    ativo       TINYINT(1)    NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    CONSTRAINT uk_usuarios_login UNIQUE (login),
    CONSTRAINT ck_usuarios_ativo CHECK (ativo IN (0,1))  -- TINYINT(1) nao e boolean: aceita 5
);

CREATE TABLE setores (
    id         BIGINT        NOT NULL AUTO_INCREMENT,
    nome       VARCHAR(150)  NOT NULL, -- Nome unico APENAS entre os setores ativos: garantido pelo Service
                                       -- (decisao da equipe); o banco nao tem indice unico para isso.
    descricao  VARCHAR(255)  NULL,
    ativo      TINYINT(1)    NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    KEY idx_setores_nome_ativo (nome, ativo),
    CONSTRAINT ck_setores_ativo CHECK (ativo IN (0,1))
);

CREATE TABLE fornecedores (
    id        BIGINT        NOT NULL AUTO_INCREMENT,
    nome      VARCHAR(150)  NOT NULL,
    cnpj      CHAR(14)      NOT NULL,                 -- 14 digitos, sem mascara
    telefone  VARCHAR(20)   NULL,
    ativo     TINYINT(1)    NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    CONSTRAINT uk_fornecedores_cnpj UNIQUE (cnpj),
    CONSTRAINT ck_fornecedores_cnpj_digitos CHECK (cnpj REGEXP '^[0-9]{14}$'),
    CONSTRAINT ck_fornecedores_ativo CHECK (ativo IN (0,1)),
    KEY idx_fornecedores_nome (nome)
);

CREATE TABLE insumos (
    id               BIGINT        NOT NULL AUTO_INCREMENT,
    nome             VARCHAR(150)  NOT NULL,
    unidade_medida   ENUM('UN','KG','L') NOT NULL, -- UN  -> quantidades inteiras; KG/L -> ate 3 casas decimais.
    quantidade_atual DECIMAL(15,3) NOT NULL DEFAULT 0,
    estoque_minimo   DECIMAL(15,3) NOT NULL,          -- alerta quando quantidade_atual <= estoque_minimo
    fornecedor_id    BIGINT        NOT NULL,
    ativo            TINYINT(1)    NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    KEY idx_insumos_nome_ativo (ativo, nome),        -- WHERE ativo = 1 ORDER BY nome sai do indice, sem filesort
    KEY idx_insumos_fornecedor (fornecedor_id),
    CONSTRAINT fk_insumos_fornecedor
        FOREIGN KEY (fornecedor_id) REFERENCES fornecedores (id)
        ON DELETE RESTRICT
        ON UPDATE RESTRICT,
    CONSTRAINT ck_insumos_ativo      CHECK (ativo IN (0,1)),
    CONSTRAINT ck_insumos_saldo      CHECK (quantidade_atual >= 0),   -- RN-01/RN-03
    CONSTRAINT ck_insumos_minimo     CHECK (estoque_minimo >= 0),
    CONSTRAINT ck_insumos_un_inteira CHECK (unidade_medida <> 'UN'    -- RN-02: UN = quantidades inteiras
                                        OR (quantidade_atual = FLOOR(quantidade_atual)
                                            AND estoque_minimo = FLOOR(estoque_minimo)))
);

CREATE TABLE movimentacoes (
    id                BIGINT        NOT NULL AUTO_INCREMENT,
    insumo_id         BIGINT        NOT NULL,
    tipo              ENUM('ENTRADA','SAIDA') NOT NULL,
    quantidade        DECIMAL(15,3) NOT NULL,
    setor_id          BIGINT        NULL, -- obrigatorio em SAIDA (setor ativo); opcional em ENTRADA;
    usuario_id        BIGINT        NOT NULL,         -- vem do JWT, nunca do corpo
    data_movimentacao DATE          NOT NULL,
    -- TIMESTAMP(6) guarda o instante em UTC e e convertido pelo fuso da sessao (usar -03:00 na conexao).
    -- Limite do TIMESTAMP: 2038-01-19 (suficiente para o projeto). data_movimentacao segue DATE.
    data_registro     TIMESTAMP(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    lote              VARCHAR(50)   NULL, -- lote/validade: opcionais, apenas em ENTRADA, sem saldo por lote.
    validade          DATE          NULL,
    saldo_anterior    DECIMAL(15,3) NOT NULL,
    saldo_atual       DECIMAL(15,3) NOT NULL,
    PRIMARY KEY (id),
    KEY idx_mov_insumo_registro (insumo_id, data_registro),
    KEY idx_mov_registro_id (data_registro DESC, id DESC), -- ordenacao padrao do historico
    KEY idx_mov_data (data_movimentacao),
    KEY idx_mov_setor (setor_id),
    KEY idx_mov_usuario (usuario_id),
    KEY idx_mov_tipo_data (tipo, data_registro),
    CONSTRAINT fk_movimentacoes_insumo
        FOREIGN KEY (insumo_id) REFERENCES insumos (id)
        ON DELETE RESTRICT
        ON UPDATE RESTRICT,
    CONSTRAINT fk_movimentacoes_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
        ON DELETE RESTRICT
        ON UPDATE RESTRICT,
    CONSTRAINT fk_movimentacoes_setor
        FOREIGN KEY (setor_id) REFERENCES setores (id)
        ON DELETE RESTRICT
        ON UPDATE RESTRICT,
    CONSTRAINT ck_mov_quantidade        CHECK (quantidade > 0),                  -- RN-02
    CONSTRAINT ck_mov_saldo_anterior    CHECK (saldo_anterior >= 0),             -- RN-03
    CONSTRAINT ck_mov_saldo_atual       CHECK (saldo_atual >= 0),                -- RN-03
    CONSTRAINT ck_mov_setor_saida       CHECK (tipo = 'ENTRADA'
                                        OR setor_id IS NOT NULL),                -- RN-06: SAIDA exige setor
    CONSTRAINT ck_mov_sem_lote_saida    CHECK (tipo = 'ENTRADA'
                                        OR (lote IS NULL AND validade IS NULL)), -- RN-06
    CONSTRAINT ck_mov_validade          CHECK (validade IS NULL
                                        OR validade >= data_movimentacao),
    CONSTRAINT ck_mov_saldo_calc        CHECK (                                  -- RN-05
        (tipo = 'ENTRADA' AND saldo_atual = saldo_anterior + quantidade) OR
        (tipo = 'SAIDA'   AND saldo_atual = saldo_anterior - quantidade))
);

-- ============================================================================
-- Funções auxiliares para validações cruzadas (usadas pelos triggers abaixo)
-- ============================================================================

DELIMITER //

-- RN-02: insumo UN não aceita quantidade fracionária na movimentação
CREATE FUNCTION fn_insumo_un_inteira(p_insumo_id BIGINT, p_quantidade DECIMAL(15,3))
RETURNS INT DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE v_unidade ENUM('UN','KG','L');
    SELECT unidade_medida INTO v_unidade FROM insumos WHERE id = p_insumo_id;
    IF v_unidade = 'UN' AND p_quantidade <> FLOOR(p_quantidade) THEN
        RETURN 0;
    END IF;
    RETURN 1;
END //

-- RN-13: data operacional não pode ser futura
CREATE FUNCTION fn_data_nao_futura(p_data DATE)
RETURNS INT DETERMINISTIC
READS SQL DATA
BEGIN
    IF p_data > CURRENT_DATE THEN
        RETURN 0;
    END IF;
    RETURN 1;
END //

-- RN-06: setor deve estar ativo para SAIDA
CREATE FUNCTION fn_setor_ativo(p_setor_id BIGINT)
RETURNS INT DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE v_ativo TINYINT(1);
    SELECT ativo INTO v_ativo FROM setores WHERE id = p_setor_id;
    RETURN v_ativo;
END //

-- RN-11: fornecedor deve estar ativo ao vincular a um insumo
CREATE FUNCTION fn_fornecedor_ativo(p_fornecedor_id BIGINT)
RETURNS INT DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE v_ativo TINYINT(1);
    SELECT ativo INTO v_ativo FROM fornecedores WHERE id = p_fornecedor_id;
    RETURN v_ativo;
END //

-- ============================================================================
-- Triggers de validação no banco (blindagem extra além do Service)
-- ============================================================================

-- RN-02 / RN-06 / RN-13: validações cruzadas antes de inserir movimentação
CREATE TRIGGER trg_movimentacoes_before_insert
BEFORE INSERT ON movimentacoes
FOR EACH ROW
BEGIN
    DECLARE v_un_ok INT;

    -- data operacional não futura
    IF fn_data_nao_futura(NEW.data_movimentacao) = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Data operacional não pode ser futura (RN-13).';
    END IF;

    -- SAIDA exige setor ativo
    IF NEW.tipo = 'SAIDA' THEN
        IF NEW.setor_id IS NULL THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'SAIDA exige setor (RN-06).';
        END IF;
        IF fn_setor_ativo(NEW.setor_id) = 0 THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Setor inativo não permitido para SAIDA (RN-06).';
        END IF;
    END IF;

    -- UN não aceita quantidade fracionária
    SET v_un_ok = fn_insumo_un_inteira(NEW.insumo_id, NEW.quantidade);
    IF v_un_ok = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Insumo UN não aceita quantidade fracionária (RN-02).';
    END IF;
END //

-- RN-07: blindagem — impedir alterações no histórico
CREATE TRIGGER trg_movimentacoes_before_update
BEFORE UPDATE ON movimentacoes
FOR EACH ROW
BEGIN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Movimentações são imutáveis (RN-07).';
END //

-- RN-11: fornecedor deve estar ativo ao criar/alterar insumo
CREATE TRIGGER trg_insumos_before_insert
BEFORE INSERT ON insumos
FOR EACH ROW
BEGIN
    IF fn_fornecedor_ativo(NEW.fornecedor_id) = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Fornecedor inativo não pode ser vinculado ao insumo (RN-11).';
    END IF;
END //

CREATE TRIGGER trg_insumos_before_update
BEFORE UPDATE ON insumos
FOR EACH ROW
BEGIN
    IF NEW.fornecedor_id <> OLD.fornecedor_id AND fn_fornecedor_ativo(NEW.fornecedor_id) = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Fornecedor inativo não pode ser vinculado ao insumo (RN-11).';
    END IF;
END //

DELIMITER ;

