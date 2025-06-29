package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.animation.MigMove;

public class Mig extends Rectangle {
    private MigMove animation;

    public Mig() {
        super(80, 30);
        setX(20);
        setY(Data.getRandom(50, 170));
        setFill(new ImagePattern(new Image(Data.getImage("mig_grn.png").toExternalForm())));
    }

    public MigMove getAnimation() {
        return animation;
    }

    public void setAnimation(MigMove animation) {
        this.animation = animation;
    }
}