package logic.engine;

import java.util.List;
import java.util.Random;

import logic.ChessGame;
import logic.Location;
import logic.pieces.Piece;

// chooses random piece and then moves it to a random spot
public class CompletelyRandomEngine implements EngineOpponent {
    private final Random random = new Random();

    public CompletelyRandomEngine() {

    }

    public MoveChoice requestMove(ChessGame logic) {
        List<Piece> pieces = logic.getPieces(logic.getCurrentPlayer());
        Piece piece = pieces.get((int) (Math.random() * pieces.size()));

        for (int attempts = 0; attempts < 64; attempts++) {
            Location location = new Location(random.nextInt(8), random.nextInt(8));

            if (logic.getPieceAt(location).isEmpty()) {
                return new MoveChoice(piece.getPos(), location, null);
            }
        }

        throw new IllegalStateException("No empty square available");

    }
}
