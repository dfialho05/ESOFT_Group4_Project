package pt.ipleiria.estg.dei.esoft.UI.GestaoSalas;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Backend.Sala;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        
        // Initialize UI components first
        createUIComponents();
        
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

    private void createUIComponents() {
        // Create main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(-11685428));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Create title
        JLabel titleLabel = new JLabel("➕ Criar Nova Sala", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Create form panel
        roundedPanel = new JPanel();
        roundedPanel.setLayout(new GridBagLayout());
        roundedPanel.setBackground(new Color(-16752737));
        roundedPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Nome da Sala
        JLabel nomeLabel = new JLabel("Nome da Sala:");
        nomeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nomeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        roundedPanel.add(nomeLabel, gbc);
        
        nomeSalaField = new JTextField(20);
        nomeSalaField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        roundedPanel.add(nomeSalaField, gbc);
        
        // Largura
        JLabel larguraLabel = new JLabel("Largura (colunas):");
        larguraLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        larguraLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        roundedPanel.add(larguraLabel, gbc);
        
        larguraField = new JTextField(10);
        larguraField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        roundedPanel.add(larguraField, gbc);
        
        // Altura
        JLabel alturaLabel = new JLabel("Altura (filas):");
        alturaLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        alturaLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        roundedPanel.add(alturaLabel, gbc);
        
        alturaField = new JTextField(10);
        alturaField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        roundedPanel.add(alturaField, gbc);
        
        // Capacidade (calculada automaticamente)
        capacidadeLabel = new JLabel("Capacidade Total: --");
        capacidadeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        capacidadeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        roundedPanel.add(capacidadeLabel, gbc);
        
        // Tipo de Sala
        JLabel tipoLabel = new JLabel("Tipo de Sala:");
        tipoLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        tipoLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;
        roundedPanel.add(tipoLabel, gbc);
        
        JPanel tipoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tipoPanel.setOpaque(false);
        normalSalaCheckBox = new JCheckBox("Normal");
        normalSalaCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        normalSalaCheckBox.setForeground(Color.WHITE);
        vipSalaCheckBox = new JCheckBox("VIP");
        vipSalaCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        vipSalaCheckBox.setForeground(Color.WHITE);
        tipoPanel.add(normalSalaCheckBox);
        tipoPanel.add(vipSalaCheckBox);
        gbc.gridx = 1; gbc.gridy = 4; gbc.fill = GridBagConstraints.HORIZONTAL;
        roundedPanel.add(tipoPanel, gbc);
        
        // Acessibilidade
        cadeiraDeRodasCheck = new JCheckBox("Acessível para cadeira de rodas");
        cadeiraDeRodasCheck.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cadeiraDeRodasCheck.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        roundedPanel.add(cadeiraDeRodasCheck, gbc);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonsPanel.setOpaque(false);
        
        voltarButton = new JButton("❌ Cancelar");
        voltarButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        voltarButton.setBackground(new Color(-14575885));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setPreferredSize(new Dimension(120, 40));
        
        criarSalaButton = new JButton("✅ Criar Sala");
        criarSalaButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        criarSalaButton.setBackground(new Color(-12423373));
        criarSalaButton.setForeground(Color.WHITE);
        criarSalaButton.setPreferredSize(new Dimension(120, 40));
        
        buttonsPanel.add(voltarButton);
        buttonsPanel.add(criarSalaButton);
        
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        roundedPanel.add(buttonsPanel, gbc);
        
        mainPanel.add(roundedPanel, BorderLayout.CENTER);
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
            System.err.println("Erro: Alguns componentes são null");
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
            String descricao = temAcessibilidades ? "Acessível para cadeira de rodas" : "";
            
            System.out.println("Criando sala: ID=" + novoId + ", Nome=" + nome + ", Tipo=" + tipoSala + 
                             ", Capacidade=" + capacidadeTotal + ", Ativa=true, Descrição=" + descricao);
            
            Sala novaSala = new Sala(novoId, nome, tipoSala, capacidadeTotal, dimensoesStr, true, descricao);
            cinema.adicionarSala(novaSala);

            System.out.println("Sala criada com sucesso! Total de salas: " + cinema.getSalas().size());

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
