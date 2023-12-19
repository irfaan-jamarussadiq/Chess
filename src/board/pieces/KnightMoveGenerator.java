package pieces;

import java.util.List;
import java.util.ArrayList;

import board.BoardModel;
import board.Move;

public class KnightMoveGenerator implements MoveGenerator {
	public List<Move> getMoves(int rank, int file) {
		List<Move> moves = new ArrayList<>(8);
		int[][] directions = { 
			{ -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 }, 
			{ 1, -2 }, { 1, 2 }, { 2, -1 }, { 2, 1 }
		}; 

		for (int[] direction : directions) {
			int endRank = rank + direction[0];
			int endFile = rank + direction[1];

			if (endRank >= 1 && endRank <= BoardModel.SIZE && endFile >= 1 && endFile <= BoardModel.SIZE) {
				moves.add(new Move(rank, file, endRank, endFile)); 
			}
		}

		return moves;
	}
}
