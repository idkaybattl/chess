package logic.pieces;

import logic.*;
import java.util.ArrayList;

public abstract class Piece {
    private Location position;
    private Color color;
    private boolean taken;

    public Piece(Location position, Color color) {
        taken = true;
        this.position = position;
        this.color = color;
    }

    public Location getPos() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public boolean isTaken() {
        return taken;
    }

    // only pseudo legal moves, filter moves that lead to check in GameLogic out
    public abstract ArrayList<Location> getValidMoves(Square[][] board);

    // pawns attack the diagonal squares even if theres no piece there
    // => attacked squares not always in list of valid moves
    // matters for checking for checks
    public abstract ArrayList<Location> getAttackedSquares(Square[][] board) {
        return getValidMoves;
    }

    public void move(Location target) {
        this.position = target;
    }

    public void take() {
        taken = true;
    }

    public ArrayList<Square> rayCast(int x, int y, Square[][] board) {
        int newX = this.getPos().x + x;
        int newY = this.getPos().y + y;

        ArrayList<Square> squares = new ArrayList<Square>();

        while (!board[newX][newY].getPiece().isPresent() && x < 8 && y < 8) {
            squares.add(board[newX][newY]);

            newX += x;
            newY += y;
        }

        return squares;
    }

    public boolean filter(Square square){
        if(square.getPiece().isPresent()) {
            if(square.getPiece().getColor() == color) {
                return false;
            }
        }
        return true;
    }
    
    public ArrayList<Square> filteredRayCast(int x, int y, Square[][] board) {
        ArrayList<Square> squares = rayCast( x, y, board);
        
        if(squares[squares.size() - 1].getPiece().isPresent()) {
            if(squares[squares.size() - 1].getPiece().getColor() == color) {
                squares.remove(squares.size() - 1);
            }
        }

        return squares;
        
    }

}
