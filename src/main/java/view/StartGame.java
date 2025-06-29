package view;

import controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import view.animation.AirplaneMove;
import view.animation.MigMove;
import view.animation.TankMove;
import view.animation.TruckMove;

import java.nio.file.Paths;
import java.util.Objects;

public class StartGame extends Application {
    private final double WIDTH = 1000;
    private final double HEIGHT = 630;
    private static Pane pane;
    private Airplane airplane;
    private Label dataLabel;
    private Label counterLabel;
    private int counter = 0;

    @Override
    public void start(Stage stage) throws Exception {
        Timeline wave = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            if (Data.getGame().isWaveOver()) {
                Data.getGame().setWaveNumber(Data.getGame().getWaveNumber() + 1);
                loadWave(Data.getGame().getWaveNumber());
            }
        }));
        Data.getGame().getTimelines().add(wave);
        wave.setCycleCount(-1);
        wave.play();

        GameController.setGameOverDisplayed(false);
        pane = new Pane();
        Data.setPane(pane);

        setColor();
        setSound();
        setSize();
        pane.setBackground(new Background(getBackground()));
        keyHandling();
        pane.getChildren().add(airplane);

        dataLabel = new Label(
                "kill number : " + Data.getGame().getKill() +
                        "\ncluster bombs : " + Data.getGame().getClusterBomb() +
                        "\nradio bombs : " + Data.getGame().getRadioBomb() +
                        "\nwave number : " + Data.getGame().getWaveNumber());
        dataLabel.setStyle("-fx-font-size: 17px; " +
                "-fx-text-fill:#00008B;" +
                " -fx-font-family: Berlin Sans FB; ");
        dataLabel.setLayoutX(20);
        dataLabel.setLayoutY(20);
        pane.getChildren().add(dataLabel);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000),
                actionEvent -> updateDataLabel()));
        timeline.setCycleCount(-1);
        timeline.play();

        counterLabel = new Label("Counter: 0 %");
        counterLabel.setStyle("-fx-font-size: 15px;" +
                " -fx-text-fill: #7a0909;" +
                " -fx-font-family: 'Berlin Sans FB'");
        counterLabel.setLayoutX(20);
        counterLabel.setLayoutY(150);
        pane.getChildren().add(counterLabel);
        Timeline timelineCounter = new Timeline(new KeyFrame(Duration.millis(1000),
                actionEvent -> updateCounterLabel()));
        timelineCounter.setCycleCount(-1);
        timelineCounter.play();

        Scene scene = new Scene(pane);
        Data.getStage().setScene(scene);
        Data.getStage().setScene(scene);
        Data.getStage().setResizable(false);
        loadWave(Data.getGame().getWaveNumber());
    }

    private void setColor() {
        if (!Data.hasColor()) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setSaturation(-1);
            pane.setEffect(colorAdjust);
        }
    }

    private void setSound() {
        if (Data.hasSound()) {
            MediaPlayer mediaPlayer = new MediaPlayer(new Media(Paths.get("src/main/resources/Sounds/4.mp3").toUri().toString()));
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            Data.getGame().setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
        }
    }

    private void setSize() {
        pane.setMinHeight(HEIGHT);
        pane.setMaxHeight(HEIGHT);
        pane.setPrefHeight(HEIGHT);
        pane.setMinWidth(WIDTH);
        pane.setMaxWidth(WIDTH);
        pane.setPrefWidth(WIDTH);
    }

    private BackgroundImage getBackground() {
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/Images/game.jpg"))
                .toExternalForm(), WIDTH, HEIGHT, false, false);
        return new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT
                , BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    private void updateCounterLabel() {
        counterLabel.setText("Counter: " + counter + " %");
        if (counter >= 0)
            counterLabel.setStyle("-fx-text-fill: #8e0303; -fx-font-family: " +
                    "'Berlin Sans FB'; -fx-font-size: 20");
    }

    private void updateDataLabel() {
        if (Data.getGame().getWaveNumber() > 1)
            dataLabel.setText("kill number : " + Data.getGame().getKill() +
                    "\ncluster bombs : " + Data.getGame().getClusterBomb() +
                    "\nradio bombs : " + Data.getGame().getRadioBomb() +
                    "\nwave number : " + Data.getGame().getWaveNumber() +
                    "\naccuracy : " + Game.getAccuracy());
        else
            dataLabel.setText("kill number : " + Data.getGame().getKill() +
                    "\ncluster bombs : " + Data.getGame().getClusterBomb() +
                    "\nradio bombs : " + Data.getGame().getRadioBomb() +
                    "\nwave number : " + Data.getGame().getWaveNumber());
    }

    private void showPauseMenu() throws Exception {
        Game.setScene(Data.getStage().getScene());
        Data.getPauseMenu().start(Data.getStage());
    }

    private void keyHandling() {
        airplane = new Airplane(0);
        AirplaneMove move = new AirplaneMove(airplane, pane);
        airplane.setAnimation(move);
        move.play();

        airplane.setOnKeyPressed(keyEvent -> {
            if (Data.isArrow()) {
                if (keyEvent.getCode() == KeyCode.RIGHT)
                    GameController.moveRight(airplane);
                else if (keyEvent.getCode() == KeyCode.LEFT)
                    GameController.moveLeft(airplane);
                else if (keyEvent.getCode() == KeyCode.UP)
                    GameController.moveUp(airplane);
                else if (keyEvent.getCode() == KeyCode.DOWN)
                    GameController.moveDown(airplane);
            } else {
                if (keyEvent.getCode() == KeyCode.D)
                    GameController.moveRight(airplane);
                else if (keyEvent.getCode() == KeyCode.A)
                    GameController.moveLeft(airplane);
                else if (keyEvent.getCode() == KeyCode.W)
                    GameController.moveUp(airplane);
                else if (keyEvent.getCode() == KeyCode.S)
                    GameController.moveDown(airplane);
            }

            if (keyEvent.getCode() == KeyCode.SPACE) {
                GameController.shoot(pane, airplane);
                updateFires();
            } else if (keyEvent.getCode() == KeyCode.R) {
                GameController.shootRadioBomb(pane, airplane);
                updateFires();
                if (Data.getGame().isWaveOver()) {
                    Data.getGame().setWaveNumber(Data.getGame().getWaveNumber() + 1);
                    loadWave(Data.getGame().getWaveNumber());
                }
            } else if (keyEvent.getCode() == KeyCode.C) {
                GameController.shootClusterBomb(pane, airplane);
                updateFires();
                if (Data.getGame().isWaveOver()) {
                    Data.getGame().setWaveNumber(Data.getGame().getWaveNumber() + 1);
                    loadWave(Data.getGame().getWaveNumber());
                }
            } else if (keyEvent.getCode() == KeyCode.T) {
                Tank tank = new Tank();
                TankMove animation = new TankMove(tank, airplane, false, pane);
                tank.setAnimation(animation);
                Data.getGame().getTanks().add(tank);
                pane.getChildren().add(tank);
                Data.addAnimation(animation);
                animation.play();
            } else if (keyEvent.getCode() == KeyCode.G) {
                Data.getGame().setRadioBomb(Data.getGame().getRadioBomb() + 1);
            } else if (keyEvent.getCode() == KeyCode.CONTROL) {
                Data.getGame().setClusterBomb(Data.getGame().getClusterBomb() + 1);
            } else if (keyEvent.getCode() == KeyCode.P) {
                pane.getChildren().removeAll(Data.getGame().getTanks());
                pane.getChildren().removeAll(Data.getGame().getTrucks());
                pane.getChildren().removeAll(Data.getGame().getTrees());
                pane.getChildren().removeAll(Data.getGame().getBunkers());
                pane.getChildren().removeAll(Data.getGame().getBuildings());
                Data.getGame().removeNodes();
                Data.getGame().setWaveNumber(Data.getGame().getWaveNumber() + 1);
                loadWave(Data.getGame().getWaveNumber());
            } else if (keyEvent.getCode() == KeyCode.Z) {
                GameController.pauseGame();
                try {
                    showPauseMenu();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (keyEvent.getCode() == KeyCode.TAB) {
                if (counter == 100) {
                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.seconds(1),
                                    actionEvent -> GameController.freeze()
                            ),
                            new KeyFrame(Duration.seconds(8),
                                    actionEvent -> GameController.unfreeze()
                            )
                    );
                    timeline.setCycleCount(1);
                    timeline.play();
                    counter = 0;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Freeze");
                    alert.setHeaderText("Your counter is not full yet!");
                    alert.setContentText("");
                    alert.show();
                    Timeline timeline = new Timeline(new KeyFrame
                            (Duration.seconds(3), event -> alert.close()));
                    timeline.setCycleCount(1);
                    timeline.play();
                }
            }
        });
    }

    private void updateFires() {
        Game.setFire(Game.getFire() + 1);
        Game.setWaveFire(Game.getWaveFire() + 1);
    }

    private void createTank(boolean shoot) {
        for (int i = 0; i < 3; i++) {
            Tank tank = new Tank();
            TankMove animation = new TankMove(tank, airplane, shoot, pane);
            tank.setAnimation(animation);
            Data.addAnimation(animation);
            Data.getGame().getTanks().add(tank);
            animation.play();
        }
    }

    private void createTruck() {
        for (int i = 0; i < 2; i++) {
            Truck truck = new Truck();
            TruckMove animation = new TruckMove(truck);
            truck.setAnimation(animation);
            Data.addAnimation(animation);
            Data.getGame().getTrucks().add(truck);
            animation.play();
        }
    }

    private void createTree() {
        for (int i = 0; i < 3; i++)
            Data.getGame().getTrees().add(new Tree());
    }

    private void createBunker() {
        Data.getGame().getBunkers().add(new Bunker());
    }

    private void createBuilding() {
        for (int i = 0; i < 2; i++)
            Data.getGame().getBuildings().add(new Building());
    }

    public void createMig() {
        Mig mig = new Mig();
        MigMove animation = new MigMove(mig, airplane, pane);
        mig.setAnimation(animation);
        Data.addAnimation(animation);
        Data.getGame().getMigs().add(mig);
        animation.play();
    }

    public void showWave(int number) {
        Label waveLabel = new Label("Wave " + number + "\nPrevious wave accuracy : " + Game.getAccuracy());
        waveLabel.setStyle("-fx-font-size: 50px;" +
                " -fx-font-weight: bold;" +
                " -fx-text-fill:#700f37;" +
                " -fx-text-alignment: CENTER");
        waveLabel.setLayoutX(180);
        waveLabel.setLayoutY(200);
        pane.getChildren().add(waveLabel);

        Timeline freeze = new Timeline(new KeyFrame(Duration.seconds(3)));
        freeze.setOnFinished(event -> {
            pane.getChildren().remove(waveLabel);
            Data.getStage().setOpacity(1.0);
            Data.getStage().setResizable(true);
        });

        waveLabel.setVisible(true);
        Data.getStage().setOpacity(1.0);
        Data.getStage().setResizable(false);
        freeze.play();
    }

    private void loadWave(int number) {
        updateDataLabel();
        showWave(number);

        Game.setWaveFire(0);
        Game.setWaveFullFire(0);

        createTruck();
        createTree();
        createBuilding();
        createBunker();

        pane.getChildren().addAll(Data.getGame().getTrucks());
        pane.getChildren().addAll(Data.getGame().getTrees());
        pane.getChildren().addAll(Data.getGame().getBuildings());
        pane.getChildren().addAll(Data.getGame().getBunkers());

        switch (number) {
            case 1:
                createTank(false);
                break;
            case 2:
                createTank(true);
                break;
            case 3:
                createTank(true);

                Label label = new Label("Migs are coming!");
                label.setStyle("-fx-text-fill: #8e0303; " +
                        "-fx-font-family:Berlin Sans FB;" +
                        " -fx-font-size: 30;");
                label.setLayoutX(500);
                label.setLayoutY(200);
                StackPane centerPane = new StackPane();
                centerPane.getChildren().add(label);
                StackPane.setMargin(label, new Insets(20));

                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(HardnessLevel.getMigComeTime(Data.getGameLevel()) - 2),
                                actionEvent -> pane.getChildren().add(centerPane)
                        ),
                        new KeyFrame(Duration.seconds(HardnessLevel.getMigComeTime(Data.getGameLevel())),
                                actionEvent -> {
                                    pane.getChildren().remove(centerPane);
                                    createMig();
                                    pane.getChildren().addAll(Data.getGame().getMigs());
                                }
                        )
                );
                Data.getGame().getTimelines().add(timeline);
                timeline.setCycleCount(-1);
                timeline.play();
                break;
            case 4:
                Data.getGame().setWaveNumber(3);
                try {
                    GameController.gameOver("You Won!", pane);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        pane.getChildren().addAll(Data.getGame().getTanks());
        Data.getStage().centerOnScreen();
        airplane.requestFocus();
        Data.getStage().show();
    }
}