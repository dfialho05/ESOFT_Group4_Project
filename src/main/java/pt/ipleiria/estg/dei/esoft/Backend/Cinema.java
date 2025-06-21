package pt.ipleiria.estg.dei.esoft.Backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe principal que centraliza todos os dados e a lógica de negócio do cinema.
 * Funciona como o "coração" do backend.
 */
public class Cinema {

    private final List<Filme> filmes;
    private final List<Sala> salas;
    private final List<Sessao> sessoes;
    private final List<ProdutoBar> produtosBar;
    private final List<Menu> menus;
    private final List<Venda> vendas;

    private final GestaoMenus gestaoMenus;

    public Cinema() {
        this.filmes = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.sessoes = new ArrayList<>();
        this.produtosBar = new ArrayList<>();
        this.menus = new ArrayList<>();
        this.vendas = new ArrayList<>();

        this.gestaoMenus = new GestaoMenus(this.menus);
        
        // Pode adicionar dados de exemplo aqui para testes
        // inicializarDadosExemplo(); // Removido para ser chamado externamente
    }

    // Getters para as listas de dados
    public List<Filme> getFilmes() {
        return new ArrayList<>(filmes);
    }

    public List<Sala> getSalas() {
        return new ArrayList<>(salas);
    }

    public List<Sessao> getSessoes() {
        return new ArrayList<>(sessoes);
    }

    public List<ProdutoBar> getProdutosBar() {
        return new ArrayList<>(produtosBar);
    }

    public List<Menu> getMenus() {
        return new ArrayList<>(menus);
    }

    public List<Venda> getVendas() {
        return new ArrayList<>(vendas);
    }

    public GestaoMenus getGestaoMenus() {
        return gestaoMenus;
    }

    /**
     * Fornece uma instância da classe Relatorios, já configurada com os dados atuais do cinema.
     * A UI deve usar este método para obter os dados para os relatórios.
     * @return Uma nova instância de Relatorios.
     */
    public Relatorios getRelatorios() {
        return new Relatorios(salas, sessoes, vendas, produtosBar);
    }
    
    // Métodos para adicionar dados ao sistema (simulando o funcionamento)
    public void adicionarFilme(Filme filme) {
        this.filmes.add(filme);
    }
    
    public void adicionarSala(Sala sala) {
        this.salas.add(sala);
    }
    
    public void adicionarSessao(Sessao sessao) {
        this.sessoes.add(sessao);
    }
    
    public void adicionarProdutoBar(ProdutoBar produto) {
        this.produtosBar.add(produto);
    }
    
    public void adicionarMenu(Menu menu) {
        this.menus.add(menu);
    }
    
    public void registarVenda(Venda venda) {
        this.vendas.add(venda);
    }
} 