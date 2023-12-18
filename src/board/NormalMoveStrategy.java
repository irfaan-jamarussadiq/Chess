package board;

import pieces.Piece;
 
class NormalMoveStrategy implements MoveStrategy {

	public void move(BoardModel board, Move move) {
		Piece piece = board.pieceAt(move.getStartRank(), move.getStartFile());
		board.removePiece(move.getStartRank(), move.getStartFile());
		board.addPiece(piece, move.getEndRank(), move.getEndFile());
	}
}	
