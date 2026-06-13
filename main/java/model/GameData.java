package model;

import javafx.stage.Stage;

import java.util.Date;

public class GameData {
    public String p1name;
    public String p2name;
    public String[] p1score = new String[3];
    public String[] p2score = new String[3];
    public boolean[] p1win = new boolean[3];
    public boolean[] p2win = new boolean[3];
    public String winner;
    public boolean isTournament;

    public GameData() {
        for (int i = 0; i < 3; i++) {
            p1score[i] = "0";
            p2score[i] = "0";
        }
    }

    public String winner(int currentRound) {
        if (currentRound < 2) return null;
        int p1wins = 0;
        int p2wins = 0;
        for (boolean win : p1win) {
            if (win) p1wins++;
        }
        for (boolean win : p2win) {
            if (win) p2wins++;
        }
        if (currentRound - p1wins == 2) {
            if (currentRound - p2wins == 2) {
                return "draw";
            } else {
                return p2name;
            }
        } else if (currentRound - p2wins == 2) {
            return p1name;
        }
        return null;
    }
}
