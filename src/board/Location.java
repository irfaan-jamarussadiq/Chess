package board;

public class Location {
	private int rank;
	private int file;

	public Location(int rank, int file) {
		this.rank = rank;
		this.file = file;
	}

	public int getRank() {
		return rank;
	}

	public int getFile() {
		return file;
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

