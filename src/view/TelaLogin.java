package view;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    // ta baixa fidelidade.... feio sim, porem funcional, quando for resolvendo tudo, principalmente conectar o banco com o java
    // eu vou melhorar ele, por agora calma

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnSair;

    public TelaLogin() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Almoxarifado de Bebidas - Autenticação");
        setSize(400, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setResizable(false);

        // o painel feinho principal
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título / Cabeçalho
        JLabel lblTitulo = new JLabel("Acesso ao Sistema", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        // email ou usuario
        JLabel lblEmail = new JLabel("E-mail:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(lblEmail, gbc);

        txtEmail = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtEmail, gbc);

        // a senha
        JLabel lblSenha = new JLabel("Senha:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblSenha, gbc);

        txtSenha = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(txtSenha, gbc);

        // o painel dos botoes
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnEntrar = new JButton("Entrar");
        btnSair = new JButton("Sair");

        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEntrar.addActionListener(e -> efetuarLogin());

        btnSair.addActionListener(e -> System.exit(0));

        panelBotoes.add(btnSair);
        panelBotoes.add(btnEntrar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(panelBotoes, gbc);

        add(panel);
    }

    // a logica para autenticar
    private void efetuarLogin() {
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        // validar campo vazio, ou seja, se deixar o login ou senha vazios naaao vai pertubar o banco de dados
        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha todos os campos!",
                    "Campos Obrigatórios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // simular logins, CALMA! essa lambança de if e else vai sumir quando conectar o banco de dados no codigo
        // por agora usa um desses emails ai q ta la no sql
        if (email.equalsIgnoreCase("joao@bebidas.com") && senha.equals("senha123")) {
            abrirMenuPrincipal("João Silva");
        } else if (email.equalsIgnoreCase("maria@bebidas.com") && senha.equals("senha456")) {
            abrirMenuPrincipal("Maria Souza");
        } else if (email.equalsIgnoreCase("carlos@bebidas.com") && senha.equals("senha789")) {
            abrirMenuPrincipal("Carlos Eduardo");
        } else if (email.equalsIgnoreCase("gabriel@bebidas.com") && senha.equals("123")) {
            abrirMenuPrincipal("Gabriel");
        } else {
            // rf 2, da erro e manda tentar denovo
            JOptionPane.showMessageDialog(this,
                    "E-mail ou senha inválidos. Tente novamente.",
                    "Falha",
                    JOptionPane.ERROR_MESSAGE);
            txtSenha.setText("");
            txtSenha.requestFocus();
        }
    }

    private void abrirMenuPrincipal(String nomeUsuario) {
        JOptionPane.showMessageDialog(this, "Login realizado com sucesso! Bem-vindo, " + nomeUsuario);
        new TelaMenu(nomeUsuario).setVisible(true); // aqui abre a tela de menu
        this.dispose(); // fecha a tela de login
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}