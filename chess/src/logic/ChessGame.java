package logic;

import java.util.Optional;
import java.util.List;
import logic.pieces.Piece;

interface ChessGame {
    List<Location> availableMoves(Piece piece);

    MoveResult movePiece(Piece piece, Location target);

    GameStatus getGameStatus();

    Optional<Piece> getPieceAt(Location location);

    Board getBoard();

    Player getCurrentPlayer();
}
