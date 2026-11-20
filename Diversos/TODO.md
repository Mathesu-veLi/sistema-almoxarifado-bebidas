# TODO — Roteiro de Execução do Projeto Integrador
**API REST + MySQL — Sistema de Almoxarifado para Indústria de Bebidas**

- **Curso:** Técnico em Desenvolvimento de Sistemas — SENAI / FIEB (Módulo 2)
- **Arquitetura:** API REST em Java + Spring Boot + MySQL; front-end web fornecido por outra empresa
- **Banco:** MySQL 8.x (registrar a versão usada no documento técnico)
- **Fontes de verdade:**
  - `Diversos/Enunciado_Projeto_Integrador_Industria_Bebidas.docx` → regras de negócio, tecnologias e escopo
  - `Diversos/Contrato_Tecnico_API_Industria_Bebidas.docx` → rotas, campos, JSON, códigos HTTP, filtros e ordenação

> **Regra de ouro:** o front-end externo já espera as rotas, os campos JSON e os códigos HTTP do contrato. Não altere o contrato para “facilitar” a implementação.

> **Este arquivo substitui o roteiro antigo.** O `Diversos/TODO.docx` foi removido (commit `6d9eb9b`) e o `*.pdf.OLD` é material do escopo anterior (desktop). `Entregaveis/` é a pasta oficial de entrega atual, separada por matéria.

---

## 1. Como o trabalho está organizado

As entregas estão numeradas **na ordem em que devem ser executadas**:

- **1 a 7:** documentação e modelagem (RN → RF → RNF → casos de uso → banco → classes)
- **8 a 11:** banco no MySQL, código da API e coleção Postman
- **12:** documentos de explicação do projeto (schema, seed e classes do código)
- **13 a 15:** testes, documento técnico e demonstração

Oito entregas (1 a 7 e 12) são **adicionais** de modelagem, documentação e apoio ao estudo; as outras sete cobrem os seis itens do Enunciado — a coluna “Item do Enunciado” da tabela 1.2 mostra a correspondência.

As **fases** (0 a 21) são as etapas de trabalho que produzem cada entrega.

### 1.1 Ordem de execução

| Fase | O que se faz | Apoio | Entrega gerada |
| --- | --- | --- | --- |
| **0** | Ler os documentos-base e preparar o ambiente | `README.md`, `AGENTS.md` | — |
| **1** | Documento de regras de negócio (RN-01 a RN-16) | Contrato + Enunciado | **Entrega 1** |
| **2** | Documento de requisitos funcionais (26 operações) | Contrato, seção 3 | **Entrega 2** |
| **3** | Documento de requisitos não funcionais + matriz de rastreabilidade | Contrato + Enunciado | **Entrega 3** |
| **4** | Diagrama de casos de uso | Contrato | **Entrega 4** |
| **5** | Descrição textual de cada elipse do diagrama | Contrato | **Entrega 5** |
| **6** | Diagrama de banco de dados (MySQL) | `.opencode/skills/database-operations` | **Entrega 6** |
| **7** | Diagrama de classes (UML) | `.opencode/skills/database-operations` | **Entrega 7** |
| **8** | Script SQL de estrutura (`Entregaveis/Banco de Dados/schema.sql`) e validação | `.opencode/skills/database-operations` | **Entrega 8** |
| **9** | Esqueleto do backend Spring Boot | `.opencode/skills/api-rest` | **Entrega 10** |
| **10** | Entidades JPA, enums, DTOs e repositórios | `.opencode/skills/database-operations` | **Entrega 10** |
| **11** | JWT, BCrypt e rotas protegidas | `.opencode/skills/jwt-security` | **Entrega 10** |
| **12** | Usuários, setores e fornecedores | `.opencode/skills/api-rest` | **Entrega 10** |
| **13** | Insumos e alertas de estoque | `.opencode/skills/api-rest` | **Entrega 10** |
| **14** | Movimentações, transação e concorrência | `.opencode/skills/database-operations` | **Entrega 10** |
| **15** | Validações e erros padronizados | `.opencode/skills/api-rest` | **Entrega 10** |
| **16** | Dados de demonstração (`Entregaveis/Banco de Dados/seed.sql`) e carga no MySQL | `.opencode/skills/database-operations` | **Entrega 9** |
| **17** | Documentos de explicação do projeto (schema, seed e classes) | Código + scripts | **Entrega 12** |
| **18** | Testes, coleção Postman e evidências | Postman + Maven | **Entregas 11 e 13** |
| **19** | Documento técnico e instruções de execução | `README.md` | **Entrega 14** |
| **20** | Ensaio da demonstração e da banca | Enunciado, seção 9 | **Entrega 15** |
| **21** | Conferência final e envio | Este TODO | confere **Entregas 8, 9 e 10** |

### 1.2 As 15 entregas

Marque cada entrega somente quando estiver completa e revisada.

