
package presentacio.vistes;

import presentacio.controladors.CtrlLogIn;
import presentacio.controladors.CtrlPresentacio;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class VistaLogin extends VistaPrincipal{
    // Controlador de presentació
    private CtrlPresentacio ctrlPresentacio;

    // Controlador Login
    private CtrlLogIn CLI;

    // Components de la interfície
    private JPanel panel = new JPanel();
    private final JTextField textusuari = new JTextField();
    private final JLabel contrasenya = new JLabel("Contrasenya");
    private final JButton resgistrar = new JButton("Registrar");
    private final JButton entra = new JButton("Entra");
    private final JLabel titol = new JLabel("OTHELLO");
    private final JLabel usuari = new JLabel("Usuari");
    private final JPasswordField textcontrasenya = new JPasswordField();
    private final JLabel errors = new JLabel("");


// Constructora
    public VistaLogin(CtrlPresentacio CP, CtrlLogIn CLI2) {
        super(CP);
        this.CLI = CLI2;
        this.ctrlPresentacio = CP;
        inicialitzarFrameVista();
        this.panel.setLayout(null);
        this.panel.setBounds(0,0,700,700);
        inicialitzarvista();
        this.titol.setFont(new Font("Arial", Font.PLAIN, 30));
        afegirlisteners();
        actualitza("");
    }

    private void afegirlisteners() { // per cridar-la, l'estring és buida("")
        this.entra.addActionListener(event1 -> {
            this.CLI.iniciarsessio(this.textusuari.getText(), new String(this.textcontrasenya.getPassword()), ctrlPresentacio);
            frameVista.repaint();
        });
        this.resgistrar.addActionListener(event2 -> {
            if(this.textusuari.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "No pots crear un perfil amb un nom d'usuari buit");
            }
            else {
                this.CLI.registrarse(this.textusuari.getText(), new String(this.textcontrasenya.getPassword()), ctrlPresentacio);
                frameVista.repaint();
            }
        });
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

    private void inicialitzarvista(){
        frameVista.setLayout(null);
        // Posicionar components
        usuari.setBounds(250,300, 100, 100);
        contrasenya.setBounds(250,340, 100, 100);
        textusuari.setBounds(350, 330, 100, 40);
        textcontrasenya.setBounds(350, 375, 100, 40);
        entra.setBounds(100, 500, 100, 50);
        resgistrar.setBounds(500, 500, 100, 50);
        errors.setBounds(200, 225, 500,100);
        titol.setBounds(300, 100, 300, 100);

        this.panel.add(this.usuari);
        this.panel.add(this.contrasenya);
        this.panel.add(this.textusuari);
        this.panel.add(this.textcontrasenya);
        this.panel.add(this.entra);
        this.panel.add(this.resgistrar);
        this.panel.add(this.errors);
        this.panel.add(this.titol);
    }

    public void actualitza(String s) {
        esborracontinguts();
        frameVista.getContentPane().removeAll();
        frameVista.getContentPane().add(this.panel);
        this.errors.setText(s);
    }

    public void esborracontinguts(){
        frameVista.getContentPane().removeAll();
        frameVista.repaint();
    }

    public void ferVisible() {
        //afegir els components al frame
        frameVista.getContentPane().add(this.panel);
        frameVista.pack();
        frameVista.setVisible(true);
    }

}

