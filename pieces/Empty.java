package pieces;

import java.util.ArrayList;
import board.BoardModel;

public class Empty extends Piece {

    public Empty(int rank, int file, Player player) {
        super(rank, file, player);
    }

    @Override
    public boolean isValidMove(int newRank, int newFile) {
        return false;
    }

    @Override
    public ArrayList<Move> getMoveList(BoardModel model) {
        return new ArrayList<>();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Piece deepCopy() {
        return new Empty(rank, file, player);
    }
}