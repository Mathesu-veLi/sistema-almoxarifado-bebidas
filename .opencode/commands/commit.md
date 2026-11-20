---
description: Executa commits atômicos com Conventional Commits, autoria escolhida entre os colaboradores e Pull Request quando o autor não é o mantenedor
agent: commit-agent
---

Verifique a identidade já configurada no Git (`git config user.name` e `git config user.email`): se for de um colaborador de `docs/colaboradores.md`, use-a sem perguntar — só pergunte quando não houver configuração válida. Valide os arquivos modificados e execute commits atômicos com a identidade aplicada apenas no commit (`git -c user.name=... -c user.email=... commit`), nunca sobrescrevendo a configuração local, seguindo as instruções de AGENTS.md e da skill git-commit. Se o autor não for `Mathesu-veLi`, crie a branch de trabalho, publique-a e abra o Pull Request para `main` com o corpo no formato de `.github/PULL_REQUEST_TEMPLATE.md` (ou entregue o link pronto, sem `&body=`, para o modelo carregar no navegador). Se faltar o `gh`, pode instalá-lo avisando antes que o GitHub CLI está sendo baixado e instalado na máquina do usuário. Detalhes adicionais: $ARGUMENTS
