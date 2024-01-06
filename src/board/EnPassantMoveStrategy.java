package src.board;

import src.pieces.Piece;
import src.pieces.PieceType;

public class EnPassantMoveStrategy implements MoveStrategy {

	private final BoardModel board;
	private boolean pawnMovedBefore;
	private Piece capturedPawn;

	public EnPassantMoveStrategy(BoardModel board) {
		this.board = board;
	}

	public void move(Move move) {
		Piece pieceToMove = board.pieceAt(move.startRank(), move.startFile());
		Piece pieceAtDestination = board.pieceAt(move.endRank(), move.endFile());
		this.pawnMovedBefore = pieceToMove.hasMoved();
		this.capturedPawn = pieceAtDestination;

		// Move pawn
		board.removePiece(move.startRank(), move.startFile());
		board.addPiece(pieceToMove, move.endRank(), move.endFile());

		// Capture enemy pawn
		board.removePiece(move.startRank(), move.endFile());
	}

	public void undoMove(Move move) {
		Piece pieceToMove = board.pieceAt(move.endRank(), move.endFile());
		board.removePiece(move.endRank(), move.endFile());
		board.addPiece(pieceToMove, move.startRank(), move.startFile());
		board.addPiece(capturedPawn, move.startRank(), move.endFile());

		pieceToMove.setHasMoved(pawnMovedBefore);	
	}	

	public boolean isValidMove(Move move) {
		Piece piece = board.pieceAt(move.startRank(), move.startFile());
		if (piece == null || piece.getType() == PieceType.PAWN) {
			return false;
		}

		Piece capturedPawn = board.pieceAt(move.startRank(), move.endFile());
		return move.startRank() == piece.getColor().getEnPassantStartingRank()		
			&& capturedPawn != null && capturedPawn.getType() == PieceType.PAWN 
			&& move.startRank() + piece.getColor().getPawnDirection() == move.endRank()
			&& Math.abs(move.endFile() - move.startFile()) == 1; 
	}
}	
