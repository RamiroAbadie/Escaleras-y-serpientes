package view;

import model.Casilla;
import model.Escalera;
import model.Jugador;
import model.Serpiente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class GameView extends JFrame {
    private static GameView instance;
    private CustomBoardPanel boardPanel;
    private JButton rollDiceButton;
    private JLabel diceResultLabel;
    private JTextArea gameLog;

    private GameView() {
        setTitle("Escaleras y Serpientes");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        boardPanel = new CustomBoardPanel();
        boardPanel.setPreferredSize(new Dimension(800, 800));
        rollDiceButton = new JButton("Lanzar Dado");
        diceResultLabel = new JLabel("Resultado del dado: ");
        gameLog = new JTextArea(10, 30);
        gameLog.setEditable(false);
        JPanel controlPanel = new JPanel();
        controlPanel.add(rollDiceButton);
        controlPanel.add(diceResultLabel);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(boardPanel), BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        mainPanel.add(new JScrollPane(gameLog), BorderLayout.EAST);
        add(mainPanel);
        pack();
    }

    public static GameView getInstance() {
        if (instance == null) {
            instance = new GameView();
        }
        return instance;
    }

    public void addRollDiceListener(ActionListener listener) {
        rollDiceButton.addActionListener(listener);
    }

    public void setDiceResult(int result) {
        diceResultLabel.setText("Resultado del dado: " + result);
    }

    public void log(String message) {
        gameLog.append(message + "\n");
    }

    public void updateBoard(List<Casilla> casillas, List<Jugador> jugadores) {
        boardPanel.setCasillas(casillas);
        boardPanel.setJugadores(jugadores);
        boardPanel.repaint();
    }

    public void disableRollDiceButton() {
        rollDiceButton.setEnabled(false);
    }

    public void enableRollDiceButton() {
        rollDiceButton.setEnabled(true);
    }

    private class CustomBoardPanel extends JPanel {
        private List<Casilla> casillas;
        private List<Jugador> jugadores;

        public void setCasillas(List<Casilla> casillas) {
            this.casillas = casillas;
        }

        public void setJugadores(List<Jugador> jugadores) {
            this.jugadores = jugadores;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawBoard((Graphics2D) g);
        }

        private void drawBoard(Graphics2D g) {
            int cellSize = getWidth() / 10;
            for (int i = 0; i < 100; i++) {
                int x = (i % 10) * cellSize;
                int y = (9 - i / 10) * cellSize;
                g.setColor(Color.WHITE);
                g.fillRect(x, y, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, cellSize, cellSize);
                g.drawString(String.valueOf(i + 1), x + 10, y + 20);
            }

            if (casillas != null) {
                for (Casilla casilla : casillas) {
                    if (casilla instanceof Escalera) {
                        g.setColor(Color.GREEN);
                        drawLadder(g, casilla.getInicio(), casilla.getFin(), cellSize);
                    } else if (casilla instanceof Serpiente) {
                        g.setColor(Color.RED);
                        drawSnake(g, casilla.getInicio(), casilla.getFin(), cellSize);
                    }
                }
            }

            if (jugadores != null) {
                for (Jugador jugador : jugadores) {
                    int pos = jugador.getPosicion() - 1;
                    int x = (pos % 10) * cellSize;
                    int y = (9 - pos / 10) * cellSize;
                    g.setColor(Color.BLUE);
                    g.drawString(jugador.getNombre(), x + 10, y + 40);
                }
            }
        }

        private void drawLadder(Graphics2D g, int inicio, int fin, int cellSize) {
            int startX = ((inicio - 1) % 10) * cellSize + cellSize / 2;
            int startY = (9 - (inicio - 1) / 10) * cellSize + cellSize / 2;
            int endX = ((fin - 1) % 10) * cellSize + cellSize / 2;
            int endY = (9 - (fin - 1) / 10) * cellSize + cellSize / 2;

            g.drawLine(startX, startY, endX, endY);
            g.drawLine(startX + 5, startY, endX + 5, endY);
            g.drawLine(startX - 5, startY, endX - 5, endY);
        }

        private void drawSnake(Graphics2D g, int inicio, int fin, int cellSize) {
            int startX = ((inicio - 1) % 10) * cellSize + cellSize / 2;
            int startY = (9 - (inicio - 1) / 10) * cellSize + cellSize / 2;
            int endX = ((fin - 1) % 10) * cellSize + cellSize / 2;
            int endY = (9 - (fin - 1) / 10) * cellSize + cellSize / 2;

            g.drawLine(startX, startY, endX, endY);
            g.drawOval(startX - 5, startY - 5, 10, 10);
            g.drawOval(endX - 5, endY - 5, 10, 10);
        }
    }
}
