package pt.ipleiria.estg.dei.esoft.Backend;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Relatorios {

    private final List<Sala> salas;
    private final List<Sessao> sessoes;
    private final List<Venda> vendas;
    private final List<ProdutoBar> produtosBar;

    public Relatorios(List<Sala> salas, List<Sessao> sessoes, List<Venda> vendas, List<ProdutoBar> produtosBar) {
        this.salas = salas != null ? salas : new ArrayList<>();
        this.sessoes = sessoes != null ? sessoes : new ArrayList<>();
        this.vendas = vendas != null ? vendas : new ArrayList<>();
        this.produtosBar = produtosBar != null ? produtosBar : new ArrayList<>();
    }

    // Relatório Geral
    public long getTotalSalasAtivas() {
        return salas.stream().filter(Sala::isAtivo).count();
    }

    public long getSessoesProgramadasHoje() {
        LocalDate hoje = LocalDate.now();
        return sessoes.stream().filter(s -> s.estaAtivaNaData(hoje)).count();
    }

    public long getBilhetesVendidosHoje() {
        LocalDate hoje = LocalDate.now();
        return vendas.stream()
                .filter(v -> v.getDataVenda().toLocalDate().equals(hoje))
                .mapToLong(v -> v.getBilhetesVendidos().size())
                .sum();
    }

    public List<String> getAlertasStockBaixo() {
        return produtosBar.stream()
                .filter(ProdutoBar::isStockBaixo)
                .map(p -> p.getNome() + " (Stock: " + p.getStockAtual() + ", Mínimo: " + p.getStockMinimo() + ")")
                .collect(Collectors.toList());
    }

    // Relatório de Faturação
    public double getTotalFaturadoBilhetes(LocalDateTime inicio, LocalDateTime fim) {
        return vendas.stream()
                .filter(v -> !v.getDataVenda().isBefore(inicio) && !v.getDataVenda().isAfter(fim))
                .flatMap(v -> v.getBilhetesVendidos().stream())
                .mapToDouble(Bilhete::getPrecoTotal)
                .sum();
    }

    public double getTotalFaturadoBar(LocalDateTime inicio, LocalDateTime fim) {
        double totalProdutos = vendas.stream()
                .filter(v -> !v.getDataVenda().isBefore(inicio) && !v.getDataVenda().isAfter(fim))
                .flatMap(v -> v.getProdutosVendidos().stream())
                .mapToDouble(ProdutoBar::getPreco)
                .sum();

        double totalMenus = vendas.stream()
                .filter(v -> !v.getDataVenda().isBefore(inicio) && !v.getDataVenda().isAfter(fim))
                .flatMap(v -> v.getMenusVendidos().stream())
                .mapToDouble(Menu::getPrecoTotal)
                .sum();

        return totalProdutos + totalMenus;
    }

    public double getTotalFaturado(LocalDateTime inicio, LocalDateTime fim) {
        return getTotalFaturadoBilhetes(inicio, fim) + getTotalFaturadoBar(inicio, fim);
    }

    // Relatório do Bar
    public List<String> getProdutosMaisVendidos(LocalDateTime inicio, LocalDateTime fim) {
        Map<String, Long> contagem = vendas.stream()
                .filter(v -> !v.getDataVenda().isBefore(inicio) && !v.getDataVenda().isAfter(fim))
                .flatMap(v -> v.getProdutosVendidos().stream())
                .collect(Collectors.groupingBy(ProdutoBar::getNome, Collectors.counting()));

        return getTopN(contagem, 5);
    }

    public List<String> getMenusMaisVendidos(LocalDateTime inicio, LocalDateTime fim) {
        Map<String, Long> contagem = vendas.stream()
                .filter(v -> !v.getDataVenda().isBefore(inicio) && !v.getDataVenda().isAfter(fim))
                .flatMap(v -> v.getMenusVendidos().stream())
                .collect(Collectors.groupingBy(Menu::getNome, Collectors.counting()));

        return getTopN(contagem, 5);
    }

    // Relatório de Sessões
    public List<String> getTiposFilmeMaisVendidos(LocalDateTime inicio, LocalDateTime fim) {
        Map<String, Long> contagem = vendas.stream()
                .filter(v -> !v.getDataVenda().isBefore(inicio) && !v.getDataVenda().isAfter(fim))
                .flatMap(v -> v.getBilhetesVendidos().stream())
                .map(b -> b.getSessao().getFilme().getGenero())
                .collect(Collectors.groupingBy(g -> g, Collectors.counting()));

        return getTopN(contagem, 5);
    }

    public List<String> getPreferenciasPorFaixaEtaria(LocalDateTime inicio, LocalDateTime fim) {
        Map<String, Long> contagem = vendas.stream()
                .filter(v -> !v.getDataVenda().isBefore(inicio) && !v.getDataVenda().isAfter(fim))
                .flatMap(v -> v.getBilhetesVendidos().stream())
                .map(b -> b.getSessao().getFilme().getClassificacaoEtaria())
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        return getTopN(contagem, 5);
    }

    public List<String> getSessoesMaisPopulares(LocalDateTime inicio, LocalDateTime fim) {
        Map<String, Long> contagem = vendas.stream()
                .filter(v -> !v.getDataVenda().isBefore(inicio) && !v.getDataVenda().isAfter(fim))
                .flatMap(v -> v.getBilhetesVendidos().stream())
                .map(b -> b.getSessao().getFilme().getTitulo() + " (" + b.getSessao().getHorario() + ")")
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        return getTopN(contagem, 5);
    }

    // Método auxiliar para obter o top N de um mapa
    private List<String> getTopN(Map<String, Long> map, int n) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(n)
                .map(entry -> entry.getKey() + ": " + entry.getValue() + " vendas")
                .collect(Collectors.toList());
    }
} 