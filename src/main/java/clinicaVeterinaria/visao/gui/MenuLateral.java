package clinicaVeterinaria.visao.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.List;
import java.util.ArrayList;

public class MenuLateral extends JPanel {
    private final List<JButton> botoesMenu = new ArrayList<>(); // Lista para armazenar os botões do menu
    private String selecaoAtual = "Home"; // A seleção atual do menu, iniciada como "Home"
    private final MainWindow mainWindow; 

    public MenuLateral(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setBackground(UIConstants.SECONDARY);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // todas os icones dos botoes do menuzin
        Icon iconHome          = mainWindow.loadIcon("/images/IconeHome.png"); 
        Icon iconClientes      = mainWindow.loadIcon("/images/IconeClientes.png");
        Icon iconVeterinarios  = mainWindow.loadIcon("/images/IconeVeterinarios.png");
        Icon iconAnimais       = mainWindow.loadIcon("/images/IconeAnimais.png");
        Icon iconProcedimentos = mainWindow.loadIcon("/images/IconeProcedimentos.png");
        Icon iconConsultas     = mainWindow.loadIcon("/images/IconeConsultas.png");

        // criacoa dos botoes
        botoesMenu.add(mainWindow.criarBotaoMenu("Home",          iconHome));
        botoesMenu.add(mainWindow.criarBotaoMenu("Clientes",      iconClientes));
        botoesMenu.add(mainWindow.criarBotaoMenu("Veterinários",  iconVeterinarios));
        botoesMenu.add(mainWindow.criarBotaoMenu("Animais",       iconAnimais));
        botoesMenu.add(mainWindow.criarBotaoMenu("Procedimentos", iconProcedimentos));
        botoesMenu.add(mainWindow.criarBotaoMenu("Consultas",     iconConsultas));

        for (JButton btn : botoesMenu) { // Adiciona cada botão à lista de botões do menu
            add(btn);
            add(Box.createVerticalStrut(5));
        }
    }

    public List<JButton> getBotoesMenu() { // dessa forma ele retorna a lista de botoes do menu para a MainWindow
        return botoesMenu;
    }
}
