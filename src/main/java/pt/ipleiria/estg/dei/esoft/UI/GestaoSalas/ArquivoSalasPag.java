package pt.ipleiria.estg.dei.esoft.UI.GestaoSalas;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Backend.Sala;
import pt.ipleiria.estg.dei.esoft.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ArquivoSalasPag {

    private JPanel mainPanel;
    private JPanel salasPanel;
    private JScrollPane scrollPane;
    private JButton voltarButton;
    private Cinema cinema;
    private Runnable onBack;

    public ArquivoSalasPag(Cinema cinema, Runnable onBack) {
        this.cinema = cinema;
        this.onBack = onBack;

        // Initialize UI components first
        createUIComponents();
        
        // Setup components after they are initialized
        SwingUtilities.invokeLater(() -> {
            if (voltarButton != null) {
                voltarButton.addActionListener(e -> onBack.run());
            }
            loadSalas();
        });
    }

    private void createUIComponents() {
        // Create main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 20));
        mainPanel.setBackground(new Color(-11685428));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Create title
        JLabel titleLabel = new JLabel("📁 Arquivo de Salas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Create a container for controls and the list
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setOpaque(false);

        // Create top controls panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        topPanel.setBackground(new Color(-11685428));
        
        // Create voltar button
        voltarButton = new JButton("🏠 Voltar");
        voltarButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        voltarButton.setBackground(new Color(-14575885));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setPreferredSize(new Dimension(120, 40));
        
        topPanel.add(voltarButton);
        contentPanel.add(topPanel, BorderLayout.NORTH);
        
        // Create scrollable content panel
        salasPanel = new JPanel();
        salasPanel.setLayout(new BoxLayout(salasPanel, BoxLayout.Y_AXIS));
        salasPanel.setBackground(new Color(0x0091D5));
        
        scrollPane = new JScrollPane(salasPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void loadSalas() {
        if (salasPanel == null) {
            System.err.println("Erro: salasPanel é null");
            return;
        }
        
        salasPanel.removeAll();
        salasPanel.setLayout(new BoxLayout(salasPanel, BoxLayout.Y_AXIS));
        salasPanel.setBackground(new Color(0x0091D5));

        // Debug: verificar todas as salas
        System.out.println("Total de salas no cinema: " + cinema.getSalas().size());
        for (Sala sala : cinema.getSalas()) {
            System.out.println("Sala: " + sala.getNome() + " - Ativa: " + sala.isAtivo());
        }

        List<Sala> inactiveSalas = cinema.getSalas().stream()
                .filter(sala -> !sala.isAtivo())
                .collect(Collectors.toList());

        System.out.println("Salas inativas encontradas: " + inactiveSalas.size());

        if (inactiveSalas.isEmpty()) {
            JLabel emptyLabel = new JLabel("Não existem salas inativas para mostrar.");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 20));
            emptyLabel.setForeground(Color.WHITE);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            salasPanel.add(Box.createVerticalGlue());
            salasPanel.add(emptyLabel);
            salasPanel.add(Box.createVerticalGlue());
        } else {
            for (Sala sala : inactiveSalas) {
                salasPanel.add(createSalaCard(sala));
                salasPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        salasPanel.revalidate();
        salasPanel.repaint();
    }

    private JPanel createSalaCard(Sala sala) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(new Color(236, 239, 241));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(207, 216, 220), 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        card.setMaximumSize(new Dimension(850, 120));
        card.setMinimumSize(new Dimension(600, 120));
        card.setPreferredSize(new Dimension(750, 120));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel imageLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/logo.png")); 
            Image img = icon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imageLabel.setText("Imagem Indisponível");
            imageLabel.setPreferredSize(new Dimension(120, 80));
        }
        card.add(imageLabel, BorderLayout.WEST);

        JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 0, 2));
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        detailsPanel.add(createDetailLabel("ID da sala: " + sala.getId()));
        detailsPanel.add(createDetailLabel("Capacidade: " + sala.getCapacidade()));
        detailsPanel.add(createDetailLabel("Configuração: " + sala.getConfiguracao()));
        String acessibilidade = (sala.getDescricao() != null && !sala.getDescricao().isEmpty()) ? sala.getDescricao() : "Não especificada";
        detailsPanel.add(createDetailLabel("Acessibilidade: " + acessibilidade));
        detailsPanel.add(createDetailLabel("Tipo de sala: " + sala.getTipoSala()));
        detailsPanel.add(createDetailLabel("Estado: Inativa"));
        card.add(detailsPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonsPanel.setOpaque(false);
        JButton ativarButton = new JButton("Ativar");
        styleButton(ativarButton, new Color(76, 175, 80), new Color(56, 142, 60));

        ativarButton.addActionListener(e -> {
            if (mainPanel != null) {
                int result = JOptionPane.showConfirmDialog(mainPanel, "Tem a certeza que quer reativar a sala '" + sala.getNome() + "'?", "Confirmar Ativação", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    sala.setAtivo(true);
                    loadSalas();
                }
            }
        });

        buttonsPanel.add(ativarButton);
        
        JPanel southWrapper = new JPanel(new BorderLayout());
        southWrapper.setOpaque(false);
        southWrapper.add(buttonsPanel, BorderLayout.EAST);
        card.add(southWrapper, BorderLayout.SOUTH);

        return card;
    }
    
    private JLabel createDetailLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        return label;
    }

    private void styleButton(JButton button, Color bgColor, Color hoverColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(8, 18, 8, 18));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }
}
