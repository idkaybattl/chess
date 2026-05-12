package logic.pieces;

import logic.*;
import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(Location position, Color color) {
        super(position, color);
    }

    public ArrayList<Location> getValidMoves(Board board) {
        var moves = new ArrayList<Location>();
        int yMoveDirection = (getColor() == Color.WHITE) ? 1 : -1;

        Location forwardMove = new Location(getPos().x, getPos().y + yMoveDirection);

        if (!board.getPiece(forwardMove).isPresent() && inBoard(forwardMove)) {
            moves.add(forwardMove);
        }

        if (board.getPiece(forwardMove).isPresent() && inBoard(forwardMove)
                && filter(forwardMove, board)) {
            moves.add(new Location(forwardMove.x - 1, forwardMove.y));
        }

        if (board.getPiece(forwardMove).isPresent() && inBoard(forwardMove)
                && filter(forwardMove, board)) {
            moves.add(new Location(forwardMove.x + 1, forwardMove.y));
        }

        return moves;
    }

}
