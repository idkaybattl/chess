package ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.SwingWorker;

import logic.*;
import logic.pieces.Pawn;
import logic.pieces.Piece;
import logic.pieces.Promotable;
import logic.pieces.Queen;
import logic.engine.*;

public class Controller {
    private ChessGame logic;
    private ScreenManager screenManager;
    private EngineOpponent engine;

    public Controller() {
        // TODO temporary to test stockfish
        engine = new StockfishUCI(Controller.class.getResource("/stockfish").getPath(), 4000, 400);
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
        MoveResult moveResult = logic.movePiece(piece, target);
        if (moveResult == MoveResult.MOVED && getGameStatus() == GameStatus.ONGOING
                && getCurrentPlayer() == ChessColor.BLACK) {
            requestStockfishMove();
        }
        return moveResult;
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
        // temporary
        if (piece.getColor() == ChessColor.WHITE) {
            return logic.availableMoves(piece);
        } else {
            return new ArrayList<>();
        }
    }

    public void startPromotion(Pawn pawn, Location origin, Location target) {
        screenManager.getGamePanel().startPromotion(pawn, origin, target);
    }

    public void promote(Pawn pawn, Location origin, Location target, Class<? extends Promotable> type) {
        logic.promote(pawn, origin, target, type);
        screenManager.getGamePanel().closePromotion();
        if (getGameStatus() == GameStatus.ONGOING && getCurrentPlayer() == ChessColor.BLACK) {
            requestStockfishMove();
        }
    }

    private void requestStockfishMove() {
        new SwingWorker<MoveChoice, Void>() {
            @Override
            protected MoveChoice doInBackground() {
                return engine.requestMove(logic);
            }

            @Override
            protected void done() {
                try {
                    MoveChoice move = get();
                    engineResponse(move);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.execute();
    }

    private void engineResponse(MoveChoice move) {
        Piece piece = logic.getPieceAt(move.getStart()).orElseThrow();
        MoveResult moveResult = logic.movePiece(piece, move.getTarget());

        if (moveResult == MoveResult.PROMOTION && piece instanceof Pawn) {
            Class<? extends Promotable> promotion = move.getPromotion() != null ? move.getPromotion() : Queen.class;
            logic.promote((Pawn) piece, move.getStart(), move.getTarget(), promotion);
        }

        screenManager.getGamePanel().refresh();

        if (getGameStatus() != GameStatus.ONGOING) {
            gameOver();
        }
    }
}
