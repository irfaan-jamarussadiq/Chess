package src;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.board.*;
import src.pieces.Piece;

public class GameController implements EventHandler<MouseEvent> {
    private final BoardView boardView;
    private final BoardModel boardModel;
    private final Game game;
    private final MoveValidator validator;
    private final ObservableList<MoveText> moves;
    private final ListView<MoveText> moveListView;
    private final Text statusText;
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

        statusText = new Text();
        dialog = createPopup(statusText);
    }

    void updateGameText() {
        GameState state = game.getGameState();
        if (state == GameState.ONGOING) {
            statusText.setText("Game is still ongoing.");
        } else if (state == GameState.WHITE_WON) {
            statusText.setText("White won!");
        } else if (state == GameState.BLACK_WON) {
            statusText.setText("Black won!");
        } else {
            statusText.setText("Game ended in a draw.");
        }
    }

    Stage createPopup(Text statusText) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);

        VBox box = new VBox();
        statusText.setTextAlignment(TextAlignment.CENTER);
        Button button = new Button("Play again");
        button.setOnAction(e -> dialog.close());
        box.getChildren().addAll(statusText, button);
        box.setAlignment(Pos.CENTER);

        StackPane root = new StackPane();
        root.getChildren().add(box);

        Scene dialogScene = new Scene(root, 200, 100);
        dialog.setScene(dialogScene);
        return dialog;
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