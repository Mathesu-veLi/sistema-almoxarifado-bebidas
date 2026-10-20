---
name: database-operations
description: Padrões de conexão JDBC, operações DAO, gerenciamento de transações e views de estoque para bebidas_db no MySQL.
---

# Database Operations Skill

## 1. Conexão com o Banco (`DatabaseConnection`)
* **URL:** `jdbc:mysql://localhost:3306/bebidas_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`
* **Driver:** `com.mysql.cj.jdbc.Driver`
* Implemente o padrão Singleton para manter o pool ou conexão ativa gerenciada.

```java
package com.senai.bebidas.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/bebidas_db?useSSL=false&serverTimezone=America/Sao_Paulo";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Ajustar conforme o ambiente do aluno

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

## 2. Transações e Integridade nas Movimentações
Ao registrar uma movimentação de estoque:
1. Sempre use `connection.setAutoCommit(false)`.
2. Insira o registro na tabela `movimentacoes`.
3. Verifique se o saldo não fica negativo para saídas:
   * Saldo disponível = $\sum \text{Entradas} - \sum \text{Saídas}$.
   * Se $\text{quantidade saída} > \text{saldo disponível}$, lance `EstoqueInsuficienteException` e faça `connection.rollback()`.
4. Em caso de sucesso, execute `connection.commit()`.

## 3. Consulta de Nível Crítico
Para alertar insumos críticos em tempo real:
```sql
SELECT 
    ti.id,
    ti.nome,
    ti.quantidade_minima,
    COALESCE(SUM(
        CASE 
            WHEN tm.nome LIKE '%Entrada%' THEN m.quantidade
            WHEN tm.nome LIKE '%Saída%' OR tm.nome LIKE '%Saida%' THEN -m.quantidade
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
