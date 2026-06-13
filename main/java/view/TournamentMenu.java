package view;

import controller.ConnectToServer;
import controller.SceneController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Game;
import model.Tournament;

import java.util.Objects;

public class TournamentMenu extends Application {
    private final double WIDTH = 1000;
    private final double HEIGHT = 600;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        setSize(pane, stage);
        pane.setBackground(new Background(createBackgroundImage()));
        makeView(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        stage.setHeight(636);
        ConnectToServer.getOutput().println("getTournamentUpdate");
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
        makeLabel(pane, 100, 70, 0);
        makeLabel(pane, 100, 140, 1);
        makeLabel(pane, 100, 280, 2);
        makeLabel(pane, 100, 350, 3);
        makeLabel(pane, 860, 70, 4);
        makeLabel(pane, 860, 140,5);
        makeLabel(pane, 860, 280, 6);
        makeLabel(pane, 860, 350, 7);

        makeLabel(pane, 290, 175, 8);
        makeLabel(pane, 290, 245, 9);
        makeLabel(pane, 290, 385, 10);
        makeLabel(pane, 290, 455, 11);

        makeLabel(pane, 670, 175, 12);
        makeLabel(pane, 670, 245, 13);
        makeLabel(pane, 670, 385, 14);
        makeLabel(pane, 670, 455, 15);

        makeLabel(pane, 480, 105, 16);
        makeLabel(pane, 480, 175, 17);
        makeLabel(pane, 480, 245, 18);
        makeLabel(pane, 480, 315, 19);

        makeLabel(pane, 480, 385, 20);
        makeLabel(pane, 480, 455, 21);

        makeJoinButton(pane);
        makeExitButton(pane);
    }

    private void makeLabel(Pane pane, double x, double y, int i) {
        Label label = Tournament.getLabels()[i];
        label.setTextFill(Color.WHITE);
        label.setLayoutX(x);
        label.setLayoutY(y);
        pane.getChildren().add(label);
    }

    private void makeJoinButton(Pane pane) {
        Button joinButton = Tournament.getJoinButton();
        joinButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (Tournament.isStarted()) return;
                if (joinButton.getText().equals("Joined!")) return;
                ConnectToServer.getOutput().println("joinTournament");
                joinButton.setText("Joined!");
                joinButton.setOnMouseClicked(mouseEvent -> {});
            }
        });
        joinButton.setFont(Font.font("Times New Roman", 12));
        joinButton.setStyle("-fx-background-color: #360802; -fx-text-fill: #e5e5e5");
        joinButton.setLayoutX(480);
        joinButton.setLayoutY(540);
        pane.getChildren().add(joinButton);
    }

    private void makeExitButton(Pane pane) {
        Button button = new Button("Exit");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (Tournament.isStarted()) return;
                SceneController.goToMainMenu();
            }
        });
        button.setFont(Font.font("Times New Roman", 12));
        button.setStyle("-fx-background-color: #063832; -fx-text-fill: #e5e5e5");
        button.setLayoutX(480);
        button.setLayoutY(570);
        pane.getChildren().add(button);
    }

}
