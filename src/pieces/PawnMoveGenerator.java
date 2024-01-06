package src.pieces;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import src.board.Move;

public class PawnMoveGenerator implements MoveGenerator {
	private final PieceColor color;
	private final boolean pawnHasMoved;

	public PawnMoveGenerator(PieceColor color, boolean pawnHasMoved) {
		this.color = color;
		this.pawnHasMoved = pawnHasMoved;
	}

	public List<Move> getMoves(int rank, int file) {	
		List<Move> moves = new ArrayList<>(2);
		int direction = (color == PieceColor.WHITE) ? 1 : -1;
		moves.add(new Move(rank, file, rank + direction, file));
		if (!pawnHasMoved) {
			moves.add(new Move(rank, file, rank + 2 * direction, file));
		}
		return moves;
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
