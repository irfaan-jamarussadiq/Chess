package move;

class NormalMove extends Move {
	boolean isValid(Board board) {
		Piece pieceToMove = board.pieceAt(startRank, startFile); 
		if (pieceToMove == null) {
			return false;
		}

		Piece destination = board.pieceAt(endRank, endFile);
		return destination == null;
	}

	void execute(Board board) {
		if (isValid(board)) {
			Piece pieceToMove = board.pieceAt(startRank, startFile);
			board.removePiece(startRank, startFile); 
			board.addPiece(piece, endRank, endFile);
		}
	}
}
