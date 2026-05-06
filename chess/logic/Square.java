package logic;

import java.util.Optional;
import logic.pieces.Piece;

public class Square {
    Piece piece;

    public Square() {
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }

    public Optional<Piece> getPiece() {
        if (piece == null) {
            return Optional.empty();
        } else {
            return Optional.of(this.piece);
        }
    }
}
