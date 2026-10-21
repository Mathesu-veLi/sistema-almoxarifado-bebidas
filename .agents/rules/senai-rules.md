# Regras do Projeto Integrador SENAI - Indústria de Bebidas

Este projeto possui regras inegociáveis para a banca examinadora e governança do código:
1. **Nome do Banco de Dados:** Deve ser estritamente `bebidas_db`.
2. **Carga Inicial do Banco:** Toda tabela DEVE ter **no mínimo 3 registros** cadastrados via script SQL.
3. **Telas Obrigatórias (JavaFX Desktop):**
   - Login (`login.fxml` / `LoginView.fxml`)
   - Home (`home.fxml` / `HomeView.fxml`)
   - Gerenciamento de Insumos (`insumos.fxml` / `InsumosView.fxml`)
   - Movimentação de Materiais (`movimentacao.fxml` / `MovimentacaoView.fxml`)
4. **Algoritmo de Ordenação Obrigatório:** Quicksort implementado em `com.senai.bebidas.service.OrdenacaoService` para listar insumos alfabeticamente na tela de movimentação (justificativa para arguição oral da banca).
5. **Git Profile:** projeto colaborativo — a lista oficial de autores está em `docs/08_colaboradores.md`. Antes de commitar, pergunte ao usuário qual colaborador fez as alterações e aplique a identidade só naquele commit (`git -c user.name=... -c user.email=... commit`). Nunca sobrescreva a configuração local.
6. **Commits Atômicos:** Padrão Conventional Commits em português (`<tipo>(<escopo>): <descrição em português>`).
7. **Rastreabilidade:** Sem exclusão física de registros que quebre a rastreabilidade histórica.
