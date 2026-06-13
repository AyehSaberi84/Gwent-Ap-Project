package model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.factions.*;

import java.util.ArrayList;

public class Card extends Pane {
    private final String name;
    private Faction faction;
    private Tooltip tooltip;
    private final String cardType;
    private final boolean isHero;
    private int currentNumOfCard;
    private final int basePower;
    private int power;
    private int numberOfCardsInGame;
    private final Ability ability;
    private static ArrayList<Card> neutral;
    private final ArrayList<ChangeListener> listeners = new ArrayList<>();
    private final Image image;
    private final Image rawImage;
    private final Image powerIcon;
    private final Image placeIcon;
    private Label powerLabel;


    public Card(String name, Faction faction, String cardType, boolean isHero, int basePower, int numberOfCardsInGame, Ability ability, String imageSource) {
        this.name = name;
        this.faction = faction;
        this.cardType = cardType;
        this.isHero = isHero;
        this.basePower = basePower;
        power = basePower;
        this.numberOfCardsInGame = numberOfCardsInGame;
        this.ability = ability;
        this.rawImage = new Image(String.valueOf(Card.class.getResource("/RawImages" + imageSource)));
        this.image = new Image(String.valueOf(Card.class.getResource("/Images" + imageSource)));
        powerIcon = setPowerIcon();
        placeIcon = setPlaceIcon();
        ImageView imageView = new ImageView(getRawImage());
        imageView.setFitWidth(61.8);
        imageView.setPreserveRatio(true);
        getChildren().add(imageView);
        getChildren().add(makePowerIcon());
        getChildren().add(makePlaceIcon());
        if (getAbility() != null) getChildren().add(makeAbilityIcon());
        if (basePower >= 0) makePowerLabel();
    }

    public Card(Card card) {
        this.name = card.name;
        this.faction = card.faction;
        this.cardType = card.cardType;
        this.isHero = card.isHero;
        this.basePower = card.basePower;
        power = basePower;
        this.ability = card.ability;
        this.rawImage = card.rawImage;
        this.image = card.image;
        powerIcon = setPowerIcon();
        placeIcon = setPlaceIcon();
        ImageView imageView = new ImageView(getRawImage());
        imageView.setFitWidth(61.8);
        imageView.setPreserveRatio(true);
        getChildren().add(imageView);
        getChildren().add(makePowerIcon());
        getChildren().add(makePlaceIcon());
        if (getAbility() != null) getChildren().add(makeAbilityIcon());
        if (basePower >= 0) makePowerLabel();
    }

    public static Card makeCardWithNameFromFaction(String cardName, String factionName) {
        return switch (factionName) {
            case "Neutral" -> makeCardWithName(cardName);
            case "Monster" -> Monster.makeCardWithName(cardName);
            case "Nilfgaardian Empire" -> NilfgaardianEmpire.makeCardWithName(cardName);
            case "Realm Northern" -> RealmNorthern.makeCardWithName(cardName);
            case "ScoiaTeal" -> ScoiaTael.makeCardWithName(cardName);
            case "Skellige" -> Skellige.makeCardWithName(cardName);
            default -> null;
        };
    }

    public static Card makeCardWithName(String cardName) {
        System.out.println(cardName);
        for (Card card : neutral) {
            if (card.getName().equals(cardName)) return new Card(card);
        }
        return null;
    }

    public static String getFactionName(Card card) {
        if (card.faction == null) return "Neutral";
        return card.faction.getName();
    }

    private void makePowerLabel() {
        powerLabel = new Label(String.valueOf(power));
        powerLabel.setLayoutX(getLayoutX() + 1);
        powerLabel.setLayoutY(getLayoutY() + 3);
        powerLabel.setPrefWidth(20);
        powerLabel.setFont(new Font(12));
        powerLabel.setAlignment(Pos.CENTER);
        if (isHero) powerLabel.setTextFill(Color.WHITE);
        else powerLabel.setTextFill(Color.BLACK);
        this.getChildren().add(powerLabel);
    }