| Entrega | Nome | Fase | Item do Enunciado | Feito? |
| --- | --- | --- | --- | --- |
| **1** | Documento de **regras de negócio (RN-01 a RN-16)** — texto de cada regra, impacto na API e forma de verificação | 1 | adicional | [ ] |
| **2** | Documento de **requisitos funcionais (RF)** — um RF por operação, cobrindo as 26 do contrato | 2 | adicional | [ ] |
| **3** | Documento de **requisitos não funcionais (RNF)** + **matriz de rastreabilidade** RN → RF → endpoint → CT01 a CT22 | 3 | adicional | [ ] |
| **4** | **Diagrama de casos de uso** (UML) — atores, elipses, associações, `<<include>>`/`<<extend>>` | 4 | adicional | [ ] |
| **5** | **Documento de casos de uso** — descrição textual de **cada elipse** (ator, pré/pós-condições, fluxo principal, alternativos, exceções, rastreabilidade) | 5 | adicional | [ ] |
| **6** | Diagrama de **banco de dados (MySQL)** — tabelas, tipos, PKs, FKs, restrições, índices, cardinalidades | 6 | adicional | [X] |
| **7** | Diagrama de **classes (UML)** — classes, enums, atributos, métodos, relacionamentos | 7 | adicional | [ ] |
| **8** | **`schema.sql`** — script de estrutura do banco, executável em banco limpo | 8 | item 2 | [ ] |
| **9** | **`seed.sql`** — dados de demonstração coerentes com as regras implementadas | 16 | item 2 | [ ] |
| **10** | **Código-fonte completo** do Spring Boot (`pom.xml`, camadas, configuração, instruções de execução) | 9–15 | item 1 | [ ] |
| **11** | **Coleção Postman** com as **26 operações** do contrato | 18 | item 3 | [ ] |
| **12** | **Pasta de documentos de explicação** — um arquivo para o `schema.sql`, um para o `seed.sql` e **um arquivo por classe do código**, em subpastas por entidade | 17 | adicional | [ ] |
| **13** | **Relatório curto de testes** (esperado × observado × evidência, CT01 a CT22) | 18 | item 4 | [ ] |
| **14** | **Documento técnico** (ferramentas, versões, banco, configurações sem senhas reais, arquitetura) | 19 | item 5 | [ ] |
| **15** | **Demonstração prática**, com decisões técnicas e regras de negócio | 20 | item 6 | [ ] |

> As entregas 1 a 7 e 12 são **adicionais** de modelagem, documentação e estudo, criadas pela equipe. As entregas 8 a 11 e 13 a 15 correspondem aos seis itens do Enunciado (o item 2 se desdobra em `schema.sql`, `seed.sql` e código). Confirmem com o professor o formato (PDF, imagem ou editável); no repositório, cada entrega fica em `Entregaveis/`, na subpasta da matéria. Todas devem ser produzidas e revisadas pela equipe; o agente apenas orienta.

### 1.3 Definição de pronto

- [ ] As **26 operações** existem e respondem conforme o contrato.
- [ ] Os **22 casos mínimos (CT01 a CT22)** foram executados com evidência.
- [ ] `mvn test` termina com `BUILD SUCCESS`.
- [ ] O banco é criado do zero pelos scripts `schema.sql` + `seed.sql` e a API sobe sem erro.
- [ ] Nenhuma senha, token, chave JWT ou credencial real está versionada.
- [ ] Coleção Postman e relatório de testes atualizados.
- [ ] Entregas 1 a 7 (documentação e modelagem) revisadas, coerentes entre si e no formato combinado com o professor.
- [ ] Entrega 12 cobre **todas** as classes do `backend/`, todas as tabelas do `schema.sql` e todos os blocos do `seed.sql`, sem trecho desatualizado em relação ao código.
- [ ] A equipe consegue explicar o código, os documentos, os diagramas e as regras na banca — inclusive alterar uma linha ao vivo usando a entrega 12 como apoio.

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

### FASE 1 — Documento de regras de negócio (Entrega 1)

Esta fase é **dos autores**. O agente pode explicar e revisar, mas **não produz** o documento.

- [ ] **1.1. Transcrever as RN-01 a RN-16** como estão no Enunciado, com o mesmo identificador e o mesmo sentido.
  - O documento **não** pede uma descrição própria das regras: a regra é a do Enunciado; o documento acrescenta o impacto na API.
- [ ] **1.2. Para cada RN, registrar:** a regra, o impacto na API (endpoint e validação), onde ela é validada no código e como ela é comprovada (caso CT ou teste).
- [ ] **1.3. Usar a seção 4 deste TODO como checklist** e conferir que as 16 regras estão cobertas, sem sobra e sem invenção.
- [ ] **1.4. Revisar:** nenhuma regra órfã (sem impacto ou sem verificação) e nenhuma regra criada pela equipe. → **Entrega 1 aprovada.**

### FASE 2 — Documento de requisitos funcionais (Entrega 2)

Só começar depois do documento de RN aprovado.

- [ ] **2.1. Criar um RF por operação do contrato**, com identificador estável (`RF-01` a `RF-26`), na mesma ordem da seção 3 deste TODO.
- [ ] **2.2. Para cada RF, registrar:** identificador, descrição, ator, método e rota, entrada (campos e obrigatoriedade), saída (JSON de sucesso), status HTTP de sucesso, erros previstos e RN relacionadas.
- [ ] **2.3. Conferir a cobertura:** 26 operações → 26 RF; nenhum RF descrevendo funcionalidade fora do contrato (sem front-end, sem perfis, sem estorno, sem relatório).
- [ ] **2.4. Revisar a nomenclatura:** rotas, campos JSON e status idênticos ao Contrato Técnico. → **Entrega 2 aprovada.**

