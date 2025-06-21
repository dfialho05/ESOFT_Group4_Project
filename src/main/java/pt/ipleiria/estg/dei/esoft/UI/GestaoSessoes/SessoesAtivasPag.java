package pt.ipleiria.estg.dei.esoft.UI.GestaoSessoes;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Backend.Sessao;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SessoesAtivasPag {
    private JPanel mainPanel;
    private JButton voltarButton;
    private JScrollPane scrollPane;
    private JPanel sessoesPanel;
    private Cinema cinema;
    private Runnable voltarCallback;

    public SessoesAtivasPag(Cinema cinema, Runnable voltarCallback) {
        this.cinema = cinema;
        this.voltarCallback = voltarCallback;
        
        // Setup components after they are initialized
        SwingUtilities.invokeLater(() -> {
            if (voltarButton != null) {
                voltarButton.addActionListener(e -> voltarCallback.run());
            }
            
            if (sessoesPanel != null) {
                sessoesPanel.setLayout(new BoxLayout(sessoesPanel, BoxLayout.Y_AXIS));
            }
            
            loadSessoes();
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void loadSessoes() {
        if (sessoesPanel == null) {
            return;
        }
        
        sessoesPanel.removeAll();
        List<Sessao> sessoes = cinema.getSessoes();

        for (Sessao sessao : sessoes) {
            if (sessao.isAtivo()) {
                sessoesPanel.add(createSessaoCard(sessao));
                sessoesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        sessoesPanel.revalidate();
        sessoesPanel.repaint();
    }

    private JPanel createSessaoCard(Sessao sessao) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JPanel detailsPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        detailsPanel.add(new JLabel("Filme: " + sessao.getFilme().getTitulo()));
        detailsPanel.add(new JLabel("Sala: " + sessao.getSala().getNome()));
        detailsPanel.add(new JLabel("Data Início: " + sessao.getDataInicio().format(dateFormat)));
        detailsPanel.add(new JLabel("Data Fim: " + sessao.getDataFim().format(dateFormat)));

        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timePanel.add(new JLabel("Horário: " + sessao.getHorarioFormatado()));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(detailsPanel, BorderLayout.CENTER);
        topPanel.add(timePanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton desativarButton = new JButton("Desativar");
        desativarButton.addActionListener(e -> {
            if (mainPanel != null) {
                int confirm = JOptionPane.showConfirmDialog(mainPanel, "Tem a certeza que deseja desativar esta sessão?", "Confirmar Desativação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    sessao.setAtivo(false);
                    JOptionPane.showMessageDialog(mainPanel, "Sessão desativada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    loadSessoes();
                }
            }
        });
        buttonPanel.add(desativarButton);

        card.add(topPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.EAST);

        return card;
    }
} 