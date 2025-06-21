package pt.ipleiria.estg.dei.esoft.UI.GestaoSalas;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;

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
        listaDeSalasButton.addActionListener(e -> {
            // Action for listaDeSalasButton
            System.out.println("Lista de Salas button clicked");
        });

        arquivoButton.addActionListener(e -> {
            // Action for arquivoButton
            System.out.println("Arquivo button clicked");
        });

        criarSalaButton.addActionListener(e -> {
            // Action for criarSalaButton
            System.out.println("Criar Sala button clicked");
        });

        logoButton.addActionListener(e -> onLogoClick.run());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();
            }
        };

        listaDeSalasButton = createRoundedButton("Lista de Salas");
        criarSalaButton = createRoundedButton("Criar Sala");
        arquivoButton = createRoundedButton("Arquivo");
        logoButton = createLogoButton();
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 95, 127));
        button.setFont(new Font("SansSerif", Font.PLAIN, 24));
        button.setBorderPainted(false);
        return button;
    }

    private JButton createLogoButton() {
        JButton button = new JButton();
        try {
            java.net.URL logoUrl = getClass().getResource("/logo.png");
            if (logoUrl != null) {
                ImageIcon logoIcon = new ImageIcon(logoUrl);
                Image image = logoIcon.getImage();
                Image newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(newimg));
            } else {
                button.setText("Logo");
            }
        } catch (Exception e) {
            button.setText("Error");
            e.printStackTrace();
        }
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }
}