### FASE 3 — Documento de requisitos não funcionais (Entrega 3)

Só começar depois do documento de RF aprovado.

- [ ] **3.1. Levantar os RNF, cada um verificável:** segurança (JWT e hash BCrypt), desempenho, portabilidade, manutenibilidade, tratamento padronizado de erros e consistência transacional.
- [ ] **3.2. Para cada RNF, registrar:** identificador, descrição, critério de aceitação mensurável e forma de verificação (teste, inspeção ou evidência).
  - Exemplo de critério verificável: “token expira em 3600 s”, “nenhuma resposta contém senha ou hash”, “duas saídas concorrentes não deixam saldo negativo”.
- [ ] **3.3. Montar a matriz de rastreabilidade:** RN → RF → endpoint → CT01 a CT22, sem requisito órfão e sem caso de teste sem requisito.
- [ ] **3.4. Revisar a coerência com as entregas 1 e 2:** os três documentos contam a mesma história. → **Entrega 3 aprovada.**

### FASE 4 — Diagrama de casos de uso (Entrega 4)

Só começar depois dos documentos de RN, RF e RNF aprovados.

- [ ] **4.1. Identificar atores e casos de uso:** visitante (cadastro e login), usuário autenticado (demais operações) e, se a equipe preferir, o front-end como ator externo.
- [ ] **4.2. Desenhar o diagrama UML:** atores, casos de uso (elipses), associações e `<<include>>`/`<<extend>>` quando fizer sentido.
- [ ] **4.3. Rastrear cada elipse** para o RF e o endpoint correspondente; nenhuma elipse cria funcionalidade fora das 26 operações.
- [ ] **4.4. Exportar o diagrama de forma verificável** (imagem, PDF ou fonte do diagrama dentro do repositório). Um link para Lucidchart/Lucidspark não conta como entrega.
- [ ] **4.5. Revisar:** nenhum caso de uso de front-end, tela ou relatório. → **Entrega 4 aprovada.**

### FASE 5 — Documento de casos de uso (Entrega 5)

Só começar depois do diagrama de casos de uso aprovado: a descrição textual é de **cada elipse** que aparece nele.

- [ ] **5.1. Escrever a descrição textual de cada caso de uso:** uma descrição para **cada elipse** do diagrama, do início ao resultado, mostrando **o que o usuário faz** e **como o sistema responde**.

  Cada descrição deve seguir o mesmo modelo:

  | Campo | O que preencher |
  | --- | --- |
  | **Nome** | O mesmo texto da elipse no diagrama (ex.: `Registrar entrada de insumo`) |
  | **Objetivo** | O que se pretende realizar com o caso de uso |
  | **Ator principal** | Quem inicia a ação (visitante ou usuário autenticado) |
  | **Atores secundários** | Outros envolvidos, se houver (ex.: front-end externo) |
  | **Pré-condições** | O que precisa ser verdadeiro antes (ex.: usuário autenticado, insumo ativo) |
  | **Pós-condições** | O que deve estar verdadeiro ao final (ex.: saldo atualizado e histórico gravado) |
  | **Fluxo principal** | Caminho normal, numerado, alternando ação do usuário e resposta do sistema |
  | **Fluxos alternativos** | Caminhos diferentes ainda tratados (ex.: insumo inativo, filtro sem resultado) |
  | **Exceções** | Situações que impedem a conclusão (ex.: estoque insuficiente, data futura) |

  - No **fluxo principal**, cada passo deve ter sujeito explícito: `O usuário informa ...` / `O sistema valida ...` / `O sistema retorna 201 ...`. Não use passos vagos como “o sistema processa”.
  - Nos **fluxos alternativos** e **exceções**, numerar (FA01, FA02, EX01...) e dizer se o fluxo **retorna** ao passo do fluxo principal ou **encerra** o caso de uso.
  - Amarrar cada descrição ao contrato: endpoint(s), RF correspondente, RN aplicadas e status HTTP esperado (200/201/204/400/401/404/409).
- [ ] **5.2. Conferir a cobertura:** toda elipse do diagrama tem descrição textual e nenhuma descrição cria funcionalidade fora das 26 operações.
- [ ] **5.3. Revisar:** os fluxos alternativos e exceções batem com o tratamento de erros do contrato. → **Entrega 5 aprovada.**

### FASE 6 — Diagrama de banco de dados (Entrega 6)

Só começar depois dos casos de uso (diagrama e descrições) aprovados.

- [ ] **6.1. Revisar entidades e relacionamentos:** usuário, setor, fornecedor, insumo e movimentação.
  - O DER e o `schema.sql` de `Entregaveis/Banco de Dados/` são do escopo atual; revise-os junto com os documentos de RN/RF/RNF antes de codificar.
- [ ] **6.2. Definir tabelas, colunas, tipos e restrições:** PKs, FKs, unicidade, nulabilidade, índices e cardinalidades.
- [ ] **6.3. Desenhar o diagrama** (MySQL Workbench ou Draw.io), legível e exportável.
- [ ] **6.4. Revisar:** compatível com os documentos de RN/RF/RNF e de casos de uso e com o futuro script MySQL. → **Entrega 6 aprovada.**

