package domini.controladors;

import domini.classes.*;
import domini.shared.Color;
import domini.shared.Pair;
import domini.shared.Regles;
import domini.shared.TaulerPair;
import persistencia.Gestors.GestorMaquina;
import persistencia.Gestors.GestorPartida;
import persistencia.Gestors.GestorTauler;

import java.io.IOException;
import java.util.ArrayList;

import static domini.shared.Constants.PARTIDA_MAP;
import static java.lang.Integer.parseInt;

public class CtrlDominiJugarPartida {

    private final GestorPartida gestorPartida = new GestorPartida();
    private final GestorMaquina gestorMaquina = new GestorMaquina();
    private final GestorTauler gestorTauler = new GestorTauler();

    private Jugador jugN;
    private Jugador jugB;
    private Tauler tauler;
    private Partida partida;
    private int mode;

    private MiniMax algoN;
    private MiniMax algoB;

    public CtrlDominiJugarPartida() {}

    public boolean inicialitzarJugadors(String perfil, String j1, String j2, int mode) {
        this.mode = mode;
        switch(mode) {
            case 0: // Persona vs Persona
                jugN = new Jugador(perfil);
                jugB = new Jugador("Convidat");
                break;
            case 1: // Persona vs Màquina
                jugN = new Jugador(perfil);
                jugB = new Jugador(j2);
                inicialitzarAlgoritme(obtenirMaquina(j2), Color.Blanc);
                break;
            case 2: // Màquina vs Persona
                jugN = new Jugador(j1);
                jugB = new Jugador(perfil);
                inicialitzarAlgoritme(obtenirMaquina(j1), Color.Negre);
                break;
            case 3: // Màquina vs Màquina
                jugN = new Jugador(j1);
                jugB = new Jugador(j2);
                ArrayList<String> m1 = obtenirMaquina(j1);
                ArrayList<String> m2 = obtenirMaquina(j2);
                if (m1.equals(m2)) return false;
                inicialitzarAlgoritme(m1, Color.Negre);
                inicialitzarAlgoritme(m2, Color.Blanc);
                break;
        }

        return true;
    }

    public void inicialitzarTauler(String nom) throws IOException {
        // Si nom és null vol dir que no s'ha seleccionat un tauler en concret i la partida s'inicialitzarà amb un tauler per defecte
        if (nom != null && gestorTauler.carregarTauler(nom) != null) tauler = reconstrueixTauler(gestorTauler.carregarTauler(nom));
        else tauler = new Tauler("temp");
    }

    public void inicialitzarPartida(Regles regles) {
        partida = new Partida("hola", jugN, jugB, tauler, regles);
    }

    public void recuperarPartida(String nomPerfil, String nomPartida) {
        ArrayList<String> p = gestorPartida.loadGame(nomPerfil, nomPartida);
        partida = reconstrueixPartida(nomPerfil, p);
        int millorJugada = parseInt(p.get(PARTIDA_MAP.get("millorJugada")));
        mode = parseInt(p.get(PARTIDA_MAP.get("mode")));
        if (mode == 0 | mode == 1) {
            partida.setMillorJugadaN(millorJugada);
            partida.setMillorJugadaB(0);
        } else if (mode == 2) {
            partida.setMillorJugadaN(0);
            partida.setMillorJugadaB(millorJugada);
        }
    }

    public void gestioTornMaquina() throws CloneNotSupportedException {
        Pair p;
        // Torn negres
        if (partida.getTorn()) {
            p = algoN.decidirJugada(partida);
        }
        // Torn blanques
        else {
            p = algoB.decidirJugada(partida);
        }
        if (p == null) return;
        posarFitxa(p.getKey(), p.getValue());
    }

    public boolean posarFitxa(int i, int j) {
        boolean done = partida.ferJugada(i, j);
        if (done) {
            partida.canviaTorn();
        }
        return done;
    }

    public boolean getTorn() {
        return partida.getTorn();
    }

