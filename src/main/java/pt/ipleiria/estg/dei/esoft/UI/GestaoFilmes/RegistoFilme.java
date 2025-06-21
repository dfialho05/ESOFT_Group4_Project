package pt.ipleiria.estg.dei.esoft.UI.GestaoFilmes;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Backend.Filme;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public RegistoFilme(Cinema cinema, Runnable onBack) {
        this.voltarButton.addActionListener(e -> onBack.run());

        registarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
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
                        JOptionPane.showMessageDialog(mainPanel, "Por favor preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Filme novoFilme = new Filme(titulo, genero, duracao, sinopse, preco, ano, classificacao, true, diasMin, diasMax);

                    cinema.adicionarFilme(novoFilme);

                    System.out.println("Filme registado com sucesso:");
                    System.out.println(novoFilme.toString());
                    JOptionPane.showMessageDialog(mainPanel, "Filme registado com sucesso!");

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
                    JOptionPane.showMessageDialog(mainPanel, "Por favor insira valores numéricos válidos para duração, ano, preço e dias de aluguer.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        // Para fins de teste, pode-se criar um frame temporário
        JFrame frame = new JFrame("CinemaLiz - Registo de Filme");
        Cinema cinemaDeTeste = new Cinema(); // Cinema de teste
        frame.setContentPane(new RegistoFilme(cinemaDeTeste, () -> System.exit(0)).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
