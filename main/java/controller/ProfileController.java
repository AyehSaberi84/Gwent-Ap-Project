package controller;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;
import model.DataBase.UserDAO;
import model.User;
import model.Enum.Validation;
import view.Main;
import view.MainMenu;

import java.util.Optional;

public class ProfileController {
    private static UserDAO userDAO = new UserDAO();
    public static void changeUsername() {
        String username = makeInputDialog("Change username", "Enter a new Username", "Enter a new Username");
        if (!Validation.isValid(username, Validation.USERNAME_VALIDATION))
            showAlert("error message", "You cant use these characters in your username.");
        else if (User.getLoggedInUser().getUsername().equals(username))
            showAlert("error message", "Please enter a new username not the old one!");
        else {
            userDAO.updateUsername(User.getLoggedInUser().getUsername(),username);
            User.getLoggedInUser().setUsername(username);
            showAlert("Do successfully", "We changed your username successfully!");
        }
    }

    public static void changeNickname() {
        String nickname = makeInputDialog("Change nickname", "Enter a new nickname", "Enter a new nickname");
        if (User.getLoggedInUser().getNickname().equals(nickname))
            showAlert("error message", "Please enter a new nickname!");
        else {
            userDAO.updateNickname(User.getLoggedInUser().getUsername(), nickname);
            User.getLoggedInUser().setNickname(nickname);
            showAlert("Do successfully", "We changed your nickname successfully.");
        }
    }

    public static void changeEmail() {
        String email = makeInputDialog("Change Email", "Please enter a new Email", "Please enter a new email");
        if (!Validation.isValid(email, Validation.EMAIL_VALIDATION))
            showAlert("error message", "We dont support this format for the email");
        else if (User.getLoggedInUser().getEmail().equals(email))
            showAlert("error message", "Please enter a new email!");
        else {
            userDAO.updateEmail(User.getLoggedInUser().getUsername(), email);
            User.getLoggedInUser().setEmail(email);
            showAlert("Do successfully", "We changed your email successfully.");
        }
    }

    public static void changePassword() {
        Optional<Pair<String, String>> result = showTwoInputDialog();
        result.ifPresent(inputs -> {
            checkPassword(inputs.getKey(), inputs.getValue());
        });
    }

    private static void checkPassword(String oldPassword, String newPassword) {
        if (!Validation.isValid(newPassword, Validation.PASSWORD_VALIDATION))
            showAlert("error message", "You cant use these characters in your password.");
        else if (newPassword.length() < 8)
            showAlert("error message", "Your password is short.");
        else if (!Validation.isValid(newPassword, Validation.ALPHABETICAL_PASSWORD))
            showAlert("error message", "Your password doesnt contain alphabetical letters.");
        else if (!Validation.isValid(newPassword, Validation.CHECK_NUMBER_PASSWORD))
            showAlert("error message", "Your password doesnt contain digits.");
        else if (!Validation.isValid(newPassword, Validation.SPECIAL_CHARACTERS_PASSWORD))
            showAlert("error message", "Your password doesnt contain special characters.");
        else if (newPassword.equals(User.getLoggedInUser().getPassword()))
            showAlert("error message", "Please enter a new password!");
        else if (!oldPassword.equals(User.getLoggedInUser().getPassword()))
            showAlert("error message", "The old password you've entered is wrong.");
        else {
            userDAO.updatePassword(User.getLoggedInUser().getUsername(), newPassword);
            User.getLoggedInUser().setPassword(newPassword);
            showAlert("Do successfully", "We changed your password successfully.");
        }
    }

    public static void gameHistory() {

    }

    private static String makeInputDialog(String title, String header, String content) {
        TextInputDialog answerDialog = new TextInputDialog();
        answerDialog.setTitle(title);
        answerDialog.setHeaderText(header);
        answerDialog.setContentText(content);
        Optional<String> result = answerDialog.showAndWait();
        return result.orElse(null);
    }

    public static void goToMainMenu() {
        MainMenu mainMenu = new MainMenu();
        try {
            mainMenu.start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.show();
    }

    private static Optional<Pair<String, String>> showTwoInputDialog() {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Change password");
        dialog.setHeaderText("Please enter a new password");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the input fields and labels.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField firstInput = new TextField();
        firstInput.setPromptText("First Input");
        TextField secondInput = new TextField();
        secondInput.setPromptText("Second Input");

        grid.add(new Label("Old password:"), 0, 0);
        grid.add(firstInput, 1, 0);
        grid.add(new Label("New password:"), 0, 1);
        grid.add(secondInput, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(firstInput.getText(), secondInput.getText());
            }
            return null;
        });

        return dialog.showAndWait();
    }

    public static void addFriend() {
        String playerName = makeInputDialog("Add Friend", "Enter The Name Of User", "Enter The Name Of User");
        if (playerName == null) showAlert("Invalid Name", "Please enter a name!");
        else if (User.getUserByUsername(playerName) == null) showAlert("Invalid Name", "There is no user with this name.");
        else if (playerName.equals(User.getLoggedInUser().getUsername()))
            showAlert("Invalid Name", "You can't add yourself as a friend");
        else if (alreadyBefriended(playerName))
            showAlert("Invalid Name", "You have already added this user as a friend");
        else {
            ConnectToServer.getOutput().println("addFriend#" + playerName);
            showAlert("Success", "Request sent!");
        }
    }

    private static boolean alreadyBefriended(String name) {
        for (Node node : User.getLoggedInUser().getFriends().getChildren()) {
            if (!(node instanceof HBox)) continue;
            if (((Label) ((HBox) node).getChildren().getFirst()).getText().equals(name)) return true;
        }
        return false;
    }

    public static void friendRequest(String name) {
        if (alreadyHasRequest(name)) return;
        HBox hBox = new HBox();
        Label label = new Label(name);
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(18));
        Button accept = new Button("Accept");
        accept.setOnAction(actionEvent -> {
            ConnectToServer.getOutput().println("acceptRequest#" + name);
            addFriendAccepted(name);
            User.getLoggedInUser().getRequests().getChildren().remove(hBox);
        });
        Button reject = new Button("Reject");
        reject.setOnAction(actionEvent -> User.getLoggedInUser().getRequests().getChildren().remove(hBox));
        hBox.getChildren().addAll(label, accept, reject);
        hBox.setSpacing(10);
        User.getLoggedInUser().getRequests().getChildren().add(hBox);
    }

    public static void addFriendAccepted(String name) {
        HBox hBox = new HBox();
        Label label = new Label(name);
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(18));
        Button play = new Button("Play");
        play.setOnAction(actionEvent -> ConnectToServer.getOutput().println("START:" + name));
        hBox.getChildren().addAll(label, play);
        hBox.setSpacing(10);
        User.getLoggedInUser().getFriends().getChildren().add(hBox);

    }

    private static boolean alreadyHasRequest(String name) {
        for (Node node : User.getLoggedInUser().getRequests().getChildren()) {
            if (!(node instanceof HBox)) continue;
            if (((Label) ((HBox) node).getChildren().getFirst()).getText().equals(name)) return true;
        }
        return false;
    }
}
