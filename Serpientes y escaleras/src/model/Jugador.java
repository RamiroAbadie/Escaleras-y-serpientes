package model;

public class Jugador {
    private String nombre;
    private final int id;
    private static int ultimoIdAsignado = 0;
    private int posicion;

    public Jugador(String nombre) {
        this.nombre = nombre != null && !nombre.isEmpty() ? nombre : "Jugador" + ultimoIdAsignado;
        this.posicion = 0;
        this.id = ultimoIdAsignado++;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = Math.max(posicion, 0);
    }

    public boolean isEnFinal() {
        return posicion == 100;
    }
}
