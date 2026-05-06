package logic;

import java.util.ArrayList;
import java.util.Optional;
import logic.pieces.Piece;

public class GameLogic implements ChessGame {
    Player white;
    Player black;

    Player currentPlayer;

    Square[][] board;

    public GameLogic() {
        white = new Player(Color.WHITE);
        black = new Player(Color.BLACK);
        board = new Square[8][8];
        currentPlayer = white;

        for (Square[] row : board) {
            for (Square square : row) {
                square = new Square();
            }
        }

        // TODO, add Piece setup
    }

    public ArrayList<Location> availableMoves(Piece piece) {
        return piece.getValidMoves(board);
    }

    public boolean movePiece(Piece piece, Location target) {
        if (currentPlayer.getColor() == piece.getColor()) {
            ArrayList<Location> availableMoves = availableMoves(piece);
            boolean allowedMove = false;
            for (Location availableMove : availableMoves) {
                if (availableMove == target) {
                    allowedMove = true;
                    break;
                }
            }

            if (allowedMove) {
                board[piece.getPos().x][piece.getPos().y].removePiece();
                piece.move(target);
                board[target.x][target.y].setPiece(piece);

                currentPlayer = (currentPlayer == white) ? black : white;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
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
        return GameStatus.ONGOING;
    }

    private boolean inCheck(Color player) {
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

    private ArrayList<Piece> getSpecificPiecesOfPlayer(Color player, Class<?> pieceClass) {
        ArrayList<Piece> pieces = getSpecificPieces(pieceClass);
        ArrayList<Piece> piecesOfPlayer = new ArrayList<Piece>();
        for (Piece piece : pieces) {
            if (piece.getColor() == player) {
                piecesOfPlayer.add(piece);
            }
        }

        return piecesOfPlayer;
    }

    private ArrayList<Piece> getSpecificPieces(Class<?> pieceClass) {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for (Square[] row : board) {
            for (Square square : row) {
                if (square.getPiece().isPresent()) {
                    Piece piece = square.getPiece().get();
                    if (piece.getClass() == pieceClass) {
                        pieces.add(piece);
                    }
                }
            }
        }
        return pieces;
    }

    public Optional<Piece> getPieceAt(Location location) {
        return board[location.x][location.y].getPiece();
    }

    public Square[][] getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
