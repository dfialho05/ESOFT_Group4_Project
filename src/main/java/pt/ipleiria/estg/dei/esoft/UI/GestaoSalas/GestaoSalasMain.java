package pt.ipleiria.estg.dei.esoft.UI.GestaoSalas;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Main;

import javax.swing.*;
import java.awt.*;

public class GestaoSalasMain {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private JButton listaDeSalasButton;
    private JButton criarSalaButton;
    private JButton arquivoButton;
    private JButton logoButton;
    private final Cinema cinema;

    public GestaoSalasMain(Cinema cinema, Runnable onLogoClick) {
        this.cinema = cinema;
        
        listaDeSalasButton.addActionListener(e -> Main.mostrarListaSalasPag());
        
        arquivoButton.addActionListener(e -> Main.mostrarArquivoSalasPag());
        
        criarSalaButton.addActionListener(e -> Main.mostrarCriarSalaPag());
        
        logoButton.addActionListener(e -> onLogoClick.run());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
