package presentacio.vistes;

import presentacio.controladors.CtrlPerfil;
import presentacio.controladors.CtrlPresentacio;

import javax.swing.*;
import java.awt.*;

public class VistaPerfil extends VistaPrincipal{


    private CtrlPresentacio CP;

    private JPanel panel;

    private JLabel nom;
    private JLabel victories;
    private JLabel derrotes;
    private JLabel f_capturades;
    private JLabel f_perdudes;
    private final JLabel tvictories = new JLabel( "Victories");
    private final JLabel tf_capturades= new JLabel("Fitxes capturades");
    private final JLabel tderrotes = new JLabel("Derrotes");
    private final JLabel tf_perdudes = new JLabel("Fitxes perdudes");




    public VistaPerfil(CtrlPresentacio ctrlPresentacio, CtrlPerfil ctrlPerfil) {
        super(ctrlPresentacio);
        this.CP = ctrlPresentacio;
       // visualitzar();

    }

    private void inicialitzarFrameVista() {

        frameVista.setMinimumSize(new Dimension(700, 700));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(false);

        // Posició i operacions per defecte
        frameVista.setLocationRelativeTo(null);
        frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameVista.setLayout(null);
    }


    private void iniciar_components(){

        this.panel = new JPanel();
        JPanel V = new JPanel();
        V.setLayout(new GridLayout(1,1));

        this.nom = new JLabel();
        this.victories = new JLabel();
        this.derrotes = new JLabel();
        this.f_perdudes = new JLabel();

    }

    /*public void ferVisible() {
        panel.setBounds();
    }*/







}
