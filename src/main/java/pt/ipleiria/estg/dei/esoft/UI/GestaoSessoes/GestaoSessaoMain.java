package pt.ipleiria.estg.dei.esoft.UI.GestaoSessoes;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import javax.swing.*;
import java.awt.*;
import pt.ipleiria.estg.dei.esoft.Main;

public class GestaoSessaoMain {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JButton logoButton;
    private JButton sessoesAtivasButton;
    private JButton criarSessaoButton;
    private JButton arquivoButton;
    private JButton alugarFilmeButton;
    private final Cinema cinema;
    private final Runnable onBack;

    public GestaoSessaoMain(Cinema cinema, Runnable onBack) {
        this.cinema = cinema;
        this.onBack = onBack;
        
        // Initialize UI components first
        createUIComponents();
        
        // Setup action listeners
        setupActionListeners();
        
        // Setup logo after components are initialized
        SwingUtilities.invokeLater(this::setupLogo);
    }

    private void createUIComponents() {
        // Create main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(-11685428));
        
        // Create title label
        titleLabel = new JLabel("CinemaLiz - Gestão de Sessões");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Create logo button
        logoButton = new JButton("Logo");
        logoButton.setPreferredSize(new Dimension(50, 50));
        logoButton.setBackground(new Color(-16742214));
        logoButton.setBorderPainted(false);
        
        // Create buttons
        sessoesAtivasButton = new JButton("🎬 Sessões Ativas");
        sessoesAtivasButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        sessoesAtivasButton.setBackground(new Color(-16752737));
        sessoesAtivasButton.setForeground(Color.WHITE);
        sessoesAtivasButton.setPreferredSize(new Dimension(200, 60));
        
        criarSessaoButton = new JButton("➕ Criar Sessão");
        criarSessaoButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        criarSessaoButton.setBackground(new Color(-16752737));
        criarSessaoButton.setForeground(Color.WHITE);
        criarSessaoButton.setPreferredSize(new Dimension(200, 60));
        
        arquivoButton = new JButton("📁 Arquivo");
        arquivoButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        arquivoButton.setBackground(new Color(-16752737));
        arquivoButton.setForeground(Color.WHITE);
        arquivoButton.setPreferredSize(new Dimension(200, 60));
        
        alugarFilmeButton = new JButton("🎥 Alugar Filme");
        alugarFilmeButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        alugarFilmeButton.setBackground(new Color(-16752737));
        alugarFilmeButton.setForeground(Color.WHITE);
        alugarFilmeButton.setPreferredSize(new Dimension(200, 60));
        
        // Layout components
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);
        mainPanel.add(titleLabel, gbc);
        
        // Logo button
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 20, 10, 20);
        mainPanel.add(logoButton, gbc);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        buttonsPanel.setBackground(new Color(-11685428));
        buttonsPanel.add(sessoesAtivasButton);
        buttonsPanel.add(criarSessaoButton);
        buttonsPanel.add(arquivoButton);
        buttonsPanel.add(alugarFilmeButton);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 20, 20, 20);
        mainPanel.add(buttonsPanel, gbc);
    }

    private void setupLogo() {
        if (logoButton == null) {
            return; // Components not yet initialized
        }
        
        try {
            java.net.URL logoUrl = getClass().getResource("/logo.png");
            if (logoUrl != null) {
                ImageIcon logoIcon = new ImageIcon(logoUrl);
                Image image = logoIcon.getImage();
                Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                logoIcon = new ImageIcon(newimg);
                logoButton.setIcon(logoIcon);
            } else {
                logoButton.setText("🏠");
                logoButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
            }
        } catch (Exception e) {
            if (logoButton != null) {
                logoButton.setText("🏠");
                logoButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
            }
            e.printStackTrace();
        }
    }

    private void setupActionListeners() {
        if (logoButton != null) {
            logoButton.addActionListener(e -> onBack.run());
        }
        if (sessoesAtivasButton != null) {
            sessoesAtivasButton.addActionListener(e -> Main.mostrarSessoesAtivas());
        }
        if (criarSessaoButton != null) {
            criarSessaoButton.addActionListener(e -> Main.mostrarCriarSessaoPag());
        }
        if (arquivoButton != null) {
            arquivoButton.addActionListener(e -> Main.mostrarArquivoSessoes());
        }
        if (alugarFilmeButton != null) {
            alugarFilmeButton.addActionListener(e -> Main.mostrarAlugarFilmePag());
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
} 