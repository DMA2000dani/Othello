package presentacio.vistes;

import domini.shared.Regles;
import presentacio.controladors.CtrlPresentacio;

import javax.swing.*;
import java.awt.*;

public class VistaModificarRegles extends VistaPrincipal {
    private CtrlPresentacio CP;

    //Components del panel
    private JCheckBox horitzontals;
    private JCheckBox diagonals;
    private JCheckBox verticals;
    private JLabel horitzontal;
    private JLabel vertical;
    private JLabel diagonal;
    private JButton general;

    private JLabel errors;

    private JPanel panel;

    public VistaModificarRegles(CtrlPresentacio CP2) {
        super(CP2);
        this.CP = CP2;
        visualitzar("");
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

    private void inicialitzar_components(String error){
        this.panel = new JPanel();
        this.horitzontals = new JCheckBox();
        this.diagonals = new JCheckBox();
        this.verticals = new JCheckBox();
        this.horitzontal = new JLabel("Horitzontals");
        this.vertical = new JLabel("Verticals");
        this.diagonal = new JLabel("Diagonals");
        this.general = new JButton("Per defecte");
        this.errors = new JLabel(error);
        diagonals.doClick();
        verticals.doClick();
        horitzontals.doClick();
    }

    private void visualitzar(String error) {
        esborraContinguts();
        inicialitzarFrameVista();
        inicialitzar_components(error);

        //posicionar components
        this.horitzontals.setBounds(75, 275, 50, 50);
        this.horitzontal.setBounds(50,225, 100, 50);
        this.verticals.setBounds(225, 275, 50, 50);
        this.vertical.setBounds(200,225, 100, 50);
        this.diagonals.setBounds(375, 275, 50, 50);
        this.diagonal.setBounds(350,225, 100, 50);
        this.general.setBounds(500, 250, 150, 50);
        this.errors.setBounds(150, 400, 400, 75);


        // afegir components al panel
        this.panel.setLayout(null);
        this.panel.setBounds(0,0,700,700);
        this.panel.add(horitzontal);
        this.panel.add(horitzontals);
        this.panel.add(vertical);
        this.panel.add(verticals);
        this.panel.add(diagonal);
        this.panel.add(diagonals);
        this.panel.add(general);
        this.panel.add(errors);
        frameVista.add(this.panel);




        //afegir Listeners
        this.general.addActionListener(e -> {
            if(!horitzontals.isSelected()) horitzontals.doClick();
            if(!verticals.isSelected()) verticals.doClick();
            if(!diagonals.isSelected()) diagonals.doClick();
            this.errors.setText("");
        });

        this.horitzontals.addActionListener(e -> {
            errors.setText("");
            if(!horitzontals.isSelected() && !verticals.isSelected() && !diagonals.isSelected())
                visualitzar("No es pot Jugar sense Regles. Selecciona almenys 1.");
        });
        this.verticals.addActionListener(e -> {
            errors.setText("");
            if(!horitzontals.isSelected() && !verticals.isSelected() && !diagonals.isSelected())
                visualitzar("No es pot Jugar sense Regles. Selecciona almenys 1.");
        });
        this.diagonals.addActionListener(e -> {
            errors.setText("");
            if(!horitzontals.isSelected() && !verticals.isSelected() && !diagonals.isSelected())
                visualitzar("No es pot Jugar sense Regles. Selecciona almenys 1.");
        });
    }

    public Regles getRegles() {
        return new Regles(getverticals(), gethorizontals(), getdiagonals());
    }

    public boolean gethorizontals(){
        return this.horitzontals.isSelected();
    }
    public boolean getverticals(){
        return this.verticals.isSelected();
    }
    public boolean getdiagonals(){
        return this.diagonals.isSelected();
    }

    public void ferVisible() {
        frameVista.add(this.panel);
        frameVista.pack();
        frameVista.setVisible(true);
        JOptionPane.showMessageDialog(null, "Escull les regles que vulguis, quan les hagis triat (és guarden automàticament), ja podràs jugar amb aquestes", "INFO", JOptionPane.INFORMATION_MESSAGE);
    }
}
