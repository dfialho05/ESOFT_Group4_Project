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

        // Initialize UI components first
        createUIComponents();

        setTitle("CinemaLiz - Editar Filme");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        if (mainPanel != null) {
            setContentPane(mainPanel);
        }

        // Setup components after they are initialized
        SwingUtilities.invokeLater(() -> {
            populateFilmeData();

            if (editarButton != null) {
                editarButton.addActionListener(e -> saveChanges());
            }
            if (cancelarButton != null) {
                cancelarButton.addActionListener(e -> dispose());
            }
        });
    }

    private void createUIComponents() {
        // Initialize any custom components if needed
        // The form components are automatically bound
    }

    private void populateFilmeData() {
        if (filme == null) {
            JOptionPane.showMessageDialog(this, "Erro: Nenhum filme selecionado para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        
        try {
            if (tituloFilmeField != null) {
                tituloFilmeField.setText(filme.getTitulo() != null ? filme.getTitulo() : "");
            }
            if (generoFilmeField != null) {
                generoFilmeField.setText(filme.getGenero() != null ? filme.getGenero() : "");
            }
            if (duracaoFilmeField != null) {
                duracaoFilmeField.setText(String.valueOf(filme.getDuracao()));
            }
            if (classificacaoEtariaField != null) {
                classificacaoEtariaField.setText(filme.getClassificacaoEtaria() != null ? filme.getClassificacaoEtaria() : "");
            }
            if (anoLancamentoField != null) {
                anoLancamentoField.setText(String.valueOf(filme.getAnoLancamento()));
            }
            if (precoAluguelField != null) {
                precoAluguelField.setText(String.valueOf(filme.getPrecoAluguer()));
            }
            if (sinopseArea != null) {
                sinopseArea.setText(filme.getSinopse() != null ? filme.getSinopse() : "");
            }
            if (diasMinimoField != null) {
                diasMinimoField.setText(String.valueOf(filme.getDiasMinimoAluguer()));
            }
            if (diasMaximoField != null) {
                diasMaximoField.setText(String.valueOf(filme.getDiasMaximoAluguer()));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do filme: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveChanges() {
        try {
            if (tituloFilmeField == null || generoFilmeField == null || duracaoFilmeField == null || 
                classificacaoEtariaField == null || anoLancamentoField == null || precoAluguelField == null || 
                sinopseArea == null || diasMinimoField == null || diasMaximoField == null) {
                JOptionPane.showMessageDialog(this, "Erro: Componentes da interface não inicializados.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate required fields
            String titulo = tituloFilmeField.getText().trim();
            String genero = generoFilmeField.getText().trim();
            String classificacao = classificacaoEtariaField.getText().trim();
            String sinopse = sinopseArea.getText().trim();
            
            if (titulo.isEmpty() || genero.isEmpty() || classificacao.isEmpty() || sinopse.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor preencha todos os campos obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Parse numeric fields
            int duracao = Integer.parseInt(duracaoFilmeField.getText().trim());
            int ano = Integer.parseInt(anoLancamentoField.getText().trim());
            double preco = Double.parseDouble(precoAluguelField.getText().trim());
            int diasMin = Integer.parseInt(diasMinimoField.getText().trim());
            int diasMax = Integer.parseInt(diasMaximoField.getText().trim());
            
            // Validate numeric values
            if (duracao <= 0 || ano <= 0 || preco < 0 || diasMin <= 0 || diasMax <= 0) {
                JOptionPane.showMessageDialog(this, "Por favor insira valores válidos para os campos numéricos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (diasMax < diasMin) {
                JOptionPane.showMessageDialog(this, "Os dias máximo de aluguer não podem ser menores que os dias mínimo.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update the film
            filme.setTitulo(titulo);
            filme.setGenero(genero);
            filme.setDuracao(duracao);
            filme.setClassificacaoEtaria(classificacao);
            filme.setAnoLancamento(ano);
            filme.setPrecoAluguer(preco);
            filme.setSinopse(sinopse);
            filme.setDiasMinimoAluguer(diasMin);
            filme.setDiasMaximoAluguer(diasMax);

            JOptionPane.showMessageDialog(this, "Filme atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            onFilmeUpdated.run(); // Callback to refresh the list
            dispose(); // Close the edit window

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor insira valores numéricos válidos para duração, ano, preço e dias de aluguer.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar filme: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
} 