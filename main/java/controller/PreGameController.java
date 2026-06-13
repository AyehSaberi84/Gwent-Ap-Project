package controller;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import model.Card;
import model.DataBase.CardManager;
import model.Game;
import model.Player;
import model.User;
import model.commanders.Commander;
import model.commanders.monsters.*;
import model.commanders.nilfgaardian_empire.*;
import model.commanders.northern_realms.*;
import model.commanders.scoia_tael.*;
import model.commanders.skellige.CrachAnCraite;
import model.commanders.skellige.KingBran;
import model.factions.*;
import view.Main;
import view.VetoCardMenu;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class PreGameController {
    private static Faction faction;

    public static Player getPlayer() {
        return player;
    }

    private static Player player;
    private static boolean privet;
    private static final String DB_URL = "jdbc:sqlite:";

    public static void setPlayer(Player player) {
        PreGameController.player = player;
    }

    public static void createGame() {
        choosePublicOrPrivet("Do you want to start a private or public game?", "private", "public");
        if (takeConfirmation("Do you want to start a random game?")) {
            String request;
            if (privet) request = "RANDOM:privet";
            else request = "RANDOM:public";
            ConnectToServer.getOutput().println(request);
        } else {
            String playerName = makeInputDialog("Create Game", "Enter The Name Of Player", "Enter The Name Of Player");
            if (playerName == null) showAlert("Please enter a name!");
            else if (User.getUserByUsername(playerName) == null) showAlert("There is no user with this name.");
            else if (playerName.equals(User.getLoggedInUser().getUsername()))
                showAlert("You cant start a game with your self");
            else {
                ConnectToServer.getOutput().println("START:" + playerName);
            }
        }
    }

    private static void choosePublicOrPrivet(String contentText, String op1, String op2) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("show options.");
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        ButtonType optionOne = new ButtonType(op1);
        ButtonType optionTwo = new ButtonType(op2);
        alert.getButtonTypes().setAll(optionOne, optionTwo);
        alert.showAndWait().ifPresent(response -> {
            if (response == optionOne) privet = true;
            else if (response == optionTwo) privet = false;
        });
    }

    public static void askPrivacyOfGame() {
        String name = makeInputDialog("Ask privacy", "Privacy of game", "Do you want to start a private or public game?");
        if (name.equals("privet")) privet = true;
        else if (name.equals("public")) privet = false;
    }

    public static Faction selectFaction() {
        String factionName = PreGameController.makeInputDialog("Change Faction", "Select a faction", "Select a faction");
        if (!(factionName.equals("Monster") || factionName.equals("Nilfgaardian Empire") || factionName.equals("Realm Northern")
                || factionName.equals("ScoiaTael") || factionName.equals("Skellige")))
            showAlert("This faction doesnt exists.");
        else {
            switch (factionName) {
                case "Monster" -> {
                    return new Monster();
                }
                case "Nilfgaardian Empire" -> {
                    return new NilfgaardianEmpire();
                }
                case "Realm Northern" -> {
                    return new RealmNorthern();
                }
                case "ScoiaTael" -> {
                    return new ScoiaTael();
                }
                case "Skellige" -> {
                    return new Skellige();
                }
            }
        }
        return null;
    }

    public static void setFaction(Faction faction) {
        PreGameController.faction = faction;
    }

    public static void zeroCondition(Player player) {
        player.setHeroCard(0);
        player.setPowerCard(0);
        player.setTotalCard(0);
        player.setUnitCard(0);
        player.setSpecialCard(0);
        player.getDeck().clear();
    }

    public static Faction getFaction() {
        return faction;
    }

    public static void startGame(Player player) {
        if (player.getDeck().size() < 22 || player.getSpecialCard() > 10)
            showAlert("You can't have less than 22 cards and more than 10 special cards in your deck");
        else {
            ConnectToServer.getOutput().println("faction#" + player.getFaction().getName());
            ConnectToServer.getOutput().println("ready");
        }
    }

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("error message");
        alert.setHeaderText("error message");
        alert.setContentText(message);
        alert.show();
    }

    public static ArrayList<Card> findCard(Faction faction) {
        ArrayList<Card> allCards = null;
        if (faction instanceof Monster) {
            Monster monster = new Monster();
            allCards = monster.getAllCards();
        } else if (faction instanceof NilfgaardianEmpire) {
            NilfgaardianEmpire nilfgaardianEmpire = new NilfgaardianEmpire();
            allCards = nilfgaardianEmpire.getAllCards();
        } else if (faction instanceof RealmNorthern) {
            RealmNorthern realmNorthern = new RealmNorthern();
            allCards = realmNorthern.getAllCards();
        } else if (faction instanceof ScoiaTael) {
            ScoiaTael scoiaTael = new ScoiaTael();
            allCards = scoiaTael.getAllCards();
        } else if (faction instanceof Skellige) {
            Skellige skellige = new Skellige();
            allCards = skellige.getAllCards();
        }
        return allCards;
    }

    public static String makeInfoOfCard(Card card, int number) {
        String hero;
        String type;
        String abillity;
        String power;
        String allowedNumber;
        if (card.isHero()) hero = "Yes";
        else hero = "No";
        if (card.getCardType().equals("Weather") || card.getCardType().equals("Spell")) type = "Special";
        else type = "Unit";
        if (card.getAbility() == null) abillity = "This card has no abillity";
        else abillity = card.getAbility().getName();
        if (card.getBasePower() == -1) power = "This card has no power";
        else power = String.valueOf(card.getBasePower());
        allowedNumber = String.valueOf(card.getNumberOfCardsInGame());
        return "Info Of Card " + card.getName() + "\n" + "Type of Cards is : " + type + "\n"
                + "Hero : " + hero + "\n" + "Place In Gaming Space : " + card.getCardType() + "\n" +
                "Ability : " + abillity + "\n" +
                "Number Of Cards Allowed In Deck : " + allowedNumber + "\n" +
                "Number Of Cards In Deck : " + number + "\n" +
                "Power Of Card : " + power;
    }

    public static Faction assumeRandomFaction() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(5) + 1;
        if (randomNumber == 1) {
            faction = new Monster();
            return new Monster();
        } else if (randomNumber == 2) {
            faction = new NilfgaardianEmpire();
            return new NilfgaardianEmpire();
        } else if (randomNumber == 3) {
            faction = new RealmNorthern();
            return new RealmNorthern();
        } else if (randomNumber == 4) {
            faction = new ScoiaTael();
            return new ScoiaTael();
        } else {
            faction = new Skellige();
            return new Skellige();
        }
    }

    public static String makeInputDialog(String title, String header, String content) {
        TextInputDialog answerDialog = new TextInputDialog();
        answerDialog.setTitle(title);
        answerDialog.setHeaderText(header);
        answerDialog.setContentText(content);
        Optional<String> result = answerDialog.showAndWait();
        return result.orElse(null);
    }

    public static void checkCommander(Faction faction1) {
        if (faction1 instanceof Monster) {
            if (faction1.getCommander() == null) {
                faction1.setCommander(new BringerOfDeath());
                faction.setCommander(new BringerOfDeath());
            }
        } else if (faction1 instanceof NilfgaardianEmpire) {
            if (faction1.getCommander() == null) {
                faction1.setCommander(new TheWhiteFlame());
                faction.setCommander(new TheWhiteFlame());
            }
        } else if (faction1 instanceof RealmNorthern) {
            if (faction1.getCommander() == null) {
                faction1.setCommander(new SonOfMedell());
                faction.setCommander(new SonOfMedell());
            }
        } else if (faction1 instanceof ScoiaTael) {
            if (faction1.getCommander() == null) {
                faction1.setCommander(new TheBeautiful());
                faction.setCommander(new TheBeautiful());
            }
        } else if (faction1 instanceof Skellige) {
            if (faction1.getCommander() == null) {
                faction1.setCommander(new KingBran());
                faction.setCommander(new KingBran());
            }
        }
    }

    public static Image[] findImages(Faction faction) {
        Image[] images = null;
        if (faction instanceof Monster) images = monsterImages();
        else if (faction instanceof NilfgaardianEmpire) images = nilfgaardianEmpireImages();
        else if (faction instanceof RealmNorthern) images = realmNorthernImages();
        else if (faction instanceof ScoiaTael) images = scoiaTaelImages();
        else if (faction instanceof Skellige) images = skelligeImages();
        return images;
    }

    private static Image[] monsterImages() {
        BringerOfDeath bringerOfDeath = new BringerOfDeath();
        CommanderOfTheRedRiders commanderOfTheRedRiders = new CommanderOfTheRedRiders();
        DestroyerOfWorlds destroyerOfWorlds = new DestroyerOfWorlds();
        KingOfTheWildHunt kingOfTheWildHunt = new KingOfTheWildHunt();
        TheTreacherous theTreacherous = new TheTreacherous();
        return new Image[]{
                bringerOfDeath.getImage(),
                commanderOfTheRedRiders.getImage(),
                destroyerOfWorlds.getImage(),
                kingOfTheWildHunt.getImage(),
                theTreacherous.getImage()
        };
    }

    private static Image[] nilfgaardianEmpireImages() {
        EmperorOfNilfgaard emperorOfNilfgaard = new EmperorOfNilfgaard();
        HisImperialMajesty hisImperialMajesty = new HisImperialMajesty();
        InvaderOfTheNorth invaderOfTheNorth = new InvaderOfTheNorth();
        TheRelentless relentless = new TheRelentless();
        TheWhiteFlame whiteFlame = new TheWhiteFlame();
        return new Image[]{
                emperorOfNilfgaard.getImage(),
                hisImperialMajesty.getImage(),
                invaderOfTheNorth.getImage(),
                relentless.getImage(),
                whiteFlame.getImage()
        };
    }

    private static Image[] realmNorthernImages() {
        KingOfTemeria kingOfTemeria = new KingOfTemeria();
        LordCommanderOfTheNorth lordCommanderOfTheNorth = new LordCommanderOfTheNorth();
        SonOfMedell sonOfMedell = new SonOfMedell();
        TheSiegemaster theSiegemaster = new TheSiegemaster();
        TheSteelForged steelForged = new TheSteelForged();
        return new Image[]{
                kingOfTemeria.getImage(),
                lordCommanderOfTheNorth.getImage(),
                sonOfMedell.getImage(),
                theSiegemaster.getImage(),
                steelForged.getImage()
        };
    }

    private static Image[] scoiaTaelImages() {
        DaisyOfTheValley daisyOfTheValley = new DaisyOfTheValley();
        HopeOfTheAenSeidhe hopeOfTheAenSeidhe = new HopeOfTheAenSeidhe();
        PurebloodElf purebloodElf = new PurebloodElf();
        QueenOfDolBlathanna queenOfDolBlathanna = new QueenOfDolBlathanna();
        TheBeautiful theBeautiful = new TheBeautiful();
        return new Image[]{
                daisyOfTheValley.getImage(),
                hopeOfTheAenSeidhe.getImage(),
                purebloodElf.getImage(),
                queenOfDolBlathanna.getImage(),
                theBeautiful.getImage()
        };
    }

    private static Image[] skelligeImages() {
        CrachAnCraite crachAnCraite = new CrachAnCraite();
        KingBran kingBran = new KingBran();
        return new Image[]{
                crachAnCraite.getImage(),
                kingBran.getImage()
        };
    }

    public static Optional<Pair<String, String>> showTwoInputDialog(String title, String command, String buttonName, String first, String second) {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(command);

        // Set the button types.
        ButtonType loginButtonType = new ButtonType(buttonName, ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the input fields and labels.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField firstInput = new TextField();
        firstInput.setPromptText("First Input");
        TextField secondInput = new TextField();
        secondInput.setPromptText("Second Input");

        grid.add(new Label(first), 0, 0);
        grid.add(firstInput, 1, 0);
        grid.add(new Label(second), 0, 1);
        grid.add(secondInput, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(firstInput.getText(), secondInput.getText());
            }
            return null;
        });

        return dialog.showAndWait();
    }

    public static Card checkDataOfCard(String name, int count, Player player) {
        ArrayList<Card> cards = findCardsOfFaction(name);
        Card card;
        card = findCard(cards, name);
        if (card != null) {
            int numberOfCardInGame = findNumOfCardInGame(card, player);
            if ( count > card.getNumberOfCardsInGame()) {
                showAlert("You cant have this card more than " + card.getNumberOfCardsInGame());
            } else if ((count + numberOfCardInGame) > card.getNumberOfCardsInGame()) {
                showAlert("If you add this number of card in game, you have more than usual.");
            } else if (count <= 0) {
                showAlert("Invalid Number!");
            } else if (card.getBasePower() == -1 && (player.getSpecialCard() + count) > 10) {
                showAlert("You cant have more than 10 cards in your deck.");
            } else return card;
        } else showAlert("We dont have this type of card!");
        return null;
    }

    public static Card checkDataOfCardForRemove(String name, int count, Player player) {
        ArrayList<Card> cards = player.getDeck();
        if (cards.isEmpty()) {
            showAlert("Your deck is empty! we cant remove any card from your deck!");
        } else if (!checkCardInDeck(name, cards)) {
            showAlert("you dont have this card to remove!");
        } else {
            Card card = findCard(cards, name);
            int numberOfCardInGame = findNumOfCardInGame(card, player);
            if (card != null) {
                if (count > numberOfCardInGame)
                    showAlert("You dont have " + count + " cards in your deck to remove!");
                else if (count <= 0)
                    showAlert("Invalid number");
                else return card;
            }
        }
        return null;
    }

    private static ArrayList<Card> findCardsOfFaction(String name) {
        ArrayList<Card> cards = new ArrayList<>();
        switch (faction) {
            case Monster monster -> {
                if (monster.validName(name) || Card.validName(name)) cards = monster.getAllCards();
                else showAlert("We dont have this card in this faction.");
            }
            case NilfgaardianEmpire nilfgaardianEmpire -> {
                if (nilfgaardianEmpire.validName(name) || Card.validName(name))
                    cards = nilfgaardianEmpire.getAllCards();
                else showAlert("We dont have this card in this faction.");
            }
            case RealmNorthern realmNorthern -> {
                if (realmNorthern.validName(name) || Card.validName(name)) cards = realmNorthern.getAllCards();
                else showAlert("We dont have this card in this faction.");
            }
            case ScoiaTael scoiaTael -> {
                if (scoiaTael.validName(name) || Card.validName(name)) cards = scoiaTael.getAllCards();
                else showAlert("We dont have this card in this faction.");
            }
            case Skellige skellige -> {
                if (skellige.validName(name) || Card.validName(name)) cards = skellige.getAllCards();
                else showAlert("We dont have this card in this faction.");
            }
            default -> {
            }
        }
        cards.addAll(Card.makeNeutralCards());
        return cards;
    }

    private static Card findCard(ArrayList<Card> cards, String name) {
        for (Card card : cards) {
            if (card.getName().equals(name)) return card;
        }
        return null;
    }

    public static int numberOfAddOrRemove(String number) {
        int count = 1;
        if (!number.isEmpty()) count = Integer.parseInt(number);
        return count;
    }

    public static int findNumOfCardInGame(Card card1, Player player) {
        int count = 0;
        ArrayList<Card> deck = player.getDeck();
        for (Card card : deck) {
            if (card.getName().equals(card1.getName())) count++;
        }
        return count;
    }

    public static Commander[] findCommanderOfFaction(Faction faction) {
        Commander[] commanders = null;
        if (faction instanceof Monster) commanders = monsterCommanders();
        else if (faction instanceof NilfgaardianEmpire) commanders = nilfgaardianEmpireCommanders();
        else if (faction instanceof RealmNorthern) commanders = realmNorthernCommanders();
        else if (faction instanceof ScoiaTael) commanders = scoiaTaelCommanders();
        else if (faction instanceof Skellige) commanders = skelligeCommanders();
        return commanders;
    }

    private static Commander[] monsterCommanders() {
        return new Commander[]{
                new BringerOfDeath(),
                new CommanderOfTheRedRiders(),
                new DestroyerOfWorlds(),
                new KingOfTheWildHunt(),
                new TheTreacherous()
        };
    }

    private static Commander[] nilfgaardianEmpireCommanders() {
        return new Commander[]{
                new EmperorOfNilfgaard(),
                new HisImperialMajesty(),
                new InvaderOfTheNorth(),
                new TheRelentless(),
                new TheWhiteFlame()
        };
    }

    private static Commander[] realmNorthernCommanders() {
        return new Commander[]{
                new KingOfTemeria(),
                new LordCommanderOfTheNorth(),
                new SonOfMedell(),
                new TheSiegemaster(),
                new TheSteelForged()
        };
    }

    private static Commander[] scoiaTaelCommanders() {
        return new Commander[]{
                new DaisyOfTheValley(),
                new HopeOfTheAenSeidhe(),
                new PurebloodElf(),
                new QueenOfDolBlathanna(),
                new TheBeautiful()
        };
    }

    private static Commander[] skelligeCommanders() {
        return new Commander[]{
                new CrachAnCraite(),
                new KingBran()
        };
    }

    private static boolean checkCardInDeck(String name, ArrayList<Card> deck) {
        for (Card card : deck) {
            if (card.getName().equals(name)) return true;
        }
        return false;
    }

    public static void saveDeck(ArrayList<Card> cards, Faction faction, Player player) {
        if (takeConfirmation("Do you want to save it with name?")) makeDataBaseWithName(cards, faction, player);
        else makeDataBaseWithAddress(cards, faction, player);
    }

    public static boolean takeConfirmation(String asking) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Asking permission");
        alert.setHeaderText("Asking permission");
        alert.setContentText(asking);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private static void makeDataBaseWithName(ArrayList<Card> cards, Faction faction, Player player) {
        String url = makeInputDialog("Saving Deck", "Enter a name", "Enter a name to save deck.");
        if (player.getDeck().isEmpty()) {
            showAlert("You have nothing in your deck!");
        } else if (checkExistentOfDataBase(url)) {
            if (takeConfirmation("A database with this name already exists!\nDo you want to change the info of \nthis data base with this name?")) {
                rewriteDataBase(url);
                saveDataInDataBase(url, cards);
            } else
                showAlert("your deck is unsaved.");
        } else saveDataInDataBase(url, cards);
    }

    private static void saveDataInDataBase(String url, ArrayList<Card> cards) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL + url + ".db");
            CardManager cardManager = new CardManager(url);
            cardManager.createTable();
            cardManager.saveCard(faction.getName());
            cardManager.saveCard(faction.getCommander().getName());
            for (Card card : cards) {
                cardManager.saveCard(card.getName());
            }
            connection.close();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    private static void rewriteDataBase(String url) {
        try {
            CardManager cardManager = new CardManager(url);
            cardManager.connect(url);
            cardManager.deleteAllCards();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    private static boolean checkExistentOfDataBase(String name) {
        File file = new File(name + ".db");
        return file.exists();
    }

    private static void updateDataBaseWithAddress(ArrayList<Card> cards, String directoryPath) {
        try {
            String dbFilePath = directoryPath + File.separator + "new_database";
            CardManager cardManager = new CardManager(dbFilePath);
            cardManager.deleteAllCards();
            changeDataBaseWithAddress(directoryPath, cards);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    private static void makeDataBaseWithAddress(ArrayList<Card> cards, Faction faction, Player player) {
        String directoryPath = makeInputDialog("Enter Address", "Please enter a valid directory address", "Please enter a valid directory address");
        File directory = new File(directoryPath);
        if (player.getDeck().isEmpty()) {
            showAlert("You have nothing in your deck!");
        } else if (!directory.exists() || !directory.isDirectory()) {
            showAlert("Invalid directory path: " + directoryPath);
        } else if (checkDataBaseAddress(directoryPath)) {
            if (takeConfirmation("Do you want to change the file in this address?"))
                updateDataBaseWithAddress(cards, directoryPath);
            else showAlert("your deck is unsaved.");
        } else changeDataBaseWithAddress(directoryPath, cards);
    }

    private static void changeDataBaseWithAddress(String directoryPath, ArrayList<Card> cards) {
        try {
            String dbFilePath = directoryPath + File.separator + "new_database";
            CardManager cardManager = new CardManager(dbFilePath);
            cardManager.createTable();
            cardManager.saveCard(faction.getName());
            cardManager.saveCard(faction.getCommander().getName());
            for (Card card : cards) {
                cardManager.saveCard(card.getName());
            }
            cardManager.closeConnection();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    private static boolean checkDataBaseAddress(String directoryPath) {
        File databaseFile = new File(directoryPath, "new_database" + ".db");
        return databaseFile.exists();
    }

    public static List<String> loadDeck(Player player) {
        if (takeConfirmation("Do you want to save it with name?")) return loadDataBaseAndCards(player);
        else return loadDataBaseWithAddress(player);
    }

    private static List<String> loadDataBaseAndCards(Player player) {
        String name = makeInputDialog("Load Deck", "Enter a valid name", "Enter the name of your file please");
        if (!checkExistentOfDataBase(name)) showAlert("We dont have a data base with this name!");
        else {
            try {
                CardManager cardManager = new CardManager(name);
                return cardManager.loadAllCardNames();
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }
        return null;
    }

    private static List<String> loadDataBaseWithAddress(Player player) {
        String directoryPath = makeInputDialog("Load Deck", "Enter a valid address", "Enter a valid address");
        try {
            String dbFilePath = directoryPath + File.separator + "new_database";
            File directory = new File(directoryPath);
            if (!directory.exists() || !directory.isDirectory())
                showAlert("Invalid directory path: " + directoryPath);
            else {
                CardManager cardManager = new CardManager(dbFilePath);
                return cardManager.loadAllCardNames();
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    public static void setPlayerName(String name) {
        player.setName(name);
    }

    public static void makeNewGame(String enemyFaction, String player2, String turn) {
        Game game = new Game(player, new Player(player2));
        User.getLoggedInUser().getAllGames().add(game);
        User.getLoggedInUser().setGamesPlayed(User.getLoggedInUser().getGamesPlayed() + 1);
        game.setDate(new Date());
        game.getEnemy().setFaction(Faction.getFactionByName(enemyFaction));
        ConnectToServer.getOutput().println("commander#" + player.getFaction().getCommander().getName());
        if (turn.equals("yourTurn")) GameController.startTurn();
        Platform.runLater(() -> {
            try {
                new VetoCardMenu(game).start(Main.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
