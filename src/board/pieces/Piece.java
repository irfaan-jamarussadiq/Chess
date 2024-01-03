package src.board.pieces;

import java.util.Objects;

public class Piece {
	private final PieceType type;
	private final PieceColor color;
	private boolean hasMoved;

	public Piece(PieceType type, PieceColor color) {
		this.type = type;
		this.color = color;
		this.hasMoved = false;
	}

	public boolean isEnemyOf(Piece piece) {
		return piece != null && color != piece.color;
	}

	public PieceType getType() {
		return type;
	}

	public PieceColor getColor() {
		return color;
	}

	public boolean hasMoved() {
		return hasMoved;
	}

	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	public char getBoardCharacter() {
		char character = '\u0000';
		switch(type) {
			case ROOK:
				character = 'R';
				break;
			case KNIGHT:
				character = 'N';
				break;
			case BISHOP:
				character = 'B';
				break;
			case QUEEN:
				character = 'Q';
				break;
			case KING:
				character = 'K';
				break;
			default:
				break;
		}
		
		return character;
	}		

	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != Piece.class) {
			return false;
		}

		Piece otherPiece = (Piece) other;
		return type == otherPiece.type && color == otherPiece.color;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, color);
	}

	@Override
	public String toString() {
		return type + " " + color; 
	}
		
}
