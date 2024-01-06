package src.board;

public record Location(int rank, int file) {
	public static boolean isWithinBounds(int rank, int file) {
		return rank >= 1 && rank <= BoardModel.SIZE && file >= 1 && file <= BoardModel.SIZE;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other || other.getClass() != Location.class) {
			return false;
		}

		Location otherLocation = (Location) other;
		return rank == otherLocation.rank && file == otherLocation.file;
	}

	@Override
	public String toString() {
		return String.format("(%d, %d)", rank, file);
	}
}

