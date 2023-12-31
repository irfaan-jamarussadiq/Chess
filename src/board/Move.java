package src.board;

public record Move(int startRank, int startFile, int endRank, int endFile) {
	@Override
	public String toString() {
		return String.format("(%d, %d) to (%d, %d)", startRank, startFile, endRank, endFile);
	}
}
