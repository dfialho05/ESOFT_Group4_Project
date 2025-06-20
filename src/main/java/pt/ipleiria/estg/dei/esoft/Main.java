package pt.ipleiria.estg.dei.esoft;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.UI.GestaoPag;
import pt.ipleiria.estg.dei.esoft.UI.MainPag;

import javax.swing.*;
import java.util.function.Consumer;

public class Main {

    private static JFrame frame;
    private static Cinema cinema;

    public static void main(String[] args) {
        cinema = DadosExemplo.criarCinemaComExemplos();

        java.awt.EventQueue.invokeLater(() -> {
            frame = new JFrame("CinemaLiz");
            mostrarMainPag(); // Mostra a página principal ao iniciar
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
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
}