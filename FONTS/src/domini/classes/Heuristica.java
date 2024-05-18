package domini.classes;

import domini.shared.Color;
import domini.shared.Pair;
import domini.shared.Regles;

import java.util.LinkedList;


public abstract class Heuristica{

    /**
     * Creadora per defecte de la classe
     */
    public Heuristica() {
    }

    /**
     * Funció per saber el color de l'oponent
     * @param player Color del jugador actual
     * @return Color del jugador oponent
     */
    protected static Color oponent(Color player) {
        Color opnt;
        if (player == Color.Blanc) opnt = Color.Negre;
        else opnt = Color.Blanc;
        return opnt;
    }

    /**
     * Funció abstracte per avaluar l'estat actual del taulell
     * @param board Taulell actual de la partida
     * @param player Color del jugador
     * @param reg Regles de captures del joc
     * @return Valor enter que indica el resultat després d'avaluar l'estat
     */
    public abstract int avalua(Tauler board, Color player, Regles reg);
}
