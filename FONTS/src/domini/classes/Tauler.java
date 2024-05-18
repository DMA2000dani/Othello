package domini.classes;
import domini.shared.*;

public class Tauler implements Cloneable {
        private String idTauler;
        private int fitxesB;
        private int fitxesN;
        private CASELLA[][] tauler;


        //Constructores

        /**
         * Constructora de tauler per defecte, el tauler s'inicialitza amb el tauler típic de l'Othello clàsc,
         * amb totes regles de captura(horitzonta, vertical i diagonals) i amb el seu identificador
         * @param idtauler identificador del tauler que es creat
         */
        public Tauler (String idtauler) {
                this.idTauler = idtauler;
                this.fitxesB = 2;
                this.fitxesN = 2;
                this.tauler = new CASELLA[8][8];
                // Creació de le caselles del tauler a Buit
                for (int i = 0; i < 8; i++)
                        for (int j = 0; j < 8; j++)
                                tauler[i][j] = new CASELLA();
                iniTaulell();
        }

        /**
         * Inicialitza el tauler amb les fitxes inicials per defecte, típic de l'Othello clàsic.
         */
        private void iniTaulell() { // afegeix les 4 fitxes inicials al tauler
                tauler[3][3].color = Color.Negre;
                tauler[4][4].color = Color.Negre;
                tauler[3][4].color = Color.Blanc;
                tauler[4][3].color = Color.Blanc;
        }
        /**
         * Mètode per obtenir una hard copy d'un tauler
         * @return Retorna una còpia d'un tauler
         * @throws CloneNotSupportedException Si no es pot fer un clon de l'objecte
         */
        public Tauler clone() throws CloneNotSupportedException {
                Tauler clon = (Tauler)super.clone();
                clon.tauler = new CASELLA[8][8];
                for (int i = 0; i < 8; ++i)
                        for (int j = 0; j < 8; ++j) {
                                clon.tauler[i][j] = new CASELLA(tauler[i][j].getcolor());
                        }
                return clon;
        }

        //gets
        public String getIdTauler(){
                return this.idTauler;
        }

        public int getFitxesB(){ return this.fitxesB;}
        public int getFitxesN(){ return this.fitxesN;}

        public CASELLA[][] getTauler(){ return this.tauler; }

        //sets
        public void setIdTauler(String id){idTauler = id;}
        public void setTauler(CASELLA[][] taulernou){this.tauler = taulernou;} // al fer això, qui crida la funció és
        public void setFitxesB(int b){fitxesB = b;}
        public void setFitxesN(int n){fitxesN = n;}

        /**
         * Mira si es pot posar almenys una fitxa del color negre si negre és true o blanc si negre = false
         * @param negre boolean que indica si el color és negre si negre es true o blanc si negre es false
         * @return retorna true si hi hi ha moviments a fer amb una fitxa negre(si negre = true) o blanca
         * (si negre = false). Retorna false si no hi ha almenys un moviments a fer amb una fitxa de color negre
         * (si negre = true)o blanca (si negre = false).
         */
        public boolean hihamoviments(boolean negre, Regles reg) {
                if (fitxesN + fitxesB == 64) return false;
                Color c_fitxa;
                if (negre) c_fitxa = Color.Negre;
                else c_fitxa = Color.Blanc;
                for (int i = 0; i < 8; ++i) {
                        for (int j = 0; j < 8; ++j) {
                                Pair p1 = new Pair(i,j);
                                if (posicioValida(p1, c_fitxa, reg)) return true;
                                // Si almenys és pot posar una fitxa, hi ha moviments
                        }
                }
                return false; // Si al mirar de posar fitxa en totes les caselles no es pot posar en cap, no hi ha
                // moviments.
        }

        /**
         * @return retorna true si en el tauler no es poden posar cap fitxa per continuar una partida,
         * és a dir, que ja ha acabat. Retorna false si algú pot possar fitxa, és a dir, encara no ha acabat la prtida
         */
        public boolean isacabat(Regles reg) {
                if (hihamoviments(true, reg)) return false;
                return !hihamoviments(false, reg);
        }

        private CASELLA getCasella(Pair p) {
                return tauler[p.getKey()][p.getValue()];
        }

        private boolean dinsTauler(Pair p) {
                return p.getKey() >= 0 && p.getValue() >=0 && p.getKey() <= 7 && p.getValue() <= 7;
        }

        private void volteja(Pair dst, Pair orig, Pair dir, Color c) {
                if (orig.equals(dst)) tauler[orig.getKey()][orig.getValue()].setcolor(c);
                while (!orig.equals(dst)) {
                        tauler[orig.getKey()][orig.getValue()].setcolor(c);
                        orig.sum(dir);
                }
                tauler[orig.getKey()][orig.getValue()].setcolor(c);
        }

        private void sumaFitxa(Color col) {
                if (col == Color.Blanc) ++fitxesB;
                else if (col == Color.Negre) ++fitxesN;
        }

        public int posarFitxa(Pair pos , Color col, Regles reg) {
                if (getCasella(pos).getcolor() != Color.Buit) return 0;
                int fitxesColocades = 0;
                for (Pair direccio : reg.generarDireccions()) {
                        Pair next = pos.add(direccio);
                        if (dinsTauler(next) && getCasella(next).getcolor() != col && getCasella(next).getcolor() != Color.Buit) {
                                fitxesColocades += capturaRec(next, col, direccio, pos, true, true);
                        }
                }
                if (fitxesColocades > 0) sumaFitxa(col);
                return fitxesColocades;
        }

        private int capturaRec(Pair p, Color c, Pair dir, Pair orig, boolean volteja, boolean primer) {
                if (!dinsTauler(p) || getCasella(p).getcolor() == Color.Buit) return 0;
                if (primer && getCasella(p).getcolor() != c) return capturaRec(p.add(dir), c, dir, orig, volteja, false);
                if (getCasella(p).getcolor() == c) {
                        int capturades = Math.max(Math.abs(p.getKey()-orig.getKey()), Math.abs(p.getValue()-orig.getValue())) - 1;
                        if (volteja) {
                                volteja(p.substract(dir), orig.add(dir), dir, c);
                                if (c == Color.Blanc) {
                                        fitxesN -= capturades;
                                        fitxesB += capturades;
                                }
                                else if (c == Color.Negre) {
                                        fitxesB -= capturades;
                                        fitxesN += capturades;
                                }
                                tauler[orig.getKey()][orig.getValue()].setcolor(c);
                        }
                        return capturades;
                }
                else {
                        return capturaRec(p.add(dir), c, dir, orig, volteja, false);
                }
        }

        public boolean posicioValida(Pair p, Color col, Regles reg) {
                if (getCasella(p).getcolor() != Color.Buit) return false;
                for (Pair direccio : reg.generarDireccions()) {
                        Pair next = p.add(direccio);
                        if (dinsTauler(next) && getCasella(next).getcolor() != col && getCasella(next).getcolor() != Color.Buit) {
                                if (capturaRec(next, col, direccio, p, false, true) > 0) return true;
                        }
                }
                return false;
        }
}
