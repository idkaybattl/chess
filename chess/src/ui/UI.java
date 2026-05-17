package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import javax.swing.JFrame;
import javax.swing.JButton;
import logic.*;
import logic.pieces.*;

public class UI extends JFrame {
    private ChessGame logic;

    private JButton[][] squares = new JButton[8][8];

    public UI() {
        logic = new GameLogic();

        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("dada");
        setResizable(false);

        JMenuBar menu = new JMenuBar();
        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        boardPanel.setPreferredSize(new Dimension(640, 640));

        // board
        squares = new JButton[8][8];

        // weird loop to appease grid layout
        // start adding from top left to bottom right
        // -> first one has to be a8 -> y=7, x = 0
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                JButton button = new JButton();
                squares[x][y] = button;

                button.setOpaque(true);
                button.setBorderPainted(false);

                boolean light = (x + y) % 2 == 0;
                button.setBackground(light ? Color.DARK_GRAY : Color.LIGHT_GRAY);

                int finalX = x;
                int finalY = y;
                button.addActionListener(e -> squareClicked(finalX, finalY));

                boardPanel.add(squares[x][y]);
            }
        }

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.add(boardPanel);

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);

        updateBoard();
    }

    public static void main(String[] args) {
        new UI();
    }

    private void squareClicked(int x, int y) {
        System.out.println("Clicked " + x + ", " + y);
    }

    private void updateBoard() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                updateSquare(x, y);

            }
        }
    }

    private void updateSquare(int x, int y) {
        var squareContent = logic.getPieceAt(x, y);
        if (squareContent.isPresent()) {
            Piece piece = squareContent.get();
            squares[x][y]
                    .setText(((piece.getColor() == ChessColor.WHITE) ? "W" : "B") + piece.getClass().getSimpleName());
        } else {
            squares[x][y].setText("");
        }
    }

    // private void updateBoard() {
    // logic.getBoard();
    // }
}
