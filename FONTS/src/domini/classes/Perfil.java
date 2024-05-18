package domini.classes;
/*
 * Classe Perfil
 */

public class Perfil extends Jugador{
    private String usuari;
    private String contrasenya;

    /*
     * Getters i Setters
     */
    public String getUsuari() {
        return usuari;
    }
    public String getContrasenya() { return contrasenya; }
    public String getIdJugador(){
        return super.idJugador;
    }

    public void setUsuari(String usuari) { this.usuari = usuari; }
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }




    /**
     * Constructora d'un perfil donat l'id del jugador, l'usuari i la seva contrasenya
     * @param idJugador string que identifica l'id del perfil
     * @param usuari string que indica el nom d'usuari del perfil
     * @param contrasenya string que infica la contrasenya del perfil
     */
    public Perfil(String idJugador, String usuari, String contrasenya){
        super(idJugador);
        this.usuari = usuari;
        this.contrasenya = contrasenya;
    }

    /**
     * Constructora d'un perfil buit.
     * @param idJugador string que indentifica l'id del perfil
     */
    public Perfil(String idJugador){
        super(idJugador);
    }
}
