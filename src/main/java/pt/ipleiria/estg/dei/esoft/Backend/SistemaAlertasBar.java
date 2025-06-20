package pt.ipleiria.estg.dei.esoft.Backend;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

/**
 * Classe para gerir o sistema de alertas do bar
 */
public class SistemaAlertasBar {
    
    private List<ProdutoBar> produtos;
    private boolean alertasAtivos;
    
    /**
     * Construtor padrão
     */
    public SistemaAlertasBar() {
        this.produtos = new ArrayList<>();
        this.alertasAtivos = true;
    }
    
    /**
     * Construtor com lista de produtos
     */
    public SistemaAlertasBar(List<ProdutoBar> produtos) {
        this.produtos = produtos != null ? new ArrayList<>(produtos) : new ArrayList<>();
        this.alertasAtivos = true;
    }
    
    // Getters e Setters
    
    public List<ProdutoBar> getProdutos() {
        return new ArrayList<>(produtos);
    }
    
    public void setProdutos(List<ProdutoBar> produtos) {
        this.produtos = produtos != null ? new ArrayList<>(produtos) : new ArrayList<>();
    }
    
    public boolean isAlertasAtivos() {
        return alertasAtivos;
    }
    
    public void setAlertasAtivos(boolean alertasAtivos) {
        this.alertasAtivos = alertasAtivos;
    }
    
    // Métodos de gestão de produtos
    
    /**
     * Adiciona um produto ao sistema
     */
    public void adicionarProduto(ProdutoBar produto) {
        if (produto != null && !produtos.contains(produto)) {
            produtos.add(produto);
        }
    }
    
    /**
     * Remove um produto do sistema
     */
    public boolean removerProduto(ProdutoBar produto) {
        return produtos.remove(produto);
    }
    
    /**
     * Remove um produto pelo ID
     */
    public boolean removerProdutoPorId(int idProduto) {
        return produtos.removeIf(p -> p.getIdProduto() == idProduto);
    }
    
    /**
     * Obtém um produto pelo ID
     */
    public ProdutoBar getProdutoPorId(int idProduto) {
        return produtos.stream()
                      .filter(p -> p.getIdProduto() == idProduto)
                      .findFirst()
                      .orElse(null);
    }
    
    /**
     * Obtém produtos por tipo
     */
    public List<ProdutoBar> getProdutosPorTipo(String tipo) {
        return produtos.stream()
                      .filter(p -> p.getTipo().equalsIgnoreCase(tipo))
                      .collect(Collectors.toList());
    }
    
    /**
     * Obtém produtos ativos
     */
    public List<ProdutoBar> getProdutosAtivos() {
        return produtos.stream()
                      .filter(ProdutoBar::isAtivo)
                      .collect(Collectors.toList());
    }
    
    /**
     * Obtém produtos com stock
     */
    public List<ProdutoBar> getProdutosComStock() {
        return produtos.stream()
                      .filter(ProdutoBar::temStock)
                      .collect(Collectors.toList());
    }
    
    /**
     * Obtém produtos esgotados
     */
    public List<ProdutoBar> getProdutosEsgotados() {
        return produtos.stream()
                      .filter(ProdutoBar::isEsgotado)
                      .collect(Collectors.toList());
    }
    
    /**
     * Obtém produtos com stock baixo
     */
    public List<ProdutoBar> getProdutosComStockBaixo() {
        return produtos.stream()
                      .filter(ProdutoBar::isStockBaixo)
                      .collect(Collectors.toList());
    }
    
    // Métodos de alertas
    
    /**
     * Obtém todos os alertas de stock baixo
     */
    public List<String> getTodosAlertas() {
        if (!alertasAtivos) {
            return new ArrayList<>();
        }
        
        return produtos.stream()
                      .map(ProdutoBar::getAlertaStockBaixo)
                      .filter(alerta -> alerta != null)
                      .collect(Collectors.toList());
    }
    
    /**
     * Obtém alertas de produtos esgotados
     */
    public List<String> getAlertasEsgotados() {
        if (!alertasAtivos) {
            return new ArrayList<>();
        }
        
        return produtos.stream()
                      .filter(ProdutoBar::isEsgotado)
                      .map(ProdutoBar::getAlertaStockBaixo)
                      .filter(alerta -> alerta != null)
                      .collect(Collectors.toList());
    }
    
    /**
     * Obtém alertas de produtos com stock baixo (não esgotados)
     */
    public List<String> getAlertasStockBaixo() {
        if (!alertasAtivos) {
            return new ArrayList<>();
        }
        
        return produtos.stream()
                      .filter(p -> p.isStockBaixo() && !p.isEsgotado())
                      .map(ProdutoBar::getAlertaStockBaixo)
                      .filter(alerta -> alerta != null)
                      .collect(Collectors.toList());
    }
    
    /**
     * Verifica se há alertas
     */
    public boolean temAlertas() {
        return !getTodosAlertas().isEmpty();
    }
    
    /**
     * Verifica se há produtos esgotados
     */
    public boolean temProdutosEsgotados() {
        return !getProdutosEsgotados().isEmpty();
    }
    
    /**
     * Verifica se há produtos com stock baixo
     */
    public boolean temProdutosComStockBaixo() {
        return !getProdutosComStockBaixo().isEmpty();
    }
    
