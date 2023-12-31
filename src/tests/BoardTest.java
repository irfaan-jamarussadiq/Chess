package src.tests;

import src.board.pieces.*;
import src.board.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

	@Test
	public void testPieceAccess() {
		BoardModel board = new BoardModel();
		assertEquals(board.pieceAt(1, 1), new Piece(PieceType.ROOK, PieceColor.WHITE));
	}

	@Test
	public void testPawnCanMoveTwoSquaresOnFirstMove() {
		BoardModel board = new BoardModel();
		MoveStrategy strategy = new NormalMoveStrategy();
		Move move = new Move(2, 4, 4, 4);
		board.move(strategy, move);
		Piece pawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
		assertEquals(pawn, board.pieceAt(4, 4));
		assertNull(board.pieceAt(2, 4));
	}

	@Test
	public void testMakePawnCaptureMove() {
		BoardModel board = new BoardModel();

		MoveStrategy normal = new NormalMoveStrategy();
		board.move(normal, new Move(2, 4, 4, 4));
		board.move(normal, new Move(7, 5, 5, 5));

		MoveStrategy capture = new CaptureMoveStrategy();
		board.move(capture, new Move(4, 4, 5, 5));
		
		Piece whitePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
		assertEquals(whitePawn, board.pieceAt(5, 5));
		assertNull(board.pieceAt(4, 4));
	}

	@Test
	public void testMakeKnightCaptureMove() {
		BoardModel board = new BoardModel();

		MoveStrategy normal = new NormalMoveStrategy();
		board.move(normal, new Move(2, 5, 4, 5));
		board.move(normal, new Move(7, 5, 5, 5));
		board.move(normal, new Move(1, 7, 3, 6));
		board.move(normal, new Move(8, 7, 6, 6));

		MoveStrategy capture = new CaptureMoveStrategy();
		board.move(capture, new Move(3, 6, 5, 5));
		
		Piece whiteKnight = new Piece(PieceType.KNIGHT, PieceColor.WHITE);
		assertEquals(whiteKnight, board.pieceAt(5, 5));
		assertNull(board.pieceAt(3, 6));
	}

	@Test
	public void testMakeEnPassantMove() {
		BoardModel board = new BoardModel();

		MoveStrategy normal = new NormalMoveStrategy();
		board.move(normal, new Move(2, 5, 4, 5));
		board.move(normal, new Move(7, 7, 6, 7));
		board.move(normal, new Move(4, 5, 5, 5));
		board.move(normal, new Move(7, 4, 5, 4));

		MoveStrategy enPassant = new EnPassantMoveStrategy();
		board.move(enPassant, new Move(5, 5, 6, 4));
		
		Piece whitePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
		assertEquals(whitePawn, board.pieceAt(6, 4));
		assertNull(board.pieceAt(5, 5));
		assertNull(board.pieceAt(5, 4));
	}

	@Test
	public void testMakeShortCastlingMove() {
		BoardModel board = new BoardModel();

		MoveStrategy normal = new NormalMoveStrategy();
		board.move(normal, new Move(2, 5, 4, 5));
		board.move(normal, new Move(1, 6, 2, 5));
		board.move(normal, new Move(1, 7, 3, 6));

		MoveStrategy shortCastling = new ShortCastlingMoveStrategy();
		board.move(shortCastling, new Move(1, 5, 1, 7));
		
		Piece whiteKing = new Piece(PieceType.KING, PieceColor.WHITE);
		Piece whiteRook = new Piece(PieceType.ROOK, PieceColor.WHITE);
		assertEquals(whiteKing, board.pieceAt(1, 7));
		assertEquals(whiteRook, board.pieceAt(1, 6));
		assertNull(board.pieceAt(1, 5));
		assertNull(board.pieceAt(1, 8));
	}

	@Test
	public void testMakeLongCastlingMove() {
		BoardModel board = new BoardModel();

		MoveStrategy normal = new NormalMoveStrategy();
		System.out.println(board);	
		board.move(normal, new Move(2, 4, 4, 4));
		System.out.println(board);	
		board.move(normal, new Move(1, 3, 3, 5));
		System.out.println(board);	
		board.move(normal, new Move(1, 2, 3, 3));
		System.out.println(board);	
		board.move(normal, new Move(1, 4, 2, 4));
		System.out.println(board);	

		MoveStrategy shortCastling = new LongCastlingMoveStrategy();
		board.move(shortCastling, new Move(1, 5, 1, 3));
		System.out.println(board);	
		
		Piece whiteKing = new Piece(PieceType.KING, PieceColor.WHITE);
		Piece whiteRook = new Piece(PieceType.ROOK, PieceColor.WHITE);
		assertEquals(whiteKing, board.pieceAt(1, 3));
		assertEquals(whiteRook, board.pieceAt(1, 4));
		assertNull(board.pieceAt(1, 5));
		assertNull(board.pieceAt(1, 1));
	}					
}
