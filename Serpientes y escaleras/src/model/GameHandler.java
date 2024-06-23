package model;

import java.util.List;

public class GameHandler {
    private Tablero tablero;
    private int resultadoDado;

    public GameHandler() {
        this.tablero = new Tablero();
    }

    public void empezarPartida() {
        System.out.println("Empezó la partida!");
        tablero.crearTablero();
        Jugador j1 = new Jugador("Ramiro");
        Jugador j2 = new Jugador("Valentino");
        Jugador j3 = new Jugador("Santino");
        agregarJugador(j1);
        agregarJugador(j2);
        agregarJugador(j3);
    }

    public void ejecutarTurno(Jugador jugador, int resultadoDado) {
        System.out.println("\nTurno de " + jugador.getNombre());
        tablero.moverJugador(jugador, resultadoDado);
    }

    public void agregarJugador(Jugador jugador) {
        tablero.agregarJugador(jugador);
    }

    public int getResultadoDado() {
        return resultadoDado;
    }

    public void tirarDado() {
        resultadoDado = tablero.tirarDado();
    }

    public List<Jugador> getJugadores() {
        return tablero.getJugadores();
    }

    public List<Casilla> getCasillas() {
        return tablero.getCasillas();
    }
}
