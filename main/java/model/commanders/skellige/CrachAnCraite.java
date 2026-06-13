package model.commanders.skellige;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.ScoiaTael;
import model.factions.Skellige;

public class CrachAnCraite extends Commander {
    public CrachAnCraite (){
        super.setName("Crach An Craite");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/Skellige/skellige_crach_an_craite.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/Skellige/skellige_crach_an_craite.jpg"))));
        super.setFaction(new Skellige());
    }
    @Override
    public void doAction() {

    }
}
