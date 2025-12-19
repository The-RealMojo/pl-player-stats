package com.mojo.plstats.ui;

import com.mojo.plstats.application.PlayerManager;
import com.mojo.plstats.core.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UpdatePlayerStatsController {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtGoals;
    @FXML
    private TextField txtAssists;
    @FXML
    private TextField txtMarketValue;
    @FXML
    private TextField txtAvailable;
    @FXML
    private TextArea txtSummary;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnSaveChanges;
    @FXML
    private Button btnBack;
    @FXML
    private Label lblStatus;

    private final PlayerManager manager = PlayerManager.getInstance();
    private Player currentPlayer;

    @FXML
    public void initialize() {
        btnSearch.setOnAction(e -> searchPlayer());
        btnSaveChanges.setOnAction(e -> saveChanges());
        btnBack.setOnAction(e -> goBack());
    }

    private void searchPlayer() {
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            setStatus("Please enter a player name to search.", false);
            clearDetails();
            return;
        }

        Player p = manager.searchPlayer(name);
        if (p == null) {
            setStatus("Player not found: " + name, false);
            clearDetails();
            return;
        }

        currentPlayer = p;
        // Populate editable fields
        txtGoals.setText(String.valueOf(p.getGoals()));
        txtAssists.setText(String.valueOf(p.getAssists()));
        txtMarketValue.setText(String.valueOf(p.getMarketValue()));
        txtAvailable.setText(String.valueOf(p.isAvailable()));

        // Show a quick summary
        txtSummary.setText(buildSummary(p));
        setStatus("Player loaded. Update stats and click Save Changes.", true);
    }

    private void saveChanges() {
        if (currentPlayer == null) {
            setStatus("Search for a player before saving changes.", false);
            return;
        }

        try {
            int goals = Integer.parseInt(txtGoals.getText().trim());
            int assists = Integer.parseInt(txtAssists.getText().trim());
            double marketValue = Double.parseDouble(txtMarketValue.getText().trim());
            boolean available = Boolean.parseBoolean(txtAvailable.getText().trim());

            currentPlayer.setGoals(goals);
            currentPlayer.setAssists(assists);
            currentPlayer.setMarketValue(marketValue);
            currentPlayer.setAvailable(available);

            txtSummary.setText(buildSummary(currentPlayer));
            setStatus("Stats updated successfully. Use 'Save to File' on the main menu to persist to disk.", true);
        } catch (NumberFormatException ex) {
            setStatus("Error: Goals, assists and market value must be valid numbers.", false);
        } catch (IllegalArgumentException ex) {
            setStatus("Error: " + ex.getMessage(), false);
        }
    }

    private String buildSummary(Player p) {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════\n");
        sb.append("           PLAYER SUMMARY\n");
        sb.append("═══════════════════════════════════════════\n\n");
        sb.append(String.format("  Name:            %s\n", p.getName()));
        sb.append(String.format("  Club:            %s\n", p.getClub()));
        sb.append(String.format("  Position:        %s\n", p.getPosition()));
        sb.append(String.format("  Goals:           %d\n", p.getGoals()));
        sb.append(String.format("  Assists:         %d\n", p.getAssists()));
        sb.append(String.format("  Market Value:    £%.2f million\n", p.getMarketValue()));
        sb.append(String.format("  Available:       %s\n", p.isAvailable() ? "Yes ✓" : "No ✗"));
        sb.append("\n═══════════════════════════════════════════\n");
        return sb.toString();
    }

    private void clearDetails() {
        currentPlayer = null;
        txtGoals.clear();
        txtAssists.clear();
        txtMarketValue.clear();
        txtAvailable.clear();
        txtSummary.clear();
    }

    private void setStatus(String message, boolean success) {
        lblStatus.setText(message);
        lblStatus.setStyle(success ? "-fx-text-fill: #22c55e;" : "-fx-text-fill: #f97373;");
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


