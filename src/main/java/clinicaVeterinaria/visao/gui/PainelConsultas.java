package clinicaVeterinaria.visao.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import clinicaVeterinaria.modelo.Animal;
import clinicaVeterinaria.modelo.Cliente;
import clinicaVeterinaria.modelo.Consulta;
import clinicaVeterinaria.modelo.ItemConsulta;
import clinicaVeterinaria.modelo.Procedimento;
import clinicaVeterinaria.modelo.Veterinario;
import clinicaVeterinaria.persistencia.BancoDeDados;
import clinicaVeterinaria.persistencia.IdInexistenteExcecao;

public class PainelConsultas extends JPanel {
    private final BancoDeDados banco;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField campoPesquisa;
    private TableRowSorter<DefaultTableModel> organizador;

    public PainelConsultas(BancoDeDados banco) {
        this.banco = banco;
        setLayout(new BorderLayout());
        setBackground(UIConstants.WHITE);

        // Cabeçalho
        JLabel titulo = new JLabel("Gestão de Consultas", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(UIConstants.PRIMARY);
        titulo.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 70));
        header.add(titulo, BorderLayout.CENTER);

        //Imagem do cabeçalho
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/consulta.jpg"));
        Image img = icon.getImage().getScaledInstance(350, 150, Image.SCALE_SMOOTH); 
        JLabel labelImagem = new JLabel(new ImageIcon(img));

        // Painel de pesquisa
        JPanel painelPesquisa = new JPanel(new BorderLayout());
        painelPesquisa.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        campoPesquisa = new JTextField();
        campoPesquisa.setToolTipText("Pesquisar por nome do cliente ou ID");
        
