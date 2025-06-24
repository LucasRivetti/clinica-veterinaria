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


        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem mCliente = new JMenuItem("Clientes");
        JMenuItem mVeterinario = new JMenuItem("Veterinários");
        JMenuItem mAnimal = new JMenuItem("Animais");
        JMenuItem mProcedimento = new JMenuItem("Procedimentos");
        JMenuItem mConsulta = new JMenuItem("Consultas");

        menu.add(mCliente);
        menu.add(mVeterinario);
        menu.add(mAnimal);
        menu.add(mProcedimento);
        menu.add(mConsulta);
        menuBar.add(menu);
        setJMenuBar(menuBar);


        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // aqui ta a base dos paineis, tudo isso ai e placeholder vao mudando conforme forem fazendo os paineis e tbm deixem essa porra mais bonita fiz so a base funcional
        mainPanel.add(new JLabel("Bem-vindo à Clínica Veterinária! Selecione uma opção no menu.", SwingConstants.CENTER), "Home");
        mainPanel.add(new ClientePanel(), "Clientes");
        mainPanel.add(new JLabel("Painel Veterinários"), "Veterinários");
        mainPanel.add(new JLabel("Painel Animais"), "Animais");
        mainPanel.add(new JLabel("Painel Procedimentos"), "Procedimentos");
        mainPanel.add(new JLabel("Painel Consultas"), "Consultas");

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}
