package model.commanders.scoia_tael;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.ScoiaTael;

public class HopeOfTheAenSeidhe extends Commander {
    public HopeOfTheAenSeidhe (){
        super.setName("Hope Of The AenSeidhe");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/ScoiaTael/scoiatael_francesca_hope_of_the_aen_seidhe.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/ScoiaTael/scoiatael_francesca_hope_of_the_aen_seidhe.jpg"))));
        super.setFaction(new ScoiaTael());
    }
    @Override
    public void doAction() {

    }
}
