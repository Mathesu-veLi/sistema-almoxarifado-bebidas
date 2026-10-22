# Projeto Integrador — Indústria de Bebidas

> Documento temporário, para orientar a equipe durante o desenvolvimento. Pode ser atualizado ou removido quando não for mais útil.

Sistema desktop de **almoxarifado e controle de estoque** para uma indústria de bebidas, com frontend em **Swing/JFrame**, backend em **Spring Boot REST** e banco **MySQL** (`bebidas_db`).

Ferramentas combinadas: backend no **IntelliJ IDEA**; frontend no **Eclipse com WindowBuilder**, depois de prototipar as telas no **Figma, Canva ou Google Stitch**.

O enunciado completo está em **`Diversos/Projeto Integrador Módulo 2 - Industria_Bebidas.pdf`**. Leiam o PDF antes de começar: ele define as entregas, as telas obrigatórias e as regras da banca.

## Arquivos do projeto

| Caminho | O que é |
| --- | --- |
| `Entregaveis/Banco de Dados/BancodoProjeto.sql` | Script oficial do banco `bebidas_db` (tabelas, views e carga inicial). |
| `Entregaveis/Banco de Dados/bebidas_db_2026-10-13T14_02_26.535Z.jpeg` | Imagem do DER. |
| `Entregaveis/Modelagem/requisitos_industria_bebidas.md` | Requisitos e regras de negócio consolidados pela equipe. |
| `Diversos/TODO.md` | Roteiro passo a passo de execução do projeto. |
| `Diversos/TODO.docx` | Versão em Word do mesmo roteiro (histórico). |
| `docs/colaboradores.md` | Lista oficial dos autores, usada para definir a autoria dos commits. |

> **`Entregaveis/` é o material da equipe.** O agente só lê esses arquivos. Qualquer alteração no script SQL, no DER ou nos requisitos precisa ser feita e autorizada por vocês.

## Como usar o `Diversos/TODO.md`

O `TODO.md` é o **roteiro de trabalho**, não uma entrega. Ele organiza o desenvolvimento em fases, da preparação do ambiente até o treino para a banca:

- **Fase 1:** executar e validar o script do banco.
- **Fase 2:** fechar a modelagem antes do código (diagrama de classes, DER, arquitetura, contrato REST e cenários de teste).
- **Fases 3 a 6:** criar o backend (projeto Maven, entidades, repositórios, regras de negócio, Quicksort e endpoints REST).
- **Fase 7:** criar o projeto frontend Swing.
- **Fase 8:** prototipar as telas (Figma, Canva ou Google Stitch) e só depois desenhar no WindowBuilder.
- **Fase 9:** integrar o frontend com a API.
- **Fase 10:** executar a bateria de testes.
- **Fase 11:** montar a pasta final de entrega.
- **Fase 12:** treinar as respostas da arguição oral.

Cada item tem uma caixa `[ ]`. Marquem conforme concluírem, e leiam a fase inteira antes de começar: ela indica onde fazer, o que conferir e qual é o resultado esperado. Se algo no roteiro não bater com o PDF do edital, vale o PDF.

## Como pedir ajuda ao agente

O agente é um **apoio**, não o autor do projeto. Ele pode explicar conceitos, revisar código, ajudar a interpretar erros e sugerir caminhos. Quem decide, escreve e defende o projeto é a equipe.

**Pedidos que funcionam bem:**

- "Explica como preencher um `JTable` com a lista que vem de `GET /api/insumos`."
- "Deu este erro ao subir o Spring Boot: `<cole o erro>`. O que pode ser?"
- "Revisa esta consulta do `MovimentacaoRepository` e aponta problemas."
- "Como justificar o uso do Quicksort na banca?"
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
4. Só então o código entra no projeto, com commit assinado por quem fez a alteração.

Dica para a banca: não colem código que vocês não sabem explicar. Se o agente sugerir algo, peçam o "porquê" antes de usar.

## Agentes de IA gratuitos

O agente não substitui o estudo da equipe, mas ajuda a tirar dúvidas, revisar código e destravar erros. Para este projeto, a recomendação é o **OpenCode**.

### Por que o OpenCode

- **Gratuito e de código aberto** ([opencode.ai](https://opencode.ai/), versão em português em [opencode.ai/br](https://opencode.ai/br)).
- **Traz LLMs gratuitas** e também aceita modelos de vários provedores (Gemini, Groq, OpenRouter, Ollama local etc.).
- Funciona no **terminal, na IDE ou no desktop**, sem prender a equipe a um único editor.
- É o ambiente que já está configurado neste repositório (`AGENTS.md`, `opencode.json` e as skills em `.opencode/`).

> Modelos gratuitos costumam ter **limite de uso, fila e menor qualidade** que os pagos, e alguns provedores podem usar o conteúdo enviado para treino. Não colem senhas, tokens, dados de clientes ou informações pessoais no chat.

### Outras opções gratuitas

| Ferramenta | Onde roda | Observação |
| --- | --- | --- |
| [Cline](https://cline.bot) | VS Code | Extensão gratuita; usa sua própria chave de API ou modelo local. |
| [Roo Code](https://roocode.com) | VS Code | Fork do Cline, com modos de agente e suporte a vários provedores. |
| [Aider](https://aider.chat) | Terminal | Agente de código aberto que edita arquivos via Git; aceita modelos locais. |
| [Continue](https://continue.dev) | VS Code e JetBrains | Autocomplete e chat gratuitos, com modelos locais ou em nuvem. |
| [Gemini CLI](https://github.com/google-gemini/gemini-cli) | Terminal | Agente do Google com cota gratuita da conta Google. |
| [Qwen Code](https://github.com/QwenLM/qwen-code) | Terminal | Agente de código aberto com modelos Qwen gratuitos. |

Para rodar modelos **totalmente offline**, dá para usar o [Ollama](https://ollama.com) junto do OpenCode ou do Aider — o custo passa a ser só o do computador.

### Material de apoio em vídeo

- **O que é um agente de IA:** [O que são AGENTES de IA: APRENDA do ZERO!](https://www.youtube.com/watch?v=xSHqm8JsqkI)
- **Instalar e usar o OpenCode (em português):** [Curso OpenCode Completo — Use IAs GRATUITAMENTE!](https://www.youtube.com/watch?v=Q9bH393Bq64)
- **OpenCode Desktop do zero (em português):** [OpenCode Desktop: Tutorial Completo do Zero](https://www.youtube.com/watch?v=Ato5qq80XtM)
- **Instalação no Windows (em inglês, com legendas):** [OpenCode: Free AI Coding Agent Setup Guide](https://www.youtube.com/watch?v=20vcJmqAr4M)

A documentação oficial em [opencode.ai/docs](https://opencode.ai/docs) é a fonte mais confiável quando o vídeo estiver desatualizado.

## Commits

- O projeto é colaborativo: o commit pertence a quem fez a alteração (lista em `docs/colaboradores.md`).
- Padrão: `tipo(escopo): descrição em português` — por exemplo, `feat(insumos): cria controller da tela de insumos`.
- Um commit por assunto. Não misturem banco, tela e documentação no mesmo commit.
- Se usarem o comando `/commit`, informem qual colaborador fez as alterações. Sem essa confirmação, o commit não deve ser feito.
