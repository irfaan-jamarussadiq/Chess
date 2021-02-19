package pieces;

import java.util.ArrayList;
import board.BoardModel;

public class Queen extends Piece {

    public Queen(int rank, int file, Player player) {
        super(rank, file, player);
    }

    @Override
    public boolean isValidMove(int newRank, int newFile) {
        int diffRank = Math.abs(newRank - rank);
        int diffFile = Math.abs(newFile - file);
        boolean isBishopMove = diffRank == diffFile && diffRank != 0;
        boolean isRookMove = (diffRank == 0) ^ (diffFile == 0);
        return isBishopMove || isRookMove;
    }

    /*
        A queen moves like both a bishop and a rook, so its move list
        is all of its bishop moves plus all of its rook moves.
    */
    @Override
    public ArrayList<Move> getMoveList(BoardModel model) {
        MoveList moveList = new MoveList();
        moveList.addAllDiagonalMoves(model, this);
        moveList.addAllStraightMoves(model, this);
        return moveList.getMoveList();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Piece deepCopy() {
        return new Queen(rank, file, player);
    }
}