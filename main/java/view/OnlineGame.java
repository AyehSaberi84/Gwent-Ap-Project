package view;

import controller.ConnectToServer;
import controller.ProfileController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Game;

import java.io.File;
import java.net.URL;

import static view.Main.stage;

public class OnlineGame extends Application{
    private static Pane pane ;
    private String folder ;
    private int numOfpics ;
    public OnlineGame(String folder){
        this.folder = folder ;
        File directory = new File(folder);
        this.numOfpics = directory.list().length;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        ImageView imageView = new ImageView();
        Pane root = new Pane(imageView);
        this.pane = root ;
        Scene scene = new Scene(root);

        // Set preserveRatio to true
        imageView.setPreserveRatio(true);

        // Bind fitWidth and fitHeight to scene dimensions
        imageView.fitWidthProperty().bind(scene.widthProperty());
        imageView.fitHeightProperty().bind(scene.heightProperty());

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event -> showNextImage(imageView))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        setSize(stage);
        stage.setTitle("Image Slideshow");
        stage.setScene(scene);
        makeExitButton(root);
        setSize(stage);
        stage.show();
    }

    private void makeExitButton(Pane pane){
        Button exit = makeButton("Exit",35,900);
        exit.setOnAction(event -> ProfileController.goToMainMenu());
        pane.getChildren().add(exit);
    }

    private void showNextImage(ImageView imageView) {
        File directory = new File(folder);
        this.numOfpics = directory.list().length;
        URL url = getClass().getResource("/Screenshots/parizahra1720661920287/1.png");
        Image image = new Image(folder + "/" + numOfpics + ".png");
        imageView.setImage(image);
    }



    private Button makeButton(String name, double SPACENum, double setX) {
        Button button = new Button(name);
        button.setFont(Font.font("Times New Roman", 20));
        button.setStyle("-fx-background-color: #c9cec9; -fx-text-fill: #2d1902");
        button.setPrefSize(150, 30);
        button.setLayoutX(200 + setX);
        button.setLayoutY(20 * SPACENum);
        return button;
    }

    public void setSize(Stage stage) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
    }

}


