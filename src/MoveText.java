package src;

import src.board.Move;
import src.pieces.Piece;

public class MoveText {
    private final Piece piece;
    private final Move move;
    public MoveText(Piece piece, Move move) {
        this.piece = piece;
        this.move = move;
    }

    @Override
    public String toString() {
        return piece.getBoardCharacter() + move.toString();
    }
}
