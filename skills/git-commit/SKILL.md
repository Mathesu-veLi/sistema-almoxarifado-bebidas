---
name: git-commit
description: Executa commits atômicos padronizados seguindo Conventional Commits, com autoria escolhida entre os colaboradores do projeto.
---

# Git Commit Skill

Use esta skill sempre que qualquer arquivo for criado, modificado ou removido no projeto.

## Identidade do Autor (escolhida por commit)
O projeto é colaborativo e a lista oficial de autores fica em [`docs/08_colaboradores.md`](../../docs/08_colaboradores.md):

| Handle | Nome | E-mail |
| --- | --- | --- |
| `Mathesu-veLi` | Matheus Levi | `matheuslevit@gmail.com` |
| `LucasRAJ` | Lucas Rafael | `angelicarosasantos99@gmail.com` |
| `israelsouzadev` | `israelsouzadev` | `israelssilva2007@gmail.com` |
| `matheus391` | `matheus391` | `theusilva205@gmail.com` |

**Fluxo obrigatório antes de cada commit:**
1. Pergunte ao usuário (ferramenta de perguntas/`question`) **qual colaborador fez as alterações**, oferecendo a lista acima como opções. Use seleção múltipla quando o trabalho for em par.
2. Aplique a identidade escolhida **somente naquele commit**, sem alterar a configuração do repositório:
```bash
git -c user.name="Nome" -c user.email="email" commit -m "tipo(escopo): descrição"
```
3. Se houver mais de um colaborador, o principal é o autor e os demais entram no rodapé: `Co-authored-by: Nome <email>`.
4. Nunca rode `git config user.name`/`user.email` para trocar o autor, nunca use `--global` e nunca commite sem a autoria confirmada pelo usuário.

## Formato do Commit (Conventional Commits)
Siga estritamente a convenção:
`<tipo>(<escopo>): <descrição em português>`

### Tipos Permitidos:
* `feat`: Nova funcionalidade (telas Swing/JFrame, endpoints REST, novas entidades, novos serviços).
* `fix`: Correção de bug em código, query SQL ou regra de negócio.
* `docs`: Adição ou alteração de documentação (arquivos em `docs/`, `AGENTS.md`, README).
* `style`: Formatação, identação ou CSS sem alteração lógica.
* `refactor`: Refatoração de código sem alterar comportamento.
* `test`: Adição ou atualização de testes (JUnit, casos de teste).
* `chore`: Configurações de ambiente, Maven `pom.xml`, `.gitignore`, rotinas auxiliares.

### Regras de Atomicidade:
1. Nunca faça um único commit gigante misturando documentação, banco de dados e telas.
2. Faça commits pequenos e bem delimitados (ex: primeiro cria a documentação de classes -> commita; depois cria o banco -> commita).
3. Sempre execute `git status` e selecione apenas os arquivos pertinentes com `git add <caminho>`.
4. A mensagem de commit deve ser clara, em português brasileiro e em minúsculas (ex: `feat: implementa tela de login com validação`).
