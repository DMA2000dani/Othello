package persistencia.Gestors;
import java.io.*;
import java.util.*;


public class GestorRanking {
    static String pathRank = "data/Ranking/ranking.txt";

    /**
     * Constructora per defecte
     */
    public GestorRanking(){

    }

    /**
     * Métode per obtenir el ranking global de l'aplicació
     * @return Ranking en format d'arraylist de String
     */
    public ArrayList<String[]> getRanking(){
        //StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String[]> rank = new ArrayList<>();
        try {
            File rankfile = new File(pathRank);
            Scanner scanner = new Scanner(rankfile);

            while(scanner.hasNext()){
                String aux = scanner.nextLine();
                rank.add(aux.split(" "));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rank;
    }

    /**
     * Métode per actualitzar el ranking
     * @param player Nom jugador
     * @param mark Nova puntuació
     */
    public void refreshRanking(String player, Double mark){
        try {
            File rankfile = new File(pathRank);
            Scanner scanner = new Scanner(rankfile);

            StringBuilder stringBuilder = new StringBuilder();
            //String[] aux;
            String aux;
            while(scanner.hasNext()){
                //aux = scanner.nextLine().split(" ");
                aux = scanner.nextLine();
                if (!(aux.contains(player))) {
                    stringBuilder.append(aux).append("\n");
                }
            }
            stringBuilder.append(player).append(" ").append(mark);

            FileWriter fileWriter= new FileWriter(pathRank);
            fileWriter.write(stringBuilder.toString());
            fileWriter.close();

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

