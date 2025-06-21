package pt.ipleiria.estg.dei.esoft.Backend;

/**
 * Enum para os tipos de sala disponíveis
 */
public enum TipoSala {
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