package model.commanders.nilfgaardian_empire;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.NilfgaardianEmpire;

public class HisImperialMajesty extends Commander {
    public HisImperialMajesty (){
        super.setName("His Imperial Majesty");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/NilfgaardianEmpire/nilfgaard_emhyr_copper.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/NilfgaardianEmpire/nilfgaard_emhyr_copper.jpg"))));
        super.setFaction(new NilfgaardianEmpire());
    }
    @Override
    public void doAction() {

    }
}
