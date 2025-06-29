package view.animation;

import javafx.animation.Transition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.*;

import java.util.Objects;

public class Shoot extends Transition {
    private final Missile missile;
    private double vSpeed = Data.getRandom((double) -2, 1);
    private final String shootingType;
    private final Airplane airplane;

    public Shoot(Airplane airplane, Missile missile, String shootingType) {
        this.missile = missile;
        this.shootingType = shootingType;
        this.airplane = airplane;
        missile.setAnimation(this);
        this.setCycleDuration(Duration.millis(100));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double frac) {
        double y = missile.getY() + vSpeed;
        double x = missile.getX() + 2;
        vSpeed += 0.2;

        for (Tank tank : Data.getGame().getTanks()) {
            if (tank.getBoundsInParent().intersects(missile.getBoundsInParent())) {
                Data.getPane().getChildren().remove(missile);
                if (!tank.isHit()) shoot(tank, null, null, null, null);
            }
        }

        for (Truck truck : Data.getGame().getTrucks()) {
            if (truck.getBoundsInParent().intersects(missile.getBoundsInParent())) {
                Data.getPane().getChildren().remove(missile);
                if (!truck.isHit()) shoot(null, truck, null, null, null);
            }
        }

        for (Tree tree : Data.getGame().getTrees()) {
            if (tree.getBoundsInParent().intersects(missile.getBoundsInParent())) {
                Data.getPane().getChildren().remove(missile);
                if (!tree.isHit()) shoot(null, null, tree, null, null);
            }
        }

        for (Bunker bunker : Data.getGame().getBunkers()) {
            if (bunker.getBoundsInParent().intersects(missile.getBoundsInParent())) {
                Data.getPane().getChildren().remove(missile);
                if (!bunker.isHit()) shoot(null, null, null, bunker, null);
            }
        }

        for (Building building : Data.getGame().getBuildings()) {
            if (building.getBoundsInParent().intersects(missile.getBoundsInParent())) {
                Data.getPane().getChildren().remove(missile);
                if (!building.isHit()) shoot(null, null, null, null, building);
            }
        }

        missile.setY(y);
        missile.setX(x);
        if (!missile.isHit() && y >= 600) {
            missile.setHit(true);
            stop();
            Explosion animation = new Explosion(airplane, missile, null, null, null, null, null, shootingType);
            Data.addAnimation(animation);
            animation.play();
        } else if (x <= 0 || x >= 1000) {
            Data.getPane().getChildren().remove(missile);
            stop();
        }
    }

    public void shoot(Tank tank, Truck truck, Tree tree, Bunker bunker, Building building) {
        if (missile.isHit()) return;
        missile.setHit(true);

        MediaPlayer mediaPlayer = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource
                ("/Sounds/explosion.wav")).toString()));
        mediaPlayer.setAutoPlay(true);

        if (Data.getStartGame().getCounter() != 100)
            Data.getStartGame().setCounter(Data.getStartGame().getCounter() + 10);

        Explosion animation = new Explosion
                (airplane, null, tank, truck, tree, building, bunker, shootingType);
        Data.addAnimation(animation);
        animation.play();
    }
}