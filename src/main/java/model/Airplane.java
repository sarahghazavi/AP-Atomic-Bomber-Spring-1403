package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.animation.AirplaneMove;

public class Airplane extends Rectangle {
    private AirplaneMove animation;
    private double angle;

    public Airplane(double angle) {
        super(100, 50);
        this.angle = angle;
        setX(Data.getRandom(30, 970));
        setY(Data.getRandom(30, 480));
        setFill(new ImagePattern(new Image(Data.getImage("plane.png").toExternalForm())));
        setScaleX(-1);
    }

    public AirplaneMove getAnimation() {
        return animation;
    }

    public void setAnimation(AirplaneMove animation) {
        this.animation = animation;
    }

    public double getAngle() {
        return angle;
    }

    public void changeAngle(double angle) {
        this.angle += angle;
        setRotate(getRotate() + angle);
        if (((this.angle % 360) + 360) % 360 > 90 && ((this.angle % 360) + 360) % 360 < 270) setScaleY(-1);
        else setScaleY(1);
    }
}