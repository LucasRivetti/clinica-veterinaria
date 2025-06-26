package clinicaVeterinaria.visao.gui;

import javax.swing.*;
import java.awt.*;

public class PainelProcedimentos extends JPanel {
    public PainelProcedimentos() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.WHITE);
        add(new JLabel("Gestão de Procedimentos", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
