package board;

import java.util.List;

import pieces.Piece;
import pieces.PieceType;
import pieces.MoveGenerator;
import pieces.MoveGeneratorFactory;
 
public class NormalMoveStrategy implements MoveStrategy {

	private boolean pieceHasMovedBefore;
	
	public void move(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.getStartRank(), move.getStartFile());
		board.removePiece(move.getStartRank(), move.getStartFile());
		board.addPiece(pieceToMove, move.getEndRank(), move.getEndFile());

		this.pieceHasMovedBefore = pieceToMove.hasMoved();
		pieceToMove.setHasMoved(true);
	}

	public void undoMove(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.getEndRank(), move.getEndFile());
		board.removePiece(move.getEndRank(), move.getEndFile());
		board.addPiece(pieceToMove, move.getStartRank(), move.getStartFile());

		pieceToMove.setHasMoved(pieceHasMovedBefore);
	}

	public boolean isValidMove(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.getStartRank(), move.getStartFile());
		Piece pieceAtDestination = board.pieceAt(move.getEndRank(), move.getEndFile());
		if (pieceToMove == null || pieceAtDestination != null) {
			return false;
		}

		MoveGenerator generator = MoveGeneratorFactory.createMoveGenerator(pieceToMove, board);
		List<Move> possibleMoves = generator.getMoves(move.getStartRank(), move.getStartFile());
		return possibleMoves.contains(move);
	}
}	
