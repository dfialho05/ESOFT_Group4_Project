package pt.ipleiria.estg.dei.esoft.Backend;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Classe que representa um filme no sistema
 */
public class Filme {
    
    private String titulo;
    private String genero;
    private int duracao; // em minutos
    private String sinopse;
    private double precoAluguer; // Custo total da licença de exibição
    private int anoLancamento;
    private String classificacaoEtaria;
    private boolean ativo; // true se está em exibição, false caso contrário
    private int diasMinimoAluguer; // período mínimo da licença de exibição em dias
    private int diasMaximoAluguer; // período máximo da licença de exibição em dias
    
    /**
     * Construtor padrão
     */
    public Filme() {
        this.ativo = true; // por padrão, um filme novo está ativo
        this.diasMinimoAluguer = 1; // por padrão, mínimo 1 dia
        this.diasMaximoAluguer = 30; // por padrão, máximo 30 dias
    }
    
    /**
     * Construtor com todos os parâmetros
     */
    public Filme(String titulo, String genero, int duracao, String sinopse, 
                 double precoAluguer, int anoLancamento, String classificacaoEtaria, 
                 boolean ativo, int diasMinimoAluguer, int diasMaximoAluguer) {
        this.titulo = titulo;
        this.genero = genero;
        this.duracao = duracao;
        this.sinopse = sinopse;
        this.precoAluguer = precoAluguer;
        this.anoLancamento = anoLancamento;
        this.classificacaoEtaria = classificacaoEtaria;
        this.ativo = ativo;
        this.diasMinimoAluguer = diasMinimoAluguer;
        this.diasMaximoAluguer = diasMaximoAluguer;
    }
    
    // Getters e Setters
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public int getDuracao() {
        return duracao;
    }
    
    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
    
    public String getSinopse() {
        return sinopse;
    }
    
    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
    
    public double getPrecoAluguer() {
        return precoAluguer;
    }
    
    public void setPrecoAluguer(double precoAluguer) {
        this.precoAluguer = precoAluguer;
    }
    
    public int getAnoLancamento() {
        return anoLancamento;
    }
    
    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }
    
    public String getClassificacaoEtaria() {
        return classificacaoEtaria;
    }
    
    public void setClassificacaoEtaria(String classificacaoEtaria) {
        this.classificacaoEtaria = classificacaoEtaria;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public int getDiasMinimoAluguer() {
        return diasMinimoAluguer;
    }
    
    public void setDiasMinimoAluguer(int diasMinimoAluguer) {
        this.diasMinimoAluguer = diasMinimoAluguer;
    }
    
    public int getDiasMaximoAluguer() {
        return diasMaximoAluguer;
    }
    
    public void setDiasMaximoAluguer(int diasMaximoAluguer) {
        this.diasMaximoAluguer = diasMaximoAluguer;
    }
    
    /**
     * Método para ativar o filme (colocar em exibição)
     */
    public void ativar() {
        this.ativo = true;
    }
    
    /**
     * Método para desativar o filme (remover da exibição)
     */
    public void desativar() {
        this.ativo = false;
    }
    
    /**
     * Método para obter a duração formatada em horas e minutos
     */
    public String getDuracaoFormatada() {
        int horas = duracao / 60;
        int minutos = duracao % 60;
        return String.format("%dh %02dm", horas, minutos);
    }
    
    /**
     * Método para obter o período da licença de exibição formatado
     */
    public String getPeriodoAluguerFormatado() {
        if (diasMinimoAluguer == diasMaximoAluguer) {
            return diasMinimoAluguer + " dia" + (diasMinimoAluguer > 1 ? "s" : "");
        } else {
            return diasMinimoAluguer + " a " + diasMaximoAluguer + " dias";
        }
    }
    
    /**
     * Devolve o custo total da licença de exibição do filme.
     * O parâmetro numeroDias é ignorado, uma vez que o preço é total.
     * @param numeroDias Ignorado.
     * @return O custo total da licença.
     */
    public double calcularPrecoAluguer(int numeroDias) {
        if (numeroDias < diasMinimoAluguer || numeroDias > diasMaximoAluguer) {
            throw new IllegalArgumentException("Número de dias solicitado para a licença está fora do período válido: " +
                                               diasMinimoAluguer + " a " + diasMaximoAluguer);
        }
        return precoAluguer;
    }
    
    /**
     * Método para verificar se um período de licença é válido
     */
    public boolean isPeriodoAluguerValido(int numeroDias) {
        return numeroDias >= diasMinimoAluguer && numeroDias <= diasMaximoAluguer;
    }
    
    /**
     * Método para obter a data de fim da licença baseada na data de início
     */
    public LocalDate calcularDataDevolucao(LocalDate dataAluguer, int numeroDias) {
        if (!isPeriodoAluguerValido(numeroDias)) {
            throw new IllegalArgumentException("Período de licença inválido");
        }
        return dataAluguer.plusDays(numeroDias);
    }
    
    /**
     * Método para calcular o número de dias restantes de uma licença
     */
    public long calcularDiasRestantes(LocalDate dataAluguer, LocalDate dataAtual, int numeroDias) {
        LocalDate dataDevolucao = calcularDataDevolucao(dataAluguer, numeroDias);
        long diasRestantes = ChronoUnit.DAYS.between(dataAtual, dataDevolucao);
        return Math.max(0, diasRestantes); // não retorna valores negativos
    }
    
    /**
     * Método para verificar se uma licença está expirada
     */
    public boolean isAluguerEmAtraso(LocalDate dataAluguer, LocalDate dataAtual, int numeroDias) {
        LocalDate dataDevolucao = calcularDataDevolucao(dataAluguer, numeroDias);
        return dataAtual.isAfter(dataDevolucao);
    }
    
    @Override
    public String toString() {
        return "Filme{" +
                "titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", duracao=" + getDuracaoFormatada() +
                ", sinopse='" + sinopse + '\'' +
                ", precoAluguer=" + precoAluguer +
                ", anoLancamento=" + anoLancamento +
                ", classificacaoEtaria='" + classificacaoEtaria + '\'' +
                ", ativo=" + ativo +
                ", periodoAluguer=" + getPeriodoAluguerFormatado() +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Filme filme = (Filme) obj;
        return titulo != null ? titulo.equals(filme.titulo) : filme.titulo == null;
    }
    
    @Override
    public int hashCode() {
        return titulo != null ? titulo.hashCode() : 0;
    }
} 