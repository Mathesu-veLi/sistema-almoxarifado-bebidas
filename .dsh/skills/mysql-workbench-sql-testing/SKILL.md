---
name: mysql-workbench-sql-testing
description: Localiza scripts SQL no projeto, inspeciona schema.sql e orienta teste no MySQL Workbench com XAMPP/MySQL standalone. Use quando o usuário disser "testar script no MySQL Workbench", "rodar schema.sql", "como executar SQL no Workbench" ou "validar banco com XAMPP".
whenToUse: >
  O usuário tem um script SQL (schema.sql, seed.sql ou outro) na pasta Entregaveis/Banco de Dados/ e quer
  executá-lo no MySQL Workbench. O MySQL pode estar rodando via XAMPP, instalado standalone ou em container.
  A skill cobre localização do arquivo, inspeção de sintaxe MySQL, conexão ao servidor, execução no SQL Editor
  e verificação de constraints CHECK vs. triggers/application logic para regras de negócio (RN-02, RN-03, RN-13).
---

# mysql-workbench-sql-testing

Skill para localizar, inspecionar e testar scripts SQL do projeto no MySQL Workbench.

## Steps

### 1. Carregar ferramentas de arquivo
```bash
# No código do agente (run_code):
await tools.ops_load_sdk({ tools: ["glob", "read_files", "stat"] })
```

### 2. Localizar scripts SQL em Entregaveis/Banco de Dados/
```bash
# No código do agente (run_code):
const sqlFiles = await tools.ops_glob({ pattern: ["Entregaveis/**/*.sql"], outputMode: "paths" })
```
**Resultado esperado:** lista caminhos como `Entregaveis/Banco de Dados/schema.sql`, `Entregaveis/Banco de Dados/seed.sql`.

### 3. Ler o script SQL para confirmar sintaxe MySQL
```bash
# No código do agente (run_code):
const schema = await tools.ops_read_files({ files: [{ path: "Entregaveis/Banco de Dados/schema.sql", limit: 300 }] })
```
**Verificar:**
- Header com `CREATE DATABASE IF NOT EXISTS bebidas_db DEFAULT CHARACTER SET utf8mb4;`
- `USE bebidas_db;`
- Tabelas: `usuarios`, `setores`, `fornecedores`, `insumos`, `movimentacoes`
- PKs, FKs (`ON DELETE RESTRICT`), índices, `CHECK` constraints
- Comentário: `-- Requer MySQL 8.0.16+ (antes disso o banco aceita a sintaxe CHECK mas NÃO a aplica).`

### 4. Comparar com diagrama DBML (opcional)
```bash
const dbml = await tools.ops_read_files({ files: [{ path: "Entregaveis/Banco de Dados/bebidas_db_diagram.dbml", limit: 200 }] })
```
**Confirmar:** estrutura idêntica (tabelas, colunas, FKs, CHECKs). O DBML não representa functions/triggers auxiliares (estão só no schema.sql).

### 5. Verificar requisitos da FASE 8 do TODO.md
```bash
const todo = await tools.ops_read_files({ files: [{ path: "Diversos/TODO.md", offset: 140, limit: 100 }] })
```
**Conferir:** entidades obrigatórias — usuario, setor, fornecedor, insumo, movimentacao. Não há `seed.sql` nem `backend/` com entidades Java no repositório atual.

### 6. Orientar conexão no MySQL Workbench com XAMPP
**Passos para o usuário:**

1. **Iniciar MySQL no XAMPP**
   - Abra o XAMPP Control Panel → clique **Start** na linha do MySQL.
   - Porta padrão: **3306**. Usuário: **root**. Senha: **em branco** (ou a definida na instalação).

2. **Abrir MySQL Workbench**
   - Clique na conexão **Local instance MySQL80** (ou crie nova: Hostname `127.0.0.1`, Port `3306`, Username `root`, Password `vazio`).

3. **Abrir o script**
   - Menu **File → Open SQL Script...**
   - Selecione `Entregaveis/Banco de Dados/schema.sql`

4. **Executar tudo**
   - Pressione **Ctrl + Shift + Enter** (ou clique no ícone de raio ⚡ na barra do SQL Editor).
   - O script cria o banco `bebidas_db`, faz `USE bebidas_db;` e cria as 5 tabelas.

5. **Verificar resultado**
   - Painel esquerdo (SCHEMAS): deve aparecer `bebidas_db` → Tables → `usuarios`, `setores`, `fornecedores`, `insumos`, `movimentacoes`.
   - Clique com botão direito em uma tabela → **Select Rows - Limit 1000** para confirmar estrutura vazia.

### 7. Explicar constraints CHECK vs. Triggers/Application Logic
**O que CHECK cobre (MySQL 8.0.16+):**
- `ativo IN (0,1)` em todas as tabelas
- `cnpj REGEXP '^[0-9]{14}$'` em fornecedores
- `quantidade > 0` em movimentacoes
- `saldo_anterior >= 0`, `saldo_atual >= 0` em movimentacoes
- `tipo IN ('ENTRADA','SAIDA')` em movimentacoes
- `setor_id IS NOT NULL` quando tipo = 'SAIDA' (via CHECK com CASE)
- `saldo_atual = saldo_anterior + quantidade` (ENTRADA) ou `saldo_anterior - quantidade` (SAIDA)

