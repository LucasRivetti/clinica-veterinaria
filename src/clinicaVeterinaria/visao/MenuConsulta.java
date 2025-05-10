package clinicaVeterinaria.visao;

import clinicaVeterinaria.persistencia.BancoDeDados;
import clinicaVeterinaria.modelo.Consulta;

import java.util.Scanner;

public class MenuConsulta {

    private BancoDeDados bancoDeDados;

    public MenuConsulta(BancoDeDados bancoDeDados) {
        this.bancoDeDados = bancoDeDados;
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\033[H\033[2J");   
        System.out.flush();   
        while (true) {
            System.out.println("\n--- Menu Consulta ---");
            System.out.println("1. Cadastrar Nova Consulta");
            System.out.println("2. Listar Consultas");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    cadastrarConsulta(scanner);
                    break;
                case 2:
                    listarConsultas();
                    break;
                case 3:
                    System.out.println("Saindo para o menu principal...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void cadastrarConsulta(Scanner scanner) {
        System.out.println("\nCadastrar Nova Consulta:");

        System.out.print("ID da Consulta: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        Consulta consulta = new Consulta(id, null, null, null, new java.util.Date(), descricao);

        bancoDeDados.getConsultas().adicionar(consulta);
        System.out.println("Consulta cadastrada com sucesso!");
    }

    private void listarConsultas() {
        System.out.println("\nListar Consultas:");
        System.out.println(bancoDeDados.getConsultas().toString());
    }
}
