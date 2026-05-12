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

            Location longMove = new Location(forwardMove.x, forwardMove.y + yMoveDirection);
            // if square is free and pawn is on proper rank
            if (!board.getPiece(longMove).isPresent() && inBoard(forwardMove)
                    && ((getColor() == Color.WHITE && getPos().y == 1)
                            || (getColor() == Color.BLACK && getPos().y == 6))) {
                moves.add(longMove);
            }
        }

        Location[] sideMoves = { new Location(forwardMove.x - 1, forwardMove.y),
                new Location(forwardMove.x + 1, forwardMove.y) };

        if (board.getPiece(sideMoves[0]).isPresent() && inBoard(sideMoves[0])
                && filter(sideMoves[0], board)) {
            moves.add(sideMoves[0]);
        }

        if (board.getPiece(sideMoves[1]).isPresent() && inBoard(sideMoves[1])
                && filter(sideMoves[1], board)) {
            moves.add(sideMoves[1]);
        }

        // TODO: en passant

        return moves;
    }

}
