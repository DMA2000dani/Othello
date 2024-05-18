package presentacio.vistes;

import presentacio.controladors.CtrlCarregarPartida;
import presentacio.controladors.CtrlPresentacio;

import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class VistaCarregarPartida extends VistaPrincipal {
    // Controlador de presentació
    private CtrlPresentacio CP;
    // Controlador CarregarTauler
    private CtrlCarregarPartida CCP;

    //Atributs

    String nomPartidaSel;

    //components vista
    private JScrollPane scroll2;
    private JPanel panel;

    // per a la creació dels taulers i noms de la vista
    private ArrayList<JPanel> taulersP;
    private ArrayList<ArrayList<String> > infoP;

    // pixels per al proxima partida a crear
    private int x;
    private int y;



    public VistaCarregarPartida(CtrlPresentacio CP2, CtrlCarregarPartida CCP2) throws IOException {
        super(CP2);
        this.nomPartidaSel = "";
        this.CP = CP2;
        this.CCP = CCP2;
        inicialitzarFrameVista();
        this.infoP = new ArrayList<ArrayList<String>>();
        this.taulersP = new ArrayList<JPanel>();

        //Creació dels taulers de la partida
        this.panel = new JPanel();
        visualitzar();
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

    private JPanel crear_imgtauler(ArrayList<String> stauler) {
        JPanel foto_t = new JPanel();
        foto_t.setLayout(null);
        // Crear les caselles del tauler
        for (int i = 3; i < 11; i++) {
            for (int j = 0; j < 8; j++) {
                JButton b = new JButton();
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                if (String.valueOf(stauler.get(i).charAt(j)).equals("?")) b.setBackground(new Color(1, 50, 32));
                else if (String.valueOf(stauler.get(i).charAt(j)).equals("N")) b.setBackground(Color.BLACK);
                else if(String.valueOf(stauler.get(i).charAt(j)).equals("B")) b.setBackground(Color.WHITE);
                foto_t.add(b);
                b.setBounds(j * 25, (i-3) * 25, 25, 25);
            }
        }
        return foto_t;
    }


    private void afegir_partida(JPanel tauler, int index) {
        //inicialitza elements
        JButton Esborra = new JButton();
        Esborra.setText(" Esborra");
        JButton Selecciona = new JButton();
        Selecciona.setText("Selecciona");
        JLabel nomP = new JLabel(infoP.get(index).get(0));
        //regles
        JLabel lRegles = new JLabel("Regles: ");
        JLabel lvertical = new JLabel("- vertical");
        JLabel lhoritzontal = new JLabel("- horitzontal");
        JLabel ldiagonal = new JLabel("- diagonal");
        JLabel J1 = new JLabel("Jugador1: " + infoP.get(index).get(1));
        JLabel J2 = new JLabel("Jugador2: " + infoP.get(index).get(2));



        //afegir listeners
        Selecciona.addActionListener(event -> {
            // gestionar el seleccionar Partida
            nomPartidaSel = infoP.get(index).get(0);
            ctrlPresentacio.iniciarPartidaGuardada(nomPartidaSel);
        });
        Esborra.addActionListener(event -> { // però només borra de manera local
            esborraContinguts();
            // Si es vol esborrar la partida seleccionada, actualitualitzem el nom de la partida a ""
            if(nomPartidaSel.equals(infoP.get(index).get(0))) nomPartidaSel = "";

            this.scroll2 = new JScrollPane();
            this.panel = new JPanel();
            this.CCP.esborrar(CP.getPerfilLoginat(), this.infoP.get(index).get(0));
            try {
                actualitzarPartides();
            } catch (IOException e) {
                e.printStackTrace();
            }
            frameVista.add(this.scroll2);
            ferVisible();
        });

        // posiciona elements

        nomP.setBounds(x + 50, y - 50, 200, 50);
        tauler.setBounds(x - 25, y, 200, 200);
        J1.setBounds(x,y + 200, 300, 50);
        J2.setBounds(x,y+ 230, 300, 50);
        lRegles.setBounds(x, y + 266, 75, 50);
        ldiagonal.setBounds(x + 75, y +266, 100, 50);
        lhoritzontal.setBounds(x + 75, y + 291, 100, 50);
        lvertical.setBounds(x + 75, y + 316, 100, 50);
        Selecciona.setBounds(x, y + 368, 150, 50);
        Esborra.setBounds(x , y +418, 150, 50);

        //afegeix elements
        this.panel.add(Esborra);
        this.panel.add(Selecciona);
        this.panel.add(nomP);
        this.panel.add(tauler);
        this.panel.add(J1);
        this.panel.add(J2);
        this.panel.add(lRegles);
        if(infoP.get(index).get(14).charAt(0) == 'T') this.panel.add(ldiagonal);
        if(infoP.get(index).get(14).charAt(2) == 'T') this.panel.add(lvertical);
        if(infoP.get(index).get(14).charAt(1) == 'T')this.panel.add(lhoritzontal);
    }

    private void visualitzar() {
        //afegir taulers
        this.taulersP = new ArrayList<JPanel>();
        for(int i = 0; i < infoP.size(); ++i) {
            this.taulersP.add(i, crear_imgtauler(infoP.get(i)));
        }
        //inicialitzar panel
        this.panel.setLayout(null);
        this.panel.setBackground(Color.GRAY);

        //Les següents dues línees, no son redundants, si s'elimina 1, no es visualitzaria com hauria de visualitzar-se,
        this.panel.setBounds(0,0, 350 * infoP.size(), 635); // en aquesta línea es posiciona el panell
        this.panel.setPreferredSize(new Dimension(350*infoP.size(), 635)); // Aquesta és per a que el JScrollPane
        //S'apiga la mida que ha de poder visualitzar

        //afegir elements al panel
        this.x = 50;
        this.y = 100;
        for(int i = 0; i < this.infoP.size(); ++i) {
            afegir_partida(this.taulersP.get(i), i);
            this.x += 350;
        }
        //crear un JscrollPane per a visualitzar tot el this.panel en el JFrame
        this.scroll2 = new JScrollPane(this.panel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.scroll2.setBounds(0,0,700, 635);
    }
    public void actualitzarPartides() throws IOException {
        this.infoP = (ArrayList<ArrayList<String>>) this.CCP.obtepartides(CP.getPerfilLoginat()).clone();
        visualitzar();
    }

    public String getNomPartidaSel() {return this.nomPartidaSel;}

    public void ferVisible() {
        frameVista.add(this.scroll2);
        frameVista.pack();
        frameVista.setVisible(true);
    }

}
