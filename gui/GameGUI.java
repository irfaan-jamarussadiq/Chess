package gui;

import board.BoardController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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

        setMenuActions(play, settings, about);

        VBox buttons = new VBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(play, settings, about);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(0, 0, -HEIGHT/8, 0));
        return buttons;
    }

    private static void setMenuActions(Button ... buttons) {
        Button play = buttons[0];
        Button settings = buttons[1];
        Button about = buttons[2];

        Pane playPane = getPlayPane();
        Pane settingsPane = getSettingsPane();
        Pane aboutPane = getAboutPane();

        play.setOnAction(e -> play.getScene().setRoot(playPane));
        settings.setOnAction(e -> settings.getScene().setRoot(settingsPane));
        about.setOnAction(e -> about.getScene().setRoot(aboutPane));
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
            "-fx-effect:" + 
                "dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);";

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
        back.setStyle( 
            "-fx-font-size:15;" +
            "-fx-background-radius: 15px;" +
            "-fx-background-color:" +
                "linear-gradient(#f2f2f2, #d6d6d6)," +
                "linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%)," +
                "linear-gradient(#dddddd 0%, #f6f6f6 50%);" +
            "-fx-background-radius: 8,7,6;" +
            "-fx-background-insets: 0,1,2;" +
            "-fx-text-fill: black;" +
            "-fx-effect:" + 
                "dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);"
        );
        back.setPrefWidth(75);
        back.setPrefHeight(25);
        back.setTranslateX(20);
        back.setTranslateY(20);

        // why is this not working now?
        back.setOnAction(e -> back.getScene().setRoot(getMenuPane()));

        Label darkLabel = new Label("Dark Color: ");
        Label lightLabel = new Label("Light Color: ");
        darkLabel.setStyle("-fx-font-size:20;");
        lightLabel.setStyle("-fx-font-size:20;");

        ColorPicker light = new ColorPicker();
        ColorPicker dark = new ColorPicker();

        VBox colors = new VBox();
        colors.setSpacing(20);
        colors.setPadding(new Insets(70, 50, 50, 60));
        colors.getChildren().addAll(darkLabel, light, lightLabel, dark);
        
        root.getChildren().addAll(back, colors);

        return root;
    }

    private static Pane getAboutPane() {
        Pane root = new Pane();
        Button back = new Button("Back");
        back.setStyle(
            "-fx-font-size:15;" +
            "-fx-background-radius: 15px;" +
            "-fx-background-color:" +
                "linear-gradient(#f2f2f2, #d6d6d6)," +
                "linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%)," +
                "linear-gradient(#dddddd 0%, #f6f6f6 50%);" +
            "-fx-background-radius: 8,7,6;" +
            "-fx-background-insets: 0,1,2;" +
            "-fx-text-fill: black;" +
            "-fx-effect:" + 
                "dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);"
        );
        back.setPrefWidth(75);
        back.setPrefHeight(25);
        back.setTranslateX(20);
        back.setOnAction(e -> back.getScene().setRoot(getMenuPane()));

        Text description = new Text();
        String label = 
            "This is a human versus human player chess program.\n" +
            "It was made in Java FX by Irfaan Jamarussadiq.";

        description.setText(label);
        description.setWrappingWidth(WIDTH);
        description.setTextAlignment(TextAlignment.CENTER);
        description.setStyle("-fx-font-size:20;");

        VBox layout = new VBox();
        layout.getChildren().addAll(back, description);
        layout.setPadding(new Insets(20, 0, 20, 0));
        root.getChildren().add(layout);
        return root;
    }
    
    
    public static void main(String[] args) {
		launch(args);
	}
}