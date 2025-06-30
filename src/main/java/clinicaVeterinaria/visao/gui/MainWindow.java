package clinicaVeterinaria.visao.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import clinicaVeterinaria.persistencia.BancoDeDados;

public class MainWindow extends JFrame {
    private static final int MENU_WIDTH     = 220; 
    private static final int ANIMATION_STEP = 20; 
    private static final int TIMER_DELAY_MS = 10;

    public final BancoDeDados bancoDeDados = new BancoDeDados(); //banco de dados da aplicação estamos iniciando aqui para ser usado em outros painéis

    // todo os componentes da janela principal, dividi eles em outros componentes para facilitar a manutenção e o entendimento do código pra vcs 
    private CardLayout layoutCartoes; 
    private JPanel painelPrincipal;
    private MenuLateral painelLateral;
    private BarraSuperior barraSuperior;
    private boolean menuAberto = false; 
    private Timer temporizadorAnimacao;
    private int larguraAtual = 0;
    private int larguraAlvo  = 0;
    private String selecaoAtual = "Home"; 

    public MainWindow() {
        super("Clínica Veterinária");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //
        setLayout(new BorderLayout());
        getContentPane().setBackground(UIConstants.BACKGROUND);

        inicializarTemporizador();
        inicializarPainelLateral();
        inicializarBarraSuperior();
        inicializarPainelPrincipal();
        atualizarSelecaoMenu("Home");
        atalhos();

        pack();
        setLocationRelativeTo(null);
        
    }

    private void inicializarTemporizador() { // Inicializa o temporizador para animação do menu lateral dessa forma o menu lateral abre e fecha com animação
        temporizadorAnimacao = new Timer(TIMER_DELAY_MS, e -> {
            if (menuAberto) {
                larguraAtual = Math.min(larguraAtual + ANIMATION_STEP, larguraAlvo);
            } else {
                larguraAtual = Math.max(larguraAtual - ANIMATION_STEP, 0);
            }
            painelLateral.setPreferredSize(new Dimension(larguraAtual, getHeight())); 
            painelLateral.revalidate();
            if (larguraAtual == larguraAlvo || larguraAtual == 0) {
                temporizadorAnimacao.stop();
            }
        });
    }

    private void inicializarPainelLateral() { 
        painelLateral = new MenuLateral(this);
        painelLateral.setPreferredSize(new Dimension(0, getHeight()));
        add(painelLateral, BorderLayout.WEST);
    }

    private void inicializarBarraSuperior() {
        barraSuperior = new BarraSuperior(this);
        barraSuperior.btnHamburguer.addActionListener(e -> alternarMenu());
        add(barraSuperior, BorderLayout.NORTH);
    }

    private void inicializarPainelPrincipal() {
        layoutCartoes = new CardLayout();
        painelPrincipal = new JPanel(layoutCartoes);
        painelPrincipal.setBackground(UIConstants.BACKGROUND);

        // Adiciona os painéis principais da aplicação deixei comentado ja que ainda não implementamos todos os painéis
        // mas assim que implementarmos podemos descomentar e adicionar aqui que ja vai aparecer quando vcs rodarem
        painelPrincipal.add(new PainelHome(this), "Home");
        painelPrincipal.add(new PainelClientes(bancoDeDados), "Clientes");
        painelPrincipal.add(new PainelVeterinarios(bancoDeDados), "Veterinários");
        painelPrincipal.add(new PainelAnimais(bancoDeDados), "Animais");
        painelPrincipal.add(new PainelProcedimentos(bancoDeDados), "Procedimentos");
        painelPrincipal.add(new PainelConsultas(bancoDeDados), "Consultas");

        add(painelPrincipal, BorderLayout.CENTER);
    }

    private void alternarMenu() { 
        menuAberto = !menuAberto; // serve para alterar o menu entre aberto e fechado
        larguraAlvo = menuAberto ? MENU_WIDTH : 0;
        temporizadorAnimacao.start();
    }

