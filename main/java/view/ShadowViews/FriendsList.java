package view.ShadowViews;

import controller.ConnectToServer;
import controller.ProfileController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.User;

public class FriendsList extends Application {
    private final Pane pane;
    private BorderPane menuPane;
    private final double WIDTH = 1000;
    private final double HEIGHT = 600;

    public FriendsList(Pane pane) {
        this.pane = pane;
    }

    @Override
    public void start(Stage stage) {
        makeView();
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
        menuPane = new BorderPane();
        menuPane.setStyle("-fx-background-color: rgba(52,28,2,0.87);");
        double pauseMenuWIDTH = 600;
        double pauseMenuHEIGHT = 500;
        menuPane.setPrefSize(pauseMenuWIDTH, pauseMenuHEIGHT);
        menuPane.setLayoutX((WIDTH - pauseMenuWIDTH) / 2);
        menuPane.setLayoutY((HEIGHT - pauseMenuHEIGHT) / 2);
    }

    private void makeBox() {
        VBox vBox = new VBox();
        vBox.setLayoutX(130);
        vBox.setLayoutY(50);
        vBox.setAlignment(Pos.CENTER);

        HBox addFriendContainer = new HBox();
        Button addFriend = makeButton("Add friend");
        addFriendContainer.getChildren().add(addFriend);
        addFriendContainer.setAlignment(Pos.CENTER);
        addFriend.setOnAction(actionEvent -> ProfileController.addFriend());
        menuPane.setTop(addFriendContainer);

        VBox friends = User.getLoggedInUser().getFriends();
        friends.setLayoutX(100);
        VBox requests = User.getLoggedInUser().getRequests();
        requests.setLayoutX(420);
        menuPane.setCenter(new Pane(friends, requests));

        Button exit = makeButton("Exit");
        VBox exitContainer = new VBox(exit);
        exitContainer.setAlignment(Pos.CENTER);
        exit.setOnAction(actionEvent -> menuPane.setVisible(false));
        menuPane.setBottom(exitContainer);
    }

    public static Label makeLabel(String string) {
        Label label = new Label(string);
        label.setFont(Font.font("Bookman Old Style", 20));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private Button makeButton(String name) {
        Button button = new Button(name);
        button.setFont(Font.font("Bookman Old Style", 20));
        button.setStyle("-fx-background-color: #6e3e06; -fx-text-fill: #f3fff3");
        button.setPrefSize(180, 30);
        button.setAlignment(Pos.CENTER);
        return button;
    }

}
