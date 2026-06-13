package model.factions;

import javafx.scene.image.Image;
import model.Ability;
import model.Card;
import model.commanders.Commander;
import model.commanders.scoia_tael.*;
import model.commanders.skellige.CrachAnCraite;
import model.commanders.skellige.KingBran;

import java.util.ArrayList;
import java.util.Objects;

public class Skellige extends Faction {
    private static ArrayList<Card> allCards = new ArrayList<>();

    public Skellige() {
        super.setName("Skellige");
        super.setFlagImage(new Image(Objects.requireNonNull(Faction.class.getResourceAsStream("/Images/Skellige/deck_shield_skellige.png"))));
        super.setCardImage(new Image(String.valueOf(Faction.class.getResource("/Images/Skellige/faction_skellige.jpg"))));
        setAllCards();
    }


    public static Card makeCardWithName(String cardName) {
        new Skellige();
        for (Card card : allCards) {
            if (card.getName().equals(cardName)) return new Card(card);
        }
        return null;
    }

    @Override
    public Commander makeCommanderByName(String commanderName) {
        return switch (commanderName) {
            case "Crach An Craite" -> new CrachAnCraite();
            case "King Bran" -> new KingBran();
            default -> null;
        };
    }


    private void setAllCards() {
        if (!allCards.isEmpty()) allCards = new ArrayList<>();

        Card mardroeme = new Card("Mardroeme", this, "Spell", false, -1, 3, Ability.MARDROEME,"/Skellige/special_mardroeme.jpg");
        allCards.add(mardroeme);

        Card berserker = new Card("Berserker", this, "Close Combat", false, 4, 1, Ability.BERSERKER,"/Skellige/skellige_berserker.jpg");
        allCards.add(berserker);

        Card vidkaarl = new Card("Vidkaarl", this, "Close Combat", false, 14, 0, Ability.MORAL_BOOST,"/Skellige/skellige_vildkaarl.jpg");
        allCards.add(vidkaarl);

        Card svanrige = new Card("Svanrige", this, "Close Combat", false, 4, 1, null,"/Skellige/skellige_svanrige.jpg");
        allCards.add(svanrige);

        Card udalryk = new Card("Udalryk", this, "Close Combat", false, 4, 1, null,"/Skellige/skellige_udalryk.jpg");
        allCards.add(udalryk);

        Card donarAnHindar = new Card("Donar an Hindar", this, "Close Combat", false, 4, 1, null,"/Skellige/skellige_donar.jpg");
        allCards.add(donarAnHindar);

        Card clanAnCraite = new Card("Clan An Craite", this, "Close Combat", false, 6, 3, Ability.TIGHT_BOND,"/Skellige/skellige_craite_warrior.jpg");
        allCards.add(clanAnCraite);

        Card blueboyLugos = new Card("Blueboy Lugos", this, "Close Combat", false, 6, 1, null,"/Skellige/skellige_blueboy.jpg");
        allCards.add(blueboyLugos);

        Card madmanLugos = new Card("Madman Lugos", this, "Close Combat", false, 6, 1, null,"/Skellige/skellige_madmad_lugos.jpg");
        allCards.add(madmanLugos);

        Card cerys = new Card("Cerys", this, "Close Combat", true, 10, 1, Ability.MUSTER,"/Skellige/skellige_cerys.jpg");
        allCards.add(cerys);

        Card kambi = new Card("Kambi", this, "Close Combat", true, 11, 1, Ability.TRANSFORMER,"/Skellige/skellige_kambi.jpg");
        allCards.add(kambi);

        Card birnaBran = new Card("Birna Bran", this, "Close Combat", false, 2, 1, Ability.MEDIC,"/Skellige/skellige_birna.jpg");
        allCards.add(birnaBran);

        Card clanDrummondShieldmaiden = new Card("Clan Drummond Shieldmaiden", this, "Close Combat", false, 4, 3, Ability.TIGHT_BOND,"/Skellige/skellige_shield_maiden.jpg");
        allCards.add(clanDrummondShieldmaiden);

        Card clanTordarrochArmorsmith = new Card("Clan Tordarroch Armorsmith", this, "Close Combat", false, 4, 1, null,"/Skellige/skellige_tordarroch.jpg");
        allCards.add(clanTordarrochArmorsmith);

        Card clanDimunPirate = new Card("Clan Dimun Pirate", this, "Ranged", false, 6, 1, Ability.SCORCH,"/Skellige/skellige_dimun_pirate.jpg");
        allCards.add(clanDimunPirate);

        Card clanBrokvarArcher = new Card("Clan Brokvar Archer", this, "Ranged", false, 6, 3, null,"/Skellige/skellige_brokva_archer.jpg");
        allCards.add(clanBrokvarArcher);

        Card ermion = new Card("Ermion", this, "Ranged", true, 8, 1, Ability.MARDROEME,"/Skellige/skellige_ermion.jpg");
        allCards.add(ermion);

        Card hjalmar = new Card("Hjalmar", this, "Ranged", true, 10, 1, null,"/Skellige/skellige_hjalmar.jpg");
        allCards.add(hjalmar);

        Card youngBerserker = new Card("Young Berserker", this, "Ranged", false, 2, 3, Ability.BERSERKER,"/Skellige/skellige_young_berserker.jpg");
        allCards.add(youngBerserker);

        Card youngVidkaarl = new Card("Young Vidkaarl", this, "Ranged", false, 8, 0, Ability.TIGHT_BOND,"/Skellige/skellige_young_vildkaarl.jpg");
        allCards.add(youngVidkaarl);

        Card lightLongship = new Card("Light Longship", this, "Ranged", false, 4, 3, Ability.MUSTER,"/Skellige/skellige_light_longship.jpg");
        allCards.add(lightLongship);

        Card holgerBlackhand = new Card("Holger Blackhand", this, "Siege", false, 4, 1, null,"/Skellige/skellige_holger.jpg");
        allCards.add(holgerBlackhand);

        Card warLongship = new Card("War Longship", this, "Siege", false, 6, 3, Ability.TIGHT_BOND,"/Skellige/skellige_war_longship.jpg");
        allCards.add(warLongship);

        Card draigBonDhu = new Card("Draig Bon-Dhu", this, "Siege", false, 2, 1, Ability.COMMANDERS_HORN,"/Skellige/skellige_draig.jpg");
        allCards.add(draigBonDhu);

        Card olaf = new Card("Olaf", this, "Agile", false, 12, 1, Ability.MORAL_BOOST,"/Skellige/skellige_olaf.jpg");
        allCards.add(olaf);

    }

    public boolean validName(String cardName) {
        for (Card card : allCards)
            if (card.getName().equals(cardName)) return true;
        return false;
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }
}
