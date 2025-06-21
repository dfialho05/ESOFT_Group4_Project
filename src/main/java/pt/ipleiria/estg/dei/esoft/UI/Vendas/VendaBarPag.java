package pt.ipleiria.estg.dei.esoft.UI.Vendas;

import pt.ipleiria.estg.dei.esoft.Backend.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VendaBarPag extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JList<ProdutoBar> produtosList;
    private JList<pt.ipleiria.estg.dei.esoft.Backend.Menu> menusList;
    private JList<ItemVenda> carrinhoList;
    private JLabel totalLabel;
    private JButton adicionarProdutoButton;
    private JButton adicionarMenuButton;
    private JButton removerItemButton;
    private JButton finalizarVendaButton;
    private JButton cancelarButton;
    private JButton limparCarrinhoButton;
    
    private final Cinema cinema;
    private final Runnable onVendaConcluida;
    private List<ItemVenda> carrinho;
    private DefaultListModel<ProdutoBar> produtosListModel;
    private DefaultListModel<pt.ipleiria.estg.dei.esoft.Backend.Menu> menusListModel;
    private DefaultListModel<ItemVenda> carrinhoListModel;
    
    // Classe interna para representar itens no carrinho
    private static class ItemVenda {
        private final String nome;
        private final double preco;
        private final int quantidade;
        private final String tipo; // "produto" ou "menu"
        
        public ItemVenda(String nome, double preco, int quantidade, String tipo) {
            this.nome = nome;
            this.preco = preco;
            this.quantidade = quantidade;
            this.tipo = tipo;
        }
        
        public String getNome() { return nome; }
        public double getPreco() { return preco; }
        public int getQuantidade() { return quantidade; }
        public String getTipo() { return tipo; }
        public double getPrecoTotal() { return preco * quantidade; }
        
        @Override
        public String toString() {
            return String.format("%s x%d - %.2f€", nome, quantidade, getPrecoTotal());
        }
    }
    
    public VendaBarPag(Cinema cinema, Runnable onVendaConcluida) {
        this.cinema = cinema;
        this.onVendaConcluida = onVendaConcluida;
        this.carrinho = new ArrayList<>();
        
        setupFrame();
        createUIComponents();
        setupActionListeners();
        carregarProdutos();
        carregarMenus();
        atualizarCarrinho();
        
        setVisible(true);
    }
    
    private void setupFrame() {
        setTitle("CinemaLiz - Venda do Bar");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void createUIComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(-15231278));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel titleLabel = new JLabel("🍿 CinemaLiz - Venda do Bar", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Center panel with tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(-15231278));
        tabbedPane.setForeground(Color.WHITE);
        
        // Produtos tab
        JPanel produtosPanel = createProdutosPanel();
        tabbedPane.addTab("Produtos", produtosPanel);
        
        // Menus tab
        JPanel menusPanel = createMenusPanel();
        tabbedPane.addTab("Menus", menusPanel);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Bottom panel
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private JPanel createProdutosPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(-16752737));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE), 
            "🍿 Produtos Disponíveis", 
            0, 0, 
            new Font("SansSerif", Font.BOLD, 16), 
            Color.WHITE
        ));
        
        // Products list
        produtosListModel = new DefaultListModel<>();
        produtosList = new JList<>(produtosListModel);
        produtosList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        produtosList.setBackground(new Color(-14529293));
        produtosList.setForeground(Color.WHITE);
        produtosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        produtosList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane produtosScrollPane = new JScrollPane(produtosList);
        produtosScrollPane.setPreferredSize(new Dimension(400, 300));
        produtosScrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        // Add button
        adicionarProdutoButton = new JButton("➕ Adicionar ao Carrinho");
        adicionarProdutoButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        adicionarProdutoButton.setBackground(new Color(-12423373));
        adicionarProdutoButton.setForeground(Color.WHITE);
        adicionarProdutoButton.setPreferredSize(new Dimension(200, 40));
        adicionarProdutoButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(-16752737));
        buttonPanel.add(adicionarProdutoButton);
        
        panel.add(produtosScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createMenusPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(-16752737));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE), 
            "🍽️ Menus Disponíveis", 
            0, 0, 
            new Font("SansSerif", Font.BOLD, 16), 
            Color.WHITE
        ));
        
        // Menus list
        menusListModel = new DefaultListModel<>();
        menusList = new JList<>(menusListModel);
        menusList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        menusList.setBackground(new Color(-14529293));
        menusList.setForeground(Color.WHITE);
        menusList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menusList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane menusScrollPane = new JScrollPane(menusList);
        menusScrollPane.setPreferredSize(new Dimension(400, 300));
        menusScrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        // Add button
        adicionarMenuButton = new JButton("➕ Adicionar ao Carrinho");
        adicionarMenuButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        adicionarMenuButton.setBackground(new Color(-12423373));
        adicionarMenuButton.setForeground(Color.WHITE);
        adicionarMenuButton.setPreferredSize(new Dimension(200, 40));
        adicionarMenuButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(-16752737));
        buttonPanel.add(adicionarMenuButton);
        
        panel.add(menusScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 10));
        panel.setBackground(new Color(-15231278));
        
        // Carrinho panel
        JPanel carrinhoPanel = new JPanel(new BorderLayout(10, 10));
        carrinhoPanel.setBackground(new Color(-16752737));
        carrinhoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE), 
            "🛒 Carrinho", 
            0, 0, 
            new Font("SansSerif", Font.BOLD, 16), 
            Color.WHITE
        ));
        
        // Carrinho list
        carrinhoListModel = new DefaultListModel<>();
        carrinhoList = new JList<>(carrinhoListModel);
        carrinhoList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        carrinhoList.setBackground(new Color(-14529293));
        carrinhoList.setForeground(Color.WHITE);
        carrinhoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carrinhoList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane carrinhoScrollPane = new JScrollPane(carrinhoList);
        carrinhoScrollPane.setPreferredSize(new Dimension(300, 150));
        carrinhoScrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        // Total label
        totalLabel = new JLabel("Total: 0.00€");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        carrinhoPanel.add(carrinhoScrollPane, BorderLayout.CENTER);
        carrinhoPanel.add(totalLabel, BorderLayout.SOUTH);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonsPanel.setBackground(new Color(-15231278));
        
        removerItemButton = new JButton("🗑️ Remover Item");
        removerItemButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        removerItemButton.setBackground(new Color(-14575885));
        removerItemButton.setForeground(Color.WHITE);
        removerItemButton.setPreferredSize(new Dimension(150, 40));
        removerItemButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        limparCarrinhoButton = new JButton("🧹 Limpar Carrinho");
        limparCarrinhoButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        limparCarrinhoButton.setBackground(new Color(-14575885));
        limparCarrinhoButton.setForeground(Color.WHITE);
        limparCarrinhoButton.setPreferredSize(new Dimension(150, 40));
        limparCarrinhoButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        cancelarButton = new JButton("❌ Cancelar");
        cancelarButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        cancelarButton.setBackground(new Color(-14575885));
        cancelarButton.setForeground(Color.WHITE);
        cancelarButton.setPreferredSize(new Dimension(150, 40));
        cancelarButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        finalizarVendaButton = new JButton("✅ Finalizar Venda");
        finalizarVendaButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        finalizarVendaButton.setBackground(new Color(-12423373));
        finalizarVendaButton.setForeground(Color.WHITE);
        finalizarVendaButton.setPreferredSize(new Dimension(150, 40));
        finalizarVendaButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        finalizarVendaButton.setEnabled(false);
        
        buttonsPanel.add(removerItemButton);
        buttonsPanel.add(limparCarrinhoButton);
        buttonsPanel.add(cancelarButton);
        buttonsPanel.add(finalizarVendaButton);
        
        panel.add(carrinhoPanel, BorderLayout.NORTH);
        panel.add(buttonsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void setupActionListeners() {
        adicionarProdutoButton.addActionListener(e -> adicionarProduto());
        adicionarMenuButton.addActionListener(e -> adicionarMenu());
        removerItemButton.addActionListener(e -> removerItem());
        limparCarrinhoButton.addActionListener(e -> limparCarrinho());
        cancelarButton.addActionListener(e -> dispose());
        finalizarVendaButton.addActionListener(e -> finalizarVenda());
    }
    
    private void carregarProdutos() {
        produtosListModel.clear();
        
        List<ProdutoBar> produtos = cinema.getProdutosBar().stream()
                .filter(ProdutoBar::isAtivo)
                .collect(Collectors.toList());
        
        for (ProdutoBar produto : produtos) {
            produtosListModel.addElement(produto);
        }
        
        // Definir renderer personalizado para produtos
        produtosList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                if (value instanceof ProdutoBar) {
                    ProdutoBar produto = (ProdutoBar) value;
                    setText(String.format("%s - %.2f€", produto.getNome(), produto.getPreco()));
                    
                    if (produto.getDescricao() != null && !produto.getDescricao().trim().isEmpty()) {
                        setToolTipText(produto.getDescricao());
                    }
                }
                
                if (isSelected) {
                    setBackground(new Color(-12423373));
                    setForeground(Color.WHITE);
                } else {
                    setBackground(new Color(-14529293));
                    setForeground(Color.WHITE);
                }
                
                return this;
            }
        });
    }
    
    private void carregarMenus() {
        menusListModel.clear();
        
        List<pt.ipleiria.estg.dei.esoft.Backend.Menu> menus = cinema.getMenus().stream()
                .filter(pt.ipleiria.estg.dei.esoft.Backend.Menu::isAtivo)
                .collect(Collectors.toList());
        
        for (pt.ipleiria.estg.dei.esoft.Backend.Menu menu : menus) {
            menusListModel.addElement(menu);
        }
        
        // Definir renderer personalizado para menus
        menusList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                if (value instanceof pt.ipleiria.estg.dei.esoft.Backend.Menu) {
                    pt.ipleiria.estg.dei.esoft.Backend.Menu menu = (pt.ipleiria.estg.dei.esoft.Backend.Menu) value;
                    setText(String.format("%s - %.2f€", menu.getNome(), menu.getPrecoTotal()));
                    
                    String descricao = menu.getDescricao();
                    if (descricao != null && !descricao.trim().isEmpty()) {
                        setToolTipText(descricao);
                    }
                }
                
                if (isSelected) {
                    setBackground(new Color(-12423373));
                    setForeground(Color.WHITE);
                } else {
                    setBackground(new Color(-14529293));
                    setForeground(Color.WHITE);
                }
                
                return this;
            }
        });
    }
    
    private void adicionarProduto() {
        ProdutoBar produtoSelecionado = produtosList.getSelectedValue();
        if (produtoSelecionado == null) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecione um produto para adicionar ao carrinho.", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String quantidadeStr = JOptionPane.showInputDialog(this, 
            "Quantidade de " + produtoSelecionado.getNome() + ":", 
            "1");
        
        if (quantidadeStr == null) return; // User cancelled
        
        try {
            int quantidade = Integer.parseInt(quantidadeStr);
            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "A quantidade deve ser maior que zero.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            ItemVenda item = new ItemVenda(
                produtoSelecionado.getNome(),
                produtoSelecionado.getPreco(),
                quantidade,
                "produto"
            );
            
            carrinho.add(item);
            atualizarCarrinho();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, insira um número válido para a quantidade.", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void adicionarMenu() {
        pt.ipleiria.estg.dei.esoft.Backend.Menu menuSelecionado = menusList.getSelectedValue();
        if (menuSelecionado == null) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecione um menu para adicionar ao carrinho.", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String quantidadeStr = JOptionPane.showInputDialog(this, 
            "Quantidade de " + menuSelecionado.getNome() + ":", 
            "1");
        
        if (quantidadeStr == null) return; // User cancelled
        
        try {
            int quantidade = Integer.parseInt(quantidadeStr);
            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "A quantidade deve ser maior que zero.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            ItemVenda item = new ItemVenda(
                menuSelecionado.getNome(),
                menuSelecionado.getPrecoTotal(),
                quantidade,
                "menu"
            );
            
            carrinho.add(item);
            atualizarCarrinho();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, insira um número válido para a quantidade.", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void removerItem() {
        int selectedIndex = carrinhoList.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < carrinho.size()) {
            carrinho.remove(selectedIndex);
            atualizarCarrinho();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecione um item para remover.", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void limparCarrinho() {
        carrinho.clear();
        atualizarCarrinho();
    }
    
    private void atualizarCarrinho() {
        carrinhoListModel.clear();
        
        for (ItemVenda item : carrinho) {
            carrinhoListModel.addElement(item);
        }
        
        // Definir renderer personalizado para o carrinho
        carrinhoList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                if (value instanceof ItemVenda) {
                    ItemVenda item = (ItemVenda) value;
                    setText(String.format("%s x%d - %.2f€", item.getNome(), item.getQuantidade(), item.getPrecoTotal()));
                    
                    String tooltip = String.format("%s\nQuantidade: %d\nPreço unitário: %.2f€\nTotal: %.2f€", 
                        item.getNome(), item.getQuantidade(), item.getPreco(), item.getPrecoTotal());
                    setToolTipText(tooltip);
                }
                
                if (isSelected) {
                    setBackground(new Color(-12423373));
                    setForeground(Color.WHITE);
                } else {
                    setBackground(new Color(-14529293));
                    setForeground(Color.WHITE);
                }
                
                return this;
            }
        });
        
        double total = carrinho.stream()
                .mapToDouble(ItemVenda::getPrecoTotal)
                .sum();
        
        totalLabel.setText(String.format("Total: %.2f€", total));
        finalizarVendaButton.setEnabled(!carrinho.isEmpty());
    }
    
    private void finalizarVenda() {
        if (carrinho.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "O carrinho está vazio. Adicione itens antes de finalizar a venda.", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        double total = carrinho.stream()
                .mapToDouble(ItemVenda::getPrecoTotal)
                .sum();
        
        int confirmacao = JOptionPane.showConfirmDialog(this, 
            String.format("Confirmar venda?\n\nTotal: %.2f€", total), 
            "Confirmar Venda", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                // Criar nova venda
                int proximoId = cinema.getVendas().stream()
                        .mapToInt(Venda::getId)
                        .max()
                        .orElse(0) + 1;
                
                Venda venda = new Venda(proximoId, java.time.LocalDateTime.now());
                
                // Adicionar produtos e menus à venda
                for (ItemVenda item : carrinho) {
                    if ("produto".equals(item.getTipo())) {
                        // Encontrar o produto correspondente
                        ProdutoBar produto = cinema.getProdutosBar().stream()
                                .filter(p -> p.getNome().equals(item.getNome()))
                                .findFirst()
                                .orElse(null);
                        
                        if (produto != null) {
                            for (int i = 0; i < item.getQuantidade(); i++) {
                                venda.adicionarProduto(produto);
                            }
                        }
                    } else if ("menu".equals(item.getTipo())) {
                        // Encontrar o menu correspondente
                        pt.ipleiria.estg.dei.esoft.Backend.Menu menu = cinema.getMenus().stream()
                                .filter(m -> m.getNome().equals(item.getNome()))
                                .findFirst()
                                .orElse(null);
                        
                        if (menu != null) {
                            for (int i = 0; i < item.getQuantidade(); i++) {
                                venda.adicionarMenu(menu);
                            }
                        }
                    }
                }
                
                // Adicionar venda ao cinema
                cinema.registarVenda(venda);
                
                JOptionPane.showMessageDialog(this, 
                    String.format("Venda finalizada com sucesso!\n\nTotal: %.2f€", total), 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Limpar carrinho e fechar janela
                carrinho.clear();
                atualizarCarrinho();
                
                if (onVendaConcluida != null) {
                    onVendaConcluida.run();
                }
                
                dispose();
                
            } catch (Exception e) {
                System.err.println("Erro ao finalizar venda: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Erro ao finalizar venda: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 