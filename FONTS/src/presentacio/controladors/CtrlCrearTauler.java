package presentacio.controladors;

import domini.controladors.CtrlDominiCrearTauler;
import presentacio.vistes.VistaCrearTauler;

import java.io.IOException;

public class CtrlCrearTauler {

    private CtrlDominiCrearTauler ctrlDominiCrearTauler = new CtrlDominiCrearTauler();

    private VistaCrearTauler vistaCrearTauler;

    private String[][] tauler = new String[8][8];

    public CtrlCrearTauler() {
        inicialitzarMatriu();
    }

    public void assignarVistes(VistaCrearTauler vistaCrearTauler) {
        this.vistaCrearTauler = vistaCrearTauler;
    }

    public void inicialitzarMatriu() {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                tauler[i][j] = "?";
            }
        }
        tauler[3][3] = "N";
        tauler[4][4] = "N";
        tauler[3][4] = "B";
        tauler[4][3] = "B";
    }

    public void canviarColorCasella(int i, int j, String s) {
        tauler[i][j] = s;
    }

    public void obrirPopUpGuardar() throws IOException {
        if (!ctrlDominiCrearTauler.connex(tauler)) {
            vistaCrearTauler.missatgeErrorNoConnex();
        }
        else {
            if (!ctrlDominiCrearTauler.isDosColors()) vistaCrearTauler.missatgeUnColor();
            else {
                String nom = vistaCrearTauler.missatgeNomTauler();
                if (nom != null) guardarTauler(nom);
            }
        }
    }

    public void guardarTauler(String nom) throws IOException {
        if (!ctrlDominiCrearTauler.guardarTauler(nom, tauler, false)) { // El fitxer ja existeix
            if (vistaCrearTauler.missatgeNomJaExisteix() == 0) {
                if (ctrlDominiCrearTauler.guardarTauler(nom, tauler, true)) {
                    vistaCrearTauler.missatgeGuardatExit(nom);
                }
            }
        }
        else vistaCrearTauler.missatgeGuardatExit(nom);
    }
}
