package pieces;

import java.util.List;
import java.util.ArrayList;

import board.BoardModel;
import board.Move;
import pieces.Piece;

public class PawnMoveGenerator implements MoveGenerator {
	public List<Move> generateMoves(BoardModel board, int rank, int file) {	
		List<Move> moves = new ArrayList<>();
		Piece pawn = board.pieceAt(rank, file);
		if (pawn == null || pawn.getType() != PieceType.PAWN) {
			throw new IllegalArgumentException();
		}

		int direction = (pawn.getColor() == PieceColor.WHITE) ? 1 : -1;
		int startRank = (pawn.getColor() == PieceColor.WHITE) ? 2 : 7;

		// Check for one square move
		if (board.pieceAt(rank + direction, file) == null) {
			moves.add(new Move(rank, file, rank + direction, file));
		}	

		// Check for two square move on first pawn move
		if (rank == startRank && board.pieceAt(rank + 2 * direction, file) == null) {
			moves.add(new Move(rank, file, rank + 2 * direction, file));
		}

		// Check for captures
		if (rank + direction <= BoardModel.SIZE && file + direction <= BoardModel.SIZE && file + direction >= 1) {
			Piece potentialEnemy = board.pieceAt(rank + direction, file + direction);
			if (pawn.isEnemyOf(potentialEnemy)) {
				moves.add(new Move(rank, file, rank + direction, file + direction));
			}
		}

		if (rank + direction <= BoardModel.SIZE && file - direction <= BoardModel.SIZE && file - direction >= 1) {
			Piece potentialEnemy = board.pieceAt(rank + direction, file - direction);
			if (pawn.isEnemyOf(potentialEnemy)) {
				moves.add(new Move(rank, file, rank + direction, file - direction));
			}
		}
		
		return moves;
	}

}
