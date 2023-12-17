class MoveValidator {
	private Board board;
	private Move move;

	MoveValidator(Board board, Move move) {
		this.board = board;
		this.move = move;
	}

	boolean isNormalMove(Move move) {
		Piece pieceToMove = board.pieceAt(move.getStartRank(), move.getStartFile());
		if (pieceToMove == null) {
			return false;
		}

		Piece pieceAtDestination = board.pieceAt(move.getEndRank(), move.getEndFile());
		return pieceAtDestination != null;
	}
}

