package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.animation.TruckMove;

public class Truck extends Rectangle {
    private TruckMove animation;
    private boolean getBonusBomb = false;
    private boolean addKillScore = false;

    public Truck() {
        super(50, 25);
        setX(950);
        setY(520);
        setFill(new ImagePattern(new Image(Data.getImage("truck.png").toExternalForm())));
    }

    public boolean isHit() {
        return false;
    }

    public boolean getBonusBomb() {
        return getBonusBomb;
    }

    public void setGetBonusBomb(boolean getBonusBomb) {
        this.getBonusBomb = getBonusBomb;
    }

    public TruckMove getAnimation() {
        return animation;
    }

    public void setAnimation(TruckMove animation) {
        this.animation = animation;
    }

    public void remove() {
        Data.getGame().getTrucks().remove(this);
        Data.getPane().getChildren().remove(this);
        if (!addKillScore) {
            Data.getGame().changeKill(5);
            addKillScore = true;
        }
    }

    public void stop() {
        animation.stop();
    }

    public void setAppearance(int number, String type) {
        if (type == null)
            this.setFill(new ImagePattern(new Image(Data.getImage("explosion" + number + ".png").toExternalForm())));
        else
            this.setFill(new ImagePattern(new Image(Data.getImage(type + number + ".png").toExternalForm())));
    }
}