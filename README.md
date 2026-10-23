# Projeto Integrador — Indústria de Bebidas

> Documento temporário, para orientar a equipe durante o desenvolvimento. Pode ser atualizado ou removido quando não for mais útil.

Sistema desktop (programa que roda no computador) de **almoxarifado e controle de estoque** para uma indústria de bebidas. Ele tem três partes: o **frontend** (as telas, feitas em **Swing/JFrame**), o **backend** (a parte que roda no servidor, feita em **Spring Boot REST**) e o **banco de dados** (**MySQL**, com o nome `bebidas_db`).

Ferramentas que combinamos: o backend será feito no **IntelliJ IDEA**; o frontend no **Eclipse com WindowBuilder**. Antes de programar as telas, desenhamos um rascunho no **Figma, Canva ou Google Stitch**.

O enunciado completo está em **`Diversos/Projeto Integrador Módulo 2 - Industria_Bebidas.pdf`**. Leiam o PDF antes de começar: ele define as entregas, as telas obrigatórias e as regras da banca.

## Arquivos do projeto

| Caminho | O que é |
| --- | --- |
| `Entregaveis/Banco de Dados/BancodoProjeto.sql` | Arquivo com os comandos que criam o banco `bebidas_db` (tabelas, consultas prontas e dados de exemplo). |
| `Entregaveis/Banco de Dados/bebidas_db_2026-10-13T14_02_26.535Z.jpeg` | Imagem do DER (diagrama que mostra as tabelas e como elas se relacionam). |
| `Entregaveis/Modelagem/requisitos_industria_bebidas.md` | O que o sistema precisa fazer e as regras que deve seguir, reunidos pela equipe. |
| `Diversos/TODO.md` | Roteiro passo a passo de execução do projeto. |
| `Diversos/TODO.docx` | Versão em Word do mesmo roteiro (histórico). |
| `docs/colaboradores.md` | Lista oficial de quem participa do projeto, usada para registrar quem fez cada commit. |

> **`Entregaveis/` é o material da equipe.** O agente só lê esses arquivos. Qualquer alteração no arquivo SQL, no DER ou nos requisitos precisa ser feita e autorizada por vocês.

## Como usar o `Diversos/TODO.md`

O `TODO.md` é o **roteiro de trabalho**, não uma entrega. Ele organiza o desenvolvimento em fases, da preparação do computador até o treino para a banca:

- **Fase 1:** rodar o arquivo SQL e conferir se o banco foi criado certinho.
- **Fase 2:** definir o desenho do sistema antes de programar (diagrama de classes, DER, arquitetura, o que cada endereço da API — o canal entre as telas e o servidor — recebe e devolve, e os casos de teste).
- **Fases 3 a 6:** montar o backend — projeto Maven, classes que representam as tabelas, acesso ao banco, regras de negócio (o que pode e o que não pode), o Quicksort e os endereços da API.
- **Fase 7:** criar o projeto das telas em Swing.
- **Fase 8:** desenhar rascunhos das telas (Figma, Canva ou Google Stitch) e só depois montar no WindowBuilder.
- **Fase 9:** ligar as telas à API, para mostrar e gravar os dados de verdade.
- **Fase 10:** executar a bateria de testes.
- **Fase 11:** montar a pasta final de entrega.
- **Fase 12:** treinar as respostas da arguição oral (a conversa com a banca no final).

Cada item tem uma caixa `[ ]`. Marquem conforme concluírem, e leiam a fase inteira antes de começar: ela indica onde fazer, o que conferir e qual é o resultado esperado. Se algo no roteiro não bater com o PDF do edital, vale o PDF.

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

Digite sua dúvida na caixa de mensagem e pressione **Enter**. Vale perguntar qualquer coisa: requisito do edital, erro de compilação, conceito, revisão de código, Git, Quicksort, Swing, JPA...

Exemplos de primeiras mensagens:

- "Leia o `AGENTS.md` e me explique em poucas palavras qual é o seu papel neste projeto."
- "Estou começando agora. Me ajude a entender a Fase 1 do `Diversos/TODO.md`."
- "Deu este erro ao subir o backend: `<cole o erro completo>`. O que pode ser?"
- "Explica como montar um `JTable` (a tabela da tela) que recebe a lista de `GET /api/insumos` (o endereço da API que devolve os insumos)."

Atalhos úteis:

