# TODO — Roteiro de Execução do Projeto Integrador (API REST + MySQL)

- **Projeto:** Sistema de Almoxarifado para Indústria de Bebidas
- **Curso:** Técnico em Desenvolvimento de Sistemas — SENAI / FIEB (Módulo 2)
- **Arquitetura:** API REST em Java + Spring Boot + MySQL; front-end web fornecido por outra empresa
- **Banco:** MySQL (8.x recomendado; registrar a versão usada no documento técnico)
- **Fontes de verdade:** `Diversos/Enunciado_Projeto_Integrador_Industria_Bebidas.docx` e `Diversos/Contrato_Tecnico_API_Industria_Bebidas.docx`
- **Finalidade:** roteiro operacional para a equipe executar o projeto com apoio pontual do agente.

> **Este TODO substitui o roteiro antigo.** O PDF `*.pdf.OLD`, o `Diversos/TODO.docx` e o material de `Entregaveis/` pertencem ao escopo anterior (desktop) e são **históricos**. O contrato atual é a fonte de verdade da API.

> **Regra de ouro:** o front-end externo já espera as rotas, os campos JSON e os códigos HTTP do contrato. Não altere o contrato para “facilitar” a implementação.

---

## 1. O que precisa ser entregue

Marque cada entrega somente quando ela estiver completa e revisada.

### 1.1 Entregáveis do Enunciado (6)

- [ ] **Entrega 1 — Código-fonte completo do projeto Spring Boot**, com `pom.xml`, código organizado em camadas, arquivo de configuração e instruções reproduzíveis de execução.
- [ ] **Entrega 2 — API integrada ao MySQL**, preparada para execução local, com script de criação/carga do banco e dados de demonstração.
- [ ] **Entrega 3 — Coleção Postman (ou equivalente)** com as requisições das **26 operações** do contrato.
- [ ] **Entrega 4 — Relatório curto de testes**, com resultado esperado, resultado observado e evidência para os casos **CT01 a CT22**.
- [ ] **Entrega 5 — Documento técnico**, com ferramentas, versões, banco utilizado, configurações necessárias (sem senhas reais), arquitetura resumida e decisões técnicas.
- [ ] **Entrega 6 — Demonstração prática**, com explicação das decisões técnicas e das principais regras de negócio.

### 1.2 Entregáveis adicionais de modelagem e requisitos (4)

> **Ordem obrigatória:** primeiro os documentos, depois os diagramas, e só então o código. O agente pode orientar e revisar, mas a produção é dos autores.

- [ ] **Entrega 7 — Documento de regras de negócio, requisitos funcionais e não funcionais**, contendo:
  - **Requisitos funcionais (RF)** rastreáveis às **26 operações** do contrato;
  - **Requisitos não funcionais (RNF)** aplicáveis à API (segurança, desempenho, portabilidade, manutenibilidade, tratamento de erros etc.);
  - **Regras de negócio (RN-01 a RN-16)** com descrição, impacto e forma de verificação;
  - **Matriz de rastreabilidade** ligando RF → endpoint → caso de teste (CT01 a CT22).
- [ ] **Entrega 8 — Documento e diagrama de casos de uso**, com atores, casos de uso, pré-condições, fluxo principal, fluxos alternativos/exceções e rastreabilidade para os RF e endpoints.
- [ ] **Entrega 9 — Diagrama de banco de dados (MySQL)**, com tabelas, colunas, tipos de dados, chaves primárias e estrangeiras, restrições de unicidade, nulabilidade, índices e cardinalidades. Deve representar exatamente o schema criado pelo script SQL.
- [ ] **Entrega 10 — Diagrama de classes (UML)**, com as classes do domínio (`Usuario`, `Setor`, `Fornecedor`, `Insumo`, `Movimentacao`), enums, atributos, métodos e relacionamentos com multiplicidades. Deve ser compatível com o diagrama de banco e com o contrato da API.

> As entregas 7 a 10 são **adicionais** às 6 do Enunciado. Confirmem com o professor o formato (PDF, imagem ou documento editável) e o local de entrega. Elas devem ser produzidas e revisadas pela equipe; o agente apenas orienta.

### Definição de pronto

O projeto só está pronto para entrega quando:

- [ ] As **26 operações** existem e respondem conforme o contrato.
- [ ] Os **22 casos mínimos (CT01 a CT22)** foram executados com evidência.
- [ ] `mvn test` termina com sucesso.
- [ ] O banco é criado do zero pelo script e a API sobe sem erro.
- [ ] Nenhuma senha, token, chave JWT ou credencial real está versionada.
- [ ] A coleção Postman e o relatório de testes estão atualizados.
- [ ] O **documento de RN/RF/RNF** está revisado, com rastreabilidade para as 26 operações e os CT01 a CT22.
- [ ] O **documento e o diagrama de casos de uso** cobrem as 26 operações e não incluem funcionalidade fora do contrato.
- [ ] O **diagrama de banco de dados** representa o schema MySQL criado pelo script.
- [ ] O **diagrama de classes** está compatível com o diagrama de banco e com os DTOs/contrato.
- [ ] As entregas 7 a 10 estão no formato combinado com o professor.
- [ ] A equipe consegue explicar o código, os documentos, os diagramas e as regras na banca.

---

## 2. Quadro resumo das fases

