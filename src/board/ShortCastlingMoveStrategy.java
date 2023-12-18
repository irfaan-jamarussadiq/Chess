package board;

import pieces.Piece;

class ShortCastlingMoveStrategy implements MoveStrategy {

	public void move(BoardModel board, Move move) {
		// Move king
		Piece king = board.pieceAt(move.getStartRank(), move.getStartFile());
		board.removePiece(move.getStartRank(), move.getStartFile());
		board.addPiece(king, move.getEndRank(), move.getEndFile());

		// Move rook
		Piece rook = board.pieceAt(move.getStartRank(), move.getStartFile() + 3);
		board.removePiece(move.getStartRank(), move.getStartFile() + 3);
		board.addPiece(rook, move.getStartRank(), move.getStartFile() + 1);
	}
}	