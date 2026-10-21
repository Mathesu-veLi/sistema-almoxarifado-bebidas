# AGENTS.md — Diretrizes para Agentes de IA

Este repositório contém o **Projeto Integrador - Módulo 2 (Curso Técnico em Desenvolvimento de Sistemas - SENAI/Sistema FIEB)**: Sistema Desktop de Almoxarifado e Controle de Estoque para uma **Indústria de Bebidas**.

O objetivo deste arquivo é fornecer contexto de alta fidelidade e regras inegociáveis para que agentes de IA auxiliem no desenvolvimento, modelagem, testes e documentação do projeto sem necessidade de reintrodução de contexto.

---

## 1. Identidade e Regras do Git (Commit Agent)
* **Autor dos commits:** o projeto é colaborativo e a lista oficial fica em [`docs/08_colaboradores.md`](docs/08_colaboradores.md). Antes de cada commit, o agente **pergunta ao usuário qual colaborador fez as alterações** e usa a identidade escolhida.
  * Aplique a identidade **somente naquele commit**, via `-c`, sem alterar a configuração do repositório: `git -c user.name="Nome" -c user.email="email" commit -m "tipo(escopo): descrição"`.
  * **Nunca** rode `git config user.name`/`user.email` para trocar o autor, nunca use `--global` e nunca commite com identidade não confirmada.
  * Trabalho em par: o colaborador escolhido é o autor e os demais entram como `Co-authored-by: Nome <email>` no rodapé da mensagem.
* **Colaboradores:**
  | Handle | Nome | E-mail |
  | --- | --- | --- |
  | `Mathesu-veLi` | Matheus Levi | `matheuslevit@gmail.com` |
  | `LucasRAJ` | Lucas Rafael | `angelicarosasantos99@gmail.com` |
  | `israelsouzadev` | `israelsouzadev` | `israelssilva2007@gmail.com` |
  | `matheus391` | `matheus391` | `theusilva205@gmail.com` |
