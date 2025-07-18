package clinicaVeterinaria.visao.InterfaceTerminal;

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

            System.out.println("\n---*\033[1m MENU CLIENTE \033[0m*---");
            System.out.println("► 1. Adicionar Cliente");
            System.out.println("► 2. Editar Cliente");
            System.out.println("► 3. Remover Cliente");
            System.out.println("► 4. Buscar por ID");
            System.out.println("► 5. Listar Clientes");
            System.out.println("► 6. Voltar");
            System.out.print("Escolha uma opção: ");
            System.out.println("\n----------------------");
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

    public void adicionarCliente(Scanner scanner) {
        System.out.println("Digite o ID do Cliente:");
        int id = scanner.nextInt();
        scanner.nextLine();         

        try {
            if (bancoDeDados.getClientes().buscarPorId(id) != null) {
                System.out.println("Erro: ID já existente.");
                return;
            }
        } catch (IdInexistenteExcecao e) {
        }

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
            
            System.out.println("Cliente encontrado: " + cliente);
            System.out.println("Deixe o campo vazio para manter o valor atual.");
            System.out.println("Digite o novo nome do Cliente:");
            String nome = scanner.nextLine();
            if (!nome.isEmpty()) {
                cliente.setNome(nome);
            }

            System.out.println("Digite o novo telefone do Cliente:");
            String telefone = scanner.nextLine();
            if (!telefone.isEmpty()) {
                cliente.setTelefone(telefone);
            }

            System.out.println("Digite o novo email do Cliente:");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                cliente.setEmail(email);
            }

            System.out.println("Digite o novo CPF do Cliente:");
            String cpf = scanner.nextLine();
            if (!cpf.isEmpty()) {
                cliente.setCpf(cpf);
            }

            System.out.println("Digite o novo endereço do Cliente:");
            String endereco = scanner.nextLine();
            if (!endereco.isEmpty()) {
                cliente.setEndereco(endereco);
            }

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