package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Data;

public class Settings extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Settings.fxml"));
        Pane pane = fxmlLoader.load();
        pane.setBackground(new Background(Data.getBackground()));
        Scene scene = new Scene(pane);
        stage.setTitle("Settings");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setScene(scene);
        pane.requestFocus();
        stage.show();
    }
}
