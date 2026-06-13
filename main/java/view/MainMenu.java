package view;

import controller.ConnectToServer;
import controller.LogSaver;
import controller.PreGameController;
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
import model.DataBase.UserDAO;
import model.Game;
import model.Player;
import model.User;
import view.ShadowViews.ScoreTable;

import java.util.Date;
import java.util.Objects;

public class MainMenu extends Application {
    public static Pane pane;
    private final double WIDTH = 1000;
    private final double HEIGHT = 600;
    private final double SPACE = 10;
    private final double WSpace = 50;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        MainMenu.pane = pane;
        setSize(pane, stage);
        pane.setBackground(new Background(createBackgroundImage()));
        makeView(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
//        Game fame = new Game(new Player("p1") , new Player("p2"));
//        fame.setDate(new Date());
//        LogSaver.makeScreen(fame);
    }

    private BackgroundImage createBackgroundImage() {
        Image image = new Image(Objects.requireNonNull(Game.class.getResource("/Images/BG/mainBG.jpg")).toExternalForm(), WIDTH, HEIGHT, false, false);
        return new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
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

    private void makeView(Pane pane) {
        makeLabel(pane);
        makeButtons(pane);
    }

    private void makeLabel(Pane pane) {
        Label showMenu = new Label("Welcome To The MAIN MENU!");
        showMenu.setFont(Font.font("Algerian", 24));
        showMenu.setTextFill(Color.SILVER);
        showMenu.setLayoutX(WIDTH / 2 - WSpace * 3);
        showMenu.setLayoutY(SPACE);
        pane.getChildren().add(showMenu);
    }

    private void makeButtons(Pane pane) {
        Button profileMenu = makeButton("Go To Profile Menu", 7);
        profileMenu.setOnAction(event -> {
            ProfileMenu profileMenu1 = new ProfileMenu();
            try {
                profileMenu1.start(Main.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Button preGameMenu = makeButton("Start A New Game", 11);
        preGameMenu.setOnAction(event -> PreGameController.createGame());

        Button scoreTable = makeButton("Score Table", 15);
        scoreTable.setOnAction(event -> SceneController.goToScoreTable(pane));

        Button tournament = makeButton("Join Tournament", 19);
        tournament.setOnAction(event -> SceneController.goToTournament());

        Button logOut = makeButton("Log Out", 23);
        logOut.setOnAction(event -> {
            User.setLoggedInUser(null);
            SceneController.goToMain();
            ConnectToServer.getOutput().println("LOGOUT");
        });

        Button randomGames = makeButton("Show Random Games", 27);
        randomGames.setOnAction(event -> ConnectToServer.getOutput().println("SHOW RANDOM GAMES:" + User.getLoggedInUser().getUsername()));


        Button exit = makeButton("Exit", 35);
        exit.setOnAction(event -> {
            SceneController.goToMain();
            ConnectToServer.getOutput().println("LOGOUT");
        });
        Button TV = makeButton("TV", 31);
        TV.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TVPage tvPage = new TVPage();
                try {
                    tvPage.start(Main.stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        pane.getChildren().addAll(profileMenu, preGameMenu, scoreTable, logOut, tournament, randomGames,TV , exit );
    }

    private Button makeButton(String name, double SPACENum) {
        Button button = new Button(name);
        button.setFont(Font.font("Times New Roman", 20));
        button.setStyle("-fx-background-color: #460802; -fx-text-fill: #e5e5e5");
        button.setPrefSize(250, 30);
        button.setLayoutX(WIDTH / 2 + WSpace * 3 - 250);
        button.setLayoutY(SPACE * SPACENum);
        return button;
    }
}
