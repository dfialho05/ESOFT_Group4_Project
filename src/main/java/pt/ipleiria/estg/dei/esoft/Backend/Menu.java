package pt.ipleiria.estg.dei.esoft.Backend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um menu que pode conter produtos do bar e bilhetes
 */
public class Menu {
    
    private int idMenu;
    private String nome;
    private String descricao;
    private double precoTotal;
    private boolean ativo;
    private LocalDateTime dataCriacao;
    private List<ItemMenu> itens;
    
    /**
     * Classe interna para representar um item do menu (produto ou bilhete)
     */
    public static class ItemMenu {
        private int idItem;
        private String tipo; // "PRODUTO" ou "BILHETE"
        private ProdutoBar produto;
        private BilheteInfo bilheteInfo; // Informações do bilhete em vez do bilhete específico
        private int quantidade;
        private double precoUnitario;
        private double precoTotal;
        
        public ItemMenu(int idItem, ProdutoBar produto, int quantidade) {
            this.idItem = idItem;
            this.tipo = "PRODUTO";
            this.produto = produto;
            this.quantidade = quantidade;
            this.precoUnitario = produto.getPreco();
            this.precoTotal = produto.getPreco() * quantidade;
        }
        
        public ItemMenu(int idItem, BilheteInfo bilheteInfo, int quantidade) {
            this.idItem = idItem;
            this.tipo = "BILHETE";
            this.bilheteInfo = bilheteInfo;
            this.quantidade = quantidade;
            this.precoUnitario = bilheteInfo.getPreco();
            this.precoTotal = bilheteInfo.getPreco() * quantidade;
        }
        
        // Getters e Setters
        public int getIdItem() {
            return idItem;
        }
        
        public void setIdItem(int idItem) {
            this.idItem = idItem;
        }
        
        public String getTipo() {
            return tipo;
        }
        
        public void setTipo(String tipo) {
            this.tipo = tipo;
        }
        
        public ProdutoBar getProduto() {
            return produto;
        }
        
        public void setProduto(ProdutoBar produto) {
            this.produto = produto;
            if (produto != null) {
                this.tipo = "PRODUTO";
                this.precoUnitario = produto.getPreco();
                calcularPrecoTotal();
            }
        }
        
        public BilheteInfo getBilheteInfo() {
            return bilheteInfo;
        }
        
        public void setBilheteInfo(BilheteInfo bilheteInfo) {
            this.bilheteInfo = bilheteInfo;
            if (bilheteInfo != null) {
                this.tipo = "BILHETE";
                this.precoUnitario = bilheteInfo.getPreco();
                calcularPrecoTotal();
            }
        }
        
        public int getQuantidade() {
            return quantidade;
        }
        
        public void setQuantidade(int quantidade) {
            if (quantidade < 0) {
                throw new IllegalArgumentException("A quantidade não pode ser negativa");
            }
            this.quantidade = quantidade;
            calcularPrecoTotal();
        }
        
        public double getPrecoUnitario() {
            return precoUnitario;
        }
        
        public void setPrecoUnitario(double precoUnitario) {
            if (precoUnitario < 0) {
                throw new IllegalArgumentException("O preço unitário não pode ser negativo");
            }
            this.precoUnitario = precoUnitario;
            calcularPrecoTotal();
        }
        
        public double getPrecoTotal() {
            return precoTotal;
        }
        
        public void setPrecoTotal(double precoTotal) {
            this.precoTotal = precoTotal;
        }
        
        private void calcularPrecoTotal() {
            this.precoTotal = this.precoUnitario * this.quantidade;
        }
        
        public String getNome() {
            if ("PRODUTO".equals(tipo) && produto != null) {
                return produto.getNome();
            } else if ("BILHETE".equals(tipo) && bilheteInfo != null) {
                return "Bilhete - " + bilheteInfo.getNomeFilme();
            }
            return "Item desconhecido";
        }
        
        public String getDescricao() {
            if ("PRODUTO".equals(tipo) && produto != null) {
                return produto.getDescricao() != null ? produto.getDescricao() : produto.getNome();
            } else if ("BILHETE".equals(tipo) && bilheteInfo != null) {
                return bilheteInfo.getDescricao();
            }
            return "";
        }
        
        @Override
        public String toString() {
            return String.format("%s - Qtd: %d - Preço: %.2f€", getNome(), quantidade, precoTotal);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            
            ItemMenu item = (ItemMenu) obj;
            return idItem == item.idItem;
        }
        
        @Override
        public int hashCode() {
            return idItem;
        }
    }
    
    /**
     * Classe para armazenar informações do bilhete (sem ser um bilhete específico)
     */
    public static class BilheteInfo {
        private String nomeFilme;
        private String nomeSala;
        private String horario;
        private double preco;
        private String tipoBilhete; // "Normal", "VIP", etc.
        
        public BilheteInfo(String nomeFilme, String nomeSala, String horario, double preco, String tipoBilhete) {
            this.nomeFilme = nomeFilme;
            this.nomeSala = nomeSala;
            this.horario = horario;
            this.preco = preco;
            this.tipoBilhete = tipoBilhete;
        }
        
