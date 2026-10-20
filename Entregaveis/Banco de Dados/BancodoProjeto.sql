-- ========================================================================
-- PROJETO INTEGRADOR - MÓDULO 2 - SENAI / SISTEMA FIEB
-- SISTEMA DESKTOP DE ALMOXARIFADO PARA INDÚSTRIA DE BEBIDAS
-- SCRIPT DE CRIAÇÃO E CARGA INICIAL DO BANCO DE DADOS
-- ========================================================================

CREATE DATABASE IF NOT EXISTS bebidas_db 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE bebidas_db;

-- 1. TABELA DE CARGOS
DROP TABLE IF EXISTS MOVIMENTACOES;
DROP TABLE IF EXISTS LOTES_INSUMO;
DROP TABLE IF EXISTS FUNCIONARIOS;
DROP TABLE IF EXISTS TIPOS_INSUMOS;
DROP TABLE IF EXISTS FORNECEDORES;
DROP TABLE IF EXISTS SETORES;
DROP TABLE IF EXISTS TIPO_MOVIMENTACAO;
DROP TABLE IF EXISTS CARGOS;

CREATE TABLE CARGOS (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL
) ENGINE = InnoDB;

-- Pelo menos 3 registros (Atendimento ao item 3.2 do edital)
INSERT INTO CARGOS (nome) VALUES 
('Supervisor de Produção'), 
('Operador de Estoque'),
('Gerente de Almoxarifado');

-- 2. TABELA DE TIPOS DE MOVIMENTAÇÃO
CREATE TABLE TIPO_MOVIMENTACAO (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL
) ENGINE = InnoDB;

-- Pelo menos 3 registros
INSERT INTO TIPO_MOVIMENTACAO (nome) VALUES 
('Entrada por Fornecedor'), 
('Saída por Produção'),
('Perda ou Avaria por Descarte');

-- 3. TABELA DE CATEGORIAS / TIPOS DE INSUMOS
CREATE TABLE TIPOS_INSUMOS (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    quantidade_minima INTEGER NOT NULL DEFAULT 0
) ENGINE = InnoDB;

-- Pelo menos 3 registros
INSERT INTO TIPOS_INSUMOS (nome, quantidade_minima) VALUES 
('Concentrado de Guaraná', 50), 
('Açúcar Cristal', 100), 
('Garrafa PET 2L', 500),
('Tampas Plásticas 28mm', 1000);

-- 4. TABELA DE FORNECEDORES
CREATE TABLE FORNECEDORES (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    cnpj VARCHAR(20) NOT NULL UNIQUE
) ENGINE = InnoDB;

-- Pelo menos 3 registros
INSERT INTO FORNECEDORES (nome, cnpj) VALUES 
('Concentrados do Brasil S.A.', '11.222.333/0001-44'), 
('PetEmbalagens Ltda', '55.666.777/0001-88'),
('Açucareira União & Cia', '33.444.555/0001-22');

-- 5. TABELA DE SETORES
CREATE TABLE SETORES (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL
) ENGINE = InnoDB;

-- Pelo menos 3 registros
INSERT INTO SETORES (nome) VALUES 
('Linha de Envase'), 
('Preparação de Xarope'),
('Almoxarifado Central');

-- 6. TABELA DE LOTES DE INSUMO
CREATE TABLE LOTES_INSUMO (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    tipos_insumos_id INTEGER NOT NULL,
    fornecedor_id INTEGER NOT NULL,
    lote VARCHAR(255) NOT NULL,
    validade DATE NOT NULL,
    volume INTEGER NOT NULL, 
    unidade_medida VARCHAR(50) NOT NULL,
    setor_id INTEGER NOT NULL,
    CONSTRAINT fk_insumos_tipos_insumos FOREIGN KEY (tipos_insumos_id) REFERENCES TIPOS_INSUMOS(id),
    CONSTRAINT fk_insumos_fornecedores FOREIGN KEY (fornecedor_id) REFERENCES FORNECEDORES(id),
    CONSTRAINT fk_insumos_setores FOREIGN KEY (setor_id) REFERENCES SETORES(id)
) ENGINE = InnoDB;

-- Pelo menos 3 registros
INSERT INTO LOTES_INSUMO (tipos_insumos_id, fornecedor_id, lote, validade, volume, unidade_medida, setor_id) VALUES 
(1, 1, 'LOT-GUA-01', '2027-06-30', 200, 'Litros', 3),
(2, 3, 'LOT-ACU-10', '2028-12-31', 500, 'Quilos', 3),
(3, 2, 'LOT-PET-88', '2029-12-31', 2000, 'Unidades', 3);

-- 7. TABELA DE FUNCIONÁRIOS (USUÁRIOS DO SISTEMA)
CREATE TABLE FUNCIONARIOS (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,   
    nome VARCHAR(255) NOT NULL,  
    cpf VARCHAR(14) NOT NULL UNIQUE, 
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    cargo_id INTEGER NOT NULL,
    CONSTRAINT fk_funcionarios_cargos FOREIGN KEY (cargo_id) REFERENCES CARGOS(id)
) ENGINE = InnoDB;

