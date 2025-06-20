package pt.ipleiria.estg.dei.esoft.Backend;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um bilhete de cinema no sistema
 * Focada na gestão de vendas e lucros
 */
public class Bilhete {
    
    private int idBilhete;
    private Sessao sessao;
    private List<Lugar> lugares; // lista de lugares reservados
    private LocalDateTime dataVenda;
    private double precoTotal;
    private boolean ativo; // se o bilhete está ativo/valido
    
    /**
     * Classe interna para representar um lugar reservado
     */
    public static class Lugar {
        private int fila;
        private int coluna;
        private LocalDate dataSessao;
        
        public Lugar(int fila, int coluna, LocalDate dataSessao) {
            this.fila = fila;
            this.coluna = coluna;
            this.dataSessao = dataSessao;
        }
        
        // Getters e Setters
        public int getFila() {
            return fila;
        }
        
        public void setFila(int fila) {
            this.fila = fila;
        }
        
        public int getColuna() {
            return coluna;
        }
        
        public void setColuna(int coluna) {
            this.coluna = coluna;
        }
        
        public LocalDate getDataSessao() {
            return dataSessao;
        }
        
        public void setDataSessao(LocalDate dataSessao) {
            this.dataSessao = dataSessao;
        }
        
        @Override
        public String toString() {
            return "Fila " + fila + ", Coluna " + coluna + " (" + dataSessao + ")";
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            
            Lugar lugar = (Lugar) obj;
            return fila == lugar.fila && coluna == lugar.coluna && dataSessao.equals(lugar.dataSessao);
        }
        
        @Override
        public int hashCode() {
            return fila * 31 + coluna * 17 + dataSessao.hashCode();
        }
    }
    
    /**
     * Construtor padrão
     */
    public Bilhete() {
        this.lugares = new ArrayList<>();
        this.dataVenda = LocalDateTime.now();
        this.ativo = true;
    }
    
    /**
     * Construtor com parâmetros básicos
     */
    public Bilhete(int idBilhete, Sessao sessao) {
        this.idBilhete = idBilhete;
        this.sessao = sessao;
        this.lugares = new ArrayList<>();
        this.dataVenda = LocalDateTime.now();
        this.ativo = true;
        this.precoTotal = 0.0; // será calculado quando adicionar lugares
    }
    
    /**
     * Construtor completo
     */
    public Bilhete(int idBilhete, Sessao sessao, List<Lugar> lugares, 
                   LocalDateTime dataVenda, double precoTotal, boolean ativo) {
        this.idBilhete = idBilhete;
        this.sessao = sessao;
        this.lugares = lugares != null ? new ArrayList<>(lugares) : new ArrayList<>();
        this.dataVenda = dataVenda;
        this.precoTotal = precoTotal;
        this.ativo = ativo;
    }
    
    // Getters e Setters
    
    public int getIdBilhete() {
        return idBilhete;
    }
    
    public void setIdBilhete(int idBilhete) {
        this.idBilhete = idBilhete;
    }
    
