package clinicaVeterinaria.visao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import clinicaVeterinaria.modelo.*;
import clinicaVeterinaria.persistencia.BancoDeDados;
import clinicaVeterinaria.persistencia.IdInexistenteExcecao;

public class MenuConsulta {

    @SuppressWarnings("FieldMayBeFinal")
    private BancoDeDados bancoDeDados;

    public MenuConsulta(BancoDeDados bancoDeDados) {
        this.bancoDeDados = bancoDeDados;
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\033[H\033[2J");   
        System.out.flush();   
        while (true) {
            System.out.println("\n---* \033[1m MENU CONSULTA \033[0m *---");
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

        try {
            if (bancoDeDados.getConsultas().buscarPorId(id) != null) {
                System.out.println("Erro: ID já existente.");
                return;
            }
        } catch (IdInexistenteExcecao e) {
        }

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

        List<ItemConsulta> itensConsulta = new ArrayList<>();
        while (true) {
            System.out.print("ID do Procedimento (ou 0 para finalizar): ");
            int idProcedimento = scanner.nextInt();
            scanner.nextLine();

            if (idProcedimento == 0) break;
        
            try {
                Procedimento procedimento = bancoDeDados.getProcedimentos().buscarPorId(idProcedimento);
                System.out.print("Alterar o Valor do procedimento? (S/N): ");
                String resposta = scanner.nextLine();
                if (resposta.equalsIgnoreCase("N")) {
                    ItemConsulta item = new ItemConsulta(procedimento, procedimento.getPreco());
                    itensConsulta.add(item);
                    continue;
                }
                System.out.print("Valor do procedimento: ");
                double valor = scanner.nextDouble();
                scanner.nextLine();

                ItemConsulta item = new ItemConsulta(procedimento, valor);
                itensConsulta.add(item);
            } catch (Exception e) {
                System.out.println("Procedimento não encontrado.");
            }
        }

        if (itensConsulta.isEmpty()) {
            System.out.println("A consulta deve ter pelo menos um procedimento.");
            return;
        }

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();


        Consulta consulta = new Consulta(id, cliente, veterinario, animal, new java.util.Date(), itensConsulta, descricao);


        bancoDeDados.getConsultas().adicionar(consulta);
        System.out.println("Consulta cadastrada com sucesso!");
    }

    private void editarConsulta(Scanner scanner) {
        Animal novoanimal = new Animal();
        Veterinario novoveterinario = new Veterinario();
        Cliente novocliente = new Cliente();
        System.out.println("Digite o ID da consulta a ser editada:");
        int idConsulta = scanner.nextInt();
        scanner.nextLine();

        Consulta consulta;
        try {
            consulta = bancoDeDados.getConsultas().buscarPorId(idConsulta);
            if (consulta == null) {
                System.out.println("Consulta não encontrada.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar consulta. Certifique-se de inserir um ID válido.");
            return;
        }
        System.out.println("Consulta encontrada: " + consulta.toString());
        System.out.println("deixe vazio para manter o valor atual:");
        System.out.println("Digite os novos dados da consulta:");

        System.out.println("Nova descrição (atual: " + consulta.getDescricao() + "): ");
        String novaDescricao = scanner.nextLine();
        if (!novaDescricao.isEmpty()) {
            consulta.setDescricao(novaDescricao);
        }

        System.out.println("Digite o novo ID do animal (atual: " + consulta.getAnimal().getId() + "): ");
        int novoIdAnimal = scanner.nextInt();
        scanner.nextLine();
        if (novoIdAnimal != 0) {
            try {
                novoanimal = bancoDeDados.getAnimais().buscarPorId(novoIdAnimal);
                    consulta.setAnimal(novoanimal);
            } catch (Exception e) {
                System.out.println("Animal não encontrado.");
            }
        }
        System.out.println("Digite o novo ID do veterinário (atual: " + consulta.getVeterinario().getId() + "): ");
        int novoIdVeterinario = scanner.nextInt();
        scanner.nextLine();
        if (novoIdVeterinario != 0) {
            try {
            novoveterinario = bancoDeDados.getVeterinarios().buscarPorId(novoIdVeterinario);
                consulta.setVeterinario(novoveterinario);
            } catch (Exception e) {
                System.out.println("Veterinário não encontrado.");
            }
        }

        System.out.println("Digite o novo ID do cliente (atual: " + consulta.getCliente().getId() + "): ");
        int novoIdCliente = scanner.nextInt();
        scanner.nextLine();
        if (novoIdCliente != 0) {
            try {
               novocliente = bancoDeDados.getClientes().buscarPorId(novoIdCliente);
                consulta.setCliente(novocliente);
            } catch (Exception e) {
                System.out.println("Cliente não encontrado.");
            }
        }

        List<ItemConsulta> itensConsulta = consulta.getItens(); 
        while (true) {
            System.out.println("Deseja adicionar ou remover um procedimento?");
            System.out.println("1. Adicionar Procedimento");
            System.out.println("2. Remover Procedimento");
            System.out.println("3. Finalizar Edição");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                System.out.print("ID do Procedimento: ");
                int idProcedimento = scanner.nextInt();
                scanner.nextLine();

                try {
                    Procedimento procedimento = bancoDeDados.getProcedimentos().buscarPorId(idProcedimento);
                    System.out.print("Valor do procedimento: ");
                    double valor = scanner.nextDouble();
                    scanner.nextLine();

                    ItemConsulta item = new ItemConsulta(procedimento, valor);
                    itensConsulta.add(item);
                    consulta.setItens(itensConsulta); 
                } catch (Exception e) {
                    System.out.println("Procedimento não encontrado.");
                }
            } else if (opcao == 2) {
                System.out.print("ID do Procedimento a remover: ");
                int idProcedimento = scanner.nextInt();
                scanner.nextLine();
                boolean removido = false;
                for (ItemConsulta item : itensConsulta) {
                    if (item.getProcedimento().getId() == idProcedimento) {
                        itensConsulta.remove(item);
                        removido = true;
                        break;
                    }
                }
                if (removido) {
                    System.out.println("Procedimento removido com sucesso.");
                    consulta.setItens(itensConsulta); 
                } else {
                    System.out.println("Procedimento não encontrado.");
                }
            } else if (opcao == 3) {
                break;
            }
        }

        try{
        bancoDeDados.getConsultas().atualizar(consulta);
            System.out.println("Consulta editada com sucesso!");
        }catch (IdInexistenteExcecao e) {
            System.out.println("Erro: Consulta não encontrada.");
        }
    }

    private void removerConsulta(Scanner scanner) {
        System.out.println("Digite o id da consulta a ser removida:");
        int idConsulta = scanner.nextInt();
        scanner.nextLine();
        try {
            bancoDeDados.getConsultas().remover(idConsulta);
            System.out.println("Consulta removida com sucesso!");
        } catch (IdInexistenteExcecao e) {
            System.out.println("Erro: Consulta não encontrada.");
        }


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