-- Pelo menos 3 registros
INSERT INTO FUNCIONARIOS (nome, cpf, email, senha, cargo_id) VALUES 
('João Silva', '123.456.789-00', 'joao@bebidas.com', 'senha123', 1),
('Maria Souza', '987.654.321-11', 'maria@bebidas.com', 'senha456', 2),
('Carlos Eduardo', '456.789.123-22', 'carlos@bebidas.com', 'senha789', 3);

-- 8. TABELA DE MOVIMENTAÇÕES DE ESTOQUE
CREATE TABLE MOVIMENTACOES (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    lotes_insumo_id INTEGER NOT NULL,
    tipo_movimentacao_id INTEGER NOT NULL,
    quantidade INTEGER NOT NULL,
    setor_id INTEGER NOT NULL,
    funcionario_id INTEGER NOT NULL,
    data_movimentacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_movimentacoes_lote FOREIGN KEY (lotes_insumo_id) REFERENCES LOTES_INSUMO(id),
    CONSTRAINT fk_movimentacoes_tipo FOREIGN KEY (tipo_movimentacao_id) REFERENCES TIPO_MOVIMENTACAO(id),
    CONSTRAINT fk_movimentacoes_setor FOREIGN KEY (setor_id) REFERENCES SETORES(id),
    CONSTRAINT fk_movimentacoes_funcionario FOREIGN KEY (funcionario_id) REFERENCES FUNCIONARIOS(id)
) ENGINE = InnoDB;

-- Entradas iniciais correspondentes aos lotes
INSERT INTO MOVIMENTACOES (lotes_insumo_id, tipo_movimentacao_id, quantidade, setor_id, funcionario_id, data_movimentacao) VALUES 
(1, 1, 200, 3, 2, NOW()), 
(2, 1, 500, 3, 2, NOW()),
(3, 1, 2000, 3, 2, NOW()); 

-- Saídas para as linhas de produção (envase e preparação)
INSERT INTO MOVIMENTACOES (lotes_insumo_id, tipo_movimentacao_id, quantidade, setor_id, funcionario_id, data_movimentacao) VALUES 
(1, 2, 60, 2, 1, NOW()),   
(2, 2, 100, 2, 1, NOW()),
(3, 2, 1600, 1, 1, NOW()); 

-- ========================================================================
-- VIEWS DE CONSULTA DINÂMICA DE SALDO
-- ========================================================================

-- Saldo disponível por lote específico
CREATE OR REPLACE VIEW vw_quantidade_atual_por_lote AS
SELECT 
    ti.id AS tipo_insumo_id,
    ti.nome AS insumo_nome,
    li.id AS lote_id,
    li.lote AS lote_codigo,
    li.validade AS lote_validade,
    li.unidade_medida AS unidade_medida,
    COALESCE(SUM(
        CASE 
            WHEN tm.nome LIKE '%Entrada%' THEN m.quantidade
            WHEN tm.nome LIKE '%Saída%' OR tm.nome LIKE '%Saida%' OR tm.nome LIKE '%Perda%' THEN -m.quantidade
            ELSE 0 
        END
    ), 0) AS quantidade_disponivel
FROM LOTES_INSUMO li
JOIN TIPOS_INSUMOS ti ON li.tipos_insumos_id = ti.id
LEFT JOIN MOVIMENTACOES m ON m.lotes_insumo_id = li.id
LEFT JOIN TIPO_MOVIMENTACAO tm ON m.tipo_movimentacao_id = tm.id
GROUP BY ti.id, ti.nome, li.id, li.lote, li.validade, li.unidade_medida;

-- Saldo total consolidado por insumo e verificação de nível crítico
CREATE OR REPLACE VIEW vw_quantidade_atual AS
SELECT 
    ti.id AS id,
    ti.nome AS nome,
    ti.quantidade_minima AS quantidade_minima,
    COALESCE(SUM(
        CASE 
            WHEN tm.nome LIKE '%Entrada%' THEN m.quantidade
            WHEN tm.nome LIKE '%Saída%' OR tm.nome LIKE '%Saida%' OR tm.nome LIKE '%Perda%' THEN -m.quantidade
            ELSE 0 
        END
    ), 0) AS quantidade_disponivel,
    CASE 
        WHEN COALESCE(SUM(
            CASE 
                WHEN tm.nome LIKE '%Entrada%' THEN m.quantidade
                WHEN tm.nome LIKE '%Saída%' OR tm.nome LIKE '%Saida%' OR tm.nome LIKE '%Perda%' THEN -m.quantidade
                ELSE 0 
            END
        ), 0) <= ti.quantidade_minima THEN 'CRÍTICO'
        ELSE 'NORMAL'
    END AS status_estoque
FROM TIPOS_INSUMOS ti
LEFT JOIN LOTES_INSUMO li ON li.tipos_insumos_id = ti.id
LEFT JOIN MOVIMENTACOES m ON m.lotes_insumo_id = li.id
LEFT JOIN TIPO_MOVIMENTACAO tm ON m.tipo_movimentacao_id = tm.id
GROUP BY ti.id, ti.nome, ti.quantidade_minima;

-- Consulta de teste: Insumos que demandam alerta imediato
SELECT * FROM vw_quantidade_atual WHERE status_estoque = 'CRÍTICO';
