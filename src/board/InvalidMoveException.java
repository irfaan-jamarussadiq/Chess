package src.board;

public class InvalidMoveException extends IllegalArgumentException {
	protected int startRank, startFile, endRank, endFile;

	InvalidMoveException(int startRank, int startFile, int endRank, int endFile) {
		this.startRank = startRank;
		this.startFile = startFile;
		this.endRank = endRank;	
		this.endFile = endFile;
	}

	InvalidMoveException(Move move) {
		this.startRank = move.startRank();
		this.startFile = move.startFile();
		this.endRank = move.endRank();
		this.endFile = move.endFile();
	}

	@Override
	public String getMessage() {
		String messageFormat = "Cannot move from (%d, %d) to (%d, %d)"; 
		return String.format(messageFormat, startRank, startFile, endRank, endFile);
	}
}
