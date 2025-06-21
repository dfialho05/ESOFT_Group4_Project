package pt.ipleiria.estg.dei.esoft.UI.GestaoSessoes;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import javax.swing.*;
import java.awt.*;
import pt.ipleiria.estg.dei.esoft.Main;

public class GestaoSessaoMain {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JButton logoButton;
    private JButton sessoesAtivasButton;
    private JButton criarSessaoButton;
    private JButton arquivoButton;
    private JButton alugarFilmeButton;
    private final Cinema cinema;
    private final Runnable onBack;

    public GestaoSessaoMain(Cinema cinema, Runnable onBack) {
        this.cinema = cinema;
        this.onBack = onBack;
        
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
            logoButton.addActionListener(e -> onBack.run());
        }
        if (sessoesAtivasButton != null) {
            sessoesAtivasButton.addActionListener(e -> Main.mostrarSessoesAtivas());
        }
        if (criarSessaoButton != null) {
            criarSessaoButton.addActionListener(e -> Main.mostrarCriarSessaoPag());
        }
        if (arquivoButton != null) {
            arquivoButton.addActionListener(e -> Main.mostrarArquivoSessoes());
        }
        if (alugarFilmeButton != null) {
            alugarFilmeButton.addActionListener(e -> Main.mostrarAlugarFilmePag());
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
} 