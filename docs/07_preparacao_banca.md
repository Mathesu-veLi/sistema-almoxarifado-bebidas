# Roteiro de Preparação para a Banca Avaliadora (Bate-papo sobre a Solução)

**Projeto:** Sistema Desktop de Almoxarifado para Indústria de Bebidas  
**Curso:** Técnico em Desenvolvimento de Sistemas - SENAI / Sistema FIEB  
**Módulo:** 2 - Projeto Integrador  

Este documento contém as 11 perguntas oficiais extraídas do edital (páginas 6 e 7 do PDF) com respostas técnicas fundamentadas para que a equipe defenda o projeto com excelência perante a banca.

---

### 1. Documento de requisitos
> **Pergunta da Banca:** *Quais foram os principais requisitos funcionais que vocês levantaram para o sistema e por que consideraram esses requisitos importantes para a indústria de bebidas?*

* **Resposta Técnica:**  
  "Os principais requisitos funcionais levantados foram:
  1. **Autenticação segura (RF-01):** Impede acessos não autorizados e identifica quem opera o sistema.
  2. **Cadastro e manutenção de insumos com controle de estoque mínimo (RF-06 a RF-09):** Essencial para gerenciar matérias-primas como concentrados, adoçantes e embalagens PET.
  3. **Rastreabilidade total das movimentações com histórico imutável (RF-12 e RF-13):** Registra usuário, setor solicitante, data/hora e tipo de movimentação.
  4. **Emissão de alertas automáticos para níveis críticos (RF-15):** Evita a paralisação das linhas de envase por falta de insumos vitais.
  5. **Ordenação alfabética por algoritmo próprio (RF-14):** Facilita a localização rápida dos insumos no almoxarifado.  
  Consideramos esses requisitos prioritários porque a indústria de bebidas opera com alta rotatividade, datas de validade estritas e alto custo de parada de máquina. A falta de controle manual em papel vinha gerando perdas e atrasos operacionais."

---

### 2. Modelagem do banco de dados
> **Pergunta da Banca:** *Explique como vocês modelaram o DER. Quais entidades e relacionamentos você considera mais importantes e qual foi sua participação nessa modelagem?*

* **Resposta Técnica:**  
  "Modelamos o banco utilizando o padrão relacional normalizado no DrawDB. As entidades centrais são:
  * `tipos_insumos` (categoria abstrata com estoque mínimo de segurança).
  * `lotes_insumos` (a matéria-prima física real, com lote, fornecedor, validade e volume).
  * `movimentacoes` (o histórico contábil de entradas e saídas).  
  O relacionamento mais importante é o vínculo de `movimentacoes` com `lotes_insumos`, `funcionarios` e `setores`. Isso permite que o saldo atual seja calculado dinamicamente via view (`vw_quantidade_atual`) sem redundância de dados, garantindo que saibamos exatamente qual lote foi consumido por qual setor e autorizado por qual operador."

---

### 3. Script SQL
> **Pergunta da Banca:** *Por que o banco de dados recebeu o nome 'bebidas_db' e quais tabelas principais foram criadas? Cite também uma chave estrangeira utilizada.*

* **Resposta Técnica:**  
  "O banco recebeu o nome `bebidas_db` por ser um requisito explícito e mandatório do edital do SENAI (item 3.1).  
  As tabelas principais criadas foram: `cargos`, `funcionarios`, `fornecedores`, `setores`, `tipos_insumos`, `lotes_insumos`, `tipos_movimentacao` e `movimentacoes`.  
  Um exemplo de chave estrangeira é `fk_movimentacoes_lotes_insumo_id` na tabela `movimentacoes`, que referencia `lotes_insumos(id)`. Ela garante a integridade referencial, impedindo que uma movimentação seja registrada para um lote inexistente ou que um lote com histórico seja apagado indevidamente."

---

