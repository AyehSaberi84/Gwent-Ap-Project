package model;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import view.Main;
import view.ResultMenu;

public class Tournament {

    private static Button joinButton = new Button(" Join ");
    private static final Label[] labels = new Label[22];

    static {
        for (int i = 0; i < 22; i++) {
            labels[i] = new Label("-");
        }
    }

    private static boolean started = false;

    public static Label[] getLabels() {
        return labels;
    }

    public static void update(String[] parts) {
        started = parts[23].equals("true");
        for (int i = 0; i < 22; i++) {
            String[] info = parts[i + 1].split("@");
            labels[i].setText(info[0]);
            if (info[1].equals("W")) labels[i].setTextFill(Color.YELLOW);
            if (i < 8 && parts[i + 1].equals(User.getLoggedInUser().getUsername())) {
                joinButton.setOnMouseClicked(event -> {});
                joinButton.setText("Joined!");
            }
        }

    }

    public static boolean isStarted() {
        return started;
    }

    public static Button getJoinButton() {
        return joinButton;
    }

    public static void showResults(String[] playerNames) {
        try {
            new ResultMenu(playerNames).start(Main.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
