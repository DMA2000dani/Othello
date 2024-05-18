package persistencia.Gestors;
import java.util.*;
import java.io.*;

public class GestorPartida {
    static String pathUsers = "data/Jugadors/Perfils/";

    /**
     * Mètode privat per eliminar recurisvament un directori
     * @param file
     */
    private void deleteDirRec(File file){
        try {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteDirRec(f);
                }
            }
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructora per defecte
     */
    public GestorPartida(){

    }

    /**
     * Mètode per guardar les dades d'una partida
     * @param user Jugador que guarda la partida
     * @param idg ID de la partida
     * @param p1 Jugador 1 de la partida
     * @param p2 Jugador 2 de la partida
     * @param board Tauler de la partida
     * @param finish Indica si la partida s'ha acabat o no
     * @param time Indica el temps de la partida
     * @param torn Indica el torn de la partida
     * @param rules Indica les regles de la partida
     * @param jugada Indica la millor jugada en aquell moment de la partida
     * @param mode Indica el mode de joc de la partida (Persona vs Persona, Persona vs Maquina, ...)
     */
    public void saveGame(String user, String idg, String p1, String p2, String[][] board, Boolean finish, long time, Boolean torn, String rules, Integer jugada, Integer mode){
        try{
            boolean created = false;
            File file = new File(pathUsers + user + "/Partides/");
            String[] strings = file.list();
            for(String s : strings){
                if(s.equals(idg)) {
                    created = true;
                    break;
                }
            }
            if(!created) {
                File file1 = new File(pathUsers + user + "/Partides/" + idg);
                //if(file1.mkdir()) System.out.println("Directory Creat");
                file1.mkdir();
                File file2 = new File(pathUsers + user + "/Partides/" + idg + "/info.txt");
                //if(file2.createNewFile()) System.out.println("Fitxer Creat");
                file2.createNewFile();
            }


            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(idg).append("\n").append(p1).append("\n").append(p2).append("\n");
            int n = board.length;
            int m = board[0].length;
            for (String[] value : board) {
                for (int j = 0; j < m; j++) {
                    if (j == m - 1) stringBuilder.append(value[j]).append("\n");
                    else stringBuilder.append(value[j]);
                }
            }
            stringBuilder.append(finish).append("\n").append(time).append("\n").append(torn).append("\n").append(rules).append("\n").append(jugada).append("\n").append(mode);

            FileWriter fileWriter = new FileWriter(pathUsers + user + "/Partides/" + idg + "/info.txt");
            fileWriter.write(stringBuilder.toString());
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metode per carregar una partida existent en format d'arraylist de string donat el nom del perfil i l'ID de la partida
     * @param user Nom del perfil
     * @param idg ID de la partida
     * @return partida en format d'arraylist
     */
    public ArrayList<String> loadGame(String user, String idg){
        ArrayList<String> game = new ArrayList<>();
        try{
            File file = new File(pathUsers + user + "/Partides/");
            String[] gameDir = file.list();
            int i = 0;
            boolean found = false;
            assert gameDir != null;
            for(String s : gameDir){
                if(s.equals(idg)) {
                    found = true;
                    break;
                }
                i += 1;
            }
            if(found){
                File file1 = new File(pathUsers + user + "/Partides/" + gameDir[i] + "/info.txt");
                Scanner scanner = new Scanner(file1);
                while(scanner.hasNext()){
                    String aux = scanner.nextLine();
                    game.add(aux);
                }
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return game;
    }

    /**
     * Mètode per eliminar una partida d'un perfil donat el seu nom i l'ID de la partida
     * @param user Nom del perfil
     * @param idg ID de la partida
     */
    public void deleteGame(String user, String idg){
        File file = new File(pathUsers + user + "/Partides/" + idg);
        deleteDirRec(file);
    }

    /**
     * Mètode per obtenir una llista de totes les partides d'un perfil
     * @param user Nom del perfil
     * @return Llistat de partides
     */
    public ArrayList<String> getAllGames(String user){
        ArrayList<String> ret = new ArrayList<>();
        try{
            File file = new File(pathUsers + user + "/Partides/");
            String[] strings = file.list();
            for(String string : strings){
                ret.add(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
