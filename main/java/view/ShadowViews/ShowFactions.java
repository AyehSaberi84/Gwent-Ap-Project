package view.ShadowViews;

import controller.PreGameController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.factions.*;
import view.PreGameMenu;

public class ShowFactions extends Application {
    private PreGameMenu preGameMenu;
    private final Pane pane;
    private Pane menuPane;
    private final double menuWIDTH = 800;
    private final double menuHEIGHT = 400;
    private final double WIDTH = 1200;
    private final double HEIGHT = 700;

    public ShowFactions(Pane pane,PreGameMenu preGameMenu) {
        this.pane = pane;
        this.preGameMenu = preGameMenu;
    }

    @Override
    public void start(Stage stage) throws Exception {
        makeView();
        pane.getChildren().add(menuPane);
    }

    private void makeView() {
        makeBackground();
        makePanesAndView();
    }

    private void makeBackground() {
        menuPane = new Pane();
        menuPane.setStyle("-fx-background-color: rgba(77,2,44,0.69);");
        menuPane.setPrefSize(menuWIDTH, menuHEIGHT);
        menuPane.setLayoutX((WIDTH - menuWIDTH) / 2);
        menuPane.setLayoutY((HEIGHT - menuHEIGHT) / 2);
    }

    private void makePanesAndView() {
        HBox hBox = makeHbox();
        makeVBoxForFaction(hBox);
        Button button = makeButton("Exit");
        menuPane.getChildren().addAll(hBox, button);
    }

    private HBox makeHbox() {
        HBox hBox = new HBox(10);
        hBox.setPrefSize(menuWIDTH, menuHEIGHT * 0.8 + (double) 0);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private void makeVBoxForFaction(HBox hBox) {
        VBox monster = makeVbox(new Monster(), "Monster");
        VBox nilfgaardianEmpire = makeVbox(new NilfgaardianEmpire(), "Nilfgaardian Empire");
        VBox realmNorthern = makeVbox(new RealmNorthern(), "Realm Northern");
        VBox scoiaTael = makeVbox(new ScoiaTael(), "ScoiaTael");
        VBox skellige = makeVbox(new Skellige(), "Skellige");
        hBox.getChildren().addAll(monster, nilfgaardianEmpire, realmNorthern, scoiaTael, skellige);
    }

    private VBox makeVbox(Faction faction, String factionName) {
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView();
        imageView.setImage(faction.getFlagImage());
        imageView.setOnMouseClicked(event -> preGameMenu.changeFaction(faction));
        Label label = makeLabel(factionName);
        vBox.getChildren().addAll(label, imageView);
        return vBox;
    }

    private Label makeLabel(String name) {
        Label label = new Label(name);
        label.setFont(Font.font("Bookman Old Style", 15));
        label.setTextFill(Color.WHITE);
        label.setLayoutX(100);
        label.setLayoutY(100);
        return label;
    }

    private Button makeButton(String name) {
        Button button = new Button(name);
        button.setFont(Font.font("Bookman Old Style", 20));
        button.setStyle("-fx-background-color: #7c0339; -fx-text-fill: #f3fff3");
        button.setPrefSize(100, 30);
        button.setLayoutX(menuWIDTH * 0.45);
        button.setLayoutY(menuHEIGHT * 0.7);
        button.setOnAction(event -> menuPane.setVisible(false));
        return button;
    }
}
