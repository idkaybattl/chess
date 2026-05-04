package logic;

import logic.Location;

public class Piece {
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

    public Location[] getValidMoves(Square[][] board) {
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
}
