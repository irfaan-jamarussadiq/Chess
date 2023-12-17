package board;

class InvalidMoveException extends IllegalArgumentException {
	protected int startRank, startFile, endRank, endFile;

	InvalidMoveException(int startRank, int startFile, int endRank, int endFile) {
		this.startRank = startRank;
		this.startFile = startFile;
		this.endRank = endRank;	
		this.endFile = endFile;
	}

	@Override
	public String getMessage() {
		String messageFormat = "Cannot move from (%d, %d) to (%d, %d)"; 
		return String.format(messageFormat, startRank, startFile, endRank, endFile);
	}
}
