package presentacio.vistes;

import domini.shared.TaulerPair;
import presentacio.controladors.CtrlCarregarTauler;
import presentacio.controladors.CtrlPresentacio;

import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class VistaCarregarTauler extends VistaPrincipal{

    // Controlador de presentació
    private CtrlPresentacio CP;
    // Controlador CarregarTauler
    private CtrlCarregarTauler CCT;

    //Atributs

    //components vista
    private JScrollPane scroll2;
    private JPanel panel;

    // per a la creació dels taulers i noms de la vista

    private ArrayList<JPanel> taulersP;
    private ArrayList<String> nomsT;

    private String taulerSeleccioant;

    // pixels per al proxim tauler a crear
    private int x;
    private int y;

    public VistaCarregarTauler(CtrlPresentacio CP2, CtrlCarregarTauler CCT2) throws IOException {
        super(CP2);
        this.CCT = CCT2;
        this.CP = CP2;


        this.taulersP = new ArrayList<JPanel>();
        this.nomsT = new ArrayList<String>();
        this.taulerSeleccioant = null;
        this.panel = new JPanel();

        inicialitzarFrameVista();
        actualitzar_taulers();



    }

    public void actualitzar_taulers() throws IOException {
        if(CCT.obtetaulers() != new ArrayList<TaulerPair>()) {
            ArrayList<TaulerPair> tp = (ArrayList<TaulerPair>) this.CCT.obtetaulers().clone();
            //Inicialitació de taulersP i nomsT a partir dels taulers que ja s'havien fet anteriorment
            this.taulersP.clear();
            this.nomsT.clear();
            for(int i = 0; i < tp.size(); ++i) {
                this.taulersP.add(i, crear_imgtauler(tp.get(i).getMatriu()));
                this.nomsT.add(i, tp.get(i).getNom());
            }
        }
        esborraContinguts();
        this.scroll2 = new JScrollPane();
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

    private void visualitzar() {
        //inicialitzar panel
        this.panel.setLayout(null);
        this.panel.setBackground(Color.GRAY);

        //Les següents dues línees, no son redundants, si s'elimina 1, no es visualitzaria com hauria de visualitzar-se,
        this.panel.setBounds(0,0, 350 * nomsT.size(), 635); // en aquesta línea es posiciona el panell
        this.panel.setPreferredSize(new Dimension(350*nomsT.size(), 635)); // Aquesta és per a que el JScrollPane
                                                                                        //S'apiga la mida que ha de poder visualitzar

        //afegir elements al panel
        this.x = 100;
        this.y = 150;
        for(int i = 0; i < this.nomsT.size(); ++i) {
            afegir_tauler(this.nomsT.get(i), this.taulersP.get(i), i);
            this.x += 350;
        }
        //crear un JscrollPane per a visualitzar-ho tot en el JFrame
        this.scroll2 = new JScrollPane(this.panel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.scroll2.setBounds(0,0,700, 635);
    }

    private JPanel crear_imgtauler(String[][] stauler) {
        JPanel foto_t = new JPanel();
        foto_t.setLayout(null);
        // Crear les caselles del tauler
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                // Creem i odifiquem la icona i el tamany d'un nou botó(seria una casella)
                JButton b = new JButton();
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);

                if (stauler[j][i].equals("?")) b.setBackground(new Color(1, 50, 32));
                else if (stauler[j][i].equals("N")) b.setBackground(Color.BLACK);
                else if (stauler[j][i].equals("B")) b.setBackground(Color.WHITE);
                foto_t.add(b);
                b.setBounds(i * 25, j * 25, 25, 25);
            }
        }
        return foto_t;
    }


    private void afegir_tauler(String nom, JPanel tauler, int index) {
        //inicialitza elements
        JButton Esborra = new JButton();
        Esborra.setText(" Esborra");
        JButton Selecciona = new JButton();
        Selecciona.setText("Selecciona");
        JLabel nomT = new JLabel();
        nomT.setText(nom);
        //afegir listeners
        Selecciona.addActionListener(s -> {
            this.taulerSeleccioant = nomT.getText();
            JOptionPane.showMessageDialog(null, "Has seleccionat el tauler amb nom: " + nom + ". En la proxima partida (si no se selecciona un altre tauler o s’esborra aquest), jugaras amb aquest", "INFO", JOptionPane.INFORMATION_MESSAGE);
        });
        Esborra.addActionListener(e -> {
            System.out.println("El nom de tauler és: " + nom + ("El nom del tauler a esborrar és: " + this.taulerSeleccioant));
            int result = 0;
            if (nom.equals(this.taulerSeleccioant)) {
                result = JOptionPane.showConfirmDialog(null, "Realment vols esborrar el tauler seleccionat?", "Esborrar", JOptionPane.YES_NO_OPTION);
            }
            if(result == 0) {
                if (CCT.esborrarTauler(nom)) {
                    this.taulersP.remove(index);
                    this.nomsT.remove(index);
                    esborraContinguts();
                    this.scroll2 = new JScrollPane();
                    this.panel = new JPanel();
                    visualitzar();
                    frameVista.add(this.scroll2);
                    ferVisible();
                    JOptionPane.showMessageDialog(null, "S'ha esborrat el tauler " + nom, "INFO", JOptionPane.INFORMATION_MESSAGE);
                }
                else JOptionPane.showMessageDialog(null, "Error en esborrar el tauler", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        int midanomTauler = nomT.getText().length();
        // posiciona elements
        Esborra.setBounds(x , y +276, 150, 50);
        Selecciona.setBounds(x, y + 213, 150, 50);
        nomT.setBounds(x - 25, y - 50, midanomTauler * 8, 50);
        tauler.setBounds(x - 25, y, 200, 200);

        //afegeix elements
        this.panel.add(Esborra);
        this.panel.add(Selecciona);
        this.panel.add(nomT);
        this.panel.add(tauler);
    }


    public String getSeleccionat(){return this.taulerSeleccioant;}

    public void ferVisible() {
        frameVista.add(this.scroll2);
        frameVista.pack();
        frameVista.setVisible(true);
    }

}