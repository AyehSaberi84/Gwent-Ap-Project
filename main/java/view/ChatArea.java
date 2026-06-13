package view;

import controller.ConnectToServer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatArea extends Application {
    private Pane pane;
    private BorderPane root;
    private static TextArea messageArea;
    private TextField textField;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ChatArea(Pane pane) {
        this.pane = pane;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chatting");
        root = new BorderPane();
        messageArea = new TextArea();
        messageArea.setEditable(false);
        messageArea.setWrapText(true);
        messageArea.setFont(Font.font("Arial", 14));
        messageArea.setStyle("-fx-text-fill: #f3f3f3; -fx-control-inner-background: rgb(70,39,1);");

        ScrollPane scrollPane = new ScrollPane(messageArea);
        root.setCenter(scrollPane);

        textField = new TextField();
        textField.setPromptText("Enter your message");
        textField.setFont(Font.font("Arial", 14));
        textField.setStyle("-fx-text-fill: #f3f3f3; -fx-control-inner-background: rgb(70,39,1);");
        textField.setOnAction(e -> sendMessage());

        Button sendButton =makeButton("Send");
        sendButton.setOnAction(e -> sendMessage());

        Button exit = makeButton("Exit");
        exit.setOnAction(e -> {
            ConnectToServer.getOutput().println("EXIT CHAT:" + User.getLoggedInUser().getUsername());
            root.setVisible(false);
        });

        HBox inputBox = new HBox(10, textField, sendButton, exit);
        inputBox.setPadding(new Insets(10));

        root.setBottom(inputBox);
        root.setLayoutY(300);
        root.setLayoutX(500);
        pane.getChildren().add(root);
    }

    private void sendMessage() {
        String time = dtf.format(LocalDateTime.now());
        String message = "Message from || " + User.getLoggedInUser().getUsername() + " at [" + time + "] || " + textField.getText();
        System.out.println(message);
        ConnectToServer.getOutput().println("CHAT MESSAGE#" + User.getLoggedInUser().getUsername() + "#" + message);
        textField.clear();
    }

    public static void appendText(String message) {
        messageArea.appendText(message + "\n");
    }

    public Button makeButton(String name) {
        Button button = new Button(name);
        button.setFont(Font.font("Times New Roman", 16));
        button.setStyle("-fx-background-color: #4b2a04; -fx-text-fill: #b1b4b1");
        button.setPrefSize(150, 30);
        return button;
    }
}
