# TODO — Roteiro de Execução do Projeto Integrador
**API REST + MySQL — Sistema de Almoxarifado para Indústria de Bebidas**

- **Curso:** Técnico em Desenvolvimento de Sistemas — SENAI / FIEB (Módulo 2)
- **Arquitetura:** API REST em Java + Spring Boot + MySQL; front-end web fornecido por outra empresa
- **Banco:** MySQL 8.x (registrar a versão usada no documento técnico)
- **Fontes de verdade:**
  - `Diversos/Enunciado_Projeto_Integrador_Industria_Bebidas.docx` → regras de negócio, tecnologias e escopo
  - `Diversos/Contrato_Tecnico_API_Industria_Bebidas.docx` → rotas, campos, JSON, códigos HTTP, filtros e ordenação

> **Regra de ouro:** o front-end externo já espera as rotas, os campos JSON e os códigos HTTP do contrato. Não altere o contrato para “facilitar” a implementação.

> **Este arquivo substitui o roteiro antigo.** `*.pdf.OLD`, `Diversos/TODO.docx` e `Entregaveis/` são material do escopo anterior (desktop) e ficam apenas como histórico.

---

## 1. Como o trabalho está organizado

As entregas estão numeradas **na ordem em que devem ser executadas**:

- **1 a 4:** documentação e modelagem (RN/RF/RNF → casos de uso → banco → classes)
- **5 e 6:** banco no MySQL e código da API
- **7 a 10:** testes, documento técnico e demonstração

Quatro entregas (1 a 4) são **adicionais** de modelagem; as outras seis correspondem aos itens do Enunciado — a coluna “Item do Enunciado” da tabela 1.2 mostra a correspondência.

As **fases** (0 a 16) são as etapas de trabalho que produzem cada entrega.

### 1.1 Ordem de execução

| Fase | O que se faz | Apoio | Entrega gerada |
| --- | --- | --- | --- |
| **0** | Ler os documentos-base e preparar o ambiente | `README.md`, `AGENTS.md` | — |
| **1** | Documento de regras de negócio, RF e RNF | Contrato + Enunciado | **Entrega 1** |
| **2** | Documento e diagrama de casos de uso | Contrato | **Entrega 2** |
| **3** | Diagrama de banco de dados (MySQL) | `.opencode/skills/database-operations` | **Entrega 3** |
| **4** | Diagrama de classes (UML) | `.opencode/skills/database-operations` | **Entrega 4** |
| **5** | Script SQL (`schema.sql` + `seed.sql`) e validação | `.opencode/skills/database-operations` | **Entrega 5** |
| **6** | Esqueleto do backend Spring Boot | `.opencode/skills/api-rest` | **Entrega 6** |
| **7** | Entidades JPA, enums, DTOs e repositórios | `.opencode/skills/database-operations` | **Entrega 6** |
| **8** | JWT, BCrypt e rotas protegidas | `.opencode/skills/jwt-security` | **Entrega 6** |
| **9** | Usuários, setores e fornecedores | `.opencode/skills/api-rest` | **Entrega 6** |
| **10** | Insumos e alertas de estoque | `.opencode/skills/api-rest` | **Entrega 6** |
| **11** | Movimentações, transação e concorrência | `.opencode/skills/database-operations` | **Entrega 6** |
| **12** | Validações e erros padronizados | `.opencode/skills/api-rest` | **Entrega 6** |
| **13** | Testes, coleção Postman e evidências | Postman + Maven | **Entregas 7 e 8** |
| **14** | Documento técnico e instruções de execução | `README.md` | **Entrega 9** |
| **15** | Ensaio da demonstração e da banca | Enunciado, seção 9 | **Entrega 10** |
| **16** | Conferência final e envio | Este TODO | fecha **Entregas 5 e 6** |

### 1.2 As 10 entregas

Marque cada entrega somente quando estiver completa e revisada.

| Entrega | Nome | Fase | Item do Enunciado | Feito? |
| --- | --- | --- | --- | --- |
| **1** | Documento de **RN-01 a RN-16 + RF (26 operações) + RNF + matriz de rastreabilidade** | 1 | adicional | [ ] |
| **2** | Documento e diagrama de **casos de uso** (atores, fluxos, rastreabilidade) | 2 | adicional | [ ] |
| **3** | Diagrama de **banco de dados (MySQL)** — tabelas, tipos, PKs, FKs, restrições, índices, cardinalidades | 3 | adicional | [X] |
| **4** | Diagrama de **classes (UML)** — classes, enums, atributos, métodos, relacionamentos | 4 | adicional | [ ] |
| **5** | **Banco MySQL integrado**, com script de criação/carga e dados de demonstração | 5 | item 2 | [ ] |
| **6** | **Código-fonte completo** do Spring Boot (`pom.xml`, camadas, configuração, instruções de execução) | 6–12 | item 1 | [ ] |
| **7** | **Coleção Postman** com as **26 operações** do contrato | 13 | item 3 | [ ] |
| **8** | **Relatório curto de testes** (esperado × observado × evidência, CT01 a CT22) | 13 | item 4 | [ ] |
| **9** | **Documento técnico** (ferramentas, versões, banco, configurações sem senhas reais, arquitetura) | 14 | item 5 | [ ] |
| **10** | **Demonstração prática**, com decisões técnicas e regras de negócio | 15 | item 6 | [ ] |

