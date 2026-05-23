package logic;

import logic.pieces.Piece;

public record SimplePiece(Class<? extends Piece> type, ChessColor color) {
}