        // Getters e Setters
        public String getNomeFilme() {
            return nomeFilme;
        }
        
        public void setNomeFilme(String nomeFilme) {
            this.nomeFilme = nomeFilme;
        }
        
        public String getNomeSala() {
            return nomeSala;
        }
        
        public void setNomeSala(String nomeSala) {
            this.nomeSala = nomeSala;
        }
        
        public String getHorario() {
            return horario;
        }
        
        public void setHorario(String horario) {
            this.horario = horario;
        }
        
        public double getPreco() {
            return preco;
        }
        
        public void setPreco(double preco) {
            this.preco = preco;
        }
        
        public String getTipoBilhete() {
            return tipoBilhete;
        }
        
        public void setTipoBilhete(String tipoBilhete) {
            this.tipoBilhete = tipoBilhete;
        }
        
        public String getDescricao() {
            return String.format("Filme: %s | Sala: %s | Horário: %s | Tipo: %s", 
                               nomeFilme, nomeSala, horario, tipoBilhete);
        }
        
        @Override
        public String toString() {
            return String.format("Bilhete %s - %s (%s) - %.2f€", tipoBilhete, nomeFilme, horario, preco);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            
            BilheteInfo bilheteInfo = (BilheteInfo) obj;
            return nomeFilme.equals(bilheteInfo.nomeFilme) && 
                   nomeSala.equals(bilheteInfo.nomeSala) && 
                   horario.equals(bilheteInfo.horario) && 
                   tipoBilhete.equals(bilheteInfo.tipoBilhete);
        }
        
        @Override
        public int hashCode() {
            return nomeFilme.hashCode() + nomeSala.hashCode() + horario.hashCode() + tipoBilhete.hashCode();
        }
    }
    
    /**
     * Construtor padrão
     */
    public Menu() {
        this.itens = new ArrayList<>();
        this.dataCriacao = LocalDateTime.now();
        this.ativo = true;
    }
    
    /**
     * Construtor com parâmetros básicos
     */
    public Menu(int idMenu, String nome, String descricao) {
        this.idMenu = idMenu;
        this.nome = nome;
        this.descricao = descricao;
        this.itens = new ArrayList<>();
        this.dataCriacao = LocalDateTime.now();
        this.ativo = true;
        this.precoTotal = 0.0;
    }
    
    /**
     * Construtor completo
     */
    public Menu(int idMenu, String nome, String descricao, double precoTotal, 
                boolean ativo, LocalDateTime dataCriacao, List<ItemMenu> itens) {
        this.idMenu = idMenu;
        this.nome = nome;
        this.descricao = descricao;
        this.precoTotal = precoTotal;
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
        this.itens = itens != null ? new ArrayList<>(itens) : new ArrayList<>();
    }
    
    // Getters e Setters
    
    public int getIdMenu() {
        return idMenu;
    }
    
    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public double getPrecoTotal() {
        return precoTotal;
    }
    
    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public List<ItemMenu> getItens() {
        return new ArrayList<>(itens); // retorna cópia para evitar modificações externas
    }
    
    public void setItens(List<ItemMenu> itens) {
        this.itens = itens != null ? new ArrayList<>(itens) : new ArrayList<>();
        calcularPrecoTotal();
    }
    
    // Métodos de gestão de itens
    
    /**
     * Adiciona um produto ao menu
     */
    public boolean adicionarProduto(ProdutoBar produto, int quantidade) {
        if (produto == null || !produto.isAtivo()) {
            return false;
        }
        
        if (quantidade <= 0) {
            return false;
        }
        
        // Verifica se o produto já existe no menu
        for (ItemMenu item : itens) {
            if ("PRODUTO".equals(item.getTipo()) && item.getProduto().getIdProduto() == produto.getIdProduto()) {
                // Atualiza a quantidade
                item.setQuantidade(item.getQuantidade() + quantidade);
                calcularPrecoTotal();
                return true;
            }
        }
        
        // Adiciona novo item
        int novoId = itens.size() + 1;
        ItemMenu novoItem = new ItemMenu(novoId, produto, quantidade);
        itens.add(novoItem);
        calcularPrecoTotal();
        
        return true;
    }
    
    /**
     * Adiciona um bilhete ao menu usando informações do bilhete
     */
    public boolean adicionarBilhete(BilheteInfo bilheteInfo, int quantidade) {
        if (bilheteInfo == null) {
            return false;
        }
        
        if (quantidade <= 0) {
            return false;
        }
        
        // Verifica se o bilhete já existe no menu
        for (ItemMenu item : itens) {
            if ("BILHETE".equals(item.getTipo()) && item.getBilheteInfo().equals(bilheteInfo)) {
                // Atualiza a quantidade
                item.setQuantidade(item.getQuantidade() + quantidade);
                calcularPrecoTotal();
                return true;
            }
        }
        
        // Adiciona novo item
        int novoId = itens.size() + 1;
        ItemMenu novoItem = new ItemMenu(novoId, bilheteInfo, quantidade);
        itens.add(novoItem);
        calcularPrecoTotal();
        
        return true;
    }
    