| Fase | Ação principal | Onde fazer | Apoio | Feito? |
| --- | --- | --- | --- | --- |
| **0** | Ler os documentos e preparar o ambiente | `Diversos/` + máquina local | `README.md`, `AGENTS.md` | [ ] |
| **1** | Documentos (RN/RF/RNF e casos de uso), diagramas (banco e classes) e script MySQL | Editor de texto + Draw.io/Lucidchart + MySQL Workbench/CLI | `skills/database-operations/SKILL.md` | [ ] |
| **2** | Criar o projeto Spring Boot | Spring Initializr / IntelliJ IDEA | `skills/api-rest/SKILL.md` | [ ] |
| **3** | Criar entidades JPA, enums, DTOs e repositórios | IntelliJ IDEA | `skills/database-operations/SKILL.md` | [ ] |
| **4** | Implementar JWT, BCrypt e proteção das rotas | IntelliJ IDEA | `skills/jwt-security/SKILL.md` | [ ] |
| **5** | Implementar usuários, setores e fornecedores | IntelliJ IDEA | `skills/api-rest/SKILL.md` | [ ] |
| **6** | Implementar insumos e alertas de estoque | IntelliJ IDEA | `skills/api-rest/SKILL.md` | [ ] |
| **7** | Implementar movimentações, transação e concorrência | IntelliJ IDEA | `skills/database-operations/SKILL.md` | [ ] |
| **8** | Padronizar validações e erros | IntelliJ IDEA | `skills/api-rest/SKILL.md` | [ ] |
| **9** | Executar testes e montar a coleção Postman | Postman + Maven | Contrato, seção 7 | [ ] |
| **10** | Escrever o documento técnico e as instruções | Editor de texto / `README` do backend | `README.md` | [ ] |
| **11** | Ensaiar a demonstração e a banca | Equipe | Enunciado, seção 9 | [ ] |
| **12** | Conferir e entregar | Equipe | Este TODO | [ ] |

> As entregas 7 a 10 (documentos, casos de uso e diagramas) são produzidas na **Fase 1**, nesta ordem: RN/RF/RNF → casos de uso → banco → classes. O **documento técnico** da Entrega 5 é outro documento, produzido na **Fase 10**.

---

## 3. Controle das 26 operações

Marque **Código** quando o endpoint estiver implementado e **Postman** quando estiver testado com evidência.

### Usuários e autenticação (7)

| # | Método e rota | Código | Postman |
| --- | --- | --- | --- |
| 1 | `POST /api/usuarios` | [ ] | [ ] |
| 2 | `POST /api/auth/login` | [ ] | [ ] |
| 3 | `GET /api/usuarios/me` | [ ] | [ ] |
| 4 | `GET /api/usuarios` | [ ] | [ ] |
| 5 | `GET /api/usuarios/{id}` | [ ] | [ ] |
| 6 | `PUT /api/usuarios/{id}` | [ ] | [ ] |
| 7 | `DELETE /api/usuarios/{id}` | [ ] | [ ] |

### Setores (5)

| # | Método e rota | Código | Postman |
| --- | --- | --- | --- |
| 8 | `POST /api/setores` | [ ] | [ ] |
| 9 | `GET /api/setores` | [ ] | [ ] |
| 10 | `GET /api/setores/{id}` | [ ] | [ ] |
| 11 | `PUT /api/setores/{id}` | [ ] | [ ] |
| 12 | `DELETE /api/setores/{id}` | [ ] | [ ] |

### Fornecedores (5)

| # | Método e rota | Código | Postman |
| --- | --- | --- | --- |
| 13 | `POST /api/fornecedores` | [ ] | [ ] |
| 14 | `GET /api/fornecedores` | [ ] | [ ] |
| 15 | `GET /api/fornecedores/{id}` | [ ] | [ ] |
| 16 | `PUT /api/fornecedores/{id}` | [ ] | [ ] |
| 17 | `DELETE /api/fornecedores/{id}` | [ ] | [ ] |

### Insumos (5)

| # | Método e rota | Código | Postman |
| --- | --- | --- | --- |
| 18 | `POST /api/insumos` | [ ] | [ ] |
| 19 | `GET /api/insumos` | [ ] | [ ] |
| 20 | `GET /api/insumos/{id}` | [ ] | [ ] |
| 21 | `PUT /api/insumos/{id}` | [ ] | [ ] |
| 22 | `DELETE /api/insumos/{id}` | [ ] | [ ] |

### Movimentações (3)

| # | Método e rota | Código | Postman |
| --- | --- | --- | --- |
| 23 | `POST /api/movimentacoes` | [ ] | [ ] |
| 24 | `GET /api/movimentacoes` | [ ] | [ ] |
| 25 | `GET /api/movimentacoes/{id}` | [ ] | [ ] |

### Alertas (1)

| # | Método e rota | Código | Postman |
| --- | --- | --- | --- |
| 26 | `GET /api/alertas/estoque` | [ ] | [ ] |

---

## 4. Casos mínimos de aceitação (CT01 a CT22)

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

### 4.1. Controle das regras de negócio (RN-01 a RN-16)

Marque cada regra quando ela estiver implementada **e** comprovada por teste ou evidência.

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

## 5. Passo a passo detalhado

### FASE 0 — Preparação

