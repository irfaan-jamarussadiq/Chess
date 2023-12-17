package board;

import pieces.Piece;

class MoveExecutor {
	private BoardModel board;

	MoveExecutor(BoardModel board) {
		this.board = board;
	}

	void makeNormalMove(Move move) {
		Piece piece = board.pieceAt(move.getStartRank(), move.getStartFile());
		board.removePiece(move.getStartRank(), move.getStartFile());
		board.addPiece(piece, move.getEndRank(), move.getEndFile());
	} 


	void makeCaptureMove(Move move) {
		Piece piece = board.pieceAt(move.getStartRank(), move.getStartFile());
		board.removePiece(move.getStartRank(), move.getStartFile());
		board.addPiece(piece, move.getEndRank(), move.getEndFile());
	}
}
