---
name: swing-desktop
description: Diretrizes de desenvolvimento para interfaces Swing/JFrame, GUI Builder do NetBeans, JTable, JOptionPane e integração do frontend desktop com a API REST Spring Boot.
---

# Swing Desktop Skill (frontend JFrame)

Esta skill orienta o desenvolvimento das 4 telas desktop solicitadas pelo edital do SENAI, agora em **Swing/JFrame** consumindo o **backend Spring Boot**.

## 1. Organização dos Arquivos do Frontend

Projeto Maven independente em `frontend/`:

* `src/main/java/com/senai/bebidas/`
  * `app/Main.java` — classe `main` que inicia o frontend.
  * `view/`
    * `LoginFrame.java`
    * `HomeFrame.java`
    * `InsumosFrame.java`
    * `MovimentacaoFrame.java`
  * `controller/`
    * `LoginController.java`
    * `HomeController.java`
    * `InsumosController.java`
    * `MovimentacaoController.java`
  * `service/`
    * `ApiClient.java` (HTTP genérico + Jackson)
    * `AuthApiService.java`
    * `InsumoApiService.java`
    * `MovimentacaoApiService.java`
  * `model/` — DTOs espelhados do backend.
  * `util/`
    * `SessaoUsuario.java`
    * `AlertaUtil.java`
    * `ApiConfig.java`
* `src/main/resources/images/` — ícones e logomarca.

> **Regra:** o frontend não importa driver JDBC nem abre conexão com MySQL. Toda operação passa por `service/`.

## 2. Padrão das Telas (JFrame)

### Regras gerais
* Cada tela é uma classe que estende `JFrame` e foi desenhada no **GUI Builder do NetBeans** (`GroupLayout`).
* Não editar manualmente o bloco `initComponents()` gerado pelo NetBeans; a lógica fica nos listeners/controllers.
* Centralizar a janela: `setLocationRelativeTo(null)`.
* `setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)` nas telas secundárias; `EXIT_ON_CLOSE` apenas no `LoginFrame`.
* Usar `EventQueue.invokeLater(...)` no `main` para iniciar a UI.
* Toda chamada HTTP deve rodar **fora da EDT**. Use `SwingWorker` (ou `CompletableFuture` + `SwingUtilities.invokeLater`) para não travar a interface.

### Tela 1: Login (`LoginFrame`)
* Componentes: `JTextField txtEmail`, `JPasswordField txtSenha`, `JButton btnEntrar`, `JLabel lblMensagemErro`.
* Validação local: campos não vazios antes de chamar a API.
* Sucesso: guarda o `FuncionarioDTO` em `SessaoUsuario`, abre `HomeFrame` e descarta/fecha o login.
* Falha: `AlertaUtil.mostrarErro("Acesso Negado", "E-mail ou senha inválidos.")`; limpa somente a senha e devolve o foco.

### Tela 2: Home (`HomeFrame`)
* Componentes: `JLabel lblBoasVindas`, `JLabel lblCargo`, `JButton btnInsumos`, `JButton btnMovimentacao`, `JButton btnSair`.
* Preenche nome e cargo a partir de `SessaoUsuario.getUsuarioLogado()`.
* Navegação: abre `InsumosFrame` / `MovimentacaoFrame` (pode ser modal ou não) e `btnSair` limpa a sessão e reabre o login.

### Tela 3: Gerenciamento de Insumos (`InsumosFrame`)
* `JTable` com `DefaultTableModel` e colunas: ID, Nome, Unidade, Qtd. Mínima, Saldo, Situação.
* `JTextField txtPesquisa` com `DocumentListener` (ou botão "Pesquisar") para filtrar via `GET /api/insumos?termo=`.
* Formulário: `txtNome`, `txtDescricao`, `txtVolume`, `txtUnidade`, `txtQtdMinima`.
* Botões: `btnSalvar`, `btnEditar`, `btnExcluir`, `btnLimpar`, `btnVoltar`.
* Validações antes do envio: nome com no mínimo 3 caracteres; quantidade mínima inteira `>= 0`.
* Exclusão: confirmar com `JOptionPane.showConfirmDialog`; se a API retornar conflito de vínculo, exibir a mensagem amigável.

