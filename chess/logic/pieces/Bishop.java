public class Bishop extends Piece {
    public  Location[] getValidMoves(Square[][] board) {
        ArrayList<Location> moves = new ArrayList<Location>();
            ArrayList<Square> posSquares =  rayCast( 1, 1, board);
            // check if last piece is same color
            // if then remove
            posSquares.add(rayCast( 1, -1, board));

            posSquares.add(rayCast( -1, 1, board));

            posSquares.add(rayCast( -1, -1, board));

        return moves;
    }       

}