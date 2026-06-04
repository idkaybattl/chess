package logic;

import logic.pieces.*;

import java.util.Optional;

public class Move {
    private Piece piece;
    private Location start;
    private Location target;
    private Piece capturedPiece;
    private Location capturedLocation;
    private Class<? extends Promotable> promotion;

    public Move(Piece piece, Location start, Location target) {
        this(piece, start, target, null, target, null);
    }

    public Move(Piece piece, Location start, Location target, Piece capturedPiece, Location capturedLocation,
            Class<? extends Promotable> promotion) {
        this.piece = piece;
        this.start = new Location(start);
        this.target = new Location(target);
        this.capturedPiece = capturedPiece;
        this.capturedLocation = new Location(capturedLocation);
        this.promotion = promotion;
    }

    private String promotionString() {
        if (promotion != null) {
            if (promotion == Queen.class) {
                return "q";
            } else if (promotion == Rook.class) {
                return "r";
            } else if (promotion == Knight.class) {
                return "n";
            } else {
                return "b";
            }
        } else {
            return "";
        }
    }

    public String uciNotation() {
        return (start.uciNotation() + target.uciNotation() + promotionString());
    }

    public Location getStart() {
        return start;
    }

    public Location getTarget() {
        return target;
    }

    public Piece getPiece() {
        return piece;
    }

    public Optional<Piece> getCapturedPiece() {
        return Optional.ofNullable(capturedPiece);
    }

    public Location getCapturedLocation() {
        return capturedLocation;
    }
}
