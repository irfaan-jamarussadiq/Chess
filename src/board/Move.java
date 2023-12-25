package board; 

import java.util.Objects;

import pieces.Piece;

public class Move {
	private int startRank, startFile, endRank, endFile;
	private Piece capturedPiece;

	public Move(int startRank, int startFile, int endRank, int endFile) {
		this.startRank = startRank;
		this.startFile = startFile;
		this.endRank = endRank;
		this.endFile = endFile;
	}

	public int getStartRank() {
		return startRank;
	}

	public int getStartFile() {
		return startFile;
	}

	public int getEndRank() {
		return endRank;
	}

	public int getEndFile() {
		return endFile;
	}

	public Piece getCapturedPiece() {
		return capturedPiece;
	}

	public void setCapturedPiece(Piece piece) {
		this.capturedPiece = piece;
	}

	@Override
	public String toString() {
		return String.format("(%d, %d) to (%d, %d)", startRank, startFile, endRank, endFile);
	}

	@Override
	public boolean equals(Object other) {
		if (this == null || other.getClass() != Move.class) {
			return false;
		}

		Move otherMove = (Move) other;
		return startRank == otherMove.startRank && startFile == otherMove.startFile 
			&& endRank == otherMove.endRank && endFile == otherMove.endFile;
	}

	@Override
	public int hashCode() {
		return Objects.hash(startRank, startFile, endRank, endFile);
	}

}
