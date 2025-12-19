package com.mojo.plstats.ui;

import com.mojo.plstats.application.PlayerManager;
import com.mojo.plstats.core.Player;
import com.mojo.plstats.infrastructure.PlayerFileHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.util.List;

public class LoadPlayersController {

    @FXML
    private TextField txtPath;
    @FXML
    private Button btnLoad;
    @FXML
    private Label lblStatus;
    @FXML
    private Button btnBack;

    private final PlayerManager manager = PlayerManager.getInstance();

    @FXML
    public void initialize() {
        btnLoad.setOnAction(e -> load());
        btnBack.setOnAction(e -> goBack());
    }

    private void load() {
        try {
            String path = txtPath.getText();
            List<Player> loaded = PlayerFileHandler.loadPlayersFromFile(path);
            for (Player p : loaded) {
                manager.addPlayer(p);
            }
            lblStatus.setText("Players loaded and added to system.");
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
