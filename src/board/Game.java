package src.board;

import src.board.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private final BoardModel board;

	public final Player whitePlayer;
	public final Player blackPlayer;
	private Player currentPlayer;
	private final MoveValidator validator;

	public Game(BoardModel board) {	
		this.board = board;
		this.whitePlayer = new Player(PieceColor.WHITE);
		this.blackPlayer = new Player(PieceColor.BLACK);
		this.currentPlayer = whitePlayer;
		this.validator = new MoveValidator(board);
	}

	public boolean playerIsInCheck(PieceColor color) {
		Player player = (color == PieceColor.WHITE) ? whitePlayer : blackPlayer;
		int kingRank = player.getKingLocation().rank();
		int kingFile = player.getKingLocation().file();
		return validator.squareIsAttacked(kingRank, kingFile, player.getColor());
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
		if (validator.isValidMove(currentPlayer, move)) {
			MoveStrategy strategy = MoveStrategyFactory.createMoveStrategy(board, move);
			board.move(strategy, move);
			validator.updateKingLocation(currentPlayer, endRank, endFile);
			currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
		}
	}

	@Override
	public String toString() {
		String player = (currentPlayer == whitePlayer) ? "WHITE" : "BLACK";	
		String playerString = String.format("Current player: %s", player);
		return playerString + "\n" + board.toString();
	}

	public List<Move> getValidMoves(Piece piece, int rank, int file) {
		List<Move> validMoves = new ArrayList<>();
		if (piece == null) {
			return validMoves;
		}

		MoveGenerator generator = MoveGeneratorFactory.createMoveGenerator(piece, board);
		for (Move move : generator.getMoves(rank, file)) {
			if (MoveValidator.moveIsWithinBoard(move) && validator.isValidMove(currentPlayer, move)) {
				validMoves.add(move);
			}
		}

		return validMoves;
	}

	private boolean playerHasNoMoves(Player player) {
		for (int rank = 1; rank <= BoardModel.SIZE; rank++) {
			for (int file = 1; file <= BoardModel.SIZE; file++) {
				Piece piece = board.pieceAt(rank, file);
				if (piece != null && piece.getColor() == player.getColor()) {
					MoveGenerator generator = MoveGeneratorFactory.createMoveGenerator(piece, board);
					for (Move move : generator.getMoves(rank, file)) {
						if (MoveValidator.moveIsWithinBoard(move) && validator.isValidMove(player, move)) {
							return false;
						}
					}
				}
			}
		}

		return true;			 
	}

}
