package logic;

import logic.pieces.*;
import java.util.Optional;

public class TempMove {
    private Optional<Piece> capturedPiece;
    private boolean capturedPieceWasTaken;
    private Piece movingPiece;
    private Location origin;
    private Location target;
    private Location capturedLocation;

    public TempMove(
            Optional<Piece> capturedPiece,

            Piece movingPiece,
            Location origin,
            Location target,
            Location capturedLocation) {
        this.capturedPiece = capturedPiece;
        this.capturedPieceWasTaken = capturedPiece.map(Piece::isTaken).orElse(false);
        this.movingPiece = movingPiece;
        this.origin = new Location(origin);
        this.target = new Location(target);
        this.capturedLocation = new Location(capturedLocation);
    }

    public Optional<Piece> getCapturedPiece() {
        return capturedPiece;
    }

    public boolean capturedPieceWasTaken() {
        return capturedPieceWasTaken;
    }

    public Piece getMovingPiece() {
        return movingPiece;
    }

    public Location getOrigin() {
        return origin;
    }

    public Location getTarget() {
        return target;

    }

    public Location getCapturedLocation() {
        return capturedLocation;
    }
}
