package pt.ipleiria.estg.dei.esoft.UI.Vendas;

import pt.ipleiria.estg.dei.esoft.Backend.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatoriosPag extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JTextArea relatorioGeralArea;
    private JTextArea relatorioVendasArea;
    private JTextArea relatorioSessoesArea;
    private JTextArea relatorioStockArea;
    private JButton voltarButton;
    private JButton atualizarButton;
    private JButton exportarButton;
    
    private final Cinema cinema;
    private final Runnable onBack;
    
    public RelatoriosPag(Cinema cinema, Runnable onBack) {
        this.cinema = cinema;
        this.onBack = onBack;
        
        setupFrame();
        createUIComponents();
        setupActionListeners();
        carregarRelatorios();
        
        setVisible(true);
    }
    
    private void setupFrame() {
        setTitle("CinemaLiz - Consulta & Relatórios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void createUIComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(-15231278));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel titleLabel = new JLabel("📊 CinemaLiz - Consulta & Relatórios", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Center panel with tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(-15231278));
        tabbedPane.setForeground(Color.WHITE);
        
        // Relatório Geral tab
        JPanel relatorioGeralPanel = createRelatorioPanel("📈 Relatório Geral");
        relatorioGeralArea = createTextArea();
        relatorioGeralPanel.add(new JScrollPane(relatorioGeralArea), BorderLayout.CENTER);
        tabbedPane.addTab("📈 Geral", relatorioGeralPanel);
        
        // Relatório de Vendas tab
        JPanel relatorioVendasPanel = createRelatorioPanel("💰 Relatório de Vendas");
        relatorioVendasArea = createTextArea();
        relatorioVendasPanel.add(new JScrollPane(relatorioVendasArea), BorderLayout.CENTER);
        tabbedPane.addTab("💰 Vendas", relatorioVendasPanel);
        
        // Relatório de Sessões tab
        JPanel relatorioSessoesPanel = createRelatorioPanel("🎬 Relatório de Sessões");
        relatorioSessoesArea = createTextArea();
        relatorioSessoesPanel.add(new JScrollPane(relatorioSessoesArea), BorderLayout.CENTER);
        tabbedPane.addTab("🎬 Sessões", relatorioSessoesPanel);
        
        // Relatório de Stock tab
        JPanel relatorioStockPanel = createRelatorioPanel("📦 Relatório de Stock");
        relatorioStockArea = createTextArea();
        relatorioStockPanel.add(new JScrollPane(relatorioStockArea), BorderLayout.CENTER);
        tabbedPane.addTab("📦 Stock", relatorioStockPanel);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Bottom panel with buttons
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private JPanel createRelatorioPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(-16752737));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE), 
            title, 
            0, 0, 
            new Font("SansSerif", Font.BOLD, 16), 
            Color.WHITE
        ));
        return panel;
    }
    
    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setBackground(new Color(-14529293));
        textArea.setForeground(Color.WHITE);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return textArea;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setBackground(new Color(-15231278));
        
        atualizarButton = new JButton("🔄 Atualizar");
        atualizarButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        atualizarButton.setBackground(new Color(-12423373));
        atualizarButton.setForeground(Color.WHITE);
        atualizarButton.setPreferredSize(new Dimension(150, 40));
        atualizarButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        exportarButton = new JButton("💾 Exportar");
        exportarButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        exportarButton.setBackground(new Color(-12423373));
        exportarButton.setForeground(Color.WHITE);
        exportarButton.setPreferredSize(new Dimension(150, 40));
        exportarButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        voltarButton = new JButton("❌ Voltar");
        voltarButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        voltarButton.setBackground(new Color(-14575885));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setPreferredSize(new Dimension(150, 40));
        voltarButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        panel.add(atualizarButton);
        panel.add(exportarButton);
        panel.add(voltarButton);
        
        return panel;
    }
    
    private void setupActionListeners() {
        atualizarButton.addActionListener(e -> carregarRelatorios());
        exportarButton.addActionListener(e -> exportarRelatorio());
        voltarButton.addActionListener(e -> dispose());
    }
    
    private void carregarRelatorios() {
        carregarRelatorioGeral();
        carregarRelatorioVendas();
        carregarRelatorioSessoes();
        carregarRelatorioStock();
    }
    
    private void carregarRelatorioGeral() {
        StringBuilder relatorio = new StringBuilder();
        Relatorios relatorios = cinema.getRelatorios();
        
        relatorio.append("=== RELATÓRIO GERAL DO CINEMA ===\n\n");
        relatorio.append("📅 Data: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n");
        relatorio.append("⏰ Hora: ").append(java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))).append("\n\n");
        
        // Estatísticas gerais
        relatorio.append("📊 ESTATÍSTICAS GERAIS:\n");
        relatorio.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        relatorio.append("• Total de vendas: ").append(cinema.getVendas().size()).append("\n");
        relatorio.append("• Salas ativas: ").append(relatorios.getTotalSalasAtivas()).append("/").append(cinema.getSalas().size()).append("\n");
        relatorio.append("• Filmes ativos: ").append(cinema.getFilmes().stream().filter(Filme::isAtivo).count()).append("/").append(cinema.getFilmes().size()).append("\n");
        relatorio.append("• Sessões programadas hoje: ").append(relatorios.getSessoesProgramadasHoje()).append("\n");
        relatorio.append("• Bilhetes vendidos hoje: ").append(relatorios.getBilhetesVendidosHoje()).append("\n");
        relatorio.append("• Produtos disponíveis: ").append(cinema.getProdutosBar().size()).append("\n");
        relatorio.append("• Menus disponíveis: ").append(cinema.getMenus().size()).append("\n\n");
        
        // Receita total
        double receitaTotal = cinema.getVendas().stream()
                .mapToDouble(Venda::getTotalVenda)
                .sum();
        relatorio.append("💰 RECEITA TOTAL: ").append(String.format("%.2f€", receitaTotal)).append("\n\n");
        
        // Receita de hoje
        double receitaHoje = cinema.getVendas().stream()
                .filter(v -> v.getDataVenda().toLocalDate().equals(LocalDate.now()))
                .mapToDouble(Venda::getTotalVenda)
                .sum();
        relatorio.append("💰 RECEITA HOJE: ").append(String.format("%.2f€", receitaHoje)).append("\n");
        
        relatorioGeralArea.setText(relatorio.toString());
    }
    
    private void carregarRelatorioVendas() {
        StringBuilder relatorio = new StringBuilder();
        
        relatorio.append("=== RELATÓRIO DE VENDAS ===\n\n");
        
        // Vendas de hoje
        relatorio.append("📅 VENDAS DE HOJE:\n");
        relatorio.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        
        List<Venda> vendasHoje = cinema.getVendas().stream()
                .filter(v -> v.getDataVenda().toLocalDate().equals(LocalDate.now()))
                .toList();
        
        if (vendasHoje.isEmpty()) {
            relatorio.append("Nenhuma venda registada hoje.\n\n");
        } else {
            for (int i = 0; i < vendasHoje.size(); i++) {
                Venda venda = vendasHoje.get(i);
                relatorio.append(i + 1).append(". Venda #").append(venda.getId())
                        .append(" - ").append(venda.getDataVenda().format(DateTimeFormatter.ofPattern("HH:mm")))
                        .append(" - ").append(String.format("%.2f€", venda.getTotalVenda())).append("\n");
                
                if (!venda.getBilhetesVendidos().isEmpty()) {
                    relatorio.append("   🎬 Bilhetes: ").append(venda.getBilhetesVendidos().size()).append("\n");
                }
                if (!venda.getProdutosVendidos().isEmpty()) {
                    relatorio.append("   🍿 Produtos: ").append(venda.getProdutosVendidos().size()).append("\n");
                }
                if (!venda.getMenusVendidos().isEmpty()) {
                    relatorio.append("   🍽️ Menus: ").append(venda.getMenusVendidos().size()).append("\n");
                }
                relatorio.append("\n");
            }
        }
        
        // Estatísticas de vendas
        relatorio.append("📊 ESTATÍSTICAS DE VENDAS:\n");
        relatorio.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        
        long totalBilhetes = cinema.getVendas().stream()
                .mapToLong(v -> v.getBilhetesVendidos().size())
                .sum();
        relatorio.append("• Total de bilhetes vendidos: ").append(totalBilhetes).append("\n");
        
        long totalProdutos = cinema.getVendas().stream()
                .mapToLong(v -> v.getProdutosVendidos().size())
                .sum();
        relatorio.append("• Total de produtos vendidos: ").append(totalProdutos).append("\n");
        
        long totalMenus = cinema.getVendas().stream()
                .mapToLong(v -> v.getMenusVendidos().size())
                .sum();
        relatorio.append("• Total de menus vendidos: ").append(totalMenus).append("\n");
        
        double mediaVenda = cinema.getVendas().isEmpty() ? 0 : 
                cinema.getVendas().stream().mapToDouble(Venda::getTotalVenda).average().orElse(0);
        relatorio.append("• Valor médio por venda: ").append(String.format("%.2f€", mediaVenda)).append("\n");
        
        relatorioVendasArea.setText(relatorio.toString());
    }
    
    private void carregarRelatorioSessoes() {
        StringBuilder relatorio = new StringBuilder();
        
        relatorio.append("=== RELATÓRIO DE SESSÕES ===\n\n");
        
        // Sessões de hoje
        relatorio.append("🎬 SESSÕES DE HOJE:\n");
        relatorio.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        
        List<Sessao> sessoesHoje = cinema.getSessoes().stream()
                .filter(s -> s.estaAtivaHoje())
                .toList();
        
        if (sessoesHoje.isEmpty()) {
            relatorio.append("Nenhuma sessão programada para hoje.\n\n");
        } else {
            for (Sessao sessao : sessoesHoje) {
                relatorio.append("• ").append(sessao.getFilme().getTitulo())
                        .append(" - ").append(sessao.getSala().getNome())
                        .append(" - ").append(sessao.getHorario().toString())
                        .append(" - ").append(String.format("%.2f€", sessao.getPrecoBilhete())).append("\n");
                
                int lugaresDisponiveis = sessao.contarLugaresDisponiveisHoje();
                int totalLugares = sessao.getSala().getCapacidade();
                double ocupacao = ((double) (totalLugares - lugaresDisponiveis) / totalLugares) * 100;
                
                relatorio.append("  Lugares: ").append(totalLugares - lugaresDisponiveis)
                        .append("/").append(totalLugares)
                        .append(" (").append(String.format("%.1f%%", ocupacao)).append(" ocupação)\n\n");
            }
        }
        
        // Estatísticas de sessões
        relatorio.append("📊 ESTATÍSTICAS DE SESSÕES:\n");
        relatorio.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        
        long sessoesAtivas = cinema.getSessoes().stream().filter(Sessao::isAtivo).count();
        relatorio.append("• Total de sessões ativas: ").append(sessoesAtivas).append("\n");
        
        long sessoesHojeCount = sessoesHoje.size();
        relatorio.append("• Sessões programadas hoje: ").append(sessoesHojeCount).append("\n");
        
        long sessoesFuturas = cinema.getSessoes().stream()
                .filter(s -> s.getDataFim().isAfter(LocalDate.now()))
                .count();
        relatorio.append("• Sessões futuras: ").append(sessoesFuturas).append("\n");
        
        double ocupacaoMedia = cinema.getSessoes().stream()
                .filter(s -> s.estaAtivaHoje())
                .mapToDouble(s -> s.getPercentagemOcupacaoHoje())
                .average()
                .orElse(0);
        relatorio.append("• Ocupação média hoje: ").append(String.format("%.1f%%", ocupacaoMedia)).append("\n");
        
        relatorioSessoesArea.setText(relatorio.toString());
    }
    
    private void carregarRelatorioStock() {
        StringBuilder relatorio = new StringBuilder();
        
        relatorio.append("=== RELATÓRIO DE STOCK ===\n\n");
        
        // Produtos com stock baixo
        relatorio.append("⚠️ ALERTAS DE STOCK BAIXO:\n");
        relatorio.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        
        List<ProdutoBar> produtosStockBaixo = cinema.getProdutosBar().stream()
                .filter(p -> p.getStockAtual() <= p.getStockMinimo())
                .toList();
        
        if (produtosStockBaixo.isEmpty()) {
            relatorio.append("Nenhum produto com stock baixo.\n\n");
        } else {
            for (ProdutoBar produto : produtosStockBaixo) {
                relatorio.append("• ").append(produto.getNome())
                        .append(" - Stock: ").append(produto.getStockAtual())
                        .append(" (mínimo: ").append(produto.getStockMinimo()).append(")\n");
            }
            relatorio.append("\n");
        }
        
        // Lista completa de produtos
        relatorio.append("📦 ESTADO DO STOCK:\n");
        relatorio.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        
        for (ProdutoBar produto : cinema.getProdutosBar()) {
            String status = produto.getStockAtual() <= produto.getStockMinimo() ? "⚠️" : "✅";
            relatorio.append(status).append(" ").append(produto.getNome())
                    .append(" - Stock: ").append(produto.getStockAtual())
                    .append(" - Mínimo: ").append(produto.getStockMinimo())
                    .append(" - Preço: ").append(String.format("%.2f€", produto.getPreco())).append("\n");
        }
        
        // Estatísticas de stock
        relatorio.append("\n📊 ESTATÍSTICAS DE STOCK:\n");
        relatorio.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        
        long produtosComStockBaixo = produtosStockBaixo.size();
        relatorio.append("• Produtos com stock baixo: ").append(produtosComStockBaixo).append("\n");
        
        long produtosAtivos = cinema.getProdutosBar().stream().filter(ProdutoBar::isAtivo).count();
        relatorio.append("• Produtos ativos: ").append(produtosAtivos).append("/").append(cinema.getProdutosBar().size()).append("\n");
        
        double valorTotalStock = cinema.getProdutosBar().stream()
                .mapToDouble(p -> p.getPreco() * p.getStockAtual())
                .sum();
        relatorio.append("• Valor total em stock: ").append(String.format("%.2f€", valorTotalStock)).append("\n");
        
        relatorioStockArea.setText(relatorio.toString());
    }
    
    private void exportarRelatorio() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Exportar Relatório");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSelectedFile(new java.io.File("relatorio_cinema_" + 
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".txt"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                java.io.File file = fileChooser.getSelectedFile();
                java.io.PrintWriter writer = new java.io.PrintWriter(file);
                
                // Escrever todos os relatórios
                writer.println("=== RELATÓRIO COMPLETO DO CINEMA ===\n");
                writer.println("Data: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                writer.println("Hora: " + java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
                writer.println("\n" + "=".repeat(50) + "\n");
                
                writer.println("RELATÓRIO GERAL:");
                writer.println(relatorioGeralArea.getText());
                writer.println("\n" + "=".repeat(50) + "\n");
                
                writer.println("RELATÓRIO DE VENDAS:");
                writer.println(relatorioVendasArea.getText());
                writer.println("\n" + "=".repeat(50) + "\n");
                
                writer.println("RELATÓRIO DE SESSÕES:");
                writer.println(relatorioSessoesArea.getText());
                writer.println("\n" + "=".repeat(50) + "\n");
                
                writer.println("RELATÓRIO DE STOCK:");
                writer.println(relatorioStockArea.getText());
                
                writer.close();
                
                JOptionPane.showMessageDialog(this, 
                    "Relatório exportado com sucesso para:\n" + file.getAbsolutePath(), 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                    
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao exportar relatório: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 