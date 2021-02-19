package gui;

import board.BoardController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameGUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BoardController controller = new BoardController();
    
        Pane root = new Pane();
        root.getChildren().add(controller.getView());
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, controller);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
		launch(args);
	}
}
