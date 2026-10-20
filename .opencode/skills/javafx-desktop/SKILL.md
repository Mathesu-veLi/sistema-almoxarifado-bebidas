---
name: javafx-desktop
description: Diretrizes de desenvolvimento para interfaces JavaFX, FXML, Controllers, estilos CSS e integração no Apache NetBeans.
---

# JavaFX Desktop Skill

Esta skill orienta o desenvolvimento das 4 telas desktop solicitadas pelo edital do SENAI:

## 1. Organização dos Arquivos de Interface
No padrão Maven/NetBeans:
* `src/main/resources/fxml/`
  * `LoginView.fxml` (ou `login.fxml`)
  * `HomeView.fxml`
  * `InsumosView.fxml`
  * `MovimentacaoView.fxml`
* `src/main/resources/css/`
  * `styles.css` (tema corporativo: cores sóbrias, azul/cinza/verde, tipografia limpa)
* `src/main/java/com/senai/bebidas/controller/`
  * `LoginController.java`
  * `HomeController.java`
  * `InsumosController.java`
  * `MovimentacaoController.java`

## 2. Requisitos de Cada Tela

### Tela 1: Autenticação (`LoginView`)
* Campos: `TextField txtEmail`, `PasswordField txtSenha`, `Button btnEntrar`.
* Validação: Verifica campos vazios antes de chamar o `AuthService`.
* Credenciais inválidas: Dispara `AlertaUtil.mostrarErro("Acesso Negado", "Usuário ou senha incorretos.")`.
* Sucesso: Guarda o `Funcionario` na classe `SessaoUsuario` e navega para `HomeView`.

### Tela 2: Inicial (`HomeView`)
* Exibe: "Bem-vindo, {nome do usuário} ({cargo})".
* Ações:
  * Botão "Gerenciamento de Insumos" -> abre `InsumosView`.
  * Botão "Movimentação de Materiais" -> abre `MovimentacaoView`.
  * Botão "Sair (Logout)" -> limpa `SessaoUsuario` e retorna para `LoginView`.

### Tela 3: Gerenciamento de Insumos (`InsumosView`)
* Componente principal: `TableView<InsumoDTO>` com colunas (Nome, Categoria/Tipo, Qtd Mínima, Lotes ativos, Validade mais próxima).
* Campo de busca dinâmica: `TextField txtPesquisa` com listener para filtrar a lista conforme o usuário digita.
* Formulário CRUD: Modal ou painel lateral para inclusão/edição de insumo com validação prévia.
* Validações obrigatórias:
  * Nome não pode ser vazio.
  * Quantidade mínima deve ser maior ou igual a zero.
* Botão "Voltar para Home".

### Tela 4: Movimentação de Materiais (`MovimentacaoView`)
* **Ordenação Obrigatória:** A lista/combobox de insumos DEVE ser ordenada pelo algoritmo `Quicksort` implementado no `OrdenacaoService`.
* Campos:
  * Insumo / Lote (seleção).
  * Tipo de Movimentação (Entrada / Reposição ou Saída / Retirada).
  * Quantidade movimentada (inteiro positivo > 0).
  * Setor Solicitante (ComboBox com setores).
  * Data da Operação (DatePicker ou data/hora atual automática).
  * Responsável: Preenchido automaticamente com o usuário autenticado da `SessaoUsuario`.
* **Alerta Automático de Estoque Crítico:**
  * Se após a saída o saldo do insumo ficar menor que a `quantidade_minima`, emitir aviso visual imediato (`AlertaUtil.mostrarAviso(...)`) e destacar a linha em vermelho/laranja.
* Botão "Voltar para Home".

## 3. Padrão de Diálogos (`AlertaUtil`)
Utilize caixas de diálogo nativas do JavaFX:
```java
public class AlertaUtil {
    public static void mostrarInformacao(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void mostrarAviso(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText("Atenção: Nível Crítico de Estoque");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
```
