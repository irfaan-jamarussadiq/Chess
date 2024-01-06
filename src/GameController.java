package src;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import src.board.*;
import src.pieces.Piece;

public class GameController implements EventHandler<MouseEvent> {
    private final BoardView boardView;
    private final BoardModel boardModel;
    private final Game game;
    private Location pieceToMoveLocation;

    public GameController() {
        boardModel = new BoardModel();
        boardView = new BoardView(boardModel);
        boardView.addEventHandler(MouseEvent.MOUSE_CLICKED, this);

        game = new Game(boardModel);
    }

    public BoardView getBoardView() {
        return boardView;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        int rank = BoardModel.SIZE - (int) mouseEvent.getY() / TileView.SQUARE_SIZE;
        int file = (int) mouseEvent.getX() / TileView.SQUARE_SIZE + 1;

        if (!Location.isWithinBounds(rank, file)) {
            return;
        }

        if (pieceToMoveLocation == null && boardModel.pieceAt(rank, file) == null) {
            return;
        }

        if (pieceToMoveLocation == null) {
            // When a piece is clicked at (rank, file), highlight the moves of that piece.
            pieceToMoveLocation = new Location(rank, file);
            Piece pieceToMove = boardModel.pieceAt(rank, file);
            for (Move move : game.getValidMoves(pieceToMove, rank, file)) {
                boardView.highlightSquare(move.endRank(), move.endFile());
            }
        } else {
            // When a square is clicked among the moves for that piece, move the piece previously clicked to that location.
            int pieceRank = pieceToMoveLocation.rank();
            int pieceFile = pieceToMoveLocation.file();
            Piece pieceToMove = boardModel.pieceAt(pieceRank, pieceFile);
            for (Move move : game.getValidMoves(pieceToMove, pieceRank, pieceFile)) {
                boardView.deselect(move.endRank(), move.endFile());
            }

            game.playMove(pieceRank, pieceFile, rank, file);
            boardView.createBoardView(boardModel);
            pieceToMoveLocation = null;
        }

    }
}