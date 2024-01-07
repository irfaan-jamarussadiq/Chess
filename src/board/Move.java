package src.board;

public record Move(int startRank, int startFile, int endRank, int endFile) {
	@Override
	public String toString() {
        return (char)('a' + endFile - 1) + "" + endRank;
	}
}
