package controller;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.Ability;
import model.Card;
import model.Game;
import model.Player;
import model.factions.*;
import view.EndGameMenu;
import view.GameMenu;
import view.Main;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameController {

    private static Card selectedCard = null;
    private static final PrintWriter output = ConnectToServer.getOutput();

    public static void vetoCard(Card card, Player player, HBox hBox, int i) {
        if (player.getRedrawnCards().size() >= 2) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error message");
            alert.setHeaderText("Error message");
            alert.setContentText("You can't redraw more than 2 cards");
            alert.show();
            return;
        }
        player.getHand().getChildren().remove(card);
        Card newCard = addRandomCardToHand(player, i);
        ImageView imageView = new ImageView(newCard.getImage());
        makeToolTip(newCard);
        imageView.setFitWidth(128);
        imageView.setPreserveRatio(true);
        hBox.getChildren().set(i, imageView);
        imageView.setOnMouseClicked(actionEvent -> vetoCard(newCard, player, hBox, i));
        player.getRedrawnCards().add(card);
    }

    public static void endVetoCard(Game game) {
        game.getPlayer1().getDeck().addAll(game.getPlayer1().getRedrawnCards());

        StringBuilder message = new StringBuilder("enemyHand");
        for (Node child : game.getCurrentPlayer().getHand().getChildren()) {
            message.append("#");
            message.append(((Card) child).getName());
            message.append("@");
            message.append(Card.getFactionName((Card) child));
        }

        output.println(message);

        try {
            new GameMenu(game).start(Main.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void placeCard(HBox hBox, String rowName, MouseEvent mouseEvent) {
        if (notMyTurn()) return;
        if (!canPlaceOn(rowName)) return;
        Game.getCurrentGame().getCurrentPlayer().getHand().getChildren().remove(selectedCard);
        boolean right;
        if (mouseEvent.getX() > 325) {
            right = true;
            hBox.getChildren().add(selectedCard);
        } else {
            right = false;
            hBox.getChildren().addFirst(selectedCard);
        }
        showSelection(selectedCard, false);
        final Card sCard = selectedCard;
        selectedCard.setOnMouseClicked(event -> playedCardSelected(sCard, hBox, rowName));

        output.println("placeCard#" + sCard.getName() + "#" + Card.getFactionName(sCard) + "#" + rowName + "#" + right);

        updateLabels();

        Game.getCurrentGame().setMyTurn(false);
        if (selectedCard.getAbility() != null) useAbility(selectedCard, hBox, rowName);
        else endTurn();
        selectedCard = null;
            LogSaver.makeScreen(Game.getCurrentGame());
    }

    private static boolean canPlaceOn(String rowName) {
        if (selectedCard.getCardType().equals(rowName)) return true;
        if(selectedCard.getCardType().equals("Agile") && !rowName.equals("Siege")) return true;
        if (selectedCard.getName().equals("Scorch")) useSpell();
        return false;
    }

    private static void updateNumberOfCards(Player player) {
       player.setNumberOfCards(player.getHand().getChildren().size());
    }

    private static void useSpell() {
        if (notMyTurn()) return;
        Game.getCurrentGame().getCurrentPlayer().getHand().getChildren().remove(selectedCard);
        showSelection(selectedCard, false);
        sendToGraveyard(selectedCard, Game.getCurrentGame().getCurrentPlayer());
        output.println("useSpell#" + selectedCard.getName());
        if (selectedCard.getAbility() != null) useAbility(selectedCard, null, null);
        selectedCard = null;
        updateLabels();
    }

    public static void enemyUseSpell(String cardName) {
        Platform.runLater(() -> {
            Card card = getCardFromEnemyHand(cardName);
            Game.getCurrentGame().getEnemy().getHand().getChildren().remove(card);
            sendToGraveyard(card, Game.getCurrentGame().getEnemy());
            updateLabels();
        });
    }

    private static void sendToGraveyard(Card card, Player player) {
        player.getGraveyard().getChildren().add(card);
        card.setLayoutX(0);
        card.setLayoutY(0);
        card.setOnMouseClicked(event -> {});
    }

    public static void selectCard(Card card) {
        if (selectedCard != null) showSelection(selectedCard, false);
        if (card == selectedCard) {
            selectedCard = null;
            return;
        }
        selectedCard = card;
        showSelection(card, true);
    }

    private static void showSelection(Card card, boolean select) {
        for (Node child : card.getChildren()) {
            child.setLayoutY(child.getLayoutY() + 20 * (select ? -1 : 1));
        }
    }

    public static void playCommander() {
        // TODO
    }

    public static void updateTotalScore(Player player) {
        int totalScore = 0;
        totalScore += getTotalScoreOfRow(player.getCloseCombat(), player.getCloseLabel());
        totalScore += getTotalScoreOfRow(player.getRanged(), player.getRangedLabel());
        totalScore += getTotalScoreOfRow(player.getSiege(), player.getSigeLabel());
        player.setTotalScore(totalScore);
    }

    public static int getTotalScoreOfRow(HBox hBox, Label label) {
        int score = 0;
        for (Node child : hBox.getChildren()) {
            score += ((Card) child).getPower();
        }
        label.setText(String.valueOf(score));
        return score;
    }

    public static void passRound(Button button) {
        button.setText("Passed");
        button.setOnMouseClicked(event -> {});
        Game.getCurrentGame().getCurrentPlayer().setPassed(true);
        output.println("passed");
        endTurn();
        if (Game.getCurrentGame().getEnemy().isPassed()) {
            ConnectToServer.getOutput().println("requestEndRound");
            endRound();
        }
    }

    public static void unpass() {
        Button button = Game.getCurrentGame().getPass();
        button.setText("Pass");
        button.setOnMouseClicked(event -> passRound(button));
        Game.getCurrentGame().getCurrentPlayer().setPassed(false);
        Game.getCurrentGame().getEnemy().setPassed(false);
    }

    public static void setEnemyPassed() {
        Game.getCurrentGame().getEnemy().setPassed(true);
    }

    private static void endTurn() {
        if (Game.getCurrentGame().getEnemy().isPassed()) {
            Game.getCurrentGame().setMyTurn(true);
            return;
        }
        Game.getCurrentGame().setMyTurn(false);
        output.println("yourTurn");
    }

    public static void startTurn() {
        if (Game.getCurrentGame().getCurrentPlayer().isPassed()) {
            endTurn();
            return;
        }
        Game.getCurrentGame().setMyTurn(true);
    }

    private static boolean notMyTurn() {
        return !Game.getCurrentGame().isMyTurn();
    }

    public static void endRound() {
        Game.getCurrentGame().setMyTurn(false);
        Player player = Game.getCurrentGame().getCurrentPlayer();
        String score = String.valueOf(player.getScore());
        String optional = "";
        Card cardToKeep = null;
        Card rN = player.getDeck().get(new Random().nextInt(player.getDeck().size()));
        switch (player.getFaction().getName()) {
            case "Monster" -> cardToKeep = getRandomCardFromBoard();
            case "Realm Northern" -> optional = rN.getName() + "#" + Card.getFactionName(rN);
            case "Skellige" -> {
                // TODO if round == 3 getRandomCardFromGraveyard();
            }
        }
        clearBoard(cardToKeep, Game.getCurrentGame().getCurrentPlayer());
        String rowName = rowNameOfCard(cardToKeep);
        if (rowName != null) optional = rowName + "#" + cardToKeep.getName();
        ConnectToServer.getOutput().println("end#" + score + "#" + optional);

    }

    private static String rowNameOfCard(Card card) {
        for (Node node : Game.getCurrentGame().getCurrentPlayer().getCloseCombat().getChildren()) {
            if (node == card) return "Close Combat";
        }
        for (Node node : Game.getCurrentGame().getCurrentPlayer().getRanged().getChildren()) {
            if (node == card) return "Ranged";
        }
        for (Node node : Game.getCurrentGame().getCurrentPlayer().getSiege().getChildren()) {
            if (node == card) return "Siege";
        }
        return null;
    }

    public static void roundEnded(String win, String turn) {
        switch (win) {
            case "win" -> Game.getCurrentGame().getEnemy().setLives(Game.getCurrentGame().getEnemy().getLives() - 1);
            case "lose" ->
                    Game.getCurrentGame().getCurrentPlayer().setLives(Game.getCurrentGame().getCurrentPlayer().getLives() - 1);
            case "draw" -> {
                Game.getCurrentGame().getCurrentPlayer().setLives(Game.getCurrentGame().getCurrentPlayer().getLives() - 1);
                Game.getCurrentGame().getEnemy().setLives(Game.getCurrentGame().getEnemy().getLives() - 1);
            }
        }
        unpass();
        if (turn.equals("yourTurn")) startTurn();
    }

    public static void clearBoard(Card cardToKeep, Player player) {
        clearRow(player.getGraveyard().getChildren(), player.getCloseCombat(),
                new ArrayList<>(player.getCloseCombat().getChildren()), cardToKeep);
        clearRow(player.getGraveyard().getChildren(), player.getRanged(),
                new ArrayList<>(player.getRanged().getChildren()), cardToKeep);
        clearRow(player.getGraveyard().getChildren(), player.getSiege(),
                new ArrayList<>(player.getSiege().getChildren()), cardToKeep);

        updateLabels();
    }

    private static boolean clearRow(List<Node> graveyard, HBox row, ArrayList<Node> rowCopy, Card cardToKeep) {
        boolean hasCardToKeep = false;
        for (Node node : rowCopy) {
            Card card = (Card) node;
            if (cardToKeep == card) {
                hasCardToKeep = true;
            } else if (card.getAbility() == Ability.TRANSFORMER) {
                int index = row.getChildren().indexOf(card);
                row.getChildren().remove(card);
                row.getChildren().add(index, transformed(card));
            } else {
                graveyard.add(card);
                card.setLayoutX(0);
                card.setLayoutY(0);
                card.setOnMouseClicked(event -> {});
            }
        }
        return hasCardToKeep;
    }

    private static Card transformed(Card card) {
        return Card.makeCardWithNameFromFaction("Young Vidkaarl", "Skellige");
    }

    public static void clearMonster(String rowName, String cardName) {
        HBox row = getHBoxByRowName(rowName, Game.getCurrentGame().getEnemy());
        clearBoard(getCardFromRow(row, cardName), Game.getCurrentGame().getEnemy());
    }

    private static Card getCardFromRow(HBox row, String cardName) {
        for (Node child : row.getChildren()) {
            if (((Card) child).getName().equals(cardName)) return (Card) child;
        }
        return null;
    }

    private static Card getRandomCardFromBoard() {
        Player player = Game.getCurrentGame().getCurrentPlayer();
        ArrayList<Node> cardsOnBoard = new ArrayList<>();
        cardsOnBoard.addAll(player.getCloseCombat().getChildren());
        cardsOnBoard.addAll(player.getRanged().getChildren());
        cardsOnBoard.addAll(player.getSiege().getChildren());
        if (cardsOnBoard.isEmpty()) return null;
        Random random = new Random();
        return (Card) cardsOnBoard.get(random.nextInt(cardsOnBoard.size()));
    }

    public static void addToHandNorthern(String cardName, String factionName) {
        Card card = Card.makeCardWithNameFromFaction(cardName, factionName);
        addToHandFromDeck(card);
        updateLabels();
    }

    public static void addToEnemyHandNorthern(String cardName, String factionName) {
        Game.getCurrentGame().getEnemy().getHand().getChildren().add(Card.makeCardWithNameFromFaction(cardName, factionName));
        updateLabels();
    }

    public static void endGame(String[] parts) {
        if (parts[17].equals("true")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText("Your enemy has disconnected");
            alert.setContentText("You won the game!");
            alert.show();
        }
        try {
            new EndGameMenu(parts[15], Game.getCurrentGame().getCurrentPlayer().getName(), parts[1], parts[2], parts[3], parts[4],
            parts[5], parts[6], parts[7], parts[8], parts[9], parts[10], parts[11], parts[12], parts[13], parts[14], parts[16]).start(Main.stage);
            LogSaver.endGame(Game.getCurrentGame());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Card addRandomCardToHand(Player player, int inHandIndex) {
        HBox hand = player.getHand();
        Card card = getRandomCardFromDeck();
        hand.getChildren().add(inHandIndex, card);
        return card;
    }

    public static void makeToolTip(Card card) {
        if (card.getAbility() == null) return;
        Tooltip tooltip = new Tooltip(card.getAbility().getDescription());
        card.setTooltip(tooltip);
        Tooltip.install(card, tooltip);
        card.setOnMouseEntered(event -> tooltip.show(card, event.getScreenX(), event.getScreenY() + 10));
        card.setOnMouseExited(event -> tooltip.hide());
    }

    public static void setEnemyHand(String[] cardNames) {
        for (String cardName : cardNames) {
            if (cardName.equals("enemyHand")) continue;
            String[] nameAndFaction = cardName.split("@");
            Game.getCurrentGame().getEnemy().getHand().getChildren().add(Card.makeCardWithNameFromFaction(nameAndFaction[0], nameAndFaction[1]));
        }
    }

    public static void setEnemyFaction(String faction) {
        Game.getCurrentGame().getEnemy().setFaction(Faction.getFactionByName(faction));
    }

    public static void selectPlayerRow(HBox hBox, String type, MouseEvent mouseEvent) {
        if (selectedCard == null) return;
        if (!(selectedCard.getAbility() == Ability.SPY)) {
            placeCard(hBox, type, mouseEvent);
        }
    }

    public static void selectEnemyRow(HBox hBox, String type, MouseEvent mouseEvent) {
        if (selectedCard == null) return;
        if (selectedCard.getAbility() == Ability.SPY) {
            placeCard(hBox, type, mouseEvent);
        }
    }

    private static HBox getHBoxByRowName(String type, Player player) {
        return switch (type) {
            case "Close Combat" -> player.getCloseCombat();
            case "Ranged" -> player.getRanged();
            case "Siege" -> player.getSiege();
            default -> null;
        };
    }

    public static void placeWeatherCard() {
        if (notMyTurn()) return;
        if (selectedCard == null) return;
        if (!selectedCard.getCardType().equals("Weather")) return;
        Game.getCurrentGame().getCurrentPlayer().getHand().getChildren().remove(selectedCard);
        if (selectedCard.getName().equals("Clear Weather")) {
            Game.getCurrentGame().getWeatherCards().getChildren().clear();
        } else {
            Game.getCurrentGame().getWeatherCards().getChildren().add(selectedCard);
            selectedCard.setOnMouseClicked(event -> {});
            showSelection(selectedCard, false);
        }
        output.println("placeWeatherCard#" + selectedCard.getName());
        selectedCard = null;
        updateLabels();
        endTurn();
            LogSaver.makeScreen(Game.getCurrentGame());
    }

    public static void enemyPlaceWeatherCard(String cardName) {
        Platform.runLater(() -> {
            Card card = getCardFromEnemyHand(cardName);
            Game.getCurrentGame().getEnemy().getHand().getChildren().remove(card);
            if (card.getName().equals("Clear Weather")) {
                Game.getCurrentGame().getWeatherCards().getChildren().clear();
            } else {
                Game.getCurrentGame().getWeatherCards().getChildren().add(card);
                card.setOnMouseClicked(event -> {});
            }

            updateLabels();

        });
        LogSaver.makeScreen(Game.getCurrentGame());

    }

    private static void playedCardSelected(Card card, HBox hBox, String rowName) {
        if (selectedCard == null) return;
        if (isHBoxForEnemy(hBox)) return;
        if (selectedCard.getAbility() == Ability.DECOY) {
            if (notMyTurn()) return;
            Game.getCurrentGame().getCurrentPlayer().getHand().getChildren().remove(selectedCard);
            int index = hBox.getChildren().indexOf(card);
            hBox.getChildren().add(index, selectedCard);
            showSelection(selectedCard, false);
            selectedCard.setOnMouseClicked(event -> {});
            selectedCard = null;
            output.println("decoy#" + rowName + "#" + index);
            hBox.getChildren().remove(card);
            Game.getCurrentGame().getCurrentPlayer().getHand().getChildren().add(card);
            card.setOnMouseClicked(event -> selectCard(card));
            updateLabels();
            endTurn();
        }
    }

    private static boolean isHBoxForEnemy(HBox hBox) {
        Player enemy = Game.getCurrentGame().getEnemy();
        return enemy.getCloseCombat() == hBox || enemy.getRanged() == hBox || enemy.getSiege() == hBox;
    }

    public static void enemyDecoy(String rowName, String indexString) {
        Platform.runLater(() -> {
            Player enemy = Game.getCurrentGame().getEnemy();
            Card decoyCard = getCardFromEnemyHand("Decoy");
            HBox hBox = getHBoxByRowName(rowName, enemy);
            int index = hBox.getChildren().size() - Integer.parseInt(indexString) - 1;
            Card card = (Card) hBox.getChildren().get(index);
            enemy.getHand().getChildren().remove(decoyCard);
            hBox.getChildren().add(index, decoyCard);
            hBox.getChildren().remove(card);
            decoyCard.setOnMouseClicked(event -> {});
            enemy.getHand().getChildren().add(card);
            updateLabels();
        });
    }

    public static void placeSpecialCard(Pane specialPlace, String rowName) {
        if (notMyTurn()) return;
        if (!specialPlace.getChildren().isEmpty()) return;
        if (selectedCard == null) return;
        if (!selectedCard.getName().equals("Commander’s horn") && !selectedCard.getName().equals("Mardroeme")) return;
        Game.getCurrentGame().getCurrentPlayer().getHand().getChildren().remove(selectedCard);
        specialPlace.getChildren().add(selectedCard);
        selectedCard.setLayoutX(0);
        selectedCard.setLayoutY(0);
        showSelection(selectedCard, false);
        selectedCard.setOnMouseClicked(event -> {});
        output.println("placeSpecialCard#" + selectedCard.getName() + "#" + rowName);

        updateLabels();

        Game.getCurrentGame().setMyTurn(false);
        if (selectedCard.getAbility() != null) useAbility(selectedCard, getHBoxByRowName(rowName, Game.getCurrentGame().getCurrentPlayer()), rowName);
        else endTurn();
        selectedCard = null;
            LogSaver.makeScreen(Game.getCurrentGame());
    }

    public static void enemyPlaceSpecialCard(String[] parts) {
        Platform.runLater(() -> {
            Card card = getCardFromEnemyHand(parts[1]);
            Game.getCurrentGame().getEnemy().getHand().getChildren().remove(card);
            Pane specialPlace = getEnemySpecialPlace(parts[2]);
            specialPlace.getChildren().add(card);
            card.setLayoutX(0);
            card.setLayoutY(0);
            card.setOnMouseClicked(event -> {});
            updateLabels();

        });
        LogSaver.makeScreen(Game.getCurrentGame());

    }

    public static void enemyPlaceCard(String cardName, String row, String left) {
        Card card = getCardFromEnemyHand(cardName);
        Player player;
        Game.getCurrentGame().getEnemy().getHand().getChildren().remove(card);
        if (card.getAbility() == Ability.SPY) player = Game.getCurrentGame().getCurrentPlayer();
        else player = Game.getCurrentGame().getEnemy();
        if (left.equals("true")) Platform.runLater(() -> {
            getHBoxByRowName(row, player).getChildren().addFirst(card);
            updateLabels();
        });
        else Platform.runLater(() -> {
            getHBoxByRowName(row, player).getChildren().add(card);
            updateLabels();
        });
        LogSaver.makeScreen(Game.getCurrentGame());

    }

    private static Card getCardFromEnemyHand(String cardName) {
        for (Node child : Game.getCurrentGame().getEnemy().getHand().getChildren()) {
            if (((Card) child).getName().equals(cardName)) return (Card) child;
        }
        return null;
    }

    private static Pane getEnemySpecialPlace(String rowName) {
        Player enemy = Game.getCurrentGame().getEnemy();
        return switch (rowName) {
            case "Close Combat" -> enemy.getCloseCombatSpecial();
            case "Ranged" -> enemy.getRangedSpecial();
            case "Siege" -> enemy.getSiegeSpecial();
            default -> null;
        };
    }

    private static void updateLabels() {
        updateCardPowers();
        updateTotalScore(Game.getCurrentGame().getCurrentPlayer());
        updateTotalScore(Game.getCurrentGame().getEnemy());
        updateNumberOfCards(Game.getCurrentGame().getCurrentPlayer());
        updateNumberOfCards(Game.getCurrentGame().getEnemy());
    }

    private static void updateCardPowers() {
        Player player = Game.getCurrentGame().getCurrentPlayer();
        updateRow(player.getCloseCombat(), player.getCloseCombatSpecial(), isEffectedByWeather("Close Combat"));
        updateRow(player.getRanged(), player.getRangedSpecial(), isEffectedByWeather("Ranged"));
        updateRow(player.getSiege(), player.getSiegeSpecial(), isEffectedByWeather("Siege"));
        player = Game.getCurrentGame().getEnemy();
        updateRow(player.getCloseCombat(), player.getCloseCombatSpecial(), isEffectedByWeather("Close Combat"));
        updateRow(player.getRanged(), player.getRangedSpecial(), isEffectedByWeather("Ranged"));
        updateRow(player.getSiege(), player.getSiegeSpecial(), isEffectedByWeather("Siege"));
    }

    private static boolean isEffectedByWeather(String rowName) {
        return switch (rowName) {
            case "Close Combat" -> weatherCardActive("Biting Frost");
            case "Ranged" -> weatherCardActive("Impenetrable fog") || weatherCardActive("Skellige Storm");
            case "Siege" -> weatherCardActive("Torrential Rain") || weatherCardActive("Skellige Storm");
            default -> false;
        };
    }

    private static boolean weatherCardActive(String cardName) {
        for (Node node : Game.getCurrentGame().getWeatherCards().getChildren()) {
            if (((Card) node).getName().equals(cardName)) return true;
        }
        return false;
    }

    private static void updateRow(HBox row, Pane specialPlace, boolean isEffectedByWeather) {
        boolean berserkerTransform = !specialPlace.getChildren().isEmpty()
                && ((Card) specialPlace.getChildren().getFirst()).getAbility() == Ability.MARDROEME;

        for (Node node : row.getChildren()) {
            Card card = (Card) node;
            if (card.getName().equals("Decoy")) continue;
            int power = card.getBasePower();
            if (card.isHero()) {
                card.setPower(power);
                continue;
            }
            if (card.getAbility() == Ability.BERSERKER && berserkerTransform) {
                String cardName;
                if (card.getName().startsWith("Young")) cardName = "Young Vidkaarl";
                else cardName = "Vidkaarl";
                int i = row.getChildren().indexOf(card);
                row.getChildren().remove(card);
                row.getChildren().add(i, Card.makeCardWithNameFromFaction(cardName, "Skellige"));
            }
            if (isEffectedByWeather) power = Math.min(1, power);

            if (card.getAbility() == Ability.TIGHT_BOND) {
                int numberOfCardsWithSameName = 0;
                for(Node nodeInRow : row.getChildren()) {
                    Card cardInRow = (Card) nodeInRow;
                    if (card.getName().equals(cardInRow.getName())) numberOfCardsWithSameName++;
                }
                power *= numberOfCardsWithSameName;
            }
            int numberOfMoralBoosts = 0;
            for (Node nodeInRow : row.getChildren()) {
                Card cardInRow = (Card) nodeInRow;
                if (cardInRow == card) continue;
                if (cardInRow.getAbility() == Ability.MORAL_BOOST) numberOfMoralBoosts++;
            }
            power += numberOfMoralBoosts;

            if (!specialPlace.getChildren().isEmpty()) {
                if (((Card) specialPlace.getChildren().getFirst()).getAbility() == Ability.COMMANDERS_HORN) {
                    power *= 2;
                }
            }

            card.setPower(power);
        }
    }

    private static void addToHandFromDeck(Card card) {
        GameController.makeToolTip(card);
        card.setOnMouseClicked(event -> GameController.selectCard(card));
        Game.getCurrentGame().getCurrentPlayer().getHand().getChildren().add(card);
        Game.getCurrentGame().getCurrentPlayer().getDeck().remove(card);
    }

    private static void useAbility(Card card, HBox hBox, String rowName) {
        switch (card.getAbility()) {
            case DECOY:
                break;
            case MEDIC: {
                medicAbility();
                break;
            }
            case MUSTER: {
                musterAbility(card, hBox, rowName);
                break;
            }
            case SPY: {
                spyAbility();
                break;
            }
            case SCORCH: {
                scorchAbility(card);
                break;
            }
            default: {
                updateLabels();
                endTurn();
            }
        }

    }

    private static void medicAbility() {
        if (Game.getCurrentGame().getCurrentPlayer().getGraveyard().getChildren().isEmpty()) {
            endTurn();
            return;
        }
        for (Node child : Game.getCurrentGame().getCurrentPlayer().getGraveyard().getChildren()) {
            addCardToShow((Card) child);
        }
    }

    private static void addCardToShow(Card card) {
        Card newCard = new Card(Card.makeCardWithNameFromFaction(card.getName(), Card.getFactionName(card)));
        newCard.setOnMouseClicked(event -> {
            playRandom(newCard, Game.getCurrentGame().getCurrentPlayer(), false);
            Game.getCurrentGame().getCurrentPlayer().getGraveyard().getChildren().remove(card);
            Game.getCurrentGame().getGraveyardVBox().getChildren().clear();
            endTurn();
            output.println("playRandom#" + newCard.getName());
        });
        Game.getCurrentGame().getGraveyardVBox().getChildren().add(newCard);
    }

    private static void playRandom(Card card, Player player, boolean enemy) {
        Pane pane = null;
        // if (card.getAbility() == Ability.SPY) player = Game.getCurrentGame().getEnemy();
        // else player = Game.getCurrentGame().getCurrentPlayer();
        if (card.getCardType().equals("Close Combat") || card.getCardType().equals("Ranged") || card.getCardType().equals("Siege")) {
            pane = getHBoxByRowName(card.getCardType(), player);
        } else if (card.getCardType().equals("Agile")) pane = getHBoxByRowName("Close Combat", player);
        // TODO else if (card.getCardType().equals("Weather")) pane = Game.getCurrentGame().getWeatherCards();
        if (enemy) pane.getChildren().addFirst(card);
        else pane.getChildren().add(card);
        updateLabels();
    }

    public static void enemyPlayRandom(String cardName) {
        Card card = null;
        for (Node child : Game.getCurrentGame().getEnemy().getGraveyard().getChildren()) {
            if (((Card) child).getName().equals(cardName)) {
                card = (Card) child;
                break;
            }
        }
        playRandom(card, Game.getCurrentGame().getEnemy(), true);
    }

    private static void musterAbility(Card card, HBox hBox, String rowName) {
        String cardName = card.getName();
        String musterType = cardName;
        if (cardName.startsWith("Gaunter")) musterType = "Gaunter";
        else if (cardName.startsWith("Arachas")) musterType = "Arachas";
        else if (cardName.startsWith("Crone")) musterType = "Crone";
        else if (cardName.startsWith("Vampire")) musterType = "Vampire";
        StringBuilder message = new StringBuilder("muster");
        ArrayList<Card> cards = new ArrayList<>();
        for (Card cardInDeck : Game.getCurrentGame().getCurrentPlayer().getDeck()) {
            if (cardInDeck.getName().startsWith(musterType)) {
                cards.add(cardInDeck);
                message.append("#").append(cardInDeck.getName()).append("@").append(Card.getFactionName(cardInDeck));
            }
        }
        for (Card cardInCards : cards) {
            Game.getCurrentGame().getCurrentPlayer().getDeck().remove(cardInCards);
        }
        message.append("#hand");
        for (Node node : Game.getCurrentGame().getCurrentPlayer().getHand().getChildren()) {
            Card cardInHand = (Card) node;
            if (cardInHand.getName().startsWith(musterType)) {
                cards.add(cardInHand);
                message.append("#").append(cardInHand.getName()).append("@").append(Card.getFactionName(cardInHand));
            }
        }
        message.append("#").append(rowName);
        hBox.getChildren().addAll(cards);
        output.println(message);
        updateLabels();
        endTurn();
    }

    public static void enemyMuster(String[] parts) {
        ArrayList<Card> cards = new ArrayList<>();
        int i = 1;
        while (!parts[i].equals("hand")) {
            String[] nameAndFaction = parts[i].split("@");
            cards.add(Card.makeCardWithNameFromFaction(nameAndFaction[0], nameAndFaction[1]));
            i++;
        }
        for (int j = i + 1; j < parts.length - 1; j++) {
            String[] nameAndFaction = parts[j].split("@");
            cards.add(getCardFromEnemyHand(nameAndFaction[0]));
        }
        HBox hBox = getHBoxByRowName(parts[parts.length - 1], Game.getCurrentGame().getEnemy());
        for (Card card : cards) {
            hBox.getChildren().addFirst(card);
        }
        updateLabels();
    }

    private static void spyAbility() {
        Card card1 = getRandomCardFromDeck();
        Card card2 = getRandomCardFromDeck();
        addToHandFromDeck(card1);
        addToHandFromDeck(card2);
        output.println("spy#" + card1.getName() + "@" + Card.getFactionName(card1) + "#" + card2.getName() + "@" + Card.getFactionName(card2));
        endTurn();
    }

    public static void enemySpy(String[] parts) {
        String[] firstCard = parts[1].split("@");
        String[] secondCard = parts[2].split("@");
        HBox enemyHand = Game.getCurrentGame().getEnemy().getHand();
        enemyHand.getChildren().add(Card.makeCardWithNameFromFaction(firstCard[0], firstCard[1]));
        enemyHand.getChildren().add(Card.makeCardWithNameFromFaction(secondCard[0], secondCard[1]));
        updateLabels();
    }

    private static Card getRandomCardFromDeck() {
        ArrayList<Card> deck = Game.getCurrentGame().getCurrentPlayer().getDeck();
        Random random = new Random();
        int index = random.nextInt(deck.size());
        Card card = deck.get(index);
        deck.remove(index);
        return card;
    }

    private static void scorchAbility(Card card) {
        if (card.getName().equals("Scorch")) {
            originalScorch();
        } else {
            notScorch(getHBoxByRowName(card.getCardType(), Game.getCurrentGame().getEnemy()), card.getCardType());
        }
        endTurn();

    }

    private static void originalScorch() {

        int highPower = 0;
        Player player = Game.getCurrentGame().getCurrentPlayer();
        Player enemy = Game.getCurrentGame().getEnemy();

        int highPowerInRow;
        highPowerInRow = highPowerInRow(player.getCloseCombat());
        if (highPowerInRow > highPower) highPower = highPowerInRow;
        highPowerInRow = highPowerInRow(player.getRanged());
        if (highPowerInRow > highPower) highPower = highPowerInRow;
        highPowerInRow = highPowerInRow(player.getSiege());
        if (highPowerInRow > highPower) highPower = highPowerInRow;
        highPowerInRow = highPowerInRow(enemy.getCloseCombat());
        if (highPowerInRow > highPower) highPower = highPowerInRow;
        highPowerInRow = highPowerInRow(enemy.getRanged());
        if (highPowerInRow > highPower) highPower = highPowerInRow;
        highPowerInRow = highPowerInRow(enemy.getSiege());
        if (highPowerInRow > highPower) highPower = highPowerInRow;

        killCardWithPowerInRow(highPower, player.getCloseCombat(), player);
        killCardWithPowerInRow(highPower, player.getRanged(), player);
        killCardWithPowerInRow(highPower, player.getSiege(), player);
        killCardWithPowerInRow(highPower, enemy.getCloseCombat(), enemy);
        killCardWithPowerInRow(highPower, enemy.getRanged(), enemy);
        killCardWithPowerInRow(highPower, enemy.getSiege(), enemy);
        output.println("kill#" + highPower + "#player");
        output.println("kill#" + highPower + "#enemy");

    }

    private static int highPowerInRow(HBox row) {
        int highPower = 0;
        for (Node node : row.getChildren()) {
            Card card = (Card) node;
            if (card.isHero()) continue;
            if (card.getPower() > highPower) {
                highPower = card.getPower();
            }
        }
        return highPower;
    }

    private static void notScorch(HBox row, String rowName) {
        int sum = 0;
        int highPower = 0;
        for (Node node : row.getChildren()) {
            Card card = (Card) node;
            if (card.isHero()) continue;
            sum += card.getPower();
            if (card.getPower() > highPower) highPower = card.getPower();
        }
        if (sum >= 10) {
            killCardWithPowerInRow(highPower, row, Game.getCurrentGame().getEnemy());
            output.println("kill#" + highPower + "#" + rowName);
        }
    }

    private static void killCardWithPowerInRow(int power, HBox row, Player player) {
        ArrayList<Card> cards = new ArrayList<>();
        for (Node node : row.getChildren()) {
            Card card = (Card) node;
            if (card.getPower() == power) cards.add(card);
        }
        for (Card card : cards) {
            sendToGraveyard(card, player);
        }
    }

    public static void enemyKillCard(String powerStr, String playerStr) {
        int power = Integer.parseInt(powerStr);
        Player player;
        if (playerStr.equals("player")) {
            player = Game.getCurrentGame().getCurrentPlayer();
            killCardWithPowerInRow(power, player.getCloseCombat(), player);
            killCardWithPowerInRow(power, player.getRanged(), player);
            killCardWithPowerInRow(power, player.getSiege(), player);
            player = Game.getCurrentGame().getEnemy();
            killCardWithPowerInRow(power, player.getCloseCombat(), player);
            killCardWithPowerInRow(power, player.getRanged(), player);
            killCardWithPowerInRow(power, player.getSiege(), player);
        }
        else {
            player = Game.getCurrentGame().getEnemy();
            killCardWithPowerInRow(power, getHBoxByRowName(playerStr, player), player);
        }
    }

    public static void setEnemyCommander(String commanderName) {
        Faction faction = Game.getCurrentGame().getEnemy().getFaction();
        faction.setCommander(faction.makeCommanderByName(commanderName));
    }
}