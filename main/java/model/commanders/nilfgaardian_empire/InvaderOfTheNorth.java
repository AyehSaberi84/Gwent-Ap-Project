package model.commanders.nilfgaardian_empire;

import javafx.scene.image.Image;
import model.commanders.Commander;
import model.factions.NilfgaardianEmpire;

public class InvaderOfTheNorth extends Commander {
    public InvaderOfTheNorth (){
        super.setName("Invader Of The North");
        super.setImage(new Image(String.valueOf(Commander.class.getResource("/Images/NilfgaardianEmpire/nilfgaard_emhyr_invader_of_the_north.jpg"))));
        super.setRawImage(new Image(String.valueOf(Commander.class.getResource("/rawImages/NilfgaardianEmpire/nilfgaard_emhyr_invader_of_the_north.jpg"))));
        super.setFaction(new NilfgaardianEmpire());
    }
    @Override
    public void doAction() {

    }
}
