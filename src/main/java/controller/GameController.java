package controller;

import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.*;
import view.animation.AirplaneMove;
import view.animation.Shoot;

import java.text.DecimalFormat;
import java.util.Objects;

public class GameController {
    private static boolean gameOverDisplayed = false;

    public static void setGameOverDisplayed(boolean b) {
        gameOverDisplayed = b;
    }

    public static void moveRight(Airplane airplane) {
        double angle = ((airplane.getAngle() % 360) + 360) % 360;
        if (angle <= 180)
            airplane.changeAngle(-2);
        else if (angle >= 180)
            airplane.changeAngle(2);
        if (airplane.getY() <= 40)
            airplane.setY(40);
        if (airplane.getX() >= 960)
            airplane.setX(40);
        if (airplane.getX() <= 40)
            airplane.setX(960);
        if (airplane.getY() >= 580)
            airplane.setY(50);
    }

    public static void moveLeft(Airplane airplane) {
        double angle = ((airplane.getAngle() % 360) + 360) % 360;
        if (angle <= 180)
            airplane.changeAngle(2);
        else if (angle >= 180)
            airplane.changeAngle(-2);
        if (airplane.getY() <= 40)
            airplane.setY(40);
        if (airplane.getX() >= 960)
            airplane.setX(40);
        if (airplane.getX() <= 40)
            airplane.setX(960);
        if (airplane.getY() >= 580)
            airplane.setY(50);
    }

    public static void moveUp(Airplane airplane) {
        double angle = ((airplane.getAngle() % 360) + 360) % 360;
        if (angle <= 270 && angle >= 90)
            airplane.changeAngle(2);
        else
            airplane.changeAngle(-2);
    }

    public static void moveDown(Airplane airplane) {
        double angle = ((airplane.getAngle() % 360) + 360) % 360;
        if (angle <= 270 && angle >= 90)
            airplane.changeAngle(-2);
        else
            airplane.changeAngle(2);
    }

    public static void shoot(Pane pane, Airplane airplane) {
        Missile missile = new Missile(airplane, "bullet");
        pane.getChildren().add(pane.getChildren().indexOf(airplane), missile);
        Shoot shoot = new Shoot(airplane, missile, null);
        Data.addAnimation(shoot);
        shoot.play();
    }

    public static void shootRadioBomb(Pane pane, Airplane airplane) {
        if (Data.getGame().getRadioBomb() > 0) {
            Data.getGame().setRadioBomb(Data.getGame().getRadioBomb() - 1);
            Missile missile = new Missile(airplane, "radioBomb");
            pane.getChildren().add(pane.getChildren().indexOf(airplane), missile);
            Shoot animation = new Shoot(airplane, missile, "radio");
            Data.addAnimation(animation);
            animation.play();
        } else
            Data.alert(Alert.AlertType.ERROR, "Radio Bomb",
                    "You don't have any radio bombs!", "");
    }

    public static void shootClusterBomb(Pane pane, Airplane airplane) {
        if (Data.getGame().getClusterBomb() > 0) {
            Data.getGame().setClusterBomb(Data.getGame().getClusterBomb() - 1);
            Missile missile = new Missile(airplane, "clusterBomb");
            pane.getChildren().add(pane.getChildren().indexOf(airplane), missile);
            Shoot animation = new Shoot(airplane, missile, "bluster");
            Data.addAnimation(animation);
            animation.play();
        } else
            Data.alert(Alert.AlertType.ERROR, "Cluster Bomb",
                    "You don't have any cluster bombs!", "");
    }

