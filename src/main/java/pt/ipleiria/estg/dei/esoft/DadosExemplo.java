package pt.ipleiria.estg.dei.esoft;

import pt.ipleiria.estg.dei.esoft.Backend.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * Classe utilitária para gerar dados de exemplo para a aplicação.
 * Isto permite testar a UI e os relatórios com um conjunto de dados pré-definido.
 */
public class DadosExemplo {

    /**
     * Cria e retorna uma instância de Cinema populada com dados de exemplo.
     * @return Uma instância de Cinema com dados para demonstração.
     */
    public static Cinema criarCinemaComExemplos() {
        Cinema cinema = new Cinema();

        // 1. Adicionar Salas
        Sala sala1 = new Sala(1, "Sala 1", "Normal", 100, "10x10", true, "");
        Sala sala2 = new Sala(2, "Sala 2", "Normal", 80, "8x10", true, "");
        Sala salaVIP = new Sala(3, "Sala VIP", "VIP", 40, "5x8", true, "Assentos reclináveis");
        cinema.adicionarSala(sala1);
        cinema.adicionarSala(sala2);
        cinema.adicionarSala(salaVIP);

        // 2. Adicionar Filmes
        Filme filmeAcao = new Filme("Velocidade Furiosa X", "Ação", 141, "...", 8.50, 2023, "M/12", true, 1, 30);
        Filme filmeComedia = new Filme("Agentes Secundários", "Comédia", 107, "...", 7.00, 2010, "M/12", true, 1, 30);
        Filme filmeAnimacao = new Filme("Super Mario Bros.", "Animação", 92, "...", 7.50, 2023, "M/6", true, 1, 30);
        cinema.adicionarFilme(filmeAcao);
        cinema.adicionarFilme(filmeComedia);
        cinema.adicionarFilme(filmeAnimacao);

        // 3. Adicionar Sessões
        Sessao sessaoAcao = new Sessao(1, filmeAcao, sala1, LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), LocalTime.of(21, 30));
        Sessao sessaoComedia = new Sessao(2, filmeComedia, sala2, LocalDate.now().minusDays(5), LocalDate.now().plusDays(20), LocalTime.of(19, 0));
        Sessao sessaoAnimacao = new Sessao(3, filmeAnimacao, salaVIP, LocalDate.now(), LocalDate.now().plusDays(30), LocalTime.of(16, 0));
        cinema.adicionarSessao(sessaoAcao);
        cinema.adicionarSessao(sessaoComedia);
        cinema.adicionarSessao(sessaoAnimacao);

        // 4. Adicionar Produtos de Bar
        ProdutoBar pipocas = new ProdutoBar(1, "Pipocas Grandes", "Snack", 4.50, 100, 10);
        ProdutoBar refri = new ProdutoBar(2, "Refrigerante", "Bebida", 2.50, 200, 20);
        ProdutoBar chocolate = new ProdutoBar(3, "Chocolate", "Doce", 1.80, 50, 5); // Stock baixo para teste de alerta
        cinema.adicionarProdutoBar(pipocas);
        cinema.adicionarProdutoBar(refri);
        cinema.adicionarProdutoBar(chocolate);

        // 5. Adicionar Menus
        GestaoMenus gestaoMenus = cinema.getGestaoMenus();
        Menu menuFamilia = gestaoMenus.criarMenu("Menu Família", "2 Pipocas e 2 Refrigerantes");
        gestaoMenus.adicionarProdutoAoMenu(menuFamilia.getIdMenu(), pipocas, 2);
        gestaoMenus.adicionarProdutoAoMenu(menuFamilia.getIdMenu(), refri, 2);

        // 6. Registar Vendas (simulação)
        // Venda de ontem
        Venda venda1 = new Venda(1, LocalDateTime.now().minusDays(1));
        Bilhete b1 = new Bilhete(1, sessaoAcao);
        b1.setPrecoTotal(sessaoAcao.getPrecoBilhete());
        venda1.adicionarBilhete(b1);
        venda1.adicionarProduto(pipocas);
        cinema.registarVenda(venda1);

        // Venda de hoje
        Venda venda2 = new Venda(2, LocalDateTime.now());
        Bilhete b2 = new Bilhete(2, sessaoComedia);
        b2.setPrecoTotal(sessaoComedia.getPrecoBilhete());
        venda2.adicionarBilhete(b2);
        venda2.adicionarMenu(menuFamilia);
        cinema.registarVenda(venda2);
        
        Venda venda3 = new Venda(3, LocalDateTime.now());
        Bilhete b3 = new Bilhete(3, sessaoAnimacao);
        b3.setPrecoTotal(sessaoAnimacao.getPrecoBilhete());
        venda3.adicionarBilhete(b3);
        cinema.registarVenda(venda3);

        return cinema;
    }
}