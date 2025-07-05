package clinicaVeterinaria.visao.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import clinicaVeterinaria.modelo.Veterinario;
import clinicaVeterinaria.persistencia.BancoDeDados;
import clinicaVeterinaria.persistencia.IdInexistenteExcecao;

public class PainelVeterinarios extends JPanel {
    private BancoDeDados banco;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField campoPesquisa;
    private TableRowSorter<DefaultTableModel> organizador; 

    // Agora recebe o banco pelo construtor dessa forma o painel veterinários pode ser usado em outros lugares
    public PainelVeterinarios(BancoDeDados banco) {
        this.banco = banco;
        setLayout(new BorderLayout());

        // Header
         JLabel titulo = new JLabel("Gestão de Veterinarios", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(UIConstants.PRIMARY);

        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 70));
        header.add(titulo, BorderLayout.CENTER);

        //painel de pesquisa
        JPanel painelPesquisa = new JPanel(new BorderLayout());
        painelPesquisa.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        campoPesquisa = new JTextField();
        campoPesquisa.setToolTipText("Pesquisar por nome do Veterinario, ID, Especialidade ou CRMv");
        

        JButton btnPesquisar = new JButton("Pesquisar");
        JButton btnLimpar = new JButton("Limpar");

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnPesquisar);
        painelBotoes.add(btnLimpar);

        painelPesquisa.add(new JLabel("Pesquisar:"), BorderLayout.WEST);
        painelPesquisa.add(campoPesquisa, BorderLayout.CENTER);
        painelPesquisa.add(painelBotoes, BorderLayout.EAST);
        painelPesquisa.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

         // Imagem de cabeçalho
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/veterinarios.jpg"));
        Image img = icon.getImage().getScaledInstance(350, 150, Image.SCALE_SMOOTH); 
        JLabel labelImagem = new JLabel(new ImageIcon(img));
        labelImagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        painelTopo.add(header);
        painelTopo.add(labelImagem);
        painelTopo.add(painelPesquisa);

        add(painelTopo, BorderLayout.NORTH);

        String[] colunas = { "ID", "Nome", "Telefone", "Email", "CRMv", "Especialidade" };
        modeloTabela = new DefaultTableModel(colunas, 0) { 
            @Override
            public boolean isCellEditable(int row, int col) { return false; } // Não permite edição direta das células
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Seleção de apenas uma linha por vez assim nao da pra editar ou excluir mais de um ao mesmo tempo 

        organizador = new TableRowSorter<>(modeloTabela);
        tabela.setRowSorter(organizador);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton btnNovo = new JButton("Novo");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        buttons.add(btnNovo);
        buttons.add(btnEditar);
        buttons.add(btnExcluir);
        add(buttons, BorderLayout.SOUTH);
        atualizarTabela();


        // Ações dos botões
        btnNovo.addActionListener(e -> abrirFormulario(null));

        btnEditar.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um veterinário para editar.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                int id = (int) modeloTabela.getValueAt(row, 0);
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
            int row = tabela.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um Veterinario para excluir.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                int id = (int) modeloTabela.getValueAt(row, 0);
                try {
                    banco.getVeterinarios().remover(id);
                    atualizarTabela();
                    JOptionPane.showMessageDialog(this, "Veterinário removido.",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (IdInexistenteExcecao ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnPesquisar.addActionListener(e -> {
            String texto = campoPesquisa.getText().trim();
            
            if (texto.isEmpty()) {
                organizador.setRowFilter(null);
            } else {
                try {
                    int id = Integer.parseInt(texto);
                    organizador.setRowFilter(RowFilter.orFilter(
                        List.of(
                            RowFilter.regexFilter("^" + id + "$", 0)
                        )
                    ));
                } catch (NumberFormatException ex) {
                    organizador.setRowFilter(RowFilter.orFilter(
                        List.of(
                            RowFilter.regexFilter("(?i)" + texto, 0),
                            RowFilter.regexFilter("(?i)" + texto, 1),
                            RowFilter.regexFilter("(?i)" + texto, 4),
                            RowFilter.regexFilter("(?i)" + texto, 5)
                        )
                    ));
                }
            }
        });

        btnLimpar.addActionListener(e -> {
            campoPesquisa.setText("");
            organizador.setRowFilter(null);
            atualizarTabela();
        });


    }

    private void atualizarTabela() { // Atualiza a tabela com os dados do banco de dados
        modeloTabela.setRowCount(0);
        List<Veterinario> lista = banco.getVeterinarios().listar();
        for (Veterinario c : lista) {
            Object[] linha = { c.getId(), c.getNome(), c.getTelefone(), c.getEmail(), c.getCrmv(), c.getEspecialidade() };
            modeloTabela.addRow(linha);
        }
    }

     private boolean matchesOnlyText(String texto) {
            return texto.matches("[A-Za-zÀ-ÿ\\s]+");
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
        dialog.add(new JLabel("CRMv:"));
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
                if (!matchesOnlyText(nome)) {
                    JOptionPane.showMessageDialog(dialog, "Nome deve conter apenas letras e espaços.", "Erro de entrada", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String tel = txtTelefone.getText();
                String email = txtEmail.getText();
                String crmv = txtCrmv.getText();
                String especialidade = txtEspecialidade.getText();
                if (!matchesOnlyText(especialidade)) {
                    JOptionPane.showMessageDialog(dialog, "especialidade deve conter apenas letras e espaços.", "Erro de entrada", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(!tel.matches("\\d+")){
                    JOptionPane.showMessageDialog(dialog, "Telefone deve conter apenas números.", "Erro de entrada", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(!crmv.matches("\\d+")){
                    JOptionPane.showMessageDialog(dialog, "CRMv deve conter apenas números.", "Erro de entrada", JOptionPane.ERROR_MESSAGE);
                    return;
                }

               if (nome.isEmpty() || tel.isEmpty() || email.isEmpty() || crmv.isEmpty() || especialidade.isEmpty()) { //se algum campo estiver vazio, exibe mensagem de erro
                    JOptionPane.showMessageDialog(dialog, "Preencha todos os campos.", "Erro de entrada", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (c == null) {
                    List<Veterinario> veterinarios = banco.getVeterinarios().listar();
                    for (Veterinario existente : veterinarios) {
                        if (existente.getId() == id) {
                            JOptionPane.showMessageDialog(dialog, "Já existe um veterinario com esse ID!", "ID duplicado", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    Veterinario vete = new Veterinario(id, nome, tel, email, crmv, especialidade);
                    banco.getVeterinarios().adicionar(vete);
                } else {
                    Veterinario vete = new Veterinario(id, nome, tel, email, crmv, especialidade);
                    banco.getVeterinarios().atualizar(vete);
                }
                atualizarTabela();
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
