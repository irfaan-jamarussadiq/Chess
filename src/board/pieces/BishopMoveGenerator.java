package pieces;

import java.util.List;
import java.util.ArrayList;

import board.BoardModel;
import board.Move;

public class BishopMoveGenerator implements MoveGenerator {
	public List<Move> generateMoves(BoardModel board, int rank, int file) {
		List<Move> moves = new ArrayList<>(16);
		
		Piece currentPiece = board.pieceAt(rank, file);
		int[][] directions = { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } }; 
		for (int[] direction : directions) {
			int endRank = rank + direction[0];
			int endFile = file + direction[1];

			while (endRank >= 1 && endRank <= BoardModel.SIZE && endFile >= 1 && endFile <= BoardModel.SIZE) {
				Piece potentialEnemy = board.pieceAt(endRank, endFile);
				if (potentialEnemy != null && !potentialEnemy.isEnemyOf(currentPiece)) {
					break;
				}

				moves.add(new Move(rank, file, endRank, endFile)); 
				if (potentialEnemy != null && potentialEnemy.isEnemyOf(currentPiece)) {
					break;
				}

				endRank += direction[0];
				endFile += direction[1];
			}
		}

		return moves;
	}
}
