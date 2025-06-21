package pt.ipleiria.estg.dei.esoft;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Backend.Sala;
import pt.ipleiria.estg.dei.esoft.UI.GestaoPag;
import pt.ipleiria.estg.dei.esoft.UI.GestaoFilmes.GestaoFilmeMain;
import pt.ipleiria.estg.dei.esoft.UI.GestaoFilmes.RegistoFilme;
import pt.ipleiria.estg.dei.esoft.UI.GestaoSalas.ArquivoSalasPag;
import pt.ipleiria.estg.dei.esoft.UI.GestaoSalas.CriarSalaPag;
import pt.ipleiria.estg.dei.esoft.UI.GestaoSalas.EditarSalaPag;
import pt.ipleiria.estg.dei.esoft.UI.GestaoSalas.GestaoSalasMain;
import pt.ipleiria.estg.dei.esoft.UI.GestaoSalas.ListaSalasPag;
import pt.ipleiria.estg.dei.esoft.UI.MainPag;

import javax.swing.*;
import java.util.function.Consumer;

public class Main {

    private static JFrame frame;
    private static Cinema cinema;

    public static void main(String[] args) {
        System.out.println("Iniciando aplicação CinemaLiz...");
        
        try {
            cinema = DadosExemplo.criarCinemaComExemplos();
            System.out.println("Cinema criado com sucesso!");
            System.out.println("Filmes criados: " + cinema.getFilmes().size());
            System.out.println("Salas criadas: " + cinema.getSalas().size());
            System.out.println("Sessões criadas: " + cinema.getSessoes().size());
            System.out.println("Produtos de bar criados: " + cinema.getProdutosBar().size());
            System.out.println("Menus criados: " + cinema.getMenus().size());
            System.out.println("Vendas registadas: " + cinema.getVendas().size());
        } catch (Exception e) {
            System.err.println("Erro ao criar cinema: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        java.awt.EventQueue.invokeLater(() -> {
            try {
                System.out.println("Criando interface gráfica...");
                frame = new JFrame("CinemaLiz");
                mostrarMainPag(); // Mostra a página principal ao iniciar
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                System.out.println("Interface gráfica criada com sucesso!");
            } catch (Exception e) {
                System.err.println("Erro ao criar interface gráfica: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public static void mostrarMainPag() {
        frame.setContentPane(new MainPag(cinema, Main::mostrarGestaoPag).getMainPanel());
        frame.revalidate();
        frame.repaint();
    }

    public static void mostrarGestaoPag() {
        frame.setContentPane(new GestaoPag(cinema, Main::mostrarMainPag).getMainPanel());
        frame.revalidate();
        frame.repaint();
    }

    public static void mostrarGestaoSalasMain() {
        frame.setContentPane(new GestaoSalasMain(cinema, Main::mostrarGestaoPag).getMainPanel());
        frame.revalidate();
        frame.repaint();
    }

    public static void mostrarListaSalasPag() {
        frame.setContentPane(new ListaSalasPag(cinema, Main::mostrarGestaoSalasMain).getMainPanel());
        frame.revalidate();
        frame.repaint();
    }

    public static void mostrarGestaoFilmeMain() {
        frame.setContentPane(new GestaoFilmeMain(cinema, Main::mostrarGestaoPag).getMainPanel());
        frame.revalidate();
        frame.repaint();
    }

    public static void mostrarCriarSalaPag() {
        frame.setContentPane(new CriarSalaPag(cinema, Main::mostrarGestaoSalasMain).getMainPanel());
        frame.revalidate();
        frame.repaint();
    }

    public static void mostrarRegistoFilme() {
        frame.setContentPane(new RegistoFilme(cinema, Main::mostrarGestaoFilmeMain).getMainPanel());
        frame.revalidate();
        frame.repaint();
    }

    public static void mostrarEditarSalaPag(Sala sala) {
        frame.setContentPane(new EditarSalaPag(sala, Main::mostrarListaSalasPag).getMainPanel());
        frame.revalidate();
        frame.repaint();
    }

    public static void mostrarArquivoSalasPag() {
        frame.setContentPane(new ArquivoSalasPag(cinema, Main::mostrarGestaoSalasMain).getMainPanel());
        frame.revalidate();
        frame.repaint();
    }
}