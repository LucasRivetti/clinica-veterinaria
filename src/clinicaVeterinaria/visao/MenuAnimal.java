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

        do {
            System.out.println("\n--- Menu de Animais ---");
            System.out.println("1. Cadastrar animal");
            System.out.println("2. Listar animais");
            System.out.println("3. Buscar animal por ID");
            System.out.println("4. Remover animal");
            System.out.println("5. Atualizar animal");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    cadastrarAnimal();
                    break;
                case 2:
                    listarAnimais();
                    break;
                case 3:
                    buscarAnimalPorId();
                    break;
                case 4:
                    removerAnimal();
                    break;
                case 5:
                    atualizarAnimal();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }


        } while (opcao != 0);
    }

    private void cadastrarAnimal() {
        try {
            System.out.print("ID do animal: ");
            int id = scanner.nextInt();
            scanner.nextLine();

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
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar animal: " + e.getMessage());
            scanner.nextLine(); // limpar buffer se erro
        }
    }

    private void listarAnimais() {
        System.out.println("\n--- Lista de Animais ---");
        for (Animal animal : banco.getAnimais().listar()) {
            System.out.println(animal);
            System.out.println("-------------------------");
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

    private void atualizarAnimal() {
        try {
            System.out.print("Digite o ID do animal a atualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Animal animal = banco.getAnimais().buscarPorId(id);

            System.out.print("Novo nome (" + animal.getNome() + "): ");
            animal.setNome(scanner.nextLine());

            System.out.print("Nova espécie (" + animal.getEspecie() + "): ");
            animal.setEspecie(scanner.nextLine());

            System.out.print("Nova raça (" + animal.getRaca() + "): ");
            animal.setRaca(scanner.nextLine());

            System.out.print("Nova idade (" + animal.getIdade() + "): ");
            animal.setIdade(scanner.nextInt());
            scanner.nextLine();

            System.out.print("Novo sexo (" + animal.getSexo() + "): ");
            animal.setSexo(scanner.nextLine());

            banco.getAnimais().atualizar(animal);
            System.out.println("Animal atualizado com sucesso!");

        } catch (IdInexistenteExcecao e) {
            System.out.println("Erro: Animal não encontrado.");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }
}
