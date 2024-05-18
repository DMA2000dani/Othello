package presentacio.controladors;

import domini.controladors.CtrlDominiCarregarPartida;
import domini.controladors.CtrlDominiCarregarTauler;
import domini.shared.TaulerPair;
import presentacio.vistes.VistaCarregarPartida;
import presentacio.vistes.VistaCarregarTauler;

import java.io.IOException;
import java.util.ArrayList;

public class CtrlCarregarPartida {
    VistaCarregarPartida VCP;
    CtrlDominiCarregarPartida CDCP;

    public CtrlCarregarPartida (VistaCarregarPartida VCP2) {
        this.CDCP = new CtrlDominiCarregarPartida();
        this.VCP = VCP2;
    }

    public ArrayList<ArrayList<String> > obtepartides(String usuari) throws IOException {
        return this.CDCP.obtepartides(usuari);
    }

    public void esborrar(String perfilLoginat, String s) {
        this.CDCP.esborrarPartida(perfilLoginat, s);
    }

}
