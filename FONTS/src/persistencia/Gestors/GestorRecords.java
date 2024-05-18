package persistencia.Gestors;

import java.util.*;
import java.io.*;

public class GestorRecords {
    String recordPath = "data/Records/";

    /**
     * Constructora per defecte
     */
    public GestorRecords(){

    }

    /**
     * Métode per obtenir la millor jugada fins aquell moment
     * @return Nom sel jugador seguit de la seva millor jugada
     */
    public String getHighlight(){
        StringBuilder ret = new StringBuilder();
        try{
            File file = new File(recordPath + "highlight.txt");
            Scanner scanner = new Scanner(file);
            if (scanner.hasNext()) ret.append(scanner.nextLine());
            //else ret.append("No record registered");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

    /**
     * Métode per obtenir el % mes gran de victories
     * @return Nom del jugador seguit del seu winrate
     */
    public String getWR(){
        StringBuilder ret = new StringBuilder();
        try{
            File file = new File(recordPath + "wr.txt");
            Scanner scanner = new Scanner(file);
            if (scanner.hasNext()) ret.append(scanner.nextLine());
            //else ret.append("No record registered");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

    /**
     * Métode que obté qui té el major número de victories
     * @return Nom del jugador seguit del número de victories
     */
    public String getVictory(){
        StringBuilder ret = new StringBuilder();
        try{
            File file = new File(recordPath + "wins.txt");
            Scanner scanner = new Scanner(file);
            if (scanner.hasNext()) ret.append(scanner.nextLine());
            //else ret.append("No record registered");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

    /**
     * Métode que actualitza qui té la millor jugada
     * @param user Nom del jugador
     * @param best Nova millor jugada
     */
    public void updateHighlight(String user, Integer best){
        try{
            File file = new File(recordPath + "highlight.txt");
            Scanner scanner = new Scanner(file);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(user).append(" ").append(best);

            FileWriter fileWriter = new FileWriter(recordPath + "highlight.txt");
            String[] string;

            if (scanner.hasNext()){
                string = scanner.next().split(" ");
                if (best > Integer.parseInt(string[1])){
                    fileWriter.write(stringBuilder.toString());
                }
                scanner.close();
            }
            else {
                fileWriter.write(stringBuilder.toString());
            }
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Métode per actualitzar qui té major winrate
     * @param user Nom del jugador
     * @param wr Nou wr del jugador
     */
    public void updateWR(String user, Double wr){
        try{
            File file = new File(recordPath + "wr.txt");
            Scanner scanner = new Scanner(file);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(user).append(" ").append(wr);

            FileWriter fileWriter = new FileWriter(recordPath + "wr.txt");
            String[] string;

            if (scanner.hasNext()){
                string = scanner.next().split(" ");
                if (wr > Double.parseDouble(string[1])){
                    fileWriter.write(stringBuilder.toString());
                }
                scanner.close();
            }
            else {
                fileWriter.write(stringBuilder.toString());
            }
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Métode per actualitzar qui té el major número de victories
     * @param user nom del jugador
     * @param v nou major número de victories
     */
    public void updateVictory(String user, Integer v){
        try{
            File file = new File(recordPath + "wins.txt");
            Scanner scanner = new Scanner(file);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(user).append(" ").append(v);

            FileWriter fileWriter = new FileWriter(recordPath + "wins.txt");
            String[] string;

            if (scanner.hasNext()){
                string = scanner.next().split(" ");
                if (v > Integer.parseInt(string[1])){
                    fileWriter.write(stringBuilder.toString());
                }
                scanner.close();
            }
            else {
                fileWriter.write(stringBuilder.toString());
            }
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
