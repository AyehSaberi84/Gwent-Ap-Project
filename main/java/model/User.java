package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.VBox;
import view.ShadowViews.FriendsList;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class User implements Serializable {

    private static User loggedInUser;
    private ArrayList <Game> allGames  = new ArrayList<>();
    private static ArrayList<User> allUsers = new ArrayList<>();
    private ArrayList<Card> allUsersCard;
    private ArrayList<Deck> decks;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String answer;
    private int highestScore = 0;
    private int gamesPlayed;
    private IntegerProperty wins = new SimpleIntegerProperty(0);
    private int loses;
    private int draws;
    private int rate;

    private VBox friends = new VBox(FriendsList.makeLabel("Friends:"));
    private VBox requests = new VBox(FriendsList.makeLabel("Requests:"));

    public User(String username, String password, String nickname, String email, String answer) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.answer = answer;
        allUsers.add(this);
    }



    public static User getUserByUsername(String name) {
        for (User user : allUsers)
            if (user.getUsername().equals(name)) return user;
        return null;
    }

    public static void makeRank() {
        allUsers.sort(Comparator.comparing(User::getWins).reversed().thenComparing(User::getUsername));
        int count = 1;
        for (User user : allUsers) {
            user.setRate(count);
            count++;
        }
    }

    public String getUsername() {
        return username;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getWins() {
        return wins.get();
    }

    public IntegerProperty winsProperty() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins.set(wins);
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public ArrayList<Game> getAllGames() {
        return allGames;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ArrayList<User> allUsers) {
        User.allUsers = allUsers;
    }

    public VBox getFriends() {
        return friends;
    }

    public VBox getRequests() {
        return requests;
    }

}