### Tela 4: Movimentação de Materiais (`MovimentacaoFrame`)
* `JComboBox<InsumoDTO> cbInsumos` carregado por `GET /api/insumos/ordenados`.
  * **A ordem alfabética vem pronta do backend (Quicksort).** O frontend não deve reordenar com `Collections.sort()`.
* `JComboBox<LoteInsumoDTO> cbLotes`: recarregado ao selecionar o insumo (`GET /api/lotes?tipoInsumoId=`).
* `JComboBox<TipoMovimentacaoDTO> cbTipo`, `JComboBox<SetorDTO> cbSetor`.
* `JSpinner` (ou `JFormattedTextField`) `txtQuantidade` com modelo numérico mínimo 1.
* `JLabel lblResponsavel`: preenchido com o usuário logado, sem edição.
* `JButton btnConfirmar`: envia `POST /api/movimentacoes`.
* **Alerta de nível crítico:** se `MovimentacaoResponse.alertaCritico == true`, exibir `AlertaUtil.mostrarAviso(...)` com destaque visual.
* `JButton btnVoltar`: retorna à Home.

## 3. Diálogos (`AlertaUtil`)

Centralize tudo em `javax.swing.JOptionPane`:

```java
package com.senai.bebidas.util;

import javax.swing.JOptionPane;

public final class AlertaUtil {

    private AlertaUtil() { }

    public static void mostrarInformacao(String titulo, String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void mostrarErro(String titulo, String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.ERROR_MESSAGE);
    }

    public static void mostrarAviso(String titulo, String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.WARNING_MESSAGE);
    }

    public static boolean confirmar(String titulo, String mensagem) {
        int resposta = JOptionPane.showConfirmDialog(
                null, mensagem, titulo, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return resposta == JOptionPane.YES_OPTION;
    }
}
```

## 4. Cliente HTTP (`ApiClient`)

Use `java.net.http.HttpClient` + Jackson. Configuração central:

```java
package com.senai.bebidas.util;

public final class ApiConfig {
    public static final String BASE_URL = "http://localhost:8080/api";
    private ApiConfig() { }
}
```

```java
package com.senai.bebidas.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.bebidas.util.ApiConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {

    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T get(String path, Class<T> tipo) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.BASE_URL + path))
                .GET()
                .build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new RuntimeException(mapper.readTree(response.body()).path("mensagem").asText());
        }
        return mapper.readValue(response.body(), tipo);
    }

    public <T> T post(String path, Object corpo, Class<T> tipo) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.BASE_URL + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(corpo)))
                .build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new RuntimeException(mapper.readTree(response.body()).path("mensagem").asText());
        }
        return mapper.readValue(response.body(), tipo);
    }
}
```

* Para listas, use `mapper.getTypeFactory().constructCollectionType(List.class, InsumoDTO.class)`.
* Sempre trate `java.net.ConnectException` com mensagem: "Backend indisponível. Inicie o Spring Boot na porta 8080."
* Jackson deve ignorar campos desconhecidos: `mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)`.

## 5. Sessão (`SessaoUsuario`)

```java
package com.senai.bebidas.util;

import com.senai.bebidas.model.FuncionarioDTO;

public final class SessaoUsuario {

    private static FuncionarioDTO usuarioLogado;

    private SessaoUsuario() { }

    public static void iniciarSessao(FuncionarioDTO funcionario) {
        usuarioLogado = funcionario;
    }

    public static FuncionarioDTO getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void encerrarSessao() {
        usuarioLogado = null;
    }

    public static boolean isAutenticado() {
        return usuarioLogado != null;
    }
}
```

## 6. Execução no NetBeans

1. Abrir `frontend/pom.xml` no NetBeans (`File > Open Project`).
2. Confirmar o JDK 17 em `Project Properties > Sources`.
3. Iniciar o backend antes do frontend (`mvn spring-boot:run` em `backend/`).
4. Executar a classe `com.senai.bebidas.app.Main`.

> O GUI Builder do NetBeans exige que o formulário seja um `JFrame` com código gerado no `initComponents()`. Não misture `setLayout(null)` manualmente; use `GroupLayout` gerado pela ferramenta.
