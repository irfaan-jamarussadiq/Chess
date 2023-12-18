package tests;

import pieces.Piece;
import pieces.PieceType;
import pieces.PieceColor;
import board.BoardModel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {

	@Test
	public void testPieceAccess() {
		BoardModel board = new BoardModel();
		assertEquals(board.pieceAt(1, 1), new Piece(PieceType.ROOK, PieceColor.WHITE));
	}

	@Test
	public void testNormalMove() {
		BoardModel board = new BoardModel();
		board.movePiece(2, 4, 4, 4);
		assertEquals(board.pieceAt(4, 4), new Piece(PieceType.PAWN, PieceColor.WHITE));
		assertEquals(board.pieceAt(2, 4), null);
	}
}
