package controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PastGameController {

    public static File[] getPastGames() {
        String directoryPath = "src/main/resources/Screenshots"; // Replace with your desired directory path
        String folderNameToFind = "#"; // Replace with the folder name you're looking for

        File dir = new File(directoryPath);
        File[] matchingFolders = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(folderNameToFind);
            }
        });
        for(File f : matchingFolders){
            System.out.println(f.getAbsolutePath());
        }
        return matchingFolders ;

    }
}

