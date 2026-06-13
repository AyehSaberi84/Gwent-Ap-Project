package view;

import controller.GameController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Card;
import model.Game;

import java.util.ArrayList;
import java.util.Objects;

public class VetoCardMenu extends Application {

    private final double WIDTH = 1000;
    private final double HEIGHT = 600;

    private Game game;


    public VetoCardMenu(Game game) {
        this.game = game;
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
        stage.setHeight(HEIGHT + 36);
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
        VBox vBox = new VBox(10);
        vBox.setPrefSize(WIDTH, HEIGHT);
        // vBox.setStyle("-fx-background-color: rgba(168,163,163,0.44);");
        vBox.setAlignment(Pos.CENTER);
        ArrayList<HBox> hBoxes = makeHBoxes();
        vBox.getChildren().addAll(makeLabel(), hBoxes.get(0), hBoxes.get(1), makeFinishButton());
        pane.getChildren().add(vBox);
    }

    private Label makeLabel() {
        Label label = new Label("You can choose up to 2 cards to redraw");
        label.setFont(Font.font("Algerian", 22));
        label.setStyle("-fx-background-color: #e1a758");
        label.setMinHeight(32);
        return label;
    }

    private Button makeFinishButton() {
        Button finish = new Button("Finish");
        finish.setFont(Font.font("Times New Roman", 16));
        finish.setStyle("-fx-background-color: #e1a758");
        finish.setPrefSize(100, 30);
        finish.setOnMouseClicked(event -> GameController.endVetoCard(game));
        return finish;
    }

    private ArrayList<HBox> makeHBoxes() {
        HBox hBox1 = new HBox(10);
        hBox1.setAlignment(Pos.CENTER);
        HBox hBox2 = new HBox(10);
        hBox2.setAlignment(Pos.CENTER);
        HBox hBox = hBox1;
        for (int i = 0; i < 10; i++) {
            if (i == 5) hBox = hBox2;
            Card card = GameController.addRandomCardToHand(game.getPlayer1(), i);
            ImageView imageView = new ImageView(card.getImage());
            GameController.makeToolTip(card);
            imageView.setFitWidth(128);
            imageView.setPreserveRatio(true);
            hBox.getChildren().add(imageView);
            int finalI = i % 5;
            HBox finalHBox = hBox;
            imageView.setOnMouseClicked(event -> GameController.vetoCard(card, game.getPlayer1(), finalHBox, finalI));
        }
        ArrayList<HBox> hBoxes = new ArrayList<>();
        hBoxes.add(hBox1);
        hBoxes.add(hBox2);
        return hBoxes;
    }

}