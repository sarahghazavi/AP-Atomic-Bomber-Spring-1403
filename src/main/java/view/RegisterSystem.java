package view;

import controller.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.Data;

public class RegisterSystem {
    @FXML
    Pane pane;
    @FXML
    TextField username;
    @FXML
    PasswordField password;

    public void resetFocus() {
        pane.requestFocus();
    }

    public void signUp() {
        if (password.getText().isEmpty() || username.getText().isEmpty())
            Data.alert(Alert.AlertType.ERROR, "Sign Up",
                    "Please don't leave the fields empty!", "");
        else if (UserController.usernameExists(username.getText()))
            Data.alert(Alert.AlertType.ERROR, "Sign Up",
                    "Username already exists!", "Please try again.");
        else {
            UserController.makeUser(username.getText(), password.getText());
            Data.alert(Alert.AlertType.INFORMATION, "Sign Up", "Account created successfully!",
                    "You can login now.");
            username.setText("");
            password.setText("");
        }
    }

    public void login() throws Exception {
        if (password.getText().isEmpty() || username.getText().isEmpty())
            Data.alert(Alert.AlertType.ERROR, "Login",
                    "Please don't leave the fields empty!", "");
        else if (!UserController.usernameExists(username.getText()))
            Data.alert(Alert.AlertType.ERROR, "Login",
                    "Username doesn't exist!", "Please try again.");
        else if (!UserController.passwordMatch(username.getText(), password.getText()))
            Data.alert(Alert.AlertType.ERROR, "Login",
                    "Password is wrong!", "Please try again.");
        else {
            Data.setCurrentUsername(username.getText());
            Data.setPassword(password.getText());
            Data.alert(Alert.AlertType.INFORMATION, "Login",
                    "You've logged in successfully!", "");
            Data.getMainMenu().start(Data.getStage());
        }
    }

    public void guestEnter() throws Exception {
        Data.setCurrentUsername("guest");
        Data.setPassword("");
        UserController.makeUser("guest", "");
        Data.alert(Alert.AlertType.INFORMATION, "Login",
                "You've logged in successfully as a guest!", "");
        Data.getMainMenu().start(Data.getStage());
    }
}