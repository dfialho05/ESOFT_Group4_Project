import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import pt.ipleiria.estg.dei.esoft.Backend.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class VendaTestCase {
    
    private Venda venda;
    private Filme filme;
    private Sala sala;
    private Sessao sessao;
    private Bilhete bilhete;
    private ProdutoBar produto;
    private Menu menu;
    
    @BeforeEach
    void setUp() {
        // Criar dados de teste
        filme = new Filme("Teste Filme", "Ação", 120, "Descrição do filme", 8.50, 2023, "M/12", true, 1, 30);
        sala = new Sala(1, "Sala Teste", "Normal", 100, "10x10", true, "");
        sessao = new Sessao(1, filme, sala, LocalDate.now(), LocalDate.now().plusDays(7), LocalTime.of(20, 0));
        bilhete = new Bilhete(1, sessao);
        produto = new ProdutoBar(1, "Pipocas", "Snack", 4.50, 100, 10);
        menu = new Menu(1, "Menu Teste", "Menu de teste");
        
        // Configurar bilhete com lugar
        bilhete.adicionarLugar(1, 1, LocalDate.now().plusDays(1));
        
        // Configurar menu com produto
        menu.adicionarProduto(produto, 2);
        
        venda = new Venda(1, LocalDateTime.now());
    }
    
    /**
     * Teste 1: Verificar criação de venda e propriedades básicas
     */
    @Test
    void testCriacaoVenda() {
        // Arrange & Act (já feito no setUp)
        
        // Assert
        assertNotNull(venda);
        assertEquals(1, venda.getId());
        assertNotNull(venda.getDataVenda());
        assertTrue(venda.getDataVenda().isBefore(LocalDateTime.now().plusSeconds(1)));
        assertEquals(0.0, venda.getTotalVenda());
        assertTrue(venda.getBilhetesVendidos().isEmpty());
        assertTrue(venda.getProdutosVendidos().isEmpty());
        assertTrue(venda.getMenusVendidos().isEmpty());
    }
    
    /**
     * Teste 2: Verificar adição de bilhetes à venda
     */
    @Test
    void testAdicionarBilhetes() {
        // Arrange
        Bilhete bilhete2 = new Bilhete(2, sessao);
        bilhete2.adicionarLugar(1, 2, LocalDate.now().plusDays(1));
        
        // Act
        venda.adicionarBilhete(bilhete);
        venda.adicionarBilhete(bilhete2);
        
        // Assert
        assertEquals(2, venda.getBilhetesVendidos().size());
        assertTrue(venda.getBilhetesVendidos().contains(bilhete));
        assertTrue(venda.getBilhetesVendidos().contains(bilhete2));
        assertEquals(bilhete.getPrecoTotal() + bilhete2.getPrecoTotal(), venda.getTotalVenda());
        
        // Act - Tentar adicionar bilhete nulo
        venda.adicionarBilhete(null);
        
        // Assert - Não deve adicionar
        assertEquals(2, venda.getBilhetesVendidos().size());
    }
    
    /**
     * Teste 3: Verificar adição de produtos à venda
     */
    @Test
    void testAdicionarProdutos() {
        // Arrange
        ProdutoBar produto2 = new ProdutoBar(2, "Refrigerante", "Bebida", 2.50, 50, 5);
        
        // Act
        venda.adicionarProduto(produto);
        venda.adicionarProduto(produto2);
        
        // Assert
        assertEquals(2, venda.getProdutosVendidos().size());
        assertTrue(venda.getProdutosVendidos().contains(produto));
        assertTrue(venda.getProdutosVendidos().contains(produto2));
        assertEquals(produto.getPreco() + produto2.getPreco(), venda.getTotalVenda());
        
        // Act - Tentar adicionar produto nulo
        venda.adicionarProduto(null);
        
        // Assert - Não deve adicionar
        assertEquals(2, venda.getProdutosVendidos().size());
    }
    
    /**
     * Teste 4: Verificar adição de menus à venda
     */
    @Test
    void testAdicionarMenus() {
        // Arrange
        Menu menu2 = new Menu(2, "Menu Premium", "Menu premium de teste");
        menu2.adicionarProduto(produto, 1);
        
        // Act
        venda.adicionarMenu(menu);
        venda.adicionarMenu(menu2);
        
        // Assert
        assertEquals(2, venda.getMenusVendidos().size());
        assertTrue(venda.getMenusVendidos().contains(menu));
        assertTrue(venda.getMenusVendidos().contains(menu2));
        assertEquals(menu.getPrecoTotal() + menu2.getPrecoTotal(), venda.getTotalVenda());
        
        // Act - Tentar adicionar menu nulo
        venda.adicionarMenu(null);
        
        // Assert - Não deve adicionar
        assertEquals(2, venda.getMenusVendidos().size());
    }
    
    /**
     * Teste 5: Verificar cálculo total da venda com diferentes tipos de itens
     */
    @Test
    void testCalculoTotalVenda() {
        // Arrange
        ProdutoBar produto2 = new ProdutoBar(2, "Refrigerante", "Bebida", 2.50, 50, 5);
        Menu menu2 = new Menu(2, "Menu Premium", "Menu premium de teste");
        menu2.adicionarProduto(produto2, 1);
        
        // Act - Adicionar todos os tipos de itens
        venda.adicionarBilhete(bilhete);
        venda.adicionarProduto(produto);
        venda.adicionarMenu(menu2);
        
        // Assert - Verificar cálculo total
        double totalEsperado = bilhete.getPrecoTotal() + produto.getPreco() + menu2.getPrecoTotal();
        assertEquals(totalEsperado, venda.getTotalVenda());
        
        // Verificar que as listas retornam cópias (não podem ser modificadas externamente)
        List<Bilhete> bilhetes = venda.getBilhetesVendidos();
        List<ProdutoBar> produtos = venda.getProdutosVendidos();
        List<Menu> menus = venda.getMenusVendidos();
        
        // Tentar modificar as listas (não deve afetar a venda original)
        bilhetes.clear();
        produtos.clear();
        menus.clear();
        
        // Assert - As listas originais não devem ter sido afetadas
        assertEquals(1, venda.getBilhetesVendidos().size());
        assertEquals(1, venda.getProdutosVendidos().size());
        assertEquals(1, venda.getMenusVendidos().size());
    }
} 