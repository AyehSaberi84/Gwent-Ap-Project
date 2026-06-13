package model.commanders.monsters;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.Monster;

public class DestroyerOfWorlds extends Commander {
    public DestroyerOfWorlds (){
        super.setName("Destroyer Of Worlds");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/Monster/monsters_eredin_gold.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/Monster/monsters_eredin_gold.jpg"))));
        super.setFaction(new Monster());
    }
    @Override
    public void doAction() {

    }
}
