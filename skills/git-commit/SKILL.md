---
name: git-commit
description: Executa commits atômicos padronizados seguindo Conventional Commits, com autoria escolhida entre os colaboradores do projeto.
---

# Git Commit Skill

Use esta skill sempre que qualquer arquivo for criado, modificado ou removido no projeto.

## Identidade do Autor (escolhida por commit)

O projeto é colaborativo e a lista oficial de autores fica em `docs/colaboradores.md`.

**Leia esse arquivo antes de commitar.** Ele é a única fonte de verdade sobre quem participa do projeto — não mantenha lista própria aqui, não copie nomes para esta skill e não invente colaborador. Se o arquivo não puder ser lido, pare e peça ao usuário para informar quem fez as alterações.

**Fluxo obrigatório antes de cada commit:**

1. Pergunte ao usuário (ferramenta de perguntas/`question`) **qual colaborador fez as alterações**, oferecendo os colaboradores de `docs/colaboradores.md` como opções. Use seleção múltipla quando o trabalho for em par.
2. Aplique a identidade escolhida **somente naquele commit**, sem alterar a configuração do repositório:

```bash
git -c user.name="Nome" -c user.email="email" commit -m "tipo(escopo): descrição"
```

3. Se houver mais de um colaborador, o principal é o autor e os demais entram no rodapé: `Co-authored-by: Nome <email>`.
4. Nunca rode `git config user.name`/`user.email` para trocar o autor, nunca use `--global` e nunca commite sem a autoria confirmada pelo usuário.

## Branch e Pull Request (quando o autor não é o mantenedor)

O mantenedor é **Matheus Levi (`Mathesu-veLi`)**. Os commits dele vão direto para a `main`.
Para qualquer outro colaborador, o fluxo é branch + Pull Request:

1. Atualize a `main` (`git fetch origin` + `git pull --rebase origin main`) e crie a branch:
   `git switch -c <tipo>/<escopo>` — minúsculas, sem acento, hífens (ex.: `feat/insumos-listagem`, `docs/todo-renumeracao`).
2. A quantidade de branches é decisão do agente: **uma por assunto lógico**. Assuntos independentes vão em branches
   separadas; assuntos dependentes ficam juntos, com commits atômicos. Reaproveite a branch remota do mesmo assunto.
3. Faça os commits dentro da branch, com a identidade do colaborador (seção acima).
4. Publique apenas a branch: `git push -u origin <branch>`. Nunca `main`, nunca `--force`.
5. Abra o PR para `main` com `gh pr create --base main --head <branch> --title "..." --body "..."` quando o `gh` estiver
   autenticado; sem `gh`, entregue o link
   `https://github.com/Mathesu-veLi/sistema-almoxarifado-bebidas/compare/main...<branch>?expand=1&title=<título>&body=<corpo>`
   com título e corpo prontos (URL-encoding, `%0A` para quebra de linha).
6. O merge é da equipe, após revisão: o agente **nunca** mescla, aprova ou fecha PR.
7. Se o push falhar por autenticação, pare e peça ao usuário rodar o comando no terminal dele.

## Formato do Commit (Conventional Commits)

Siga estritamente a convenção:

```text
<tipo>(<escopo>): <descrição em português>
```

### Tipos Permitidos

- `feat`: nova funcionalidade (endpoint, entidade, service, filtro JWT, regra de negócio).
- `fix`: correção de bug em código, consulta SQL ou regra de negócio.
- `docs`: documentação (`docs/`, `AGENTS.md`, `README.md`, skills, coleção Postman).
- `style`: formatação/identação sem alteração lógica.
- `refactor`: refatoração sem alterar comportamento.
- `test`: testes automatizados (JUnit, MockMvc, `@DataJpaTest`).
- `chore`: configuração de ambiente, Maven `pom.xml`, `.gitignore`, rotinas auxiliares.

### Exemplos adequados ao escopo atual

```text
feat(usuarios): implementa cadastro e login com JWT
feat(security): adiciona filtro de autenticação Bearer
feat(insumos): cria listagem com pesquisa e ordenação
feat(movimentacoes): registra entrada e saída com transação
feat(alertas): consulta insumos no estoque mínimo
fix(movimentacoes): impede saldo negativo em retiradas concorrentes
test(movimentacoes): cobre estoque insuficiente e rollback
docs(api): registra instruções de execução local
chore(maven): adiciona dependências do Spring Security
```

### Regras de Atomicidade

1. Nunca faça um único commit gigante misturando documentação, banco de dados, segurança e endpoints.
2. Faça commits pequenos e bem delimitados: primeiro a entidade/repositório, depois o service, depois o controller, depois o teste.
3. Sempre execute `git status` e selecione apenas os arquivos pertinentes com `git add <caminho>`.
4. A mensagem deve ser clara, em português brasileiro e em minúsculas (ex.: `feat(insumos): cria endpoint de listagem`).
5. Não commite `target/`, senhas, tokens, chaves JWT, arquivos locais de IDE ou configurações com credenciais.
6. Se o commit for de documentação, não inclua código; se for de código, não inclua documentos de entrega não relacionados.

## Antes de Commitar

```bash
git status
git diff
git diff --staged
```

Confirme:

- os arquivos são os esperados;
- não há segredo/credencial;
- o contrato REST não foi alterado sem autorização;
- o projeto compila/testa (`mvn test`) quando houver código;
- a autoria foi confirmada pelo usuário;
- se o autor não for o mantenedor, você está em uma branch de trabalho (nunca na `main`), com o PR aberto ou o link pronto.