### FASE 7 — Diagrama de classes (Entrega 7)

Só começar depois do diagrama de banco aprovado.

- [ ] **7.1. Identificar classes e enums:** `Usuario`, `Setor`, `Fornecedor`, `Insumo`, `Movimentacao`; enums `TipoMovimentacao` (`ENTRADA`/`SAIDA`) e `UnidadeMedida` (`UN`/`KG`/`L`), se a equipe optar por enum.
- [ ] **7.2. Definir atributos, métodos, relacionamentos e multiplicidades.**
- [ ] **7.3. Desenhar o diagrama** refletindo o contrato e o diagrama de banco; sem front-end.
- [ ] **7.4. Revisar:** compatível com o diagrama de banco e com os DTOs/contrato. → **Entrega 7 aprovada.**

### FASE 8 — Script de estrutura e validação do MySQL (Entrega 8)

Só começar depois dos diagramas de banco e de classes aprovados.

- [ ] **8.1. Escrever o script de estrutura** em `Entregaveis/Banco de Dados/schema.sql` (entrega oficial); se já existir versão em andamento, revise em vez de criar outra.
  - O arquivo de estrutura é só `Entregaveis/Banco de Dados/schema.sql`; os dados de demonstração ficam em `Entregaveis/Banco de Dados/seed.sql`, criado na **FASE 16** (depois do código). Usar `utf8mb4` (o InnoDB já é o engine padrão do MySQL 8, sem obrigação de declarar `ENGINE` no script).
- [ ] **8.2. Modelo mínimo esperado**

| Tabela | Campos mínimos | Observações para MySQL |
| --- | --- | --- |
| `usuarios` | `id`, `nome`, `login`, `senha_hash`, `ativo` | `login` único; `senha_hash VARCHAR(255)`; `ativo TINYINT(1) DEFAULT 1` |
| `setores` | `id`, `nome`, `descricao`, `ativo` | nome único entre ativos (validar no service; MySQL não tem índice parcial simples) |
| `fornecedores` | `id`, `nome`, `cnpj`, `telefone`, `ativo` | `cnpj CHAR(14)` único, somente dígitos; `telefone` opcional |
| `insumos` | `id`, `nome`, `unidade_medida`, `quantidade_atual`, `estoque_minimo`, `fornecedor_id`, `ativo` | quantidades em `DECIMAL(15,3)`; `unidade_medida` em `UN`/`KG`/`L`; FK para fornecedor |
| `movimentacoes` | `id`, `insumo_id`, `tipo`, `quantidade`, `setor_id`, `usuario_id`, `data_movimentacao`, `data_registro`, `lote`, `validade`, `saldo_anterior`, `saldo_atual` | `tipo` `ENTRADA`/`SAIDA`; `setor_id` opcional, `lote` e `validade` NULL permitidos; `data_movimentacao DATE`; `data_registro TIMESTAMP(6)`; FKs sem cascade no histórico |

- [ ] **8.3. Definir tipos e restrições**
  - `DECIMAL(15,3)` para quantidades; `DATE` para `data_movimentacao`; `TIMESTAMP(6)` para `data_registro`; `TINYINT(1)`/`BOOLEAN` para `ativo`.
  - `ENUM('ENTRADA','SAIDA')` ou `VARCHAR(7)` validado para `tipo`; `ENUM('UN','KG','L')` ou `VARCHAR(2)` validado para `unidade_medida`.
  - Unicidade em `login` e `cnpj`; FKs de `insumos.fornecedor_id` e de `movimentacoes.insumo_id`/`usuario_id`/`setor_id`.
  - **Não** usar `ON DELETE CASCADE` em movimentações: o histórico deve ser preservado.
- [ ] **8.4. Executar e validar do zero** em banco limpo (sem dados): `SHOW TABLES;`, `DESCRIBE tabela;`, `SELECT COUNT(*) FROM tabela;` e testar unicidade e FKs.
  - A validação com dados de demonstração (saldos, histórico e consulta de alerta) acontece na **FASE 16**.
- [ ] **8.5. Revisar os artefatos com a equipe:** documentos de RN/RF/RNF e de casos de uso, diagrama de banco, diagrama de classes e script contam a mesma história.
  - Todos entendem as regras, as tabelas e por que não há saldo por lote. → **Entrega 8 aprovada.**

### FASE 9 — Esqueleto do backend Spring Boot

- [ ] **9.1. Criar o projeto:** Spring Initializr ou IntelliJ IDEA; Maven; Java 17+.
  - Artefato `backend`; pacote base `com.senai.bebidas` (ou o que a equipe definir).
- [ ] **9.2. Adicionar as dependências:** Spring Web, Spring Data JPA, Validation, Spring Security, MySQL Driver, Spring Boot Test e a biblioteca JWT escolhida (ex.: JJWT `api`/`impl`/`jackson`).
  - `mvn clean package -DskipTests` com sucesso.
- [ ] **9.3. Configurar o `application.properties`**
  - URL do MySQL com `serverTimezone=America/Sao_Paulo`; usuário e senha por variável de ambiente.
  - `spring.jpa.hibernate.ddl-auto=validate` (o schema vem do script); `spring.jpa.open-in-view=false`; `server.port=8080`.
  - Segredo JWT e expiração de 3600 segundos por configuração.
