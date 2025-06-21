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

    public MainPag(Cinema cinema, Runnable onGestaoClick) {
        this.cinema = cinema;
        
        // Initialize UI components first
        createUIComponents();
        
        if (logoLabel != null) {
            logoLabel.setText("");
            try {
                java.net.URL logoUrl = getClass().getResource("/logo.png");
                System.out.println("Tentando carregar o logo de: " + logoUrl); // Debug
                ImageIcon logoIcon = new ImageIcon(logoUrl);
                Image image = logoIcon.getImage();
                Image newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
                logoIcon = new ImageIcon(newimg);
                logoLabel.setIcon(logoIcon);
            } catch (Exception e) {
                logoLabel.setText("Logo não encontrado");
                e.printStackTrace(); // Print detailed error
            }
        }

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

    private void createUIComponents() {
        // Fallback para inicializar o mainPanel se o form designer falhar
        if (mainPanel == null) {
            mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(new Color(0x3B82F6)); // Cor de fundo principal
        }
        
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
