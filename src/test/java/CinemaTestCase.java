import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import pt.ipleiria.estg.dei.esoft.Backend.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class CinemaTestCase {
    private Cinema cinema;
    private Sala sala;
    private Filme filme;
    private Sessao sessao;
    private ProdutoBar produto;
    private Menu menu;
    private Venda venda;

    @BeforeEach
    void setUp() {
        cinema = new Cinema();
        sala = new Sala(1, "Sala 1", "Normal", 100, "10x10", true, "");
        filme = new Filme("Filme Teste", "Ação", 120, "Sinopse", 8.0, 2023, "M/12", true, 1, 30);
        sessao = new Sessao(1, filme, sala, LocalDate.now(), LocalDate.now().plusDays(7), LocalTime.of(20, 0));
        produto = new ProdutoBar(1, "Pipocas", "Snack", 4.50, 100, 10);
        menu = new Menu(1, "Menu Teste", "Inclui pipocas");
        venda = new Venda(1, LocalDateTime.now());
    }

    @Test
    void testAdicaoDeEntidades() {
        cinema.adicionarSala(sala);
        cinema.adicionarFilme(filme);
        cinema.adicionarSessao(sessao);
        cinema.adicionarProdutoBar(produto);
        cinema.adicionarMenu(menu);
        assertTrue(cinema.getSalas().contains(sala));
        assertTrue(cinema.getFilmes().contains(filme));
        assertTrue(cinema.getSessoes().contains(sessao));
        assertTrue(cinema.getProdutosBar().contains(produto));
        assertTrue(cinema.getMenus().contains(menu));
    }

    @Test
    void testRegistroEConsultaDeVendas() {
        cinema.registarVenda(venda);
        assertTrue(cinema.getVendas().contains(venda));
        // Buscar venda por ID (simples)
        Venda vendaBuscada = cinema.getVendas().stream().filter(v -> v.getId() == venda.getId()).findFirst().orElse(null);
        assertNotNull(vendaBuscada);
        assertEquals(venda, vendaBuscada);
    }

    @Test
    void testGestaoMenusEConsulta() {
        cinema.adicionarMenu(menu);
        GestaoMenus gestaoMenus = cinema.getGestaoMenus();
        assertNotNull(gestaoMenus);
        // Verificar se o menu foi adicionado à gestão
        List<Menu> menus = gestaoMenus.obterTodosMenus();
        assertTrue(menus.contains(menu));
    }
} 