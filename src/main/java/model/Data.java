package model;

import javafx.animation.Transition;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import view.*;

import java.net.URL;
import java.util.Objects;
import java.util.Random;

public class Data {
    private static Pane pane;
    private static boolean sound;
    private static boolean arrow;
    private static boolean color;
    private static String gameLevel = "easy";
    private static final Random random = new Random();
    private static final Register registerMenu = new Register();
    private static final Main mainMenu = new Main();
    private static final Profile profileMenu = new Profile();
    private static final Score scoreMenu = new Score();
    private static final Settings settingsMenu = new Settings();
    private static final Avatar avatarMenu = new Avatar();
    private static final StartGame startGame = new StartGame();
    private static final Game game = new Game();
    private static final Pause pauseMenu = new Pause();
    private static Stage stage;
    private static String currentUsername;
    private static String password;

    public static Stage getStage() {
        return stage;
    }

    public static int getRandom(int min, int max) {
        return (int) (min + (max - min) * random.nextDouble());
    }

    public static double getRandom(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static String getPassword() {
        return password;
    }

    public static Main getMainMenu() {
        return mainMenu;
    }

    public static Register getRegisterMenu() {
        return registerMenu;
    }

    public static Profile getProfileMenu() {
        return profileMenu;
    }

    public static Score getScoreMenu() {
        return scoreMenu;
    }

    public static Settings getSettingsMenu() {
        return settingsMenu;
    }

    public static Avatar getAvatarMenu() {
        return avatarMenu;
    }

    public static Pane getPane() {
        return pane;
    }

    public static boolean hasSound() {
        return sound;
    }

    public static boolean isArrow() {
        return arrow;
    }

    public static boolean hasColor() {
        return color;
    }

    public static String getGameLevel() {
        return gameLevel;
    }

    public static Game getGame() {
        return game;
    }

    public static StartGame getStartGame() {
        return startGame;
    }

    public static Pause getPauseMenu() {
        return pauseMenu;
    }

    public static void setStage(Stage stage) {
        Data.stage = stage;
    }

    public static void setCurrentUsername(String currentUsername) {
        Data.currentUsername = currentUsername;
    }

    public static void setPassword(String password) {
        Data.password = password;
    }

    public static void setSound(boolean sound) {
        Data.sound = sound;
    }

    public static void setArrow(boolean arrow) {
        Data.arrow = arrow;
    }

    public static void setColor(boolean color) {
        Data.color = color;
    }

    public static void setGameLevel(String gameLevel) {
        Data.gameLevel = gameLevel;
        System.out.println(gameLevel);
    }

    public static void setPane(Pane pane) {
        Data.pane = pane;
    }

    public static void addAnimation(Transition transition) {
        game.getTransitions().add(transition);
    }

    public static BackgroundImage getBackground() {
        Image image = new Image(Objects.requireNonNull(Data.class.getResource("/Images/back2.jpg"))
                .toExternalForm(), 1200, 900, false, false);
        return new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT
                , BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    }

    public static URL getImage(String name) {
        return Data.class.getResource("/Images/" + name);
    }

    public static void alert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}