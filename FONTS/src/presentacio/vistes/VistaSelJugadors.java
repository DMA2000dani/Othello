package presentacio.vistes;

import presentacio.controladors.CtrlJugarPartida;
import presentacio.controladors.CtrlPresentacio;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class VistaSelJugadors extends VistaPrincipal {

    private final CtrlJugarPartida ctrlJugarPartida;

    private String taulerCarregat;

    private final JPanel panellContrincants = new JPanel();
    private final String[] modes = new String[]{"Persona vs Persona", "Persona vs Màquina", "Màquina vs Persona", "Màquina vs Màquina"};
    private final JComboBox<String> modeJoc = new JComboBox<>(modes);
    private final JComboBox<String> maq1 = new JComboBox<>();
    private final JComboBox<String> maq2 = new JComboBox<>();
    private final JButton jugar = new JButton("Jugar");


    // Formulari per a crear una màquina
    private final JPanel creaMaquina = new JPanel();
    private final JTextField nom = new JTextField();
    SpinnerModel value = new SpinnerNumberModel(1, 1, 10, 1);
    private final JSpinner prof = new JSpinner(value);
    ButtonGroup bgroupHeu = new ButtonGroup();
    private final JRadioButton stat = new JRadioButton("Estàtica");
    private final JRadioButton dyn = new JRadioButton("Dinàmica");
    ButtonGroup bgroupPod = new ButtonGroup();
    private final JRadioButton pod = new JRadioButton("Sí");
    private final JRadioButton noPod = new JRadioButton("No");
    private final JButton crear = new JButton("Crea Màquina");


    private final JPanel infoMaquina1 = new JPanel();
    private final JLabel infoProf1 = new JLabel("");
    private final JLabel infoHeur1 = new JLabel("");
    private final JLabel infoPodes1 = new JLabel("");

    private final JPanel infoMaquina2 = new JPanel();
    private final JLabel infoProf2 = new JLabel("");
    private final JLabel infoHeur2 = new JLabel("");
    private final JLabel infoPodes2 = new JLabel("");

    ButtonGroup taulerGp = new ButtonGroup();
    private final JPanel tauler = new JPanel();
    private final JRadioButton taulerDefault = new JRadioButton("Tauler per defecte");
    private final JRadioButton taulerSel = new JRadioButton("Tauler seleccionat");


    public VistaSelJugadors(CtrlPresentacio ctrlPresentacio, CtrlJugarPartida ctrlJugarPartida) {
        super(ctrlPresentacio);
        this.ctrlJugarPartida = ctrlJugarPartida;
        inicialitzarComponents();
    }

    public void ferVisible() {
        tauler.setBounds(20, 20, 670, 100);
        frameVista.add(tauler);
        creaMaquina.setBounds(20,140, 150, 450);
        frameVista.add(creaMaquina);
        panellContrincants.setBounds(190,140, 500, 300);
        frameVista.add(panellContrincants);
        infoMaquina1.setBounds(190, 460, 240, 130);
        frameVista.add(infoMaquina1);
        infoMaquina2.setBounds(450, 460, 240, 130);
        frameVista.add(infoMaquina2);
        frameVista.pack();
        frameVista.setVisible(true);
    }

    public void inicialitzarComponents() {

        tauler.setLayout(new GridLayout(2, 1));
        taulerGp.add(taulerDefault);
        taulerGp.add(taulerSel);
        tauler.add(taulerDefault);
        tauler.add(taulerSel);
        tauler.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Tauler"));

        bgroupHeu.add(stat);
        bgroupHeu.add(dyn);

        JPanel heuristica = new JPanel();
        heuristica.setLayout(new GridLayout(2,1));
        heuristica.add(stat);
        heuristica.add(dyn);
        heuristica.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Heurística"));

        bgroupPod.add(pod);
        bgroupPod.add(noPod);

        JPanel podes = new JPanel();
        podes.setLayout(new GridLayout(2, 1));
        podes.add(pod);
        podes.add(noPod);
        podes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Podes"));

        creaMaquina.setLayout(new GridLayout(7, 1));
        creaMaquina.add(new JLabel("Nom:"));
        creaMaquina.add(nom);
        creaMaquina.add(new JLabel("Profunditat:"));
        creaMaquina.add(prof);
        creaMaquina.add(heuristica);
        creaMaquina.add(podes);
        creaMaquina.add(crear);
        creaMaquina.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Nova màquina"));

        panellContrincants.setLayout(new GridLayout(7, 1));
        panellContrincants.add(new JLabel("Mode de Joc"));
        panellContrincants.add(modeJoc);
        panellContrincants.add(new JLabel("Màquina 1"));
        panellContrincants.add(maq1);
        panellContrincants.add(new JLabel("Màquina 2"));
        panellContrincants.add(maq2);
        panellContrincants.add(jugar);
        panellContrincants.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Jugadors"));

        afegirLabelsMaquina(infoMaquina1, infoProf1, infoHeur1, infoPodes1);
        infoMaquina1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Màquina 1"));

        afegirLabelsMaquina(infoMaquina2, infoProf2, infoHeur2, infoPodes2);
        infoMaquina2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Màquina 2"));

        maq1.setEnabled(false);
        maq2.setEnabled(false);

        String[] maquines = ctrlJugarPartida.obtenirMaquines().toArray(new String[0]);

        for (String maq : maquines) {
            maq1.addItem(maq);
            maq2.addItem(maq);
        }

        taulerCarregat = ctrlJugarPartida.getNomTauler();
        taulerDefault.setSelected(taulerCarregat == null);
        taulerSel.setSelected(taulerCarregat != null);
        if (taulerCarregat != null) taulerSel.setText("Tauler seleccionat: " + taulerCarregat);

        assignarListeners();
    }

    private void assignarListeners() {
        jugar.addActionListener(e -> {
            try {
                if (maq1.getItemCount() == 0) ctrlJugarPartida.iniciarPartidaNova("", "", modeJoc.getSelectedIndex());
                else ctrlJugarPartida.iniciarPartidaNova(
                        Objects.requireNonNull(maq1.getSelectedItem()).toString(),
                        Objects.requireNonNull(maq2.getSelectedItem()).toString(),
                        modeJoc.getSelectedIndex()
                    );
            } catch (CloneNotSupportedException | IOException cloneNotSupportedException) {
                cloneNotSupportedException.printStackTrace();
            }
        });

        crear.addActionListener(e -> {
            if (nom.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Cal introduir un nom per a la màquina", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!stat.isSelected() && !dyn.isSelected()) {
                JOptionPane.showMessageDialog(null, "Cal introduir una heurística", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!pod.isSelected() && !noPod.isSelected()) {
                JOptionPane.showMessageDialog(null, "Cal introduir si es fan podes o no", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int heu;
            if (stat.isSelected()) heu = 1;
            else heu = 2;
            boolean po = pod.isSelected();
            if (ctrlJugarPartida.crearMaquina(nom.getText(), (Integer)prof.getValue(), heu, po)) {
                JOptionPane.showMessageDialog(null, "S'ha creat la màquina correctament", "INFO", JOptionPane.INFORMATION_MESSAGE);
                maq1.addItem(nom.getText());
                maq2.addItem(nom.getText());
            } else {
                JOptionPane.showMessageDialog(null, "Ja existeix una màquina o un perfil amb el mateix nom", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        modeJoc.addActionListener(e -> {
            switch (modeJoc.getSelectedIndex()) {
                case 0:
                    maq1.setEnabled(false);
                    maq2.setEnabled(false);
                    break;
                case 1:
                    if (maquinesSuficients(1)) {
                        maq1.setEnabled(false);
                        maq2.setEnabled(true);
                        actualitzaLabelsMaquina(1, Objects.requireNonNull(maq1.getSelectedItem()).toString(), Objects.requireNonNull(maq2.getSelectedItem()).toString());
                    }
                    break;
                case 2:
                    if (maquinesSuficients(1)) {
                        maq1.setEnabled(true);
                        maq2.setEnabled(false);
                        actualitzaLabelsMaquina(2, Objects.requireNonNull(maq1.getSelectedItem()).toString(), Objects.requireNonNull(maq2.getSelectedItem()).toString());
                    }
                    break;
                case 3:
                    if (maquinesSuficients(2)) {
                        maq1.setEnabled(true);
                        maq2.setEnabled(true);
                        actualitzaLabelsMaquina(3, Objects.requireNonNull(maq1.getSelectedItem()).toString(), Objects.requireNonNull(maq2.getSelectedItem()).toString());
                    }
                    break;
            }
        });

        maq1.addActionListener(e -> {
            if (maq1.getSelectedItem() != null && maq2.getSelectedItem() != null) {
                actualitzaLabelsMaquina(2, Objects.requireNonNull(maq1.getSelectedItem()).toString(), Objects.requireNonNull(maq2.getSelectedItem()).toString());
            }
        });
        maq2.addActionListener(e -> {
            if (maq1.getSelectedItem() != null && maq2.getSelectedItem() != null) {
                actualitzaLabelsMaquina(1, Objects.requireNonNull(maq1.getSelectedItem()).toString(), Objects.requireNonNull(maq2.getSelectedItem()).toString());
            }
        });

        taulerDefault.addActionListener(e -> ctrlJugarPartida.setNomTauler(null));

        taulerSel.addActionListener(e -> {
            if (taulerCarregat == null) {
                JOptionPane.showMessageDialog(null, "No hi ha cap tauler carregat", "ERROR", JOptionPane.ERROR_MESSAGE);
                taulerDefault.setSelected(true);
            } else ctrlJugarPartida.setNomTauler(taulerCarregat);
        });
    }

    public void missatgeMaquinesIdentiques() {
        JOptionPane.showMessageDialog(null, "Les dues màquines tenen la mateixa parametrització", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    private boolean maquinesSuficients(int i) {
        if (maq1.getItemCount() < i) {
            JOptionPane.showMessageDialog(null, "No hi ha prou màquines per a aquest mode de joc. Poden crear-se de noves amb el panell de l'esquerra", "ERROR", JOptionPane.ERROR_MESSAGE);
            modeJoc.setSelectedIndex(0);
            return false;
        }
        return true;
    }

    public void missatgePartidaImpossible() {
        JOptionPane.showMessageDialog(null, "No es pot jugar la partida per què donades les regles i el tauler, cap dels jugadors pot col·locar una fitxa. Prova de carregar un tauler diferent o modificar les regles", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    private void afegirLabelsMaquina(JPanel infoMaquina, JLabel infoProf, JLabel infoHeur, JLabel infoPodes) {
        infoMaquina.setLayout(new GridLayout(3, 2));
        infoMaquina.add(new Label("Profunditat:"));
        infoMaquina.add(infoProf);
        infoMaquina.add(new Label("Heurística:"));
        infoMaquina.add(infoHeur);
        infoMaquina.add(new Label("Podes:"));
        infoMaquina.add(infoPodes);
    }

    private void actualitzaLabelsMaquina(int n, String m1, String m2) {
        switch(n) {
            case 1: // Actualitza la segona màquina
                actualitzaLabelMaquina(ctrlJugarPartida.obtenirMaquina(m2), infoProf2, infoHeur2, infoPodes2);
                break;
            case 2: // Actualitza la primera màquina
                actualitzaLabelMaquina(ctrlJugarPartida.obtenirMaquina(m1), infoProf1, infoHeur1, infoPodes1);
                break;
            case 3: // Actualitza la primera i la segona màquina
                actualitzaLabelMaquina(ctrlJugarPartida.obtenirMaquina(m1), infoProf1, infoHeur1, infoPodes1);
                actualitzaLabelMaquina(ctrlJugarPartida.obtenirMaquina(m2), infoProf2, infoHeur2, infoPodes2);
                break;
        }
    }

    private void actualitzaLabelMaquina(ArrayList<String> params, JLabel infoProf, JLabel infoHeur, JLabel infoPod) {
        infoProf.setText(params.get(0));
        if (params.get(1).equals("1")) infoHeur.setText("Estàtica");
        else infoHeur.setText("Dinàmica");
        if (params.get(2).equals("true")) infoPod.setText("Sí");
        else infoPod.setText("No");
    }


}
