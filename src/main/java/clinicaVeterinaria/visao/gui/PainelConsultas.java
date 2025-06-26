package clinicaVeterinaria.visao.gui;

import javax.swing.*;
import java.awt.*;

public class PainelConsultas extends JPanel {
    public PainelConsultas() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.WHITE);
        add(new JLabel("Gestão de Consultas", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
