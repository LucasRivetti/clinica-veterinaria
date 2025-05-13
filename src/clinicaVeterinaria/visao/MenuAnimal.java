package clinicaVeterinaria.visao;

import java.util.Scanner;

import clinicaVeterinaria.modelo.Animal;
import clinicaVeterinaria.modelo.Cliente;
import clinicaVeterinaria.persistencia.BancoDeDados;
import clinicaVeterinaria.persistencia.IdInexistenteExcecao;

public class MenuAnimal {

    private BancoDeDados banco;
    private Scanner scanner;

    public MenuAnimal(BancoDeDados banco) {
        this.banco = banco;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao;
        System.out.print("\033[H\033[2J");   
        System.out.flush();   
       while (true) {
            System.out.println(
    "    /^-----^\\\n" +
    "    V  o o  V\n" +
    "     |  Y  |\n" +
    "      \\ Q /\n" +
    "      / - \\\n" +
    "      |    \\\n" +
    "      |     \\     )\n" +
    "      || (___\\===="
);
            System.out.println("\n---*\033[1m MENU ANIMAL \033[0m*---");
            System.out.println("► 1. Adicionar Animal");
            System.out.println("► 2. Editar Animal");
            System.out.println("► 3. Remover Animal");
            System.out.println("► 4. Buscar por ID");
            System.out.println("► 5. Listar Animais");
            System.out.println("► 6. Voltar");
            System.out.print("Escolha uma opção: ");
            System.out.println("\n-------------------");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarAnimal();
                    break;
                case 2:
                    editarAnimal();
                    break;
                case 3:
                    removerAnimal();
                    break;
                case 4:
                    buscarAnimalPorId();
                    break;
                case 5:
                    listarAnimais();
                    break;
                case 6:
                    System.out.println("Voltando ao menu principal...");
                    return;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }


        }
    }

    private void cadastrarAnimal() {
        try {
            System.out.print("ID do animal: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            
            try {
                if (banco.getAnimais().buscarPorId(id) != null) {
                System.out.println("Erro: ID já existente.");
                return;
                } catch (IdInexistenteExcecao e) {
                }
            }

            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Espécie: ");
            String especie = scanner.nextLine();

            System.out.print("Raça: ");
            String raca = scanner.nextLine();

            System.out.print("Idade: ");
            int idade = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Sexo (M/F): ");
            String sexo = scanner.nextLine();

            System.out.print("ID do dono (cliente): ");
            int idCliente = scanner.nextInt();
            scanner.nextLine();
            Cliente dono = banco.getClientes().buscarPorId(idCliente);
            
            Animal animal = new Animal(id, nome, especie, raca, idade, sexo, dono);
            banco.getAnimais().adicionar(animal);
            System.out.println("Animal cadastrado com sucesso!");

        } catch (IdInexistenteExcecao e) {
            System.out.println("Erro: Cliente não encontrado.");
            System.out.println("Deseja Cadastar o Cliente? (S/N)");
            String resposta = scanner.nextLine();
            if (resposta.equalsIgnoreCase("S")) {
                MenuCliente menuCliente = new MenuCliente(banco);
                menuCliente.adicionarCliente(scanner);
                System.out.println("Cliente cadastrado com sucesso!");
                System.out.println("Deseja cadastrar o animal? (S/N)");
                resposta = scanner.nextLine();
                if (resposta.equalsIgnoreCase("S")) {
                    cadastrarAnimal();
                } else {
                    System.out.println("Cadastro de animal cancelado.");
                }
            } else {
                System.out.println("Cadastro de animal cancelado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar animal: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void buscarAnimalPorId() {
        try {
            System.out.print("Digite o ID do animal: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Animal animal = banco.getAnimais().buscarPorId(id);
            System.out.println(animal);
        } catch (IdInexistenteExcecao e) {
            System.out.println("Animal não encontrado.");
        }
    }

    private void removerAnimal() {
        try {
            System.out.print("Digite o ID do animal a remover: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            banco.getAnimais().remover(id);
            System.out.println("Animal removido com sucesso!");
        } catch (IdInexistenteExcecao e) {
            System.out.println("Erro: Animal não encontrado.");
        }
    }

    private void editarAnimal() {
        try {
            System.out.print("Digite o ID do animal a editar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Animal animal = banco.getAnimais().buscarPorId(id);

            System.out.println("Animal encontrado: " + animal);
            System.out.println("Deixe o campo vazio para manter o valor atual.");
            System.out.print("Nome (" + animal.getNome() + "): ");
            String nome = scanner.nextLine();
            if (!nome.isEmpty()) {
                animal.setNome(nome);
            }

            System.out.print("Espécie (" + animal.getEspecie() + "): ");
            String especie = scanner.nextLine();
            if (!especie.isEmpty()) {
                animal.setEspecie(especie);
            }

            System.out.print("Raça (" + animal.getRaca() + "): ");
            String raca = scanner.nextLine();
            if (!raca.isEmpty()) {
                animal.setRaca(raca);
            }

            System.out.print("Idade (" + animal.getIdade() + "): ");
            String idadeInput = scanner.nextLine();
            if (!idadeInput.isEmpty()) {
                int idade = Integer.parseInt(idadeInput);
                animal.setIdade(idade);
            }

            System.out.print("Sexo (" + animal.getSexo() + "): ");
            String sexo = scanner.nextLine();
            if (!sexo.isEmpty()) {
                animal.setSexo(sexo);
            }

            System.out.print("ID do dono (cliente) (" + animal.getDono().getId() + "): ");
            String idClienteInput = scanner.nextLine();
            if (!idClienteInput.isEmpty()) {
                int idCliente = Integer.parseInt(idClienteInput);
                Cliente dono = banco.getClientes().buscarPorId(idCliente);
                animal.setDono(dono);
            }

            banco.getAnimais().atualizar(animal);
            System.out.println("Animal atualizado com sucesso!");
        } catch (IdInexistenteExcecao e) {
            System.out.println("Erro: Animal não encontrado.");
        } catch (Exception e) {
            System.out.println("Erro ao editar: " + e.getMessage());
        }
    }

    private void listarAnimais() {
        System.out.println("\n--- Lista de Animais ---");
        for (Animal animal : banco.getAnimais().listar()) {
            System.out.println(animal);
            System.out.println("-------------------------\n");
        }
    }
}