package view;

import controller.SceneController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Game;

import java.util.Objects;


public class ResultMenu extends Application {

    private final double WIDTH = 1000;
    private final double HEIGHT = 600;

    private String[] playerNames;

    public ResultMenu(String[] playerNames) {
        this.playerNames = playerNames;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        setSize(pane, stage);
        pane.setBackground(new Background(createBackgroundImage()));
        makeView(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void setSize(Pane pane, Stage stage) {
        pane.setMaxWidth(WIDTH);
        pane.setMinWidth(WIDTH);
        pane.setMaxHeight(HEIGHT);
        pane.setMinHeight(HEIGHT);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setResizable(false);
    }

    private BackgroundImage createBackgroundImage() {
        Image image = new Image(Objects.requireNonNull(Game.class.getResource("/Images/BG/EmptyBG.jpg")).toExternalForm(), WIDTH, HEIGHT, false, false);
        return new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }

    private void makeView(Pane pane) {
        for (int i = 1; i <= 5; i++) {
            makeLabel(pane, i);
        }
        Button exit = new Button("Exit");
        exit.setLayoutX(480);
        exit.setLayoutY(500);
        exit.setOnMouseClicked(event -> SceneController.goToMainMenu());
        pane.getChildren().add(exit);
    }

    private void makeLabel(Pane pane, int i) {
        Label label = new Label(i + ". " + playerNames[i]);
        label.setTextFill(Color.YELLOW);
        label.setLayoutX(480);
        label.setLayoutY(100 + 80 * i);
        pane.getChildren().add(label);
    }
}
