import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import pt.ipleiria.estg.dei.esoft.Backend.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class MenuTestCase {
    private Menu menu;
    private ProdutoBar pipocas;
    private ProdutoBar refri;
    private Menu.BilheteInfo bilheteInfo;

    @BeforeEach
    void setUp() {
        pipocas = new ProdutoBar(1, "Pipocas", "Snack", 4.50, 100, 10);
        refri = new ProdutoBar(2, "Refrigerante", "Bebida", 2.50, 50, 5);
        bilheteInfo = new Menu.BilheteInfo("Filme Teste", "Sala 1", "20:00", 8.0, "Normal");
        menu = new Menu(1, "Menu Família", "Inclui pipocas e refrigerante");
    }

    @Test
    void testCriacaoMenu() {
        assertNotNull(menu);
        assertEquals(1, menu.getIdMenu());
        assertEquals("Menu Família", menu.getNome());
        assertEquals("Inclui pipocas e refrigerante", menu.getDescricao());
        assertTrue(menu.isAtivo());
        assertEquals(0, menu.getNumeroItens());
        assertFalse(menu.temItens());
    }

    @Test
    void testAdicionarRemoverProduto() {
        boolean adicionado = menu.adicionarProduto(pipocas, 2);
        assertTrue(adicionado);
        assertEquals(1, menu.getNumeroItens());
        assertTrue(menu.temItens());
        Menu.ItemMenu item = menu.getItens().get(0);
        assertEquals("Pipocas", item.getNome());
        assertEquals(2, item.getQuantidade());
        assertEquals(9.0, item.getPrecoTotal());
        // Remover item
        boolean removido = menu.removerItem(item.getIdItem());
        assertTrue(removido);
        assertEquals(0, menu.getNumeroItens());
    }

    @Test
    void testAdicionarRemoverBilhete() {
        boolean adicionado = menu.adicionarBilhete(bilheteInfo, 3);
        assertTrue(adicionado);
        assertEquals(1, menu.getNumeroItens());
        Menu.ItemMenu item = menu.getItens().get(0);
        assertEquals("Bilhete - Filme Teste", item.getNome());
        assertEquals(3, item.getQuantidade());
        assertEquals(24.0, item.getPrecoTotal());
        // Remover item
        boolean removido = menu.removerItem(item.getIdItem());
        assertTrue(removido);
        assertEquals(0, menu.getNumeroItens());
    }

    @Test
    void testCalculoPrecoTotalEFormatacao() {
        menu.adicionarProduto(pipocas, 2);
        menu.adicionarProduto(refri, 1);
        menu.adicionarBilhete(bilheteInfo, 2);
        double precoEsperado = 2*4.5 + 1*2.5 + 2*8.0;
        assertEquals(precoEsperado, menu.getPrecoTotal());
        assertEquals(String.format("%.2f €", precoEsperado), menu.getPrecoTotalFormatado());
    }

    @Test
    void testAtivacaoDesativacaoEInfo() {
        menu.adicionarProduto(pipocas, 1);
        assertTrue(menu.isMenuValido());
        menu.desativar();
        assertFalse(menu.isAtivo());
        menu.ativar();
        assertTrue(menu.isAtivo());
        String infoResumida = menu.getInfoResumida();
        assertTrue(infoResumida.contains("Menu Família"));
        String infoCompleta = menu.getInfoCompleta();
        assertTrue(infoCompleta.contains("Menu Família"));
        assertTrue(infoCompleta.contains("Inclui pipocas"));
        assertTrue(infoCompleta.contains("Pipocas"));
    }
} 