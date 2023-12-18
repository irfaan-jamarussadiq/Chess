package tests;

import pieces.Piece;
import pieces.PieceType;
import pieces.PieceColor;
import board.BoardModel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {
	@Test
	public void testInitialBoardSetUp() {
		BoardModel board = new BoardModel();
		// Check rooks are in correct locations
		assertEquals(board.pieceAt(1, 1), new Piece(PieceType.ROOK, PieceColor.WHITE));
	}
}