**O que CHECK NÃO cobre (precisam de triggers ou lógica na aplicação):**
| Validação (RN) | Por que CHECK não basta | Onde implementar |
|---|---|---|
| **RN-02**: Quantidade fracionária em insumo `UN` nas movimentações | CHECK não consulta outra tabela (`insumos`) para saber a unidade | Trigger `trg_movimentacoes_before_insert/update` + function `fn_insumo_un_inteira` |
| **RN-03**: Saldo não negativo com concorrência (duas saídas simultâneas) | CHECK vê apenas a linha atual; race condition precisa lock transacional | Service com `@Transactional` + lock pessimista (`SELECT ... FOR UPDATE`) |
| **RN-13**: Data operacional não futura (`data_movimentacao <= CURDATE()`) | CHECK não aceita funções não-determinísticas (`NOW()`, `CURDATE()`) | Trigger `trg_movimentacoes_before_insert` + function `fn_data_nao_futura` |
| Setor ativo em SAIDA (`setor.ativo = 1`) | CHECK não faz JOIN com `setores` | Trigger `trg_movimentacoes_before_insert` + function `fn_setor_ativo` |
| Fornecedor ativo no cadastro de insumo | CHECK não faz JOIN com `fornecedores` | Trigger `trg_insumos_before_insert/update` + function `fn_fornecedor_ativo` |

**No schema.sql atual:** functions e triggers auxiliares já estão definidos após as tabelas (linhas ~120-230). Eles implementam as validações cruzadas acima.

### 8. Verificação pós-execução
**Comandos úteis no Workbench (nova aba SQL):**
```sql
-- Listar tabelas criadas
SHOW TABLES FROM bebidas_db;

-- Ver estrutura de uma tabela
DESCRIBE bebidas_db.movimentacoes;

-- Ver constraints
SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE, TABLE_NAME
FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
WHERE TABLE_SCHEMA = 'bebidas_db';

-- Ver triggers
SHOW TRIGGERS FROM bebidas_db;

-- Ver functions
SHOW FUNCTION STATUS WHERE Db = 'bebidas_db';
```

## Pitfalls

| Problema | Causa | Solução |
|---|---|---|
| `ops_glob is not a function` | SDK não carregado | Sempre chame `ops_load_sdk({ tools: ["glob", "read_files", "stat"] })` antes |
| `program completion must be lossless JSON` | Retorno de objeto não serializável (ex.: `undefined` ou `BigInt`) | Retorne apenas primitivos/arrays/objetos planos; evite `console.log` no retorno |
| Erro de conexão no Workbench | MySQL do XAMPP não iniciado ou porta errada | Verifique XAMPP Control Panel → MySQL "Running"; porta 3306; firewall |
| `Access denied for user 'root'@'localhost'` | Senha definida no XAMPP | Use a senha configurada; ou redefina via `mysqladmin -u root password 'nova'` |
| CHECK constraints não funcionam | MySQL < 8.0.16 | Exija MySQL 8.0.16+; o script avisa no header |
| Script para no meio | Erro de FK (tabela referenciada não existe) | O script já usa ordem correta (usuarios → setores → fornecedores → insumos → movimentacoes) |
| Triggers não aparecem | Delimiter não processado | Workbench executa bloco inteiro com Ctrl+Shift+Enter; não execute linha a linha |

## Verification

**Sucesso do teste completo:**
1. `glob` encontra `Entregaveis/Banco de Dados/schema.sql` (e opcionalmente `seed.sql`)
2. `read_files` confirma: MySQL 8.0.16+, utf8mb4, 5 tabelas, PKs/FKs/CHECKs/triggers
3. Usuário conecta no Workbench (XAMPP porta 3306, root/sem senha)
4. Script executa sem erros (Ctrl+Shift+Enter)
5. Painel SCHEMAS mostra `bebidas_db` com 5 tabelas
6. `SHOW TRIGGERS` lista `trg_movimentacoes_before_insert`, `trg_movimentacoes_before_update`, `trg_insumos_before_insert`, `trg_insumos_before_update`
7. Agente explica quais RN são validadas por CHECK e quais precisam de trigger/application logic

---

**Arquivos de referência do projeto:**
- `Entregaveis/Banco de Dados/schema.sql` — script principal (230 linhas)
- `Entregaveis/Banco de Dados/bebidas_db_diagram.dbml` — diagrama (118 linhas, idêntico em estrutura)
- `Diversos/TODO.md` — FASE 8 (modelo mínimo do banco)
- `Diversos/Contrato_Tecnico_API_Industria_Bebidas.docx` — fonte de verdade dos endpoints
- `Diversos/Enunciado_Projeto_Integrador_Industria_Bebidas.docx` — regras de negócio RN-01 a RN-16
