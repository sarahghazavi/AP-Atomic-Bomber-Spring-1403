package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Game {
    private static Scene scene;
    private static int fire;
    private static int waveFire;
    private static int fullFire;
    private static int waveFullFire;
    private static double accuracy;
    private final ArrayList<Transition> transitions = new ArrayList<>();
    private final ArrayList<Tank> tanks = new ArrayList<>();
    private final ArrayList<Tree> trees = new ArrayList<>();
    private final ArrayList<Truck> trucks = new ArrayList<>();
    private final ArrayList<Bunker> bunkers = new ArrayList<>();
    private final ArrayList<Mig> migs = new ArrayList<>();
    private final ArrayList<Building> buildings = new ArrayList<>();
    private final ArrayList<Timeline> timelines = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private int kill = 0;
    private int radioBomb = 0;
    private int clusterBomb = 0;
    private int waveNumber = 1;

    public Game() {
        reset();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            if (waveFire != 0)
                accuracy = Double.parseDouble(decimalFormat.format(waveFullFire / waveFire));
            else accuracy = 0;
        }));

        timeline.setCycleCount(-1);
        timeline.play();
    }

    public boolean isWaveOver() {
        return tanks.isEmpty() && trucks.isEmpty() && bunkers.isEmpty()
                && buildings.isEmpty() && trees.isEmpty();
    }

    public void removeNodes() {
        for (Mig mig : migs) {
            Data.getPane().getChildren().remove(mig.getAnimation().getCircle());
            mig.getAnimation().stop();
        }

        for (Tank tank : tanks) {
            Data.getPane().getChildren().remove(tank.getAnimation().getCircle());
            if (tank.getAnimation().getBullet() != null) {
                Data.getPane().getChildren().remove(tank.getAnimation().getBullet());
                tank.getAnimation().getBullet().getAnimation().stop();
            }
            tank.getAnimation().stop();
        }

        trees.clear();
        trucks.clear();
        buildings.clear();
        bunkers.clear();
        tanks.clear();
        transitions.clear();
        migs.clear();

        for (Timeline timeline : timelines)
            timeline.stop();
        timelines.clear();
    }

    public void reset() {
        removeNodes();
        Data.getStartGame().setCounter(0);
        fire = 0;
        waveFire = 0;
        fullFire = 0;
        waveFullFire = 0;
        accuracy = 0;
        kill = 0;
        radioBomb = 0;
        clusterBomb = 0;
        waveNumber = 1;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public ArrayList<Tank> getTanks() {
        return tanks;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public ArrayList<Truck> getTrucks() {
        return trucks;
    }

    public ArrayList<Bunker> getBunkers() {
        return bunkers;
    }

    public ArrayList<Mig> getMigs() {
        return migs;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public ArrayList<Timeline> getTimelines() {
        return timelines;
    }

    public static Scene getScene() {
        return scene;
    }

    public static int getFire() {
        return fire;
    }

    public static int getWaveFire() {
        return waveFire;
    }

    public static int getFullFire() {
        return fullFire;
    }

    public static int getWaveFullFire() {
        return waveFullFire;
    }

    public static double getAccuracy() {
        return accuracy;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public int getKill() {
        return kill;
    }

    public int getRadioBomb() {
        return radioBomb;
    }

    public int getClusterBomb() {
        return clusterBomb;
    }

    public int getWaveNumber() {
        return waveNumber;
    }

    public static void setScene(Scene scene) {
        Game.scene = scene;
    }

    public static void setFire(int fire) {
        Game.fire = fire;
    }

    public static void setWaveFire(int waveFire) {
        Game.waveFire = waveFire;
    }

    public static void setFullFire(int fullFire) {
        Game.fullFire = fullFire;
    }

    public static void setWaveFullFire(int waveFullFire) {
        Game.waveFullFire = waveFullFire;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void changeKill(int kill) {
        this.kill += kill;
    }

    public void setRadioBomb(int radioBomb) {
        this.radioBomb = radioBomb;
    }

    public void setClusterBomb(int clusterBomb) {
        this.clusterBomb = clusterBomb;
    }

    public void setWaveNumber(int waveNumber) {
        this.waveNumber = waveNumber;
    }
}