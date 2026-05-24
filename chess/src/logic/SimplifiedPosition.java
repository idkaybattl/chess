package logic;

public record SimplifiedPosition(
        SimplePiece[][] board,
        ChessColor turn,
        boolean whiteKSideCastle,
        boolean whiteQSideCastle,
        boolean blackKSideCastle,
        boolean blackQSideCastle,
        Location enPassantTarget) {
}
