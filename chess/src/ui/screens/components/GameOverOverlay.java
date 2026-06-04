package ui.screens.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.Controller;
import logic.GameStatus;

public class GameOverOverlay extends JPanel {

    private JLabel gameOverText;
    private JButton restartButton;

    public GameOverOverlay(Controller controller) {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(100, 100, 100, 100));

        gameOverText = new JLabel();
        gameOverText.setFont(new Font("Sans-Serif", Font.BOLD, 36));
        gameOverText.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameOverText.setAlignmentY(Component.CENTER_ALIGNMENT);
        restartButton = new JButton("PLAY AGAIN");
        restartButton.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        restartButton.setBorderPainted(false);
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        restartButton.addActionListener(e -> controller.startNewGame());

        add(gameOverText);
        add(restartButton);
    }

    public void gameOver(GameStatus gameStatus) {
        switch (gameStatus) {
            case BLACK: {
                gameOverText.setText("Black won!");
                break;
            }
            case WHITE: {
                gameOverText.setText("White won!");
                break;
            }
            case DRAW: {
                gameOverText.setText("Game ended in a draw");
                break;
            }
            default: {
                gameOverText.setText("game ended.");
                break;
            }
        }
    }
}
