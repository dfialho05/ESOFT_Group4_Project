import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import pt.ipleiria.estg.dei.esoft.Backend.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class GestaoMenusTestCase {
    private GestaoMenus gestaoMenus;
    private ProdutoBar pipocas;
    private ProdutoBar refri;
    private Menu menu;

    @BeforeEach
    void setUp() {
        gestaoMenus = new GestaoMenus();
        pipocas = new ProdutoBar(1, "Pipocas", "Snack", 4.50, 100, 10);
        refri = new ProdutoBar(2, "Refrigerante", "Bebida", 2.50, 50, 5);
        menu = gestaoMenus.criarMenu("Menu Teste", "Inclui pipocas");
    }

    @Test
    void testCriacaoMenuEGestao() {
        assertNotNull(menu);
        assertEquals("Menu Teste", menu.getNome());
        List<Menu> menus = gestaoMenus.obterTodosMenus();
        assertTrue(menus.contains(menu));
        assertEquals(1, gestaoMenus.getNumeroMenus());
    }

    @Test
    void testAdicionarRemoverProdutoEmMenu() {
        gestaoMenus.adicionarProdutoAoMenu(menu.getIdMenu(), pipocas, 2);
        gestaoMenus.adicionarProdutoAoMenu(menu.getIdMenu(), refri, 1);
        Menu menuBuscado = gestaoMenus.obterMenu(menu.getIdMenu());
        assertEquals(2, menuBuscado.getNumeroItens());
        // Remover produto
        Menu.ItemMenu item = menuBuscado.getItens().get(0);
        boolean removido = gestaoMenus.removerItemDoMenu(menu.getIdMenu(), item.getIdItem());
        assertTrue(removido);
        assertEquals(1, menuBuscado.getNumeroItens());
    }

    @Test
    void testBuscaMenuPorId() {
        Menu buscado = gestaoMenus.obterMenu(menu.getIdMenu());
        assertNotNull(buscado);
        assertEquals(menu, buscado);
        assertNull(gestaoMenus.obterMenu(999));
    }

    @Test
    void testValidacaoEAtivacaoDesativacao() {
        assertTrue(menu.isMenuValido());
        gestaoMenus.desativarMenu(menu.getIdMenu());
        assertFalse(menu.isMenuValido());
        gestaoMenus.ativarMenu(menu.getIdMenu());
        assertTrue(menu.isMenuValido());
    }
} 