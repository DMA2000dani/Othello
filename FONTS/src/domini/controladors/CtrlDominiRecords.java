package domini.controladors;

import persistencia.Gestors.GestorRecords;

public class CtrlDominiRecords {
    GestorRecords gestorRecords;

    public CtrlDominiRecords(){
        gestorRecords = new GestorRecords();
    }

    public String obteMillorJugada(){
        return gestorRecords.getHighlight();
    }

    public String obteWR(){
        return gestorRecords.getWR();
    }

    public String obteVictores(){
        return gestorRecords.getVictory();
    }

    public void actualitzarMillorJugada(String jugador, Integer millorJugada){
        gestorRecords.updateHighlight(jugador, millorJugada);
    }

    public void actualitzarWR(String jugador, Double WR){
        gestorRecords.updateWR(jugador, WR);
    }

    public void actualitzarVictories(String jugador, Integer victories){
        gestorRecords.updateVictory(jugador, victories);
    }
}
