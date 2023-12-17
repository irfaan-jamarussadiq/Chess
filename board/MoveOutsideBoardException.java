package board;

class MoveOutsideBoardException extends IllegalArgumentException {
	private int startRank, startFile, endRank, endFile;

	MoveOutsideBoardException(int startRank, int startFile, int endRank, int endFile) {
		this.startRank = startRank;
		this.startFile = startFile;
		this.endRank = endRank;	
		this.endFile = endFile;
	}

	@Override
	public String getMessage() {
		String messageFormat = "Move from (%d, %d) to (%d, %d) was out of bounds"; 
		return String.format(messageFormat, startRank, startFile, endRank, endFile);
	}
}
