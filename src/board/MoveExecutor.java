class MoveExecutor {
	private Board board;
	private Move move;

	MoveExecutor(Board board, Move move) {
		this.board = board;
		this.move = move;
	}

	void makeNormalMove(Move move) {
		Piece piece = board.pieceAt(move.getStartRank(), move.getStartFile());
		board.removePiece(move.getStartRank(), move.getStartFile());
		board.addPiece(move.getEndRank(), move.getEndFile(), piece);
	} 


	void makeCaptureMove(Move move) {
		Piece piece = board.pieceAt(move.getStartRank(), move.getStartFile());
		board.removePiece(move.getStartRank(), move.getStartFile());
		board.addPiece(move.getEndRank(), move.getEndFile(), piece);
	}
}
