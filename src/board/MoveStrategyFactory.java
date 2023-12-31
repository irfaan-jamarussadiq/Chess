package src.board;

public class MoveStrategyFactory {

	private static MoveStrategy[] strategies = {
		new NormalMoveStrategy(),
		new CaptureMoveStrategy(),
		new EnPassantMoveStrategy(),
		new ShortCastlingMoveStrategy(),
		new LongCastlingMoveStrategy()
	};

	public static MoveStrategy createMoveStrategy(BoardModel board, Move move) {
		for (MoveStrategy strategy : strategies) {
			if (strategy.isValidMove(board, move)) {
				return strategy;
			}
		}		

		throw new UnsupportedOperationException("Could not find move strategy for move.");
	}
}
