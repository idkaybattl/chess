package logic;

import java.util.Optional;
import java.util.List;
import logic.pieces.Piece;

interface ChessGame {
    List<Location> availableMoves(Piece piece);

    boolean movePiece(Piece piece, Location target);

    GameStatus getGameStatus();

    Optional<Piece> getPieceAt(Location location);

    Square[][] getBoard();

    Player getCurrentPlayer();
}
