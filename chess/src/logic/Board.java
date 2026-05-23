package logic;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import logic.pieces.*;

public class Board {
    private Square[][] squares;
    private final EnumMap<ChessColor, HashMap<Class<? extends Piece>, ArrayList<Piece>>> piecesByColorAndType;

    public Board() {
        squares = new Square[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                squares[x][y] = new Square();
            }
        }

        piecesByColorAndType = new EnumMap<>(ChessColor.class);
        piecesByColorAndType.put(ChessColor.WHITE, setupPieces(ChessColor.WHITE));
        piecesByColorAndType.put(ChessColor.BLACK, setupPieces(ChessColor.BLACK));
    }

    private HashMap<Class<? extends Piece>, ArrayList<Piece>> setupPieces(ChessColor color) {
        int backRank = color == ChessColor.WHITE ? 0 : 7;
        int pawnRank = color == ChessColor.WHITE ? 1 : 6;

        HashMap<Class<? extends Piece>, ArrayList<Piece>> piecesByType = new HashMap<>();

        piecesByType.put(King.class, new ArrayList<Piece>()).add(new King(new Location(4, backRank), color));
        piecesByType.put(Queen.class, new ArrayList<Piece>()).add(new Queen(new Location(3, backRank), color));
        piecesByType.put(Rook.class, new ArrayList<Piece>());
        piecesByType.put(Knight.class, new ArrayList<Piece>());
        piecesByType.put(Bishop.class, new ArrayList<Piece>());
        piecesByType.put(Pawn.class, new ArrayList<Piece>());

        piecesByType.get(Rook.class).add(new Rook(new Location(0, backRank), color));
        piecesByType.get(Rook.class).add(new Rook(new Location(7, backRank), color));
        piecesByType.get(Knight.class).add(new Knight(new Location(1, backRank), color));
        piecesByType.get(Knight.class).add(new Knight(new Location(6, backRank), color));
        piecesByType.get(Bishop.class).add(new Bishop(new Location(2, backRank), color));
        piecesByType.get(Bishop.class).add(new Bishop(new Location(5, backRank), color));

        ArrayList<Piece> pawns = piecesByType.get(Pawn.class);
        for (int i = 0; i < 8; i++) {
            pawns.add(new Pawn(new Location(i, pawnRank), color));
        }

        for (ArrayList<Piece> pieces : piecesByType.values()) {
            for (Piece piece : pieces) {
                getSquare(piece.getPos()).setPiece(piece);
            }
        }

        return piecesByType;
    }

    public King getKing(ChessColor color) {
        return (King) getPieces(color, King.class).get(0);
    }

    public HashSet<Class<? extends Piece>> uniquePieces(ChessColor color) {
        ArrayList<Piece> activePieces = getActivePieces(color);
        HashSet<Class<? extends Piece>> uniquePieces = new HashSet<>();
        for (Piece activePiece : activePieces) {
            uniquePieces.add(activePiece.getClass());
        }

        return uniquePieces;
    }

    public ArrayList<Piece> getPieces(ChessColor color) {
        ArrayList<Piece> pieces = new ArrayList<>();
        for (ArrayList<Piece> piecesOfType : piecesByColorAndType.get(color).values()) {
            pieces.addAll(piecesOfType);
        }
        return pieces;
    }

    public <T extends Piece> ArrayList<T> getPieces(ChessColor color, Class<T> type) {
        ArrayList<T> pieces = new ArrayList<>();

        for (Piece piece : piecesByColorAndType.get(color).get(type)) {
            pieces.add(type.cast(piece));
        }
        return pieces;
    }

    public ArrayList<Piece> getActivePieces(ChessColor color) {
        ArrayList<Piece> pieces = new ArrayList<>();
        for (ArrayList<Piece> piecesOfType : piecesByColorAndType.get(color).values()) {
            for (Piece piece : piecesOfType) {
                if (!piece.isTaken()) {
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }

    public <T extends Piece> ArrayList<T> getActivePieces(ChessColor color, Class<T> type) {
        ArrayList<T> pieces = new ArrayList<>();
        for (Piece piece : piecesByColorAndType.get(color).get(type)) {
            if (!piece.isTaken()) {
                pieces.add(type.cast(piece));
            }
        }
        return pieces;
    }

    public ArrayList<Piece> getTakenPieces(ChessColor color) {
        ArrayList<Piece> pieces = new ArrayList<>();
        for (ArrayList<Piece> piecesOfType : piecesByColorAndType.get(color).values()) {
            for (Piece piece : piecesOfType) {
                if (piece.isTaken()) {
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }

    public <T extends Piece> ArrayList<T> getTakenPieces(ChessColor color, Class<T> type) {
        ArrayList<T> pieces = new ArrayList<>();
        for (Piece piece : piecesByColorAndType.get(color).get(type)) {
            if (piece.isTaken()) {
                pieces.add(type.cast(piece));
            }
        }
        return pieces;
    }

    public Square[][] getSquares() {
        return squares;
    }

    public Optional<Piece> getPiece(Location location) {
        if (!location.inBoard()) {
            return Optional.empty();
        }

        return squares[location.getX()][location.getY()].getPiece();
    }

    public Optional<Piece> getPiece(int x, int y) {
        return getPiece(new Location(x, y));
    }

    private Square getSquare(Location location) {
        return squares[location.getX()][location.getY()];
    }

    public <T extends Piece> ArrayList<T> getSpecificPieces(Class<T> pieceClass) {
        ArrayList<T> pieces = new ArrayList<>();

        for (Square[] row : squares) {
            for (Square square : row) {
                square.getPiece().ifPresent(piece -> {
                    if (pieceClass.isInstance(piece)) {
                        pieces.add(pieceClass.cast(piece));
                    }
                });
            }
        }

        return pieces;
    }

    public void castle(King king, boolean queenside) {
        int backrank = king.getPos().getX();
        Location newKingPos = new Location(((queenside) ? 2 : 6), backrank);
        Location newRookPos = new Location(((queenside) ? 3 : 5), backrank);
        // unsafe: assumes that square contains rook
        Rook rook = (Rook) ((queenside) ? getPiece(0, backrank).get() : getPiece(7, backrank).get());

        getSquare(newKingPos).setPiece(king);
        getSquare(newRookPos).setPiece(rook);
        getSquare(king.getPos()).removePiece();
        getSquare(rook.getPos()).removePiece();
        king.move(newKingPos);
        rook.move(newRookPos);
    }

    public void movePiece(Piece piece, Location target) {
        movePiece(piece, target, target);
    }

    public void movePiece(Piece piece, Location target, Location capturedLocation) {
        if (piece instanceof King && Math.abs(target.getX() - piece.getPos().getX()) == 2) {
            castle((King) piece, (target.getX() == 2));
        } else {
            Optional<Piece> capturedPiece = getPiece(capturedLocation);

            if (capturedPiece.isPresent()) {
                capturedPiece.get().take();
                getSquare(capturedLocation).removePiece();
            }

            Location origin = new Location(piece.getPos());
            getSquare(origin).removePiece();
            piece.move(new Location(target));
            getSquare(target).setPiece(piece);
        }
    }

    public TempMove tempMove(Piece piece, Location target) {
        return tempMove(piece, target, target);
    }

    public TempMove tempMove(Piece piece, Location target, Location capturedLocation) {
        Optional<Piece> capturedPiece = getPiece(capturedLocation);
        Location origin = new Location(piece.getPos());
        TempMove tempMove = new TempMove(capturedPiece, piece, origin, target, capturedLocation);

        if (capturedPiece.isPresent()) {
            capturedPiece.get().take();
            getSquare(capturedLocation).removePiece();
        }

        getSquare(origin).removePiece();
        piece.tempMove(new Location(target));
        getSquare(target).setPiece(piece);

        return tempMove;
    }

    public void undoTempMove(TempMove move) {
        Optional<Piece> capturedPiece = move.getCapturedPiece();

        getSquare(move.getTarget()).removePiece();
        move.getMovingPiece().undoTempMove(move.getOrigin());
        getSquare(move.getOrigin()).setPiece(move.getMovingPiece());

        if (capturedPiece.isPresent()) {
            Piece piece = capturedPiece.get();
            piece.restoreTaken(move.capturedPieceWasTaken());
            getSquare(move.getCapturedLocation()).setPiece(piece);
        }
    }
}
