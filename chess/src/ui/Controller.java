package ui;

import java.util.List;
import java.util.Optional;

import logic.*;
import logic.pieces.Pawn;
import logic.pieces.Piece;
import logic.pieces.Promotable;

public class Controller {
    private ChessGame logic;
    private ScreenManager screenManager;

    public Controller() {

    }

    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    public void startNewGame() {
        logic = new GameLogic();
        screenManager.getGamePanel().startNewGame();
    }

    public void gameOver() {
        GameStatus gameStatus = getGameStatus();
        screenManager.getGamePanel().gameOver(gameStatus);
    }

    public Optional<Piece> getPieceAt(Location pos) {
        return logic.getPieceAt(pos);
    }

    public MoveResult movePiece(Piece piece, Location target) {
        return logic.movePiece(piece, target);
    }

    public boolean inCheck(ChessColor player) {
        return logic.inCheck(player);
    }

    public GameStatus getGameStatus() {
        return logic.getGameStatus();
    }

    public ChessColor getCurrentPlayer() {
        return logic.getCurrentPlayer();
    }

    public List<Location> availableMoves(Piece piece) {
        // TODO conversion
        return logic.availableMoves(piece);
    }

    public void startPromotion(Pawn pawn, Location origin, Location target) {
        screenManager.getGamePanel().startPromotion(pawn, origin, target);
    }

    public void promote(Pawn pawn, Location origin, Location target, Class<? extends Promotable> type) {
        logic.promote(pawn, origin, target, type);
        screenManager.getGamePanel().closePromotion();
    }
}
