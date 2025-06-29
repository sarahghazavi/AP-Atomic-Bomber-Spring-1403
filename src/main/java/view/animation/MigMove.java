package view.animation;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.Airplane;
import model.Data;
import model.HardnessLevel;
import model.Mig;

public class MigMove extends Transition {
    private final Mig mig;
    private final Circle circle = new Circle();
    private final double speed = Data.getRandom(0.4, 1.5);
    private final Airplane airplane;
    private final Pane pane;

    public MigMove(Mig mig, Airplane airplane, Pane pane) {
        this.mig = mig;
        this.airplane = airplane;
        this.pane = pane;
        circle.setCenterX(mig.getX() + 40);
        circle.setCenterY(mig.getY() + 25);
        circle.setRadius(HardnessLevel.getMigRadius(Data.getGameLevel()));
        circle.setFill(null);
        circle.setStroke(Color.DARKBLUE);
        circle.setStrokeWidth(5);
        Data.getPane().getChildren().add(circle);
        setCycleCount(-1);
        setCycleDuration(Duration.millis(100));
    }

    public Circle getCircle() {
        return circle;
    }

    @Override
    protected void interpolate(double frac) {
        if (mig == null) {
            stop();
            return;
        }

        double x = mig.getX() + speed;

        if (x >= 975) {
            Data.getPane().getChildren().remove(mig);
            Data.getPane().getChildren().remove(circle);
            stop();
        }

        mig.setX(x);
        circle.setCenterX(x + 25);

        if (circle.getBoundsInParent().intersects(airplane.getBoundsInParent())) {
            FinishGame finishGame = new FinishGame(airplane, pane);
            finishGame.play();
        }
    }
}