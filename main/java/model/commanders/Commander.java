package model.commanders;

import javafx.scene.image.Image;
import model.factions.Faction;

public class Commander {
    public transient Image image;
    private transient Image rawImage;
    public Faction faction;
    private String name;

    public void doAction() {

    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setRawImage(Image rawImage) {
        this.rawImage = rawImage;
    }

    public Image getRawImage() {
        return rawImage;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
