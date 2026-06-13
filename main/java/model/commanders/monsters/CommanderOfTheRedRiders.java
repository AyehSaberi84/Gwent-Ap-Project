package model.commanders.monsters;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.Monster;

public class CommanderOfTheRedRiders extends Commander {
   public CommanderOfTheRedRiders (){
       super.setName("Commander Of The Red Riders");
       super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/Monster/monsters_eredin_copper.jpg"))));
       super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/Monster/monsters_eredin_copper.jpg"))));
       super.setFaction(new Monster());
   }

    @Override
    public void doAction() {

    }
}
