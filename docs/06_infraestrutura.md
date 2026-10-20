# Documento Técnico de Infraestrutura

**Projeto:** Sistema Desktop de Almoxarifado para Indústria de Bebidas  
**Curso:** Técnico em Desenvolvimento de Sistemas - SENAI / Sistema FIEB  
**Módulo:** 2 - Projeto Integrador  

Este documento cumpre integralmente o **Item 9 do edital do projeto**:
* **9.1.1.** SGBD utilizado e respectiva versão.
* **9.1.2.** Tecnologia e linguagem utilizadas no desenvolvimento.
* **9.1.3.** Sistema operacional utilizado durante o desenvolvimento.

---

## 1. Sistema Gerenciador de Banco de Dados (SGBD) (Item 9.1.1)

* **SGBD Escolhido:** MySQL Community Server / MariaDB.
* **Versão Oficial:** MySQL 8.0.36 (ou MariaDB 10.4.32 via XAMPP).
* **Porta Padrão:** `3306`.
* **Mecanismo de Armazenamento (Engine):** `InnoDB` (habilitando suporte a chaves estrangeiras, integridade referencial e transações ACID - `COMMIT`/`ROLLBACK`).
* **Codificação de Caracteres (Collation):** `utf8mb4_unicode_ci` (suporte completo a acentuação em português e caracteres especiais).
* **Driver de Conexão:** MySQL Connector/J versão `8.3.0` (`com.mysql.cj.jdbc.Driver`).

---

## 2. Tecnologias e Linguagens Utilizadas (Item 9.1.2)

* **Linguagem Principal:** Java (JDK - Java Development Kit) versão **17 LTS** (ou 21 LTS).
* **Paradigma:** Orientação a Objetos pura (Encapsulamento, Herança, Polimorfismo e Abstração).
* **Framework de Interface Gráfica (Desktop):** **JavaFX 17+** (módulos: `javafx-controls`, `javafx-fxml`, `javafx-graphics`).
* **Linguagem de Marcação de Telas:** FXML (XML baseado para separação estrita entre layout e lógica).
* **Estilização Visual:** CSS3 voltado a JavaFX (`JavaFX CSS`).
* **Ambiente de Desenvolvimento Integrado (IDE):** Apache NetBeans IDE (versão 19 ou superior).
* **Gerenciador de Dependências e Build:** Apache Maven 3.9+.
* **Bibliotecas Adicionais:**
  * `mysql-connector-j` (8.3.0): Comunicação JDBC nativa.
  * `junit-jupiter` (5.10.0): Framework de execução de testes unitários.

---

## 3. Sistema Operacional Utilizado no Desenvolvimento (Item 9.1.3)

* **Sistema Operacional Principal:** Microsoft Windows 10 / Windows 11 Professional (64-bit).
* **Compatibilidade da Solução:** Por ser desenvolvida em Java (JVM) e JavaFX, a aplicação possui portabilidade multiplataforma (*Write Once, Run Anywhere*), podendo ser executada em distribuições Linux (Ubuntu 22.04 LTS+) e macOS com os devidos runtimes JavaFX instalados.

---

## 4. Requisitos de Hardware e Pré-requisitos para Execução

### Requisitos Mínimos:
* **Processador:** Dual-Core 2.0 GHz ou superior.
* **Memória RAM:** 4 GB (recomendado 8 GB para execução concomitante de NetBeans e MySQL).
* **Espaço em Disco:** 500 MB livres.
* **Resolução de Tela:** $1280 \times 720$ ou superior.

### Pré-requisitos de Software na Máquina de Avaliação:
1. Java Development Kit (JDK) 17 ou superior instalado e configurado na variável `JAVA_HOME`.
2. Servidor MySQL ativo rodando na porta 3306.
3. Importação do script `bebidas_db.sql` executada previamente.
