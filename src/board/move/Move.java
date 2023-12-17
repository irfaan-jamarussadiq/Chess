package move;

abstract class Move {
	private int startRank, startFile, endRank, endFile;

	Move(int startRank, int startFile, int endRank, int endFile) {
		if (!withinBoard(startRank, startFile, endRank, endFile)) {
			throw new MoveOutsideBoardException(startRank, startFile, endRank, endFile);
		}

		this.startRank = startRank;
		this.startFile = startFile;
		this.endRank = endRank;
		this.endFile = endFile;
	}

	private static boolean withinBoard(int startRank, int startFile, int endRank, int endFile) {
		return startRank >= 1 && startRank <= Board.SIZE
			&& startFile >= 1 && startFile <= Board.SIZE
			&& endRank >= 1 && endRank <= Board.SIZE
			&& endFile >= 1 && endFile <= Board.SIZE; 
	}

	abstract boolean isValid(Board board);
	abstract void execute(Board board);
}
