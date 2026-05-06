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
        return Optional.ofNullable(this.piece);
    }
}
