import Controller.GameController;
import view.GameView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameController juego = GameController.getInstance();
            GameView vista = GameView.getInstance();
            vista.setVisible(true);
            juego.empezarPartida();
        });
    }
}
