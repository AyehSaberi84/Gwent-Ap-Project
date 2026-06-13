package model.commanders.nilfgaardian_empire;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.NilfgaardianEmpire;

public class TheRelentless extends Commander {
    public TheRelentless (){
        super.setName("The Relentless");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/NilfgaardianEmpire/nilfgaard_emhyr_gold.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/NilfgaardianEmpire/nilfgaard_emhyr_gold.jpg"))));
        super.setFaction(new NilfgaardianEmpire());
    }
    @Override
    public void doAction() {

    }
}
