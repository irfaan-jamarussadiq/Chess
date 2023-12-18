package board;

import pieces.Piece;

class EnPassantMoveStrategy implements MoveStrategy {

	public void move(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.getStartRank(), move.getStartFile());

		// Move pawn
		board.removePiece(move.getStartRank(), move.getStartFile());
		board.addPiece(pieceToMove, move.getEndRank(), move.getEndFile());

		// Capture enemy pawn
		board.removePiece(move.getStartRank(), move.getEndFile());
	}
}	