    public Sessao getSessao() {
        return sessao;
    }
    
    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }
    
    public List<Lugar> getLugares() {
        return new ArrayList<>(lugares); // retorna cópia para evitar modificações externas
    }
    
    public void setLugares(List<Lugar> lugares) {
        this.lugares = lugares != null ? new ArrayList<>(lugares) : new ArrayList<>();
        calcularPrecoTotal();
    }
    
    public LocalDateTime getDataVenda() {
        return dataVenda;
    }
    
    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }
    
    public double getPrecoTotal() {
        return precoTotal;
    }
    
    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    // Métodos de gestão de lugares
    
    /**
     * Adiciona um lugar ao bilhete
     */
    public boolean adicionarLugar(int fila, int coluna, LocalDate dataSessao) {
        // Verifica se o lugar está disponível
        if (!sessao.isLugarDisponivel(dataSessao, fila, coluna)) {
            return false; // lugar não disponível
        }
        
        // Verifica se a data da sessão é válida
        if (!sessao.estaAtivaNaData(dataSessao)) {
            return false; // sessão não ativa nessa data
        }
        
        Lugar novoLugar = new Lugar(fila, coluna, dataSessao);
        
        // Verifica se o lugar já existe no bilhete
        if (lugares.contains(novoLugar)) {
            return false; // lugar já reservado neste bilhete
        }
        
        // Reserva o lugar na sessão
        if (!sessao.reservarLugar(dataSessao, fila, coluna)) {
            return false; // falha ao reservar lugar
        }
        
        // Adiciona o lugar ao bilhete
        lugares.add(novoLugar);
        calcularPrecoTotal();
        
        return true;
    }
    
    /**
     * Adiciona um lugar para hoje
     */
    public boolean adicionarLugarHoje(int fila, int coluna) {
        return adicionarLugar(fila, coluna, LocalDate.now());
    }
    
    /**
     * Remove um lugar do bilhete
     */
    public boolean removerLugar(int fila, int coluna, LocalDate dataSessao) {
        Lugar lugarParaRemover = new Lugar(fila, coluna, dataSessao);
        
        if (lugares.remove(lugarParaRemover)) {
            // Liberta o lugar na sessão
            sessao.libertarLugar(dataSessao, fila, coluna);
            calcularPrecoTotal();
            return true;
        }
        
        return false; // lugar não encontrado no bilhete
    }
    
    /**
     * Remove um lugar para hoje
     */
    public boolean removerLugarHoje(int fila, int coluna) {
        return removerLugar(fila, coluna, LocalDate.now());
    }
    
    /**
     * Verifica se um lugar específico está reservado neste bilhete
     */
    public boolean temLugar(int fila, int coluna, LocalDate dataSessao) {
        Lugar lugar = new Lugar(fila, coluna, dataSessao);
        return lugares.contains(lugar);
    }
    
    /**
     * Verifica se um lugar específico está reservado neste bilhete para hoje
     */
    public boolean temLugarHoje(int fila, int coluna) {
        return temLugar(fila, coluna, LocalDate.now());
    }
    
    /**
     * Obtém o número de lugares reservados
     */
    public int getNumeroLugares() {
        return lugares.size();
    }
    
    /**
     * Verifica se o bilhete tem lugares reservados
     */
    public boolean temLugares() {
        return !lugares.isEmpty();
    }
    
    /**
     * Obtém os lugares formatados
     */
    public String getLugaresFormatados() {
        if (lugares.isEmpty()) {
            return "Nenhum lugar reservado";
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lugares.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(lugares.get(i).toString());
        }
        return sb.toString();
    }
    
    // Métodos de cálculo de preço
    
    /**
     * Calcula o preço total do bilhete baseado no número de lugares e tipo de sala
     */
    private void calcularPrecoTotal() {
        if (sessao == null) {
            this.precoTotal = 0.0;
            return;
        }
        
        // Obtém o preço baseado na sessão
        double precoPorLugar = sessao.getPrecoBilhete();
        this.precoTotal = lugares.size() * precoPorLugar;
    }
    
    /**
     * Obtém o preço por lugar baseado no tipo de sala
     */
    public double getPrecoPorLugar() {
        if (sessao == null) {
            return 0.0;
        }
        return sessao.getPrecoBilhete();
    }
    
    /**
     * Obtém o preço total formatado
     */
    public String getPrecoTotalFormatado() {
        return String.format("%.2f €", precoTotal);
    }
    
    /**
     * Obtém o preço por lugar formatado
     */
    public String getPrecoPorLugarFormatado() {
        return String.format("%.2f €", getPrecoPorLugar());
    }
    
    // Métodos de validação
    
    /**
     * Verifica se o bilhete é válido
     */
    public boolean isBilheteValido() {
        return sessao != null && 
               sessao.isSessaoValida() && 
               ativo && 
               temLugares() &&
               !lugares.isEmpty();
    }
    
    /**
     * Verifica se o bilhete ainda é válido para uma data específica
     */
    public boolean isBilheteValidoParaData(LocalDate data) {
        if (!isBilheteValido()) {
            return false;
        }
        
        // Verifica se pelo menos um lugar é para a data especificada
        return lugares.stream().anyMatch(lugar -> lugar.getDataSessao().equals(data));
    }
    
    /**
     * Verifica se o bilhete ainda é válido para hoje
     */
    public boolean isBilheteValidoHoje() {
        return isBilheteValidoParaData(LocalDate.now());
    }
    
    /**
     * Verifica se o bilhete já expirou (todas as sessões já passaram)
     */
    public boolean isBilheteExpirado() {
        if (!isBilheteValido()) {
            return true;
        }
        
        LocalDate hoje = LocalDate.now();
        return lugares.stream().allMatch(lugar -> lugar.getDataSessao().isBefore(hoje));
    }
    
    // Métodos de cancelamento
    
    /**
     * Cancela o bilhete (liberta todos os lugares)
     */
    public void cancelarBilhete() {
        if (!ativo) {
            return; // já cancelado
        }
        
        // Liberta todos os lugares na sessão
        for (Lugar lugar : lugares) {
            sessao.libertarLugar(lugar.getDataSessao(), lugar.getFila(), lugar.getColuna());
        }
        
        lugares.clear();
        ativo = false;
        precoTotal = 0.0;
    }
    
    /**
     * Ativa o bilhete
     */
    public void ativarBilhete() {
        this.ativo = true;
    }
    
    /**
     * Desativa o bilhete
     */
    public void desativarBilhete() {
        this.ativo = false;
    }
    
    // Métodos de informação para gestão de vendas
    
    /**
     * Obtém informações do filme
     */
    public String getInfoFilme() {
        if (sessao == null || sessao.getFilme() == null) {
            return "N/A";
        }
        return sessao.getFilme().getTitulo();
    }
    
    /**
     * Obtém informações da sala
     */
    public String getInfoSala() {
        if (sessao == null || sessao.getSala() == null) {
            return "N/A";
        }
        return "Sala " + sessao.getSala().getId() + " (" + sessao.getSala().getTipoSala() + ")";
    }
    
    /**
     * Obtém informações da sessão
     */
    public String getInfoSessao() {
        if (sessao == null) {
            return "N/A";
        }
        return sessao.getHorarioCompletoFormatado() + " - " + sessao.getPeriodoAtividadeFormatado();
    }
    
    /**
     * Obtém informações completas do bilhete para gestão
     */
    public String getInfoCompleta() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bilhete #").append(idBilhete).append("\n");
        sb.append("Filme: ").append(getInfoFilme()).append("\n");
        sb.append("Sala: ").append(getInfoSala()).append("\n");
        sb.append("Sessão: ").append(getInfoSessao()).append("\n");
        sb.append("Lugares: ").append(getLugaresFormatados()).append("\n");
        sb.append("Preço por lugar: ").append(getPrecoPorLugarFormatado()).append("\n");
        sb.append("Preço total: ").append(getPrecoTotalFormatado()).append("\n");
        sb.append("Status: ").append(ativo ? "Ativo" : "Cancelado").append("\n");
        sb.append("Data de venda: ").append(dataVenda.toString());
        
        return sb.toString();
    }
    
    /**
     * Obtém informações resumidas para relatórios de vendas
     */
    public String getInfoResumida() {
        return String.format("Bilhete #%d | %s | %s | %d lugares | %.2f€ | %s", 
                           idBilhete, 
                           getInfoFilme(), 
                           getInfoSala(), 
                           getNumeroLugares(), 
                           precoTotal,
                           ativo ? "Ativo" : "Cancelado");
    }
    
    @Override
    public String toString() {
        return "Bilhete{" +
                "idBilhete=" + idBilhete +
                ", filme='" + getInfoFilme() + '\'' +
                ", sala=" + getInfoSala() +
                ", lugares=" + getLugaresFormatados() +
                ", precoTotal=" + getPrecoTotalFormatado() +
                ", ativo=" + ativo +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Bilhete bilhete = (Bilhete) obj;
        return idBilhete == bilhete.idBilhete;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(idBilhete);
    }
} 