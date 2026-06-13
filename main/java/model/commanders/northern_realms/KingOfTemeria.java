package model.commanders.northern_realms;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.RealmNorthern;

public class KingOfTemeria extends Commander {
    public KingOfTemeria (){
        super.setName("King Of Temeria");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/RealmNorthern/realms_foltest_copper.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/RealmNorthern/realms_foltest_copper.jpg"))));
        super.setFaction(new RealmNorthern());
    }
    @Override
    public void doAction() {

    }
}
