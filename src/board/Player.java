package board;

import pieces.PieceColor;

public class Player {
	private Location kingLocation;
	private PieceColor color;

	public Player(PieceColor color) {
		this.color = color;
		this.kingLocation = new Location(color.getPieceStartingRank(), 5);
	}

	public Location getKingLocation() {
		return kingLocation;
	}

	public void setKingLocation(int rank, int file) {
		this.kingLocation = new Location(rank, file);
	}

	public PieceColor getColor() {
		return color;
	}

}		
