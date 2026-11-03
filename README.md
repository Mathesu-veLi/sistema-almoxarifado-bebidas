# Projeto Integrador — Indústria de Bebidas (API REST)

> Documento temporário, para orientar a equipe durante o desenvolvimento. Pode ser atualizado ou removido quando não for mais útil.

Sistema de **almoxarifado e controle de estoque** para uma indústria de bebidas. A solução foi dividida entre duas empresas: a primeira já desenvolveu o **front-end web**; a nossa equipe é responsável por implementar **somente a API REST**, as regras de negócio e a integração com o banco de dados.

O front-end vai consumir exatamente as URLs, os métodos HTTP e os formatos JSON definidos no **Contrato Técnico da API**. Por isso, a regra mais importante do projeto é: **não alterar rotas, nomes de campos, formatos JSON ou códigos HTTP para "facilitar" a implementação**.

## Aviso importante: o escopo mudou

- O projeto **não é mais** um sistema desktop com telas Swing/JFrame.
- O PDF antigo, `Diversos/Projeto Integrador Módulo 2 - Industria_Bebidas.pdf.OLD`, está **obsoleto**. Não use esse PDF como requisito.
- As fontes de verdade atuais são os dois arquivos `.docx` da pasta `Diversos/`:
  - `Diversos/Enunciado_Projeto_Integrador_Industria_Bebidas.docx` — contexto, tecnologias, módulos, regras de negócio e entregáveis.
  - `Diversos/Contrato_Tecnico_API_Industria_Bebidas.docx` — contrato de integração com as **26 operações**, JSON, status HTTP, filtros e erros.
- `Diversos/TODO.md`, `Diversos/TODO.docx` e o material de `Entregaveis/` foram produzidos para o escopo antigo (desktop). Eles são **históricos** e não devem ser usados como roteiro ou contrato atual. Se a equipe produzir um novo DER/SQL, ele passa a ser a referência do banco.

## O que o projeto é

Uma **API REST** feita em **Java + Spring Boot**, integrada a um banco relacional, que permite:

- cadastrar e autenticar usuários com **JWT**;
- cadastrar setores, fornecedores e insumos;
- registrar **entradas** e **saídas** de estoque;
- manter o **histórico imutável** de movimentações;
- consultar **alertas** de estoque crítico;
- inativar cadastros sem perder o histórico.

O front-end é externo. A equipe **não** desenvolve telas, HTML, CSS, JavaScript, Swing, WindowBuilder ou aplicativo desktop.

## O que a equipe entrega

1. Código-fonte completo do projeto Spring Boot, com arquivo de dependências e instruções reproduzíveis de execução e teste.
2. API integrada ao banco relacional, com dados de demonstração, e preparada para execução local.
3. Coleção Postman (ou equivalente) com as requisições dos **26 métodos**.
4. Relatório curto de testes com resultado esperado, resultado observado e evidência.
5. Documento técnico: ferramentas, versões, banco utilizado, configurações necessárias (sem senhas reais) e arquitetura resumida.
6. Demonstração prática, com explicação das decisões técnicas e das principais regras de negócio.

## Arquivos e documentos do projeto

| Caminho | O que é |
| --- | --- |
| `Diversos/Enunciado_Projeto_Integrador_Industria_Bebidas.docx` | Enunciado atual: contexto, tecnologias, módulos, RN-01 a RN-16 e entregáveis. |
| `Diversos/Contrato_Tecnico_API_Industria_Bebidas.docx` | Contrato atual: as 26 operações, JSON, status HTTP, filtros, ordenação e erros. |
| `Diversos/Projeto Integrador Módulo 2 - Industria_Bebidas.pdf.OLD` | PDF antigo, **obsoleto**. Não usar. |
| `Diversos/TODO.md` e `Diversos/TODO.docx` | Roteiro da versão anterior (desktop). **Histórico**, não é o roteiro atual. |
| `Entregaveis/` | Material da versão anterior (SQL, DER e requisitos desktop). **Somente leitura** e não é fonte de verdade do escopo atual. |
| `docs/colaboradores.md` | Lista oficial de quem participa do projeto, usada para registrar quem fez cada commit. |
| `skills/` | Material de consulta do agente (API REST, JWT, banco, ordenação e commits). |
| `AGENTS.md` | Regras de comportamento do agente neste repositório. |

