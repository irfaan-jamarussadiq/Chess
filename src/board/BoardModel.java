package board;

import java.util.List;
import java.util.ArrayList;

import pieces.Piece;
import pieces.PieceType;
import pieces.PieceColor;
import pieces.MoveGenerator;
import pieces.RookMoveGenerator;
import pieces.BishopMoveGenerator;
import pieces.KnightMoveGenerator;

public class BoardModel {

	public static final int SIZE = 8;
	private Piece[][] board;

	public BoardModel() {
		if (SIZE < 8) {
			throw new IllegalStateException();
		}

		board = new Piece[SIZE][SIZE];
		setUpStartingPosition();
	}

	private void setUpStartingPosition() {
		PieceType[] pieceRow = { 
			PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, 
			PieceType.QUEEN, PieceType.KING,
			PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP 
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

	public void movePiece(int startRank, int startFile, int endRank, int endFile) {
		Move move = new Move(startRank, startFile, endRank, endFile);
		MoveStrategy strategy = MoveStrategyFactory.createMoveStrategy(this, move);
		strategy.move(this, move);
	}

	void addPiece(Piece piece, int rank, int file) {
		board[rank - 1][file - 1] = piece;
	}

	void removePiece(int rank, int file) {
		board[rank - 1][file - 1] = null;
	}

	boolean squareIsAttacked(int rank, int file, PieceColor color) {
		List<Move> attackingMoves = new ArrayList<>(BoardModel.SIZE / 2);	
		attackingMoves.addAll(new KnightMoveGenerator().getMoves(rank, file));
		attackingMoves.addAll(new RookMoveGenerator(this).getMoves(rank, file));
		attackingMoves.addAll(new BishopMoveGenerator(this).getMoves(rank, file));

		Piece currentPiece = pieceAt(rank, file);
		for (Move move : attackingMoves) {
			Piece potentialEnemy = pieceAt(move.getEndRank(), move.getEndFile());
			if (potentialEnemy != null && potentialEnemy.isEnemyOf(currentPiece)) {
				return true;
			}
		}	

		return false;
	}	
}
