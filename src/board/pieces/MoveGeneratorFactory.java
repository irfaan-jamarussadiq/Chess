package pieces;

import java.util.Map;
import static java.util.Map.entry;
import board.BoardModel;

public class MoveGeneratorFactory {

	public static MoveGenerator createMoveGenerator(Piece piece) {
		if (piece.getType() == PieceType.PAWN) {
			return new PawnMoveGenerator(piece.getColor());
		} else if (piece.getType() == PieceType.KNIGHT) {
			return new KnightMoveGenerator();
		} else if (piece.getType() == PieceType.BISHOP) {
			return new BishopMoveGenerator();
		} else if (piece.getType() == PieceType.ROOK) {
			return new RookMoveGenerator();
		} else if (piece.getType() == PieceType.QUEEN) {
			return new QueenMoveGenerator();
		} else if (piece.getType() == PieceType.KING) {
			return new KingMoveGenerator();
		}

		throw new UnsupportedOperationException("Could not find move generator for piece!");
	}
}