> **`Entregaveis/` é o material da equipe.** O agente só lê esses arquivos. Qualquer alteração no SQL, no DER ou nos requisitos precisa ser feita e autorizada por vocês.

## Tecnologias obrigatórias

| Área | Tecnologia / responsabilidade |
| --- | --- |
| Linguagem e framework | Java + Spring Boot |
| Camada HTTP | Spring Web; endpoints REST; JSON |
| Persistência | Spring Data JPA + Hibernate; banco relacional |
| Organização | Camadas Controller, Service e Repository |
| Segurança | Autenticação JWT; senha armazenada com hash seguro |
| Validação | Regras de negócio e tratamento padronizado de erros |
| Verificação | Postman ou ferramenta equivalente; evidências de testes |

Ferramentas sugeridas: **IntelliJ IDEA** (ou outra IDE Java), **MySQL** (ou o banco relacional definido na disciplina de Modelagem), **Maven** e **Postman**.

## Arquitetura esperada

```text
Front-end externo
      │  HTTP/JSON + Bearer <JWT>
      ▼
Controller  →  Service  →  Repository  →  Banco relacional
      │             │
      │             └─ regras de negócio, transações, concorrência
      └─ recebe a requisição, valida o DTO e devolve o status HTTP
```

- **Controller:** expõe a rota, recebe o JSON, usa `@Valid` e devolve a resposta com o status correto.
- **Service:** aplica as regras de negócio, controla transações e converte entidades em DTOs.
- **Repository:** consulta e grava no banco com Spring Data JPA.
- **DTO:** objeto de entrada/saída; nunca devolva a entidade com senha ou hash.
- **Exception Handler:** centraliza os erros no formato `{status, mensagem, timestamp}`.
- **Security:** valida o JWT antes de liberar os endpoints protegidos.

## As 26 operações (mapa rápido)

| Grupo | Operações | Total |
| --- | --- | --- |
| Usuários e autenticação | `POST /api/usuarios`, `POST /api/auth/login`, `GET /api/usuarios/me`, `GET /api/usuarios`, `GET /api/usuarios/{id}`, `PUT /api/usuarios/{id}`, `DELETE /api/usuarios/{id}` | 7 |
| Setores | `POST /api/setores`, `GET /api/setores`, `GET /api/setores/{id}`, `PUT /api/setores/{id}`, `DELETE /api/setores/{id}` | 5 |
| Fornecedores | `POST /api/fornecedores`, `GET /api/fornecedores`, `GET /api/fornecedores/{id}`, `PUT /api/fornecedores/{id}`, `DELETE /api/fornecedores/{id}` | 5 |
| Insumos | `POST /api/insumos`, `GET /api/insumos`, `GET /api/insumos/{id}`, `PUT /api/insumos/{id}`, `DELETE /api/insumos/{id}` | 5 |
| Movimentações | `POST /api/movimentacoes`, `GET /api/movimentacoes`, `GET /api/movimentacoes/{id}` | 3 |
| Alertas | `GET /api/alertas/estoque` | 1 |

Regras gerais do contrato:

- Base URL `/api`; `Content-Type` e `Accept: application/json`.
- IDs inteiros positivos; datas operacionais `YYYY-MM-DD`; timestamps ISO 8601 com fuso.
- Listas retornam arrays JSON, inclusive vazios.
- Todas as rotas exigem `Authorization: Bearer <JWT>`, **exceto** `POST /api/usuarios` e `POST /api/auth/login`.
- O token expira em **3600 segundos**.
- `GET` nunca altera estado.
- A API não expõe senhas nem hashes.

