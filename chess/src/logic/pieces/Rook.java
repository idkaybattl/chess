package logic.pieces;

import logic.*;
import java.util.ArrayList;

public class Rook extends Piece {
    public Rook(Location position, Color color) {
        super(position, color);
    }

    public ArrayList<Location> getValidMoves(Square[][] board) {
        ArrayList<Location> moves = new ArrayList<Location>();
        
        moves.addAll(filteredRayCast(1, 0, board));
        moves.addAll(filteredRayCast(-1, 0, board));
        moves.addAll(filteredRayCast(0, 1, board));
        moves.addAll(filteredRayCast(0, -1, board));
        
        return moves;
    }

}
