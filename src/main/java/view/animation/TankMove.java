package view.animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.*;

public class TankMove extends Transition {
    private static final double speed = HardnessLevel.getTankSpeed(Data.getGameLevel());
    private final Tank tank;
    private final boolean shoot;
    private Circle circle;
    private Bullet bullet = null;
    private Timeline timeline = null;

    public TankMove(Tank tank, Airplane airplane, boolean shoot, Pane pane) {
        this.tank = tank;
        this.shoot = shoot;

        if (shoot) {
            Circle circle = new Circle();
            circle.setCenterX(tank.getX() + 50);
            circle.setCenterY(tank.getY() + 50);
            circle.setRadius(HardnessLevel.getTankRadius(Data.getGameLevel()));
            circle.setFill(null);
            circle.setStroke(Color.DARKGRAY);
            circle.setStrokeWidth(2);
            Data.getPane().getChildren().add(circle);
            this.circle = circle;

            timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
                bullet = new Bullet(tank);
                Data.getPane().getChildren().add(bullet);
                BulletMove animation = new BulletMove(bullet, tank, airplane, pane);
                animation.play();
            }));
            Data.getGame().getTimelines().add(timeline);
            timeline.setCycleCount(-1);
            timeline.play();
        }
        setCycleDuration(Duration.millis(100));
        setCycleCount(-1);
    }

    public Circle getCircle() {
        return circle;
    }

    public Bullet getBullet() {
        return bullet;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    @Override
    protected void interpolate(double frac) {
        if (tank == null) {
            Data.getGame().getTransitions().remove(this);
            stop();
        }

        assert tank != null;
        double x = tank.getX() - speed;
        if (x <= 0) x = 975;
        tank.setX(x);
        if (shoot)
            circle.setCenterX(x + 50);
    }
}