> As entregas 1 a 4 são **adicionais** de modelagem e requisitos, criadas pela equipe. As entregas 5 a 10 correspondem aos seis itens do Enunciado, na ordem em que são executados. Confirmem com o professor o formato (PDF, imagem ou editável) e o local de entrega. Todas devem ser produzidas e revisadas pela equipe; o agente apenas orienta.

### 1.3 Definição de pronto

- [ ] As **26 operações** existem e respondem conforme o contrato.
- [ ] Os **22 casos mínimos (CT01 a CT22)** foram executados com evidência.
- [ ] `mvn test` termina com `BUILD SUCCESS`.
- [ ] O banco é criado do zero pelo script e a API sobe sem erro.
- [ ] Nenhuma senha, token, chave JWT ou credencial real está versionada.
- [ ] Coleção Postman e relatório de testes atualizados.
- [ ] Entregas 1 a 4 (documentação e modelagem) revisadas, coerentes entre si e no formato combinado com o professor.
- [ ] A equipe consegue explicar o código, os documentos, os diagramas e as regras na banca.

---

## 2. Passo a passo por fase

### FASE 0 — Preparação

- [ ] **0.1. Ler os dois documentos-base** (`Enunciado` e `Contrato Técnico`).
  - Entender que o projeto é só backend e que o contrato tem 26 operações.
- [ ] **0.2. Conferir o ambiente:** Java 17+, Maven, MySQL, Postman, IDE Java e Git.
  - `java -version`, `mvn -version` e `mysql --version` devem funcionar.
- [ ] **0.3. Criar o banco local:** schema no MySQL e usuário de aplicação.
  - Senha por variável de ambiente, nunca no Git.
- [ ] **0.4. Combinar com o professor o formato de entrega:** repositório/ZIP e onde entregar Postman e PDFs.
- [ ] **0.5. Revisar o fluxo de Git:** branch por assunto, Pull Request, commits com a autoria de `docs/colaboradores.md`.

### FASE 1 — Documento de RN/RF/RNF (Entrega 1)

Esta fase é **dos autores**. O agente pode explicar e revisar, mas **não produz** o documento.

- [ ] **1.1. Revisar os objetos do contrato:** usuário, setor, fornecedor, insumo, movimentação e alerta (campos de cada JSON, seção 3 do contrato).
- [ ] **1.2. Levantar os requisitos funcionais (RF):** um RF por operação, com identificador, descrição, ator, endpoint, entrada, saída e status HTTP. → 26 endpoints cobertos.
- [ ] **1.3. Levantar os requisitos não funcionais (RNF):** segurança (JWT e hash), desempenho, portabilidade, manutenibilidade, tratamento de erros e consistência transacional. Cada RNF deve ser verificável.
- [ ] **1.4. Documentar as RN-01 a RN-16:** descrição, impacto na API e forma de verificação (usar a tabela da seção 4 como conferência).
- [ ] **1.5. Montar e revisar o documento:** introdução, escopo, atores, RF, RNF, RN e matriz de rastreabilidade RF → endpoint → CT01 a CT22.

### FASE 2 — Documento e diagrama de casos de uso (Entrega 2)

Só começar depois que o documento de RN/RF/RNF estiver aprovado.

- [ ] **2.1. Identificar atores e casos de uso:** visitante (cadastro e login), usuário autenticado (demais operações) e, se a equipe preferir, o front-end como ator externo.
- [ ] **2.2. Detalhar cada caso de uso:** objetivo, ator, pré-condições, fluxo principal, fluxos alternativos/exceções e resposta esperada.
- [ ] **2.3. Desenhar o diagrama UML:** atores, casos de uso, associações e `<<include>>`/`<<extend>>` quando fizer sentido.
- [ ] **2.4. Rastrear e revisar:** cada caso de uso ligado aos RF e endpoints; nenhuma funcionalidade fora do contrato.

### FASE 3 — Diagrama de banco de dados (Entrega 3)

Só começar depois dos casos de uso aprovados.

- [ ] **3.1. Revisar entidades e relacionamentos:** usuário, setor, fornecedor, insumo e movimentação.
  - Não reaproveitar o DER antigo de `Entregaveis/` sem revisar: ele é do escopo desktop e usa estoque por lote.
- [ ] **3.2. Definir tabelas, colunas, tipos e restrições:** PKs, FKs, unicidade, nulabilidade, índices e cardinalidades.
- [ ] **3.3. Desenhar o diagrama** (MySQL Workbench ou Draw.io), legível e exportável.
- [ ] **3.4. Revisar:** compatível com o documento de RN/RF/RNF e com o futuro script MySQL. → **Entrega 3 aprovada.**

### FASE 4 — Diagrama de classes (Entrega 4)

Só começar depois do diagrama de banco aprovado.

