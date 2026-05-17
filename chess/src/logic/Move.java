package logic;

import logic.pieces.Piece;
import java.util.Optional;

public class Move {
    private Piece piece;
    private Location start;
    private Location target;
    private Piece capturedPiece;
    private Location capturedLocation;

    public Move(Piece piece, Location start, Location target) {
        this(piece, start, target, null, target);
    }

    public Move(Piece piece, Location start, Location target, Piece capturedPiece, Location capturedLocation) {
        this.piece = piece;
        this.start = new Location(start);
        this.target = new Location(target);
        this.capturedPiece = capturedPiece;
        this.capturedLocation = new Location(capturedLocation);
    }

    public String algebraicNotation() {
        return (start.algebraicNotation() + target.algebraicNotation());
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
