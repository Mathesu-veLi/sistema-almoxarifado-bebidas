# Plugin pull-inicio-sessao (DSH)

## Propósito

No OpenCode, este plugin roda na primeira chamada do modelo de cada sessão: verifica o Git, faz fetch e, se a árvore estiver limpa, executa git pull --ff-only.

No DSH, não existe sistema de plugins. O agente do DSH deve:
1. Na primeira interação de cada sessão, rodar `git fetch` e verificar o status do repositório.
2. Se a árvore estiver limpa e houver commits remotos, rodar `git pull --ff-only`.
3. Se houver alterações não commitadas, pedir ao usuário para commitar antes (usando o commit-agent).
4. Nunca fazer merge, rebase, stash ou --force.
