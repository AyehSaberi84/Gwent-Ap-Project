package controller;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.*;
import view.ShadowViews.FriendsList;
import view.ShadowViews.GameHistory;
import view.ShadowViews.ScoreTable;
import view.ShadowViews.UserInfo;


public class SceneController {
    public static void goToRegister() {
        RegisterMenu registerMenu = new RegisterMenu();
        try {
            registerMenu.start(Main.stage);
        } catch (Exception e){
            e.fillInStackTrace();
        }
    }

    public static void goToLogin() {
        LoginMenu loginMenu = new LoginMenu();
        try {
            loginMenu.start(Main.stage);
        } catch (Exception e){
            e.fillInStackTrace();
        }
    }

    public static void goToPreGameMenu() {
        PreGameMenu preGameMenu = new PreGameMenu();
        try {
            preGameMenu.start(Main.stage);
        } catch (Exception e){
            e.fillInStackTrace();
        }
    }

    public static void goToMain() {
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

    public static void goToMainMenu (){
        MainMenu mainMenu = new MainMenu();
        try {
            mainMenu.start(Main.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void goToGameHistory (Pane pane){
        GameHistory gameHistory = new GameHistory(pane);
        try {
            gameHistory.start(Main.stage);
        } catch (Exception e){
            e.fillInStackTrace();
        }
    }

    public static void goToScoreTable (Pane pane){
        ScoreTable scoreTable = new ScoreTable(pane);
        try {
            scoreTable.start(Main.stage);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public static void goToUserInfo(Pane pane) {
        UserInfo userInfo = new UserInfo(pane);
        try {
            userInfo.start(Main.stage);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public static void goToTournament() {
        TournamentMenu tournamentMenu = new TournamentMenu();
        try {
            tournamentMenu.start(Main.stage);
        } catch (Exception e) {
            e.fillInStackTrace();
            e.printStackTrace();
        }

    }

    public static void goToFriendsList(Pane pane) {
        FriendsList friendsList = new FriendsList(pane);
        try {
            friendsList.start(Main.stage);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
}