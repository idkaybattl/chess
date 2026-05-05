package logic;

import logic.Location;
import java.util.ArrayList;

public abstract class Piece {
    Location position;
    Color color;
    boolean taken;

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

    public abstract Location[] getValidMoves(Square[][] board) {
        // filler

        Location[] moves = new Location[0];
        return moves;
    }

    public void move(Location target) {
        this.position = target;
    }

    public void take() {
        taken = true;
    }


    public ArrayList<Square> RayCast(int x, int y, Square[][] board) {
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



