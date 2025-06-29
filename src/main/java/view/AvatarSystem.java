package view;

import controller.UserController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Data;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class AvatarSystem {
    @FXML
    Label avLabel;
    @FXML
    Label firstL;
    @FXML
    Label secondL;
    @FXML
    Label thirdL;
    @FXML
    Label forthL;
    @FXML
    Label fifthL;
    @FXML
    ImageView first;
    @FXML
    ImageView second;
    @FXML
    ImageView third;
    @FXML
    ImageView forth;
    @FXML
    ImageView fifth;
    // Button:
    @FXML
    Button choose;
    @FXML
    Button upload;
    @FXML
    Button drag;
    @FXML
    Button back;
    @FXML
    Pane pane;

    public void resetFocus() {
        pane.requestFocus();
    }

    public void back() throws Exception {
        Data.getProfileMenu().start(Data.getStage());
    }

    private void mainNodes(boolean state) {
        back.setVisible(state);
        choose.setVisible(state);
        upload.setVisible(state);
        drag.setVisible(state);
    }

    private void otherNodes(boolean state) {
        avLabel.setVisible(state);

        first.setVisible(state);
        second.setVisible(state);
        third.setVisible(state);
        forth.setVisible(state);
        fifth.setVisible(state);

        firstL.setVisible(state);
        secondL.setVisible(state);
        thirdL.setVisible(state);
        forthL.setVisible(state);
        fifthL.setVisible(state);
    }

    public void chooseAvatar() {
        mainNodes(false);
        otherNodes(true);
    }

    public void pickOne(MouseEvent mouseEvent) {
        if (first.equals(mouseEvent.getSource()))
            UserController.setAvatar(Data.getCurrentUsername(), 1);
        else if (second.equals(mouseEvent.getSource()))
            UserController.setAvatar(Data.getCurrentUsername(), 2);
        else if (third.equals(mouseEvent.getSource()))
            UserController.setAvatar(Data.getCurrentUsername(), 3);
        else if (forth.equals(mouseEvent.getSource()))
            UserController.setAvatar(Data.getCurrentUsername(), 4);
        else if (fifth.equals(mouseEvent.getSource()))
            UserController.setAvatar(Data.getCurrentUsername(), 5);

        Data.alert(Alert.AlertType.INFORMATION, "Choose Avatar",
                "Avatar selected successfully!", "");
        otherNodes(false);
        mainNodes(true);
    }

    public void uploadAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Avatar");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.
                ExtensionFilter("Images", "*.png"));

        File selected = fileChooser.showOpenDialog(Data.getStage());

        if (selected != null) {
            try {
                Path destination = Paths.get("src/main/resources/Players/" + Data.getCurrentUsername()
                        + "/avatar.png");
                Files.createDirectories(destination.getParent());
                Files.copy(selected.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
                Data.getAvatarMenu().start(Data.getStage());

                Data.alert(Alert.AlertType.INFORMATION, "Choose Avatar",
                        "Avatar uploaded successfully!", "");
            } catch (Exception e) {
                System.out.println("AvatarSystem : uploadAvatar");
            }
        }
    }

    public void dragDrop() {
        Stage dragStage = new Stage();
        Label drop = new Label("Drop your picture here");
        drop.getStyleClass().add("label");

        drop.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles() && event.getDragboard().getFiles().size() == 1
                    && isImageFile(event.getDragboard().getFiles().get(0)))
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        });

        drop.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                File file = db.getFiles().get(0);
                if (isImageFile(file)) {
                    success = true;
                    try {
                        Path destination = Paths.get("src/main/resources/Players/" +
                                Data.getCurrentUsername() + "/avatar.png");
                        Files.createDirectories(destination.getParent());
                        Files.copy(file.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

                        Data.alert(Alert.AlertType.INFORMATION, "Choose Avatar",
                                "Avatar uploaded successfully!", "");
                    } catch (Exception e) {
                        System.out.println("AvatarSystem : dragDrop");
                    }
                    dragStage.close();

                    try {
                        Data.getAvatarMenu().start(Data.getStage());
                    } catch (Exception e) {
                        System.out.println("AvatarSystem : dragDrop");
                    }
                } else
                    Data.alert(Alert.AlertType.ERROR, "Choose Avatar",
                            "Image format is invalid!", "Please try again.");
            }
            event.setDropCompleted(success);
            event.consume();
        });

        VBox root = new VBox(drop);
        root.getStylesheets().add(String.valueOf(getClass().getResource("/CSS/style.css")));
        dragStage.setScene(new Scene(root, 400, 300));
        dragStage.setTitle("Drag and Drop");
        dragStage.show();
    }

    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".png") || name.endsWith(".jpg") ||
                name.endsWith(".jpeg") || name.endsWith(".gif");
    }
}
