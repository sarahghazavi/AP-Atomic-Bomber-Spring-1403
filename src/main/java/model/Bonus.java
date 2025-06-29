package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.animation.BonusBombMove;

public class Bonus extends Rectangle {
    private BonusBombMove animation;
    private final String type;

    public Bonus(String type, Building building, Bunker bunker) {
        super(25, 40);
        this.type = type;
        Data.getPane().getChildren().add(this);
        if (building != null) {
            setX(building.getX() + 10);
            setY(building.getY() - 10);
        } else {
            setX(bunker.getX() + 10);
            setY(bunker.getY() - 10);
        }
        this.setFill(new ImagePattern(new Image(Data.getImage(type + ".png").toExternalForm())));
    }

    public BonusBombMove getAnimation() {
        return animation;
    }

    public void setAnimation(BonusBombMove animation) {
        this.animation = animation;
    }

    public String getType() {
        return type;
    }
}