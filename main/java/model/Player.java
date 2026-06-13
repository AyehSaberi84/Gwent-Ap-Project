package model;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.factions.Faction;

import java.util.ArrayList;

public class Player {
    private User user;

    private String username;
    private ArrayList<Card> deck = new ArrayList<>();
    private HBox hand = new HBox();
    private IntegerProperty totalCard = new SimpleIntegerProperty(0);
    private IntegerProperty unitCard = new SimpleIntegerProperty(0);
    private IntegerProperty specialCard = new SimpleIntegerProperty(0);
    private IntegerProperty powerCard = new SimpleIntegerProperty(0);
    private IntegerProperty heroCard = new SimpleIntegerProperty(0);
    private ArrayList<Card> redrawnCards = new ArrayList<>();

    private Faction faction ;
    private int score ;
    private Pane graveyard = new Pane();
    private int lives = 2;
    private boolean passed;
    private Game game ;

    private HBox siege = new HBox();
    private HBox ranged = new HBox();
    private HBox closeCombat = new HBox();
    private Pane siegeSpecial = new Pane();
    private Pane rangedSpecial = new Pane();
    private Pane closeCombatSpecial = new Pane();
    private Label scoreLabel = new Label("0");
    private Label closeLabel = new Label("0");
    private Label rangedLabel = new Label("0");
    private Label sigeLabel = new Label("0");

    private ImageView leftCrystal = new ImageView(Icons.GEM_ON.getImage());
    private ImageView rightCrystal = new ImageView(Icons.GEM_ON.getImage());

    private Label numberOfCards = new Label("10");

    public Player(String name) {
        username = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public HBox getHand() {
        return hand;
    }

    public void setHand(HBox hand) {
        this.hand = hand;
    }

    public ArrayList<Card> getRedrawnCards() {
        return redrawnCards;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }



    public Pane getGraveyard() {
        return graveyard;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
        updateGems();
    }

    private void updateGems() {
        if (lives > 0) rightCrystal.setImage(Icons.GEM_ON.getImage());
        else rightCrystal.setImage(Icons.GEM_OFF.getImage());
        if (lives > 1) leftCrystal.setImage(Icons.GEM_ON.getImage());
        else leftCrystal.setImage(Icons.GEM_OFF.getImage());
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public HBox getSiege() {
        return siege;
    }

    public void setSiege(HBox siege) {
        this.siege = siege;
    }

    public HBox getRanged() {
        return ranged;
    }

    public void setRanged(HBox ranged) {
        this.ranged = ranged;
    }

    public HBox getCloseCombat() {
        return closeCombat;
    }

    public Pane getSiegeSpecial() {
        return siegeSpecial;
    }

    public Pane getRangedSpecial() {
        return rangedSpecial;
    }

    public Pane getCloseCombatSpecial() {
        return closeCombatSpecial;
    }

    public int getTotalCard() {
        return totalCard.get();
    }

    public IntegerProperty totalCardProperty() {
        return totalCard;
    }

    public void setTotalCard(int totalCard) {
        this.totalCard.set(totalCard);
    }

    public int getUnitCard() {
        return unitCard.get();
    }

    public IntegerProperty unitCardProperty() {
        return unitCard;
    }

    public void setUnitCard(int unitCard) {
        this.unitCard.set(unitCard);
    }

    public int getSpecialCard() {
        return specialCard.get();
    }

    public IntegerProperty specialCardProperty() {
        return specialCard;
    }

    public void setSpecialCard(int specialCard) {
        this.specialCard.set(specialCard);
    }

    public int getPowerCard() {
        return powerCard.get();
    }

    public IntegerProperty powerCardProperty() {
        return powerCard;
    }

    public void setPowerCard(int powerCard) {
        this.powerCard.set(powerCard);
    }

    public int getHeroCard() {
        return heroCard.get();
    }

    public IntegerProperty heroCardProperty() {
        return heroCard;
    }

    public void setHeroCard(int heroCard) {
        this.heroCard.set(heroCard);
    }


    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public Label getNumberOfCards() {
        return numberOfCards;
    }

    public ImageView getLeftCrystal() {
        return leftCrystal;
    }

    public ImageView getRightCrystal() {
        return rightCrystal;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    public Label getCloseLabel() {
        return closeLabel;
    }

    public Label getRangedLabel() {
        return rangedLabel;
    }

    public Label getSigeLabel() {
        return sigeLabel;
    }

    public void setNumberOfCards(int number) {
        numberOfCards.setText(String.valueOf(number));
    }

    public void setTotalScore(int totalScore) {
        score = totalScore;
        scoreLabel.setText(String.valueOf(totalScore));
    }
}
