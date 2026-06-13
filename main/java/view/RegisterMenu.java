package view;

import controller.RegisterController;
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
public class RegisterMenu extends Application {
    private ConfigurableApplicationContext context;
    private final double WIDTH = 1000;
    private final double HEIGHT = 600;
    private final double SPACE = 10;
    private final double WSpace = 50;

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

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(Main.class);
    }
    @Override
    public void stop() throws Exception {
        context.close();
    }

    private BackgroundImage createBackgroundImage() {
        Image image = new Image(Objects.requireNonNull(Game.class.getResource("/Images/BG/registerBG.jpg")).toExternalForm(), WIDTH, HEIGHT, false, false);
        return new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }

    private void makeView(Pane pane) {
        setLabel(pane);
        makeFields(pane);
    }

    private void setLabel(Pane pane) {
        Label showMenu = new Label("Hello! you are in the Register Menu. \n Please fill the requested data.");
        showMenu.setFont(Font.font("Algerian", 22));
        showMenu.setTextFill(Color.BLACK);
        showMenu.setLayoutX(WIDTH / 2 + WSpace);
        showMenu.setLayoutY(SPACE);
        pane.getChildren().add(showMenu);
    }

    private void makeFields(Pane pane) {

        TextField usernameField = setStyleOfFields("Enter your username here", 7);
        PasswordField passwordField = setStyleOfPassword("Enter your password here", 11);
        PasswordField passwordFieldAgain = setStyleOfPassword("Enter your password again", 15);
        TextField nickname = setStyleOfFields("Enter your nickname here", 19);
        TextField email = setStyleOfFields("Enter your email here", 23);
        Button register = makeButton("Sign Up", 27);
        Button random = makeButton("Random", 31);
        Button exit = makeButton("Exit", 35);
        pane.getChildren().addAll(usernameField, passwordField, passwordFieldAgain, nickname, email, register, random, exit);

        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RegisterController.register(usernameField.getText(), passwordField.getText(), passwordFieldAgain.getText()
                        , nickname.getText(), email.getText());
            }
        });
        random.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RegisterController.generateRandomPassword(usernameField.getText(), nickname.getText(), email.getText());
            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
    }

    private TextField setStyleOfFields(String promptText, double SPACENum) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setStyle("-fx-background-color: #e1a758; -fx-text-fill: #131111; -fx-font-family: 'Times New Roman'; -fx-font-size: 14px;");
        textField.setPrefSize(WIDTH / 3, 30);
        textField.setLayoutX(WIDTH / 2 + WSpace * 2);
        textField.setLayoutY(SPACE * SPACENum);
        textField.setAlignment(Pos.CENTER);
        return textField;
    }

    private PasswordField setStyleOfPassword(String promptText, double SPACENum) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(promptText);
        passwordField.setStyle("-fx-background-color: #e1a758; -fx-text-fill: #131111; -fx-font-family: 'Times New Roman'; -fx-font-size: 14px;");
        passwordField.setPrefSize(WIDTH / 3, 30);
        passwordField.setLayoutX(WIDTH / 2 + WSpace * 2);
        passwordField.setLayoutY(SPACE * SPACENum);
        passwordField.setAlignment(Pos.CENTER);
        return passwordField;
    }

    private Button makeButton(String name, double SPACENum) {
        Button button = new Button(name);
        button.setFont(Font.font("Times New Roman", 16));
        button.setStyle("-fx-background-color: #e1a758");
        button.setPrefSize(100, 30);
        button.setLayoutX(WIDTH / 2 + WSpace * 2 + 100);
        button.setLayoutY(SPACE * SPACENum);
        return button;
    }

    public void setSize(Pane pane) {
        pane.setMaxWidth(WIDTH);
        pane.setMinWidth(WIDTH);
        pane.setMaxHeight(HEIGHT);
        pane.setMinHeight(HEIGHT);
    }
}