    public void atualizarSelecaoMenu(String nome) {
        selecaoAtual = nome; 
        for (JButton btn : painelLateral.getBotoesMenu()) {
            if (btn.getText().equals(nome)) {
                btn.setBackground(UIConstants.PRIMARY);
                btn.setFont(btn.getFont().deriveFont(Font.BOLD));
            } else {
                btn.setBackground(UIConstants.SECONDARY);
                btn.setFont(btn.getFont().deriveFont(Font.PLAIN));
            }
        }
        layoutCartoes.show(painelPrincipal, nome);
    }

    public void atalhos() { // atalhos de teclado para selecionar a janela, crtl c e para clientes crtl v para veterinários, 
                            //crtl a para animais, crtl p para procedimentos e crtl s para consultas.
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("control C"), "clientes");
        actionMap.put("clientes", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                atualizarSelecaoMenu("Clientes");
            }
        }
        );
        inputMap.put(KeyStroke.getKeyStroke("control V"), "veterinarios");
        actionMap.put("veterinarios", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                atualizarSelecaoMenu("Veterinários");
            }
        }
        );
        inputMap.put(KeyStroke.getKeyStroke("control A"), "animais");
        actionMap.put("animais", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                atualizarSelecaoMenu("Animais");
            }
        }
        );
        inputMap.put(KeyStroke.getKeyStroke("control P"), "procedimentos");
        actionMap.put("procedimentos", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                atualizarSelecaoMenu("Procedimentos");
            }
        }
        );  
        inputMap.put(KeyStroke.getKeyStroke("control S"), "consultas");
        actionMap.put("consultas", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                atualizarSelecaoMenu("Consultas");
            }
        }
        );
    }

    public Icon loadIcon(String resourcePath) {  //busca o a imagem do ícone no caminho especificado e carrega ele como um ícone
        URL url = getClass().getResource(resourcePath);
        if (url == null) {
            System.err.println("Ícone não encontrado: " + resourcePath);
            return new ImageIcon(new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB));
        }
        return new ImageIcon(url);
    }

    public BufferedImage loadImage(String resourcePath) { 
        try {
            URL url = getClass().getResource(resourcePath);
            if (url == null) {
                System.err.println("Imagem não encontrada: " + resourcePath);
                return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            }
            return ImageIO.read(url); // imageio lê a imagem do caminho especificado
        } catch (IOException e) {
            e.printStackTrace();
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }
    }

    public JButton criarBotaoMenu(String texto, Icon icone) { 
        JButton b = new JButton(texto, icone);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        b.setForeground(UIConstants.WHITE);
        b.setBackground(UIConstants.SECONDARY);
        b.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setFocusPainted(false);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setIconTextGap(10);

        b.addActionListener(e -> atualizarSelecaoMenu(texto));
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                b.setBackground(UIConstants.ACCENT);
            }
            @Override public void mouseExited(MouseEvent e) {
                if (texto.equals(selecaoAtual)) {
                    b.setBackground(UIConstants.PRIMARY);
                } else {
                    b.setBackground(UIConstants.SECONDARY);
                }
            }
        });
        return b;
    }

    // aqui ta toda a questao de responsividade da imagem que ta no fundo da janela principal
    public static class ImagePanel extends JPanel {
        private final Image image;
        public ImagePanel(Image image) {
            this.image = image;
        }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                int w = getWidth(), h = getHeight();
                double imgRatio = (double) image.getWidth(this) / image.getHeight(this);
                int drawW = w, drawH = (int) (w / imgRatio);
                if (drawH > h) {
                    drawH = h;
                    drawW = (int) (h * imgRatio);
                }
                int x = (w - drawW) / 2, y = (h - drawH) / 2;
                g.drawImage(image, x, y, drawW, drawH, this);
            }
        }
    }

    public static void main(String[] args) { // Método main para iniciar a aplicação apenas 
        SwingUtilities.invokeLater(() -> {
            MainWindow janela = new MainWindow();
            janela.setVisible(true);
        });
    }
}
