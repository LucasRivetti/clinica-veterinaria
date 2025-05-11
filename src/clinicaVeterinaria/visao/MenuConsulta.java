package clinicaVeterinaria.visao;

import clinicaVeterinaria.persistencia.BancoDeDados;
import clinicaVeterinaria.modelo.*;

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
            System.out.println("1. Adicionar Consulta");
            System.out.println("2. Editar Consulta");
            System.out.println("3. Remover Consulta");
            System.out.println("4. Buscar Consulta por ID");
            System.out.println("5. Listar Consulta");
            System.out.println("6. Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); 

             switch (opcao) {
                case 1:
                    cadastrarConsulta(scanner);
                    break;
                case 2:
                    editarConsulta(scanner);
                    break;
                case 3:
                    removerConsulta(scanner);
                    break;
                case 4:
                    buscarConsultaPorId(scanner);
                    break;
                case 5:
                    listarConsultas();
                    break;
                case 6:
                    System.out.println("Voltando ao menu principal...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void cadastrarConsulta(Scanner scanner) {
        Animal animal = new Animal();
        Veterinario veterinario = new Veterinario();
        Cliente cliente = new Cliente();
        System.out.println("\nCadastrar Nova Consulta:");

        System.out.print("ID da Consulta: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("ID do Animal: ");
        int idAnimal = scanner.nextInt();
        scanner.nextLine();
        try{
            animal = bancoDeDados.getAnimais().buscarPorId(idAnimal);
        }catch (Exception e){
            System.out.println("Animal não encontrado.");
            return;
        }   
        
        System.out.print("ID do Veterinário: ");
        int idVeterinario = scanner.nextInt();
        scanner.nextLine();
        try{
            veterinario = bancoDeDados.getVeterinarios().buscarPorId(idVeterinario);
        }catch (Exception e){
            System.out.println("Veterinário não encontrado.");
            return;
        }

        System.out.print("ID do Cliente: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine();
        try{
            cliente = bancoDeDados.getClientes().buscarPorId(idCliente);
        }catch (Exception e){
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();


        Consulta consulta = new Consulta(id, cliente, veterinario, animal, new java.util.Date(), descricao);

        bancoDeDados.getConsultas().adicionar(consulta);
        System.out.println("Consulta cadastrada com sucesso!");
    }

    private void editarConsulta(Scanner scanner) {
        System.out.println("\nimplementar:");
    }

    private void removerConsulta(Scanner scanner) {
        System.out.println("\nimplementar:");
    }

    private void buscarConsultaPorId(Scanner scanner) {
        System.out.println("\nBuscar Consulta por ID:");
        try {
            System.out.print("ID da Consulta: ");
            int id = scanner.nextInt();
            scanner.nextLine(); 
            Consulta consulta = bancoDeDados.getConsultas().buscarPorId(id);
            if (consulta != null) {
                System.out.println("Consulta encontrada: " + consulta.toString());
            } else {
                System.out.println("Consulta não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar consulta. Certifique-se de inserir um ID válido.");
            scanner.nextLine();
        }
    }


    private void listarConsultas() {
        System.out.println("\nListar Consultas:");
        System.out.println(bancoDeDados.getConsultas().toString());
    }
}