- [ ] **4.1. Identificar classes e enums:** `Usuario`, `Setor`, `Fornecedor`, `Insumo`, `Movimentacao`; enums `TipoMovimentacao` (`ENTRADA`/`SAIDA`) e `UnidadeMedida` (`UN`/`KG`/`L`), se a equipe optar por enum.
- [ ] **4.2. Definir atributos, métodos, relacionamentos e multiplicidades.**
- [ ] **4.3. Desenhar o diagrama** refletindo o contrato e o diagrama de banco; sem front-end.
- [ ] **4.4. Revisar:** compatível com o diagrama de banco e com os DTOs/contrato. → **Entrega 4 aprovada.**

### FASE 5 — Script SQL e validação do MySQL (Entrega 5)

Só começar depois dos diagramas de banco e de classes aprovados.

- [ ] **5.1. Escrever o script de estrutura** em arquivo **novo** (`db/schema.sql`); não sobrescrever o `BancodoProjeto.sql` antigo sem autorização dos autores.
  - Separar `db/schema.sql` (estrutura) e `db/seed.sql` (dados de demonstração). Usar `InnoDB` e `utf8mb4`.
- [ ] **5.2. Modelo mínimo esperado**

| Tabela | Campos mínimos | Observações para MySQL |
| --- | --- | --- |
| `usuarios` | `id`, `nome`, `login`, `senha_hash`, `ativo` | `login` único; `senha_hash VARCHAR(255)`; `ativo TINYINT(1) DEFAULT 1` |
| `setores` | `id`, `nome`, `descricao`, `ativo` | nome único entre ativos (validar no service; MySQL não tem índice parcial simples) |
| `fornecedores` | `id`, `nome`, `cnpj`, `telefone`, `ativo` | `cnpj CHAR(14)` único, somente dígitos; `telefone` opcional |
| `insumos` | `id`, `nome`, `unidade_medida`, `quantidade_atual`, `estoque_minimo`, `fornecedor_id`, `ativo` | quantidades em `DECIMAL(15,3)`; `unidade_medida` em `UN`/`KG`/`L`; FK para fornecedor |
| `movimentacoes` | `id`, `insumo_id`, `tipo`, `quantidade`, `setor_id`, `usuario_id`, `data_movimentacao`, `data_registro`, `lote`, `validade`, `saldo_anterior`, `saldo_atual` | `tipo` `ENTRADA`/`SAIDA`; `setor_id`, `lote` e `validade` NULL permitidos; `data_movimentacao DATE`; `data_registro DATETIME(6)`; FKs sem cascade no histórico |

- [ ] **5.3. Definir tipos e restrições**
  - `DECIMAL(15,3)` para quantidades; `DATE` para `data_movimentacao`; `DATETIME(6)` ou `TIMESTAMP` para `data_registro`; `TINYINT(1)`/`BOOLEAN` para `ativo`.
  - `ENUM('ENTRADA','SAIDA')` ou `VARCHAR(7)` validado para `tipo`; `ENUM('UN','KG','L')` ou `VARCHAR(2)` validado para `unidade_medida`.
  - Unicidade em `login` e `cnpj`; FKs de `insumos.fornecedor_id` e de `movimentacoes.insumo_id`/`usuario_id`/`setor_id`.
  - **Não** usar `ON DELETE CASCADE` em movimentações: o histórico deve ser preservado.
- [ ] **5.4. Preparar os dados de demonstração (`seed.sql`)**
  - Usuários com senha em **hash BCrypt**; setores, fornecedores e insumos ativos.
  - Insumos começam com saldo zero; criar entradas e saídas para gerar saldo e histórico.
  - Pelo menos um insumo abaixo ou no estoque mínimo para demonstrar o alerta. Sem credenciais reais.
- [ ] **5.5. Executar e validar do zero** em banco limpo: `SHOW TABLES;`, `DESCRIBE tabela;`, `SELECT COUNT(*) FROM tabela;`, testar unicidade e FKs e a consulta de alerta:
  `SELECT ... FROM insumos WHERE ativo = 1 AND quantidade_atual <= estoque_minimo;`
- [ ] **5.6. Revisar os artefatos com a equipe:** documento de RN/RF/RNF, casos de uso, diagrama de banco, diagrama de classes e script contam a mesma história.
  - Todos entendem as regras, as tabelas e por que não há saldo por lote.

### FASE 6 — Esqueleto do backend Spring Boot

- [ ] **6.1. Criar o projeto:** Spring Initializr ou IntelliJ IDEA; Maven; Java 17+.
  - Artefato `backend`; pacote base `com.senai.bebidas` (ou o que a equipe definir).
- [ ] **6.2. Adicionar as dependências:** Spring Web, Spring Data JPA, Validation, Spring Security, MySQL Driver, Spring Boot Test e a biblioteca JWT escolhida (ex.: JJWT `api`/`impl`/`jackson`).
  - `mvn clean package -DskipTests` com sucesso.
- [ ] **6.3. Configurar o `application.properties`**
  - URL do MySQL com `serverTimezone=America/Sao_Paulo`; usuário e senha por variável de ambiente.
  - `spring.jpa.hibernate.ddl-auto=validate` (o schema vem do script); `spring.jpa.open-in-view=false`; `server.port=8080`.
  - Segredo JWT e expiração de 3600 segundos por configuração.
