# Roteiro de Execucao do Projeto Integrador (TODO da Equipe)

**Projeto:** Sistema de Almoxarifado para Industria de Bebidas  
**Curso:** Tecnico em Desenvolvimento de Sistemas - SENAI / FIEB (Modulo 2)  
**Finalidade:** Guia operacional direto e pratico para desenvolvimento manual da equipe sem uso de IA.

---

## 1. Quadro Resumo: O que fazer de relance

Use esta tabela para saber o status geral e o caminho de cada etapa apenas batendo o olho:

| Fase | Acao Principal | Ferramenta / Onde Fazer | Arquivo de Apoio | Feito? |
|---|---|---|---|:---:|
| **Fase 0** | Validar instalacoes do computador | CMD / NetBeans / MySQL / Scene Builder | - | [ ] |
| **Fase 1** | Rodar o script do banco de dados | MySQL Workbench ou XAMPP | `Entregaveis/Banco de Dados/BancodoProjeto.sql` | [ ] |
| **Fase 2** | Criar o projeto JavaFX no NetBeans | Apache NetBeans (Maven) | `pom.xml` | [ ] |
| **Fase 3** | Programar Conexao, Modelos e DAOs | NetBeans (Pastas `util`, `model`, `dao`) | `docs/02_diagrama_de_classes.md` | [ ] |
| **Fase 4** | Criar o algoritmo Quicksort | NetBeans (Pasta `service`) | `skills/sorting-algorithms/SKILL.md` | [ ] |
| **Fase 5** | Desenhar as 4 telas no Scene Builder | Gluon Scene Builder | `docs/04_arquitetura_e_fluxo_telas.md` | [ ] |
| **Fase 6** | Programar acoes dos botoes e regras | NetBeans (Pasta `controller`) | `docs/04_arquitetura_e_fluxo_telas.md` | [ ] |
| **Fase 7** | Realizar testes manuais do sistema | Sistema rodando no NetBeans | `docs/05_plano_de_testes.md` | [ ] |
| **Fase 8** | Montar a pasta compactada (.zip) | Windows Explorer | Instrucoes do Edital (Item 10) | [ ] |
| **Fase 9** | Treinar respostas da apresentacao oral | Estudo individual e em grupo | `docs/07_preparacao_banca.md` | [ ] |

---

## 2. Checklist das 9 Entregas Oficiais do SENAI

Marque conforme os arquivos da entrega forem separados:

- [ ] **Entrega 1:** Documento de Requisitos (`docs/01_requisitos.md`)
- [ ] **Entrega 2:** Imagem do DER (`Entregaveis/Banco de Dados/bebidas_db_2026-10-13T14_02_26.535Z.jpeg`)
- [ ] **Entrega 3:** Script SQL (`Entregaveis/Banco de Dados/BancodoProjeto.sql`)
- [ ] **Entrega 4:** Tela de Login funcional (validacao de usuario, senha e erro amigavel)
- [ ] **Entrega 5:** Tela Inicial funcional (nome do usuario logado, cargo e navegacao)
- [ ] **Entrega 6:** Tela de Gerenciamento de Insumos (tabela, pesquisa, insercao, edicao, exclusao)
- [ ] **Entrega 7:** Tela de Movimentacao (ordenacao por Quicksort e alerta de estoque critico)
- [ ] **Entrega 8:** Documento de Planejamento e Registro de Testes (`docs/05_plano_de_testes.md`)
- [ ] **Entrega 9:** Documento de Infraestrutura Tecnica (`docs/06_infraestrutura.md`)

---

## 3. Passo a Passo Detalhado

---

### FASE 0: Validar Ferramentas no Computador

- [ ] **0.1. Validar versao do Java**
  * **Onde:** Prompt de Comando (CMD).
  * **Comando:** `java -version`
  * **Resultado esperado:** Versao 17 ou 21. Se nao tiver, instalar JDK 17 LTS.

- [ ] **0.2. Iniciar o Servidor MySQL**
  * **Onde:** Painel do XAMPP ou servico do MySQL Workbench.
  * **Resultado esperado:** Servico rodando na porta 3306.

- [ ] **0.3. Integrar NetBeans com Scene Builder**
  * **Onde:** NetBeans -> `Tools` -> `Options` -> `Java` -> `JavaFX`.
  * **Acao:** Apontar o caminho do executavel do Gluon Scene Builder.
  * **Resultado esperado:** Ao dar duplo clique em um arquivo `.fxml`, o Scene Builder deve abrir automaticamente.

---

### FASE 1: Banco de Dados (bebidas_db)

