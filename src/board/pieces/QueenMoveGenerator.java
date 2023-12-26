package pieces;

import java.util.List;
import java.util.ArrayList;

import board.Move;
import board.BoardModel;

public class QueenMoveGenerator implements MoveGenerator {

	private BoardModel board;
	
	public QueenMoveGenerator(BoardModel board) {
		this.board = board;
	}

	public List<Move> getMoves(int rank, int file) {
		List<Move> moves = new ArrayList<>(4 * BoardModel.SIZE);
		MoveGenerator rookMoveGenerator = new RookMoveGenerator(board);
		MoveGenerator bishopMoveGenerator = new BishopMoveGenerator(board);

		moves.addAll(rookMoveGenerator.getMoves(rank, file));
		moves.addAll(bishopMoveGenerator.getMoves(rank, file));
		return moves;
	}

}