    /**
     * Adiciona um bilhete ao menu usando um bilhete existente como referência
     */
    public boolean adicionarBilhete(Bilhete bilhete, int quantidade) {
        if (bilhete == null || !bilhete.isAtivo()) {
            return false;
        }
        
        if (quantidade <= 0) {
            return false;
        }
        
        // Cria BilheteInfo a partir do bilhete
        BilheteInfo bilheteInfo = new BilheteInfo(
            bilhete.getInfoFilme(),
            bilhete.getInfoSala(),
            bilhete.getInfoSessao(),
            bilhete.getPrecoTotal(),
            "Normal" // Tipo padrão, pode ser personalizado
        );
        
        return adicionarBilhete(bilheteInfo, quantidade);
    }
    
    /**
     * Remove um item do menu
     */
    public boolean removerItem(int idItem) {
        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getIdItem() == idItem) {
                itens.remove(i);
                calcularPrecoTotal();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Atualiza a quantidade de um item
     */
    public boolean atualizarQuantidade(int idItem, int novaQuantidade) {
        for (ItemMenu item : itens) {
            if (item.getIdItem() == idItem) {
                item.setQuantidade(novaQuantidade);
                calcularPrecoTotal();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Calcula o preço total do menu
     */
    private void calcularPrecoTotal() {
        this.precoTotal = 0.0;
        for (ItemMenu item : itens) {
            this.precoTotal += item.getPrecoTotal();
        }
    }
    
    /**
     * Obtém o número de itens no menu
     */
    public int getNumeroItens() {
        return itens.size();
    }
    
    /**
     * Verifica se o menu tem itens
     */
    public boolean temItens() {
        return !itens.isEmpty();
    }
    
    /**
     * Obtém apenas os produtos do menu
     */
    public List<ItemMenu> getProdutos() {
        List<ItemMenu> produtos = new ArrayList<>();
        for (ItemMenu item : itens) {
            if ("PRODUTO".equals(item.getTipo())) {
                produtos.add(item);
            }
        }
        return produtos;
    }
    
    /**
     * Obtém apenas os bilhetes do menu
     */
    public List<ItemMenu> getBilhetes() {
        List<ItemMenu> bilhetes = new ArrayList<>();
        for (ItemMenu item : itens) {
            if ("BILHETE".equals(item.getTipo())) {
                bilhetes.add(item);
            }
        }
        return bilhetes;
    }
    
    /**
     * Verifica se o menu é válido
     */
    public boolean isMenuValido() {
        return nome != null && !nome.trim().isEmpty() && temItens();
    }
    
    /**
     * Ativa o menu
     */
    public void ativar() {
        this.ativo = true;
    }
    
    /**
     * Desativa o menu
     */
    public void desativar() {
        this.ativo = false;
    }
    
    /**
     * Obtém o preço total formatado
     */
    public String getPrecoTotalFormatado() {
        return String.format("%.2f€", precoTotal);
    }
    
    /**
     * Obtém informações resumidas do menu
     */
    public String getInfoResumida() {
        return String.format("Menu: %s - %d itens - %s", nome, getNumeroItens(), getPrecoTotalFormatado());
    }
    
    /**
     * Obtém informações completas do menu
     */
    public String getInfoCompleta() {
        StringBuilder info = new StringBuilder();
        info.append("=== MENU ===\n");
        info.append("ID: ").append(idMenu).append("\n");
        info.append("Nome: ").append(nome).append("\n");
        info.append("Descrição: ").append(descricao != null ? descricao : "Sem descrição").append("\n");
        info.append("Preço Total: ").append(getPrecoTotalFormatado()).append("\n");
        info.append("Status: ").append(ativo ? "Ativo" : "Inativo").append("\n");
        info.append("Data Criação: ").append(dataCriacao).append("\n");
        info.append("Número de Itens: ").append(getNumeroItens()).append("\n\n");
        
        if (temItens()) {
            info.append("=== ITENS ===\n");
            for (ItemMenu item : itens) {
                info.append(item.toString()).append("\n");
            }
        } else {
            info.append("Menu sem itens\n");
        }
        
        return info.toString();
    }
    
    /**
     * Obtém uma lista formatada dos itens
     */
    public String getItensFormatados() {
        if (!temItens()) {
            return "Menu sem itens";
        }
        
        StringBuilder lista = new StringBuilder();
        for (int i = 0; i < itens.size(); i++) {
            ItemMenu item = itens.get(i);
            lista.append(i + 1).append(". ").append(item.toString()).append("\n");
        }
        return lista.toString();
    }
    
    @Override
    public String toString() {
        return getInfoResumida();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Menu menu = (Menu) obj;
        return idMenu == menu.idMenu;
    }
    
    @Override
    public int hashCode() {
        return idMenu;
    }
} 