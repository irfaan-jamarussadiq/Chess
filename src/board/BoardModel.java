package board;

import pieces.Piece;
import pieces.PieceType;
import pieces.PieceColor;

public class BoardModel {

	public static final int SIZE = 8;
	private Piece[][] board;
	private MoveValidator validator;
	private MoveExecutor executor;

	public BoardModel() {
		if (SIZE < 8) {
			throw new IllegalStateException();
		}

		validator = new MoveValidator(this);
		executor = new MoveExecutor(this);

		board = new Piece[SIZE][SIZE];
		setUpStartingPosition();
	}

	private void setUpStartingPosition() {
		int start = SIZE / 2 - 4;
		int end = SIZE / 2 + 4;

		PieceType[] pieceRow = { 
			PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, 
			PieceType.QUEEN, PieceType.KING,
			PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP 
		};

		for (int file = start; file < end; file++) {
			board[start + 1][file] = new Piece(PieceType.PAWN, PieceColor.WHITE); 		
			board[end - 1][file] = new Piece(PieceType.PAWN, PieceColor.BLACK); 		
			board[start][file] = new Piece(pieceRow[file], PieceColor.WHITE);
			board[end][file] = new Piece(pieceRow[file], PieceColor.BLACK);
		}
	}

	public Piece pieceAt(int rank, int file) {
		return board[rank - 1][file - 1];
	}

	public void movePiece(int startRank, int startFile, int endRank, int endFile) {
		Move move = new Move(startRank, startFile, endRank, endFile);
		if (validator.isNormalMove(move)) {
			executor.makeNormalMove(move);
		} else if (validator.isCaptureMove(move)) {
			executor.makeCaptureMove(move);
		} else {
			throw new InvalidMoveException(startRank, startFile, endRank, endFile);
		}
	}

	void addPiece(Piece piece, int rank, int file) {
		board[rank - 1][file - 1] = piece;
	}

	void removePiece(int rank, int file) {
		board[rank - 1][file - 1] = null;
	}
}
