package clinicaVeterinaria.visao.gui;

import javax.swing.*;
import java.awt.*;

public class PainelAnimais extends JPanel {
    public PainelAnimais() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.WHITE);
        add(new JLabel("Gestão de Animais", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}

//List<Cliente> clientes = banco.getClientes().listar();
