package src.tests;

import src.pieces.*;
import src.board.*;

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
		game.playMove(3, 4, 5, 4);

		Piece whitePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
		assertEquals(whitePawn, board.pieceAt(3, 4)); 
	}

	@Test
	public void testPawnCannotCaptureEmptySquare() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(2, 4, 3, 5);
		game.playMove(2, 4, 3, 3);

		Piece whitePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
		assertEquals(whitePawn, board.pieceAt(2, 4)); 
	}

	@Test
	public void testCannotCaptureFriendlyPiece() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(1, 3, 2, 4);
		
		Piece whiteBishop = new Piece(PieceType.BISHOP, PieceColor.WHITE);
		assertEquals(whiteBishop, board.pieceAt(1, 3));
		Piece whitePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
		assertEquals(whitePawn, board.pieceAt(2, 4));
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
		game.playMove(1, 3, 3, 5);
		game.playMove(1, 6, 5, 2);

		Piece whiteBishop = new Piece(PieceType.BISHOP, PieceColor.WHITE);
		assertEquals(whiteBishop, board.pieceAt(1, 3));
		assertNull(board.pieceAt(3, 5));
		assertEquals(whiteBishop, board.pieceAt(1, 6));
		assertNull(board.pieceAt(5, 2));
	}

	@Test
	public void testCanShortCastleWithUnoccupiedSquares() {
		BoardModel board = new BoardModel();
		board.move(2, 5, 4, 5);
		board.move(1, 6, 2, 5);
		board.move(1, 7, 3, 6);

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
		board.move(2, 5, 4, 5);
		board.move(1, 6, 2, 5);
		board.move(1, 7, 3, 6);
		board.move(1, 5, 1, 6);
		board.move(1, 6, 1, 5);

		Game game = new Game(board);
		game.playMove(1, 5, 1, 7);

		Piece whiteKing = new Piece(PieceType.KING, PieceColor.WHITE);
		assertEquals(whiteKing, board.pieceAt(1, 5));
		Piece whiteRook = new Piece(PieceType.ROOK, PieceColor.WHITE);
		assertEquals(whiteRook, board.pieceAt(1, 8));
	}

	@Test
	public void testCannotShortCastleWithOccupiedSquares() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(1, 5, 1, 7);

		Piece whiteKing = new Piece(PieceType.KING, PieceColor.WHITE);
		assertEquals(whiteKing, board.pieceAt(1, 5));
		Piece whiteRook = new Piece(PieceType.ROOK, PieceColor.WHITE);
		assertEquals(whiteRook, board.pieceAt(1, 8));
	}

	@Test
	public void testCannotLongCastleAfterKingMove() {
		BoardModel board = new BoardModel();
		board.move(2, 4, 4, 4);
		board.move(1, 3, 3, 5);
		board.move(1, 2, 3, 3);
		board.move(1, 4, 2, 4);
		board.move(1, 5, 1, 4);
		board.move(1, 4, 1, 5);

		Game game = new Game(board);
		game.playMove(1, 5, 1, 3);

		Piece whiteKing = new Piece(PieceType.KING, PieceColor.WHITE);
		assertEquals(whiteKing, board.pieceAt(1, 5));
		Piece whiteRook = new Piece(PieceType.ROOK, PieceColor.WHITE);
		assertEquals(whiteRook, board.pieceAt(1, 1));
	}

	@Test
	public void testCannotLongCastleWithOccupiedSquares() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(1, 5, 1, 3);

		Piece whiteKing = new Piece(PieceType.KING, PieceColor.WHITE);
		assertEquals(whiteKing, board.pieceAt(1, 5));
		Piece whiteRook = new Piece(PieceType.ROOK, PieceColor.WHITE);
		assertEquals(whiteRook, board.pieceAt(1, 1));
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

		assertTrue(game.playerIsInCheck(PieceColor.WHITE));
		assertFalse(game.playerIsInCheck(PieceColor.BLACK));
	}

	@Test
	public void testPawnMoveBlockedCheck() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(2, 4, 4, 4);
		game.playMove(7, 5, 5, 5);
		game.playMove(1, 7, 3, 6); 
		game.playMove(8, 6, 4, 2); 
		game.playMove(2, 3, 3, 3); 

		assertFalse(game.playerIsInCheck(PieceColor.WHITE));
		assertFalse(game.playerIsInCheck(PieceColor.BLACK));
	}

	@Test
	public void testKingCaptureMoveBlockedCheck() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(2, 4, 4, 4);
		game.playMove(7, 5, 5, 5);
		game.playMove(1, 7, 3, 6); 
		game.playMove(8, 6, 4, 2); 
		game.playMove(1, 3, 2, 4); 
		game.playMove(4, 2, 2, 4); 
		game.playMove(1, 5, 2, 4); 

		assertFalse(game.playerIsInCheck(PieceColor.WHITE));
		assertFalse(game.playerIsInCheck(PieceColor.BLACK));
	}

	@Test
	public void testKingCannotMoveIntoCheck() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(2, 4, 4, 4);
		game.playMove(7, 5, 5, 5);
		game.playMove(1, 7, 3, 6); 
		game.playMove(8, 6, 4, 2); 
		game.playMove(1, 5, 2, 4); 
		
		Piece king = new Piece(PieceType.KING, PieceColor.WHITE);
		assertEquals(king, board.pieceAt(1, 5));
	}

	@Test
	public void testKingNotInCheckmate() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(2, 4, 4, 4);
		game.playMove(7, 5, 5, 5);
		
		assertFalse(game.playerIsInCheckmate(PieceColor.WHITE));
		assertFalse(game.playerIsInCheckmate(PieceColor.BLACK));
	}

	@Test
	public void testKingIsInCheckmate() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(2, 7, 4, 7);
		game.playMove(7, 5, 5, 5);
		game.playMove(2, 6, 4, 6);
		game.playMove(8, 4, 4, 8);
		
		assertTrue(game.playerIsInCheckmate(PieceColor.WHITE));
		assertFalse(game.playerIsInCheckmate(PieceColor.BLACK));
	}

	@Test
	public void testKnightCanDefendCheckmateThreat() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(2, 6, 4, 6);
		game.playMove(7, 5, 5, 5);
		game.playMove(1, 7, 3, 8);
		game.playMove(8, 4, 4, 8);
		
		assertFalse(game.playerIsInCheckmate(PieceColor.WHITE));
		assertFalse(game.playerIsInCheckmate(PieceColor.BLACK));
	}

	@Test
	public void testKingIsInCheckmateAfterFourMoves() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		game.playMove(2, 5, 4, 5);
		game.playMove(7, 5, 5, 5);
		game.playMove(1, 6, 4, 3);
		game.playMove(8, 6, 5, 3);
		game.playMove(1, 4, 5, 8);
		game.playMove(8, 7, 6, 6);
		game.playMove(5, 8, 7, 6);
		
		assertFalse(game.playerIsInCheckmate(PieceColor.WHITE));
		assertTrue(game.playerIsInCheckmate(PieceColor.BLACK));
	}

	@Test
	public void testKingIsNotInStalemate() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		assertFalse(game.playerIsInStalemate(PieceColor.BLACK));
	}

	@Test
	public void testKingIsInStalemate() {
		BoardModel board = new BoardModel();
		Game game = new Game(board);
		Move[] moves = {
			new Move(2, 5, 3, 5),
			new Move(7, 1, 5, 1),
			new Move(1, 4, 5, 8),			
			new Move(8, 1, 6, 1),			
			new Move(5, 8, 5, 1),			
			new Move(7, 8, 5, 8),			
			new Move(2, 8, 4, 8),			
			new Move(6, 1, 6, 8),			
			new Move(5, 1, 7, 3),			
			new Move(7, 6, 6, 6),			
			new Move(7, 3, 7, 4),			
			new Move(8, 5, 7, 6),			
			new Move(7, 4, 7, 2),			
			new Move(8, 4, 3, 4),			
			new Move(7, 2, 8, 2),			
			new Move(3, 4, 7, 8),			
			new Move(8, 2, 8, 3),			
			new Move(7, 6, 6, 7),			
			new Move(8, 3, 6, 5)			
		}; 	
		
		for (Move move : moves) {
			game.playMove(move.startRank(), move.startFile(), move.endRank(), move.endFile());
		}	

		System.out.println(game);
		assertFalse(game.playerIsInStalemate(PieceColor.BLACK));
	}

}
