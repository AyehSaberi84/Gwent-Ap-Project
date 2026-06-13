package model.commanders.northern_realms;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.RealmNorthern;

public class LordCommanderOfTheNorth extends Commander {
    public LordCommanderOfTheNorth (){
        super.setName("Lord Commander Of The North");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/RealmNorthern/realms_foltest_bronze.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/RealmNorthern/realms_foltest_bronze.jpg"))));
        super.setFaction(new RealmNorthern());
    }
    @Override
    public void doAction() {

    }
}
