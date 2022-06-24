package pl.doseq.musicplayer.util;

import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Listeners {

    private Listeners() {}

    public static void volumeSliderListener(Slider slider, MediaPlayer player) {
        slider.valueProperty().addListener((observableValue, number, t1) -> {
            player.setVolume(slider.getValue() * 0.01);
            MusicPlayerController.setGlobalVolume(slider.getValue() * 0.01);
        });
    }

    public static void mediaSliderListener(Slider slider, MediaPlayer player) {
        slider.valueProperty().addListener(observable -> {
            if(slider.isValueChanging()) {
                player.seek(Duration.seconds(slider.getValue()));
            }
        });
    }

}
