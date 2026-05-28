package logic;

import java.util.Optional;
import java.util.List;

import logic.pieces.Piece;
import logic.pieces.Promotable;
import logic.pieces.Pawn;

public interface ChessGame {
    List<Location> availableMoves(Piece piece);

    MoveResult movePiece(Piece piece, Location target);

    MoveResult movePiece(Piece piece, int x, int y);

    MoveResult promote(Pawn pawn, Location origin, Location target, Class<? extends Promotable> type);

    GameStatus getGameStatus();

    boolean inCheck(ChessColor player);

    Optional<Piece> getPieceAt(Location location);

    Optional<Piece> getPieceAt(int x, int y);

    Board getBoard();

    ChessColor getCurrentPlayer();
}
