package src.board.pieces;

import java.util.List;
import src.board.Move;

public interface MoveGenerator {
	List<Move> getMoves(int rank, int file);	

	default List<Move> getCaptures(int rank, int file) {
		return getMoves(rank, file);
	}	
}
