package logic;

import java.util.Optional;
import java.util.List;
import logic.pieces.Piece;

public interface ChessGame {
    List<Location> availableMoves(Piece piece);

    MoveResult movePiece(Piece piece, Location target);

    MoveResult movePiece(Piece piece, int x, int y);

    GameStatus getGameStatus();

    Optional<Piece> getPieceAt(Location location);

    Optional<Piece> getPieceAt(int x, int y);

    Board getBoard();

    ChessColor getCurrentPlayer();
}
