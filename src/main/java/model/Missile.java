package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.animation.Shoot;

public class Missile extends Rectangle {
    private boolean isHit = false;
    private Shoot animation;

    public Missile(Airplane airplane, String type) {
        super(20, 20);
        setX(airplane.getX() + airplane.getWidth() / 2 - 10);
        setY(airplane.getY() + 8);
        setFill(new ImagePattern(new Image(Data.getImage(type + ".png").toExternalForm())));
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public Shoot getAnimation() {
        return animation;
    }

    public void setAnimation(Shoot animation) {
        this.animation = animation;
    }

    public void remove() {
        Data.getPane().getChildren().remove(this);
    }

    public void stop() {
        animation.stop();
    }

    public void setAppearance(int number) {
        setFill(new ImagePattern(new Image(Data.getImage("explosion" + number + ".png").toExternalForm())));
    }
}