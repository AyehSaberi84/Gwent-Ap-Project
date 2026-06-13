package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import model.DataBase.UserDAO;
import model.Enum.Questions;
import model.Enum.Validation;
import model.User;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.stereotype.Component;
import view.Main;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class RegisterController {
    private static boolean linkClicked = false;

    public static void register(String username, String password, String checkPassword, String nickName, String email) {
        UserDAO userDAO = new UserDAO();
        if (userDAO.checkUsernameExistence(username)) {
            username = generateRandomUsername(username);
            if (takeConfirmation(username)) makeNewUser(username, password, nickName, email);
        } else checkDataOfUser(username, password, checkPassword, nickName, email);
    }

    private static String generateRandomUsername(String username) {
        Random random = new Random();
        StringBuilder newName = new StringBuilder(username);
        int length = random.nextInt(4) + 1; // Generate a random length between 1 and 4
        for (int i = 0; i < length; i++) {
            if (random.nextBoolean()) newName.append(random.nextInt(10)); // Append a random digit (0-9)
            else newName.append('-'); // Append a '-'
        }
        return newName.toString();
    }

    private static boolean takeConfirmation(String username) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Asking permission");
        alert.setHeaderText("Asking permission");
        alert.setContentText("Do you want to use username:" + username + "?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private static void checkDataOfUser(String username, String password, String checkPassword, String nickName, String email) {
        if (!Validation.isValid(username, Validation.USERNAME_VALIDATION))
            showAlert("This is not a valid username. \n Please enter a new one.");
        else if (!Validation.isValid(email, Validation.EMAIL_VALIDATION))
            showAlert("Please enter a valid email.");
        else if (!Validation.isValid(password, Validation.PASSWORD_VALIDATION))
            showAlert("You cant use these characters in your password.");
        else if (password.length() < 8)
            showAlert("Your password is short.");
        else if (!Validation.isValid(password, Validation.ALPHABETICAL_PASSWORD))
            showAlert("Your password doesnt contain alphabetical letters.");
        else if (!Validation.isValid(password, Validation.CHECK_NUMBER_PASSWORD))
            showAlert("Your password doesnt contain digits.");
        else if (!Validation.isValid(password, Validation.SPECIAL_CHARACTERS_PASSWORD))
            showAlert("Your password doesnt contain special characters.");
        else if (!password.equals(checkPassword))
            showOptionsOfPassword("Do you want to rewrite the password or go to register menu?",
                    "Go to register menu.", "Enter password again.");
        else makeNewUser(username, password, nickName, email);

    }
    private static void makeNewUser(String username, String password, String nickName, String email){
        String answer = showSecurityQuestions();
        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.registerUser(username, password, nickName, email, answer);
        if (success) {
            User user = new User(username, password, nickName, email, answer);
            User.setLoggedInUser(user);
            ConnectToServer.makeSocket();
            ConnectToServer.connectToServer();
            ConnectToServer.getOutput().println("REGISTER:" + username);
            SceneController.goToMainMenu();
        } else showAlert("Error in registering");
        userDAO.close();
    }
//    private static void makeNewUser(String username, String password, String nickName, String email) {
//        EmailSender.sendEmail(email, "http://localhost:8080/click");
//        Server server = new Server(8080);
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        context.setContextPath("/");
//        server.setHandler(context);
//        context.addServlet(new ServletHolder(new ClickServlet()), "/click");
//        try {
//            server.start();
//            Thread.sleep(60000);
//            if (linkClicked) {
//                String answer = showSecurityQuestions();
//                UserDAO userDAO = new UserDAO();
//                boolean success = userDAO.registerUser(username, password, nickName, email, answer);
//                if (success) {
//                    User user = new User(username, password, nickName, email, answer);
//                    User.setLoggedInUser(user);
//                    ConnectToServer.makeSocket();
//                    ConnectToServer.connectToServer();
//                    ConnectToServer.getOutput().println("REGISTER:" + username);
//                    SceneController.goToMainMenu();
//                } else showAlert("Error in registering");
//                userDAO.close();
//            } else {
//                showAlert("we cant register you because you didnt answer us.");
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("error message");
        alert.setHeaderText("error message");
        alert.setContentText(message);
        alert.show();
    }

    private static void showOptionsOfPassword(String contentText, String op1, String op2) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("show options.");
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        ButtonType optionOne = new ButtonType(op1);
        ButtonType optionTwo = new ButtonType(op2);
        alert.getButtonTypes().setAll(optionOne, optionTwo);
        alert.showAndWait().ifPresent(response -> {
            if (response == optionOne) goToRegisterMenu();
        });
    }

    public static void generateRandomPassword(String username, String nickname, String email) {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String DIGITS = "0123456789";
        String SPECIAL_CHARS = "@#$%^&+=";
        String PASSWORD_CHARS = CHAR_LOWER + CHAR_UPPER + DIGITS + SPECIAL_CHARS;
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        int length = 12;
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(PASSWORD_CHARS.length());
            password.append(PASSWORD_CHARS.charAt(randomIndex));
        }
        showOptionsOfRandomPassword(username, nickname, email, password.toString());
    }

    private static void showOptionsOfRandomPassword(String username, String nickname, String email, String password) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("show options.");
        alert.setHeaderText(null);
        alert.setContentText("This is your password : " + password);
        ButtonType op1 = new ButtonType("Go to register menu.");
        ButtonType op2 = new ButtonType("Generate a new password.");
        ButtonType op3 = new ButtonType("Register");
        alert.getButtonTypes().setAll(op1, op2, op3);
        alert.showAndWait().ifPresent(response -> {
            if (response == op1) goToRegisterMenu();
            else if (response == op2) generateRandomPassword(username, nickname, email);
            else if (response == op3) makeNewUser(username, password, nickname, email);
        });
    }

    private static void goToRegisterMenu() {
        Main main = new Main();
        if (main.scene == null) {
            try {
                main.start(Main.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            Stage stage = new Stage();
            stage.setScene(main.scene);
            stage.show();
        }
    }

    private static String showSecurityQuestions() {
        Optional<String> selectedQuestion = getString();
        if (selectedQuestion.isPresent()) {
            TextInputDialog answerDialog = new TextInputDialog();
            answerDialog.setTitle("Answer");
            answerDialog.setHeaderText(selectedQuestion.get());
            answerDialog.setContentText("Enter your answer:");
            Optional<String> result = answerDialog.showAndWait();
            if (result.isPresent()) return result.get();
        }
        return null;
    }

    private static Optional<String> getString() {
        List<String> questions = List.of(Questions.QUESTION1.getQuestion(), Questions.QUESTION2.getQuestion(),
                Questions.QUESTION3.getQuestion(), Questions.QUESTION4.getQuestion(), Questions.QUESTION5.getQuestion(),
                Questions.QUESTION6.getQuestion(), Questions.QUESTION7.getQuestion(), Questions.QUESTION8.getQuestion(),
                Questions.QUESTION9.getQuestion(), Questions.QUESTION10.getQuestion());

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Choose a question", questions);
        dialog.setTitle("Qs");
        dialog.setHeaderText("Choose a question");
        return dialog.showAndWait();
    }
    public static class ClickServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            linkClicked = true;
            resp.getWriter().println("Link clicked!");
        }
    }
}

