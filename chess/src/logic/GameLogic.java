package logic;

import java.util.ArrayList;
import java.util.Optional;
import logic.pieces.Pawn;
import logic.pieces.King;
import logic.pieces.Rook;
import logic.pieces.Piece;
import logic.pieces.Bishop;

public class GameLogic implements ChessGame {
    Player white;
    Player black;

    Player currentPlayer;

    ArrayList<Move> moveHistory;

    Board board;

    public GameLogic() {
        white = new Player(ChessColor.WHITE);
        black = new Player(ChessColor.BLACK);
        moveHistory = new ArrayList<>();
        board = new Board(white, black);
        currentPlayer = white;
    }

    public ArrayList<Location> availableMoves(Piece piece) {
        ArrayList<Location> validMoves = piece.getValidMoves(board);

        Player player = (piece.getColor() == ChessColor.WHITE) ? white : black;

        if (piece instanceof Pawn) {
            validMoves.addAll(getEnPassantMoves((Pawn) piece));
        }

        if (piece instanceof King) {
            validMoves.addAll(getCastleMoves(player));
        }

        // Simulate each move and remove moves that leave this player's king in check.
        for (int i = 0; i < validMoves.size(); i++) {
            Location target = validMoves.get(i);
            TempMove tempMove = board.tempMove(piece, target, getCapturedLocation(piece, target));

            if (inCheck(player)) {
                validMoves.remove(i);
                i--;
            }

            board.undoTempMove(tempMove);
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

        if (!(lastMove.getPiece() instanceof Pawn) || lastMove.getPiece().getColor() == pawn.getColor()) {
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
        Location enPassantTarget = new Location(target.x, pawnPos.y + direction);

        if (enPassantTarget.inBoard()) {
            moves.add(enPassantTarget);
        }

        return moves;
    }

    private ArrayList<Location> getCastleMoves(Player player) {
        ArrayList<Location> castleMoves = new ArrayList<>();
        Player otherPlayer = (player.getColor() == ChessColor.WHITE) ? black : white;

        if (!player.getKing().hasMoved()) {
            int backrank = (player.getColor() == ChessColor.WHITE) ? 0 : 7;

            Optional<Piece> qSideSquare = board.getPiece(0, backrank);
            Optional<Piece> kSideSquare = board.getPiece(7, backrank);

            // long castle
            if (qSideSquare.isPresent()) {
                Piece rook = qSideSquare.get();
                if ((rook instanceof Rook)
                        && !rook.hasMoved()
                        && board.getPiece(1, backrank).isEmpty()
                        && board.getPiece(2, backrank).isEmpty()
                        && board.getPiece(3, backrank).isEmpty()
                        && !inCheck(player)
                        && !allAttackedSquares(otherPlayer).contains(new Location(2, backrank))
                        && !allAttackedSquares(otherPlayer).contains(new Location(3, backrank))) {
                    castleMoves.add(new Location(2, backrank));
                }
            }

            // short castle
            if (kSideSquare.isPresent()) {
                Piece rook = kSideSquare.get();
                var attackedSquares = allAttackedSquares(otherPlayer);
                if ((rook instanceof Rook)
                        && !rook.hasMoved()
                        && board.getPiece(5, backrank).isEmpty()
                        && board.getPiece(6, backrank).isEmpty()
                        && !inCheck(player)
                        && !attackedSquares.contains(new Location(5, backrank))
                        && !attackedSquares.contains(new Location(6, backrank))) {
                    castleMoves.add(new Location(6, backrank));
                }
            }
        }

        return castleMoves;
    }

    private ArrayList<Location> allAttackedSquares(Player player) {
        var attackedSquares = new ArrayList<Location>();

        for (Piece piece : player.getActivePieces()) {
            attackedSquares.addAll(piece.getAttackedSquares(board));
        }

        return attackedSquares;
    }

    public MoveResult movePiece(Piece piece, Location target) {
        if (currentPlayer.getColor() != piece.getColor()) {
            return MoveResult.NOT_YOUR_TURN;
        }

        if (getGameStatus() != GameStatus.ONGOING) {
            return MoveResult.GAME_OVER;
        }

        ArrayList<Location> availableMoves = availableMoves(piece);
        boolean allowedMove = availableMoves.contains(target);

        if (!allowedMove) {
            return MoveResult.ILLEGAL_MOVE;
        }

        Location origin = new Location(piece.getPos());
        Location capturedLocation = getCapturedLocation(piece, target);
        Optional<Piece> capturedPiece = board.getPiece(capturedLocation);

        // TODO: extra case for castling
        // add board.castle(king, rook)
        board.movePiece(piece, target, capturedLocation);
        moveHistory.add(new Move(piece, origin, target, capturedPiece.orElse(null), capturedLocation));

        currentPlayer = (currentPlayer == white) ? black : white;
        if (getGameStatus() != GameStatus.ONGOING) {
            return MoveResult.GAME_OVER;
        }

        return MoveResult.MOVED;
    }

    public MoveResult movePiece(Piece piece, int x, int y) {
        return movePiece(piece, new Location(x, y));
    }

    public GameStatus getGameStatus() {
        // TODO:
        // Check for draws:
        // insufficient material
                // only king / king + knight / king + bishop / king + 2 same colored bishops
        //not only current player but both (am to stupid for that in 1 rn)
        if (currentPlayer.getActivePieces(Queen).isEmpty() && currentPlayer.getActivePieces(Rook).isEmpty() ) {
            if (currentPlayer.getActivePieces(Knight).size() < 2 && currentPlayer.getActivePieces(Bishop).isEmpty() ||
                currentPlayer.getActivePieces(Knight).isEmpty() && currentPlayer.getActivePieces(Bishop).size() < 2 ||
                currentPlayer.getActivePieces(Knight).isEmpty() && currentPlayer.getActivePieces(Bishop).size() < 3
                    && currentPlayer.getActivePieces(Bishop).get(0).getSquareColor() == currentPlayer.getActivePieces(Bishop).get(1).getSquareColor()){


                    }
        // 50 moves
        // repetition

        if (!anyMovesLeft(currentPlayer)) {
            if (inCheck(currentPlayer)) {
                return currentPlayer == white ? GameStatus.BLACK : GameStatus.WHITE;
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

    private Location getCapturedLocation(Piece piece, Location target) {
        if (piece instanceof Pawn && piece.getPos().x != target.x && board.getPiece(target).isEmpty()) {
            return new Location(target.x, piece.getPos().y);
        }

        return target;
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

