package pt.ipleiria.estg.dei.esoft.UI;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import javax.swing.*;
import java.awt.*;

public class MainPag {
    private JPanel mainPanel;
    private JButton gestaoButton;
    private JButton vendasButton;
    private JButton consultaButton;
    private final Cinema cinema;
    private JPanel contentPanel;
    private JLabel logoLabel;

    public MainPag(Cinema cinema) {
        this.cinema = cinema;
        logoLabel.setText("");
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logo.png"));
            Image image = logoIcon.getImage();
            Image newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
            logoIcon = new ImageIcon(newimg);
            logoLabel.setIcon(logoIcon);
        } catch (Exception e) {
            logoLabel.setText("Logo");
        }

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

        gestaoButton = createRoundedButton("Gestão");
        vendasButton = createRoundedButton("Vendas");
        consultaButton = createRoundedButton("Consulta");
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
        button.setBackground(new Color(0, 95, 127)); // Cor dos botões
        button.setFont(new Font("SansSerif", Font.PLAIN, 24));
        // A borda pode ser removida se o look for melhor sem ela
        button.setBorderPainted(false);
        return button;
    }
}