* **Padrão de Commit:** Conventional Commits obrigatório.
  * Formato: `<tipo>(<escopo opcional>): <descrição objetiva em português>`
  * Tipos: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`.
  * Commits devem ser **atômicos** (uma única responsabilidade lógica por commit).
  * Sempre verifique `git status` e configure os dados de autor antes de realizar qualquer commit.

---

## 2. Stack Tecnológica e Ferramentas
* **Linguagem:** Java 17 LTS (ou 21 LTS).
* **Interface Gráfica (Desktop):** JavaFX (FXML + Controllers Java + CSS).
* **IDE Principal:** Apache NetBeans (padrão com suporte a Maven ou Ant do SENAI).
* **Banco de Dados:** MySQL 8.0 ou MariaDB 10.4+.
  * **Nome do Banco (Mandatório):** `bebidas_db`.
* **Acesso a Dados:** JDBC Nativo com padrão DAO (Data Access Object) e `PreparedStatement`.
* **Gerenciador de Dependências:** Maven (`pom.xml`) com `javafx-controls`, `javafx-fxml` e `mysql-connector-j`.

---

## 3. Arquitetura da Aplicação
A aplicação segue estritamente o padrão **MVC (Model-View-Controller) + DAO + Service**:
* `src/main/java/com/senai/bebidas/model/`: Entidades de domínio (Insumo, LoteInsumo, Movimentacao, Funcionario, etc.) e DTOs.
* `src/main/java/com/senai/bebidas/dao/`: Camada de persistência JDBC com CRUD e transações.
* `src/main/java/com/senai/bebidas/service/`: Regras de negócio, cálculo de estoque, validações e **algoritmos de ordenação**.
* `src/main/java/com/senai/bebidas/controller/`: Controllers JavaFX gerenciando os eventos das telas.
* `src/main/java/com/senai/bebidas/util/`: Conexão com banco (`DatabaseConnection`), controle de sessão (`SessaoUsuario`) e alertas (`AlertaUtil`).
* `src/main/resources/fxml/`: Arquivos FXML das telas.
* `src/main/resources/css/`: Estilos da interface.

---

## 4. Guardrails e Requisitos Mandatórios (Edital SENAI)
1. **Nome do Banco:** Deve ser `bebidas_db`.
2. **Carga Inicial do Banco:** Toda tabela DEVE ter **no mínimo 3 registros** cadastrados via script SQL.
3. **Telas Obrigatórias:**
   * **Login (`login.fxml`):** Validação de usuário/senha com alerta para credenciais inválidas.
   * **Home (`home.fxml`):** Exibe nome do funcionário autenticado, botão de logout e atalhos para Insumos e Movimentações.
   * **Gerenciamento de Insumos (`insumos.fxml`):** Tabela, filtro/busca por texto, inclusão, edição, exclusão e validação antes do banco.
   * **Movimentação de Materiais (`movimentacao.fxml`):** Listagem alfabética ordenada via **algoritmo próprio** (Quicksort), registro de retirada/reposição com data e responsável, e **alerta visual para nível crítico**.
4. **Algoritmo de Ordenação Próprio:** Na tela de movimentação, a ordenação alfabética não deve ser feita apenas por `ORDER BY` do SQL ou `Collections.sort()` — deve existir a classe de serviço com algoritmo implementado (Quicksort) para justificar na banca oral.
5. **Rastreabilidade:** Nenhuma exclusão física de insumo ou movimentação que quebre a rastreabilidade histórica.

---

## 5. Índice de Documentação (`docs/`)
Sempre consulte os documentos específicos para detalhes profundos:
* [`docs/01_requisitos.md`](docs/01_requisitos.md): RFs, RNs e RNFs detalhados com critérios de aceite.
* [`docs/02_diagrama_de_classes.md`](docs/02_diagrama_de_classes.md): Diagrama de classes completo (Mermaid), atributos, métodos e relações.
* [`docs/03_modelagem_banco_e_revisao.md`](docs/03_modelagem_banco_e_revisao.md): Revisão do banco drawdb, análise de inconsistências e script SQL oficial.
* [`docs/04_arquitetura_e_fluxo_telas.md`](docs/04_arquitetura_e_fluxo_telas.md): Wireframes conceituais, fluxo JavaFX e sessão.
* [`docs/05_plano_de_testes.md`](docs/05_plano_de_testes.md): Casos de teste funcionais com entradas, ações e saídas esperadas.
* [`docs/06_infraestrutura.md`](docs/06_infraestrutura.md): Especificação de ambiente e SGBD para a entrega 9 do SENAI.
* [`docs/07_preparacao_banca.md`](docs/07_preparacao_banca.md): Roteiro de perguntas e respostas para a arguição oral.
* [`docs/08_colaboradores.md`](docs/08_colaboradores.md): Lista oficial de colaboradores e regra de autoria dos commits.

---

## 6. Habilidades do Agente (Skills)
As habilidades especializadas estão disponíveis de forma sincronizada em `.agents/skills/` (Antigravity), `.opencode/skills/` (OpenCode) e `skills/`:
* [`skills/git-commit/SKILL.md`](skills/git-commit/SKILL.md): Procedimento para commits atômicos com autoria escolhida entre os colaboradores.
* [`skills/javafx-desktop/SKILL.md`](skills/javafx-desktop/SKILL.md): Padrões de código JavaFX, FXML e NetBeans.
* [`skills/sorting-algorithms/SKILL.md`](skills/sorting-algorithms/SKILL.md): Algoritmo de ordenação e explicação teórica para a banca.
* [`skills/database-operations/SKILL.md`](skills/database-operations/SKILL.md): Padrão DAO, transações e views de estoque em MySQL.

---

## 7. Compatibilidade de Ambientes: Antigravity & OpenCode

O repositório foi adaptado para interoperabilidade nativa e transparente em ambos os agentes:

### No Antigravity (Google DeepMind)
* **Instruções Principais:** Carrega automaticamente as regras corporativas a partir deste arquivo `AGENTS.md`.
* **Workspace Customizations:** Descoberta automática de skills em `.agents/skills/` e regras adicionais em `.agents/rules/senai-rules.md`.
* **Subagentes:** Podem ser invocados herdando o contexto completo de arquitetura e guardrails do SENAI.

### No OpenCode
* **Configuração Central:** `opencode.json` na raiz define as instruções (`AGENTS.md`), comandos e agentes.
* **Skills:** Reconhecidas em `.opencode/skills/` (e por fallback em `.agents/skills/`).
* **Comandos Customizados (Slash Commands):**
  * `/commit`: Executa commits atômicos padronizados com o autor Matheus (`.opencode/commands/commit.md`).
  * `/banca`: Prepara simulações de perguntas e respostas para a arguição oral da banca (`.opencode/commands/banca.md`).
  * `/testes`: Valida cenários do sistema contra o plano de testes (`.opencode/commands/testes.md`).
* **Subagente Especializado:** `commit-agent` configurado em `.opencode/agents/commit-agent.md` e em `opencode.json`.

