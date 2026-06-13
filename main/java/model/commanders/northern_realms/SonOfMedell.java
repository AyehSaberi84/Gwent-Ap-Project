package model.commanders.northern_realms;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.RealmNorthern;

public class SonOfMedell extends Commander {
    public SonOfMedell (){
        super.setName("Son Of Medell");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/RealmNorthern/realms_foltest_son_of_medell.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/RealmNorthern/realms_foltest_son_of_medell.jpg"))));
        super.setFaction(new RealmNorthern());
    }
    @Override
    public void doAction() {

    }
}
