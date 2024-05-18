package domini.classes;

import domini.shared.Color;
import domini.shared.Pair;
import domini.shared.Regles;

public class Partida {

    /* ----- ATRIBUTS ----- */

    private final String idPartida;

    private final Jugador jugadorN;

    private final Jugador jugadorB;

    private final Tauler tauler;

    private boolean acabada = false;

    private long temps = System.currentTimeMillis();

    private boolean torn = true;

    private final Regles regles;

    private Color guanyador = Color.Buit;

    private int millorJugadaN = 0;

    private int millorJugadaB = 0;

    /* ----- MÈTODES ----- */

    /**
     * Constructora per defecte
     */

    public Partida(String idPartida, Jugador jugadorN, Jugador jugadorB, Tauler tauler, Regles regles) {
        this.idPartida = idPartida;
        this.jugadorN = jugadorN;
        this.jugadorB = jugadorB;
        this.tauler = tauler;
        this.regles = regles;
    }

    public Partida(String idPartida, Jugador jugadorN, Jugador jugadorB, Tauler tauler, boolean acabada, long temps, boolean torn, Regles regles) {
        this.idPartida = idPartida;
        this.jugadorN = jugadorN;
        this.jugadorB = jugadorB;
        this.tauler = tauler;
        this.acabada = acabada;
        this.temps = temps;
        this.torn = torn;
        this.regles = regles;
    }

    public String getIdPartida() {
        return this.idPartida;
    }

    public Regles getRegles() {
        return this.regles;
    }

    public Jugador getJugadorB() {
        return jugadorB;
    }

    public Jugador getJugadorN() {
        return jugadorN;
    }

    public Tauler getTauler() {
        return this.tauler;
    }

    public boolean isAcabada() {
        return acabada;
    }

    public void setAcabada(boolean acab) {
        acabada = acab;
    }

    public long getTemps() {
        return this.temps;
    }

    public boolean getTorn() {
        return torn;
    }

    public Color getTornColor() {
        if (torn) return Color.Negre;
        else return Color.Blanc;
    }

    public int getMillorJugadaN() {
        return millorJugadaN;
    }

    public int getMillorJugadaB() {
        return millorJugadaB;
    }

    public void setMillorJugadaN(int millorJugadaN) {
        this.millorJugadaN = millorJugadaN;
    }

    public void setMillorJugadaB(int millorJugadaB) {
        this.millorJugadaB = millorJugadaB;
    }

    public void canviaTorn() {
        this.torn = !this.torn;
    }

    public boolean ferJugada(int i, int j) {
        int capturades = tauler.posarFitxa(new Pair(i, j), bool2col(torn), regles);
        if (torn) millorJugadaN = Math.max(millorJugadaN, capturades);
        else millorJugadaB = Math.max(millorJugadaB, capturades);
        acabada = tauler.isacabat(regles);
        if (acabada) {
            if (tauler.getFitxesN() > tauler.getFitxesB()) guanyador = Color.Negre;
            else if (tauler.getFitxesN() < tauler.getFitxesB()) guanyador = Color.Blanc;
            else guanyador = Color.Buit;
        }
        return capturades > 0;
    }

    public Color getGuanyador() {
        return guanyador;
    }

    private Color bool2col(boolean b) {
        if (b) return Color.Negre;
        else return Color.Blanc;
    }

    public boolean potJugarTornActual() {
        return tauler.hihamoviments(torn, regles);
    }

    public boolean updateAcabada() {
        return tauler.isacabat(regles);
    }
}
