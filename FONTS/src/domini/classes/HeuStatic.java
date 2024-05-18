package domini.classes;

import domini.shared.Color;
import domini.shared.Pair;
import domini.shared.Regles;

import java.util.LinkedList;

public class HeuStatic extends Heuristica{
    private static final int[][] pesos = {
            { 4, -3,  2,  2,  2,  2, -3, 4},
            {-3, -4, -1, -1, -1, -1, -4,-3},
            { 2, -1,  1,  0,  0,  1, -1, 2},
            { 2, -1,  0,  1,  1,  0, -1, 2},
            { 2, -1,  0,  1,  1,  0, -1, 2},
            { 2, -1,  1,  0,  0,  1, -1, 2},
            {-3, -4, -1, -1, -1, -1, -4,-3},
            { 4, -3,  2,  2,  2,  2, -3, 4}
    };
    private static final Pair[] direccions = {
            new Pair(-1, 0),
            new Pair(-1, 1),
            new Pair( 0, 1),
            new Pair( 1, 1),
            new Pair( 1, 0),
            new Pair( 1,-1),
            new Pair( 0,-1),
            new Pair(-1,-1)
    };

    /**
     * Funció heurístic estatic. Només es té en compte els pesos d'una matriu auxiliar
     * @param tauler Estat actual del taulell
     * @param player Color del jugador
     * @return Avaluacio heuristica del taulell
     */
    public int avalua(Tauler tauler, Color player, Regles reg) {
        int value = 0;

        boolean[][] visitat = new boolean[8][8];
        LinkedList<Pair> q = new LinkedList<>();
        q.add(new Pair(3,3));
        visitat[3][3] = true;
        Pair p;
        if (tauler.getTauler()[3][3].getcolor() == player) value  += pesos[3][3];
        if (tauler.getTauler()[3][3].getcolor() == oponent(player)) value -= pesos[3][3];
        while (!q.isEmpty()) {
            p = q.pop();
            for (Pair direccio : direccions) {
                int x = p.getKey() + direccio.getKey();
                int y = p.getValue() + direccio.getValue();
                Pair nou = new Pair(x, y);
                if (0 <= x && x <= 7 && 0 <= y && y <= 7 && !visitat[x][y]) {
                    visitat[x][y] = true;
                    Color col = tauler.getTauler()[x][y].getcolor();
                    if (col != Color.Buit) {
                        if (col == player) value += pesos[x][y];
                        else value -= pesos[x][y];
                        q.add(nou);
                    }
                }
            }
        }
        return value;
    }
}
