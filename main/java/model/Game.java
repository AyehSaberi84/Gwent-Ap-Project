package model;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.image.BufferedImage;
import java.util.Date;

public class Game {

    private static Game currentGame;

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player enemy;
    private Date date;
    private final Button pass = new Button("Pass");
    private int[] player1scores = new int[3];
    private int[] player2scores = new int[3];
    private String winnerName;
    private boolean myTurn;
    private String cuurentImage ;

    public String getCuurentImage() {
        return cuurentImage;
    }

    public void setCuurentImage(String cuurentImage) {
        this.cuurentImage = cuurentImage;
    }

    private final VBox graveyardVBox = new VBox();

    private final HBox weatherCards = new HBox();

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        currentPlayer = player1;
        enemy = player2;
        currentGame = this ;
    }
    @Override
    public String toString() {
        String s = player1.getName() + player2.getName() + date.getTime() ;
        return s ;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        Game.currentGame = currentGame;
    }

    public HBox getWeatherCards() {
        return weatherCards;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int[] getPlayer1scores() {
        return player1scores;
    }

    public void setPlayer1scores(int[] player1scores) {
        this.player1scores = player1scores;
    }

    public int[] getPlayer2scores() {
        return player2scores;
    }

    public void setPlayer2scores(int[] player2scores) {
        this.player2scores = player2scores;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinner(String winnerName) {
        this.winnerName = winnerName;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getEnemy() {
        return enemy;
    }

    public void setEnemy(Player enemy) {
        this.enemy = enemy;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public Button getPass() {
        return pass;
    }

    public VBox getGraveyardVBox() {
        return graveyardVBox;
    }
}
