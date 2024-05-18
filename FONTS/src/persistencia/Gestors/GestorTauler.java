package persistencia.Gestors;

import domini.shared.TaulerPair;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class GestorTauler {

    private final String path = "./data/Tauler";

    /**
     * Constructora per defecet
     */
    public GestorTauler() {}

    /**
     * Métode per guardar un tauler
     * @param nom Nom del tauler
     * @param matriu Tauler
     * @param overwrite Indica si s'ha de sobrescriure al fitxer si ja existeix un amb nom "nom"
     * @return CERT si l'operació ha tingut exit, FALS en altre cas
     * @throws IOException
     */
    public boolean guardarTauler(String nom, String[][] matriu, boolean overwrite) throws IOException {
        File nou = new File(path + "/" + nom + ".txt");
        if (nou.createNewFile() || overwrite) {
            escriureFitxer(nou, matriu);
            System.out.println("File created: " + nou.getName());
            return true;
        } else {
            System.out.println("File already exists.");
            return false;
        }
    }

    /**
     * Métode per carregar un tauler existent donat el seu nom
     * @param nom Nom del tauler
     * @return Tauler amb nom "nom"
     * @throws IOException
     */
    public TaulerPair carregarTauler(String nom) throws IOException {
        File dir = new File(path);
        TaulerPair tp = null;
        File[] fileList = dir.listFiles();
        if (fileList != null) {
            for (File fileEntry : Objects.requireNonNull(dir.listFiles())) {
                String name = fileEntry.getName().substring(0, fileEntry.getName().lastIndexOf('.'));
                if (name.equals(nom)) {
                    String[][] matriu = llegeixTauler(fileEntry.getName());
                    tp = new TaulerPair(name, matriu);
                    break;
                }
            }
        }
        return tp;
    }

    /**
     * Métode per carregar un llistat de taulers
     * @return Llistat de taulers
     * @throws IOException
     */
    public ArrayList<TaulerPair> carregarTaulers() throws IOException {
        File dir = new File(path);
        ArrayList<TaulerPair> tps = new ArrayList<>();
        File[] fileList = dir.listFiles();
        if (fileList != null) {
            for (File fileEntry : fileList) {
                String name = fileEntry.getName().substring(0, fileEntry.getName().lastIndexOf('.'));
                String[][] matriu = llegeixTauler(fileEntry.getName());
                TaulerPair tp = new TaulerPair(name, matriu);
                tps.add(tp);
            }
        }
        return tps;
    }

    /**
     * Métode per eliminar un tauler de l'alplicació donat el seu nom
     * @param nom Nom del tauler
     * @return TRUE si s'ha eliminat, FALSE si no s'ha pogut eliminar
     */
    public boolean esborrarTauler(String nom) {
        File file = new File(path + "/" + nom + ".txt");
        return file.delete();
    }

    /**
     * Métode privat per llegir un tauler doant el seu nom
     * @param nom Nom del tauler
     * @return Tauler
     * @throws IOException
     */
    private String[][] llegeixTauler(String nom) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader(path + "/" + nom));
        String[][] tauler = new String[8][8];
        int ch;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                ch = r.read();
                if (ch == 10) {
                    ch = r.read();
                }
                if (ch == 63) tauler[i][j] = "?";
                else if (ch == 78) {
                    tauler[i][j] = "N";
                }
                else {
                    tauler[i][j] = "B";
                }
            }
        }
        return tauler;
    }

    /**
     * Métode privat per escriure un Tauler a un fitxer
     * @param file Fitxer on es vol escriure
     * @param matriu Tauler
     * @throws IOException
     */
    private void escriureFitxer(File file, String[][] matriu) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                writer.write(matriu[i][j]);
            }
            writer.write("\n");
        }
        writer.close();
    }
}
