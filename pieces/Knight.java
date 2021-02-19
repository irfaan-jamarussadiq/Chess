package pieces;

import java.util.ArrayList;
import board.BoardModel;

public class Knight extends Piece {
    
    public Knight(int rank, int file, Player player) {
        super(rank, file, player);
    }

    @Override
    public boolean isValidMove(int newRank, int newFile) {
        int diffRank = Math.abs(newRank - rank);
        int diffFile = Math.abs(newFile - file);

        return (diffRank == 2 && diffFile == 1) 
            || (diffRank == 1 && diffFile == 2);
    }

    @Override
    public ArrayList<Move> getMoveList(BoardModel model) {
        MoveList moveList = new MoveList();
        moveList.addMove(model, this, rank - 2, file - 1);
        moveList.addMove(model, this, rank - 2, file + 1);
        moveList.addMove(model, this, rank - 1, file - 2);
        moveList.addMove(model, this, rank - 1, file + 2);
        moveList.addMove(model, this, rank + 1, file - 2);
        moveList.addMove(model, this, rank + 1, file + 2);     
        moveList.addMove(model, this, rank + 2, file - 1);
        moveList.addMove(model, this, rank + 2, file + 1);      
        return moveList.getMoveList();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Piece deepCopy() {
        return new Knight(rank, file, player);
    }
}