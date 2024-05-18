package presentacio.vistes;

import presentacio.controladors.CtrlPresentacio;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class VistaPrincipal {

    //NOU BOTO RANKINGS PERFILS NOMES PERSONES!!!

    // Controlador de presentació
    protected CtrlPresentacio ctrlPresentacio;

    // Components de la interfície
    protected static final JFrame frameVista = new JFrame("Othello");

    // Menús
    private final JMenuBar menubarVista = new JMenuBar();
    private final JMenuItem menuPerfil = new JMenuItem("Perfil");
    private final JMenu menuRankings = new JMenu("Rankings");
    private final JMenuItem menuitemGeneral = new JMenuItem("General");
    private final JMenuItem menuitemRecords = new JMenuItem("Records");
    private final JMenuItem menuCarregarPartida = new JMenuItem("Carregar Partida");
    private final JMenu menuConfiguracio = new JMenu("Configuracio Partida");
    private final JMenuItem menuitemModRegles = new JMenuItem("Modificar Regles");
    private final JMenuItem menuitemCrearTauler = new JMenuItem("Crear Tauler");
    private final JMenuItem menuitemCarregarTauler = new JMenuItem("Carregar Tauler");
    private final JMenuItem menuJugar = new JMenuItem("Jugar");

    // Constructora i mètodes públics
    public VistaPrincipal(CtrlPresentacio ctrlPresentacio) {
        this.ctrlPresentacio = ctrlPresentacio;
        inicialitzarComponents();
    }

    public void ferVisible() {
        frameVista.pack();
        frameVista.setVisible(true);
    }

    public void esborraContinguts() {
        frameVista.getContentPane().removeAll();
        frameVista.repaint();
    }

    // Mètodes privats

    private void inicialitzarComponents() {
        inicialitzarFrameVista();
        assignarListenersComponents();
    }

    private void inicialitzarFrameVista() {
        // Mida
        frameVista.setMinimumSize(new Dimension(700, 700));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(false);

        // Posició i operacions per defecte
        frameVista.setLocationRelativeTo(null);
        frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void inicialitzarMenubarVista() {
        menuRankings.add(menuitemGeneral);
        menuRankings.add(menuitemRecords);
        menuConfiguracio.add(menuitemModRegles);
        menuConfiguracio.add(menuitemCrearTauler);
        menuConfiguracio.add(menuitemCarregarTauler);
        menubarVista.add(menuPerfil);
        menubarVista.add(menuRankings);
        menubarVista.add(menuCarregarPartida);
        this.menuCarregarPartida.setPreferredSize(new Dimension(100,30));
        menubarVista.add(menuConfiguracio);
        menubarVista.add(menuJugar);
        frameVista.setJMenuBar(menubarVista);
        frameVista.repaint();
    }

    private void assignarListenersComponents() {
        menuJugar.addActionListener
                (event -> ctrlPresentacio.iniciarPartidaNova());
        menuitemCrearTauler.addActionListener
                (event -> ctrlPresentacio.crearTauler());
        menuitemCarregarTauler.addActionListener
                (event -> ctrlPresentacio.carregarTauler());
        menuCarregarPartida.addActionListener
                (event -> {
                    try {
                        ctrlPresentacio.carregarPartida();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        menuitemModRegles.addActionListener
                (event -> ctrlPresentacio.modificarRegles());

        menuitemGeneral.addActionListener(event->ctrlPresentacio.carregarRankingG());

        menuitemRecords.addActionListener(event->ctrlPresentacio.carregarRecords());

        menuPerfil.addActionListener(event->ctrlPresentacio.carregarPerfil());


    }
}
