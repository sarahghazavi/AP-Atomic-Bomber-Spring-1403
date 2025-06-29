package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Bunker extends Rectangle {
    private boolean getBonusBomb = false;
    private boolean addKillScore = false;

    public Bunker() {
        super(50, 50);
        setX(Data.getRandom(50, 950));
        setY(510);
        setFill(new ImagePattern(new Image(Data.getImage("bunker.png").toExternalForm())));
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
        Data.getGame().getBunkers().remove(this);
        Data.getPane().getChildren().remove(this);
        if (!addKillScore) {
            Data.getGame().changeKill(2);
            addKillScore = true;
        }
    }

    public void setAppearance(int number) {
        setFill(new ImagePattern(new Image(Data.getImage("explosion" + number + ".png").toExternalForm())));
    }
}