package src;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import src.board.pieces.Piece;
import src.board.pieces.PieceColor;

public class Tile extends StackPane {
    private static final Color HIGHLIGHT_COLOR = Color.rgb(144, 238, 214);
    public static final int SQUARE_SIZE = 100;
    private static final Color LIGHT_SQUARE = Color.rgb(157, 172, 255);
    private static final Color DARK_SQUARE = Color.rgb(111, 115, 210);
    private final Rectangle square;
    private final Color defaultColor;

    public Tile(int rank, int file, Piece piece) {
        this.defaultColor = (rank + file) % 2 == 0 ? LIGHT_SQUARE : DARK_SQUARE;
        square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, defaultColor);
        square.setX(rank * SQUARE_SIZE);
        square.setY(file * SQUARE_SIZE);
        this.getChildren().add(square);

        if (piece != null) {
            Image image = new Image(getPiecePath(piece));
            ImageView pieceImage = new ImageView(image);
            pieceImage.setFitWidth(SQUARE_SIZE);
            pieceImage.setFitHeight(SQUARE_SIZE);
            this.getChildren().add(pieceImage);
        }
    }

    private String getPiecePath(Piece piece) {
        String type = piece.getType().toString().toLowerCase();
        String colorNumber = piece.getColor() == PieceColor.WHITE ? "" : "2";
        return String.format("file:assets/%s%s.png", type, colorNumber);
    }

    public void highlightSquare() {
        square.setFill(HIGHLIGHT_COLOR);
    }

    public void deselect() {
        square.setFill(defaultColor);
    }
}