- [ ] **1.1. Executar o Script SQL Oficial**
  * **Onde:** MySQL Workbench ou phpMyAdmin.
  * **Acao:** Abrir e executar o arquivo `Entregaveis/Banco de Dados/BancodoProjeto.sql`.
  * **Resultado esperado:** Banco `bebidas_db` criado com as 8 tabelas e views de estoque.

- [ ] **1.2. Auditar a Regra do Edital (Minimo de 3 registros por tabela)**
  * **Onde:** MySQL Workbench.
  * **Comando de teste:**
    ```sql
    USE bebidas_db;
    SELECT COUNT(*) FROM cargos;
    SELECT COUNT(*) FROM tipos_movimentacao;
    SELECT COUNT(*) FROM tipos_insumos;
    SELECT COUNT(*) FROM fornecedores;
    SELECT COUNT(*) FROM setores;
    SELECT COUNT(*) FROM lotes_insumos;
    SELECT COUNT(*) FROM funcionarios;
    SELECT COUNT(*) FROM movimentacoes;
    ```
  * **Resultado esperado:** Todas as tabelas devem retornar contagem igual ou superior a 3.

---

### FASE 2: Criacao do Projeto no NetBeans

- [ ] **2.1. Criar o Projeto Maven**
  * **Onde:** NetBeans -> `File` -> `New Project...` -> `Java with Maven` -> `FXML JavaFX Application`.
  * **Nome do Projeto:** `ControleEstoqueBebidas`
  * **Group Id:** `com.senai.bebidas`
  * **Package:** `com.senai.bebidas`

- [ ] **2.2. Configurar o driver do MySQL no `pom.xml`**
  * **Onde:** Arquivo `pom.xml` na raiz do projeto.
  * **Acao:** Adicionar a dependencia:
    ```xml
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.3.0</version>
    </dependency>
    ```
  * **Resultado esperado:** Clicar em `Clean and Build` e a compilacao finalizar com `BUILD SUCCESS`.

- [ ] **2.3. Criar a Estrutura de Pacotes**
  * **Onde:** `src/main/java/com/senai/bebidas/`
  * **Criar:**
    * `com.senai.bebidas.model`
    * `com.senai.bebidas.dao`
    * `com.senai.bebidas.service`
    * `com.senai.bebidas.controller`
    * `com.senai.bebidas.util`

- [ ] **2.4. Criar as Pastas de Recursos**
  * **Onde:** `src/main/resources/`
  * **Criar:**
    * Pasta `fxml` (telas visuais)
    * Pasta `css` (folha de estilo `estilo.css`)
    * Pasta `images` (icones e logomarca)

---

### FASE 3: Backend (Conexao, Modelos e DAOs)

- [ ] **3.1. Criar `DatabaseConnection.java`**
  * **Onde:** Pacote `com.senai.bebidas.util`.
  * **Acao:** Criar metodo estatico `getConnection()` com URL:
    `jdbc:mysql://localhost:3306/bebidas_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`
  * **Resultado esperado:** Criar um metodo `main` simples e validar mensagem de conexao bem-sucedida.

- [ ] **3.2. Criar `SessaoUsuario.java`**
  * **Onde:** Pacote `com.senai.bebidas.util`.
  * **Acao:** Criar classe com atributo estatico `private static Funcionario usuarioLogado;` e metodos `setUsuarioLogado()`, `getUsuarioLogado()` e `limparSessao()`.
  * **Finalidade:** Manter o nome e cargo do usuario ativos durante a navegacao.

- [ ] **3.3. Criar Classes de Entidade (Model)**
  * **Onde:** Pacote `com.senai.bebidas.model`.
  * **Referencia de atributos:** Consultar `docs/02_diagrama_de_classes.md`.
  * **Classes obrigatorias:**
    * `Funcionario.java` (id, nome, email, senha, cpf, cargoNome)
    * `Insumo.java` (id, nome, descricao, volumeEmbalagem, unidadeMedida, quantidadeMinima, saldoAtual)
    * `LoteInsumo.java` (id, numeroLote, tipoInsumoId, fornecedorId, setorId, dataValidade, saldoAtual)
    * `Movimentacao.java` (id, loteInsumoId, funcionarioId, tipoMovimentacaoId, setorId, quantidade, dataMovimentacao)

