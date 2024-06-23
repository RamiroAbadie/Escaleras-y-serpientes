package Controller;

import model.GameHandler;
import model.Jugador;
import view.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {
    private static GameController instance;
    private final String nombreJuego;
    private GameHandler handler;
    private GameView vista;
    private int turnoActual;

    private GameController() {
        nombreJuego = "Escaleras y Serpientes";
        handler = new GameHandler();
        vista = GameView.getInstance();
        turnoActual = 0;

        vista.addRollDiceListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarTurno();
            }
        });
    }

    public String getNombreJuego() {
        return nombreJuego;
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void empezarPartida() {
        handler.empezarPartida();
        vista.updateBoard(handler.getCasillas(), handler.getJugadores());
    }

    private void realizarTurno() {
        Jugador jugadorActual = handler.getJugadores().get(turnoActual);
        handler.tirarDado();
        int resultadoDado = handler.getResultadoDado();
        vista.setDiceResult(resultadoDado);
        handler.ejecutarTurno(jugadorActual, resultadoDado);
        vista.updateBoard(handler.getCasillas(), handler.getJugadores());
        vista.log("Turno de " + jugadorActual.getNombre() + ": lanzó " + resultadoDado + " y está en la casilla " + jugadorActual.getPosicion());

        if (jugadorActual.isEnFinal()) {
            vista.log("¡" + jugadorActual.getNombre() + " ha ganado!");
            vista.disableRollDiceButton();
        } else {
            turnoActual = (turnoActual + 1) % handler.getJugadores().size();
        }
    }
}
