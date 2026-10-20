# Documento de Requisitos e Regras de Negócio

**Projeto:** Sistema Desktop de Almoxarifado para Indústria de Bebidas  
**Curso:** Técnico em Desenvolvimento de Sistemas - SENAI / Sistema FIEB  
**Módulo:** 2 - Projeto Integrador  

---

## 1. Contexto do Projeto
A empresa do setor de bebidas produz refrigerantes, sucos e energéticos. O controle manual (planilhas e papel) de insumos e embalagens gera inconsistências, perdas por validade de insumos com alta rotatividade e atrasos nas linhas de envase e produção.
O sistema desktop a ser implementado visa sanar esses problemas centralizando o cadastro, a movimentação, a rastreabilidade e o controle automático de níveis críticos de estoque.

---

## 2. Regras de Negócio (RN)

* **RN-01 — Atributos Obrigatórios do Insumo e Lote:**  
  Cada insumo estocado deve possuir obrigatoriamente: fornecedor, identificador de lote, data de validade, volume/quantidade, unidade de medida (litros, kg, unidades, etc.) e setor de utilização/armazenamento.
* **RN-02 — Alerta Automático de Nível Crítico:**  
  Quando a quantidade disponível de um insumo for inferior ou igual à quantidade mínima de segurança cadastrada pela empresa, o sistema deve emitir um alerta visual/notificação imediata ao usuário.
* **RN-03 — Obrigatoriedade de Rastreabilidade na Movimentação:**  
  Toda e qualquer operação de movimentação (entrada ou saída) deve registrar obrigatoriamente: o usuário autenticado responsável pela operação, o setor solicitante, a data/hora exata e o tipo de movimentação.
* **RN-04 — Persistência Histórica e Imutabilidade do Histórico:**  
  O histórico de movimentações não pode ser excluído fisicamente para garantir auditoria, conformidade com normas industriais e rastreabilidade total.
* **RN-05 — Ordenação Alfabética por Algoritmo de Ordenação:**  
  Na tela de movimentação, os insumos cadastrados devem ser apresentados em ordem alfabética utilizando um algoritmo de ordenação (ex: Quicksort) implementado pela equipe.
* **RN-06 — Não Negatividade de Saldo:**  
  Nenhuma operação de retirada pode ser confirmada se a quantidade solicitada for superior ao saldo disponível do lote/insumo.

---

## 3. Requisitos Funcionais (RF)

| ID | Nome | Descrição |
|---|---|---|
| **RF-01** | Autenticação de Usuários | O sistema deve validar login (email/usuário) e senha contra os registros de funcionários no banco de dados. |
| **RF-02** | Tratamento de Acesso Inválido | Em caso de credenciais inválidas ou campos vazios, exibir mensagem amigável e permitir nova tentativa sem fechar o sistema. |
| **RF-03** | Identificação na Tela Inicial | A tela inicial deve exibir o nome do usuário autenticado e seu respectivo cargo. |
| **RF-04** | Encerramento de Sessão (Logout) | A tela inicial deve permitir encerrar a sessão do usuário com segurança, retornando à tela de login. |
| **RF-05** | Navegação Principal | A tela inicial deve disponibilizar botões claros de navegação para a tela de **Gerenciamento de Insumos** e para a tela de **Movimentação de Materiais**. |
| **RF-06** | Exibição Tabular de Insumos | O sistema deve exibir a listagem de insumos e lotes em formato de tabela (`TableView`), com informações claras de nome, quantidade mínima e lotes. |
| **RF-07** | Pesquisa Dinâmica de Insumos | Permitir que o operador filtre a lista de insumos por termo/palavra-chave (nome do insumo) em tempo real. |
| **RF-08** | Inclusão de Novos Insumos | Permitir o cadastro de novos insumos com nome, categoria e quantidade mínima de estoque seguro. |
| **RF-09** | Atualização de Insumos | Permitir editar dados cadastrais de insumos já existentes. |
| **RF-10** | Remoção de Insumos | Permitir inativar ou remover insumo cadastrado, impedindo exclusão caso haja movimentações atreladas (integridade referencial). |
| **RF-11** | Validação Prévia de Dados | Validar todos os campos obrigatórios e formatos antes de submeter a gravação ao banco de dados. |
| **RF-12** | Registro de Movimentação | Permitir registrar operações de entrada (reposição) e saída (retirada para produção), informando lote, quantidade, setor solicitante e data. |
| **RF-13** | Associação Automática de Responsável | Vincular automaticamente o usuário logado na sessão como responsável pela movimentação. |
| **RF-14** | Ordenação Alfabética de Insumos | Apresentar os insumos ordenados alfabeticamente na tela de movimentação através do algoritmo Quicksort. |
| **RF-15** | Alerta Visual de Estoque Crítico | Notificar o usuário imediatamente quando a quantidade de um insumo atingir ou ultrapassar o limite crítico. |
| **RF-16** | Retorno à Tela Principal | As telas secundárias (Gerenciamento e Movimentação) devem possuir botão funcional de retorno à tela inicial. |

---

## 4. Requisitos Não Funcionais (RNF)

* **RNF-01 — Plataforma Desktop:** A aplicação deve ser desenvolvida exclusivamente para ambiente desktop utilizando **Java + JavaFX**, compatível com Apache NetBeans.
* **RNF-02 — Banco de Dados Relacional:** O SGBD utilizado deve ser relacional (MySQL/MariaDB), com o schema obrigatoriamente nomeado como `bebidas_db`.
* **RNF-03 — Integridade e Carga Mínima de Dados:** Todas as tabelas devem possuir tipos compatíveis, chaves primárias (`PRIMARY KEY`), chaves estrangeiras (`FOREIGN KEY`) e pelo menos **3 registros iniciais cadastrados**.
* **RNF-04 — Usabilidade e Ergonomia:** Interface com fontes legíveis, cores padronizadas e feedback ao usuário (diálogos modais informativos, de erro e de confirmação).
* **RNF-05 — Segurança Básica de Credenciais:** As senhas dos funcionários não devem trafegar em texto claro em logs ou telas.
