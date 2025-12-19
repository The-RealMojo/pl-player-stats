package com.mojo.plstats.ui;

import com.mojo.plstats.application.PlayerManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class TransferPlayerController {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtNewClub;
    @FXML
    private Button btnTransfer;
    @FXML
    private Label lblStatus;
    @FXML
    private Button btnBack;

    private final PlayerManager manager = PlayerManager.getInstance();

    @FXML
    public void initialize() {
        btnTransfer.setOnAction(e -> transfer());
        btnBack.setOnAction(e -> goBack());
    }

    private void transfer() {
        String name = txtName.getText();
        String newClub = txtNewClub.getText();

        if (manager.transferPlayer(name, newClub)) {
            lblStatus.setText("Transfer completed!");
            lblStatus.setStyle("-fx-text-fill: green;");
            txtName.clear();
            txtNewClub.clear();
        } else {
            lblStatus.setText("Transfer failed — player not found.");
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
