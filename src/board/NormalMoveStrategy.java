package src.board;

import java.util.List;

import src.pieces.Piece;
import src.pieces.MoveGenerator;
import src.pieces.MoveGeneratorFactory;
import src.pieces.PieceType;

public class NormalMoveStrategy implements MoveStrategy {

	private final BoardModel board;
	private boolean pieceHasMovedBefore;

	public NormalMoveStrategy(BoardModel board) {
		this.board = board;
	}
	
	public void move(Move move) {
		Piece pieceToMove = board.pieceAt(move.startRank(), move.startFile());
		board.removePiece(move.startRank(), move.startFile());
		board.addPiece(pieceToMove, move.endRank(), move.endFile());

		this.pieceHasMovedBefore = pieceToMove.hasMoved();
		pieceToMove.setHasMoved(true);
	}

	public void undoMove(Move move) {
		Piece pieceToMove = board.pieceAt(move.endRank(), move.endFile());
		board.removePiece(move.endRank(), move.endFile());
		board.addPiece(pieceToMove, move.startRank(), move.startFile());

		pieceToMove.setHasMoved(pieceHasMovedBefore);
	}

	public boolean isValidMove(Move move) {
		Piece pieceToMove = board.pieceAt(move.startRank(), move.startFile());
		Piece pieceAtDestination = board.pieceAt(move.endRank(), move.endFile());
		if (pieceToMove == null || pieceAtDestination != null) {
			return false;
		}

		if (pieceToMove.getType() == PieceType.KING && Math.abs(move.endFile() - move.startFile()) > 1) {
			return false;
		}

		MoveGenerator generator = MoveGeneratorFactory.createMoveGenerator(pieceToMove, board);
		List<Move> possibleMoves = generator.getMoves(move.startRank(), move.startFile());
		return possibleMoves.contains(move);
	}
}	
