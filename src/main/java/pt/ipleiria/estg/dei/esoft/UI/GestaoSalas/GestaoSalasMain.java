package pt.ipleiria.estg.dei.esoft.UI.GestaoSalas;

import javax.swing.*;

public class GestaoSalasMain {
    private JButton listaDeSalasButton;
    private JButton arquivoButton;
    private JButton criarSalaButton;

    public GestaoSalasMain() {
        listaDeSalasButton.addActionListener(e -> {
            // Action for listaDeSalasButton
            System.out.println("Lista de Salas button clicked");
        });

        arquivoButton.addActionListener(e -> {
            // Action for arquivoButton
            System.out.println("Arquivo button clicked");
        });

        criarSalaButton.addActionListener(e -> {
            // Action for criarSalaButton
            System.out.println("Criar Sala button clicked");
        });
    }
}
