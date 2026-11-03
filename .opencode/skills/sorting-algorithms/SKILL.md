---
name: sorting-algorithms
description: Ordenação e consultas exigidas pelo Contrato Técnico da API, com opções de implementação no banco (ORDER BY) ou em Java, incluindo Quicksort opcional e fundamentação para a banca.
---

# Sorting Algorithms Skill (ordenação e consultas)

## 1. O que o contrato atual exige

O Contrato Técnico **não exige um algoritmo específico**. Ele exige que as listagens saiam na ordem correta:

| Listagem | Ordem obrigatória |
| --- | --- |
| Insumos | nome crescente |
| Alertas de estoque | nome crescente |
| Histórico de movimentações | `dataRegistro` decrescente e, no empate, `id` decrescente |
| Demais cadastros (usuários, setores, fornecedores) | id crescente |

Além disso:

- A pesquisa de insumos por nome é **parcial** e **ignora maiúsculas/minúsculas**.
- A ordenação pode ser implementada com recursos do banco ou de Java (RN-16).
- **Quicksort não é obrigatório** no escopo atual. A skill antiga que exigia Quicksort foi substituída por esta.

> Se a equipe escolher Quicksort, ótimo: é uma decisão técnica válida e pode ser explicada na banca. Mas não apresente isso como exigência do contrato.

## 2. Opção 1: ordenar no banco (mais simples)

Use método derivado do Spring Data:

```java
public interface InsumoRepository extends JpaRepository<Insumo, Integer> {

    List<Insumo> findByAtivoTrueOrderByNomeAsc();

    List<Insumo> findByAtivoTrueAndNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
}
```

Ou `@Query` com `ORDER BY`:

```java
@Query("""
    select i from Insumo i
     where i.ativo = true
     order by i.nome asc
    """)
List<Insumo> listarAtivosOrdenados();
```

Histórico:

```java
List<Movimentacao> findAllByOrderByDataRegistroDescIdDesc();
```

Vantagens:

- Menos código e menos chance de erro.
- O banco usa índices e otimiza a ordenação.
- O contrato permite explicitamente.

Cuidados:

- Use `IgnoreCase` ou `lower(...)` para pesquisa sem caixa.
- Inclua o desempate `id desc` no histórico.
- Não esqueça o filtro de `ativo` quando a listagem padrão for de ativos.
- Não ordene no controller; deixe a consulta/service responsável.

## 3. Opção 2: ordenar em Java

Use quando a regra de ordenação for mais complexa, quando a lista já estiver em memória ou quando a equipe quiser demonstrar o algoritmo.

### Comparator

```java
List<Insumo> ordenados = insumos.stream()
        .sorted(Comparator.comparing(Insumo::getNome, String.CASE_INSENSITIVE_ORDER))
        .toList();
```

Histórico com desempate:

```java
historico.sort(
    Comparator.comparing(Movimentacao::getDataRegistro).reversed()
              .thenComparing(Movimentacao::getId, Comparator.reverseOrder())
);
```

### Quicksort (opcional)

Se a equipe quiser implementar Quicksort para fins de aprendizado:

```java
public final class OrdenacaoService {

    private OrdenacaoService() { }

    public static void ordenarPorNome(List<Insumo> lista) {
        if (lista == null || lista.size() <= 1) return;
        quickSort(lista, 0, lista.size() - 1);
    }

    private static void quickSort(List<Insumo> lista, int inicio, int fim) {
        if (inicio < fim) {
            int pivo = particionar(lista, inicio, fim);
            quickSort(lista, inicio, pivo - 1);
            quickSort(lista, pivo + 1, fim);
        }
    }

    private static int particionar(List<Insumo> lista, int inicio, int fim) {
        Insumo pivo = lista.get(fim);
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            if (lista.get(j).getNome().compareToIgnoreCase(pivo.getNome()) <= 0) {
                i++;
                trocar(lista, i, j);
            }
        }
        trocar(lista, i + 1, fim);
        return i + 1;
    }

    private static void trocar(List<Insumo> lista, int i, int j) {
        Insumo temp = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temp);
    }
}
```

Complexidade:

- Melhor caso/média: **O(n log n)**.
- Pior caso: **O(n²)** (pivô ruim, lista já ordenada com pivô no fim).
- Espaço: **O(log n)** de pilha de recursão.
- Não é estável; se precisar de estabilidade, use `Comparator`/`TimSort` ou inclua critério de desempate.

## 4. Como escolher e justificar

| Situação | Escolha razoável |
| --- | --- |
| Listagem simples por nome | `ORDER BY` no banco ou método derivado |
| Histórico com dois critérios | `ORDER BY dataRegistro DESC, id DESC` |
| Regra de ordenação dinâmica/complexa | `Comparator` em Java |
| Aula/banca sobre algoritmos | Quicksort implementado pela equipe, se o grupo souber explicar |
| Lista pequena já carregada | `Comparator` é suficiente |

Se a equipe optar por Quicksort, prepare a justificativa:

- divisão e conquista;
- média O(n log n);
- in-place (pouco espaço extra);
- escolha do pivô e risco do pior caso;
- por que não usou `ORDER BY` (decisão didática, não exigência do contrato).

Se optar por `ORDER BY`, a justificativa também é válida:

- o contrato permite recursos do banco (RN-16);
- menos código e melhor uso de índices;
- a equipe concentra o esforço nas regras de negócio e na segurança.

## 5. Testes

- Lista vazia e com um elemento.
- Nomes fora de ordem, com maiúsculas/minúsculas diferentes.
- Nomes duplicados.
- Lista já ordenada e ordem inversa.
- Histórico com mesma `dataRegistro` (desempate por id).
- Pesquisa parcial (`"garrafa"` encontra `"Garrafa PET 2L"`).
- Listagem de inativos (`?ativo=todos` e `?ativo=false`).

## 6. Anti-padrões

- Dizer que Quicksort é obrigatório (não é no contrato atual).
- Ordenar no controller ou no DTO.
- Esquecer o desempate `id desc` no histórico.
- Ordenar apenas por `dataRegistro` e deixar a ordem instável.
- Usar `ORDER BY` com nome de coluna errado ou SQL injection (não concatene entrada do usuário).
- Reordenar a lista depois de já ter vindo ordenada do banco, sem necessidade.
- Misturar ordem de insumos e ordem de alertas de forma diferente.
- Ignorar `ativo` nas listagens padrão.
