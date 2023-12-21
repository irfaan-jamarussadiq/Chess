package pieces;

import java.util.List;
import java.util.ArrayList;

import board.BoardModel;
import board.Move;

public class QueenMoveGenerator implements MoveGenerator {
	public List<Move> generateMoves(BoardModel board, int rank, int file) {
		List<Move> moves = new ArrayList<>(16);
		moves.addAll(new RookMoveGenerator().generateMoves(board, rank, file));
		moves.addAll(new BishopMoveGenerator().generateMoves(board, rank, file));
		return moves;
	}
}
