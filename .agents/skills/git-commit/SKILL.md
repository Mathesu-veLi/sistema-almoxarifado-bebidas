---
name: git-commit
description: Executa commits atômicos padronizados seguindo Conventional Commits com o perfil de autor de Matheus (Mathesu-veLi).
---

# Git Commit Skill

Use esta skill sempre que qualquer arquivo for criado, modificado ou removido no projeto.

## Perfil Obrigatório do Autor
* **Git User Name:** `Mathesu-veLi`
* **Git Email:** `matheuslevit@gmail.com`

Antes de commitar, valide se o repositório está configurado corretamente com:
```bash
git config user.name
git config user.email
```
Se não estiverem configurados ou estiverem com outro usuário, execute:
```bash
git config user.name "Mathesu-veLi"
git config user.email "matheuslevit@gmail.com"
```

## Formato do Commit (Conventional Commits)
Siga estritamente a convenção:
`<tipo>(<escopo>): <descrição em português>`

### Tipos Permitidos:
* `feat`: Nova funcionalidade (telas JavaFX, novas entidades, novos serviços).
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
