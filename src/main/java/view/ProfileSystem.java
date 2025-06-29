package view;

import controller.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import model.Data;

import java.util.Optional;

public class ProfileSystem {
    @FXML
    Pane pane;

    public void resetFocus() {
        pane.requestFocus();
    }

    public void changeUsername() {
        while (true) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Change Username");
            dialog.setHeaderText("Enter your new username:");
            dialog.setContentText("");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String newName = result.get();
                String oldName = Data.getCurrentUsername();
                if (oldName.equals(newName)) {
                    Data.alert(Alert.AlertType.ERROR, "Change Username",
                            "Your username should be different from the old one!",
                            "Please try again.");
                    continue;
                } else if (UserController.usernameExists(newName)) {
                    Data.alert(Alert.AlertType.ERROR, "Change Username",
                            "Username already exists!",
                            "Please try again.");
                    continue;
                } else {
                    UserController.setUsername(Data.getCurrentUsername(), newName);
                    Data.setCurrentUsername(newName);
                    Data.alert(Alert.AlertType.INFORMATION, "Change Username",
                            "Username has changed successfully!", "");
                }
            }
            pane.requestFocus();
            break;
        }
    }

    public void changePassword() {
        while (true) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Change Password");
            dialog.setHeaderText("Enter your new password:");
            dialog.setContentText("");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String oldP = Data.getPassword();
                String newP = result.get();

                if (oldP.equals(newP)) {
                    Data.alert(Alert.AlertType.ERROR, "Change Password",
                            "Your password should be different from the old one!",
                            "Please try again.");
                    continue;
                } else {
                    UserController.setField(Data.getCurrentUsername(), "password", newP);
                    Data.setPassword(newP);
                    Data.alert(Alert.AlertType.INFORMATION, "Change Password",
                            "Password has changed successfully!", "");
                }
            }
            pane.requestFocus();
            break;
        }
    }

    public void deleteAccount() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Account");
        alert.setHeaderText("Your account will be permanently deleted!");
        alert.setContentText("");
        alert.showAndWait();

        if (alert.getResult().getButtonData().isCancelButton()) return;
        UserController.deleteUser(Data.getCurrentUsername());
        Data.setCurrentUsername("");
        Data.setPassword("");

        try {
            Data.getRegisterMenu().start(Data.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void back() throws Exception {
        Data.getMainMenu().start(Data.getStage());
    }

    public void logout() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You will be logged out!");
        alert.setContentText("");
        alert.showAndWait();
        if (alert.getResult().getButtonData().isCancelButton()) return;
        if (Data.getCurrentUsername().equalsIgnoreCase("guest"))
            UserController.deleteUser("guest");
        Data.setCurrentUsername("");
        Data.setPassword("");
        Data.getRegisterMenu().start(Data.getStage());
    }

    public void goToAvatarMenu() throws Exception {
        Data.getAvatarMenu().start(Data.getStage());
    }
}