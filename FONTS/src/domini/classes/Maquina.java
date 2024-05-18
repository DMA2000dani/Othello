package domini.classes;

public class Maquina extends Jugador {

    private int dificultat;
    private String nom;
    /**
     * Constructora de la Màquina
     * @param idJugador id del jugador
     */
    public Maquina(String idJugador) {
        super(idJugador);
    }

    public Jugador getJugador(){
        return super.getJugador();
    }


}
