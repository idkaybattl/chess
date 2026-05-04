package logic;
import java.util.Optional;
import java.util.List;

public enum GameStatus {
  ONGOING,
  DRAW,
  WHITE,
  BLACK,
}

public enum Color {
  BLACK,
  WHITE,
}

public enum PlayerStatus {
  ONGOING,
  WIN,
  DRAW,
}

public class Player {
  private Color color;
  private Piece[] pieces;
  private Piece king;

  public Player(Color color) {
    this.color = color;
  }

  public Color getColor() {
    return color;
  }

  public PlayerStatus getPlayerStatus() {
    
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
    // filler

    Location[] moves = new Location[0];
    return moves;
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
