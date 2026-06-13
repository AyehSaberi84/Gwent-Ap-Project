package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import model.DataBase.UserDAO;
import model.User;
import model.Enum.Validation;
import org.springframework.stereotype.Component;
import view.Main;
import view.MainMenu;

import java.util.Objects;
import java.util.Optional;

import static view.Main.stage;

@Component
public class LoginController {
//    public static void login(String username, String password) {
//        UserDAO userDAO = new UserDAO();
//        if (!userDAO.checkUsernameExistence(username))
//            showAlert("There is no user with this username.");
//        else {
//            User user = User.getUserByUsername(username);
//            if (user == null || !Objects.requireNonNull(user).getPassword().equals(password))
//                showAlert("You Entered a wrong password. try again.");
//            else {
//                User.setLoggedInUser(User.getUserByUsername(username));
//
//                ConnectToServer.makeSocket();
//                ConnectToServer.connectToServer();
//                ConnectToServer.getOutput().println("LOGIN:" + username);
//                SceneController.goToMainMenu();
//            }
//        }
//
//    }

    public static void login(String username, String password) {
        UserDAO userDAO = new UserDAO();
        if (!userDAO.checkUsernameExistence(username))
            showAlert("There is no user with this username.");
        else {
            User user = User.getUserByUsername(username);
            if (user == null || !Objects.requireNonNull(user).getPassword().equals(password))
                showAlert("You Entered a wrong password. try again.");
            else TwoFAPassword(user,username);
        }

    }

    private static void TwoFAPassword(User user, String username) {
        OTPGenerator otpGenerator = new OTPGenerator();
        String otp = otpGenerator.generateOTP();
        EmailSender.sendEmailForLogin(user.getEmail(), "Your OTP Code", "Your OTP code is: " + otp);

        String userOtp = makeInputDialog("2FA","Second Password","Enter the second password that mail you.");

        if (otpGenerator.verifyOTP(userOtp.trim())) {
            User.setLoggedInUser(User.getUserByUsername(username));
            ConnectToServer.makeSocket();
            ConnectToServer.connectToServer();
            ConnectToServer.getOutput().println("LOGIN:" + username);
            SceneController.goToMainMenu();
        } else showAlert("You entered wrong password.\n You cant login to your account");
    }

    public static void forgetPassword(String username) {
        if (username == null) showAlert("Please enter your username first!");
        else if (User.getUserByUsername(username) == null) showAlert("There is no user with this name.");
        else showQuestion(username);
    }

    private static void showQuestion(String username) {
        TextInputDialog answerDialog = new TextInputDialog();
        answerDialog.setTitle("Answer");
        answerDialog.setHeaderText("Answer:");
        answerDialog.setContentText("Enter your answer:");
        Optional<String> answer = answerDialog.showAndWait();
        answer.ifPresent(s -> passwordCheck(s, username));
    }

    private static void passwordCheck(String answer, String username) {
        User user = User.getUserByUsername(username);
        assert user != null;
        if (!answer.equals(user.getAnswer()))
            showAlert("This is a wrong answer.");
        else {
            TextInputDialog answerDialog = new TextInputDialog();
            answerDialog.setTitle("New password");
            answerDialog.setHeaderText("New password:");
            answerDialog.setContentText("Enter your password:");
            Optional<String> password = answerDialog.showAndWait();
            password.ifPresent(s -> correctPassword(s, user));
        }
    }

    private static void correctPassword(String password, User user) {
        if (!Validation.isValid(password, Validation.PASSWORD_VALIDATION))
            showAlert("You cant use these characters in your password.");
        else if (password.length() < 8)
            showAlert("Your password is short.");
        else if (!Validation.isValid(password, Validation.ALPHABETICAL_PASSWORD))
            showAlert("Your password doesnt contain alphabetical letters.");
        else if (!Validation.isValid(password, Validation.CHECK_NUMBER_PASSWORD))
            showAlert("Your password doesnt contain digits.");
        else if (!Validation.isValid(password, Validation.SPECIAL_CHARACTERS_PASSWORD))
            showAlert("Your password doesnt contain special characters.");
        else {
            user.setPassword(password);
            showAlert("Password changed successfully.");
        }
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("error message");
        alert.setHeaderText("error message");
        alert.setContentText(message);
        alert.show();
    }

    public static String makeInputDialog(String title, String header, String content) {
        TextInputDialog answerDialog = new TextInputDialog();
        answerDialog.setTitle(title);
        answerDialog.setHeaderText(header);
        answerDialog.setContentText(content);
        Optional<String> result = answerDialog.showAndWait();
        return result.orElse(null);
    }
}
