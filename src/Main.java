package src;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import src.board.BoardModel;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        BoardModel board = new BoardModel();
        BorderPane gameView = createGameView(board);
        Scene scene = new Scene(gameView, 1000, 1000);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }

    private BorderPane createGameView(BoardModel board) {
        BorderPane gameView = new BorderPane();
        GridPane boardView = new BoardView(board);
        gameView.setCenter(boardView);
        BorderPane.setMargin(boardView, new Insets(20, 20, 20, 20));
        return gameView;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
