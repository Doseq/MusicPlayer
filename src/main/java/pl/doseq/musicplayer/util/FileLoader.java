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
        return loadFilesFromDirectory(directoryChooser.showDialog(new Stage())).orElse(Collections.emptyList());
    }

    public static File loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose MP3 files");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3","*.mp3"));
        return fileChooser.showOpenDialog(new Stage());
    }

    private static Optional<List<File>> loadFilesFromDirectory(File directory) {
        List<File> files = new LinkedList<>(Arrays.asList(directory.listFiles()));
        for (File loadedFile : files) {
            int i = loadedFile.getName().lastIndexOf(".");
            if(!loadedFile.getName().substring(i+1).equals("mp3")) files.remove(loadedFile);
        }
        return Optional.of(files);
    }


}
