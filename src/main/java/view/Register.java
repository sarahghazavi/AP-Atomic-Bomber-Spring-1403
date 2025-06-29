package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Data;

import java.util.Objects;

public class Register extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(Objects.requireNonNull
                (Register.class.getResource("/FXML/Register.fxml")));
        pane.setBackground(new Background(registerBackground()));

        stage.setScene(new Scene(pane));
        stage.setTitle("Login Menu");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().
                getResource("/Images/icon.png")).toExternalForm()));
        pane.requestFocus();
        stage.show();
        Data.setStage(stage);
    }

    private BackgroundImage registerBackground() {
        Image image = new Image(Objects.requireNonNull(Register.class.getResource("/Images/register.jpg"))
                .toExternalForm(), 1200, 900, false, false);
        return new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    }
}
