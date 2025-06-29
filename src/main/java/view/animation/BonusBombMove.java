package view.animation;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.Airplane;
import model.Bonus;
import model.Data;

public class BonusBombMove extends Transition {
    private final Airplane airplane;
    private final Bonus bonus;

    public BonusBombMove(Bonus bonus, Airplane airplane) {
        this.airplane = airplane;
        this.bonus = bonus;
        bonus.setAnimation(this);
        this.setCycleDuration(Duration.millis(200));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double frac) {
        double y = bonus.getY() - 0.7;

        if (y <= 0) {
            Data.getPane().getChildren().remove(bonus);
            stop();
        }
        if (bonus.getBoundsInParent().intersects(airplane.getBoundsInParent())) {
            if (bonus.getType().equals("clusterBomb")) Data.getGame().setClusterBomb(Data.getGame().getClusterBomb() + 1);
            else Data.getGame().setRadioBomb(Data.getGame().getRadioBomb() + 1);
            Data.getPane().getChildren().remove(bonus);
            stop();
        }
        bonus.setY(y);
    }
}