- [ ] **0.1. Ler os dois documentos-base**
  - `Diversos/Enunciado_Projeto_Integrador_Industria_Bebidas.docx`
  - `Diversos/Contrato_Tecnico_API_Industria_Bebidas.docx`
  - **Resultado esperado:** a equipe entende que o projeto é só backend e que o contrato tem 26 operações.

- [ ] **0.2. Conferir o ambiente**
  - Java 17 ou superior, Maven, MySQL, Postman (ou equivalente), IDE Java e Git.
  - **Resultado esperado:** `java -version`, `mvn -version` e `mysql --version` funcionam.

- [ ] **0.3. Criar o banco local**
  - Criar o schema no MySQL e um usuário de aplicação.
  - Não usar a senha real do MySQL no Git; usar variável de ambiente.
  - **Resultado esperado:** conexão local funcionando.

- [ ] **0.4. Combinar o formato de entrega com o professor**
  - Repositório Git, ZIP ou outro formato; onde entregar a coleção Postman e os PDFs.
  - **Resultado esperado:** formato acordado e registrado no grupo.

- [ ] **0.5. Revisar o fluxo de Git**
  - Branch por assunto, Pull Request e revisão.
  - Commits com autoria de quem fez a alteração (`docs/colaboradores.md`).
  - **Resultado esperado:** todos sabem como contribuir sem mexer direto na `main`.

---

### FASE 1 — Documentos, diagramas e banco MySQL

Esta fase é **dos autores**. O agente pode explicar, tirar dúvidas e revisar, mas **não produz** os documentos nem os diagramas.

A ordem é obrigatória:

1. Documento de regras de negócio, requisitos funcionais e não funcionais (Entrega 7).
2. Documento e diagrama de casos de uso (Entrega 8).
3. Diagrama de banco de dados (Entrega 9).
4. Diagrama de classes (Entrega 10).
5. Script SQL do MySQL.
6. Só depois começa o código Java/Spring Boot (Fase 2).

- [ ] **1.1. Revisar os objetos do contrato**
  - Usuário, setor, fornecedor, insumo, movimentação e alerta.
  - Conferir os campos de cada JSON no contrato, seção 3.

- [ ] **1.2. Levantar os requisitos funcionais (RF)**
  - Derivar um RF para cada uma das 26 operações do contrato.
  - Para cada RF, registrar: identificador, descrição, ator, endpoint, entrada, saída e status HTTP.
  - **Resultado esperado:** todos os 26 endpoints cobertos por RF.

- [ ] **1.3. Levantar os requisitos não funcionais (RNF)**
  - Registrar exigências de segurança (JWT e hash de senha), desempenho, portabilidade, manutenibilidade, tratamento de erros e consistência transacional.
  - Cada RNF deve ser verificável: a equipe precisa saber como comprovar que ele foi atendido.
  - **Resultado esperado:** RNFs coerentes com o Enunciado e o Contrato.

- [ ] **1.4. Documentar as regras de negócio (RN-01 a RN-16)**
  - Descrever cada RN, seu impacto na API e a forma de verificação (teste ou evidência).
  - Usar a tabela da seção 4.1 como base de conferência.
  - **Resultado esperado:** as 16 regras documentadas e rastreáveis.

- [ ] **1.5. Montar o documento de RN/RF/RNF (Entrega 7)**
  - Estrutura sugerida: introdução, escopo, atores, RF, RNF, RN e matriz de rastreabilidade.
  - A matriz deve ligar RF → endpoint → caso de teste (CT01 a CT22).
  - **Resultado esperado:** documento revisado pela equipe, sem requisito inventado.

- [ ] **1.6. Produzir o documento e o diagrama de casos de uso (Entrega 8)**
  - Atores: visitante (cadastro e login), usuário autenticado (demais operações) e, se a equipe preferir, o front-end como ator externo.
  - Casos de uso principais: autenticar, gerenciar usuários, setores, fornecedores e insumos, registrar movimentação, consultar histórico e consultar alertas.
  - Para cada caso: objetivo, ator, pré-condições, fluxo principal, fluxos alternativos/exceções e resposta esperada.
  - Diagrama UML com atores, casos de uso, associações e `<<include>>`/`<<extend>>` quando fizer sentido.
  - Rastrear cada caso de uso para os RF e endpoints correspondentes.
  - **Resultado esperado:** documento e diagrama revisados, sem funcionalidade fora do contrato.

- [ ] **1.7. Desenhar o diagrama de banco de dados (Entrega 9)**
  - Tabelas, colunas, tipos, chaves primárias e estrangeiras, restrições de unicidade, nulabilidade, índices e cardinalidades.
  - Não reaproveitar o DER antigo de `Entregaveis/` sem revisar: ele é do escopo desktop e usa estoque por lote.
  - **Resultado esperado:** diagrama compatível com o documento de RN/RF/RNF e com o script MySQL.

- [ ] **1.8. Desenhar o diagrama de classes (Entrega 10)**
  - Classes do domínio: `Usuario`, `Setor`, `Fornecedor`, `Insumo` e `Movimentacao`.
  - Enums `TipoMovimentacao` (`ENTRADA`/`SAIDA`) e `UnidadeMedida` (`UN`/`KG`/`L`), se a equipe optar por enum.
  - Atributos, métodos relevantes, relacionamentos e multiplicidades.
  - O diagrama deve refletir o contrato e o diagrama de banco; não incluir front-end.
  - **Resultado esperado:** diagrama revisado e compatível com o banco.