- [ ] **9.4. Criar a estrutura de pacotes:** `controller`, `service`, `repository`, `model`, `dto`, `exception`, `security`, `config`.
- [ ] **9.5. Criar o `README` do backend:** pré-requisitos, criação do banco, script, variáveis, subir a API e importar o Postman.
- [ ] **9.6. Proteger o repositório:** `.gitignore` para `target/`, `.idea/`, `.env`, credenciais e arquivos locais.

### FASE 10 — Entidades JPA, enums, DTOs e repositórios

- [ ] **10.1. Criar as entidades:** `Usuario`, `Setor`, `Fornecedor`, `Insumo`, `Movimentacao` e os enums `TipoMovimentacao` e `UnidadeMedida`.
- [ ] **10.2. Mapear corretamente**
  - `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column` com os nomes do DER, `@ManyToOne` + `@JoinColumn`, `@Enumerated(EnumType.STRING)`.
  - `BigDecimal` para quantidades; `LocalDate` para data operacional/validade; `OffsetDateTime` ou `Instant` para `dataRegistro`; `ativo` nas entidades de cadastro.
  - O Hibernate deve **validar** o schema, sem criar tabelas por conta própria.
- [ ] **10.3. Criar os DTOs:** requests e responses separados, DTOs de erro padronizados; nunca devolver entidade com senha/hash.
- [ ] **10.4. Criar os repositórios**
  - `UsuarioRepository`: busca por login, unicidade, ativos.
  - `SetorRepository` / `FornecedorRepository`: listagens por ativo e id.
  - `InsumoRepository`: busca parcial por nome, ordenação, filtro de ativo e consulta de alerta.
  - `MovimentacaoRepository`: filtros e ordenação por `dataRegistro` desc + `id` desc.
  - Evitar `findAll()` sem critério.
- [ ] **10.5. Testar a persistência** com `@DataJpaTest` para as consultas principais.

### FASE 11 — Segurança: JWT, BCrypt e rotas protegidas

- [ ] **11.1. Configurar o `PasswordEncoder`** com BCrypt: cadastro grava hash, login compara com `matches`.
- [ ] **11.2. Liberar somente as rotas públicas:** `POST /api/usuarios` e `POST /api/auth/login`. Todo o resto exige `Authorization: Bearer <JWT>`.
- [ ] **11.3. Implementar o login:** buscar por `login`, verificar `ativo`, comparar senha e gerar token.
  - Resposta `{ "token": "...", "tipo": "Bearer", "expiraEm": 3600 }`; credenciais inválidas ou usuário inativo → 401.
- [ ] **11.4. Implementar o serviço de JWT:** segredo em variável de ambiente, assinatura validada, expiração de 3600 segundos, token identificando o usuário.
- [ ] **11.5. Implementar o filtro JWT:** ler `Authorization: Bearer`, validar, carregar o usuário e preencher o `SecurityContext`.
  - Token ausente/inválido/expirado → 401 com `{status, mensagem, timestamp}`; nunca devolver o HTML de login do Spring Security.
- [ ] **11.6. Implementar `GET /api/usuarios/me`:** identidade vem do token, nunca de um ID enviado pelo cliente.
- [ ] **11.7. Testar a segurança:** login correto, login errado, usuário inativo, sem token, token inválido e expirado → **CT03, CT04 e CT05**.

### FASE 12 — Usuários, setores e fornecedores

- [ ] **12.1. Usuários (7 operações)**
  - `POST`: nome, login e senha obrigatórios; senha ≥ 8; login único; 201 sem senha.
  - `GET /me`: usuário do token.
  - `GET`: ativos por padrão, `?ativo=todos` e `?ativo=false`, ordem por id.
  - `GET /{id}`: inclui inativo; 404 se não existir.
  - `PUT /{id}`: nome, login e `ativo`; não altera senha; login único; permite reativação.
  - `DELETE /{id}`: inativação lógica; 204 sem corpo. → **CT01, CT02, CT17 e CT18.**
- [ ] **12.2. Setores (5 operações)**
  - `POST`: nome obrigatório e único entre ativos; `descricao`.
  - `GET`: ativos por padrão; filtros `?ativo=todos`/`?ativo=false`; ordem por id.
  - `GET /{id}`: inclui inativo; 404 se não existir.
  - `PUT /{id}`: nome, descrição e `ativo` obrigatório; nome único entre ativos.
  - `DELETE /{id}`: excluir se sem vínculos; senão inativar; 204.
- [ ] **12.3. Fornecedores (5 operações)**
  - `POST`: nome e CNPJ obrigatórios; CNPJ com 14 dígitos e único; telefone opcional.
  - `GET` e `GET /{id}`: mesmas regras de ativo/inativo dos demais cadastros.
  - `PUT /{id}`: nome, CNPJ, telefone e `ativo`; CNPJ único; fornecedor inativo não pode ser vinculado a novo insumo.
  - `DELETE /{id}`: se associado a insumos, preservar referência e inativar; 204. → **CT06.**
- [ ] **12.4. Revisar os erros:** 400 para validação, 404 para ID inexistente, 409 para duplicidade, com JSON padronizado.

