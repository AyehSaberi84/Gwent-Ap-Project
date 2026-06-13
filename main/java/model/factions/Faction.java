package model.factions;

import javafx.scene.image.Image;
import model.commanders.Commander;

public class Faction {
    private String name ;
    private transient Image flagImage;
    private transient Image cardImage;
    private transient Commander commander ;
    public Image getFlagImage() {
        return flagImage;
    }
    public void setFlagImage(Image flagImage) {
        this.flagImage = flagImage;
    }

    public String getName () {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCardImage(Image cardImage) {
        this.cardImage = cardImage;
    }

    public Commander getCommander() {
        return commander;
    }

    public void setCommander(Commander commander) {
        this.commander = commander;
    }

    public static Faction getFactionByName(String name) {
        return switch (name) {
            case "Monster" -> new Monster();
            case "Nilfgaardian Empire" -> new NilfgaardianEmpire();
            case "Realm Northern" -> new RealmNorthern();
            case "ScoiaTeal" -> new ScoiaTael();
            case "Skellige" -> new Skellige();
            default -> null;
        };
    }

    public Commander makeCommanderByName(String commanderName) {
        System.out.println("not this");
        return null;
    }
}