- [ ] **1.9. Escrever o script SQL no MySQL**
  - Criar um script **novo**, em arquivo separado; não sobrescrever o `BancodoProjeto.sql` antigo sem autorização dos autores.
  - Sugestão de organização: `db/schema.sql` (estrutura) e `db/seed.sql` (dados de demonstração).
  - Usar `InnoDB` e `utf8mb4`.

- [ ] **1.10. Modelo mínimo esperado**

| Tabela | Campos mínimos | Observações para MySQL |
| --- | --- | --- |
| `usuarios` | `id`, `nome`, `login`, `senha_hash`, `ativo` | `login` único; `senha_hash VARCHAR(255)`; `ativo TINYINT(1) DEFAULT 1` |
| `setores` | `id`, `nome`, `descricao`, `ativo` | nome único entre ativos (validar no service; MySQL não tem índice parcial simples) |
| `fornecedores` | `id`, `nome`, `cnpj`, `telefone`, `ativo` | `cnpj CHAR(14)` único, somente dígitos; `telefone` opcional |
| `insumos` | `id`, `nome`, `unidade_medida`, `quantidade_atual`, `estoque_minimo`, `fornecedor_id`, `ativo` | `quantidade_atual` e `estoque_minimo` em `DECIMAL(15,3)`; `unidade_medida` em `UN`/`KG`/`L`; FK para fornecedor |
| `movimentacoes` | `id`, `insumo_id`, `tipo`, `quantidade`, `setor_id`, `usuario_id`, `data_movimentacao`, `data_registro`, `lote`, `validade`, `saldo_anterior`, `saldo_atual` | `tipo` `ENTRADA`/`SAIDA`; `setor_id` NULL permitido; `lote`/`validade` NULL permitidos; quantidades em `DECIMAL(15,3)`; `data_movimentacao DATE`; `data_registro DATETIME(6)` ou `TIMESTAMP`; FKs sem cascade no histórico |

- [ ] **1.11. Definir tipos e restrições**
  - `DECIMAL(15,3)` para quantidades (KG/L com até 3 casas).
  - `DATE` para `data_movimentacao`; `DATETIME(6)` ou `TIMESTAMP` para `data_registro`.
  - `TINYINT(1)`/`BOOLEAN` para `ativo`.
  - `ENUM('ENTRADA','SAIDA')` ou `VARCHAR(7)` com validação para `tipo`.
  - `ENUM('UN','KG','L')` ou `VARCHAR(2)` com validação para `unidade_medida`.
  - `login` único; `cnpj` único.
  - FKs de `insumos.fornecedor_id`, `movimentacoes.insumo_id`, `movimentacoes.usuario_id` e `movimentacoes.setor_id`.
  - **Não** usar `ON DELETE CASCADE` em movimentações: o histórico deve ser preservado.

- [ ] **1.12. Preparar os dados de demonstração**
  - Usuários com senha em **hash BCrypt** (nunca senha em texto puro).
  - Setores, fornecedores e insumos ativos.
  - Insumos começam com saldo zero; criar entradas e saídas para gerar saldo e histórico.
  - Incluir pelo menos um insumo abaixo ou no estoque mínimo para demonstrar o alerta.
  - **Resultado esperado:** script reproduzível, sem credenciais reais.

- [ ] **1.13. Executar e validar o script**
  - Rodar o script do zero em um banco limpo.
  - Conferir com `SHOW TABLES;`, `DESCRIBE tabela;` e `SELECT COUNT(*) FROM tabela;`.
  - Testar as restrições de unicidade e as FKs.
  - Testar a consulta de alerta:
    `SELECT ... FROM insumos WHERE ativo = 1 AND quantidade_atual <= estoque_minimo;`
  - **Resultado esperado:** banco criado sem erro e dados de demonstração coerentes.

- [ ] **1.14. Revisar os artefatos com a equipe**
  - Todos entendem as regras, os casos de uso, as tabelas, os relacionamentos e por que não há saldo por lote.
  - Conferir se o documento de RN/RF/RNF, os casos de uso, o diagrama de banco, o diagrama de classes e o script contam a mesma história.
  - **Resultado esperado:** entregas 7, 8, 9 e 10, diagramas e script aprovados antes de codar.

---

### FASE 2 — Esqueleto do backend Spring Boot

- [ ] **2.1. Criar o projeto**
  - Spring Initializr ou IntelliJ IDEA; Maven; Java 17+.
  - Nome/artefato: `backend`; pacote base: `com.senai.bebidas` (ou o que a equipe definir).
  - **Resultado esperado:** projeto importado e compilando.

- [ ] **2.2. Adicionar as dependências**
  - `Spring Web`, `Spring Data JPA`, `Validation`, `Spring Security`, `MySQL Driver`, `Spring Boot Test`.
  - Biblioteca JWT escolhida pela equipe (ex.: JJWT `api`, `impl`, `jackson`).
  - **Resultado esperado:** `pom.xml` coerente e `mvn clean package -DskipTests` com sucesso.

- [ ] **2.3. Configurar o `application.properties`**
  - URL do MySQL com `serverTimezone=America/Sao_Paulo`.
  - Usuário e senha por variável de ambiente.
  - `spring.jpa.hibernate.ddl-auto=validate` (o schema vem do script).
  - `spring.jpa.open-in-view=false`.
  - `server.port=8080`.
  - Segredo JWT e expiração de 3600 segundos por configuração.
  - **Resultado esperado:** aplicação sobe e conecta no MySQL.

