package domini.controladors;

import domini.shared.Pair;
import domini.shared.Regles;
import persistencia.Gestors.GestorTauler;

import java.io.IOException;
import java.util.LinkedList;

public class CtrlDominiCrearTauler {

    private boolean dosColors;

    private final GestorTauler gestorTauler = new GestorTauler();

    public CtrlDominiCrearTauler() {}

    public boolean guardarTauler(String nom, String[][] matriu, boolean overwrite) throws IOException {
        return gestorTauler.guardarTauler(nom, matriu, overwrite);
    }

    public boolean connex(String[][] matriu) {
        // Comptar fitxes de cada color
        int blanquesTotals = 0;
        int negresTotals = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (matriu[i][j].equals("B")) ++blanquesTotals;
                else if (matriu[i][j].equals("N")) ++negresTotals;
            }
        }

        // Fer un BFS per comptar les fitxes accessibles des del centre. Si no coincideix amb les totals, aleshores el tauler no és connex i per tant no es dona per vàlid
        int blanquesBFS = 0;
        int negresBFS = 0;
        boolean[][] visitat = new boolean[8][8];
        LinkedList<Pair> q = new LinkedList<>();
        q.add(new Pair(3,3));
        visitat[3][3] = true;
        // No és té en compte que pugui no haver una casella buida en aquesta posició ja que a nivell de presentació la interfície no dóna l'opció de deixar el quadre central del tauler buit.
        if (matriu[3][3].equals("B")) ++blanquesBFS;
        else ++negresBFS;
        Pair p;
        // Es creen les regles per defecte, per generar totes les direccions d'exploració per realitzar el BFS, encara que no tingui molt de sentit parlar de regles en aquest context
        Regles reg = new Regles();
        while (!q.isEmpty()) {
            p = q.pop();
            for (Pair direccio : reg.generarDireccions()) {
                int x = p.getKey() + direccio.getKey();
                int y = p.getValue() + direccio.getValue();
                Pair nou = new Pair(x, y);
                if (0 <= x && x <= 7 && 0 <= y && y <= 7 && !visitat[x][y]) {
                    visitat[x][y] = true;
                    if (matriu[x][y].equals("N")) {
                        ++negresBFS;
                        q.add(nou);
                    } else if (matriu[x][y].equals("B")) {
                        ++blanquesBFS;
                        q.add(nou);
                    }
                }
            }
        }
        dosColors = negresTotals != 0 && blanquesTotals != 0;
        return negresBFS == negresTotals && blanquesBFS == blanquesTotals;
    }

    public boolean isDosColors() {
        return dosColors;
    }
}
