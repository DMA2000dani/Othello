package domini.classes;

import domini.shared.Color;
import domini.shared.Pair;
import domini.shared.Regles;

import java.util.ArrayList;

public class MiniMaxPodes extends MiniMax {


    public MiniMaxPodes(int profunditat, Heuristica heuristica) {
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
                maximActual = valorMiniMaxPodes(t, -10000, 10000, profunditat, canviaTorn(torn), reg, false);
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
     * Retorna el valor heurístic d'un estat de joc aplicant-hi podes
     * @param tauler estat de joc
     * @param alfa Cota inferior del valor que es pot assignar a un node maximitzant
     * @param beta Cota superior del valor que es pot assignar a un node maximitzant
     * @param prof Nivells de profunditat
     * @param torn Color de la casella
     * @return valor heurístic de l'estat del joc
     */
    private int valorMiniMaxPodes(Tauler tauler, int alfa, int beta, int prof, Color torn, Regles reg, boolean maximitzant) throws CloneNotSupportedException {
        if (prof == 0 || tauler.isacabat(reg)) {
            return heuristica.avalua(tauler, torn, reg);
        } else {
            // Es generen tots els moviments possibles en un tauler i per un color de fitxa
            ArrayList<Pair> moveList = generaMoviments(tauler, torn, reg);
            // Per cada un dels moviments es calcula el valor heurístic del tauler resultant
            if (maximitzant) {
                for (Pair move : moveList) {
                    Tauler t = tauler.clone();
                    if (t.posarFitxa(move, torn, reg) > 0) {
                        alfa = Math.max(alfa, valorMiniMaxPodes(t, alfa, beta, prof-1, canviaTorn(torn), reg,false));
                        if (alfa >= beta) return beta;
                    }
                }
                return alfa;
            }
            else {
                for (Pair move : moveList) {
                    Tauler t = tauler.clone();
                    if (t.posarFitxa(move, torn, reg) > 0) {
                        beta = Math.min(beta, valorMiniMaxPodes(t, alfa, beta, prof-1, canviaTorn(torn), reg, true));
                        if (beta <= alfa) return alfa;
                    }
                }
                return beta;
            }
        }
    }

}
