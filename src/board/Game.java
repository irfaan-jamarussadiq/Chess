package src.board;

import src.pieces.*;

import java.util.*;

public class Game {
	private final BoardModel board;
	public final Player whitePlayer;
	public final Player blackPlayer;
	private Player currentPlayer;
	private GameState gameState;
	private final MoveValidator validator;

	public Game(BoardModel board) {	
		this.board = board;
		this.whitePlayer = new Player(PieceColor.WHITE);
		this.blackPlayer = new Player(PieceColor.BLACK);
		this.currentPlayer = whitePlayer;
		this.gameState = GameState.ONGOING;

		this.validator = new MoveValidator(board);
	}

	public boolean playerIsInCheck(PieceColor color) {
		Player player = (color == PieceColor.WHITE) ? whitePlayer : blackPlayer;
		int kingRank = player.getKingLocation().rank();
		int kingFile = player.getKingLocation().file();
		return validator.squareIsAttacked(kingRank, kingFile, player.getColor());
	}

	public  boolean currentPlayerIsInCheck() {
		return playerIsInCheck(currentPlayer.getColor());
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
			board.move(startRank, startFile, endRank, endFile);
			validator.updateKingLocation(currentPlayer, endRank, endFile);
			currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
			updateGameState();
		}
	}

	public void updateGameState() {
		if (currentPlayer.getColor() == PieceColor.WHITE && playerIsInCheck(PieceColor.WHITE) && playerHasNoMoves(currentPlayer)) {
			gameState = GameState.BLACK_WON;
		} else if (currentPlayer.getColor() == PieceColor.BLACK && playerIsInCheck(PieceColor.BLACK) && playerHasNoMoves(currentPlayer)) {
			gameState = GameState.WHITE_WON;
		} else if (playerHasNoMoves(currentPlayer)) {
			gameState = GameState.STALEMATE;
		} else {
			gameState = GameState.ONGOING;
		}
	}

	public GameState getGameState() {
		return gameState;
	}

	public List<Move> getValidMoves(Piece piece, int rank, int file) {
		List<Move> validMoves = new ArrayList<>();
		if (piece == null) {
			return validMoves;
		}

		MoveGenerator generator = MoveGeneratorFactory.createMoveGenerator(piece, board);
		Set<Move> moves = new HashSet<>();
		moves.addAll(generator.getMoves(rank, file));
		moves.addAll(generator.getCaptures(rank, file));

		for (Move move : moves) {
			if (MoveValidator.moveIsWithinBoard(move) && validator.isValidMove(currentPlayer, move)) {
				validMoves.add(move);
			}
		}

		return validMoves;
	}

	public Player getCurrentPlayer() {
		return  currentPlayer;
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
