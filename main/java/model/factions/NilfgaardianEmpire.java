package model.factions;

import javafx.scene.image.Image;
import model.Ability;
import model.Card;
import model.commanders.Commander;
import model.commanders.monsters.*;
import model.commanders.nilfgaardian_empire.*;

import java.util.ArrayList;
import java.util.Objects;

public class NilfgaardianEmpire extends Faction {
    private static ArrayList<Card> allCards = new ArrayList<>();

    public NilfgaardianEmpire() {
        super.setName("Nilfgaardian Empire");
        super.setFlagImage(new Image(Objects.requireNonNull(Faction.class.getResourceAsStream("/Images/NilfgaardianEmpire/deck_shield_nilfgaard.png"))));
        super.setCardImage(new Image(String.valueOf(Faction.class.getResource("/Images/NilfgaardianEmpire/faction_nilfgaard.jpg"))));
        setAllCards();
    }

    public static Card makeCardWithName(String cardName) {
        new NilfgaardianEmpire();
        for (Card card : allCards) {
            if (card.getName().equals(cardName)) return new Card(card);
        }
        return null;
    }

    @Override
    public Commander makeCommanderByName(String commanderName) {
        return switch (commanderName) {
            case "Emperor Of Nilfgaard" -> new EmperorOfNilfgaard();
            case "His Imperial Majesty" -> new HisImperialMajesty();
            case "Invader Of The North" -> new InvaderOfTheNorth();
            case "The Relentless" -> new TheRelentless();
            case "The White Flame" -> new TheWhiteFlame();
            default -> null;
        };
    }

