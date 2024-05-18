package domini.classes;
import domini.shared.Color;

public class CASELLA {

    // Atributs
    Color color;

// Constructores

    /**
     * Constructora per defecte (crea una casella amb el seu atribut color a Buit)
     */
    public CASELLA() {
        this.color = Color.Buit;
    }

    CASELLA(Color color) {
        this.color = color;
    }

//Gets

    public Color getcolor() {
        return this.color;
    }

// Sets

    public void setcolor(Color col) {
        this.color = col;
    }


    // Altres

    /**
     * mira si és voltejable, és a dir, si hi ha una fitxa i a més és de l'oponent
     * @param col color de la fitxa
     * @return retorna true hi ha una fitxa de l'oponent. Retorna false en cas contrari.
     */
    public boolean voltejable(Color col) { //retorna true si hi ha una fitxa amb la del color contrari al passat per paràmetre
        return (col == Color.Blanc && this.color == Color.Negre) || (this.color == Color.Blanc && col == Color.Negre) ;
    }

    /**
     * Pre: La casella no ha d'estar buida.
     * Canvia el color de la fitxa, és a dir, si era blanca, ho canvia a negre i viceversa.
     */
    public void voltejar() { // canvia el color de la fitxa
        if (this.color == Color.Blanc) this.color = Color.Negre;
        else this.color = Color.Blanc;
    }

}
