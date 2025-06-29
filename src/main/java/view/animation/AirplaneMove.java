package view.animation;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Airplane;

public class AirplaneMove extends Transition {
    private final Airplane airplane;
    private final Pane pane;

    public AirplaneMove(Airplane airplane, Pane pane) {
        this.airplane = airplane;
        this.pane = pane;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(200));
    }

    @Override
    protected void interpolate(double frac) {
        double y = airplane.getY() + 1.2 * Math.sin(Math.toRadians(airplane.getAngle()));
        double x = airplane.getX() + 1.2 * Math.cos(Math.toRadians(airplane.getAngle()));
        if (y <= 25) y = 25;
        else if (y >= 505) {
            FinishGame finishGame = new FinishGame(airplane, pane);
            finishGame.play();
            stop();
            return;
        }

        airplane.setY(y);
        if (x >= 985) x = 0;
        if (x < 0) x = 975;
        airplane.setX(x);
    }
}