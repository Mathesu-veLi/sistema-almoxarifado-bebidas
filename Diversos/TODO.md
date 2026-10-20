# 📋 Guia Passo a Passo do Projeto Integrador (TODO da Equipe)

> **Projeto:** Sistema de Almoxarifado e Controle de Estoque — Indústria de Bebidas  
> **Curso:** Técnico em Desenvolvimento de Sistemas — SENAI / FIEB (Módulo 2)  
> **Objetivo deste documento:** Este é o roteiro prático e detalhado da equipe. Qualquer integrante do grupo consegue pegar este guia, sentar no computador do laboratório ou de casa e executar cada etapa do zero até a entrega final, **sem depender de inteligência artificial**.

---

## 🧭 Como Usar Este Guia
* As tarefas estão em **ordem lógica de execução** (do que fazer primeiro até a entrega final).
* Cada tarefa possui uma caixa de marcação `[ ]`. Conforme o grupo for concluindo, marque com um `[X]`.
* Sempre que uma tarefa tiver um arquivo de apoio já pronto na pasta `docs/` ou `Entregaveis/`, nós indicamos exatamente onde olhar!

---

## 📌 Checklist Rápido das 9 Entregas Oficiais do SENAI
Antes de começar, tenha em mente o que o professor vai cobrar no final:
- [ ] **Entrega 1:** Documento de Requisitos e Funcionalidades (`docs/01_requisitos.md`)
- [ ] **Entrega 2:** Imagem do Diagrama Entidade-Relacionamento / DER (`.png` ou `.jpeg`)
- [ ] **Entrega 3:** Script SQL do banco com nome `bebidas_db` e mínimo 3 registros por tabela
- [ ] **Entrega 4:** Tela de Login funcionando com validação de senha e alertas
- [ ] **Entrega 5:** Tela Inicial (Home) mostrando nome do usuário logado e navegação
- [ ] **Entrega 6:** Tela de Gerenciamento de Insumos (tabela, busca, cadastrar, editar, excluir)
- [ ] **Entrega 7:** Tela de Movimentação (ordenação alfabética via Quicksort e alerta de estoque crítico)
- [ ] **Entrega 8:** Documento com o Plano e Registro de Testes executados
- [ ] **Entrega 9:** Documento de Infraestrutura técnica (Java, NetBeans, MySQL, SO)
- [ ] **Pasta Final Compactada:** `.zip` contendo tudo identificado com o nome do aluno/equipe

---

## 🛠️ ETAPA 0: Preparação do Computador e Ferramentas

Antes de programar qualquer linha, garanta que os programas necessários estejam instalados e funcionando:

- [ ] **0.1. Verificar o Java no computador:**
  * Abra o Prompt de Comando (CMD) ou PowerShell.
  * Digite: `java -version` e aperte Enter.
  * *O que deve aparecer:* Java versão 17 ou 21. Se não tiver, baixe e instale o JDK 17 (LTS).
- [ ] **0.2. Verificar o MySQL:**
  * Abra o **MySQL Workbench** ou o painel do **XAMPP** (inicie o serviço MySQL).
  * Certifique-se de que a porta `3306` está ativa e anote sua senha do usuário `root` (geralmente é vazia no XAMPP ou `root` / `admin` no MySQL Workbench).
- [ ] **0.3. Verificar a IDE (Apache NetBeans):**
  * Abra o Apache NetBeans (versão 17, 18, 19 ou superior).
- [ ] **0.4. Verificar o Scene Builder:**
  * Baixe e instale o **Gluon Scene Builder** (ferramenta visual para arrastar e desenhar as telas JavaFX).
  * No NetBeans, vá em: `Tools` (Ferramentas) -> `Options` (Opções) -> aba `Java` -> aba `JavaFX` e aponte o caminho onde o Scene Builder foi instalado.

---

## 🗄️ ETAPA 1: Banco de Dados (`bebidas_db`)

O banco de dados é o coração do sistema. As telas só vão funcionar se o banco estiver criado e com dados dentro.

- [ ] **1.1. Abrir o script SQL do projeto:**
  * O script completo e corrigido já está na pasta:  
    `Entregaveis/Banco de Dados/BancodoProjeto.sql`
- [ ] **1.2. Executar o script no MySQL:**
  * Abra o MySQL Workbench (ou o phpMyAdmin no navegador `localhost/phpmyadmin`).
  * Abra o arquivo `BancodoProjeto.sql` e execute tudo (ícone de raio no Workbench).
