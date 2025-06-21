package pt.ipleiria.estg.dei.esoft.UI.GestaoFilmes;

import pt.ipleiria.estg.dei.esoft.Backend.Filme;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarFilme extends JFrame {
    private JPanel mainPanel;
    private JTextField tituloFilmeField;
    private JTextField generoFilmeField;
    private JTextField duracaoFilmeField;
    private JTextField classificacaoEtariaField;
    private JTextField anoLancamentoField;
    private JTextField precoAluguelField;
    private JTextArea sinopseArea;
    private JTextField diasMinimoField;
    private JTextField diasMaximoField;
    private JButton editarButton;
    private JButton cancelarButton;
    private Filme filme;
    private Runnable onFilmeUpdated;

    public EditarFilme(Filme filme, Runnable onFilmeUpdated) {
        this.filme = filme;
        this.onFilmeUpdated = onFilmeUpdated;

        setTitle("CinemaLiz - Editar Filme");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(mainPanel);

        populateFilmeData();

        editarButton.addActionListener(e -> saveChanges());
        cancelarButton.addActionListener(e -> dispose());
    }

    private void populateFilmeData() {
        tituloFilmeField.setText(filme.getTitulo());
        generoFilmeField.setText(filme.getGenero());
        duracaoFilmeField.setText(String.valueOf(filme.getDuracao()));
        classificacaoEtariaField.setText(filme.getClassificacaoEtaria());
        anoLancamentoField.setText(String.valueOf(filme.getAnoLancamento()));
        precoAluguelField.setText(String.valueOf(filme.getPrecoAluguer()));
        sinopseArea.setText(filme.getSinopse());
        diasMinimoField.setText(String.valueOf(filme.getDiasMinimoAluguer()));
        diasMaximoField.setText(String.valueOf(filme.getDiasMaximoAluguer()));
    }

    private void saveChanges() {
        try {
            filme.setTitulo(tituloFilmeField.getText());
            filme.setGenero(generoFilmeField.getText());
            filme.setDuracao(Integer.parseInt(duracaoFilmeField.getText()));
            filme.setClassificacaoEtaria(classificacaoEtariaField.getText());
            filme.setAnoLancamento(Integer.parseInt(anoLancamentoField.getText()));
            filme.setPrecoAluguer(Double.parseDouble(precoAluguelField.getText()));
            filme.setSinopse(sinopseArea.getText());
            filme.setDiasMinimoAluguer(Integer.parseInt(diasMinimoField.getText()));
            filme.setDiasMaximoAluguer(Integer.parseInt(diasMaximoField.getText()));

            JOptionPane.showMessageDialog(this, "Filme atualizado com sucesso!");
            onFilmeUpdated.run(); // Callback to refresh the list
            dispose(); // Close the edit window

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor insira valores numéricos válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
} 