- [ ] **3.4. Criar Classes de Acesso a Dados (DAO)**
  * **Onde:** Pacote `com.senai.bebidas.dao`.
  * **Classes e metodos minimos:**
    * `FuncionarioDAO.java` -> Metodo `autenticar(String email, String senha)`
    * `InsumoDAO.java` -> Metodos `listarTodos()`, `inserir(Insumo i)`, `atualizar(Insumo i)`, `excluir(int id)`
    * `MovimentacaoDAO.java` -> Metodo `registrar(Movimentacao m)` e `consultarSaldoLote(int loteId)`

---

### FASE 4: Algoritmo Quicksort de Ordenacao

- [ ] **4.1. Criar a Classe `OrdenacaoService.java`**
  * **Onde:** Pacote `com.senai.bebidas.service`.
  * **Referencia de codigo:** Consultar `skills/sorting-algorithms/SKILL.md`.
  * **Acao:** Implementar o Quicksort recursivo que recebe `List<Insumo>` e ordena em ordem alfabetica comparando `insumo.getNome().compareToIgnoreCase()`.
  * **Resultado esperado:** A ordenacao dos insumos na tela de movimentacao DEVE ser feita por este metodo Java, e nao pelo `ORDER BY` do SQL (requisito do edital).

---

### FASE 5: Desenho das 4 Telas no Scene Builder

Desenhar as telas em `src/main/resources/fxml/` conforme especificado em `docs/04_arquitetura_e_fluxo_telas.md`:

- [ ] **5.1. Tela de Login (`login.fxml`)**
  * **Campos:** `TextField txtEmail`, `PasswordField txtSenha`, `Button btnEntrar`, `Label lblMensagemErro`.
  * **Controller associado:** `com.senai.bebidas.controller.LoginController`.

- [ ] **5.2. Tela Inicial / Menu (`home.fxml`)**
  * **Campos:** `Label lblBoasVindas`, `Label lblCargo`, `Button btnInsumos`, `Button btnMovimentacao`, `Button btnLogout`.
  * **Controller associado:** `com.senai.bebidas.controller.HomeController`.

- [ ] **5.3. Tela de Gerenciamento de Insumos (`insumos.fxml`)**
  * **Campos de listagem:** `TextField txtPesquisa`, `TableView tblInsumos` (Colunas: ID, Nome, Unidade, Qtd Minima, Saldo).
  * **Campos de formulario:** `txtNome`, `txtDescricao`, `txtVolume`, `txtUnidade`, `txtQtdMinima`.
  * **Botoes:** `btnSalvar`, `btnEditar`, `btnExcluir`, `btnLimpar`, `btnVoltar`.
  * **Controller associado:** `com.senai.bebidas.controller.InsumosController`.

- [ ] **5.4. Tela de Movimentacao de Materiais (`movimentacao.fxml`)**
  * **Campos:** `ComboBox cmbInsumos` (ou tabela com Quicksort), `ComboBox cmbLotes`, `ComboBox cmbTipoMovimentacao`, `TextField txtQuantidade`, `DatePicker dpData`, `ComboBox cmbSetor`, `Label lblResponsavel`.
  * **Botoes e Alertas:** `btnConfirmar`, `btnVoltar`, Painel ou Label de destaque para Alerta de Estoque Critico.
  * **Controller associado:** `com.senai.bebidas.controller.MovimentacaoController`.

---

### FASE 6: Programacao dos Controladores (Controllers)

- [ ] **6.1. Programar `LoginController.java`**
  * Validar campos em branco ao clicar em "Entrar".
  * Consultar `FuncionarioDAO.autenticar(email, senha)`.
  * Se login invalido: exibir mensagem clara de erro sem fechar a aplicacao.
  * Se login valido: salvar usuario em `SessaoUsuario` e abrir `home.fxml`.

- [ ] **6.2. Programar `HomeController.java`**
  * No metodo `initialize()`: ler `SessaoUsuario.getUsuarioLogado()` e exibir nome e cargo nos labels.
  * Acao do botao "Insumos": carregar `insumos.fxml`.
  * Acao do botao "Movimentacao": carregar `movimentacao.fxml`.
  * Acao do botao "Sair": limpar sessao e retornar para `login.fxml`.

- [ ] **6.3. Programar `InsumosController.java`**
  * No metodo `initialize()`: preencher a `TableView` com os dados de `InsumoDAO.listarTodos()`.
  * Barra de busca: filtrar a lista da tabela em tempo real conforme digitado em `txtPesquisa`.
  * Acao de Salvar: validar se campos obrigatorios estao preenchidos antes de chamar o banco.
  * Acao de Excluir: confirmar acao com dialogo de confirmacao; se houver vinculo historico, impedir exclusao e avisar o usuario.
  * Acao de Voltar: retornar para `home.fxml`.

