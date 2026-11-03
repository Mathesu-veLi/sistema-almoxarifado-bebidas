---
name: database-operations
description: Entidades JPA, repositórios Spring Data, transações, concorrência e inativação lógica para a API REST de almoxarifado da indústria de bebidas.
---

# Database Operations Skill (Spring Boot + JPA)

## 1. Regra de ouro: o DER/SQL da equipe é a autoridade

A modelagem do banco é produzida pela equipe na disciplina de **Modelagem e Banco de Dados**. Esta skill não define o schema oficial; ela explica como mapear o modelo decidido pela equipe para JPA e como implementar as regras do contrato.

O contrato atual trabalha com cinco conceitos principais:

| Conceito | Campos de negócio (contrato) |
| --- | --- |
| **Usuario** | `id`, `nome`, `login`, `ativo`; senha apenas como hash interno |
| **Setor** | `id`, `nome`, `descricao`, `ativo` |
| **Fornecedor** | `id`, `nome`, `cnpj`, `telefone`, `ativo` |
| **Insumo** | `id`, `nome`, `unidadeMedida`, `quantidadeAtual`, `estoqueMinimo`, `fornecedorId`, `ativo` |
| **Movimentacao** | `id`, `insumoId`, `tipo`, `quantidade`, `setorId`, `usuarioId`, `dataMovimentacao`, `dataRegistro`, `lote`, `validade`, `saldoAnterior`, `saldoAtual` |

> Não invente tabelas/colunas para "completar" o modelo. Se o DER da equipe não tiver um campo necessário, aponte a lacuna e pergunte antes de alterar.

## 2. Configuração da conexão

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/SEU_BANCO?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo
spring.datasource.username=${DB_USER:root}
spring.datasource.password=${DB_PASSWORD:}
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.open-in-view=false
spring.jpa.show-sql=true
```

- **`ddl-auto=validate`:** o schema é criado pelo script SQL da equipe; o Hibernate só valida. Não use `create-drop` em banco com dados.
- **Credenciais:** use variáveis de ambiente; nunca versione senha real.
- **`open-in-view=false`:** carregue o necessário dentro do service e converta para DTO.
- O nome do banco é definido pela equipe na disciplina de Modelagem; não há exigência de `bebidas_db` no contrato atual.

## 3. Entidades JPA

Regras gerais:

- `@Entity` + `@Table(name = "...")` com o nome exato definido no DER/SQL.
- `@Id @GeneratedValue(strategy = GenerationType.IDENTITY)`.
- `@Column(nullable = false, length = ...)` conforme o schema.
- Relacionamentos com `@ManyToOne` + `@JoinColumn(name = "...")`.
- Enums com `@Enumerated(EnumType.STRING)` (ex.: `TipoMovimentacao.ENTRADA/SAIDA`).
- Evite `@Data` em entidade JPA (recursão em `toString`/`equals`); prefira getters/setters explícitos.
- Prefira DTOs na API; não serialize entidades diretamente.

Exemplo de mapeamento (adapte aos nomes do DER da equipe):

```java
@Entity
@Table(name = "insumos")
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(name = "unidade_medida", nullable = false, length = 10)
    private String unidadeMedida;

    @Column(name = "quantidade_atual", nullable = false, precision = 15, scale = 3)
    private BigDecimal quantidadeAtual = BigDecimal.ZERO;

    @Column(name = "estoque_minimo", nullable = false, precision = 15, scale = 3)
    private BigDecimal estoqueMinimo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @Column(nullable = false)
    private boolean ativo = true;

    // getters e setters
}
```

```java
@Entity
@Table(name = "movimentacoes")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "insumo_id", nullable = false)
    private Insumo insumo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoMovimentacao tipo; // ENTRADA ou SAIDA

    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal quantidade;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setor setor; // obrigatório em SAIDA, nulo em ENTRADA

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "data_movimentacao", nullable = false)
    private LocalDate dataMovimentacao;

    @Column(name = "data_registro", nullable = false)
    private OffsetDateTime dataRegistro;

    private String lote;

    private LocalDate validade;

    @Column(name = "saldo_anterior", nullable = false, precision = 15, scale = 3)
    private BigDecimal saldoAnterior;

    @Column(name = "saldo_atual", nullable = false, precision = 15, scale = 3)
    private BigDecimal saldoAtual;
}
```

> A equipe pode optar por calcular o saldo a partir do histórico (ledger) em vez de manter `quantidade_atual` no insumo. O contrato exige o **resultado** (`quantidadeAtual`, `saldoAnterior`, `saldoAtual`), não uma estratégia específica. Decidam juntos e mantenham consistência.

## 4. Repositórios Spring Data

Exemplos de consultas úteis:

```java
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);

    boolean existsByLoginIgnoreCase(String login);

    List<Usuario> findByAtivoTrueOrderByIdAsc();
}
```

```java
public interface InsumoRepository extends JpaRepository<Insumo, Integer> {

