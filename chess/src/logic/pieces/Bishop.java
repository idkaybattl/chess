package logic.pieces;

import logic.*;
import java.util.ArrayList;

public class Bishop extends Piece {
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

}
