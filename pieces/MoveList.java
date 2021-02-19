package pieces;

import java.util.ArrayList;
import board.BoardModel;

public class MoveList {
    
    private ArrayList<Move> moveList;

    public MoveList() {
        moveList = new ArrayList<>();
    }

    public void addAllDiagonalMoves(BoardModel model, Piece piece) {
        addLineOfMoves(model, piece, 1, 1);
        addLineOfMoves(model, piece, -1, -1);
        addLineOfMoves(model, piece, 1, -1);
        addLineOfMoves(model, piece, -1, 1);
    }

    public void addAllStraightMoves(BoardModel model, Piece piece) {
        addLineOfMoves(model, piece, 0, 1);
        addLineOfMoves(model, piece, 0, -1);
        addLineOfMoves(model, piece, 1, 0);
        addLineOfMoves(model, piece, -1, 0);
    }

    /*
        Adds a straight line of moves, whether horizontal, vertical,
        or diagonal. The rankDirection and fileDirection variables indicate 
        which direction to take to traverse the line of squares.

        -1 indicates that the rank or file decreases (up/left)
         0 indicates that the rank or file does not change.
         1 indicates that the rank or file increases (down/right)
    */
    private void addLineOfMoves(BoardModel model, Piece piece, 
    int rankDirection, int fileDirection) {

        int nextRank = piece.rank + rankDirection;
        int nextFile = piece.file + fileDirection;
        
        while (model.isWithinBoard(nextRank, nextFile)) {
            Piece nextPiece = model.pieceAt(nextRank, nextFile);
            Player nextPlayer = nextPiece.player;
            if (nextPlayer == piece.player) {
                break;
            }

            addMove(model, piece, nextRank, nextFile);

            if (nextPlayer.isOppositePlayer(piece.player)) {
                break;
            }

            nextRank += rankDirection;
            nextFile += fileDirection;
        }
    }

    /*
        Adds a move given that the square described by rank and file does not
        contain a piece of the same player as piece.
    */
    public void addMove(BoardModel model, Piece piece, int rank, int file) {
        Move move = new Move(rank, file, piece);
        if (model.isWithinBoard(rank, file)) {
            Player playerAtNewSquare = model.pieceAt(rank, file).player;
            if (playerAtNewSquare != piece.player) {
                moveList.add(move);
            }
        }
    }
    public ArrayList<Move> getMoveList() {
        return moveList;
    }
}