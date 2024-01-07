package src.pieces;

import java.util.List;
import java.util.ArrayList;

import src.board.Move;
import src.board.BoardModel;

public class BishopMoveGenerator implements MoveGenerator {
	private final BoardModel board;

	public BishopMoveGenerator(BoardModel board) {
		this.board = board;
	}
		
	public List<Move> getMoves(int rank, int file) {
		Piece piece = board.pieceAt(rank, file);
		List<Move> moves = new ArrayList<>(2 * BoardModel.SIZE);
		int[][] directions = { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } }; 
		for (int[] direction : directions) {
			int endRank = rank + direction[0];
			int endFile = file + direction[1];
			while (endRank >= 1 && endRank <= BoardModel.SIZE && endFile >= 1 && endFile <= BoardModel.SIZE) {
				Piece potentialEnemy = board.pieceAt(endRank, endFile);	
				if (potentialEnemy != null && !potentialEnemy.isEnemyOf(piece)) {
					break;
				}

				moves.add(new Move(rank, file, endRank, endFile)); 
				if (potentialEnemy != null && potentialEnemy.isEnemyOf(piece)) {
					break;
				}

				endRank += direction[0];
				endFile += direction[1];
			}
		}

		return moves;
	}
}
