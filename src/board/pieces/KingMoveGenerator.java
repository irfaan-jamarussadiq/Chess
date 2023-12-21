package pieces;

import java.util.List;
import java.util.ArrayList;

import board.BoardModel;
import board.Move;
import board.Player;
import pieces.Piece;

public class KingMoveGenerator implements MoveGenerator {
	public List<Move> generateMoves(BoardModel board, int rank, int file) {	
		List<Move> moves = new ArrayList<>(8);
		Piece king = board.pieceAt(rank, file);
		if (king == null || king.getType() != PieceType.KING) {
			throw new IllegalArgumentException();
		}

		Player player = new Player(king.getColor());
		int[][] directions = { {-1, -1} , { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } }; 
		for (int[] direction : directions) {
			int endRank = rank + direction[0];
			int endFile = file + direction[1];
			if (endRank >= 1 && endRank <= BoardModel.SIZE && endFile >= 1 && endFile <= BoardModel.SIZE) {
				moves.add(new Move(rank, file, endRank, endFile));
			}
		}

		return moves;
	}

	public List<Move> getCastlingMoves(int rank, int file) {
		throw new UnsupportedOperationException();
	}

}