    /**
     * Obtém resumo de alertas
     */
    public String getResumoAlertas() {
        if (!alertasAtivos) {
            return "Sistema de alertas desativado";
        }
        
        List<ProdutoBar> esgotados = getProdutosEsgotados();
        List<ProdutoBar> stockBaixo = getProdutosComStockBaixo();
        
        if (esgotados.isEmpty() && stockBaixo.isEmpty()) {
            return "Nenhum alerta de stock";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESUMO DE ALERTAS ===\n");
        
        if (!esgotados.isEmpty()) {
            sb.append("PRODUTOS ESGOTADOS (").append(esgotados.size()).append("):\n");
            for (ProdutoBar produto : esgotados) {
                sb.append("- ").append(produto.getNome()).append("\n");
            }
            sb.append("\n");
        }
        
        if (!stockBaixo.isEmpty()) {
            sb.append("PRODUTOS COM STOCK BAIXO (").append(stockBaixo.size()).append("):\n");
            for (ProdutoBar produto : stockBaixo) {
                sb.append("- ").append(produto.getNome())
                  .append(" (").append(produto.getStockAtual())
                  .append("/").append(produto.getStockMinimo()).append(")\n");
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Obtém relatório completo de stock
     */
    public String getRelatorioStock() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO DE STOCK ===\n");
        sb.append("Total de produtos: ").append(produtos.size()).append("\n");
        sb.append("Produtos ativos: ").append(getProdutosAtivos().size()).append("\n");
        sb.append("Produtos com stock: ").append(getProdutosComStock().size()).append("\n");
        sb.append("Produtos esgotados: ").append(getProdutosEsgotados().size()).append("\n");
        sb.append("Produtos com stock baixo: ").append(getProdutosComStockBaixo().size()).append("\n\n");
        
        // Agrupar produtos por tipo
        Map<String, List<ProdutoBar>> produtosPorTipo = produtos.stream()
                .collect(Collectors.groupingBy(ProdutoBar::getTipo));
        
        for (Map.Entry<String, List<ProdutoBar>> entry : produtosPorTipo.entrySet()) {
            String tipo = entry.getKey();
            List<ProdutoBar> produtosTipo = entry.getValue();
            
            sb.append(tipo).append(" (").append(produtosTipo.size()).append("):\n");
            for (ProdutoBar produto : produtosTipo) {
                sb.append("  - ").append(produto.getInfoResumida()).append("\n");
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    /**
     * Obtém valor total em stock
     */
    public double getValorTotalStock() {
        return produtos.stream()
                      .mapToDouble(ProdutoBar::getValorTotalStock)
                      .sum();
    }
    
    /**
     * Obtém valor total em stock formatado
     */
    public String getValorTotalStockFormatado() {
        return String.format("%.2f €", getValorTotalStock());
    }
    
    /**
     * Obtém estatísticas de stock
     */
    public String getEstatisticasStock() {
        int total = produtos.size();
        int ativos = getProdutosAtivos().size();
        int comStock = getProdutosComStock().size();
        int esgotados = getProdutosEsgotados().size();
        int stockBaixo = getProdutosComStockBaixo().size();
        double valorTotal = getValorTotalStock();
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTATÍSTICAS DE STOCK ===\n");
        sb.append("Total de produtos: ").append(total).append("\n");
        sb.append("Produtos ativos: ").append(ativos).append(" (").append(String.format("%.1f", (double) ativos / total * 100)).append("%)\n");
        sb.append("Produtos com stock: ").append(comStock).append(" (").append(String.format("%.1f", (double) comStock / total * 100)).append("%)\n");
        sb.append("Produtos esgotados: ").append(esgotados).append(" (").append(String.format("%.1f", (double) esgotados / total * 100)).append("%)\n");
        sb.append("Produtos com stock baixo: ").append(stockBaixo).append(" (").append(String.format("%.1f", (double) stockBaixo / total * 100)).append("%)\n");
        sb.append("Valor total em stock: ").append(getValorTotalStockFormatado()).append("\n");
        
        return sb.toString();
    }
    
    // Métodos de ativação/desativação
    
    /**
     * Ativa o sistema de alertas
     */
    public void ativarAlertas() {
        this.alertasAtivos = true;
    }
    
    /**
     * Desativa o sistema de alertas
     */
    public void desativarAlertas() {
        this.alertasAtivos = false;
    }
    
    /**
     * Alterna o estado dos alertas
     */
    public void alternarAlertas() {
        this.alertasAtivos = !this.alertasAtivos;
    }
    
    // Métodos de validação
    
    /**
     * Verifica se o sistema é válido
     */
    public boolean isSistemaValido() {
        return produtos != null && !produtos.isEmpty();
    }
    
    /**
     * Obtém produtos inválidos
     */
    public List<ProdutoBar> getProdutosInvalidos() {
        return produtos.stream()
                      .filter(p -> !p.isProdutoValido())
                      .collect(Collectors.toList());
    }
    
    @Override
    public String toString() {
        return "SistemaAlertasBar{" +
                "produtos=" + produtos.size() +
                ", alertasAtivos=" + alertasAtivos +
                ", temAlertas=" + temAlertas() +
                '}';
    }
} 