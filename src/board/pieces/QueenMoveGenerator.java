package pieces;

import java.util.List;
import java.util.ArrayList;

import board.Move;
import board.BoardModel;

public class QueenMoveGenerator implements MoveGenerator {
	private int minRank, maxRank, minFile, maxFile;

	public QueenMoveGenerator(int minRank, int maxRank, int minFile, int maxFile) {
		this.minRank = minRank;
		this.maxRank = maxRank;
		this.minFile = minFile;
		this.maxFile = maxFile;
	}

	public QueenMoveGenerator() {
		this(1, BoardModel.SIZE, 1, BoardModel.SIZE);
	}

	public List<Move> getMoves(int rank, int file) {
		List<Move> moves = new ArrayList<>(4 * Math.max(maxRank - minRank, maxFile - minFile));
		MoveGenerator rookMoveGenerator = new RookMoveGenerator(minRank, maxRank, minFile, maxFile);
		MoveGenerator bishopMoveGenerator = new BishopMoveGenerator(minRank, maxRank, minFile, maxFile);

		moves.addAll(rookMoveGenerator.getMoves(rank, file));
		moves.addAll(bishopMoveGenerator.getMoves(rank, file));
		return moves;
	}
}
