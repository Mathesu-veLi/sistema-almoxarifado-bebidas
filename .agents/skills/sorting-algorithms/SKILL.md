---
name: sorting-algorithms
description: Implementação e fundamentação teórica do algoritmo de ordenação (Quicksort) no backend Spring Boot, exigido na tela de movimentação e para a banca oral do SENAI.
---

# Sorting Algorithms Skill (Algoritmo de Ordenação)

## 1. Contexto do Requisito no Edital SENAI
* **Item 7.1.1:** Apresentar os insumos cadastrados em ordem alfabética.
* **Item 7.1.2:** Aplicar algum algoritmo de ordenação escolhido pelo estudante.
* **Pergunta da Banca (Item 9):** *"Qual algoritmo de ordenação foi utilizado para listar os insumos em ordem alfabética? Por que a equipe escolheu esse algoritmo?"*

## 2. Onde o algoritmo vive na nova arquitetura

* **Backend:** `backend/src/main/java/com/senai/bebidas/service/OrdenacaoService.java`.
* **Uso:** `MovimentacaoService.listarInsumosOrdenados()` busca os insumos no repositório (sem `ORDER BY`) e aplica o Quicksort antes de devolver.
* **Exposição:** `GET /api/insumos/ordenados` retorna a lista já ordenada.
* **Frontend Swing:** apenas exibe a ordem recebida da API. **Não** usa `Collections.sort()`, `List.sort()` ou `ORDER BY` para esse fim.

## 3. Algoritmo Selecionado: Quicksort (Divisão e Conquista)

### Por que escolher o Quicksort? (Justificativa para a Banca)
1. **Eficiência Temporal:** complexidade média de tempo de $O(n \log n)$, significativamente superior a algoritmos simples como Bubble Sort ($O(n^2)$) ou Selection Sort ($O(n^2)$), especialmente quando o almoxarifado lida com centenas de lotes e matérias-primas.
2. **Eficiência Espacial (In-place):** opera diretamente sobre a lista em memória com consumo adicional mínimo ($O(\log n)$ de pilha de recursão).
3. **Divisão e Conquista:** demonstra domínio de estruturas de dados e algoritmos clássicos exigidos pelo perfil técnico do SENAI.
4. **Independência do SGBD:** a ordenação é feita em Java, não delegada ao `ORDER BY`, comprovando a implementação própria.

## 4. Implementação Canônica no Backend (Java 17)

```java
package com.senai.bebidas.service;

import com.senai.bebidas.model.TipoInsumo;

import java.util.List;

public final class OrdenacaoService {

    private OrdenacaoService() { }

    public static void ordenarInsumosPorNome(List<TipoInsumo> lista) {
        if (lista == null || lista.size() <= 1) {
            return;
        }
        quickSort(lista, 0, lista.size() - 1);
    }

    private static void quickSort(List<TipoInsumo> lista, int inicio, int fim) {
        if (inicio < fim) {
            int indicePivo = particionar(lista, inicio, fim);
            quickSort(lista, inicio, indicePivo - 1);
            quickSort(lista, indicePivo + 1, fim);
        }
    }

    private static int particionar(List<TipoInsumo> lista, int inicio, int fim) {
        // Escolha do pivô (último elemento)
        TipoInsumo pivo = lista.get(fim);
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            // Comparação alfabética ignorando maiúsculas/minúsculas
            if (lista.get(j).getNome().compareToIgnoreCase(pivo.getNome()) <= 0) {
                i++;
                trocar(lista, i, j);
            }
        }
        trocar(lista, i + 1, fim);
        return i + 1;
    }

    private static void trocar(List<TipoInsumo> lista, int i, int j) {
        TipoInsumo temp = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temp);
    }
}
```

### Uso no serviço

```java
@Service
public class MovimentacaoService {

    private final TipoInsumoRepository tipoInsumoRepository;

    public MovimentacaoService(TipoInsumoRepository tipoInsumoRepository) {
        this.tipoInsumoRepository = tipoInsumoRepository;
    }

    public List<TipoInsumo> listarInsumosOrdenados() {
        List<TipoInsumo> insumos = tipoInsumoRepository.findAll(); // sem ORDER BY
        OrdenacaoService.ordenarInsumosPorNome(insumos);
        return insumos;
    }
}
```

## 5. Cuidados e Anti-padrões

* Evitar: `SELECT ... ORDER BY nome` para a tela de movimentação.
* Evitar: `Collections.sort(lista)`, `lista.sort(...)` ou `Stream.sorted()` nesse fluxo.
* Evitar: ordenar no frontend Swing depois de receber a lista (a ordem correta já vem do backend).
* Preferir: Quicksort puro, recursivo, com particionamento de Lomuto e troca in-place.
* Preferir: tratar nomes nulos antes (cadastro valida `@NotBlank`).

## 6. Testes Unitários de Ordenação

Criar `backend/src/test/java/com/senai/bebidas/service/OrdenacaoServiceTest.java` com JUnit 5 e validar:
* Lista vazia e lista com 1 elemento (não deve lançar exceção).
* Nomes fora de ordem (ex: `["Xarope", "Concentrado", "Açúcar", "Garrafa"]`).
* Diferenças de caixa alta/baixa (`compareToIgnoreCase`).
* Nomes duplicados (ordem estável não é exigida, mas todos devem permanecer).
* Lista já ordenada e lista em ordem inversa (pior caso do pivô no último elemento).

Execução:

```bash
cd backend
mvn test -Dtest=OrdenacaoServiceTest
```
