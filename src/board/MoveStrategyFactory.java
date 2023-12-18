package board;

import pieces.Piece;
import pieces.PieceType;
import pieces.PieceColor;

class MoveStrategyFactory {

	public static MoveStrategy createMoveStrategy(BoardModel board, Move move) {
		if (isNormalMove(board, move)) {
			return new NormalMoveStrategy();
		} else if (isCaptureMove(board, move)) {
			return new CaptureMoveStrategy();
		} else if (isEnPassantMove(board, move)) {
			return new EnPassantMoveStrategy();
		} else if (isShortCastlingMove(board, move)) {
			return new ShortCastlingMoveStrategy();
		} else if (isLongCastlingMove(board, move)) {
			return new LongCastlingMoveStrategy();
		} else {
			throw new UnsupportedOperationException("Please create a new MoveStrategy.");
		}
	}

	private static boolean moveIsWithinBoard(Move move) {
		return move.getStartRank() >= 1 && move.getStartRank() <= BoardModel.SIZE
			&& move.getStartFile() >= 1 && move.getStartFile() <= BoardModel.SIZE
			&& move.getEndRank() >= 1 && move.getEndRank() <= BoardModel.SIZE
			&& move.getEndFile() >= 1 && move.getEndFile() <= BoardModel.SIZE;
	}

	private static boolean isNormalMove(BoardModel board, Move move) {
		if (!moveIsWithinBoard(move)) {
			return false;
		}

		Piece pieceToMove = board.pieceAt(move.getStartRank(), move.getStartFile());
		Piece pieceAtDestination = board.pieceAt(move.getEndRank(), move.getEndFile());
		return pieceToMove != null && pieceAtDestination == null;	
	}

	private static boolean isCaptureMove(BoardModel board, Move move) {
		if (!moveIsWithinBoard(move)) {
			return false;
		}

		Piece pieceToMove = board.pieceAt(move.getStartRank(), move.getStartFile());
		Piece pieceAtDestination = board.pieceAt(move.getEndRank(), move.getEndFile());
		return pieceToMove != null && pieceToMove.isEnemyOf(pieceAtDestination);
	}

	private static boolean isEnPassantMove(BoardModel board, Move move) {
		if (!moveIsWithinBoard(move)) {
			return false;
		}

		// Check if piece moves to empty square 
		Piece pieceToMove = board.pieceAt(move.getStartRank(), move.getStartFile());
		Piece pieceAtDestination = board.pieceAt(move.getEndRank(), move.getEndFile());
		if (pieceToMove == null || pieceAtDestination != null) {
			return false;
		}

		// Check if piece is pawn moving diagonally while capturing adjacent enemy pawn 
		Piece whitePawn = new Piece(PieceType.PAWN, PieceColor.WHITE);
		Piece blackPawn = new Piece(PieceType.PAWN, PieceColor.BLACK);
		Piece capturedPawn = board.pieceAt(move.getStartRank(), move.getEndFile());
		if (pieceToMove.equals(whitePawn) && capturedPawn.equals(blackPawn)) {
			return move.getStartRank() == BoardModel.SIZE - 3 && move.getEndRank() == BoardModel.SIZE - 2	
				&& Math.abs(move.getEndFile() - move.getStartFile()) == 1;
		}

		if (pieceToMove.equals(blackPawn) && capturedPawn.equals(whitePawn)) {
			return move.getStartRank() == 4 && move.getEndRank() == 3 	
				&& Math.abs(move.getEndFile() - move.getStartFile()) == 1;
		}

		return false;
	}

	private static boolean isShortCastlingMove(BoardModel board, Move move) {
		if (!moveIsWithinBoard(move) || move.getStartRank() != move.getEndRank()) {
			return false;
		}

		Piece king = board.pieceAt(move.getStartRank(), move.getStartFile());
		Piece rook = board.pieceAt(move.getStartRank(), move.getStartFile() + 3);
		if (king.getType() != PieceType.KING || rook.getType() != PieceType.ROOK) {
			return false;
		}

		// Check if castling squares are empty.
		Piece firstCastlingSquare = board.pieceAt(move.getStartRank(), move.getStartFile() + 1);
		Piece secondCastlingSquare = board.pieceAt(move.getStartRank(), move.getStartFile() + 2);
		if (firstCastlingSquare != null || secondCastlingSquare != null) {
			return false;
		}

		// Check if king + castling squares are under attack.
		for (int file = move.getStartFile(); file <= move.getStartFile() + 2; file++) {
			if (board.squareIsAttacked(move.getStartRank(), file, king.getColor())) {
				return false;
			} 
		}	

		return true;
	}

	private static boolean isLongCastlingMove(BoardModel board, Move move) {
		if (!moveIsWithinBoard(move) || move.getStartRank() != move.getEndRank()) {
			return false;
		}

		Piece king = board.pieceAt(move.getStartRank(), move.getStartFile());
		Piece rook = board.pieceAt(move.getStartRank(), move.getStartFile() - 4);
		if (king.getType() != PieceType.KING || rook.getType() != PieceType.ROOK) {
			return false;
		}

		// Check if castling squares are empty.
		for (int file = move.getStartFile() - 3; file <= move.getStartFile() - 1; file++) {
			if (board.pieceAt(move.getStartRank(), file) == null) {
				return false;
			}
		}	

		// Check if king + castling squares are under attack.
		for (int file = move.getStartFile() - 3; file <= move.getStartFile(); file++) {
			if (board.squareIsAttacked(move.getStartRank(), file, king.getColor())) {
				return false;
			} 
		}	

		return true;
	}
}		
