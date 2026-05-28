package ui.screens.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.Controller;

public class GameOverOverlay extends JPanel {
    private Controller controller;

    private JLabel wonText;
    private JButton restartButton;

    public GameOverOverlay(Controller controller) {
        super();

        this.controller = controller;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(100, 100, 100, 200));
        setAlignmentX(0.5f);
        setAlignmentY(0.5f);

        wonText = new JLabel();
        wonText.setFont(new Font("Sans-Serif", Font.BOLD, 36));
        wonText.setAlignmentX(Component.CENTER_ALIGNMENT);
        wonText.setAlignmentY(Component.CENTER_ALIGNMENT);
        restartButton = new JButton("PLAY AGAIN");
        restartButton.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        restartButton.addActionListener(e -> controller.startNewGame());

        add(wonText);
        add(restartButton);
    }
}
