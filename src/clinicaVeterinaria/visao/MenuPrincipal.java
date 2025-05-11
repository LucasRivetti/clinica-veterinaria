package clinicaVeterinaria.visao;

import java.util.Scanner;
import clinicaVeterinaria.persistencia.BancoDeDados;

public class MenuPrincipal {

    private BancoDeDados bancoDeDados;
    private Scanner scanner;

    public MenuPrincipal(BancoDeDados bancoDeDados) {
        this.bancoDeDados = bancoDeDados;
        this.scanner = new Scanner(System.in); 
    }

    public void exibirMenu() {
  
        while (true) {
            System.out.println("=====================================================================================");
            System.out.println( 
            "░█████╗░██╗░░░░░██╗███╗░░██╗██╗░█████╗░░█████╗░\n" +
            "██╔══██╗██║░░░░░██║████╗░██║██║██╔══██╗██╔══██╗\n" +
            "██║░░╚═╝██║░░░░░██║██╔██╗██║██║██║░░╚═╝███████║\n" +
            "██║░░██╗██║░░░░░██║██║╚████║██║██║░░██╗██╔══██║\n" +
            "╚█████╔╝███████╗██║██║░╚███║██║╚█████╔╝██║░░██║\n" +
            "░╚════╝░╚══════╝╚═╝╚═╝░░╚══╝╚═╝░╚════╝░╚═╝░░╚═╝\n" +
            "██╗░░░██╗███████╗████████╗███████╗██████╗░██╗███╗░░██╗░█████╗░██████╗░██╗░█████╗░\n" +
            "██║░░░██║██╔════╝╚══██╔══╝██╔════╝██╔══██╗██║████╗░██║██╔══██╗██╔══██╗██║██╔══██╗\n" +
            "╚██╗░██╔╝█████╗░░░░░██║░░░█████╗░░██████╔╝██║██╔██╗██║███████║██████╔╝██║███████║\n" +
            "░╚████╔╝░██╔══╝░░░░░██║░░░██╔══╝░░██╔══██╗██║██║╚████║██╔══██║██╔══██╗██║██╔══██║\n" +
            "░░╚██╔╝░░███████╗░░░██║░░░███████╗██║░░██║██║██║░╚███║██║░░██║██║░░██║██║██║░░██║\n" +
            "░░░╚═╝░░░╚══════╝░░░╚═╝░░░╚══════╝╚═╝░░╚═╝╚═╝╚═╝░░╚══╝╚═╝░░╚═╝╚═╝░░╚═╝╚═╝╚═╝░░╚═╝");
System.out.println("=====================================================================================");
            System.out.println("► 1. Menu Veterinário");
            System.out.println("► 2. Menu Animal");
            System.out.println("► 3. Menu Consulta");
            System.out.println("► 4. Menu Cliente");
            System.out.println("► 5. Sair");
            System.out.print("► Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    MenuVeterinario menuVeterinario = new MenuVeterinario(bancoDeDados);
                    menuVeterinario.exibirMenu();
                    System.out.print("\033[H\033[2J");   
                    System.out.flush();   
                    break;
                case 2:
                    MenuAnimal menuAnimal = new MenuAnimal(bancoDeDados);
                    menuAnimal.exibirMenu();
                    System.out.print("\033[H\033[2J");   
                    System.out.flush();   
                    break;
                case 3:
                    MenuConsulta menuConsulta = new MenuConsulta(bancoDeDados);
                    menuConsulta.exibirMenu();
                    System.out.print("\033[H\033[2J");   
                    System.out.flush();   
                    break;
                case 4:
                    MenuCliente menuCliente = new MenuCliente(bancoDeDados);
                    menuCliente.exibirMenu();
                    System.out.print("\033[H\033[2J");   
                    System.out.flush();   
                    break;
                case 5:
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
