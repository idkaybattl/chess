package logic.pieces;

import logic.*;
import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(Location position, Color color) {
        super(position, color);
    }

    
    public ArrayList<Location> getValidMoves(Square[][] board) {
        ArrayList<Location> moves = new ArrayList<Location>();
        int yMoveDirection = (color == Color.WHITE) ? 1 : -1;
        
        Location forwardMove = new Location(position.x , position.y + yMoveDirection);
        
        if (!board[forwardMove.x][forwardMove.y].isPresent() && inBoard()) {
            moves.add(forwardMove);
        }

        if (board[forwardMove.x - 1][forwardMove.y].isPresent() && inBoard() && filter(board[move.x][move.y]) ) {
            moves.add( new Location(forwardMove.x - 1, forwardMove.y));
        }

        if (board[forwardMove.x + 1][forwardMove.y].isPresent() && inBoard() && filter(board[move.x][move.y]) ) {
            moves.add( new Location(forwardMove.x + 1, forwardMove.y));
        }

        return moves;
    }

}