package pt.ipleiria.estg.dei.esoft.Backend;

/**
 * Classe que representa uma sala de cinema no sistema
 */
public class Sala {
    
    private int id;
    private String nome;
    private String tipoSala; // "Normal", "VIP", "IMAX", etc.
    private int capacidade;
    private String configuracao; // e.g., "10x10"
    private boolean ativo;
    private String descricao;
    
    public Sala(int id, String nome, String tipoSala, int capacidade, String configuracao, boolean ativo, String descricao) {
        this.id = id;
        this.nome = nome;
        this.tipoSala = tipoSala;
        this.capacidade = capacidade;
        this.configuracao = configuracao;
        this.ativo = ativo;
        this.descricao = descricao;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipoSala() {
        return tipoSala;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public String getConfiguracao() {
        return configuracao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public String getDescricao() {
        return descricao;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipoSala(String tipoSala) {
        this.tipoSala = tipoSala;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public void setConfiguracao(String configuracao) {
        this.configuracao = configuracao;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        return true; // Assuming all types of rooms are accessible
    }
    
    /**
     * Método para obter informações de acessibilidade formatadas
     */
    public String getInfoAcessibilidade() {
        return "Acessível para cadeira de rodas";
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
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipoSala=" + tipoSala +
                ", capacidade=" + capacidade +
                ", configuracao='" + configuracao + '\'' +
                ", ativo=" + ativo +
                ", descricao='" + descricao + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Sala sala = (Sala) obj;
        return id == sala.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    public boolean isAtiva() {
        return ativo;
    }
}