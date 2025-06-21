package pt.ipleiria.estg.dei.esoft.UI;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import javax.swing.*;
import java.awt.*;

public class GestaoPag {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JButton logoButton;
    private JButton salasButton;
    private JButton filmesButton;
    private JButton sessoesButton;
    private final Cinema cinema;
    private final Runnable onSalasClick;
    private final Runnable onFilmesClick;
    private final Runnable onSessoesClick;
    private final Runnable onVoltarClick;

    public GestaoPag(Cinema cinema, Runnable onSalasClick, Runnable onFilmesClick, 
                     Runnable onSessoesClick, Runnable onVoltarClick) {
        this.cinema = cinema;
        this.onSalasClick = onSalasClick;
        this.onFilmesClick = onFilmesClick;
        this.onSessoesClick = onSessoesClick;
        this.onVoltarClick = onVoltarClick;
        
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
            logoButton.addActionListener(e -> onVoltarClick.run());
        }
        if (salasButton != null) {
            salasButton.addActionListener(e -> onSalasClick.run());
        }
        if (filmesButton != null) {
            filmesButton.addActionListener(e -> onFilmesClick.run());
        }
        if (sessoesButton != null) {
            sessoesButton.addActionListener(e -> onSessoesClick.run());
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
} 