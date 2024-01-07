package src.board;

public class MoveStrategyFactory {
	public static MoveStrategy createMoveStrategy(BoardModel board, Move move) {
		MoveStrategy[] strategies = {
				new NormalMoveStrategy(board),
				new CaptureMoveStrategy(board),
				new EnPassantMoveStrategy(board),
				new ShortCastlingMoveStrategy(board),
				new LongCastlingMoveStrategy(board)
		};

		for (MoveStrategy strategy : strategies) {
			if (strategy.isValidMove(move)) {
				return strategy;
			}
		}		

		throw new UnsupportedOperationException("Could not find move strategy for move.");
	}
}
