RN-01 — O saldo começa em zero e só muda por ENTRADA ou SAIDA; nunca por edição direta do cadastro.
RN-02 — Entradas aumentam e saídas diminuem o saldo; quantidades positivas e compatíveis com a unidade (UN inteira; KG/L até 3 casas).
RN-03 — Não permitir saldo negativo, inclusive com duas retiradas simultâneas.
RN-04 — Histórico e atualização de saldo são uma única operação transacional; falha não pode gerar atualização parcial.
RN-05 — Toda movimentação registra usuário autenticado, data operacional, data/hora real do registro e saldos anterior/posterior.
RN-06 — SAIDA exige setor ativo; ENTRADA pode ter lote e validade opcionais; não há saldo separado por lote.
RN-07 — Movimentações são imutáveis: não existem endpoints de edição ou exclusão do histórico.
RN-08 — Insumos com histórico e cadastros relacionados preservam referências e são inativados quando necessário.
RN-09 — Cadastros inativos continuam consultáveis por ID; listagens mostram ativos por padrão e aceitam ?ativo=todos ou ?ativo=false.
RN-10 — Setores, fornecedores, insumos e usuários podem ser reativados pela atualização de ativo.
RN-11 — Cada insumo tem um único fornecedor principal; alterações respeitam vínculos e situação ativa.
RN-12 — Alerta quando quantidadeAtual <= estoqueMinimo, considerando somente insumos ativos.
RN-13 — A data operacional é informada pelo cliente, não pode ser futura e não altera a ordem contábil dos saldos.
RN-14 — Cadastro de usuário e login são públicos; todos os demais endpoints exigem JWT.
RN-15 — Todos os usuários autenticados têm as mesmas permissões; a senha nunca é devolvida.
RN-16 — Pesquisa de insumos por nome é parcial e ignora caixa; a ordenação alfabética pode usar recursos do banco ou de Java.