package domini.classes;

import domini.shared.Color;
import domini.shared.Pair;
import domini.shared.Regles;

public class HeuDynamic extends Heuristica{
    private enum gamePhase {
        earlyGame,
        midGame,
        lateGame
    }

    /**
     * Avaluació segons els corners
     * @param board Estat actual del taulell
     * @param player Color del jugador actual
     * @return Diferencia entre #corners dels dos jugadors
     */
    private int corners(Tauler board, Color player) {
        int minp = 0;
        int maxp = 0;

        boolean corner00, corner07, corner70, corner77;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                corner00 = (i == 0 && j == 0);
                corner07 = (i == 0 && j == 7);
                corner70 = (i == 7 && j == 0);
                corner77 = (i == 7 && j == 7);

                if (corner00 || corner07 || corner70 || corner77){
                    if (board.getTauler()[i][j].getcolor() == player) minp += 1;
                    if (board.getTauler()[i][j].getcolor() == oponent(player)) maxp += 1;
                }
            }
        }
        return (minp - maxp);
    }

    /**
     * Avaluació segons les caselles diagonals adjacent a les cantonades
     * @param board Estat actual del taulell
     * @param player Color del jugador
     * @return Diferencia dels X cells dels jugadors
     */
    private int xSquares(Tauler board, Color player) {
        int minp = 0;
        int maxp = 0;

        if (board.getTauler()[1][1].getcolor() == player) minp += 1;
        if (board.getTauler()[1][6].getcolor() == player) minp += 1;
        if (board.getTauler()[6][1].getcolor() == player) minp += 1;
        if (board.getTauler()[6][6].getcolor() == player) minp += 1;

        if (board.getTauler()[1][1].getcolor() == oponent(player)) maxp += 1;
        if (board.getTauler()[1][6].getcolor() == oponent(player)) maxp += 1;
        if (board.getTauler()[6][1].getcolor() == oponent(player)) maxp += 1;
        if (board.getTauler()[6][6].getcolor() == oponent(player)) maxp += 1;

        return (minp - maxp);
    }

    /**
     * Avaluació segons els primers diagonals a l'inici de la partida
     * @param board Estat actual del taulell
     * @param player Color del jugador
     * @return Diferencia dels primers daigonals a l'inici de la partida dels jugadors
     */
    private int firstDiagonal(Tauler board, Color player) {
        int minp = 0;
        int maxp = 0;

        if (board.getTauler()[2][2].getcolor() == player) minp += 1;
        if (board.getTauler()[2][5].getcolor() == player) minp += 1;
        if (board.getTauler()[5][2].getcolor() == player) minp += 1;
        if (board.getTauler()[5][5].getcolor() == player) minp += 1;

        if (board.getTauler()[2][2].getcolor() == oponent(player)) maxp += 1;
        if (board.getTauler()[2][5].getcolor() == oponent(player)) maxp += 1;
        if (board.getTauler()[5][2].getcolor() == oponent(player)) maxp += 1;
        if (board.getTauler()[5][5].getcolor() == oponent(player)) maxp += 1;

        return (minp - maxp);
    }

    /**
     * Avaluació segons dels laterals adjacents a les cantonades
     * @param board Estat actual del taulell
     * @param player Color del jugador
     * @return Diferencia dels lateras adjacents a les cantonades del jugadors
     */
    private int cSquares(Tauler board, Color player){
        int minp = 0;
        int maxp = 0;

        if (board.getTauler()[0][1].getcolor() == player) minp += 1;
        if (board.getTauler()[1][0].getcolor() == player) minp += 1;
        if (board.getTauler()[0][6].getcolor() == player) minp += 1;
        if (board.getTauler()[1][7].getcolor() == player) minp += 1;
        if (board.getTauler()[6][0].getcolor() == player) minp += 1;
        if (board.getTauler()[7][1].getcolor() == player) minp += 1;
        if (board.getTauler()[6][7].getcolor() == player) minp += 1;
        if (board.getTauler()[7][6].getcolor() == player) minp += 1;

        if (board.getTauler()[0][1].getcolor() == oponent(player)) maxp += 1;
        if (board.getTauler()[1][0].getcolor() == oponent(player)) maxp += 1;
        if (board.getTauler()[0][6].getcolor() == oponent(player)) maxp += 1;
        if (board.getTauler()[1][7].getcolor() == oponent(player)) maxp += 1;
        if (board.getTauler()[6][0].getcolor() == oponent(player)) maxp += 1;
        if (board.getTauler()[7][1].getcolor() == oponent(player)) maxp += 1;
        if (board.getTauler()[6][7].getcolor() == oponent(player)) maxp += 1;
        if (board.getTauler()[7][6].getcolor() == oponent(player)) maxp += 1;


        return (minp - maxp);
    }

    /**
     * Avaluació segons el número de fitxes de cada jugador
     * @param board Estat actual del taulell
     * @param player Color del juagdor
     * @return Diferència de les fitxes dels jugadors
     */
    private int evalDisc(Tauler board, Color player){
        int minp = 0;
        int maxp = 0;

        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (board.getTauler()[i][j].getcolor() == player) minp += 1;
                if (board.getTauler()[i][j].getcolor() == oponent(player)) maxp += 1;
            }
        }

        return (minp - maxp);
    }

    /**
     * Avaluació segons la mobilitat
     * @param board Estat actual del taulell
     * @param player Color del jugador
     * @return Diferència del número de moviments dels jugadors
     */
    private int evalMobility(Tauler board, Color player, Regles reg){
        int minp = 0;
        int maxp = 0;

        for (int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Pair pos = new Pair(i, j);
                if (board.posicioValida(pos, player, reg)) {
                    minp += 1;
                }
                if (board.posicioValida(pos, oponent(player), reg)) {
                    maxp += 1;
                }
            }
        }
        return (minp - maxp);
    }

    /**
     * Avaluació segons les posicions de les fitxes (corners + x-squares + c-squares + first diagonal)
     * @param board Estat actual del taulell
     * @param player Color del jugador
     * @return Valor de l'estat segons la posició
     */
    private int evalPosition(Tauler board, Color player){
        int a = corners(board, player);
        int b = xSquares(board, player);
        int c = cSquares(board, player);
        int d = firstDiagonal(board, player);

        return (1000 * a) + (-500 * b) + (-250 * c) + (100 * d);
    }

    /*private int evalParity(Tauler board, Color player){
        int total = board.getFitxesB() + board.getFitxesN();
        int remindRnd = 64 - total;
        return 0;
    }*/

    /**
     * Funció per saber en quina fase esta la partida
     * @param board Estat actual del taulell
     * @return Retorna la fase de la partida
     */
    private gamePhase getFaseJoc(Tauler board){
        int n = board.getFitxesB() + board.getFitxesN();

        if (n < 20)  return gamePhase.earlyGame;
        else if (n < 45) return gamePhase.midGame;
        else return gamePhase.lateGame;
    }

    /**
     * Funció heurístic dinamic. Es te diferents factors dins d'una partida segons la fase de joc
     * @param board Estat actual del taulell
     * @param player Color del jugador
     * @return Avaluació númeric del taulell board
     */
    public int avalua(Tauler board, Color player, Regles reg) {

        if (board.isacabat(reg)) return 100 * evalDisc(board, player);

        switch (getFaseJoc(board)){
            case earlyGame:
                return evalPosition(board, player) + 50 * evalMobility(board, player, reg);
            case midGame:
                return evalPosition(board, player) + 25 * evalMobility(board, player, reg) + 10 * evalDisc(board, player);
            case lateGame:
                return evalPosition(board, player) + 40 * evalMobility(board, player, reg) + 60 * evalDisc(board, player);
        }
        return 0;
    }
}
