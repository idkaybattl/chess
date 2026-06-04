package ui.screens;

import java.awt.GridBagLayout;

import javax.swing.*;

import logic.GameStatus;
import logic.Location;
import logic.pieces.Pawn;
import ui.Controller;
import ui.screens.components.*;

public class GamePanel extends JPanel {

    private JLayeredPane layeredPane;

    private BoardPanel boardPanel;
    private GameOverOverlay gameOverOverlay;

    public GamePanel(Controller controller) {
        super(new GridBagLayout());

        boardPanel = new BoardPanel(controller);
        gameOverOverlay = new GameOverOverlay(controller);

        gameOverOverlay.setPreferredSize(boardPanel.getPreferredSize());
        gameOverOverlay.setVisible(false);

        layeredPane = new JLayeredPane() {
            @Override
            public void doLayout() {
                boardPanel.setBounds(0, 0, getWidth(), getHeight());
                gameOverOverlay.setBounds(0, 0, getWidth(), getHeight());
            }
        };
        layeredPane.add(boardPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(gameOverOverlay, JLayeredPane.PALETTE_LAYER);

        layeredPane.setPreferredSize(boardPanel.getPreferredSize());

        // TODO menubar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Test");
        menuBar.add(menu);

        setAlignmentX(0.5f);
        setAlignmentY(0.5f);

        add(layeredPane);
    }

    public void gameOver(GameStatus gameStatus) {
        gameOverOverlay.setVisible(true);
        gameOverOverlay.gameOver(gameStatus);
    }

    public void startNewGame() {
        gameOverOverlay.setVisible(false);
        boardPanel.updateBoard();
    }

    public void startPromotion(Pawn pawn, Location origin, Location target) {
        boardPanel.startPromotion(pawn, origin, target);
    }

    public void closePromotion() {
        boardPanel.closePromotion();
    }
}
