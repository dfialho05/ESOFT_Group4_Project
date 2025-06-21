package pt.ipleiria.estg.dei.esoft.UI.GestaoSalas;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Main;

import javax.swing.*;
import java.awt.*;

public class GestaoSalasMain {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JButton logoButton;
    private JButton listaButton;
    private JButton criarButton;
    private JButton arquivoButton;
    private final Cinema cinema;
    private final Runnable onLogoClick;

    public GestaoSalasMain(Cinema cinema, Runnable onLogoClick) {
        this.cinema = cinema;
        this.onLogoClick = onLogoClick;
        
        // Setup action listeners
        setupActionListeners();
        
        // Setup logo after components are initialized
        SwingUtilities.invokeLater(this::setupLogo);
    }

    private void setupLogo() {
        if (logoButton == null) {
            return; // Components not yet initialized
        }
        
        try {
            java.net.URL logoUrl = getClass().getResource("/logo.png");
            if (logoUrl != null) {
                ImageIcon logoIcon = new ImageIcon(logoUrl);
                Image image = logoIcon.getImage();
                Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                logoIcon = new ImageIcon(newimg);
                logoButton.setIcon(logoIcon);
            } else {
                logoButton.setText("Logo");
            }
        } catch (Exception e) {
            if (logoButton != null) {
                logoButton.setText("Logo");
            }
            e.printStackTrace();
        }
    }

    private void setupActionListeners() {
        if (logoButton != null) {
            logoButton.addActionListener(e -> onLogoClick.run());
        }
        if (listaButton != null) {
            listaButton.addActionListener(e -> Main.mostrarListaSalasPag());
        }
        if (criarButton != null) {
            criarButton.addActionListener(e -> Main.mostrarCriarSalaPag());
        }
        if (arquivoButton != null) {
            arquivoButton.addActionListener(e -> Main.mostrarArquivoSalasPag());
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
} 