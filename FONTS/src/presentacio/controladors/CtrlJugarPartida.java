package presentacio.controladors;

import domini.classes.CASELLA;
import domini.controladors.CtrlDominiJugarPartida;
import domini.shared.Color;
import domini.shared.Regles;
import presentacio.vistes.VistaJugarPartida;
import presentacio.vistes.VistaSelJugadors;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class CtrlJugarPartida {

    private String perfil;
    private String nomTauler;
    private Regles regles;
    private String nomPartida;

    private boolean guardada;

    private final CtrlDominiJugarPartida ctrlDominiJugarPartida = new CtrlDominiJugarPartida();
    private VistaJugarPartida vistaJugarPartida;
    private VistaSelJugadors vistaSelJugadors;

    public CtrlJugarPartida(String perfil, String nomTauler, Regles regles) {
        this.perfil = perfil;
        this.nomTauler = nomTauler;
        this.regles = regles;
        guardada = false;
    }

    public CtrlJugarPartida(String perfil, String nomPartida) {
        this.perfil = perfil;
        this.nomPartida = nomPartida;
        guardada = true;
    }

    public void assignarVistes(VistaSelJugadors vistaSelJugadors, VistaJugarPartida vistaJugarPartida) {
        this.vistaSelJugadors = vistaSelJugadors;
        this.vistaJugarPartida = vistaJugarPartida;
    }

    public void iniciarPartidaNova(String m1, String m2, int mode) throws CloneNotSupportedException, IOException {
        ctrlDominiJugarPartida.setMode(mode);
        if (ctrlDominiJugarPartida.inicialitzarJugadors(perfil, m1, m2, mode)) {
            ctrlDominiJugarPartida.inicialitzarTauler(nomTauler);
            ctrlDominiJugarPartida.inicialitzarPartida(regles);
            vistaJugarPartida.ferVisible();
            vistaJugarPartida.refrescarTauler();
            if (ctrlDominiJugarPartida.jugadaImpossible()) {
                if (ctrlDominiJugarPartida.updateAcabada()) {
                    vistaJugarPartida.esborraContinguts();
                    vistaSelJugadors.ferVisible();
                    vistaSelJugadors.missatgePartidaImpossible();
                    return;
                }
                ctrlDominiJugarPartida.saltaTorn();
                vistaJugarPartida.actualitzaTorn();
                seguentMoviment();
            }
        }
        else vistaSelJugadors.missatgeMaquinesIdentiques();
    }

    public void iniciarPartidaGuardada() {
        ctrlDominiJugarPartida.recuperarPartida(perfil, nomPartida);
        vistaJugarPartida.refrescarTauler();
    }

    public void guardarPartida() {
        if (ctrlDominiJugarPartida.getMode() == 3) vistaJugarPartida.missatgeGuardarPartida2Maquines();
        else {
            String nom = vistaJugarPartida.missatgeGuardarPartida();
            if (nom != null) {
                ctrlDominiJugarPartida.guardarPartida(ctrlDominiJugarPartida.getMode(), perfil, nom);
                vistaJugarPartida.missatgeGuardarPartidaExit(nom);
            }
        }
    }

    public void seguentMoviment() throws CloneNotSupportedException {
        if (ctrlDominiJugarPartida.isAcabada()) { // Quan s'acaba la partida
            Color guanyador = ctrlDominiJugarPartida.getGuanyador();
            String s;
            if (guanyador == Color.Blanc) s = "Guanyen les blanques";
            else if (guanyador == Color.Negre) s = "Guanyen les negres";
            else s = "Empat";
            vistaJugarPartida.missatgeFinalPartida(s);
            if (guardada) ctrlDominiJugarPartida.esborraPartida(perfil, nomPartida);
            return;
            // TODO: Envar partida a estadístiques.
        }
        if (ctrlDominiJugarPartida.jugadaImpossible()) {
            ctrlDominiJugarPartida.saltaTorn();
            seguentMoviment();
        }
        if (ctrlDominiJugarPartida.tornMaquina()) { // Si és el torn de la màquina
            ctrlDominiJugarPartida.gestioTornMaquina();
        }
        vistaJugarPartida.refrescarTauler();
        vistaJugarPartida.actualitzaTorn();
        if (ctrlDominiJugarPartida.jugadaImpossible()) {
            ctrlDominiJugarPartida.saltaTorn();
            seguentMoviment();
        }
    }

    public boolean posarFitxa(int i, int j) {
        if (ctrlDominiJugarPartida.tornMaquina()) return false;
        return ctrlDominiJugarPartida.posarFitxa(i, j);
    }


    public JButton[][] refrescarTauler(JButton[][] caselles) {
        CASELLA[][] tauler = ctrlDominiJugarPartida.getPartida().getTauler().getTauler();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (tauler[i][j].getcolor() == Color.Negre) {
                    caselles[i][j].setBackground(java.awt.Color.BLACK);
                } else if (tauler[i][j].getcolor() == Color.Blanc) {
                    caselles[i][j].setBackground(java.awt.Color.WHITE);
                }
            }
        }
        vistaJugarPartida.actualitzaTorn();
        return caselles;
    }

    public boolean isGuardada() {
        return guardada;
    }

    public String getNomPartida() {
        return nomPartida;
    }

    public String getNomTauler() {
        return nomTauler;
    }

    public void setNomTauler(String n) {
        nomTauler = n;
    }

    public String torn() {
        if (ctrlDominiJugarPartida.getTorn()) return "Negres";
        else return "Blanques";
    }

    public boolean crearMaquina(String nom, int prof, int heu, boolean podes) {
        return ctrlDominiJugarPartida.crearMaquina(nom, prof, heu, podes);
    }

    public ArrayList<String> obtenirMaquina(String m) {
        return ctrlDominiJugarPartida.obtenirMaquina(m);
    }

    public ArrayList<String> obtenirMaquines() {
        return ctrlDominiJugarPartida.obtenirMaquines();
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
