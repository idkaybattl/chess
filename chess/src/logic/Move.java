package logic;

import logic.pieces.Piece;

public class Move {
    private Piece piece;
    private Location start;
    private Location target;

    public Move(Piece piece, Location start, Location target) {
        this.piece = piece;
        this.start = start;
        this.target = target;
    }

    public String algebraicNotation() {
        return (start.algebraicNotation() + target.algebraicNotation());
    }

    public Location getStart() {
        return start;
    }

    public Location getTarget() {
        return target;
    }
}
