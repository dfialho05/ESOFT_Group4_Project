import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import pt.ipleiria.estg.dei.esoft.Backend.*;
import static org.junit.jupiter.api.Assertions.*;

class SalaTestCase {
    
    private Sala salaNormal;
    private Sala salaVIP;
    private Sala sala3D;
    private Sala salaInativa;
    
    @BeforeEach
    void setUp() {
        // Criar diferentes tipos de salas para teste
        salaNormal = new Sala(1, "Sala 1", "Normal", 100, "10x10", true, "Sala padrão");
        salaVIP = new Sala(2, "Sala VIP", "VIP", 50, "5x10", true, "Sala VIP com assentos reclináveis");
        sala3D = new Sala(3, "Sala 3D", "3D", 120, "12x10", true, "Sala 3D com projetor especial");
        salaInativa = new Sala(4, "Sala Manutenção", "Normal", 80, "8x10", false, "Em manutenção");
    }
    
    /**
     * Teste 1: Verificar criação de salas e propriedades básicas
     */
    @Test
    void testCriacaoSalas() {
        // Assert - Sala Normal
        assertNotNull(salaNormal);
        assertEquals(1, salaNormal.getId());
        assertEquals("Sala 1", salaNormal.getNome());
        assertEquals("Normal", salaNormal.getTipoSala());
        assertEquals(100, salaNormal.getCapacidade());
        assertEquals("10x10", salaNormal.getConfiguracao());
        assertTrue(salaNormal.isAtivo());
        assertEquals("Sala padrão", salaNormal.getDescricao());
        
        // Assert - Sala VIP
        assertNotNull(salaVIP);
        assertEquals(2, salaVIP.getId());
        assertEquals("Sala VIP", salaVIP.getNome());
        assertEquals("VIP", salaVIP.getTipoSala());
        assertEquals(50, salaVIP.getCapacidade());
        assertEquals("5x10", salaVIP.getConfiguracao());
        assertTrue(salaVIP.isAtivo());
        assertEquals("Sala VIP com assentos reclináveis", salaVIP.getDescricao());
        
        // Assert - Sala Inativa
        assertNotNull(salaInativa);
        assertEquals(4, salaInativa.getId());
        assertFalse(salaInativa.isAtivo());
        assertEquals("Em manutenção", salaInativa.getDescricao());
    }
    
    /**
     * Teste 2: Verificar métodos de ativação e desativação
     */
    @Test
    void testAtivacaoDesativacao() {
        // Arrange - Sala ativa
        assertTrue(salaNormal.isAtivo());
        assertTrue(salaNormal.estaDisponivel());
        assertEquals("Ativa", salaNormal.getStatusSala());
        
        // Act - Desativar sala
        salaNormal.desativar();
        
        // Assert - Sala desativada
        assertFalse(salaNormal.isAtivo());
        assertFalse(salaNormal.estaDisponivel());
        assertEquals("Inativa", salaNormal.getStatusSala());
        
        // Act - Reativar sala
        salaNormal.ativar();
        
        // Assert - Sala reativada
        assertTrue(salaNormal.isAtivo());
        assertTrue(salaNormal.estaDisponivel());
        assertEquals("Ativa", salaNormal.getStatusSala());
        
        // Act - Usar setter
        salaNormal.setAtivo(false);
        
        // Assert - Sala desativada via setter
        assertFalse(salaNormal.isAtivo());
        assertFalse(salaNormal.estaDisponivel());
    }
    
    /**
     * Teste 3: Verificar métodos de acessibilidade
     */
    @Test
    void testAcessibilidade() {
        // Assert - Todas as salas devem ter acesso para cadeira de rodas
        assertTrue(salaNormal.temAcessoCadeiraRodas());
        assertTrue(salaVIP.temAcessoCadeiraRodas());
        assertTrue(sala3D.temAcessoCadeiraRodas());
        assertTrue(salaInativa.temAcessoCadeiraRodas());
        
        // Assert - Informações de acessibilidade
        assertEquals("Acessível para cadeira de rodas", salaNormal.getInfoAcessibilidade());
        assertEquals("Acessível para cadeira de rodas", salaVIP.getInfoAcessibilidade());
        assertEquals("Acessível para cadeira de rodas", sala3D.getInfoAcessibilidade());
        assertEquals("Acessível para cadeira de rodas", salaInativa.getInfoAcessibilidade());
    }
    
    /**
     * Teste 4: Verificar modificação de propriedades
     */
    @Test
    void testModificacaoPropriedades() {
        // Act - Modificar propriedades
        salaNormal.setNome("Sala 1 Renovada");
        salaNormal.setTipoSala("Premium");
        salaNormal.setCapacidade(120);
        salaNormal.setConfiguracao("12x10");
        salaNormal.setDescricao("Sala renovada com melhor som");
        
        // Assert - Verificar modificações
        assertEquals("Sala 1 Renovada", salaNormal.getNome());
        assertEquals("Premium", salaNormal.getTipoSala());
        assertEquals(120, salaNormal.getCapacidade());
        assertEquals("12x10", salaNormal.getConfiguracao());
        assertEquals("Sala renovada com melhor som", salaNormal.getDescricao());
    }
    
    /**
     * Teste 5: Verificar métodos toString, equals e hashCode
     */
    @Test
    void testMetodosObjeto() {
        // Arrange - Criar sala idêntica
        Sala salaIdentica = new Sala(1, "Sala 1", "Normal", 100, "10x10", true, "Sala padrão");
        Sala salaDiferente = new Sala(2, "Sala 2", "Normal", 100, "10x10", true, "Sala padrão");
        
        // Assert - equals
        assertTrue(salaNormal.equals(salaIdentica));
        assertFalse(salaNormal.equals(salaDiferente));
        assertFalse(salaNormal.equals(null));
        assertTrue(salaNormal.equals(salaNormal)); // mesmo objeto
        
        // Assert - hashCode
        assertEquals(salaNormal.hashCode(), salaIdentica.hashCode());
        assertNotEquals(salaNormal.hashCode(), salaDiferente.hashCode());
        
        // Assert - toString
        String toStringSala = salaNormal.toString();
        assertTrue(toStringSala.contains("Sala{"));
        assertTrue(toStringSala.contains("id=1"));
        assertTrue(toStringSala.contains("nome='Sala 1'"));
        assertTrue(toStringSala.contains("tipoSala=Normal"));
        assertTrue(toStringSala.contains("capacidade=100"));
        assertTrue(toStringSala.contains("configuracao='10x10'"));
        assertTrue(toStringSala.contains("ativo=true"));
        assertTrue(toStringSala.contains("descricao='Sala padrão'"));
        
        // Testar com sala inativa
        String toStringSalaInativa = salaInativa.toString();
        assertTrue(toStringSalaInativa.contains("ativo=false"));
    }
} 