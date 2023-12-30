package board;

import java.util.List;
import java.util.Stack;

import pieces.Piece;
import pieces.PieceType;
import pieces.PieceColor;
import pieces.MoveGenerator;
import pieces.MoveGeneratorFactory;
import pieces.KingMoveGenerator;

public class Game {
	private BoardModel board;
	private Stack<Move> moveHistory; 

	public final Player whitePlayer;
	public final Player blackPlayer;
	private Player currentPlayer;

	public Game(BoardModel board) {	
		this.board = board;
		this.moveHistory = new Stack<>();
		this.whitePlayer = new Player(PieceColor.WHITE);
		this.blackPlayer = new Player(PieceColor.BLACK);
		this.currentPlayer = whitePlayer; 
	}

	public boolean playerIsInCheck(PieceColor color) {
		Player player = (color == PieceColor.WHITE) ? whitePlayer : blackPlayer;
		int kingRank = player.getKingLocation().getRank();
		int kingFile = player.getKingLocation().getFile();
		return squareIsAttacked(kingRank, kingFile, player.getColor());
	}

	public boolean playerIsInCheckmate(PieceColor color) {
		Player player = (color == PieceColor.WHITE) ? whitePlayer : blackPlayer;
		return playerIsInCheck(color) && playerHasNoMoves(player);
	}

	public boolean playerIsInStalemate(PieceColor color) {
		Player player = (color == PieceColor.WHITE) ? whitePlayer : blackPlayer;
		return !playerIsInCheck(color) && playerHasNoMoves(player);
	}

	public void playMove(int startRank, int startFile, int endRank, int endFile) {
		Move move = new Move(startRank, startFile, endRank, endFile);	
		if (isValidMove(currentPlayer, move)) {
			MoveStrategy strategy = MoveStrategyFactory.createMoveStrategy(board, move);
			board.move(strategy, move);
			updateKingLocation(currentPlayer, endRank, endFile);
			moveHistory.push(move);
			currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
		}
	}

	@Override
	public String toString() {
		String player = (currentPlayer == whitePlayer) ? "WHITE" : "BLACK";	
		String playerString = String.format("Current player: %s", player);
		return playerString + "\n" + board.toString();
	}

	private boolean playerHasNoMoves(Player player) {
		for (int rank = 1; rank <= BoardModel.SIZE; rank++) {
			for (int file = 1; file <= BoardModel.SIZE; file++) {
				Piece piece = board.pieceAt(rank, file);
				if (piece != null && piece.getColor() == player.getColor()) {
					MoveGenerator generator = MoveGeneratorFactory.createMoveGenerator(piece, board);
					for (Move move : generator.getMoves(rank, file)) {
						if (moveIsWithinBoard(move) && isValidMove(player, move)) {
							return false;
						}
					}
				}
			}
		}

		return true;			 
	}

	private void updateKingLocation(Player player, int rank, int file) {
		Piece expectedKing = board.pieceAt(rank, file);
		if (expectedKing != null && expectedKing.getType() == PieceType.KING) {
			player.setKingLocation(rank, file);
		}
	}

	private boolean isValidMove(Player player, Move move) {
		Piece pieceToMove = board.pieceAt(move.getStartRank(), move.getStartFile());	
		if (pieceToMove == null || pieceToMove.getColor() != player.getColor()) {
			return false;
		}
		
		MoveStrategy strategy;
		boolean isValid;
		try {
			strategy = MoveStrategyFactory.createMoveStrategy(board, move);
			isValid = strategy.isValidMove(board, move) && !moveWouldCauseCheck(player, move, strategy);
		} catch(UnsupportedOperationException e) {
			isValid = false;
		}

		return isValid;
	}

	private boolean moveWouldCauseCheck(Player player, Move move, MoveStrategy strategy) {
		strategy.move(board, move);
		updateKingLocation(player, move.getEndRank(), move.getEndFile());
		Location kingLocation = player.getKingLocation();
		boolean kingInCheck = squareIsAttacked(kingLocation.getRank(), kingLocation.getFile(), player.getColor());
		strategy.undoMove(board, move);		
		updateKingLocation(player, move.getStartRank(), move.getStartFile());
		return kingInCheck;
	}

	private boolean squareIsAttacked(int rank, int file, PieceColor color) {
		for (PieceType type : PieceType.values()) {
			Piece piece = new Piece(type, color);
			MoveGenerator generator = MoveGeneratorFactory.createMoveGenerator(piece, board);
			List<Move> captures = generator.getCaptures(rank, file);
			for (Move move : captures) {
				if (moveIsWithinBoard(move)) {
					Piece potentialEnemy = board.pieceAt(move.getEndRank(), move.getEndFile());
					if (piece.isEnemyOf(potentialEnemy) && potentialEnemy.getType() == type) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private static boolean moveIsWithinBoard(Move move) {
		return move.getStartRank() >= 1 && move.getStartRank() <= BoardModel.SIZE
			&& move.getStartFile() >= 1 && move.getStartFile() <= BoardModel.SIZE
			&& move.getEndRank() >= 1 && move.getEndRank() <= BoardModel.SIZE
			&& move.getEndFile() >= 1 && move.getEndFile() <= BoardModel.SIZE;
	}

}
