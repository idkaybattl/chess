package ui;

import java.awt.CardLayout;

import javax.swing.JPanel;

import ui.screens.*;

public class ScreenManager extends JPanel {
    private Controller controller;

    private final GamePanel gamePanel;
    private final String GAMEPANEL = "Game Panel";

    public ScreenManager(Controller controller) {
        super(new CardLayout());

        this.controller = controller;

        gamePanel = new GamePanel(controller);

        add(gamePanel, GAMEPANEL);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
