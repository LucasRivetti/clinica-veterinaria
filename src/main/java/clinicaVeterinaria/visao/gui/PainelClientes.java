package clinicaVeterinaria.visao.gui;

import clinicaVeterinaria.modelo.Cliente;
import clinicaVeterinaria.persistencia.BancoDeDados;
import clinicaVeterinaria.persistencia.IdInexistenteExcecao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PainelClientes extends JPanel {
    private final BancoDeDados banco;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public PainelClientes(BancoDeDados banco) {
        this.banco = banco;
        setLayout(new BorderLayout());

        // Cabeçalho
        JLabel titulo = new JLabel("Gestão de Clientes", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(UIConstants.PRIMARY);

        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 70));
        header.add(titulo, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // Tabela de clientes declarada com colunas fixas
        // e sem permitir edição direta
        String[] colunas = { "ID", "Nome", "Telefone", "Email", "CPF", "Endereço" };
        modeloTabela = new DefaultTableModel(colunas, 0) { 
            @Override
            public boolean isCellEditable(int row, int col) { return false; } // Não permite edição direta das células
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Seleção de apenas uma linha por vez assim nao da pra editar ou excluir mais de um ao mesmo tempo 
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

        // Eventos dos botões dessa forema o usuario pode adicionar, editar e excluir clientes
        // e a tabela é atualizada automaticamente
        btnNovo.addActionListener(e -> abrirFormulario(null));
        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.", "Aviso", JOptionPane.WARNING_MESSAGE); //
            } else {
                int id = (int) modeloTabela.getValueAt(linha, 0);
                try {
                    Cliente c = banco.getClientes().buscarPorId(id);
                    abrirFormulario(c);
                } catch (IdInexistenteExcecao ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); // mensagem de erro caso o ID não exista aqui usamos o IdInexistenteExcecao
                }
            }
        });
        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                int id = (int) modeloTabela.getValueAt(linha, 0);
                try {
                    banco.getClientes().remover(id);
                    atualizarTabela();
                    JOptionPane.showMessageDialog(this, "Cliente removido.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); // remove o cliente do banco e atualiza a tabela
                } catch (IdInexistenteExcecao ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        atualizarTabela();
    }

    private void atualizarTabela() { // Atualiza a tabela com os dados do banco de dados
        modeloTabela.setRowCount(0);
        List<Cliente> lista = banco.getClientes().listar();
        for (Cliente c : lista) {
            Object[] linha = { c.getId(), c.getNome(), c.getTelefone(), c.getEmail(), c.getCpf(), c.getEndereco() };
            modeloTabela.addRow(linha);
        }
    }

    private void abrirFormulario(Cliente c) { // Abre um formulário para adicionar ou editar um cliente
        Frame frame = JOptionPane.getFrameForComponent(this);
        JDialog dialog = new JDialog(frame, c == null ? "Novo Cliente" : "Editar Cliente", true);

        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(7, 2, 8, 8));

        JTextField txtId       = new JTextField(c == null ? "" : String.valueOf(c.getId()));
        txtId.setEnabled(c == null); // só habilita para novo
        JTextField txtNome     = new JTextField(c == null ? "" : c.getNome()); // campos de texto para os dados do cliente
        JTextField txtTelefone = new JTextField(c == null ? "" : c.getTelefone());
        JTextField txtEmail    = new JTextField(c == null ? "" : c.getEmail());
        JTextField txtCpf      = new JTextField(c == null ? "" : c.getCpf());
        JTextField txtEndereco = new JTextField(c == null ? "" : c.getEndereco());

        dialog.add(new JLabel("ID:"));
        dialog.add(txtId);
        dialog.add(new JLabel("Nome:"));
        dialog.add(txtNome);
        dialog.add(new JLabel("Telefone:"));
        dialog.add(txtTelefone);
        dialog.add(new JLabel("Email:"));
        dialog.add(txtEmail);
        dialog.add(new JLabel("CPF:"));
        dialog.add(txtCpf);
        dialog.add(new JLabel("Endereço:"));
        dialog.add(txtEndereco);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        dialog.add(btnSalvar);
        dialog.add(btnCancelar);

        btnSalvar.addActionListener(ae -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());// Lê o ID do cliente, se for novo, pode ser vazio
                String nome = txtNome.getText().trim(); 
                String tel = txtTelefone.getText().trim();
                String email = txtEmail.getText().trim();
                String cpf = txtCpf.getText().trim();
                String end = txtEndereco.getText().trim();

                if (nome.isEmpty() || tel.isEmpty() || email.isEmpty() || cpf.isEmpty() || end.isEmpty()) { //se algum campo estiver vazio, exibe mensagem de erro
                    JOptionPane.showMessageDialog(dialog, "Preencha todos os campos.", "Erro de entrada", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Não permite ID duplicado ao cadastrar novo
                if (c == null) {
                    List<Cliente> clientes = banco.getClientes().listar();
                    for (Cliente existente : clientes) {
                        if (existente.getId() == id) {
                            JOptionPane.showMessageDialog(dialog, "Já existe um cliente com esse ID!", "ID duplicado", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    Cliente cli = new Cliente(id, nome, tel, email, cpf, end);
                    banco.getClientes().adicionar(cli);
                } else {
                    Cliente cli = new Cliente(id, nome, tel, email, cpf, end);
                    banco.getClientes().atualizar(cli);
                }
                atualizarTabela();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Operação realizada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "ID deve ser um número.", "Erro de entrada", JOptionPane.ERROR_MESSAGE);
            } catch (IdInexistenteExcecao ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCancelar.addActionListener(ae -> dialog.dispose());

        dialog.setVisible(true);
    }
}
