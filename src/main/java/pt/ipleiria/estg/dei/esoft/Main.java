package pt.ipleiria.estg.dei.esoft;

import pt.ipleiria.estg.dei.esoft.Backend.Cinema;
import pt.ipleiria.estg.dei.esoft.Backend.Sala;
import pt.ipleiria.estg.dei.esoft.UI.GestaoPag;
import pt.ipleiria.estg.dei.esoft.UI.GestaoFilmes.GestaoFilmeMain;
import pt.ipleiria.estg.dei.esoft.UI.GestaoFilmes.RegistoFilme;
import pt.ipleiria.estg.dei.esoft.UI.GestaoFilmes.PesquisaFilme;
import pt.ipleiria.estg.dei.esoft.UI.GestaoFilmes.ArquivoFilme;
import pt.ipleiria.estg.dei.esoft.UI.GestaoSalas.ArquivoSalasPag;
import pt.ipleiria.estg.dei.esoft.UI.GestaoSalas.CriarSalaPag;
import pt.ipleiria.estg.dei.esoft.UI.GestaoSalas.EditarSalaPag;
import pt.ipleiria.estg.dei.esoft.UI.GestaoSalas.GestaoSalasMain;
import pt.ipleiria.estg.dei.esoft.UI.GestaoSalas.ListaSalasPag;
import pt.ipleiria.estg.dei.esoft.UI.GestaoSessoes.GestaoSessaoMain;
import pt.ipleiria.estg.dei.esoft.UI.GestaoSessoes.CriarSessaoPag;
import pt.ipleiria.estg.dei.esoft.UI.GestaoSessoes.SessoesAtivasPag;
import pt.ipleiria.estg.dei.esoft.UI.GestaoSessoes.ArquivoSessoesPag;
import pt.ipleiria.estg.dei.esoft.UI.GestaoSessoes.AlugarFilmePag;
import pt.ipleiria.estg.dei.esoft.UI.MainPag;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class Main {

    private static JFrame frame;
    private static Cinema cinema;

    public static void main(String[] args) {
        System.out.println("Iniciando aplicação CinemaLiz...");
        
        try {
            cinema = DadosExemplo.criarCinemaComExemplos();
            System.out.println("Cinema criado com sucesso!");
            System.out.println("Filmes criados: " + cinema.getFilmes().size());
            System.out.println("Salas criadas: " + cinema.getSalas().size());
            System.out.println("Sessões criadas: " + cinema.getSessoes().size());
            System.out.println("Produtos de bar criados: " + cinema.getProdutosBar().size());
            System.out.println("Menus criados: " + cinema.getMenus().size());
            System.out.println("Vendas registadas: " + cinema.getVendas().size());
        } catch (Exception e) {
            System.err.println("Erro ao criar cinema: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        java.awt.EventQueue.invokeLater(() -> {
            try {
                System.out.println("Criando interface gráfica...");
                frame = new JFrame("CinemaLiz");
                mostrarMainPag(); // Mostra a página principal ao iniciar
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                System.out.println("Interface gráfica criada com sucesso!");
            } catch (Exception e) {
                System.err.println("Erro ao criar interface gráfica: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public static void mostrarMainPag() {
        try {
            MainPag mainPag = new MainPag(cinema, Main::mostrarGestaoPag);
            JPanel panel = mainPag.getMainPanel();
            
            if (panel == null) {
                System.err.println("Erro: mainPanel é null na MainPag");
                // Create a fallback panel
                panel = new JPanel();
                panel.setLayout(new BorderLayout());
                panel.setBackground(new Color(-11685428));
                
                JLabel errorLabel = new JLabel("Erro ao carregar interface", SwingConstants.CENTER);
                errorLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
                errorLabel.setForeground(Color.WHITE);
                panel.add(errorLabel, BorderLayout.CENTER);
            }
            
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
        } catch (Exception e) {
            System.err.println("Erro ao mostrar MainPag: " + e.getMessage());
            e.printStackTrace();
            
            // Create emergency fallback
            JPanel emergencyPanel = new JPanel();
            emergencyPanel.setLayout(new BorderLayout());
            emergencyPanel.setBackground(new Color(-11685428));
            
            JLabel emergencyLabel = new JLabel("Erro na aplicação", SwingConstants.CENTER);
            emergencyLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
            emergencyLabel.setForeground(Color.WHITE);
            emergencyPanel.add(emergencyLabel, BorderLayout.CENTER);
            
            frame.setContentPane(emergencyPanel);
            frame.revalidate();
            frame.repaint();
        }
    }

    public static void mostrarGestaoPag() {
        try {
            System.out.println("Criando GestaoPag...");
            GestaoPag gestaoPag = new GestaoPag(cinema, 
                Main::mostrarGestaoSalasMain, 
                Main::mostrarGestaoFilmeMain, 
                Main::mostrarGestaoSessaoMain, 
                Main::mostrarMainPag);
            JPanel panel = gestaoPag.getMainPanel();
            
            System.out.println("Panel obtido: " + (panel != null ? "não é null" : "é null"));
            
            if (panel == null) {
                System.err.println("Erro: mainPanel é null na GestaoPag");
                panel = createFallbackPanel("Erro ao carregar gestão");
            }
            
            System.out.println("Definindo contentPane...");
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
            System.out.println("GestaoPag carregada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao mostrar GestaoPag: " + e.getMessage());
            e.printStackTrace();
            mostrarMainPag(); // Fallback to main page
        }
    }

    public static void mostrarGestaoSalasMain() {
        try {
            System.out.println("Criando GestaoSalasMain...");
            GestaoSalasMain gestaoSalasMain = new GestaoSalasMain(cinema, Main::mostrarGestaoPag);
            JPanel panel = gestaoSalasMain.getMainPanel();
            
            System.out.println("Panel obtido: " + (panel != null ? "não é null" : "é null"));
            
            if (panel == null) {
                System.err.println("Erro: mainPanel é null na GestaoSalasMain");
                panel = createFallbackPanel("Erro ao carregar Gestão de Salas");
            }
            
            System.out.println("Definindo contentPane...");
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
            System.out.println("GestaoSalasMain carregada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao mostrar GestaoSalasMain: " + e.getMessage());
            e.printStackTrace();
            mostrarGestaoPag(); // Fallback to gestão page
        }
    }

    public static void mostrarListaSalasPag() {
        try {
            ListaSalasPag listaSalasPag = new ListaSalasPag(cinema, Main::mostrarGestaoSalasMain);
            JPanel panel = listaSalasPag.getMainPanel();
            
            if (panel == null) {
                System.err.println("Erro: mainPanel é null na ListaSalasPag");
                panel = createFallbackPanel("Erro ao carregar Lista de Salas");
            }
            
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
        } catch (Exception e) {
            System.err.println("Erro ao mostrar ListaSalasPag: " + e.getMessage());
            e.printStackTrace();
            mostrarGestaoSalasMain(); // Fallback to gestão salas page
        }
    }

    public static void mostrarGestaoFilmeMain() {
        try {
            System.out.println("Criando GestaoFilmeMain...");
            GestaoFilmeMain gestaoFilmeMain = new GestaoFilmeMain(cinema, Main::mostrarGestaoPag);
            JPanel panel = gestaoFilmeMain.getMainPanel();
            
            System.out.println("Panel obtido: " + (panel != null ? "não é null" : "é null"));
            
            if (panel == null) {
                System.err.println("Erro: mainPanel é null na GestaoFilmeMain");
                panel = createFallbackPanel("Erro ao carregar Gestão de Filmes");
            }
            
            System.out.println("Definindo contentPane...");
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
            System.out.println("GestaoFilmeMain carregada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao mostrar GestaoFilmeMain: " + e.getMessage());
            e.printStackTrace();
            mostrarGestaoPag(); // Fallback to gestão page
        }
    }

    public static void mostrarCriarSalaPag() {
        try {
            CriarSalaPag criarSalaPag = new CriarSalaPag(cinema, Main::mostrarGestaoSalasMain);
            JPanel panel = criarSalaPag.getMainPanel();
            
            if (panel == null) {
                System.err.println("Erro: mainPanel é null na CriarSalaPag");
                panel = createFallbackPanel("Erro ao carregar Criar Sala");
            }
            
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
        } catch (Exception e) {
            System.err.println("Erro ao mostrar CriarSalaPag: " + e.getMessage());
            e.printStackTrace();
            mostrarGestaoSalasMain(); // Fallback to gestão salas page
        }
    }

    public static void mostrarRegistoFilme() {
        try {
            RegistoFilme registoFilme = new RegistoFilme(cinema, Main::mostrarGestaoFilmeMain);
            JPanel panel = registoFilme.getMainPanel();
            
            if (panel == null) {
                System.err.println("Erro: mainPanel é null na RegistoFilme");
                panel = createFallbackPanel("Erro ao carregar Registo de Filme");
            }
            
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
        } catch (Exception e) {
            System.err.println("Erro ao mostrar RegistoFilme: " + e.getMessage());
            e.printStackTrace();
            mostrarGestaoFilmeMain(); // Fallback to gestão filmes page
        }
    }

    public static void mostrarEditarSalaPag(Sala sala) {
        try {
            EditarSalaPag editarSalaPag = new EditarSalaPag(sala, Main::mostrarListaSalasPag);
            JPanel panel = editarSalaPag.getMainPanel();
            
            if (panel == null) {
                System.err.println("Erro: mainPanel é null na EditarSalaPag");
                panel = createFallbackPanel("Erro ao carregar Editar Sala");
            }
            
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
        } catch (Exception e) {
            System.err.println("Erro ao mostrar EditarSalaPag: " + e.getMessage());
            e.printStackTrace();
            mostrarListaSalasPag(); // Fallback to lista salas page
        }
    }

    public static void mostrarArquivoSalasPag() {
        try {
            ArquivoSalasPag arquivoSalasPag = new ArquivoSalasPag(cinema, Main::mostrarGestaoSalasMain);
            JPanel panel = arquivoSalasPag.getMainPanel();
            
            if (panel == null) {
                System.err.println("Erro: mainPanel é null na ArquivoSalasPag");
                panel = createFallbackPanel("Erro ao carregar Arquivo de Salas");
            }
            
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
        } catch (Exception e) {
            System.err.println("Erro ao mostrar ArquivoSalasPag: " + e.getMessage());
            e.printStackTrace();
            mostrarGestaoSalasMain(); // Fallback to gestão salas page
        }
    }

    public static void mostrarArquivoFilme() {
        try {
            ArquivoFilme arquivoFilme = new ArquivoFilme(cinema, Main::mostrarGestaoFilmeMain);
            JPanel panel = arquivoFilme.getMainPanel();
            
            if (panel == null) {
                System.err.println("Erro: mainPanel é null na ArquivoFilme");
                panel = createFallbackPanel("Erro ao carregar Arquivo de Filmes");
            }
            
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
        } catch (Exception e) {
            System.err.println("Erro ao mostrar ArquivoFilme: " + e.getMessage());
            e.printStackTrace();
            mostrarGestaoFilmeMain(); // Fallback to gestão filmes page
        }
    }

    public static void mostrarPesquisaFilme() {
        try {
            PesquisaFilme pesquisaFilme = new PesquisaFilme(cinema, Main::mostrarGestaoFilmeMain);
            JPanel panel = pesquisaFilme.getMainPanel();
            
            if (panel == null) {
                System.err.println("Erro: mainPanel é null na PesquisaFilme");
                panel = createFallbackPanel("Erro ao carregar Pesquisa de Filmes");
            }
            
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
        } catch (Exception e) {
            System.err.println("Erro ao mostrar PesquisaFilme: " + e.getMessage());
            e.printStackTrace();
            mostrarGestaoFilmeMain(); // Fallback to gestão filmes page
        }
    }

    public static void mostrarGestaoSessaoMain() {
        try {
            GestaoSessaoMain gestaoSessaoMain = new GestaoSessaoMain(cinema, Main::mostrarGestaoPag);
            JPanel panel = gestaoSessaoMain.getMainPanel();
            
            if (panel == null) {
                System.err.println("Erro: mainPanel é null na GestaoSessaoMain");
                panel = createFallbackPanel("Erro ao carregar Gestão de Sessões");
            }
            
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
        } catch (Exception e) {
            System.err.println("Erro ao mostrar GestaoSessaoMain: " + e.getMessage());
            e.printStackTrace();
            mostrarGestaoPag(); // Fallback to gestão page
        }
    }

    public static void mostrarCriarSessaoPag() {
        try {
            CriarSessaoPag criarSessaoPag = new CriarSessaoPag(cinema, Main::mostrarGestaoSessaoMain);
            JPanel panel = criarSessaoPag.getMainPanel();
            
            if (panel == null) {
                System.err.println("Erro: mainPanel é null na CriarSessaoPag");
                panel = createFallbackPanel("Erro ao carregar Criar Sessão");
            }
            
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
        } catch (Exception e) {
            System.err.println("Erro ao mostrar CriarSessaoPag: " + e.getMessage());
            e.printStackTrace();
            mostrarGestaoSessaoMain(); // Fallback to gestão sessões page
        }
    }

    public static void mostrarSessoesAtivas() {
        try {
            SessoesAtivasPag sessoesAtivasPag = new SessoesAtivasPag(cinema, Main::mostrarGestaoSessaoMain);
            JPanel panel = sessoesAtivasPag.getMainPanel();
            
            if (panel == null) {
                System.err.println("Erro: mainPanel é null na SessoesAtivasPag");
                panel = createFallbackPanel("Erro ao carregar Sessões Ativas");
            }
            
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
        } catch (Exception e) {
            System.err.println("Erro ao mostrar SessoesAtivasPag: " + e.getMessage());
            e.printStackTrace();
            mostrarGestaoSessaoMain(); // Fallback to gestão sessões page
        }
    }

    public static void mostrarArquivoSessoes() {
        try {
            ArquivoSessoesPag arquivoSessoesPag = new ArquivoSessoesPag(cinema, Main::mostrarGestaoSessaoMain);
            JPanel panel = arquivoSessoesPag.getMainPanel();
            
            if (panel == null) {
                System.err.println("Erro: mainPanel é null na ArquivoSessoesPag");
                panel = createFallbackPanel("Erro ao carregar Arquivo de Sessões");
            }
            
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
        } catch (Exception e) {
            System.err.println("Erro ao mostrar ArquivoSessoesPag: " + e.getMessage());
            e.printStackTrace();
            mostrarGestaoSessaoMain(); // Fallback to gestão sessões page
        }
    }

    public static void mostrarAlugarFilmePag() {
        try {
            AlugarFilmePag alugarFilmePag = new AlugarFilmePag(cinema, Main::mostrarGestaoSessaoMain);
            JPanel panel = alugarFilmePag.getMainPanel();
            
            if (panel == null) {
                System.err.println("Erro: mainPanel é null na AlugarFilmePag");
                panel = createFallbackPanel("Erro ao carregar Alugar Filmes");
            }
            
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
        } catch (Exception e) {
            System.err.println("Erro ao mostrar AlugarFilmePag: " + e.getMessage());
            e.printStackTrace();
            mostrarGestaoSessaoMain(); // Fallback to gestão sessões page
        }
    }

    // Helper method to create fallback panels
    private static JPanel createFallbackPanel(String message) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(-11685428));
        
        JLabel errorLabel = new JLabel(message, SwingConstants.CENTER);
        errorLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        errorLabel.setForeground(Color.WHITE);
        panel.add(errorLabel, BorderLayout.CENTER);
        
        return panel;
    }
}