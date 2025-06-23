package clinicaVeterinaria;

import clinicaVeterinaria.persistencia.BancoDeDados;
import clinicaVeterinaria.visao.MenuPrincipal;

public class Programa {
    public static void main(String[] args) {
        BancoDeDados banco = new BancoDeDados();
        MenuPrincipal menu = new MenuPrincipal(banco);
        menu.exibirMenu();
    }
}