- [ ] **1.3. Conferir os requisitos obrigatórios do edital:**
  * [ ] O nome do banco é exatamente `bebidas_db`? (Obrigatório pelo edital!)
  * [ ] Todas as 8 tabelas foram criadas? (`cargos`, `tipos_movimentacao`, `tipos_insumos`, `fornecedores`, `setores`, `lotes_insumos`, `funcionarios`, `movimentacoes`).
  * [ ] Cada tabela tem pelo menos 3 linhas cadastradas? (Rode um `SELECT * FROM funcionarios;`, `SELECT * FROM tipos_insumos;`, etc., para confirmar).
- [ ] **1.4. Salvar a imagem do DER para a Entrega 2:**
  * Já temos a imagem gerada em:  
    `Entregaveis/Banco de Dados/bebidas_db_2026-10-13T14_02_26.535Z.jpeg`
  * Dica: confira se a imagem está legível. Ela será enviada diretamente na entrega do SENAI.

---

## ☕ ETAPA 2: Criação e Configuração do Projeto no NetBeans

Agora vamos preparar a base do código Java no NetBeans.

- [ ] **2.1. Criar um novo projeto Maven no NetBeans:**
  * No menu superior: `File` -> `New Project...`
  * Escolha a categoria: `Java with Maven` -> `FXML JavaFX Application` (ou `Simple JavaFX Application`).
  * Nome do Projeto: `ControleEstoqueBebidas`.
  * Grupo (Group Id): `com.senai.bebidas`.
  * Pacote principal: `com.senai.bebidas`.
  * Clique em `Finish`.
- [ ] **2.2. Adicionar as dependências no `pom.xml`:**
  * Abra o arquivo `pom.xml` do projeto e adicione a dependência do MySQL Connector:
    ```xml
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.3.0</version>
    </dependency>
    ```
  * Clique com o botão direito no projeto e selecione `Clean and Build` para baixar a biblioteca.
- [ ] **2.3. Criar os pacotes da arquitetura MVC + DAO:**
  Dentro de `src/main/java/com/senai/bebidas/`, crie as seguintes pastas (pacotes):
  * [ ] `com.senai.bebidas.model` *(onde ficam as classes dos dados: Insumo, Funcionario, etc.)*
  * [ ] `com.senai.bebidas.dao` *(onde ficam as classes que conversam com o MySQL)*
  * [ ] `com.senai.bebidas.service` *(onde ficam as regras e o algoritmo Quicksort)*
  * [ ] `com.senai.bebidas.controller` *(onde ficam os controladores das telas)*
  * [ ] `com.senai.bebidas.util` *(onde fica a conexão com o banco e alertas)*
- [ ] **2.4. Criar a pasta de telas e estilos:**
  Dentro de `src/main/resources/`:
  * [ ] Criar a pasta `fxml` (onde ficarão os arquivos `.fxml` de cada tela).
  * [ ] Criar a pasta `css` (onde ficará o arquivo `estilo.css` para deixar o sistema bonito).
  * [ ] Criar a pasta `images` (para colocar a logo da empresa ou ícones).

---

## 🔌 ETAPA 3: Backend — Conexão, Classes de Modelo e DAO

Nesta etapa, criamos o código que conversa com o banco de dados antes mesmo de desenhar as telas.

### 3.1. Conexão com o Banco (`util`)
- [ ] **Criar a classe `DatabaseConnection.java` em `com.senai.bebidas.util`:**
  * Configurar a URL do JDBC: `jdbc:mysql://localhost:3306/bebidas_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`
  * Usuário: `root`
  * Senha: sua senha local do MySQL.
  * Fazer um método de teste com `main` para rodar e ver se imprime `"Conectado ao bebidas_db com sucesso!"`.

### 3.2. Controle de Sessão (`util`)
- [ ] **Criar a classe `SessaoUsuario.java`:**
  * Guardar em uma variável estática o funcionário logado (nome, e-mail e cargo).
  * Criar métodos: `setUsuarioLogado(Funcionario f)`, `getUsuarioLogado()` e `limparSessao()`.
  * *Por que isso é importante?* Para a Home saber o nome de quem entrou e para registrar o responsável nas movimentações (exigência do edital!).

