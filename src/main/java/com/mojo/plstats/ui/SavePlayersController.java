package com.mojo.plstats.ui;

import com.mojo.plstats.application.PlayerManager;
import com.mojo.plstats.infrastructure.PlayerFileHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class SavePlayersController {

    @FXML
    private TextField txtPath;
    @FXML
    private Button btnSave;
    @FXML
    private Label lblStatus;
    @FXML
    private Button btnBack;

    private final PlayerManager manager = PlayerManager.getInstance();

    private static final String DEFAULT_DATABASE_FILE = "players.txt";

    @FXML
    public void initialize() {
        // Pre-fill with default database file
        txtPath.setText(DEFAULT_DATABASE_FILE);
        btnSave.setOnAction(e -> save());
        btnBack.setOnAction(e -> goBack());
    }

    private void save() {
        try {
            String path = txtPath.getText();
            PlayerFileHandler.savePlayersToFile(manager.getAllPlayers(), path);
            lblStatus.setText("Players saved to file.");
            lblStatus.setStyle("-fx-text-fill: green;");
            txtPath.clear();
        } catch (Exception ex) {
            lblStatus.setText("Error: " + ex.getMessage());
            lblStatus.setStyle("-fx-text-fill: red;");
        }
    }

    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
            Parent root = loader.load();
            Scene scene = btnBack.getScene();
            scene.setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
