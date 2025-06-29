package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.animation.TankMove;

public class Tank extends Rectangle {
    private TankMove animation;
    private boolean getBonusBomb = false;
    private boolean addKillScore = false;

    public Tank() {
        super(125, 125);
        setX(Data.getRandom(50, 950));
        setY(525);
        setFill(new ImagePattern(new Image(Data.getImage("tank.png").toExternalForm())));
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

    public TankMove getAnimation() {
        return animation;
    }

    public void setAnimation(TankMove animation) {
        this.animation = animation;
    }

    public void remove() {
        Data.getGame().getTanks().remove(this);
        Data.getPane().getChildren().remove(this);
        Data.getPane().getChildren().remove(animation.getCircle());
        Data.getPane().getChildren().remove(animation.getBullet());
        if (animation.getTimeline() != null) animation.getTimeline().stop();
        if (!addKillScore) {
            Data.getGame().changeKill(6);
            addKillScore = true;
        }
    }

    public void stop() {
        animation.stop();
    }

    public void setAppearance(int number) {
        setFill(new ImagePattern(new Image(Data.getImage("deadtank" + number + ".png").toExternalForm())));
    }
}