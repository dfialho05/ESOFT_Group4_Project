package pt.ipleiria.estg.dei.esoft.Backend;

/**
 * Classe que representa um produto do bar
 */
public class ProdutoBar {
    
    private int idProduto;
    private String nome;
    private String tipo;
    private double preco;
    private int stockAtual;
    private int stockMinimo;
    private boolean ativo;
    private String descricao;
    
    /**
     * Construtor padrão
     */
    public ProdutoBar() {
        this.ativo = true;
        this.tipo = "Outro";
        this.stockAtual = 0;
        this.stockMinimo = 5; // stock mínimo padrão
    }
    
    /**
     * Construtor com parâmetros básicos
     */
    public ProdutoBar(int idProduto, String nome, String tipo, double preco, int stockAtual, int stockMinimo) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.tipo = tipo;
        this.preco = preco;
        this.stockAtual = stockAtual;
        this.stockMinimo = stockMinimo;
        this.ativo = true;
    }
    
    /**
     * Construtor completo
     */
    public ProdutoBar(int idProduto, String nome, String tipo, double preco, 
                      int stockAtual, int stockMinimo, boolean ativo, String descricao) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.tipo = tipo;
        this.preco = preco;
        this.stockAtual = stockAtual;
        this.stockMinimo = stockMinimo;
        this.ativo = ativo;
        this.descricao = descricao;
    }
    
    // Getters e Setters
    
    public int getIdProduto() {
        return idProduto;
    }
    
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public void setPreco(double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo");
        }
        this.preco = preco;
    }
    
    public int getStockAtual() {
        return stockAtual;
    }
    
    public void setStockAtual(int stockAtual) {
        if (stockAtual < 0) {
            throw new IllegalArgumentException("O stock não pode ser negativo");
        }
        this.stockAtual = stockAtual;
    }
    
    public int getStockMinimo() {
        return stockMinimo;
    }
    
    public void setStockMinimo(int stockMinimo) {
        if (stockMinimo < 0) {
            throw new IllegalArgumentException("O stock mínimo não pode ser negativo");
        }
        this.stockMinimo = stockMinimo;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    // Métodos de gestão de stock
    
    /**
     * Adiciona stock ao produto
     */
    public void adicionarStock(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa");
        }
        this.stockAtual += quantidade;
    }
    
    /**
     * Remove stock do produto (venda)
     */
    public boolean removerStock(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa");
        }
        
        if (stockAtual >= quantidade) {
            this.stockAtual -= quantidade;
            return true;
        }
        
        return false; // stock insuficiente
    }
    
    /**
     * Verifica se há stock suficiente
     */
    public boolean temStockSuficiente(int quantidade) {
        return stockAtual >= quantidade;
    }
    
    /**
     * Verifica se o produto está em stock
     */
    public boolean temStock() {
        return stockAtual > 0;
    }
    
    /**
     * Verifica se o stock está baixo (abaixo do mínimo)
     */
    public boolean isStockBaixo() {
        return stockAtual <= stockMinimo;
    }
    
    /**
     * Verifica se o produto está esgotado
     */
    public boolean isEsgotado() {
        return stockAtual == 0;
    }
    
    /**
     * Calcula quantas unidades faltam para atingir o stock mínimo
     */
    public int getUnidadesParaStockMinimo() {
        return Math.max(0, stockMinimo - stockAtual);
    }
    
    /**
     * Calcula quantas unidades faltam para atingir um stock desejado
     */
    public int getUnidadesParaStock(int stockDesejado) {
        return Math.max(0, stockDesejado - stockAtual);
    }
    
    /**
     * Obtém a percentagem de stock atual em relação ao mínimo
     */
    public double getPercentagemStock() {
        if (stockMinimo == 0) {
            return stockAtual > 0 ? 100.0 : 0.0;
        }
        return (double) stockAtual / stockMinimo * 100;
    }
    
    // Métodos de preço
    
    /**
     * Obtém o preço formatado
     */
    public String getPrecoFormatado() {
        return String.format("%.2f €", preco);
    }
    
    /**
     * Calcula o valor total do stock atual
     */
    public double getValorTotalStock() {
        return stockAtual * preco;
    }
    
    /**
     * Obtém o valor total do stock formatado
     */
    public String getValorTotalStockFormatado() {
        return String.format("%.2f €", getValorTotalStock());
    }
    
    // Métodos de validação
    
    /**
     * Verifica se o produto é válido
     */
    public boolean isProdutoValido() {
        return nome != null && !nome.trim().isEmpty() && 
               tipo != null && !tipo.trim().isEmpty() &&
               preco >= 0 && stockAtual >= 0 && stockMinimo >= 0;
    }
    
    /**
     * Verifica se o produto pode ser vendido
     */
    public boolean podeSerVendido() {
        return ativo && temStock() && isProdutoValido();
    }
    
    // Métodos de ativação/desativação
    
    /**
     * Ativa o produto
     */
    public void ativar() {
        this.ativo = true;
    }
    
    /**
     * Desativa o produto
     */
    public void desativar() {
        this.ativo = false;
    }
    
    // Métodos de informação
    
    /**
     * Obtém o status do stock formatado
     */
    public String getStatusStock() {
        if (isEsgotado()) {
            return "ESGOTADO";
        } else if (isStockBaixo()) {
            return "STOCK BAIXO";
        } else {
            return "EM STOCK";
        }
    }
    
    /**
     * Obtém informações completas do produto
     */
    public String getInfoCompleta() {
        StringBuilder sb = new StringBuilder();
        sb.append("Produto #").append(idProduto).append("\n");
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Tipo: ").append(tipo).append("\n");
        sb.append("Preço: ").append(getPrecoFormatado()).append("\n");
        sb.append("Stock atual: ").append(stockAtual).append(" unidades\n");
        sb.append("Stock mínimo: ").append(stockMinimo).append(" unidades\n");
        sb.append("Status: ").append(getStatusStock()).append("\n");
        sb.append("Valor total em stock: ").append(getValorTotalStockFormatado()).append("\n");
        sb.append("Ativo: ").append(ativo ? "Sim" : "Não");
        
        if (descricao != null && !descricao.trim().isEmpty()) {
            sb.append("\nDescrição: ").append(descricao);
        }
        
        return sb.toString();
    }
    
    /**
     * Obtém informações resumidas do produto
     */
    public String getInfoResumida() {
        return String.format("#%d | %s | %s | %d/%d | %.2f€ | %s", 
                           idProduto, 
                           nome, 
                           tipo, 
                           stockAtual, 
                           stockMinimo,
                           preco,
                           getStatusStock());
    }
    
    /**
     * Obtém alerta de stock baixo
     */
    public String getAlertaStockBaixo() {
        if (!isStockBaixo()) {
            return null; // não há alerta
        }
        
        if (isEsgotado()) {
            return String.format("ALERTA: Produto '%s' está ESGOTADO! Repor stock urgentemente.", nome);
        } else {
            return String.format("ALERTA: Produto '%s' com stock baixo (%d/%d). Repor %d unidades.", 
                               nome, stockAtual, stockMinimo, getUnidadesParaStockMinimo());
        }
    }
    
    @Override
    public String toString() {
        return "ProdutoBar{" +
                "idProduto=" + idProduto +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", preco=" + getPrecoFormatado() +
                ", stock=" + stockAtual + "/" + stockMinimo +
                ", ativo=" + ativo +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        ProdutoBar produto = (ProdutoBar) obj;
        return idProduto == produto.idProduto;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(idProduto);
    }
} 