package logic.pieces;

import logic.*;
import java.util.ArrayList;

public abstract class Piece {
    private Location position;
    private Color color;
    private boolean taken;

    public Piece(Location position, Color color) {
        taken = true;
        this.position = position;
        this.color = color;
    }

    public Location getPos() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public boolean isTaken() {
        return taken;
    }

    // only pseudo legal moves, filter moves that lead to check in GameLogic out
    public abstract ArrayList<Location> getValidMoves(Board board);

    // pawns attack the diagonal squares even if theres no piece there
    // => attacked squares not always in list of valid moves
    // matters for checking for checks
    public ArrayList<Location> getAttackedSquares(Board board) {
        return getValidMoves(board);
    }

    public void move(Location target) {
        this.position = target;
    }

    public void take() {
        taken = true;
    }

    public ArrayList<Location> rayCast(int x, int y, Board board) {
        int newX = this.getPos().x + x;
        int newY = this.getPos().y + y;

        var locations = new ArrayList<Location>();

        while (!board.getPiece(newX, newY).isPresent() && x < 8 && y < 8) {
            locations.add(new Location(newX, newY));

            newX += x;
            newY += y;
        }

        return locations;
    }

    public boolean filter(Location location, Board board) {
        if (board.getPiece(location).isPresent()) {
            if (board.getPiece(location).get().getColor() == color) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Location> filteredRayCast(int x, int y, Board board) {
        ArrayList<Location> squares = rayCast(x, y, board);

        if (board.getPiece(squares.get(squares.size() - 1)).isPresent()) {
            if (board.getPiece(squares.get(squares.size() - 1)).get().getColor() == color) {
                squares.remove(squares.size() - 1);
            }
        }

        return squares;

    }

    public boolean inBoard(Location location) {
        return (location.x >= 0 && location.x < 8 && location.y >= 0 && location.y < 8);

    }

}