    List<Insumo> findByAtivoTrueOrderByNomeAsc();

    List<Insumo> findByAtivoTrueAndNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

    List<Insumo> findByAtivoTrueAndQuantidadeAtualLessThanEqualOrderByNomeAsc(BigDecimal limite);
}
```

```java
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer> {

    List<Movimentacao> findAllByOrderByDataRegistroDescIdDesc();

    List<Movimentacao> findByInsumoIdOrderByDataRegistroDescIdDesc(Integer insumoId);
}
```

Cuidados:

- `IgnoreCase` atende à pesquisa parcial sem diferenciar maiúsculas/minúsculas.
- A ordenação pode ser feita por método derivado, `@Query` com `ORDER BY` ou em Java. O contrato atual **não exige Quicksort**.
- Para filtros combinados de movimentação, use `Specification`, `@Query` com parâmetros opcionais ou métodos derivados; escolha o que a equipe entende melhor.
- Não use `findAll()` sem ordenação quando o contrato exige ordem específica.
- Para listagens de cadastros, sempre considere `ativo` (`true` padrão, `false` ou `todos`).

## 5. Transações e integridade nas movimentações

O registro de movimentação é o ponto mais sensível. O contrato exige:

- `ENTRADA` soma e `SAIDA` subtrai.
- Quantidade estritamente positiva.
- Saldo nunca negativo, inclusive com concorrência.
- Histórico + saldo em uma única transação (rollback integral).
- `usuarioId` vem do JWT.
- `SAIDA` exige setor ativo; `ENTRADA` pode ter lote/validade opcionais.
- `dataMovimentacao` não pode ser futura.
- `dataRegistro` é gerada pelo servidor.
- `saldoAnterior` e `saldoAtual` são gravados.

Fluxo sugerido:

```java
@Service
public class MovimentacaoService {

    @Transactional
    public MovimentacaoResponse registrar(MovimentacaoRequest request, Usuario usuario) {
        // 1. validar data, tipo, quantidade, setor, lote/validade
        // 2. buscar o insumo com lock (ou atualização atômica)
        // 3. calcular saldo anterior e posterior
        // 4. se SAIDA e saldo posterior < 0 → lançar exceção de negócio (409)
        // 5. atualizar o saldo do insumo
        // 6. gravar a movimentação com dataRegistro = agora
        // 7. retornar DTO
    }
}
```

### Concorrência

Duas estratégias comuns (a equipe decide):

**a) Lock pessimista**

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("select i from Insumo i where i.id = :id")
Optional<Insumo> buscarParaAtualizacao(@Param("id") Integer id);
```

Use dentro da transação, antes de verificar o saldo. A segunda transação espera a primeira terminar.

**b) Atualização atômica**

```java
@Modifying
@Query("""
    update Insumo i
       set i.quantidadeAtual = i.quantidadeAtual - :quantidade
     where i.id = :id
       and i.quantidadeAtual >= :quantidade
    """)
int debitarSeHouverSaldo(@Param("id") Integer id, @Param("quantidade") BigDecimal quantidade);
```

