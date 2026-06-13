package view;

import controller.LiveController;
import controller.PastGameController;
import controller.ProfileController;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class LivePage extends Application {
    private File[] onlinegames ;
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
        Label showMenu = new Label("Welcome To The Live Menu!");
        showMenu.setFont(Font.font("Freestyle Script", 40));
        showMenu.setTextFill(Color.WHITE);
        showMenu.setLayoutX(WSpace);
        showMenu.setLayoutY(SPACE);
        pane.getChildren().add(showMenu);
    }

    private void makeAllButtons (Pane pane){
        onlinegames = LiveController.getPastGames();
        if(onlinegames.length==0){
            Label emtyLabel = new Label("There is no live Game");
            emtyLabel.setFont(Font.font( 40));
            emtyLabel.setTextFill(Color.WHITE);
            emtyLabel.setLayoutX(WSpace);
            emtyLabel.setLayoutY(SPACE + 50);
            pane.getChildren().add(emtyLabel);
        }
        if(onlinegames.length >0) {
            Button game1 = makeButton(onlinegames[0].getAbsolutePath().replaceFirst(".+@" , "@"), 5, 0);
            game1.setOnAction(new EventHandler< ActionEvent >() {
                @Override
                public void handle(ActionEvent event) {
                    OnlineGame onlineGame = new OnlineGame(onlinegames[0].getAbsolutePath());
                    try {
                        onlineGame.start(Main.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            pane.getChildren().add(game1);
        }
         if(onlinegames.length >1) {
            Button game2 = makeButton(onlinegames[1].getAbsolutePath().replaceFirst(".+@" , "@"), 10, 0);
            game2.setOnAction(new EventHandler< ActionEvent >() {
                @Override
                public void handle(ActionEvent event) {
                    OnlineGame onlineGame = new OnlineGame(onlinegames[1].getAbsolutePath());
                    try {
                        onlineGame.start(Main.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            pane.getChildren().add(game2);
        }
        if(onlinegames.length >2) {
            Button game3 = makeButton(onlinegames[2].getAbsolutePath().replaceFirst(".+@" , "@"), 15, 0);
            game3.setOnAction(new EventHandler< ActionEvent >() {
                @Override
                public void handle(ActionEvent event) {
                    OnlineGame onlineGame = new OnlineGame(onlinegames[2].getAbsolutePath());
                    try {
                        onlineGame.start(Main.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            pane.getChildren().add(game3);
        }
        if(onlinegames.length >3) {
            Button game4 = makeButton(onlinegames[3].getAbsolutePath().replaceFirst(".+@" , "@"), 20, 0);
            game4.setOnAction(new EventHandler< ActionEvent >() {
                @Override
                public void handle(ActionEvent event) {
                    OnlineGame onlineGame = new OnlineGame(onlinegames[3].getAbsolutePath());
                    try {
                        onlineGame.start(Main.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            pane.getChildren().add(game4);
        }

        Button exit = makeButton("Exit",35,90);
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
