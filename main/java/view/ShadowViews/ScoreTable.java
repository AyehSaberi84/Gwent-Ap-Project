package view.ShadowViews;

import controller.ConnectToServer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;

import java.util.List;

public class ScoreTable extends Application {
    private final Pane pane;
    private Pane menuPane;
    private static VBox menuStats = new VBox(10);
    private static VBox menuName = new VBox(10);
    private static VBox menuScore = new VBox(10);
    private final double WIDTH = 1000;
    private final double HEIGHT = 600;

    public ScoreTable(Pane pane) {
        this.pane = pane;
    }

    @Override
    public void start(Stage stage) {
        makeView();
        startUserStatusCheck();
        pane.getChildren().add(menuPane);
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private void makeView() {
        makeBackground();
        makeBox();
    }

    private void makeBackground() {
        menuPane = new Pane();
        menuPane.setStyle("-fx-background-color: rgba(52,28,2,0.87);");
        double pauseMenuWIDTH = 600;
        double pauseMenuHEIGHT = 500;
        menuPane.setPrefSize(pauseMenuWIDTH, pauseMenuHEIGHT);
        menuPane.setLayoutX((WIDTH - pauseMenuWIDTH) / 2);
        menuPane.setLayoutY((HEIGHT - pauseMenuHEIGHT) / 2);
    }

    private void makeBox() {
        HBox menuContent = new HBox(10);
        menuContent.setLayoutX(130);
        menuContent.setLayoutY(50);
        menuContent.setAlignment(Pos.CENTER);

        menuName.getChildren().clear();
        menuName.getChildren().add(makeLabel("Names:"));

        menuScore.getChildren().clear();
        menuScore.getChildren().add(makeLabel("Scores:"));

        User.makeRank();

        menuStats.getChildren().clear();
        menuStats.getChildren().add(makeLabel("Status:"));

        addInfoToScreen(Math.min(User.getAllUsers().size(), 10));

        Button button = makeButton("Exit");
        menuScore.getChildren().add(button);

        menuContent.getChildren().addAll(menuName, menuScore, menuStats);
        menuPane.getChildren().addAll(menuContent);
    }

    private static Label makeLabel(String string) {
        Label label = new Label(string);
        label.setFont(Font.font("Bookman Old Style", 20));
        label.setTextFill(Color.GREY);
        return label;
    }

    private Button makeButton(String name) {
        Button button = new Button(name);
        button.setFont(Font.font("Bookman Old Style", 20));
        button.setStyle("-fx-background-color: #6e3e06; -fx-text-fill: #f3fff3");
        button.setPrefSize(100, 30);
        button.setOnAction(event -> {
            menuPane.setVisible(false);
        });
        button.setAlignment(Pos.CENTER);
        return button;
    }

    private void addInfoToScreen(int size) {
        for (int i = 0; i < size; i++) {
            User user = User.getAllUsers().get(i);
            fillDataOfUser(i, user);
            ConnectToServer.getOutput().println("CHECK:" + user.getUsername());
        }
    }

    private void startUserStatusCheck() {
        // Create a simple timeline that repeats every 5 seconds
       Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(15), event -> {
            for (User user : User.getAllUsers()) {
                ConnectToServer.checkUserStatus(user.getUsername());
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // Ensure timeline repeats indefinitely
        timeline.play();
    }

    public static void updateUserStatus(String username, boolean isOnline) {
        Platform.runLater(() -> {
            for (int i = 1; i < menuName.getChildren().size(); i++) {
                Node node = menuName.getChildren().get(i);
                if (node instanceof Label nameLabel) {
                    if (nameLabel.getText().contains(username)) {
                        Label statusLabel = (Label) menuStats.getChildren().get(i);
                        if (statusLabel != null) {
                            statusLabel.setTextFill(isOnline ? Color.GREEN : Color.RED);
                            statusLabel.setText(isOnline ? "Online" : "Offline");
                        }
                    }
                }
            }
        });
    }

    public static void updateScoreTable() {
        Platform.runLater(() -> {
            List<User> users = User.getAllUsers();
            users.sort((u1, u2) -> Integer.compare(u2.getWins(), u1.getWins()));

            menuName.getChildren().clear();
            menuScore.getChildren().clear();
            menuStats.getChildren().clear();

            menuName.getChildren().add(makeLabel("Names:"));
            menuScore.getChildren().add(makeLabel("Scores:"));
            menuStats.getChildren().add(makeLabel("Status:"));

            int size = Math.min(users.size(), 10);
            for (int i = 0; i < size; i++) {
                User user = users.get(i);
                fillDataOfUser(i, user);
                user.winsProperty().addListener((observable, oldValue, newValue) -> updateScoreTable());
            }
        });
    }

    private static void fillDataOfUser(int i, User user) {
        Label nameLabel = makeLabel((i + 1) + ". " + user.getUsername());
        Label scoreLabel = makeLabel(" - Number of wins: " + user.getWins());
        Label statusLabel = makeLabel("Unknown"); // Default status is unknown
        menuName.getChildren().add(nameLabel);
        menuScore.getChildren().add(scoreLabel);
        menuStats.getChildren().add(statusLabel);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
