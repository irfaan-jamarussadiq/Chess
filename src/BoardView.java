package src;

import javafx.scene.layout.GridPane;
import src.board.BoardModel;
import src.pieces.Piece;

public class BoardView extends GridPane {
    private final TileView[][] boardSquares;

    public BoardView(BoardModel boardModel) {
        int boardSize = BoardModel.SIZE;
        boardSquares = new TileView[boardSize][boardSize];
        createBoardView(boardModel);
    }

    void createBoardView(BoardModel boardModel) {
        int boardSize = BoardModel.SIZE;
        for (int rank = 1; rank <= boardSize; rank++) {
            for (int file = 1; file <= boardSize; file++) {
                Piece piece = boardModel.pieceAt(rank, file);
                boardSquares[rank - 1][file - 1] = new TileView(rank, file, piece);
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

    public void showKingIsInCheck(int kingRank, int kingFile) {
        boardSquares[kingRank - 1][kingFile - 1].showKingIsInCheck();
    }
}