- [ ] **6.4. Criar a estrutura de pacotes:** `controller`, `service`, `repository`, `model`, `dto`, `exception`, `security`, `config`.
- [ ] **6.5. Criar o `README` do backend:** pré-requisitos, criação do banco, script, variáveis, subir a API e importar o Postman.
- [ ] **6.6. Proteger o repositório:** `.gitignore` para `target/`, `.idea/`, `.env`, credenciais e arquivos locais.

### FASE 7 — Entidades JPA, enums, DTOs e repositórios

- [ ] **7.1. Criar as entidades:** `Usuario`, `Setor`, `Fornecedor`, `Insumo`, `Movimentacao` e os enums `TipoMovimentacao` e `UnidadeMedida`.
- [ ] **7.2. Mapear corretamente**
  - `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column` com os nomes do DER, `@ManyToOne` + `@JoinColumn`, `@Enumerated(EnumType.STRING)`.
  - `BigDecimal` para quantidades; `LocalDate` para data operacional/validade; `OffsetDateTime` ou `Instant` para `dataRegistro`; `ativo` nas entidades de cadastro.
  - O Hibernate deve **validar** o schema, sem criar tabelas por conta própria.
- [ ] **7.3. Criar os DTOs:** requests e responses separados, DTOs de erro padronizados; nunca devolver entidade com senha/hash.
- [ ] **7.4. Criar os repositórios**
  - `UsuarioRepository`: busca por login, unicidade, ativos.
  - `SetorRepository` / `FornecedorRepository`: listagens por ativo e id.
  - `InsumoRepository`: busca parcial por nome, ordenação, filtro de ativo e consulta de alerta.
  - `MovimentacaoRepository`: filtros e ordenação por `dataRegistro` desc + `id` desc.
  - Evitar `findAll()` sem critério.
- [ ] **7.5. Testar a persistência** com `@DataJpaTest` para as consultas principais.

### FASE 8 — Segurança: JWT, BCrypt e rotas protegidas

- [ ] **8.1. Configurar o `PasswordEncoder`** com BCrypt: cadastro grava hash, login compara com `matches`.
- [ ] **8.2. Liberar somente as rotas públicas:** `POST /api/usuarios` e `POST /api/auth/login`. Todo o resto exige `Authorization: Bearer <JWT>`.
- [ ] **8.3. Implementar o login:** buscar por `login`, verificar `ativo`, comparar senha e gerar token.
  - Resposta `{ "token": "...", "tipo": "Bearer", "expiraEm": 3600 }`; credenciais inválidas ou usuário inativo → 401.
- [ ] **8.4. Implementar o serviço de JWT:** segredo em variável de ambiente, assinatura validada, expiração de 3600 segundos, token identificando o usuário.
- [ ] **8.5. Implementar o filtro JWT:** ler `Authorization: Bearer`, validar, carregar o usuário e preencher o `SecurityContext`.
  - Token ausente/inválido/expirado → 401 com `{status, mensagem, timestamp}`; nunca devolver o HTML de login do Spring Security.
- [ ] **8.6. Implementar `GET /api/usuarios/me`:** identidade vem do token, nunca de um ID enviado pelo cliente.
- [ ] **8.7. Testar a segurança:** login correto, login errado, usuário inativo, sem token, token inválido e expirado → **CT03, CT04 e CT05**.

### FASE 9 — Usuários, setores e fornecedores

- [ ] **9.1. Usuários (7 operações)**
  - `POST`: nome, login e senha obrigatórios; senha ≥ 8; login único; 201 sem senha.
  - `GET /me`: usuário do token.
  - `GET`: ativos por padrão, `?ativo=todos` e `?ativo=false`, ordem por id.
  - `GET /{id}`: inclui inativo; 404 se não existir.
  - `PUT /{id}`: nome, login e `ativo`; não altera senha; login único; permite reativação.
  - `DELETE /{id}`: inativação lógica; 204 sem corpo. → **CT01, CT02, CT17 e CT18.**
- [ ] **9.2. Setores (5 operações)**
  - `POST`: nome obrigatório e único entre ativos; `descricao`.
  - `GET`: ativos por padrão; filtros `?ativo=todos`/`?ativo=false`; ordem por id.
  - `GET /{id}`: inclui inativo; 404 se não existir.
  - `PUT /{id}`: nome, descrição e `ativo` obrigatório; nome único entre ativos.
  - `DELETE /{id}`: excluir se sem vínculos; senão inativar; 204.
- [ ] **9.3. Fornecedores (5 operações)**
  - `POST`: nome e CNPJ obrigatórios; CNPJ com 14 dígitos e único; telefone opcional.
  - `GET` e `GET /{id}`: mesmas regras de ativo/inativo dos demais cadastros.
  - `PUT /{id}`: nome, CNPJ, telefone e `ativo`; CNPJ único; fornecedor inativo não pode ser vinculado a novo insumo.
  - `DELETE /{id}`: se associado a insumos, preservar referência e inativar; 204. → **CT06.**
