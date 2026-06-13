package model.commanders.scoia_tael;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.ScoiaTael;

public class QueenOfDolBlathanna extends Commander {
    public QueenOfDolBlathanna() {
        super.setName("Queen Of DolBlathanna");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/ScoiaTael/scoiatael_francesca_silver.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/ScoiaTael/scoiatael_francesca_silver.jpg"))));
        super.setFaction(new ScoiaTael());
    }

    @Override
    public void doAction() {

    }
}
