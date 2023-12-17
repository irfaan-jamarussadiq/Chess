package board;

import pieces.Piece;

class MoveExecutor {
	private BoardModel board;
	private Move move;

	MoveExecutor(BoardModel board, Move move) {
		this.board = board;
		this.move = move;
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
