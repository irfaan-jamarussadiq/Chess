package pieces;

import java.util.List;
import java.util.stream.Collectors;

import board.Move;
import board.BoardModel;

public interface MoveGenerator {
	List<Move> getMoves(int rank, int file);	

	default List<Move> getCaptures(int rank, int file) {
		return getMoves(rank, file);
	}	
}
