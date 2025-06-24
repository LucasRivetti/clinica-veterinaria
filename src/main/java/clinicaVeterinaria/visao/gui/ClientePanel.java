package clinicaVeterinaria.visao.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import clinicaVeterinaria.modelo.Cliente;
import clinicaVeterinaria.persistencia.BancoDeDados;
import clinicaVeterinaria.persistencia.IdInexistenteExcecao;

public class ClientePanel extends JPanel {
    private BancoDeDados banco = new BancoDeDados();
    private JTable table;
    private DefaultTableModel tableModel;

    public ClientePanel() {
        setLayout(new BorderLayout());

        // Header
        JLabel title = new JLabel("Gestão de Clientes", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(18f));
        title.setForeground(Color.black);
        
        ImageIcon icon = new ImageIcon(
                Toolkit.getDefaultToolkit().getImage(
                        getClass().getResource("/images/GatoEVeterinario.png")));
        Image img = icon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        JLabel photo = new JLabel(icon);
        photo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 140));
        header.add(title, BorderLayout.NORTH);
        header.add(photo, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);

        String[] columns = { "ID", "Nome", "Telefone", "Email", "CPF", "Endereço" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel);
        refreshTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton btnNovo = new JButton("Novo");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        buttons.add(btnNovo);
        buttons.add(btnEditar);
        buttons.add(btnExcluir);
        add(buttons, BorderLayout.SOUTH);

        // Ações dos botões
        // Cada botão tem uma ação associada:
        // - Novo: Abre um formulário para adicionar um novo cliente.
        // - Editar: Abre um formulário para editar o cliente selecionado.
        // - Excluir: Remove o cliente selecionado após confirmação.
        // Se nenhum cliente estiver selecionado, exibe uma mensagem de aviso.
        // Se ocorrer um erro ao buscar ou remover o cliente, exibe uma mensagem de erro
        // Lembre-se de atualizar a tabela após cada operação para refletir as mudanças.
        btnNovo.addActionListener(e -> abrirFormulario(null));
        // c-null indica novo cliente
        // Aqui o botão Novo chama o método abrirFormulario com null, indicando que é
        // para criar um novo cliente.
        // O método abrirFormulario irá lidar com a criação de um novo cliente.

        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                int id = (int) tableModel.getValueAt(row, 0);
                try {
                    Cliente c = banco.getClientes().buscarPorId(id);
                    abrirFormulario(c);
                } catch (IdInexistenteExcecao ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // Aqui o botão Editar verifica se há uma linha selecionada na tabela.
        // Se não houver, exibe uma mensagem de aviso.
        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                int id = (int) tableModel.getValueAt(row, 0);
                try {
                    banco.getClientes().remover(id);
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Cliente removido.",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (IdInexistenteExcecao ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Método para atualizar a tabela com os dados dos clientes
    // Isso deve ser chamado após qualquer operação de adição, edição ou exclusão
    // lembrem disso pra outras classes
    // que manipularem os clientes, para garantir que a tabela esteja sempre
    // atualizada.
    // Isso percorre a lista de clientes do banco de dados,
    // limpa o modelo da tabela e adiciona cada cliente como uma nova linha.
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Cliente> list = banco.getClientes().listar();
        for (Cliente c : list) {
            Object[] row = { c.getId(), c.getNome(), c.getTelefone(), c.getEmail(), c.getCpf(), c.getEndereco() };
            tableModel.addRow(row);
        }
    }

    // Método para abrir o formulário de cadastro/edição de cliente
    // Se o cliente for null, abre para novo cadastro; caso contrário, abre para
    // edição do cliente existente.
    // O formulário é um JDialog que permite inserir ou editar os dados do cliente.
    // Após salvar, atualiza a tabela e fecha o diálogo.
    // Se houver erro de entrada, exibe uma mensagem de erro.
    // Se o ID for fornecido, ele é usado para buscar o cliente existente; caso
    // contrário, um novo cliente é criado.
    private void abrirFormulario(Cliente c) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                c == null ? "Novo Cliente" : "Editar Cliente", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(7, 2, 5, 5));

        JTextField txtId = new JTextField(c == null ? "" : String.valueOf(c.getId()));
        txtId.setEnabled(c == null);
        JTextField txtNome = new JTextField(c == null ? "" : c.getNome());
        JTextField txtTelefone = new JTextField(c == null ? "" : c.getTelefone());
        JTextField txtEmail = new JTextField(c == null ? "" : c.getEmail());
        JTextField txtCpf = new JTextField(c == null ? "" : c.getCpf());
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
                int id = Integer.parseInt(txtId.getText());
                String nome = txtNome.getText();
                String tel = txtTelefone.getText();
                String email = txtEmail.getText();
                String cpf = txtCpf.getText();
                String end = txtEndereco.getText();

                Cliente cli = new Cliente(id, nome, tel, email, cpf, end);
                if (c == null) {
                    banco.getClientes().adicionar(cli);
                } else {
                    banco.getClientes().atualizar(cli);
                }
                refreshTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Operação realizada com sucesso.",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "ID deve ser um número.",
                        "Erro de entrada", JOptionPane.ERROR_MESSAGE);
            } catch (IdInexistenteExcecao ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCancelar.addActionListener(ae -> dialog.dispose());

        dialog.setVisible(true);
    }
}
