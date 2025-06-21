package pt.ipleiria.estg.dei.esoft.UI.GestaoSalas;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Backend.Sala;
import pt.ipleiria.estg.dei.esoft.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListaSalasPag {

    private enum SortOrder {
        NONE,
        CAPACIDADE_ASC,
        CAPACIDADE_DESC,
        NOME_ASC,
        NOME_DESC
    }

    // Variáveis para guardar o estado dos filtros
    private Integer filterMinCapacidade = null;
    private String filterTipoSala = null;
    private Boolean filterTemAcessibilidade = null;

    private JPanel mainPanel;
    private JPanel salasPanel;
    private JScrollPane scrollPane;
    private JButton voltarButton;
    private JButton filtrosButton;
    private JButton capacidadeButton;
    private JButton visualizacaoButton;
    private JButton acessibilidadeButton;
    private JButton tipoSalaButton;
    private Cinema cinema;
    private Runnable onBack;
    private SortOrder currentSortOrder = SortOrder.NONE;

    public ListaSalasPag(Cinema cinema, Runnable onBack) {
        this.cinema = cinema;
        this.onBack = onBack;

        // Initialize UI components first
        createUIComponents();
        
        // Setup event listeners after components are initialized
        SwingUtilities.invokeLater(() -> {
            setupEventListeners();
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
        JLabel titleLabel = new JLabel("📋 Lista de Salas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create a container for controls and the list
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setOpaque(false);
        
        // Create top controls panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        topPanel.setBackground(new Color(-11685428));
        
        // Create buttons
        voltarButton = new JButton("🏠 Voltar");
        voltarButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        voltarButton.setBackground(new Color(-14575885));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setPreferredSize(new Dimension(120, 40));
        
        filtrosButton = new JButton("🔍 Limpar Filtros");
        filtrosButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        filtrosButton.setBackground(new Color(-12423373));
        filtrosButton.setForeground(Color.WHITE);
        filtrosButton.setPreferredSize(new Dimension(150, 40));
        
        capacidadeButton = new JButton("📊 Capacidade");
        capacidadeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        capacidadeButton.setBackground(new Color(-12423373));
        capacidadeButton.setForeground(Color.WHITE);
        capacidadeButton.setPreferredSize(new Dimension(150, 40));
        
        tipoSalaButton = new JButton("🎬 Tipo de Sala");
        tipoSalaButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        tipoSalaButton.setBackground(new Color(-12423373));
        tipoSalaButton.setForeground(Color.WHITE);
        tipoSalaButton.setPreferredSize(new Dimension(150, 40));
        
        acessibilidadeButton = new JButton("♿ Acessibilidade");
        acessibilidadeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        acessibilidadeButton.setBackground(new Color(-12423373));
        acessibilidadeButton.setForeground(Color.WHITE);
        acessibilidadeButton.setPreferredSize(new Dimension(150, 40));
        
        // Add buttons to top panel
        topPanel.add(voltarButton);
        topPanel.add(filtrosButton);
        topPanel.add(capacidadeButton);
        topPanel.add(tipoSalaButton);
        topPanel.add(acessibilidadeButton);
        
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

    private void setupEventListeners() {
        if (voltarButton != null) {
            voltarButton.addActionListener(e -> onBack.run());
        }

        // Botão de Ordenar por Capacidade
        if (capacidadeButton != null) {
            capacidadeButton.addActionListener(e -> {
                if (currentSortOrder == SortOrder.CAPACIDADE_ASC) {
                    currentSortOrder = SortOrder.CAPACIDADE_DESC;
                } else {
                    currentSortOrder = SortOrder.CAPACIDADE_ASC;
                }
                loadSalas();
            });
        }
        
        // Botão para limpar filtros
        if (filtrosButton != null) {
            filtrosButton.addActionListener(e -> resetFilters());
        }
        
        // Botão para filtrar por Tipo de Sala
        if (tipoSalaButton != null) {
            tipoSalaButton.addActionListener(e -> filterByTipoSala());
        }
        if (visualizacaoButton != null) {
            visualizacaoButton.addActionListener(e -> filterByTipoSala());
        }

        // Botão para filtrar por Acessibilidade
        if (acessibilidadeButton != null) {
            acessibilidadeButton.addActionListener(e -> filterByAcessibilidade());
        }
    }
    
    private void filterByTipoSala() {
        if (mainPanel == null) return;
        
        Object[] options = {"Normal", "VIP", "Remover Filtro"};
        String result = (String) JOptionPane.showInputDialog(
                mainPanel,
                "Selecione o tipo de sala para filtrar:",
                "Filtrar por Tipo de Sala",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                filterTipoSala
        );
        if (result != null) {
            if (result.equals("Remover Filtro")) {
                filterTipoSala = null;
            } else {
                filterTipoSala = result;
            }
            loadSalas();
        }
    }

    private void filterByAcessibilidade() {
        if (mainPanel == null) return;
        
        Object[] options = {"Com Acessibilidade", "Sem Acessibilidade", "Remover Filtro"};
        String currentSelection;
        if (filterTemAcessibilidade == null) {
            currentSelection = "Remover Filtro";
        } else if (filterTemAcessibilidade) {
            currentSelection = "Com Acessibilidade";
        } else {
            currentSelection = "Sem Acessibilidade";
        }

        String result = (String) JOptionPane.showInputDialog(
                mainPanel,
                "Selecione o filtro de acessibilidade:",
                "Filtrar por Acessibilidade",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                currentSelection
        );

        if (result != null) {
            switch (result) {
                case "Com Acessibilidade":
                    filterTemAcessibilidade = true;
                    break;
                case "Sem Acessibilidade":
                    filterTemAcessibilidade = false;
                    break;
                default:
                    filterTemAcessibilidade = null;
                    break;
            }
            loadSalas();
        }
    }
    
    private void resetFilters() {
        filterMinCapacidade = null;
        filterTipoSala = null;
        filterTemAcessibilidade = null;
        currentSortOrder = SortOrder.NONE; // Também reseta a ordenação
        loadSalas();
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

        List<Sala> salas = cinema.getSalas().stream()
                .filter(Sala::isAtivo)
                // Aplicar filtros
                .filter(s -> filterTipoSala == null || s.getTipoSala().equalsIgnoreCase(filterTipoSala))
                .filter(s -> {
                    if (filterTemAcessibilidade == null) {
                        return true;
                    }
                    boolean hasAccessibility = s.getDescricao() != null && 
                        (s.getDescricao().toLowerCase().contains("cadeira") || 
                         s.getDescricao().toLowerCase().contains("acessível") ||
                         s.getDescricao().toLowerCase().contains("acessibilidade") ||
                         !s.getDescricao().trim().isEmpty());
                    return filterTemAcessibilidade.equals(hasAccessibility);
                })
                .collect(Collectors.toList());

        System.out.println("Salas ativas encontradas: " + salas.size());

        // Aplicar ordenação
        switch (currentSortOrder) {
            case CAPACIDADE_ASC:
                salas.sort(Comparator.comparingInt(Sala::getCapacidade));
                capacidadeButton.setText("Capacidade (ASC)");
                break;
            case CAPACIDADE_DESC:
                salas.sort(Comparator.comparingInt(Sala::getCapacidade).reversed());
                capacidadeButton.setText("Capacidade (DESC)");
                break;
            case NOME_ASC:
                // a ser implementado
                break;
            case NOME_DESC:
                // a ser implementado
                break;
            default:
                // Sem ordenação, manter a ordem da lista
                if (capacidadeButton != null) {
                    capacidadeButton.setText("Capacidade");
                }
                break;
        }
        
        // Atualizar texto dos botões de filtro
        updateFilterButtonStyles();

        if (salas.isEmpty()) {
            JLabel emptyLabel = new JLabel("Não existem salas com os filtros aplicados.");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 20));
            emptyLabel.setForeground(Color.WHITE);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            salasPanel.add(Box.createVerticalGlue());
            salasPanel.add(emptyLabel);
            salasPanel.add(Box.createVerticalGlue());
        } else {
            for (Sala sala : salas) {
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
        detailsPanel.add(createDetailLabel("Estado: Ativa"));
        card.add(detailsPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonsPanel.setOpaque(false);
        JButton editarButton = new JButton("Editar");
        JButton inativarButton = new JButton("Inativar");
        styleButton(editarButton, new Color(33, 150, 243), new Color(25, 118, 210)); 
        styleButton(inativarButton, new Color(244, 67, 54), new Color(211, 47, 47));

        inativarButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(mainPanel, "Tem a certeza que quer inativar a sala '" + sala.getNome() + "'?", "Confirmar Inativação", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                sala.setAtivo(false);
                loadSalas();
            }
        });
        
        editarButton.addActionListener(e -> {
             Main.mostrarEditarSalaPag(sala);
        });

        buttonsPanel.add(editarButton);
        buttonsPanel.add(inativarButton);
        
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

    private void updateFilterButtonStyles() {
        styleButton(tipoSalaButton, filterTipoSala != null);
        styleButton(acessibilidadeButton, filterTemAcessibilidade != null);
    }
    
    private void styleButton(JButton button, boolean active) {
        if (button != null) {
            if (active) {
                button.setBackground(new Color(0x3B82F6)); // Cor ativa
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(UIManager.getColor("Button.background")); // Cor padrão
                button.setForeground(UIManager.getColor("Button.foreground"));
            }
        }
    }
}