        JButton btnPesquisar = new JButton("Pesquisar");
        JButton btnLimpar = new JButton("Limpar");

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnPesquisar);
        painelBotoes.add(btnLimpar);

        painelPesquisa.add(new JLabel("Pesquisar:"), BorderLayout.WEST);
        painelPesquisa.add(campoPesquisa, BorderLayout.CENTER);
        painelPesquisa.add(painelBotoes, BorderLayout.EAST);
        painelPesquisa.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); 
        
        // Auxiliar pro topo
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        labelImagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelTopo.add(header);
        painelTopo.add(labelImagem);
        painelTopo.add(painelPesquisa); 
        add(painelTopo, BorderLayout.NORTH);

        // Tabela de consultas declarada com colunas fixas
        // e sem permitir edição direta
        String[] colunas = { "ID", "Cliente", "Veterinário", "Animal", "Data", "Descrição", "Procedimentos", "Valor" };
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabela = new JTable(modeloTabela);
        organizador = new TableRowSorter<>(modeloTabela);
        tabela.setRowSorter(organizador);

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        //Dois cliques para abrir a descrição e os procedimentos
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tabela.rowAtPoint(e.getPoint());
                int col = tabela.columnAtPoint(e.getPoint());
                if (row != -1 && col == 5 && e.getClickCount() == 2) {
                    int modelRow = tabela.convertRowIndexToModel(row);
                    Object descricaoObj = modeloTabela.getValueAt(modelRow, 5);
                    String descricao = descricaoObj != null ? descricaoObj.toString() : "";
                    JTextArea area = new JTextArea(descricao);
                    area.setEditable(false);
                    area.setLineWrap(true);
                    area.setWrapStyleWord(true);
                    JScrollPane scroll = new JScrollPane(area);
                    scroll.setPreferredSize(new Dimension(350, 120));
                    JOptionPane.showMessageDialog(
                        PainelConsultas.this,
                        scroll,
                        "Descrição da Consulta",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else if (row != -1 && col == 6 && e.getClickCount() == 2) {
                    int modelRow = tabela.convertRowIndexToModel(row);
                    Object procedimentosObj = modeloTabela.getValueAt(modelRow, 6);
                    String procedimentos = procedimentosObj != null ? procedimentosObj.toString() : "";
                    JTextArea area = new JTextArea(procedimentos);
                    area.setEditable(false);
                    area.setLineWrap(true);
                    area.setWrapStyleWord(true);
                    JScrollPane scroll = new JScrollPane(area);
                    scroll.setPreferredSize(new Dimension(350, 120));
                    JOptionPane.showMessageDialog(
                        PainelConsultas.this,
                        scroll,
                        "Procedimentos da Consulta",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        // Botões
        JPanel botoes = new JPanel();
        JButton btnNovo = new JButton("Novo");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        botoes.add(btnNovo);
        botoes.add(btnEditar);
        botoes.add(btnExcluir);
        add(botoes, BorderLayout.SOUTH);

        // Eventos dos botões para adicionar, editar e excluir consultas
        // e a tabela é atualizada automaticamente
        btnNovo.addActionListener(e -> abrirFormulario(null));
        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma consulta para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                int id = (int) modeloTabela.getValueAt(linha, 0);
                try {
                    Consulta c = banco.getConsultas().buscarPorId(id);
                    abrirFormulario(c);
                } catch (IdInexistenteExcecao ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma consulta para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                int id = (int) modeloTabela.getValueAt(linha, 0);
                try {
                    banco.getConsultas().remover(id);
                    atualizarTabela();
                    JOptionPane.showMessageDialog(this, "Consulta removida.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (IdInexistenteExcecao ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
                            RowFilter.regexFilter("(?i)" + texto, 1), 
                            RowFilter.regexFilter("(?i)" + texto, 2), 
                            RowFilter.regexFilter("(?i)" + texto, 6)  
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

        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Consulta> lista = banco.getConsultas().listar();
        for (Consulta c : lista) {
            String procedimentos = c.getItens().isEmpty() ? "Nenhum" : c.getItens().stream()
                .map(item -> item.getProcedimento().getNome())
                .reduce((a, b) -> a + ", " + b).orElse("");
            Object[] linha = { c.getId(),
                c.getCliente() != null ? c.getCliente().getNome() : "Sem cliente",
                c.getVeterinario() != null ? c.getVeterinario().getNome() : "Sem veterinário",
                c.getAnimal() != null ? c.getAnimal().getNome() : "Sem animal",
                c.getDataHora() != null ? c.getDataHora().toString() : "Sem data",
                c.getDescricao() != null ? c.getDescricao() : "",
                procedimentos,
                String.format("R$ %.2f", c.calcularValorTotal())
            };
            modeloTabela.addRow(linha);
        }
    }

    private void abrirFormulario(Consulta c) { // Abre um formulário para adicionar ou editar uma consulta
        Frame frame = JOptionPane.getFrameForComponent(this);
        JDialog dialog = new JDialog(frame, c == null ? "Nova Consulta" : "Editar Consulta", true);

        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(10, 2, 8, 8));

        JTextField txtId = new JTextField(c == null ? "" : String.valueOf(c.getId()));
        txtId.setEnabled(c == null);

        JComboBox<Cliente> cbCliente = new JComboBox<>();
        List<Cliente> clientes = banco.getClientes().listar();
        for (Cliente cli : clientes) {
            cbCliente.addItem(cli);
        }
        if (c != null && c.getCliente() != null) {
            cbCliente.setSelectedItem(c.getCliente());
        }

        JComboBox<Veterinario> cbVeterinario = new JComboBox<>();
        List<Veterinario> veterinarios = banco.getVeterinarios().listar();
        for (Veterinario v : veterinarios) {
            cbVeterinario.addItem(v);
        }
        if (c != null && c.getVeterinario() != null) {
            cbVeterinario.setSelectedItem(c.getVeterinario());
        }

        JComboBox<Animal> cbAnimal = new JComboBox<>();
        cbAnimal.setEnabled(false); // Desabilita inicialmente

        //Preenche animais do cliente selecionado
        cbCliente.addActionListener(e -> {
            cbAnimal.removeAllItems();
            Cliente clienteSelecionado = (Cliente) cbCliente.getSelectedItem();
            if (clienteSelecionado != null) {
                List<Animal> animaisDoCliente = banco.getAnimais().listar();
                for (Animal a : animaisDoCliente) {
                    if (a.getDono() != null && a.getDono().equals(clienteSelecionado)) {
                        cbAnimal.addItem(a);
                    }
                }
                cbAnimal.setEnabled(cbAnimal.getItemCount() > 0);
            } else {
                cbAnimal.setEnabled(false);
            }
        });

        //Se for edição, seleciona o cliente e animal corretos
        if (c != null && c.getCliente() != null) {
            cbCliente.setSelectedItem(c.getCliente());
            cbCliente.getActionListeners()[0].actionPerformed(null);
            if (c.getAnimal() != null) {
                cbAnimal.setSelectedItem(c.getAnimal());
            }
        }

        //Formatação da data/hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataAtual = LocalDateTime.now().format(formatter);

        JTextField txtData = new JTextField(
            c == null || c.getDataHora() == null ? dataAtual : 
            new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(c.getDataHora())
        );
        txtData.setToolTipText("Formato: dd/MM/yyyy HH:mm (ex: 30/06/2025 14:30)");

        JTextField txtDescricao = new JTextField(c == null ? "" : c.getDescricao());

        //Painel de procedimentos com checkboxes e spinners
        JPanel painelProcedimentos = new JPanel();
        painelProcedimentos.setLayout(new BoxLayout(painelProcedimentos, BoxLayout.Y_AXIS));
        List<Procedimento> procedimentosDisponiveis = banco.getProcedimentos().listar();
        List<ItemConsulta> itensExistentes = c != null ? c.getItens() : new java.util.ArrayList<>();
        java.util.Map<Procedimento, JCheckBox> mapCheck = new java.util.HashMap<>();
        java.util.Map<Procedimento, JSpinner> mapSpinner = new java.util.HashMap<>();

        for (Procedimento p : procedimentosDisponiveis) {
        JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox check = new JCheckBox(p.getNome() + String.format(" (R$ %.2f)", p.getPreco()));
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        spinner.setEnabled(false);

        //Se for edição, marca e seta quantidade se já existia
        int qtd = 1;
        for (ItemConsulta item : itensExistentes) {
            if (item.getProcedimento().getId() == p.getId()) {
                check.setSelected(true);
                spinner.setEnabled(true);
                qtd = item.getQuantidade();
            }
        }
        spinner.setValue(qtd);
        check.addActionListener(e -> spinner.setEnabled(check.isSelected()));
        linha.add(check);
        linha.add(new JLabel("Qtd:"));
        linha.add(spinner);
        painelProcedimentos.add(linha);
        mapCheck.put(p, check);
        mapSpinner.put(p, spinner);
    }
        JScrollPane scrollProcedimentos = new JScrollPane(painelProcedimentos);
        scrollProcedimentos.setPreferredSize(new Dimension(350, 120));

        JLabel lblValorTotal = new JLabel("Valor Total: R$ 0.00");
        Runnable atualizarTotal = () -> {
            double total = 0.0;
            for (Procedimento p : procedimentosDisponiveis) {
                JCheckBox check = mapCheck.get(p);
                JSpinner spinner = mapSpinner.get(p);
                if (check.isSelected()) {
                    int qtd = (int) spinner.getValue();
                    total += p.getPreco() * qtd;
                }
            }
            lblValorTotal.setText(String.format("Valor Total: R$ %.2f", total));
        };

        //Atualizar o valor total
        for (Procedimento p : procedimentosDisponiveis) {
            mapCheck.get(p).addActionListener(e -> atualizarTotal.run());
            mapSpinner.get(p).addChangeListener(e -> atualizarTotal.run());
        }
        atualizarTotal.run();

        dialog.add(new JLabel("ID:"));
        dialog.add(txtId);
        dialog.add(new JLabel("Cliente:"));
        dialog.add(cbCliente);
        dialog.add(new JLabel("Veterinário:"));
        dialog.add(cbVeterinario);
        dialog.add(new JLabel("Animal:"));
        dialog.add(cbAnimal);
        dialog.add(new JLabel("Data/Hora:"));
        dialog.add(txtData);
        dialog.add(new JLabel("Descrição:"));
        dialog.add(txtDescricao);
        dialog.add(new JLabel("Procedimentos (marque e defina a quantidade):"));
        dialog.add(scrollProcedimentos);
        dialog.add(lblValorTotal);
        dialog.add(new JLabel());

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        dialog.add(btnSalvar);
        dialog.add(btnCancelar);

        btnSalvar.addActionListener(ae -> {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            Cliente cliente = (Cliente) cbCliente.getSelectedItem();
            Veterinario veterinario = (Veterinario) cbVeterinario.getSelectedItem();
            Animal animal = (Animal) cbAnimal.getSelectedItem();
            String descricao = txtDescricao.getText().trim();
            java.util.Date dataHora = null;
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
                dataHora = sdf.parse(txtData.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Data/Hora inválida. Use o formato: dd/MM/yyyy HH:mm (ex: 30/06/2025 14:30)", "Erro de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cliente == null || veterinario == null || animal == null || descricao.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Preencha todos os campos.", "Erro de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Monta os itens da consulta
            List<ItemConsulta> itens = new java.util.ArrayList<>();
            boolean peloMenosUm = false;
            for (Procedimento p : procedimentosDisponiveis) {
                JCheckBox check = mapCheck.get(p);
                JSpinner spinner = mapSpinner.get(p);
                if (check.isSelected()) {
                    int qtd = (int) spinner.getValue();
                    itens.add(new ItemConsulta(p, p.getPreco(), qtd)); // Corrigido: salva quantidade
                    peloMenosUm = true;
                }
            }
            if (!peloMenosUm) {
                JOptionPane.showMessageDialog(dialog, "Selecione pelo menos um procedimento.", "Erro de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (c == null) {
                List<Consulta> consultas = banco.getConsultas().listar();
                for (Consulta existente : consultas) {
                    if (existente.getId() == id) {
                        JOptionPane.showMessageDialog(dialog, "Já existe uma consulta com esse ID!", "ID duplicado", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                Consulta nova = new Consulta(id, cliente, veterinario, animal, dataHora, itens, descricao);
                banco.getConsultas().adicionar(nova);
            } else {
                c.setCliente(cliente);
                c.setVeterinario(veterinario);
                c.setAnimal(animal);
                c.setDataHora(dataHora);
                c.setDescricao(descricao);
                c.setItens(itens);
                banco.getConsultas().atualizar(c);
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
