package logic.pieces;

import logic.*;
import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(Location position, Color color) {
        super(position, color);
    }

    public ArrayList<Location> getValidMoves(Square[][] board) {
        ArrayList<Location> moves = new ArrayList<Location>();
        ArrayList<Square> posSquares = rayCast(1, 1, board);
        // check if last piece is same color
        // if then remove
        posSquares.addAll(rayCast(1, -1, board));

        posSquares.addAll(rayCast(-1, 1, board));

        posSquares.addAll(rayCast(-1, -1, board));

        return moves;
    }

}
