package pt.ipleiria.estg.dei.esoft.Backend;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe para gestão de menus do sistema
 */
public class GestaoMenus {
    
    private List<Menu> menus;
    private int proximoId;
    
    /**
     * Construtor padrão
     */
    public GestaoMenus() {
        this.menus = new ArrayList<>();
        this.proximoId = 1;
    }
    
    /**
     * Construtor com lista de menus existente
     */
    public GestaoMenus(List<Menu> menus) {
        this.menus = menus != null ? new ArrayList<>(menus) : new ArrayList<>();
        this.proximoId = calcularProximoId();
    }
    
    /**
     * Calcula o próximo ID disponível
     */
    private int calcularProximoId() {
        if (menus.isEmpty()) {
            return 1;
        }
        
        int maxId = menus.stream()
                .mapToInt(Menu::getIdMenu)
                .max()
                .orElse(0);
        
        return maxId + 1;
    }
    
    // Métodos de gestão de menus
    
    /**
     * Cria um novo menu
     */
    public Menu criarMenu(String nome, String descricao) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do menu não pode estar vazio");
        }
        
        // Verifica se já existe um menu com o mesmo nome
        if (existeMenuComNome(nome)) {
            throw new IllegalArgumentException("Já existe um menu com o nome: " + nome);
        }
        
        Menu novoMenu = new Menu(proximoId++, nome.trim(), descricao);
        menus.add(novoMenu);
        
        return novoMenu;
    }
    
    /**
     * Verifica se existe um menu com o nome especificado
     */
    public boolean existeMenuComNome(String nome) {
        return menus.stream()
                .anyMatch(menu -> menu.getNome().equalsIgnoreCase(nome.trim()));
    }
    
    /**
     * Obtém um menu pelo ID
     */
    public Menu obterMenu(int idMenu) {
        return menus.stream()
                .filter(menu -> menu.getIdMenu() == idMenu)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Obtém um menu pelo nome
     */
    public Menu obterMenuPorNome(String nome) {
        return menus.stream()
                .filter(menu -> menu.getNome().equalsIgnoreCase(nome.trim()))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Obtém todos os menus
     */
    public List<Menu> obterTodosMenus() {
        return new ArrayList<>(menus);
    }
    
    /**
     * Obtém apenas os menus ativos
     */
    public List<Menu> obterMenusAtivos() {
        return menus.stream()
                .filter(Menu::isAtivo)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém apenas os menus inativos
     */
    public List<Menu> obterMenusInativos() {
        return menus.stream()
                .filter(menu -> !menu.isAtivo())
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém menus que contêm produtos
     */
    public List<Menu> obterMenusComProdutos() {
        return menus.stream()
                .filter(menu -> !menu.getProdutos().isEmpty())
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém menus que contêm bilhetes
     */
    public List<Menu> obterMenusComBilhetes() {
        return menus.stream()
                .filter(menu -> !menu.getBilhetes().isEmpty())
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém menus que contêm tanto produtos como bilhetes
     */
    public List<Menu> obterMenusCompletos() {
        return menus.stream()
                .filter(menu -> !menu.getProdutos().isEmpty() && !menu.getBilhetes().isEmpty())
                .collect(Collectors.toList());
    }
    
    /**
     * Atualiza um menu existente
     */
    public boolean atualizarMenu(int idMenu, String novoNome, String novaDescricao) {
        Menu menu = obterMenu(idMenu);
        if (menu == null) {
            return false;
        }
        
        if (novoNome != null && !novoNome.trim().isEmpty()) {
            // Verifica se o novo nome já existe em outro menu
            Menu menuComMesmoNome = obterMenuPorNome(novoNome);
            if (menuComMesmoNome != null && menuComMesmoNome.getIdMenu() != idMenu) {
                throw new IllegalArgumentException("Já existe um menu com o nome: " + novoNome);
            }
            menu.setNome(novoNome.trim());
        }
        
        if (novaDescricao != null) {
            menu.setDescricao(novaDescricao);
        }
        
        return true;
    }
    
    /**
     * Remove um menu
     */
    public boolean removerMenu(int idMenu) {
        Menu menu = obterMenu(idMenu);
        if (menu == null) {
            return false;
        }
        
        return menus.remove(menu);
    }
    
    /**
     * Ativa um menu
     */
    public boolean ativarMenu(int idMenu) {
        Menu menu = obterMenu(idMenu);
        if (menu == null) {
            return false;
        }
        
        menu.ativar();
        return true;
    }
    
    /**
     * Desativa um menu
     */
    public boolean desativarMenu(int idMenu) {
        Menu menu = obterMenu(idMenu);
        if (menu == null) {
            return false;
        }
        
        menu.desativar();
        return true;
    }
    
    /**
     * Adiciona um produto a um menu
     */
    public boolean adicionarProdutoAoMenu(int idMenu, ProdutoBar produto, int quantidade) {
        Menu menu = obterMenu(idMenu);
        if (menu == null) {
            return false;
        }
        
        return menu.adicionarProduto(produto, quantidade);
    }
    
    /**
     * Adiciona um bilhete a um menu
     */
    public boolean adicionarBilheteAoMenu(int idMenu, Bilhete bilhete, int quantidade) {
        Menu menu = obterMenu(idMenu);
        if (menu == null) {
            return false;
        }
        
        return menu.adicionarBilhete(bilhete, quantidade);
    }
    
    /**
     * Adiciona um bilhete a um menu usando informações do bilhete
     */
    public boolean adicionarBilheteInfoAoMenu(int idMenu, Menu.BilheteInfo bilheteInfo, int quantidade) {
        Menu menu = obterMenu(idMenu);
        if (menu == null) {
            return false;
        }
        
        return menu.adicionarBilhete(bilheteInfo, quantidade);
    }
    
    /**
     * Cria um BilheteInfo a partir de informações básicas
     */
    public Menu.BilheteInfo criarBilheteInfo(String nomeFilme, String nomeSala, String horario, double preco, String tipoBilhete) {
        return new Menu.BilheteInfo(nomeFilme, nomeSala, horario, preco, tipoBilhete);
    }
    
    /**
     * Remove um item de um menu
     */
    public boolean removerItemDoMenu(int idMenu, int idItem) {
        Menu menu = obterMenu(idMenu);
        if (menu == null) {
            return false;
        }
        
        return menu.removerItem(idItem);
    }
    
    /**
     * Atualiza a quantidade de um item em um menu
     */
    public boolean atualizarQuantidadeItem(int idMenu, int idItem, int novaQuantidade) {
        Menu menu = obterMenu(idMenu);
        if (menu == null) {
            return false;
        }
        
        return menu.atualizarQuantidade(idItem, novaQuantidade);
    }
    
    /**
     * Obtém o número total de menus
     */
    public int getNumeroMenus() {
        return menus.size();
    }
    
    /**
     * Obtém o número de menus ativos
     */
    public int getNumeroMenusAtivos() {
        return (int) menus.stream().filter(Menu::isAtivo).count();
    }
    
    /**
     * Obtém o número de menus inativos
     */
    public int getNumeroMenusInativos() {
        return (int) menus.stream().filter(menu -> !menu.isAtivo()).count();
    }
    
    /**
     * Verifica se existem menus
     */
    public boolean temMenus() {
        return !menus.isEmpty();
    }
    
    /**
     * Obtém estatísticas dos menus
     */
    public String getEstatisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== ESTATÍSTICAS DOS MENUS ===\n");
        stats.append("Total de Menus: ").append(getNumeroMenus()).append("\n");
        stats.append("Menus Ativos: ").append(getNumeroMenusAtivos()).append("\n");
        stats.append("Menus Inativos: ").append(getNumeroMenusInativos()).append("\n");
        stats.append("Menus com Produtos: ").append(obterMenusComProdutos().size()).append("\n");
        stats.append("Menus com Bilhetes: ").append(obterMenusComBilhetes().size()).append("\n");
        stats.append("Menus Completos: ").append(obterMenusCompletos().size()).append("\n");
        
        if (temMenus()) {
            double precoMedio = menus.stream()
                    .mapToDouble(Menu::getPrecoTotal)
                    .average()
                    .orElse(0.0);
            
            double precoTotal = menus.stream()
                    .mapToDouble(Menu::getPrecoTotal)
                    .sum();
            
            stats.append("Preço Médio dos Menus: ").append(String.format("%.2f€", precoMedio)).append("\n");
            stats.append("Valor Total dos Menus: ").append(String.format("%.2f€", precoTotal)).append("\n");
        }
        
        return stats.toString();
    }
    
    /**
     * Obtém uma lista formatada de todos os menus
     */
    public String getListaMenus() {
        if (!temMenus()) {
            return "Não existem menus criados.";
        }
        
        StringBuilder lista = new StringBuilder();
        lista.append("=== LISTA DE MENUS ===\n");
        
        for (int i = 0; i < menus.size(); i++) {
            Menu menu = menus.get(i);
            lista.append(i + 1).append(". ").append(menu.getInfoResumida()).append("\n");
        }
        
        return lista.toString();
    }
    
    /**
     * Obtém informações detalhadas de um menu
     */
    public String getInfoMenu(int idMenu) {
        Menu menu = obterMenu(idMenu);
        if (menu == null) {
            return "Menu não encontrado.";
        }
        
        return menu.getInfoCompleta();
    }
    
    /**
     * Obtém menus ordenados por preço (crescente)
     */
    public List<Menu> obterMenusPorPrecoCrescente() {
        return menus.stream()
                .sorted((m1, m2) -> Double.compare(m1.getPrecoTotal(), m2.getPrecoTotal()))
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém menus ordenados por preço (decrescente)
     */
    public List<Menu> obterMenusPorPrecoDecrescente() {
        return menus.stream()
                .sorted((m1, m2) -> Double.compare(m2.getPrecoTotal(), m1.getPrecoTotal()))
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém menus ordenados por nome
     */
    public List<Menu> obterMenusPorNome() {
        return menus.stream()
                .sorted((m1, m2) -> m1.getNome().compareToIgnoreCase(m2.getNome()))
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém menus ordenados por data de criação (mais recentes primeiro)
     */
    public List<Menu> obterMenusPorDataCriacao() {
        return menus.stream()
                .sorted((m1, m2) -> m2.getDataCriacao().compareTo(m1.getDataCriacao()))
                .collect(Collectors.toList());
    }
    
    /**
     * Pesquisa menus por nome (busca parcial)
     */
    public List<Menu> pesquisarMenusPorNome(String termo) {
        String termoLower = termo.toLowerCase();
        return menus.stream()
                .filter(menu -> menu.getNome().toLowerCase().contains(termoLower))
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém menus com preço dentro de um intervalo
     */
    public List<Menu> obterMenusPorIntervaloPreco(double precoMin, double precoMax) {
        return menus.stream()
                .filter(menu -> menu.getPrecoTotal() >= precoMin && menu.getPrecoTotal() <= precoMax)
                .collect(Collectors.toList());
    }
    
    /**
     * Limpa todos os menus
     */
    public void limparMenus() {
        menus.clear();
        proximoId = 1;
    }
    
    @Override
    public String toString() {
        return getListaMenus();
    }
} 