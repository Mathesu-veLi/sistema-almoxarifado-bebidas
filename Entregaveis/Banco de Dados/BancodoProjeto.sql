CREATE TABLE CARGOS (

  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR (255)

)ENGINE = innoDB;

INSERT INTO CARGOS (nome) VALUES ('Supervisor de produção') , ('Operador de estoque');
SELECT * FROM CARGOS;



CREATE TABLE TIPO_MOVIMENTACAO (

  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR (50)

)ENGINE = innoDB;

INSERT INTO TIPO_MOVIMENTACAO (nome) VALUES ('Entrada por fornecedor') , ('Saída por produção');
SELECT * FROM TIPO_MOVIMENTACAO;


CREATE TABLE TIPOS_INSUMOS (

  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR (255),
  quantidade_minima INTEGER

)ENGINE = innoDB;

INSERT INTO TIPOS_INSUMOS (nome, quantidade_minima)
VALUES ('Concentrado de guaraná',50), ('Açúcar Cristal' , 100) , ('Garrafa PET 2L' , 500);
SELECT * FROM TIPOS_INSUMOS;

CREATE TABLE FORNECEDORES (

  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR (255),
  cnpj VARCHAR (255)

)ENGINE = innoDB;

INSERT INTO FORNECEDORES (nome, cnpj) 
VALUES ('Concentrados do Brasil S.A.', '11.222.333/0001-44'), ('PetEmbalagens Ltda', '55.666.777/0001-88');
SELECT * FROM FORNECEDORES;



CREATE TABLE SETORES (

  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR (255)

)ENGINE = innoDB;

INSERT INTO SETORES (nome) VALUES ('Linha de Envase'), ('Preparação de Xarope');
SELECT * FROM SETORES;





CREATE TABLE LOTES_INSUMO (

  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  tipos_insumos_id INTEGER,
  fornecedor_id INTEGER,
  lote VARCHAR (255),
  validade DATE,
  volume INTEGER, 
  unidade_medida VARCHAR (255),
  setor_id INTEGER


)ENGINE = innoDB;

ALTER TABLE LOTES_INSUMO
ADD CONSTRAINT fk_insumos_tipos_insumos_id_tipos_insumos
FOREIGN KEY (tipos_insumos_id) REFERENCES TIPOS_INSUMOS(id),
ADD CONSTRAINT fk_insumos_fornecedor_id_fornecedores
FOREIGN KEY (fornecedor_id) REFERENCES FORNECEDORES (id),
ADD CONSTRAINT fk_insumos_setor_id_setores
FOREIGN KEY (setor_id) REFERENCES SETORES (id);

INSERT INTO LOTES_INSUMO (tipos_insumos_id, fornecedor_id, lote, validade, volume, unidade_medida, setor_id)
VALUES (1, 1, 'LOT-GUA-01', '2027-06-30', 200, 'Litros', 2);

INSERT INTO LOTES_INSUMO (tipos_insumos_id, fornecedor_id, lote, validade, volume, unidade_medida, setor_id)
VALUES (3, 2, 'LOT-PET-88', '2029-12-31', 2000, 'Unidades', 1);

SELECT * FROM LOTES_INSUMO;




CREATE TABLE FUNCIONARIOS (

  id INTEGER PRIMARY KEY AUTO_INCREMENT,   
  nome VARCHAR (255),  
  cpf VARCHAR (255), 
  email VARCHAR (255),
  senha VARCHAR (255),
  cargo_id INTEGER

)ENGINE = innoDB;

ALTER TABLE FUNCIONARIOS
ADD CONSTRAINT fk_funcionarios_cargos_id_cargo FOREIGN KEY (cargo_id) references CARGOS (id);

INSERT INTO FUNCIONARIOS (nome, cpf, email, senha, cargo_id) 
VALUES ('João Silva', '123.456.789-00', 'joao@bebidas.com', 'senha123', 1),
('Maria Souza', '987.654.321-11', 'maria@bebidas.com', 'senha456', 2);
SELECT * FROM FUNCIONARIOS;



CREATE TABLE  MOVIMENTACOES (

  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  lotes_insumo_id INTEGER,
  tipo_movimentacao_id INTEGER,
  quantidade INTEGER,
  setor_id INTEGER,
  funcionario_id INTEGER,
  data_ DATETIME

)ENGINE = innoDB;

ALTER TABLE MOVIMENTACOES
ADD CONSTRAINT fk_movimentacoes_tipo_movimentacao_id_tipo_movimentacao 
FOREIGN KEY (tipo_movimentacao_id) REFERENCES TIPO_MOVIMENTACAO(id),
ADD CONSTRAINT fk_funcionariosid
FOREIGN KEY (funcionario_id) REFERENCES FUNCIONARIOS(id),
ADD CONSTRAINT fk_setorid
FOREIGN KEY (setor_id) REFERENCES SETORES (id),
ADD CONSTRAINT fk_lotesinsumoid
FOREIGN KEY (lotes_insumo_id) REFERENCES LOTES_INSUMO(id);


INSERT INTO MOVIMENTACOES (lotes_insumo_id, tipo_movimentacao_id, quantidade, setor_id, funcionario_id, data_)
VALUES (1, 1, 200, 2, 2, NOW()), 
       (2, 1, 2000, 1, 2, NOW()); 


INSERT INTO MOVIMENTACOES (lotes_insumo_id, tipo_movimentacao_id, quantidade, setor_id, funcionario_id, data_)
VALUES (1, 2, 60, 2, 1, NOW()),   
       (2, 2, 1600, 1, 1, NOW()); 



CREATE OR REPLACE VIEW vw_quantidade_atual_por_lote AS
SELECT 
    ti.nome AS nome,
    li.lote AS lote,
    li.validade AS validade,
    COALESCE(SUM(
        CASE 
            WHEN tm.nome LIKE '%Entrada%' THEN m.quantidade
            WHEN tm.nome LIKE '%Saída%' OR tm.nome LIKE '%Saida%' THEN -m.quantidade
            ELSE 0 
        END
    ), 0) AS quantidade_disponivel
FROM LOTES_INSUMO li
JOIN TIPOS_INSUMOS ti ON li.tipos_insumos_id = ti.id
LEFT JOIN MOVIMENTACOES m ON m.lotes_insumo_id = li.id
LEFT JOIN TIPO_MOVIMENTACAO tm ON m.tipo_movimentacao_id = tm.id
GROUP BY ti.nome, li.lote, li.validade;




CREATE OR REPLACE VIEW vw_quantidade_atual AS
SELECT 
    ti.nome AS nome,
    COALESCE(SUM(
        CASE 
            WHEN tm.nome LIKE '%Entrada%' THEN m.quantidade
            WHEN tm.nome LIKE '%Saída%' OR tm.nome LIKE '%Saida%' THEN -m.quantidade
            ELSE 0 
        END
    ), 0) AS quantidade_disponivel,
    ti.quantidade_minima AS quantidade_minima
FROM TIPOS_INSUMOS ti
LEFT JOIN LOTES_INSUMO li ON li.tipos_insumos_id = ti.id
LEFT JOIN MOVIMENTACOES m ON m.lotes_insumo_id = li.id
LEFT JOIN TIPO_MOVIMENTACAO tm ON m.tipo_movimentacao_id = tm.id
GROUP BY ti.nome, ti.quantidade_minima;


SELECT * FROM vw_quantidade_atual_por_lote;


SELECT nome, quantidade_disponivel, quantidade_minima 
FROM vw_quantidade_atual 
WHERE quantidade_disponivel < quantidade_minima;















