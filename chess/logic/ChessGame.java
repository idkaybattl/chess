package logic;

import java.util.Optional;

interface ChessGame {
    Location[] availableMoves(Piece piece);

    boolean movePiece(Piece piece, Location target);

    GameStatus getGameStatus();

    Optional<Piece> getPieceAt(Location location);

    Square[][] getBoard();

    Player getCurrentPlayer();
}
