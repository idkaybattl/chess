package logic;

import java.util.ArrayList;
import java.util.Optional;
import logic.pieces.Piece;
import logic.pieces.Pawn;

public class GameLogic implements ChessGame {
    Player white;
    Player black;

    Player currentPlayer;

    ArrayList<Move> moveHistory;

    Board board;

    public GameLogic() {
        white = new Player(ChessColor.WHITE);
        black = new Player(ChessColor.BLACK);
        board = new Board(white, black);
        currentPlayer = white;
    }

    public ArrayList<Location> availableMoves(Piece piece) {
        ArrayList<Location> validMoves = piece.getValidMoves(board);

        if (piece instanceof Pawn) {
            validMoves.addAll(getEnPassantMoves((Pawn) piece));
        }

        Location originalPos = new Location(piece.getPos());
        Player player = (piece.getColor() == ChessColor.WHITE) ? white : black;

        // simulating piece move, then undoing
        for (int i = 0; i < validMoves.size(); i++) {
            board.movePiece(piece, validMoves.get(i));
            if (inCheck(player)) {
                validMoves.remove(i);
                i--;
            }
            board.undoMove(piece, originalPos);
        }

        return validMoves;
    }

    public boolean anyMovesLeft(Player player) {
        for (Piece piece : player.getActivePieces()) {
            if (availableMoves(piece).size() > 0) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Location> getEnPassantMoves(Pawn pawn) {
        var moves = new ArrayList<Location>();

        if (moveHistory.isEmpty()) {
            return moves;
        }

        Move lastMove = moveHistory.get(moveHistory.size() - 1);

        if (!(lastMove.getPiece() instanceof Pawn)) {
            return moves;
        }

        Location start = lastMove.getStart();
        Location target = lastMove.getTarget();
        Location pawnPos = pawn.getPos();

        boolean movedTwoSquares = Math.abs(target.y - start.y) == 2;
        boolean endedBesidePawn = (target.y == pawnPos.y && Math.abs(target.x - pawnPos.x) == 1);

        if (!movedTwoSquares || !endedBesidePawn) {
            return moves;
        }

        int direction = pawn.getColor() == ChessColor.WHITE ? 1 : -1;
        moves.add(new Location(target.x, pawnPos.y + direction));

        return moves;
    }

    private ArrayList<Location> allAttackedSquares(Player player) {
        var attackedSquares = new ArrayList<Location>();

        for (Piece piece : player.getActivePieces()) {
            attackedSquares.addAll(piece.getAttackedSquares(board));
        }

        return attackedSquares;
    }

    // move piece, check if game is finished and change player
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

                    moveHistory.add(new Move(piece, piece.getPos(), target));

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

    public MoveResult movePiece(Piece piece, int x, int y) {
        return movePiece(piece, new Location(x, y));
    }

    public GameStatus getGameStatus() {
        // TODO:
        // Check for draws:
        // insufficient material
        // 50 moves
        // repetition

        if (!anyMovesLeft(white)) {
            if (inCheck(white)) {
                return GameStatus.BLACK;
            } else {
                return GameStatus.DRAW;
            }
        }

        if (!anyMovesLeft(black)) {
            if (inCheck(black)) {
                return GameStatus.WHITE;
            } else {
                return GameStatus.DRAW;
            }
        }

        return GameStatus.ONGOING;
    }

    private boolean inCheck(Player player) {
        Player enemy = (player.getColor() == ChessColor.WHITE) ? black : white;
        ArrayList<Location> attackedLocations = allAttackedSquares(enemy);

        for (Location attackedLocation : attackedLocations) {
            if (getPieceAt(attackedLocation).isPresent() && getPieceAt(attackedLocation).get() == player.getKing()) {
                return true;
            }
        }

        return false;
    }

    public Optional<Piece> getPieceAt(Location location) {
        return board.getPiece(location);
    }

    public Optional<Piece> getPieceAt(int x, int y) {
        return board.getPiece(x, y);
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
