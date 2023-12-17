package board;

class MoveOutsideBoardException extends InvalidMoveException {
	MoveOutsideBoardException(int startRank, int startFile, int endRank, int endFile) {
		super(startRank, startFile, endRank, endFile);
	}

	@Override
	public String getMessage() {
		String messageFormat = "Move from (%d, %d) to (%d, %d) was out of bounds"; 
		return String.format(messageFormat, super.startRank, super.startFile, super.endRank, super.endFile);
	}
}
