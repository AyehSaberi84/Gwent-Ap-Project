package model.commanders.monsters;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.Monster;

public class TheTreacherous extends Commander {
    public TheTreacherous (){
        super.setName("The Treacherous");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/Monster/monsters_eredin_the_treacherous.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/Monster/monsters_eredin_the_treacherous.jpg"))));
        super.setFaction(new Monster());
    }
    @Override
    public void doAction() {

    }
}
