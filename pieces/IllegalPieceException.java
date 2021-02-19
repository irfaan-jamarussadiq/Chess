package pieces;

public class IllegalPieceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public IllegalPieceException(String string) {
		super(string);
	}
}
