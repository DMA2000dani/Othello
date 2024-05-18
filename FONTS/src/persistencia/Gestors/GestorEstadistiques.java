package persistencia.Gestors;


import java.util.*;
import java.io.*;
/*public Estadistiques(){
        double puntuacio = 0.0;
        int victories = 0;
        int derrotes = 0;
        int totalPartides = 0;
        int fitxesCapturades = 0;
        int fitxesPerdudes = 0;
        long tempsJugat = 0;
        double puntuacio_ranking = 0.0;

        }*/
public class GestorEstadistiques {
    static String pathStats = "data/Jugadors/";

    /**
     * Constructora per defecte
     */
    public GestorEstadistiques(){

    }

    /**
     * Metode privat per saber si l'usuari user es una persona o una màquina
     * @param user String, indica el jugador
     * @param path Path dels jugadors
     * @return Cert si "user" es una persona, Fals si "user" es una màquina
     */
    private Boolean isHuman(String user, String path){
        File file = new File(path);
        String[] strings = file.list();
        for (String s : strings){
            if(s.equals(user)) return true;
        }
        return false;
    }

    /**
     * PRE: l'usuari "user" existeix
     * @param user String, indica el jugador la cual volem consultar les estadistiques
     * @return Estadistiques del jugador "user".
     */
    public String getEstadistiques(String user){
        String str = "";
        StringBuilder stringBuilder = new StringBuilder();
        try {

            Boolean human = isHuman(user, pathStats + "Perfils/");
            String path;

            if (human) path = pathStats + "Perfils/";//file = new File(pathStats + "Perfils/");
            else path = pathStats + "Maquines/";//file = new File(pathStats + "Maquines/");

            File file = new File(path);
            String[] dirpath = file.list();
            boolean found = false;
            assert dirpath != null;
            for (String s : dirpath) {
                if (s.equals(user)) {
                    found = true;
                    break;
                }
            }

            if (found){
                File userData = new File(path + user + "/stats.txt");
                Scanner scanner = new Scanner(userData);
                while(scanner.hasNext()){
                    stringBuilder.append(scanner.nextLine()).append("\n");
                }
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * PRE: El jugador "user" existeix i per tant les seves estadistiques també
     * @param user Jugador
     * @param stats Noves estadistiques del jugador
     * POST: s'ha actualitzat les estadistiques del jugador
     */
    public void refreshStats(String user, String stats){
        String[] newstats = stats.split(" ");
        try {
            Boolean human = isHuman(user, pathStats + "Perfils/");
            String path;

            if (human) path = pathStats + "Perfils/";
            else path = pathStats + "Maquines/";

            String[] oldstats = new String[8];
            File file = new File(path + user + "/stats.txt");
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNext() && i < oldstats.length){
                oldstats[i] = scanner.nextLine();
                i++;
            }
            scanner.close();

            StringBuilder stringBuilder = new StringBuilder();

            double puntuacio = Double.parseDouble(oldstats[0]) + Double.parseDouble(newstats[0]);
            int victories = Integer.parseInt(oldstats[1]) + Integer.parseInt(newstats[1]);
            int derrotes = Integer.parseInt(oldstats[2]) + Integer.parseInt(newstats[2]);
            int totalPartides = Integer.parseInt(oldstats[3]) + Integer.parseInt(newstats[3]);
            int fitxesFavor = Integer.parseInt(oldstats[4]) + Integer.parseInt(newstats[4]);
            int fitxesContra = Integer.parseInt(oldstats[5]) + Integer.parseInt(newstats[5]);
            long temps = Long.parseLong(oldstats[6]) + Long.parseLong(newstats[6]);
            double puntuacioRank = Double.parseDouble(oldstats[7]) + Double.parseDouble(newstats[7]);

            stringBuilder.append(puntuacio).append("\n").append(victories).append("\n").append(derrotes).append("\n").append(totalPartides).append("\n");
            stringBuilder.append(fitxesFavor).append("\n").append(fitxesContra).append("\n").append(temps).append("\n").append(puntuacioRank);

            FileWriter userData = new FileWriter(file, false);
            userData.write(stringBuilder.toString());
            userData.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
