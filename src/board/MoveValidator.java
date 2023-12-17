package board;

import pieces.Piece;

class MoveValidator {
	private BoardModel board;

	MoveValidator(BoardModel board) {
		this.board = board;
	}

	boolean isNormalMove(Move move) {
		Piece pieceToMove = board.pieceAt(move.getStartRank(), move.getStartFile());
		if (pieceToMove == null) {
			return false;
		}

		Piece pieceAtDestination = board.pieceAt(move.getEndRank(), move.getEndFile());
		return pieceAtDestination == null;
	}

	boolean isCaptureMove(Move move) {
		Piece pieceToMove = board.pieceAt(move.getStartRank(), move.getStartFile());
		if (pieceToMove == null) {
			return false;
		}

		Piece pieceAtDestination = board.pieceAt(move.getEndRank(), move.getEndFile());
		return pieceAtDestination != null && pieceToMove.isEnemyOf(pieceAtDestination);
	}
}

