package persistencia.Gestors;
import java.util.*;
import java.io.*;

public class GestorMaquina {
    static String pathIA = "data/Jugadors/Maquines/";
    static String pathPerfil= "data/Jugadors/Perfils/";
    static String pathRank = "data/Ranking/ranking.txt";

    /**
     * Constructora per defecte
     */
    public GestorMaquina(){

    }

    /**
     * Metode per donar d'alta una maquina nova
     * @param name Nom de la màquina
     * @param heu heuristica que utilitza la màquina
     * @param depth profunditat de la màquina
     * @param pruning indica si la màquina fa podes o no
     * @return TRUE si s'ha donat d'alta, FALSE en cas contrari
     */
    public boolean altaMaquina(String name, Integer heu, Integer depth, boolean pruning){
        File file1 = new File(pathPerfil);
        String[] strings = file1.list();
        for (String s : strings){
            if (s.equals(name)) return false;
        }
        File iafile = new File(pathIA);
        String[] dirpath = iafile.list();
        for(String s : dirpath){
            if(s.equals(name)) return false;
        }
        try {
            File iaDir = new File(pathIA + name);
            iaDir.mkdir();

            File userData = new File(pathIA + name + "/iadata.txt");
            userData.createNewFile();
            //posar estadistiques
            File userStats = new File(pathIA + name + "/stats.txt");
            userStats.createNewFile();

            String defaultStats = "0\n0\n0\n0\n0\n0\n0\n0";
            File file2 = new File(pathIA + name + "/stats.txt");
            FileWriter userSts = new FileWriter(file2);
            userSts.write(defaultStats);
            userSts.close();

            //posar rank
            FileWriter fileWriter = new FileWriter(pathRank, true);
            fileWriter.append(name + " 0" + "\n");
            fileWriter.close();

            StringBuilder stringBuilder = new StringBuilder();
            FileWriter iaWriter = new FileWriter(pathIA + name + "/iadata.txt");
            stringBuilder.append(heu).append("-").append(depth).append("-").append(pruning);
            iaWriter.write(stringBuilder.toString());
            /*iaWriter.write(String.valueOf(heu));
            iaWriter.write("-");
            iaWriter.write(String.valueOf(depth));
            iaWriter.write("-");
            iaWriter.write(String.valueOf(pruning));*/
            iaWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Mètode per obtenir informació de una màquina donat el seu nom
     * @param name Nom de la màquina
     * @return informació de la màquina. heurística, profunditat, si fa podes
     */
    public ArrayList<String> obteMaquina(String name){
        ArrayList<String> maq = new ArrayList<>();
        String[] ia;
        try {
            File iafile = new File(pathIA);
            String[] dirpath = iafile.list();
            int pos = 0;
            String aux = "";
            boolean found = false;
            for (String s : dirpath) {
                if (s.equals(name)) {
                    found = true;
                    break;
                }
                else pos += 1;
            }
            if (found){
                File iadata = new File(pathIA + name + "/iadata.txt");
                Scanner scanner = new Scanner(iadata);
                String aux2 = scanner.nextLine();
                ia = aux2.split("-");
                for(int i = 0; i < ia.length; i++){
                    maq.add(ia[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maq;
    }

    /**
     * Mètode per obtenir totes les màquines de l'aplicació
     * @return llista dels noms de les màquines
     */
    public ArrayList<String> getAllIA(){
        ArrayList<String> tots = new ArrayList<>();
        try{
            File file = new File(pathIA);
            String[] names = file.list();
            for(String s : names){
                tots.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tots;
    }
}
