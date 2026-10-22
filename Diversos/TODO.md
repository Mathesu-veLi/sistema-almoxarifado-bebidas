# Roteiro de Execução do Projeto Integrador (TODO da Equipe)

**Projeto:** Sistema de Almoxarifado para Indústria de Bebidas  
**Curso:** Técnico em Desenvolvimento de Sistemas - SENAI / FIEB (Módulo 2)  
**Arquitetura:** Frontend desktop Swing/JFrame + Backend Spring Boot (REST) + MySQL  
**Finalidade:** Guia operacional direto e prático para a equipe desenvolver o projeto, com apoio pontual do agente assistente.

---

## 1. Quadro Resumo: O que fazer de relance

| Fase | Ação Principal | Ferramenta / Onde Fazer | Arquivo de Apoio | Feito? |
|---|---|---|---|---|
| **Fase 1** | Executar o script do banco de dados | MySQL Workbench ou XAMPP | `Entregaveis/Banco de Dados/BancodoProjeto.sql` | [ ] |
| **Fase 2** | Modelar classes, arquitetura, contrato REST e regras | Draw.io / Lucidchart / papel + editor de texto | `Entregaveis/Modelagem_/requisitos_industria_bebidasv2.md` | [ ] |
| **Fase 3** | Criar o projeto backend Spring Boot | Spring Initializr / IntelliJ IDEA (Maven) | `backend/pom.xml` | [ ] |
| **Fase 4** | Configurar conexão, entidades JPA e repositórios | IntelliJ IDEA (`config`, `model`, `repository`) | `Entregaveis/Banco de Dados/BancodoProjeto.sql` | [ ] |
| **Fase 5** | Implementar regras de negócio e o Quicksort | IntelliJ IDEA (`service`) | `skills/sorting-algorithms/SKILL.md` | [ ] |
| **Fase 6** | Criar os endpoints REST e testar com Postman | IntelliJ IDEA (`controller`, `dto`, `exception`) | Contrato REST definido na Fase 2 | [ ] |
| **Fase 7** | Criar o projeto frontend Swing | Eclipse (Maven) | `frontend/pom.xml` | [ ] |
| **Fase 8** | Prototipar as telas e desenhar no WindowBuilder | Figma / Canva / Google Stitch + WindowBuilder (Eclipse) | `skills/swing-desktop/SKILL.md` | [ ] |
| **Fase 9** | Integrar o frontend com a API (HTTP/JSON) | Eclipse (`service`, `controller`) | `skills/swing-desktop/SKILL.md` | [ ] |
| **Fase 10** | Realizar testes unitários, de API e manuais | Backend + frontend rodando | Roteiro de testes da equipe | [ ] |
| **Fase 11** | Montar a pasta compactada (.zip) | Windows Explorer | Instruções do Edital (Item 10) | [ ] |
| **Fase 12** | Treinar respostas da apresentação oral | Estudo individual e em grupo | Requisitos em `Entregaveis/Modelagem_` | [ ] |

---

## 2. Checklist das 9 Entregas Oficiais do SENAI

Marque conforme os arquivos da entrega forem separados:

- [ ] **Entrega 1:** Documento de Requisitos (PDF produzido pela equipe, com base em `Entregaveis/Modelagem_/requisitos_industria_bebidasv2.md`)
- [ ] **Entrega 2:** Imagem do DER (`Entregaveis/Banco de Dados/bebidas_db_2026-10-13T14_02_26.535Z.jpeg`)
- [ ] **Entrega 3:** Script SQL (`Entregaveis/Banco de Dados/BancodoProjeto.sql`)
- [ ] **Entrega 4:** Tela de Login funcional (validação de usuário, senha e erro amigável)
- [ ] **Entrega 5:** Tela Inicial funcional (nome do usuário logado, cargo e navegação)
- [ ] **Entrega 6:** Tela de Gerenciamento de Insumos (tabela, pesquisa, inserção, edição, exclusão)
- [ ] **Entrega 7:** Tela de Movimentação (ordenação por Quicksort e alerta de estoque crítico)
- [ ] **Entrega 8:** Documento de Planejamento e Registro de Testes (produzido pela equipe)
- [ ] **Entrega 9:** Documento de Infraestrutura Técnica (produzido pela equipe)