    public boolean tornMaquina() {
        if (getTorn() && (getMode() == 2 | getMode() == 3)) return true;
        return !getTorn() && (getMode() == 1 | getMode() == 3);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Partida getPartida() {
        return partida;
    }

    public boolean isAcabada() {
        return partida.isAcabada();
    }

    public Color getGuanyador() {
        return partida.getGuanyador();
    }

    public void saltaTorn() {
        partida.canviaTorn();
    }

    public void guardarPartida(int mode, String perfil, String nom) {
        System.out.println(mode);
        int millorJugada = 0;
        if (mode == 0 | mode == 1) millorJugada = partida.getMillorJugadaN();
        else if (mode == 2) millorJugada = partida.getMillorJugadaB();
        gestorPartida.saveGame(
                perfil,
                nom,
                partida.getJugadorN().getIdJugador(),
                partida.getJugadorB().getIdJugador(),
                crearMatriu(),
                partida.isAcabada(),
                partida.getTemps(),
                partida.getTorn(),
                partida.getRegles().toString(),
                millorJugada,
                mode
        );
    }

    public void esborraPartida(String perfil, String nomPartida) {
        gestorPartida.deleteGame(perfil, nomPartida);
    }

    public boolean jugadaImpossible() {
        return !partida.potJugarTornActual();
    }

    public boolean updateAcabada() {
        return partida.updateAcabada();
    }

    private String[][] crearMatriu() {
        String[][] matriu = new String[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (partida.getTauler().getTauler()[i][j].getcolor() == Color.Blanc) {
                    matriu[i][j] = "B";
                } else if (partida.getTauler().getTauler()[i][j].getcolor() == Color.Negre) {
                    matriu[i][j] = "N";
                } else {
                    matriu[i][j] = "?";
                }
            }
        }
        return matriu;
    }

    public boolean crearMaquina(String nom, int prof, int heu, boolean podes) {
        return gestorMaquina.altaMaquina(nom, prof, heu, podes);
    }

    public ArrayList<String> obtenirMaquines() {
        return gestorMaquina.getAllIA();
    }

    public  ArrayList<String> obtenirMaquina(String nom) {
        return gestorMaquina.obteMaquina(nom);
    }

    private void inicialitzarAlgoritme(ArrayList<String> config, Color color) {
        if (config.get(2).equals("true")) { // Si la màquina fa podes
            if (config.get(1).equals("1")) { // Heurística estàtica
                if (color.equals(Color.Negre)) algoN = new MiniMaxPodes(parseInt(config.get(0)), new HeuStatic());
                else algoB = new MiniMaxPodes(parseInt(config.get(0)), new HeuStatic());
            } else { // Heurística dinàmica
                if (color.equals(Color.Negre)) algoN = new MiniMaxPodes(parseInt(config.get(0)), new HeuDynamic());
                else algoB = new MiniMaxPodes(parseInt(config.get(0)), new HeuDynamic());
            }
        } else {
            if (config.get(1).equals("1")) {
                if (color.equals(Color.Negre)) algoN = new MiniMaxNoPodes(parseInt(config.get(0)), new HeuStatic());
                else algoB = new MiniMaxNoPodes(parseInt(config.get(0)), new HeuStatic());
            } else {
                if (color.equals(Color.Negre)) algoN = new MiniMaxNoPodes(parseInt(config.get(0)), new HeuDynamic());
                else algoB = new MiniMaxNoPodes(parseInt(config.get(0)), new HeuDynamic());
            }
        }
    }

    private Tauler reconstrueixTauler(TaulerPair tp) {
        Tauler tauler = new Tauler(tp.getNom());
        String[][] matriu = tp.getMatriu();
        CASELLA[][] caselles = new CASELLA[8][8];
        int fitxesN = 0;
        int fitxesB = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                caselles[i][j] = new CASELLA();
                if (matriu[i][j].equals("N")) {
                    caselles[i][j].setcolor(Color.Negre);
                    ++fitxesN;
                } else if (matriu[i][j].equals("B")) {
                    caselles[i][j].setcolor(Color.Blanc);
                    ++fitxesB;
                } else {
                    caselles[i][j].setcolor(Color.Buit);
                }
            }
        }
        tauler.setFitxesN(fitxesN);
        tauler.setFitxesB(fitxesB);
        tauler.setTauler(caselles);
        return tauler;
    }

    private Partida reconstrueixPartida(String nomPerfil, ArrayList<String> p) {
        String[][] matriu = new String[8][8];
        for (int i = 3; i < 11; ++i) {
            for (int j = 0; j < 8; ++j) {
                matriu[i-3][j] = p.get(i).substring(j, j+1);
            }
        }
        Tauler board = reconstrueixTauler(new TaulerPair(p.get(0), matriu));
        inicialitzarJugadors(nomPerfil, p.get(PARTIDA_MAP.get("idJugadorN")), p.get(PARTIDA_MAP.get("idJugadorB")), parseInt(p.get(PARTIDA_MAP.get("mode"))));
        return new Partida(
                p.get(PARTIDA_MAP.get("idPartida")),
                new Jugador(p.get(PARTIDA_MAP.get("idJugadorN"))),
                new Jugador(p.get(PARTIDA_MAP.get("idJugadorB"))),
                board,
                p.get(PARTIDA_MAP.get("acabada")).equals("true"),
                Long.parseLong(p.get(PARTIDA_MAP.get("temps"))),
                p.get(PARTIDA_MAP.get("torn")).equals("true"),
                new Regles(p.get(PARTIDA_MAP.get("regles")))
        );
    }
}
