package pt.ipleiria.estg.dei.esoft.UI;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import javax.swing.*;
import java.awt.*;

public class GestaoPag {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JButton logoButton;
    private JButton salasButton;
    private JButton filmesButton;
    private JButton sessoesButton;
    private final Cinema cinema;
    private final Runnable onSalasClick;
    private final Runnable onFilmesClick;
    private final Runnable onSessoesClick;
    private final Runnable onVoltarClick;

    public GestaoPag(Cinema cinema, Runnable onSalasClick, Runnable onFilmesClick, 
                     Runnable onSessoesClick, Runnable onVoltarClick) {
        System.out.println("Iniciando GestaoPag...");
        this.cinema = cinema;
        this.onSalasClick = onSalasClick;
        this.onFilmesClick = onFilmesClick;
        this.onSessoesClick = onSessoesClick;
        this.onVoltarClick = onVoltarClick;
        
        // Initialize UI components first
        createUIComponents();
        
        // Setup action listeners
        setupActionListeners();
        
        // Setup logo after components are initialized
        SwingUtilities.invokeLater(this::setupLogo);
        System.out.println("GestaoPag inicializada com sucesso!");
    }

    private void createUIComponents() {
        System.out.println("Criando componentes da UI...");
        
        // Create main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(-11685428));
        
        // Create title label
        titleLabel = new JLabel("CinemaLiz - Gestão");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Create logo button
        logoButton = new JButton("Logo");
        logoButton.setPreferredSize(new Dimension(50, 50));
        logoButton.setBackground(new Color(-16742214));
        logoButton.setBorderPainted(false);
        
        // Create buttons
        salasButton = new JButton("Salas");
        salasButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
        salasButton.setBackground(new Color(-16752737));
        salasButton.setForeground(Color.WHITE);
        salasButton.setPreferredSize(new Dimension(150, 50));
        
        filmesButton = new JButton("Filmes");
        filmesButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
        filmesButton.setBackground(new Color(-16752737));
        filmesButton.setForeground(Color.WHITE);
        filmesButton.setPreferredSize(new Dimension(150, 50));
        
        sessoesButton = new JButton("Sessões");
        sessoesButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
        sessoesButton.setBackground(new Color(-16752737));
        sessoesButton.setForeground(Color.WHITE);
        sessoesButton.setPreferredSize(new Dimension(150, 50));
        
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
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonsPanel.setBackground(new Color(-11685428));
        buttonsPanel.add(salasButton);
        buttonsPanel.add(filmesButton);
        buttonsPanel.add(sessoesButton);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 20, 20, 20);
        mainPanel.add(buttonsPanel, gbc);
        
        System.out.println("mainPanel: " + (mainPanel != null ? "não é null" : "é null"));
        System.out.println("titleLabel: " + (titleLabel != null ? "não é null" : "é null"));
        System.out.println("logoButton: " + (logoButton != null ? "não é null" : "é null"));
        System.out.println("salasButton: " + (salasButton != null ? "não é null" : "é null"));
        System.out.println("filmesButton: " + (filmesButton != null ? "não é null" : "é null"));
        System.out.println("sessoesButton: " + (sessoesButton != null ? "não é null" : "é null"));
    }

    private void setupLogo() {
        System.out.println("Configurando logo...");
        if (logoButton == null) {
            System.err.println("Erro: logoButton é null");
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
                System.out.println("Logo configurado com sucesso!");
            } else {
                logoButton.setText("Logo");
                System.out.println("Logo não encontrado, usando texto");
            }
        } catch (Exception e) {
            if (logoButton != null) {
                logoButton.setText("Logo");
            }
            System.err.println("Erro ao configurar logo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupActionListeners() {
        System.out.println("Configurando action listeners...");
        if (logoButton != null) {
            logoButton.addActionListener(e -> onVoltarClick.run());
            System.out.println("Action listener do logo configurado");
        } else {
            System.err.println("Erro: logoButton é null");
        }
        if (salasButton != null) {
            salasButton.addActionListener(e -> onSalasClick.run());
            System.out.println("Action listener das salas configurado");
        } else {
            System.err.println("Erro: salasButton é null");
        }
        if (filmesButton != null) {
            filmesButton.addActionListener(e -> onFilmesClick.run());
            System.out.println("Action listener dos filmes configurado");
        } else {
            System.err.println("Erro: filmesButton é null");
        }
        if (sessoesButton != null) {
            sessoesButton.addActionListener(e -> onSessoesClick.run());
            System.out.println("Action listener das sessões configurado");
        } else {
            System.err.println("Erro: sessoesButton é null");
        }
    }

    public JPanel getMainPanel() {
        System.out.println("getMainPanel chamado, mainPanel: " + (mainPanel != null ? "não é null" : "é null"));
        return mainPanel;
    }
} 