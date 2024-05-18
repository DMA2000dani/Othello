package domini.classes;

import domini.shared.Color;
import domini.shared.Pair;
import domini.shared.Regles;

import java.util.ArrayList;

public class MiniMaxNoPodes extends MiniMax {

    public MiniMaxNoPodes(int profunditat, Heuristica heuristica) {
        super(profunditat, heuristica);
    }


    public Pair decidirJugada(Partida partida) throws CloneNotSupportedException {
        Tauler tauler = partida.getTauler();
        Color torn = partida.getTornColor();
        Regles reg = partida.getRegles();
        ArrayList<Pair> moveList = generaMoviments(tauler, torn, reg);
        Pair millorMoviment = null;
        int maxim = -10000; // TODO: Mirar si es poden utilitzar infinits
        int maximActual;
        for(Pair move : moveList) {
            // S'aplica el moviment al tauler;
            Tauler t = tauler.clone();
            if (t.posarFitxa(move, torn, reg) > 0) {
                // Es calcula el valor de la jugada
                maximActual = valorMiniMax(t, profunditat, canviaTorn(torn), reg, false);
                // Si la jugada és millor que totes les anteriors, s'actualitza el millor moviment
                if (maximActual > maxim) {
                    maxim = maximActual;
                    millorMoviment = move;
                }
            }
        }
        return millorMoviment;
    }

    /**
     * Retorna el valor heurístic d'un estat de joc (sense podes)
     * @param tauler estat de joc
     * @param prof Nivells de profunditat
     * @param torn Color de la casella
     * @return valor heurístic de l'estat del joc
     */
    private int valorMiniMax(Tauler tauler, int prof, Color torn, Regles reg, boolean maximitzant) throws CloneNotSupportedException {
        int millorValor;
        if (prof == 0 || tauler.isacabat(reg)) {
            return heuristica.avalua(tauler, torn, reg);
        } else {
            // Es generen tots els moviments possibles en un tauler i per un color de fitxa
            ArrayList<Pair> moveList = generaMoviments(tauler, torn, reg);
            if (maximitzant) {
                // Node maximitzant
                millorValor = -10000;
                for (Pair move : moveList) {
                    Tauler t = tauler.clone();
                    if (t.posarFitxa(move, torn, reg) > 0) {
                        millorValor = Math.max(millorValor, valorMiniMax(tauler, prof - 1, canviaTorn(torn), reg, false));
                    }
                }
                return millorValor;
            }
            else {
                // Node minimitzant
                millorValor = 10000;
                for (Pair move : moveList) {
                    Tauler t = tauler.clone();
                    if (t.posarFitxa(move, torn, reg) > 0) {
                        millorValor = Math.min(millorValor, valorMiniMax(tauler, prof - 1, canviaTorn(torn), reg,true));
                    }
                }
                return millorValor;
            }
        }
    }

}
