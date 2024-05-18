package domini.classes;


import domini.shared.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Comparator;


public class Rankings {


    private int posicio;

    Map<String,Double> hashmapT;

    String nom_winrate;
    double winrate;

    String nom_victories;
    Integer max_victories;

    String nom_jugada;
    Integer jugada_max;







    /**
     * Constructora per defecte
     */
    public Rankings() {
        hashmapT=new TreeMap<>();
    }

    /*GETTERS I SETTERS */

    public Map<String,Double> getrankingT(){
        return hashmapT;
    }

    public int getPosicio() {
        return this.posicio;
    }

    public void setPosicio(int posicio) {
        this.posicio = posicio;
    }

    public String getNom_winrate() {
        return nom_winrate;
    }

    public void setNom_winrate(String nom_winrate) {
        this.nom_winrate = nom_winrate;
    }

    public double getWinrate() {
        return winrate;
    }

    public void setWinrate(double winrate) {
        this.winrate = winrate;
    }

    public String getNom_victories() {
        return nom_victories;
    }

    public void setNom_victories(String nom_victories) {
        this.nom_victories = nom_victories;
    }

    public Integer getMax_victories() {
        return max_victories;
    }

    public void setMax_victories(Integer max_victories) {
        this.max_victories = max_victories;
    }

    public String getNom_jugada() {
        return nom_jugada;
    }

    public void setNom_jugada(String nom_jugada) {
        this.nom_jugada = nom_jugada;
    }

    public Integer getJugada_max() {
        return jugada_max;
    }

    public void setJugada_max(Integer jugada_max) {
        this.jugada_max = jugada_max;
    }

    //METODES//

    /**
     *
     * @return
     */
    public Map<String,Double> ordenar(){

        return   hashmapT.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));


    }

    /*Map<String, Integer> sortedByValueDesc = hashmapT.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .collect(toMap(Map.Entry::getKey,
                    Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
*/




    /**
     * Crear una instancia nova de Jugador(Màquina o Humà) amb puntuació:0.0 al ranking corresponent.
     * @param idJugador : id del judador
     */



    public void afegir_jugador(Jugador idJugador) {
        String nom = idJugador.getIdJugador();
        hashmapT.put(nom, 0.0);
    }


    /**
     * Actualitzar puntuació del idJugador depenent si es Humà o Màquina
     * @param idJugador: id del jugador
     */
    public void actualitzar_puntuacio(Jugador idJugador) {
        String nom = idJugador.getIdJugador();
        hashmapT.put(nom, idJugador.getStats().getPuntuacio_ranking()); //Afegir Puntuacio ranquing maquina
    }

    //Cridar estadistiques

    /**
     *
     * @param idJugador
     * @param jugada_max
     */
    public void actualitzar_records(Jugador idJugador, Integer jugada_max){
        double winrate = idJugador.getStats().getWinrate();
        String nom = idJugador.getIdJugador();
        if(winrate > this.winrate){
            this.winrate = winrate;
            this.nom_winrate = nom;
        }

        int max_victories = idJugador.getStats().getVictories();
        if(max_victories > this.max_victories){
            this.max_victories = max_victories;
            this.nom_victories = nom;
        }

        if(jugada_max > this.jugada_max){
            this.jugada_max = jugada_max;
            this.nom_jugada = nom;
        }
    }


}
