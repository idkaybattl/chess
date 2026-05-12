package logic;

import java.util.Optional;
import java.util.ArrayList;
import logic.pieces.Piece;

public class Board {
    private Square[][] squares;

    public Board(Player white, Player black) {
        squares = new Square[8][8];
        for (Square[] row : squares) {
            for (Square square : row) {
                square = new Square();
            }
        }

        // TODO: piece setup
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

    public ArrayList<Piece> getSpecificPieces(Class<?> pieceClass) {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for (Square[] row : squares) {
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

    public void movePiece(Piece piece, Location target) {
        getSquare(piece.getPos()).removePiece();
        piece.move(target);
        getSquare(target).setPiece(piece);
    }
}
