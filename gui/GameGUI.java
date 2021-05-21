package gui;

import board.BoardController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameGUI extends Application {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 640;

    @Override
    public void start(Stage window) throws Exception {
        Scene menu = new Scene(getMenuPane(), WIDTH, HEIGHT);
        window.setScene(menu);
        window.setTitle("Chess Program");
        window.show();
    }

    private static StackPane getMenuPane() {
        StackPane root = new StackPane();
        StackPane background = getMenuBackground();
        VBox menu = getMenu();

        root.getChildren().add(background);
        root.getChildren().add(menu);

        return root;
    }

    private static StackPane getMenuBackground() {
        StackPane holder = new StackPane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);

        holder.getChildren().add(canvas);
        holder.setStyle("-fx-background-color: white");
        return holder;
    }

    private static VBox getMenu() {
        VBox menu = new VBox();
        menu.getChildren().add(getTitle());
        menu.getChildren().add(getMenuButtons());
        menu.setAlignment(Pos.BASELINE_CENTER);
        menu.setPadding(new Insets(HEIGHT/8, 0, 0, 0));
        return menu;
    }

    private static Text getTitle() {
        Text text = new Text();      
        text.setText("Chess");
        text.setStyle("-fx-font-size:60;");
        return text;
    }

    private static VBox getMenuButtons() {
        Button play = getMenuButton("PLAY");
        Button settings = getMenuButton("SETTINGS");
        Button about = getMenuButton("ABOUT");

        Pane playPane = getPlayPane();
        play.setOnAction(e -> play.getScene().setRoot(playPane));

        Pane settingsPane = getSettingsPane();
        settings.setOnAction(e -> settings.getScene().setRoot(settingsPane));

        Pane aboutPane = getAboutPane();
        about.setOnAction(e -> about.getScene().setRoot(aboutPane));

        VBox buttons = new VBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(play, settings, about);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(0, 0, -HEIGHT/8, 0));
        return buttons;
    }

    private static Button getMenuButton(String name) {
        Button button = new Button(name);
        button.setPrefWidth(WIDTH/2);
        button.setPrefHeight(HEIGHT/10);

        String buttonStyle = 
            "-fx-font-size:30;" +
            "-fx-background-radius: 15px;" +
            "-fx-background-color:" +
                "linear-gradient(#f2f2f2, #d6d6d6)," +
                "linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%)," +
                "linear-gradient(#dddddd 0%, #f6f6f6 50%);" +
            "-fx-background-radius: 8,7,6;" +
            "-fx-background-insets: 0,1,2;" +
            "-fx-text-fill: black;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);";

        button.setStyle(buttonStyle);
        return button;
    }

    private static Pane getPlayPane() {
        BoardController controller = new BoardController();
    
        Pane root = new Pane();
        root.getChildren().add(controller.getView());
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, controller);

        return root;
    }

    // TODO: Implement settings scene
    private static Pane getSettingsPane() {
        Pane root = new Pane();
        Button back = new Button("Back");
        back.setPrefWidth(100);
        back.setPrefHeight(50);
        
        // menu is somehow null?
        back.setOnAction(e -> back.getScene().setRoot(getMenuPane()));
        
        root.getChildren().add(back);

        return root;
    }

    // TODO: Implement about scene
    private static Pane getAboutPane() {
        Pane root = new Pane();
        Button back = new Button("Back");
        back.setPrefWidth(100);
        back.setPrefHeight(50);
        
        // menu is somehow null?
        back.setOnAction(e -> back.getScene().setRoot(getMenuPane()));
        
        root.getChildren().add(back);

        return root;
    }
    
    
    public static void main(String[] args) {
		launch(args);
	}
}