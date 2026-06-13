package model.factions;

import javafx.scene.image.Image;
import model.Ability;
import model.Card;
import model.commanders.Commander;
import model.commanders.nilfgaardian_empire.*;
import model.commanders.northern_realms.*;

import java.util.ArrayList;
import java.util.Objects;

public class RealmNorthern extends Faction {
    private static ArrayList<Card> allCards = new ArrayList<>();

    public RealmNorthern() {
        super.setName("Realm Northern");
        super.setFlagImage(new Image(Objects.requireNonNull(Faction.class.getResourceAsStream("/Images/RealmNorthern/deck_shield_realms.png"))));
        super.setCardImage(new Image(String.valueOf(Faction.class.getResource("/Images/RealmNorthern/faction_realms.jpg"))));
        setAllCards();
    }

    public static Card makeCardWithName(String cardName) {
        new RealmNorthern();
        for (Card card : allCards) {
            if (card.getName().equals(cardName)) return new Card(card);
        }
        return null;
    }

    @Override
    public Commander makeCommanderByName(String commanderName) {
        return switch (commanderName) {
            case "King Of Temeria" -> new KingOfTemeria();
            case "Lord Commander Of The North" -> new LordCommanderOfTheNorth();
            case "Son Of Medell" -> new SonOfMedell();
            case "The Siegemaster" -> new TheSiegemaster();
            case "The Steel Forged" -> new TheSteelForged();
            default -> null;
        };
    }

    private void setAllCards() {
        if (!allCards.isEmpty()) allCards = new ArrayList<>();

        Card ballista = new Card("Ballista", this, "Siege", false, 6, 2, null, "/RealmNorthern/realms_ballista.jpg");
        allCards.add(ballista);

        Card blueStripesCommando = new Card("Blue Stripes Commando", this, "Close Combat", false, 4, 3, Ability.TIGHT_BOND, "/RealmNorthern/realms_blue_stripes.jpg");
        allCards.add(blueStripesCommando);

        Card catapult = new Card("Catapult", this, "Siege", false, 8, 2, Ability.TIGHT_BOND, "/RealmNorthern/realms_catapult_1.jpg");
        allCards.add(catapult);

        Card dragonHunter = new Card("Dragon Hunter", this, "Ranged", false, 5, 2, Ability.TIGHT_BOND, "/RealmNorthern/realms_crinfrid.jpg");
        allCards.add(dragonHunter);

        Card dethmold = new Card("Dethmold", this, "Ranged", false, 6, 1, null, "/RealmNorthern/realms_dethmold.jpg");
        allCards.add(dethmold);

        Card dunBannerMedic = new Card("Dun Banner Medic", this, "Siege", false, 5, 1, Ability.MEDIC, "/RealmNorthern/realms_banner_nurse.jpg");
        allCards.add(dunBannerMedic);

        Card esteradThyssen = new Card("Esterad Thyssen", this, "Close Combat", true, 10, 1, null, "/RealmNorthern/realms_esterad.jpg");
        allCards.add(esteradThyssen);

        Card johnNatalis = new Card("John Natalis", this, "Close Combat", true, 10, 1, null, "/RealmNorthern/realms_natalis.jpg");
        allCards.add(johnNatalis);

        Card kaedweniSiegeExpert = new Card("Kaedweni Siege Expert", this, "Siege", false, 1, 3, Ability.MORAL_BOOST, "/RealmNorthern/realms_kaedwen_siege.jpg");
        allCards.add(kaedweniSiegeExpert);

        Card keiraMetz = new Card("Keira Metz", this, "Ranged", false, 5, 1, null, "/RealmNorthern/realms_keira.jpg");
        allCards.add(keiraMetz);

        Card philippaEilhart = new Card("Philippa Eilhart", this, "Ranged", true, 10, 1, null, "/RealmNorthern/realms_philippa.jpg");
        allCards.add(philippaEilhart);

        Card poorFuckingInfantry = new Card("Poor Fucking Infantry", this, "Close Combat", false, 1, 4, Ability.TIGHT_BOND, "/RealmNorthern/realms_poor_infantry.jpg");
        allCards.add(poorFuckingInfantry);

        Card princeStennis = new Card("Prince Stennis", this, "Close Combat", false, 5, 1, Ability.SPY, "/RealmNorthern/realms_stennis.jpg");
        allCards.add(princeStennis);

        Card redanianFootSoldier = new Card("Redanian Foot Soldier", this, "Close Combat", false, 1, 2, null, "/RealmNorthern/realms_redania.jpg");
        allCards.add(redanianFootSoldier);

        Card sabrinaGlevissing = new Card("Sabrina Glevissing", this, "Ranged", false, 4, 1, null, "/RealmNorthern/realms_sabrina.jpg");
        allCards.add(sabrinaGlevissing);

        Card sheldonSkaggs = new Card("Sheldon Skaggs", this, "Ranged", false, 4, 1, null, "/RealmNorthern/realms_sheldon.jpg");
        allCards.add(sheldonSkaggs);

        Card siegeTower = new Card("Siege Tower", this, "Siege", false, 6, 1, null, "/RealmNorthern/realms_siege_tower.jpg");
        allCards.add(siegeTower);

        Card siegfriedOfDenesle = new Card("Siegfried of Denesle", this, "Close Combat", false, 5, 1, null, "/RealmNorthern/realms_siegfried.jpg");
        allCards.add(siegfriedOfDenesle);

        Card sigismundDijkstra = new Card("Sigismund Dijkstra", this, "Close Combat", false, 4, 1, Ability.SPY, "/RealmNorthern/realms_dijkstra.jpg");
        allCards.add(sigismundDijkstra);

        Card sileDeTansarville = new Card("Síle de Tansarville", this, "Ranged", false, 5, 1, null, "/RealmNorthern/realms_sheala.jpg");
        allCards.add(sileDeTansarville);

        Card thaler = new Card("Thaler", this, "Siege", false, 1, 1, Ability.SPY, "/RealmNorthern/realms_thaler.jpg");
        allCards.add(thaler);

        Card trebuchet = new Card("Trebuchet", this, "Siege", false, 6, 2, null, "/RealmNorthern/realms_trebuchet.jpg");
        allCards.add(trebuchet);

        Card vernonRoche = new Card("Vernon Roche", this, "Close Combat", true, 10, 1, null, "/RealmNorthern/realms_vernon.jpg");
        allCards.add(vernonRoche);

        Card ves = new Card("Ves", this, "Close Combat", false, 5, 1, null, "/RealmNorthern/realms_ves.jpg");
        allCards.add(ves);

        Card yarpenZirgrin = new Card("Yarpen Zirgrin", this, "Close Combat", false, 2, 1, null, "/RealmNorthern/realms_yarpen.jpg");
        allCards.add(yarpenZirgrin);
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    public boolean validName(String cardName) {
        for (Card card : allCards)
            if (card.getName().equals(cardName)) return true;
        return false;
    }

}
