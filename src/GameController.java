package src;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.board.*;
import src.pieces.Piece;
import src.pieces.PieceColor;

public class GameController implements EventHandler<MouseEvent> {
    private final BoardView boardView;
    private final BoardModel boardModel;
    private final Game game;
    private final MoveValidator validator;
    private final ObservableList<MoveText> moves;
    private final ListView<MoveText> moveListView;
    private final Text currentPlayerText, statusText;
    private final Stage dialog;
    private Location pieceToMoveLocation;

    public GameController() {
        boardModel = new BoardModel();
        boardView = new BoardView(boardModel);
        boardView.addEventHandler(MouseEvent.MOUSE_CLICKED, this);

        game = new Game(boardModel);
        validator = new MoveValidator(boardModel);
        moves = FXCollections.observableArrayList();
        moveListView = new ListView<>();
        currentPlayerText = new Text();
        statusText = new Text();

        this.dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        Pane root = new VBox();
        root.getChildren().addAll(currentPlayerText, statusText);
        Scene dialogScene = new Scene(root, 400, 400);
        dialog.setScene(dialogScene);
    }

    void updateGameText() {
        currentPlayerText.setText(String.format("Current player: %s", game.getCurrentPlayer().getColor()));
        statusText.setText(String.format("Game status: %s", game.getGameState()));
    }

    public BoardView getBoardView() {
        return boardView;
    }

    public ListView<MoveText> getMoveListView() { return moveListView; }

    public Stage getDialog() {
        return dialog;
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

            Move move = new Move(pieceRank, pieceFile, rank, file);
            if (validator.isValidMove(game.getCurrentPlayer(), move)) {
                moves.add(new MoveText(pieceToMove, move));
                moveListView.setItems(moves);
                game.playMove(pieceRank, pieceFile, rank, file);
                boardView.createBoardView(boardModel);
                updateGameText();
            }

            if (game.currentPlayerIsInCheck()) {
                Location kingLocation = game.getCurrentPlayer().getKingLocation();
                boardView.showKingIsInCheck(kingLocation.rank(), kingLocation.file());
            }

            if (game.playerIsInCheckmate(game.getCurrentPlayer().getColor())) {
                dialog.show();
            }

            pieceToMoveLocation = null;
        }

    }
}