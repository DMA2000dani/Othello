package presentacio.vistes;

import presentacio.controladors.CtrlJugarPartida;
import presentacio.controladors.CtrlPresentacio;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VistaJugarPartida extends VistaPrincipal {

    private final CtrlJugarPartida ctrlJugarPartida;

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] caselles = new JButton[8][8];
    private JPanel tauler;

    JLabel torn = new JLabel("Negres");
    JButton seguent = new JButton("Següent");
    JButton guardar = new JButton("Guardar");

    public VistaJugarPartida(CtrlPresentacio ctrlPresentacio, CtrlJugarPartida ctrlJugarPartida) {
        super(ctrlPresentacio);
        this.ctrlJugarPartida = ctrlJugarPartida;
        inicialitzarComponents();
    }

    public void ferVisible() {
        super.esborraContinguts();
        gui.setBounds(0,0,700,625);
        frameVista.add(gui);
        frameVista.pack();
        frameVista.setVisible(true);
    }

    public final void inicialitzarComponents() {
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        tauler = new JPanel(new GridLayout(0, 8));
        tauler.setBorder(new LineBorder(Color.BLACK));
        gui.add(tauler);
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_END);
        tools.add(new JLabel("Torn actual: "));
        tools.add(torn);
        tools.add(Box.createHorizontalGlue());
        tools.add(seguent);
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

        caselles[3][3].setBackground(Color.BLACK);
        caselles[3][4].setBackground(Color.WHITE);
        caselles[4][3].setBackground(Color.WHITE);
        caselles[4][4].setBackground(Color.BLACK);

        // Omplir el tauler
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                tauler.add(caselles[ii][jj]);
            }
        }

        activarCaselles();
        assignarListeners();
    }

    private void assignarListeners() {
        seguent.addActionListener(e -> {
            try {
                ctrlJugarPartida.seguentMoviment();
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                cloneNotSupportedException.printStackTrace();
            }
        });
        guardar.addActionListener(e -> ctrlJugarPartida.guardarPartida());
    }

    public void actualitzaTorn() {
        torn.setText(ctrlJugarPartida.torn());
    }

    private void activarCaselles() {
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                int i = ii;
                int j = jj;
                caselles[ii][jj].addActionListener(e -> {
                    if (ctrlJugarPartida.posarFitxa(i, j)) {
                        caselles = ctrlJugarPartida.refrescarTauler(caselles);
                        actualitzaTorn();
                        try {
                            ctrlJugarPartida.seguentMoviment();
                        } catch (CloneNotSupportedException cloneNotSupportedException) {
                            cloneNotSupportedException.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public void refrescarTauler() {
        caselles = ctrlJugarPartida.refrescarTauler(caselles);
    }

    public String missatgeGuardarPartida() {
        if (ctrlJugarPartida.isGuardada()) {
            int result = JOptionPane.showConfirmDialog(null, "Vols mantenir el nom de la partida guardada?", "Sobreescriure", JOptionPane.YES_NO_OPTION);
            if (result == 0) return ctrlJugarPartida.getNomPartida();
        }
        String s = "";
        while(s.equals("")) {
            s = JOptionPane.showInputDialog(null,"Introdueix el nom de la partida", "Guardar partida...", JOptionPane.QUESTION_MESSAGE);
            if (s != null && s.equals("")) {
                JOptionPane.showMessageDialog(null, "Cal introduir un nom", "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
            else return s;
        }
        return JOptionPane.showInputDialog(null,"Introdueix el nom de la partida", "Guardar partida...", JOptionPane.QUESTION_MESSAGE);
    }

    public void missatgeGuardarPartida2Maquines() {
        JOptionPane.showMessageDialog(null, "No es pot guardar una partida entre dues màquines", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public void missatgeGuardarPartidaExit(String nom) {
        JOptionPane.showMessageDialog(null, "S'ha guardat la partida: " + nom, "INFO", JOptionPane.INFORMATION_MESSAGE);
    }

    public void missatgeFinalPartida(String s) {
        JOptionPane.showMessageDialog(null, "S'ha acabat la partida. " + s + "!", "INFO", JOptionPane.INFORMATION_MESSAGE);
        super.esborraContinguts();
        super.ferVisible();
    }

}
