package src.board;

public interface MoveStrategy {
	void move(Move move);
	void undoMove(Move move);
	boolean isValidMove(Move move);
}
