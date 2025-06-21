package pt.ipleiria.estg.dei.esoft.UI.GestaoSalas;

import pt.ipleiria.estg.dei.esoft.Backend.Sala;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        // Initialize UI components first
        createUIComponents();
        
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
                // Verificar se a sala tem acessibilidade de forma mais robusta
                boolean temAcessibilidade = sala.getDescricao() != null && 
                    (sala.getDescricao().toLowerCase().contains("cadeira") || 
                     sala.getDescricao().toLowerCase().contains("acessível") ||
                     sala.getDescricao().toLowerCase().contains("acessibilidade"));
                acessibilidadeCheckBox.setSelected(temAcessibilidade);
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

    private void createUIComponents() {
        // Create main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(-11685428));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Create title
        JLabel titleLabel = new JLabel("✏️ Editar Sala", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Create form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(-16752737));
        formPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Nome da Sala
        JLabel nomeLabel = new JLabel("Nome da Sala:");
        nomeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nomeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nomeLabel, gbc);
        
        nomeSalaField = new JTextField(20);
        nomeSalaField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(nomeSalaField, gbc);
        
        // Capacidade (read-only)
        JLabel capacidadeLabelTitle = new JLabel("Capacidade:");
        capacidadeLabelTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        capacidadeLabelTitle.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(capacidadeLabelTitle, gbc);
        
        capacidadeLabel = new JLabel("-- lugares");
        capacidadeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        capacidadeLabel.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(capacidadeLabel, gbc);
        
        // Tipo de Sala
        JLabel tipoLabel = new JLabel("Tipo de Sala:");
        tipoLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        tipoLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(tipoLabel, gbc);
        
        JPanel tipoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tipoPanel.setOpaque(false);
        normalCheckBox = new JCheckBox("Normal");
        normalCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        normalCheckBox.setForeground(Color.WHITE);
        vipCheckBox = new JCheckBox("VIP");
        vipCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        vipCheckBox.setForeground(Color.WHITE);
        tipoPanel.add(normalCheckBox);
        tipoPanel.add(vipCheckBox);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(tipoPanel, gbc);
        
        // Acessibilidade
        acessibilidadeCheckBox = new JCheckBox("Acessível para cadeira de rodas");
        acessibilidadeCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        acessibilidadeCheckBox.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(acessibilidadeCheckBox, gbc);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonsPanel.setOpaque(false);
        
        voltarButton = new JButton("❌ Cancelar");
        voltarButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        voltarButton.setBackground(new Color(-14575885));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setPreferredSize(new Dimension(120, 40));
        
        editarSalaButton = new JButton("✅ Salvar Alterações");
        editarSalaButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        editarSalaButton.setBackground(new Color(-12423373));
        editarSalaButton.setForeground(Color.WHITE);
        editarSalaButton.setPreferredSize(new Dimension(150, 40));
        
        buttonsPanel.add(voltarButton);
        buttonsPanel.add(editarSalaButton);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonsPanel, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
} 