package model.commanders.northern_realms;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.RealmNorthern;

public class TheSteelForged extends Commander {
    public TheSteelForged (){
        super.setName("The Steel Forged");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/RealmNorthern/realms_foltest_gold.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/RealmNorthern/realms_foltest_gold.jpg"))));
        super.setFaction(new RealmNorthern());
    }
    @Override
    public void doAction() {

    }
}
