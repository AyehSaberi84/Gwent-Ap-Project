package model.commanders.skellige;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.Skellige;

public class KingBran extends Commander {
    public KingBran (){
        super.setName("King Bran");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/Skellige/skellige_king_bran.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/Skellige/skellige_king_bran.jpg"))));
        super.setFaction(new Skellige());
    }
    @Override
    public void doAction() {

    }
}
