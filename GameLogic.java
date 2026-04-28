import java.util.Optional;
import Types.*;
public class GameLogic implements ChessGame {
  Square[][] board;
  Player currentPlayer;
  
  public GameLogic() {
    board = new Square[8][8];
    currentPlayer = WHITE;
    
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
    
      currentPlayer = (currentPlayer == WHITE) ? BLACK : WHITE;
      return true;
    } else {
      return false;
    }
  }
  
  public GameStatus getGameStatus() {
    PlayerStatus whiteStatus = getGameStatus(WHITE);
    PlayerStatus blackStatus = getGameStatus(BLACK);
    
    // Check for draws:
    // insufficient material
    // 50 moves
    // repetition
  }
  
  PlayerStatus getPlayerStatus(Color player) {
    // if (getSpecificPiecesOfPlayer(player, King.class))
  }
  
  List<Piece> getSpecificPiecesOfPlayer(Color player, Class<?> pieceClass) {
    List<Piece> pieces = getSpecificPieces(pieceClass);
    List<Piece> piecesOfPlayer = new List<Piece>();
    for (piece : pieces) {
      if (piece.getColor() == player) {
        piecesOfPlayer.add(piece);
      }
    }

    return piecesOfPlayer;
  }
  
  List<Piece> getSpecificPieces(Class<?> pieceClass) {
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

public class Square {
  Piece piece;
  
  public Square() {
  }
  
  public void setPiece(Piece piece) {
    this.piece = piece;
  }
  
  public void removePiece() {
    this.piece = null;
  }
  
  public Optional<Piece> getPiece() {
    if (piece == null) {
      return Optional.empty();
    } else {
      return Optional.of(this.piece);
    }
  }
}
  
public class Piece {
  Location position;
  Color color;
  
  public Piece(Location position, Color, color) {
    this.position = position;
    this.color = color;
  }

  public Location getPos() {
    return Location;
  }

  public Color getColor() {
    return color;
  }
  
  public Location[] getValidMoves(Square[][] board) {
    
  }
  
  public void move(Location target) {
    this.position = target;
  }
}
  
public class Location {
  int x;
  int y;
  public Location(int x, int y) {
    self.x = x;
    self.y = y;
  }
}

    
