package clinicaVeterinaria.visao.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PainelHome extends JPanel { 
    public PainelHome(MainWindow mainWindow) {
        setLayout(new BorderLayout());
        setBackground(UIConstants.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Bem-vindo à Clínica Veterinária", SwingConstants.CENTER); 
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(UIConstants.PRIMARY);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 24, 0));
        add(lblTitulo, BorderLayout.NORTH);

        BufferedImage bannerImg = mainWindow.loadImage("/images/HomeBanner.png");
        MainWindow.ImagePanel bannerPanel = new MainWindow.ImagePanel(bannerImg);
        bannerPanel.setBackground(UIConstants.WHITE);
        add(bannerPanel, BorderLayout.CENTER);
    }
}
