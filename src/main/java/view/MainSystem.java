package view;

import controller.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Data;

import java.nio.file.Paths;

public class MainSystem {
    @FXML
    Label welcome;
    @FXML
    ImageView avatar;
    @FXML
    Pane pane;

    public void setMenu() {
        welcome.setText("Welcome " + Data.getCurrentUsername() + "!");
        String imagePath = Paths.get("src/main/resources/Players", Data.getCurrentUsername(), "avatar.png").toString();
        Image image = new Image("file:" + imagePath);
        avatar.setImage(image);
    }

    public void resetFocus() {
        pane.requestFocus();
    }

    public void startGame() throws Exception {
        loadGameData();
        Data.getStartGame().start(Data.getStage());
    }

    private void loadGameData() {
        Data.setSound(Boolean.parseBoolean(UserController.getField(Data.getCurrentUsername(), "music")));
        Data.setColor(Boolean.parseBoolean(UserController.getField(Data.getCurrentUsername(), "color")));
        Data.setArrow(Boolean.parseBoolean(UserController.getField(Data.getCurrentUsername(), "arrow")));
        Data.setGameLevel(UserController.getField(Data.getCurrentUsername(), "level"));
    }

    public void continueGame() {
        // TODO
    }

    public void goToProfile() throws Exception {
        Data.getProfileMenu().start(Data.getStage());
    }

    public void showScoreboard() throws Exception {
        Data.getScoreMenu().start(Data.getStage());
    }

    public void goToSettings() throws Exception {
        Data.getSettingsMenu().start(Data.getStage());
    }

    public void exit() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You will be logged out!");
        alert.setContentText("");
        alert.showAndWait();
        if (alert.getResult().getButtonData().isCancelButton()) {
            resetFocus();
            return;
        }

        if (Data.getCurrentUsername().equals("guest")) UserController.deleteUser("guest");
        Data.setCurrentUsername("");
        Data.setPassword("");

        Data.getRegisterMenu().start(Data.getStage());
    }
}