- [ ] **9.4. Revisar os erros:** 400 para validação, 404 para ID inexistente, 409 para duplicidade, com JSON padronizado.

### FASE 10 — Insumos e alertas

- [ ] **10.1. `POST /api/insumos`**
  - Campos: nome, unidade de medida, estoque mínimo e fornecedor.
  - Saldo inicial sempre zero; **não** aceitar `quantidadeAtual` no request; fornecedor deve estar ativo.
  - Unidade `UN` inteira; `KG`/`L` com até 3 casas. → **CT07.**
- [ ] **10.2. Listagem, consulta e atualização**
  - `GET /api/insumos`: busca parcial por nome, ignorando maiúsculas/minúsculas; `?ativo=todos` e `?ativo=false`; ordem alfabética.
  - `GET /api/insumos/{id}`: inclui inativo; 404 se não existir.
  - `PUT /api/insumos/{id}`: não altera saldo; unidade imutável depois de existir movimentação; fornecedor ativo quando alterado; `ativo` obrigatório.
  - `DELETE /api/insumos/{id}`: excluir se sem movimentações; com histórico, inativar e preservar. → **CT08, CT13, CT16 e CT17.**
- [ ] **10.3. Alertas:** `GET /api/alertas/estoque` com somente insumos ativos e `quantidadeAtual <= estoqueMinimo`.
  - Ordem por nome; `situacao` sempre `"CRITICO"`; array `[]` quando vazio. → **CT15.**
- [ ] **10.4. Conferir o modelo:** nenhum saldo por lote; lote e validade são informações da movimentação.

### FASE 11 — Movimentações, transação e concorrência

Fase de maior peso nas regras de negócio. Não pule os testes de concorrência.

- [ ] **11.1. `POST /api/movimentacoes`**
  - Endpoint único para `ENTRADA` e `SAIDA`; não criar `PUT` nem `DELETE`.
  - `usuarioId` vem do JWT; não aceitar do corpo. → 201 com o JSON do contrato.
- [ ] **11.2. Validar a entrada**
  - `tipo` obrigatório (`ENTRADA`/`SAIDA`); `quantidade` estritamente positiva e compatível com a unidade.
  - `dataMovimentacao` não pode ser futura.
  - `SAIDA` exige setor ativo e não recebe lote/validade.
  - `ENTRADA` pode omitir lote/validade; se validade vier, não pode ser anterior à data operacional.
  - Insumo inativo → 409. → **CT12, CT13 e CT21.**
- [ ] **11.3. Garantir a transação**
  - Buscar o insumo com lock pessimista **ou** fazer atualização atômica condicional.
  - Calcular `saldoAnterior` e `saldoAtual`, atualizar o saldo e gravar a movimentação na mesma transação.
  - `dataRegistro` gerada pelo servidor no momento do lançamento; falha → rollback integral. → **CT09, CT10, CT11 e CT19.**
- [ ] **11.4. Testar concorrência:** duas saídas simultâneas com saldo insuficiente para as duas.
  - Uma falha com 409; saldo nunca negativo e sem histórico parcial. → **CT20.**
- [ ] **11.5. Histórico**
  - `GET /api/movimentacoes`: filtros `insumoId`, `setorId`, `usuarioId`, `tipo`, `dataInicio` e `dataFim`.
  - Ordenação `dataRegistro` desc e, no empate, `id` desc; `GET /{id}` com 404 se não existir. → **CT14 e CT22.**
- [ ] **11.6. Documentar a decisão:** explicar no documento técnico se usaram lock pessimista, atualização atômica ou lock otimista.

### FASE 12 — Validações e tratamento padronizado de erros

- [ ] **12.1. Criar o `@RestControllerAdvice`** para centralizar validação, autenticação, recurso não encontrado, regra de negócio e erro inesperado.
- [ ] **12.2. Mapear os status**
  - **400:** campo obrigatório, quantidade inválida, data futura, `ativo` inválido, `dataInicio > dataFim`.
  - **401:** credenciais inválidas, token ausente/inválido/expirado.
  - **404:** recurso não encontrado.
  - **409:** duplicidade, estoque insuficiente, insumo inativo, vínculo que impede exclusão.
  - **500:** falha inesperada.
- [ ] **12.3. Padronizar o JSON:** `{ "status": ..., "mensagem": "...", "timestamp": "..." }`, com timestamp real da falha; nunca devolver stack trace, SQL ou detalhes internos.
- [ ] **12.4. Testar os erros:** cada status com pelo menos um caso positivo e um negativo.

### FASE 13 — Testes e coleção Postman

- [ ] **13.1. Montar a coleção Postman:** uma pasta por grupo (usuários/auth, setores, fornecedores, insumos, movimentações, alertas), 26 requisições no total.
  - Ambiente com `baseUrl` e `token`; no login, salvar o token na variável; nas rotas protegidas, usar `Bearer {{token}}`.
- [ ] **13.2. Executar os casos CT01 a CT22**, registrando resultado esperado, observado e evidência.
  - Incluir os testes de erro: sem token, token inválido, estoque insuficiente, data futura, duplicidade e insumo inativo.
