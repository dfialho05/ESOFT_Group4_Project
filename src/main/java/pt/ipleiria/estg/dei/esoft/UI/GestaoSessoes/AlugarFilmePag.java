package pt.ipleiria.estg.dei.esoft.UI.GestaoSessoes;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Backend.Filme;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class AlugarFilmePag {
    private JPanel mainPanel;
    private JList<Filme> filmesList;
    private JTextField dataInicioField;
    private JTextField dataFimField;
    private JLabel duracaoLabel;
    private JLabel precoLabel;
    private JButton alugarFilmeButton;
    private JButton voltarButton;
    private JScrollPane filmesScrollPane;
    private final Cinema cinema;
    private final Runnable onBack;

    public AlugarFilmePag(Cinema cinema, Runnable onBack) {
        this.cinema = cinema;
        this.onBack = onBack;

        createUIComponents(); // Chamar para construir a UI

        // Setup components after they are initialized
        SwingUtilities.invokeLater(() -> {
            if (filmesList != null) {
                DefaultListModel<Filme> model = new DefaultListModel<>();
                List<Filme> filmesAtivos = cinema.getFilmes().stream().filter(Filme::isAtivo).collect(Collectors.toList());
                for (Filme filme : filmesAtivos) {
                    model.addElement(filme);
                }
                filmesList.setModel(model);
                filmesList.setCellRenderer(new FilmeListRenderer());
                filmesList.addListSelectionListener(e -> updateCalculos());
            }

            if (voltarButton != null) {
                voltarButton.addActionListener(e -> onBack.run());
            }
            if (alugarFilmeButton != null) {
                alugarFilmeButton.addActionListener(e -> alugarFilmes());
            }
            
            DocumentListener dateListener = new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateCalculos();
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateCalculos();
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateCalculos();
                }
            };

            if (dataInicioField != null) {
                dataInicioField.getDocument().addDocumentListener(dateListener);
            }
            if (dataFimField != null) {
                dataFimField.getDocument().addDocumentListener(dateListener);
            }
            
            updateCalculos();
        });
    }
    
    private void updateCalculos() {
        // Update price
        if (precoLabel != null) {
            double precoTotal = 0.0;
            if (filmesList != null) {
                List<Filme> selectedFilmes = filmesList.getSelectedValuesList();
                for (Filme filme : selectedFilmes) {
                    precoTotal += filme.getPrecoAluguer();
                }
            }
            precoLabel.setText(String.format("%.2f€", precoTotal));
        }
        
        // Update duration
        if (duracaoLabel != null && dataInicioField != null && dataFimField != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            try {
                LocalDate dataInicio = LocalDate.parse(dataInicioField.getText(), formatter);
                LocalDate dataFim = LocalDate.parse(dataFimField.getText(), formatter);
                if (!dataFim.isBefore(dataInicio)) {
                    long dias = ChronoUnit.DAYS.between(dataInicio, dataFim);
                    duracaoLabel.setText(dias + " dias");
                } else {
                    duracaoLabel.setText("Data inválida");
                }
            } catch (DateTimeParseException e) {
                duracaoLabel.setText("--");
            }
        }
    }

    private void alugarFilmes() {
        if (filmesList == null || dataInicioField == null || dataFimField == null) {
            return;
        }
        
        List<Filme> selectedFilmes = filmesList.getSelectedValuesList();
        if (selectedFilmes.isEmpty()) {
            if (mainPanel != null) {
                JOptionPane.showMessageDialog(mainPanel, "Por favor, selecione pelo menos um filme.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataInicio, dataFim;
        try {
            dataInicio = LocalDate.parse(dataInicioField.getText(), formatter);
            dataFim = LocalDate.parse(dataFimField.getText(), formatter);
        } catch (DateTimeParseException e) {
            if (mainPanel != null) {
                JOptionPane.showMessageDialog(mainPanel, "Formato de data inválido. Use dd-mm-yyyy.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        if (dataFim.isBefore(dataInicio)) {
            if (mainPanel != null) {
                JOptionPane.showMessageDialog(mainPanel, "A data de fim não pode ser anterior à data de início.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }
        
        String nomesFilmes = selectedFilmes.stream().map(Filme::getTitulo).collect(Collectors.joining(", "));
        String mensagem = "Os seguintes filmes foram alugados com sucesso:\n" + nomesFilmes +
                          "\nPeríodo: " + dataInicio.format(formatter) + " a " + dataFim.format(formatter) +
                          "\nPreço Total: " + (precoLabel != null ? precoLabel.getText() : "0.00€");
                          
        if (mainPanel != null) {
            JOptionPane.showMessageDialog(mainPanel, mensagem, "Aluguer Bem-Sucedido", JOptionPane.INFORMATION_MESSAGE);
        }
        
        onBack.run();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
    
    private static class FilmeListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Filme) {
                Filme filme = (Filme) value;
                setText(filme.getTitulo() + " (" + String.format("%.2f€", filme.getPrecoAluguer()) + ")");
            }
            return this;
        }
    }

    private void createUIComponents() {
        // Main panel
        mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(new Color(0x2d3c42));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("🎥 Aluguer de Filmes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setOpaque(false);

        // Left panel - Film selection
        JPanel leftPanel = new JPanel(new BorderLayout(0, 10));
        leftPanel.setOpaque(false);

        JLabel filmesLabel = new JLabel("Filmes Disponíveis:");
        filmesLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        filmesLabel.setForeground(Color.WHITE);
        leftPanel.add(filmesLabel, BorderLayout.NORTH);

        filmesList = new JList<>();
        filmesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        filmesScrollPane = new JScrollPane(filmesList);
        filmesScrollPane.setPreferredSize(new Dimension(300, 400));
        leftPanel.add(filmesScrollPane, BorderLayout.CENTER);

        contentPanel.add(leftPanel, BorderLayout.WEST);

        // Right panel - Details and form
        JPanel rightPanel = new JPanel(new BorderLayout(0, 15));
        rightPanel.setOpaque(false);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Data início
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createLabel("Data Início (dd-mm-yyyy):"), gbc);
        dataInicioField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(dataInicioField, gbc);

        // Data fim
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(createLabel("Data Fim (dd-mm-yyyy):"), gbc);
        dataFimField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(dataFimField, gbc);

        // Duração
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(createLabel("Duração:"), gbc);
        duracaoLabel = new JLabel("--");
        duracaoLabel.setForeground(Color.WHITE);
        duracaoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 1;
        formPanel.add(duracaoLabel, gbc);

        // Preço
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(createLabel("Preço Total:"), gbc);
        precoLabel = new JLabel("0.00€");
        precoLabel.setForeground(Color.WHITE);
        precoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 1;
        formPanel.add(precoLabel, gbc);

        rightPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        voltarButton = new JButton("🏠 Voltar");
        styleButton(voltarButton);
        alugarFilmeButton = new JButton("✅ Alugar Filmes");
        styleButton(alugarFilmeButton);
        
        buttonPanel.add(voltarButton);
        buttonPanel.add(alugarFilmeButton);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        contentPanel.add(rightPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBackground(new Color(0x0091D5));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
    }
} 