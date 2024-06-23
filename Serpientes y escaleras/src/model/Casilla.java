package model;

public abstract class Casilla {
    private int inicio;
    private int fin;

    public Casilla(int inicio, int fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    public int getInicio() {
        return inicio;
    }

    public int getFin() {
        return fin;
    }
}
