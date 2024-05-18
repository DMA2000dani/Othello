package domini.controladors;

import domini.shared.TaulerPair;
import persistencia.Gestors.GestorTauler;

import java.io.IOException;
import java.util.ArrayList;

public class CtrlDominiCarregarTauler {

    private GestorTauler GT;

    public CtrlDominiCarregarTauler() {
        this.GT = new GestorTauler();
    }

    public ArrayList<TaulerPair> obtetaulers() throws IOException {
        return GT.carregarTaulers();
    }

    public boolean esborrarTauler(String nom) {
        return GT.esborrarTauler(nom);
    }
}
