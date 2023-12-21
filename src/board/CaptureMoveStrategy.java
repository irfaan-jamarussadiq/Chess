package board;

import pieces.Piece;

class CaptureMoveStrategy implements MoveStrategy {
	public void move(BoardModel board, Move move) {
		Piece pieceToMove = board.pieceAt(move.getStartRank(), move.getStartFile());
		Piece pieceAtDestination = board.pieceAt(move.getEndRank(), move.getEndFile());

		board.removePiece(move.getStartRank(), move.getStartFile());
		board.addPiece(pieceToMove, move.getEndRank(), move.getEndFile());
	}	
}	
