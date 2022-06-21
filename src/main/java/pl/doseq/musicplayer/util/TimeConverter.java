package pl.doseq.musicplayer.util;


import javafx.util.Duration;

public class TimeConverter {

    private TimeConverter() {}

    public static String getTimeString(Duration duration) {
        long roundedSeconds = (long) Math.floor(duration.toSeconds());
        return String.format("%02d:%02d:%02d", roundedSeconds/3600, (roundedSeconds%3600)/60, roundedSeconds%60);
    }
}
