package view;

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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class PastGamesPage extends Application {
    private File[] games ;
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
        Label showMenu = new Label("Welcome To The Past Games Menu!");
        showMenu.setFont(Font.font("Freestyle Script", 40));
        showMenu.setTextFill(Color.WHITE);
        showMenu.setLayoutX(WSpace);
        showMenu.setLayoutY(SPACE);
        pane.getChildren().add(showMenu);
    }

    private void makeAllButtons (Pane pane){
        games = PastGameController.getPastGames();
        if(games.length ==0){
            Label emtyLabel = new Label("There is no Past Game");
            emtyLabel.setFont(Font.font( 40));
            emtyLabel.setTextFill(Color.WHITE);
            emtyLabel.setLayoutX(WSpace);
            emtyLabel.setLayoutY(SPACE + 50);
            pane.getChildren().add(emtyLabel);
        }
        if(games.length >0) {
            Button game1 = makeButton(games[0].getAbsolutePath().replaceFirst(".+#", "#"), 5, 0);
            game1.setOnAction(new EventHandler< ActionEvent >() {
                @Override
                public void handle(ActionEvent event) {
                    ShowPastGame showPastGame = new ShowPastGame(games[0].getAbsolutePath());
                    try {
                        showPastGame.start(Main.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            pane.getChildren().add(game1);
        }
       if(games.length >1) {
            Button game2 = makeButton(games[1].getAbsolutePath().replaceFirst(".+#", "#"), 10, 0);
            game2.setOnAction(new EventHandler< ActionEvent >() {
                @Override
                public void handle(ActionEvent event) {
                    ShowPastGame showPastGame = new ShowPastGame(games[1].getAbsolutePath());
                    try {
                        showPastGame.start(Main.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            pane.getChildren().add(game2);
        }
        if(games.length >2) {
            Button game3 = makeButton(games[2].getAbsolutePath().replaceFirst(".+#", "#"), 15, 0);
            game3.setOnAction(new EventHandler< ActionEvent >() {
                @Override
                public void handle(ActionEvent event) {
                    ShowPastGame showPastGame = new ShowPastGame(games[2].getAbsolutePath());
                    try {
                        showPastGame.start(Main.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            pane.getChildren().add(game3);
        }
        if(games.length >3) {
            Button game4 = makeButton(games[3].getAbsolutePath().replaceFirst(".+#", "#"), 20, 0);
            game4.setOnAction(new EventHandler< ActionEvent >() {
                @Override
                public void handle(ActionEvent event) {
                    ShowPastGame showPastGame = new ShowPastGame(games[2].getAbsolutePath());
                    try {
                        showPastGame.start(Main.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            pane.getChildren().add(game4);
        }
         if(games.length >4) {
            Button game5 = makeButton(games[4].getAbsolutePath().replaceFirst(".+#", "#"), 25, 0);
            game5.setOnAction(new EventHandler< ActionEvent >() {
                @Override
                public void handle(ActionEvent event) {
                    ShowPastGame showPastGame = new ShowPastGame(games[2].getAbsolutePath());
                    try {
                        showPastGame.start(Main.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            pane.getChildren().add(game5);
        }
        if(games.length >5) {
            Button game6 = makeButton(games[5].getAbsolutePath().replaceFirst(".+#", "#"), 30, 0);
            game6.setOnAction(new EventHandler< ActionEvent >() {
                @Override
                public void handle(ActionEvent event) {
                    ShowPastGame showPastGame = new ShowPastGame(games[2].getAbsolutePath());
                    try {
                        showPastGame.start(Main.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            pane.getChildren().add(game6);
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
