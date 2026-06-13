package model.factions;

import javafx.scene.image.Image;
import model.Ability;
import model.Card;
import model.commanders.Commander;
import model.commanders.monsters.*;

import java.util.ArrayList;
import java.util.Objects;

public class Monster extends Faction {
    private static ArrayList<Card> allCards = new ArrayList<>();

    public Monster() {
        super.setName("Monster");
        super.setFlagImage(new Image(String.valueOf(Objects.requireNonNull(Faction.class.getResource("/Images/Monster/deck_shield_monsters.png")))));
        super.setCardImage(new Image(String.valueOf(Faction.class.getResource("/Images/Monster/faction_monsters.jpg"))));
        setAllCards();
    }

    public static Card makeCardWithName(String cardName) {
        new Monster();
        for (Card card : allCards) {
            if (card.getName().equals(cardName)) return new Card(card);
        }
        return null;
    }

    @Override
    public Commander makeCommanderByName(String commanderName) {
        return switch (commanderName) {
            case "Bringer Of Death" -> new BringerOfDeath();
            case "Commander Of The Red Riders" -> new CommanderOfTheRedRiders();
            case "Destroyer Of Worlds" -> new DestroyerOfWorlds();
            case "King Of The Wild Hunt" -> new KingOfTheWildHunt();
            case "The Treacherous" -> new TheTreacherous();
            default -> null;
        };
    }

