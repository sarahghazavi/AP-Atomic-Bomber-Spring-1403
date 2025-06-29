package view.animation;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.Data;
import model.Truck;

public class TruckMove extends Transition {
    private final Truck truck;

    public TruckMove(Truck truck) {
        this.truck = truck;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(100));
    }

    @Override
    protected void interpolate(double frac) {
        if (truck == null) {
            Data.getGame().getTransitions().remove(this);
            stop();
        } else if (truck.getX() + 0.5 >= 975) truck.setX(25);
        else truck.setX(truck.getX() + 0.5);
    }
}