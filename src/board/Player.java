package board;

import board.BoardModel; 
import pieces.PieceColor;

public class Player {

	private int kingRank;
	private int kingFile;
	private PieceColor color;
	private boolean kingHasMoved;

	public Player(PieceColor color) {
		kingRank = (color == PieceColor.WHITE) ? 1 : BoardModel.SIZE;
		kingFile = 5;
		kingHasMoved = false;
	}

	public int getKingRank() {
		return kingRank;
	}

	public int getKingFile() {
		return kingFile;
	}

	public boolean getKingHasMoved() {
		return kingHasMoved;
	}
}		
