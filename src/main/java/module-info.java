module pl.doseq.musicplayer {
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.kordamp.ikonli.bootstrapicons;

    opens pl.doseq.musicplayer to javafx.fxml;
    exports pl.doseq.musicplayer;
    exports pl.doseq.musicplayer.util;
    opens pl.doseq.musicplayer.util to javafx.fxml;
}