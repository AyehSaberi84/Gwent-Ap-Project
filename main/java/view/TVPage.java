package view;

import controller.ProfileController;
import controller.SceneController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Game;

import java.util.Objects;

public class TVPage extends Application {
    private final double WIDTH = 1000;
    private final double HEIGHT = 600;
    private final double SPACE = 15;
    private final double WSpace = 50;
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        setSize(pane);
        pane.setBackground(new Background(createBackgroundImage()));
        makeView(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private BackgroundImage createBackgroundImage() {
        Image image = new Image(Objects.requireNonNull(Game.class.getResource("/Images/BG/TVBG.jpg")).toExternalForm(), WIDTH, HEIGHT, false, false);
        return new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }

    private void setSize(Pane pane) {
        pane.setMaxWidth(WIDTH);
        pane.setMinWidth(WIDTH);
        pane.setMaxHeight(HEIGHT);
        pane.setMinHeight(HEIGHT);
    }
    private void makeView (Pane pane){
        makeLabel(pane);
        makeAllButtons(pane);
    }

    private void makeLabel(Pane pane) {
        Label showMenu = new Label("Welcome To The TV Menu!");
        showMenu.setFont(Font.font("Freestyle Script", 40));
        showMenu.setTextFill(Color.WHITE);
        showMenu.setLayoutX(WSpace);
        showMenu.setLayoutY(SPACE);
        pane.getChildren().add(showMenu);
    }

    private void makeAllButtons (Pane pane){
        Button live = makeButton("Live",5,0);
        live.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LivePage livePage = new LivePage();
                try {
                    livePage.start(Main.stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        pane.getChildren().add(live);
        Button pastGames = makeButton("past games",10,0);
        pastGames.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PastGamesPage pastGamesPage = new PastGamesPage();
                try {
                    pastGamesPage.start(Main.stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        pane.getChildren().add(pastGames);


        Button exit = makeButton("Exit",29,90);
        exit.setOnAction(event -> ProfileController.goToMainMenu());
        pane.getChildren().add(exit);

    }
    private Button makeButton(String name, double SPACENum, double setX) {
        Button button = new Button(name);
        button.setFont(Font.font("Times New Roman", 20));
        button.setStyle("-fx-background-color: #c9cec9; -fx-text-fill: #2d1902");
        button.setPrefSize(250, 30);
        button.setLayoutX(100 + setX);
        button.setLayoutY(SPACE * SPACENum);
        return button;
    }
}
