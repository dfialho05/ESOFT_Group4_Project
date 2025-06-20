package pt.ipleiria.estg.dei.esoft.Backend;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe para gerir os preços constantes dos bilhetes por tipo de sala
 */
public class PrecosBilhetes {
    
    // Mapa estático para armazenar os preços por tipo de sala
    private static final Map<TipoSala, Double> precosPorTipo = new HashMap<>();
    
    // Inicialização dos preços padrão
    static {
        precosPorTipo.put(TipoSala.NORMAL, 8.0);
        precosPorTipo.put(TipoSala.VIP, 12.0);
        precosPorTipo.put(TipoSala.TRES_D, 10.0);
        precosPorTipo.put(TipoSala.IMAX, 15.0);
        precosPorTipo.put(TipoSala.QUATRO_DX, 18.0);
        precosPorTipo.put(TipoSala.XVISION, 20.0);
    }
    
    /**
     * Obtém o preço para um tipo de sala específico
     */
    public static double getPreco(TipoSala tipoSala) {
        return precosPorTipo.getOrDefault(tipoSala, 8.0); // preço padrão se não encontrado
    }
    
    /**
     * Define o preço para um tipo de sala específico
     */
    public static void setPreco(TipoSala tipoSala, double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo");
        }
        precosPorTipo.put(tipoSala, preco);
    }
    
    /**
     * Obtém todos os preços atuais
     */
    public static Map<TipoSala, Double> getTodosPrecos() {
        return new HashMap<>(precosPorTipo);
    }
    
    /**
     * Obtém o preço formatado para um tipo de sala
     */
    public static String getPrecoFormatado(TipoSala tipoSala) {
        return String.format("%.2f €", getPreco(tipoSala));
    }
    
    /**
     * Obtém todos os preços formatados
     */
    public static String getTodosPrecosFormatados() {
        StringBuilder sb = new StringBuilder();
        sb.append("Preços dos bilhetes por tipo de sala:\n");
        
        for (Map.Entry<TipoSala, Double> entry : precosPorTipo.entrySet()) {
            sb.append(entry.getKey().getDescricao())
              .append(": ")
              .append(String.format("%.2f €", entry.getValue()))
              .append("\n");
        }
        
        return sb.toString();
    }
    
    /**
     * Aplica um desconto percentual a todos os preços
     */
    public static void aplicarDescontoGlobal(double percentagemDesconto) {
        if (percentagemDesconto < 0 || percentagemDesconto > 100) {
            throw new IllegalArgumentException("Percentagem de desconto deve estar entre 0 e 100");
        }
        
        double fatorDesconto = 1 - (percentagemDesconto / 100.0);
        
        for (Map.Entry<TipoSala, Double> entry : precosPorTipo.entrySet()) {
            double novoPreco = entry.getValue() * fatorDesconto;
            precosPorTipo.put(entry.getKey(), novoPreco);
        }
    }
    
    /**
     * Aplica um aumento percentual a todos os preços
     */
    public static void aplicarAumentoGlobal(double percentagemAumento) {
        if (percentagemAumento < 0) {
            throw new IllegalArgumentException("Percentagem de aumento não pode ser negativa");
        }
        
        double fatorAumento = 1 + (percentagemAumento / 100.0);
        
        for (Map.Entry<TipoSala, Double> entry : precosPorTipo.entrySet()) {
            double novoPreco = entry.getValue() * fatorAumento;
            precosPorTipo.put(entry.getKey(), novoPreco);
        }
    }
    
    /**
     * Restaura os preços padrão
     */
    public static void restaurarPrecosPadrao() {
        precosPorTipo.clear();
        precosPorTipo.put(TipoSala.NORMAL, 8.0);
        precosPorTipo.put(TipoSala.VIP, 12.0);
        precosPorTipo.put(TipoSala.TRES_D, 10.0);
        precosPorTipo.put(TipoSala.IMAX, 15.0);
        precosPorTipo.put(TipoSala.QUATRO_DX, 18.0);
        precosPorTipo.put(TipoSala.XVISION, 20.0);
    }
} 