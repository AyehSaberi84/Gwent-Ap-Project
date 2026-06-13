package view;

import controller.SceneController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Game;
import model.Icons;
import model.User;

import java.util.Objects;
import java.util.Scanner;

public class EndGameMenu extends Application {
    private final double WIDTH = 1000;
    private final double HEIGHT = 600;

    private String winner;
    private String playerName;

    private String p1name;
    private String p1r1s;
    private String p1r1w;
    private String p1r2s;
    private String p1r2w;
    private String p1r3s;
    private String p1r3w;

    private String p2name;
    private String p2r1s;
    private String p2r1w;
    private String p2r2s;
    private String p2r2w;
    private String p2r3s;
    private String p2r3w;
    private boolean isTournament;


    public static void run(Scanner scanner) {

    }

    public EndGameMenu(String winner, String playerName, String p1name, String p1r1s, String p1r1w, String p1r2s, String p1r2w, String p1r3s, String p1r3w, String p2name, String p2r1s, String p2r1w, String p2r2s, String p2r2w, String p2r3s, String p2r3w, String isTournament) {
        this.winner = winner;
        this.playerName = playerName;
        this.p1name = p1name;
        this.p1r1s = p1r1s;
        this.p1r1w = p1r1w;
        this.p1r2s = p1r2s;
        this.p1r2w = p1r2w;
        this.p1r3s = p1r3s;
        this.p1r3w = p1r3w;
        this.p2name = p2name;
        this.p2r1s = p2r1s;
        this.p2r1w = p2r1w;
        this.p2r2s = p2r2s;
        this.p2r2w = p2r2w;
        this.p2r3s = p2r3s;
        this.p2r3w = p2r3w;
        this.isTournament = isTournament.equals("true");
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        setSize(pane, stage);
        pane.setBackground(new Background(createBackgroundImage()));
        makeView(pane);
        stage.setResizable(false);
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
        if (Game.getCurrentGame().getCurrentPlayer().getName().equals(p1name)) {
            Game.getCurrentGame().setPlayer1(Game.getCurrentGame().getCurrentPlayer());
            Game.getCurrentGame().setPlayer2(Game.getCurrentGame().getEnemy());
        } else {
            Game.getCurrentGame().setPlayer1(Game.getCurrentGame().getEnemy());
            Game.getCurrentGame().setPlayer2(Game.getCurrentGame().getCurrentPlayer());
        }
        ImageView imageView;
        if (winner.equals(playerName)) {
            imageView = new ImageView(Icons.WIN.getImage());
            User.getLoggedInUser().setWins(User.getLoggedInUser().getWins() + 1);
            Game.getCurrentGame().setWinner(Game.getCurrentGame().getCurrentPlayer().getName());
        } else if (winner.equals("draw")) {
            imageView = new ImageView(Icons.DRAW.getImage());
            User.getLoggedInUser().setDraws(User.getLoggedInUser().getDraws() + 1);
            Game.getCurrentGame().setWinner(null);
        } else {
            imageView = new ImageView(Icons.LOSE.getImage());
            User.getLoggedInUser().setLoses(User.getLoggedInUser().getLoses() + 1);
            Game.getCurrentGame().setWinner(Game.getCurrentGame().getEnemy().getName());
        }
        int[] p1scores = new int[] {Integer.parseInt(p1r1s), Integer.parseInt(p1r2s), Integer.parseInt(p1r3s)};
        Game.getCurrentGame().setPlayer1scores(p1scores);
        int[] p2scores = new int[] {Integer.parseInt(p2r1s), Integer.parseInt(p2r2s), Integer.parseInt(p2r3s)};
        Game.getCurrentGame().setPlayer2scores(p2scores);
        int p1score = 0;
        int p2score = 0;
        for (int i = 0; i < 3; i++) {
            p1score += p1scores[i];
            p2score += p2scores[i];
        }
        Game.getCurrentGame().getPlayer1().setScore(p1score);
        Game.getCurrentGame().getPlayer2().setScore(p2score);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        HBox hBox1 = new HBox(makeLabel(p1name), makeLabel(p1r1s, p1r1w), makeLabel(p1r2s, p1r2w), makeLabel(p1r3s, p1r3w));
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(50);
        HBox hBox2 = new HBox(makeLabel(p2name), makeLabel(p2r1s, p2r1w), makeLabel(p2r2s, p2r2w), makeLabel(p2r3s, p2r3w));
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setSpacing(50);
        vBox.getChildren().addAll(imageView, hBox1, hBox2);

        vBox.setLayoutX(WIDTH / 6);

        Button exit = new Button("Exit");
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isTournament) SceneController.goToTournament();
                else SceneController.goToMainMenu();
            }
        });

        vBox.getChildren().add(exit);

        pane.getChildren().add(vBox);
    }

    private Label makeLabel(String name) {
        Label label = new Label(name);
        label.setFont(new Font(18));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private Label makeLabel(String score, String win) {
        Label label = new Label(score);
        label.setFont(new Font(18));
        if (win.equals("true")) label.setTextFill(Color.YELLOW);
        else label.setTextFill(Color.WHITE);
        return label;
    }
}
