import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import pt.ipleiria.estg.dei.esoft.Backend.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class FilmeTestCase {
    
    private Filme filmeAcao;
    private Filme filmeComedia;
    private Filme filmeAntigo;
    private Filme filmeInativo;
    
    @BeforeEach
    void setUp() {
        // Criar diferentes tipos de filmes para teste
        filmeAcao = new Filme("Velocidade Furiosa X", "Ação", 141, "Dom Toretto e sua família enfrentam o vilão mais letal.", 8.50, 2023, "M/12", true, 1, 30);
        filmeComedia = new Filme("Agentes Secundários", "Comédia", 107, "Uma comédia hilariante sobre agentes secretos incompetentes.", 7.00, 2010, "M/12", true, 1, 30);
        filmeAntigo = new Filme("Casablanca", "Drama", 102, "Um clássico do cinema americano.", 5.00, 1942, "M/12", false, 1, 30);
        filmeInativo = new Filme("Filme Teste", "Terror", 95, "Filme de teste inativo.", 6.00, 2023, "M/16", false, 1, 30);
    }
    
    /**
     * Teste 1: Verificar criação de filmes e propriedades básicas
     */
    @Test
    void testCriacaoFilmes() {
        // Assert - Filme de Ação
        assertNotNull(filmeAcao);
        assertEquals("Velocidade Furiosa X", filmeAcao.getTitulo());
        assertEquals("Ação", filmeAcao.getGenero());
        assertEquals(141, filmeAcao.getDuracao());
        assertEquals("Dom Toretto e sua família enfrentam o vilão mais letal.", filmeAcao.getSinopse());
        assertEquals(8.50, filmeAcao.getPrecoAluguer());
        assertEquals(2023, filmeAcao.getAnoLancamento());
        assertEquals("M/12", filmeAcao.getClassificacaoEtaria());
        assertTrue(filmeAcao.isAtivo());
        assertEquals(1, filmeAcao.getDiasMinimoAluguer());
        assertEquals(30, filmeAcao.getDiasMaximoAluguer());
        
        // Assert - Filme Antigo
        assertNotNull(filmeAntigo);
        assertEquals("Casablanca", filmeAntigo.getTitulo());
        assertEquals(1942, filmeAntigo.getAnoLancamento());
        assertFalse(filmeAntigo.isAtivo());
    }
    
    /**
     * Teste 2: Verificar formatação de duração
     */
    @Test
    void testDuracaoFormatada() {
        // Assert - Filme de 141 minutos (2h 21m)
        assertEquals("2h 21m", filmeAcao.getDuracaoFormatada());
        
        // Assert - Filme de 107 minutos (1h 47m)
        assertEquals("1h 47m", filmeComedia.getDuracaoFormatada());
        
        // Assert - Filme de 102 minutos (1h 42m)
        assertEquals("1h 42m", filmeAntigo.getDuracaoFormatada());
        
        // Testar com duração exata de horas
        Filme filmeExato = new Filme("Teste", "Ação", 120, "Teste", 5.0, 2023, "M/12", true, 1, 30);
        assertEquals("2h 00m", filmeExato.getDuracaoFormatada());
        
        // Testar com duração menor que 1 hora
        Filme filmeCurto = new Filme("Teste", "Ação", 45, "Teste", 5.0, 2023, "M/12", true, 1, 30);
        assertEquals("0h 45m", filmeCurto.getDuracaoFormatada());
    }
    
    /**
     * Teste 3: Verificar ativação e desativação
     */
    @Test
    void testAtivacaoDesativacao() {
        // Arrange - Filme ativo
        assertTrue(filmeAcao.isAtivo());
        
        // Act - Desativar filme
        filmeAcao.desativar();
        
        // Assert - Filme desativado
        assertFalse(filmeAcao.isAtivo());
        
        // Act - Reativar filme
        filmeAcao.ativar();
        
        // Assert - Filme reativado
        assertTrue(filmeAcao.isAtivo());
        
        // Act - Usar setter
        filmeAcao.setAtivo(false);
        
        // Assert - Filme desativado via setter
        assertFalse(filmeAcao.isAtivo());
    }
    
    /**
     * Teste 4: Verificar período de aluguer formatado
     */
    @Test
    void testPeriodoAluguerFormatado() {
        // Assert - Período com mínimo e máximo diferentes
        assertEquals("1 a 30 dias", filmeAcao.getPeriodoAluguerFormatado());
        
        // Testar com período fixo (mínimo = máximo)
        Filme filmePeriodoFixo = new Filme("Teste", "Ação", 120, "Teste", 5.0, 2023, "M/12", true, 7, 7);
        assertEquals("7 dias", filmePeriodoFixo.getPeriodoAluguerFormatado());
        
        // Testar com período de 1 dia
        Filme filmeUmDia = new Filme("Teste", "Ação", 120, "Teste", 5.0, 2023, "M/12", true, 1, 1);
        assertEquals("1 dia", filmeUmDia.getPeriodoAluguerFormatado());
    }
    
    /**
     * Teste 5: Verificar validação de período de aluguer
     */
    @Test
    void testValidacaoPeriodoAluguer() {
        // Assert - Períodos válidos
        assertTrue(filmeAcao.isPeriodoAluguerValido(1));
        assertTrue(filmeAcao.isPeriodoAluguerValido(15));
        assertTrue(filmeAcao.isPeriodoAluguerValido(30));
        
        // Assert - Períodos inválidos
        assertFalse(filmeAcao.isPeriodoAluguerValido(0));
        assertFalse(filmeAcao.isPeriodoAluguerValido(31));
        assertFalse(filmeAcao.isPeriodoAluguerValido(-1));
        
        // Testar com filme de período fixo
        Filme filmePeriodoFixo = new Filme("Teste", "Ação", 120, "Teste", 5.0, 2023, "M/12", true, 7, 7);
        assertTrue(filmePeriodoFixo.isPeriodoAluguerValido(7));
        assertFalse(filmePeriodoFixo.isPeriodoAluguerValido(6));
        assertFalse(filmePeriodoFixo.isPeriodoAluguerValido(8));
    }
    
    /**
     * Teste 6: Verificar cálculo de preço de aluguer
     */
    @Test
    void testCalculoPrecoAluguer() {
        // Assert - Preço para período válido
        assertEquals(8.50, filmeAcao.calcularPrecoAluguer(1));
        assertEquals(8.50, filmeAcao.calcularPrecoAluguer(15));
        assertEquals(8.50, filmeAcao.calcularPrecoAluguer(30));
        
        // Assert - Exceção para período inválido
        assertThrows(IllegalArgumentException.class, () -> {
            filmeAcao.calcularPrecoAluguer(0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            filmeAcao.calcularPrecoAluguer(31);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            filmeAcao.calcularPrecoAluguer(-1);
        });
        
        // Testar com filme de período fixo
        Filme filmePeriodoFixo = new Filme("Teste", "Ação", 120, "Teste", 10.0, 2023, "M/12", true, 7, 7);
        assertEquals(10.0, filmePeriodoFixo.calcularPrecoAluguer(7));
        
        assertThrows(IllegalArgumentException.class, () -> {
            filmePeriodoFixo.calcularPrecoAluguer(6);
        });
    }
    
    /**
     * Teste 7: Verificar cálculos de datas e atrasos
     */
    @Test
    void testCalculosDatas() {
        // Arrange
        LocalDate dataAluguer = LocalDate.of(2023, 6, 1);
        LocalDate dataAtual = LocalDate.of(2023, 6, 15);
        int numeroDias = 30;
        
        // Act & Assert - Data de devolução
        LocalDate dataDevolucao = filmeAcao.calcularDataDevolucao(dataAluguer, numeroDias);
        assertEquals(LocalDate.of(2023, 7, 1), dataDevolucao);
        
        // Act & Assert - Dias restantes (ainda não expirou)
        long diasRestantes = filmeAcao.calcularDiasRestantes(dataAluguer, dataAtual, numeroDias);
        assertEquals(16, diasRestantes);
        
        // Act & Assert - Verificar se está em atraso (não está)
        assertFalse(filmeAcao.isAluguerEmAtraso(dataAluguer, dataAtual, numeroDias));
        
        // Testar com data atual após a devolução
        LocalDate dataAtualAtrasada = LocalDate.of(2023, 7, 5);
        long diasRestantesAtrasado = filmeAcao.calcularDiasRestantes(dataAluguer, dataAtualAtrasada, numeroDias);
        assertEquals(0, diasRestantesAtrasado); // não retorna valores negativos
        
        assertTrue(filmeAcao.isAluguerEmAtraso(dataAluguer, dataAtualAtrasada, numeroDias));
        
        // Testar com período inválido
        assertThrows(IllegalArgumentException.class, () -> {
            filmeAcao.calcularDataDevolucao(dataAluguer, 31);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            filmeAcao.calcularDiasRestantes(dataAluguer, dataAtual, 31);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            filmeAcao.isAluguerEmAtraso(dataAluguer, dataAtual, 31);
        });
    }
} 