### 4. Tela de autenticação
> **Pergunta da Banca:** *Como a tela de login valida usuário e senha? O que acontece quando o usuário informa credenciais inválidas?*

* **Resposta Técnica:**  
  "Ao submeter o formulário de login no JavaFX, o `LoginController` primeiramente valida se os campos de texto não estão vazios. Em seguida, aciona o `AuthService`, que consulta o `FuncionarioDAO` via JDBC utilizando um `PreparedStatement` parametrizado (prevenindo ataques de SQL Injection). Se os dados coincidirem com um registro ativo, a sessão do usuário é instanciada na classe `SessaoUsuario` e a tela principal (`HomeView.fxml`) é aberta.  
  Caso as credenciais sejam inválidas, o sistema captura o retorno nulo e emite um alerta amigável na tela informando que as credenciais são inválidas, limpando o campo de senha e mantendo o foco nele para uma nova tentativa, sem travar ou fechar a aplicação."

---

### 5. Tela inicial
> **Pergunta da Banca:** *Quais funcionalidades estão disponíveis na tela inicial após o login e como o usuário navega para as demais telas do sistema?*

* **Resposta Técnica:**  
  "Na tela inicial (`HomeView`), o sistema exibe dinamicamente no cabeçalho o nome do funcionário autenticado e seu cargo (obtidos da `SessaoUsuario`). Estão disponíveis três ações principais:
  1. Acesso ao módulo de **Gerenciamento de Insumos** (abre a tela de catálogo e tabela).
  2. Acesso ao módulo de **Movimentação de Materiais** (abre o formulário de entradas e saídas).
  3. Encerramento da Sessão (**Logout**), que invalida a sessão do usuário e retorna à tela de login.  
  A navegação é gerenciada pelo carregamento de arquivos FXML no mesmo `Stage` (ou troca do `Scene root`), garantindo uma transição fluida e leve."

---

### 6. Gerenciamento de insumos
> **Pergunta da Banca:** *Explique como funciona o cadastro de um novo insumo e quais validações foram implementadas antes de gravar os dados.*

* **Resposta Técnica:**  
  "O cadastro de um novo insumo é iniciado a partir do botão 'Novo Insumo' na tela de gerenciamento. O operador preenche o nome e a quantidade mínima de segurança. Antes de enviar ao banco, o `InsumoService` executa validações de regras de negócio em memória:
  * Verifica se o nome possui no mínimo 3 caracteres e não é composto apenas por espaços.
  * Valida se a quantidade mínima é um número inteiro válido e maior ou igual a zero.
  * Verifica se já não existe outro insumo com o mesmo nome para evitar duplicidade.  
  Somente após todas as validações serem aprovadas é que o `TipoInsumoDAO` executa o comando `INSERT` no MySQL, atualizando a `TableView` imediatamente."

---

### 7. Pesquisa e manutenção de insumos
> **Pergunta da Banca:** *Como o sistema permite pesquisar, editar e excluir insumos? Quais cuidados vocês adotaram para evitar inconsistências nos dados?*

* **Resposta Técnica:**  
  "A pesquisa é realizada através de um campo de texto dinâmico com listener JavaFX (`FilteredList`), que filtra os insumos na tabela conforme o usuário digita, buscando por qualquer parte do nome do insumo.  
  Para edição, o operador seleciona o item na tabela, os campos são carregados e validados antes do `UPDATE`.  
  Para exclusão, implementamos proteção de integridade referencial: se o insumo já possuir lotes ou histórico de movimentação atrelado, o sistema bloqueia a exclusão física e avisa o operador, evitando falhas de chave estrangeira (`Cannot delete or update a parent row`) e mantendo a rastreabilidade exigida pelo edital."

---

### 8. Movimentação de materiais
> **Pergunta da Banca:** *Descreva o fluxo para registrar uma retirada ou uma reposição de material. Quais informações obrigatórias são registradas nessa operação?*

