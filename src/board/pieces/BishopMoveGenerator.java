package pieces;

import java.util.List;
import java.util.ArrayList;

import board.Move;
import board.BoardModel;

public class BishopMoveGenerator implements MoveGenerator {
	private int minRank, maxRank, minFile, maxFile;

	public BishopMoveGenerator(int minRank, int maxRank, int minFile, int maxFile) {
		this.minRank = minRank;
		this.maxRank = maxRank;
		this.minFile = minFile;
		this.maxFile = maxFile;
	}
		
	public BishopMoveGenerator() {
		this(1, BoardModel.SIZE, 1, BoardModel.SIZE);
	}

	public List<Move> getMoves(int rank, int file) {
		List<Move> moves = new ArrayList<>(2 * Math.max(maxRank - minRank, maxFile - minFile));
		int[][] directions = { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } }; 
		for (int[] direction : directions) {
			int endRank = rank + direction[0];
			int endFile = file + direction[1];
			while (endRank >= minRank && endRank <= maxRank && endFile >= minFile && endFile <= maxFile) {
				moves.add(new Move(rank, file, endRank, endFile)); 
				endRank += direction[0];
				endFile += direction[1];
			}
		}

		return moves;
	}
}
