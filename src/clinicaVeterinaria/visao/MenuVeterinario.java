package clinicaVeterinaria.visao;

import java.util.Scanner;
import clinicaVeterinaria.modelo.Veterinario;
import clinicaVeterinaria.persistencia.BancoDeDados;

public class MenuVeterinario {

    private BancoDeDados bancoDeDados;

    public MenuVeterinario(BancoDeDados bancoDeDados) {
        this.bancoDeDados = bancoDeDados;
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\033[H\033[2J");   
        System.out.flush();   
        while (true) {
            System.out.println("\n--- Menu Veterinário ---");
            System.out.println("1. Adicionar Veterinário");
            System.out.println("2. Listar Veterinários");
            System.out.println("3. Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    adicionarVeterinario(scanner);
                    break;
                case 2:
                    listarVeterinarios();
                    break;
                case 3:
                    System.out.println("Voltando ao menu principal...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void adicionarVeterinario(Scanner scanner) {
        
        System.out.println("Digite o ID do Veterinário:");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        System.out.println("Digite o nome do Veterinário:");
        String nome = scanner.nextLine();

        System.out.println("Digite o CRMV do Veterinário:");
        String crmv = scanner.nextLine();

        String especialidade = ""; 
        String telefone = ""; 
        String email = ""; 

        Veterinario veterinario = new Veterinario(id, nome, crmv, especialidade, telefone, email);

        bancoDeDados.getVeterinarios().adicionar(veterinario);
        System.out.println("Veterinário adicionado com sucesso!");
    }

    private void listarVeterinarios() {
        System.out.println("\n--- Lista de Veterinários ---");
        String veterinariosList = bancoDeDados.getVeterinarios().toString();
        if (veterinariosList.isEmpty()) {
            System.out.println("Não há veterinários cadastrados.");
        } else {
            System.out.println(veterinariosList);
        }
    }
}
