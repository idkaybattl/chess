package logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import logic.pieces.Pawn;
import logic.pieces.Bishop;
import logic.pieces.King;
import logic.pieces.Knight;
import logic.pieces.Rook;
import logic.pieces.Piece;
import logic.pieces.Queen;

public class GameLogic implements ChessGame {
    ChessColor currentPlayer;

    ArrayList<Move> moveHistory;
    // since last pawn move or capture
    ArrayList<SimplifiedPosition> boardHistory;

    Board board;

    public GameLogic() {
        moveHistory = new ArrayList<>();
        board = new Board();
        currentPlayer = ChessColor.WHITE;
        boardHistory.add(createSimplifiedPosition());
    }

    public ArrayList<Location> availableMoves(Piece piece) {
        ArrayList<Location> validMoves = piece.getValidMoves(board);

        ChessColor player = piece.getColor();

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

    public boolean anyMovesLeft(ChessColor player) {
        for (Piece piece : board.getActivePieces(player)) {
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

    private ArrayList<Location> getCastleMoves(ChessColor player) {
        ArrayList<Location> castleMoves = new ArrayList<>();
        ChessColor otherPlayer = (player == ChessColor.WHITE) ? ChessColor.BLACK : ChessColor.WHITE;

        if (!board.getKing(player).hasMoved()) {
            int backrank = (player == ChessColor.WHITE) ? 0 : 7;

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

    private ArrayList<Location> allAttackedSquares(ChessColor player) {
        var attackedSquares = new ArrayList<Location>();

        for (Piece piece : board.getActivePieces(player)) {
            attackedSquares.addAll(piece.getAttackedSquares(board));
        }

        return attackedSquares;
    }

    private SimplifiedPosition createSimplifiedPosition() {
        // TODO
        var whiteCastleMoves = getCastleMoves(ChessColor.WHITE);
        var blackCastleMoves = getCastleMoves(ChessColor.BLACK);

        boolean whiteKSideCastle = false;
        boolean whiteQSideCastle = false;
        if (whiteCastleMoves.size() >= 2) {
            whiteKSideCastle = true;
            whiteQSideCastle = true;
        } else if (whiteCastleMoves.size() == 1) {
            if (whiteCastleMoves.get(0).getX() == 6) {
                whiteKSideCastle = true;
            } else {
                whiteQSideCastle = true;
            }
        }

        boolean blackKSideCastle = false;
        boolean blackQSideCastle = false;
        if (blackCastleMoves.size() >= 2) {
            blackKSideCastle = true;
            blackQSideCastle = true;
        } else if (blackCastleMoves.size() == 1) {
            if (blackCastleMoves.get(0).getX() == 6) {
                blackKSideCastle = true;
            } else {
                blackQSideCastle = true;
            }
        }

        return new SimplifiedPosition(board.simplifyBoard(), currentPlayer, whiteKSideCastle, whiteQSideCastle,
                blackKSideCastle, blackQSideCastle, getEnPassantTarget());
    }

    // null if not available
    private Location getEnPassantTarget() {
        if (moveHistory.size() > 0) {
            Move lastMove = moveHistory.getLast();
            Location start = lastMove.getStart();
            Location target = lastMove.getTarget();

            if (Math.abs(start.getY() - target.getY()) == 2) {
                Optional<Piece> leftOptionalPiece = board.getPiece(target.getX() - 1, target.getY());
                if (leftOptionalPiece.isPresent()) {
                    Piece leftPiece = leftOptionalPiece.get();
                    if (leftPiece instanceof Pawn && leftPiece.getColor() == currentPlayer) {
                        return target;
                    }
                }
                Optional<Piece> rightOptionalPiece = board.getPiece(target.getX() + 1, target.getY());
                if (rightOptionalPiece.isPresent()) {
                    Piece rightPiece = rightOptionalPiece.get();
                    if (rightPiece instanceof Pawn && rightPiece.getColor() == currentPlayer) {
                        return target;
                    }
                }
            }

        }
        return null;
    }

    public MoveResult movePiece(Piece piece, Location target) {
        if (currentPlayer != piece.getColor()) {
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

        board.movePiece(piece, target, capturedLocation);
        moveHistory.add(new Move(piece, origin, target, capturedPiece.orElse(null), capturedLocation));

        if (piece instanceof Pawn || capturedPiece.isPresent()) {
            boardHistory = new ArrayList<>();
        } else {
            boardHistory.add(createSimplifiedPosition());
        }

        currentPlayer = (currentPlayer == ChessColor.WHITE) ? ChessColor.BLACK : ChessColor.WHITE;
        if (getGameStatus() != GameStatus.ONGOING) {
            return MoveResult.GAME_OVER;
        }

        return MoveResult.MOVED;
    }

    public MoveResult movePiece(Piece piece, int x, int y) {
        return movePiece(piece, new Location(x, y));
    }

    private boolean hasInsufficientMaterial(ChessColor player) {
        ChessColor otherPlayer = (player == ChessColor.WHITE) ? ChessColor.BLACK : ChessColor.WHITE;

        var uniquePieces = board.uniquePieces(player);
        // A king + any(pawn, rook, queen) is sufficient.
        if (!uniquePieces.contains(Queen.class)
                && !uniquePieces.contains(Rook.class)
                && !uniquePieces.contains(Pawn.class)) {

            // A king and two (or more) knights is sufficient.
            if (board.getActivePieces(player, Knight.class).size() >= 2) {
                return false;
            }

            // A king and more than one other type of piece is sufficient (e.g. knight +
            // bishop).
            // i.e. there are at least three different pieces: king + two others
            if (uniquePieces.size() >= 3) {
                return false;
            }

            var opponentUniquePieces = board.uniquePieces(otherPlayer);
            // King + knight against king + any(rook, bishop, knight, pawn) is sufficient.
            if (uniquePieces.contains(Knight.class)
                    && (opponentUniquePieces.contains(Rook.class)
                            || opponentUniquePieces.contains(Bishop.class)
                            || opponentUniquePieces.contains(Knight.class)
                            || opponentUniquePieces.contains(Pawn.class))) {
                return false;
            }

            // King + bishop against king + any(knight, pawn) is sufficient.
            if (uniquePieces.contains(Bishop.class)
                    && (opponentUniquePieces.contains(Knight.class)
                            || opponentUniquePieces.contains(Pawn.class))) {
                return false;
            }

            // King + bishop(s) is also sufficient if there's bishops on opposite colours
            // (even king + bishop against king + bishop).
            ArrayList<Bishop> bishops = board.getActivePieces(player, Bishop.class);
            bishops.addAll(board.getActivePieces(otherPlayer, Bishop.class));
            HashSet<Boolean> bishopColors = new HashSet<>();
            for (Bishop bishop : bishops) {
                bishopColors.add(bishop.getSquareColor());
            }
            if (bishopColors.size() == 2) {
                return false;
            }

            // none of the sufficient material conditions are given
            return true;
        }

        return false;
    }

    public GameStatus getGameStatus() {
        // TODO:
        // Check for draws:
        // insufficient material

        if (hasInsufficientMaterial(ChessColor.WHITE) && hasInsufficientMaterial(ChessColor.BLACK)) {
            return GameStatus.DRAW;
        }

        // repetition
        // same position appears thrice in history
        // includes piece position and possible moves
        // i.e. have to account for castling and en passant rights
        // board history can be reset when capturing or moving pawn
        int samePositionCount = 1;
        SimplifiedPosition currentPosition = boardHistory.getLast();
        for (int i = boardHistory.size() - 2; i >= 0; i--) {
            if (boardHistory.get(i) == currentPosition) {
                samePositionCount++;
            }
        }
        if (samePositionCount >= 3) {
            return GameStatus.DRAW;
        }

        // 50 moves
        // 100 moves total
        if (boardHistory.size() > 100) {
            return GameStatus.DRAW;
        }

        if (!anyMovesLeft(ChessColor.WHITE)) {
            if (inCheck(ChessColor.WHITE)) {
                return GameStatus.BLACK;
            } else {
                return GameStatus.DRAW;
            }
        }
        if (!anyMovesLeft(ChessColor.BLACK)) {
            if (inCheck(ChessColor.BLACK)) {
                return GameStatus.WHITE;
            } else {
                return GameStatus.DRAW;
            }
        }

        return GameStatus.ONGOING;
    }

    public boolean inCheck(ChessColor player) {
        ChessColor enemy = (player == ChessColor.WHITE) ? ChessColor.BLACK : ChessColor.WHITE;
        ArrayList<Location> attackedLocations = allAttackedSquares(enemy);

        for (Location attackedLocation : attackedLocations) {
            if (getPieceAt(attackedLocation).isPresent()
                    && getPieceAt(attackedLocation).get() == board.getKing(player)) {
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

    public ChessColor getCurrentPlayer() {
        return currentPlayer;
    }
}
