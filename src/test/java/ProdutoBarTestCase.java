import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import pt.ipleiria.estg.dei.esoft.Backend.*;
import static org.junit.jupiter.api.Assertions.*;

class ProdutoBarTestCase {
    
    private ProdutoBar pipocas;
    private ProdutoBar refrigerante;
    private ProdutoBar chocolate;
    private ProdutoBar produtoEsgotado;
    private ProdutoBar produtoStockBaixo;
    
    @BeforeEach
    void setUp() {
        // Criar diferentes tipos de produtos para teste
        pipocas = new ProdutoBar(1, "Pipocas Grandes", "Snack", 4.50, 100, 10);
        refrigerante = new ProdutoBar(2, "Refrigerante", "Bebida", 2.50, 50, 5);
        chocolate = new ProdutoBar(3, "Chocolate", "Doce", 1.80, 3, 5); // stock baixo
        produtoEsgotado = new ProdutoBar(4, "Produto Esgotado", "Snack", 3.00, 0, 5);
        produtoStockBaixo = new ProdutoBar(5, "Produto Stock Baixo", "Bebida", 2.00, 2, 5);
    }
    
    /**
     * Teste 1: Verificar gestão de stock e validações
     */
    @Test
    void testGestaoStockEValidacoes() {
        // Assert - Estado inicial
        assertEquals(100, pipocas.getStockAtual());
        assertEquals(10, pipocas.getStockMinimo());
        assertTrue(pipocas.temStock());
        assertFalse(pipocas.isStockBaixo());
        assertFalse(pipocas.isEsgotado());
        assertEquals("EM STOCK", pipocas.getStatusStock());
        
        // Act - Adicionar stock
        pipocas.adicionarStock(50);
        
        // Assert - Stock aumentado
        assertEquals(150, pipocas.getStockAtual());
        assertFalse(pipocas.isStockBaixo());
        
        // Act - Remover stock
        boolean removeu = pipocas.removerStock(30);
        
        // Assert - Stock removido com sucesso
        assertTrue(removeu);
        assertEquals(120, pipocas.getStockAtual());
        
        // Act - Tentar remover mais stock do que disponível
        boolean removeuExcesso = pipocas.removerStock(200);
        
        // Assert - Não deve remover
        assertFalse(removeuExcesso);
        assertEquals(120, pipocas.getStockAtual()); // stock não deve ter mudado
        
        // Act - Remover todo o stock
        pipocas.removerStock(120);
        
        // Assert - Produto esgotado
        assertEquals(0, pipocas.getStockAtual());
        assertTrue(pipocas.isEsgotado());
        assertTrue(pipocas.isStockBaixo());
        assertEquals("ESGOTADO", pipocas.getStatusStock());
        assertFalse(pipocas.temStock());
        
        // Act - Tentar remover stock de produto esgotado
        boolean removeuEsgotado = pipocas.removerStock(10);
        
        // Assert - Não deve remover
        assertFalse(removeuEsgotado);
        assertEquals(0, pipocas.getStockAtual());
        
        // Act - Adicionar stock negativo (deve lançar exceção)
        assertThrows(IllegalArgumentException.class, () -> {
            pipocas.adicionarStock(-10);
        });
        
        // Act - Remover stock negativo (deve lançar exceção)
        assertThrows(IllegalArgumentException.class, () -> {
            pipocas.removerStock(-5);
        });
        
        // Testar produtos com stock baixo
        assertTrue(chocolate.isStockBaixo());
        assertEquals("STOCK BAIXO", chocolate.getStatusStock());
        assertEquals(2, chocolate.getUnidadesParaStockMinimo()); // faltam 2 para atingir mínimo
        
        // Testar produto esgotado
        assertTrue(produtoEsgotado.isEsgotado());
        assertTrue(produtoEsgotado.isStockBaixo());
        assertEquals(5, produtoEsgotado.getUnidadesParaStockMinimo()); // faltam 5 para atingir mínimo
    }
    
