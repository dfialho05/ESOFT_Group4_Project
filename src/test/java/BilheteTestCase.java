import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import pt.ipleiria.estg.dei.esoft.Backend.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BilheteTestCase {
    
    private Filme filme;
    private Sala sala;
    private Sessao sessao;
    private Bilhete bilhete;
    
    @BeforeEach
    void setUp() {
        // Criar dados de teste
        filme = new Filme("Teste Filme", "Ação", 120, "Descrição do filme", 8.50, 2023, "M/12", true, 1, 30);
        sala = new Sala(1, "Sala Teste", "Normal", 100, "10x10", true, "");
        sessao = new Sessao(1, filme, sala, LocalDate.now(), LocalDate.now().plusDays(7), LocalTime.of(20, 0));
        bilhete = new Bilhete(1, sessao);
    }
    
    /**
     * Teste 1: Verificar criação de bilhete e propriedades básicas
     */
    @Test
    void testCriacaoBilhete() {
        // Arrange & Act (já feito no setUp)
        
        // Assert
        assertNotNull(bilhete);
        assertEquals(1, bilhete.getIdBilhete());
        assertEquals(sessao, bilhete.getSessao());
        assertTrue(bilhete.isAtivo());
        assertEquals(0.0, bilhete.getPrecoTotal());
        assertEquals(0, bilhete.getNumeroLugares());
        assertFalse(bilhete.temLugares());
        assertNotNull(bilhete.getDataVenda());
        assertTrue(bilhete.getDataVenda().isBefore(LocalDateTime.now().plusSeconds(1)));
    }
    
    /**
     * Teste 2: Verificar adição e remoção de lugares
     */
    @Test
    void testAdicionarERemoverLugares() {
        // Arrange
        LocalDate dataSessao = LocalDate.now().plusDays(1);
        
        // Act - Adicionar lugares
        boolean adicionou1 = bilhete.adicionarLugar(1, 1, dataSessao);
        boolean adicionou2 = bilhete.adicionarLugar(1, 2, dataSessao);
        boolean adicionou3 = bilhete.adicionarLugar(2, 1, dataSessao);
        
        // Assert - Verificar se foram adicionados
        assertTrue(adicionou1);
        assertTrue(adicionou2);
        assertTrue(adicionou3);
        assertEquals(3, bilhete.getNumeroLugares());
        assertTrue(bilhete.temLugares());
        assertTrue(bilhete.temLugar(1, 1, dataSessao));
        assertTrue(bilhete.temLugar(1, 2, dataSessao));
        assertTrue(bilhete.temLugar(2, 1, dataSessao));
        
        // Act - Remover lugares
        boolean removeu1 = bilhete.removerLugar(1, 1, dataSessao);
        boolean removeu2 = bilhete.removerLugar(1, 2, dataSessao);
        
        // Assert - Verificar se foram removidos
        assertTrue(removeu1);
        assertTrue(removeu2);
        assertEquals(1, bilhete.getNumeroLugares());
        assertTrue(bilhete.temLugares());
        assertFalse(bilhete.temLugar(1, 1, dataSessao));
        assertFalse(bilhete.temLugar(1, 2, dataSessao));
        assertTrue(bilhete.temLugar(2, 1, dataSessao));
        
        // Act - Tentar remover lugar que não existe
        boolean removeuInexistente = bilhete.removerLugar(5, 5, dataSessao);
        
        // Assert - Verificar que não removeu
        assertFalse(removeuInexistente);
        assertEquals(1, bilhete.getNumeroLugares());
    }
    
    /**
     * Teste 3: Verificar cálculo de preços
     */
    @Test
    void testCalculoPrecos() {
        // Arrange
        LocalDate dataSessao = LocalDate.now().plusDays(1);
        double precoPorLugar = sessao.getPrecoBilhete(); // 8.50
        
        // Act - Adicionar lugares
        bilhete.adicionarLugar(1, 1, dataSessao);
        bilhete.adicionarLugar(1, 2, dataSessao);
        bilhete.adicionarLugar(2, 1, dataSessao);
        
        // Assert - Verificar cálculos
        assertEquals(precoPorLugar, bilhete.getPrecoPorLugar());
        assertEquals(precoPorLugar * 3, bilhete.getPrecoTotal());
        assertEquals("8.50 €", bilhete.getPrecoPorLugarFormatado());
        assertEquals("25.50 €", bilhete.getPrecoTotalFormatado());
        
        // Act - Remover um lugar
        bilhete.removerLugar(1, 1, dataSessao);
        
        // Assert - Verificar recálculo
        assertEquals(precoPorLugar * 2, bilhete.getPrecoTotal());
        assertEquals("17.00 €", bilhete.getPrecoTotalFormatado());
    }
    
    /**
     * Teste 4: Verificar validação de bilhete
     */
    @Test
    void testValidacaoBilhete() {
        // Arrange
        LocalDate dataSessao = LocalDate.now().plusDays(1);
        
        // Act & Assert - Bilhete sem lugares não é válido
        assertFalse(bilhete.isBilheteValido());
        assertFalse(bilhete.isBilheteValidoParaData(dataSessao));
        assertFalse(bilhete.isBilheteValidoHoje());
        assertTrue(bilhete.isBilheteExpirado()); // sem lugares = expirado
        
        // Act - Adicionar lugar
        bilhete.adicionarLugar(1, 1, dataSessao);
        
        // Assert - Bilhete com lugar é válido
        assertTrue(bilhete.isBilheteValido());
        assertTrue(bilhete.isBilheteValidoParaData(dataSessao));
        assertFalse(bilhete.isBilheteExpirado());
        
        // Act - Desativar bilhete
        bilhete.desativarBilhete();
        
        // Assert - Bilhete desativado não é válido
        assertFalse(bilhete.isBilheteValido());
        assertFalse(bilhete.isAtivo());
        
        // Act - Reativar bilhete
        bilhete.ativarBilhete();
        
        // Assert - Bilhete reativado é válido
        assertTrue(bilhete.isBilheteValido());
        assertTrue(bilhete.isAtivo());
    }
    
    /**
     * Teste 5: Verificar cancelamento de bilhete
     */
    @Test
    void testCancelamentoBilhete() {
        // Arrange
        LocalDate dataSessao = LocalDate.now().plusDays(1);
        bilhete.adicionarLugar(1, 1, dataSessao);
        bilhete.adicionarLugar(1, 2, dataSessao);
        bilhete.adicionarLugar(2, 1, dataSessao);
        
        // Verificar estado inicial
        assertEquals(3, bilhete.getNumeroLugares());
        assertTrue(bilhete.isAtivo());
        assertTrue(bilhete.getPrecoTotal() > 0);
        
        // Act - Cancelar bilhete
        bilhete.cancelarBilhete();
        
        // Assert - Verificar cancelamento
        assertEquals(0, bilhete.getNumeroLugares());
        assertFalse(bilhete.isAtivo());
        assertEquals(0.0, bilhete.getPrecoTotal());
        assertFalse(bilhete.temLugares());
        assertFalse(bilhete.isBilheteValido());
        
        // Act - Tentar cancelar novamente (não deve fazer nada)
        bilhete.cancelarBilhete();
        
        // Assert - Estado deve permanecer o mesmo
        assertEquals(0, bilhete.getNumeroLugares());
        assertFalse(bilhete.isAtivo());
        assertEquals(0.0, bilhete.getPrecoTotal());
    }
}
