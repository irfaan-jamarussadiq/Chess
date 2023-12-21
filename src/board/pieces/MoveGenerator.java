package pieces;

import java.util.List;
import java.util.stream.Collectors;

import board.Move;
import board.BoardModel;

public interface MoveGenerator {
	List<Move> generateMoves(BoardModel board, int rank, int file);	

	default List<Move> generateCaptures(BoardModel board, int rank, int file) {
		Piece piece = board.pieceAt(rank, file);
		return generateMoves(board, rank, file)
			.stream()
			.filter(move -> piece.isEnemyOf(board.pieceAt(move.getEndRank(), move.getEndFile()))) 
			.collect(Collectors.toList());
	}

}