---

## 3. Passo a Passo Detalhado

---

### FASE 1: Banco de Dados (bebidas_db)

- [ ] **1.1. Executar o script SQL oficial**
  * **Onde:** MySQL Workbench ou phpMyAdmin.
  * **Ação:** Abrir e executar o arquivo `Entregaveis/Banco de Dados/BancodoProjeto.sql`.
  * **Resultado esperado:** Banco `bebidas_db` criado com as 8 tabelas e as views de estoque.

- [ ] **1.2. Auditar a regra do edital (mínimo de 3 registros por tabela)**
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

### FASE 2: Modelagem e Documentação Técnica

Antes de escrever código, fechem o desenho do sistema. Essa fase evita retrabalho e é o que a banca costuma cobrar na arguição.

- [ ] **2.1. Revisar e fechar os requisitos**
  * **Onde:** `Entregaveis/Modelagem_/requisitos_industria_bebidasv2.md` e o PDF do edital.
  * **Ação:** conferir RFs, RNs e RNFs, resolver dúvidas com a equipe e produzir o documento de requisitos da entrega.
  * **Resultado esperado:** requisitos sem lacunas e acordados entre todos.

- [ ] **2.2. Validar a modelagem do banco**
  * **Onde:** DER em `Entregaveis/Banco de Dados/` e script `BancodoProjeto.sql`.
  * **Ação:** revisar tabelas, chaves primárias, chaves estrangeiras e cardinalidades (1:1, 1:N, N:N) contra as regras de negócio.
  * **Resultado esperado:** cada regra de negócio tem tabela/coluna que a representa.

- [ ] **2.3. Desenhar o diagrama de classes**
  * **Onde:** draw.io, Lucidchart, StarUML ou papel.
  * **Ação:** representar entidades, atributos, métodos principais e relacionamentos; incluir as camadas do backend e os DTOs.
  * **Resultado esperado:** diagrama salvo no material da equipe e usado como guia na implementação.

- [ ] **2.4. Definir a arquitetura e o contrato REST**
  * **Ação:** acordar o fluxo `view → controller → service → repository` no backend e `view → controller → service HTTP` no frontend.
  * **Ação:** listar endpoints, DTOs de entrada/saída e códigos de resposta esperados (200, 400, 404, 409).
  * **Resultado esperado:** contrato documentado antes de criar os controllers.

- [ ] **2.5. Definir os cenários de teste**
  * **Ação:** escrever os casos de teste principais (login inválido, cadastro incompleto, retirada acima do saldo, alerta de nível crítico, logout).
  * **Resultado esperado:** roteiro de testes pronto para ser executado na Fase 10.

- [ ] **2.6. Revisar a modelagem com a equipe**
  * **Ação:** apresentar diagrama, DER e contrato para o grupo e ajustar o que não fechar.
  * **Resultado esperado:** todos entendem o desenho e conseguem explicá-lo na banca.

---

### FASE 3: Criação do Backend Spring Boot

