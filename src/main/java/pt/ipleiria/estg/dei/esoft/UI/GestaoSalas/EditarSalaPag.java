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

        // Preencher os campos com os dados da sala
        nomeSalaField.setText(sala.getNome());
        capacidadeLabel.setText(sala.getCapacidade() + " lugares");
        acessibilidadeCheckBox.setSelected(sala.getDescricao().contains("Cadeira de Rodas"));
        if (sala.getTipoSala().equals("Normal")) {
            normalCheckBox.setSelected(true);
        } else if (sala.getTipoSala().equals("VIP")) {
            vipCheckBox.setSelected(true);
        }

        // Action listener para o botão de voltar
        voltarButton.addActionListener(e -> onBack.run());

        // Action listener para o botão de editar (ainda por implementar a lógica de guardar)
        editarSalaButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel, "Lógica de guardar as alterações será implementada.", "Funcionalidade Futura", JOptionPane.INFORMATION_MESSAGE);
            onBack.run(); // Volta para a lista
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
} 