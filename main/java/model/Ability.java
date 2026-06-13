package model;

import javafx.scene.image.Image;

public enum Ability {

    COMMANDERS_HORN(new Image(String.valueOf(Card.class.getResource("/Icons/card_ability_horn.png"))),
            "Doubles the strength of all unit cards in that row", "CommandersHorn"),
    DECOY(new Image(String.valueOf(Card.class.getResource("/Icons/card_ability_decoy.png"))),
            "Swap with a card on the battlefield to return it to your hand", "Decoy"),
    MEDIC(new Image(String.valueOf(Card.class.getResource("/Icons/card_ability_medic.png"))),
            "Choose one card from your discard pile and play it instantly", "Medic"),
    MORAL_BOOST(new Image(String.valueOf(Card.class.getResource("/Icons/card_ability_morale.png"))),
            "Adds +1 to all units in the row", "Moral Boost"),
    MUSTER(new Image(String.valueOf(Card.class.getResource("/Icons/card_ability_muster.png"))),
            "Find any cards with the same name in your deck and play them instantly", "Muster"),
    SPY(new Image(String.valueOf(Card.class.getResource("/Icons/card_ability_spy.png"))),
            "Place on your opponent's battlefield and draw 2 cards from your deck", "Spy"),
    TIGHT_BOND(new Image(String.valueOf(Card.class.getResource("/Icons/card_ability_bond.png"))),
            "Place next to a card with the same name to double the strength of both cards", "Tight Bond"),
    SCORCH(new Image(String.valueOf(Card.class.getResource("/Icons/card_ability_scorch.png"))),
            "Destroy your enemy's strongest unit","Scorch"),
    BERSERKER(new Image(String.valueOf(Card.class.getResource("/Icons/card_ability_berserker.png"))),
            "Transforms into a bear when a Mardroeme card is on its row", "Berserker"),
    MARDROEME(new Image(String.valueOf(Card.class.getResource("/Icons/card_ability_mardroeme.png"))),
            "Triggers transformation of all Berserker cards on the same row", "Mardroeme"),
    TRANSFORMER(new Image(String.valueOf(Card.class.getResource("/Icons/card_ability_avenger.png"))),
             "At the end of the round transforms into a card with a power of 8", "Transformers");

    private final Image image;
    private final String name;
    private final String description;

    Ability(Image image, String description, String name) {
        this.image = image;
        this.name = name;
        this.description = description;
    }

    public Image getIcon() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() { return description; }

}