### FASE 13 — Insumos e alertas

- [ ] **13.1. `POST /api/insumos`**
  - Campos: nome, unidade de medida, estoque mínimo e fornecedor.
  - Saldo inicial sempre zero; **não** aceitar `quantidadeAtual` no request; fornecedor deve estar ativo.
  - Unidade `UN` inteira; `KG`/`L` com até 3 casas. → **CT07.**
- [ ] **13.2. Listagem, consulta e atualização**
  - `GET /api/insumos`: busca parcial por nome, ignorando maiúsculas/minúsculas; `?ativo=todos` e `?ativo=false`; ordem alfabética.
  - `GET /api/insumos/{id}`: inclui inativo; 404 se não existir.
  - `PUT /api/insumos/{id}`: não altera saldo; unidade imutável depois de existir movimentação; fornecedor ativo quando alterado; `ativo` obrigatório.
  - `DELETE /api/insumos/{id}`: excluir se sem movimentações; com histórico, inativar e preservar. → **CT08, CT13, CT16 e CT17.**
- [ ] **13.3. Alertas:** `GET /api/alertas/estoque` com somente insumos ativos e `quantidadeAtual <= estoqueMinimo`.
  - Ordem por nome; `situacao` sempre `"CRITICO"`; array `[]` quando vazio. → **CT15.**
- [ ] **13.4. Conferir o modelo:** nenhum saldo por lote; lote e validade são informações da movimentação.

### FASE 14 — Movimentações, transação e concorrência

Fase de maior peso nas regras de negócio. Não pule os testes de concorrência.

- [ ] **14.1. `POST /api/movimentacoes`**
  - Endpoint único para `ENTRADA` e `SAIDA`; não criar `PUT` nem `DELETE`.
  - `usuarioId` vem do JWT; não aceitar do corpo. → 201 com o JSON do contrato.
- [ ] **14.2. Validar a entrada**
  - `tipo` obrigatório (`ENTRADA`/`SAIDA`); `quantidade` estritamente positiva e compatível com a unidade.
  - `dataMovimentacao` não pode ser futura.
  - `SAIDA` exige setor ativo e não recebe lote/validade.
  - `ENTRADA` pode omitir lote/validade; se validade vier, não pode ser anterior à data operacional.
  - Insumo inativo → 409. → **CT12, CT13 e CT21.**
- [ ] **14.3. Garantir a transação**
  - Buscar o insumo com lock pessimista **ou** fazer atualização atômica condicional.
  - Calcular `saldoAnterior` e `saldoAtual`, atualizar o saldo e gravar a movimentação na mesma transação.
  - `dataRegistro` gerada pelo servidor no momento do lançamento; falha → rollback integral. → **CT09, CT10, CT11 e CT19.**
- [ ] **14.4. Testar concorrência:** duas saídas simultâneas com saldo insuficiente para as duas.
  - Uma falha com 409; saldo nunca negativo e sem histórico parcial. → **CT20.**
- [ ] **14.5. Histórico**
  - `GET /api/movimentacoes`: filtros `insumoId`, `setorId`, `usuarioId`, `tipo`, `dataInicio` e `dataFim`.
  - Ordenação `dataRegistro` desc e, no empate, `id` desc; `GET /{id}` com 404 se não existir. → **CT14 e CT22.**
- [ ] **14.6. Documentar a decisão:** explicar no documento técnico se usaram lock pessimista, atualização atômica ou lock otimista.

### FASE 15 — Validações e tratamento padronizado de erros

- [ ] **15.1. Criar o `@RestControllerAdvice`** para centralizar validação, autenticação, recurso não encontrado, regra de negócio e erro inesperado.
- [ ] **15.2. Mapear os status**
  - **400:** campo obrigatório, quantidade inválida, data futura, `ativo` inválido, `dataInicio > dataFim`.
  - **401:** credenciais inválidas, token ausente/inválido/expirado.
  - **404:** recurso não encontrado.
  - **409:** duplicidade, estoque insuficiente, insumo inativo, vínculo que impede exclusão.
  - **500:** falha inesperada.
- [ ] **15.3. Padronizar o JSON:** `{ "status": ..., "mensagem": "...", "timestamp": "..." }`, com timestamp real da falha; nunca devolver stack trace, SQL ou detalhes internos.
- [ ] **15.4. Testar os erros:** cada status com pelo menos um caso positivo e um negativo. → **Entrega 10 fechada** (código completo).

### FASE 16 — Dados de demonstração e carga no MySQL (Entrega 9)

Só começar **depois do código** (fases 9 a 15) e **antes da coleção Postman** (fase 18): a carga precisa refletir as regras já implementadas e serve de base para os testes.

- [ ] **16.1. Escrever o `Entregaveis/Banco de Dados/seed.sql`** com os dados de demonstração.
  - Usuários com senha em **hash BCrypt**; setores, fornecedores e insumos ativos.
  - Insumos começam com saldo zero; criar entradas e saídas para gerar saldo e histórico.
  - Pelo menos um insumo abaixo ou no estoque mínimo para demonstrar o alerta. Sem credenciais reais.
