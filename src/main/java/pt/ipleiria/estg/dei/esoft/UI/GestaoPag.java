package pt.ipleiria.estg.dei.esoft.UI;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Main;

import javax.swing.*;
import java.awt.*;

public class GestaoPag {
    private JPanel mainPanel;
    private JButton salasButton;
    private JButton filmesButton;
    private JButton sessoesButton;
    private JButton logoButton;
    private final Cinema cinema;
    private Runnable onLogoClick;

    public GestaoPag(Cinema cinema, Runnable onLogoClick) {
        this.cinema = cinema;
        this.onLogoClick = onLogoClick;

        salasButton.addActionListener(e -> Main.mostrarGestaoSalasMain());
        filmesButton.addActionListener(e -> Main.mostrarGestaoFilmeMain());
        sessoesButton.addActionListener(e -> System.out.println("Sessoes button clicked"));
        logoButton.addActionListener(e -> onLogoClick.run());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
