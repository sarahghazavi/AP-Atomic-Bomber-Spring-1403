package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Data;

public class Score extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ScoreSystem.walkOnDirectory();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Score.fxml"));
        Pane pane = fxmlLoader.load();
        pane.setBackground(new Background(Data.getBackground()));
        Scene scene = new Scene(pane);
        stage.setTitle("Score Board");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setScene(scene);
        pane.requestFocus();
        stage.show();
    }
}