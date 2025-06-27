package clinicaVeterinaria.visao.gui;

import clinicaVeterinaria.modelo.Procedimento;
import clinicaVeterinaria.persistencia.BancoDeDados;
import clinicaVeterinaria.persistencia.IdInexistenteExcecao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PainelProcedimentos extends JPanel {
    private final BancoDeDados banco;
    private final JPanel painelCards = new JPanel();
    private final JScrollPane scroll;

    public PainelProcedimentos(BancoDeDados banco) {
        this.banco = banco;
        setLayout(new BorderLayout());

        // Cabeçalho
        JLabel titulo = new JLabel("Procedimentos", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(UIConstants.PRIMARY);

        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 70));
        header.add(titulo, BorderLayout.CENTER);

        // Painel de cards
        painelCards.setBorder(new EmptyBorder(20, 20, 20, 20));
        painelCards.setLayout(new BoxLayout(painelCards, BoxLayout.Y_AXIS));

        scroll = new JScrollPane(painelCards);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        // Botão novo
        JButton btnNovo = new JButton("Novo Procedimento");
        JPanel botoes = new JPanel();
        botoes.add(btnNovo);

        add(header, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        btnNovo.addActionListener(e -> abrirFormulario(null));
        atualizarCards();
    }
    private void atualizarCards() {
        painelCards.removeAll();
        
        JPanel linhaAtual = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        painelCards.add(linhaAtual);
        
        int contador = 0;
        for (Procedimento p : banco.getProcedimentos().listar()) {
            if (contador > 0 && contador % 7 == 0) {    
                linhaAtual = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
                painelCards.add(linhaAtual);
            }
            linhaAtual.add(criarCard(p));
            contador++;
        }
        
        painelCards.revalidate();
        painelCards.repaint();
    }

    private JPanel criarCard(Procedimento p) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(200, 180));
        card.setMaximumSize(new Dimension(200, 180));
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);

        JLabel lblNome = new JLabel(p.getNome(), SwingConstants.CENTER);
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 16));
        card.add(lblNome, BorderLayout.NORTH);

        JPanel detalhes = new JPanel(new GridLayout(2, 1, 5, 5));
        detalhes.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        detalhes.add(new JLabel(String.format("Preço: R$ %.2f", p.getPreco()), SwingConstants.CENTER));
        detalhes.add(new JLabel("ID: " + p.getId(), SwingConstants.CENTER));
        card.add(detalhes, BorderLayout.CENTER);

        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> abrirFormulario(p));
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirProcedimento(p));

        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 5, 0));
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        card.add(painelBotoes, BorderLayout.SOUTH);

        return card;
    }

    private void excluirProcedimento(Procedimento p) {
        try {
            banco.getProcedimentos().remover(p.getId());
            atualizarCards();
            JOptionPane.showMessageDialog(this, "Procedimento removido.",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IdInexistenteExcecao ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirFormulario(Procedimento p) {
        Frame frame = JOptionPane.getFrameForComponent(this);
        JDialog dialog = new JDialog(frame, p == null ? "Novo procedimento" : "Editar procedimento", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(4, 2, 7, 7));

        JTextField txtId = new JTextField(p == null ? "" : String.valueOf(p.getId()));
        txtId.setEnabled(p == null);
        JTextField txtNome = new JTextField(p == null ? "" : p.getNome());
        JTextField txtPreco = new JTextField(p == null ? "" : String.valueOf(p.getPreco()));

        dialog.add(new JLabel("ID:"));
        dialog.add(txtId);
        dialog.add(new JLabel("Nome:"));
        dialog.add(txtNome);
        dialog.add(new JLabel("Preço (R$):"));
        dialog.add(txtPreco);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        dialog.add(btnSalvar);
        dialog.add(btnCancelar);

        btnSalvar.addActionListener(ae -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String nome = txtNome.getText().trim();
                double preco = Double.parseDouble(txtPreco.getText().trim());

                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Preencha todos os campos.", "Erro de entrada", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (preco < 0) {
                    JOptionPane.showMessageDialog(dialog, "O preço não pode ser negativo.", "Erro de entrada", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (p == null) {
                    for (Procedimento existente : banco.getProcedimentos().listar()) {
                        if (existente.getId() == id) {
                            JOptionPane.showMessageDialog(dialog,
                                    "Já existe um procedimento com esse ID!", "ID duplicado", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    banco.getProcedimentos().adicionar(new Procedimento(id, nome, preco));
                } else {
                    banco.getProcedimentos().atualizar(new Procedimento(id, nome, preco));
                }

                atualizarCards();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Operação realizada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "ID e Preço devem ser números válidos.",
                        "Erro de entrada", JOptionPane.ERROR_MESSAGE);
            } catch (IdInexistenteExcecao ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(ae -> dialog.dispose());
        dialog.setVisible(true);
    }
}