- [ ] **13.3. Testes automatizados:** service e controller, `@DataJpaTest` para repositórios, `@SpringBootTest` + `MockMvc` para endpoints; `mvn test` com `BUILD SUCCESS`.
- [ ] **13.4. Testar o banco do zero:** apagar o schema de teste, rodar o script, subir a API e repetir os fluxos principais.
- [ ] **13.5. Exportar as evidências:** coleção Postman e prints/logs do relatório → **Entregas 7 e 8 prontas.**

### FASE 14 — Documento técnico e instruções (Entrega 9)

- [ ] **14.1. Escrever o documento técnico**
  - Ferramentas e versões (Java, Spring Boot, Maven, MySQL, Postman, IDE), banco e schema, como criar/carregar.
  - Configurações necessárias, sem senhas reais; arquitetura resumida (Controller → Service → Repository, JWT, tratamento de erros).
  - Decisões: inativação lógica, transação/concorrência, ordenação, data operacional × data de registro.
  - **Não confundir** com o documento de RN/RF/RNF (Entrega 1): lá ficam requisitos e regras; aqui ficam ferramentas, banco, configuração e arquitetura.
- [ ] **14.2. Revisar o `README` do backend:** pré-requisitos, banco, script, variáveis, `mvn spring-boot:run`, Postman e `mvn test`.
- [ ] **14.3. Revisar segurança:** nenhuma senha, token, chave JWT ou credencial real no repositório; `.env.example` apenas com nomes de variáveis.

### FASE 15 — Demonstração e banca (Entrega 10)

Use o checklist do Enunciado, seção 9.

- [ ] **15.1.** Cadastro de usuário e login retornando JWT.
- [ ] **15.2.** Bloqueio de endpoint sem autenticação.
- [ ] **15.3.** Cadastros e consultas de setores, fornecedores e insumos.
- [ ] **15.4.** Pesquisa ordenada e consulta de registros inativos.
- [ ] **15.5.** Entrada, saída e conferência do saldo após cada operação.
- [ ] **15.6.** Rejeição de retirada superior ao estoque e de entrada inválida.
- [ ] **15.7.** Consulta do histórico e dos alertas de estoque.
- [ ] **15.8.** Preservação do histórico após inativação e teste de consistência.
- [ ] **15.9.** Ensaiar a explicação de cada decisão técnica.
- [ ] **15.10.** Dividir quem apresenta cada parte e cronometrar a demonstração.

### FASE 16 — Conferência final e entrega

- [ ] **16.1.** Conferir as 10 entregas da seção 1.2.
- [ ] **16.2.** Conferir documento de RN/RF/RNF, casos de uso, diagrama de banco e diagrama de classes.
- [ ] **16.3.** Conferir as 26 operações e os 22 casos CT01 a CT22.
- [ ] **16.4.** Rodar `mvn clean test` e subir a API em uma máquina limpa.
- [ ] **16.5.** Conferir `git status`, remover `target/` e confirmar que não há segredos.
- [ ] **16.6.** Confirmar com o professor o formato final de envio.
- [ ] **16.7.** Fazer o commit/tag final e avisar a equipe.

---

## 3. Controle das 26 operações

Marque **Código** quando o endpoint estiver implementado e **Postman** quando estiver testado com evidência.

| # | Grupo | Método e rota | Código | Postman |
| --- | --- | --- | --- | --- |
| 1 | Usuários/auth | `POST /api/usuarios` | [ ] | [ ] |
| 2 | Usuários/auth | `POST /api/auth/login` | [ ] | [ ] |
| 3 | Usuários/auth | `GET /api/usuarios/me` | [ ] | [ ] |
| 4 | Usuários/auth | `GET /api/usuarios` | [ ] | [ ] |
| 5 | Usuários/auth | `GET /api/usuarios/{id}` | [ ] | [ ] |
| 6 | Usuários/auth | `PUT /api/usuarios/{id}` | [ ] | [ ] |
| 7 | Usuários/auth | `DELETE /api/usuarios/{id}` | [ ] | [ ] |
| 8 | Setores | `POST /api/setores` | [ ] | [ ] |
| 9 | Setores | `GET /api/setores` | [ ] | [ ] |
| 10 | Setores | `GET /api/setores/{id}` | [ ] | [ ] |
| 11 | Setores | `PUT /api/setores/{id}` | [ ] | [ ] |
| 12 | Setores | `DELETE /api/setores/{id}` | [ ] | [ ] |
| 13 | Fornecedores | `POST /api/fornecedores` | [ ] | [ ] |
| 14 | Fornecedores | `GET /api/fornecedores` | [ ] | [ ] |
| 15 | Fornecedores | `GET /api/fornecedores/{id}` | [ ] | [ ] |
| 16 | Fornecedores | `PUT /api/fornecedores/{id}` | [ ] | [ ] |
| 17 | Fornecedores | `DELETE /api/fornecedores/{id}` | [ ] | [ ] |
| 18 | Insumos | `POST /api/insumos` | [ ] | [ ] |
| 19 | Insumos | `GET /api/insumos` | [ ] | [ ] |
| 20 | Insumos | `GET /api/insumos/{id}` | [ ] | [ ] |
| 21 | Insumos | `PUT /api/insumos/{id}` | [ ] | [ ] |
| 22 | Insumos | `DELETE /api/insumos/{id}` | [ ] | [ ] |
| 23 | Movimentações | `POST /api/movimentacoes` | [ ] | [ ] |
| 24 | Movimentações | `GET /api/movimentacoes` | [ ] | [ ] |
| 25 | Movimentações | `GET /api/movimentacoes/{id}` | [ ] | [ ] |
| 26 | Alertas | `GET /api/alertas/estoque` | [ ] | [ ] |

