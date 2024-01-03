package src;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import src.board.BoardModel;
import src.board.Game;
import src.board.Move;
import src.board.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class GameController implements EventHandler<MouseEvent> {
    private final GameView gameView;
    private final BoardModel boardModel;
    private final Game game;
    private Piece previousPiece;
    private List<Move> previousMoves = new ArrayList<>();
    private List<Move> currentMoves = new ArrayList<>();

    public GameController() {
        boardModel = new BoardModel();
        game = new Game(boardModel);
        gameView = new GameView(boardModel);
        gameView.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
    }

    public GameView getGameView() {
        return gameView;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        int rank = BoardModel.SIZE - (int) mouseEvent.getY() / Tile.SQUARE_SIZE;
        int file = (int) mouseEvent.getX() / Tile.SQUARE_SIZE + 1;

        if (rank < 1 || file < 1 || rank > BoardModel.SIZE || file > BoardModel.SIZE) {
            return;
        }

        Piece currrentPiece = boardModel.pieceAt(rank, file);

        for (Move move : previousMoves) {
            gameView.deselect(move.endRank(), move.endFile());
        }

        if (previousPiece != currrentPiece) {
            currentMoves = game.getValidMoves(currrentPiece, rank, file);

            for (Move move : currentMoves) {
                gameView.highlightSquare(move.endRank(), move.endFile());
            }

            previousMoves = new ArrayList<>(currentMoves);
            previousPiece = currrentPiece;
        } else {
            previousPiece = null;
        }
    }
}