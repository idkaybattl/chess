package logic;

import java.util.ArrayList;
import logic.pieces.Piece;

public class Player {
    private Color color;
    private Piece[] pieces;
    private Piece king;

    public Player(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    // only accounts for player specific rules
    // not including things like draw by repetition
    public PlayerStatus getStatus() {
        // placeholder
        return PlayerStatus.ONGOING;
    }

    public boolean targetsEnemyKing() {
        // placeholder
        return false;
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
}
