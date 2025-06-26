package clinicaVeterinaria.visao.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class BarraSuperior extends JPanel {
    public JButton btnHamburguer;

    public BarraSuperior(MainWindow mainWindow) { // Construtor da barra superior
        setLayout(new BorderLayout());
        setBackground(UIConstants.SECONDARY);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        
        Icon iconHamburguer = mainWindow.loadIcon("/images/IconeMenu.png");
        btnHamburguer = new JButton(iconHamburguer);
        btnHamburguer.setToolTipText("Menu");
        btnHamburguer.setPreferredSize(new Dimension(40, 40)); // Tamanho do botão
        btnHamburguer.setBackground(UIConstants.SECONDARY); 
        btnHamburguer.setFocusPainted(false); // Remove o foco do botão
        btnHamburguer.setBorder(BorderFactory.createEmptyBorder()); // Remove a borda
        btnHamburguer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // deixa o cursor como maozinha quando ta em cima do botão
        btnHamburguer.addMouseListener(new MouseAdapter() { // esse evento é para mudar a cor do botão quando o mouse entra e sai
            @Override public void mouseEntered(MouseEvent e) {
                btnHamburguer.setBackground(UIConstants.ACCENT); 
            }
            @Override public void mouseExited(MouseEvent e) {
                btnHamburguer.setBackground(UIConstants.SECONDARY);
            }
        });
        add(btnHamburguer, BorderLayout.WEST);
    }
}
