package clinicaVeterinaria.visao;

import java.util.Scanner;
import clinicaVeterinaria.modelo.Veterinario;
import clinicaVeterinaria.persistencia.BancoDeDados;
import clinicaVeterinaria.persistencia.IdInexistenteExcecao;

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
            System.out.println("2. Editar Veterinário");
            System.out.println("3. Remover Veterinário");
            System.out.println("4. Buscar por ID");
            System.out.println("5. Listar Veterinários");
            System.out.println("6. Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    adicionarVeterinario(scanner);
                    break;
                case 2:
                    editarVeterinario(scanner);
                    break;
                case 3:
                    removerVeterinario(scanner);
                    break;
                case 4:
                    buscarVeterinarioPorId(scanner);
                    break;
                case 5:
                    listarVeterinarios();
                    break;
                case 6:
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

        System.out.println("Digite a especialidade do Veterinário:");
        String especialidade = scanner.nextLine();

        System.out.println("Digite o telefone do Veterinário:");
        String telefone = scanner.nextLine();

        System.out.println("Digite o email do Veterinário:");
        String email = scanner.nextLine();

        Veterinario veterinario = new Veterinario(id, nome, crmv, especialidade, telefone, email);
        bancoDeDados.getVeterinarios().adicionar(veterinario);
        System.out.println("Veterinário adicionado com sucesso!");
    }
        
    private void editarVeterinario(Scanner scanner) {
        System.out.println("Digite o ID do Veterinário a ser editado:");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Veterinario veterinario = bancoDeDados.getVeterinarios().buscarPorId(id);
            
            System.out.println("Digite o novo nome do Veterinário:");
            String nome = scanner.nextLine();
            veterinario.setNome(nome);

            System.out.println("Digite o novo CRMV do Veterinário:");
            String crmv = scanner.nextLine();
            veterinario.setCrmv(crmv);

            System.out.println("Digite a nova especialidade do Veterinário:");
            String especialidade = scanner.nextLine();
            veterinario.setEspecialidade(especialidade);

            System.out.println("Digite o novo telefone do Veterinário:");
            String telefone = scanner.nextLine();
            veterinario.setTelefone(telefone);

            System.out.println("Digite o novo email do Veterinário:");
            String email = scanner.nextLine();
            veterinario.setEmail(email);

            bancoDeDados.getVeterinarios().atualizar(veterinario);
            System.out.println("Veterinário editado com sucesso!");
        } catch (IdInexistenteExcecao e) {
            System.out.println("Erro: Veterinário não encontrado.");
        }
    }

    private void removerVeterinario(Scanner scanner) {
        System.out.println("Digite o ID do Veterinário a ser removido:");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            bancoDeDados.getVeterinarios().remover(id);
            System.out.println("Veterinário removido com sucesso!");
        } catch (IdInexistenteExcecao e) {
            System.out.println("Erro: Veterinário não encontrado.");
        }
    }

    private void buscarVeterinarioPorId(Scanner scanner) {
        System.out.println("Digite o ID do Veterinário:");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Veterinario veterinario = bancoDeDados.getVeterinarios().buscarPorId(id);
            System.out.println("Veterinário encontrado:\n" + veterinario);
        } catch (IdInexistenteExcecao e) {
            System.out.println("Erro: Veterinário não encontrado.");
        }
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