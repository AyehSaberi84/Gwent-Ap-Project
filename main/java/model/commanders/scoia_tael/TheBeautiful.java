package model.commanders.scoia_tael;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.ScoiaTael;

public class TheBeautiful extends Commander {
    public TheBeautiful (){
        super.setName("The Beautiful");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/ScoiaTael/scoiatael_francesca_gold.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/ScoiaTael/scoiatael_francesca_gold.jpg"))));
        super.setFaction(new ScoiaTael());
    }
    @Override
    public void doAction() {

    }
}
