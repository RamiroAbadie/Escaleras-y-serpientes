package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Tablero {
    private List<Casilla> casillas;
    private List<Jugador> jugadores;
    private Dado dado;
    private final int maxEscaleras = 10;
    private final int maxSerpientes = 10;

    public Tablero() {
        this.casillas = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        this.dado = new Dado();
    }

    public List<Casilla> getCasillas() {
        return casillas;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void crearTablero() {
        System.out.println("Creando tablero!");
        Random generador = new Random();
        int maxCasillas = 100;
        int randomTipo;
        int destino;
        int escalerasCreadas = 0;
        int serpientesCreadas = 0;

        for (int i = 0; i < maxCasillas; i++) {
            Casilla casilla;
            if (i == 100) {  // La casilla final siempre es normal
                casilla = new Normal(i + 1, i + 1);
            } else {
                randomTipo = generador.nextInt(3);  // 0, 1 o 2

                if (randomTipo == 0 || (escalerasCreadas >= maxEscaleras && serpientesCreadas >= maxSerpientes)) {
                    casilla = new Normal(i + 1, i + 1);
                } else if (randomTipo == 1 && escalerasCreadas < maxEscaleras) {
                    if (maxCasillas - i - 1 > 0 && i < 100) {
                        destino = generador.nextInt(maxCasillas - i - 1) + i + 2;
                        if (destino != 25) {  // Evita que la escalera llegue a la casilla final
                            casilla = new Escalera(i + 1, destino);
                            escalerasCreadas++;
                        } else {
                            casilla = new Normal(i + 1, i + 1);
                        }
                    } else {
                        casilla = new Normal(i + 1, i + 1);  // Si no es posible, crea una casilla normal
                    }
                } else if (randomTipo == 2 && serpientesCreadas < maxSerpientes) {
                    if (i > 0 && i != 100) {  // Evita que la última casilla (100) tenga una serpiente que lleve a la casilla final
                        destino = generador.nextInt(i) + 1;
                        casilla = new Serpiente(i + 1, destino);
                        serpientesCreadas++;
                    } else {
                        casilla = new Normal(i + 1, i + 1);  // Si no es posible, crea una casilla normal
                    }
                } else {
                    casilla = new Normal(i + 1, i + 1);
                }
            }
            casillas.add(casilla);
        }
        mostrarEscalerasYSerpientes();
        System.out.println("----Tablero creado----");
    }

    public void agregarJugador(Jugador jugador) {
        if (jugador != null) {
            jugadores.add(jugador);
        } else {
            Jugador nuevoJugador = new Jugador("Jugador" + (jugadores.size() + 1));
            jugadores.add(nuevoJugador);
        }
    }

    public int tirarDado() {
        int resultado;
        do {
            dado.tirarDado();
            resultado = dado.getNumero();
        } while (resultado <= 0 || resultado > 6);
        return resultado;
    }

    public void moverJugador(Jugador jugador, int numeroDado) {
        int nuevaPosicion = jugador.getPosicion() + numeroDado;

        if (nuevaPosicion > casillas.size()) {
            System.out.println("Jugador " + jugador.getNombre() + " se ha pasado de la casilla final.");
            return;
        }

        Casilla casillaActual = casillas.get(nuevaPosicion - 1);
        jugador.setPosicion(nuevaPosicion);

        System.out.println("Jugador " + jugador.getNombre() + " está en la casilla " + jugador.getPosicion());

        if (casillaActual instanceof Escalera) {
            Escalera escalera = (Escalera) casillaActual;
            System.out.println("¡Subiste por una escalera desde la casilla " + escalera.getInicio() + " hasta la casilla " + escalera.getFin() + "!");
            jugador.setPosicion(escalera.getFin());
        } else if (casillaActual instanceof Serpiente) {
            Serpiente serpiente = (Serpiente) casillaActual;
            System.out.println("¡Bajaste por una serpiente desde la casilla " + serpiente.getInicio() + " hasta la casilla " + serpiente.getFin() + "!");
            jugador.setPosicion(serpiente.getFin());
        }
    }

    private void mostrarEscalerasYSerpientes() {
        System.out.println("Escaleras y Serpientes en el tablero:");
        for (Casilla casilla : casillas) {
            if (casilla instanceof Escalera) {
                Escalera escalera = (Escalera) casilla;
                System.out.println("Escalera: Desde " + escalera.getInicio() + " hasta " + escalera.getFin());
            } else if (casilla instanceof Serpiente) {
                Serpiente serpiente = (Serpiente) casilla;
                System.out.println("Serpiente: Desde " + serpiente.getInicio() + " hasta " + serpiente.getFin());
            }
        }
    }

    public Dado getDado() {
        return dado;
    }
}
