package controller;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import model.Game;
import model.GameData;
import view.LivePage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import static view.Main.stage;

public class LogSaver {

    public static void makeScreen(Game game)  {

        WritableImage image;
        try {
            image = stage.getScene().snapshot(null);
        }
        catch (Exception e){
            return ;
        }
        BufferedImage bufferedImage = convertToBufferedImage(image);
        File outputFile = null;

        Path dir = Path.of("src/main/resources/Screenshots/@" + game.toString());
        if(Files.exists(dir)){
            int number = 1 ;
            File folder = new File("src/main/resources/Screenshots/@" + game.toString());
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        if(Integer.parseInt(file.getName().replace(".png" , "")) == number){
                            number++ ;
                        }
                    }
                }
            }
            outputFile = new File("src/main/resources/Screenshots/@" + game.toString() +"/" + number + ".png"); // Specify your desired file path


        }
        else if(Files.exists(Path.of("src/main/resources/Screenshots/@"  + game.getPlayer2().getName() + game.getPlayer1().getName() + game.getDate().getTime()))){
            int number = 1 ;
            File folder = new File("src/main/resources/Screenshots/@"  + game.getPlayer2().getName() + game.getPlayer1().getName() + game.getDate().getTime());
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        if(Integer.parseInt(file.getName().replace(".png" , "")) == number){
                            number++ ;
                        }
                    }
                }
            }
            outputFile = new File("src/main/resources/Screenshots/@"  + game.getPlayer2().getName() + game.getPlayer1().getName() + game.getDate().getTime() +"/" + number + ".png");
        }
        else{



            try {

                Files.createDirectory(dir);
                int number = 1 ;
                File folder = new File("src/main/resources/Screenshots/@" + game.toString());
                File[] listOfFiles = folder.listFiles();
                if (listOfFiles != null) {
                    for (File file : listOfFiles) {
                        if (file.isFile()) {
                            if(Integer.parseInt(file.getName()) == number){
                                number++ ;
                            }
                        }
                    }
                }
                outputFile = new File("src/main/resources/Screenshots/@" + game.toString() +"/" + number + ".png");
            } catch (IOException e) {
                System.err.println("Error creating folder: " + e.getMessage());
            }
        }


        try {
            ImageIO.write(bufferedImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public static void endGame(Game game){
        Path dir = Path.of("src/main/resources/Screenshots/@" + game.toString());
        if(Files.exists(dir)){
            File originalDir = new File(dir.toString());
            File renamedDir = new File(dir.toString().replace("@" , "#"));
            originalDir.renameTo(renamedDir);
        }
        else{
            File originalDir = new File("src/main/resources/Screenshots/@" + game.getPlayer2().getName() + game.getPlayer1().getName() );
            File renamedDir = new File(("src/main/resources/Screenshots/@" + game.getPlayer2().getName() + game.getPlayer1().getName()).replace("@" , "#"));
            originalDir.renameTo(renamedDir);
        }
    }
    private static BufferedImage convertToBufferedImage(WritableImage fxImage) {
        int width = (int) Math.ceil(fxImage.getWidth());
        int height = (int) Math.ceil(fxImage.getHeight());

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int[] buffer = new int[width];

        PixelReader reader = fxImage.getPixelReader();
        WritablePixelFormat<IntBuffer> format = PixelFormat.getIntArgbInstance();

        for (int y = 0; y < height; y++) {
            reader.getPixels(0, y, width, 1, format, buffer, 0, width);
            bufferedImage.getRaster().setDataElements(0, y, width, 1, buffer);
        }

        return bufferedImage;
    }
    public static void encode(Game game){

    }
//    private ArrayList<String> commands = new ArrayList<>() ;
//    private static HashMap<String , LogSaver> onlinegames = new HashMap();
//    private HashMap<String , LogSaver> gamesNonStatic = new HashMap<>();
//    private Player player1 ;
//    private Player player2 ;
//
//    public Player getPlayer1() {
//        return player1;
//    }
//
//    public void setPlayer1(Player player1) {
//        this.player1 = player1;
//    }
//
//    public Player getPlayer2() {
//        return player2;
//    }
//
//    public void setPlayer2(Player player2) {
//        this.player2 = player2;
//    }
//
//    public ArrayList<String> getCommands() {
//        return commands;
//    }
//    public static void SaveGame(String fileName){
//        Gson gson = new Gson();
//        LogSaver game = new LogSaver(fileName);
//        game.gamesNonStatic = onlinegames ;
//        String str = gson.toJson(game) ;
//        try {
//            Files.writeString(Path.of(fileName) , str);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public static LogSaver loadGame(String fileName){
//        Gson gson = new Gson();
//        String str ;
//        try {
//            str = Files.readString(Path.of(fileName));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return gson.fromJson(str, LogSaver.class);
//    }
//    public static void setNewCommand(String command) {
//        String[] parts = command.split("#");
//        if (command.startsWith("newGame")){
//          String clientName =  parts[4] ;
//          String enemyName =  parts[2] ;
//          LogSaver logSaver = new LogSaver(clientName + enemyName);
//          onlinegames.put(clientName + enemyName , logSaver);
//    }
//        else if (command.startsWith("end")){
//            String names =  parts[parts.length-1] ;
//            onlinegames.get( names  ).getCommands().add(command);
//            SaveGame("@"+names);
//        }
//        else {
//            String names =  parts[parts.length-1] ;
//            onlinegames.get( names  ).getCommands().add(command);
//        }
//    }

}