* **Resposta Técnica:**  
  "O operador acessa a tela de movimentações, onde os insumos são exibidos em ordem alfabética. Ele seleciona o insumo e o lote específico, escolhe o tipo (Entrada por Fornecedor ou Saída para Produção), informa a quantidade movimentada e o setor solicitante.  
  As informações obrigatórias registradas são: lote do insumo, tipo de movimentação, quantidade, setor solicitante, data/hora da operação e o usuário autenticado (preenchido automaticamente via `SessaoUsuario`). Se for uma saída, o sistema verifica se há saldo suficiente no lote antes de gravar. Caso a quantidade restante atinja ou fique abaixo do estoque mínimo, o sistema grava a operação e dispara instantaneamente um alerta visual de **Nível Crítico**."

---

### 9. Algoritmo de ordenação
> **Pergunta da Banca:** *Qual algoritmo de ordenação foi utilizado para listar os insumos em ordem alfabética? Por que a equipe escolheu esse algoritmo?*

* **Resposta Técnica:**  
  "Utilizamos o algoritmo **Quicksort** implementado na classe `OrdenacaoService`.  
  A equipe escolheu o Quicksort por três motivos técnicos fundamentais:
  1. **Complexidade de Tempo $O(n \log n)$:** Em média, ele é consideravelmente mais rápido e eficiente que algoritmos quadráticos como Bubble Sort ou Selection Sort ($O(n^2)$), o que é vital para uma fábrica que trabalha com centenas de itens em estoque.
  2. **Eficiência de Memória (*In-Place*):** Ele opera particionando e ordenando diretamente os elementos na própria lista com consumo adicional de memória quase nulo ($O(\log n)$ de pilha de recursão).
  3. **Abordagem de Divisão e Conquista:** Demonstra o domínio de estruturas de dados e algoritmos clássicos exigidos pelo perfil técnico do SENAI."

---

### 10. Testes
> **Pergunta da Banca:** *Quais casos de teste foram definidos para validar o sistema e quais ferramentas ou ambiente foram utilizados durante os testes?*

* **Resposta Técnica:**  
  "Definimos 18 casos de teste cobrindo todos os requisitos funcionais do edital (RF-01 ao RF-16). Validamos cenários de sucesso e caminhos de exceção, como:
  * Tentativas de login com senha incorreta e campos vazios.
  * Validações de limites de formulário (quantidades negativas, campos vazios).
  * Tentativa de retirada sem saldo disponível em estoque.
  * Verificação do disparo de alerta quando o estoque atinge o nível crítico.
  * Teste unitário da ordenação alfabética do Quicksort com listas desordenadas e acentuadas.  
  Utilizamos como ambiente o Windows 11 com JDK 17, a IDE Apache NetBeans e o framework **JUnit 5** para automação de testes unitários da camada de serviço e regras de negócio."

---

### 11. Infraestrutura e decisões técnicas
> **Pergunta da Banca:** *Quais tecnologias, linguagem de programação, SGBD e sistema operacional foram utilizados no desenvolvimento? Por que essas escolhas foram feitas pela equipe?*

* **Resposta Técnica:**  
  "Utilizamos:
  * **Linguagem e Plataforma:** Java 17 LTS com JavaFX. Escolhida por ser a linguagem corporativa padrão do curso, possuir tipagem estática forte, excelente suporte a orientação a objetos e rica biblioteca para desenvolvimento Desktop nativo.
  * **IDE:** Apache NetBeans, por oferecer integração nativa com o ecossistema Java, gerenciador Maven e facilidade no design de interfaces FXML.
  * **SGBD:** MySQL 8.0 com mecanismo `InnoDB`, escolhido pela confiabilidade, conformidade com propriedades ACID (transações seguras para movimentações de estoque) e integridade referencial estrita.
  * **Sistema Operacional:** Windows 10/11 x64, por ser o sistema amplamente utilizado nos computadores de almoxarifado e terminais industriais da empresa."
