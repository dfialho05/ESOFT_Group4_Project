package pt.ipleiria.estg.dei.esoft.UI;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.UI.Vendas.RelatoriosPag;
import javax.swing.*;
import java.awt.*;

public class MainPag {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel logoLabel;
    private JButton gestaoButton;
    private JButton vendasButton;
    private JButton consultaButton;
    private final Cinema cinema;
    private final Runnable onGestaoClick;
    private final Runnable onVendasClick;

    public MainPag(Cinema cinema, Runnable onGestaoClick, Runnable onVendasClick) {
        this.cinema = cinema;
        this.onGestaoClick = onGestaoClick;
        this.onVendasClick = onVendasClick;
        
        // Initialize UI components
        initializeComponents();
        
        // Setup action listeners
        setupActionListeners();
        
        // Setup logo after components are initialized
        SwingUtilities.invokeLater(this::setupLogo);
    }

    private void initializeComponents() {
        // Create main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(-11685428));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title label
        titleLabel = new JLabel("CinemaLiz", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(titleLabel, gbc);
        
        // Logo label
        logoLabel = new JLabel("", SwingConstants.CENTER);
        logoLabel.setPreferredSize(new Dimension(100, 100));
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(logoLabel, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        buttonPanel.setBackground(new Color(-11685428));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        // Gestão button
        gestaoButton = new JButton("🏢 Gestão");
        gestaoButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        gestaoButton.setBackground(new Color(-16752737));
        gestaoButton.setForeground(Color.WHITE);
        gestaoButton.setPreferredSize(new Dimension(180, 60));
        gestaoButton.setFocusPainted(false);
        
        // Vendas button
        vendasButton = new JButton("💰 Vendas");
        vendasButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        vendasButton.setBackground(new Color(-16752737));
        vendasButton.setForeground(Color.WHITE);
        vendasButton.setPreferredSize(new Dimension(180, 60));
        vendasButton.setFocusPainted(false);
        
        // Consulta button (agora com relatórios)
        consultaButton = new JButton("📊 Consulta & Relatórios");
        consultaButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        consultaButton.setBackground(new Color(-16752737));
        consultaButton.setForeground(Color.WHITE);
        consultaButton.setPreferredSize(new Dimension(180, 60));
        consultaButton.setFocusPainted(false);
        
        // Add buttons to panel
        buttonPanel.add(gestaoButton);
        buttonPanel.add(vendasButton);
        buttonPanel.add(consultaButton);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(buttonPanel, gbc);
    }

    private void setupLogo() {
        try {
            java.net.URL logoUrl = getClass().getResource("/logo.png");
            if (logoUrl != null) {
                ImageIcon logoIcon = new ImageIcon(logoUrl);
                Image image = logoIcon.getImage();
                Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
                logoIcon = new ImageIcon(newimg);
                logoLabel.setIcon(logoIcon);
            } else {
                logoLabel.setText("🎬");
                logoLabel.setFont(new Font("SansSerif", Font.PLAIN, 60));
                logoLabel.setForeground(Color.WHITE);
            }
        } catch (Exception e) {
            logoLabel.setText("🎬");
            logoLabel.setFont(new Font("SansSerif", Font.PLAIN, 60));
            logoLabel.setForeground(Color.WHITE);
            e.printStackTrace();
        }
    }

    private void setupActionListeners() {
        gestaoButton.addActionListener(e -> onGestaoClick.run());
        vendasButton.addActionListener(e -> onVendasClick.run());
        consultaButton.addActionListener(e -> {
            new RelatoriosPag(cinema, () -> {
                // Callback quando fechar os relatórios
            });
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
} 