    private ImageView makePowerIcon() {
        ImageView powerIcon = new ImageView(getPowerIcon());
        powerIcon.setLayoutX(getLayoutX() - 3);
        powerIcon.setLayoutY(getLayoutY() - 2);
        powerIcon.setFitWidth(43);
        powerIcon.setPreserveRatio(true);
        return powerIcon;
    }


    private ImageView makePlaceIcon() {
        ImageView placeIcon = new ImageView(getPlaceIcon());
        placeIcon.setLayoutX(getLayoutX() + 43);
        placeIcon.setLayoutY(getLayoutY() + 70);
        placeIcon.setFitWidth(18.4);
        placeIcon.setPreserveRatio(true);
        return placeIcon;
    }

    private ImageView makeAbilityIcon() {
        if (getAbility() == null) return null;
        ImageView placeIcon = new ImageView(getAbility().getIcon());
        placeIcon.setLayoutX(getLayoutX() + 22);
        placeIcon.setLayoutY(getLayoutY() + 70);
        placeIcon.setFitWidth(18.4);
        placeIcon.setPreserveRatio(true);
        return placeIcon;
    }

    private Image setPowerIcon() {
        if (isHero) return Icons.HERO.getImage();
        if (basePower >= 0) return Icons.NORMAL.getImage();
        return switch (name) {
            case "Mardroeme" -> Icons.MARDROEME.getImage();
            case "Biting Frost" -> Icons.FROST.getImage();
            case "Impenetrable fog" -> Icons.FOG.getImage();
            case "Torrential Rain" -> Icons.RAIN.getImage();
            case "Decoy" -> Icons.DECOY.getImage();
//            case "Skellige Storm" -> Icons.STORM.getImage();
//            case "Clear Weather" -> Icons.CLEAR.getImage();
//            case "Scorch" -> Icons.SCORCH.getImage();
//            case "Commander’s horn" -> Icons.HORN.getImage();
            default -> null;
        };
    }

    private Image setPlaceIcon() {
        return switch (cardType) {
            case "Close Combat" -> Icons.CLOSE.getImage();
            case "Ranged" -> Icons.RANGED.getImage();
            case "Siege" -> Icons.SIEGE.getImage();
            case "Agile" -> Icons.AGILE.getImage();
            default -> null;
        };
    }

    public Image getPowerIcon() {
        return powerIcon;
    }

    public Image getPlaceIcon() {
        return placeIcon;
    }

    public Image getImage() {
        return image;
    }

    public Image getRawImage() {
        return rawImage;
    }


