package pt.ipleiria.estg.dei.esoft.UI.GestaoSessoes;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Backend.Filme;
import pt.ipleiria.estg.dei.esoft.Backend.Sala;
import pt.ipleiria.estg.dei.esoft.Backend.Sessao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CriarSessaoPag {
    private JPanel mainPanel;
    private JLabel anoValue;
    private JButton criarSessaoButton;
    private JButton voltarButton;
    private JCheckBox hora12CheckBox;
    private JCheckBox hora16CheckBox;
    private JCheckBox hora18CheckBox;
    private JCheckBox hora21CheckBox;
    private JTextField dataInicioField;
    private JTextField dataFimField;
    private JComboBox<Filme> filmeComboBox;

    private final Cinema cinema;
    private final Runnable onBack;

    public CriarSessaoPag(Cinema cinema, Runnable onBack) {
        this.cinema = cinema;
        this.onBack = onBack;

        // Setup components after they are initialized
        SwingUtilities.invokeLater(() -> {
            // Inicializar componentes manualmente se necessário
            if (mainPanel == null) {
                mainPanel = new JPanel();
            }
            if (anoValue == null) {
                anoValue = new JLabel();
            }
            if (criarSessaoButton == null) {
                criarSessaoButton = new JButton("Criar Sessão");
            }
            if (voltarButton == null) {
                voltarButton = new JButton("Voltar");
            }
            if (hora12CheckBox == null) {
                hora12CheckBox = new JCheckBox("12:00");
            }
            if (hora16CheckBox == null) {
                hora16CheckBox = new JCheckBox("16:00");
            }
            if (hora18CheckBox == null) {
                hora18CheckBox = new JCheckBox("18:00");
            }
            if (hora21CheckBox == null) {
                hora21CheckBox = new JCheckBox("21:00");
            }
            if (dataInicioField == null) {
                dataInicioField = new JTextField();
            }
            if (dataFimField == null) {
                dataFimField = new JTextField();
            }
            if (filmeComboBox == null) {
                filmeComboBox = new JComboBox<>();
            }

            // Custom renderer for JComboBox to show only movie title
            if (filmeComboBox != null) {
                filmeComboBox.setRenderer(new FilmeComboBoxRenderer());
            }

            // Group the checkboxes to ensure only one is selected, making them radio buttons
            if (hora12CheckBox != null && hora16CheckBox != null && hora18CheckBox != null && hora21CheckBox != null) {
                ButtonGroup horarioGroup = new ButtonGroup();
                horarioGroup.add(hora12CheckBox);
                horarioGroup.add(hora16CheckBox);
                horarioGroup.add(hora18CheckBox);
                horarioGroup.add(hora21CheckBox);
                hora12CheckBox.setSelected(true); // Set a default selection
            }

            // Populate movie JComboBox
            if (filmeComboBox != null) {
                List<Filme> activeMovies = cinema.getFilmes().stream().filter(Filme::isAtivo).collect(Collectors.toList());
                for (Filme filme : activeMovies) {
                    filmeComboBox.addItem(filme);
                    updateAnoLancamento();
                }

                // Add listener to JComboBox to update year
                filmeComboBox.addItemListener(e -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        updateAnoLancamento();
                    }
                });
            }

            // Initial state
            updateAnoLancamento();
            if (filmeComboBox != null && cinema.getFilmes().stream().filter(Filme::isAtivo).count() == 0) {
                if (criarSessaoButton != null) {
                    criarSessaoButton.setEnabled(false);
                }
                if (mainPanel != null) {
                    JOptionPane.showMessageDialog(mainPanel, "Não existem filmes ativos para criar sessões.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }

            if (criarSessaoButton != null) {
                criarSessaoButton.addActionListener(e -> criarSessoes());
            }
            if (voltarButton != null) {
                voltarButton.addActionListener(e -> onBack.run());
            }
        });
    }

    private void updateAnoLancamento() {
        if (filmeComboBox == null || anoValue == null) {
            return;
        }
        
        Filme selectedFilme = (Filme) filmeComboBox.getSelectedItem();
        if (selectedFilme != null) {
            anoValue.setText(String.valueOf(selectedFilme.getAnoLancamento()));
        } else {
            anoValue.setText("--");
        }
    }

    private void criarSessoes() {
        if (filmeComboBox == null || dataInicioField == null || dataFimField == null || 
            hora12CheckBox == null || hora16CheckBox == null || hora18CheckBox == null || hora21CheckBox == null) {
            return;
        }
        
        Filme selectedFilme = (Filme) filmeComboBox.getSelectedItem();
        if (selectedFilme == null) {
            if (mainPanel != null) {
                JOptionPane.showMessageDialog(mainPanel, "Por favor, selecione um filme.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataInicio;
        LocalDate dataFim;
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

        LocalTime horarioSelecionado = null;
        if (hora12CheckBox.isSelected()) horarioSelecionado = LocalTime.of(12, 0);
        else if (hora16CheckBox.isSelected()) horarioSelecionado = LocalTime.of(16, 0);
        else if (hora18CheckBox.isSelected()) horarioSelecionado = LocalTime.of(18, 0);
        else if (hora21CheckBox.isSelected()) horarioSelecionado = LocalTime.of(21, 0);

        if (horarioSelecionado == null) {
            if (mainPanel != null) {
                JOptionPane.showMessageDialog(mainPanel, "Por favor, selecione um horário.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        // Simple logic to find an available room. This could be more complex.
        Sala salaDisponivel = cinema.getSalas().stream().filter(Sala::isAtivo).findFirst().orElse(null);
        if (salaDisponivel == null) {
            if (mainPanel != null) {
                JOptionPane.showMessageDialog(mainPanel, "Não existem salas disponíveis para criar sessões.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }
        
        Sessao novaSessao = new Sessao(0, selectedFilme, salaDisponivel, dataInicio, dataFim, horarioSelecionado);
        boolean hasConflict = cinema.getSessoes().stream().anyMatch(s -> s.temConflitoHorario(novaSessao));

        if(hasConflict){
            if (mainPanel != null) {
                JOptionPane.showMessageDialog(mainPanel, "Conflito de horário detetado para a sala " + salaDisponivel.getNome() + " no horário " + horarioSelecionado.toString() + ". A sessão não foi criada.","Conflito de Horário", JOptionPane.WARNING_MESSAGE);
            }
            return;
        }

        int proximoId = cinema.getSessoes().stream().mapToInt(Sessao::getIdSessao).max().orElse(0) + 1;
        novaSessao.setIdSessao(proximoId);
        cinema.adicionarSessao(novaSessao);

        if (mainPanel != null) {
            JOptionPane.showMessageDialog(mainPanel, "Sessão para o filme '" + selectedFilme.getTitulo() + "' criada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
        onBack.run(); // Go back to the previous page
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Custom renderer class
    private static class FilmeComboBoxRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Filme) {
                setText(((Filme) value).getTitulo());
            }
            return this;
        }
    }
}
