# Regras do Projeto Integrador SENAI — Indústria de Bebidas (API REST)

Este projeto possui exigências do enunciado e do contrato técnico que a **equipe** deve cumprir. O agente apenas ajuda: não constrói o projeto, não produz as entregas e não altera `Entregaveis/` sem autorização direta do usuário.

## Fontes de verdade

1. `Diversos/Contrato_Tecnico_API_Industria_Bebidas.docx` — rotas, métodos, JSON, status HTTP, filtros, ordenação e as 26 operações.
2. `Diversos/Enunciado_Projeto_Integrador_Industria_Bebidas.docx` — contexto, tecnologias, módulos, RN-01 a RN-16, entregáveis e avaliação.
3. `docs/colaboradores.md` — autoria oficial dos commits.

O PDF antigo (`*.pdf.OLD`), o `Diversos/TODO.*` e o material de `Entregaveis/` pertencem ao escopo anterior (desktop) e **não** são fonte de verdade do projeto atual.

## Escopo e exigências

1. **Papel do agente:** assistente dos autores. Explica, revisa, tira dúvidas e ajuda a depurar. Não modela, não decide arquitetura e não escreve documentos de entrega por conta própria.
2. **Material dos autores:** `Entregaveis/` (script SQL, DER e requisitos antigos) é somente leitura. Qualquer alteração depende de permissão explícita na conversa.
3. **Escopo atual:** somente backend REST em **Java + Spring Boot**. Não há front-end, telas Swing, HTML, CSS ou aplicação desktop.
4. **Arquitetura:** camadas **Controller → Service → Repository**, com DTOs, validação e tratamento padronizado de erros.
5. **Contrato REST:** implementar exatamente as **26 operações** do contrato; não renomear rotas, campos, JSON ou códigos HTTP.
6. **Autenticação:** **JWT** com expiração de **3600 segundos**; senha com hash seguro; `POST /api/usuarios` e `POST /api/auth/login` são públicos; todos os demais endpoints exigem `Bearer`.
7. **Saldo:** começa em zero; só muda por `ENTRADA`/`SAIDA`; nunca negativo; histórico e saldo na mesma transação; movimentações imutáveis.
8. **Concorrência:** duas saídas simultâneas não podem consumir o mesmo saldo; usar transação + lock/atualização atômica e retornar 409 quando faltar saldo.
9. **Inativação:** usuários sempre inativados; setores/fornecedores/insumos excluídos se sem vínculos, senão inativados; histórico preservado; listagens aceitam `?ativo=todos` e `?ativo=false`.
10. **Erros:** JSON `{status, mensagem, timestamp}`; 400 validação, 401 autenticação, 404 não encontrado, 409 regra/estado, 500 falha inesperada; sem stack trace.
11. **Ordenação:** insumos/alertas por nome crescente; histórico por `dataRegistro` desc e `id` desc; demais cadastros por id crescente. Pode usar banco ou Java; **Quicksort não é obrigatório**.
12. **Git:** projeto colaborativo — lista em `docs/colaboradores.md`. Antes de commitar, pergunte qual colaborador fez as alterações e aplique a identidade só naquele commit (`git -c user.name=... -c user.email=... commit`). Nunca sobrescreva a configuração local.
13. **Commits atômicos:** padrão Conventional Commits em português (`<tipo>(<escopo>): <descrição em português>`).
14. **Segredos:** nunca versionar senha, token, chave JWT, `target/` ou configuração local.
15. **Fora do escopo:** front-end/desktop, e-mail/push, compras, perfis, refresh token, estornos, saldo por lote, vencimento automático, integrações e relatórios PDF/Excel.
