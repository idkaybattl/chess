package logic;
import logic.*;

interface ChessGame {
  Location[] availableMoves(Piece piece);
  boolean movePiece(Piece piece, int x, int y);
  Gamestatus getGameStatus();
  
  Piece getPieceAt(int x, int y);
  Piece[][] getBoard();
  
  Player getCurrentPlayer();
}   