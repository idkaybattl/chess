package logic.pieces;

import logic.*;
import java.util.ArrayList;

public class Bishop extends Promotable {
    public Bishop(Location position, ChessColor color) {
        super(position, color);
    }

    public ArrayList<Location> getValidMoves(Board board) {
        var moves = new ArrayList<Location>();

        moves.addAll(filteredRayCast(1, 1, board));
        moves.addAll(filteredRayCast(1, -1, board));
        moves.addAll(filteredRayCast(-1, 1, board));
        moves.addAll(filteredRayCast(-1, -1, board));

        return moves;
    }

    public boolean getSquareColor() {
        return ((position.getX() + position.getY()) % 2 == 0);
        // true = dark Square Bishop
    }

    public PieceType getType() {
        return PieceType.BISHOP;
    }
}
