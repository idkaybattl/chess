package ui.screens.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import logic.*;
import logic.pieces.*;
import ui.Controller;

public class BoardLayer extends JPanel {
    private Controller controller;

    private Location selectedSquare;
    private java.util.List<Location> highlightedMoves = new ArrayList<>();
    private JButton[][] squares = new JButton[8][8];

    private final EnumMap<ChessColor, HashMap<String, Image>> icons;

    private final Color moveColor = new Color(100, 122, 179);
    private final Color placeColor = new Color(179, 100, 164);

    public BoardLayer(Controller controller) {
        super(new GridLayout(8, 8));

        this.controller = controller;

        // piece icons
        icons = new EnumMap<>(ChessColor.class);
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

        setPreferredSize(new Dimension(640, 640));

        // board
        squares = new JButton[8][8];

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                JButton button = new JButton();
                squares[x][y] = button;

                button.setOpaque(true);
                button.setBorder(new LineBorder(Color.GREEN, 2));
                button.setBorderPainted(false);

                boolean light = (x + y) % 2 == 0;
                button.setBackground(light ? Color.DARK_GRAY : Color.LIGHT_GRAY);

                // final values needed for lambda since x and y are still going to change
                int finalX = x;
                int finalY = y;
                button.addActionListener(e -> squareClicked(finalX, finalY));
            }
        }

        setUpBoardButtons(Rotation.NORTH);
    }

    private Image getIcon(ChessColor color, String iconName) {
        try {
            String path = "/icons/" + iconName + (color == ChessColor.WHITE ? "-white.png" : "-black.png");
            URL resource = BoardLayer.class.getResource(path);

            if (resource == null) {
                throw new IllegalStateException("Missing icon resource: " + path);
            }

            Image img = ImageIO.read(resource);
            Image scaled = img.getScaledInstance(76, 76, Image.SCALE_SMOOTH);

            return scaled;
        } catch (IOException ex) {
            throw new IllegalStateException("Couldn't load icon", ex);
        }
    }

    private void squareClicked(int x, int y) {
        Location clicked = new Location(x, y);

        MoveResult moveResult = MoveResult.MOVED;

        if (selectedSquare != null && highlightedMoves.contains(clicked)) {
            Piece selectedPiece = controller.getPieceAt(selectedSquare).orElseThrow();
            Location origin = new Location(selectedSquare);
            moveResult = controller.movePiece(selectedPiece, clicked);
            clearSelection();
            updateBoard();
            if (moveResult == MoveResult.GAME_OVER) {
                controller.gameOver();
            } else if (moveResult == MoveResult.PROMOTION && selectedPiece instanceof Pawn) {
                controller.startPromotion((Pawn) selectedPiece, origin, clicked);
            }
            return;
        }

        Optional<Piece> clickedPiece = controller.getPieceAt(clicked);

        if (clickedPiece.isPresent()
                && clickedPiece.get().getColor() == controller.getCurrentPlayer()) {
            selectSquare(clicked, clickedPiece.get());
        } else {
            clearSelection();
        }

        updateBoard();
    }

    private void selectSquare(Location location, Piece piece) {
        selectedSquare = location;
        highlightedMoves = controller.availableMoves(piece);
    }

    private void clearSelection() {
        selectedSquare = null;
        highlightedMoves.clear();
    }

    public void updateBoard() {
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
            button.setBorder(new LineBorder(placeColor, 2));
            button.setBorderPainted(true);
        } else if (highlightedMoves.contains(location)) {
            button.setBorder(new LineBorder(moveColor, 2));
            button.setBorderPainted(true);
        } else {
            button.setBorderPainted(false);
        }

        var squareContent = controller.getPieceAt(location);
        if (squareContent.isPresent()) {
            Piece piece = squareContent.get();
            ChessColor color = piece.getColor();
            if (piece instanceof King) {
                if (controller.inCheck(color)) {
                    button.setIcon(new ImageIcon(icons.get(color).get("king-scared")));
                } else {
                    button.setIcon(new ImageIcon(icons.get(color).get("king")));
                }
            } else if (piece instanceof Queen) {
                button.setIcon(new ImageIcon(icons.get(color).get("queen")));
            } else if (piece instanceof Rook) {
                button.setIcon(new ImageIcon(icons.get(color).get("rook")));
            } else if (piece instanceof Knight) {
                button.setIcon(new ImageIcon(icons.get(color).get("knight")));
            } else if (piece instanceof Bishop) {
                button.setIcon(new ImageIcon(icons.get(color).get("bishop")));
            } else if (piece instanceof Pawn) {
                button.setIcon(new ImageIcon(icons.get(color).get("pawn")));
            }
        } else {
            button.setIcon(null);
        }
    }

    public EnumMap<ChessColor, HashMap<String, Image>> getIcons() {
        return icons;
    }

    public void setUpBoardButtons(Rotation rotation) {
        removeAll();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                JButton button;
                switch (rotation) {
                    case Rotation.NORTH: {
                        button = squares[x][7 - y];
                        break;
                    }
                    case Rotation.EAST: {
                        button = squares[y][x];
                        break;
                    }
                    case Rotation.SOUTH: {
                        button = squares[7 - x][y];
                        break;
                    }
                    default: {
                        button = squares[7 - y][7 - x];
                        break;
                    }
                }
                add(button);
            }
        }
    }
}
