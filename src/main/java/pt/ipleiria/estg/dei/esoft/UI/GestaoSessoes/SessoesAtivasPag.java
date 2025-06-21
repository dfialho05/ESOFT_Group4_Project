package pt.ipleiria.estg.dei.esoft.UI.GestaoSessoes;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Backend.Sessao;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SessoesAtivasPag {
    private JPanel mainPanel;
    private JButton voltarButton;
    private JScrollPane scrollPane;
    private JPanel sessoesPanel;
    private Cinema cinema;
    private Runnable voltarCallback;

    public SessoesAtivasPag(Cinema cinema, Runnable voltarCallback) {
        this.cinema = cinema;
        this.voltarCallback = voltarCallback;
        
        createUIComponents(); // Chamar para construir a UI

        // Setup components after they are initialized
        SwingUtilities.invokeLater(() -> {
            if (voltarButton != null) {
                voltarButton.addActionListener(e -> voltarCallback.run());
            }
            
            if (sessoesPanel != null) {
                sessoesPanel.setLayout(new BoxLayout(sessoesPanel, BoxLayout.Y_AXIS));
            }
            
            loadSessoes();
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void loadSessoes() {
        if (sessoesPanel == null) {
            return;
        }
        
        sessoesPanel.removeAll();
        List<Sessao> sessoes = cinema.getSessoes();

        for (Sessao sessao : sessoes) {
            if (sessao.isAtivo()) {
                sessoesPanel.add(createSessaoCard(sessao));
                sessoesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        sessoesPanel.revalidate();
        sessoesPanel.repaint();
    }

    private JPanel createSessaoCard(Sessao sessao) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JPanel detailsPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        detailsPanel.add(new JLabel("Filme: " + sessao.getFilme().getTitulo()));
        detailsPanel.add(new JLabel("Sala: " + sessao.getSala().getNome()));
        detailsPanel.add(new JLabel("Data Início: " + sessao.getDataInicio().format(dateFormat)));
        detailsPanel.add(new JLabel("Data Fim: " + sessao.getDataFim().format(dateFormat)));

        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timePanel.add(new JLabel("Horário: " + sessao.getHorarioFormatado()));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(detailsPanel, BorderLayout.CENTER);
        topPanel.add(timePanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton desativarButton = new JButton("Desativar");
        desativarButton.addActionListener(e -> {
            if (mainPanel != null) {
                int confirm = JOptionPane.showConfirmDialog(mainPanel, "Tem a certeza que deseja desativar esta sessão?", "Confirmar Desativação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    sessao.setAtivo(false);
                    JOptionPane.showMessageDialog(mainPanel, "Sessão desativada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    loadSessoes();
                }
            }
        });
        buttonPanel.add(desativarButton);

        card.add(topPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.EAST);

        return card;
    }

    private void createUIComponents() {
        // Main panel
        mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(new Color(0x2d3c42));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("🎬 Sessões Ativas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Container panel for controls and list
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setOpaque(false);

        // Top controls panel
        JPanel topControls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topControls.setOpaque(false);

        voltarButton = new JButton("🏠 Voltar");
        styleButton(voltarButton);
        topControls.add(voltarButton);
        contentPanel.add(topControls, BorderLayout.NORTH);

        // Sessions panel with scroll
        sessoesPanel = new JPanel();
        sessoesPanel.setOpaque(true);
        sessoesPanel.setBackground(new Color(0x2d3c42));
        sessoesPanel.setLayout(new BoxLayout(sessoesPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(sessoesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBackground(new Color(0x0091D5));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
    }
} 