    private void setAllCards() {
        if (!allCards.isEmpty()) allCards = new ArrayList<>();

        Card imperaBrigadeGuard = new Card("Impera Brigade Guard", this, "Close Combat", false, 3, 4, Ability.TIGHT_BOND, "/NilfgaardianEmpire/nilfgaard_imperal_brigade.jpg");
        allCards.add(imperaBrigadeGuard);

        Card stefanSkellen = new Card("Stefan Skellen", this, "Close Combat", false, 9, 1, Ability.SPY, "/NilfgaardianEmpire/nilfgaard_stefan.jpg");
        allCards.add(stefanSkellen);

        Card shilardFitz_Oesterlen = new Card("Shilard Fitz-Oesterlen", this, "Close Combat", false, 7, 1, Ability.SPY, "/NilfgaardianEmpire/nilfgaard_shilard.jpg");
        allCards.add(shilardFitz_Oesterlen);

        Card youngEmissary = new Card("Young Emissary", this, "Close Combat", false, 5, 2, Ability.TIGHT_BOND, "/NilfgaardianEmpire/nilfgaard_young_emissary.jpg");
        allCards.add(youngEmissary);

        Card cahirMawrDyffrynaepCeallach = new Card("Cahir Mawr Dyffryn aep Ceallach", this, "Close Combat", false, 6, 1, null, "/NilfgaardianEmpire/nilfgaard_cahir.jpg");
        allCards.add(cahirMawrDyffrynaepCeallach);

        Card vattierdeRideaux = new Card("Vattier de Rideaux", this, "Close Combat", false, 4, 1, Ability.SPY, "/NilfgaardianEmpire/nilfgaard_vattier.jpg");
        allCards.add(vattierdeRideaux);

        Card mennoCoehorn = new Card("Menno Coehorn", this, "Close Combat", true, 10, 1, Ability.MEDIC, "/NilfgaardianEmpire/nilfgaard_menno.jpg");
        allCards.add(mennoCoehorn);

        Card puttkammer = new Card("Puttkammer", this, "Ranged", false, 3, 1, null, "/NilfgaardianEmpire/nilfgaard_puttkammer.jpg");
        allCards.add(puttkammer);

        Card assirevarAnahid = new Card("Assire var Anahid", this, "Ranged", false, 6, 1, null, "/NilfgaardianEmpire/nilfgaard_assire.jpg");
        allCards.add(assirevarAnahid);

        Card blackInfantryArcher = new Card("Black Infantry Archer", this, "Ranged", false, 10, 2, null, "/NilfgaardianEmpire/nilfgaard_black_archer.jpg");
        allCards.add(blackInfantryArcher);

        Card tiborEggebracht = new Card("Tibor Eggebracht", this, "Ranged", true, 10, 1, null, "/NilfgaardianEmpire/nilfgaard_tibor.jpg");
        allCards.add(tiborEggebracht);

        Card renualdaepMatsen = new Card("Renuald aep Matsen", this, "Ranged", false, 5, 1, null, "/NilfgaardianEmpire/nilfgaard_renuald.jpg");
        allCards.add(renualdaepMatsen);

        Card fringillaVigo = new Card("Fringilla Vigo", this, "Ranged", false, 6, 1, null, "/NilfgaardianEmpire/nilfgaard_fringilla.jpg");
        allCards.add(fringillaVigo);

        Card rottenMangonel = new Card("Rotten Mangonel", this, "Siege", false, 3, 1, null, "/NilfgaardianEmpire/nilfgaard_rotten.jpg");
        allCards.add(rottenMangonel);

        Card heavyZerrikanianFireScorpion = new Card("Heavy Zerrikanian Fire Scorpion", this, "Siege", false, 10, 1, null, "/NilfgaardianEmpire/nilfgaard_heavy_zerri.jpg");
        allCards.add(heavyZerrikanianFireScorpion);

        Card zerrikanianFireScorpion = new Card("Zerrikanian Fire Scorpion", this, "Siege", false, 5, 1, null, "/NilfgaardianEmpire/nilfgaard_zerri.jpg");
        allCards.add(zerrikanianFireScorpion);

        Card siegeEngineer = new Card("Siege Engineer", this, "Siege", false, 6, 1, null, "/NilfgaardianEmpire/nilfgaard_siege_engineer.jpg");
        allCards.add(siegeEngineer);

        Card morvranVoorhis = new Card("Morvran Voorhis", this, "Siege", true, 10, 1, null, "/NilfgaardianEmpire/nilfgaard_moorvran.jpg");
        allCards.add(morvranVoorhis);

        Card albrich = new Card("Albrich", this, "Ranged", false, 2, 1, null, "/NilfgaardianEmpire/nilfgaard_albrich.jpg");
        allCards.add(albrich);

        Card cynthia = new Card("Cynthia", this, "Ranged", false, 4, 1, null, "/NilfgaardianEmpire/nilfgaard_cynthia.jpg");
        allCards.add(cynthia);

        Card etolianAuxiliaryArchers = new Card("Etolian Auxiliary Archers ", this, "Ranged", false, 1, 2, Ability.MEDIC, "/NilfgaardianEmpire/nilfgaard_archer_support.jpg");
        allCards.add(etolianAuxiliaryArchers);

        Card lethoOfGulet = new Card("Letho of Gulet", this, "Close Combat", true, 10, 1, null, "/NilfgaardianEmpire/nilfgaard_letho.jpg");
        allCards.add(lethoOfGulet);

        Card morteisen = new Card("Morteisen", this, "Close Combat", false, 3, 1, null, "/NilfgaardianEmpire/nilfgaard_morteisen.jpg");
        allCards.add(morteisen);

        Card nausicaaCavalryRider = new Card("Nausicaa Cavalry Rider", this, "Close Combat", false, 2, 3, Ability.TIGHT_BOND, "/NilfgaardianEmpire/nilfgaard_nauzicaa_2.jpg");
        allCards.add(nausicaaCavalryRider);

        Card rainfarn = new Card("Rainfarn", this, "Close Combat", false, 4, 1, null, "/NilfgaardianEmpire/nilfgaard_rainfarn.jpg");
        allCards.add(rainfarn);

        Card siegeTechnician = new Card("Siege Technician", this, "Siege", false, 0, 1, Ability.MEDIC, "/NilfgaardianEmpire/nilfgaard_siege_support.jpg");
        allCards.add(siegeTechnician);

        Card sweers = new Card("Sweers", this, "Ranged", false, 2, 1, null, "/NilfgaardianEmpire/nilfgaard_sweers.jpg");
        allCards.add(sweers);

        Card vanhemar = new Card("Vanhemar", this, "Ranged", false, 4, 1, null, "/NilfgaardianEmpire/nilfgaard_vanhemar.jpg");
        allCards.add(vanhemar);

        Card vreemde = new Card("Vreemde", this, "Close Combat", false, 2, 0, null, "/NilfgaardianEmpire/nilfgaard_vreemde.jpg");
        allCards.add(vreemde);


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
