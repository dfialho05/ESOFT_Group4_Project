package pt.ipleiria.estg.dei.esoft.Backend;

/**
 * Enum para os tipos de sala disponíveis
 */
enum TipoSala {
    VIP("VIP"),
    NORMAL("Normal"),
    TRES_D("3D"),
    IMAX("IMAX"),
    QUATRO_DX("4DX"),
    XVISION("XVision");
    
    private final String descricao;
    
    TipoSala(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
}

/**
 * Classe que representa uma sala de cinema no sistema
 */
public class Sala {
    
    private int idSala;
    private int capacidade;
    private String configuracao; // descrição da configuração dos assentos
    private boolean acessibilidade; // true se tem acesso para cadeira de rodas
    private TipoSala tipoSala;
    private boolean ativo; // true se a sala está ativa, false se inativa
    
    /**
     * Construtor padrão
     */
    public Sala() {
        this.ativo = true; // por padrão, uma sala nova está ativa
        this.tipoSala = TipoSala.NORMAL; // por padrão, sala normal
    }
    
    /**
     * Construtor com todos os parâmetros
     */
    public Sala(int idSala, int capacidade, String configuracao, 
                boolean acessibilidade, TipoSala tipoSala, boolean ativo) {
        this.idSala = idSala;
        this.capacidade = capacidade;
        this.configuracao = configuracao;
        this.acessibilidade = acessibilidade;
        this.tipoSala = tipoSala;
        this.ativo = ativo;
    }
    
    // Getters e Setters
    
    public int getIdSala() {
        return idSala;
    }
    
    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }
    
    public int getCapacidade() {
        return capacidade;
    }
    
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
    
    public String getConfiguracao() {
        return configuracao;
    }
    
    public void setConfiguracao(String configuracao) {
        this.configuracao = configuracao;
    }
    
    public boolean isAcessibilidade() {
        return acessibilidade;
    }
    
    public void setAcessibilidade(boolean acessibilidade) {
        this.acessibilidade = acessibilidade;
    }
    
    public TipoSala getTipoSala() {
        return tipoSala;
    }
    
    public void setTipoSala(TipoSala tipoSala) {
        this.tipoSala = tipoSala;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    /**
     * Método para ativar a sala
     */
    public void ativar() {
        this.ativo = true;
    }
    
    /**
     * Método para desativar a sala
     */
    public void desativar() {
        this.ativo = false;
    }
    
    /**
     * Método para verificar se a sala tem acessibilidade para cadeira de rodas
     */
    public boolean temAcessoCadeiraRodas() {
        return acessibilidade;
    }
    
    /**
     * Método para obter informações de acessibilidade formatadas
     */
    public String getInfoAcessibilidade() {
        return acessibilidade ? "Acessível para cadeira de rodas" : "Não acessível para cadeira de rodas";
    }
    
    /**
     * Método para obter o status da sala formatado
     */
    public String getStatusSala() {
        return ativo ? "Ativa" : "Inativa";
    }
    
    /**
     * Método para verificar se a sala está disponível para uso
     */
    public boolean estaDisponivel() {
        return ativo;
    }
    
    @Override
    public String toString() {
        return "Sala{" +
                "idSala=" + idSala +
                ", capacidade=" + capacidade +
                ", configuracao='" + configuracao + '\'' +
                ", acessibilidade=" + getInfoAcessibilidade() +
                ", tipoSala=" + tipoSala.getDescricao() +
                ", ativo=" + getStatusSala() +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Sala sala = (Sala) obj;
        return idSala == sala.idSala;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(idSala);
    }
} 