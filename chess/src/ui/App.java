package ui;

import javax.swing.*;

public class App extends JFrame {
    private Controller controller;
    private ScreenManager screenManager;

    public App() {
        super();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("chessy");
        setResizable(false);

        controller = new Controller();
        screenManager = new ScreenManager(controller);
        controller.setScreenManager(screenManager);

        add(screenManager);

        controller.startNewGame();

        setContentPane(screenManager);
        pack();

        setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }
}
