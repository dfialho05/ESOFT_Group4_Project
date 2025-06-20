package pt.ipleiria.estg.dei.esoft.UI;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import javax.swing.*;

public class MainPag {
    private JPanel mainPanel;
    private JButton gestaoButton;
    private JButton vendasButton;
    private JButton consultaButton;
    private final Cinema cinema;

    public MainPag(Cinema cinema) {
        this.cinema = cinema;

        gestaoButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel, "A janela 'Gestão' será aberta aqui.");
        });

        vendasButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel, "A janela 'Vendas' será aberta aqui.");
        });

        consultaButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel, "A janela 'Consulta' será aberta aqui.");
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
