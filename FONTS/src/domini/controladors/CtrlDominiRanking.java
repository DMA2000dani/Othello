package domini.controladors;

import persistencia.Gestors.GestorRanking;

import java.util.ArrayList;
import java.util.Comparator;

public class CtrlDominiRanking {
    GestorRanking gestorRanking;

    public CtrlDominiRanking(){
        gestorRanking = new GestorRanking();
    }

    public String getRanking(){
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String[]> ranking = gestorRanking.getRanking();

        Comparator<String[]> comparator = (jugador1, jugador2) ->
                Double.compare(Double.parseDouble(jugador1[1]), Double.parseDouble(jugador2[2]));
        ranking.sort(comparator);

        for(String[] strings : ranking){
            stringBuilder.append(strings[0]).append(" ").append(strings[1]).append("\n");
        }
        return  stringBuilder.toString();
    }

    public void actualitzarRanking(String jugador, Double puntuacio){
        gestorRanking.refreshRanking(jugador, puntuacio);
    }
}