- [ ] **3.1. Gerar o projeto**
  * **Onde:** [start.spring.io](https://start.spring.io) ou IntelliJ IDEA (`File > New > Project > Spring Initializr`; na versão Community, importar o Maven gerado).
  * **Nome/Artifact:** `backend`
  * **Group:** `com.senai.bebidas`
  * **Package:** `com.senai.bebidas`
  * **Java:** 17
  * **Dependências:** `Spring Web`, `Spring Data JPA`, `Validation`, `MySQL Driver`.

- [ ] **3.2. Conferir o `pom.xml`**
  * Deve conter `spring-boot-starter-parent` 3.x, `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `spring-boot-starter-validation`, `mysql-connector-j` e `spring-boot-starter-test`.
  * **Resultado esperado:** `mvn clean package -DskipTests` termina com `BUILD SUCCESS`.

- [ ] **3.3. Configurar `application.properties`**
  * **Onde:** `backend/src/main/resources/application.properties`.
  * **Conteúdo mínimo:**
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/bebidas_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo
    spring.datasource.username=root
    spring.datasource.password=
    spring.jpa.hibernate.ddl-auto=validate
    spring.jpa.open-in-view=false
    server.port=8080
    ```
  * **Resultado esperado:** Aplicação sobe sem erro de conexão (`mvn spring-boot:run`).

- [ ] **3.4. Criar a estrutura de pacotes**
  * **Onde:** `backend/src/main/java/com/senai/bebidas/`
  * **Criar:**
    * `model`
    * `repository`
    * `service`
    * `controller`
    * `dto`
    * `exception`
    * `config`

---

### FASE 4: Entidades JPA e Repositórios

- [ ] **4.1. Criar as entidades em `model`**
  * **Referência:** script SQL oficial em `Entregaveis/Banco de Dados/BancodoProjeto.sql`.
  * **Classes obrigatórias:**
    * `Cargo.java` (`id`, `nome`)
    * `Funcionario.java` (`id`, `nome`, `cpf`, `email`, `senha`, `cargo`)
    * `Fornecedor.java` (`id`, `nome`, `cnpj`)
    * `Setor.java` (`id`, `nome`)
    * `TipoInsumo.java` (`id`, `nome`, `quantidadeMinima`)
    * `LoteInsumo.java` (`id`, `tipoInsumo`, `fornecedor`, `lote`, `validade`, `volume`, `unidadeMedida`, `setor`)
    * `TipoMovimentacao.java` (`id`, `nome`, `operacao`)
    * `Movimentacao.java` (`id`, `loteInsumo`, `tipoMovimentacao`, `quantidade`, `setor`, `funcionario`, `dataMovimentacao`)
  * **Atenção:** usar `@Table(name = "...")` com o nome exato da tabela e `@JoinColumn` com o nome exato da FK (ex.: `tipos_insumos_id`, `lotes_insumo_id`).

- [ ] **4.2. Criar os repositórios em `repository`**
  * Interfaces que estendem `JpaRepository<Entidade, Integer>`:
    * `FuncionarioRepository` → `findByEmailAndSenha(...)`
    * `TipoInsumoRepository` → `findByNomeContainingIgnoreCase(...)`, `existsByNomeIgnoreCase(...)`
    * `LoteInsumoRepository` → `findByTipoInsumoId(...)`
    * `MovimentacaoRepository` → `findAllByOrderByDataMovimentacaoDesc()`
    * `SetorRepository` e `TipoMovimentacaoRepository`

- [ ] **4.3. Criar os DTOs em `dto`**
  * `LoginRequest`, `FuncionarioDTO`
  * `InsumoRequest`, `InsumoDTO`, `EstoqueDTO`
  * `LoteInsumoDTO`, `SetorDTO`, `TipoMovimentacaoDTO`
  * `MovimentacaoRequest`, `MovimentacaoResponse`, `MovimentacaoDTO`
  * `ErroResponse`

---

### FASE 5: Regras de Negócio e Quicksort

- [ ] **5.1. Criar `AuthService`**
  * Consulta `FuncionarioRepository.findByEmailAndSenha(...)`.
  * Se não encontrar, lançar `RegraNegocioException("E-mail ou senha inválidos.")`.
  * Retornar `FuncionarioDTO` (sem senha).

- [ ] **5.2. Criar `InsumoService`**
  * Validar nome (mínimo 3 caracteres) e `quantidadeMinima >= 0`.
  * Impedir nome duplicado.
  * Implementar `listar(termo)`, `cadastrar`, `atualizar` e `excluir`.
  * Na exclusão, verificar se existe lote/movimentação vinculado e lançar `RegraNegocioException`.

- [ ] **5.3. Criar `OrdenacaoService`**
  * **Onde:** Pacote `com.senai.bebidas.service`.
  * **Referência de código:** `skills/sorting-algorithms/SKILL.md`.
  * **Ação:** Implementar o Quicksort recursivo que recebe `List<TipoInsumo>` e ordena com `insumo.getNome().compareToIgnoreCase(...)`.
  * **Resultado esperado:** A ordenação da tela de movimentação DEVE ser feita por este método, e não por `ORDER BY`, `Collections.sort()` ou `List.sort()`.

- [ ] **5.4. Criar `MovimentacaoService`**
  * `listarInsumosOrdenados()`: buscar com `findAll()` (sem `ORDER BY`) e aplicar `OrdenacaoService`.
  * `registrar(...)`: método `@Transactional` que valida o tipo, verifica saldo em saídas, grava a movimentação e calcula `alertaCritico`.

- [ ] **5.5. Criar as exceções e o `GlobalExceptionHandler`**
  * `RegraNegocioException` → HTTP 400.
  * `EstoqueInsuficienteException` → HTTP 409.
  * `RecursoNaoEncontradoException` → HTTP 404.
  * Resposta JSON padrão: `{ "mensagem": "...", "status": 400, "timestamp": "..." }`.

---

### FASE 6: Endpoints REST

- [ ] **6.1. `AuthController`**
  * `POST /api/auth/login` recebendo `LoginRequest` com `@Valid`.

- [ ] **6.2. `InsumoController`**
  * `GET /api/insumos?termo=`
  * `GET /api/insumos/ordenados`
  * `POST /api/insumos`
  * `PUT /api/insumos/{id}`
  * `DELETE /api/insumos/{id}`

- [ ] **6.3. `MovimentacaoController`**
  * `POST /api/movimentacoes`
  * `GET /api/movimentacoes`

- [ ] **6.4. `CatalogoController`**
  * `GET /api/setores`
  * `GET /api/tipos-movimentacao`
  * `GET /api/lotes?tipoInsumoId=`

- [ ] **6.5. Testar a API com Postman/curl**
  * Login: `POST http://localhost:8080/api/auth/login`
  * Listagem: `GET http://localhost:8080/api/insumos`
  * Ordenação: `GET http://localhost:8080/api/insumos/ordenados`
  * **Resultado esperado:** JSON válido e status HTTP coerente.

---

### FASE 7: Criação do Frontend Swing

- [ ] **7.1. Criar o projeto Maven**
  * **Onde:** Eclipse → `File > New > Maven Project`.
  * **Nome do Projeto:** `frontend`
  * **Group Id:** `com.senai.bebidas`
  * **Package:** `com.senai.bebidas`
  * **Classe principal:** `com.senai.bebidas.app.Main`

- [ ] **7.2. Adicionar as dependências do `pom.xml`**
  * `jackson-databind` (serialização JSON).
  * `exec-maven-plugin` (executar a classe `Main` via Maven).

- [ ] **7.3. Criar a estrutura de pacotes**
  * `com.senai.bebidas.app`
  * `com.senai.bebidas.view`
  * `com.senai.bebidas.controller`
  * `com.senai.bebidas.service`
  * `com.senai.bebidas.model`
  * `com.senai.bebidas.util`

- [ ] **7.4. Criar as pastas de recursos**
  * `frontend/src/main/resources/images` (ícones e logomarca).

---

### FASE 8: Prototipação e Desenho das 4 Telas com WindowBuilder

Antes de abrir o WindowBuilder, desenhem as telas no **Figma**, **Canva** ou **Google Stitch** para validar layout e fluxo com a equipe. Só depois levem o protótipo aprovado para o WindowBuilder.

- [ ] **8.1. Prototipar as 4 telas**
  * **Onde:** Figma, Canva ou Google Stitch.
  * **Ação:** montar o wireframe de Login, Home, Insumos e Movimentação, incluindo a navegação entre elas.
  * **Resultado esperado:** protótipo validado pela equipe e imagens salvas no material do projeto.

- [ ] **8.2. Configurar o WindowBuilder**
  * **Onde:** Eclipse com o plugin **WindowBuilder** instalado.
  * **Ação:** importar o projeto Maven `frontend` e conferir se o editor visual de `JFrame` abre.
  * **Resultado esperado:** projeto importado e editor do WindowBuilder funcionando.

- [ ] **8.3. Login (`LoginFrame`)**
  * **Campos:** `txtEmail`, `txtSenha`, `btnEntrar`, `lblMensagemErro`.
  * **Controller associado:** `com.senai.bebidas.controller.LoginController`.

- [ ] **8.4. Home (`HomeFrame`)**
  * **Campos:** `lblBoasVindas`, `lblCargo`, `btnInsumos`, `btnMovimentacao`, `btnSair`.
  * **Controller associado:** `com.senai.bebidas.controller.HomeController`.

- [ ] **8.5. Gerenciamento de Insumos (`InsumosFrame`)**
  * **Listagem:** `txtPesquisa`, `JTable tblInsumos` (ID, Nome, Unidade, Qtd. Mínima, Saldo, Situação).
  * **Formulário:** `txtNome`, `txtDescricao`, `txtVolume`, `txtUnidade`, `txtQtdMinima`.
  * **Botões:** `btnSalvar`, `btnEditar`, `btnExcluir`, `btnLimpar`, `btnVoltar`.
  * **Controller associado:** `com.senai.bebidas.controller.InsumosController`.

- [ ] **8.6. Movimentação de Materiais (`MovimentacaoFrame`)**
  * **Campos:** `cbInsumos` (ordem vinda da API), `cbLotes`, `cbTipoMovimentacao`, `cbSetor`, `txtQuantidade` (`JSpinner`), `lblResponsavel`, `lblData`.
  * **Botões e alertas:** `btnConfirmar`, `btnVoltar` e `JOptionPane`/label para alerta de estoque crítico.
  * **Controller associado:** `com.senai.bebidas.controller.MovimentacaoController`.

---

### FASE 9: Integração frontend ↔ API

- [ ] **9.1. Criar `ApiConfig`**
  * **Onde:** `com.senai.bebidas.util`.
  * **Conteúdo:** `BASE_URL = "http://localhost:8080/api"` e timeout.

- [ ] **9.2. Criar `SessaoUsuario`**
  * **Onde:** `com.senai.bebidas.util`.
  * **Ação:** atributo estático `FuncionarioDTO usuarioLogado` e métodos `iniciarSessao`, `getUsuarioLogado`, `encerrarSessao`, `isAutenticado`.

- [ ] **9.3. Criar `AlertaUtil`**
  * **Onde:** `com.senai.bebidas.util`.
  * **Ação:** métodos com `JOptionPane` para informação, erro, aviso e confirmação.

- [ ] **9.4. Criar `ApiClient` e os serviços HTTP**
  * `ApiClient` com `HttpClient` + Jackson (GET, POST, PUT, DELETE).
  * `AuthApiService`, `InsumoApiService`, `MovimentacaoApiService`.
  * **Atenção:** tratar `status >= 400` lendo o campo `mensagem` do JSON.

- [ ] **9.5. Programar `LoginController`**
  * Validar campos em branco.
  * Chamar `POST /api/auth/login`.
  * Em sucesso: `SessaoUsuario.iniciarSessao(...)` e abrir `HomeFrame`.
  * Em falha: exibir erro e manter a tela aberta.

- [ ] **9.6. Programar `HomeController`**
  * Exibir nome e cargo da `SessaoUsuario`.
  * Abrir `InsumosFrame` e `MovimentacaoFrame`.
  * Logout: limpar sessão e reabrir `LoginFrame`.

- [ ] **9.7. Programar `InsumosController`**
  * Carregar a tabela com `GET /api/insumos`.
  * Buscar com `?termo=`.
  * Salvar (`POST`/`PUT`), excluir (`DELETE`) e exibir erros amigáveis.

- [ ] **9.8. Programar `MovimentacaoController`**
  * Carregar insumos com `GET /api/insumos/ordenados` (sem reordenar no frontend).
  * Carregar lotes, setores e tipos.
  * Preencher o responsável com a sessão.
  * Enviar `POST /api/movimentacoes` e tratar `alertaCritico`.
  * Usar `SwingWorker` para não travar a interface.

---

### FASE 10: Bateria de Testes Práticos

Execute os testes do roteiro da equipe com backend e frontend rodando. Resumo:

- [ ] **Teste 1:** Login errado → alerta de erro e tela aberta.
- [ ] **Teste 2:** Login correto → Home com nome e cargo.
- [ ] **Teste 3:** Abrir Insumos → `JTable` lista os registros da API.
- [ ] **Teste 4:** Pesquisar termo → tabela filtra corretamente.
- [ ] **Teste 5:** Cadastrar com campo vazio → bloqueio e aviso.
- [ ] **Teste 6:** Abrir Movimentação → insumos em ordem alfabética (Quicksort).
- [ ] **Teste 7:** Retirar mais que o estoque → bloqueio com HTTP 409.
- [ ] **Teste 8:** Retirada que cruza o mínimo → alerta visual de estoque crítico.
- [ ] **Teste 9:** Logout → sessão encerrada e retorno ao Login.
- [ ] **Teste 10:** Parar o backend e tentar logar → mensagem "Backend indisponível" sem travar.
- [ ] **Testes automatizados:** `cd backend && mvn test` com `BUILD SUCCESS`.

---

### FASE 11: Organização da Pasta Final de Entrega

Conforme especificação oficial do edital (Item 10):

- [ ] **11.1. Criar pasta com o nome do aluno/equipe** (Exemplo: `ProjetoIntegrador_Modulo2_NomeAluno`).
- [ ] **11.2. Inserir os 6 itens obrigatórios dentro da pasta:**
  1. Documento de Requisitos (PDF produzido pela equipe, com base em `Entregaveis/Modelagem_`)
  2. Imagem do DER (`Entregaveis/Banco de Dados/bebidas_db_2026-10-13T14_02_26.535Z.jpeg`)
  3. Script SQL (`Entregaveis/Banco de Dados/BancodoProjeto.sql`)
  4. Código-fonte completo do projeto (pastas `backend/` e `frontend/` com seus `pom.xml` e `src/`)
  5. Documento de Testes (PDF produzido pela equipe)
  6. Documento de Infraestrutura (PDF produzido pela equipe)
- [ ] **11.3. Remover `target/`, `.zip` antigos e credenciais locais antes de compactar.**
- [ ] **11.4. Compactar a pasta final no formato `.zip`.**

---

### FASE 12: Preparação para a Arguição Oral da Banca

Perguntas fundamentais do edital que a equipe deve dominar. Detalhes também nos requisitos em `Entregaveis/Modelagem_`:

- [ ] **Pergunta 1: Por que o banco se chama `bebidas_db`?**  
  *Resposta:* exigência formal e obrigatória do edital do SENAI.

- [ ] **Pergunta 2: Qual algoritmo de ordenação foi usado e por que?**  
  *Resposta:* Quicksort, implementado no backend em `OrdenacaoService` e exposto por `/api/insumos/ordenados`. Escolhido por divisão e conquista, complexidade média O(n log n) e ordenação in-place.

- [ ] **Pergunta 3: Por que separar frontend e backend?**  
  *Resposta:* o Swing cuida da interface; o Spring Boot concentra regras de negócio e persistência. Isso permite testar a API isoladamente, evoluir a interface sem alterar regras e evita acesso direto ao banco pelo desktop.

- [ ] **Pergunta 4: Como foi garantida a rastreabilidade das operações?**  
  *Resposta:* toda movimentação grava o operador logado na sessão, data/hora exata e setor solicitante. Não há deleção física no histórico contábil.

- [ ] **Pergunta 5: Como o sistema identifica o nível crítico?**  
  *Resposta:* compara o saldo remanescente com o atributo `quantidade_minima` do tipo de insumo. Se o saldo for menor ou igual, a API retorna `alertaCritico = true` e o frontend emite a notificação visual.
isual.

ual.
isual.

isual.

ual.

isual.

al.

ual.

isual.

