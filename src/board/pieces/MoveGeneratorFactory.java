package src.board.pieces;

import src.board.BoardModel;

public class MoveGeneratorFactory {

	public static MoveGenerator createMoveGenerator(Piece piece, BoardModel board) {
		if (piece.getType() == PieceType.PAWN) {
			return new PawnMoveGenerator(piece.getColor(), piece.hasMoved());
		} else if (piece.getType() == PieceType.KNIGHT) {
			return new KnightMoveGenerator();
		} else if (piece.getType() == PieceType.BISHOP) {
			return new BishopMoveGenerator(board);
		} else if (piece.getType() == PieceType.ROOK) {
			return new RookMoveGenerator(board);
		} else if (piece.getType() == PieceType.QUEEN) {
			return new QueenMoveGenerator(board);
		} else if (piece.getType() == PieceType.KING) {
			return new KingMoveGenerator();
		}

		throw new UnsupportedOperationException("Could not find move generator for piece!");
	}
}

