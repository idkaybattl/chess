package logic;
import logic.*;

public class GameLogic implements ChessGame {
  Player white;
  Player black;

  Player currentPlayer;

  Square[][] board;  
  
  public GameLogic() {
    white = new Player(WHITE);
    black = new Player(BLACK);
    board = new Square[8][8];
    currentPlayer = white;
    
    for (row : board) {
      for (square : row) {
        square = new Square();
      }
    }
    
    // TODO, add Piece setup
  }
  
  public Location[] availableMoves(Piece piece) {
    return piece.availableMoves(board);
  }
  
  public boolean movePiece(Piece piece, Location target) {
    Location[] availableMoves = availableMoves(piece);
    boolean allowedMove = false;
    for (availableMove : availableMoves) {
      if (availableMove == target) {
        allowedMove = true;
        break;
      }
    }
    
    if (allowedMove) {
      board[piece.position.x][piece.position.y].removePiece;
      piece.move(target);
      board[target.x][target.y].setPiece(piece);
    
      currentPlayer = (currentPlayer == white) ? black : white;
      return true;
    } else {
      return false;
    }
  }
  
  public GameStatus getGameStatus() {
    PlayerStatus whiteStatus = white.getStatus();
    PlayerStatus blackStatus = black.getStatus();
    
    // Check for draws:
    // insufficient material
    // 50 moves
    // repetition
  }

  private List<Piece> getPiecesOfPlayer(Color player) {
    List<Piece> pieces = new List<Piece>();
    
    for (row : board) {
      for (square : row) {
        if (square.getPiece.isPresent()) {
          Piece piece = square.get();
          if (piece.getColor == player) {
            pieces.add(piece);
          }
        }
      }
    }

    return pieces;
  }

  private boolean inCheck(Color player) {
    
  }
  
  private PlayerStatus getPlayerStatus(Color player) {
    if (getSpecificPiecesOfPlayer(player, King.class).availableMoves.length == 0) {
      
    } else {
      return ONGOING;
    }
  }
  
  private List<Piece> getSpecificPiecesOfPlayer(Color player, Class<?> pieceClass) {
    List<Piece> pieces = getSpecificPieces(pieceClass);
    List<Piece> piecesOfPlayer = new List<Piece>();
    for (piece : pieces) {
      if (piece.getColor() == player) {
        piecesOfPlayer.add(piece);
      }
    }

    return piecesOfPlayer;
  }
  
  private List<Piece> getSpecificPieces(Class<?> pieceClass) {
    List<Piece> pieces = new List<Piece>();
    for (row : board) {
      for (square : row) {
        if (square.getPiece().isPresent()) {
        Piece piece = square.getPiece().get();
          if (piece.class == pieceClass) {
            pieces.add(piece);
          }
        }
      }
    }
    return pieces;
  }
  
  public Optional<Piece> getPieceAt(Location location) {
    return board[location.x][location.y].getPiece;
  }
  
  public Square[][] getBoard() {
    return board;
  }
  
  public Player getCurrentPlayer() {
    return currentPlayer;
  }
}



    