---

## 4. RN-01 a RN-16

Marque cada regra quando estiver implementada **e** comprovada por teste ou evidência.

| Regra | O que precisa ser verdadeiro | Onde validar | Feito? |
| --- | --- | --- | --- |
| **RN-01** | Insumo nasce com `quantidadeAtual = 0`; o saldo só muda por `ENTRADA` ou `SAIDA`, nunca pelo `PUT` do cadastro | `POST/PUT /api/insumos`, `POST /api/movimentacoes` | [ ] |
| **RN-02** | `ENTRADA` soma e `SAIDA` subtrai; quantidade positiva e compatível com `UN`/`KG`/`L` | Service de movimentação, DTOs, testes de unidade | [ ] |
| **RN-03** | Saldo nunca fica negativo, inclusive com retiradas simultâneas | Teste de concorrência (CT20) | [ ] |
| **RN-04** | Saldo e histórico são gravados na mesma transação; falha faz rollback integral | `@Transactional`, CT19 | [ ] |
| **RN-05** | A movimentação registra usuário do JWT, `dataMovimentacao`, `dataRegistro`, `saldoAnterior` e `saldoAtual` | `POST/GET /api/movimentacoes` | [ ] |
| **RN-06** | `SAIDA` exige setor ativo e não aceita lote/validade; `ENTRADA` aceita lote/validade opcionais; não há saldo por lote | Service de movimentação, schema MySQL | [ ] |
| **RN-07** | Movimentações são imutáveis: não existem `PUT`/`DELETE` de histórico | Mapa das 26 operações | [ ] |
| **RN-08** | Insumo com histórico é inativado e mantém as referências; nada de `ON DELETE CASCADE` no histórico | `DELETE /api/insumos/{id}`, FKs do MySQL | [ ] |
| **RN-09** | Inativos continuam acessíveis por ID; listagens mostram ativos por padrão e aceitam `?ativo=todos` e `?ativo=false` | Todos os `GET` de cadastros | [ ] |
| **RN-10** | `PUT` com `ativo=true` reativa setores, fornecedores, insumos e usuários | `PUT` dos quatro cadastros, CT17 | [ ] |
| **RN-11** | Cada insumo tem um único fornecedor principal; fornecedor inativo não entra em novo vínculo | `POST/PUT /api/insumos`, CT06 | [ ] |
| **RN-12** | Alerta somente para insumos ativos com `quantidadeAtual <= estoqueMinimo` | `GET /api/alertas/estoque`, CT15 | [ ] |
| **RN-13** | `dataMovimentacao` é informada pelo cliente, não pode ser futura e não reordena saldos retroativamente | Validação do DTO, CT21 | [ ] |
| **RN-14** | Somente `POST /api/usuarios` e `POST /api/auth/login` são públicos; o resto exige JWT | `SecurityFilterChain`, CT05 | [ ] |
| **RN-15** | Todos os autenticados têm as mesmas permissões; senha e hash nunca aparecem no JSON | DTOs de resposta, CT01 | [ ] |
| **RN-16** | Pesquisa de insumos é parcial e ignora caixa; ordenação pode usar banco ou Java | `GET /api/insumos`, CT08 | [ ] |

---

## 5. Casos mínimos de aceitação (CT01 a CT22)

Execute todos com o backend e o MySQL rodando. Guarde a evidência (print, log ou export do Postman) para o relatório.

| Caso | Situação | Resultado esperado | Evidência | Feito? |
| --- | --- | --- | --- | --- |
| **CT01** | Cadastrar usuário válido | 201; senha ausente na resposta |  | [ ] |
| **CT02** | Login duplicado | 409 |  | [ ] |
| **CT03** | Login correto | 200 e JWT válido por 3600 segundos |  | [ ] |
| **CT04** | Login incorreto ou usuário inativo | 401 |  | [ ] |
| **CT05** | GET protegido sem token | 401 |  | [ ] |
| **CT06** | Criar setor/fornecedor | 201 e persistência |  | [ ] |
| **CT07** | Criar insumo | 201; saldo zero |  | [ ] |
| **CT08** | Listar e pesquisar insumos | 200; pesquisa parcial e ordem alfabética |  | [ ] |
| **CT09** | Entrada de 100 | saldo +100; histórico criado |  | [ ] |
| **CT10** | Saída de 30 | saldo -30; histórico criado |  | [ ] |
| **CT11** | Saída maior que saldo | 409; sem efeitos parciais |  | [ ] |
| **CT12** | Quantidade zero/negativa | 400 |  | [ ] |
| **CT13** | Insumo inativo em movimentação | 409 |  | [ ] |
| **CT14** | Filtrar histórico | registros correspondentes aos filtros |  | [ ] |
| **CT15** | Alertas de estoque | apenas ativos abaixo ou no mínimo |  | [ ] |
| **CT16** | Excluir insumo com histórico | 204; ativo=false; histórico preservado |  | [ ] |
| **CT17** | Reativar cadastro por PUT | 200; ativo=true |  | [ ] |
| **CT18** | Listagem `?ativo=todos` | inclui registros inativos |  | [ ] |
| **CT19** | Falha durante registro de movimentação | rollback integral |  | [ ] |
| **CT20** | Duas saídas concorrentes com saldo insuficiente | não gera estoque negativo |  | [ ] |
| **CT21** | Data operacional futura | 400 |  | [ ] |
| **CT22** | Entrada com lote e validade opcionais | 201; histórico preserva os valores |  | [ ] |

