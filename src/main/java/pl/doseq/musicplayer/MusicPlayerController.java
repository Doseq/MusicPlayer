package pl.doseq.musicplayer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.javafx.FontIcon;
import pl.doseq.musicplayer.util.FileLoader;
import pl.doseq.musicplayer.util.Listeners;
import pl.doseq.musicplayer.util.TimeConverter;

import java.io.File;
import java.net.URL;
import java.util.*;

public class MusicPlayerController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private ListView<String> trackList = new ListView<>();
    @FXML
    private Label songLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private FontIcon playButton, pauseButton, resetButton, previousButton, nextButton, muteButton, exitButton;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider mediaSlider;

    private List<File> mediaReferenceList = new ArrayList<>();
    private int songNumber = 0;
    private boolean isMuted;

    private Timer timer;
    private TimerTask task;
    private boolean running;

    private Media media;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void playMedia() {
        if(mediaPlayer == null) loadMedia(songNumber);
        beginTimer();
        mediaPlayer.play();
    }

    public void pauseMedia() {
        cancelTimer();
        mediaPlayer.pause();
    }

    public void resetMedia() {
        mediaSlider.setValue(0);
        mediaPlayer.seek(Duration.seconds(0));
        pauseMedia();
    }

    public void nextMedia() {
        if(songNumber< mediaReferenceList.size()-1){
            setMedia(++songNumber);
        } else {
            songNumber=0;
            setMedia(songNumber);
        }
    }

    public void previousMedia() {
        if(songNumber>0){
            setMedia(--songNumber);
        } else {
            songNumber= mediaReferenceList.size()-1;
            setMedia(songNumber);
        }
    }

    public void muteMedia() {
        isMuted = !isMuted;
        mediaPlayer.setMute(isMuted);
        if(isMuted) {
            muteButton.setIconCode(BootstrapIcons.VOLUME_MUTE);
        } else {
            muteButton.setIconCode(BootstrapIcons.VOLUME_UP);
        }
    }

    public void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();

                Platform.runLater(() -> {
                    timeLabel.setText(TimeConverter.getTimeString(mediaPlayer.getCurrentTime()) + " / " + TimeConverter.getTimeString(media.getDuration()));
                    if(current==end) {
                        cancelTimer();
                        nextMedia();
                    }
                });
                mediaSlider.setValue(mediaPlayer.getCurrentTime().toSeconds());
                mediaSlider.setMax(media.getDuration().toSeconds());
                if(current/end==1) {
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 50, 50);
    }

    public void cancelTimer() {
        running = false;
        timer.cancel();
    }

    public void loadFilesByDirectory() {
        List<File> loadedFiles = FileLoader.loadFilesInDirectory();
        mediaReferenceList.addAll(loadedFiles);
        trackList.getItems()
                 .addAll(loadedFiles.stream()
                                    .map(File::getName)
                                    .toList());
    }

    public void loadSingleFile() {
        File loadedFile = FileLoader.loadFile();
        mediaReferenceList.add(loadedFile);
        trackList.getItems().add(loadedFile.getName());
    }

    public void exitPlayer() {
        Platform.exit();
    }

    private void loadMedia(int number) {
        media = new Media(mediaReferenceList.get(number).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songLabel.setText(mediaReferenceList.get(number).getName());
        Listeners.mediaSliderListener(mediaSlider, mediaPlayer);
        Listeners.volumeSliderListener(volumeSlider, mediaPlayer);
    }

    private void setMedia(int number) {
        mediaPlayer.stop();
        if(running) cancelTimer();
        loadMedia(number);
        beginTimer();
        mediaPlayer.play();
    }
}