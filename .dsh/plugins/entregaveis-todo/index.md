# Plugin entregaveis-todo (DSH)

## Propósito

No OpenCode, este plugin observa gravações em Entregaveis/ e injeta um lembrete no próximo contexto do modelo com os critérios de conferência de .opencode/entregaveis.json.

No DSH, não existe sistema de plugins. O equivalente é:
- Ler .dsh/entregaveis.json (ou .opencode/entregaveis.json) para conhecer os critérios de cada entrega.
- Quando um arquivo em Entregaveis/ for criado ou editado, o agente do DSH deve manualmente comparar o conteúdo com os critérios e orientar a equipe sobre o que falta para marcar [X] no Diversos/TODO.md, seção 1.2.

## Arquivos relacionados

- .dsh/entregaveis.json — critérios adaptados para DSH
- .opencode/entregaveis.json — critérios originais do OpenCode (mantidos)
- Diversos/TODO.md — roteiro de execução com as 15 entregas
