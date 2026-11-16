-- Criação do banco
-- Requer MySQL 8.0.16+ (antes disso o banco aceita a sintaxe CHECK mas NAO a aplica).
CREATE DATABASE IF NOT EXISTS bebidas_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_0900_ai_ci;

USE bebidas_db;

-- DROP TABLE IF EXISTS movimentacoes;
-- DROP TABLE IF EXISTS insumos;
-- DROP TABLE IF EXISTS fornecedores;
-- DROP TABLE IF EXISTS setores;
-- DROP TABLE IF EXISTS usuarios;

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
    nome       VARCHAR(150)  NOT NULL, -- Nome unico APENAS entre os setores ativos: validado no Service,
                                       -- nao ha restricao possivel no banco (unicidade condicional).
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
    CONSTRAINT ck_insumos_un_inteira CHECK (unidade_medida <> 'UN'    -- RN-02: UN = quantidade inteira
                                        OR quantidade_atual = FLOOR(quantidade_atual))
);

CREATE TABLE movimentacoes (
    id                BIGINT        NOT NULL AUTO_INCREMENT,
    insumo_id         BIGINT        NOT NULL,
    tipo              ENUM('ENTRADA','SAIDA') NOT NULL,
    quantidade        DECIMAL(15,3) NOT NULL,
    setor_id          BIGINT        NULL, -- obrigatorio em SAIDA (setor ativo) e nulo em ENTRADA;
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
                                        OR setor_id IS NOT NULL),                -- RN-06
    CONSTRAINT ck_mov_entrada_sem_setor CHECK (tipo = 'SAIDA'
                                        OR setor_id IS NULL),                    -- RN-06
    CONSTRAINT ck_mov_sem_lote_saida    CHECK (tipo = 'ENTRADA'
                                        OR (lote IS NULL AND validade IS NULL)), -- RN-06
    CONSTRAINT ck_mov_validade          CHECK (validade IS NULL
                                        OR validade >= data_movimentacao),
    CONSTRAINT ck_mov_saldo_calc        CHECK (                                  -- RN-05
        (tipo = 'ENTRADA' AND saldo_atual = saldo_anterior + quantidade) OR
        (tipo = 'SAIDA'   AND saldo_atual = saldo_anterior - quantidade))
);
