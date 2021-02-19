package pieces;

import java.util.ArrayList;
import board.BoardModel;

public class Rook extends Piece {
    private boolean rookHasMoved;

    public Rook(int rank, int file, Player player) {
        super(rank, file, player);
        rookHasMoved = false;
    }

    public void setRookHasMoved() {
        rookHasMoved = true;
    }

    public boolean rookHasMoved() {
        return rookHasMoved;
    }

    @Override
    public boolean isValidMove(int newRank, int newFile) {
        return (newRank == rank) ^ (newFile == file);
    }

    @Override
    public ArrayList<Move> getMoveList(BoardModel model) {
        MoveList moveList = new MoveList();
        moveList.addAllStraightMoves(model, this);
        return moveList.getMoveList();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Piece deepCopy() {
        return new Rook(rank, file, player);
    }
}