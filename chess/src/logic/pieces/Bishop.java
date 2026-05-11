package logic.pieces;

import logic.*;
import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(Location position, Color color) {
        super(position, color);
    }

    public ArrayList<Location> getValidMoves(Square[][] board) {
        ArrayList<Location> moves = new ArrayList<Location>();
        
        moves.addAll(filteredRayCast(1, 1, board));
        moves.addAll(filteredRayCast(1, -1, board));
        moves.addAll(filteredRayCast(-1, 1, board));
        moves.addAll(filteredRayCast(-1, -1, board));
        
        return moves;
    }

}
