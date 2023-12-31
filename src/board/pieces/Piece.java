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
		char character = ' ';
		switch(type) {
			case PAWN:
				character = 'p';
				break;
			case ROOK:
				character = 'r';
				break;
			case KNIGHT:
				character = 'n';
				break;
			case BISHOP:
				character = 'b';
				break;
			case QUEEN:
				character = 'q';
				break;
			case KING:
				character = 'k';
				break;
			default:
				break;
		}
		
		return (color == PieceColor.WHITE) ? character : Character.toUpperCase(character);
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
