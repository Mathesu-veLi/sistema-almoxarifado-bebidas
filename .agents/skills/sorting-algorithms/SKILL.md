---
name: sorting-algorithms
description: Implementação e fundamentação teórica do algoritmo de ordenação (Quicksort) exigido na tela de movimentação e para a banca oral do SENAI.
---

# Sorting Algorithms Skill (Algoritmo de Ordenação)

## 1. Contexto do Requisito no Edital SENAI
* **Item 7.1.1:** Apresentar os insumos cadastrados em ordem alfabética.
* **Item 7.1.2:** Aplicar algum algoritmo de ordenação escolhido pelo estudante.
* **Pergunta da Banca (Item 9):** *"Qual algoritmo de ordenação foi utilizado para listar os insumos em ordem alfabética? Por que a equipe escolheu esse algoritmo?"*

## 2. Algoritmo Selecionado: Quicksort (Divisão e Conquista)

### Por que escolher o Quicksort? (Justificativa para a Banca)
1. **Eficiência Temporal:** Complexidade média de tempo de $O(n \log n)$, sendo significativamente superior a algoritmos simples como Bubble Sort ($O(n^2)$) ou Selection Sort ($O(n^2)$), especialmente quando o almoxarifado lida com centenas de lotes e matérias-primas.
2. **Eficiência Espacial (In-place):** Opera diretamente sobre a lista em memória com consumo de memória adicional mínimo ($O(\log n)$ de pilha de recursão), ideal para sistemas desktop sem sobrecarregar a JVM.
3. **Padrão da Indústria:** É um dos algoritmos de ordenação mais consolidados na ciência da computação.

## 3. Implementação Canônica em Java

```java
package com.senai.bebidas.service;

import com.senai.bebidas.model.TipoInsumo;
import java.util.List;

public class OrdenacaoService {

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
            // Comparação alfabética ignorando maiúsculas/minúsculas e acentos
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

## 4. Testes Unitários de Ordenação
Sempre que testar a ordenação, validar com:
* Lista vazia e lista com 1 elemento.
* Nomes fora de ordem (ex: ["Xarope", "Concentrado", "Açúcar", "Garrafa"]).
* Nomes com acentuação e diferenças de caixa alta/baixa.
* Nomes duplicados.