    /**
     * Teste 2: Verificar cálculos, formatação e informações
     */
    @Test
    void testCalculosFormatacaoEInformacoes() {
        // Assert - Cálculos de valor
        assertEquals(450.0, pipocas.getValorTotalStock()); // 100 * 4.50
        assertEquals("450.00 €", pipocas.getValorTotalStockFormatado());
        assertEquals("4.50 €", pipocas.getPrecoFormatado());
        
        // Assert - Percentagem de stock
        assertEquals(1000.0, pipocas.getPercentagemStock()); // 100/10 * 100
        assertEquals(60.0, chocolate.getPercentagemStock()); // 3/5 * 100
        assertEquals(0.0, produtoEsgotado.getPercentagemStock()); // 0/5 * 100
        
        // Act - Modificar preço
        pipocas.setPreco(5.00);
        
        // Assert - Valor total atualizado
        assertEquals(500.0, pipocas.getValorTotalStock()); // 100 * 5.00
        assertEquals("500.00 €", pipocas.getValorTotalStockFormatado());
        assertEquals("5.00 €", pipocas.getPrecoFormatado());
        
        // Act - Tentar definir preço negativo
        assertThrows(IllegalArgumentException.class, () -> {
            pipocas.setPreco(-2.50);
        });
        
        // Assert - Preço não deve ter sido alterado
        assertEquals(5.00, pipocas.getPreco());
        
        // Act - Tentar definir stock negativo
        assertThrows(IllegalArgumentException.class, () -> {
            pipocas.setStockAtual(-10);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            pipocas.setStockMinimo(-5);
        });
        
        // Assert - Validação de produto
        assertTrue(pipocas.isProdutoValido());
        assertTrue(pipocas.podeSerVendido());
        
        // Act - Desativar produto
        pipocas.desativar();
        
        // Assert - Produto não pode ser vendido quando inativo
        assertFalse(pipocas.podeSerVendido());
        assertFalse(pipocas.isAtivo());
        
        // Act - Reativar produto
        pipocas.ativar();
        
        // Assert - Produto pode ser vendido novamente
        assertTrue(pipocas.podeSerVendido());
        assertTrue(pipocas.isAtivo());
        
        // Testar alertas de stock baixo
        String alertaChocolate = chocolate.getAlertaStockBaixo();
        assertNotNull(alertaChocolate);
        assertTrue(alertaChocolate.contains("ALERTA"));
        assertTrue(alertaChocolate.contains("stock baixo"));
        assertTrue(alertaChocolate.contains("Repor 2 unidades"));
        
        String alertaEsgotado = produtoEsgotado.getAlertaStockBaixo();
        assertNotNull(alertaEsgotado);
        assertTrue(alertaEsgotado.contains("ALERTA"));
        assertTrue(alertaEsgotado.contains("ESGOTADO"));
        assertTrue(alertaEsgotado.contains("Repor stock urgentemente"));
        
        // Produto com stock normal não deve ter alerta
        assertNull(refrigerante.getAlertaStockBaixo());
        
        // Testar informações formatadas
        String infoResumida = pipocas.getInfoResumida();
        assertTrue(infoResumida.contains("#1"));
        assertTrue(infoResumida.contains("Pipocas Grandes"));
        assertTrue(infoResumida.contains("Snack"));
        assertTrue(infoResumida.contains("100/10"));
        assertTrue(infoResumida.contains("5.00€"));
        assertTrue(infoResumida.contains("EM STOCK"));
        
        String infoCompleta = pipocas.getInfoCompleta();
        assertTrue(infoCompleta.contains("Produto #1"));
        assertTrue(infoCompleta.contains("Nome: Pipocas Grandes"));
        assertTrue(infoCompleta.contains("Tipo: Snack"));
        assertTrue(infoCompleta.contains("Preço: 5.00 €"));
        assertTrue(infoCompleta.contains("Stock atual: 100 unidades"));
        assertTrue(infoCompleta.contains("Stock mínimo: 10 unidades"));
        assertTrue(infoCompleta.contains("Status: EM STOCK"));
        assertTrue(infoCompleta.contains("Valor total em stock: 500.00 €"));
        assertTrue(infoCompleta.contains("Ativo: Sim"));
        
        // Testar toString
        String toString = pipocas.toString();
        assertTrue(toString.contains("ProdutoBar{"));
        assertTrue(toString.contains("idProduto=1"));
        assertTrue(toString.contains("nome='Pipocas Grandes'"));
        assertTrue(toString.contains("tipo='Snack'"));
        assertTrue(toString.contains("preco=5.00 €"));
        assertTrue(toString.contains("stock=100/10"));
        assertTrue(toString.contains("ativo=true"));
    }
} 