    public static void gameOver(String state, Pane root) throws Exception {
        if (gameOverDisplayed)
            return;
        gameOverDisplayed = true;
        if (!Data.getCurrentUsername().equals("guest")) saveData();

        javafx.scene.shape.Rectangle overlay = new Rectangle(root.getWidth(), root.getHeight(), Color.BLACK);
        overlay.setOpacity(0.8);
        root.getChildren().add(overlay);

        DecimalFormat df = new DecimalFormat("#.##");
        Label label;
        if (Game.getFire() != 0)
            label = new Label(state + "\nLast Wave: " + Data.getGame().getWaveNumber() +
                    "\nKills: " + Data.getGame().getKill() +
                    "\nAccuracy: " + df.format((double) Game.getFullFire() / Game.getFire()) + " %");
        else
            label = new Label(state + "\nLast Wave: " + Data.getGame().getWaveNumber() +
                    "\nKills: " + Data.getGame().getKill() + "\nAccuracy: 0.00 %");

        label.setStyle("-fx-font-family: Berlin Sans FB; -fx-font-size: 24px;-fx-padding: 10px;");
        label.setLayoutX(50);
        label.setLayoutY(50);

        Button backButton;
        if (!Data.getCurrentUsername().equals("guest")) backButton = new Button("Main Menu");
        else backButton = new Button("Register Menu");
        backButton.setLayoutX(50);
        backButton.setLayoutY(200);
//        backButton.setStyle("-fx-font-family: 'Berlin Sans FB'; -fx-font-size: 20px; -fx-text-fill: #b10000;" +
//                "-fx-border-color: #b10000; -fx-padding: 10px;-fx-cursor: HAND");
        backButton.setOnAction(event -> {
            try {
                if (!Data.getCurrentUsername().equals("guest"))
                    Data.getMainMenu().start(Data.getStage());
                else {
                    UserController.deleteUser("guest");
                    Data.getRegisterMenu().start(Data.getStage());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        root.getChildren().addAll(label, backButton);
//        scene.getStylesheets().add(String.valueOf(Register.class.getResource("/CSS/style.css")));

//        Data.getStage().setScene(scene);
//        Data.getStage().setResizable(false);
//        Data.getStage().centerOnScreen();
//        Data.getStage().show();
        Data.getGame().reset();
        if (Data.hasSound())
            Data.getGame().getMediaPlayer().stop();
    }

    public static void saveData() {
        int level;
        switch (Data.getGameLevel()) {
            case "easy":
                level = 1;
                break;
            case "average":
                level = 2;
                break;
            default:
                level = 3;
                break;
        }

        UserController.setField(Data.getCurrentUsername(), "wave",
                String.valueOf(Data.getGame().getWaveNumber()));
        UserController.setField(Data.getCurrentUsername(), "kill",
                String.valueOf(Data.getGame().getKill() + Integer.parseInt(Objects.requireNonNull
                        (UserController.getField(Data.getCurrentUsername(), "kill")))));
        UserController.setField(Data.getCurrentUsername(), "xKill",
                String.valueOf(Data.getGame().getKill() * level + Integer.parseInt(Objects.requireNonNull
                        (UserController.getField(Data.getCurrentUsername(), "xKill")))));
        UserController.setField(Data.getCurrentUsername(), "shoot",
                String.valueOf(Game.getFire() + Integer.parseInt(Objects.requireNonNull
                        (UserController.getField(Data.getCurrentUsername(), "shoot")))));
        UserController.setField(Data.getCurrentUsername(), "xShoot",
                String.valueOf(Game.getFullFire() + Integer.parseInt(Objects.requireNonNull
                        (UserController.getField(Data.getCurrentUsername(), "xShoot")))));
        UserController.setField(Data.getCurrentUsername(), "music",
                String.valueOf(Data.hasSound()));
    }


    public static void pauseGame() {
        for (Transition transition : Data.getGame().getTransitions())
            transition.stop();
        for (Tank tank : Data.getGame().getTanks())
            if (tank.getAnimation().getBullet() != null)
                tank.getAnimation().getBullet().getAnimation().stop();
        for (Timeline timeline : Data.getGame().getTimelines())
            timeline.stop();
    }

    public static void resumeGame() {
        for (Transition transition : Data.getGame().getTransitions())
            transition.play();
        for (Tank tank : Data.getGame().getTanks())
            if (tank.getAnimation().getBullet() != null)
                tank.getAnimation().getBullet().getAnimation().play();
        for (Timeline timeline : Data.getGame().getTimelines())
            timeline.play();
    }

    public static void freeze() {
        pauseGame();
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(1);
        Data.getPane().setEffect(colorAdjust);
        for (Transition transition : Data.getGame().getTransitions())
            if (transition instanceof AirplaneMove)
                transition.play();
    }

    public static void unfreeze() {
        Data.getPane().setEffect(null);
        for (Transition transition : Data.getGame().getTransitions())
            if (transition instanceof AirplaneMove)
                transition.stop();
        resumeGame();
    }
}