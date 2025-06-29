package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Tree extends Rectangle {
    private boolean getBonusBomb = false;
    private boolean addKillScore = false;

    public Tree() {
        super(45, 80);
        setX(Data.getRandom(50, 950));
        setY(460);
        setFill(new ImagePattern(new Image(Data.getImage("tree.png").toExternalForm())));
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
        Data.getGame().getTrees().remove(this);
        Data.getPane().getChildren().remove(this);
        if (!addKillScore) {
            Data.getGame().changeKill(0);
            addKillScore = true;
        }
    }

    public void setAppearance(int number, String shootingType) {
        if (shootingType == null)
            setFill(new ImagePattern(new Image(Data.getImage("explosion" + number + ".png").toExternalForm())));
        else
            setFill(new ImagePattern(new Image(Data.getImage(shootingType + number + ".png").toExternalForm())));
    }
}