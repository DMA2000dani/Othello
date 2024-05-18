package domini.controladors;

import domini.classes.CASELLA;
import domini.classes.Partida;
import domini.classes.Tauler;
import domini.shared.Color;
import domini.shared.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class IO {

    public static ArrayList<String> llegeixFitxers(final File directori) {
        ArrayList<String> s = new ArrayList<>();
        for (final File fileEntry : Objects.requireNonNull(directori.listFiles())) {
            if (fileEntry.isDirectory()) {
                llegeixFitxers(fileEntry);
            } else {
                s.add(fileEntry.getName());
            }
        }
        return s;
    }

    public static void llegeixTauler(Tauler tauler, String path) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader(path));
        int fn = 0; //fitxesN
        int fb = 0;//fitxesB
        int ch;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                Color col;
                ch = r.read();
                if (ch == 10) {
                    ch = r.read();
                }
                if (ch == 63)  col = Color.Buit;
                else if (ch == 78) {
                    col = Color.Negre;
                    ++fn;
                }
                else {
                    col = Color.Blanc;
                    ++fb;
                }
                tauler.getTauler()[i][j].setcolor(col);
            }
        }
        tauler.setFitxesN(fn);
        tauler.setFitxesB(fb);
    }

    public static void print(CASELLA[][] board) {
        System.out.println("   a  b  c  d  e  f  g  h");
        for (int i = 0; i < 8; ++i) {
            System.out.print(8-i + "  ");
            for (int j = 0; j < 8; ++j) {
                switch(board[i][j].getcolor()) {
                    case Blanc:
                        System.out.print("B  ");
                        break;
                    case Negre:
                        System.out.print("N  ");
                        break;
                    case Buit:
                        System.out.print("?  ");
                        break;
                }
            }
            System.out.println(" ");
        }
    }

    public static void print(Pair p) {
        System.out.print("(");
        System.out.print(p.getKey());
        System.out.print(",");
        System.out.print(p.getValue());
        System.out.print(")\n");
    }

    public static void print(String s) {
        System.out.println(s);
    }

    public static void printNoBreak(String s) {
        System.out.print(s);
    }

    public static String read() throws IOException {
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        return bfr.readLine();
    }

    public static void print(int i) {
        System.out.println(i);
    }

    public static void printNoBreak(int i) {
        System.out.print(i);
    }

    public static void printTorn(boolean torn) {
        if (torn) print( "Juguen les NEGRES" );
        else print( "Juguen les BLANQUES" );
    }

    public static void printJugadorsParitda(Partida p) {
        print( "Jugadors:" );
        print( "- Negres: " + p.getJugadorN().getIdJugador() );
        print( "- Blanques: " + p.getJugadorB().getIdJugador() );
    }

    public static void printFitxesPartida(Partida p) {
        print("Nombre de fitxes:");
        print( "- Negres: " + p.getTauler().getFitxesN() );
        print( "- Blanques: " + p.getTauler().getFitxesB());
    }
}