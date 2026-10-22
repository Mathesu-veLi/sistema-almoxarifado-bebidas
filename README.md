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
| `Entregaveis/Modelagem_/requisitos_industria_bebidasv1.md` | Primeira versão dos requisitos levantados pela equipe. |
| `Entregaveis/Modelagem_/requisitos_industria_bebidasv2.md` | Versão atualizada dos requisitos e regras de negócio. |
| `Diversos/TODO.md` | Roteiro passo a passo de execução do projeto. |
| `Diversos/TODO.docx` | Versão em Word do mesmo roteiro (histórico). |
| `docs/08_colaboradores.md` | Lista oficial dos autores, usada para definir a autoria dos commits. |

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

## Commits

- O projeto é colaborativo: o commit pertence a quem fez a alteração (lista em `docs/08_colaboradores.md`).
- Padrão: `tipo(escopo): descrição em português` — por exemplo, `feat(insumos): cria controller da tela de insumos`.
- Um commit por assunto. Não misturem banco, tela e documentação no mesmo commit.
- Se usarem o comando `/commit`, informem qual colaborador fez as alterações. Sem essa confirmação, o commit não deve ser feito.
