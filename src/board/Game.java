package board;

import java.util.List;
import java.util.Stack;

import pieces.Piece;
import pieces.PieceColor;
import pieces.MoveGenerator;
import pieces.MoveGeneratorFactory;
import pieces.KingMoveGenerator;

public class Game {
	private BoardModel board;
	private MoveValidator validator;
	private Stack<Move> moveHistory; 

	private static Player WHITE_PLAYER = new Player(PieceColor.WHITE);
	private static Player BLACK_PLAYER = new Player(PieceColor.BLACK);
	private Player currentPlayer;

	public Game(BoardModel board) {	
		this.board = board;
		this.validator = new MoveValidator(board);
		this.moveHistory = new Stack<>();
		this.currentPlayer = WHITE_PLAYER; 
	}

	public boolean kingIsInCheck(Player player) {
		return false;
	}	

	public boolean kingIsInCheckmate(Player player) {
		return kingIsInCheck(player) && kingHasNoMoves(player);
	}

	public boolean kingIsInStalemate(Player player) {
		return !kingIsInCheck(player) && kingHasNoMoves(player);
	}

	public void playMove(int startRank, int startFile, int endRank, int endFile) {
		Piece pieceToMove = board.pieceAt(startRank, startFile);
		Piece pieceAtDestination = board.pieceAt(endRank, endFile);
		Move move = new Move(startRank, startFile, endRank, endFile);	

		MoveStrategy strategy = MoveStrategyFactory.createMoveStrategy(board, move);

		if (validator.isValidMove(currentPlayer, strategy, move) && currentPlayer.getColor() == pieceToMove.getColor()) {
			board.move(strategy, move);
			moveHistory.push(move);
			currentPlayer = (currentPlayer == WHITE_PLAYER) ? BLACK_PLAYER : WHITE_PLAYER;
		}
	}

	private boolean kingHasNoMoves(Player player) {
		KingMoveGenerator generator = new KingMoveGenerator(); 
		Location kingLocation = player.getKingLocation();
		List<Move> kingMoves = generator.getMoves(kingLocation.getRank(), kingLocation.getFile());
		for (Move move : kingMoves) {
			// TODO ...
		}

		return true;
	}

	@Override
	public String toString() {
		String player = (currentPlayer == WHITE_PLAYER) ? "WHITE" : "BLACK";	
		String playerString = String.format("Current player: %s", player);
		return playerString + "\n" + board.toString();
	}
}
