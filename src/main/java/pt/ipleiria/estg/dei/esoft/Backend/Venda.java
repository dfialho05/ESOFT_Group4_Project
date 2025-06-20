package pt.ipleiria.estg.dei.esoft.Backend;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Venda {

    private int id;
    private LocalDateTime dataVenda;
    private List<Menu> menusVendidos;
    private List<ProdutoBar> produtosVendidos;
    private List<Bilhete> bilhetesVendidos;
    private double totalVenda;

    public Venda(int id, LocalDateTime dataVenda) {
        this.id = id;
        this.dataVenda = dataVenda;
        this.menusVendidos = new ArrayList<>();
        this.produtosVendidos = new ArrayList<>();
        this.bilhetesVendidos = new ArrayList<>();
        this.totalVenda = 0.0;
    }

    // Getters
    public int getId() {
        return id;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public List<Menu> getMenusVendidos() {
        return new ArrayList<>(menusVendidos);
    }

    public List<ProdutoBar> getProdutosVendidos() {
        return new ArrayList<>(produtosVendidos);
    }

    public List<Bilhete> getBilhetesVendidos() {
        return new ArrayList<>(bilhetesVendidos);
    }

    public double getTotalVenda() {
        return totalVenda;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    // Métodos para adicionar itens à venda
    public void adicionarMenu(Menu menu) {
        if (menu != null) {
            this.menusVendidos.add(menu);
            this.totalVenda += menu.getPrecoTotal();
        }
    }

    public void adicionarProduto(ProdutoBar produto) {
        if (produto != null) {
            this.produtosVendidos.add(produto);
            this.totalVenda += produto.getPreco();
        }
    }

    public void adicionarBilhete(Bilhete bilhete) {
        if (bilhete != null) {
            this.bilhetesVendidos.add(bilhete);
            this.totalVenda += bilhete.getPrecoTotal();
        }
    }
} 