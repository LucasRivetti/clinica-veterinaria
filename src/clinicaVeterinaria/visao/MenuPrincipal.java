package clinicaVeterinaria.visao;

import clinicaVeterinaria.persistencia.BancoDeDados;
import java.util.Scanner;

public class MenuPrincipal {

    private BancoDeDados bancoDeDados;
    private Scanner scanner;

    public MenuPrincipal(BancoDeDados bancoDeDados) {
        this.bancoDeDados = bancoDeDados;
        this.scanner = new Scanner(System.in); 
    }

    public void exibirMenu() {
        
        int bdinicializado = 0;
  
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
            System.out.println("► 5. Menu Procedimento");
            System.out.println("► 6. Sair");
            System.out.println("► 7. Banco de Dados Teste");
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
                    MenuProcedimento menuProcedimento = new MenuProcedimento(bancoDeDados);
                    menuProcedimento.exibirMenu();
                    System.out.print("\033[H\033[2J");   
                    System.out.flush(); 
                    break;
                case 6:
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    return;
                case 7:
                    if (bdinicializado == 0) {
                        TesteBd testeBd = new TesteBd(bancoDeDados);
                        testeBd.inicializarBanco(bancoDeDados);

                        System.out.print("\033[H\033[2J");   
                        System.out.flush();   
                        System.out.println("Banco de dados inicializado com sucesso!");
                        System.out.println("\n");
                        bdinicializado = 1;
                        break;
                    } else {
                        System.out.println("Banco de dados já inicializado.");
                        break;
                    }
                default:
                    System.out.print("\033[H\033[2J");   
                    System.out.flush();   
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
