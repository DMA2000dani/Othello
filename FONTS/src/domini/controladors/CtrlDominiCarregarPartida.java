package domini.controladors;

import persistencia.Gestors.GestorPartida;

import java.io.IOException;
import java.util.ArrayList;

public class CtrlDominiCarregarPartida {
    private GestorPartida GP;

    public CtrlDominiCarregarPartida() {
        this.GP = new GestorPartida();
    }

    public ArrayList<ArrayList<String> > obtepartides(String usuari) throws IOException {
        ArrayList<String> snompartides = new ArrayList<String>();
        ArrayList<ArrayList<String> > spartides = new ArrayList<ArrayList<String> >();
        if( !usuari.equals("") && this.GP.getAllGames(usuari) != null) {
            snompartides = (ArrayList<String>) this.GP.getAllGames(usuari).clone();

            for (int i = 0; i < snompartides.size(); ++i) {
                spartides.add(i, GP.loadGame(usuari, snompartides.get(i)));
            }
        }
        return spartides;
    }

    public void esborrarPartida(String usuari, String nomP) {
        this.GP.deleteGame(usuari, nomP);
    }
}
