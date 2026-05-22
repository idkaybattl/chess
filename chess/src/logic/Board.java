package logic;

import java.util.ArrayList;
import java.util.Optional;
import logic.pieces.Bishop;
import logic.pieces.King;
import logic.pieces.Knight;
import logic.pieces.Pawn;
import logic.pieces.Piece;
import logic.pieces.Queen;
import logic.pieces.Rook;

public class Board {
    private Square[][] squares;

    public Board(Player white, Player black) {
        squares = new Square[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                squares[x][y] = new Square();
            }
        }

        setupPieces(white);
        setupPieces(black);
    }

    private void setupPieces(Player player) {
        ChessColor color = player.getColor();
        int backRank = color == ChessColor.WHITE ? 0 : 7;
        int pawnRank = color == ChessColor.WHITE ? 1 : 6;

        Piece[] pieces = new Piece[] {
                new Rook(new Location(0, backRank), color),
                new Knight(new Location(1, backRank), color),
                new Bishop(new Location(2, backRank), color),
                new Queen(new Location(3, backRank), color),
                new King(new Location(4, backRank), color),
                new Bishop(new Location(5, backRank), color),
                new Knight(new Location(6, backRank), color),
                new Rook(new Location(7, backRank), color),
                new Pawn(new Location(0, pawnRank), color),
                new Pawn(new Location(1, pawnRank), color),
                new Pawn(new Location(2, pawnRank), color),
                new Pawn(new Location(3, pawnRank), color),
                new Pawn(new Location(4, pawnRank), color),
                new Pawn(new Location(5, pawnRank), color),
                new Pawn(new Location(6, pawnRank), color),
                new Pawn(new Location(7, pawnRank), color)
        };

        for (Piece piece : pieces) {
            getSquare(piece.getPos()).setPiece(piece);
        }

        player.setPieces(pieces);
    }

    public Square[][] getSquares() {
        return squares;
    }

    public Optional<Piece> getPiece(Location location) {
        if (!location.inBoard()) {
            return Optional.empty();
        }

        return squares[location.getX()][location.getY()].getPiece();
    }

    public Optional<Piece> getPiece(int x, int y) {
        return getPiece(new Location(x, y));
    }

    private Square getSquare(Location location) {
        return squares[location.getX()][location.getY()];
    }

    public <T extends Piece> ArrayList<T> getSpecificPieces(Class<T> pieceClass) {
        ArrayList<T> pieces = new ArrayList<>();

        for (Square[] row : squares) {
            for (Square square : row) {
                square.getPiece().ifPresent(piece -> {
                    if (pieceClass.isInstance(piece)) {
                        pieces.add(pieceClass.cast(piece));
                    }
                });
            }
        }

        return pieces;
    }

    public void castle(King king, boolean queenSide) {
        int backrank = king.getPos().getX();
        Location newKingPos = new Location(((queenside) ? 2 : 6), backrank);
        Location newRookPos = new Location(((queenside) ? 3 : 5), backrank);
        Rook rook = (queenSide) ? getPiece(0, backrank) : getPiece(7, backrank);

        getSquare(newKingPos).setPiece(king);
        getSquare(newRookPos).setPiece(rook);
        getSquare(king.getPos()).removePiece();
        getSquare(rook.getPos()).removePiece();
        king.move(newKingPos);
        rook.move(newRookPos);
    }

    public void movePiece(Piece piece, Location target) {
        movePiece(piece, target, target);
    }

    public void movePiece(Piece piece, Location target, Location capturedLocation) {
        Optional<Piece> capturedPiece = getPiece(capturedLocation);

        if (capturedPiece.isPresent()) {
            capturedPiece.get().take();
            getSquare(capturedLocation).removePiece();
        }

        Location origin = new Location(piece.getPos());
        getSquare(origin).removePiece();
        piece.move(new Location(target));
        getSquare(target).setPiece(piece);
    }

    public TempMove tempMove(Piece piece, Location target) {
        return tempMove(piece, target, target);
    }

    public TempMove tempMove(Piece piece, Location target, Location capturedLocation) {
        Optional<Piece> capturedPiece = getPiece(capturedLocation);
        Location origin = new Location(piece.getPos());
        TempMove tempMove = new TempMove(capturedPiece, piece, origin, target, capturedLocation);

        if (capturedPiece.isPresent()) {
            capturedPiece.get().take();
            getSquare(capturedLocation).removePiece();
        }

        getSquare(origin).removePiece();
        piece.tempMove(new Location(target));
        getSquare(target).setPiece(piece);

        return tempMove;
    }

    public void undoTempMove(TempMove move) {
        Optional<Piece> capturedPiece = move.getCapturedPiece();

        getSquare(move.getTarget()).removePiece();
        move.getMovingPiece().undoTempMove(move.getOrigin());
        getSquare(move.getOrigin()).setPiece(move.getMovingPiece());

        if (capturedPiece.isPresent()) {
            Piece piece = capturedPiece.get();
            piece.restoreTaken(move.capturedPieceWasTaken());
            getSquare(move.getCapturedLocation()).setPiece(piece);
        }
    }
}
