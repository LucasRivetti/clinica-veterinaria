package clinicaVeterinaria.visao.gui;

import clinicaVeterinaria.modelo.Animal;
import clinicaVeterinaria.modelo.Cliente;
import clinicaVeterinaria.persistencia.BancoDeDados;
import clinicaVeterinaria.persistencia.IdInexistenteExcecao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PainelAnimais extends JPanel {
    private final BancoDeDados banco;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField campoPesquisa;


    public PainelAnimais(BancoDeDados banco) {
        this.banco = banco;
        setLayout(new BorderLayout());

        // Cabeçalho
        JLabel titulo = new JLabel("Gestão de Animais", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(UIConstants.PRIMARY);

        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 70));
        header.add(titulo, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);
        
        // painel pesquisa

        JPanel painelPesquisa = new JPanel(new BorderLayout());
        painelPesquisa.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        campoPesquisa = new JTextField();
        campoPesquisa.setToolTipText("Pesquisar por nome do animal ou ID");


        JButton btnPesquisar = new JButton("Pesquisar");
        JButton btnLimpar = new JButton("Limpar");

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnPesquisar);
        painelBotoes.add(btnLimpar);



        painelPesquisa.add(new JLabel("Pesquisar:"), BorderLayout.WEST);
        painelPesquisa.add(campoPesquisa, BorderLayout.CENTER);
        painelPesquisa.add(painelBotoes, BorderLayout.EAST);
        add(painelPesquisa, BorderLayout.NORTH);
        

        // Tabela de animais declarada com colunas fixas
        // e sem permitir edição direta
        String[] colunas = { "ID", "Nome", "Espécie", "Raça", "Idade", "Sexo", "Dono" };
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        // Botões
        JPanel botoes = new JPanel();
        JButton btnNovo = new JButton("Novo");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        botoes.add(btnNovo);
        botoes.add(btnEditar);
        botoes.add(btnExcluir);
        add(botoes, BorderLayout.SOUTH);

        // Eventos dos botões dessa forema o usuario pode adicionar, editar e excluir animais
        // e a tabela é atualizada automaticamente
        btnNovo.addActionListener(e -> abrirFormulario(null));
        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um animal para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                int id = (int) modeloTabela.getValueAt(linha, 0);
                try {
                    Animal a = banco.getAnimais().buscarPorId(id);
                    abrirFormulario(a);
                } catch (IdInexistenteExcecao ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um animal para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                int id = (int) modeloTabela.getValueAt(linha, 0);
                try {
                    banco.getAnimais().remover(id);
                    atualizarTabela();
                    JOptionPane.showMessageDialog(this, "Animal removido.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (IdInexistenteExcecao ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Animal> lista = banco.getAnimais().listar();
        for (Animal a : lista) {
            Object[] linha = { a.getId(), a.getNome(), a.getEspecie(), a.getRaca(), a.getIdade(), a.getSexo(), a.getDono() != null ? a.getDono().getNome() : "Sem dono" };
            modeloTabela.addRow(linha);
        }
    }

    private void abrirFormulario(Animal a) { // Abre um formulário para adicionar ou editar um cliente
        Frame frame = JOptionPane.getFrameForComponent(this);
        JDialog dialog = new JDialog(frame, a == null ? "Novo Animal" : "Editar Animal", true);

        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(8, 2, 8, 8));

        JTextField txtId = new JTextField(a == null ? "" : String.valueOf(a.getId()));
        txtId.setEnabled(a == null); // só habilita para novo
        JTextField txtNome = new JTextField(a == null ? "" : a.getNome()); // campos de texto para os dados do cliente
        JTextField txtEspecie = new JTextField(a == null ? "" : a.getEspecie());
        JTextField txtRaca = new JTextField(a == null ? "" : a.getRaca());
        JTextField txtIdade = new JTextField(a == null ? "" : String.valueOf(a.getIdade()));
        JTextField txtSexo = new JTextField(a == null ? "" : a.getSexo());
        
        // selecao do cliente que e dono do cachorro
        JComboBox<Cliente> cbDono = new JComboBox<>();
        List<Cliente> clientes = banco.getClientes().listar();
        for (Cliente c : clientes) {
            cbDono.addItem(c);
        }
        if (a != null && a.getDono() != null) {
            cbDono.setSelectedItem(a.getDono());
        }

        dialog.add(new JLabel("ID:"));
        dialog.add(txtId);
        dialog.add(new JLabel("Nome:"));
        dialog.add(txtNome);
        dialog.add(new JLabel("Espécie:"));
        dialog.add(txtEspecie);
        dialog.add(new JLabel("Raça:"));
        dialog.add(txtRaca);
        dialog.add(new JLabel("Idade:"));
        dialog.add(txtIdade);
        dialog.add(new JLabel("Sexo:"));
        dialog.add(txtSexo);
        dialog.add(new JLabel("Dono:"));
        dialog.add(cbDono);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        dialog.add(btnSalvar);
        dialog.add(btnCancelar);

        btnSalvar.addActionListener(ae -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String nome = txtNome.getText().trim();
                String especie = txtEspecie.getText().trim();
                String raca = txtRaca.getText().trim();
                int idade = Integer.parseInt(txtIdade.getText().trim());
                String sexo = txtSexo.getText().trim();
                Cliente dono = (Cliente) cbDono.getSelectedItem();

                if (nome.isEmpty() || especie.isEmpty() || raca.isEmpty() || sexo.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Preencha todos os campos.", "Erro de entrada", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Não permite ID duplicado ao cadastrar novo
                if (a == null) {
                    List<Animal> animais = banco.getAnimais().listar();
                    for (Animal existente : animais) {
                        if (existente.getId() == id) {
                            JOptionPane.showMessageDialog(dialog, "Já existe um animal com esse ID!", "ID duplicado", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    Animal animal = new Animal(id, nome, especie, raca, idade, sexo, dono);
                    banco.getAnimais().adicionar(animal);
                } else {
                    Animal animal = new Animal(id, nome, especie, raca, idade, sexo, dono);
                    banco.getAnimais().atualizar(animal);
                }
                atualizarTabela();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Operação realizada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "ID e Idade devem ser números.", "Erro de entrada", JOptionPane.ERROR_MESSAGE);
            } catch (IdInexistenteExcecao ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCancelar.addActionListener(ae -> dialog.dispose());

        dialog.setVisible(true);
    }
}