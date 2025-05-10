package clinicaVeterinaria;

import clinicaVeterinaria.visao.MenuPrincipal;
import clinicaVeterinaria.persistencia.BancoDeDados;

public class Programa {
    public static void main(String[] args) {
        BancoDeDados banco = new BancoDeDados();
        MenuPrincipal menu = new MenuPrincipal(banco);
        menu.exibirMenu();
    }
}
