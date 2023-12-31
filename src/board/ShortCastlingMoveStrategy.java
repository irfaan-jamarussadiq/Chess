package src.board;

import src.board.pieces.Piece;
import src.board.pieces.PieceType;

public class ShortCastlingMoveStrategy implements MoveStrategy {

	private boolean kingMovedBefore;
	private boolean rookMovedBefore;

	public void move(BoardModel board, Move move) {
		// Move king
		Piece king = board.pieceAt(move.startRank(), move.startFile());
		this.kingMovedBefore = king.hasMoved();
		board.removePiece(move.startRank(), move.startFile());
		board.addPiece(king, move.endRank(), move.endFile());

		// Move rook
		Piece rook = board.pieceAt(move.startRank(), move.startFile() + 3);
		this.rookMovedBefore = rook.hasMoved();
		board.removePiece(move.startRank(), move.startFile() + 3);
		board.addPiece(rook, move.startRank(), move.startFile() + 1);

		king.setHasMoved(true);
		rook.setHasMoved(true);
	}

	public void undoMove(BoardModel board, Move move) {
		// Undo king move
		Piece king = board.pieceAt(move.endRank(), move.endFile());
		board.removePiece(move.endRank(), move.endFile());
		board.addPiece(king, move.startRank(), move.startFile());

		// Undo rook move	
		Piece rook = board.pieceAt(move.endRank(), move.startFile() + 1);
		board.removePiece(move.endRank(), move.startFile() + 1);
		board.addPiece(rook, move.startRank(), move.startFile() + 3);

		king.setHasMoved(kingMovedBefore);
		rook.setHasMoved(rookMovedBefore);
	}

	public boolean isValidMove(BoardModel board, Move move) {
		if (move.startFile() != 5) {
			return false;
		}

		Piece expectedKing = board.pieceAt(move.startRank(), move.startFile());
		Piece expectedRook = board.pieceAt(move.startRank(), move.startFile() + 3);
		if (expectedKing == null || expectedKing.getType() != PieceType.KING 
			|| expectedRook == null || expectedRook.getType() != PieceType.ROOK) {
			return false;
		}

		if (expectedKing.hasMoved() || expectedRook.hasMoved()) {
			return false;
		}

		int startingRank = expectedKing.getColor().getPieceStartingRank();
		return move.startRank() == startingRank && move.endFile() == 7 && board.pieceAt(startingRank, 6) == null && board.pieceAt(startingRank, 7) == null;
	}
		
}	
