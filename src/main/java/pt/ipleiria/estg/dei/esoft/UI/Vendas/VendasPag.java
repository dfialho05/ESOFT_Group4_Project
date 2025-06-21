package pt.ipleiria.estg.dei.esoft.UI.Vendas;

import pt.ipleiria.estg.dei.esoft.Backend.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VendasPag {
    private JPanel mainPanel;
    private JButton voltarButton;
    private JButton venderBilhetesButton;
    private JButton venderBarButton;
    private JButton relatoriosButton;
    private JLabel totalVendasLabel;
    private JLabel vendasHojeLabel;
    private JLabel bilhetesVendidosLabel;
    private JLabel produtosVendidosLabel;
    private final Cinema cinema;
    private final Runnable onBack;

    public VendasPag(Cinema cinema, Runnable onBack) {
        this.cinema = cinema;
        this.onBack = onBack;
        
        // Initialize UI components
        initializeComponents();
        
        // Setup action listeners
        setupActionListeners();
        
        // Update statistics
        SwingUtilities.invokeLater(this::updateStatistics);
    }

    private void initializeComponents() {
        // Create main panel if it doesn't exist
        if (mainPanel == null) {
            mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout(20, 20));
            mainPanel.setBackground(new Color(-15231278));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Create title
            JLabel titleLabel = new JLabel("CinemaLiz - Vendas", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
            titleLabel.setForeground(Color.WHITE);
            mainPanel.add(titleLabel, BorderLayout.NORTH);
            
            // Create center panel
            JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
            centerPanel.setBackground(new Color(-15231278));
            
            // Create statistics panel
            JPanel statsPanel = createStatsPanel();
            centerPanel.add(statsPanel);
            
            // Create buttons panel
            JPanel buttonsPanel = createButtonsPanel();
            centerPanel.add(buttonsPanel);
            
            mainPanel.add(centerPanel, BorderLayout.CENTER);
        }
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBackground(new Color(-16752737));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        totalVendasLabel = new JLabel("Total de Vendas: 0");
        totalVendasLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        totalVendasLabel.setForeground(Color.WHITE);
        
        vendasHojeLabel = new JLabel("Vendas Hoje: 0");
        vendasHojeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        vendasHojeLabel.setForeground(Color.WHITE);
        
        bilhetesVendidosLabel = new JLabel("Bilhetes Vendidos Hoje: 0");
        bilhetesVendidosLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        bilhetesVendidosLabel.setForeground(Color.WHITE);
        
        produtosVendidosLabel = new JLabel("Produtos Vendidos Hoje: 0");
        produtosVendidosLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        produtosVendidosLabel.setForeground(Color.WHITE);
        
        panel.add(totalVendasLabel);
        panel.add(vendasHojeLabel);
        panel.add(bilhetesVendidosLabel);
        panel.add(produtosVendidosLabel);
        
        return panel;
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 15, 15));
        panel.setBackground(new Color(-15231278));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        venderBilhetesButton = new JButton("Vender Bilhetes");
        venderBilhetesButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        venderBilhetesButton.setBackground(new Color(-12423373));
        venderBilhetesButton.setForeground(Color.WHITE);
        venderBilhetesButton.setPreferredSize(new Dimension(200, 60));
        
        venderBarButton = new JButton("Vender Bar");
        venderBarButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        venderBarButton.setBackground(new Color(-12423373));
        venderBarButton.setForeground(Color.WHITE);
        venderBarButton.setPreferredSize(new Dimension(200, 60));
        
        relatoriosButton = new JButton("Relatórios");
        relatoriosButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        relatoriosButton.setBackground(new Color(-12423373));
        relatoriosButton.setForeground(Color.WHITE);
        relatoriosButton.setPreferredSize(new Dimension(200, 60));
        
        voltarButton = new JButton("Voltar");
        voltarButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        voltarButton.setBackground(new Color(-14575885));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setPreferredSize(new Dimension(200, 60));
        
        panel.add(venderBilhetesButton);
        panel.add(venderBarButton);
        panel.add(relatoriosButton);
        panel.add(voltarButton);
        
        return panel;
    }

    private void setupActionListeners() {
        if (voltarButton != null) {
            voltarButton.addActionListener(e -> onBack.run());
        }
        
        if (venderBilhetesButton != null) {
            venderBilhetesButton.addActionListener(e -> abrirVendaBilhetes());
        }
        
        if (venderBarButton != null) {
            venderBarButton.addActionListener(e -> abrirVendaBar());
        }
        
        if (relatoriosButton != null) {
            relatoriosButton.addActionListener(e -> abrirRelatorios());
        }
    }

    private void updateStatistics() {
        if (totalVendasLabel == null || vendasHojeLabel == null || 
            bilhetesVendidosLabel == null || produtosVendidosLabel == null) {
            return;
        }

        Relatorios relatorios = cinema.getRelatorios();
        
        // Total de vendas
        int totalVendas = cinema.getVendas().size();
        totalVendasLabel.setText("Total de Vendas: " + totalVendas);
        
        // Vendas de hoje
        long vendasHoje = cinema.getVendas().stream()
                .filter(v -> v.getDataVenda().toLocalDate().equals(java.time.LocalDate.now()))
                .count();
        vendasHojeLabel.setText("Vendas Hoje: " + vendasHoje);
        
        // Bilhetes vendidos hoje
        long bilhetesHoje = relatorios.getBilhetesVendidosHoje();
        bilhetesVendidosLabel.setText("Bilhetes Vendidos Hoje: " + bilhetesHoje);
        
        // Produtos vendidos hoje
        long produtosHoje = cinema.getVendas().stream()
                .filter(v -> v.getDataVenda().toLocalDate().equals(java.time.LocalDate.now()))
                .mapToLong(v -> v.getProdutosVendidos().size() + v.getMenusVendidos().size())
                .sum();
        produtosVendidosLabel.setText("Produtos Vendidos Hoje: " + produtosHoje);
    }

    private void abrirVendaBilhetes() {
        try {
            VendaBilhetesPag vendaBilhetesPag = new VendaBilhetesPag(cinema, this::updateStatistics);
        } catch (Exception e) {
            System.err.println("Erro ao abrir VendaBilhetesPag: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainPanel, 
                "Erro ao abrir a página de venda de bilhetes: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirVendaBar() {
        try {
            VendaBarPag vendaBarPag = new VendaBarPag(cinema, this::updateStatistics);
        } catch (Exception e) {
            System.err.println("Erro ao abrir VendaBarPag: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainPanel, 
                "Erro ao abrir a página de venda do bar: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirRelatorios() {
        if (mainPanel != null) {
            StringBuilder relatorio = new StringBuilder();
            relatorio.append("=== RELATÓRIO DE VENDAS ===\n\n");
            
            Relatorios relatorios = cinema.getRelatorios();
            
            // Estatísticas gerais
            relatorio.append("ESTATÍSTICAS GERAIS:\n");
            relatorio.append("• Total de vendas: ").append(cinema.getVendas().size()).append("\n");
            relatorio.append("• Salas ativas: ").append(relatorios.getTotalSalasAtivas()).append("\n");
            relatorio.append("• Sessões programadas hoje: ").append(relatorios.getSessoesProgramadasHoje()).append("\n");
            relatorio.append("• Bilhetes vendidos hoje: ").append(relatorios.getBilhetesVendidosHoje()).append("\n\n");
            
            // Alertas de stock
            List<String> alertas = relatorios.getAlertasStockBaixo();
            if (!alertas.isEmpty()) {
                relatorio.append("ALERTAS DE STOCK BAIXO:\n");
                for (String alerta : alertas) {
                    relatorio.append("• ").append(alerta).append("\n");
                }
                relatorio.append("\n");
            }
            
            JOptionPane.showMessageDialog(mainPanel, 
                relatorio.toString(), 
                "Relatório de Vendas", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
} 