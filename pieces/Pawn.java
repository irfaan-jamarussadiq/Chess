package pieces;

import java.util.ArrayList;
import board.BoardModel;
import static pieces.Player.*;

public class Pawn extends Piece {
    
    public Pawn(int rank, int file, Player player) {
        super(rank, file, player);
    }

    @Override
    public boolean isValidMove(int newRank, int newFile) {
        boolean canMoveOneSquare = canMoveNSquares(1, newRank, newFile);
        boolean canMoveTwoSquares = canMoveNSquares(2, newRank, newFile);
        boolean canCapture = canCapture(newRank, newFile);

        return canMoveOneSquare || canMoveTwoSquares || canCapture;
    }

    /*
        Checks whether the pawn can move to the new square as described by
        newRank and newFile by moving "n" squares forward.
    */
    private boolean canMoveNSquares(int n, int newRank, int newFile) {
        int diffRank = newRank - rank;
        boolean blackNMove = diffRank == n && player == BLACK;
        boolean whiteNMove = diffRank == -n && player == WHITE;

        if (n == 2) {
            blackNMove = blackNMove && rank == 1;
            whiteNMove = whiteNMove && rank == 6;
        }

        return newFile == file && (blackNMove || whiteNMove);
    }

    private boolean canCapture(int newRank, int newFile) {
        boolean rankRight = newRank == rank + player.getDirection(); 
        boolean fileRight = newFile == file + player.getDirection()
            || newFile == file - player.getDirection();
        return rankRight && fileRight;
    }

    @Override
    public ArrayList<Move> getMoveList(BoardModel model) {
        ArrayList<Move> moveList = new ArrayList<>();

        int direction = player.getDirection();

        if (pawnSquareEmpty(rank + direction, file, model)) {
            moveList.add(new Move(rank + direction, file, this));
            if (pawnSquareEmpty(rank + 2 * direction, file, model)) {
                moveList.add(new Move(rank + 2 * direction, file, this));
            }
        }

        if (pawnSquareIsEnemy(rank + direction, file - direction, model)) {
            moveList.add(new Move(rank + direction, file - direction, this));
        }

        if (pawnSquareIsEnemy(rank + direction, file + direction, model)) {
            moveList.add(new Move(rank + direction, file + direction, this));
        }

        return moveList;
    }

    private boolean pawnSquareEmpty(int rank, int file, BoardModel model) {   
        return model.isWithinBoard(rank, file) && isValidMove(rank, file)
            && model.pieceAt(rank, file).isEmpty();
    }

    private boolean pawnSquareIsEnemy(int rank, int file, BoardModel model) {
        return model.isWithinBoard(rank, file) && isValidMove(rank, file)
            && model.pieceAt(rank, file).getPlayer().isOppositePlayer(player);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Piece deepCopy() {
        return new Pawn(rank, file, player);
    }
}