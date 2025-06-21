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
        System.out.println("Criando cinema com dados de exemplo...");
        Cinema cinema = new Cinema();

        // 1. Adicionar Salas (incluindo algumas desativadas)
        System.out.println("Criando salas...");
        Sala sala1 = new Sala(1, "Sala 1", "Normal", 100, "10x10", true, "");
        Sala sala2 = new Sala(2, "Sala 2", "Normal", 80, "8x10", true, "");
        Sala salaVIP = new Sala(3, "Sala VIP", "VIP", 40, "5x8", true, "Assentos reclináveis");
        Sala sala3D = new Sala(4, "Sala 3D", "3D", 120, "12x10", true, "Projetor 3D, óculos incluídos");
        Sala sala4DX = new Sala(5, "Sala 4DX", "4DX", 60, "6x10", true, "Assentos móveis, efeitos especiais");
        Sala salaAntiga = new Sala(6, "Sala Antiga", "Normal", 50, "5x10", false, "Em manutenção");
        Sala salaPremium = new Sala(7, "Sala Premium", "Premium", 30, "3x10", true, "Assentos de couro, serviço de bar");
        Sala salaInfantil = new Sala(8, "Sala Infantil", "Infantil", 70, "7x10", false, "Fechada temporariamente");
        
        cinema.adicionarSala(sala1);
        cinema.adicionarSala(sala2);
        cinema.adicionarSala(salaVIP);
        cinema.adicionarSala(sala3D);
        cinema.adicionarSala(sala4DX);
        cinema.adicionarSala(salaAntiga);
        cinema.adicionarSala(salaPremium);
        cinema.adicionarSala(salaInfantil);
        System.out.println("8 salas criadas e adicionadas ao cinema");

        // 2. Adicionar Filmes (mais variedade)
        System.out.println("Criando filmes...");
        Filme filmeAcao = new Filme("Velocidade Furiosa X", "Ação", 141, "Dom Toretto e sua família enfrentam o vilão mais letal que já encontraram.", 8.50, 2023, "M/12", true, 1, 30);
        Filme filmeComedia = new Filme("Agentes Secundários", "Comédia", 107, "Uma comédia hilariante sobre agentes secretos incompetentes.", 7.00, 2010, "M/12", true, 1, 30);
        Filme filmeAnimacao = new Filme("Super Mario Bros.", "Animação", 92, "A aventura épica do Mario e Luigi no mundo real.", 7.50, 2023, "M/6", true, 1, 30);
        Filme filmeTerror = new Filme("A Última Noite", "Terror", 95, "Um grupo de amigos enfrenta uma entidade sobrenatural.", 8.00, 2023, "M/16", true, 1, 30);
        Filme filmeDrama = new Filme("O Último Adeus", "Drama", 128, "Uma história emocionante sobre amor e perda.", 7.80, 2023, "M/12", true, 1, 30);
        Filme filmeFiccao = new Filme("Galáxia Perdida", "Ficção Científica", 156, "Uma missão espacial para salvar a humanidade.", 9.00, 2023, "M/12", true, 1, 30);
        Filme filmeRomance = new Filme("Amor Eterno", "Romance", 112, "Uma história de amor que atravessa décadas.", 6.50, 2023, "M/12", true, 1, 30);
        Filme filmeDocumentario = new Filme("Planeta Terra", "Documentário", 89, "Uma jornada pelos ecossistemas mais incríveis do mundo.", 6.00, 2023, "M/6", true, 1, 30);
        Filme filmeAntigo = new Filme("Casablanca", "Drama", 102, "Um clássico do cinema americano.", 5.00, 1942, "M/12", false, 1, 30);
        Filme filmeInfantil = new Filme("Aventuras na Floresta", "Animação", 85, "Uma aventura mágica para toda a família.", 6.50, 2023, "M/6", true, 1, 30);
        
        cinema.adicionarFilme(filmeAcao);
        cinema.adicionarFilme(filmeComedia);
        cinema.adicionarFilme(filmeAnimacao);
        cinema.adicionarFilme(filmeTerror);
        cinema.adicionarFilme(filmeDrama);
        cinema.adicionarFilme(filmeFiccao);
        cinema.adicionarFilme(filmeRomance);
        cinema.adicionarFilme(filmeDocumentario);
        cinema.adicionarFilme(filmeAntigo);
        cinema.adicionarFilme(filmeInfantil);
        System.out.println("10 filmes criados e adicionados ao cinema");

        // 3. Adicionar Sessões (mais variedade de horários e salas)
        System.out.println("Criando sessões...");
        Sessao sessaoAcao = new Sessao(1, filmeAcao, sala1, LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), LocalTime.of(21, 30));
        Sessao sessaoComedia = new Sessao(2, filmeComedia, sala2, LocalDate.now().minusDays(5), LocalDate.now().plusDays(20), LocalTime.of(19, 0));
        Sessao sessaoAnimacao = new Sessao(3, filmeAnimacao, salaVIP, LocalDate.now(), LocalDate.now().plusDays(30), LocalTime.of(16, 0));
        Sessao sessaoTerror = new Sessao(4, filmeTerror, sala3D, LocalDate.now().minusDays(3), LocalDate.now().plusDays(15), LocalTime.of(22, 0));
        Sessao sessaoDrama = new Sessao(5, filmeDrama, sala2, LocalDate.now().minusDays(2), LocalDate.now().plusDays(25), LocalTime.of(20, 30));
        Sessao sessaoFiccao = new Sessao(6, filmeFiccao, sala4DX, LocalDate.now().minusDays(1), LocalDate.now().plusDays(35), LocalTime.of(21, 0));
        Sessao sessaoRomance = new Sessao(7, filmeRomance, salaPremium, LocalDate.now(), LocalDate.now().plusDays(40), LocalTime.of(18, 30));
        Sessao sessaoDocumentario = new Sessao(8, filmeDocumentario, sala1, LocalDate.now().minusDays(7), LocalDate.now().plusDays(5), LocalTime.of(14, 0));
        Sessao sessaoInfantil = new Sessao(9, filmeInfantil, salaVIP, LocalDate.now().minusDays(1), LocalDate.now().plusDays(45), LocalTime.of(11, 0));
        Sessao sessaoAcao2 = new Sessao(10, filmeAcao, sala3D, LocalDate.now().minusDays(5), LocalDate.now().plusDays(12), LocalTime.of(23, 0));
        
        cinema.adicionarSessao(sessaoAcao);
        cinema.adicionarSessao(sessaoComedia);
        cinema.adicionarSessao(sessaoAnimacao);
        cinema.adicionarSessao(sessaoTerror);
        cinema.adicionarSessao(sessaoDrama);
        cinema.adicionarSessao(sessaoFiccao);
        cinema.adicionarSessao(sessaoRomance);
        cinema.adicionarSessao(sessaoDocumentario);
        cinema.adicionarSessao(sessaoInfantil);
        cinema.adicionarSessao(sessaoAcao2);
        System.out.println("10 sessões criadas e adicionadas ao cinema");

        // 4. Adicionar Produtos de Bar (mais variedade)
        System.out.println("Criando produtos de bar...");
        ProdutoBar pipocas = new ProdutoBar(1, "Pipocas Grandes", "Snack", 4.50, 100, 10);
        ProdutoBar refri = new ProdutoBar(2, "Refrigerante", "Bebida", 2.50, 200, 20);
        ProdutoBar chocolate = new ProdutoBar(3, "Chocolate", "Doce", 1.80, 50, 5); // Stock baixo para teste de alerta
        ProdutoBar pipocasPequenas = new ProdutoBar(4, "Pipocas Pequenas", "Snack", 3.00, 80, 8);
        ProdutoBar agua = new ProdutoBar(5, "Água", "Bebida", 1.50, 150, 15);
        ProdutoBar batatas = new ProdutoBar(6, "Batatas Fritas", "Snack", 3.50, 60, 6);
        ProdutoBar cafe = new ProdutoBar(7, "Café", "Bebida", 2.00, 40, 4);
        ProdutoBar gelado = new ProdutoBar(8, "Gelado", "Doce", 2.80, 30, 3);
        ProdutoBar sumo = new ProdutoBar(9, "Sumo Natural", "Bebida", 3.20, 45, 5);
        ProdutoBar nachos = new ProdutoBar(10, "Nachos", "Snack", 5.00, 25, 2); // Stock muito baixo
        
        cinema.adicionarProdutoBar(pipocas);
        cinema.adicionarProdutoBar(refri);
        cinema.adicionarProdutoBar(chocolate);
        cinema.adicionarProdutoBar(pipocasPequenas);
        cinema.adicionarProdutoBar(agua);
        cinema.adicionarProdutoBar(batatas);
        cinema.adicionarProdutoBar(cafe);
        cinema.adicionarProdutoBar(gelado);
        cinema.adicionarProdutoBar(sumo);
        cinema.adicionarProdutoBar(nachos);
        System.out.println("10 produtos de bar criados e adicionados ao cinema");

        // 5. Adicionar Menus (mais variedade)
        System.out.println("Criando menus...");
        GestaoMenus gestaoMenus = cinema.getGestaoMenus();
        
        Menu menuFamilia = gestaoMenus.criarMenu("Menu Família", "2 Pipocas e 2 Refrigerantes");
        gestaoMenus.adicionarProdutoAoMenu(menuFamilia.getIdMenu(), pipocas, 2);
        gestaoMenus.adicionarProdutoAoMenu(menuFamilia.getIdMenu(), refri, 2);
        cinema.adicionarMenu(menuFamilia);
        
        Menu menuCasal = gestaoMenus.criarMenu("Menu Casal", "2 Pipocas, 2 Bebidas e 1 Chocolate");
        gestaoMenus.adicionarProdutoAoMenu(menuCasal.getIdMenu(), pipocas, 2);
        gestaoMenus.adicionarProdutoAoMenu(menuCasal.getIdMenu(), refri, 1);
        gestaoMenus.adicionarProdutoAoMenu(menuCasal.getIdMenu(), agua, 1);
        gestaoMenus.adicionarProdutoAoMenu(menuCasal.getIdMenu(), chocolate, 1);
        cinema.adicionarMenu(menuCasal);
        
        Menu menuInfantil = gestaoMenus.criarMenu("Menu Infantil", "1 Pipoca Pequena, 1 Sumo e 1 Gelado");
        gestaoMenus.adicionarProdutoAoMenu(menuInfantil.getIdMenu(), pipocasPequenas, 1);
        gestaoMenus.adicionarProdutoAoMenu(menuInfantil.getIdMenu(), sumo, 1);
        gestaoMenus.adicionarProdutoAoMenu(menuInfantil.getIdMenu(), gelado, 1);
        cinema.adicionarMenu(menuInfantil);
        
        Menu menuPremium = gestaoMenus.criarMenu("Menu Premium", "1 Pipoca Grande, 1 Bebida Premium e 1 Doce");
        gestaoMenus.adicionarProdutoAoMenu(menuPremium.getIdMenu(), pipocas, 1);
        gestaoMenus.adicionarProdutoAoMenu(menuPremium.getIdMenu(), cafe, 1);
        gestaoMenus.adicionarProdutoAoMenu(menuPremium.getIdMenu(), chocolate, 1);
        cinema.adicionarMenu(menuPremium);
        System.out.println("4 menus criados e adicionados ao cinema");

        // 6. Registar Vendas (simulação mais completa)
        System.out.println("Criando vendas...");
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
        
        // Mais vendas para demonstrar relatórios
        Venda venda4 = new Venda(4, LocalDateTime.now().minusHours(2));
        Bilhete b4 = new Bilhete(4, sessaoTerror);
        b4.setPrecoTotal(sessaoTerror.getPrecoBilhete());
        venda4.adicionarBilhete(b4);
        venda4.adicionarMenu(menuCasal);
        cinema.registarVenda(venda4);
        
        Venda venda5 = new Venda(5, LocalDateTime.now().minusHours(1));
        Bilhete b5 = new Bilhete(5, sessaoFiccao);
        b5.setPrecoTotal(sessaoFiccao.getPrecoBilhete());
        venda5.adicionarBilhete(b5);
        venda5.adicionarProduto(nachos);
        venda5.adicionarProduto(refri);
        cinema.registarVenda(venda5);
        
        Venda venda6 = new Venda(6, LocalDateTime.now().minusDays(2));
        Bilhete b6 = new Bilhete(6, sessaoDrama);
        b6.setPrecoTotal(sessaoDrama.getPrecoBilhete());
        venda6.adicionarBilhete(b6);
        venda6.adicionarMenu(menuPremium);
        cinema.registarVenda(venda6);
        System.out.println("6 vendas criadas e registadas no cinema");

        System.out.println("Cinema criado com sucesso com todos os dados de exemplo!");
        return cinema;
    }
}