- [ ] **2.4. Criar a estrutura de pacotes**
  - `controller`, `service`, `repository`, `model`, `dto`, `exception`, `security`, `config`.
  - **Resultado esperado:** organização em camadas visível no projeto.

- [ ] **2.5. Criar o `README` do backend**
  - Pré-requisitos, como criar o banco, rodar o script, configurar variáveis, subir a API e importar o Postman.
  - **Resultado esperado:** outra pessoa consegue executar o projeto seguindo o README.

- [ ] **2.6. Proteger o repositório**
  - `.gitignore` para `target/`, `.idea/`, `.env`, credenciais e arquivos locais.
  - **Resultado esperado:** nenhum segredo no Git.

---

### FASE 3 — Entidades JPA, enums, DTOs e repositórios

- [ ] **3.1. Criar as entidades**
  - `Usuario`, `Setor`, `Fornecedor`, `Insumo`, `Movimentacao`.
  - Enums `TipoMovimentacao` (`ENTRADA`, `SAIDA`) e `UnidadeMedida` (`UN`, `KG`, `L`), se a equipe optar por enum.
  - **Resultado esperado:** classes mapeadas para as tabelas do DER.

- [ ] **3.2. Mapear corretamente**
  - `@Entity`, `@Table`, `@Id`, `@GeneratedValue`.
  - `@Column` com nomes do DER, `@ManyToOne` + `@JoinColumn`.
  - `@Enumerated(EnumType.STRING)` para enums.
  - `BigDecimal` para quantidades; `LocalDate` para data operacional/validade; `OffsetDateTime` ou `Instant` para `dataRegistro`.
  - `ativo` nas entidades de cadastro.
  - **Resultado esperado:** o Hibernate valida o schema sem criar tabelas por conta própria.

- [ ] **3.3. Criar os DTOs**
  - Requests e responses separados; nunca devolver entidade com senha/hash.
  - DTOs de erro padronizados.
  - **Resultado esperado:** o JSON da API bate com o contrato.

- [ ] **3.4. Criar os repositórios**
  - `UsuarioRepository`: busca por login, unicidade, ativos.
  - `SetorRepository` e `FornecedorRepository`: listagens por ativo e id.
  - `InsumoRepository`: busca parcial por nome, ordenação, filtro de ativo e consulta de alerta.
  - `MovimentacaoRepository`: filtros e ordenação por `dataRegistro` desc + `id` desc.
  - **Resultado esperado:** consultas necessárias sem `findAll()` sem critério.

- [ ] **3.5. Testar a persistência**
  - `@DataJpaTest` para as consultas principais.
  - **Resultado esperado:** repositórios funcionando contra o MySQL (ou banco de teste).

---

### FASE 4 — Segurança: JWT, BCrypt e rotas protegidas

- [ ] **4.1. Configurar o `PasswordEncoder`**
  - Usar BCrypt.
  - **Resultado esperado:** cadastro grava hash e login compara com `matches`.

- [ ] **4.2. Liberar somente as rotas públicas**
  - `POST /api/usuarios` e `POST /api/auth/login`.
  - Todas as demais exigem `Authorization: Bearer <JWT>`.
  - **Resultado esperado:** GET sem token retorna 401.

- [ ] **4.3. Implementar o login**
  - Buscar por `login`, verificar `ativo`, comparar senha e gerar token.
  - Resposta: `{ "token": "...", "tipo": "Bearer", "expiraEm": 3600 }`.
  - Credenciais inválidas ou usuário inativo: 401.
  - **Resultado esperado:** login válido retorna token utilizável.

- [ ] **4.4. Implementar o serviço de JWT**
  - Segredo em variável de ambiente, assinatura validada, expiração de 3600 segundos.
  - O token deve identificar o usuário autenticado.
  - **Resultado esperado:** token inválido/expirado é rejeitado.

- [ ] **4.5. Implementar o filtro JWT**
  - Ler `Authorization: Bearer`, validar, carregar o usuário e preencher o `SecurityContext`.
  - Token ausente/inválido/expirado: 401 com JSON `{status, mensagem, timestamp}`.
  - Não devolver HTML de login do Spring Security.
  - **Resultado esperado:** rotas protegidas acessíveis somente com token válido.

- [ ] **4.6. Implementar `GET /api/usuarios/me`**
  - Identidade vem do token, nunca de um ID enviado pelo cliente.
  - **Resultado esperado:** retorna o usuário autenticado sem senha.

- [ ] **4.7. Testar a segurança**
  - Login correto, login errado, usuário inativo, sem token, token inválido, token expirado.
  - **Resultado esperado:** CT03, CT04 e CT05 cobertos.

---

### FASE 5 — Usuários, setores e fornecedores

- [ ] **5.1. Usuários (7 operações)**
  - `POST`: nome, login e senha obrigatórios; senha ≥ 8; login único; retorno 201 sem senha.
  - `GET /me`: usuário do token.
  - `GET`: ativos por padrão; `?ativo=todos` e `?ativo=false`; ordem por id.
  - `GET /{id}`: inclui inativo; 404 se não existir.
  - `PUT /{id}`: nome, login e `ativo`; não altera senha; login único; reativação.
  - `DELETE /{id}`: inativação lógica; 204 sem corpo.
  - **Resultado esperado:** CT01, CT02, CT17 e CT18 cobertos.

