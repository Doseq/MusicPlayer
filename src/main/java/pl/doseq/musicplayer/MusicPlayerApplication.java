package pl.doseq.musicplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MusicPlayerApplication extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MusicPlayerApplication.class.getResource("musicPlayer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 822.0, 394.0, Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setMaxHeight(394.0);
        stage.setMaxWidth(822.0);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}