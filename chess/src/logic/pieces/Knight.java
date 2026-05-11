package logic.pieces;

import logic.*;
import java.util.ArrayList;

public class Knight extends Piece {

    private static int[][] localMoves = {{-2 , 1}, {-2 , -1}, {-1, 2}, {-1, -2}, {1, 2}, {1, -2}, {2, 1} {2, -1}};

    public Knight(Location position, Color color) {
        super(position, color);
    }

    public ArrayList<Location> getValidMoves(Square[][] board) {
        ArrayList<Location> moves = new ArrayList<Location>();
        
        for(i = 0; i < 9; i++) {
            moves.add( localMoves[i][0], localMoves[i][1] )); 
        }
        // colorcheck? inboard?
        return moves;
    }

}