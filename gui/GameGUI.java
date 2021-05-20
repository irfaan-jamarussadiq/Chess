package gui;

import board.BoardController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameGUI extends Application {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 640;

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        StackPane background = createBackground();
        VBox buttons = createButtonLayout(primaryStage);

        root.getChildren().add(background);
        root.getChildren().add(buttons);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static Scene getGameScene() {
        BoardController controller = new BoardController();
    
        Pane root = new Pane();
        root.getChildren().add(controller.getView());
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, controller);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        return scene;
    }

    private static StackPane createBackground() {
        StackPane holder = new StackPane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);

        holder.getChildren().add(canvas);
        holder.setStyle("-fx-background-color: black");
        return holder;
    }

    private static Button createButton(String name) {
        Button button = new Button(name);
        button.setPrefWidth(400);
        button.setPrefHeight(100);

        String buttonStyle = 
            "-fx-font-size:30;" +
            "-fx-font-weight: bold;" + 
            "-fx-background-radius: 15px;";

        button.setStyle(buttonStyle);
        return button;
    }

    private static VBox createButtonLayout(Stage primaryStage) {
        Button play = createButton("PLAY");
        Button settings = createButton("SETTINGS");
        Button about = createButton("ABOUT");

        play.setOnAction(e -> primaryStage.setScene(getGameScene()));

        VBox buttons = new VBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(play, settings, about);
        buttons.setAlignment(Pos.CENTER);

        return buttons;
    }
    
    
    public static void main(String[] args) {
		launch(args);
	}
}
