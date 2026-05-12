package logic.pieces;

import logic.*;
import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(Location position, Color color) {
        super(position, color);
    }

    public ArrayList<Location> getValidMoves(Board board) {
        var moves = new ArrayList<Location>();

        moves.addAll(filteredRayCast(1, 1, board));
        moves.addAll(filteredRayCast(1, -1, board));
        moves.addAll(filteredRayCast(-1, 1, board));
        moves.addAll(filteredRayCast(-1, -1, board));

        moves.addAll(filteredRayCast(1, 0, board));
        moves.addAll(filteredRayCast(-1, 0, board));
        moves.addAll(filteredRayCast(0, 1, board));
        moves.addAll(filteredRayCast(0, -1, board));

        return moves;
    }

}
