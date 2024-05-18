package domini.shared;

public class TaulerPair {

    private final String nom;

    private final String[][] matriu;

    public TaulerPair(String nom, String[][] matriu) {
        this.nom = nom;
        this.matriu = matriu;
    }

    public String getNom() {
        return nom;
    }

    public String[][] getMatriu() {
        return matriu;
    }
}
