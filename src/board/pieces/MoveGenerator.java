package pieces;

import java.util.List;

import board.Move;

public interface MoveGenerator {
	List<Move> getMoves(int rank, int file);
}
