package view;

import controller.GameController;
import controller.UserController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Data;
import model.Game;

import java.util.Objects;
import java.util.Optional;

public class Pause extends Application {
    @FXML
    Pane pane;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Pause.fxml"));
        pane = fxmlLoader.load();
        pane.setBackground(new Background(Data.getBackground()));
        Scene scene = new Scene(pane);
        stage.setTitle("Pause Menu");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setScene(scene);
        pane.requestFocus();
        stage.show();
    }

    public void resetFocus() {
        pane.requestFocus();
    }

    public void stopMusic() {
        Data.getGame().getMediaPlayer().stop();
    }

    public void saveExit() throws Exception {
        if (Data.hasSound())
            Data.getGame().getMediaPlayer().stop();
        GameController.saveData();
        Data.getMainMenu().start(Data.getStage());
    }

    public void exit() throws Exception {
        if (Data.hasSound())
            Data.getGame().getMediaPlayer().stop();

        if (Data.getCurrentUsername().equals("guest")) {
            UserController.deleteUser("guest");
            Data.getRegisterMenu().start(Data.getStage());
        } else
            Data.getMainMenu().start(Data.getStage());
    }

    public void keyGuide() {
        Data.alert(Alert.AlertType.INFORMATION, "Key Guide",
                Data.isArrow() ? "You have to use arrow keys to move the airplane!"
                        : "You have to use a/w/d/s keys to move the airplane!", "");
    }

    public void changeMusic() throws Exception {
        while (true) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Choose Music");
            dialog.setHeaderText("Enter a number (we have 3 musics):");
            dialog.setContentText("");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                int number = Integer.parseInt(result.get());

                if (number > 4 || number < 1) {
                    Data.alert(Alert.AlertType.ERROR, "Choose Music",
                            "Music number is invalid!", "Please try again.");
                    continue;
                }

                if (Data.hasSound())
                    Data.getGame().getMediaPlayer().stop();
                Data.setSound(true);
                String musicName = "/Sounds/" + number + ".mp3";
                MediaPlayer mediaPlayer = new MediaPlayer(new Media(Objects.requireNonNull
                        (getClass().getResource(musicName)).toURI().toString()));
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                Data.getGame().setMediaPlayer(mediaPlayer);
                mediaPlayer.play();
            }
            pane.requestFocus();
            break;
        }
    }

    public void back() {
        Data.getStage().setScene(Game.getScene());
        GameController.resumeGame();
        Data.getStage().show();
    }
}