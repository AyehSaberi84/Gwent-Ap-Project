package view.ShadowViews;

import controller.ConnectToServer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import view.MainMenu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RandomGames extends Application {
    private Pane pane;
    private VBox menuPane;
    private final double WIDTH = 1000;
    private final double HEIGHT = 600;
    private final List<String> clientHandlers;
    private final HashMap<String, String> mapOfRandoms;

    public RandomGames(List<String> clientHandlers, HashMap<String, String> mapOfRandoms) {
        this.clientHandlers = clientHandlers;
        this.mapOfRandoms = mapOfRandoms;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.pane = MainMenu.pane;
        makeBackground();
        showData();
        pane.getChildren().add(menuPane);
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private void makeBackground() {
        menuPane = new VBox(10);
        menuPane.setAlignment(Pos.CENTER);
        menuPane.setStyle("-fx-background-color: rgba(52,28,2,0.87);");
        double pauseMenuWIDTH = 600;
        double pauseMenuHEIGHT = 500;
        menuPane.setPrefSize(pauseMenuWIDTH, pauseMenuHEIGHT);
        menuPane.setLayoutX((WIDTH - pauseMenuWIDTH) / 2);
        menuPane.setLayoutY((HEIGHT - pauseMenuHEIGHT) / 2);
    }

    private void showData() {

        for (Map.Entry<String, String> entry : mapOfRandoms.entrySet()) {
            String name = entry.getValue();
            String enemyName = entry.getKey();
            Label label = makeLabel("There is a game between " + name + " and  " + enemyName);
            menuPane.getChildren().add(label);
        }
        for (String string : clientHandlers) {
            Label label = makeLabel("This user is waiting for game : " + string);
            menuPane.getChildren().add(label);
        }
        Button button = makeButton();
        button.setOnAction(event -> {
            menuPane.setVisible(false);
            ConnectToServer.getOutput().println("END OF SHOWING RANDOM GAMES");
        });
        menuPane.getChildren().add(button);
    }

    private static Label makeLabel(String string) {
        Label label = new Label(string);
        label.setFont(Font.font("Bookman Old Style", 20));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private Button makeButton() {
        Button button = new Button("Exit");
        button.setFont(Font.font("Bookman Old Style", 20));
        button.setStyle("-fx-background-color: rgba(108,58,3,0.87); -fx-text-fill: #f3fff3");
        button.setPrefSize(100, 30);
        return button;
    }
}
