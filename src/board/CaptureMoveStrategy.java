package board;

import java.util.List;

import pieces.Piece;
import pieces.MoveGenerator;
import pieces.MoveGeneratorFactory;

public class CaptureMoveStrategy implements MoveStrategy {
	public void move(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.getStartRank(), move.getStartFile());
		Piece pieceAtDestination = board.pieceAt(move.getEndRank(), move.getEndFile());
		board.removePiece(move.getStartRank(), move.getStartFile());
		board.addPiece(pieceToMove, move.getEndRank(), move.getEndFile());

		move.setCapturedPiece(pieceAtDestination);
	}	

	public void undoMove(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.getEndRank(), move.getEndFile());
		board.removePiece(move.getEndRank(), move.getEndFile());
		board.addPiece(pieceToMove, move.getStartRank(), move.getStartFile());
		board.addPiece(move.getCapturedPiece(), move.getEndRank(), move.getEndFile());
	}
	
	public boolean isValidMove(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.getStartRank(), move.getStartFile());
		Piece pieceAtDestination = board.pieceAt(move.getEndRank(), move.getEndFile());
		if (pieceToMove == null || !pieceToMove.isEnemyOf(pieceAtDestination)) {
			return false;
		}
	
		MoveGenerator generator = MoveGeneratorFactory.createMoveGenerator(pieceToMove);
		List<Move> captures = generator.getCaptures(move.getStartRank(), move.getStartFile());
		return captures.contains(move);
	} 
}	
