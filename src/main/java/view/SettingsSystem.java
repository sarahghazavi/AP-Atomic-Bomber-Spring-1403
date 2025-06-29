package view;

import controller.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import model.Data;

public class SettingsSystem {
    @FXML
    CheckBox muteMusicBox;
    @FXML
    CheckBox noColorBox;
    @FXML
    CheckBox useArrowsBox;
    @FXML
    ComboBox<String> combo;
    @FXML
    Pane pane;

    public void resetFocus() {
        pane.requestFocus();
    }

    public void initialize() {
        muteMusicBox.setSelected(!Boolean.parseBoolean(UserController.getField
                (Data.getCurrentUsername(), "music")));
        noColorBox.setSelected(!Boolean.parseBoolean(UserController.getField
                (Data.getCurrentUsername(), "color")));
        useArrowsBox.setSelected(Boolean.parseBoolean(UserController.getField
                (Data.getCurrentUsername(), "arrow")));
        combo.setValue(UserController.getField
                (Data.getCurrentUsername(), "level"));
    }

    public void back() throws Exception {
        submitChanges();

        Data.alert(Alert.AlertType.INFORMATION, "Change Settings",
                "Your changes have been saved successfully!", "");

        Data.getMainMenu().start(Data.getStage());
    }

    private void submitChanges() {
        UserController.setField(Data.getCurrentUsername(), "music",
                String.valueOf(!muteMusicBox.isSelected()));

        UserController.setField(Data.getCurrentUsername(), "color",
                String.valueOf(!noColorBox.isSelected()));

        UserController.setField(Data.getCurrentUsername(), "arrow",
                String.valueOf(useArrowsBox.isSelected()));

        UserController.setField(Data.getCurrentUsername(), "level",
                combo.getValue());
    }
}