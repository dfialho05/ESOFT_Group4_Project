package pt.ipleiria.estg.dei.esoft;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.UI.MainPag;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // Cria a instância do cinema com todos os dados de exemplo
        Cinema cinema = DadosExemplo.criarCinemaComExemplos();

        // Inicia a interface gráfica na thread de eventos do Swing
        java.awt.EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("CinemaLiz");
            frame.setContentPane(new MainPag(cinema).getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack(); // Ajusta o tamanho da janela ao conteúdo
            frame.setLocationRelativeTo(null); // Centra a janela
            frame.setVisible(true);
        });
    }
}