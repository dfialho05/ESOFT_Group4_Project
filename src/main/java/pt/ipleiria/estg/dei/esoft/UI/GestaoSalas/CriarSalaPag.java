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
        // 1. Chamar createUIComponents PRIMEIRO para que o GUI Designer inicialize os painéis
        createUIComponents();
        
        // 2. Definir as propriedades da classe
        this.cinema = cinema;
        this.onBack = onBack;

        // 3. Adicionar os listeners e manipular os componentes DEPOIS de tudo estar inicializado
        // Group the checkboxes to ensure only one is selected
        ButtonGroup tipoSalaGroup = new ButtonGroup();
        tipoSalaGroup.add(normalSalaCheckBox);
        tipoSalaGroup.add(vipSalaCheckBox);
        normalSalaCheckBox.setSelected(true); // Default selection

        // Add listeners for automatic capacity calculation
        larguraField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calcularCapacidade();
            }
        });
        
        alturaField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calcularCapacidade();
            }
        });

        criarSalaButton.addActionListener(e -> criarSala());
        voltarButton.addActionListener(e -> onBack.run());
    }

    private void calcularCapacidade() {
        try {
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
            capacidadeLabel.setText("Capacidade Total: --");
        }
    }

    private void criarSala() {
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
            capacidadeLabel.setText("Capacidade Total: --");
            cadeiraDeRodasCheck.setSelected(false);
            normalSalaCheckBox.setSelected(true);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainPanel, "Valores de largura e altura devem ser números válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        // Apenas o roundedPanel é personalizado, o mainPanel será criado pelo GUI Designer.
        roundedPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Este código cria o efeito de painel com cantos arredondados.
                // É importante chamar super.paintComponent(g) para garantir que os
                // componentes filhos do painel (botões, campos de texto, etc.) sejam desenhados.
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Usamos a cor de fundo do formulário para o efeito de "recorte".
                g2d.setColor(new Color(0x0091D5));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
                
                // Deixamos a classe pai tratar do resto da pintura (incluindo os filhos).
                super.paintComponent(g);
                g2d.dispose();
            }
        };
        // Para que o fundo do mainPanel seja visível nos cantos, este painel não pode ser opaco.
        roundedPanel.setOpaque(false);
    }
}
