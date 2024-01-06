package src;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import src.pieces.Piece;
import src.pieces.PieceColor;

public class TileView extends StackPane {
    private static final Color HIGHLIGHT_COLOR = Color.rgb(144, 238, 214);
    public static final int SQUARE_SIZE = 100;
    private static final Color LIGHT_SQUARE = Color.rgb(157, 172, 255);
    private static final Color DARK_SQUARE = Color.rgb(111, 115, 210);
    private final Rectangle square;
    private final ImageView pieceView;
    private final Color defaultColor;

    private Piece piece;

    public TileView(int rank, int file, Piece piece) {
        this.defaultColor = (rank + file) % 2 == 0 ? LIGHT_SQUARE : DARK_SQUARE;
        this.piece = piece;

        square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, defaultColor);
        square.setX(rank * SQUARE_SIZE);
        square.setY(file * SQUARE_SIZE);

        pieceView = new ImageView(getPieceImage(piece));
        pieceView.setFitWidth(SQUARE_SIZE);
        pieceView.setFitHeight(SQUARE_SIZE);

        this.getChildren().addAll(square, pieceView);
    }

    public void highlightSquare() {
        square.setFill(HIGHLIGHT_COLOR);
    }

    public void deselect() {
        square.setFill(defaultColor);
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        pieceView.setImage(getPieceImage(piece));
    }

    private Image getPieceImage(Piece piece) {
        if (piece == null) {
            return null;
        }

        String type = piece.getType().toString().toLowerCase();
        String colorNumber = piece.getColor() == PieceColor.WHITE ? "" : "2";
        String piecePath = String.format("file:assets/%s%s.png", type, colorNumber);
        return new Image(piecePath);
    }

    public Piece getPiece() {
        return piece;
    }
}
