package pieces;

import java.util.List;
import java.util.Arrays;

import board.Move;

public class PawnMoveGenerator implements MoveGenerator {
	private PieceColor color;

	public PawnMoveGenerator(PieceColor color) {
		this.color = color;
	}

	public List<Move> getMoves(int rank, int file) {	
		Move[] moves = new Move[2];
		int direction = (color == PieceColor.WHITE) ? 1 : -1;
		moves[0] = new Move(rank, file, rank + direction, file);
		moves[1] = new Move(rank, file, rank + 2 * direction, file);	
		return Arrays.asList(moves);
	}

	@Override
	public List<Move> getCaptures(int rank, int file) {	
		Move[] moves = new Move[2];
		int direction = (color == PieceColor.WHITE) ? 1 : -1;
		moves[0] = new Move(rank, file, rank + direction, file + direction);
		moves[1] = new Move(rank, file, rank + direction, file - direction);	
		return Arrays.asList(moves);
	}
}
