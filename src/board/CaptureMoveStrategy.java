package src.board;

import java.util.List;

import src.board.pieces.Piece;
import src.board.pieces.MoveGenerator;
import src.board.pieces.MoveGeneratorFactory;

public class CaptureMoveStrategy implements MoveStrategy {

	private boolean pieceHasMovedBefore;
	private Piece capturedPiece;

	public void move(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.startRank(), move.startFile());
		Piece pieceAtDestination = board.pieceAt(move.endRank(), move.endFile());
		board.removePiece(move.startRank(), move.startFile());
		board.addPiece(pieceToMove, move.endRank(), move.endFile());

		this.pieceHasMovedBefore = pieceToMove.hasMoved();
		this.capturedPiece = pieceAtDestination;
	}	

	public void undoMove(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.endRank(), move.endFile());
		board.removePiece(move.endRank(), move.endFile());
		board.addPiece(pieceToMove, move.startRank(), move.startFile());
		board.addPiece(capturedPiece, move.endRank(), move.endFile());

		pieceToMove.setHasMoved(pieceHasMovedBefore);	
	}
	
	public boolean isValidMove(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.startRank(), move.startFile());
		Piece pieceAtDestination = board.pieceAt(move.endRank(), move.endFile());
		if (pieceToMove == null || !pieceToMove.isEnemyOf(pieceAtDestination)) {
			return false;
		}
	
		MoveGenerator generator = MoveGeneratorFactory.createMoveGenerator(pieceToMove, board);
		List<Move> captures = generator.getCaptures(move.startRank(), move.startFile());
		return captures.contains(move);
	} 
}	
