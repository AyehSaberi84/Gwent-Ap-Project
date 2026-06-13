package model.commanders.nilfgaardian_empire;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.NilfgaardianEmpire;

public class TheWhiteFlame extends Commander {
    public TheWhiteFlame (){
        super.setName("The White Flame");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/NilfgaardianEmpire/nilfgaard_emhyr_silver.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/NilfgaardianEmpire/nilfgaard_emhyr_silver.jpg"))));
        super.setFaction(new NilfgaardianEmpire());
    }
    @Override
    public void doAction() {

    }
}
