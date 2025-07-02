package clinicaVeterinaria;

import javax.swing.SwingUtilities;
import clinicaVeterinaria.visao.gui.MainWindow;

public class Programa {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow janela = new MainWindow();
            janela.setVisible(true);
        });
    }
}
