import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import pt.ipleiria.estg.dei.esoft.Backend.*;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class PrecosBilhetesTestCase {
    
    @BeforeEach
    void setUp() {
        // Restaurar preços padrão antes de cada teste
        PrecosBilhetes.restaurarPrecosPadrao();
    }
    
    /**
     * Teste 1: Verificar preços padrão e operações básicas
     */
    @Test
    void testPrecosPadraoEOperacoesBasicas() {
        // Assert - Verificar preços padrão
        assertEquals(8.0, PrecosBilhetes.getPreco(TipoSala.NORMAL));
        assertEquals(12.0, PrecosBilhetes.getPreco(TipoSala.VIP));
        assertEquals(10.0, PrecosBilhetes.getPreco(TipoSala.TRES_D));
        assertEquals(15.0, PrecosBilhetes.getPreco(TipoSala.IMAX));
        assertEquals(18.0, PrecosBilhetes.getPreco(TipoSala.QUATRO_DX));
        assertEquals(20.0, PrecosBilhetes.getPreco(TipoSala.XVISION));
        
        // Assert - Verificar preços formatados
        assertEquals("8.00 €", PrecosBilhetes.getPrecoFormatado(TipoSala.NORMAL));
        assertEquals("12.00 €", PrecosBilhetes.getPrecoFormatado(TipoSala.VIP));
        assertEquals("10.00 €", PrecosBilhetes.getPrecoFormatado(TipoSala.TRES_D));
        
        // Act - Modificar preço
        PrecosBilhetes.setPreco(TipoSala.NORMAL, 9.50);
        
        // Assert - Verificar modificação
        assertEquals(9.50, PrecosBilhetes.getPreco(TipoSala.NORMAL));
        assertEquals("9.50 €", PrecosBilhetes.getPrecoFormatado(TipoSala.NORMAL));
        
        // Act - Tentar definir preço negativo
        assertThrows(IllegalArgumentException.class, () -> {
            PrecosBilhetes.setPreco(TipoSala.NORMAL, -5.0);
        });
        
        // Assert - Preço não deve ter sido alterado
        assertEquals(9.50, PrecosBilhetes.getPreco(TipoSala.NORMAL));
        
        // Act - Obter todos os preços
        Map<TipoSala, Double> todosPrecos = PrecosBilhetes.getTodosPrecos();
        
        // Assert - Verificar que retorna uma cópia
        assertNotNull(todosPrecos);
        assertEquals(6, todosPrecos.size());
        assertEquals(9.50, todosPrecos.get(TipoSala.NORMAL));
        assertEquals(12.0, todosPrecos.get(TipoSala.VIP));
        
        // Act - Modificar a cópia (não deve afetar o original)
        todosPrecos.put(TipoSala.NORMAL, 999.0);
        
        // Assert - Original não deve ter sido afetado
        assertEquals(9.50, PrecosBilhetes.getPreco(TipoSala.NORMAL));
    }
    
    /**
     * Teste 2: Verificar operações de desconto e aumento
     */
    @Test
    void testDescontosEAumentos() {
        // Arrange - Verificar preços iniciais
        double precoNormalInicial = PrecosBilhetes.getPreco(TipoSala.NORMAL);
        double precoVIPInicial = PrecosBilhetes.getPreco(TipoSala.VIP);
        
        // Act - Aplicar desconto de 20%
        PrecosBilhetes.aplicarDescontoGlobal(20.0);
        
        // Assert - Verificar desconto aplicado
        assertEquals(precoNormalInicial * 0.8, PrecosBilhetes.getPreco(TipoSala.NORMAL), 0.01);
        assertEquals(precoVIPInicial * 0.8, PrecosBilhetes.getPreco(TipoSala.VIP), 0.01);
        assertEquals("6.40 €", PrecosBilhetes.getPrecoFormatado(TipoSala.NORMAL));
        assertEquals("9.60 €", PrecosBilhetes.getPrecoFormatado(TipoSala.VIP));
        
        // Act - Aplicar aumento de 25%
        PrecosBilhetes.aplicarAumentoGlobal(25.0);
        
        // Assert - Verificar aumento aplicado
        assertEquals(precoNormalInicial * 0.8 * 1.25, PrecosBilhetes.getPreco(TipoSala.NORMAL), 0.01);
        assertEquals(precoVIPInicial * 0.8 * 1.25, PrecosBilhetes.getPreco(TipoSala.VIP), 0.01);
        
        // Act - Tentar aplicar desconto inválido
        assertThrows(IllegalArgumentException.class, () -> {
            PrecosBilhetes.aplicarDescontoGlobal(-10.0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            PrecosBilhetes.aplicarDescontoGlobal(150.0);
        });
        
        // Act - Tentar aplicar aumento inválido
        assertThrows(IllegalArgumentException.class, () -> {
            PrecosBilhetes.aplicarAumentoGlobal(-5.0);
        });
        
        // Assert - Preços não devem ter sido alterados pelos valores inválidos
        assertEquals(precoNormalInicial * 0.8 * 1.25, PrecosBilhetes.getPreco(TipoSala.NORMAL), 0.01);
        
        // Act - Restaurar preços padrão
        PrecosBilhetes.restaurarPrecosPadrao();
        
        // Assert - Verificar restauração
        assertEquals(precoNormalInicial, PrecosBilhetes.getPreco(TipoSala.NORMAL));
        assertEquals(precoVIPInicial, PrecosBilhetes.getPreco(TipoSala.VIP));
        assertEquals("8.00 €", PrecosBilhetes.getPrecoFormatado(TipoSala.NORMAL));
        assertEquals("12.00 €", PrecosBilhetes.getPrecoFormatado(TipoSala.VIP));
        
        // Act - Verificar todos os preços formatados
        String todosPrecosFormatados = PrecosBilhetes.getTodosPrecosFormatados();
        
        // Assert - Verificar conteúdo da string formatada
        assertNotNull(todosPrecosFormatados);
        assertTrue(todosPrecosFormatados.contains("Preços dos bilhetes por tipo de sala:"));
        assertTrue(todosPrecosFormatados.contains("Normal: 8.00 €"));
        assertTrue(todosPrecosFormatados.contains("VIP: 12.00 €"));
        assertTrue(todosPrecosFormatados.contains("3D: 10.00 €"));
        assertTrue(todosPrecosFormatados.contains("IMAX: 15.00 €"));
        assertTrue(todosPrecosFormatados.contains("4DX: 18.00 €"));
        assertTrue(todosPrecosFormatados.contains("XVision: 20.00 €"));
    }
} 