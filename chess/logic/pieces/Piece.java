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

    public abstract ArrayList<Location> getValidMoves(Square[][] board);

    public void move(Location target) {
        this.position = target;
    }

    public void take() {
        taken = true;
    }

    public ArrayList<Square> rayCast(int x, int y, Square[][] board) {
        int newX = this.getPos().x + x;
        int newY = this.getPos().y + y;

        ArrayList<Square> squares = new ArrayList<Square>();

        while (!board[newX][newY].getPiece().isPresent()) {
            squares.add(board[newX][newY]);

            newX += x;
            newY += y;
        }

        return squares;
    }
}
