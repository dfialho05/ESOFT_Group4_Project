package pt.ipleiria.estg.dei.esoft.UI.Vendas;

import pt.ipleiria.estg.dei.esoft.Backend.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VendaBilhetesPag extends JFrame {
    private JPanel mainPanel;
    private JComboBox<String> sessaoComboBox;
    private JComboBox<String> dataComboBox;
    private JPanel lugaresPanel;
    private JScrollPane lugaresScrollPane;
    private JLabel precoTotalLabel;
    private JLabel lugaresSelecionadosLabel;
    private JButton finalizarVendaButton;
    private JButton cancelarButton;
    private JButton limparSelecaoButton;
    private JLabel infoSessaoLabel;
    private JLabel horarioDisplayLabel;
    
    private final Cinema cinema;
    private final Runnable onVendaConcluida;
    private List<Bilhete.Lugar> lugaresSelecionados;
    private Sessao sessaoAtual;
    private LocalDate dataAtual;
    private List<Sessao> sessoesAtivas;
    private List<LocalDate> datasDisponiveis;
    
    public VendaBilhetesPag(Cinema cinema, Runnable onVendaConcluida) {
        this.cinema = cinema;
        this.onVendaConcluida = onVendaConcluida;
        this.lugaresSelecionados = new ArrayList<>();
        this.sessoesAtivas = new ArrayList<>();
        this.datasDisponiveis = new ArrayList<>();
        
        setupFrame();
        createUIComponents();
        setupActionListeners();
        carregarSessoes();
        
        setVisible(true);
    }
    
    private void setupFrame() {
        setTitle("CinemaLiz - Venda de Bilhetes");
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
        JLabel titleLabel = new JLabel("Venda de Bilhetes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(20, 20));
        centerPanel.setBackground(new Color(-15231278));
        
        // Selection panel
        JPanel selectionPanel = createSelectionPanel();
        centerPanel.add(selectionPanel, BorderLayout.NORTH);
        
        // Lugares panel
        JPanel lugaresContainerPanel = createLugaresPanel();
        centerPanel.add(lugaresContainerPanel, BorderLayout.CENTER);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Bottom panel
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private JPanel createSelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(-16752737));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE), 
            "Seleção de Sessão", 
            0, 0, 
            new Font("SansSerif", Font.BOLD, 16), 
            Color.WHITE
        ));
        
        // Top row - Sessão, Data and Horário
        JPanel topRow = new JPanel(new GridLayout(1, 6, 10, 5));
        topRow.setBackground(new Color(-16752737));
        
        // Sessão selection
        JLabel sessaoLabel = new JLabel("Sessão:");
        sessaoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        sessaoLabel.setForeground(Color.WHITE);
        
        sessaoComboBox = new JComboBox<>();
        sessaoComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        // Data selection
        JLabel dataLabel = new JLabel("Data:");
        dataLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        dataLabel.setForeground(Color.WHITE);
        
        dataComboBox = new JComboBox<>();
        dataComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        // Horário display (read-only)
        JLabel horarioLabel = new JLabel("Horário:");
        horarioLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        horarioLabel.setForeground(Color.WHITE);
        
        horarioDisplayLabel = new JLabel("--:--");
        horarioDisplayLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        horarioDisplayLabel.setForeground(Color.YELLOW);
        horarioDisplayLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        horarioDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        topRow.add(sessaoLabel);
        topRow.add(sessaoComboBox);
        topRow.add(dataLabel);
        topRow.add(dataComboBox);
        topRow.add(horarioLabel);
        topRow.add(horarioDisplayLabel);
        
        // Info panel
        infoSessaoLabel = new JLabel("Selecione uma sessão e data para ver os lugares disponíveis");
        infoSessaoLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        infoSessaoLabel.setForeground(Color.WHITE);
        infoSessaoLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        panel.add(topRow, BorderLayout.NORTH);
        panel.add(infoSessaoLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createLugaresPanel() {
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(new Color(-15231278));
        
        JLabel lugaresLabel = new JLabel("Seleção de Lugares", SwingConstants.CENTER);
        lugaresLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        lugaresLabel.setForeground(Color.WHITE);
        containerPanel.add(lugaresLabel, BorderLayout.NORTH);
        
        lugaresPanel = new JPanel();
        lugaresPanel.setLayout(new BoxLayout(lugaresPanel, BoxLayout.Y_AXIS));
        lugaresPanel.setBackground(new Color(-14529293));
        
        lugaresScrollPane = new JScrollPane(lugaresPanel);
        lugaresScrollPane.setPreferredSize(new Dimension(600, 300));
        containerPanel.add(lugaresScrollPane, BorderLayout.CENTER);
        
        return containerPanel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 10));
        panel.setBackground(new Color(-15231278));
        
        // Info panel
        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        infoPanel.setBackground(new Color(-16752737));
        infoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE), 
            "Informações", 
            0, 0, 
            new Font("SansSerif", Font.BOLD, 14), 
            Color.WHITE
        ));
        
        lugaresSelecionadosLabel = new JLabel("Lugares selecionados: 0");
        lugaresSelecionadosLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lugaresSelecionadosLabel.setForeground(Color.WHITE);
        
        precoTotalLabel = new JLabel("Preço total: 0.00€");
        precoTotalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        precoTotalLabel.setForeground(Color.WHITE);
        
        infoPanel.add(lugaresSelecionadosLabel);
        infoPanel.add(precoTotalLabel);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonsPanel.setBackground(new Color(-15231278));
        
        limparSelecaoButton = new JButton("Limpar Seleção");
        limparSelecaoButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        limparSelecaoButton.setBackground(new Color(-14575885));
        limparSelecaoButton.setForeground(Color.WHITE);
        limparSelecaoButton.setPreferredSize(new Dimension(150, 40));
        
        cancelarButton = new JButton("Cancelar");
        cancelarButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        cancelarButton.setBackground(new Color(-14575885));
        cancelarButton.setForeground(Color.WHITE);
        cancelarButton.setPreferredSize(new Dimension(150, 40));
        
        finalizarVendaButton = new JButton("Finalizar Venda");
        finalizarVendaButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        finalizarVendaButton.setBackground(new Color(-12423373));
        finalizarVendaButton.setForeground(Color.WHITE);
        finalizarVendaButton.setPreferredSize(new Dimension(150, 40));
        finalizarVendaButton.setEnabled(false);
        
        buttonsPanel.add(limparSelecaoButton);
        buttonsPanel.add(cancelarButton);
        buttonsPanel.add(finalizarVendaButton);
        
        panel.add(infoPanel, BorderLayout.NORTH);
        panel.add(buttonsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void setupActionListeners() {
        sessaoComboBox.addActionListener(e -> {
            int selectedIndex = sessaoComboBox.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < sessoesAtivas.size()) {
                sessaoAtual = sessoesAtivas.get(selectedIndex);
                carregarDatas();
                atualizarInfoSessao();
            } else {
                sessaoAtual = null;
                dataComboBox.removeAllItems();
                limparLugares();
            }
        });
        
        dataComboBox.addActionListener(e -> {
            int selectedIndex = dataComboBox.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < datasDisponiveis.size()) {
                dataAtual = datasDisponiveis.get(selectedIndex);
                atualizarLugares();
            } else {
                dataAtual = null;
                limparLugares();
            }
        });
        
        limparSelecaoButton.addActionListener(e -> limparSelecao());
        cancelarButton.addActionListener(e -> dispose());
        finalizarVendaButton.addActionListener(e -> finalizarVenda());
    }
    
    private void carregarSessoes() {
        sessaoComboBox.removeAllItems();
        sessoesAtivas.clear();
        
        System.out.println("Carregando sessões...");
        System.out.println("Total de sessões no cinema: " + cinema.getSessoes().size());
        
        List<Sessao> sessoes = cinema.getSessoes().stream()
                .filter(s -> {
                    boolean ativo = s.isAtivo();
                    boolean temFilme = s.getFilme() != null;
                    boolean temSala = s.getSala() != null;
                    
                    System.out.println("Sessão " + s.getIdSessao() + ": ativo=" + ativo + ", filme=" + temFilme + ", sala=" + temSala);
                    
                    return ativo && temFilme && temSala;
                })
                .collect(Collectors.toList());
        
        System.out.println("Sessões válidas encontradas: " + sessoes.size());
        
        for (Sessao sessao : sessoes) {
            try {
                String descricao = String.format("%s - %s (%s)", 
                    sessao.getFilme().getTitulo(),
                    sessao.getSala().getNome(),
                    sessao.getHorario().toString());
                sessaoComboBox.addItem(descricao);
                sessoesAtivas.add(sessao);
                System.out.println("Adicionada sessão: " + descricao);
            } catch (Exception e) {
                System.err.println("Erro ao processar sessão: " + sessao.getIdSessao() + " - " + e.getMessage());
            }
        }
        
        if (sessoesAtivas.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Não existem sessões ativas e válidas disponíveis.\n\n" +
                "Verifique se:\n" +
                "• Existem filmes ativos\n" +
                "• Existem salas ativas\n" +
                "• Existem sessões configuradas corretamente", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void carregarDatas() {
        dataComboBox.removeAllItems();
        datasDisponiveis.clear();
        
        if (sessaoAtual == null) {
            System.out.println("Sessão atual é null");
            return;
        }
        
        System.out.println("Carregando datas para sessão: " + sessaoAtual.getIdSessao());
        
        try {
            LocalDate data = sessaoAtual.getDataInicio();
            LocalDate dataFim = sessaoAtual.getDataFim();
            LocalDate hoje = java.time.LocalDate.now();
            
            System.out.println("Data início: " + data);
            System.out.println("Data fim: " + dataFim);
            System.out.println("Hoje: " + hoje);
            
            // Garantir que só mostramos datas futuras ou hoje
            if (data.isBefore(hoje)) {
                data = hoje;
                System.out.println("Ajustando data início para hoje: " + data);
            }
            
            while (!data.isAfter(dataFim)) {
                System.out.println("Verificando data: " + data);
                if (sessaoAtual.estaAtivaNaData(data)) {
                    String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    dataComboBox.addItem(dataFormatada);
                    datasDisponiveis.add(data);
                    System.out.println("Adicionada data: " + dataFormatada);
                } else {
                    System.out.println("Data não ativa: " + data);
                }
                data = data.plusDays(1);
            }
            
            System.out.println("Total de datas válidas: " + dataComboBox.getItemCount());
            
            if (dataComboBox.getItemCount() > 0) {
                dataComboBox.setSelectedIndex(0);
                System.out.println("Data selecionada: " + dataComboBox.getSelectedItem());
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Não existem datas válidas para esta sessão.\n\n" +
                    "A sessão pode ter terminado ou não ter datas futuras disponíveis.", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar datas da sessão: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar datas da sessão selecionada: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarInfoSessao() {
        if (sessaoAtual != null) {
            String info = String.format("Filme: %s | Sala: %s | Horário: %s | Preço: %.2f€",
                sessaoAtual.getFilme().getTitulo(),
                sessaoAtual.getSala().getNome(),
                sessaoAtual.getHorario().toString(),
                sessaoAtual.getPrecoBilhete());
            infoSessaoLabel.setText(info);
            
            // Atualizar o horário no display
            horarioDisplayLabel.setText(sessaoAtual.getHorario().toString());
        } else {
            infoSessaoLabel.setText("Selecione uma sessão e data para ver os lugares disponíveis");
            horarioDisplayLabel.setText("--:--");
        }
    }
    
    private void atualizarLugares() {
        lugaresPanel.removeAll();
        
        if (sessaoAtual == null || dataAtual == null) {
            System.out.println("Não é possível atualizar lugares: sessão=" + (sessaoAtual != null) + ", data=" + (dataAtual != null));
            limparLugares();
            return;
        }
        
        System.out.println("Atualizando lugares para sessão " + sessaoAtual.getIdSessao() + " na data " + dataAtual);
        
        Sala sala = sessaoAtual.getSala();
        if (sala == null) {
            System.out.println("Sala é null");
            limparLugares();
            return;
        }
        
        System.out.println("Sala: " + sala.getNome() + ", Configuração: " + sala.getConfiguracao());
        
        try {
            // Get sala configuration
            String configuracao = sala.getConfiguracao();
            if (configuracao == null || configuracao.trim().isEmpty()) {
                System.out.println("Configuração de sala é null ou vazia");
                JOptionPane.showMessageDialog(this, 
                    "A sala selecionada não tem uma configuração válida de lugares.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                limparLugares();
                return;
            }
            
            int numFilas = calcularNumFilas(configuracao);
            int numColunas = calcularNumColunas(configuracao);
            
            System.out.println("Filas: " + numFilas + ", Colunas: " + numColunas);
            
            if (numFilas <= 0 || numColunas <= 0) {
                System.out.println("Configuração inválida: " + configuracao);
                JOptionPane.showMessageDialog(this, 
                    "Configuração de sala inválida: " + configuracao + "\n\n" +
                    "Formato esperado: 'NxM' (ex: 10x8)", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                limparLugares();
                return;
            }
            
            // Create grid panel
            JPanel gridPanel = new JPanel(new GridLayout(numFilas + 1, numColunas, 2, 2));
            gridPanel.setBackground(new Color(-14529293));
            
            // Add column headers
            gridPanel.add(new JLabel("")); // Empty corner
            for (int col = 1; col <= numColunas; col++) {
                JLabel colLabel = new JLabel(String.valueOf(col), SwingConstants.CENTER);
                colLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
                colLabel.setForeground(Color.WHITE);
                gridPanel.add(colLabel);
            }
            
            // Add rows with buttons
            for (int fila = 1; fila <= numFilas; fila++) {
                // Row header
                JLabel rowLabel = new JLabel(String.valueOf(fila), SwingConstants.CENTER);
                rowLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
                rowLabel.setForeground(Color.WHITE);
                gridPanel.add(rowLabel);
                
                // Seat buttons
                for (int col = 1; col <= numColunas; col++) {
                    JButton lugarButton = createLugarButton(fila, col);
                    gridPanel.add(lugarButton);
                }
            }
            
            lugaresPanel.add(gridPanel);
            lugaresPanel.revalidate();
            lugaresPanel.repaint();
            
            System.out.println("Lugares atualizados com sucesso");
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar lugares: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar lugares da sala: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            limparLugares();
        }
    }
    
    private void limparLugares() {
        lugaresPanel.removeAll();
        lugaresPanel.revalidate();
        lugaresPanel.repaint();
    }
    
    private JButton createLugarButton(int fila, int coluna) {
        JButton button = new JButton(fila + "-" + coluna);
        button.setFont(new Font("SansSerif", Font.PLAIN, 10));
        button.setPreferredSize(new Dimension(30, 30));
        
        // Check if seat is available
        boolean disponivel = sessaoAtual.isLugarDisponivel(dataAtual, fila, coluna);
        boolean selecionado = lugaresSelecionados.stream()
                .anyMatch(l -> l.getFila() == fila && l.getColuna() == coluna && l.getDataSessao().equals(dataAtual));
        
        if (selecionado) {
            button.setBackground(new Color(-12423373)); // Green - selected
            button.setForeground(Color.WHITE);
        } else if (disponivel) {
            button.setBackground(new Color(-16752737)); // Blue - available
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(-14575885)); // Gray - occupied
            button.setForeground(Color.WHITE);
            button.setEnabled(false);
        }
        
        if (disponivel) {
            final int filaFinal = fila;
            final int colunaFinal = coluna;
            button.addActionListener(e -> toggleLugar(filaFinal, colunaFinal));
        }
        
        return button;
    }
    
    private void toggleLugar(int fila, int coluna) {
        Bilhete.Lugar lugar = new Bilhete.Lugar(fila, coluna, dataAtual);
        
        if (lugaresSelecionados.contains(lugar)) {
            lugaresSelecionados.remove(lugar);
        } else {
            lugaresSelecionados.add(lugar);
        }
        
        atualizarLugares();
        atualizarInformacoes();
    }
    
    private void atualizarInformacoes() {
        lugaresSelecionadosLabel.setText("Lugares selecionados: " + lugaresSelecionados.size());
        
        double precoTotal = 0.0;
        if (sessaoAtual != null) {
            precoTotal = lugaresSelecionados.size() * sessaoAtual.getPrecoBilhete();
        }
        
        precoTotalLabel.setText(String.format("Preço total: %.2f€", precoTotal));
        
        finalizarVendaButton.setEnabled(!lugaresSelecionados.isEmpty());
    }
    
    private void limparSelecao() {
        lugaresSelecionados.clear();
        atualizarLugares();
        atualizarInformacoes();
    }
    
    private void finalizarVenda() {
        if (lugaresSelecionados.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecione pelo menos um lugar.", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (sessaoAtual == null || dataAtual == null) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecione uma sessão e data.", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Create ticket
        int proximoId = cinema.getVendas().stream()
                .mapToInt(Venda::getId)
                .max()
                .orElse(0) + 1;
        
        Venda venda = new Venda(proximoId, java.time.LocalDateTime.now());
        
        Bilhete bilhete = new Bilhete(proximoId, sessaoAtual);
        bilhete.setLugares(lugaresSelecionados);
        bilhete.setPrecoTotal(lugaresSelecionados.size() * sessaoAtual.getPrecoBilhete());
        
        venda.adicionarBilhete(bilhete);
        cinema.registarVenda(venda);
        
        // Show success message
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Venda realizada com sucesso!\n\n");
        mensagem.append("Filme: ").append(sessaoAtual.getFilme().getTitulo()).append("\n");
        mensagem.append("Sala: ").append(sessaoAtual.getSala().getNome()).append("\n");
        mensagem.append("Data: ").append(dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n");
        mensagem.append("Horário: ").append(sessaoAtual.getHorario()).append("\n");
        mensagem.append("Lugares: ").append(lugaresSelecionados.stream()
                .map(l -> l.getFila() + "-" + l.getColuna())
                .collect(Collectors.joining(", "))).append("\n");
        mensagem.append("Preço total: ").append(String.format("%.2f€", bilhete.getPrecoTotal()));
        
        JOptionPane.showMessageDialog(this, 
            mensagem.toString(), 
            "Venda Concluída", 
            JOptionPane.INFORMATION_MESSAGE);
        
        if (onVendaConcluida != null) {
            onVendaConcluida.run();
        }
        
        dispose();
    }
    
    private int calcularNumFilas(String configuracao) {
        // Simple parsing - assumes format like "5x8" (5 rows, 8 columns)
        try {
            if (configuracao == null || configuracao.trim().isEmpty()) {
                return 0;
            }
            
            String[] parts = configuracao.split("x");
            if (parts.length != 2) {
                return 0;
            }
            
            int filas = Integer.parseInt(parts[0].trim());
            return filas > 0 ? filas : 0;
        } catch (Exception e) {
            System.err.println("Erro ao calcular número de filas: " + e.getMessage());
            return 0;
        }
    }
    
    private int calcularNumColunas(String configuracao) {
        try {
            if (configuracao == null || configuracao.trim().isEmpty()) {
                return 0;
            }
            
            String[] parts = configuracao.split("x");
            if (parts.length != 2) {
                return 0;
            }
            
            int colunas = Integer.parseInt(parts[1].trim());
            return colunas > 0 ? colunas : 0;
        } catch (Exception e) {
            System.err.println("Erro ao calcular número de colunas: " + e.getMessage());
            return 0;
        }
    }
} 