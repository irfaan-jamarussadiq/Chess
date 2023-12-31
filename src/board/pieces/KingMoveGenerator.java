package src.board.pieces;

import java.util.List;
import java.util.Arrays;

import src.board.Move;

public class KingMoveGenerator implements MoveGenerator {
	public List<Move> getMoves(int rank, int file) {	
		Move[] moves = new Move[8]; 
		int[][] directions = { {-1, -1} , { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } }; 
		for (int i = 0; i < directions.length; i++) {
			int endRank = rank + directions[i][0];
			int endFile = file + directions[i][1];
			moves[i] = new Move(rank, file, endRank, endFile);
		}

		return Arrays.asList(moves);
	}
}
