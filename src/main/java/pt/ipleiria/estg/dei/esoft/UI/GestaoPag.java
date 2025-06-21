package pt.ipleiria.estg.dei.esoft.UI;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Main;

import javax.swing.*;
import java.awt.*;

public class GestaoPag {
    private JPanel mainPanel;
    private JPanel contentPanel;
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

        salasButton = createRoundedButton("Salas");
        filmesButton = createRoundedButton("Filmes");
        sessoesButton = createRoundedButton("Sessões");
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
