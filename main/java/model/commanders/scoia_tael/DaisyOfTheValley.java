package model.commanders.scoia_tael;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.commanders.northern_realms.TheSteelForged;
import model.factions.RealmNorthern;
import model.factions.ScoiaTael;

public class DaisyOfTheValley extends Commander {
    public DaisyOfTheValley (){
        super.setName("Daisy Of The Valley");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/ScoiaTael/scoiatael_francesca_copper.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/ScoiaTael/scoiatael_francesca_copper.jpg"))));
        super.setFaction(new ScoiaTael());
    }
    @Override
    public void doAction() {

    }
}
