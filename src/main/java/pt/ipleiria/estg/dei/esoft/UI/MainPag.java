package pt.ipleiria.estg.dei.esoft.UI;

import javax.swing.*;

public class MainPag {
    private JButton gestaoButton;
    private JButton vendasButton;
    private JButton consultaButton;

    public MainPag() {
        gestaoButton.addActionListener(e -> {
            // Action for gestaoButton
            // I want this to go to GestaoPag
            System.out.println("Gestao button clicked");
            GestaoPag gestaoPag = new GestaoPag();
            JFrame frame = new JFrame("Gestao Page");
            frame.setContentPane(gestaoPag.getMainPanel());
        });

        vendasButton.addActionListener(e -> {
            // Action for vendasButton
            System.out.println("Vendas button clicked");
        });

        consultaButton.addActionListener(e -> {
            // Action for consultaButton
            System.out.println("Consulta button clicked");
        });
    }
}
