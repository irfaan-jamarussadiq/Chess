package src;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import src.board.BoardModel;
import src.board.pieces.Piece;

public class GameView extends GridPane {
    private Tile[][] boardSquares;

    public GameView(BoardModel boardModel) {
        int boardSize = BoardModel.SIZE;
        boardSquares = new Tile[boardSize][boardSize];
        for (int rank = 1; rank <= boardSize; rank++) {
            for (int file = 1; file <= boardSize; file++) {
                // TODO: When a square's color is set, how should GridPane update?
                Piece piece = boardModel.pieceAt(rank, file);
                boardSquares[rank - 1][file - 1] = new Tile(rank, file, piece);
                this.add(boardSquares[rank - 1][file - 1], file, BoardModel.SIZE - rank);
            }
        }
    }

    public void highlightSquare(int rank, int file) {
        boardSquares[rank - 1][file - 1].highlightSquare();
    }

    public void deselect(int rank, int file) {
        boardSquares[rank - 1][file - 1].deselect();
    }
}