Se o retorno for `0`, não havia saldo suficiente: lance a exceção e faça rollback. Essa abordagem evita leitura seguida de escrita vulnerável.

**c) Lock otimista**

Campo `@Version` + tratamento de `OptimisticLockException` com retry ou 409. Também é válido, mas explique a escolha.

> Em qualquer estratégia, duas saídas simultâneas não podem consumir o mesmo saldo. Teste isso.

### Rollback

- Deixe a exceção propagar para o `@Transactional` fazer rollback.
- Não capture a exceção e continue como se tivesse dado certo.
- Não salve a movimentação antes de validar o saldo.
- Não atualize o saldo em um método separado sem transação.

## 6. Inativação, exclusão e histórico

- `Usuario`: `DELETE` sempre inativa (`ativo=false`), nunca apaga.
- `Setor`, `Fornecedor`, `Insumo`: `DELETE` exclui se não houver vínculos; se houver, inativa.
- `Movimentacao`: não tem `ativo` e não pode ser editada/excluída.
- `GET` por ID retorna inativos; listagens mostram ativos por padrão.
- `PUT` de cadastros exige `ativo` e permite reativação.
- Preserve as FKs históricas: não use `ON DELETE CASCADE` em movimentações.
- O campo `ativo` não existe em movimentações.

## 7. Alertas de estoque

Alerta = insumos **ativos** com `quantidadeAtual <= estoqueMinimo`.

```java
List<Insumo> criticos = insumoRepository
        .findByAtivoTrueAndQuantidadeAtualLessThanEqualOrderByNomeAsc(
                /* o limite é o próprio estoqueMinimo de cada insumo */);
```

Como o limite é por insumo, use uma consulta que compare as duas colunas:

```java
@Query("""
    select i from Insumo i
     where i.ativo = true
       and i.quantidadeAtual <= i.estoqueMinimo
     order by i.nome asc
    """)
List<Insumo> buscarCriticos();
```

O DTO de alerta deve seguir o contrato:

```json
{
  "insumoId": 10,
  "nome": "Garrafa PET 2L",
  "quantidadeAtual": 450,
  "estoqueMinimo": 500,
  "situacao": "CRITICO"
}
```

Se não houver críticos, retorne `[]`.

## 8. Tratamento de erros no banco/API

- `GlobalExceptionHandler` com `@RestControllerAdvice`.
- `MethodArgumentNotValidException` → 400.
- `RecursoNaoEncontradoException` → 404.
- `RegraNegocioException` → 409 (duplicidade, estoque insuficiente, insumo inativo, vínculo).
- `DataIntegrityViolationException` → 409 com mensagem amigável (não exponha SQL).
- `Exception` genérica → 500.
- Formato: `{ "status": ..., "mensagem": "...", "timestamp": "..." }`.

## 9. Testes de persistência

- `@DataJpaTest` para repositórios e consultas.
- `@SpringBootTest` + `MockMvc` para o fluxo completo.
- Teste:
  - cadastro com saldo zero;
  - entrada e saída;
  - saída maior que o saldo → 409 e rollback;
  - duas saídas concorrentes → sem saldo negativo;
  - inativação com histórico preservado;
  - listagem `?ativo=todos` e `?ativo=false`;
  - busca parcial `IgnoreCase`;
  - alerta com `<=` e somente ativos.
- Execute `mvn test` antes de commitar.

## 10. Anti-padrões

- `ddl-auto=create-drop` em banco com dados.
- Editar `quantidadeAtual` diretamente no `PUT /api/insumos/{id}`.
- Apagar movimentações ou usar cascade nelas.
- Fazer `SELECT` de saldo e depois `UPDATE` sem lock/atomicidade em fluxo concorrente.
- Capturar exceção dentro da transação e não relançar.
- Expor entidade com relacionamentos circulares ou senha.
- Usar `float`/`double` para quantidade; prefira `BigDecimal`.
- Ignorar `ativo` nas listagens.
- Commitar credenciais ou `target/`.