### 3.3. Classes de Modelo (`model`)
Consultar o arquivo `docs/02_diagrama_de_classes.md` para ver todos os atributos e métodos:
- [ ] **`Funcionario.java`**: `id`, `nome`, `email`, `senha`, `cpf`, `cargoNome`.
- [ ] **`Insumo.java`**: `id`, `nome`, `descricao`, `volumeEmbalagem`, `unidadeMedida`, `quantidadeMinima`, `saldoAtual`.
- [ ] **`LoteInsumo.java`**: `id`, `numeroLote`, `tipoInsumoId`, `fornecedorId`, `setorId`, `dataValidade`, `saldoAtual`.
- [ ] **`Movimentacao.java`**: `id`, `loteInsumoId`, `funcionarioId`, `tipoMovimentacaoId`, `setorId`, `quantidade`, `dataMovimentacao`.

### 3.4. Camada de Persistência (`dao`)
Criar as classes que executam os comandos SQL via `PreparedStatement`:
- [ ] **`FuncionarioDAO.java`:**
  * Método `autenticar(String email, String senha)`: faz um `SELECT` comparando e-mail e senha. Se achar, retorna o `Funcionario`; se não achar, retorna `null`.
- [ ] **`InsumoDAO.java`:**
  * Método `listarTodos()`: busca insumos com saldo atual (usando a view `vw_quantidade_atual`).
  * Método `inserir(Insumo insumo)`: executa o `INSERT INTO tipos_insumos`.
  * Método `atualizar(Insumo insumo)`: executa o `UPDATE tipos_insumos`.
  * Método `excluir(int id)`: tenta inativar ou excluir (com `try/catch` para avisar se tiver vínculos no estoque).
- [ ] **`MovimentacaoDAO.java`:**
  * Método `registrarMovimentacao(Movimentacao mov)`: insere um registro na tabela `movimentacoes`.
  * Método `buscarSaldoAtualPorLote(int loteId)`: consulta o saldo do lote antes de dar baixa.

---

## 🧠 ETAPA 4: Algoritmo de Ordenação Próprio (Quicksort)

> ⚠️ **ATENÇÃO:** O edital e os professores exigem um algoritmo de ordenação feito pela equipe, e não apenas o `ORDER BY` do SQL!

- [ ] **4.1. Criar a classe `OrdenacaoService.java` em `com.senai.bebidas.service`:**
  * Consultar o código pronto e explicado em: `skills/sorting-algorithms/SKILL.md`.
  * Implementar o método `ordenarInsumosPorNome(List<Insumo> lista)`.
  * O método deve usar o algoritmo **Quicksort recursivo**, comparando os nomes dos insumos com `compareToIgnoreCase()`.
- [ ] **4.2. Criar um teste simples para garantir:**
  * Criar um método `main` rápido, colocar 3 nomes fora de ordem (ex: "Xarope", "Açúcar", "Garrafa PET"), mandar o Quicksort ordenar e conferir se saiu certinho em ordem alfabética.

---

## 🎨 ETAPA 5: Construção das Telas no Scene Builder (Frontend)

Abra o **Scene Builder** para desenhar as 4 telas solicitadas. Salve todas dentro de `src/main/resources/fxml/`.

Consulte o arquivo `docs/04_arquitetura_e_fluxo_telas.md` para ver o visual e os nomes exatos de cada botão e campo.

### 5.1. Tela 1: Login (`login.fxml`)
- [ ] Campo de texto para e-mail (`txtEmail`).
- [ ] Campo de senha (`txtSenha` do tipo `PasswordField`).
- [ ] Botão "Entrar" (`btnEntrar`).
- [ ] Mensagem de erro ou label para status (`lblMensagemErro`).
- [ ] No Scene Builder, no menu lateral esquerdo inferior (aba *Controller*), aponte para:  
  `com.senai.bebidas.controller.LoginController`.

### 5.2. Tela 2: Inicial / Menu (`home.fxml`)
- [ ] Mensagem de boas-vindas: "Bem-vindo, [Nome do Usuário]!" (`lblBoasVindas`).
- [ ] Rótulo com o cargo: "Cargo: Operador de Estoque" (`lblCargo`).
- [ ] Botão grande para ir para: "Gerenciamento de Insumos" (`btnInsumos`).
- [ ] Botão grande para ir para: "Movimentação de Materiais" (`btnMovimentacao`).
- [ ] Botão no topo ou canto: "Sair / Logout" (`btnLogout`).
- [ ] Aponte o controller para: `com.senai.bebidas.controller.HomeController`.

### 5.3. Tela 3: Gerenciamento de Insumos (`insumos.fxml`)
- [ ] Campo de busca por texto com botão "Buscar" ou filtro ao digitar (`txtPesquisa`).
- [ ] Tabela (`TableView`) listando: ID, Nome, Unidade, Quantidade Mínima, Saldo Atual.
- [ ] Campos de formulário para preencher:
  * Nome do Insumo (`txtNome`)
  * Descrição (`txtDescricao`)
  * Volume (`txtVolume`)
  * Unidade de Medida (`txtUnidade`)
  * Quantidade Mínima Segura (`txtQtdMinima`)
