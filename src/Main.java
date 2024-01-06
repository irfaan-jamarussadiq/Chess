package src;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        GameController controller = new GameController();

        BorderPane layout = new BorderPane();
        BoardView boardView = controller.getBoardView();
        layout.setCenter(boardView);
        BorderPane.setMargin(boardView, new Insets(20, 20, 20, 20));

        Scene scene = new Scene(layout, 1000, 1000);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
