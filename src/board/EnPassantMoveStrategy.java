package board;

import pieces.Piece;
import pieces.PieceType;

public class EnPassantMoveStrategy implements MoveStrategy {

	private boolean pawnMovedBefore;
	private Piece capturedPawn;

	public void move(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.getStartRank(), move.getStartFile());
		Piece pieceAtDestination = board.pieceAt(move.getEndRank(), move.getEndFile());
		this.pawnMovedBefore = pieceToMove.hasMoved();
		this.capturedPawn = pieceAtDestination;

		// Move pawn
		board.removePiece(move.getStartRank(), move.getStartFile());
		board.addPiece(pieceToMove, move.getEndRank(), move.getEndFile());

		// Capture enemy pawn
		board.removePiece(move.getStartRank(), move.getEndFile());
	}

	public void undoMove(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.getEndRank(), move.getEndFile());
		board.removePiece(move.getEndRank(), move.getEndFile());
		board.addPiece(pieceToMove, move.getStartRank(), move.getStartFile());
		board.addPiece(capturedPawn, move.getStartRank(), move.getEndFile());

		pieceToMove.setHasMoved(pawnMovedBefore);	
	}	

	public boolean isValidMove(BoardModel board, Move move) {
		Piece piece = board.pieceAt(move.getStartRank(), move.getStartFile());
		if (piece == null || piece.getType() == PieceType.PAWN) {
			return false;
		}

		Piece capturedPawn = board.pieceAt(move.getStartRank(), move.getEndFile());
		return move.getStartRank() == piece.getColor().getEnPassantStartingRank()		
			&& capturedPawn != null && capturedPawn.getType() == PieceType.PAWN 
			&& move.getStartRank() + piece.getColor().getPawnDirection() == move.getEndRank()
			&& Math.abs(move.getEndFile() - move.getStartFile()) == 1; 
	}
}	
