package src.board;

import java.util.List;

import src.board.pieces.Piece;
import src.board.pieces.MoveGenerator;
import src.board.pieces.MoveGeneratorFactory;
 
public class NormalMoveStrategy implements MoveStrategy {

	private boolean pieceHasMovedBefore;
	
	public void move(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.startRank(), move.startFile());
		board.removePiece(move.startRank(), move.startFile());
		board.addPiece(pieceToMove, move.endRank(), move.endFile());

		this.pieceHasMovedBefore = pieceToMove.hasMoved();
		pieceToMove.setHasMoved(true);
	}

	public void undoMove(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.endRank(), move.endFile());
		board.removePiece(move.endRank(), move.endFile());
		board.addPiece(pieceToMove, move.startRank(), move.startFile());

		pieceToMove.setHasMoved(pieceHasMovedBefore);
	}

	public boolean isValidMove(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.startRank(), move.startFile());
		Piece pieceAtDestination = board.pieceAt(move.endRank(), move.endFile());
		if (pieceToMove == null || pieceAtDestination != null) {
			return false;
		}

		MoveGenerator generator = MoveGeneratorFactory.createMoveGenerator(pieceToMove, board);
		List<Move> possibleMoves = generator.getMoves(move.startRank(), move.startFile());
		return possibleMoves.contains(move);
	}
}	
