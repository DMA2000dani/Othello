package presentacio.vistes;

//package presentacio.controladors.CtrlRecords;

import presentacio.controladors.CtrlPresentacio;
import presentacio.controladors.CtrlRecords;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Objects;


public class VistaRecords extends VistaPrincipal{
    //Controlador de presentacio
    private CtrlPresentacio CP;

    //Controlador Record
    private CtrlRecords CR;

    protected static final JFrame framevista = new JFrame("RECORDS");

    //Làmina per a Winrate
    private final JPanel panel_winrate = new JPanel();
    private JLabel nomwinrate = new JLabel("senyor1");
    private JLabel textwinrate = new JLabel("%Victories/Derrotes:");
    private JLabel numero_winrate = new JLabel("9999");

    //Làmina per a Bestplay
    private final JPanel panel_bestplay = new JPanel();
    private JLabel nombestplay = new JLabel("senyor2");
    private JLabel textbestplay = new JLabel("Nº fitxes capturades en un moviment:");
    private JLabel numero_bestplay = new JLabel("8888");

    //Laminia per victories
    private final JPanel panel_victories = new JPanel();
    private JLabel nomvictories = new JLabel("senyor3");
    private JLabel textvictories = new JLabel("Nº Victories:");
    private JLabel numero_victories = new JLabel("7777");



    public VistaRecords(CtrlPresentacio CP2, CtrlRecords ctrlRecords) {
        super(CP2);
        this.CP = CP2;
       inicialitzar();
    }


    private void inicialitzar(){

        panel_bestplay.setLayout(new GridLayout(3,1));
        panel_bestplay.add(nombestplay);
        panel_bestplay.add(textbestplay);
        panel_bestplay.add(numero_bestplay);
        panel_bestplay.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Millor_jugada:"));


        panel_victories.setLayout(new GridLayout(3,1));
        panel_victories.add(nomvictories);
        panel_victories.add(textvictories);
        panel_victories.add(numero_victories);
        panel_bestplay.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Més victories:"));

        panel_winrate.setLayout(new GridLayout(3,1));
        panel_winrate.add(nombestplay);
        panel_winrate.add(textbestplay);
        panel_winrate.add(numero_bestplay);
        panel_bestplay.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Millor Winrate"));

    }

    private void inicialitzarFrameVista() {

        // Mida
        frameVista.setMinimumSize(new Dimension(700, 700));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(false);

        // Posició i operacions per defecte
        frameVista.setLocationRelativeTo(null);
        frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameVista.setLayout(null);
    }



    public void ferVisible() {

        panel_winrate.setBounds(20,40, 150, 450);
        frameVista.add(panel_bestplay);
        panel_bestplay.setBounds(190,40, 500, 300);
        frameVista.add(panel_victories);
        panel_bestplay.setBounds(190,40, 500, 300);
        frameVista.add(panel_winrate);
        frameVista.pack();
        frameVista.setVisible(true);
    }
}
