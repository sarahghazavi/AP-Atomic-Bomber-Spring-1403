package view;

import controller.UserController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import model.Data;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;

public class ScoreSystem {
    @FXML
    TableView<Map.Entry<String, Integer>> table;
    @FXML
    TableColumn<Map.Entry<String, Integer>, String> usernameColumn;
    @FXML
    TableColumn<Map.Entry<String, Integer>, Integer> dataColumn;
    @FXML
    Label title;
    @FXML
    Pane pane;
    private static final int MAX_PLAYERS = 50;
    private static final String[] players = new String[MAX_PLAYERS];
    private static final int[] wave = new int[MAX_PLAYERS];
    private static final int[] kill = new int[MAX_PLAYERS];
    private static final int[] xKill = new int[MAX_PLAYERS];
    private static final int[] shoot = new int[MAX_PLAYERS];
    private static final int[] xShoot = new int[MAX_PLAYERS];

    @FXML
    public void initialize() {
        usernameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        dataColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());

        usernameColumn.setCellFactory(new Callback<TableColumn<Map.Entry<String, Integer>, String>,
                TableCell<Map.Entry<String, Integer>, String>>() {
            @Override
            public TableCell<Map.Entry<String, Integer>, String> call(TableColumn<Map.Entry<String, Integer>, String> param) {
                return new TableCell<Map.Entry<String, Integer>, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (getIndex() < 3 && !empty)
                            setStyle("-fx-text-fill: #700e0e;");
                        else
                            setStyle("");
                    }
                };
            }
        });
    }

    public static void walkOnDirectory() {
        Path path = Paths.get("src/main/resources/Players");
        int index = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path subPath : stream) {
                uploadData(subPath, index);
                index++;
            }
        } catch (Exception e) {
            System.out.println("ScoreSystem : walkOnDirectory");
        }
    }

    private static void uploadData(Path path, int index) {
        String username = path.toString().substring(path.toString().lastIndexOf("\\") + 1);
        players[index] = username;
        wave[index] = Integer.parseInt(Objects.requireNonNull
                (UserController.getField(username, "wave")));
        kill[index] = Integer.parseInt(Objects.requireNonNull
                (UserController.getField(username, "kill")));
        xKill[index] = Integer.parseInt(Objects.requireNonNull
                (UserController.getField(username, "xKill")));
        shoot[index] = Integer.parseInt(Objects.requireNonNull
                (UserController.getField(username, "shoot")));
        xShoot[index] = Integer.parseInt(Objects.requireNonNull
                (UserController.getField(username, "xShoot")));
    }

    public void resetFocus() {
        pane.requestFocus();
    }

    public void back() throws Exception {
        Data.getMainMenu().start(Data.getStage());
    }

    private void show(String[] name, int[] data, String title) {
        ObservableList<Map.Entry<String, Integer>> items = FXCollections.observableArrayList();
        for (int i = 0; i < name.length && name[i] != null; i++)
            items.add(new AbstractMap.SimpleEntry<>(name[i], data[i]));
        table.setItems(items);
        this.title.setText(title);
    }

    private void show(String[] name, String[] data) {
        ObservableList<Map.Entry<String, Integer>> items = FXCollections.observableArrayList();
        for (int i = 0; i < name.length && name[i] != null; i++)
            items.add(new AbstractMap.SimpleEntry<>(name[i], Integer.parseInt(data[i])));
        table.setItems(items);
        this.title.setText("Best Shooter");
    }

    private void sort(String[] names, int[] data1, int i, int j, int[] data2) {
        if (data2[j] > data2[i]) {
            int num = data1[i];
            data1[i] = data1[j];
            data1[j] = num;
            String str = names[i];
            names[i] = names[j];
            names[j] = str;
        }
    }

    public void showBestPlayers() {
        String[] names = new String[players.length];
        int[] scores = new int[players.length];

        for (int i = 0; i < players.length; i++) {
            scores[i] = xKill[i];
            names[i] = players[i];
        }

        for (int i = 0; i < players.length; i++) {
            for (int j = i + 1; j < players.length; j++) {
                sort(names, scores, i, j, scores);
                if (scores[j] == scores[i])
                    sort(names, scores, i, j, wave);
            }
        }

        show(names, scores, "Best Players");
    }


    public void showBestKillers() {
        String[] names = new String[players.length];
        int[] kills = new int[players.length];

        for (int i = 0; i < players.length; i++) {
            kills[i] = kill[i];
            names[i] = players[i];
        }

        for (int i = 0; i < players.length; i++)
            for (int j = i + 1; j < players.length; j++)
                sort(names, kills, i, j, kills);

        show(names, kills, "Best Killers");
    }

    public void showLastWave() {
        String[] names = new String[players.length];
        int[] waves = new int[players.length];

        for (int i = 0; i < players.length; i++) {
            waves[i] = wave[i];
            names[i] = players[i];
        }

        for (int i = 0; i < players.length; i++)
            for (int j = i + 1; j < players.length; j++)
                sort(names, waves, i, j, waves);

        show(names, waves, "Last Wave Number");
    }

    public void showBestShooter() {
        DecimalFormat df = new DecimalFormat("#.##");
        String[] names = new String[players.length];
        String[] shooter = new String[players.length];

        for (int i = 0; i < players.length; i++) {
            if (shoot[i] == 0)
                shooter[i] = "0";
            else
                shooter[i] = df.format((double) xShoot[i] / shoot[i]);
            names[i] = players[i];
        }

        for (int i = 0; i < players.length; i++) {
            for (int j = i + 1; j < players.length; j++) {
                if (Double.parseDouble(shooter[j]) > Double.parseDouble(shooter[i])) {
                    String str = shooter[i];
                    shooter[i] = shooter[j];
                    shooter[j] = str;
                    str = names[i];
                    names[i] = names[j];
                    names[j] = str;
                }
            }
        }

        show(names, shooter);
    }
}