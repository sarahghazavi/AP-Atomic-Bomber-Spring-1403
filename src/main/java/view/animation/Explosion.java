package view.animation;

import javafx.animation.Transition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.*;

import java.util.Objects;

public class Explosion extends Transition {
    private final String shootingType;
    private final Missile missile;
    private final Tank tank;
    private final Truck truck;
    private final Tree tree;
    private final Building building;
    private final Bunker bunker;

    public Explosion(Airplane airplane, Missile missile, Tank tank, Truck truck,
                     Tree tree, Building building, Bunker bunker, String shootingType) {
        this.missile = missile;
        this.tank = tank;
        this.truck = truck;
        this.tree = tree;
        this.building = building;
        this.bunker = bunker;
        this.shootingType = shootingType;

        if (Data.hasSound()) {
            MediaPlayer mediaPlayer = new MediaPlayer(new Media(Objects.requireNonNull
                    (getClass().getResource("/Sounds/explosion.wav")).toString()));
            mediaPlayer.setAutoPlay(true);
        }

        setCycleCount(1);

        if (missile != null) setCycleDuration(Duration.millis(300));
        else setCycleDuration(Duration.millis(1000));

        setOnFinished(event -> {
            if (building != null) {
                if (!building.getBonusBomb()) {
                    building.setGetBonusBomb(true);
                    Game.setFullFire(Game.getFullFire() + 1);
                    Game.setWaveFullFire(Game.getWaveFullFire() + 1);
                    BonusBombMove bonusBombMove = new BonusBombMove(new Bonus("radioBomb",
                            building, null), airplane);
                    bonusBombMove.play();
                }
                building.remove();
            } else if (bunker != null) {
                if (!bunker.getBonusBomb()) {
                    bunker.setGetBonusBomb(true);
                    BonusBombMove bonusBombMove = new BonusBombMove(new Bonus("clusterBomb",
                            null, bunker), airplane);
                    bonusBombMove.play();
                }
                bunker.remove();
            } else if (tree != null) {
                if (!tree.getBonusBomb()) {
                    tree.setGetBonusBomb(true);
                    Game.setFullFire(Game.getFullFire() + 1);
                    Game.setWaveFullFire(Game.getWaveFullFire() + 1);
                }
                tree.remove();
            } else if (tank != null) {
                if (!tank.getBonusBomb()) {
                    tank.setGetBonusBomb(true);
                    Game.setFullFire(Game.getFullFire() + 1);
                    Game.setWaveFullFire(Game.getWaveFullFire() + 1);
                }
                tank.stop();
                tank.remove();
            } else if (truck != null) {
                if (!truck.getBonusBomb()) {
                    truck.setGetBonusBomb(true);
                    Game.setFullFire(Game.getFullFire() + 1);
                    Game.setWaveFullFire(Game.getWaveFullFire() + 1);
                }
                truck.stop();
                truck.remove();
            } else if (missile != null) {
                stop();
                Data.getPane().getChildren().remove(missile);
            }
        });
    }

    @Override
    protected void interpolate(double frac) {
        if (missile != null) missile.stop();

        int number = 1;
        if (0.25 < frac && frac <= 0.5) number = 2;
        else if (0.5 < frac && frac <= 0.75) number = 3;
        else if (0.75 < frac && frac <= 1) number = 4;

        if (tank != null) tank.setAppearance(number);
        if (truck != null) truck.setAppearance(number, shootingType);
        if (tree != null) tree.setAppearance(number, shootingType);
        if (building != null) building.setAppearance(number, shootingType);
        if (bunker != null) bunker.setAppearance(number);
        if (missile != null) missile.setAppearance(number);
    }
}