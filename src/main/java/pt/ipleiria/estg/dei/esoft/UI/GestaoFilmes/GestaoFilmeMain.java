package pt.ipleiria.estg.dei.esoft.UI.GestaoFilmes;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Main;

import javax.swing.*;
import java.awt.*;

public class GestaoFilmeMain {
    private JPanel mainPanel;
    private JButton pesquisaDeFilmesButton;
    private JButton registoDeFilmeButton;
    private JButton arquivoButton;
    private JButton logoButton;
    private final Cinema cinema;
    private Runnable onLogoClick;

    public GestaoFilmeMain(Cinema cinema, Runnable onLogoClick) {
        this.cinema = cinema;
        this.onLogoClick = onLogoClick;
        
        pesquisaDeFilmesButton.addActionListener(e -> {
            PesquisaFilme pesquisaFilmeFrame = new PesquisaFilme(cinema);
            pesquisaFilmeFrame.setVisible(true);
        });

        registoDeFilmeButton.addActionListener(e -> Main.mostrarRegistoFilme());

        arquivoButton.addActionListener(e -> {
            ArquivoFilme arquivoFilmeFrame = new ArquivoFilme(cinema);
            arquivoFilmeFrame.setVisible(true);
        });

        logoButton.addActionListener(e -> onLogoClick.run());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
