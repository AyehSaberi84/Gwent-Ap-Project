package view;

import controller.ConnectToServer;
import controller.LogSaver;
import controller.PreGameController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Card;
import model.Game;
import model.Player;
import model.commanders.Commander;
import model.commanders.monsters.*;
import model.commanders.nilfgaardian_empire.*;
import model.commanders.northern_realms.*;
import model.commanders.scoia_tael.*;
import model.commanders.skellige.CrachAnCraite;
import model.commanders.skellige.KingBran;
import model.factions.*;
import view.ShadowViews.ShowFactions;

import java.util.*;

public class PreGameMenu extends Application {
    private Pane pane;
    private Faction faction;
    private final double WIDTH = 1200;
    private final double HEIGHT = 700;
    private ScrollPane deckScrollPane;
    private Player player;
    private ImageView commanderView;
    private final Player loggedInPlayer = new Player("");
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private ArrayList<Card> allCards = new ArrayList<>();
    private HashMap<ImageView, Card> cardImageViewHashMap = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {
        loggedInPlayer.setName("");
        player = loggedInPlayer;
        PreGameController.setPlayer(player);
        ConnectToServer.getOutput().println("getPlayerName");
        pane = new Pane();
        setSize(pane, stage);
        pane.setBackground(new Background(createBackgroundImage()));
        makeView(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void setSize(Pane pane, Stage stage) {
        pane.setMaxWidth(WIDTH);
        pane.setMinWidth(WIDTH);
        pane.setMaxHeight(HEIGHT);
        pane.setMinHeight(HEIGHT);
        stage.setMinWidth(WIDTH + 12);
        stage.setMinHeight(HEIGHT + 36);
        stage.setMaxWidth(WIDTH + 12);
        stage.setMaxHeight(HEIGHT + 36);
        stage.setResizable(true);
    }

    private BackgroundImage createBackgroundImage() {
        Image image = new Image(Objects.requireNonNull(Game.class.getResource("/Images/BG/preGameBG.jpg")).toExternalForm(), WIDTH, HEIGHT, false, false);
        return new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }

    private void makeView(Pane pane) {
        makeHBoxForTheTop(pane);
        scrollBarForCollection(pane);
        scrollBarForDeck(pane);
        makeVBoxForCenter(pane);
    }

    private void scrollBarForDeck(Pane pane) {
        makeScrollPaneForRight(pane);
    }

    private void scrollBarForCollection(Pane pane) {
        makeScrollBarForLeft(pane, addCardToScreen());
    }

    private void makeScrollBarForLeft(Pane pane, VBox vBox) {
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setStyle("-fx-background: rgba(0,0,0,0.96);");
        scrollPane.setLayoutY(HEIGHT / 5);
        scrollPane.setLayoutX(10 + (double) 0);
        scrollPane.setPrefSize(WIDTH * 0.36, HEIGHT * 0.8 - 10);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setFitToWidth(true);
        pane.getChildren().add(scrollPane);
    }

    private void makeScrollPaneForRight(Pane pane) {
        deckScrollPane = new ScrollPane(new VBox());
        deckScrollPane.setStyle("-fx-background: rgba(0,0,0,0.96);");
        deckScrollPane.setLayoutY(HEIGHT / 5);
        deckScrollPane.setLayoutX(10 + 750.0);
        deckScrollPane.setPrefSize(WIDTH * 0.36, HEIGHT * 0.8 - 10);
        deckScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        pane.getChildren().add(deckScrollPane);
    }

    private void makeVBoxForCenter(Pane pane) {
        PreGameController.checkCommander(faction);
        VBox centerVBox = new VBox(0);
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.setVisible(true);
        centerVBox.setPrefSize(WIDTH * 0.25, HEIGHT * 0.8 - 10);
        centerVBox.setLayoutX(WIDTH * 0.375);
        centerVBox.setLayoutY(HEIGHT / 5);
        centerVBox.setStyle("-fx-background-color: rgba(5,5,5,0.44);");
        addCommanderToView(centerVBox);
        pane.getChildren().add(centerVBox);
    }

    private void makeHBoxForTheTop(Pane pane) {
        HBox hBox = new HBox(10);
        hBox.setPrefSize(WIDTH - 10, HEIGHT * 0.2 - 10);
        hBox.setLayoutX(5);
        hBox.setLayoutY(5);
        hBox.setStyle("-fx-background-color: rgba(0,0,0,0.44);");
        addInfoToTheScreen(hBox);
        pane.getChildren().add(hBox);

    }

    private void addInfoToTheScreen(HBox hBox) {
        addFactionView(hBox);
        hBox.getChildren().add(makeVBoxForInfo());
        hBox.getChildren().addAll(makeVBoxForDeckInfo());
    }

    private void addFactionView(HBox hBox) {
        Label label;
        if (loggedInPlayer.getFaction() == null && PreGameController.getFaction() == null)
            faction = PreGameController.assumeRandomFaction();
        else if (PreGameController.getFaction() == null) faction = loggedInPlayer.getFaction();
        else faction = PreGameController.getFaction();
        label = showFaction(faction, hBox);
        VBox vBox = makeVBoxForFaction(label);
        hBox.getChildren().add(vBox);
    }

    private Label showFaction(Faction faction, HBox hBox) {
        ImageView imageView = new ImageView();
        imageView.setImage(faction.getFlagImage());
        Label label = null;

        switch (faction) {
            case Monster monster -> {
                label = makeLabel("Monster");
                loggedInPlayer.setFaction(monster);
            }
            case NilfgaardianEmpire nilfgaardianEmpire -> {
                label = makeLabel("Nilfgaardian Empire");
                loggedInPlayer.setFaction(nilfgaardianEmpire);
            }
            case RealmNorthern realmNorthern -> {
                label = makeLabel("Realm Northern");
                loggedInPlayer.setFaction(realmNorthern);
            }
            case ScoiaTael scoiaTael -> {
                label = makeLabel("ScoiaTael");
                loggedInPlayer.setFaction(scoiaTael);
            }
            case Skellige skellige -> {
                label = makeLabel("Skellige");
                loggedInPlayer.setFaction(skellige);
            }
            default -> {
            }
        }
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.getChildren().addAll(imageView);
        return label;
    }

    private Label makeLabel(String name) {
        Label label = new Label(name);
        label.setFont(Font.font("Bookman Old Style", 20));
        label.setTextFill(Color.WHITE);
        label.setLayoutX(100);
        label.setLayoutY(100);
        return label;
    }

    private Button makeButton(String name) {
        Button button = new Button(name);
        button.setFont(Font.font("Times New Roman", 18));
        button.setStyle("-fx-background-color: rgba(0,0,0,0.65); -fx-text-fill: #3d3b3b");
        button.setPrefSize(WIDTH / 7, 30);
        button.setLayoutX(100);
        button.setLayoutY(100);
        return button;
    }

    private VBox makeVBoxForFaction(Label label) {
        VBox vBox = new VBox(10);
        vBox.setPrefSize(WIDTH / 6, HEIGHT * 0.2 - 10);
        vBox.setStyle("-fx-background-color: rgba(115,121,116,0);");

        Button showFaction = makeButton("Show All Factions");
        showFaction.setOnAction(event -> {
            ShowFactions showFactions = new ShowFactions(pane, this);
            try {
                showFactions.start(Main.stage);
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        });
        vBox.getChildren().addAll(label, showFaction);
        return vBox;
    }

    private VBox makeVBoxForInfo() {
        VBox vBox = new VBox(10);
        Button load = makeButton("Load Deck");
        Button save = makeButton("Save Deck");
        load.setOnAction(event -> {

            List<String> names = PreGameController.loadDeck(player);
            if (names != null) makeFaction(player, names.getFirst(), names.get(1), names);
        });
        save.setOnAction(event -> PreGameController.saveDeck(player.getDeck(), faction, player));

        vBox.getChildren().addAll(load, save);
        return vBox;
    }

    public void changeFaction(Faction faction1) {
        faction = faction1;
        PreGameController.setFaction(faction);
        PreGameController.zeroCondition(player);
        resetScene();
    }

    private VBox makeVBoxForDeckInfo() {
        VBox vBox = new VBox(10);
        Button add = makeButton("Add To Deck");
        add.setOnAction(event -> {
            Optional<Pair<String, String>> result = PreGameController.showTwoInputDialog("Add to Deck", "Please fill the filled", "Add", "Name Of Card", "Number");
            result.ifPresent(inputs -> {
                int number = PreGameController.numberOfAddOrRemove(inputs.getValue());
                Card card = PreGameController.checkDataOfCard(inputs.getKey(), number, player);
                if (card != null) endOfAddingCard(card, number);
            });
        });
        Button delete = makeButton("Delete From Deck");
        delete.setOnAction(event -> {
            Optional<Pair<String, String>> result = PreGameController.showTwoInputDialog("Remove From Deck", "Please fill the filled", "Remove", "Name Of Card", "Number");
            result.ifPresent(inputs -> {
                int number = PreGameController.numberOfAddOrRemove(inputs.getValue());
                Card card = PreGameController.checkDataOfCardForRemove(inputs.getKey(), number, player);
                if (card != null) removeCard(card, number);
            });
        });
        vBox.getChildren().addAll(add, delete);
        return vBox;
    }

    public VBox addCardToScreen() {
        allCards = PreGameController.findCard(faction);
        allCards.addAll(Card.makeNeutralCards());
        VBox imageBox = new VBox(10);
        HBox currentRow = null;

        for (int i = 0; i < allCards.size(); i++) {
            if (i % 2 == 0) {
                currentRow = new HBox(10); // 10 is the spacing between images in a row
                imageBox.getChildren().add(currentRow);
            }

            Card card = allCards.get(i);
            ImageView imageView = new ImageView(card.getImage());
            cardImageViewHashMap.put(imageView, card);
            imageViews.add(imageView);
            // Create a tooltip with card information
            Tooltip tooltip = makeToolTip(imageView, allCards.get(i));
            readyForAddToDeck(imageView, card);
            // Set size for the image view
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true); // Maintain aspect ratio
            currentRow.getChildren().add(imageView);
            // Add a listener for changes in the card's information
            card.addChangeListener(() -> {
                String updatedInfo = PreGameController.makeInfoOfCard(card, card.getCurrentNumOfCard());
                tooltip.setText(updatedInfo);
            });
        }
        return imageBox;
    }

    private Tooltip makeToolTip(ImageView imageView, Card card) {
        String info = PreGameController.makeInfoOfCard(card, card.getCurrentNumOfCard());
        Tooltip tooltip = new Tooltip(info);
        card.setTooltip(tooltip);
        // Attach the tooltip to the image view
        Tooltip.install(imageView, tooltip);
        // Set event handlers for showing and hiding the tooltip
        imageView.setOnMouseEntered(event -> tooltip.show(imageView, event.getScreenX(), event.getScreenY() + 10));
        imageView.setOnMouseExited(event -> tooltip.hide());
        return tooltip;
    }

    private void readyForAddToDeck(ImageView imageView, Card card) {
        imageView.setOnMouseClicked(event -> {
            if (card.getNumberOfCardsInGame() == PreGameController.findNumOfCardInGame(card, player)) {
                PreGameController.showAlert("You cant have this card more than " + card.getNumberOfCardsInGame());
            } else if (card.getBasePower() == -1 && player.getSpecialCard() + 1 > 10) {
                PreGameController.showAlert("You cant have more than 10 special cards");
            } else {
                increaseNormal(card);
                addToDeck(card, imageView);
            }
        });
    }

    private void updateInfo(Card card) {
        player.setTotalCard(player.getDeck().size());
        if (card.getBasePower() != -1) {
            player.setUnitCard(player.getUnitCard() + 1);
            player.setPowerCard(player.getPowerCard() + card.getBasePower());
        } else player.setSpecialCard(player.getSpecialCard() + 1);
        if (card.isHero()) player.setHeroCard(player.getHeroCard() + 1);
    }

    private void resetScene() {
        pane.getChildren().clear(); // پاک کردن محتوای فعلی Scene
        makeView(pane); // بازسازی محتوای اولیه Scene
    }

    private Label makeLabelForInfo(String name) {
        Label label = new Label(name);
        label.setFont(Font.font("Calibri (Body)", 18));
        label.setTextFill(Color.DARKORANGE);
        label.setLayoutX(100);
        label.setLayoutY(100);
        return label;

    }

    private void addCommanderToView(VBox vBox) {
        Label label = makeLabelForInfo("Welcome " + player.getName());
        Label leader = makeLabelForInfo("Leader");
        commanderView = new ImageView(faction.getCommander().getImage());
        commanderView.setOnMouseClicked(event -> {
            showLeaders();
        });
        commanderView.setFitWidth(170);
        commanderView.setFitHeight(180);
        vBox.getChildren().addAll(label, leader, commanderView);
        showUserInfo(vBox);
    }

    private void showUserInfo(VBox vBox) {
        showTotalCard(vBox);
        showUnitCards(vBox);
        showSpecial(vBox);
        showStrength(vBox);
        showHero(vBox);
        startGame(vBox);
    }

    private void showTotalCard(VBox vBox) {
        Label total = makeLabelForInfo("Total cards in deck ");
        HBox hBox = new HBox(10);
        Image TotalCard = new Image(String.valueOf(PreGameController.class.getResource("/Images/Icons/deck_stats_count.png")));
        ImageView imageView = new ImageView(TotalCard);
        hBox.setAlignment(Pos.CENTER);
        Label number = makeLabel(String.valueOf(player.getTotalCard()));
        player.totalCardProperty().addListener((observable, oldValue, newValue) -> {
            number.setText(newValue.toString());
        });
        vBox.getChildren().add(total);
        hBox.getChildren().addAll(imageView, number);
        vBox.getChildren().addAll(hBox);
    }

    private void showUnitCards(VBox vBox) {
        Label label = makeLabelForInfo("Number Of Unit Cards");
        HBox hBox = new HBox(10);
        Image image = new Image(String.valueOf(PreGameController.class.getResource("/Images/Icons/deck_stats_unit.png")));
        ImageView imageView = new ImageView(image);
        hBox.setAlignment(Pos.CENTER);
        Label number = makeLabel(String.valueOf(player.getUnitCard()));
        player.unitCardProperty().addListener((observable, oldValue, newValue) -> {
            number.setText(newValue.toString());
        });
        vBox.getChildren().add(label);
        hBox.getChildren().addAll(imageView, number);
        vBox.getChildren().addAll(hBox);
    }

    private void showSpecial(VBox vBox) {
        Label label = makeLabelForInfo("Special Card");
        HBox hBox = new HBox(10);
        Image image = new Image(String.valueOf(PreGameController.class.getResource("/Images/Icons/deck_stats_special.png")));
        ImageView imageView = new ImageView(image);
        hBox.setAlignment(Pos.CENTER);
        Label number = makeLabel(String.valueOf(player.getSpecialCard()));
        player.specialCardProperty().addListener((observable, oldValue, newValue) -> {
            number.setText(newValue.toString());
        });
        vBox.getChildren().add(label);
        hBox.getChildren().addAll(imageView, number);
        vBox.getChildren().addAll(hBox);
    }

    private void showStrength(VBox vBox) {
        Label label = makeLabelForInfo("Total Unit Card Strength");
        HBox hBox = new HBox(10);
        Image image = new Image(String.valueOf(PreGameController.class.getResource("/Images/Icons/deck_stats_strength.png")));
        ImageView imageView = new ImageView(image);
        hBox.setAlignment(Pos.CENTER);
        Label number = makeLabel(String.valueOf(player.getPowerCard()));
        player.powerCardProperty().addListener((observable, oldValue, newValue) -> {
            number.setText(newValue.toString());
        });
        vBox.getChildren().add(label);
        hBox.getChildren().addAll(imageView, number);
        vBox.getChildren().addAll(hBox);
    }

    private void showHero(VBox vBox) {
        Label label = makeLabelForInfo("Show Hero");
        HBox hBox = new HBox(10);
        Image image = new Image(String.valueOf(PreGameController.class.getResource("/Images/Icons/deck_stats_hero.png")));
        ImageView imageView = new ImageView(image);
        hBox.setAlignment(Pos.CENTER);
        Label number = makeLabel(String.valueOf(player.getHeroCard()));
        player.heroCardProperty().addListener((observable, oldValue, newValue) -> {
            number.setText(newValue.toString());
        });
        vBox.getChildren().add(label);
        hBox.getChildren().addAll(imageView, number);
        vBox.getChildren().addAll(hBox);
    }

    private void startGame(VBox vBox) {
        Button button = makeButton("Start game");
        button.setOnAction(event -> PreGameController.startGame(loggedInPlayer));
        vBox.getChildren().add(button);
    }

    private void showLeaders() {
        VBox vBox = new VBox(10);
        HBox imageBox = new HBox();
        imageBox.setSpacing(15);
        double width = 0;
        double number = 150;
        javafx.scene.image.Image[] images = PreGameController.findImages(faction);
        Commander[] commanders = PreGameController.findCommanderOfFaction(faction);
        imageBox.getChildren().clear();
        for (Image img : images) {
            if (img.getUrl().equals(faction.getCommander().getImage().getUrl())) continue;
            ImageView imgView = new ImageView(img);
            imgView.setOnMouseClicked(event -> {
                commanderView.setImage(img);
                for (Commander commander : commanders) {
                    if (commander.getImage().getUrl().equals(img.getUrl()))
                        faction.setCommander(commander);
                }
            });
            imgView.setFitWidth(number);
            width += number;
            imgView.setPreserveRatio(true);
            imageBox.getChildren().add(imgView);
        }

        Button button = makeButton("Exit");
        button.setOnAction(event -> vBox.setVisible(false));

        vBox.setLayoutX(WIDTH / 2 - width / 2);
        vBox.setLayoutY(HEIGHT / 2 - number / 2);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(imageBox, button);

        pane.getChildren().add(vBox);

    }

    private void increaseNormal(Card card) {
        card.setCurrentNumOfCard(card.getCurrentNumOfCard() + 1);
        card.notifyChange();
    }

    private void increaseByImageView(ImageView imageView) {
        Card card = cardImageViewHashMap.get(imageView);
        increaseNormal(card);
    }

    private void decreaseByImageView(ImageView imageView, int number) {
        Card card = cardImageViewHashMap.get(imageView);
        decreaseNormal(card, number);
    }

    private void decreaseNormal(Card card, int number) {
        card.setCurrentNumOfCard(card.getCurrentNumOfCard() - number);
        card.notifyChange();
    }

    private void addToDeck(Card card, ImageView imageView) {
        Card copyCard = new Card(card);
        player.getDeck().add(copyCard);
        updateInfo(card);
        // Create a copy of the clicked image view
        ImageView copyImageView = new ImageView(imageView.getImage());
        copyImageView.setFitWidth(200); // Set width (optional)
        copyImageView.setPreserveRatio(true); // Maintain aspect ratio
        // Check if the deckScrollPane already has a VBox as content
        copyImageView.setOnMouseClicked (event -> {
            player.getDeck().remove(copyCard);
            removeCard(card,1);
        });
        Node content = deckScrollPane.getContent();
        VBox vBox;
        if (content instanceof VBox) vBox = (VBox) content;
        else {
            vBox = new VBox(); // Create a new VBox if the content is not a VBox
            deckScrollPane.setContent(vBox);
        }
        // Check if the current row of the VBox is full
        HBox currentRow;
        ObservableList<Node> children = vBox.getChildren();
        if (children.isEmpty() || !(children.getLast() instanceof HBox)) {
            currentRow = new HBox(10); // Create a new row if it's the first row or if the previous row is full
            vBox.getChildren().add(currentRow);
        } else currentRow = (HBox) children.getLast(); // Get the last row
        // Add the copy to the current row
        currentRow.getChildren().add(copyImageView);
        Tooltip tooltip = card.getTooltip();
        // If the tooltip doesn't exist, create a new one
        if (tooltip == null) {
            String info = PreGameController.makeInfoOfCard(card, card.getCurrentNumOfCard());
            tooltip = new Tooltip(info);
            card.setTooltip(tooltip);
        }
        // Attach the tooltip to the copied image view
        Tooltip.install(copyImageView, tooltip);
        String info = PreGameController.makeInfoOfCard(card, card.getCurrentNumOfCard());
        card.setTooltip(new Tooltip(info));
        // Limit each row to two images
        if (currentRow.getChildren().size() == 3) {
            HBox newRow = new HBox(10); // Create a new row
            newRow.getChildren().addAll(copyImageView);
            vBox.getChildren().add(newRow); // Add the new row to the VBox
        }
    }

    private ImageView findImageView(Card card) {
        ImageView view = null;
        for (ImageView imageView : imageViews) {
            if (imageView.getImage().getUrl().equals(card.getImage().getUrl())) {
                view = imageView;
            }
        }
        return view;
    }

    public void endOfAddingCard(Card card, int number) {
        ImageView view = findImageView(card);
        for (Card card1 : allCards) {
            if (card1.getName().equals(card.getName())) card = card1;
        }
        for (int i = 0; i < number; i++) {
            assert view != null;
            increaseByImageView(view);
            addToDeck(card, view);
        }
    }

    private void removeCard(Card card, int number) {
        Node content = deckScrollPane.getContent();
        if (!(content instanceof VBox vBox)) {
            System.out.println("Content is not a VBox");
            return;
        }
        ObservableList<Node> rows = vBox.getChildren();

        // لیستی برای نگهداری ImageView هایی که باید حذف شوند
        List<ImageView> imagesToRemove = new ArrayList<>();

        // یافتن ImageView مربوط به کارت
        for (Node rowNode : rows) {
            if (!(rowNode instanceof HBox row)) {
                continue;
            }
            ObservableList<Node> images = row.getChildren();
            for (Node imageNode : images) {
                if (imageNode instanceof ImageView imageView) {
                    if (imageView.getImage().equals(card.getImage())) {
                        imagesToRemove.add(imageView);
                        if (imagesToRemove.size() == number) {
                            break;
                        }
                    }
                }
            }
            if (imagesToRemove.size() == number) {
                break;
            }
        }

        if (imagesToRemove.isEmpty()) {
            System.out.println("No images found to remove");
            return;
        }

        // حذف ImageView های یافت شده
        for (ImageView imageView : imagesToRemove) {
            for (Node rowNode : rows) {
                if (rowNode instanceof HBox row) {
                    row.getChildren().remove(imageView);
                }
            }
        }

        // حذف ردیف‌های خالی
        rows.removeIf(rowNode -> {
            if (rowNode instanceof HBox) {
                return ((HBox) rowNode).getChildren().isEmpty();
            }
            return false;
        });

        // به‌روزرسانی اطلاعات
        ImageView view = findImageView(card);
        decreaseByImageView(view, number);
        removeElementsFromList(player.getDeck(), card, number);
        decreaseInfo(number, card);
        reorganizeDeckImages(vBox);

        // نوسازی UI
        deckScrollPane.layout();
    }

    private void decreaseInfo(int number, Card card) {
        player.setTotalCard(player.getDeck().size());
        if (card.getBasePower() != -1) player.setUnitCard(player.getUnitCard() - number);
        else player.setSpecialCard(player.getSpecialCard() - number);
        if (card.isHero()) player.setHeroCard(player.getHeroCard() - number);
        player.setPowerCard(player.getPowerCard() - (card.getPower()) * number);
    }

    private void reorganizeDeckImages(VBox vBox) {
        ObservableList<Node> rows = vBox.getChildren();
        List<ImageView> allImages = new ArrayList<>();
        for (Node rowNode : rows) {
            if (!(rowNode instanceof HBox)) {
                continue;
            }
            HBox row = (HBox) rowNode;
            for (Node imageNode : row.getChildren()) {
                if (imageNode instanceof ImageView) {
                    allImages.add((ImageView) imageNode);
                }
            }
        }

        VBox newVBox = new VBox();
        HBox newRow = new HBox(10);

        // مرتب‌سازی مجدد تصاویر از لیست موقت
        for (ImageView imageView : allImages) {
            if (newRow.getChildren().size() == 2) {
                newVBox.getChildren().add(newRow);
                newRow = new HBox(10);
            }
            newRow.getChildren().add(imageView);
        }

        if (!newRow.getChildren().isEmpty()) {
            newVBox.getChildren().add(newRow);
        }

        deckScrollPane.setContent(newVBox);
    }

    public static void removeElementsFromList(List<Card> list, Card card, int number) {
        Iterator<Card> iterator = list.iterator();
        int count = 0;
        while (iterator.hasNext() && count < number) {
            if (iterator.next() == card) {
                iterator.remove();
                count++;
            }
        }
    }

    public void makeFaction(Player player, String name, String commander, List<String> cards) {
        switch (name) {
            case "Monster" -> {
                if (player.getFaction().getName().equals("Monster")) completeMonsters(player, commander, cards);
                else PreGameController.showAlert("You cant have these cards in this faction.");
            }
            case "Nilfgaardian Empire" -> {
                if (player.getFaction().getName().equals("Nilfgaardian Empire"))
                    completNilfgaardianEmpire(player, commander, cards);
                else PreGameController.showAlert("You cant have these cards in this faction.");
            }
            case "Realm Northern" -> {
                if (player.getFaction().getName().equals("Realm Northern"))
                    completeRealmNorthern(player, commander, cards);
                else PreGameController.showAlert("You cant have these cards in this faction.");
            }
            case "ScoiaTeal" -> {
                if (player.getFaction().getName().equals("ScoiaTeal")) completeScoiaTae(player, commander, cards);
                else PreGameController.showAlert("You cant have these cards in this faction.");
            }
            case "Skellige" -> {
                if (player.getFaction().getName().equals("Skellige")) completSkellige(player, commander, cards);
                else PreGameController.showAlert("You cant have these cards in this faction.");
            }
        }
    }

    private void completeMonsters(Player player, String commander, List<String> cards) {
        player.setFaction(new Monster());
        switch (commander) {
            case "Bringer Of Death" -> {
                BringerOfDeath bringerOfDeath = new BringerOfDeath();
                player.getFaction().setCommander(bringerOfDeath);
                commanderView.setImage(bringerOfDeath.getImage());
            }
            case "Commander Of The Red Riders" -> {
                CommanderOfTheRedRiders commanderOfTheRedRiders = new CommanderOfTheRedRiders();
                player.getFaction().setCommander(commanderOfTheRedRiders);
                commanderView.setImage(commanderOfTheRedRiders.getImage());
            }
            case "Destroyer Of Worlds" -> {
                DestroyerOfWorlds destroyerOfWorlds = new DestroyerOfWorlds();
                player.getFaction().setCommander(destroyerOfWorlds);
                commanderView.setImage(destroyerOfWorlds.getImage());
            }
            case "King Of The Wild Hunt" -> {
                KingOfTheWildHunt kingOfTheWildHunt = new KingOfTheWildHunt();
                player.getFaction().setCommander(kingOfTheWildHunt);
                commanderView.setImage(kingOfTheWildHunt.getImage());
            }
            case "The Treacherous" -> {
                TheTreacherous theTreacherous = new TheTreacherous();
                player.getFaction().setCommander(theTreacherous);
                commanderView.setImage(theTreacherous.getImage());
            }
        }
        fillCards(cards, allCards, player);
    }

    private void fillCards(List<String> cards, ArrayList<Card> allCards, Player player) {
        HashMap<String, Integer> map = new HashMap<>();
        for (Card allCard : allCards) {
            for (int j = 2; j < cards.size(); j++) {
                if (allCard.getName().equals(cards.get(j))) {
                    map.put(cards.get(j), map.getOrDefault(cards.get(j), 0) + 1);
                }
            }
        }
        if (checkCardInGame(map, player)) {
            for (Card card : allCards) {
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    if (card.getName().equals(entry.getKey())) endOfAddingCard(card, map.get(card.getName()));
                }
            }
        }
    }

    private static boolean checkCardInGame(HashMap<String, Integer> map, Player player) {
        for (Card card : player.getDeck()) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (card.getName().equals(entry.getKey())) {
                    int count = card.getCurrentNumOfCard() + entry.getValue();
                    if (count > card.getNumberOfCardsInGame()) {
                        PreGameController.showAlert("You cant have more than " + card.getNumberOfCardsInGame() + " from " + card.getName());
                        return false;
                    }
                }
            }
        }
        return true;
    }


    private void completNilfgaardianEmpire(Player player, String commander, List<String> cards) {
        player.setFaction(new NilfgaardianEmpire());
        switch (commander) {
            case "Emperor Of Nilfgaard" -> {
                EmperorOfNilfgaard emperorOfNilfgaard = new EmperorOfNilfgaard();
                player.getFaction().setCommander(emperorOfNilfgaard);
                commanderView.setImage(emperorOfNilfgaard.getImage());
            }
            case "His Imperial Majesty" -> {
                HisImperialMajesty hisImperialMajesty = new HisImperialMajesty();
                player.getFaction().setCommander(hisImperialMajesty);
                commanderView.setImage(hisImperialMajesty.getImage());
            }
            case "Invader Of The North" -> {
                InvaderOfTheNorth invaderOfTheNorth = new InvaderOfTheNorth();
                player.getFaction().setCommander(invaderOfTheNorth);
                commanderView.setImage(invaderOfTheNorth.getImage());
            }
            case "The Relentless" -> {
                TheRelentless relentless = new TheRelentless();
                player.getFaction().setCommander(relentless);
                commanderView.setImage(relentless.getImage());
            }
            case "The White Flame" -> {
                TheWhiteFlame theWhiteFlame = new TheWhiteFlame();
                player.getFaction().setCommander(theWhiteFlame);
                commanderView.setImage(theWhiteFlame.getImage());
            }
        }
        NilfgaardianEmpire nilfgaardianEmpire = new NilfgaardianEmpire();
        ArrayList<Card> allCards = nilfgaardianEmpire.getAllCards();
        allCards.addAll(Card.makeNeutralCards());
        fillCards(cards, allCards, player);
    }

    private void completeRealmNorthern(Player player, String commander, List<String> cards) {
        player.setFaction(new RealmNorthern());
        switch (commander) {
            case "King Of Temeria" -> {
                KingOfTemeria kingOfTemeria = new KingOfTemeria();
                player.getFaction().setCommander(kingOfTemeria);
                commanderView.setImage(kingOfTemeria.getImage());
            }
            case "Lord Commander Of The North" -> {
                LordCommanderOfTheNorth lordCommanderOfTheNorth = new LordCommanderOfTheNorth();
                player.getFaction().setCommander(lordCommanderOfTheNorth);
                commanderView.setImage(lordCommanderOfTheNorth.getImage());
            }
            case "Son Of Medell" -> {
                SonOfMedell sonOfMedell = new SonOfMedell();
                player.getFaction().setCommander(sonOfMedell);
                commanderView.setImage(sonOfMedell.getImage());
            }
            case "The Siegemaster" -> {
                TheSiegemaster theSiegemaster = new TheSiegemaster();
                player.getFaction().setCommander(theSiegemaster);
                commanderView.setImage(theSiegemaster.getImage());
            }
            case "The Steel Forged" -> {
                TheSteelForged theSteelForged = new TheSteelForged();
                player.getFaction().setCommander(theSteelForged);
                commanderView.setImage(theSteelForged.getImage());
            }
        }
        RealmNorthern realmNorthern = new RealmNorthern();
        ArrayList<Card> allCards = realmNorthern.getAllCards();
        allCards.addAll(Card.makeNeutralCards());
        fillCards(cards, allCards, player);
    }

    private void completeScoiaTae(Player player, String commander, List<String> cards) {
        player.setFaction(new ScoiaTael());
        switch (commander) {
            case "Daisy Of The Valley" -> {
                DaisyOfTheValley daisyOfTheValley = new DaisyOfTheValley();
                player.getFaction().setCommander(daisyOfTheValley);
                commanderView.setImage(daisyOfTheValley.getImage());
            }
            case "Hope Of The AenSeidhe" -> {
                HopeOfTheAenSeidhe hopeOfTheAenSeidhe = new HopeOfTheAenSeidhe();
                player.getFaction().setCommander(hopeOfTheAenSeidhe);
                commanderView.setImage(hopeOfTheAenSeidhe.getImage());
            }
            case "Pureblood Elf" -> {
                PurebloodElf purebloodElf = new PurebloodElf();
                player.getFaction().setCommander(purebloodElf);
                commanderView.setImage(purebloodElf.getImage());
            }
            case "Queen Of DolBlathanna" -> {
                QueenOfDolBlathanna queenOfDolBlathanna = new QueenOfDolBlathanna();
                player.getFaction().setCommander(queenOfDolBlathanna);
                commanderView.setImage(queenOfDolBlathanna.getImage());
            }
            case "The Beautiful" -> {
                TheBeautiful theBeautiful = new TheBeautiful();
                player.getFaction().setCommander(theBeautiful);
                commanderView.setImage(theBeautiful.getImage());
            }
        }
        ScoiaTael scoiaTael = new ScoiaTael();
        ArrayList<Card> allCards = scoiaTael.getAllCards();
        allCards.addAll(Card.makeNeutralCards());
        fillCards(cards, allCards, player);
    }

    private void completSkellige(Player player, String commander, List<String> cards) {
        player.setFaction(new Skellige());
        if (commander.equals("Crach An Craite")) {
            CrachAnCraite crachAnCraite = new CrachAnCraite();
            player.getFaction().setCommander(crachAnCraite);
            commanderView.setImage(crachAnCraite.getImage());
        } else if (commander.equals("King Bran")) {
            KingBran kingBran = new KingBran();
            player.getFaction().setCommander(kingBran);
            commanderView.setImage(kingBran.getImage());
        }
        Skellige skellige = new Skellige();
        ArrayList<Card> allCards = skellige.getAllCards();
        allCards.addAll(Card.makeNeutralCards());
        fillCards(cards, allCards, player);
    }

}