> Os IDs, datas e valores dos exemplos do contrato são ilustrativos. Não trate `id=1` ou `insumoId=10` como dados fixos; criem seus próprios registros de demonstração.

## Regras de negócio que não podem falhar

- **RN-01** — O saldo começa em zero e só muda por `ENTRADA` ou `SAIDA`; nunca por edição direta do cadastro.
- **RN-02** — Entradas aumentam e saídas diminuem o saldo; quantidades positivas e compatíveis com a unidade (`UN` inteira; `KG`/`L` até 3 casas).
- **RN-03** — Não permitir saldo negativo, inclusive com duas retiradas simultâneas.
- **RN-04** — Histórico e atualização de saldo são uma única operação transacional; falha não pode gerar atualização parcial.
- **RN-05** — Toda movimentação registra usuário autenticado, data operacional, data/hora real do registro e saldos anterior/posterior.
- **RN-06** — `SAIDA` exige setor ativo; `ENTRADA` pode ter lote e validade opcionais; não há saldo separado por lote.
- **RN-07** — Movimentações são imutáveis: não existem endpoints de edição ou exclusão do histórico.
- **RN-08** — Insumos com histórico e cadastros relacionados preservam referências e são inativados quando necessário.
- **RN-09** — Cadastros inativos continuam consultáveis por ID; listagens mostram ativos por padrão e aceitam `?ativo=todos` ou `?ativo=false`.
- **RN-10** — Setores, fornecedores, insumos e usuários podem ser reativados pela atualização de `ativo`.
- **RN-11** — Cada insumo tem um único fornecedor principal; alterações respeitam vínculos e situação ativa.
- **RN-12** — Alerta quando `quantidadeAtual <= estoqueMinimo`, considerando somente insumos ativos.
- **RN-13** — A data operacional é informada pelo cliente, não pode ser futura e não altera a ordem contábil dos saldos.
- **RN-14** — Cadastro de usuário e login são públicos; todos os demais endpoints exigem JWT.
- **RN-15** — Todos os usuários autenticados têm as mesmas permissões; a senha nunca é devolvida.
- **RN-16** — Pesquisa de insumos por nome é parcial e ignora caixa; a ordenação alfabética pode usar recursos do banco ou de Java.

O detalhe de cada regra está no **Enunciado**, seção 5. O detalhe de cada endpoint está no **Contrato Técnico**.

## Exclusão, reativação e ordenação

- `DELETE /api/usuarios/{id}`: sempre inativação lógica.
- `DELETE` de setores, fornecedores e insumos: exclusão definitiva quando não houver vínculos; caso contrário, inativação lógica.
- Em ambos os casos, responder **204 sem corpo**.
- `GET` por ID retorna também inativos, com `ativo=false`.
- Listagens mostram ativos por padrão e aceitam `?ativo=todos` ou `?ativo=false`.
- Nos `PUT` de cadastros, o campo `ativo` é obrigatório e permite reativação.
- Insumos e alertas: ordem alfabética por **nome crescente**.
- Histórico de movimentações: **dataRegistro decrescente** e, no empate, **id decrescente**.
- Outros cadastros: **id crescente**.
- A ordenação pode ser feita com recursos do banco ou de Java. **Quicksort não é obrigatório** no escopo atual.

## Erros e códigos HTTP

| HTTP | Situação | Exemplo de mensagem |
| --- | --- | --- |
| 400 | Validação de entrada | `O campo nome é obrigatório` |
| 401 | Credenciais inválidas ou token ausente/inválido/expirado | `Credenciais inválidas` / `Autenticação necessária` |
| 404 | Recurso não encontrado | `Insumo não encontrado` |
| 409 | Violação de regra/estado (duplicidade, estoque insuficiente, insumo inativo) | `Quantidade solicitada superior ao estoque disponível` |
| 500 | Falha inesperada | `Erro interno do servidor` |