---

## 6. Perguntas prováveis da banca

1. **Por que a API segue o contrato?** Porque o front-end é de outra empresa e já consome as rotas, os campos e os status definidos. Alterar o contrato quebraria a integração.
2. **Como o JWT funciona?** O login valida `login` + senha (BCrypt) e gera um token assinado com expiração de 3600 segundos; o front-end envia o token em `Authorization: Bearer`; o filtro valida assinatura e expiração antes de liberar a rota.
3. **Por que BCrypt?** O hash é irreversível e protege as senhas em caso de vazamento. A API nunca devolve senha nem hash.
4. **Como impedem saldo negativo com duas saídas simultâneas?** Com transação e controle de concorrência (lock pessimista, atualização atômica condicional ou lock otimista). Se o saldo não comportar, a operação falha com 409 e não gera histórico parcial.
5. **Por que 409 e não 400 em estoque insuficiente?** Porque o JSON está válido; o problema é de estado/regra de negócio. O contrato define 409 para conflitos de regra.
6. **Como funciona a inativação?** `ativo=false` preserva o registro e o histórico. Usuários são sempre inativados; setores, fornecedores e insumos podem ser excluídos se não tiverem vínculos. Consultas por ID mostram inativos; listagens mostram ativos por padrão.
7. **Por que não há saldo por lote?** Porque o contrato define lote e validade como informações da movimentação; o saldo é único por insumo.
8. **Diferença entre `dataMovimentacao` e `dataRegistro`?** `dataMovimentacao` é a data operacional informada pelo usuário, não pode ser futura e serve para filtros. `dataRegistro` é o instante real em que o servidor gravou a movimentação.
9. **Como o projeto está organizado?** Em camadas Controller → Service → Repository, com DTOs, tratamento centralizado de erros e segurança JWT.
10. **Como vocês testaram?** Coleção Postman cobrindo as 26 operações, casos CT01 a CT22, testes automatizados com `mvn test` e evidências de esperado/observado.
11. **Por que não há front-end?** Porque a solução foi dividida entre duas empresas; a outra já fez o front-end web. Nossa responsabilidade é a API, as regras e a integração com o banco.
12. **Como garantem pesquisa e ordenação corretas?** A pesquisa é parcial e `IgnoreCase`; insumos/alertas ordenam por nome crescente e o histórico por `dataRegistro` desc + `id` desc — no banco ou em Java, conforme a decisão da equipe.

---

## 7. Critérios de avaliação

| Critério | Peso |
| --- | --- |
| Fidelidade ao contrato REST e funcionalidades | 35% |
| Regras de negócio, validações e concorrência | 25% |
| Arquitetura Controller/Service/Repository | 15% |
| Integração com banco e JPA | 10% |
| JWT e tratamento de erros | 10% |
| Testes, documentação e demonstração | 5% |

**Prioridade prática:** contrato → regras de negócio → transação/concorrência → testes/documentação.

---

## 8. O que não faz parte do escopo

- Front-end, interfaces desktop, tela de login, HTML, CSS, JavaScript.
- Notificações por e-mail ou push.
- Compras e pedidos de fornecedores.
- Autorização por perfis (todos os autenticados têm as mesmas permissões).
- Troca/renovação de JWT, estornos e integrações externas.
- Controle de saldo por lote, vencimento automático e bloqueio de uso por lote.
- Relatórios PDF ou Excel.

---

## 9. Observações sobre os documentos antigos

- `Diversos/Projeto Integrador Módulo 2 - Industria_Bebidas.pdf.OLD` — PDF antigo, **obsoleto**.
- `Entregaveis/` — SQL, DER e requisitos da versão desktop. **Não são a fonte de verdade** do escopo atual e não devem ser alterados sem autorização dos autores.
- Se a equipe produzir um novo DER/SQL, ele passa a ser a referência do banco. O contrato continua sendo a fonte de verdade da API.

> Em caso de dúvida, vale o **Contrato Técnico** para rotas, campos, JSON, status, filtros e ordenação; e o **Enunciado** para regras de negócio, tecnologias e escopo. Se os dois não resolverem, perguntem ao professor.
