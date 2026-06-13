package view;

import controller.LoginController;
import controller.SceneController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Game;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;
@SpringBootApplication
public class LoginMenu extends Application {
    private ConfigurableApplicationContext context;
    private final double WIDTH = 1000;
    private final double HEIGHT = 600;
    private final double SPACE = 10;
    private final double WSpace = 50;
    @Override
    public void init() throws Exception {
        context = SpringApplication.run(Main.class);
    }
    @Override
    public void stop() throws Exception {
        context.close();
    }
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        setSize(pane);
        pane.setBackground(new Background(createBackgroundImage()));
        makeView(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private BackgroundImage createBackgroundImage() {
        Image image = new Image(Objects.requireNonNull(Game.class.getResource("/Images/BG/loginBG.jpg")).toExternalForm(), WIDTH, HEIGHT, false, false);
        return new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }

    public void setSize(Pane pane) {
        pane.setMaxWidth(WIDTH);
        pane.setMinWidth(WIDTH);
        pane.setMaxHeight(HEIGHT);
        pane.setMinHeight(HEIGHT);
    }

    private void makeView(Pane pane) {
        makeLabel(pane);
        makeFields(pane);
    }

    private void makeLabel(Pane pane) {
        Label showMenu = new Label("Hello! you are in the Login Menu. \n Please fill the requested data to login.");
        showMenu.setFont(Font.font("Algerian", 20));
        showMenu.setTextFill(Color.BLACK);
        showMenu.setLayoutX(WIDTH / 2 + WSpace);
        showMenu.setLayoutY(SPACE);
        pane.getChildren().add(showMenu);
    }

    private void makeFields (Pane pane){
        TextField usernameField = setStyleOfFields("Enter your username here.",7);
        PasswordField passwordField = setStyleOfPassword("Enter your password here.", 11);
        Button signIn = makeButton("Sign in",15);
        Button forgetPass = makeButton("Forget password",19);
        Button goToMenu = makeButton("Register",23);
        Button exit = makeButton("Exit",27);
        forgetPass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoginController.forgetPassword(usernameField.getText());
            }
        });
        goToMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneController.goToRegister();
            }
        });
        signIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoginController.login(usernameField.getText(),passwordField.getText());
            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        pane.getChildren().addAll(usernameField,passwordField,signIn,exit,forgetPass,goToMenu);
    }

    private TextField setStyleOfFields(String promptText, double SPACENum) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setStyle("-fx-background-color: #281602; -fx-text-fill: #e5e5e5; -fx-font-family: 'Times New Roman'; -fx-font-size: 14px;");
        textField.setPrefSize(WIDTH / 5, 30);
        textField.setLayoutX(WIDTH / 2 + WSpace * 5);
        textField.setLayoutY(SPACE * SPACENum);
        textField.setAlignment(Pos.CENTER);
        return textField;
    }
    private PasswordField setStyleOfPassword(String promptText, double SPACENum) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(promptText);
        passwordField.setStyle("-fx-background-color: #281602; -fx-text-fill: #e5e5e5; -fx-font-family: 'Times New Roman'; -fx-font-size: 14px;");
        passwordField.setPrefSize(WIDTH / 5, 30);
        passwordField.setLayoutX(WIDTH / 2 + WSpace * 5);
        passwordField.setLayoutY(SPACE * SPACENum);
        passwordField.setAlignment(Pos.CENTER);
        return passwordField;
    }
    private Button makeButton(String name, double SPACENum) {
        Button button = new Button(name);
        button.setFont(Font.font("Times New Roman", 16));
        button.setStyle("-fx-background-color: #281602; -fx-text-fill: #e5e5e5");
        button.setPrefSize(150, 30);
        button.setLayoutX(WIDTH / 2 + WSpace * 5 + 30);
        button.setLayoutY(SPACE * SPACENum);
        return button;
    }
}
