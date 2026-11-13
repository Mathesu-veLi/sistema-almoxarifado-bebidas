-- Criação do banco
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
    CONSTRAINT uk_usuarios_login UNIQUE (login)
)

CREATE TABLE setores (
    id         BIGINT        NOT NULL AUTO_INCREMENT,
    nome       VARCHAR(150)  NOT NULL, -- Nome único apenas entre os setores ATIVOS
    descricao  VARCHAR(255)  NULL,
    ativo      TINYINT(1)    NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    KEY idx_setores_nome (nome)
)

CREATE TABLE fornecedores (
    id        BIGINT        NOT NULL AUTO_INCREMENT,
    nome      VARCHAR(150)  NOT NULL,
    cnpj      CHAR(14)      NOT NULL,
    telefone  VARCHAR(20)   NULL,
    ativo     TINYINT(1)    NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    CONSTRAINT uk_fornecedores_cnpj UNIQUE (cnpj),
    KEY idx_fornecedores_nome (nome)
)

CREATE TABLE insumos (
    id               BIGINT        NOT NULL AUTO_INCREMENT,
    nome             VARCHAR(150)  NOT NULL,
    unidade_medida   ENUM('UN','KG','L') NOT NULL, -- UN  -> quantidades inteiras; KG/L -> até 3 casas decimais.
    quantidade_atual DECIMAL(15,3) NOT NULL DEFAULT 0,
    estoque_minimo   DECIMAL(15,3) NOT NULL,          -- alerta quando quantidade_atual <= estoque_minimo
    fornecedor_id    BIGINT        NOT NULL,          
    ativo            TINYINT(1)    NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    KEY idx_insumos_nome_ativo (nome, ativo),
    KEY idx_insumos_fornecedor (fornecedor_id),
    CONSTRAINT fk_insumos_fornecedor
        FOREIGN KEY (fornecedor_id) REFERENCES fornecedores (id)
        ON DELETE RESTRICT
        ON UPDATE RESTRICT
)

CREATE TABLE movimentacoes (
    id                BIGINT        NOT NULL AUTO_INCREMENT,
    insumo_id         BIGINT        NOT NULL,
    tipo              ENUM('ENTRADA','SAIDA') NOT NULL,
    quantidade        DECIMAL(15,3) NOT NULL,
    setor_id          BIGINT        NULL, -- obrigatório em SAIDA (setor ativo) e nulo em ENTRADA;
    usuario_id        BIGINT        NOT NULL,         -- vem do JWT, nunca do corpo
    data_movimentacao DATE          NOT NULL,
    data_registro     DATETIME(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    lote              VARCHAR(50)   NULL, -- lote/validade: opcionais, apenas em ENTRADA, sem saldo por lote.
    validade          DATE          NULL,
    saldo_anterior    DECIMAL(15,3) NOT NULL,
    saldo_atual       DECIMAL(15,3) NOT NULL,
    PRIMARY KEY (id),
    KEY idx_mov_insumo_registro (insumo_id, data_registro),
    KEY idx_mov_data (data_movimentacao),
    KEY idx_mov_setor (setor_id),
    KEY idx_mov_usuario (usuario_id),
    KEY idx_mov_tipo (tipo),
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
        ON UPDATE RESTRICT
)