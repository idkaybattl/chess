package logic;

import java.util.ArrayList;
import logic.pieces.Piece;
import logic.pieces.King;

public class Player {
    private ChessColor color;
    private Piece[] pieces;
    private King king;

    public Player(ChessColor color) {
        this.color = color;
        pieces = new Piece[16];
    }

    public ChessColor getColor() {
        return color;
    }

    public boolean targetsEnemyKing() {
        // placeholder
        return false;
    }

    public King getKing() {
        return king;
    }

    public void setPieces(Piece[] pieces) {
        this.pieces = pieces;

        for (Piece piece : pieces) {
            if (piece instanceof King) {
                this.king = (King) piece;
                return;
            }
        }
    }

    public ArrayList<Piece> getActivePieces() {
        ArrayList<Piece> activePieces = new ArrayList<Piece>();
        for (Piece piece : pieces) {
            if (!piece.isTaken()) {
                activePieces.add(piece);
            }
        }
        return activePieces;
    }

    public ArrayList<Piece> getTakenPieces() {
        ArrayList<Piece> takenPieces = new ArrayList<Piece>();
        for (Piece piece : pieces) {
            if (piece.isTaken()) {
                takenPieces.add(piece);
            }
        }
        return takenPieces;
    }

    public ArrayList<Piece> getActivePieces(Class<?> pieceClass) {
        ArrayList<Piece> activePieces = new ArrayList<Piece>();
        for (Piece piece : pieces) {
            if (!piece.isTaken() && piece.getClass() == pieceClass) {
                activePieces.add(piece);
            }
        }
        return activePieces;
    }
}
