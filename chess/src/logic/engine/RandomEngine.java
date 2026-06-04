package logic.engine;

import java.util.Collections;
import java.util.List;

import logic.ChessGame;
import logic.Location;
import logic.pieces.Piece;

// chooses random piece and then moves it to a random spot
public class RandomEngine implements EngineOpponent {
    public RandomEngine() {

    }

    public MoveChoice requestMove(ChessGame logic) {
        List<Piece> pieces = logic.getPieces(logic.getCurrentPlayer());
        Collections.shuffle(pieces);

        for (Piece piece : pieces) {
            List<Location> moves = logic.availableMoves(piece);
            if (moves.size() > 0) {
                Location move = moves.get((int) (Math.random() * moves.size()));
                return new MoveChoice(piece.getPos(), move, null);
            }
        }
        throw new IllegalStateException("No legal moves available");
    }
}