- [ ] **16.2. Carregar e validar** em banco limpo, na ordem `schema.sql` → `seed.sql`: conferir contagens, saldos, histórico e a consulta de alerta:
  `SELECT ... FROM insumos WHERE ativo = 1 AND quantidade_atual <= estoque_minimo;`
- [ ] **16.3. Conferir a coerência com a API:** `GET /api/alertas/estoque`, `GET /api/movimentacoes` e login com um usuário da carga respondem como o esperado. → **Entrega 9 aprovada.**

### FASE 17 — Documentos de explicação do projeto (Entrega 12)

Esta entrega existe para a **banca**: cada integrante precisa entender cada linha e conseguir alterar o código ao vivo, com segurança. Só começar com `schema.sql`, `seed.sql` e o código já aprovados (fases 8, 15 e 16).

- [ ] **17.1. Definir a pasta e a estrutura.** Sugestão (o caminho exato é decisão da equipe; se mudar, o mapa do plugin `.opencode/entregaveis.json` precisa ser atualizado):

  ```
  Entregaveis/Programação de Aplicativos/Explicacoes/
  ├── README.md              (índice geral e ordem de leitura)
  ├── schema.sql.md          (um arquivo explicando o script de estrutura)
  ├── seed.sql.md            (um arquivo explicando o script de carga)
  └── codigo/
      ├── Usuarios/          Usuario.md, UsuarioRepository.md, UsuarioService.md, UsuarioController.md, UsuarioRequest.md, UsuarioResponse.md
      ├── Setores/           (mesma divisão por classe)
      ├── Fornecedores/      (mesma divisão por classe)
      ├── Insumos/           (mesma divisão por classe)
      ├── Movimentacoes/     (mesma divisão por classe)
      ├── Alertas/           (mesma divisão por classe)
      ├── Seguranca/         JwtService.md, JwtAuthenticationFilter.md, SecurityConfig.md, AuthController.md, AuthService.md
      └── Comum/             GlobalExceptionHandler.md, TipoMovimentacao.md, UnidadeMedida.md, demais classes de apoio
  ```

  - **Um arquivo `.md` por classe**, na subpasta da entidade a que ela pertence. Enums, DTOs, exceções e configurações também contam como classe e precisam do seu arquivo.
- [ ] **17.2. Escrever `schema.sql.md`** — o script de estrutura explicado parte por parte.
  - Uma seção por tabela: para que serve, quem a usa na API e quais RN ela sustenta.
  - Uma explicação por coluna: significado, tipo escolhido e por quê (`DECIMAL(15,3)` para quantidade, `TIMESTAMP(6)` para `data_registro`, `TINYINT(1)` para `ativo`, `CHAR(14)` para CNPJ).
  - Uma explicação por restrição: PK, FK, `UNIQUE`, `NOT NULL`, `DEFAULT` — o que acontece na prática se cada uma for violada.
  - Explicar explicitamente **por que não há `ON DELETE CASCADE`** no histórico e **por que não há tabela de lote** (RN-06 e RN-08).
- [ ] **17.3. Escrever `seed.sql.md`** — a carga explicada parte por parte.
  - Uma seção por bloco de `INSERT`: quais registros entram e qual cenário cada um habilita (login válido, listagem com inativos, alerta crítico, histórico com entrada e saída).
  - Explicar por que a ordem dos `INSERT` importa (FKs) e por que os saldos do seed batem com as movimentações inseridas.
  - Explicar o hash BCrypt dos usuários: o que é, por que não há senha em texto e como descobrir a senha de demonstração sem gravar credencial real no repositório.
- [ ] **17.4. Escrever um arquivo por classe do `backend/`**, sempre com a mesma estrutura:

  | Seção | O que responder |
  | --- | --- |
  | **Onde fica** | Caminho do arquivo real, pacote e camada (Controller, Service, Repository, Model, DTO, Exception, Security, Config) |
  | **Para que serve** | O papel da classe no fluxo de uma requisição, do HTTP até o banco |
  | **Linha a linha** | Cada bloco de código explicado: anotação, atributo, método, consulta, validação e retorno |
  | **Quem depende dela** | Classes que chamam e são chamadas; endpoints, RN e CT afetados |
  | **Se mudar, o que quebra** | Efeito de alterar nome de campo, tipo, status HTTP ou consulta |
  | **Como alterar ao vivo** | Uma ou duas alterações pequenas e seguras para fazer na banca (ex.: mudar a ordem de uma consulta, ajustar uma mensagem de erro, trocar um status), com o efeito esperado e como reverter |

  - Nas anotações, explicar o efeito prático: `@Entity`/`@Table` (mapeamento), `@Column` (nome e nulabilidade), `@ManyToOne`/`@JoinColumn` (FK), `@Enumerated(EnumType.STRING)` (grava o nome do enum), `@Transactional` (atomicidade da RN-04), `@Valid` (RN de entrada), locks (`@Lock`/`@Version`).
  - Nas consultas de repositório, explicar o SQL que o Spring Data gera (ou o `@Query` escrito) e a ordenação exigida pelo contrato.
  - Nada de copiar e colar o código sem explicação: o texto precisa dizer **por que** cada linha existe.
