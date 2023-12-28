package board;

import pieces.Piece;
import pieces.PieceType;

public class LongCastlingMoveStrategy implements MoveStrategy {

	private boolean kingMovedBefore;
	private boolean rookMovedBefore;

	public void move(BoardModel board, Move move) {
		// Move king
		Piece king = board.pieceAt(move.getStartRank(), move.getStartFile());
		this.kingMovedBefore = king.hasMoved();
		board.removePiece(move.getStartRank(), move.getStartFile());
		board.addPiece(king, move.getEndRank(), move.getEndFile());

		// Move rook
		Piece rook = board.pieceAt(move.getStartRank(), move.getStartFile() - 4);
		this.rookMovedBefore = rook.hasMoved();
		board.removePiece(move.getStartRank(), move.getStartFile() - 4);
		board.addPiece(rook, move.getStartRank(), move.getStartFile() - 1);

		king.setHasMoved(true);
		rook.setHasMoved(true);
	}

	public void undoMove(BoardModel board, Move move) {
		// Undo king move
		Piece king = board.pieceAt(move.getEndRank(), move.getEndFile());
		board.removePiece(move.getEndRank(), move.getEndFile());
		board.addPiece(king, move.getStartRank(), move.getStartFile());

		// Undo rook move	
		Piece rook = board.pieceAt(move.getEndRank(), move.getStartFile() - 1);
		board.removePiece(move.getEndRank(), move.getStartFile() - 1);
		board.addPiece(rook, move.getStartRank(), move.getStartFile() - 4);

		king.setHasMoved(kingMovedBefore);
		rook.setHasMoved(rookMovedBefore);
	}

	public boolean isValidMove(BoardModel board, Move move) {
		if (move.getStartFile() != 5) {
			return false;
		}

		Piece expectedKing = board.pieceAt(move.getStartRank(), move.getStartFile());
		Piece expectedRook = board.pieceAt(move.getStartRank(), move.getStartFile() - 4);
		if (expectedKing == null || expectedKing.getType() != PieceType.KING 
			|| expectedRook == null || expectedRook.getType() != PieceType.ROOK) {
			return false;
		}

		if (expectedKing.hasMoved() || expectedRook.hasMoved()) {
			return false;
		}

		int startingRank = expectedKing.getColor().getPieceStartingRank();
		return move.getStartRank() == startingRank && move.getStartFile() == 5 && move.getEndFile() == 3 
			&& board.pieceAt(startingRank, 2) == null && board.pieceAt(startingRank, 3) == null;

	}
		 
}	
