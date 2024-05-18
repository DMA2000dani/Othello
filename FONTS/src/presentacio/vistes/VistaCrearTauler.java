package presentacio.vistes;

import presentacio.controladors.CtrlCrearTauler;
import presentacio.controladors.CtrlPresentacio;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class VistaCrearTauler extends VistaPrincipal {

    private CtrlCrearTauler ctrlCrearTauler;

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] caselles = new JButton[8][8];
    private JPanel tauler;

    JButton esborrar = new JButton("Esborrar");
    JButton guardar = new JButton("Guardar");

    public VistaCrearTauler(CtrlPresentacio ctrlPresentacio, CtrlCrearTauler ctrlCrearTauler) {
        super(ctrlPresentacio);
        this.ctrlCrearTauler = ctrlCrearTauler;
        inicialitzarComponents();
    }

    public void ferVisible() {
        gui.setBounds(0,0,700,625);
        frameVista.add(gui);
        frameVista.pack();
        frameVista.setVisible(true);
    }

    public void inicialitzarComponents() {
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));

        tauler = new JPanel(new GridLayout(0, 8));
        tauler.setBorder(new LineBorder(Color.BLACK));
        gui.add(tauler);
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_END);
        tools.add(Box.createHorizontalGlue());
        tools.add(esborrar);
        tools.add(guardar);

        // Crear les caselles del tauler
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < caselles.length; ii++) {
            for (int jj = 0; jj < caselles[ii].length; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                b.setBackground(new Color(1, 50, 32));
                caselles[jj][ii] = b;
            }
        }

        pintarCentre();

        // Omplir el tauler
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                tauler.add(caselles[ii][jj]);
                int i = ii;
                int j = jj;
                caselles[ii][jj].addActionListener(e -> {
                    if (caselles[i][j].getBackground() == Color.BLACK) {
                        caselles[i][j].setBackground(Color.WHITE);
                        ctrlCrearTauler.canviarColorCasella(i, j, "B");
                    }
                    else if (caselles[i][j].getBackground() == Color.WHITE) {
                        if (isQuadratCentral(i, j)) {
                            caselles[i][j].setBackground(Color.BLACK);
                            ctrlCrearTauler.canviarColorCasella(i, j, "N");
                        }
                        else {
                            caselles[i][j].setBackground(new Color(1, 50, 32));
                            ctrlCrearTauler.canviarColorCasella(i, j, "?");
                        }
                    } else {
                        caselles[i][j].setBackground(Color.BLACK);
                        ctrlCrearTauler.canviarColorCasella(i, j, "N");
                    }
                });
            }
        }
        assignarListeners();
    }

    private void pintarCentre() {
        caselles[3][3].setBackground(Color.BLACK);
        caselles[3][4].setBackground(Color.WHITE);
        caselles[4][3].setBackground(Color.WHITE);
        caselles[4][4].setBackground(Color.BLACK);
    }

    private boolean isQuadratCentral(int i, int j) {
        return 3 <= i && i <= 4 && 3 <= j && j <= 4;
    }

    private void assignarListeners() {
        // Es recupera la matriu amb les fitxes inicials
        esborrar.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(null, "Vols netejar el tauler?", "Esborrar", JOptionPane.YES_NO_OPTION) == 0) {
                for (int ii = 0; ii < 8; ii++) {
                    for (int jj = 0; jj < 8; jj++) {
                        caselles[ii][jj].setBackground(new Color(1, 50, 32));
                    }
                }
                pintarCentre();
                ctrlCrearTauler.inicialitzarMatriu();
            }
        });

        // Es guarda el tauler
        guardar.addActionListener(e -> {
            try {
                ctrlCrearTauler.obrirPopUpGuardar();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public void missatgeErrorNoConnex() {
        JOptionPane.showMessageDialog(null, "El tauler no és connex", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public void missatgeUnColor() {
        JOptionPane.showMessageDialog(null, "El tauler ha de tenir almenys una fitxa de cada color", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public String missatgeNomTauler() {
        String s = "";
        while(s.equals("")) {
            s = JOptionPane.showInputDialog(null,"Introdueix el nom del tauler", "Nou tauler...", JOptionPane.QUESTION_MESSAGE);
            if (s != null && s.equals("")) {
                JOptionPane.showMessageDialog(null, "Cal introduir un nom", "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
            else return s;
        }
        return JOptionPane.showInputDialog(null,"Introdueix el nom del tauler", "Nou tauler...", JOptionPane.QUESTION_MESSAGE);
    }

    public int missatgeNomJaExisteix() {
        return JOptionPane.showConfirmDialog(null, "El tauler ja existeix. Vols sobreescriure'l?", "Sobreescriure", JOptionPane.YES_NO_CANCEL_OPTION);
    }

    public void missatgeGuardatExit(String nom) {
        JOptionPane.showMessageDialog(null, "S'ha guardat el tauler: " + nom, "INFO", JOptionPane.INFORMATION_MESSAGE);
    }

}
