package controller;

import javafx.application.Platform;
import model.Game;
import model.GameData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MainServer {

    private static final int PORT = 12345;
    private static List<ClientHandler> handlers = Collections.synchronizedList(new ArrayList<>());
    private static List<ClientHandler> chatUser = Collections.synchronizedList(new ArrayList<>());
    private static List<ClientHandler> randomHandlers = Collections.synchronizedList(new ArrayList<>());
    private static HashMap<ClientHandler, ClientHandler> mapOfRandomGames = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public static class ClientHandler extends Thread {
        private Socket socket;
        private boolean playing = false;
        private ClientHandler enemy;
        private PrintWriter output;
        private String clientName;
        private String privacy;
        private boolean ready;
        private String faction;
        private GameData gameData;
        private String[] northern = new String[2];

        private int currentRound = -1;

        private int score = -1;

        private final ArrayList<ClientHandler> friends = new ArrayList<>();

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                output = new PrintWriter(socket.getOutputStream(), true);
                handlers.add(this);

                String request;
                while ((request = input.readLine()) != null) {
                    System.out.println("Received: " + request);
                    System.out.println("this is for debug and don't forget to remove it :" + request + "#" + clientName);
                    if (request.startsWith("REGISTER:")) {
                        clientName = request.substring(9);
                    } else if (request.startsWith("LOGIN:")) {
                        clientName = request.substring(6);
                    } else if (request.startsWith("START:")) {
                        checkPrivacy(request, clientName);
                    } else if (request.startsWith("RANDOM:")) {
                        randomGame(request);
                    } else if (request.startsWith("LOGOUT:")) {
                        logOutUser(request, output);
                    } else if (request.split("#")[0].equals("placeCard")) {

                        enemy.sendMessage(request);

                    } else if (request.split("#")[0].equals("enemyHand")) {
                        enemy.sendMessage(request);
                    } else if (request.split("#")[0].equals("faction")) {
                        faction = request.split("#")[1];
                    } else if (request.split("#")[0].equals("placeSpecialCard")) {


                        enemy.sendMessage(request);

                    } else if (request.split("#")[0].equals("useSpell")) {

                        enemy.sendMessage(request);

                    } else if (request.split("#")[0].equals("placeWeatherCard")) {
                        enemy.sendMessage(request);

                    } else if (request.split("#")[0].equals("decoy")) {
                        enemy.sendMessage(request);
                    } else if (request.split("#")[0].equals("yourTurn")) {
                        enemy.sendMessage(request);
                    } else if (request.split("#")[0].equals("passed")) {
                        enemy.sendMessage(request);
                    } else if (request.startsWith("PRE GAME:")) {
                        goToPreGame(request, output);
                    } else if (request.startsWith("CHECK:")) {
                        showOnlineOffline(request);
                    } else if (request.startsWith("ready")) {
                        if (enemy.ready) {
                            readyToPlay();
                        } else ready = true;
                    } else if (request.equals("CHAT")) {
                        chatUser.add(this);
                        if (chatUser.size() == 2) chatting(chatUser.getFirst(), chatUser.getLast());
                    } else if (request.startsWith("CHAT MESSAGE#")) {
                        sendMessageToChatBox(request);
                    } else if (request.startsWith("EXIT CHAT")) {
                        if (!chatUser.isEmpty()) alertEnemyForChat(request);
                    } else if (request.split("#")[0].equals("end")) {
                        endRound(request.split("#"));
                    } else if (request.split("#")[0].equals("requestEndRound")) {
                        enemy.sendMessage(request);
                    } else if (request.equals("getPlayerName")) {
                        sendMessage("setPlayerName#" + clientName);
                    } else if (request.startsWith("playRandom")) {
                        enemy.sendMessage(request);
                    } else if (request.startsWith("muster")) {
                        enemy.sendMessage(request);
                    } else if (request.startsWith("spy")) {
                        enemy.sendMessage(request);
                    } else if (request.startsWith("kill")) {
                        enemy.sendMessage(request);
                    } else if (request.startsWith("commander")) {
                        enemy.sendMessage(request);
                    } else if (request.startsWith("joinTournament")) {
                        TournamentData.addPlayer(this);
                        sendMessage("tournament" + TournamentData.getInformation());
                    } else if (request.equals("getTournamentUpdate")) {
                        sendMessage("tournament" + TournamentData.getInformation());
                    } else if (request.startsWith("SEND:")) {
                        broadcast(request);
                    } else if (request.startsWith("SEND EMOJI:")) {
                        broadCastEmoji(request);
                    } else if (request.startsWith("addFriend")) {
                        ClientHandler friend = getHandlerByName(request.split("#")[1]);
                        if (friend != null) {
                            friend.sendMessage("addFriend#" + clientName);
                            friends.add(friend);
                        }
                    } else if (request.startsWith("acceptRequest")) {
                        ClientHandler friend = getHandlerByName(request.split("#")[1]);
                        if (friend != null) {
                            friend.sendMessage("acceptRequest#" + clientName);
                            friends.add(friend);
                        }
                    } else if (request.startsWith("SHOW RANDOM GAMES:")) {
                        showRandomGames(request);
                    } else if (request.equals("END OF SHOWING RANDOM GAMES")) {
                        sendMessage("END OF SHOWING RANDOM GAMES");
                    } else {
                        output.println("Unknown command");
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception error in server : the client is disconnected!");
                if (enemy != null) endGame(enemy.clientName, true);
            }
        }

        private ClientHandler getHandlerByName(String name) {
            for (ClientHandler handler : handlers) {
                if (handler.clientName.equals(name)) return handler;
            }
            return null;
        }

        private void showRandomGames(String request) {
            String name = request.split(":")[1];
            for (ClientHandler clientHandler : randomHandlers) {
                clientHandler.sendMessage("SHOW RANDOM REQUEST:" + clientHandler.clientName);
            }
            for (Map.Entry<ClientHandler, ClientHandler> entry : mapOfRandomGames.entrySet()) {
                ClientHandler clientHandler = entry.getKey();
                String key = entry.getKey().clientName;
                String value = entry.getValue().clientName;
                clientHandler.sendMessage("SHOW RANDOM MAP:" + key + ":" + value);
            }
            ClientHandler clientHandlerMain = checkOnlinePeople(name);
            assert clientHandlerMain != null;
            clientHandlerMain.sendMessage("SHOW RANDOM PLAYERS.");
        }

        private void alertEnemyForChat(String request) {
            String name = request.split(":")[1];
            String mainName;
            if (chatUser.getFirst().clientName.equals(name)) mainName = chatUser.getLast().clientName;
            else mainName = chatUser.getFirst().clientName;
            ClientHandler clientHandler = checkOnlinePeople(mainName);
            assert clientHandler != null;
            clientHandler.sendMessage("CHAT ALERT");
            chatUser.clear();
        }

        private void readyToPlay() {
            String myTurn;
            String enemyTurn;
            if (faction.equals("ScoiaTeal") && !enemy.faction.equals("ScoiaTeal")) {
                myTurn = "#yourTurn";
                enemyTurn = "#no";
            } else if (!faction.equals("ScoiaTeal") && enemy.faction.equals("ScoiaTeal")) {
                myTurn = "#no";
                enemyTurn = "#yourTurn";
            } else {
                Random random = new Random();
                if (random.nextInt(2) == 0) {
                    myTurn = "#yourTurn";
                    enemyTurn = "#no";
                } else {
                    myTurn = "#no";
                    enemyTurn = "#yourTurn";
                }
            }
            sendMessage("newGame#" + enemy.faction + "#" + enemy.clientName + myTurn);
            enemy.sendMessage("newGame#" + this.faction + "#" + this.clientName + enemyTurn);
        }

        private static void startGame(ClientHandler player1, ClientHandler player2, boolean isTournament) {
            player1.playing = true;
            player2.playing = true;
            player1.sendMessage("GO TO PRE GAME");
            player2.sendMessage("GO TO PRE GAME");
            player1.setEnemy(player2);
            player2.setEnemy(player1);
            GameData data = new GameData();
            data.isTournament = isTournament;
            player1.gameData = data;
            player2.gameData = data;
            data.p1name = player1.clientName;
            data.p2name = player2.clientName;
            player1.currentRound = 0;
            player2.currentRound = 0;
        }

        public void sendMessage(String message) {
            output.println(message);
        }

        public void setEnemy(ClientHandler enemy) {
            this.enemy = enemy;
        }

        public static ClientHandler checkOnlinePeople(String name) {
            for (ClientHandler clientHandler : handlers) {
                if (clientHandler.clientName.equals(name)) return clientHandler;
            }
            return null;
        }

        private void goToPreGame(String request, PrintWriter output) {
            String name = request.substring(9);
            ClientHandler clientHandler = checkOnlinePeople(name);
            assert clientHandler != null;
            if (clientHandler.playing) {
                output.println("PLAYING");
            } else {
                startGame(clientHandler, this, false);
            }
        }

        private void checkPrivacy(String request, String clientName) {
            String name = request.substring(6);
            if (checkOnlinePeople(name.trim()) != null) {
                ClientHandler clientHandler = checkOnlinePeople(name);
                assert clientHandler != null;
                clientHandler.sendMessage("PERMISSION:" + clientName);
            } else sendMessage("OFFLINE:" + name);
        }

        private void randomGame(String request) {
            privacy = request.substring(7);
            randomHandlers.add(this);
            if (randomHandlers.size() >= 2) {
                if (randomHandlers.getFirst().privacy.equals(randomHandlers.getLast().privacy)
                        && !randomHandlers.getFirst().clientName.equals(randomHandlers.getLast().clientName)) {
                    startGame(randomHandlers.getFirst(), randomHandlers.getLast(), false);
                    randomHandlers.removeFirst();
                    randomHandlers.removeLast();
                }
            }
        }

        private void logOutUser(String request, PrintWriter output) {
            String name = request.substring(7);
            ClientHandler clientHandler = checkOnlinePeople(name);
            handlers.remove(clientHandler);
            output.println("LOGOUT");
        }

        private void showOnlineOffline(String request) {
            String name = request.substring(6);
            ClientHandler clientHandler = checkOnlinePeople(name);
            if (clientHandler != null) sendMessage("ONLINE:" + name);
            else sendMessage("IS OFFLINE:" + name);
        }

        private void endRound(String[] parts) {
            StringBuilder message = new StringBuilder("roundEnded#");
            StringBuilder enemyMessage = new StringBuilder("roundEnded#");
            if (gameData.p1name.equals(clientName)) gameData.p1score[currentRound] = parts[1];
            else gameData.p2score[currentRound] = parts[1];
            score = Integer.parseInt(parts[1]);
            if (enemy.score != -1) {
                if (score > enemy.score) {
                    message.append("win#");
                    enemyMessage.append("lose#");
                } else if (score < enemy.score) {
                    message.append("lose#");
                    enemyMessage.append("win#");
                } else if (faction.equals("Nilfgaardian Empire") && !enemy.faction.equals("Nilfgaardian Empire")) {
                    message.append("win#");
                    enemyMessage.append("lose#");
                } else if (!faction.equals("Nilfgaardian Empire") && enemy.faction.equals("Nilfgaardian Empire")) {
                    message.append("lose#");
                    enemyMessage.append("win#");
                } else {
                    message.append("draw#");
                    enemyMessage.append("draw#");
                }
                if (faction.equals("ScoiaTeal") && !enemy.faction.equals("ScoiaTeal")) {
                    message.append("yourTurn");
                    enemyMessage.append("no");
                } else if (!faction.equals("ScoiaTeal") && enemy.faction.equals("ScoiaTeal")) {
                    message.append("no");
                    enemyMessage.append("yourTurn");
                } else if (message.toString().startsWith("roundEnded#win")) {
                    message.append("yourTurn");
                    enemyMessage.append("no");
                } else if (enemyMessage.toString().startsWith("roundEnded#win")) {
                    message.append("no");
                    enemyMessage.append("yourTurn");
                } else {
                    Random random = new Random();
                    if (random.nextInt(2) == 0) {
                        message.append("yourTurn");
                        enemyMessage.append("no");
                    } else {
                        message.append("no");
                        enemyMessage.append("yourTurn");
                    }
                }
                sendMessage(message.toString());
                enemy.sendMessage(enemyMessage.toString());
                setGameDataWin(message.toString());
                currentRound++;
                enemy.currentRound++;
                score = -1;
                enemy.score = -1;
                String winner = gameData.winner(currentRound);
                if (message.toString().startsWith("roundEnded#win") && faction.equals("Realm Northern")) {
                    sendMessage("northern#" + parts[2] + "#" + parts[3]);
                    enemy.sendMessage("enemyNorthern#" + parts[2] + "#" + parts[3]);
                }
                if (enemyMessage.toString().startsWith("roundEnded#win") && enemy.faction.equals("Realm Northern")) {
                    enemy.sendMessage("northern#" + enemy.northern[0] + "#" + enemy.northern[1]);
                    sendMessage("enemyNorthern#" + enemy.northern[0] + "#" + enemy.northern[1]);
                }
                if (winner != null) endGame(winner, false);
            } else {
                northern[0] = parts[0];
                northern[1] = parts[1];
            }

            if (faction.equals("Monster") && parts.length > 2)
                enemy.sendMessage("monster#" + parts[2] + "#" + parts[3]);
            else enemy.sendMessage("clear");

        }

        private void endGame(String winner, boolean disconnected) {
            String endGameMessage = "endGame#" + gameData.p1name + "#" + gameData.p1score[0] + "#" + gameData.p1win[0]
                    + "#" + gameData.p1score[1] + "#" + gameData.p1win[1] + "#" + gameData.p1score[2] + "#" + gameData.p1win[2]
                    + "#" + gameData.p2name + "#" + gameData.p2score[0] + "#" + gameData.p2win[0] + "#" + gameData.p2score[1]
                    + "#" + gameData.p2win[1] + "#" + gameData.p2score[2] + "#" + gameData.p2win[2] + "#" + winner;
            gameData.winner = winner;
            sendMessage(endGameMessage + "#" + gameData.isTournament + "#" + disconnected);
            enemy.sendMessage(endGameMessage + "#" + gameData.isTournament + "#" + disconnected);
            ready = false;
            enemy.ready = false;
            playing = false;
            enemy.playing = false;
            currentRound = -1;
            enemy.currentRound = -1;
            if (gameData.isTournament) TournamentData.playerWon(winner);
            enemy.enemy = null;
            enemy = null;
        }

        private void setGameDataWin(String str) {
            if (str.startsWith("roundEnded#win")) {
                if (gameData.p1name.equals(clientName)) {
                    gameData.p1win[currentRound] = true;
                    gameData.p2win[currentRound] = false;
                } else {
                    gameData.p1win[currentRound] = false;
                    gameData.p2win[currentRound] = true;
                }
            } else if (str.startsWith("roundEnded#lose")) {
                if (gameData.p1name.equals(clientName)) {
                    gameData.p1win[currentRound] = false;
                    gameData.p2win[currentRound] = true;
                } else {
                    gameData.p1win[currentRound] = true;
                    gameData.p2win[currentRound] = false;
                }
            } else {
                gameData.p1win[currentRound] = false;
                gameData.p2win[currentRound] = false;
            }
        }

        private void broadcast(String request) {
            String message = request.split(":")[1];
            String name = request.split(":")[2];
            String enemyName = request.split(":")[3];
            ClientHandler enemyHandler = checkOnlinePeople(enemyName.trim());
            assert enemyHandler != null;
            enemyHandler.sendMessage("SEND:" + message + ":" + name);
        }

        private void broadCastEmoji(String request) {
            String name = request.split(":")[1];
            String enemyName = request.split(":")[2];
            ClientHandler enemyHandler = checkOnlinePeople(enemyName.trim());
            assert enemyHandler != null;
            enemyHandler.sendMessage("SEND EMOJI:" + name + ":" + enemyName);
        }

        private void chatting(ClientHandler clientHandler1, ClientHandler clientHandler2) {
            clientHandler1.sendMessage("CHAT");
            clientHandler2.sendMessage("CHAT");
        }

        private void sendMessageToChatBox(String request) {
            String sender = request.split("#")[1];
            String message = request.split("#")[2];
            ClientHandler receiver;
            ClientHandler senderClient;
            if (chatUser.getFirst().clientName.equals(sender.trim())) {
                senderClient = chatUser.getFirst();
                receiver = chatUser.getLast();
            } else {
                senderClient = chatUser.getLast();
                receiver = chatUser.getFirst();
            }
            senderClient.sendMessage("RECEIVE#" + message);
            receiver.sendMessage("RECEIVE#" + message);
        }

    }

    public static class TournamentData {

        static ClientHandler[] players = new ClientHandler[8];
        static ClientHandler[] r1winners = new ClientHandler[4];
        static ClientHandler[] r1losers = new ClientHandler[4];
        static ClientHandler[] r2winners = new ClientHandler[2];
        static ClientHandler[] r2losers = new ClientHandler[2];
        static ClientHandler[] losersWinners = new ClientHandler[2];

        static String[] playersC = new String[8];
        static String[] r1winnersC = new String[4];
        static String[] r1losersC = new String[4];
        static String[] r2winnersC = new String[2];
        static String[] r2losersC = new String[2];
        static String[] losersWinnersC = new String[2];
        static ClientHandler[] top5 = new ClientHandler[5];
        static boolean started = false;

        static int round1Games = 0;
        static int round2Games = 0;
        static int round3Games = 0;

        static int numberOfPlayers = 0;

        static void addPlayer(ClientHandler player) {
            if (started) return;
            players[numberOfPlayers] = player;
            numberOfPlayers++;
            if (numberOfPlayers == 8) {
                started = true;
                numberOfPlayers = 0;
                startRound1();
            }
        }

        static String getInformation() {
            StringBuilder info = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                appendInfo(players[i], playersC[i], info);
            }
            appendInfo(r1winners[0], r1winnersC[0], info);
            appendInfo(r1winners[1], r1winnersC[1], info);
            appendInfo(r1losers[0], r1losersC[0], info);
            appendInfo(r1losers[1], r1losersC[1], info);
            appendInfo(r1winners[2], r1winnersC[2], info);
            appendInfo(r1winners[3], r1winnersC[3], info);
            appendInfo(r1losers[2], r1losersC[2], info);
            appendInfo(r1losers[3], r1losersC[3], info);
            appendInfo(r2winners[0], r2winnersC[0], info);
            appendInfo(r2winners[1], r2winnersC[1], info);
            appendInfo(r2losers[0], r2losersC[0], info);
            appendInfo(r2losers[1], r2losersC[1], info);
            appendInfo(losersWinners[0], losersWinnersC[0], info);
            appendInfo(losersWinners[1], losersWinnersC[1], info);
            info.append("#").append(started);

            return info.toString();
        }

        private static void appendInfo(ClientHandler player, String c, StringBuilder info) {
            info.append("#").append(clientHandlerName(player)).append("@").append(clientHandlerCondition(c));
        }

        private static String clientHandlerName(ClientHandler clientHandler) {
            if (clientHandler == null) return "-";
            return clientHandler.clientName;
        }

        private static String clientHandlerCondition(String c) {
            if (c == null) return "N";
            return c;
        }

        private static void startRound1() {
            ClientHandler.startGame(players[0], players[1], true);
            ClientHandler.startGame(players[2], players[3], true);
            ClientHandler.startGame(players[4], players[5], true);
            ClientHandler.startGame(players[6], players[7], true);
        }

        private static void startRound2() {
            ClientHandler.startGame(r1winners[0], r1winners[1], true);
            ClientHandler.startGame(r1winners[2], r1winners[3], true);
            ClientHandler.startGame(r1losers[0], r1losers[1], true);
            ClientHandler.startGame(r1losers[2], r1losers[3], true);
        }

        private static void startRound3() {
            ClientHandler.startGame(r2winners[0], r2winners[1], true);
            ClientHandler.startGame(r2losers[0], r2losers[1], true);
            ClientHandler.startGame(losersWinners[0], losersWinners[1], true);
        }

        private static void playerWon(String playerName) {
            for (int i = 0; i < players.length; i++) {
                if (players[i].clientName.equals(playerName) && clientHandlerCondition(playersC[i]).equals("N")) {
                    r1winners[i / 2] = players[i];
                    playersC[i] = "W";
                    int j = enemyIndex(i);
                    r1losers[j / 2] = players[j];
                    playersC[j] = "L";
                    updateForPlayers();
                    round1Played();
                    return;
                }
            }
            for (int i = 0; i < r1winners.length; i++) {
                if (r1winners[i].clientName.equals(playerName) && clientHandlerCondition(r1winnersC[i]).equals("N")) {
                    r2winners[i / 2] = r1winners[i];
                    r1winnersC[i] = "W";
                    int j = enemyIndex(i);
                    r2losers[j / 2] = r1winners[j];
                    r1winnersC[j] = "L";
                    updateForPlayers();
                    round2Played();
                    return;
                }
            }
            for (int i = 0; i < r1losers.length; i++) {
                if (r1losers[i].clientName.equals(playerName) && clientHandlerCondition(r1losersC[i]).equals("N")) {
                    losersWinners[i / 2] = r1losers[i];
                    r1losersC[i] = "W";
                    updateForPlayers();
                    round2Played();
                    return;
                }
            }
            for (int i = 0; i < r2winners.length; i++) {
                if (r2winners[i].clientName.equals(playerName) && clientHandlerCondition(r2winnersC[i]).equals("N")) {
                    top5[0] = r2winners[i];
                    r2winnersC[i] = "W";
                    int j = enemyIndex(i);
                    top5[1] = r2winners[j];
                    r2winnersC[j] = "L";
                    updateForPlayers();
                    round3Played();
                    return;
                }
            }
            for (int i = 0; i < r2losers.length; i++) {
                if (r2losers[i].clientName.equals(playerName) && clientHandlerCondition(r2losersC[i]).equals("N")) {
                    top5[2] = r2losers[i];
                    r2losersC[i] = "W";
                    int j = enemyIndex(i);
                    top5[3] = r2losers[j];
                    r2losersC[j] = "L";
                    updateForPlayers();
                    round3Played();
                    return;
                }
            }
            for (int i = 0; i < losersWinners.length; i++) {
                if (losersWinners[i].clientName.equals(playerName) && clientHandlerCondition(losersWinnersC[i]).equals("N")) {
                    top5[4] = losersWinners[i];
                    losersWinnersC[i] = "W";
                    updateForPlayers();
                    round3Played();
                    return;
                }
            }
        }

        private static void round1Played() {
            round1Games++;
            if (round1Games == 4) {
                startRound2();
            }
        }

        private static void round2Played() {
            round2Games++;
            if (round2Games == 4) {
                startRound3();
            }
        }

        private static void round3Played() {
            round3Games++;
            if (round3Games == 3) {
                endTournament();
            }
        }

        private static void endTournament() {
            resultForPlayers();
            round1Games = 0;
            round2Games = 0;
            round3Games = 0;
            started = false;
            for (int i = 0; i < 8; i++) {
                players[i] = null;
                playersC[i] = "N";
            }
            for (int i = 0; i < 4; i++) {
                r1winners[i] = null;
                r1losers[i] = null;
                r1winnersC[i] = "N";
                r1losersC[i] = "N";
            }
            for (int i = 0; i < 2; i++) {
                r2winners[i] = null;
                r2losers[i] = null;
                losersWinners[i] = null;
                r2winnersC[i] = "N";
                r2losersC[i] = "N";
                losersWinnersC[i] = "N";
            }
        }

        private static int enemyIndex(int i) {
            if (i % 2 == 0) return ++i;
            return --i;
        }

        private static void updateForPlayers() {
            for (ClientHandler player : players) {
                player.sendMessage("tournament" + TournamentData.getInformation());
            }
        }

        private static void resultForPlayers() {
            StringBuilder result = new StringBuilder("tournamentResult");
            for (int i = 0; i < 5; i++) {
                result.append("#").append(clientHandlerName(top5[i]));
            }
            for (ClientHandler player : players) {
                player.sendMessage(result.toString());
            }

        }

    }


}