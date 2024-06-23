package model;

import java.util.Random;

public final class Dado {
    private int numero;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void tirarDado() {
        Random generador = new Random();
        this.numero = generador.nextInt(6) + 1;
    }
}
