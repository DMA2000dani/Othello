package domini.classes;

import domini.controladors.IO;
import domini.shared.Color;
import domini.shared.Pair;
import domini.shared.Regles;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class MiniMax {

    protected int profunditat;
    protected Heuristica heuristica;

    public MiniMax(int profunditat, Heuristica heuristica) {
        this.profunditat = profunditat;
        this.heuristica = heuristica;
    }

    public int getProfunditat() {
        return profunditat;
    }

    /**
     * Genera tots els moviments possibles (d'una sola jugada) a partir d'un estat inicial i d'un color donat
     * @param tauler Estat del tauler
     * @param color Color de la fitxa que farà els moviments
     * @return Retorna un array amb tots els moviments possibles
     */
    public static ArrayList<Pair> generaMoviments(Tauler tauler, Color color, Regles reg) {
        boolean[][] visitat = new boolean[8][8];
        ArrayList<Pair> moveList = new ArrayList<>();
        LinkedList<Pair> q = new LinkedList<>();
        q.add(new Pair(3,3));
        visitat[3][3] = true;
        Pair p;
        while (!q.isEmpty()) {
            p = q.pop();
            for (Pair direccio : reg.generarDireccions()) {
                int x = p.getKey() + direccio.getKey();
                int y = p.getValue() + direccio.getValue();
                Pair nou = new Pair(x, y);
                if (0 <= x && x <= 7 && 0 <= y && y <= 7 && !visitat[x][y]) {
                    visitat[x][y] = true;
                    if (tauler.posicioValida(nou, color, reg)) {
                        moveList.add(nou);
                    } else if (tauler.getTauler()[x][y].getcolor() != Color.Buit) {
                        q.add(nou);
                    }
                }
            }
        }
        return moveList;
    }

    /**
     * Donat un torn, retorna el torn contrari
     * @param torn Torn actual
     * @return torn contrari
     */
    protected Color canviaTorn(Color torn) {
        if (torn == Color.Buit) { return torn; }
        if (torn == Color.Blanc) { return Color.Negre; }
        return Color.Blanc;
    }

    public abstract Pair decidirJugada(Partida partida) throws CloneNotSupportedException;
}
