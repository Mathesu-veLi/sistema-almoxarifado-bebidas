# Regras do Projeto Integrador SENAI - Indústria de Bebidas

Este projeto possui exigências do edital que a **equipe** deve cumprir. O agente apenas ajuda: não constrói o projeto, não produz as entregas e não altera `Entregaveis/` sem autorização direta do usuário.

1. **Papel do agente:** assistente dos autores. Explica, revisa, tira dúvidas e ajuda a depurar. Não modela, não decide arquitetura e não escreve documentos de entrega por conta própria.
2. **Material dos autores:** `Entregaveis/` (script SQL, DER e requisitos) é somente leitura. Qualquer alteração depende de permissão explícita na conversa.
3. **Nome do Banco de Dados:** deve ser estritamente `bebidas_db`.
4. **Carga Inicial do Banco:** toda tabela deve ter **no mínimo 3 registros** cadastrados via script SQL.
5. **Arquitetura em Duas Camadas:** frontend desktop em **Swing/JFrame** (`frontend/`) e backend em **Spring Boot REST** (`backend/`). O frontend não acessa o MySQL diretamente.
6. **Telas Obrigatórias (Swing/JFrame):** `LoginFrame`, `HomeFrame`, `InsumosFrame` e `MovimentacaoFrame`.
7. **Algoritmo de Ordenação Obrigatório:** Quicksort implementado pela equipe em `com.senai.bebidas.service.OrdenacaoService` no **backend**, exposto por `/api/insumos/ordenados` e usado na tela de movimentação. Proibido `ORDER BY`, `Collections.sort()` ou `List.sort()` para esse fim.
8. **Git:** projeto colaborativo — a lista oficial de autores está em `docs/colaboradores.md`. Antes de commitar, pergunte ao usuário qual colaborador fez as alterações e aplique a identidade só naquele commit (`git -c user.name=... -c user.email=... commit`). Nunca sobrescreva a configuração local.
9. **Commits Atômicos:** padrão Conventional Commits em português (`<tipo>(<escopo>): <descrição em português>`).
10. **Rastreabilidade:** sem exclusão física de registros que quebre a rastreabilidade histórica.
11. **Contrato REST:** se a equipe mudar endpoints, registre o contrato no material da equipe e mantenha os DTOs do frontend alinhados.
