---
name: database-operations
description: Padrões de entidades JPA, repositórios Spring Data, transações, configuração MySQL e views de estoque para bebidas_db no backend Spring Boot.
---

# Database Operations Skill (Spring Boot + JPA)

## 1. Configuração da Conexão (`application.properties`)

```properties
spring.application.name=almoxarifado-bebidas

spring.datasource.url=jdbc:mysql://localhost:3306/bebidas_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false

server.port=8080
```

* **`ddl-auto=validate`:** o schema é criado pelo script `Entregaveis/Banco de Dados/BancodoProjeto.sql`, nunca pelo Hibernate. Em desenvolvimento, `update` pode ser usado com cautela, mas `create-drop` é proibido (apagaria a carga inicial).
* **Driver:** `com.mysql.cj.jdbc.Driver` (`mysql-connector-j`).
* Nunca versione credenciais reais; use variáveis de ambiente quando necessário:
  `spring.datasource.password=${DB_PASSWORD:}`.

## 2. Entidades JPA

* Anote cada entidade com `@Entity` e `@Table(name = "...")` usando exatamente o nome da tabela do script (`tipos_insumos`, `lotes_insumos`, `movimentacoes`, etc.).
* Use `@Id @GeneratedValue(strategy = GenerationType.IDENTITY)`.
* Mapeie relacionamentos com `@ManyToOne` + `@JoinColumn(name = "tipo_insumo_id")`, respeitando as FKs do banco.
* Evite `@Data` em entidades JPA (risco de recursão em `toString`/`equals`). Prefira getters/setters explícitos.
* Use `@JsonIgnore` ou DTOs para não serializar ciclos de relacionamento na API. **Prefira sempre DTOs.**

Exemplo:

```java
@Entity
@Table(name = "tipos_insumos")
public class TipoInsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(name = "quantidade_minima", nullable = false)
    private Integer quantidadeMinima;

    // getters e setters
}
```

## 3. Repositórios Spring Data

```java
public interface TipoInsumoRepository extends JpaRepository<TipoInsumo, Integer> {

    List<TipoInsumo> findByNomeContainingIgnoreCase(String termo);

    boolean existsByNomeIgnoreCase(String nome);
}
```

* Não use `@Query` com `ORDER BY` para a listagem da tela de movimentação: a ordenação é responsabilidade do `OrdenacaoService` (Quicksort).
* Consultas de saldo e nível crítico podem usar `@Query(nativeQuery = true)` apontando para as views `vw_quantidade_atual` e `vw_quantidade_atual_por_lote`, ou projeções JPQL.
* Para relatórios consolidados, prefira `interface` projections ou DTOs (`EstoqueDTO`).

## 4. Transações e Integridade nas Movimentações

Anote o método de registro com `@Transactional`:

1. Valide o tipo de movimentação (`operacao = 'E'` ou `'S'`).
2. Para saída, calcule o saldo do lote e bloqueie se `quantidade > saldo`.
3. Persista a movimentação com `movimentacaoRepository.save(mov)`.
4. Recalcule o saldo do insumo e verifique o nível crítico.
5. Se qualquer validação falhar, lance `RegraNegocioException` / `EstoqueInsuficienteException`; o `@Transactional` faz rollback automaticamente.
6. Não capture a exceção dentro do método transacional sem relançar (isso impediria o rollback).

```java
@Service
public class MovimentacaoService {

    @Transactional
    public MovimentacaoResponse registrar(MovimentacaoRequest request) {
        // validações + persistência + verificação de nível crítico
    }
}
```

## 5. Consulta de Nível Crítico (view oficial)

```sql
SELECT
    ti.id,
    ti.nome,
    ti.quantidade_minima,
    COALESCE(SUM(
        CASE
            WHEN tm.operacao = 'E' THEN m.quantidade
            WHEN tm.operacao = 'S' THEN -m.quantidade
            ELSE 0
        END
    ), 0) AS saldo_atual
FROM tipos_insumos ti
LEFT JOIN lotes_insumos li ON li.tipos_insumos_id = ti.id
LEFT JOIN movimentacoes m ON m.lotes_insumo_id = li.id
LEFT JOIN tipos_movimentacao tm ON m.tipo_movimentacao_id = tm.id
GROUP BY ti.id, ti.nome, ti.quantidade_minima
HAVING saldo_atual <= ti.quantidade_minima;
```

## 6. Tratamento de Erros na API

* `GlobalExceptionHandler` com `@RestControllerAdvice`.
* `RegraNegocioException` → HTTP `400` com JSON `{ "mensagem": "..." }`.
* `EstoqueInsuficienteException` → HTTP `409`.
* `MethodArgumentNotValidException` (Bean Validation) → HTTP `400` com a lista de campos inválidos.
* Nunca devolva stack trace ao cliente.

## 7. Boas Práticas

* Use `@Valid` nos DTOs de entrada e anotações (`@NotBlank`, `@NotNull`, `@PositiveOrZero`).
* Não exponha entidades JPA diretamente nos controllers; converta para DTO.
* Mantenha `spring.jpa.open-in-view=false` e carregue o necessário dentro da camada de serviço.
* Toda alteração de schema deve ser refletida no script SQL oficial em `Entregaveis/Banco de Dados/BancodoProjeto.sql`, sempre com autorização dos autores.
