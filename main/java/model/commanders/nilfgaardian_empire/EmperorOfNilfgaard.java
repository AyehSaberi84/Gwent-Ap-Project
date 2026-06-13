package model.commanders.nilfgaardian_empire;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.Monster;
import model.factions.NilfgaardianEmpire;

public class EmperorOfNilfgaard extends Commander {
    public EmperorOfNilfgaard (){
        super.setName("Emperor Of Nilfgaard");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/NilfgaardianEmpire/nilfgaard_emhyr_bronze.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/NilfgaardianEmpire/nilfgaard_emhyr_bronze.jpg"))));
        super.setFaction(new NilfgaardianEmpire());
    }
    @Override
    public void doAction() {

    }
}
