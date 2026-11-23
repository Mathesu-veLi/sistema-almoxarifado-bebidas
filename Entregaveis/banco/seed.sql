-- Dados de demostracao para o banco bebidas_db
-- Execute apos o schema.sql. Senhas sao apenas exemplos (hash BCrypt ficticio).
-- Substitua por hashes reais gerados com BCrypt antes de qualquer uso em homologacao/producao.

USE bebidas_db;

-- ============================================================================
-- Usuarios (id 1 = admin, id 2 = operador)
-- ============================================================================
INSERT INTO usuarios (id, nome, login, senha_hash, ativo) VALUES
(1, 'Administrador', 'admin', '$2a$10$EXEMPLOHASHBCRYPTADMINEXEMPLOHASHBCRYPTADMIN', 1),
(2, 'Operador de Almoxarifado', 'operador', '$2a$10$EXEMPLOHASHBCRYPTADMINEXEMPLOHASHBCRYPTADMIN', 1);

-- ============================================================================
-- Setores
-- ============================================================================
INSERT INTO setores (id, nome, descricao, ativo) VALUES
(1, 'Producao', 'Setor de engarrafamento e rotulagem', 1),
(2, 'Expedicao', 'Setor de expedição de produtos acabados', 1),
(3, 'Estoque', 'Setor de estoque geral de insumos', 1);

-- ============================================================================
-- Fornecedores
-- ============================================================================
INSERT INTO fornecedores (id, nome, cnpj, telefone, ativo) VALUES
(1, 'Bebidas S.A.', '12345678000190', '11999990000', 1),
(2, 'Copos & Tampas LTDA', '98765432000180', '11888880000', 1);

-- ============================================================================
-- Insumos
-- quantidade_atual reflete o saldo apos as movimentacoes abaixo
-- ============================================================================
INSERT INTO insumos (id, nome, unidade_medida, quantidade_atual, estoque_minimo, fornecedor_id, ativo) VALUES
(1, 'Garrafa PET 600ml', 'UN', 70, 20, 1, 1),
(2, 'Rotulo Cerveja 600ml', 'UN', 500, 100, 1, 1),
(3, 'Gas CO2', 'KG', 50, 10, 1, 1),
(4, 'Agua Mineral', 'L', 200, 50, 1, 1),
(5, 'Tampinha Plastica', 'UN', 1000, 200, 2, 1);

-- ============================================================================
-- Movimentacoes (saldo_anterior e saldo_atual conferem com a quantidade_atual)
-- ============================================================================
-- Garrafa PET 600ml: entrada de 100, saida de 30 -> saldo 70
INSERT INTO movimentacoes (id, insumo_id, tipo, quantidade, setor_id, usuario_id, data_movimentacao, data_registro, lote, validade, saldo_anterior, saldo_atual) VALUES
(1, 1, 'ENTRADA', 100, NULL, 1, '2026-01-10', '2026-01-10 08:00:00', 'LOTE-202601-A', '2027-01-10', 0, 100),
(2, 1, 'SAIDA', 30, 1, 2, '2026-01-15', '2026-01-15 09:30:00', NULL, NULL, 100, 70);

-- Rotulo Cerveja 600ml: entrada de 500 -> saldo 500
INSERT INTO movimentacoes (id, insumo_id, tipo, quantidade, setor_id, usuario_id, data_movimentacao, data_registro, lote, validade, saldo_anterior, saldo_atual) VALUES
(3, 2, 'ENTRADA', 500, NULL, 1, '2026-01-10', '2026-01-10 08:15:00', 'LOTE-ROT-202601', '2027-01-10', 0, 500);

-- Gas CO2: entrada de 50 kg -> saldo 50
INSERT INTO movimentacoes (id, insumo_id, tipo, quantidade, setor_id, usuario_id, data_movimentacao, data_registro, lote, validade, saldo_anterior, saldo_atual) VALUES
(4, 3, 'ENTRADA', 50, NULL, 1, '2026-01-10', '2026-01-10 08:30:00', 'LOTE-CO2-202601', '2026-07-10', 0, 50);

-- Agua Mineral: entrada de 200 L -> saldo 200
INSERT INTO movimentacoes (id, insumo_id, tipo, quantidade, setor_id, usuario_id, data_movimentacao, data_registro, lote, validade, saldo_anterior, saldo_atual) VALUES
(5, 4, 'ENTRADA', 200, NULL, 1, '2026-01-10', '2026-01-10 08:45:00', 'LOTE-AGU-202601', '2027-01-10', 0, 200);

-- Tampinha Plastica: entrada de 1000 -> saldo 1000
INSERT INTO movimentacoes (id, insumo_id, tipo, quantidade, setor_id, usuario_id, data_movimentacao, data_registro, lote, validade, saldo_anterior, saldo_atual) VALUES
(6, 5, 'ENTRADA', 1000, NULL, 1, '2026-01-10', '2026-01-10 09:00:00', 'LOTE-TAM-202601', NULL, 0, 1000);
