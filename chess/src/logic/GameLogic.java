package logic;

import java.util.ArrayList;
import java.util.Optional;
import logic.pieces.Piece;

public class GameLogic implements ChessGame {
    Player white;
    Player black;

    Player currentPlayer;

    ArrayList<Move> moveHistory;

    Board board;

    public GameLogic() {
        white = new Player(Color.WHITE);
        black = new Player(Color.BLACK);
        board = new Board(white, black);
        currentPlayer = white;
    }

    public ArrayList<Location> availableMoves(Piece piece) {
        return piece.getValidMoves(board);

        // TODO, filter moves that lead to check out
    }

    public MoveResult movePiece(Piece piece, Location target) {
        if (currentPlayer.getColor() == piece.getColor()) {
            if (getGameStatus() == GameStatus.ONGOING) {
                ArrayList<Location> availableMoves = availableMoves(piece);
                boolean allowedMove = availableMoves.contains(target);

                if (allowedMove) {
                    board.movePiece(piece, target);

                    currentPlayer = (currentPlayer == white) ? black : white;
                    if (getGameStatus() != GameStatus.ONGOING) {
                        return MoveResult.GAME_OVER;
                    }

                    moveHistory.add(new Move(piece.getPos(), target));

                    return MoveResult.MOVED;
                } else {
                    return MoveResult.ILLEGAL_MOVE;
                }
            } else {
                return MoveResult.GAME_OVER;
            }
        } else {
            return MoveResult.NOT_YOUR_TURN;
        }
    }

    public GameStatus getGameStatus() {
        PlayerStatus whiteStatus = white.getStatus();
        PlayerStatus blackStatus = black.getStatus();

        // Check for draws:
        // insufficient material
        // 50 moves
        // repetition

        // placeholder
        // TODO
        return GameStatus.ONGOING;
    }

    private boolean inCheck(Player player) {
        return true;
    }

    private PlayerStatus getPlayerStatus(Player player) {
        // if (player.getSpecificPieces(King.class).availableMoves.length == 0) {

        // placeholder
        if (true) {
            return PlayerStatus.ONGOING;
        } else {
            return PlayerStatus.ONGOING;
        }
    }

    public Optional<Piece> getPieceAt(Location location) {
        return board.getPiece(location);
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
