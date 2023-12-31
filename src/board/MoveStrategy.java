package src.board;

public interface MoveStrategy {
	void move(BoardModel board, Move move);
	void undoMove(BoardModel board, Move move);
	boolean isValidMove(BoardModel board, Move move);
}
