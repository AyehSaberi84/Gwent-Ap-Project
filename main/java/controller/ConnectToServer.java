package controller;

import javafx.application.Platform;
import model.Game;
import model.Tournament;
import view.ChatArea;
import view.GameMenu;
import view.Main;
import view.ShadowViews.RandomGames;
import view.ShadowViews.ScoreTable;
import view.ShowPastGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConnectToServer {
    private static PrintWriter output;
    private static BufferedReader input;
    private static Socket socket;
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private static List<String> randomClientName = new ArrayList<>();
    private static HashMap<String,String> mapOfClientName = new HashMap<>();

    public static void makeSocket() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public static void connectToServer() {
        try {
            new Thread(() -> {
                String message;
                try {
                    while ((message = ConnectToServer.input.readLine()) != null) {
                        System.out.println("Received from server: " + message);
                        String[] parts = message.split("#");
                        if (message.startsWith("OFFLINE:")) {
                            String playerName = message.substring(8);
                            Platform.runLater(() -> PreGameController.showAlert("User " + playerName + " is offline.\n" +
                                    "You cant start a game with her/him."));
                        } else if (message.startsWith("PERMISSION:")) {
                            String playerName = message.substring(11);
                            Platform.runLater(() -> gamePermission(playerName));
                        } else if (message.equals("GO TO PRE GAME")) {
                            Platform.runLater(SceneController::goToPreGameMenu);
                        } else if (message.equals("LOGOUT")) {
                            break;
                        } else if (message.equals("PLAYING")) {
                            Platform.runLater(() -> PreGameController.showAlert("this user is playing and you cant start a game with her/him."));
                        } else if (message.startsWith("ONLINE:")) {
                            String playerName = message.substring(7);
                            Platform.runLater(() -> ScoreTable.updateUserStatus(playerName, true));
                        } else if (message.startsWith("IS OFFLINE:")) {
                            String playerName = message.substring(11);
                            Platform.runLater(() -> ScoreTable.updateUserStatus(playerName, false));
                        } else if (message.startsWith("newGame")) {
                            PreGameController.makeNewGame(parts[1], parts[2], parts[3]);
                        } else if (parts[0].equals("placeCard")) {
                            GameController.enemyPlaceCard(parts[1], parts[3], parts[4]);
                        } else if (parts[0].equals("enemyHand")) {
                            GameController.setEnemyHand(parts);
                        } else if (parts[0].equals("ready")) {
                            GameController.setEnemyFaction(parts[1]);
                        } else if (parts[0].equals("placeSpecialCard")) {
                            GameController.enemyPlaceSpecialCard(parts);
                        } else if (parts[0].equals("useSpell")) {
                            GameController.enemyUseSpell(parts[1]);
                        } else if (parts[0].equals("placeWeatherCard")) {
                            GameController.enemyPlaceWeatherCard(parts[1]);
                        } else if (parts[0].equals("decoy")) {
                            GameController.enemyDecoy(parts[1], parts[2]);
                        } else if (parts[0].equals("yourTurn")) {

                            GameController.startTurn();
                        } else if (parts[0].equals("passed")) {
                            GameController.setEnemyPassed();
                        } else if (parts[0].equals("roundEnded")) {
                            Platform.runLater(() -> GameController.roundEnded(parts[1], parts[2]));
                        } else if (parts[0].equals("monster")) {
                            Platform.runLater(() -> GameController.clearMonster(parts[1], parts[2]));
                        } else if (parts[0].equals("clear")) {
                            Platform.runLater(() -> GameController.clearBoard(null, Game.getCurrentGame().getEnemy()));
                        } else if (parts[0].equals("northern")) {
                            Platform.runLater(() -> GameController.addToHandNorthern(parts[1], parts[2]));
                        } else if (parts[0].equals("enemyNorthern")) {
                            Platform.runLater(() -> GameController.addToEnemyHandNorthern(parts[1], parts[2]));
                        } else if (parts[0].equals("requestEndRound")) {
                            Platform.runLater(GameController::endRound);
                        } else if (parts[0].equals("setPlayerName")) {
                            Platform.runLater(() -> PreGameController.setPlayerName(parts[1]));
                        } else if (parts[0].equals("endGame")) {
                            Platform.runLater(() -> GameController.endGame(parts));
                        } else if (parts[0].equals("playRandom")) {
                            Platform.runLater(() -> GameController.enemyPlayRandom(parts[1]));
                        } else if (parts[0].equals("muster")) {
                            Platform.runLater(() -> GameController.enemyMuster(parts));
                        } else if (parts[0].equals("spy")) {
                            Platform.runLater(() -> GameController.enemySpy(parts));
                        } else if (parts[0].equals("kill")) {
                            Platform.runLater(() -> GameController.enemyKillCard(parts[1], parts[2]));
                        } else if (parts[0].equals("commander")) {
                            Platform.runLater(() -> GameController.setEnemyCommander(parts[1]));
                        } else if (parts[0].equals("tournament")) {
                            Platform.runLater(() -> Tournament.update(parts));
                        } else if (message.startsWith("SEND:")) {
                            String mainMessage = message.split(":")[1];
                            Platform.runLater(() -> GameMenu.showMessage(mainMessage));
                        } else if (message.startsWith("SEND EMOJI:")) {
                            Platform.runLater(() -> GameMenu.showMessage("😊"));
                        } else if (parts[0].equals("tournamentResult")) {
                            Platform.runLater(() -> Tournament.showResults(parts));
                        } else if (message.equals("CHAT")) {
                            Platform.runLater(GameMenu::addChatArea);
                        }
                        else if (message.startsWith("RECEIVE#")) {
                            String messageToChat = message.split("#")[1];
                            Platform.runLater(() -> ChatArea.appendText(messageToChat));
                        } else if (message.equals("CHAT ALERT")) {
                            Platform.runLater(() -> {
                                PreGameController.showAlert("Enemy is stop chatting.\ndont send any message and close the box.");
                            });
                        } else if (parts[0].equals("addFriend")) {
                            Platform.runLater(() -> ProfileController.friendRequest(parts[1]));
                        } else if (parts[0].equals("acceptRequest")) {
                            Platform.runLater(() -> ProfileController.addFriendAccepted(parts[1]));
                        } else if (message.startsWith("SHOW RANDOM REQUEST:")){
                            String name = message.split(":")[1];
                            randomClientName.add(name);
                        } else if (message.startsWith("SHOW RANDOM MAP:")) {
                            String key = message.split(":")[1];
                            String value = message.split(":")[2];
                            mapOfClientName.put(key,value);
                        } else if (message.equals("SHOW RANDOM PLAYERS.")) {
                            goToRandomGames();
                        } else if (message.equals("END OF SHOWING RANDOM GAMES")){
                            mapOfClientName.clear();
                            randomClientName.clear();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PrintWriter getOutput() {
        return output;
    }

    public static BufferedReader getInput() {
        return input;
    }

    private static void gamePermission(String name) {
        if (PreGameController.takeConfirmation("You have a game request from " + name + "\nDo you want to start a game with this user?")) {
            output.println("PRE GAME:" + name);
        }
    }

    public static void checkUserStatus(String username) {
        output.println("CHECK:" + username);
    }

    public static void goToRandomGames (){
        RandomGames randomGames = new RandomGames(randomClientName,mapOfClientName);
        Platform.runLater(() ->{
            try {
                randomGames.start(Main.stage);
            } catch (Exception e){
                e.fillInStackTrace();
            }
        });
    }
}