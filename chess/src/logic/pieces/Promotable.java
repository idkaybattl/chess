package logic.pieces;

import logic.Location;
import logic.ChessColor;

public abstract class Promotable extends Piece {
    public Promotable(Location position, ChessColor color) {
        super(position, color);
    }
}
