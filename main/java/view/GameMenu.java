package view;

import controller.ConnectToServer;
import controller.GameController;
import controller.LogSaver;
import controller.PreGameController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Card;
import model.Game;
import model.Player;

import java.util.Objects;
import java.util.Scanner;

public class GameMenu extends Application {
    private static Pane pane;
    private final double WIDTH = 1000;
    private final double HEIGHT = 600;
    private static Label messageLabel = new Label("");
    private Game game;

    public GameMenu(Game game) {
        this.game = game;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        GameMenu.pane = pane;
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        setSize(stage);
        pane.setBackground(new Background(createBackgroundImage(stage)));
        makeView(pane);
        stage.setResizable(false);
        stage.show();

    }

    public void setSize(Stage stage) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
    }

    private BackgroundImage createBackgroundImage(Stage stage) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        Image image = new Image(Objects.requireNonNull(Game.class.getResource("/Images/BG/board.jpg")).toExternalForm(), bounds.getWidth(), bounds.getHeight(), false, false);
        return new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }

    private void makeView(Pane pane) {
        makeHand(pane);
        makeHBoxes(pane);
        makeScoreLabels(pane);
        makePlayerInfo(pane, game.getCurrentPlayer(), 500);
        makePlayerInfo(pane, game.getEnemy(), 200);
        makeGraveyard(pane, game.getCurrentPlayer(), 642);
        makeGraveyard(pane, game.getEnemy(), 64);
        makeGraveyardVBox(pane);
        makeWeatherHBox(pane);
        makeSpecialPlaces(pane);
        makePassButton(pane);
        makeCommander(pane, game.getCurrentPlayer(), 634);
        makeCommander(pane, game.getEnemy(), 50);
        makeButton(pane);
        makeChattingButton(pane);

    }



    private void makeHand(Pane pane) {
        HBox hand = game.getCurrentPlayer().getHand();
        for (int i = 0; i < 10; i++) {
            Card card = (Card) hand.getChildren().get(i);
            GameController.makeToolTip(card);
            card.setOnMouseClicked(event -> GameController.selectCard(card));
        }
        hand.setAlignment(Pos.CENTER);
        hand.setLayoutY(646);
        hand.setLayoutX(474);
        hand.setSpacing(9);
        pane.getChildren().add(hand);
    }

    private void makeHBoxes(Pane pane) {
        HBox enemySiege = makeHBox("Siege", 14, game.getEnemy().getSiege(), false) ;
        HBox enemyRanged =makeHBox("Ranged", 116, game.getEnemy().getRanged(), false);
        HBox enemyCloseCombat =makeHBox("Close Combat", 218, game.getEnemy().getCloseCombat(), false);
        HBox CloseCombat =makeHBox("Close Combat", 334, game.getCurrentPlayer().getCloseCombat(), true);
        HBox Ranged =makeHBox("Ranged", 436, game.getCurrentPlayer().getRanged(), true);
        HBox Siege =makeHBox("Siege", 540, game.getCurrentPlayer().getSiege(), true);
        pane.getChildren().addAll(enemySiege,enemyRanged,enemyCloseCombat,CloseCombat,Ranged,Siege);

    }

    private HBox makeHBox(String type, double y, HBox hBox, boolean player) {
        HBox container = new HBox();
        container.setMinSize(648, 90);
        container.setMaxSize(648, 90);
        container.setLayoutX(566);
        container.setLayoutY(y);
        container.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(9);
        if (player) container.setOnMouseClicked(mouseEvent -> GameController.selectPlayerRow(hBox, type, mouseEvent));
        else container.setOnMouseClicked(mouseEvent -> GameController.selectEnemyRow(hBox, type, mouseEvent));
        container.getChildren().add(hBox);
        return container;
    }

    private void makeScoreLabels(Pane pane) {
        pane.getChildren().add(makeScoreLabel(340, 540, game.getCurrentPlayer().getScoreLabel(), 28));
        pane.getChildren().add(makeScoreLabel(340, 233, game.getEnemy().getScoreLabel(), 28));
        pane.getChildren().add(makeScoreLabel(406, 356, game.getCurrentPlayer().getCloseLabel(), 24));
        pane.getChildren().add(makeScoreLabel(406, 458, game.getCurrentPlayer().getRangedLabel(), 24));
        pane.getChildren().add(makeScoreLabel(406, 560, game.getCurrentPlayer().getSigeLabel(), 24));
        pane.getChildren().add(makeScoreLabel(406, 242, game.getEnemy().getCloseLabel(), 24));
        pane.getChildren().add(makeScoreLabel(406, 136, game.getEnemy().getRangedLabel(), 24));
        pane.getChildren().add(makeScoreLabel(406, 36, game.getEnemy().getSigeLabel(), 24));
    }

    private Label makeScoreLabel(double layoutX, double layoutY, Label label, double fontSize) {
        label.setFont(new Font(28));
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        label.setPrefWidth(44);
        label.setAlignment(Pos.CENTER);
        label.setTextFill(Color.BLACK);
        return label;
    }

    private void makePlayerInfo(Pane pane, Player player, double y) {
        ImageView factionImage = new ImageView(player.getFaction().getFlagImage());
        Label username = new Label(player.getName());
        username.setFont(new Font(24));
        username.setTextFill(Color.BLACK);
        Label factionName = new Label(player.getFaction().getName());
        factionName.setFont(new Font(14));
        factionName.setTextFill(Color.BLACK);
        ImageView cardsImage = new ImageView(new Image(String.valueOf(Card.class.getResource("/Icons/icon_card_count.png"))));
        Label cardCount = player.getNumberOfCards();
        cardCount.setFont(new Font(24));
        cardCount.setTextFill(Color.BLACK);
        cardCount.setPrefWidth(30);
        ImageView leftCrystal = player.getLeftCrystal();
        ImageView rightCrystal = player.getRightCrystal();
        HBox smallHBox = new HBox(cardsImage, cardCount, leftCrystal, rightCrystal);
        smallHBox.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(factionImage, new VBox(username, factionName, smallHBox));
        hBox.setLayoutX(90);
        hBox.setLayoutY(y);
        hBox.setSpacing(10);
        pane.getChildren().add(hBox);
    }

    private void makeGraveyard(Pane pane, Player player, double y) {
        Pane graveyard = player.getGraveyard();
        graveyard.setLayoutX(1250);
        graveyard.setLayoutY(y);
        pane.getChildren().add(graveyard);
    }

    private void makeGraveyardVBox(Pane pane) {
        VBox vBox = game.getGraveyardVBox();
        vBox.setLayoutX(1350);
        vBox.setLayoutY(50);
        vBox.setAlignment(Pos.CENTER);
        pane.getChildren().add(vBox);
    }

    private void makeSpecialPlaces(Pane pane) {
        Player player = game.getCurrentPlayer();
        makeSpecialPlace(pane, "Close Combat", 334, player.getCloseCombatSpecial(), false);
        makeSpecialPlace(pane, "Ranged", 436, player.getRangedSpecial(), false);
        makeSpecialPlace(pane, "Siege", 540, player.getSiegeSpecial(), false);
        player = game.getEnemy();
        makeSpecialPlace(pane, "Close Combat", 218, player.getCloseCombatSpecial(), true);
        makeSpecialPlace(pane, "Ranged", 116, player.getRangedSpecial(), true);
        makeSpecialPlace(pane, "Siege", 14, player.getSiegeSpecial(), true);
    }

    private void makeSpecialPlace(Pane pane, String rowName, double y, Pane specialPlace, boolean enemy) {
        specialPlace.setLayoutX(480);
        specialPlace.setLayoutY(y);
        specialPlace.setMinSize(60, 90);
        specialPlace.setMaxSize(60, 90);
        if (!enemy) specialPlace.setOnMouseClicked(event -> GameController.placeSpecialCard(specialPlace, rowName));
        pane.getChildren().add(specialPlace);
    }

    private void makeWeatherHBox(Pane pane) {
        HBox hBox = game.getWeatherCards();
        hBox.setAlignment(Pos.CENTER);
        hBox.setLayoutX(100);
        hBox.setLayoutY(352);
        hBox.setSpacing(9);
        hBox.setMinSize(250, 90);
        hBox.setMaxSize(250, 90);
        hBox.setOnMouseClicked(event -> GameController.placeWeatherCard());
        pane.getChildren().add(hBox);
    }

    private void makePassButton(Pane pane) {
        Button button = game.getPass();
        button.setLayoutX(270);
        button.setLayoutY(675);
        button.setOnMouseClicked(event -> GameController.passRound(button));
        pane.getChildren().add(button);
    }

    private void makeCommander(Pane pane, Player player, double y) {
        ImageView imageView = new ImageView(player.getFaction().getCommander().getRawImage());
        imageView.setFitWidth(86);
        imageView.setPreserveRatio(true);
        imageView.setLayoutX(110);
        imageView.setLayoutY(y);
        pane.getChildren().add(imageView);
    }
  
    private void makeButton(Pane pane) {
        Button button = new Button("Reaction");
        button.setFont(Font.font("Times New Roman", 20));
        button.setStyle("-fx-background-color: #4b2a04; -fx-text-fill: #b1b4b1");
        button.setPrefSize(100, 30);
        button.setLayoutX(70);
        button.setLayoutY(450);
        button.setOnAction(event -> showOptionsOfReaction(game.getCurrentPlayer().getName() , game.getEnemy().getName()));
        pane.getChildren().addAll(button, messageLabel);
    }

    private void showOptionsOfReaction(String name, String enemyName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("show options.");
        alert.setHeaderText(null);
        alert.setContentText("Choose one of these reactions :");
        ButtonType op1 = new ButtonType("Send Damn");
        ButtonType op2 = new ButtonType("Send Emoji");
        ButtonType op3 = new ButtonType("Send Message");
        alert.getButtonTypes().setAll(op1, op2, op3);
        alert.showAndWait().ifPresent(response -> {
            if (response == op1) ConnectToServer.getOutput().println("SEND:Damn:" + name + ":" + enemyName);
            else if (response == op2) ConnectToServer.getOutput().println("SEND EMOJI:" + name + ":" + enemyName);
            else if (response == op3) checkMessage(name, enemyName);
        });
    }

    private void checkMessage(String name, String enemyName) {
        String message = PreGameController.makeInputDialog("Reaction message", "Enter message", "Please enter a short message");
        if (message.length() > 10) PreGameController.showAlert("Your message is long.");
        else ConnectToServer.getOutput().println("SEND:" + message + ":" + name + ":" + enemyName);
    }

    public static void showMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setLayoutX(100);
        messageLabel.setLayoutY(130);
        messageLabel.setFont(Font.font("Bookman Old Style", 20));
        messageLabel.setTextFill(Color.WHITE);
        // Create a Timer to remove the message after 7 seconds
        new Thread(() -> {
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> messageLabel.setText(""));
        }).start();
    }

    private void makeChattingButton(Pane pane) {
        Button button = new Button("Chat");
        button.setFont(Font.font("Times New Roman", 20));
        button.setStyle("-fx-background-color: #4b2a04; -fx-text-fill: #b1b4b1");
        button.setPrefSize(100, 30);
        button.setLayoutX(1300);
        button.setLayoutY(300);
        button.setOnAction(event -> {
            ConnectToServer.getOutput().println("CHAT");
        });
        pane.getChildren().addAll(button);
    }

    public static void addChatArea (){
        ChatArea chatArea = new ChatArea(pane);
        try {
            chatArea.start(Main.stage);
        } catch (Exception e){
            e.fillInStackTrace();
        }
    }

}