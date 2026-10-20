# Registro e Planejamento de Testes de Software

**Projeto:** Sistema Desktop de Almoxarifado para Indústria de Bebidas  
**Curso:** Técnico em Desenvolvimento de Sistemas - SENAI / Sistema FIEB  
**Módulo:** 2 - Projeto Integrador  

Este documento atende integralmente ao **Item 8 do edital**:
* **8.1.** Descrever casos de teste de software para cada requisito funcional.
* **8.2.** Informar ferramentas e ambiente utilizados durante a execução dos testes.

---

## 1. Ferramentas e Ambiente de Testes (Item 8.2)

* **Ambiente Operacional:** Windows 10/11 Professional (x64).
* **Ambiente de Execução Java:** Java Development Kit (JDK) 17 LTS / OpenJDK 17.
* **Ferramenta de Automação de Testes Unitários:** JUnit 5 (Jupiter) e Mockito para simulação de DAOs.
* **Ferramenta de Testes de Interface (GUI):** TestFX / Testes Manuais Estruturados via NetBeans.
* **SGBD de Testes:** MySQL 8.0 Community Server rodando localmente (`localhost:3306`).
* **Massa de Dados de Teste:** Banco de dados `bebidas_db` populado com a massa inicial do script SQL.

---

## 2. Matriz de Casos de Teste por Requisito Funcional (Item 8.1)

| ID Teste | Requisito Relacionado | Cenário de Teste | Dados de Entrada | Ação Executada | Resultado Esperado | Status |
|---|---|---|---|---|---|---|
| **CT-01** | **RF-01** / **RF-02** | Login com credenciais válidas | `email: joao@bebidas.com`, `senha: senha123` | Clicar no botão "Entrar" | Autenticação autorizada; usuário direcionado para a Tela Inicial com dados de João Silva. | Aprovado |
| **CT-02** | **RF-02** | Login com senha incorreta | `email: joao@bebidas.com`, `senha: 99999` | Clicar no botão "Entrar" | Sistema exibe mensagem "Credenciais inválidas" e mantém na tela de login para nova tentativa. | Aprovado |
| **CT-03** | **RF-02** | Login com usuário inexistente | `email: inexiste@bebidas.com`, `senha: 123` | Clicar no botão "Entrar" | Mensagem de erro informando que o usuário não foi localizado. | Aprovado |
| **CT-04** | **RF-02** | Tentativa de login com campos vazios | `email: ""`, `senha: ""` | Clicar no botão "Entrar" | Validação de formulário avisa "Preencha todos os campos" sem consultar o banco. | Aprovado |
| **CT-05** | **RF-03** | Exibição de identificação na tela inicial | Login efetuado com Maria Souza | Acessar tela inicial | O cabeçalho da Home exibe: "Usuário: Maria Souza - Cargo: Operador de Estoque". | Aprovado |
| **CT-06** | **RF-04** | Encerramento de sessão (Logout) | Usuário autenticado na Home | Clicar no botão "Sair / Logout" | Sessão do usuário é invalidada (`SessaoUsuario = null`) e o sistema retorna para a tela de Login. | Aprovado |
| **CT-07** | **RF-05** / **RF-16** | Navegação e retorno entre telas | Usuário autenticado na Home | Clicar em "Insumos" e depois em "Voltar" | Abre tela de gerenciamento de insumos e o botão voltar retorna exatamente à tela inicial. | Aprovado |
| **CT-08** | **RF-06** | Exibição tabular de insumos cadastrados | Banco populado com 3+ insumos | Acessar tela de Insumos | Tabela lista todos os insumos cadastrados com colunas preenchidas corretamente. | Aprovado |
| **CT-09** | **RF-07** | Pesquisa dinâmica de insumos por termo | Termo digitado: `"Guaraná"` | Digitar no campo de busca | Tabela filtra e exibe apenas os registros que contenham "Guaraná" no nome. | Aprovado |
| **CT-10** | **RF-08** / **RF-11** | Inclusão de novo insumo com dados válidos | `Nome: "Adoçante Sucralose"`, `Qtd Mínima: 30` | Clicar em "Salvar" | Validação aprovada, insumo gravado no MySQL e tabela atualizada automaticamente. | Aprovado |
| **CT-11** | **RF-11** | Inclusão de insumo com campos vazios ou negativos | `Nome: ""`, `Qtd Mínima: -5` | Clicar em "Salvar" | Sistema impede gravação, destaca campos inválidos e emite alerta ao operador. | Aprovado |
| **CT-12** | **RF-09** | Atualização de dados de insumo existente | Insumo selecionado; altera `Qtd Mínima` de 50 para 70 | Clicar em "Atualizar" | Dados gravados com sucesso no banco e refletidos na listagem. | Aprovado |
| **CT-13** | **RF-10** | Remoção de insumo sem movimentações | Insumo cadastrado sem vínculo em lote/movimentação | Clicar em "Excluir" e confirmar | Registro removido do banco e excluído da visualização. | Aprovado |
| **CT-14** | **RF-10** | Tentativa de exclusão de insumo com movimentações | Insumo "Concentrado de Guaraná" | Clicar em "Excluir" | Sistema impede exclusão via tratamento de integridade referencial (FK) e emite mensagem clara. | Aprovado |
| **CT-15** | **RF-14** | Ordenação alfabética por algoritmo customizado (Quicksort) | Lista desordenada: ["Xarope", "Concentrado", "Açúcar"] | Abrir tela de Movimentação | Insumos aparecem ordenados: 1. Açúcar, 2. Concentrado, 3. Xarope via Quicksort. | Aprovado |
| **CT-16** | **RF-12** / **RF-13** | Registro de movimentação de reposição (Entrada) | `Lote: LOT-GUA-01`, `Tipo: Entrada`, `Qtd: 100`, `Setor: Linha 1` | Clicar em "Confirmar Movimentação" | Movimentação gravada vinculando o usuário logado e data/hora atual; saldo aumenta em 100. | Aprovado |
| **CT-17** | **RF-12** | Validação de retirada com saldo insuficiente | `Lote com saldo 50`, retirada solicitada: `80` | Clicar em "Confirmar Movimentação" | Sistema bloqueia transação com mensagem "Saldo insuficiente no lote selecionado". | Aprovado |
| **CT-18** | **RF-15** | Emissão de alerta de quantidade crítica | `Qtd mínima: 50`. Saldo atual: 60. Retirada de 20 -> Saldo final: 40 | Confirmar retirada | Operação gravada e disparado **Alerta Automático de Nível Crítico** com destaque visual na tela. | Aprovado |

---

## 3. Procedimento de Execução dos Testes Unitários
Para executar os testes automatizados da lógica de ordenação e regras de negócio via Maven:
```bash
mvn test
```
Para rodar especificamente a validação do algoritmo de ordenação Quicksort:
```bash
mvn test -Dtest=OrdenacaoServiceTest
```
