package persistencia.Gestors;
import java.io.*;
import java.util.*;

public class GestorPerfil {

    static String pathPerfil= "data/Jugadors/Perfils/";
    static String pathIA = "data/Jugadors/Maquines/";
    static String pathRank = "data/Ranking/ranking.txt";

    /**
     * Constructora per defecte
     */
    public GestorPerfil() {

    };

    /**
     * Métode privat per obtenir la contrasenya d'un perfil
     * @param user Nom del perfil
     * @return Contrasenya del perfil
     */
    private String getPassword(String user){
        String pass = "";
        try {
            File userFile = new File(pathPerfil + user + "/userdata.txt");
            Scanner scanner = new Scanner(userFile);
            pass = scanner.nextLine();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pass;
    }

    /**
     * Métode per registrar un usuari a l'aplicació
     * @param user Nom del perfil que vol l'usuari
     * @param password Contrasenya que vol l'usuari
     * @return CERT si el registre ha tingut exit, FALSE en altre cas
     */
    public boolean register(String user, String password) {
        File file1 = new File(pathIA);
        String[] strings = file1.list();
        assert strings != null;
        for (String s : strings){
            if (s.equals(user)) return false;
        }
        File file = new File(pathPerfil);
        String[] dirpath = file.list();
        assert dirpath != null;
        for(String s : dirpath) {
            if (s.equals(user)) return false;
        }
        File userDir = new File(pathPerfil + user);
        userDir.mkdir();
        try {
            File userGames = new File(pathPerfil + user + "/Partides");
            userGames.mkdir();

            File userData = new File(pathPerfil + user + "/userdata.txt");
            userData.createNewFile();
            //afegir a estadistiques
            File userStats = new File(pathPerfil + user + "/stats.txt");
            userStats.createNewFile();

            String defaultStats = "0\n0\n0\n0\n0\n0\n0\n0";
            File file2 = new File(pathPerfil + user + "/stats.txt");
            FileWriter userSts = new FileWriter(file2);
            userSts.write(defaultStats);
            userSts.close();
            //afegir a rank.
            FileWriter fileWriter = new FileWriter(pathRank, true);
            fileWriter.append(user).append(" 0").append("\n");
            fileWriter.close();

            FileWriter userWriter = new FileWriter(pathPerfil + user + "/userdata.txt");
            userWriter.write(password);
            userWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Métode per per login a l'aplicació
     * @param user Nom del perfil
     * @param password Contrasenya del perfil
     * @return CERT si login ha tingut exit, FASLE en altre cas
     */
    public boolean login(String user, String password) {
        File file = new File(pathPerfil);
        String[] dirpath = file.list();
        int pos = 0;
        String aux = "";
        boolean found = false;
        assert dirpath != null;
        for (String s : dirpath) {
            if (s.equals(user)) {
                found = true;
                break;
            }
            else pos += 1;
        }
        if (!found) return false;
        else {
            String pass = "";
            try {
                pass = getPassword(dirpath[pos]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return pass.equals(password);
        }
    }

    /**
     * Métode que obté una llista de tots els perfils de l'aplicació
     * @return Llistat de perfils
     */
    public ArrayList<String> getAllUsers(){
        ArrayList<String> tots = new ArrayList<>();
        try{
            File file = new File(pathPerfil);
            String[] names = file.list();
            assert names != null;
            tots.addAll(Arrays.asList(names));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tots;
    }
}
