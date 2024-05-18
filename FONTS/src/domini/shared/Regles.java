package domini.shared;

import java.util.ArrayList;
import java.util.Arrays;

public class Regles {
    private boolean vertical;
    private boolean horitzontal;
    private boolean diagonal;

    public Regles() {
        this.vertical = true;
        this.horitzontal = true;
        this.diagonal = true;
    }

    public Regles(boolean vertical, boolean horitzontal, boolean diagonal) {
        this.vertical = vertical;
        this.horitzontal = horitzontal;
        this.diagonal = diagonal;
    }

    public Regles(String s) {
        this.diagonal = s.charAt(0) == 'T';
        this.horitzontal = s.charAt(1) == 'T';
        this.vertical = s.charAt(2) == 'T';
    }

    public boolean isVertical() {
        return vertical;
    }

    public boolean isHoritzontal() {
        return horitzontal;
    }

    public boolean isDiagonal() {
        return diagonal;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public void setHoritzontal(boolean horitzontal) {
        this.horitzontal = horitzontal;
    }

    public void setDiagonal(boolean diagonal) {
        this.diagonal = diagonal;
    }

    public ArrayList<Pair> generarDireccions() {
        ArrayList<Pair> direccions = new ArrayList<>();
        if (vertical) direccions.addAll(Arrays.asList(new Pair(1,0), new Pair(-1, 0)));
        if (horitzontal) direccions.addAll(Arrays.asList(new Pair(0,1), new Pair(0, -1)));
        if (diagonal) direccions.addAll(Arrays.asList(new Pair(-1,-1), new Pair(1, 1), new Pair(1, -1), new Pair(-1, 1)));
        return direccions;
    }

    public String toString() {
        String res = "";
        if (diagonal) res += "T";
        else res += "F";
        if (horitzontal) res += "T";
        else res += "F";
        if (vertical) res += "T";
        else res += "F";
        return res;
    }
}
