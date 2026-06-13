package view.ShadowViews;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Game;
import model.Player;
import model.User;

import java.util.Optional;

public class GameHistory extends Application {
    private final Pane pane;
    private Pane menuPane;
    private final double WIDTH = 1000;
    private final double HEIGHT = 600;

    public GameHistory(Pane pane) {
        this.pane = pane;
    }

    @Override
    public void start(Stage stage) throws Exception {
        askNumber();
    }

    private void askNumber() {
        if (User.getLoggedInUser().getGamesPlayed() == 0)
            showAlert("You haven't played a game yet!");
        else {
            int n = Math.min(User.getLoggedInUser().getGamesPlayed(), 5);
            String number = makeInputDialog();
            if (number != null) n = Integer.parseInt(number);
            if (n > 0) makeView(n);
            else showAlert("You entered invalid number.");
        }
    }

    private String makeInputDialog() {
        TextInputDialog answerDialog = new TextInputDialog();
        answerDialog.setTitle("Number History");
        answerDialog.setHeaderText("Please Enter Number History");
        answerDialog.setContentText("Please Enter Number History");
        Optional<String> result = answerDialog.showAndWait();
        return result.orElse(null);
    }

    private void makeView(int n) {
        VBox menuContent = new VBox(10);
        menuContent.setAlignment(Pos.CENTER);
        makeBackground();
        showGameHistory(menuContent, n);
        menuPane.getChildren().add(menuContent);
        pane.getChildren().add(menuPane);
    }

    private void showGameHistory(VBox menuContent, int n) {
        User user = User.getLoggedInUser();
        Player enemyPlayer;
        Player player;
        int counter = 1;
        String enemyName;
        for (int i = user.getAllGames().size() - 1; i >= user.getAllGames().size() - n; i--) {
            Game game = user.getAllGames().get(i);
            if (game.getPlayer1().getUser() != user) {
                enemyName = game.getPlayer1().getName();
                enemyPlayer = game.getPlayer1();
                player = game.getPlayer2();
                ;
            } else {
                enemyName = game.getPlayer2().getName();
                enemyPlayer = game.getPlayer2();
                player = game.getPlayer1();
            }


            String date = game.getDate().toString();
            int[] insiderScore = game.getPlayer1scores();
            int[] enemyScore = game.getPlayer2scores();


            int insiderFinalScore = player.getScore();
            int enemyFinalScore = enemyPlayer.getScore();

            String winner = game.getWinnerName();

            Label label = makeLabel(counter + ". Enemy Username : " + enemyName + " Date : " + date + "\n"
                    + "Round one, Score : " + insiderScore[0] + "  Round one, Enemy Score : " + enemyScore[0] + "\n"
                    + "Round two, Score : " + insiderScore[1] + "  Round two, Enemy Score : " + enemyScore[1] + "\n"
                    + "Round three, Score : " + insiderScore[2] + "  Round three, Enemy Score : " + enemyScore[2] + "\n"
                    + "Final Score : " + insiderFinalScore + "  Enemy Final Score : " + enemyFinalScore + "\n"
                    + "Winner Of This Game : " + winner);
            menuContent.getChildren().add(label);
            counter++;
        }
        Button button = makeButton();
        menuContent.getChildren().add(button);
    }

    private void makeBackground() {
        menuPane = new Pane();
        menuPane.setStyle("-fx-background-color: rgba(70,15,182,0.65);");
        double pauseMenuWIDTH = 600;
        double pauseMenuHEIGHT = 500;
        menuPane.setPrefSize(pauseMenuWIDTH, pauseMenuHEIGHT);
        menuPane.setLayoutX((WIDTH - pauseMenuWIDTH) / 2);
        menuPane.setLayoutY((HEIGHT - pauseMenuHEIGHT) / 2);
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error message");
        alert.setHeaderText("Error message");
        alert.setContentText(message);
        alert.show();
    }

    private Label makeLabel(String string) {
        Label label = new Label(string);
        label.setFont(Font.font("Times New Roman", 14));
        label.setTextFill(Color.SILVER);
        return label;
    }

    private Button makeButton() {
        Button button = new Button("Exit");
        button.setFont(Font.font("Bookman Old Style", 20));
        button.setStyle("-fx-background-color: #550080; -fx-text-fill: #737974");
        button.setPrefSize(250, 30);
        button.setOnAction(event -> menuPane.setVisible(false));
        return button;
    }
}
