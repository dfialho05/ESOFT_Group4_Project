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
                acessibilidadeCheckBox.setSelected(sala.getDescricao().contains("Cadeira de Rodas"));
            }
            if (normalCheckBox != null && vipCheckBox != null) {
                if (sala.getTipoSala().equals("Normal")) {
                    normalCheckBox.setSelected(true);
                } else if (sala.getTipoSala().equals("VIP")) {
                    vipCheckBox.setSelected(true);
                }
            }

            // Action listener para o botão de voltar
            if (voltarButton != null) {
                voltarButton.addActionListener(e -> onBack.run());
            }

            // Action listener para o botão de editar (ainda por implementar a lógica de guardar)
            if (editarSalaButton != null) {
                editarSalaButton.addActionListener(e -> {
                    if (mainPanel != null) {
                        JOptionPane.showMessageDialog(mainPanel, "Lógica de guardar as alterações será implementada.", "Funcionalidade Futura", JOptionPane.INFORMATION_MESSAGE);
                    }
                    onBack.run(); // Volta para a lista
                });
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
} 