package src.board;

import src.pieces.*;

public class BoardModel implements Board {

	public static final int SIZE = 8;
	private final Piece[][] board;

	public BoardModel() {
		board = new Piece[SIZE][SIZE];
		setUpStartingPosition();
	}

	private void setUpStartingPosition() {
		PieceType[] pieceRow = { 
			PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, 
			PieceType.QUEEN, PieceType.KING,
			PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK 
		};

		for (int file = SIZE / 2 - 3; file <= SIZE / 2 + 4; file++) {
			addPiece(new Piece(PieceType.PAWN, PieceColor.WHITE), 2, file);
			addPiece(new Piece(PieceType.PAWN, PieceColor.BLACK), SIZE - 1, file);
			addPiece(new Piece(pieceRow[file - 1], PieceColor.WHITE), 1, file);
			addPiece(new Piece(pieceRow[file - 1], PieceColor.BLACK), SIZE, file);
		}

	}

	public Piece pieceAt(int rank, int file) {
		return board[rank - 1][file - 1];
	}

	public void move(int startRank, int startFile, int endRank, int endFile) {
		Move move = new Move(startRank, startFile, endRank, endFile);
		MoveStrategy strategy = MoveStrategyFactory.createMoveStrategy(this, move);
		strategy.move(move);
	}

	void addPiece(Piece piece, int rank, int file) {
		if (piece == null) {
			throw new IllegalArgumentException("Cannot add null piece.");
		}

		board[rank - 1][file - 1] = piece;
	}

	void removePiece(int rank, int file) {
		board[rank - 1][file - 1] = null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int rank = SIZE; rank >= 1; rank--) {
			for (int file = 1; file <= SIZE; file++) {
				Piece piece = pieceAt(rank, file);
				if (piece != null) {
					sb.append(piece.getBoardCharacter()).append(" ");
				} else {
					sb.append(". ");
				}	
			}

			sb.append("\n");
		}

		return sb.toString();
	}
}
