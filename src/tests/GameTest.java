package tests;

import pieces.Piece;
import pieces.PieceType;
import pieces.PieceColor;
import board.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

	@Test
	public void testPawnCanMoveTwoSquaresOnFirstMove() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(2, 4, 4, 4);
		Piece whitePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
		assertEquals(whitePawn, board.pieceAt(4, 4)); 
	}
	
	@Test
	public void testPawnCanMoveOneSquareForward() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(2, 4, 3, 4);
		Piece whitePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
		assertEquals(whitePawn, board.pieceAt(3, 4)); 
	}

	@Test
	public void testPawnCannotMoveTwoSquaresAfterFirstMove() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(2, 4, 3, 4);
		game.playMove(7, 4, 6, 4);
		assertThrows(UnsupportedOperationException.class, () -> game.playMove(3, 4, 5, 4));
	}

	@Test
	public void testPawnCannotCaptureEmptySquare() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		assertThrows(UnsupportedOperationException.class, () -> game.playMove(2, 4, 3, 5));
		assertThrows(UnsupportedOperationException.class, () -> game.playMove(2, 4, 3, 3));
	}

	@Test
	public void testBishopCanMoveToEmptySquare() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(2, 5, 4, 5);
		game.playMove(7, 4, 5, 4);
		game.playMove(1, 6, 5, 2);
		Piece whiteBishop = new Piece(PieceType.BISHOP, PieceColor.WHITE);
		assertEquals(whiteBishop, board.pieceAt(5, 2));
		assertNull(board.pieceAt(1, 6));
	}

	@Test
	public void testBishopCannotMoveThroughFriendlyPiece() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		assertThrows(UnsupportedOperationException.class, () -> game.playMove(1, 3, 3, 5));
		assertThrows(UnsupportedOperationException.class, () -> game.playMove(1, 6, 5, 2));
	}

	@Test
	public void testCanShortCastleWithUnoccupiedSquares() {
		BoardModel board = new BoardModel();
		MoveStrategy normal = new NormalMoveStrategy();
		board.move(normal, new Move(2, 5, 4, 5));
		board.move(normal, new Move(1, 6, 2, 5));
		board.move(normal, new Move(1, 7, 3, 6));

		Game game = new Game(board);
		game.playMove(1, 5, 1, 7);

		Piece whiteKing = new Piece(PieceType.KING, PieceColor.WHITE);
		Piece whiteRook = new Piece(PieceType.ROOK, PieceColor.WHITE);
		assertEquals(whiteKing, board.pieceAt(1, 7));
		assertEquals(whiteRook, board.pieceAt(1, 6));
		assertNull(board.pieceAt(1, 5));
		assertNull(board.pieceAt(1, 8));
	}

	@Test
	public void testCannotShortCastleAfterKingMove() {
		BoardModel board = new BoardModel();
		MoveStrategy normal = new NormalMoveStrategy();
		board.move(normal, new Move(2, 5, 4, 5));
		board.move(normal, new Move(1, 6, 2, 5));
		board.move(normal, new Move(1, 7, 3, 6));
		board.move(normal, new Move(1, 5, 1, 6));
		board.move(normal, new Move(1, 6, 1, 5));

		Game game = new Game(board);
		assertThrows(UnsupportedOperationException.class, () -> game.playMove(1, 5, 1, 7));
	}

	@Test
	public void testCannotShortCastleWithOccupiedSquares() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		assertThrows(UnsupportedOperationException.class, () -> game.playMove(1, 5, 1, 7));
	}

	@Test
	public void testCannotLongCastleAfterKingMove() {
		BoardModel board = new BoardModel();
		MoveStrategy normal = new NormalMoveStrategy();
		board.move(normal, new Move(2, 4, 4, 4));
		board.move(normal, new Move(1, 3, 3, 5));
		board.move(normal, new Move(1, 2, 3, 3));
		board.move(normal, new Move(1, 4, 2, 4));
		board.move(normal, new Move(1, 5, 1, 4));
		board.move(normal, new Move(1, 4, 1, 5));

		Game game = new Game(board);
		assertThrows(UnsupportedOperationException.class, () -> game.playMove(1, 5, 1, 3));
	}

	@Test
	public void testCannotLongCastleWithOccupiedSquares() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		assertThrows(UnsupportedOperationException.class, () -> game.playMove(1, 5, 1, 3));
	}

	@Test
	public void testPlayerCannotMoveTwice() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(1, 2, 3, 3);
		game.playMove(2, 4, 4, 4);

		// Check that pawn move wasn't actually made.
		Piece whitePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
		assertEquals(whitePawn, board.pieceAt(2, 4));
		assertNull(board.pieceAt(4, 4));
	}

	@Test
	public void testWhiteKingInCheck() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(2, 4, 4, 4);
		game.playMove(7, 5, 5, 5);
		game.playMove(1, 7, 3, 6); 
		game.playMove(8, 6, 4, 2); 

		assertTrue(game.kingIsInCheck(Game.WHITE_PLAYER));
		assertFalse(game.kingIsInCheck(Game.BLACK_PLAYER));
	}

}
