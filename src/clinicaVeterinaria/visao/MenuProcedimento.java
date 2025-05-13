package clinicaVeterinaria.visao;

import clinicaVeterinaria.modelo.Procedimento;
import clinicaVeterinaria.persistencia.BancoDeDados;
import clinicaVeterinaria.persistencia.IdInexistenteExcecao;
import java.util.Scanner;

public class MenuProcedimento {

    private BancoDeDados banco;
    private Scanner scanner;

    public MenuProcedimento(BancoDeDados banco) {
        this.banco = banco;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao;
        System.out.print("\033[H\033[2J");
        System.out.flush();

        while (true) {
            System.out.println("\n---* \033[1m MENU PROCEDIMENTO \033[0m *---");
            System.out.println("► 1. Adicionar Procedimento");
            System.out.println("► 2. Editar Procedimento");
            System.out.println("► 3. Remover Procedimento");
            System.out.println("► 4. Buscar por ID");
            System.out.println("► 5. Listar Procedimentos");
            System.out.println("► 6. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    adicionarProcedimento();
                    break;
                case 2:
                    editarProcedimento();
                    break;
                case 3:
                    removerProcedimento();
                    break;
                case 4:
                    buscarPorId();
                    break;
                case 5:
                    listarProcedimentos();
                    break;
                case 6:
                    System.out.println("Voltando ao menu principal...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void adicionarProcedimento() {
        try {
            System.out.print("ID do procedimento: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            try {
                if (banco.getProcedimentos().buscarPorId(id) != null) {
                    System.out.println("Erro: ID já existente.");
                    return;
                }
            } catch (IdInexistenteExcecao e) {
            }

            System.out.print("Nome do procedimento: ");
            String nome = scanner.nextLine();

            System.out.print("Preço: ");
            double preco = scanner.nextDouble();
            scanner.nextLine();

            Procedimento procedimento = new Procedimento(id, nome, preco);
            banco.getProcedimentos().adicionar(procedimento);
            System.out.println("Procedimento cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar procedimento: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void editarProcedimento() {
        try {
            System.out.print("Digite o ID do procedimento a editar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Procedimento procedimento = banco.getProcedimentos().buscarPorId(id);

            System.out.println("Procedimento encontrado:\n" + procedimento);
            System.out.println("Deixe o campo vazio para manter o valor atual.");

            System.out.print("Nome (" + procedimento.getNome() + "): ");
            String nome = scanner.nextLine();
            if (!nome.isEmpty()) {
                procedimento.setNome(nome);
            }

            System.out.print("Preço (" + procedimento.getPreco() + "): ");
            String precoInput = scanner.nextLine();
            if (!precoInput.isEmpty()) {
                double preco = Double.parseDouble(precoInput);
                procedimento.setPreco(preco);
            }

            banco.getProcedimentos().atualizar(procedimento);
            System.out.println("Procedimento atualizado com sucesso!");
        } catch (IdInexistenteExcecao e) {
            System.out.println("Erro: Procedimento não encontrado.");
        } catch (Exception e) {
            System.out.println("Erro ao editar: " + e.getMessage());
        }
    }

    private void removerProcedimento() {
        try {
            System.out.print("Digite o ID do procedimento a remover: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            banco.getProcedimentos().remover(id);
            System.out.println("Procedimento removido com sucesso!");
        } catch (IdInexistenteExcecao e) {
            System.out.println("Erro: Procedimento não encontrado.");
        }
    }

    private void buscarPorId() {
        try {
            System.out.print("Digite o ID do procedimento: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Procedimento procedimento = banco.getProcedimentos().buscarPorId(id);
            System.out.println(procedimento);
        } catch (IdInexistenteExcecao e) {
            System.out.println("Procedimento não encontrado.");
        }
    }

    private void listarProcedimentos() {
        System.out.println("\n--- Lista de Procedimentos ---");
        for (Procedimento procedimento : banco.getProcedimentos().listar()) {
            System.out.println(procedimento);
            System.out.println("------------------------------\n");
        }
    }
}
