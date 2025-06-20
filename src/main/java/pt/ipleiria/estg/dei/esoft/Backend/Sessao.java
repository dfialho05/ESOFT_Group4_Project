package pt.ipleiria.estg.dei.esoft.Backend;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe que representa uma sessão de cinema no sistema
 */
public class Sessao {
    
    private int idSessao;
    private Filme filme;
    private Sala sala;
    private LocalDate dataInicio; // data de início da sessão
    private LocalDate dataFim; // data de fim da sessão
    private LocalTime horario; // horário da sessão (ocorre todos os dias)
    private boolean ativo; // se a sessão está ativa
    
    // Matriz de lugares: Map<data, Map<fila_coluna, boolean>>
    // boolean: true = ocupado, false = disponível
    private Map<LocalDate, Map<String, Boolean>> matrizLugares;
    
    // Controlo de limpeza automática
    private LocalDate ultimaDataLimpeza; // última data em que a matriz foi limpa
    
    /**
     * Construtor padrão
     */
    public Sessao() {
        this.ativo = true;
        this.dataInicio = LocalDate.now();
        this.dataFim = LocalDate.now().plusWeeks(1); // por padrão, 1 semana
        this.horario = LocalTime.of(20, 0); // por padrão, 20:00
        this.matrizLugares = new HashMap<>();
        this.ultimaDataLimpeza = LocalDate.now().minusDays(1); // inicializa com ontem
        inicializarMatrizLugares();
    }
    
    /**
     * Construtor com parâmetros básicos
     */
    public Sessao(int idSessao, Filme filme, Sala sala, LocalDate dataInicio, 
                  LocalDate dataFim, LocalTime horario) {
        this.idSessao = idSessao;
        this.filme = filme;
        this.sala = sala;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.horario = horario;
        this.ativo = true;
        this.matrizLugares = new HashMap<>();
        this.ultimaDataLimpeza = LocalDate.now().minusDays(1);
        inicializarMatrizLugares();
    }
    
    /**
     * Construtor completo
     */
    public Sessao(int idSessao, Filme filme, Sala sala, LocalDate dataInicio, 
                  LocalDate dataFim, LocalTime horario, boolean ativo) {
        this.idSessao = idSessao;
        this.filme = filme;
        this.sala = sala;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.horario = horario;
        this.ativo = ativo;
        this.matrizLugares = new HashMap<>();
        this.ultimaDataLimpeza = LocalDate.now().minusDays(1);
        inicializarMatrizLugares();
    }
    
    // Getters e Setters
    
    public int getIdSessao() {
        return idSessao;
    }
    
    public void setIdSessao(int idSessao) {
        this.idSessao = idSessao;
    }
    
    public Filme getFilme() {
        return filme;
    }
    
    public void setFilme(Filme filme) {
        this.filme = filme;
    }
    
    public Sala getSala() {
        return sala;
    }
    
    public void setSala(Sala sala) {
        this.sala = sala;
        inicializarMatrizLugares(); // reinicializa a matriz quando a sala muda
    }
    
