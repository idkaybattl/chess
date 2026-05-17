package logic;

import java.util.Optional;
import java.util.ArrayList;
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

    public Board(Board other) {
        squares = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = other.squares[i][j];
            }
        }
    }

    public Square[][] getSquares() {
        return squares;
    }

    public Optional<Piece> getPiece(Location location) {
        return squares[location.getX()][location.getY()].getPiece();
    }

    public Optional<Piece> getPiece(int x, int y) {
        return squares[x][y].getPiece();
    }

    private Square getSquare(Location location) {
        return squares[location.getX()][location.getY()];
    }

    private Square getSquare(int x, int y) {
        return squares[x][y];
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

    public void movePiece(Piece piece, Location target) {
        if (getPiece(target).isPresent()) {
            getPiece(target).get().take();
        }
        getSquare(piece.getPos()).removePiece();
        piece.move(target);
        getSquare(target).setPiece(piece);
    }

    public void undoMove(Move move) {
        Optional<Piece> optionalPiece = getPiece(move.getTarget());
        if (optionalPiece.isPresent()) {
            movePiece(optionalPiece.get(), move.getStart());
        }
    }

    public void undoMove(Piece piece, Location origin) {
        movePiece(piece, origin);
    }
}
