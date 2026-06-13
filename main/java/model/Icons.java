package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum Icons {
//    public static final Image POWER_NORMAL = new Image(String.valueOf(Card.class.getResource("/Icons/power_normal.png")));
//    public static final Image POWER_HERO = new Image(String.valueOf(Card.class.getResource("/Icons/power_hero.png")));
//    public static final Image POWER_MARDROEME = new Image(String.valueOf(Card.class.getResource("/Icons/power_mardroeme.png")));
//    public static final Image POWER_FROST = new Image(String.valueOf(Card.class.getResource("/Icons/power_frost.png")));
//    public static final Image POWER_FOG = new Image(String.valueOf(Card.class.getResource("/Icons/power_fog.png")));
//    public static final Image POWER_RAIN = new Image(String.valueOf(Card.class.getResource("/Icons/power_rain.png")));
//    public static final Image POWER_DECOY = new Image(String.valueOf(Card.class.getResource("/Icons/power_decoy.png")));
    NORMAL(new Image(String.valueOf(Card.class.getResource("/Icons/power_normal.png")))),
    HERO(new Image(String.valueOf(Card.class.getResource("/Icons/power_hero.png")))),
    MARDROEME(new Image(String.valueOf(Card.class.getResource("/Icons/power_mardroeme.png")))),
    FROST(new Image(String.valueOf(Card.class.getResource("/Icons/power_frost.png")))),
    FOG(new Image(String.valueOf(Card.class.getResource("/Icons/power_fog.png")))),
    RAIN(new Image(String.valueOf(Card.class.getResource("/Icons/power_rain.png")))),
    DECOY(new Image(String.valueOf(Card.class.getResource("/Icons/power_decoy.png")))),

    CLOSE(new Image(String.valueOf(Card.class.getResource("/Icons/card_row_close.png")))),
    SIEGE(new Image(String.valueOf(Card.class.getResource("/Icons/card_row_siege.png")))),
    RANGED(new Image(String.valueOf(Card.class.getResource("/Icons/card_row_ranged.png")))),
    AGILE(new Image(String.valueOf(Card.class.getResource("/Icons/card_row_agile.png")))),

    GEM_ON(new Image(String.valueOf(Card.class.getResource("/Icons/icon_gem_on.png")))),
    GEM_OFF(new Image(String.valueOf(Card.class.getResource("/Icons/icon_gem_off.png")))),

    WIN(new Image(String.valueOf(Card.class.getResource("/Icons/end_win.png")))),
    LOSE(new Image(String.valueOf(Card.class.getResource("/Icons/end_lose.png")))),
    DRAW(new Image(String.valueOf(Card.class.getResource("/Icons/end_draw.png"))))
    ;

    private Image image;

    Icons(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

}
