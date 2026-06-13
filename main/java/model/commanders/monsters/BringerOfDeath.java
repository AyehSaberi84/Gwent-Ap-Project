package model.commanders.monsters;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.Monster;

public class BringerOfDeath extends Commander {
    public BringerOfDeath() {
        super.setName("Bringer Of Death");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/Monster/monsters_eredin_silver.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/Monster/monsters_eredin_silver.jpg"))));
        super.setFaction(new Monster());
    }

    @Override
    public void doAction() {

    }

}
