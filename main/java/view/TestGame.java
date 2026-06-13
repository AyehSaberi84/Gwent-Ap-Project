package view;

import controller.GameController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.Card;
import model.Game;
import model.Player;
import model.User;
import model.factions.Skellige;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestGame extends Application {

    public static Stage stage;
    public static Socket socket;
    public static BufferedReader input;
    public static PrintWriter output;
    private final String SERVER_ADDRESS = "localhost";
    private final int SERVER_PORT = 12345;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        output = new PrintWriter(socket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        new Thread(() -> {
            String message;
            try {
                while ((message = TestGame.input.readLine()) != null) {
                    System.out.println("Received from server: " + message);
                    String[] parts = message.split("#");
                    if (parts[0].equals("placeCard")) {
                        GameController.enemyPlaceCard(parts[1], parts[3], parts[4]);
                    } else if (parts[0].equals("enemyHand")) {
                        GameController.setEnemyHand(parts);
                    } else if (parts[0].equals("faction")) {
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
                    } else if (parts[0].equals("makeGame")) {
                        makeGame(false);
                    } else if (parts[0].equals("makeGameYourTurn")) {
                        makeGame(true);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        TestGame.stage = stage;
    }

    private static void makeGame(boolean turn) {
        Player player1 = new Player("s");
        player1.setDeck(Card.makeNeutralCards());
        Card.makeNeutralCards();
        player1.setFaction(new Skellige());
        Player player2 = new Player("e");
        Game game = new Game(player1, player2);

        output.println("faction#" + game.getPlayer1().getFaction().getName());
        Platform.runLater(() -> {
            try {
                if (turn) GameController.startTurn();
                new VetoCardMenu(game).start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
