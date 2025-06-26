package clinicaVeterinaria.visao.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import clinicaVeterinaria.modelo.Veterinario;
import clinicaVeterinaria.persistencia.BancoDeDados;
import clinicaVeterinaria.persistencia.IdInexistenteExcecao;

public class PainelVeterinarios extends JPanel {
    private BancoDeDados banco;
    private JTable table;
    private DefaultTableModel tableModel;

    // Agora recebe o banco pelo construtor dessa forma o painel veterinários pode ser usado em outros lugares
    public PainelVeterinarios(BancoDeDados banco) {
        this.banco = banco;
        setLayout(new BorderLayout());

        // Header
        JLabel title = new JLabel("Gestão de Veterinários", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(18f));
        title.setForeground(Color.black);

        //Escolher uma foto pra colocar no painel de veterinarios

        //ImageIcon icon = new ImageIcon(
            //    Toolkit.getDefaultToolkit().getImage(
          //              getClass().getResource("/images/veterinário.png")));
        //Image img = icon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        //icon = new ImageIcon(img);
        //JLabel photo = new JLabel(icon);
        //photo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 140));
        header.add(title, BorderLayout.NORTH);
        //  header.add(photo, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);

        String[] columns = { "ID", "Nome", "Telefone", "Email", "CRMv", "Especialidade" };
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
        btnNovo.addActionListener(e -> abrirFormulario(null));

        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um veterinário para editar.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                int id = (int) tableModel.getValueAt(row, 0);
                try {
                    Veterinario c = banco.getVeterinarios().buscarPorId(id);
                    abrirFormulario(c);
                } catch (IdInexistenteExcecao ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um Veterinario para excluir.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                int id = (int) tableModel.getValueAt(row, 0);
                try {
                    banco.getVeterinarios().remover(id);
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Veterinário removido.",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (IdInexistenteExcecao ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Veterinario> list = banco.getVeterinarios().listar();
        for (Veterinario c : list) {
            Object[] row = { c.getId(), c.getNome(), c.getTelefone(), c.getEmail(), c.getCrmv(), c.getEspecialidade() };
            tableModel.addRow(row);
        }
    }

    private void abrirFormulario(Veterinario c) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                c == null ? "Novo Veterinário" : "Editar Veterinário", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(7, 2, 5, 5));

        JTextField txtId = new JTextField(c == null ? "" : String.valueOf(c.getId()));
        txtId.setEnabled(c == null);
        JTextField txtNome = new JTextField(c == null ? "" : c.getNome());
        JTextField txtTelefone = new JTextField(c == null ? "" : c.getTelefone());
        JTextField txtEmail = new JTextField(c == null ? "" : c.getEmail());
        JTextField txtCrmv = new JTextField(c == null ? "" : c.getCrmv());
        JTextField txtEspecialidade = new JTextField(c == null ? "" : c.getEspecialidade());

        dialog.add(new JLabel("ID:"));
        dialog.add(txtId);
        dialog.add(new JLabel("Nome:"));
        dialog.add(txtNome);
        dialog.add(new JLabel("Telefone:"));
        dialog.add(txtTelefone);
        dialog.add(new JLabel("Email:"));
        dialog.add(txtEmail);
        dialog.add(new JLabel("CRM:"));
        dialog.add(txtCrmv);
        dialog.add(new JLabel("Especialidade:"));
        dialog.add(txtEspecialidade);

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
                String crmv = txtCrmv.getText();
                String especialidade = txtEspecialidade.getText();

                Veterinario vet = new Veterinario(id, nome, tel, email, crmv, especialidade);
                if (c == null) {
                    banco.getVeterinarios().adicionar(vet);
                } else {
                    banco.getVeterinarios().atualizar(vet);
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
