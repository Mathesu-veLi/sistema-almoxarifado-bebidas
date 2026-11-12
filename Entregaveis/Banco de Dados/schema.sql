-- =====================================================================
-- Projeto Integrador — Módulo 2 — Indústria de Bebidas
-- Script de estrutura (schema.sql) do banco de dados
--
-- Fonte deste schema: Entregaveis/Banco de Dados/bebidas_db_diagram.dbml
--   (diagrama de banco de dados produzido pela equipe — Entrega 9)
-- Fonte das regras: Diversos/Contrato_Tecnico_API_Industria_Bebidas.docx
--                   Diversos/Enunciado_Projeto_Integrador_Industria_Bebidas.docx
--
-- SGBD ....: MySQL 8.x
-- Engine ...: InnoDB   (transações, FKs e locks de linha)
-- Charset ..: utf8mb4  (nome de insumo, setor, fornecedor e usuário)
-- Collation : utf8mb4_0900_ai_ci (sem distinção de caixa/acento — atende
--             a busca parcial e sem caixa exigida no GET /api/insumos)
--
-- Chaves ......: BIGINT (Long nas entidades JPA)
-- data_registro: DATETIME(6) — guarda data/hora com microssegundos sem a
--                conversão automática de fuso do TIMESTAMP e sem o limite
--                de 2038.
--
-- Observações:
--   * As movimentações são imutáveis (RN-07): nenhuma FK usa ON DELETE
--     CASCADE e o histórico nunca é apagado junto com o cadastro.
--   * Não existe tabela de saldo por lote (RN-06): lote e validade são
--     apenas informativos na própria movimentação.
--   * As regras que o banco não consegue expressar sozinho (setor
--     obrigatório apenas em SAIDA, nome de setor único apenas entre
--     ativos, saldo nunca negativo, data não futura, unidade imutável
--     após movimento) ficam na camada Service da API.
-- =====================================================================

-- Criação do banco (ajuste o nome se a disciplina definir outro).
CREATE DATABASE IF NOT EXISTS bebidas_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_0900_ai_ci;

USE bebidas_db;

-- Para recriar o banco do zero em ambiente de teste, descomente o bloco
-- abaixo (a ordem respeita as dependências das FKs).
-- ATENÇÃO: apaga os dados existentes.
--
-- DROP TABLE IF EXISTS movimentacoes;
-- DROP TABLE IF EXISTS insumos;
-- DROP TABLE IF EXISTS fornecedores;
-- DROP TABLE IF EXISTS setores;
-- DROP TABLE IF EXISTS usuarios;

-- ---------------------------------------------------------------------
-- usuarios — cadastro de usuários da API (RN-14, RN-15)
-- DELETE /api/usuarios/{id} sempre inativa (ativo = 0); nunca apaga.
-- ---------------------------------------------------------------------
CREATE TABLE usuarios (
    id          BIGINT        NOT NULL AUTO_INCREMENT,
    nome        VARCHAR(150)  NOT NULL,
    login       VARCHAR(50)   NOT NULL,
    senha_hash  VARCHAR(255)  NOT NULL,               -- BCrypt, nunca senha em texto puro
    ativo       TINYINT(1)    NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    CONSTRAINT uk_usuarios_login UNIQUE (login)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ---------------------------------------------------------------------
-- setores — setores da indústria (RN-06: SAIDA exige setor ativo)
-- Nome único apenas entre os setores ATIVOS (regra RN/contrato): o
-- Service valida; o banco não cria UNIQUE porque não há índice parcial
-- simples no MySQL e um UNIQUE global impediria reaproveitar o nome de
-- um setor já inativo.
-- ---------------------------------------------------------------------
CREATE TABLE setores (
    id         BIGINT        NOT NULL AUTO_INCREMENT,
    nome       VARCHAR(150)  NOT NULL,
    descricao  VARCHAR(255)  NULL,
    ativo      TINYINT(1)    NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    KEY idx_setores_nome (nome)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ---------------------------------------------------------------------
-- fornecedores — RN-11: cada insumo tem um único fornecedor principal
-- cnpj armazenado somente com os 14 dígitos, sem máscara.
-- ---------------------------------------------------------------------
CREATE TABLE fornecedores (
    id        BIGINT        NOT NULL AUTO_INCREMENT,
    nome      VARCHAR(150)  NOT NULL,
    cnpj      CHAR(14)      NOT NULL,
    telefone  VARCHAR(20)   NULL,
    ativo     TINYINT(1)    NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    CONSTRAINT uk_fornecedores_cnpj UNIQUE (cnpj),
    KEY idx_fornecedores_nome (nome)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ---------------------------------------------------------------------
-- insumos — saldo controlado por movimentação (RN-01, RN-02, RN-12)
-- quantidade_atual só muda por ENTRADA/SAIDA; o cadastro nasce em 0.
-- UN  -> quantidades inteiras; KG/L -> até 3 casas decimais.
-- ---------------------------------------------------------------------
CREATE TABLE insumos (
    id               BIGINT        NOT NULL AUTO_INCREMENT,
    nome             VARCHAR(150)  NOT NULL,
    unidade_medida   ENUM('UN','KG','L') NOT NULL,
    quantidade_atual DECIMAL(15,3) NOT NULL DEFAULT 0,
    estoque_minimo   DECIMAL(15,3) NOT NULL,          -- alerta quando quantidade_atual <= estoque_minimo
    fornecedor_id    BIGINT        NOT NULL,          -- fornecedor principal (RN-11)
    ativo            TINYINT(1)    NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    KEY idx_insumos_nome_ativo (nome, ativo),
    KEY idx_insumos_fornecedor (fornecedor_id),
    CONSTRAINT fk_insumos_fornecedor
        FOREIGN KEY (fornecedor_id) REFERENCES fornecedores (id)
        ON DELETE RESTRICT
        ON UPDATE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ---------------------------------------------------------------------
-- movimentacoes — histórico imutável do estoque (RN-04, RN-05, RN-07)
--   * setor_id: obrigatório em SAIDA (setor ativo) e nulo em ENTRADA;
--     a obrigatoriedade condicional é validada no Service.
--   * lote/validade: opcionais, apenas em ENTRADA, sem saldo por lote.
--   * data_movimentacao: data operacional informada pelo cliente,
--     nunca futura (validada no Service) e apenas informativa/filtro.
--   * data_registro: gerado pelo servidor no instante do lançamento.
--   * saldo_anterior / saldo_atual: fotografam o saldo do insumo.
-- Não há endpoints de edição ou exclusão deste histórico.
-- ---------------------------------------------------------------------
CREATE TABLE movimentacoes (
    id                BIGINT        NOT NULL AUTO_INCREMENT,
    insumo_id         BIGINT        NOT NULL,
    tipo              ENUM('ENTRADA','SAIDA') NOT NULL,
    quantidade        DECIMAL(15,3) NOT NULL,
    setor_id          BIGINT        NULL,
    usuario_id        BIGINT        NOT NULL,         -- vem do JWT, nunca do corpo
    data_movimentacao DATE          NOT NULL,
    data_registro     DATETIME(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    lote              VARCHAR(50)   NULL,
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
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- =====================================================================
-- Fim do script de estrutura.
-- Dados de demonstração ficam em arquivo separado (seed.sql).
-- =====================================================================
