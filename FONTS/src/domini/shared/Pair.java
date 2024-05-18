package domini.shared;

public class Pair {
    private int x;
    private int y;

    /*
     * Getters i Setters
     */
    public int getKey() { return x; }

    public int getValue() {
        return y;
    }

    public void setKey(int x) {
        this.x = x;
    }

    public void setValue(int y) {
        this.y = y;
    }

    /*
     * Constructores
     */
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void sum(Pair p) {
        x += p.getKey();
        y += p.getValue();
    }

    public Pair add(Pair p) {
        return new Pair(x + p.getKey(), y + p.getValue());
    }

    public Pair substract(Pair p) {
        return new Pair(x - p.getKey(), y - p.getValue());
    }

    public boolean equals(Pair p) {
        return x == p.getKey() && y == p.getValue();
    }

}


