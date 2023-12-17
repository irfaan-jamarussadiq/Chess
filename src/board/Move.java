package board; 

class Move {
	private int startRank, startFile, endRank, endFile;

	Move(int startRank, int startFile, int endRank, int endFile) {
		this.startRank = startRank;
		this.startFile = startFile;
		this.endRank = endRank;
		this.endFile = endFile;
	}

	int getStartRank() {
		return startRank;
	}

	int getStartFile() {
		return startFile;
	}

	int getEndRank() {
		return endRank;
	}

	int getEndFile() {
		return endFile;
	}

}
