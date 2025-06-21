package pt.ipleiria.estg.dei.esoft.UI.GestaoFilmes;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Backend.Filme;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class RegistoFilme {
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
    private JButton registarButton;
    private JButton voltarButton;
    private final Cinema cinema;
    private final Runnable onBack;

    public RegistoFilme(Cinema cinema, Runnable onBack) {
        this.cinema = cinema;
        this.onBack = onBack;

        createUIComponents(); // Chamar para construir a UI

        SwingUtilities.invokeLater(() -> {
            if (voltarButton != null) {
                voltarButton.addActionListener(e -> onBack.run());
            }

            if (registarButton != null) {
                registarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if (tituloFilmeField == null || generoFilmeField == null || duracaoFilmeField == null || 
                                classificacaoEtariaField == null || anoLancamentoField == null || precoAluguelField == null || 
                                sinopseArea == null || diasMinimoField == null || diasMaximoField == null) {
                                return;
                            }
                            
                            String titulo = tituloFilmeField.getText();
                            String genero = generoFilmeField.getText();
                            int duracao = Integer.parseInt(duracaoFilmeField.getText());
                            String classificacao = classificacaoEtariaField.getText();
                            int ano = Integer.parseInt(anoLancamentoField.getText());
                            double preco = Double.parseDouble(precoAluguelField.getText());
                            String sinopse = sinopseArea.getText();
                            int diasMin = Integer.parseInt(diasMinimoField.getText());
                            int diasMax = Integer.parseInt(diasMaximoField.getText());

                            if (titulo.isEmpty() || genero.isEmpty() || classificacao.isEmpty() || sinopse.isEmpty()) {
                                if (mainPanel != null) {
                                    JOptionPane.showMessageDialog(mainPanel, "Por favor preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                                }
                                return;
                            }

                            Filme novoFilme = new Filme(titulo, genero, duracao, sinopse, preco, ano, classificacao, true, diasMin, diasMax);

                            cinema.adicionarFilme(novoFilme);

                            System.out.println("Filme registado com sucesso:");
                            System.out.println(novoFilme.toString());
                            if (mainPanel != null) {
                                JOptionPane.showMessageDialog(mainPanel, "Filme registado com sucesso!");
                            }

                            // Limpar os campos após o registo
                            tituloFilmeField.setText("");
                            generoFilmeField.setText("");
                            duracaoFilmeField.setText("");
                            classificacaoEtariaField.setText("");
                            anoLancamentoField.setText("");
                            precoAluguelField.setText("");
                            sinopseArea.setText("");
                            diasMinimoField.setText("");
                            diasMaximoField.setText("");


                        } catch (NumberFormatException ex) {
                            if (mainPanel != null) {
                                JOptionPane.showMessageDialog(mainPanel, "Por favor insira valores numéricos válidos para duração, ano, preço e dias de aluguer.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });
            }
        });
    }

    private void createUIComponents() {
        mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(new Color(0x2d3c42));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("➕ Registar Novo Filme", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add fields
        tituloFilmeField = addFormField(formPanel, gbc, "Título:", 0);
        generoFilmeField = addFormField(formPanel, gbc, "Gênero:", 1);
        duracaoFilmeField = addFormField(formPanel, gbc, "Duração (min):", 2);
        classificacaoEtariaField = addFormField(formPanel, gbc, "Classificação:", 3);
        anoLancamentoField = addFormField(formPanel, gbc, "Ano de Lançamento:", 4);
        precoAluguelField = addFormField(formPanel, gbc, "Preço de Aluguer (€):", 5);
        diasMinimoField = addFormField(formPanel, gbc, "Dias Mínimo Aluguer:", 6);
        diasMaximoField = addFormField(formPanel, gbc, "Dias Máximo Aluguer:", 7);

        // Sinopse
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(createLabel("Sinopse:"), gbc);
        sinopseArea = new JTextArea(5, 20);
        sinopseArea.setLineWrap(true);
        sinopseArea.setWrapStyleWord(true);
        JScrollPane sinopseScrollPane = new JScrollPane(sinopseArea);
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(sinopseScrollPane, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        voltarButton = new JButton("🏠 Voltar");
        styleButton(voltarButton);
        registarButton = new JButton("✅ Registar Filme");
        styleButton(registarButton);
        
        buttonPanel.add(voltarButton);
        buttonPanel.add(registarButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTextField addFormField(JPanel panel, GridBagConstraints gbc, String labelText, int yPos) {
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(createLabel(labelText), gbc);

        JTextField textField = new JTextField(30);
        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(textField, gbc);
        return textField;
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

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
