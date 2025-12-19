package com.mojo.plstats.ui;

import com.mojo.plstats.application.PlayerManager;
import com.mojo.plstats.core.Player;
import com.mojo.plstats.infrastructure.PlayerAPIService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class FetchPlayerFromAPIController {

    @FXML
    private TextField txtPlayerName;
    @FXML
    private Button btnSearchPlayer;
    @FXML
    private TextArea txtResult;
    @FXML
    private Label lblStatus;
    @FXML
    private Button btnAddToSystem;
    @FXML
    private Button btnBack;

    private final PlayerManager manager = PlayerManager.getInstance();
    private final PlayerAPIService apiService = new PlayerAPIService();
    private Player fetchedPlayer;

    @FXML
    public void initialize() {
        btnSearchPlayer.setOnAction(e -> searchPlayer());
        btnAddToSystem.setOnAction(e -> addPlayerToSystem());
        btnBack.setOnAction(e -> goBack());
        
        lblStatus.setText("Ready to search");
        lblStatus.setStyle("-fx-text-fill: #9ca3af;");
    }

    private void searchPlayer() {
        String playerName = txtPlayerName.getText().trim();
        
        if (playerName.isEmpty()) {
            txtResult.setText("⚠ Please enter a player name to search.");
            return;
        }

        try {
            lblStatus.setText("Searching for player...");
            lblStatus.setStyle("-fx-text-fill: #3b82f6;");
            
            fetchedPlayer = apiService.fetchPlayerByName(playerName);
            
            if (fetchedPlayer == null) {
                txtResult.setText("❌ Player not found in API: " + playerName);
                lblStatus.setText("Player not found");
                lblStatus.setStyle("-fx-text-fill: #ef4444;");
                return;
            }

            // Display player information
            StringBuilder sb = new StringBuilder();
            sb.append("═══════════════════════════════════════════\n");
            sb.append("           PLAYER FOUND (API)\n");
            sb.append("═══════════════════════════════════════════\n\n");
            sb.append(formatPlayerInfo(fetchedPlayer));
            sb.append("\n═══════════════════════════════════════════\n");
            sb.append("Click 'Add to System' to import this player.\n");
            
            txtResult.setText(sb.toString());
            lblStatus.setText("✓ Player found! Click 'Add to System' to import.");
            lblStatus.setStyle("-fx-text-fill: #10b981;");
            
        } catch (Exception ex) {
            txtResult.setText("❌ Error fetching player: " + ex.getMessage());
            lblStatus.setText("Error: " + ex.getMessage());
            lblStatus.setStyle("-fx-text-fill: #ef4444;");
        }
    }

    private void addPlayerToSystem() {
        if (fetchedPlayer == null) {
            txtResult.setText("⚠ No player selected. Please search for a player first.");
            return;
        }

        try {
            // Check if player already exists
            Player existing = manager.searchPlayer(fetchedPlayer.getName());
            if (existing != null) {
                txtResult.setText("⚠ Player '" + fetchedPlayer.getName() + "' already exists in the system.\n" +
                                 "Use 'Update Player Stats' to modify existing players.");
                lblStatus.setText("Player already exists");
                lblStatus.setStyle("-fx-text-fill: #f59e0b;");
                return;
            }

            if (manager.addPlayer(fetchedPlayer)) {
                txtResult.setText("✅ Player '" + fetchedPlayer.getName() + "' successfully added to the system!\n\n" +
                                 formatPlayerInfo(fetchedPlayer));
                lblStatus.setText("✓ Player added successfully!");
                lblStatus.setStyle("-fx-text-fill: #10b981;");
                fetchedPlayer = null; // Reset
                txtPlayerName.clear();
            } else {
                txtResult.setText("❌ Failed to add player to system.");
                lblStatus.setText("Failed to add player");
                lblStatus.setStyle("-fx-text-fill: #ef4444;");
            }
        } catch (Exception ex) {
            txtResult.setText("❌ Error adding player: " + ex.getMessage());
            lblStatus.setText("Error: " + ex.getMessage());
            lblStatus.setStyle("-fx-text-fill: #ef4444;");
        }
    }

    private String formatPlayerInfo(Player p) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("  Name:            %s\n", p.getName()));
        sb.append(String.format("  Age:             %d years\n", p.getAge()));
        sb.append(String.format("  Club:            %s\n", p.getClub()));
        sb.append(String.format("  Position:        %s\n", p.getPosition()));
        sb.append(String.format("  Goals:           %d\n", p.getGoals()));
        sb.append(String.format("  Assists:         %d\n", p.getAssists()));
        sb.append(String.format("  Height:          %d cm\n", p.getHeight()));
        sb.append(String.format("  Nationality:     %s\n", p.getNationality()));
        sb.append(String.format("  Preferred Foot:  %s\n", p.getPreferredFoot()));
        sb.append(String.format("  Market Value:    £%.2f million\n", p.getMarketValue()));
        sb.append(String.format("  Available:       %s\n", p.isAvailable() ? "Yes ✓" : "No ✗"));

        if (p instanceof com.mojo.plstats.core.Goalkeeper) {
            sb.append(String.format("  Clean Sheets:    %d\n",
                    ((com.mojo.plstats.core.Goalkeeper) p).getCleanSheets()));
        }
        return sb.toString();
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

