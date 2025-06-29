package view.animation;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.Airplane;
import model.Bullet;
import model.Data;
import model.Tank;

public class BulletMove extends Transition {
    private final Bullet bullet;
    private final Tank tank;
    private final Airplane airplane;
    private final Pane pane;

    public BulletMove(Bullet bullet, Tank tank, Airplane airplane, Pane pane) {
        bullet.setAnimation(this);
        this.bullet = bullet;
        this.tank = tank;
        this.airplane = airplane;
        this.pane = pane;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(100));
    }

    @Override
    protected void interpolate(double frac) {
        double bulletSpeed = Data.getRandom(0.2, 0.6);

        double angle = Math.atan2(airplane.getY() - bullet.getY(), airplane.getX() - bullet.getX());

        double bulletX = bullet.getX() + bulletSpeed * Math.cos(angle);
        double bulletY = bullet.getY() + bulletSpeed * Math.sin(angle);
        bullet.setX(bulletX);
        bullet.setY(bulletY);

        Circle circle = tank.getAnimation().getCircle();
        if (!circle.getBoundsInLocal().contains(bulletX, bulletY)) {
            Data.getPane().getChildren().remove(bullet);
            stop();
        }

        if (bullet.getBoundsInParent().intersects(airplane.getBoundsInParent()))
            new FinishGame(airplane, pane).play();
    }
}