package view;

import controller.LogSaver;
import controller.PreGameController;
import controller.SceneController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.DataBase.DatabaseInitializer;
import model.DataBase.UserDAO;
import model.Game;
import java.util.Objects;
import java.util.Random;

public class Main extends Application {
    public static Stage stage;
    public Scene scene;
    private final double WIDTH = 1000;
    private final double HEIGHT = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        DatabaseInitializer.createUsersTable();
        UserDAO userDAO = new UserDAO();
        userDAO.makeAllUsers();
        Main.stage = stage;
        Pane pane = new Pane();
        setSize(pane);
        pane.setBackground(new Background(createBackgroundImage()));
        makeRegisterButton(pane);
        makeLoginButton(pane);
        makeExitButton(pane);
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }


    private void setSize(Pane pane) {
        pane.setMaxWidth(WIDTH);
        pane.setMinWidth(WIDTH);
        pane.setMaxHeight(HEIGHT);
        pane.setMinHeight(HEIGHT);
    }

    private BackgroundImage createBackgroundImage() {
        Image image = new Image(Objects.requireNonNull(Game.class.getResource("/Images/BG/enter_menu.jpg")).toExternalForm(), WIDTH, HEIGHT, false, false);
        return new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }

    private void makeRegisterButton(Pane pane) {
        Button register = styleOfButton("Sign Up", 0);
        register.setOnAction(event -> SceneController.goToRegister());
        pane.getChildren().add(register);
    }

    private void makeLoginButton(Pane pane) {
        Button login = styleOfButton("Login", 70);
        login.setOnAction(event -> SceneController.goToLogin());
        pane.getChildren().add(login);
    }

    private void makeExitButton(Pane pane) {
        Button button = styleOfButton("Exit", 140);
        button.setOnAction(event -> System.exit(0));
        pane.getChildren().add(button);
    }

    private Button styleOfButton(String name, double change) {
        Button button = new Button(name);
        button.setFont(Font.font("Algerian", 24));
        button.setStyle("-fx-background-color: #0d8c6b");
        button.setPrefSize(150, 50);
        button.setLayoutX(WIDTH / 2 - (double) 50);
        button.setLayoutY((double) 150 + change);
        return button;
    }

}
