package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Building extends Rectangle {
    private boolean getBonusBomb = false;
    private boolean addKillScore = false;

    public Building() {
        super(50, 40);
        setX(Data.getRandom(50, 950));
        setY(500);
        setFill(new ImagePattern(new Image(Data.getImage("building.png").toExternalForm())));
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

    public void remove() {
        Data.getGame().getBuildings().remove(this);
        Data.getPane().getChildren().remove(this);
        if (!addKillScore) {
            Data.getGame().changeKill(1);
            addKillScore = true;
        }
    }

    public void setAppearance(int number, String type) {
        if (type == null)
            setFill(new ImagePattern(new Image(Data.getImage("explosion" + number + ".png").toExternalForm())));
        else
            setFill(new ImagePattern(new Image(Data.getImage(type + number + ".png").toExternalForm())));
    }
}