- [ ] **5.2. Setores (5 operações)**
  - `POST`: nome obrigatório e único entre ativos; `descricao`.
  - `GET`: ativos por padrão; filtros `?ativo=todos` e `?ativo=false`; ordem por id.
  - `GET /{id}`: inclui inativo; 404 se não existir.
  - `PUT /{id}`: nome, descrição e `ativo` obrigatório; nome único entre ativos.
  - `DELETE /{id}`: excluir se sem vínculos; senão inativar; 204.
  - **Resultado esperado:** CRUD completo e regras de inativação.

- [ ] **5.3. Fornecedores (5 operações)**
  - `POST`: nome e CNPJ obrigatórios; CNPJ com 14 dígitos e único; telefone opcional.
  - `GET`: ativos por padrão; filtros; ordem por id.
  - `GET /{id}`: inclui inativo; 404.
  - `PUT /{id}`: nome, CNPJ, telefone e `ativo`; CNPJ único; fornecedor inativo não pode ser vinculado a novo insumo.
  - `DELETE /{id}`: se associado a insumos, preservar referência e inativar; 204.
  - **Resultado esperado:** CT06 coberto.

- [ ] **5.4. Revisar os erros**
  - 400 para validação, 404 para ID inexistente, 409 para duplicidade.
  - **Resultado esperado:** mensagens amigáveis e JSON padronizado.

---

### FASE 6 — Insumos e alertas

- [ ] **6.1. `POST /api/insumos`**
  - Campos: nome, unidade de medida, estoque mínimo e fornecedor.
  - Saldo inicial sempre zero; **não** aceitar `quantidadeAtual` no request.
  - Fornecedor deve estar ativo.
  - Unidade `UN` inteira; `KG`/`L` com até 3 casas.
  - **Resultado esperado:** CT07 coberto.

- [ ] **6.2. Listagem, consulta e atualização**
  - `GET /api/insumos`: busca parcial por nome, sem diferenciar maiúsculas/minúsculas; `?ativo=todos` e `?ativo=false`; ordem alfabética.
  - `GET /api/insumos/{id}`: inclui inativo; 404 se não existir.
  - `PUT /api/insumos/{id}`: não altera saldo; unidade imutável depois de existir movimentação; fornecedor ativo quando alterado; `ativo` obrigatório.
  - `DELETE /api/insumos/{id}`: excluir se sem movimentações; com histórico, inativar e preservar.
  - **Resultado esperado:** CT08, CT13, CT16 e CT17 cobertos.

- [ ] **6.3. Alertas**
  - `GET /api/alertas/estoque`: somente insumos ativos com `quantidadeAtual <= estoqueMinimo`.
  - Ordem por nome; `situacao` sempre `"CRITICO"`; array `[]` se não houver.
  - **Resultado esperado:** CT15 coberto.

- [ ] **6.4. Conferir o modelo**
  - Não criar saldo por lote; lote e validade são informações da movimentação.
  - **Resultado esperado:** nenhuma tabela/view de saldo por lote no fluxo da API.

---

### FASE 7 — Movimentações, transação e concorrência

Esta é a fase de maior peso nas regras de negócio. Não pule os testes de concorrência.

- [ ] **7.1. `POST /api/movimentacoes`**
  - Endpoint único para `ENTRADA` e `SAIDA`.
  - Não criar `PUT` nem `DELETE` de movimentação.
  - `usuarioId` vem do JWT; não aceitar do corpo.
  - **Resultado esperado:** endpoint 201 com o JSON do contrato.

- [ ] **7.2. Validar a entrada**
  - `tipo` obrigatório (`ENTRADA`/`SAIDA`).
  - `quantidade` estritamente positiva e compatível com a unidade.
  - `dataMovimentacao` não pode ser futura.
  - `SAIDA` exige setor ativo e não recebe lote/validade.
  - `ENTRADA` pode omitir lote/validade; se validade vier, não pode ser anterior à data operacional.
  - Insumo inativo: 409.
  - **Resultado esperado:** CT12, CT13 e CT21 cobertos.

- [ ] **7.3. Garantir a transação**
  - Buscar o insumo com lock pessimista **ou** fazer atualização atômica condicional.
  - Calcular `saldoAnterior` e `saldoAtual`.
  - Atualizar o saldo e gravar a movimentação na mesma transação.
  - `dataRegistro` gerada pelo servidor no momento do lançamento.
  - Em falha, rollback integral (sem saldo alterado e sem histórico).
  - **Resultado esperado:** CT09, CT10, CT11 e CT19 cobertos.

- [ ] **7.4. Testar concorrência**
  - Simular duas saídas simultâneas com saldo insuficiente para as duas.
  - Uma deve falhar com 409; o saldo não pode ficar negativo e não pode haver histórico parcial.
  - **Resultado esperado:** CT20 coberto.

- [ ] **7.5. Histórico**
  - `GET /api/movimentacoes`: filtros `insumoId`, `setorId`, `usuarioId`, `tipo`, `dataInicio` e `dataFim`.
  - Ordenação: `dataRegistro` desc e `id` desc no empate.
  - `GET /api/movimentacoes/{id}`: 404 se não existir.
  - **Resultado esperado:** CT14 e CT22 cobertos.