O corpo de erro deve seguir:

```json
{
  "status": 409,
  "mensagem": "Quantidade solicitada superior ao estoque disponível",
  "timestamp": "2026-11-05T15:20:00-03:00"
}
```

Nunca devolva stack trace ou detalhes internos ao cliente.

## Critérios de avaliação (para priorizar o esforço)

| Critério | Peso |
| --- | --- |
| Fidelidade ao contrato REST e funcionalidades | 35% |
| Regras de negócio, validações e concorrência | 25% |
| Arquitetura Controller/Service/Repository | 15% |
| Integração com banco e JPA | 10% |
| JWT e tratamento de erros | 10% |
| Testes, documentação e demonstração | 5% |

Na prática: comecem pelo contrato (as 26 operações), depois garantam as regras de negócio (saldo, transação, concorrência e inativação), e só então refinem testes e documentação. Um endpoint bonito que não respeita o contrato vale menos do que um endpoint simples que respeita.

## Como começar a contribuir

Passo a passo para quem está entrando no projeto agora: instalar o OpenCode, clonar o repositório, abrir a pasta e usar o agente para tirar dúvidas.

### 1. Instale o Git (e o Node.js, se for instalar o OpenCode pelo npm)

- **Git:** [git-scm.com/downloads](https://git-scm.com/downloads). No Windows, o instalador já inclui o **Git Bash**.
- **Node.js LTS:** [nodejs.org](https://nodejs.org). Só é necessário se você escolher a instalação por `npm` no passo seguinte.

Confira no terminal (a janela onde você digita comandos, como o **PowerShell**):

```powershell
git --version
node --version
```

### 2. Baixe e instale o OpenCode

No Windows há dois caminhos. Escolha um e lembre qual usou: o passo 4 é diferente para cada um.

- **OpenCode Desktop (mais simples):** baixe o instalador em [opencode-desktop-win-x64.exe](https://opencode.ai/update/api/latest/desktop/opencode/opencode-desktop-win-x64.exe) e execute.
- **OpenCode CLI (terminal):** baixe o [opencode-windows-x64.zip](https://opencode.ai/update/api/latest/cli/opencode/opencode-windows-x64.zip), extraia e adicione a pasta à lista de programas do Windows (`PATH`), para poder digitar `opencode` em qualquer terminal; ou instale pelo npm (o npm é o instalador de programas que vem junto com o Node.js):

```powershell
npm install -g @opencode/cli
```

> Instaladores automáticos do Windows, como winget, Chocolatey e Scoop, não são suportados oficialmente; use o instalador do Desktop ou o arquivo `.zip`.

Se você instalou a versão CLI, confirme que ela funciona:

```powershell
opencode --version
```

No Desktop, não precisa desse comando: o programa abre pelo atalho.

Documentação oficial: [opencode.ai/v2/docs](https://opencode.ai/v2/docs/).

### 3. Clone o repositório em uma pasta sua

Clonar é baixar uma cópia completa do projeto para o seu computador, já ligada ao GitHub (o site onde o projeto fica guardado). Vamos colocá-la em uma pasta de projetos dentro da sua pasta pessoal, para não misturar com outros arquivos.

No Windows, o `~` significa a **sua pasta pessoal** (algo como `C:\Users\seu-usuario`) e funciona no **PowerShell** como no Linux. O `mkdir` cria uma pasta e o `cd` entra nela.

```powershell
cd ~
mkdir Projetos -Force
cd Projetos
git clone https://github.com/Mathesu-veLi/sistema-almoxarifado-bebidas.git
cd sistema-almoxarifado-bebidas
```

Ao final, o projeto estará em `C:\Users\seu-usuario\Projetos\sistema-almoxarifado-bebidas` (troque `seu-usuario` pelo seu nome de usuário). O `-Force` só evita erro se a pasta `Projetos` já existir; ele não apaga nada.

### 4. Abra a pasta no OpenCode

Sempre abra o OpenCode **de dentro da pasta do repositório** — é assim que ele encontra as instruções do projeto (`AGENTS.md`), a configuração (`opencode.json`) e os comandos prontos. A pasta é a que você acabou de clonar: `C:\Users\seu-usuario\Projetos\sistema-almoxarifado-bebidas`.

**Se você instalou o OpenCode Desktop:** abra o aplicativo, use a opção de abrir/selecionar uma pasta e escolha `C:\Users\seu-usuario\Projetos\sistema-almoxarifado-bebidas`. No Desktop, use a interface do aplicativo — o comando `opencode` no terminal pertence à versão CLI.

**Se você instalou o OpenCode CLI (terminal):** entre na pasta e abra:

```powershell
cd ~\Projetos\sistema-almoxarifado-bebidas
opencode
```

Também funciona passando a pasta direto: `opencode "$HOME\Projetos\sistema-almoxarifado-bebidas"`.

Na primeira execução (nos dois casos):

1. Rode `/connect` e escolha um provedor (o serviço que fornece a IA, como Google ou OpenRouter; alguns oferecem modelos gratuitos).
2. Rode `/models` (ou `Ctrl+X` e depois `M`) para escolher o modelo (a IA que vai responder).
3. Confirme na interface que o projeto aberto é o `sistema-almoxarifado-bebidas`.

> O catálogo de modelos muda com o tempo. Se um modelo gratuito estiver congestionado, troque por outro ou tente mais tarde.

### 5. Comece a pedir ajuda

Digite sua dúvida na caixa de mensagem e pressione **Enter**. Vale perguntar qualquer coisa: contrato da API, erro de compilação, Spring Boot, JPA, JWT, transações, Postman, Git...

Exemplos de primeiras mensagens:

- "Leia o `AGENTS.md` e me explique em poucas palavras qual é o seu papel neste projeto."
- "Leia o `Diversos/Enunciado_Projeto_Integrador_Industria_Bebidas.docx` e me resuma as regras de negócio."
- "Me explica a diferença entre `POST /api/usuarios` e `POST /api/auth/login` no contrato."
- "Deu este erro ao subir o backend: `<cole o erro completo>`. O que pode ser?"
- "Como implemento o `POST /api/movimentacoes` sem deixar o saldo negativo?"

Atalhos úteis:

| Atalho | Para quê |
| --- | --- |
| `@` | Anexar um arquivo da pasta ao pedido (ex.: `@Diversos/Contrato_Tecnico_API_Industria_Bebidas.docx`). |
| `!` no início | Rodar um comando no terminal (ex.: `!git status`). |
| `/` | Listar os comandos disponíveis. |
| `/new` | Começar uma conversa nova. |
| `/undo` | Desfazer a última mensagem e o que o agente fez depois dela. |

O repositório já traz comandos próprios: `/commit` (salva suas alterações e registra quem fez), `/banca` (treino para a arguição) e `/testes` (revisão de cenários).

**Combine com o agente:** ele explica, revisa e orienta, mas não decide o projeto por vocês. Peça em partes pequenas, leia a resposta e só aceite o que a equipe entende. Nunca cole senhas, chaves JWT ou dados pessoais no chat.

### 6. Faça sua primeira contribuição

Antes de mexer no projeto, entenda as palavras que vão aparecer:

| Palavra | O que significa na prática |
| --- | --- |
| **Repositório** | A pasta do projeto guardada no GitHub, com o histórico de tudo que já foi feito. |
| **Branch** | Uma cópia paralela do projeto, só sua, para trabalhar sem atrapalhar os colegas. |
| **Commit** | Um ponto de salvamento das suas alterações, com o nome de quem fez. |
| **Push** | Enviar seus commits do computador para o GitHub. |
| **Pull Request (PR)** | Um pedido para juntar a sua branch à versão oficial do projeto. |
| **Merge** | Juntar a sua branch à `main` depois que o Pull Request for aprovado. |
| **main** | A versão oficial e estável do projeto. |

Passo a passo:

1. **Crie sua branch** (sua cópia de trabalho):

   ```powershell
   git switch -c feat/minha-contribuicao
   ```

   O comando cria a branch e já entra nela. Troque `minha-contribuicao` pelo assunto (por exemplo, `feat/endpoint-insumos`). O prefixo `feat` é o padrão do projeto para coisas novas.

2. **Faça a alteração e teste.** Pode pedir ajuda ao agente durante o processo, mas confira se a API continua funcionando e se o contrato foi respeitado.

3. **Veja o que mudou:**

   ```powershell
   git status
   git diff
   ```

   O `git status` lista os arquivos alterados; o `git diff` mostra as linhas que entraram e saíram. Vale a pena conferir antes de salvar, para não enviar coisa por engano.

4. **Salve com um commit.** No OpenCode, rode `/commit` e informe qual colaborador de `docs/colaboradores.md` fez o trabalho. O agente escreve a mensagem e registra o nome de quem fez.

5. **Envie a branch para o GitHub:**

   ```powershell
   git push -u origin feat/minha-contribuicao
   ```

   Na primeira vez, o Git pode abrir uma janela do navegador para você entrar na sua conta do GitHub. Entre com a conta que usa no projeto.

6. **Abra o Pull Request.** Depois do push, entre na página do repositório no GitHub. Vai aparecer uma faixa amarela com o botão **Compare & pull request**; clique nele. Escreva um título curto (ex.: "Endpoint de insumos"), confira se a branch de destino é a `main` e clique em **Create pull request**.

   Se a faixa não aparecer, clique em **Pull requests** → **New pull request** e escolha a sua branch.

   O Pull Request é um pedido para juntar sua branch à `main`. Ele permite que outra pessoa revise antes e evita que a versão oficial fique quebrada.

7. **Peça revisão e faça o merge.** Avise um colega no grupo (ou marque-o como revisor). Depois que ele aprovar, clique em **Merge pull request** e em **Confirm merge**. Sua alteração entra na `main`.

   Se o GitHub avisar que há conflitos, não tente resolver sozinho: peça ajuda no grupo ou ao agente.

> **Regra da equipe:** pode criar branch e enviar (`push`) a sua branch à vontade. O que não pode é enviar alteração direto para a `main`: todo trabalho passa por um Pull Request e pela revisão de outra pessoa. Assim um erro não apaga o trabalho do colega e a `main` continua funcionando.

## Como rodar a API

Quando a equipe criar a pasta `backend/`, o fluxo esperado é:

1. **Crie o banco** com o script SQL novo, produzido pela equipe na disciplina de Modelagem e Banco de Dados. Não use o script antigo de `Entregaveis/` como se fosse o modelo atual.
2. **Configure a conexão** em `backend/src/main/resources/application.properties` ou por variáveis de ambiente. Não versione senha real.
3. **Confira as dependências** no `pom.xml`: Spring Web, Spring Data JPA, Validation, driver do banco, Spring Security e a biblioteca JWT escolhida pela equipe.
4. **Suba a aplicação:**

   ```bash
   cd backend
   mvn spring-boot:run
   ```

5. **Teste no Postman:** comece por `POST /api/usuarios`, depois `POST /api/auth/login`, guarde o token e use `Authorization: Bearer <token>` nas demais rotas.

> O agente pode ajudar a diagnosticar erros de execução e de conexão, mas a configuração e as credenciais locais são responsabilidade de cada colaborador.

## Como testar no Postman

1. Crie uma coleção com as **26 operações** do contrato.
2. Crie um ambiente com `baseUrl` (ex.: `http://localhost:8080/api`) e `token`.
3. No login, copie o token da resposta e salve na variável `token`.
4. Nas rotas protegidas, use `Authorization: Bearer {{token}}`.
5. Rode os casos mínimos **CT01 a CT22** do contrato (seção 7) e registre:
   - resultado esperado;
   - resultado observado;
   - evidência (print, log ou export da coleção).
6. Teste também os erros: sem token, token inválido, estoque insuficiente, data futura, cadastro duplicado e insumo inativo.

## Como pedir ajuda ao agente

O agente é um **apoio**, não o autor do projeto. Ele pode explicar conceitos, revisar código, ajudar a interpretar erros e sugerir caminhos. Quem decide, escreve e defende o projeto é a equipe.

**Pedidos que funcionam bem:**

- "Me explica como o filtro JWT deve liberar `POST /api/auth/login` e bloquear as demais rotas."
- "Deu este erro ao subir o Spring Boot: `<cole o erro>`. O que pode ser?"
- "Revisa este `MovimentacaoService` e aponta se ele garante a RN-04 e a RN-03."
- "Como justificar o uso de transação e lock na banca?"
- "O que falta na nossa coleção Postman para cobrir os 26 endpoints?"
- "Confere se este DTO devolve algum campo que o contrato não permite."

**Pedidos que atrapalham:**

- "Faz o projeto inteiro."
- "Monta o banco e o DER."
- "Escreve o relatório de testes e o documento técnico."
- "Cria o frontend."

Nesses casos o agente deve perguntar antes e devolver orientação, porque essas decisões e a autoria precisam ser de vocês.

**Fluxo sugerido:**

1. A equipe decide o que fazer (consultando o Enunciado e o Contrato).
2. A equipe implementa ou pede um apoio pontual ao agente.
3. A equipe revisa e entende o que foi sugerido.
4. Só então o código entra no projeto, com um commit que registra quem fez a alteração.

Dica para a banca: não colem código que vocês não sabem explicar. Se o agente sugerir algo, peçam o "porquê" antes de usar.

## Commits

- O projeto é colaborativo: cada commit registra quem fez a alteração (lista em `docs/colaboradores.md`).
- Padrão: `tipo(escopo): descrição em português`. O **tipo** diz a natureza da mudança (`feat` = funcionalidade nova, `fix` = correção, `docs` = documentação) e o **escopo**, a parte do projeto — por exemplo, `feat(insumos): cria endpoint de listagem`.
- Um commit por assunto. Não misture banco, API e documentação no mesmo commit.
- Se usarem o comando `/commit`, informem qual colaborador fez as alterações. Sem essa confirmação, o commit não deve ser feito.
- Nunca versione senhas, tokens, chaves JWT, arquivos `target/` ou configurações locais.

## Agentes de IA gratuitos

O agente não substitui o estudo da equipe, mas ajuda a tirar dúvidas, revisar código e destravar erros. Para este projeto, a recomendação é o **OpenCode**.

### Por que o OpenCode

- **Gratuito e de código aberto** ([opencode.ai](https://opencode.ai/), versão em português em [opencode.ai/br](https://opencode.ai/br)) — dá para usar e estudar de graça.
- **Já vem com IAs (LLMs) gratuitas** e também aceita modelos de vários provedores (as empresas ou serviços que oferecem IA, como Gemini, Groq, OpenRouter ou Ollama no próprio computador).
- Funciona no **terminal, no editor de código (IDE) ou como aplicativo no desktop**, sem prender a equipe a um programa só.
- É o ambiente que já está configurado neste repositório (as regras do `AGENTS.md`, a configuração do `opencode.json` e os comandos da pasta `.opencode/`).

> Modelos gratuitos costumam ter **limite de uso, fila e qualidade menor** que os pagos, e alguns provedores podem usar o que você envia para treinar a IA. Não colem senhas, chaves de acesso, dados de clientes ou informações pessoais no chat.

### Outras opções gratuitas

| Ferramenta | Onde roda | Observação |
| --- | --- | --- |
| [Cline](https://cline.bot) | VS Code | Complemento (extensão) gratuito; usa uma chave de acesso própria ou um modelo no seu computador. |
| [Roo Code](https://roocode.com) | VS Code | Baseado no Cline, com modos de agente e suporte a vários provedores. |
| [Aider](https://aider.chat) | Terminal | Agente gratuito que altera os arquivos do projeto e registra as mudanças pelo Git; aceita modelos no próprio computador. |
| [Continue](https://continue.dev) | VS Code e JetBrains | Sugestões enquanto você digita (autocomplete) e chat gratuitos, com modelos no computador ou na nuvem. |
| [Gemini CLI](https://github.com/google-gemini/gemini-cli) | Terminal | Agente do Google com limite gratuito usando a conta Google. |
| [Qwen Code](https://github.com/QwenLM/qwen-code) | Terminal | Agente gratuito com modelos Qwen sem custo. |

Para usar uma IA **sem internet**, dá para rodar o [Ollama](https://ollama.com) junto do OpenCode ou do Aider — o custo passa a ser só o do computador.

### Material de apoio em vídeo

- **O que é um agente de IA:** [O que são AGENTES de IA: APRENDA do ZERO!](https://www.youtube.com/watch?v=xSHqm8JsqkI)
- **Instalar e usar o OpenCode (em português):** [Curso OpenCode Completo — Use IAs GRATUITAMENTE!](https://www.youtube.com/watch?v=Q9bH393Bq64)
- **OpenCode Desktop do zero (em português):** [OpenCode Desktop: Tutorial Completo do Zero](https://www.youtube.com/watch?v=Ato5qq80XtM)
- **Instalação no Windows (em inglês, com legendas):** [OpenCode: Free AI Coding Agent Setup Guide](https://www.youtube.com/watch?v=20vcJmqAr4M)

A documentação oficial em [opencode.ai/v2/docs](https://opencode.ai/v2/docs/) é a fonte mais confiável quando o vídeo estiver desatualizado.

## Glossário rápido

| Palavra | O que significa na prática |
| --- | --- |
| **API REST** | Conjunto de endpoints HTTP que o front-end usa para ler e gravar dados. |
| **Endpoint** | Rota HTTP exposta pela API (ex.: `POST /api/auth/login`). |
| **JWT** | Token assinado que identifica o usuário autenticado e expira em 3600 segundos. |
| **Bearer** | Formato do cabeçalho `Authorization: Bearer <token>`. |
| **Hash** | Transformação irreversível usada para guardar a senha com segurança (ex.: BCrypt). |
| **DTO** | Objeto simples de transporte de dados entre camadas ou pela API. |
| **Controller** | Camada que recebe a requisição HTTP e devolve a resposta. |
| **Service** | Camada que aplica as regras de negócio e controla transações. |
| **Repository** | Interface Spring Data usada para consultar e gravar no banco. |
| **Entidade JPA** | Classe Java anotada com `@Entity` que representa uma tabela. |
| **Transação** | Conjunto de operações que só é confirmado se tudo der certo; em falha, há rollback. |
| **Lock** | Mecanismo para evitar que duas operações concorrentes deixem o saldo negativo. |
| **Inativação lógica** | Marcar `ativo=false` sem apagar o registro nem quebrar o histórico. |
| **Saldo** | `quantidadeAtual` do insumo, alterada somente por movimentações. |
| **Data operacional** | `dataMovimentacao` informada pelo cliente; não pode ser futura. |
| **Data de registro** | `dataRegistro` gerada pelo servidor no momento do lançamento. |
| **Alerta crítico** | Insumo ativo com `quantidadeAtual <= estoqueMinimo`. |
| **Postman** | Ferramenta para enviar requisições à API e guardar as evidências de teste. |
| **Contrato** | O documento `Contrato_Tecnico_API_Industria_Bebidas.docx`, que define as 26 operações. |
