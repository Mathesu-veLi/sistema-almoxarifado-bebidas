package view;

import javax.swing.*;
import java.awt.*;

public class TelaMenu extends JFrame {

    // ESSA E A TELA DE MENU FASE 1, TA BAIXA FIDELIDADE E FEIO, SIM, MAS E SO PARA VER TESTANDO DEPOIS EU LAPIDO

    // guarda o nome que voce deu no login (Atende RF-03)
    private String nomeUsuarioLogado;

    public TelaMenu(String nomeUsuario) {
        this.nomeUsuarioLogado = nomeUsuario;
        initComponents();
    }

    // aqui e o construtor padraozinho pq isso ta construindo então é so para ter uma visão de como vai ficar
    public TelaMenu() {
        this.nomeUsuarioLogado = "Operador";
        initComponents();
    }

    private void initComponents() {
        setTitle("Almoxarifado de Bebidas - Menu Principal");
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // isso e pra centralizar na tela blz
        setLayout(new BorderLayout());

        // painel superior
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        headerPanel.setBackground(new Color(245, 245, 245));

        // o nome de quem ta logado
        JLabel lblBoasVindas = new JLabel("Bem-vindo(a), " + nomeUsuarioLogado);
        lblBoasVindas.setFont(new Font("Segoe UI", Font.BOLD, 15));

        // botao de sair
        JButton btnLogout = new JButton("Sair");
        btnLogout.setFocusable(false);
        btnLogout.addActionListener(e -> acaoLogout());

        headerPanel.add(lblBoasVindas, BorderLayout.WEST);
        headerPanel.add(btnLogout, BorderLayout.EAST);

        // O painel do centro é aqui o
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 15, 15));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Botão modulo de insumos
        JButton btnInsumos = new JButton("Gerenciamento de Insumos");
        btnInsumos.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnInsumos.setFocusable(false);
        btnInsumos.addActionListener(e -> abrirGerenciamentoInsumos());

        // Botão do modulo de movimentação no estoque
        JButton btnMovimentacoes = new JButton("Movimentação de Estoque");
        btnMovimentacoes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnMovimentacoes.setFocusable(false);
        btnMovimentacoes.addActionListener(e -> abrirMovimentacaoEstoque());

        centerPanel.add(btnInsumos);
        centerPanel.add(btnMovimentacoes);

        // aqui coloca os paineis na janela
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    // isso aqui é pro botão de sair funcionar
    private void acaoLogout() {
        Object[] opcoes = {"Sim", "Não"};

        int confirmacao = JOptionPane.showOptionDialog(
                this,
                "Deseja encerrar sua sessão?",
                "Confirmar saída",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0] // Botão "Sim" vem selecionado por padrão
        );

        if (confirmacao == 0) { // 0 representa a primeira opção ("Sim")
            new TelaLogin().setVisible(true); // redireciona pro login dps q voce sai
            this.dispose(); // Fecha a tela de menu
        }
    }

    // aqui vai funcionar a tela de redirecionar pros insumos,  (RF-05)
    private void abrirGerenciamentoInsumos() {
        JOptionPane.showMessageDialog(this, "Indo para tela de insumos!!!!!");
        // Em breve: new TelaInsumos(nomeUsuarioLogado).setVisible(true);
    }

    // e essa e a tela das movimentações (RF-05)
    private void abrirMovimentacaoEstoque() {
        JOptionPane.showMessageDialog(this, "Indo para a movimentação no estoque!!!!!!!");
        // Em breve: new TelaMovimentacao(nomeUsuarioLogado).setVisible(true);
    }

    // aqui e pra nois testar e ver a belezura rodando
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaMenu("Gabriel").setVisible(true);
        });
    }
}