- [ ] Botões de ação:
  * "Salvar / Inserir" (`btnSalvar`)
  * "Editar" (`btnEditar`)
  * "Excluir" (`btnExcluir`)
  * "Limpar Campos" (`btnLimpar`)
  * "Voltar para o Menu" (`btnVoltar`)
- [ ] Aponte o controller para: `com.senai.bebidas.controller.InsumosController`.

### 5.4. Tela 4: Movimentação de Materiais (`movimentacao.fxml`)
- [ ] Lista ou Tabela de Insumos ordenada alfabeticamente pelo Quicksort.
- [ ] Seleção de Lote (`cmbLote`).
- [ ] Tipo de Movimentação: Selecionar entre "Entrada (Reposição)" ou "Saída (Retirada para Produção)".
- [ ] Campo de Quantidade (`txtQuantidade`).
- [ ] Campo de Data da Operação (`dpData` ou data atual automática).
- [ ] Setor Solicitante (`cmbSetor`).
- [ ] Exibição automática do responsável (pega direto do usuário logado na sessão).
- [ ] Botão "Confirmar Movimentação" (`btnConfirmar`).
- [ ] Painel / Label de **Alerta Crítico**: deve ficar vermelho e destacar quando o saldo ficar menor ou igual ao mínimo!
- [ ] Botão "Voltar para o Menu" (`btnVoltar`).
- [ ] Aponte o controller para: `com.senai.bebidas.controller.MovimentacaoController`.

---

## ⚡ ETAPA 6: Ligando as Telas aos Controladores (JavaFX Controllers)

Agora vamos programar as ações dos botões em `com.senai.bebidas.controller`:

- [ ] **6.1. Programar o `LoginController.java`:**
  * Ao clicar em "Entrar", verificar se os campos estão vazios.
  * Chamar `FuncionarioDAO.autenticar(email, senha)`.
  * Se o retorno for nulo: exibir pop-up de alerta `AlertaUtil.mostrarErro("Usuário ou senha inválidos!")`.
  * Se der certo: guardar na `SessaoUsuario` e abrir a tela `home.fxml`.
- [ ] **6.2. Programar o `HomeController.java`:**
  * No método `initialize()`, buscar o usuário da `SessaoUsuario` e preencher os textos `lblBoasVindas` e `lblCargo`.
  * Configurar o botão "Insumos" para carregar a tela `insumos.fxml`.
  * Configurar o botão "Movimentação" para carregar a tela `movimentacao.fxml`.
  * Configurar o botão "Logout" para limpar a sessão e voltar para `login.fxml`.
- [ ] **6.3. Programar o `InsumosController.java`:**
  * Carregar a lista inicial de insumos do banco na `TableView`.
  * Implementar a barra de pesquisa: filtrar a tabela conforme o usuário digita no campo de texto.
  * Botão "Salvar": validar se os campos obrigatórios foram preenchidos antes de chamar o DAO (não deixar salvar campo vazio ou letras onde deve ser número).
  * Botão "Editar": carregar os dados da linha selecionada nos campos de texto para poder alterar.
  * Botão "Excluir": pedir confirmação `Tem certeza que deseja excluir?`. Se tiver vínculos de histórico, avisar amigavelmente que o item não pode ser apagado para manter a rastreabilidade.
  * Botão "Voltar": fechar a tela atual e reabrir a `home.fxml`.
- [ ] **6.4. Programar o `MovimentacaoController.java`:**
  * No método `initialize()`:
    1. Buscar a lista de insumos do banco de dados.
    2. Passar a lista pelo `OrdenacaoService.ordenarInsumosPorNome(lista)` (**Quicksort**).
    3. Exibir a lista já em ordem alfabética na interface!
  * Preencher o campo do responsável com o nome de quem está logado (sem deixar o usuário mudar manualmente).
  * Ao clicar em "Confirmar Movimentação":
    1. Validar se a quantidade é maior que zero.
    2. Se for **Saída**: verificar se o saldo atual é suficiente. Se o usuário tentar tirar mais do que tem no estoque, bloquear e avisar: `"Saldo insuficiente!"`.
    3. Gravar a movimentação no banco.
    4. **Regra de Alerta Crítico:** verificar se o saldo restante ficou menor ou igual à quantidade mínima. Se sim, exibir um pop-up de alerta chamativo:  
       `⚠️ ATENÇÃO: O insumo [Nome] atingiu o nível crítico de estoque! Quantidade atual: X (Mínimo seguro: Y).`
  * Botão "Voltar": retorna para a `home.fxml`.

