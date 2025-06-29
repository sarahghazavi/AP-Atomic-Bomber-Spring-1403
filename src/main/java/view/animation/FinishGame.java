package view.animation;

import controller.GameController;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import model.Airplane;
import model.Data;

import java.nio.file.Paths;

public class FinishGame extends Transition {
    private final Airplane airplane;

    public FinishGame(Airplane airplane, Pane pane) {
        this.airplane = airplane;
        setCycleCount(1);
        setCycleDuration(Duration.millis(500));

        setOnFinished(event -> {
            try {
                MediaPlayer mediaPlayer = new MediaPlayer(new Media(Paths.get("src/main/resources/Sounds/explosion.wav").toUri().toString()));
                mediaPlayer.setAutoPlay(true);
                Data.getPane().getChildren().remove(airplane);
                GameController.gameOver("Game Over!", pane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void interpolate(double frac) {
        int number = 1;
        if (0.25 < frac && frac <= 0.5) number = 2;
        else if (0.5 < frac && frac <= 0.75) number = 3;
        else if (0.75 < frac && frac <= 1) number = 4;
        airplane.setFill(new ImagePattern(new Image(Data.getImage("airplane" + number + ".png").toExternalForm())));
    }
}