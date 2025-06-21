package pt.ipleiria.estg.dei.esoft.UI;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import javax.swing.*;
import java.awt.*;

public class MainPag {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel logoLabel;
    private JButton gestaoButton;
    private JButton vendasButton;
    private JButton consultaButton;
    private final Cinema cinema;
    private final Runnable onGestaoClick;

    public MainPag(Cinema cinema, Runnable onGestaoClick) {
        this.cinema = cinema;
        this.onGestaoClick = onGestaoClick;
        
        // Setup action listeners
        setupActionListeners();
        
        // Setup logo after components are initialized
        SwingUtilities.invokeLater(this::setupLogo);
    }

    private void setupLogo() {
        if (logoLabel == null) {
            return; // Components not yet initialized
        }
        
        try {
            java.net.URL logoUrl = getClass().getResource("/logo.png");
            if (logoUrl != null) {
                ImageIcon logoIcon = new ImageIcon(logoUrl);
                Image image = logoIcon.getImage();
                Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
                logoIcon = new ImageIcon(newimg);
                logoLabel.setIcon(logoIcon);
            } else {
                logoLabel.setText("[LOGO]");
                logoLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
                logoLabel.setForeground(Color.WHITE);
            }
        } catch (Exception e) {
            if (logoLabel != null) {
                logoLabel.setText("[LOGO]");
                logoLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
                logoLabel.setForeground(Color.WHITE);
            }
            e.printStackTrace();
        }
    }

    private void setupActionListeners() {
        if (gestaoButton != null) {
            gestaoButton.addActionListener(e -> onGestaoClick.run());
        }
        
        if (vendasButton != null) {
            vendasButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(mainPanel, "A janela 'Vendas' será aberta aqui.");
            });
        }
        
        if (consultaButton != null) {
            consultaButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(mainPanel, "A janela 'Consulta' será aberta aqui.");
            });
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
} 