    private void setAllCards() {
        if (!allCards.isEmpty()) allCards = new ArrayList<>();

        Card draug = new Card("Draug", this, "Close Combat", true, 10, 1, null, "/Monster/monsters_draug.jpg");
        allCards.add(draug);

        Card imlerith = new Card("Imlerith", this, "Close Combat", true, 10, 1, null, "/Monster/monsters_imlerith.jpg");
        allCards.add(imlerith);

        Card leshen = new Card("Leshen", this, "Close Combat", true, 10, 1, null, "/Monster/monsters_leshen.jpg");
        allCards.add(leshen);

        Card kayran = new Card("Kayran", this, "Agile", true, 8, 1, Ability.MORAL_BOOST, "/Monster/monsters_kayran.jpg");
        allCards.add(kayran);

        Card toad = new Card("Toad", this, "Ranged", false, 7, 1, Ability.SCORCH, "/Monster/monsters_toad.jpg");
        allCards.add(toad);

        Card arachasBehemoth = new Card("Arachas Behemoth", this, "Siege", false, 6, 1, Ability.MUSTER, "/Monster/monsters_arachas_behemoth.jpg");
        allCards.add(arachasBehemoth);

        Card croneBrewess = new Card("Crone: Brewess", this, "Close Combat", false, 6, 1, Ability.MUSTER, "/Monster/monsters_witch_velen.jpg");
        allCards.add(croneBrewess);

        Card croneWeavess = new Card("Crone: Weavess", this, "Close Combat", false, 6, 1,Ability.MUSTER, "/Monster/monsters_witch_velen_1.jpg");
        allCards.add(croneWeavess);

        Card croneWhispess = new Card("Crone: Whispess", this, "Close Combat", false, 6, 1, Ability.MUSTER, "/Monster/monsters_witch_velen_2.jpg");
        allCards.add(croneWhispess);

        Card earthElemental = new Card("Earth Elemental", this, "Siege", false, 6, 1, null, "/Monster/monsters_earth_elemental.jpg");
        allCards.add(earthElemental);

        Card fiend = new Card("Fiend", this, "Close Combat", false, 6, 1, null, "/Monster/monsters_fiend.jpg");
        allCards.add(fiend);

        Card fireElemental = new Card("Fire Elemental", this, "Siege", false, 6, 1, null, "/Monster/monsters_fire_elemental.jpg");
        allCards.add(fireElemental);

        Card forktail = new Card("Forktail", this, "Close Combat", false, 5, 1, null, "/Monster/monsters_forktail.jpg");
        allCards.add(forktail);

        Card frightener = new Card("Frightener", this, "Close Combat", false, 5, 1, null, "/Monster/monsters_frightener.jpg");
        allCards.add(frightener);

        Card graveHag = new Card("Grave Hag", this, "Ranged", false, 5, 1, null, "/Monster/monsters_gravehag.jpg");
        allCards.add(graveHag);

        Card griffin = new Card("Griffin", this, "Close Combat", false, 5, 1, null, "/Monster/monsters_gryffin.jpg");
        allCards.add(griffin);

        Card iceGiant = new Card("Ice Giant", this, "Siege", false, 5, 1, null, "/Monster/monster_ice_gaient.jpg");
        allCards.add(iceGiant);

        Card plagueMaiden = new Card("Plague Maiden", this, "Close Combat", false, 5, 1, null, "/Monster/monsters_mighty_maiden.jpg");
        allCards.add(plagueMaiden);

        Card vampireKatakan = new Card("Vampire: Katakan", this, "Close Combat", false, 5, 1, Ability.MUSTER, "/Monster/monsters_katakan.jpg");
        allCards.add(vampireKatakan);

        Card werewolf = new Card("Werewolf", this, "Close Combat", false, 5, 1, null, "/Monster/monsters_werewolf.jpg");
        allCards.add(werewolf);

        Card arachas = new Card("Arachas", this, "Close Combat", false, 4, 3, Ability.MUSTER, "/Monster/monsters_arachas.jpg");
        allCards.add(arachas);

        Card botchling = new Card("Botchling", this, "Close Combat", false, 4, 1, null, "/Monster/monsters_poroniec.jpg");
        allCards.add(botchling);

        Card vampireBruxa = new Card("Vampire: Bruxa", this, "Close Combat", false, 4, 1, Ability.MUSTER, "/Monster/monsters_bruxa.jpg");
        allCards.add(vampireBruxa);

        Card vampireEkimmara = new Card("Vampire: Ekimmara", this, "Close Combat", false, 4, 1, Ability.MUSTER, "/Monster/monsters_ekkima.jpg");
        allCards.add(vampireEkimmara);

        Card vampireFleder = new Card("Vampire: Fleder", this, "Close Combat", false, 4, 1, Ability.MUSTER, "/Monster/monsters_fleder.jpg");
        allCards.add(vampireFleder);

        Card vampireGarkain = new Card("Vampire: Garkain", this, "Close Combat", false, 4, 1, Ability.MUSTER, "/Monster/monsters_garkain.jpg");
        allCards.add(vampireGarkain);

        Card celaenoHarpy = new Card("Celaeno Harpy", this, "Agile", false, 2, 1, null, "/Monster/monsters_celaeno_harpy.jpg");
        allCards.add(celaenoHarpy);

        Card cockatrice = new Card("Cockatrice", this, "Ranged", false, 2, 1, null, "/Monster/monsters_cockatrice.jpg");
        allCards.add(cockatrice);

        Card endrega = new Card("Endrega", this, "Ranged", false, 2, 1, null, "/Monster/monsters_endrega.jpg");
        allCards.add(endrega);

        Card foglet = new Card("Foglet", this, "Close Combat", false, 2, 1, null, "/Monster/monsters_fogling.jpg");
        allCards.add(foglet);

        Card gargoyle = new Card("Gargoyle", this, "Ranged", false, 2, 1, null, "/Monster/monsters_gargoyle.jpg");
        allCards.add(gargoyle);

        Card harpy = new Card("Harpy", this, "Agile", false, 2, 1, null, "/Monster/monsters_harpy.jpg");
        allCards.add(harpy);

        Card nekker = new Card("Nekker", this, "Close Combat", false, 2, 3, Ability.MUSTER, "/Monster/monsters_nekker.jpg");
        allCards.add(nekker);

        Card wyvern = new Card("Wyvern", this, "Ranged", false, 2, 1, null, "/Monster/monsters_wyvern.jpg");
        allCards.add(wyvern);

        Card ghoul = new Card("Ghoul", this, "Close Combat", false, 1, 3, Ability.MUSTER, "/Monster/monsters_ghoul.jpg");
        allCards.add(ghoul);

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
