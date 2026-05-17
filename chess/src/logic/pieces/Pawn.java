package logic.pieces;

import logic.*;
import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(Location position, ChessColor color) {
        super(position, color);
    }

    public ArrayList<Location> getValidMoves(Board board) {
        var moves = new ArrayList<Location>();
        int yMoveDirection = (getColor() == ChessColor.WHITE) ? 1 : -1;

        Location forwardMove = new Location(getPos().x, getPos().y + yMoveDirection);

        if (forwardMove.inBoard() && !board.getPiece(forwardMove).isPresent()) {
            moves.add(forwardMove);

            Location longMove = new Location(forwardMove.x, forwardMove.y + yMoveDirection);
            // if square is free and pawn is on proper rank
            if (forwardMove.inBoard() && !board.getPiece(longMove).isPresent()
                    && ((getColor() == ChessColor.WHITE && getPos().y == 1)
                            || (getColor() == ChessColor.BLACK && getPos().y == 6))) {
                moves.add(longMove);
            }
        }

        Location[] sideMoves = { new Location(forwardMove.x - 1, forwardMove.y),
                new Location(forwardMove.x + 1, forwardMove.y) };

        if (sideMoves[0].inBoard() && board.getPiece(sideMoves[0]).isPresent()
                && filter(sideMoves[0], board)) {
            moves.add(sideMoves[0]);
        }

        if (sideMoves[1].inBoard() && board.getPiece(sideMoves[1]).isPresent()
                && filter(sideMoves[1], board)) {
            moves.add(sideMoves[1]);
        }

        return moves;
    }

}
