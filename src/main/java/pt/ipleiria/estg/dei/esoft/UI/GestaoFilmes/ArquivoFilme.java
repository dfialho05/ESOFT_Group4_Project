package pt.ipleiria.estg.dei.esoft.UI.GestaoFilmes;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Backend.Filme;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ArquivoFilme {
    private JPanel mainPanel;
    private JPanel moviesPanel;
    private JScrollPane scrollPane;
    private JButton backButton;
    private Cinema cinema;
    private final Runnable onBack;

    public ArquivoFilme(Cinema cinema, Runnable onBack) {
        this.cinema = cinema;
        this.onBack = onBack;
        
        createUIComponents(); // Chamar para construir a UI

        // Setup components after they are initialized
        SwingUtilities.invokeLater(() -> {
            loadInactiveMovies();
            if (backButton != null) {
                backButton.addActionListener(e -> onBack.run());
            }
        });
    }

    private void loadInactiveMovies() {
        if (moviesPanel == null) {
            return;
        }
        
        moviesPanel.removeAll();
        moviesPanel.setLayout(new BoxLayout(moviesPanel, BoxLayout.Y_AXIS));

        List<Filme> allMovies = cinema.getFilmes();

        for (Filme filme : allMovies) {
            if (!filme.isAtivo()) {
                JPanel movieCard = createMovieCard(filme);
                moviesPanel.add(movieCard);
                moviesPanel.add(Box.createVerticalStrut(10)); // Spacer
            }
        }

        moviesPanel.revalidate();
        moviesPanel.repaint();
    }

    private JPanel createMovieCard(Filme filme) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel(filme.getTitulo());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(titleLabel);

        card.add(Box.createVerticalStrut(10));

        // Synopsis
        JTextArea sinopseArea = new JTextArea(filme.getSinopse());
        sinopseArea.setWrapStyleWord(true);
        sinopseArea.setLineWrap(true);
        sinopseArea.setEditable(false);
        sinopseArea.setOpaque(false);
        sinopseArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sinopseArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(sinopseArea);

        card.add(Box.createVerticalStrut(10));

        // Details Panel
        JPanel detailsPanel = new JPanel(new GridLayout(0, 4, 10, 5));
        detailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsPanel.setOpaque(false);
        detailsPanel.add(new JLabel("Gênero:"));
        detailsPanel.add(new JLabel(filme.getGenero()));
        detailsPanel.add(new JLabel("Duração:"));
        detailsPanel.add(new JLabel(filme.getDuracaoFormatada()));
        detailsPanel.add(new JLabel("Classificação:"));
        detailsPanel.add(new JLabel(filme.getClassificacaoEtaria()));
        detailsPanel.add(new JLabel("Ano:"));
        detailsPanel.add(new JLabel(String.valueOf(filme.getAnoLancamento())));
        card.add(detailsPanel);

        card.add(Box.createVerticalGlue());

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setOpaque(false);

        JButton editButton = new JButton("Editar");
        editButton.addActionListener(e -> {
            if (mainPanel != null) {
                EditarFilme editarFilme = new EditarFilme(filme, this::loadInactiveMovies);
                editarFilme.setVisible(true);
            }
        });

        JButton activateButton = new JButton("Ativar");
        activateButton.setBackground(new Color(102, 187, 106)); // Green color
        activateButton.setForeground(Color.WHITE);
        activateButton.addActionListener(e -> {
            if (mainPanel != null) {
                int confirm = JOptionPane.showConfirmDialog(mainPanel, "Tem a certeza que deseja ativar este filme?", "Confirmar Ativação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    filme.ativar();
                    loadInactiveMovies();
                }
            }
        });

        buttonPanel.add(editButton);
        buttonPanel.add(activateButton);
        card.add(buttonPanel);

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));

        return card;
    }

    private void createUIComponents() {
        // Main panel
        mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(new Color(0x2d3c42));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("🗄️ Arquivo de Filmes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Container panel for controls and list
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setOpaque(false);

        // Top controls panel
        JPanel topControls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topControls.setOpaque(false);

        backButton = new JButton("🏠 Voltar");
        styleButton(backButton);
        topControls.add(backButton);
        contentPanel.add(topControls, BorderLayout.NORTH);

        // Movies panel with scroll
        moviesPanel = new JPanel();
        moviesPanel.setOpaque(true);
        moviesPanel.setBackground(new Color(0x2d3c42));
        moviesPanel.setLayout(new BoxLayout(moviesPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(moviesPanel);
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

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