- [ ] **17.5. Escrever o `README.md` da pasta:** índice com links para todos os arquivos, ordem de leitura sugerida (schema → seed → model → repository → service → controller → security → exception) e um mapa “entidade → tabela → classes”.
- [ ] **17.6. Conferir a cobertura:** toda tabela do `schema.sql`, todo bloco do `seed.sql` e **toda classe do `backend/`** tem seu arquivo; nenhum documento descreve código que já mudou.
- [ ] **17.7. Testar a explicação:** cada integrante escolhe uma classe e tenta explicá-la e alterá-la usando apenas o documento. O que travar volta para o texto. → **Entrega 12 aprovada.**

### FASE 18 — Testes e coleção Postman (Entregas 11 e 13)

- [ ] **18.1. Montar a coleção Postman:** uma pasta por grupo (usuários/auth, setores, fornecedores, insumos, movimentações, alertas), 26 requisições no total.
  - Ambiente com `baseUrl` e `token`; no login, salvar o token na variável; nas rotas protegidas, usar `Bearer {{token}}`.
- [ ] **18.2. Executar os casos CT01 a CT22**, registrando resultado esperado, observado e evidência.
  - Incluir os testes de erro: sem token, token inválido, estoque insuficiente, data futura, duplicidade e insumo inativo.
- [ ] **18.3. Testes automatizados:** service e controller, `@DataJpaTest` para repositórios, `@SpringBootTest` + `MockMvc` para endpoints; `mvn test` com `BUILD SUCCESS`.
- [ ] **18.4. Testar o banco do zero:** apagar o schema de teste, rodar `schema.sql` + `seed.sql`, subir a API e repetir os fluxos principais.
- [ ] **18.5. Exportar as evidências:** coleção Postman e prints/logs do relatório → **Entregas 11 e 13 prontas.**

### FASE 19 — Documento técnico e instruções (Entrega 14)

- [ ] **19.1. Escrever o documento técnico**
  - Ferramentas e versões (Java, Spring Boot, Maven, MySQL, Postman, IDE), banco e schema, como criar/carregar.
  - Configurações necessárias, sem senhas reais; arquitetura resumida (Controller → Service → Repository, JWT, tratamento de erros).
  - Decisões: inativação lógica, transação/concorrência, ordenação, data operacional × data de registro.
  - **Não confundir** com os documentos de RN, RF e RNF (entregas 1 a 3): lá ficam requisitos e regras; aqui ficam ferramentas, banco, configuração e arquitetura.
- [ ] **19.2. Revisar o `README` do backend:** pré-requisitos, banco, script, variáveis, `mvn spring-boot:run`, Postman e `mvn test`.
- [ ] **19.3. Revisar segurança:** nenhuma senha, token, chave JWT ou credencial real no repositório; `.env.example` apenas com nomes de variáveis. → **Entrega 14 aprovada.**

### FASE 20 — Demonstração e banca (Entrega 15)

Use o checklist do Enunciado, seção 9.

- [ ] **20.1.** Cadastro de usuário e login retornando JWT.
- [ ] **20.2.** Bloqueio de endpoint sem autenticação.
- [ ] **20.3.** Cadastros e consultas de setores, fornecedores e insumos.
- [ ] **20.4.** Pesquisa ordenada e consulta de registros inativos.
- [ ] **20.5.** Entrada, saída e conferência do saldo após cada operação.
- [ ] **20.6.** Rejeição de retirada superior ao estoque e de entrada inválida.
- [ ] **20.7.** Consulta do histórico e dos alertas de estoque.
- [ ] **20.8.** Preservação do histórico após inativação e teste de consistência.
- [ ] **20.9.** Ensaiar a explicação de cada decisão técnica.
- [ ] **20.10.** Simular uma alteração ao vivo no código usando a entrega 12 como apoio e reverter sem erro.
- [ ] **20.11.** Dividir quem apresenta cada parte e cronometrar a demonstração.

### FASE 21 — Conferência final e entrega

- [ ] **21.1.** Conferir as 15 entregas da seção 1.2.
- [ ] **21.2.** Conferir os documentos de RN, RF e RNF, o diagrama **e** o documento de casos de uso, o diagrama de banco e o diagrama de classes.
- [ ] **21.3.** Conferir as 26 operações e os 22 casos CT01 a CT22.
- [ ] **21.4.** Rodar `mvn clean test` e subir a API em uma máquina limpa.
- [ ] **21.5.** Conferir `git status`, remover `target/` e confirmar que não há segredos.
- [ ] **21.6.** Confirmar com o professor o formato final de envio.
- [ ] **21.7.** Fazer o commit/tag final e avisar a equipe.

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
13. **Se eu pedir para mudar uma linha agora, vocês conseguem?** Sim: a entrega 12 explica cada classe, o efeito da alteração e como reverter; é só abrir o arquivo da classe na pasta da entidade.

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
- `Entregaveis/` — pasta oficial de entrega, separada por matéria. O `schema.sql` e o DER de `Entregaveis/Banco de Dados/` são do escopo atual; só altere com autorização dos autores.
- Se a equipe produzir um novo DER/SQL, ele passa a ser a referência do banco. O contrato continua sendo a fonte de verdade da API.

> Em caso de dúvida, vale o **Contrato Técnico** para rotas, campos, JSON, status, filtros e ordenação; e o **Enunciado** para regras de negócio, tecnologias e escopo. Se os dois não resolverem, perguntem ao professor.
