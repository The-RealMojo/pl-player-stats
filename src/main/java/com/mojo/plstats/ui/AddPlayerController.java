package com.mojo.plstats.ui;

import com.mojo.plstats.application.PlayerManager;
import com.mojo.plstats.core.Goalkeeper;
import com.mojo.plstats.core.OutfieldPlayer;
import com.mojo.plstats.core.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class AddPlayerController {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtAge;
    @FXML
    private TextField txtClub;
    @FXML
    private TextField txtPosition;
    @FXML
    private TextField txtGoals;
    @FXML
    private TextField txtAssists;
    @FXML
    private TextField txtAvailable;
    @FXML
    private TextField txtHeight;
    @FXML
    private TextField txtNationality;
    @FXML
    private TextField txtPreferredFoot;
    @FXML
    private TextField txtMarketValue;
    @FXML
    private TextField txtCleanSheets;
    @FXML
    private Label lblStatus;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnBack;

    private final PlayerManager manager = PlayerManager.getInstance();

    @FXML
    public void initialize() {
        btnSave.setOnAction(e -> savePlayer());
        btnBack.setOnAction(e -> goBack());
    }

    private void savePlayer() {
        try {
            String name = txtName.getText();
            int age = Integer.parseInt(txtAge.getText());
            String club = txtClub.getText();
            String position = txtPosition.getText();
            int goals = Integer.parseInt(txtGoals.getText());
            int assists = Integer.parseInt(txtAssists.getText());
            boolean isAvailable = Boolean.parseBoolean(txtAvailable.getText());
            int height = Integer.parseInt(txtHeight.getText());
            String nationality = txtNationality.getText();
            String preferredFoot = txtPreferredFoot.getText().trim().toUpperCase();
            double marketValue = Double.parseDouble(txtMarketValue.getText());

            int cleanSheets = 0;
            if (position.equalsIgnoreCase("GK") && !txtCleanSheets.getText().isBlank()) {
                cleanSheets = Integer.parseInt(txtCleanSheets.getText());
            }

            Player newPlayer;
            if (position.equalsIgnoreCase("GK")) {
                newPlayer = new Goalkeeper(
                        name, age, club, position, goals, assists,
                        isAvailable, height, nationality, preferredFoot,
                        marketValue, cleanSheets);
            } else {
                newPlayer = new OutfieldPlayer(
                        name, age, club, position, goals, assists,
                        isAvailable, height, nationality, preferredFoot,
                        marketValue);
            }

            if (manager.addPlayer(newPlayer)) {
                lblStatus.setText("Player added successfully!");
                lblStatus.setStyle("-fx-text-fill: green;");
                clearFields();
            } else {
                lblStatus.setText("Failed to add player (duplicate or invalid).");
                lblStatus.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception ex) {
            lblStatus.setText("Error: " + ex.getMessage());
            lblStatus.setStyle("-fx-text-fill: red;");
        }
    }

    private void clearFields() {
        txtName.clear();
        txtAge.clear();
        txtClub.clear();
        txtPosition.clear();
        txtGoals.clear();
        txtAssists.clear();
        txtAvailable.clear();
        txtHeight.clear();
        txtNationality.clear();
        txtPreferredFoot.clear();
        txtMarketValue.clear();
        txtCleanSheets.clear();
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
