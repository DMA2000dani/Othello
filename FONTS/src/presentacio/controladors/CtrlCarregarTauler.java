package presentacio.controladors;

import domini.controladors.CtrlDominiCarregarTauler;
import domini.shared.TaulerPair;
import presentacio.vistes.VistaCarregarTauler;

import java.io.IOException;
import java.util.ArrayList;

public class CtrlCarregarTauler {
    VistaCarregarTauler VCT;
    CtrlDominiCarregarTauler CDCT;

    public CtrlCarregarTauler (VistaCarregarTauler VCT2) {
        this.CDCT = new CtrlDominiCarregarTauler();
        VCT = VCT2;
    }

    public ArrayList<TaulerPair> obtetaulers() throws IOException {
        return this.CDCT.obtetaulers();
    }

    public boolean esborrarTauler(String nom){
        return CDCT.esborrarTauler(nom);
    }

}
