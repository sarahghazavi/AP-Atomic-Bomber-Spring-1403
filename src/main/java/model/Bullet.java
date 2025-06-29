package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.animation.BulletMove;

public class Bullet extends Rectangle {
    private BulletMove animation;

    public Bullet(Tank tank) {
        super(40, 50);
        setX(tank.getX() + tank.getWidth() / 2 - 25);
        setY(tank.getY() + 2);
        this.setFill(new ImagePattern(new Image(Data.getImage("tankbullet" + Data.getRandom(1, 3) + ".png").toExternalForm())));
    }

    public BulletMove getAnimation() {
        return animation;
    }

    public void setAnimation(BulletMove animation) {
        this.animation = animation;
    }
}