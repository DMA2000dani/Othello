package domini.classes;

public class Jugador {
    protected String idJugador;
    private Estadistiques stats;

    /*
     * Getters i Setters
     */
    public Jugador getJugador() {return this;}
    public String getIdJugador() {
        return idJugador;
    }
    public Estadistiques getStats() {
        return stats;
    }

    public void setPuntuacio_ranking(int x){
        stats.setPuntuacio_ranking(x);
    }

    public void getPuntuacio_ranking(){

    }
    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    /**
     * Constructora d'un Jugador
     * @param idJugador string que indentifica el jugador
     */
    public Jugador(String idJugador) {

        this.idJugador = idJugador;
        stats = new Estadistiques();
    }
}
