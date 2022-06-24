package pl.doseq.musicplayer.util;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class FileLoader {

    private FileLoader() {}

    public static List<File> loadFilesInDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose folder containing MP3 files");
        return loadFilesFromDirectory(directoryChooser.showDialog(new Stage()));
    }

    public static File loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose MP3 files");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3","*.mp3"));
        return fileChooser.showOpenDialog(new Stage());
    }

    private static List<File> loadFilesFromDirectory(File directory) {
        List<File> files = new ArrayList<>(Arrays.asList(directory.listFiles())); //Must be packaged new ArrayList beacuse of unmodifiable wrapper
        if(!files.isEmpty()) {
            Iterator<File> iterator = files.iterator();
            while(iterator.hasNext()) {
                File file = iterator.next();
                int i = file.getName().lastIndexOf(".");
                if (!file.getName().substring(i + 1).equals("mp3")) iterator.remove();
            }
        }
        return files;
    }


}
