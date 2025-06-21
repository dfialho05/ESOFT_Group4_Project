package pt.ipleiria.estg.dei.esoft.UI.GestaoSalas;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Backend.Sala;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CriarSalaPag {
    private JPanel mainPanel;
    private JPanel roundedPanel;
    private JTextField nomeSalaField;
    private JTextField larguraField;
    private JTextField alturaField;
    private JLabel capacidadeLabel;
    private JCheckBox cadeiraDeRodasCheck;
    private JCheckBox normalSalaCheckBox;
    private JCheckBox vipSalaCheckBox;
    private JButton criarSalaButton;
    private JButton voltarButton;
    private Cinema cinema;
    private Runnable onBack;

    public CriarSalaPag(Cinema cinema, Runnable onBack) {
        this.cinema = cinema;
        this.onBack = onBack;
        
        // Setup components after they are initialized
        SwingUtilities.invokeLater(() -> {
            // Group the checkboxes to ensure only one is selected
            if (normalSalaCheckBox != null && vipSalaCheckBox != null) {
                ButtonGroup tipoSalaGroup = new ButtonGroup();
                tipoSalaGroup.add(normalSalaCheckBox);
                tipoSalaGroup.add(vipSalaCheckBox);
                normalSalaCheckBox.setSelected(true); // Default selection
            }

            // Add listeners for automatic capacity calculation
            if (larguraField != null) {
                larguraField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        calcularCapacidade();
                    }
                });
            }
            
            if (alturaField != null) {
                alturaField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        calcularCapacidade();
                    }
                });
            }

            if (criarSalaButton != null) {
                criarSalaButton.addActionListener(e -> criarSala());
            }
            if (voltarButton != null) {
                voltarButton.addActionListener(e -> onBack.run());
            }
        });
    }

    private void calcularCapacidade() {
        try {
            if (larguraField == null || alturaField == null || capacidadeLabel == null) {
                return;
            }
            String larguraStr = larguraField.getText().trim();
            String alturaStr = alturaField.getText().trim();
            
            if (!larguraStr.isEmpty() && !alturaStr.isEmpty()) {
                int largura = Integer.parseInt(larguraStr);
                int altura = Integer.parseInt(alturaStr);
                int capacidade = largura * altura;
                capacidadeLabel.setText("Capacidade Total: " + capacidade + " lugares");
            } else {
                capacidadeLabel.setText("Capacidade Total: --");
            }
        } catch (NumberFormatException e) {
            if (capacidadeLabel != null) {
                capacidadeLabel.setText("Capacidade Total: --");
            }
        }
    }

    private void criarSala() {
        if (nomeSalaField == null || larguraField == null || alturaField == null || 
            normalSalaCheckBox == null || cadeiraDeRodasCheck == null) {
            return;
        }
        
        String nome = nomeSalaField.getText();
        String larguraStr = larguraField.getText();
        String alturaStr = alturaField.getText();

        if (nome.trim().isEmpty() || larguraStr.trim().isEmpty() || alturaStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int largura = Integer.parseInt(larguraStr);
            int altura = Integer.parseInt(alturaStr);
            int capacidadeTotal = largura * altura;

            if (largura <= 0 || altura <= 0) {
                JOptionPane.showMessageDialog(mainPanel, "A largura e altura devem ser valores positivos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String tipoSala = normalSalaCheckBox.isSelected() ? "Normal" : "VIP";
            boolean temAcessibilidades = cadeiraDeRodasCheck.isSelected();
            
            // Assuming a simple unique ID generation for the example
            int novoId = cinema.getSalas().size() + 1;

            String dimensoesStr = largura + "x" + altura;
            Sala novaSala = new Sala(novoId, nome, tipoSala, capacidadeTotal, dimensoesStr, true, temAcessibilidades ? "Acessível para cadeira de rodas" : "");
            cinema.adicionarSala(novaSala);

            JOptionPane.showMessageDialog(mainPanel, "Sala '" + nome + "' criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear fields after creation
            nomeSalaField.setText("");
            larguraField.setText("");
            alturaField.setText("");
            if (capacidadeLabel != null) {
                capacidadeLabel.setText("Capacidade Total: --");
            }
            cadeiraDeRodasCheck.setSelected(false);
            normalSalaCheckBox.setSelected(true);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainPanel, "Valores de largura e altura devem ser números válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
