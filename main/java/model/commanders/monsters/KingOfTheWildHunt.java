package model.commanders.monsters;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.Monster;

public class KingOfTheWildHunt extends Commander {

    public KingOfTheWildHunt() {
        super.setName("King Of The Wild Hunt");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/Monster/monsters_eredin_bronze.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/Monster/monsters_eredin_bronze.jpg"))));
        super.setFaction(new Monster());
    }

    @Override
    public void doAction() {

    }
}
