package src;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import src.board.BoardModel;
import src.board.pieces.Piece;
import src.board.pieces.PieceColor;

public class BoardView extends GridPane {
    private static final int SQUARE_SIZE = 100;
    private static final Color LIGHT_SQUARE = Color.rgb(157, 172, 255);
    private static final Color DARK_SQUARE = Color.rgb(111, 115, 210);

    public BoardView(BoardModel board) {
        for (int rank = 1; rank <= BoardModel.SIZE; rank++) {
            for (int file = 1; file <= BoardModel.SIZE; file++) {
                Color color = (rank + file) % 2 == 0 ? LIGHT_SQUARE : DARK_SQUARE;
                Piece piece = board.pieceAt(rank, file);
                StackPane square = createSquare(color, piece);
                this.add(square, file, BoardModel.SIZE - rank);
            }
        }

        this.setGridLinesVisible(true);
    }

    private StackPane createSquare(Color color, Piece piece) {
        StackPane square = new StackPane();
        Rectangle r = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
        r.setFill(color);
        square.getChildren().add(r);

        if (piece != null) {
            String type = piece.getType().toString().toLowerCase();
            String colorNumber = piece.getColor() == PieceColor.WHITE ? "" : "2";
            String piecePath = "file:assets/" + type + colorNumber + ".png";
            Image image = new Image(piecePath);
            ImageView pieceView = new ImageView(image);
            pieceView.setFitWidth(SQUARE_SIZE);
            pieceView.setFitHeight(SQUARE_SIZE);
            square.getChildren().add(pieceView);
        }

        return square;
    }

}
