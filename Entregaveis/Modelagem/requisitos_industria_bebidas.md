## Regras de Negócio

- **RN-01** - Cada insumo deve ter registrado: fornecedor, lote, data de validade, volume, unidade de medida e setor de utilização.
- **RN-02** - Quando o nível de um insumo estiver abaixo do limite seguro definido previamente pela empresa, alertas automáticos devem ser emitidos.
- **RN-03** - Toda movimentação deve registrar obrigatoriamente: usuário responsável, setor solicitante, data da operação e tipo de movimentação (retirada ou reposição).
- **RN-04** - O histórico completo de movimentações deve ser mantido para garantir rastreabilidade, organização e controle das operações.
- **RN-05** - Os insumos devem ser apresentados em ordem alfabética através de um algoritmo de ordenação.

---

## Requisitos Funcionais

- **RF-01** - O sistema deve permitir a autenticação do usuário validando usuário e senha cadastrados.
- **RF-02** - O sistema deve informar o problema e permitir nova tentativa de autenticação em caso de credenciais inválidas.
- **RF-03** - A tela inicial deve exibir o nome do usuário autenticado.
- **RF-04** - A tela inicial deve disponibilizar funcionalidade para encerramento da sessão (logout) do usuário.
- **RF-05** - A tela inicial deve permitir o acesso às telas de gerenciamento de insumos e de movimentação de materiais.
- **RF-06** - O sistema deve exibir os insumos cadastrados em formato tabular na tela de gerenciamento.
- **RF-07** - O sistema deve disponibilizar pesquisa de insumos por termo/palavra-chave informada pelo usuário.
- **RF-08** - O usuário deve poder incluir novos insumos, atualizar dados ou remover insumos cadastrados.
- **RF-09** - O sistema deve validar os dados dos insumos antes da gravação no banco de dados.
- **RF-10** - O sistema deve permitir registrar operações de retirada e reposição de materiais, informando a data e o setor solicitante.
- **RF-11** - O sistema deve registrar o usuário responsável por executar a movimentação.
- **RF-12** - O sistema deve apresentar os insumos cadastrados em ordem alfabética, aplicando algoritmo de ordenação.
- **RF-13** - O sistema deve emitir alertas automáticos quando determinado insumo atingir a quantidade crítica.
- **RF-14** - O sistema deve disponibilizar opção para retorno à tela principal a partir das telas secundárias.
- **RF-15** - O acesso ao sistema é restrito a usuários autenticados por usuário e senha cadastrados.

---

## Requisitos Não Funcionais

- **RNF-01** - A aplicação deve ser desenvolvida no formato desktop.
- **RNF-02** - O sistema deve garantir a rastreabilidade e o histórico persistente de todas as movimentações realizadas.
- **RNF-03** - O banco de dados deve ser relacional, nomeado como `bebidas_db`, contendo tabelas com tipos de dados, chaves primárias e chaves estrangeiras.