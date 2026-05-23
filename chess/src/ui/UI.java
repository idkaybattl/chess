package ui;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

import javax.swing.JFrame;
import javax.swing.border.LineBorder;
import javax.swing.JButton;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Optional;

import logic.GameLogic;
import logic.ChessColor;
import logic.ChessGame;
import logic.Location;
import logic.pieces.*;

public class UI extends JFrame {
    private ChessGame logic;

    private Location selectedSquare;
    private java.util.List<Location> highlightedMoves = new ArrayList<>();
    private JButton[][] squares = new JButton[8][8];

    private final EnumMap<ChessColor, HashMap<String, ImageIcon>> icons;

    public UI() {
        super();

        logic = new GameLogic();

        icons = new EnumMap<>(ChessColor.class);
        // piece icons
        for (ChessColor color : ChessColor.values()) {
            icons.put(color, new HashMap<>());
            icons.get(color).put("king-scared", getIcon(color, "king-scared"));
            icons.get(color).put("king", getIcon(color, "king"));
            icons.get(color).put("queen", getIcon(color, "queen"));
            icons.get(color).put("rook", getIcon(color, "rook"));
            icons.get(color).put("knight", getIcon(color, "knight"));
            icons.get(color).put("bishop", getIcon(color, "bishop"));
            icons.get(color).put("pawn", getIcon(color, "pawn"));
        }

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
                button.setBorder(new LineBorder(Color.GREEN, 2));
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
        Location clicked = new Location(x, y);

        if (selectedSquare != null && highlightedMoves.contains(clicked)) {
            Piece selectedPiece = logic.getPieceAt(selectedSquare).orElseThrow();
            logic.movePiece(selectedPiece, clicked);
            clearSelection();
            updateBoard();
            return;
        }

        Optional<Piece> clickedPiece = logic.getPieceAt(clicked);

        if (clickedPiece.isPresent()
                && clickedPiece.get().getColor() == logic.getCurrentPlayer()) {
            selectSquare(clicked, clickedPiece.get());
        } else {
            clearSelection();
        }

        updateBoard();
    }

    private void updateBoard() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                updateSquare(x, y);

            }
        }
    }

    private void updateSquare(int x, int y) {
        Location location = new Location(x, y);
        JButton button = squares[x][y];

        boolean light = (x + y) % 2 == 0;
        button.setBackground(light ? Color.DARK_GRAY : Color.LIGHT_GRAY);

        if (location.equals(selectedSquare)) {
            button.setBorder(new LineBorder(Color.YELLOW, 2));
            button.setBorderPainted(true);
        } else if (highlightedMoves.contains(location)) {
            button.setBorder(new LineBorder(Color.GREEN, 2));
            button.setBorderPainted(true);
        } else {
            button.setBorderPainted(false);
        }

        var squareContent = logic.getPieceAt(location);
        if (squareContent.isPresent()) {
            Piece piece = squareContent.get();
            ChessColor color = piece.getColor();
            if (piece instanceof King) {
                if (logic.inCheck(color)) {
                    button.setIcon(icons.get(color).get("king-scared"));
                } else {
                    button.setIcon(icons.get(color).get("king"));
                }
            } else if (piece instanceof Queen) {
                button.setIcon(icons.get(color).get("queen"));
            } else if (piece instanceof Rook) {
                button.setIcon(icons.get(color).get("rook"));
            } else if (piece instanceof Knight) {
                button.setIcon(icons.get(color).get("knight"));
            } else if (piece instanceof Bishop) {
                button.setIcon(icons.get(color).get("bishop"));
            } else if (piece instanceof Pawn) {
                button.setIcon(icons.get(color).get("pawn"));
            }

            // button.setText(((piece.getColor() == ChessColor.WHITE) ? "W" : "B") +
            // piece.getClass().getSimpleName());
        } else {
            button.setIcon(null);
            // button.setText("");
        }
    }

    private ImageIcon getIcon(ChessColor color, String iconName) {
        try {
            String path = "/icons/" + iconName + (color == ChessColor.WHITE ? "-white.png" : "-black.png");
            URL resource = UI.class.getResource(path);

            if (resource == null) {
                throw new IllegalStateException("Missing icon resource: " + path);
            }

            Image img = ImageIO.read(resource);
            Image scaled = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);

            return new ImageIcon(scaled);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ImageIcon();
        }
    }

    private void selectSquare(Location location, Piece piece) {
        selectedSquare = location;
        highlightedMoves = new ArrayList<>(logic.availableMoves(piece));
    }

    private void clearSelection() {
        selectedSquare = null;
        highlightedMoves.clear();
    }
}