- [ ] **7.6. Documentar a decisão**
  - Explicar no documento técnico se usaram lock pessimista, atualização atômica ou lock otimista.
  - **Resultado esperado:** a equipe consegue defender a escolha na banca.

---

### FASE 8 — Validações e tratamento padronizado de erros

- [ ] **8.1. Criar o `@RestControllerAdvice`**
  - Centralizar as exceções de validação, autenticação, recurso não encontrado, regra de negócio e erro inesperado.

- [ ] **8.2. Mapear os status**
  - 400: campo obrigatório, quantidade inválida, data futura, `ativo` inválido, `dataInicio > dataFim`.
  - 401: credenciais inválidas, token ausente/inválido/expirado.
  - 404: recurso não encontrado.
  - 409: duplicidade, estoque insuficiente, insumo inativo, vínculo que impede exclusão.
  - 500: falha inesperada.

- [ ] **8.3. Padronizar o JSON**
  - `{ "status": ..., "mensagem": "...", "timestamp": "..." }`.
  - `timestamp` real do momento da falha.
  - Nunca devolver stack trace, SQL ou detalhes internos.

- [ ] **8.4. Testar os erros**
  - Cada status com pelo menos um caso positivo e um negativo.
  - **Resultado esperado:** contrato de erro respeitado em todos os endpoints.

---

### FASE 9 — Testes e coleção Postman

- [ ] **9.1. Montar a coleção Postman**
  - Uma pasta por grupo: usuários/auth, setores, fornecedores, insumos, movimentações, alertas.
  - 26 requisições no total.
  - Ambiente com `baseUrl` e `token`.
  - No login, salvar o token na variável; nas rotas protegidas, usar `Bearer {{token}}`.

- [ ] **9.2. Executar os casos CT01 a CT22**
  - Registrar resultado esperado, resultado observado e evidência.
  - Incluir também testes de erro: sem token, token inválido, estoque insuficiente, data futura, duplicidade e insumo inativo.

- [ ] **9.3. Testes automatizados**
  - Testes de service e controller.
  - `@DataJpaTest` para repositórios.
  - `@SpringBootTest` + `MockMvc` para endpoints.
  - `mvn test` com `BUILD SUCCESS`.

- [ ] **9.4. Testar o banco do zero**
  - Apagar o schema de teste, rodar o script e subir a API.
  - Executar os fluxos principais novamente.
  - **Resultado esperado:** o projeto não depende de dados “mágicos” da máquina de alguém.

- [ ] **9.5. Exportar as evidências**
  - Exportar a coleção Postman.
  - Salvar prints/logs do relatório de testes.
  - **Resultado esperado:** entrega 3 e entrega 4 prontas.

---

### FASE 10 — Documento técnico e instruções

- [ ] **10.1. Escrever o documento técnico (Entrega 5)**
  - Ferramentas e versões: Java, Spring Boot, Maven, MySQL, Postman, IDE.
  - Banco utilizado, nome do schema e como criar/carregar.
  - Configurações necessárias (sem senhas reais).
  - Arquitetura resumida: Controller → Service → Repository; JWT; tratamento de erros.
  - Decisões: inativação lógica, transação/concorrência, ordenação, data operacional × data de registro.
  - **Não confundir** com o documento de RN/RF/RNF (Entrega 7): lá ficam requisitos e regras; aqui ficam ferramentas, banco, configuração e arquitetura.

- [ ] **10.2. Revisar o `README` do backend**
  - Pré-requisitos, criação do banco, script, variáveis de ambiente, `mvn spring-boot:run`, importação do Postman e `mvn test`.
  - **Resultado esperado:** instruções reproduzíveis.

- [ ] **10.3. Revisar segurança**
  - Nenhuma senha, token, chave JWT ou credencial real no repositório.
  - `.env.example` apenas com nomes de variáveis, se usarem.
  - **Resultado esperado:** repositório seguro para entrega.

---

### FASE 11 — Demonstração e banca

Use o checklist do Enunciado, seção 9.

- [ ] **11.1. Cadastro de usuário e login retornando JWT.**
- [ ] **11.2. Bloqueio de endpoint sem autenticação.**
- [ ] **11.3. Cadastros e consultas de setores, fornecedores e insumos.**
- [ ] **11.4. Pesquisa ordenada e consulta de registros inativos.**
- [ ] **11.5. Entrada, saída e conferência do saldo após cada operação.**
- [ ] **11.6. Rejeição de retirada superior ao estoque e de entrada inválida.**
- [ ] **11.7. Consulta do histórico e dos alertas de estoque.**
- [ ] **11.8. Preservação do histórico após inativação e teste de consistência.**
- [ ] **11.9. Ensaiar a explicação de cada decisão técnica.**
- [ ] **11.10. Dividir quem apresenta cada parte e cronometrar a demonstração.**

---

### FASE 12 — Conferência final e entrega

- [ ] **12.1. Conferir as 10 entregas da seção 1** (6 do Enunciado + 4 adicionais).
- [ ] **12.2. Conferir o documento de RN/RF/RNF, o documento e o diagrama de casos de uso, o diagrama de banco e o diagrama de classes.**
- [ ] **12.3. Conferir as 26 operações e os 22 casos CT01 a CT22.**
- [ ] **12.4. Rodar `mvn clean test` e subir a API em uma máquina limpa.**
- [ ] **12.5. Conferir `git status`, remover `target/` e confirmar que não há segredos.**
- [ ] **12.6. Confirmar com o professor o formato final de envio.**
- [ ] **12.7. Fazer o commit/tag final e avisar a equipe.**

