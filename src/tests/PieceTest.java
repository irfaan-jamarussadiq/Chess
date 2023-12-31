package src.tests;

import src.board.pieces.Piece;
import src.board.pieces.PieceType;
import src.board.pieces.PieceColor;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

	@Test
	public void testBlackKnightBoardCharacterIsUppercaseN() {
		Piece piece = new Piece(PieceType.KNIGHT, PieceColor.BLACK);
		assertEquals(piece.getBoardCharacter(), 'N');			
	}

	@Test
	public void testWhiteKnightBoardCharacterIsLowercaseN() {
		Piece piece = new Piece(PieceType.KNIGHT, PieceColor.WHITE);
		assertEquals(piece.getBoardCharacter(), 'n');			
	}

	@Test
	public void testOppositeColorsAreEnemies() {
		Piece piece1 = new Piece(PieceType.PAWN, PieceColor.BLACK);
		Piece piece2 = new Piece(PieceType.ROOK, PieceColor.WHITE);
		assertTrue(piece1.isEnemyOf(piece2));
	}

	@Test
	public void testEmptySquareIsNotEnemy() {
		Piece piece1 = new Piece(PieceType.PAWN, PieceColor.BLACK);
		assertFalse(piece1.isEnemyOf(null));
	}
}
