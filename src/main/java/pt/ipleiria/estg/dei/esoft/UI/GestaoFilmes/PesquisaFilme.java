package pt.ipleiria.estg.dei.esoft.UI.GestaoFilmes;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Backend.Filme;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PesquisaFilme {
    private JPanel mainPanel;
    private JPanel moviesPanel;
    private JScrollPane scrollPane;
    private JButton backButton;
    private JButton filterAZButton;
    private JButton filterZAButton;
    private JButton filterGenreButton;
    private JButton filterClassificationButton;
    private JButton filterYearButton;
    private Cinema cinema;
    private final Runnable onBack;

    public PesquisaFilme(Cinema cinema, Runnable onBack) {
        this.cinema = cinema;
        this.onBack = onBack;
        
        createUIComponents(); // Chamar para construir a UI

        SwingUtilities.invokeLater(() -> {
            loadActiveMovies();
            if (backButton != null) {
                backButton.addActionListener(e -> onBack.run());
            }
            addSortActionListeners();
        });
    }

    private void addSortActionListeners() {
        // Sort by Title A-Z
        if (filterAZButton != null) {
            filterAZButton.addActionListener(e -> {
                List<Filme> filmes = cinema.getFilmes();
                filmes.sort((f1, f2) -> f1.getTitulo().compareToIgnoreCase(f2.getTitulo()));
                displayMovies(filmes);
            });
        }

        // Sort by Title Z-A
        if (filterZAButton != null) {
            filterZAButton.addActionListener(e -> {
                List<Filme> filmes = cinema.getFilmes();
                filmes.sort((f1, f2) -> f2.getTitulo().compareToIgnoreCase(f1.getTitulo()));
                displayMovies(filmes);
            });
        }

        // Sort by Genre
        if (filterGenreButton != null) {
            filterGenreButton.addActionListener(e -> {
                List<Filme> filmes = cinema.getFilmes();
                filmes.sort((f1, f2) -> f1.getGenero().compareToIgnoreCase(f2.getGenero()));
                displayMovies(filmes);
            });
        }

        // Sort by Classification
        if (filterClassificationButton != null) {
            filterClassificationButton.addActionListener(e -> {
                List<Filme> filmes = cinema.getFilmes();
                // This is a simple alphabetical sort. A more complex sort might be needed for age ratings.
                filmes.sort((f1, f2) -> f1.getClassificacaoEtaria().compareToIgnoreCase(f2.getClassificacaoEtaria()));
                displayMovies(filmes);
            });
        }

        // Sort by Year
        if (filterYearButton != null) {
            filterYearButton.addActionListener(e -> {
                List<Filme> filmes = cinema.getFilmes();
                filmes.sort((f1, f2) -> Integer.compare(f2.getAnoLancamento(), f1.getAnoLancamento())); // Most recent first
                displayMovies(filmes);
            });
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void loadActiveMovies() {
        displayMovies(cinema.getFilmes());
    }

    private void displayMovies(List<Filme> filmes) {
        if (moviesPanel == null) {
            return;
        }
        
        moviesPanel.removeAll();
        moviesPanel.setLayout(new BoxLayout(moviesPanel, BoxLayout.Y_AXIS));

        for (Filme filme : filmes) {
            if (filme.isAtivo()) {
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

        // Details in a grid for alignment
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
        detailsPanel.add(new JLabel("Preço Aluguer:"));
        detailsPanel.add(new JLabel("€" + String.format("%.2f", filme.getPrecoAluguer())));

        card.add(detailsPanel);

        // Add a flexible spacer to push buttons to the bottom
        card.add(Box.createVerticalGlue());

        // Buttons at the bottom right
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setOpaque(false);

        JButton editButton = new JButton("Editar");
        editButton.addActionListener(e -> {
            if (mainPanel != null) {
                EditarFilme editarFilme = new EditarFilme(filme, this::loadActiveMovies);
                editarFilme.setVisible(true);
            }
        });

        JButton archiveButton = new JButton("Arquivar");
        archiveButton.addActionListener(e -> {
            if (mainPanel != null) {
                int confirm = JOptionPane.showConfirmDialog(mainPanel, "Tem a certeza que deseja arquivar este filme?", "Confirmar Arquivo", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    filme.desativar();
                    loadActiveMovies();
                }
            }
        });

        buttonPanel.add(editButton);
        buttonPanel.add(archiveButton);
        card.add(buttonPanel);

        // Set a max size to prevent vertical stretching and fit content
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));

        return card;
    }

    private void createUIComponents() {
        // Main panel
        mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(new Color(0x2d3c42));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("🔍 Pesquisa de Filmes", SwingConstants.CENTER);
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
        filterAZButton = new JButton("A-Z ↓");
        styleButton(filterAZButton);
        filterZAButton = new JButton("Z-A ↑");
        styleButton(filterZAButton);
        filterGenreButton = new JButton("🎭 Gênero");
        styleButton(filterGenreButton);
        filterClassificationButton = new JButton("🔞 Classificação");
        styleButton(filterClassificationButton);
        filterYearButton = new JButton("📅 Ano");
        styleButton(filterYearButton);

        topControls.add(backButton);
        topControls.add(filterAZButton);
        topControls.add(filterZAButton);
        topControls.add(filterGenreButton);
        topControls.add(filterClassificationButton);
        topControls.add(filterYearButton);
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
}