---

## 6. Comandos úteis

### MySQL

```bash
# Entrar no MySQL
mysql -u root -p

# Criar o banco (use o nome que a disciplina definir)
CREATE DATABASE nome_do_banco
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE nome_do_banco;

# Executar os scripts da equipe
SOURCE db/schema.sql;
SOURCE db/seed.sql;

# Conferir
SHOW TABLES;
DESCRIBE insumos;
SELECT COUNT(*) FROM usuarios;
SELECT * FROM insumos WHERE ativo = 1 AND quantidade_atual <= estoque_minimo;
```

### Maven / Spring Boot

```bash
cd backend

# Compilar sem testes
mvn clean package -DskipTests

# Rodar a API
mvn spring-boot:run

# Rodar os testes
mvn test
```

### Git

```bash
git status
git diff
git switch -c feat/minha-contribuicao
```

> Commits: usar o fluxo de `docs/colaboradores.md` e a skill `git-commit`. A identidade é aplicada somente no commit, com `git -c user.name=... -c user.email=... commit`.

---

## 7. Perguntas prováveis da banca

Prepare respostas curtas e técnicas para:

1. **Por que a API segue o contrato?**

   Porque o front-end é de outra empresa e já consome as rotas, os campos e os status definidos. Alterar o contrato quebraria a integração.

2. **Como o JWT funciona neste projeto?**

   O login valida `login` + senha (BCrypt), gera um token assinado com expiração de 3600 segundos e o front-end envia esse token no cabeçalho `Authorization: Bearer`. O filtro valida a assinatura e a expiração antes de liberar a rota.

3. **Por que usar BCrypt e não guardar a senha?**

   Porque o hash é irreversível e protege as senhas em caso de vazamento. A API nunca devolve senha nem hash.

4. **Como vocês impedem saldo negativo com duas saídas simultâneas?**

   Com transação e controle de concorrência: lock pessimista, atualização atômica condicional ou lock otimista. Se o saldo não comportar, a operação falha com 409 e não gera histórico parcial.

5. **Por que 409 e não 400 em estoque insuficiente?**

   Porque o JSON está válido; o problema é de estado/regra de negócio, não de formato. O contrato define 409 para conflitos de regra.

6. **Como funciona a inativação?**

   `ativo=false` preserva o registro e o histórico. Usuários são sempre inativados; setores, fornecedores e insumos podem ser excluídos se não tiverem vínculos. Consultas por ID mostram inativos; listagens mostram ativos por padrão.

7. **Por que não há saldo por lote?**

   Porque o contrato define que lote e validade são informações da movimentação; o saldo é único por insumo. O controle por lote está fora do escopo.

8. **Qual a diferença entre `dataMovimentacao` e `dataRegistro`?**

   `dataMovimentacao` é a data operacional informada pelo usuário, não pode ser futura e serve para filtros. `dataRegistro` é o instante real em que o servidor gravou a movimentação.

9. **Como o projeto está organizado?**

   Em camadas Controller → Service → Repository, com DTOs, tratamento centralizado de erros e segurança JWT. O Controller cuida do HTTP; o Service, das regras e transações; o Repository, do banco.

10. **Como vocês testaram?**

    Com a coleção Postman cobrindo as 26 operações, os casos CT01 a CT22, testes automatizados com `mvn test` e evidências de resultado esperado/observado.

11. **Por que não há front-end no projeto?**

    Porque a solução foi dividida entre duas empresas; a outra fornecedora já fez o front-end web. Nossa responsabilidade é a API, as regras e a integração com o banco.

12. **Como garantem que a pesquisa e a ordenação estão corretas?**

    A pesquisa é parcial e `IgnoreCase`; a ordenação de insumos/alertas é por nome crescente e a do histórico é por `dataRegistro` desc + `id` desc. Pode ser feita no banco ou em Java, conforme a decisão da equipe.

---

## 8. Critérios de avaliação

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

## 9. O que não faz parte do escopo

- Front-end, interfaces desktop, tela de login, HTML, CSS, JavaScript.
- Notificações por e-mail ou push.
- Compras e pedidos de fornecedores.
- Autorização por perfis (todos os autenticados têm as mesmas permissões).
- Troca/renovação de JWT.
- Estornos.
- Controle de saldo por lote.
- Vencimento automático e bloqueio de uso por lote.
- Integrações externas.
- Relatórios PDF ou Excel.

---

## 10. Observações sobre os documentos antigos

- `Diversos/Projeto Integrador Módulo 2 - Industria_Bebidas.pdf.OLD` — PDF antigo, **obsoleto**.
- `Diversos/TODO.docx` — versão antiga do roteiro, **histórica**. Este `TODO.md` é o roteiro atual.
- `Entregaveis/` — SQL, DER e requisitos da versão desktop. **Não são a fonte de verdade** do escopo atual e não devem ser alterados sem autorização dos autores.
- Se a equipe produzir um novo DER/SQL, ele passa a ser a referência do banco. O contrato continua sendo a fonte de verdade da API.

> Em caso de dúvida, vale o **Contrato Técnico** para rotas, campos, JSON, status, filtros e ordenação; e o **Enunciado** para regras de negócio, tecnologias e escopo. Se os dois não resolverem, perguntem ao professor.
