---
description: Executa commits atômicos com Conventional Commits, autoria escolhida e Pull Request quando o autor não é o mantenedor (DSH)
---

# Comando /commit (DSH)

No DSH, use o subagent `commit-agent` para executar commits atômicos. O fluxo é:

1. Verifique `git config user.name` e `git config user.email`.
2. Se o par for de um colaborador de docs/colaboradores.md, use sem perguntar.
3. Se não, pergunte ao usuário qual colaborador fez as alterações.
4. Aplique a identidade apenas no commit: `git -c user.name="..." -c user.email="..." commit`.
5. Se o autor não for Mathesu-veLi, crie branch, publique e abra PR para main.
6. Siga o modelo .github/PULL_REQUEST_TEMPLATE.md para o corpo do PR.
