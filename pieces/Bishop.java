package pieces;

import java.util.ArrayList;
import board.BoardModel;

public class Bishop extends Piece {
    
    public Bishop(int rank, int file, Player player) {
        super(rank, file, player);
    }

    @Override
    public boolean isValidMove(int newRank, int newFile) {
        int diffRank = Math.abs(newRank - rank);
        int diffFile = Math.abs(newFile - file);
        return diffRank == diffFile && diffRank != 0;
    }

    @Override
    public ArrayList<Move> getMoveList(BoardModel model) {
        MoveList moveList = new MoveList();
        moveList.addAllDiagonalMoves(model, this);
        return moveList.getMoveList();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Piece deepCopy() {
        return new Bishop(rank, file, player);
    }
}