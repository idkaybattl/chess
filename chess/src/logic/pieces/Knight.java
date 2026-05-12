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
        
        for(i = 0; i < 8; i++) {
            Location move = new Location(position.x + localMoves[i][0], position.y + localMoves[i][1]);

            if (filter(board[move.x][move.y]) && inBoard(move)) {
                moves.add(move);
            }
             
        }
    
        return moves;
    }

}