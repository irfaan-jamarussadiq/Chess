package board;

import java.util.List;

import pieces.Piece;
import pieces.MoveGenerator;
import pieces.MoveGeneratorFactory;

public class CaptureMoveStrategy implements MoveStrategy {

	private boolean pieceHasMovedBefore;
	private Piece capturedPiece;

	public void move(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.getStartRank(), move.getStartFile());
		Piece pieceAtDestination = board.pieceAt(move.getEndRank(), move.getEndFile());
		board.removePiece(move.getStartRank(), move.getStartFile());
		board.addPiece(pieceToMove, move.getEndRank(), move.getEndFile());

		this.pieceHasMovedBefore = pieceToMove.hasMoved();
		this.capturedPiece = pieceAtDestination;
	}	

	public void undoMove(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.getEndRank(), move.getEndFile());
		board.removePiece(move.getEndRank(), move.getEndFile());
		board.addPiece(pieceToMove, move.getStartRank(), move.getStartFile());
		board.addPiece(capturedPiece, move.getEndRank(), move.getEndFile());

		pieceToMove.setHasMoved(pieceHasMovedBefore);	
	}
	
	public boolean isValidMove(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.getStartRank(), move.getStartFile());
		Piece pieceAtDestination = board.pieceAt(move.getEndRank(), move.getEndFile());
		if (pieceToMove == null || !pieceToMove.isEnemyOf(pieceAtDestination)) {
			return false;
		}
	
		MoveGenerator generator = MoveGeneratorFactory.createMoveGenerator(pieceToMove, board);
		List<Move> captures = generator.getCaptures(move.getStartRank(), move.getStartFile());
		return captures.contains(move);
	} 
}	