---

## 🧪 ETAPA 7: Testes Manuais (Simulação da Apresentação)

Antes de entregar, a equipe deve abrir o sistema e simular exatamente o que o professor vai fazer.

Consulte o arquivo completo de testes: `docs/05_plano_de_testes.md`.

- [ ] **Teste 1 — Login com erro:** Digite um e-mail errado. O sistema deve mostrar aviso amigável sem travar.
- [ ] **Teste 2 — Login com sucesso:** Digite `carlos.silva@industriabebidas.com` e a senha correta. O sistema deve abrir a Home.
- [ ] **Teste 3 — Identificação do Usuário:** Olhe para o topo da Home. O nome "Carlos Silva" e o cargo aparecem corretamente?
- [ ] **Teste 4 — Busca de Insumos:** Na tela de insumos, digite "Açúcar". Apenas o açúcar deve aparecer na tabela.
- [ ] **Teste 5 — Validação de campos vazios:** Tente salvar um insumo sem digitar o nome. O sistema deve impedir e avisar.
- [ ] **Teste 6 — Ordem Alfabética na Movimentação:** Abra a tela de movimentação e confira se a lista está em ordem de A a Z.
- [ ] **Teste 7 — Retirada maior que o estoque:** Tente retirar 99999 unidades. O sistema deve barrar.
- [ ] **Teste 8 — Alerta de Nível Crítico:** Faça uma saída de material que deixe o saldo abaixo da quantidade mínima. O alerta visual vermelho/pop-up disparou?
- [ ] **Teste 9 — Logout:** Clique em "Sair" e confira se volta para a tela de login.

---

## 📦 ETAPA 8: Empacotamento Final para Entrega

Siga exatamente o que pede a página 5 do edital:

- [ ] **8.1. Criar uma pasta única para a entrega:**
  * Nomeie com o nome do aluno ou da equipe (ex: `ProjetoIntegrador_Modulo2_NomeAluno`).
- [ ] **8.2. Colocar dentro dela:**
  1. `Documento de Requisitos` (salvar em PDF a partir de `docs/01_requisitos.md`).
  2. `Modelo Entidade-Relacionamento` (imagem `.png` ou `.jpeg` de `Entregaveis/Banco de Dados/`).
  3. `Script SQL` (`BancodoProjeto.sql`).
  4. `Código-fonte do Sistema` (a pasta do projeto Maven com todos os `.java`, `.fxml` e `pom.xml`).
  5. `Documento de Testes` (salvar em PDF a partir de `docs/05_plano_de_testes.md`).
  6. `Documento de Infraestrutura` (salvar em PDF a partir de `docs/06_infraestrutura.md`).
- [ ] **8.3. Compactar a pasta:**
  * Clique com o botão direito na pasta -> `Enviar para` -> `Pasta compactada (zipada)` (formato `.zip`).

---

## 🎤 ETAPA 9: Preparação para a Banca Oral (Perguntas dos Professores)

O edital possui 11 perguntas obrigatórias que os avaliadores podem fazer para qualquer integrante da equipe. Todas as respostas prontas e explicadas estão no arquivo:  
`docs/07_preparacao_banca.md`.

**Destaques que você deve saber na ponta da língua:**
1. **Por que o banco se chama `bebidas_db`?**  
   *Resposta:* Porque foi uma exigência explícita e obrigatória do edital do SENAI.
2. **Qual algoritmo de ordenação vocês usaram e por quê?**  
   *Resposta:* Usamos o **Quicksort**. Ele foi escolhido por ser um algoritmo eficiente de divisão e conquista com complexidade média $O(n \log n)$, ideal para ordenar listas em memória rapidamente.
3. **Como vocês garantiram a rastreabilidade do estoque?**  
   *Resposta:* Nenhuma movimentação é apagada do banco (imutabilidade histórica). Toda movimentação salva automaticamente o ID do funcionário logado na sessão, a data/hora exata e o setor de destino.
4. **Como funciona o alerta de nível crítico?**  
   *Resposta:* A cada saída de material, o sistema compara o novo saldo com a quantidade mínima de segurança cadastrada. Se o saldo for menor ou igual ao mínimo, o JavaFX dispara imediatamente uma notificação visual para o operador.

---

*Equipe pronta e projeto nos trilhos! Sigam cada passo deste checklist e o sucesso na banca é garantido.* 🚀