    public static ArrayList<Card> makeNeutralCards() {
        neutral = new ArrayList<>();

        Card bitingFrost = new Card("Biting Frost", null, "Weather", false, -1, 3, null, "/Neutral/weather_frost.jpg");
        neutral.add(bitingFrost);

        Card impenetrableFog = new Card("Impenetrable fog", null, "Weather", false, -1, 3, null, "/Neutral/weather_fog.jpg");
        neutral.add(impenetrableFog);

        Card torrentialRain = new Card("Torrential Rain", null, "Weather", false, -1, 3, null, "/Neutral/weather_rain.jpg");
        neutral.add(torrentialRain);

        Card skelligeStorm = new Card("Skellige Storm", null, "Weather", false, -1, 3, null, "/Neutral/weather_storm.jpg");
        neutral.add(skelligeStorm);

        Card clearWeather = new Card("Clear Weather", null, "Weather", false, -1, 3, null, "/Neutral/weather_clear.jpg");
        neutral.add(clearWeather);

        Card scorch = new Card("Scorch", null, "Spell", false, -1, 3, Ability.SCORCH, "/Neutral/special_scorch.jpg");
        neutral.add(scorch);

        Card commandersHorn = new Card("Commander’s horn", null, "Spell", false, -1, 3, Ability.COMMANDERS_HORN, "/Neutral/special_horn.jpg");
        neutral.add(commandersHorn);

        Card decoy = new Card("Decoy", null, "Spell", false, -1, 3, Ability.DECOY, "/Neutral/special_decoy.jpg");
        neutral.add(decoy);

        Card dandelion = new Card("Dandelion", null, "Close Combat", false, 2, 1, Ability.COMMANDERS_HORN, "/Neutral/neutral_dandelion.jpg");
        neutral.add(dandelion);

        Card cow =  new Card("Cow",null,"Ranged",false,0,1, Ability.TRANSFORMER,"/Neutral/neutral_cow.jpg");
        neutral.add(cow);

        Card emielRegis = new Card("Emiel Regis", null, "Close Combat", false, 5, 1, null, "/Neutral/neutral_emiel.jpg");
        neutral.add(emielRegis);

        Card gaunterODimm = new Card("Gaunter O’Dimm", null, "Siege", false, 2, 1, Ability.MUSTER, "/Neutral/neutral_gaunter_odimm.jpg");
        neutral.add(gaunterODimm);

        Card gaunterODImmDarkness = new Card("Gaunter O’DImm Darkness", null, "Ranged", false, 4, 3, Ability.MUSTER, "/Neutral/neutral_gaunter_odimm_darkness.jpg");
        neutral.add(gaunterODImmDarkness);

        Card geraltofRivia = new Card("Geralt of Rivia",null,"Close Combat",true,15,1,null,"/Neutral/neutral_geralt.jpg");
        neutral.add(geraltofRivia);

        Card mysteriousElf = new Card("Mysterious Elf", null, "Close Combat", true, 0, 1, Ability.SPY, "/Neutral/neutral_mysterious_elf.jpg");
        neutral.add(mysteriousElf);

        Card olgierdVonEverc = new Card("Olgierd Von Everc", null, "Agile", false, 6, 1, Ability.MORAL_BOOST, "/Neutral/neutral_olgierd.jpg");
        neutral.add(olgierdVonEverc);

        Card trissMerigold = new Card("Triss Merigold", null, "Close Combat", true, 7, 1, null, "/Neutral/neutral_triss.jpg");
        neutral.add(trissMerigold);

        Card vesemir = new Card("Vesemir", null, "Close Combat", false, 6, 1, null, "/Neutral/neutral_vesemir.jpg");
        neutral.add(vesemir);

        Card villentretenmerth = new Card("Villentretenmerth", null, "Close Combat", false, 7, 1, Ability.SCORCH, "/Neutral/neutral_villen.jpg");
        neutral.add(villentretenmerth);

        Card yenneferOfVengerberg = new Card("Yennefer of Vengerberg", null, "Ranged", true, 7, 1, Ability.MEDIC, "/Neutral/neutral_yennefer.jpg");
        neutral.add(yenneferOfVengerberg);

        Card zoltanChivay = new Card("Zoltan Chivay", null, "Close Combat", false, 5, 1, null, "/Neutral/neutral_zoltan.jpg");
        neutral.add(zoltanChivay);

        return neutral;
    }

    public String getName() {
        return name;
    }

    public boolean isHero() {
        return isHero;
    }

    public String getCardType() {
        return cardType;
    }

    public Ability getAbility() {
        return ability;
    }

    public int getCurrentNumOfCard() {
        return currentNumOfCard;
    }

    public void setCurrentNumOfCard(int currentNumOfCard) {
        this.currentNumOfCard = currentNumOfCard;
    }

    public int getNumberOfCardsInGame() {
        return numberOfCardsInGame;
    }


    public int getBasePower() {
        return basePower;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
        powerLabel.setText(String.valueOf(power));
    }

    public Tooltip getTooltip() {
        return tooltip;
    }

    public void setTooltip(Tooltip tooltip) {
        this.tooltip = tooltip;
    }

    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    // Call this method when card information changes
    public void notifyChange() {
        for (ChangeListener listener : listeners) {
            listener.changed();
        }
    }

    public interface ChangeListener {
        void changed();
    }

    public static boolean validName(String cardName) {
        for (Card card : neutral)
            if (card.getName().equals(cardName)) return true;
        return false;
    }

    public Faction getFaction() {
        return faction;
    }
}
