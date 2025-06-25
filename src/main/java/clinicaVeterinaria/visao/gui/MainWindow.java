// src/clinicaVeterinaria/visao/gui/MainWindow.java
package clinicaVeterinaria.visao.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainWindow() {
        setTitle("Clínica Veterinária");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // aplica cor de fundo geral
        getContentPane().setBackground(UIConstants.BACKGROUND);

        // Barra de menu estilizada
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(UIConstants.SECONDARY);
        menuBar.setForeground(UIConstants.WHITE);

        JMenu menu = new JMenu("Menu");
        menu.setForeground(UIConstants.TEXT);

        JMenuItem mCliente      = new JMenuItem("Clientes");
        JMenuItem mVeterinario  = new JMenuItem("Veterinários");
        JMenuItem mAnimal       = new JMenuItem("Animais");
        JMenuItem mProcedimento = new JMenuItem("Procedimentos");
        JMenuItem mConsulta     = new JMenuItem("Consultas");
        for (JMenuItem item : new JMenuItem[]{mCliente, mVeterinario, mAnimal, mProcedimento, mConsulta}) {
            item.setForeground(UIConstants.TEXT);
            item.setBackground(UIConstants.SECONDARY);
        }

        menu.add(mCliente);
        menu.add(mVeterinario);
        menu.add(mAnimal);
        menu.add(mProcedimento);
        menu.add(mConsulta);
        menuBar.add(menu);
        setJMenuBar(menuBar);


        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(UIConstants.BACKGROUND);

          // aqui ta a base dos paineis, tudo isso ai e placeholder vao mudando conforme forem fazendo os paineis e tbm deixem essa porra mais bonita fiz so a base funcional
        JLabel homeLabel = new JLabel(
            "<html><div style='text-align:center;'>Bem-vindo à Clínica Veterinária!<br/>Selecione uma opção no menu.</div></html>",
            SwingConstants.CENTER
        );
        homeLabel.setForeground(UIConstants.TEXT);
        mainPanel.add(homeLabel, "Home");

        ClientePanel clientePanel = new ClientePanel();
        clientePanel.setBackground(UIConstants.WHITE);
        mainPanel.add(clientePanel, "Clientes");

        VeterinarioPanel vetPanel = new VeterinarioPanel();
        vetPanel.setBackground(UIConstants.WHITE);
        mainPanel.add(vetPanel, "Veterinários");

        // placeholders
        mainPanel.add(makePlaceholder("Painel Animais"), "Animais");
        mainPanel.add(makePlaceholder("Painel Procedimentos"), "Procedimentos");
        mainPanel.add(makePlaceholder("Painel Consultas"), "Consultas");

        add(mainPanel, BorderLayout.CENTER);

         // troca de painel quando tu clicar no menu sem ficar abrindo novas janelas
        ActionListener switchPanel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                cardLayout.show(mainPanel, cmd);
            }
        };
        mCliente.addActionListener(switchPanel);
        mVeterinario.addActionListener(switchPanel);
        mAnimal.addActionListener(switchPanel);
        mProcedimento.addActionListener(switchPanel);
        mConsulta.addActionListener(switchPanel);
    }

    // método auxiliar para placeholders
    private JPanel makePlaceholder(String text) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(UIConstants.WHITE);
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setForeground(UIConstants.TEXT);
        p.add(lbl, BorderLayout.CENTER);
        return p;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}
