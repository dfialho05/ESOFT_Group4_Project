package pt.ipleiria.estg.dei.esoft.UI.GestaoFilmes;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Main;

import javax.swing.*;
import java.awt.*;

public class GestaoFilmeMain {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JButton logoButton;
    private JButton pesquisaButton;
    private JButton registoButton;
    private JButton arquivoButton;
    private final Cinema cinema;
    private Runnable onLogoClick;

    public GestaoFilmeMain(Cinema cinema, Runnable onLogoClick) {
        this.cinema = cinema;
        this.onLogoClick = onLogoClick;
        
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
        titleLabel = new JLabel("CinemaLiz - Gestão de Filmes");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Create logo button
        logoButton = new JButton("Logo");
        logoButton.setPreferredSize(new Dimension(50, 50));
        logoButton.setBackground(new Color(-16742214));
        logoButton.setBorderPainted(false);
        
        // Create buttons
        pesquisaButton = new JButton("🔍 Pesquisar Filme");
        pesquisaButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        pesquisaButton.setBackground(new Color(-16752737));
        pesquisaButton.setForeground(Color.WHITE);
        pesquisaButton.setPreferredSize(new Dimension(200, 60));
        
        registoButton = new JButton("➕ Registar Filme");
        registoButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        registoButton.setBackground(new Color(-16752737));
        registoButton.setForeground(Color.WHITE);
        registoButton.setPreferredSize(new Dimension(200, 60));
        
        arquivoButton = new JButton("📁 Arquivo");
        arquivoButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        arquivoButton.setBackground(new Color(-16752737));
        arquivoButton.setForeground(Color.WHITE);
        arquivoButton.setPreferredSize(new Dimension(200, 60));
        
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
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        buttonsPanel.setBackground(new Color(-11685428));
        buttonsPanel.add(pesquisaButton);
        buttonsPanel.add(registoButton);
        buttonsPanel.add(arquivoButton);
        
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
            logoButton.addActionListener(e -> onLogoClick.run());
        }
        if (pesquisaButton != null) {
            pesquisaButton.addActionListener(e -> Main.mostrarPesquisaFilme());
        }
        if (registoButton != null) {
            registoButton.addActionListener(e -> Main.mostrarRegistoFilme());
        }
        if (arquivoButton != null) {
            arquivoButton.addActionListener(e -> Main.mostrarArquivoFilme());
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
} 