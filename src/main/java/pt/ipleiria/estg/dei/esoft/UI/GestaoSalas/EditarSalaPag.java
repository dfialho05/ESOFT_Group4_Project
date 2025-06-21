package pt.ipleiria.estg.dei.esoft.UI.GestaoSalas;

import pt.ipleiria.estg.dei.esoft.Backend.Sala;
import javax.swing.*;
import java.awt.*;

public class EditarSalaPag {
    private JPanel mainPanel;
    private JTextField nomeSalaField;
    private JLabel capacidadeLabel;
    private JCheckBox acessibilidadeCheckBox;
    private JCheckBox normalCheckBox;
    private JCheckBox vipCheckBox;
    private JButton editarSalaButton;
    private JButton voltarButton;
    private Sala sala;
    private Runnable onBack;

    public EditarSalaPag(Sala sala, Runnable onBack) {
        this.sala = sala;
        this.onBack = onBack;

        // Setup components after they are initialized
        SwingUtilities.invokeLater(() -> {
            // Preencher os campos com os dados da sala
            if (nomeSalaField != null) {
                nomeSalaField.setText(sala.getNome());
            }
            if (capacidadeLabel != null) {
                capacidadeLabel.setText(sala.getCapacidade() + " lugares");
            }
            if (acessibilidadeCheckBox != null) {
                acessibilidadeCheckBox.setSelected(sala.getDescricao() != null && sala.getDescricao().contains("Cadeira de Rodas"));
            }
            
            // Configurar checkboxes de tipo como radio buttons
            if (normalCheckBox != null && vipCheckBox != null) {
                ButtonGroup tipoSalaGroup = new ButtonGroup();
                tipoSalaGroup.add(normalCheckBox);
                tipoSalaGroup.add(vipCheckBox);
                
                if (sala.getTipoSala() != null) {
                    if (sala.getTipoSala().equals("Normal")) {
                        normalCheckBox.setSelected(true);
                    } else if (sala.getTipoSala().equals("VIP")) {
                        vipCheckBox.setSelected(true);
                    } else {
                        // Default para Normal se o tipo não for reconhecido
                        normalCheckBox.setSelected(true);
                    }
                } else {
                    normalCheckBox.setSelected(true);
                }
            }

            // Action listener para o botão de voltar
            if (voltarButton != null) {
                voltarButton.addActionListener(e -> onBack.run());
            }

            // Action listener para o botão de editar
            if (editarSalaButton != null) {
                editarSalaButton.addActionListener(e -> {
                    if (mainPanel != null) {
                        // Validar campos obrigatórios
                        if (nomeSalaField == null || nomeSalaField.getText().trim().isEmpty()) {
                            JOptionPane.showMessageDialog(mainPanel, "Por favor, preencha o nome da sala.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Obter valores dos campos
                        String novoNome = nomeSalaField.getText().trim();
                        boolean temAcessibilidade = acessibilidadeCheckBox != null && acessibilidadeCheckBox.isSelected();
                        String novoTipoSala = "Normal"; // Default
                        
                        if (normalCheckBox != null && normalCheckBox.isSelected()) {
                            novoTipoSala = "Normal";
                        } else if (vipCheckBox != null && vipCheckBox.isSelected()) {
                            novoTipoSala = "VIP";
                        }

                        // Atualizar a sala
                        sala.setNome(novoNome);
                        sala.setTipoSala(novoTipoSala);
                        
                        if (temAcessibilidade) {
                            sala.setDescricao("Acessível para cadeira de rodas");
                        } else {
                            sala.setDescricao("");
                        }

                        JOptionPane.showMessageDialog(mainPanel, "Sala atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        onBack.run(); // Volta para a lista
                    }
                });
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
} 