| Atalho | Para quê |
| --- | --- |
| `@` | Anexar um arquivo da pasta ao pedido (ex.: `@Diversos/TODO.md`). |
| `!` no início | Rodar um comando no terminal (ex.: `!git status`). |
| `/` | Listar os comandos disponíveis. |
| `/new` | Começar uma conversa nova. |
| `/undo` | Desfazer a última mensagem e o que o agente fez depois dela. |

O repositório já traz comandos próprios: `/commit` (salva suas alterações e registra quem fez), `/banca` (treino para a arguição) e `/testes` (revisão de cenários).

**Combine com o agente:** ele explica, revisa e orienta, mas não decide o projeto por vocês. Peça em partes pequenas, leia a resposta e só aceite o que a equipe entende. Nunca cole senhas, chaves de acesso ou dados pessoais no chat.

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

   O comando cria a branch e já entra nela. Troque `minha-contribuicao` pelo assunto (por exemplo, `feat/tela-insumos`). O prefixo `feat` é o padrão do projeto para coisas novas.

2. **Faça a alteração e teste.** Pode pedir ajuda ao agente durante o processo, mas confira se o sistema continua funcionando.

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

6. **Abra o Pull Request.** Depois do push, entre na página do repositório no GitHub. Vai aparecer uma faixa amarela com o botão **Compare & pull request**; clique nele. Escreva um título curto (ex.: "Tela de insumos"), confira se a branch de destino é a `main` e clique em **Create pull request**.

   Se a faixa não aparecer, clique em **Pull requests** → **New pull request** e escolha a sua branch.

   O Pull Request é um pedido para juntar sua branch à `main`. Ele permite que outra pessoa revise antes e evita que a versão oficial fique quebrada.

7. **Peça revisão e faça o merge.** Avise um colega no grupo (ou marque-o como revisor). Depois que ele aprovar, clique em **Merge pull request** e em **Confirm merge**. Sua alteração entra na `main`.

   Se o GitHub avisar que há conflitos, não tente resolver sozinho: peça ajuda no grupo ou ao agente.

> **Regra da equipe:** pode criar branch e enviar (`push`) a sua branch à vontade. O que não pode é enviar alteração direto para a `main`: todo trabalho passa por um Pull Request e pela revisão de outra pessoa. Assim um erro não apaga o trabalho do colega e a `main` continua funcionando.

## Como pedir ajuda ao agente

O agente é um **apoio**, não o autor do projeto. Ele pode explicar conceitos, revisar código, ajudar a interpretar erros e sugerir caminhos. Quem decide, escreve e defende o projeto é a equipe.

**Pedidos que funcionam bem:**

- "Explica como preencher um `JTable` (a tabela que aparece na tela) com a lista que vem de `GET /api/insumos` (o endereço da API que devolve os insumos)."
- "Deu este erro ao subir o Spring Boot (o backend): `<cole o erro>`. O que pode ser?"
- "Revisa esta consulta do `MovimentacaoRepository` (a classe que busca as movimentações no banco) e aponta problemas."
- "Como justificar o uso do Quicksort (o algoritmo de ordenação que implementamos) na banca?"
- "O que falta no nosso documento de requisitos para atender ao edital?"

**Pedidos que atrapalham:**

- "Faz o projeto inteiro."
- "Monta o diagrama de classes."
- "Escreve a documentação da entrega."

Nesses casos o agente deve perguntar antes e devolver orientação, porque essas decisões e a autoria precisam ser de vocês.

**Fluxo sugerido:**

1. A equipe decide o que fazer (consultando o PDF e o `TODO.md`).
2. A equipe implementa ou pede um apoio pontual ao agente.
3. A equipe revisa e entende o que foi sugerido.
4. Só então o código entra no projeto, com um commit que registra quem fez a alteração.

Dica para a banca: não colem código que vocês não sabem explicar. Se o agente sugerir algo, peçam o "porquê" antes de usar.

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

## Commits

- O projeto é colaborativo: cada commit registra quem fez a alteração (lista em `docs/colaboradores.md`).
- Padrão: `tipo(escopo): descrição em português`. O **tipo** diz a natureza da mudança (`feat` = funcionalidade nova, `fix` = correção, `docs` = documentação) e o **escopo**, a parte do projeto — por exemplo, `feat(insumos): cria a tela de insumos`.
- Um commit por assunto. Não misturem banco, tela e documentação no mesmo commit.
- Se usarem o comando `/commit`, informem qual colaborador fez as alterações. Sem essa confirmação, o commit não deve ser feito.
