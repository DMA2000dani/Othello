package domini.classes;

public class Estadistiques {
    //Variables globals

    private int victories;      //Numero de Victories d'un Jugador.
    private int derrotes;       //Numero de Derrotes d'un jugador.
    private int totalPartides; //Numero de partides jugades per un jugador
    private int fitxesCapturades; //Numero de fitxes capturades totals d'un jugador
    private int fitxesPerdudes; //Numero de fitxes perdudes totals d'un jugador
    private long tempsJugat; //Suma de temps que un jugador ha estat en Partida
    private double puntuacio; //Total de totes les partides jugades (M i Humà) per un jugador
    private double puntuacio_ranking; //Total de partides contra mateix Sexe (Jugador o Maquina) per un jugador

    final double Punts_Victoria = 100;  //Variable Global
    final double Punts_Derrota = 0;     //Variable Global


    /* ----- MÈTODES ----- */

    /**
     * Constructora per defecte.
     * Inicialitza totes les variables a 0.
     */

     public Estadistiques(){
         puntuacio = 0.0;
         victories = 0;
         derrotes = 0;
         totalPartides = 0;
         fitxesCapturades = 0;
         fitxesPerdudes = 0;
         tempsJugat = 0;
         puntuacio_ranking = 0.0;

     }

    /* GETTERS I SETTERS */

    public void setTotalPartides(int totalPartides) {
        this.totalPartides = totalPartides;
    }

    public void setFitxesCapturades(int fitxesCapturades) {
        this.fitxesCapturades = fitxesCapturades;
    }

    public void setFitxesPerdudes(int fitxesPerdudes) {
        this.fitxesPerdudes = fitxesPerdudes;
    }

    public void setTempsJugat(long tempsJugat) {
        this.tempsJugat = tempsJugat;
    }

    public void setDerrotes(int derrotes) {
        this.derrotes = derrotes;
    }


    public int getDerrotes() {
        return derrotes;
    }

    public double getPuntuacio(){
        return puntuacio;
    }

    public double getPuntuacio_ranking() {
        return puntuacio_ranking;
    }

    public void setPuntuacio_ranking(double x){
        this.puntuacio_ranking= x;
    }

    public void setPuntuacio(double puntuacio){
         this.puntuacio = puntuacio;
    }


    public int getVictories(){
        return victories;
    }

    public void setVictories(int victories){
        this.victories = victories;
    }

    public int getTotalPartides(){

        return totalPartides;
    }

    public int getFitxesCapturades() {

        return fitxesCapturades;
    }

    public int getFitxesPerdudes(){
        return fitxesPerdudes;
    }



    //METODES//

    /**
     * Retorna un double amb
     * @return
     */
    public double getWinrate() {
        if(derrotes == 0) return victories;
        else return (double)victories/(double)derrotes;
    }
    /**
     * Pre: Només es crida si la partida esta acabada.
     * Retorna la variable res , el calcul de la puntuació d'un jugador en una partida depenent dels paràmetres.
     *
     * @param j1: Instància del jugador 1 a la partida (Màquina o Humà).
     * @param j2: Instància del jugador 2 a la partida (Màquina o Humà).
     * @param guanyat : Booleà per indicar qui ha guanyat. True = Victoria False=Derrota.
     * @param fitxesCapturades: Nombre de fitxes Capturades per un jugador
     * @param fitxesPerdudes: Nombre de fitxes Perdudes per un jugador
     * @param regles: Dificultat de la partida.
     * @return Retorna la puntuació obtinguda
     */

    public double CalculPuntuacio (Jugador j1,Jugador j2,boolean guanyat, int fitxesCapturades, int fitxesPerdudes, String regles) {

        double res;
        double mult = 1.0;
        
        if (regles.equals("H") || regles.equals("V")) {
            mult = 0.75;
        }

        if (guanyat) {
            res = Punts_Victoria;
        } else {
            res = Punts_Derrota;
        }

        res = res*mult + fitxesCapturades;

        actualitzar_estadistiques(res, guanyat, fitxesCapturades, fitxesPerdudes);


        return res;
    }

    /**
     * Funció que actualitza totes les estadistiques d'un jugador un cop finalitzat la partida
     * @param puntuacio: Resultat de la puntuació total obtinguda del Jugador.
     * @param guanyat: Retorna true si el jugador ha guanyat (Victoria)
     * @param fitxesCapturades: Número de fitxes Capturades en total d'un Jugador.
     * @param fitxesPerdudes: Número de fitxes Perdudes en total d'un Jugador.
     */
    public void actualitzar_estadistiques (double puntuacio, boolean guanyat, int fitxesCapturades, int fitxesPerdudes){
         this.puntuacio += puntuacio;

         if(guanyat){
             victories++;
         }
         else{
             derrotes++;
         }

         totalPartides++;
         this.fitxesCapturades += fitxesCapturades;
         this.fitxesPerdudes += fitxesPerdudes;

         puntuacio_ranking += puntuacio;

    }



}



