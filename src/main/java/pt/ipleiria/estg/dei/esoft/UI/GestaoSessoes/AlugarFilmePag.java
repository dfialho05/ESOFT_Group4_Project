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
} 