    public LocalDate getDataInicio() {
        return dataInicio;
    }
    
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
        inicializarMatrizLugares(); // reinicializa a matriz quando o período muda
    }
    
    public LocalDate getDataFim() {
        return dataFim;
    }
    
    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
        inicializarMatrizLugares(); // reinicializa a matriz quando o período muda
    }
    
    public LocalTime getHorario() {
        return horario;
    }
    
    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    /**
     * Obtém o preço do bilhete baseado no tipo de sala
     */
    public double getPrecoBilhete() {
        if (sala == null) {
            return 0.0;
        }
        return PrecosBilhetes.getPreco(sala.getTipoSala());
    }
    
    /**
     * Obtém o preço do bilhete formatado
     */
    public String getPrecoBilheteFormatado() {
        if (sala == null) {
            return "0.00 €";
        }
        return PrecosBilhetes.getPrecoFormatado(sala.getTipoSala());
    }
    
    // Métodos de gestão da matriz de lugares
    
    /**
     * Inicializa a matriz de lugares para todas as datas da sessão
     */
    private void inicializarMatrizLugares() {
        if (sala == null) return;
        
        matrizLugares.clear();
        LocalDate data = dataInicio;
        
        while (!data.isAfter(dataFim)) {
            Map<String, Boolean> lugaresData = new HashMap<>();
            
            // Cria a matriz baseada na configuração da sala
            String configuracao = sala.getConfiguracao();
            int numFilas = calcularNumFilas(configuracao);
            int numColunas = calcularNumColunas(configuracao);
            
            for (int fila = 1; fila <= numFilas; fila++) {
                for (int coluna = 1; coluna <= numColunas; coluna++) {
                    String chave = fila + "_" + coluna;
                    lugaresData.put(chave, false); // inicialmente todos disponíveis
                }
            }
            
            matrizLugares.put(data, lugaresData);
            data = data.plusDays(1);
        }
    }
    
    /**
     * Verifica se é necessário limpar a matriz de lugares
     * A matriz deve ser limpa automaticamente após cada dia de transmissão
     */
    private void verificarLimpezaAutomatica() {
        LocalDate hoje = LocalDate.now();
        
        // Se a última limpeza foi antes de hoje, limpa as matrizes antigas
        if (ultimaDataLimpeza.isBefore(hoje)) {
            limparMatrizesAntigas();
            ultimaDataLimpeza = hoje;
        }
    }
    
    /**
     * Limpa as matrizes de lugares de datas passadas
     */
    private void limparMatrizesAntigas() {
        LocalDate hoje = LocalDate.now();
        
        // Remove matrizes de datas passadas
        matrizLugares.entrySet().removeIf(entry -> entry.getKey().isBefore(hoje));
        
        // Inicializa matriz para hoje se não existir
        if (!matrizLugares.containsKey(hoje) && estaAtivaNaData(hoje)) {
            inicializarMatrizParaData(hoje);
        }
    }
    
    /**
     * Inicializa a matriz de lugares para uma data específica
     */
    private void inicializarMatrizParaData(LocalDate data) {
        if (sala == null || !estaAtivaNaData(data)) return;
        
        Map<String, Boolean> lugaresData = new HashMap<>();
        String configuracao = sala.getConfiguracao();
        int numFilas = calcularNumFilas(configuracao);
        int numColunas = calcularNumColunas(configuracao);
        
        for (int fila = 1; fila <= numFilas; fila++) {
            for (int coluna = 1; coluna <= numColunas; coluna++) {
                String chave = fila + "_" + coluna;
                lugaresData.put(chave, false); // todos disponíveis
            }
        }
        
        matrizLugares.put(data, lugaresData);
    }
    
    /**
     * Limpa manualmente a matriz de lugares para uma data específica
     */
    public void limparMatrizLugares(LocalDate data) {
        if (estaAtivaNaData(data)) {
            inicializarMatrizParaData(data);
        }
    }
    
    /**
     * Limpa manualmente a matriz de lugares para hoje
     */
    public void limparMatrizLugaresHoje() {
        limparMatrizLugares(LocalDate.now());
    }
    
    /**
     * Limpa manualmente todas as matrizes de lugares
     */
    public void limparTodasMatrizesLugares() {
        matrizLugares.clear();
        inicializarMatrizLugares();
    }
    
    /**
     * Calcula o número de filas baseado na configuração da sala
     */
    private int calcularNumFilas(String configuracao) {
        if (configuracao == null || configuracao.isEmpty()) {
            return 10; // valor padrão
        }
        
        try {
            String[] partes = configuracao.split("x");
            if (partes.length >= 1) {
                return Integer.parseInt(partes[0].trim());
            }
        } catch (NumberFormatException e) {
            // ignora e usa valor padrão
        }
        
        return 10; // valor padrão
    }
    
    /**
     * Calcula o número de colunas baseado na configuração da sala
     */
    private int calcularNumColunas(String configuracao) {
        if (configuracao == null || configuracao.isEmpty()) {
            return 10; // valor padrão
        }
        
        try {
            String[] partes = configuracao.split("x");
            if (partes.length >= 2) {
                return Integer.parseInt(partes[1].trim());
            }
        } catch (NumberFormatException e) {
            // ignora e usa valor padrão
        }
        
        return 10; // valor padrão
    }
    
    /**
     * Verifica se um lugar está disponível numa data específica
     */
    public boolean isLugarDisponivel(LocalDate data, int fila, int coluna) {
        verificarLimpezaAutomatica(); // verifica se precisa limpar
        
        if (!matrizLugares.containsKey(data)) {
            return false; // data não existe na sessão
        }
        
        String chave = fila + "_" + coluna;
        Map<String, Boolean> lugaresData = matrizLugares.get(data);
        
        return lugaresData.containsKey(chave) && !lugaresData.get(chave);
    }
    
    /**
     * Verifica se um lugar está disponível hoje
     */
    public boolean isLugarDisponivelHoje(int fila, int coluna) {
        return isLugarDisponivel(LocalDate.now(), fila, coluna);
    }
    
    /**
     * Reserva um lugar numa data específica
     */
    public boolean reservarLugar(LocalDate data, int fila, int coluna) {
        verificarLimpezaAutomatica(); // verifica se precisa limpar
        
        if (!isLugarDisponivel(data, fila, coluna)) {
            return false; // lugar não disponível
        }
        
        String chave = fila + "_" + coluna;
        Map<String, Boolean> lugaresData = matrizLugares.get(data);
        lugaresData.put(chave, true); // marca como ocupado
        
        return true;
    }
    
    /**
     * Reserva um lugar para hoje
     */
    public boolean reservarLugarHoje(int fila, int coluna) {
        return reservarLugar(LocalDate.now(), fila, coluna);
    }
    
    /**
     * Liberta um lugar numa data específica
     */
    public boolean libertarLugar(LocalDate data, int fila, int coluna) {
        verificarLimpezaAutomatica(); // verifica se precisa limpar
        
        if (!matrizLugares.containsKey(data)) {
            return false; // data não existe na sessão
        }
        
        String chave = fila + "_" + coluna;
        Map<String, Boolean> lugaresData = matrizLugares.get(data);
        
        if (lugaresData.containsKey(chave)) {
            lugaresData.put(chave, false); // marca como disponível
            return true;
        }
        
        return false;
    }
    
    /**
     * Liberta um lugar para hoje
     */
    public boolean libertarLugarHoje(int fila, int coluna) {
        return libertarLugar(LocalDate.now(), fila, coluna);
    }
    
    /**
     * Obtém a matriz de lugares para uma data específica
     */
    public Map<String, Boolean> getMatrizLugares(LocalDate data) {
        verificarLimpezaAutomatica(); // verifica se precisa limpar
        return new HashMap<>(matrizLugares.getOrDefault(data, new HashMap<>()));
    }
    
    /**
     * Obtém a matriz de lugares para hoje
     */
    public Map<String, Boolean> getMatrizLugaresHoje() {
        return getMatrizLugares(LocalDate.now());
    }
    
    /**
     * Obtém a matriz de lugares formatada para uma data específica
     */
    public String getMatrizLugaresFormatada(LocalDate data) {
        verificarLimpezaAutomatica(); // verifica se precisa limpar
        
        if (!matrizLugares.containsKey(data)) {
            return "Data não válida para esta sessão";
        }
        
        Map<String, Boolean> lugaresData = matrizLugares.get(data);
        int numFilas = calcularNumFilas(sala.getConfiguracao());
        int numColunas = calcularNumColunas(sala.getConfiguracao());
        
        StringBuilder sb = new StringBuilder();
        sb.append("Matriz de lugares para ").append(data.toString()).append(":\n");
        sb.append("   ");
        
        // Cabeçalho das colunas
        for (int col = 1; col <= numColunas; col++) {
            sb.append(String.format("%2d ", col));
        }
        sb.append("\n");
        
        // Matriz
        for (int fila = 1; fila <= numFilas; fila++) {
            sb.append(String.format("%2d ", fila)); // número da fila
            
            for (int col = 1; col <= numColunas; col++) {
                String chave = fila + "_" + col;
                boolean ocupado = lugaresData.getOrDefault(chave, false);
                sb.append(ocupado ? " X " : " O "); // X = ocupado, O = disponível
            }
            sb.append("\n");
        }
        
        sb.append("Legenda: O = Disponível, X = Ocupado\n");
        return sb.toString();
    }
    
    /**
     * Obtém a matriz de lugares formatada para hoje
     */
    public String getMatrizLugaresFormatadaHoje() {
        return getMatrizLugaresFormatada(LocalDate.now());
    }
    
    /**
     * Conta lugares disponíveis numa data específica
     */
    public int contarLugaresDisponiveis(LocalDate data) {
        verificarLimpezaAutomatica(); // verifica se precisa limpar
        
        if (!matrizLugares.containsKey(data)) {
            return 0;
        }
        
        Map<String, Boolean> lugaresData = matrizLugares.get(data);
        return (int) lugaresData.values().stream().filter(ocupado -> !ocupado).count();
    }
    
    /**
     * Conta lugares disponíveis hoje
     */
    public int contarLugaresDisponiveisHoje() {
        return contarLugaresDisponiveis(LocalDate.now());
    }
    
    /**
     * Conta lugares ocupados numa data específica
     */
    public int contarLugaresOcupados(LocalDate data) {
        verificarLimpezaAutomatica(); // verifica se precisa limpar
        
        if (!matrizLugares.containsKey(data)) {
            return 0;
        }
        
        Map<String, Boolean> lugaresData = matrizLugares.get(data);
        return (int) lugaresData.values().stream().filter(ocupado -> ocupado).count();
    }
    
    /**
     * Conta lugares ocupados hoje
     */
    public int contarLugaresOcupadosHoje() {
        return contarLugaresOcupados(LocalDate.now());
    }
    
    /**
     * Verifica se há lugares disponíveis numa data específica
     */
    public boolean temLugaresDisponiveis(LocalDate data) {
        return contarLugaresDisponiveis(data) > 0;
    }
    
    /**
     * Verifica se há lugares disponíveis hoje
     */
    public boolean temLugaresDisponiveisHoje() {
        return temLugaresDisponiveis(LocalDate.now());
    }
    
    /**
     * Obtém a percentagem de ocupação numa data específica
     */
    public double getPercentagemOcupacao(LocalDate data) {
        verificarLimpezaAutomatica(); // verifica se precisa limpar
        
        if (!matrizLugares.containsKey(data)) {
            return 0.0;
        }
        
        Map<String, Boolean> lugaresData = matrizLugares.get(data);
        int total = lugaresData.size();
        int ocupados = (int) lugaresData.values().stream().filter(ocupado -> ocupado).count();
        
        return total > 0 ? (double) ocupados / total * 100 : 0.0;
    }
    
    /**
     * Obtém a percentagem de ocupação hoje
     */
    public double getPercentagemOcupacaoHoje() {
        return getPercentagemOcupacao(LocalDate.now());
    }
    
    /**
     * Marca automaticamente todos os lugares como ocupados após a sessão
     * Deve ser chamado após o fim da sessão para simular que todos os lugares foram usados
     */
    public void finalizarSessao(LocalDate data) {
        if (!matrizLugares.containsKey(data)) {
            return;
        }
        
        Map<String, Boolean> lugaresData = matrizLugares.get(data);
        lugaresData.replaceAll((k, v) -> true); // marca todos como ocupados
    }
    
    /**
     * Marca automaticamente todos os lugares como ocupados após a sessão de hoje
     */
    public void finalizarSessaoHoje() {
        finalizarSessao(LocalDate.now());
    }
    
    // Métodos de gestão de horário
    
    /**
     * Define o horário da sessão usando string
     */
    public void setHorario(String horario) {
        try {
            this.horario = LocalTime.parse(horario);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Formato inválido para horário. Use HH:MM");
        }
    }
    
    /**
     * Obtém o horário formatado
     */
    public String getHorarioFormatado() {
        return horario.toString();
    }
    
    /**
     * Obtém o horário formatado com descrição
     */
    public String getHorarioCompletoFormatado() {
        return "Todos os dias às " + horario.toString();
    }
    
    // Métodos de gestão de período
    
    /**
     * Verifica se a sessão está ativa na data especificada
     */
    public boolean estaAtivaNaData(LocalDate data) {
        return ativo && !data.isBefore(dataInicio) && !data.isAfter(dataFim);
    }
    
    /**
     * Verifica se a sessão está ativa hoje
     */
    public boolean estaAtivaHoje() {
        return estaAtivaNaData(LocalDate.now());
    }
    
    /**
     * Obtém o período de atividade formatado
     */
    public String getPeriodoAtividadeFormatado() {
        return dataInicio.toString() + " a " + dataFim.toString();
    }
    
    /**
     * Calcula a duração da sessão em dias
     */
    public long getDuracaoEmDias() {
        return java.time.temporal.ChronoUnit.DAYS.between(dataInicio, dataFim) + 1;
    }
    
    /**
     * Verifica se a sessão ocorre numa data específica
     * Como é diária, ocorre em qualquer data dentro do período ativo
     */
    public boolean ocorreNaData(LocalDate data) {
        return estaAtivaNaData(data);
    }
    
    /**
     * Obtém a próxima data em que a sessão ocorrerá
     * Como é diária, será hoje se estiver ativa, ou amanhã se hoje já passou
     */
    public LocalDate getProximaDataSessao() {
        LocalDate hoje = LocalDate.now();
        
        if (hoje.isAfter(dataFim)) {
            return null; // sessão já terminou
        }
        
        if (hoje.isBefore(dataInicio)) {
            return dataInicio; // sessão ainda não começou
        }
        
        return hoje; // sessão ocorre hoje
    }
    
    /**
     * Obtém todas as datas em que a sessão ocorrerá
     */
    public java.util.List<LocalDate> getTodasDatasSessao() {
        java.util.List<LocalDate> datas = new java.util.ArrayList<>();
        LocalDate data = dataInicio;
        
        while (!data.isAfter(dataFim)) {
            datas.add(data);
            data = data.plusDays(1);
        }
        
        return datas;
    }
    
    /**
     * Calcula quantas sessões ocorrerão no total
     */
    public long getNumeroTotalSessoes() {
        return getDuracaoEmDias();
    }
    
    /**
     * Calcula quantas sessões já ocorreram até hoje
     */
    public long getNumeroSessoesRealizadas() {
        LocalDate hoje = LocalDate.now();
        
        if (hoje.isBefore(dataInicio)) {
            return 0; // ainda não começou
        }
        
        if (hoje.isAfter(dataFim)) {
            return getNumeroTotalSessoes(); // já terminou
        }
        
        return java.time.temporal.ChronoUnit.DAYS.between(dataInicio, hoje) + 1;
    }
    
    /**
     * Calcula quantas sessões ainda faltam
     */
    public long getNumeroSessoesRestantes() {
        return getNumeroTotalSessoes() - getNumeroSessoesRealizadas();
    }
    
    // Métodos de validação
    
    /**
     * Verifica se a sessão é válida
     */
    public boolean isSessaoValida() {
        return filme != null && sala != null && 
               !dataInicio.isAfter(dataFim) && 
               horario != null && 
               filme.isAtivo() && 
               sala.isAtivo();
    }
    
    /**
     * Verifica se há conflito de horário com outra sessão
     */
    public boolean temConflitoHorario(Sessao outraSessao) {
        if (!sala.equals(outraSessao.getSala())) {
            return false; // salas diferentes, não há conflito
        }
        
        // Verifica se os períodos se sobrepõem
        if (dataFim.isBefore(outraSessao.getDataInicio()) || 
            dataInicio.isAfter(outraSessao.getDataFim())) {
            return false; // períodos não se sobrepõem
        }
        
        // Como ambas são diárias, verifica sobreposição de horários
        LocalTime inicio1 = horario;
        LocalTime fim1 = inicio1.plusMinutes(filme.getDuracao());
        LocalTime inicio2 = outraSessao.getHorario();
        LocalTime fim2 = inicio2.plusMinutes(outraSessao.getFilme().getDuracao());
        
        return !(fim1.isBefore(inicio2) || inicio1.isAfter(fim2));
    }
    
    // Métodos de ativação/desativação
    
    /**
     * Ativa a sessão
     */
    public void ativar() {
        this.ativo = true;
    }
    
    /**
     * Desativa a sessão
     */
    public void desativar() {
        this.ativo = false;
    }
    
    @Override
    public String toString() {
        return "Sessao{" +
                "idSessao=" + idSessao +
                ", filme='" + (filme != null ? filme.getTitulo() : "N/A") + '\'' +
                ", sala=" + (sala != null ? sala.getIdSala() : "N/A") +
                ", periodo=" + getPeriodoAtividadeFormatado() +
                ", horario=" + getHorarioCompletoFormatado() +
                ", precoBilhete=" + getPrecoBilheteFormatado() +
                ", ativo=" + ativo +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Sessao sessao = (Sessao) obj;
        return idSessao == sessao.idSessao;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(idSessao);
    }
} 