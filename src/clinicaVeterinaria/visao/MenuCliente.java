package clinicaVeterinaria.visao;

import java.util.Scanner;
import clinicaVeterinaria.modelo.Cliente;
import clinicaVeterinaria.persistencia.BancoDeDados;
import clinicaVeterinaria.persistencia.IdInexistenteExcecao;

public class MenuCliente {

    private BancoDeDados bancoDeDados;

    public MenuCliente(BancoDeDados bancoDeDados) {
        this.bancoDeDados = bancoDeDados;
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\033[H\033[2J");   
        System.out.flush();   
        while (true) {
            System.out.println("\n--- Menu Cliente ---");
            System.out.println("1. Adicionar Cliente");
            System.out.println("2. Editar Cliente");
            System.out.println("3. Remover Cliente");
            System.out.println("4. Buscar por ID");
            System.out.println("5. Listar Clientes");
            System.out.println("6. Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    adicionarCliente(scanner);
                    break;
                case 2:
                    editarCliente(scanner);
                    break;
                case 3:
                    removerCliente(scanner);
                    break;
                case 4:
                    buscarClientePorId(scanner);
                    break;
                case 5:
                    listarClientes();
                    break;
                case 6:
                    System.out.println("Voltando ao menu principal...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void adicionarCliente(Scanner scanner) {
        System.out.println("Digite o ID do Cliente:");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        System.out.println("Digite o nome do Cliente:");
        String nome = scanner.nextLine();

        System.out.println("Digite o telefone do Cliente:");
        String telefone = scanner.nextLine();

        System.out.println("Digite o email do Cliente:");
        String email = scanner.nextLine();

        System.out.println("Digite o CPF do Cliente:");
        String cpf = scanner.nextLine();

        System.out.println("Digite o endereço do Cliente:");
        String endereco = scanner.nextLine();

        Cliente cliente = new Cliente(id, nome, telefone, email, cpf, endereco);
        bancoDeDados.getClientes().adicionar(cliente);
        System.out.println("Cliente adicionado com sucesso!");
    }

    private void editarCliente(Scanner scanner) {
        System.out.println("Digite o ID do Cliente a ser editado:");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Cliente cliente = bancoDeDados.getClientes().buscarPorId(id);
            
            System.out.println("Digite o novo nome do Cliente:");
            String nome = scanner.nextLine();
            cliente.setNome(nome);

            System.out.println("Digite o novo telefone do Cliente:");
            String telefone = scanner.nextLine();
            cliente.setTelefone(telefone);

            System.out.println("Digite o novo email do Cliente:");
            String email = scanner.nextLine();
            cliente.setEmail(email);

            System.out.println("Digite o novo CPF do Cliente:");
            String cpf = scanner.nextLine();
            cliente.setCpf(cpf);

            System.out.println("Digite o novo endereço do Cliente:");
            String endereco = scanner.nextLine();
            cliente.setEndereco(endereco);

            bancoDeDados.getClientes().atualizar(cliente);
            System.out.println("Cliente editado com sucesso!");
        } catch (IdInexistenteExcecao e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerCliente(Scanner scanner) {
        System.out.println("Digite o ID do Cliente a ser removido:");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            bancoDeDados.getClientes().remover(id);
            System.out.println("Cliente removido com sucesso!");
        } catch (IdInexistenteExcecao e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarClientePorId(Scanner scanner) {
        System.out.println("Digite o ID do Cliente:");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Cliente cliente = bancoDeDados.getClientes().buscarPorId(id);
            System.out.println(cliente);
        } catch (IdInexistenteExcecao e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarClientes() {
        System.out.println("\n--- Lista de Clientes ---");
        String clientesList = bancoDeDados.getClientes().toString();
        if (clientesList.isEmpty()) {
            System.out.println("Não há clientes cadastrados.");
        } else {
            System.out.println(clientesList);
        }
    }
}