- [ ] **6.4. Programar `MovimentacaoController.java`**
  * No metodo `initialize()`:
    1. Buscar insumos via DAO.
    2. Chamar obrigatoriamente `OrdenacaoService.ordenarInsumosPorNome(lista)` para garantir a ordem alfabetica via Quicksort.
    3. Exibir o nome do funcionario logado no campo de responsavel (bloqueado para edicao).
  * Acao de Confirmar Movimentacao:
    1. Validar quantidade maior que zero.
    2. Se for Saida: conferir se ha saldo suficiente no lote. Se nao houver, bloquear e avisar.
    3. Persistir a movimentacao no banco via `MovimentacaoDAO`.
    4. **Regra de Nivel Critico:** verificar se o saldo restante ficou menor ou igual a `quantidadeMinima`. Se sim, disparar alerta visual na tela informando nivel critico atingido.
  * Acao de Voltar: retornar para `home.fxml`.

---

### FASE 7: Bateria de Testes Praticos

Execute os 9 testes abaixo com a aplicacao aberta. Detalhes completos estao em `docs/05_plano_de_testes.md`.

- [ ] **Teste 1:** Digitar login errado -> Deve exibir alerta de erro e manter a tela aberta.
- [ ] **Teste 2:** Digitar login correto -> Deve abrir a Home com nome e cargo corretos.
- [ ] **Teste 3:** Clicar em "Insumos" -> Tabela deve listar os registros carregados do banco.
- [ ] **Teste 4:** Digitar termo no campo de busca -> A lista deve filtrar apenas os insumos correspondentes.
- [ ] **Teste 5:** Tentar cadastrar insumo com campo vazio -> Sistema deve bloquear e avisar.
- [ ] **Teste 6:** Abrir tela de Movimentacao -> A lista de insumos deve estar em ordem alfabetica (Quicksort).
- [ ] **Teste 7:** Tentar retirar quantidade maior que o estoque disponivel -> Sistema deve bloquear.
- [ ] **Teste 8:** Realizar retirada que deixe o estoque abaixo da quantidade minima -> Sistema deve exibir alerta visual de estoque critico.
- [ ] **Teste 9:** Clicar em "Sair" na Home -> Sessao deve ser encerrada e sistema deve retornar ao Login.

---

### FASE 8: Organizacao da Pasta Final de Entrega

Conforme especificacao oficial do edital (Item 10):

- [ ] **8.1. Criar pasta com o nome do aluno/equipe** (Exemplo: `ProjetoIntegrador_Modulo2_NomeAluno`).
- [ ] **8.2. Inserir os 6 itens obrigatorios dentro da pasta:**
  1. Documento de Requisitos (PDF de `docs/01_requisitos.md`)
  2. Imagem do DER (`Entregaveis/Banco de Dados/bebidas_db_2026-10-13T14_02_26.535Z.jpeg`)
  3. Script SQL (`Entregaveis/Banco de Dados/BancodoProjeto.sql`)
  4. Codigo-fonte completo do projeto (pasta do projeto NetBeans com `pom.xml`, `src/`)
  5. Documento de Testes (PDF de `docs/05_plano_de_testes.md`)
  6. Documento de Infraestrutura (PDF de `docs/06_infraestrutura.md`)
- [ ] **8.3. Compactar a pasta final no formato `.zip`**.

---

### FASE 9: Preparacao para a Arguicao Oral da Banca

Perguntas fundamentais do edital que a equipe deve dominar. Detalhes completos em `docs/07_preparacao_banca.md`:

- [ ] **Pergunta 1: Por que o banco se chama `bebidas_db`?**  
  *Resposta:* Exigencia formal e obrigatoria do edital do SENAI.

- [ ] **Pergunta 2: Qual algoritmo de ordenacao foi usado e por que?**  
  *Resposta:* Algoritmo Quicksort, implementado na classe `OrdenacaoService`. Escolhido por operar por divisao e conquista com complexidade media O(n log n), ideal para ordenacao em memoria.

- [ ] **Pergunta 3: Como foi garantida a rastreabilidade das operacoes?**  
  *Resposta:* Toda movimentacao grava o operador logado na sessao, data/hora exata e setor solicitante. Nao ha delecao fisica no historico contabil.

- [ ] **Pergunta 4: Como o sistema identifica o nivel critico?**  
  *Resposta:* Compara o saldo remanescente com o atributo `quantidadeMinima` cadastrado no tipo de insumo. Caso o saldo seja menor ou igual, uma notificacao visual e emitida de forma automatica.
