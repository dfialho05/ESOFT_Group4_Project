package pt.ipleiria.estg.dei.esoft.UI;

import javax.swing.*;
import java.awt.*;

public class GestaoPag {
    private JButton salasButton;
    private JButton filmesButton;
    private JButton sessoesButton;

    private JPanel mainPanel;
    public GestaoPag() {
        salasButton.addActionListener(e -> {
            // Action for salasButton
            System.out.println("Salas button clicked");
        });

        filmesButton.addActionListener(e -> {
            // Action for filmesButton
            System.out.println("Filmes button clicked");
        });

        sessoesButton.addActionListener(e -> {
            // Action for sessoesButton
            System.out.println("Sessoes button clicked");
        });
    }

    public Container getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel();
            mainPanel.setLayout(new GridLayout(3, 1));
            mainPanel.add(salasButton);
            mainPanel.add(filmesButton);
            mainPanel.add(sessoesButton);
        }
        return mainPanel;
    }
}
