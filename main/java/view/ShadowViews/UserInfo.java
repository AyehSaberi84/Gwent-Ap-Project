package view.ShadowViews;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.User;

public class UserInfo extends Application {
    private final Pane pane;
    private Pane menuPane;
    private final double WIDTH = 1000;
    private final double HEIGHT = 600;

    public UserInfo(Pane pane) {
        this.pane = pane;
    }

    @Override
    public void start(Stage stage) throws Exception {
        makeView();
        pane.getChildren().add(menuPane);
    }

    private void makeView() {
        makeBackground();
        showUserInfo();
    }

    private void makeBackground() {
        menuPane = new Pane();
        menuPane.setStyle("-fx-background-color: rgba(19,212,220,0.71);");
        double pauseMenuWIDTH = 600;
        double pauseMenuHEIGHT = 400;
        menuPane.setPrefSize(pauseMenuWIDTH, pauseMenuHEIGHT);
        menuPane.setLayoutX((WIDTH - pauseMenuWIDTH) / 2);
        menuPane.setLayoutY((HEIGHT - pauseMenuHEIGHT) / 2);
    }

    private void showUserInfo() {
        VBox menuContent = new VBox(10);
        menuContent.setLayoutX(130);
        menuContent.setLayoutY(50);
        menuContent.setAlignment(Pos.CENTER);
        User user = User.getLoggedInUser();
        Label name = makeLabel("Username : " + user.getUsername());
        Label nickname = makeLabel("Nickname : " + user.getNickname());
        Label highScore = makeLabel("HighScore : " + user.getHighestScore());
        Label rate = makeLabel("Rate : " + user.getRate());
        Label gamesPlayed = makeLabel("Number of Games Played : " + user.getGamesPlayed());
        Label draw = makeLabel("Draws : " + user.getDraws());
        Label wins = makeLabel("Wins : "  + user.getWins());
        Label loses = makeLabel("Loses : " + user.getLoses());
        Button button = makeButton("Exit");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               menuPane.setVisible(false);
            }
        });
        menuContent.getChildren().addAll(name, nickname,highScore,rate,gamesPlayed,draw,wins,loses,button);
        menuPane.getChildren().add(menuContent);
    }

    private Label makeLabel(String string) {
        Label label = new Label(string);
        label.setFont(Font.font("Bookman Old Style", 22));
        label.setTextFill(Color.MIDNIGHTBLUE);
        label.setLayoutX(100);
        label.setLayoutY(100);
        return label;
    }
    private Button makeButton(String name) {
        Button button = new Button(name);
        button.setFont(Font.font("Bookman Old Style", 20));
        button.setStyle("-fx-background-color: #037c76; -fx-text-fill: #f3fff3");
        button.setPrefSize(250, 30);
        button.setLayoutX(100);
        button.setLayoutY(100);
        return button;
    }
}
