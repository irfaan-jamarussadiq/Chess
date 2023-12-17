package gui;

import board.BoardController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
        StackPane root = new StackPane();        
        root.setStyle("-fx-background-color: white");
    
        VBox menu = createMenuScreen();
        root.getChildren().add(menu);       

        Scene menuScreen = new Scene(root, WIDTH, HEIGHT);
        window.setScene(menuScreen);
        window.setTitle("Chess Program");
        window.show();
    }

    private VBox createMenuScreen() {
        VBox menu = new VBox();

        // Create menu title and add it to the menu container.
        Text menuTitle = new Text();      
        menuTitle.setText("Chess");
        menuTitle.setStyle("-fx-font-size:60; ");
        menu.getChildren().add(menuTitle);

        // Create menu buttons.
        Button play = createMenuButton("PLAY");
        Button settings = createMenuButton("SETTINGS");
        Button about = createMenuButton("ABOUT");

        menu.setSpacing(10);
        menu.getChildren().addAll(play, settings, about);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(0, 0, -HEIGHT/8, 0));
        return menu;
    }

    private Button createMenuButton(String name) {
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

        // Change screen appropriately when button is clicked
        if (name == "PLAY") {
            Pane boardPane = createBoardScreen();
            button.setOnAction(e -> button.getScene().setRoot(boardPane));
        } else if (name == "SETTINGS") {
            Pane settingsPane = createSettingsScreen();
            button.setOnAction(e -> button.getScene().setRoot(settingsPane));
        } else if (name == "ABOUT") {
            Pane aboutPane = createAboutScreen();
            button.setOnAction(e -> button.getScene().setRoot(aboutPane));
        }

        return button;
    }

    private Pane createBoardScreen() {
        Pane root = new Pane();
        BoardController controller = new BoardController();
        root.getChildren().add(controller.getView());
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, controller);
        return root;        
    }

    // TODO: Implement settings scene
    private Pane createSettingsScreen() {
        Pane root = new Pane();

        // Make back button to main menu.
        Button back = createBackButton();
        // When back button is clicked, return to the screen specified by root.
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                back.getScene().setRoot(createMenuScreen());             
            }
        });        

        // Create light and dark squares color selection for chessboard.
        Label darkLabel = new Label("Dark Color: ");
        Label lightLabel = new Label("Light Color: ");
        darkLabel.setStyle("-fx-font-size:20;");
        lightLabel.setStyle("-fx-font-size:20;");

        // TODO: Consider making a custom class for color swatch list
        ColorPicker light = new ColorPicker();
        ColorPicker dark = new ColorPicker();

        VBox colors = new VBox();
        colors.setSpacing(20);
        colors.setPadding(new Insets(70, 50, 50, 60));
        colors.getChildren().addAll(darkLabel, light, lightLabel, dark);

        root.getChildren().addAll(colors, back);        
        return root;
    }

    private Button createBackButton() {
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
               
        return back;
    }

    private Pane createAboutScreen() {
        Pane root = new Pane();
        Button back = createBackButton();
        // When back button is clicked, return to the screen specified by root.
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                back.getScene().setRoot(createMenuScreen());             
            }
        });         

        // Insert program description and author.
        Text description = new Text();        
        String label = 
            "Human vs. human chess program.\n" +
            "Made by Irfaan Jamarussadiq, © 2021";

        description.setText(label);
        description.setWrappingWidth(WIDTH);
        description.setTextAlignment(TextAlignment.CENTER);
        description.setTextOrigin(VPos.TOP);
        description.setStyle("-fx-font-size:20;");

        root.getChildren().addAll(description, back);        
        return root;        
    }
    
    public static void main(String[] args) {
		launch(args);
	}
}