## Regras de Negócio

- **RN-01** - Cada insumo deve ter registrado: fornecedor, lote, data de validade, volume, unidade de medida e setor de utilização.

- **RN-02** -  quando o nível de um insumo estiver abaixo do limite seguro definido previamente pela empresa, alertas automáticos devem ser emitidos.

- **RN-03** - Toda movimentação deve registrar obrigatoriamente: usuário responsável, setor solicitante, data da operação e tipo de movimentação (retirada ou reposição).

- **RN-04** - O histórico completo de movimentações deve ser mantido para garantir rastreabilidade, organização e controle das operações.

- **RN-05** - O acesso ao sistema é restrito a usuários autenticados por usuário e senha cadastrados.

- **RN-06** - Os insumos devem ser apresentados em ordem alfabética

---

## Requisitos Funcionais

- **RF-01** - O sistema deve validar usuário e senha cadastrados no sistema.
- **RF-02** - A tela inicial deve exibir o nome do usuário autenticado.
- **RF-03** - O usuario deve poder gerenciar os insumos e ter acesso as movimentações de materiais
- **RF-04** - O usuário deve poder ver todos os insumos, incluir novos insumos e atualizar ou remover insumos cadastrados
- **RF-05** - O sistema deve validar os dados informados antes da gravação no banco de dados.
- **RF-06** - O usuário deve poder registrar operações de retirada ou reposição de materiais.
- **RF-07** - O sistema deve registrar o responsável pela operação executada.
- **RF-08** - O sistema deve informar a data da movimentação realizada.
- **RF-09** - O sistema deve lançar uma notificação automática quando determinado insumo atingir quantidade crítica.

## Requisitos Não Funcionais

**RNF-01** - A aplicação deve ser do tipo desktop.

**RNF-02** - O sistema deve garantir rastreabilidade de todas as movimentações realizadas (histórico persistente).

**RNF-03** - O banco de dados deve ser relacional, com nome `bebidas_db`, contendo tabelas com tipos de dados, chaves primárias e estrangeiras.

