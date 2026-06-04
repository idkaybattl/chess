package logic.engine;

import logic.ChessGame;

public interface EngineOpponent {
    MoveChoice requestMove(ChessGame logic);
}
