package logic.pieces;

import logic.*;
import java.util.ArrayList;

public class King extends Piece {

    public King(Location position, ChessColor color) {
        super(position, color);
    }

    private static int[][] localMoves = { { -1, -1 }, { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 },
            { -1, 0 } };

    public ArrayList<Location> getValidMoves(Board board) {
        var moves = new ArrayList<Location>();

        for (int i = 0; i < 8; i++) {
            Location move = new Location(getPos().x + localMoves[i][0], getPos().y + localMoves[i][1]);

            if (move.inBoard() && filter(move, board)) {
                moves.add(move);
            }
        }

        return moves;
    }

}
