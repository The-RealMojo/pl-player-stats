package com.mojo.plstats.ui;

import com.mojo.plstats.application.PlayerManager;
import com.mojo.plstats.core.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class ComparePlayersController {

    @FXML
    private TextField txtP1;
    @FXML
    private TextField txtP2;
    @FXML
    private Button btnCompare;
    @FXML
    private TextArea txtResult;
    @FXML
    private Button btnBack;

    private final PlayerManager manager = PlayerManager.getInstance();

    @FXML
    public void initialize() {
        btnCompare.setOnAction(e -> compare());
        btnBack.setOnAction(e -> goBack());
    }

    private void compare() {
        String p1 = txtP1.getText().trim();
        String p2 = txtP2.getText().trim();

        if (p1.isEmpty() || p2.isEmpty()) {
            txtResult.setText("⚠ Please enter both player names.");
            return;
        }

        Player player1 = manager.searchPlayer(p1);
        Player player2 = manager.searchPlayer(p2);

        if (player1 == null && player2 == null) {
            txtResult.setText("❌ Both players not found:\n   • " + p1 + "\n   • " + p2);
            return;
        } else if (player1 == null) {
            txtResult.setText("❌ Player not found: " + p1);
            return;
        } else if (player2 == null) {
            txtResult.setText("❌ Player not found: " + p2);
            return;
        }

        int statsP1 = player1.getGoals() + player1.getAssists();
        int statsP2 = player2.getGoals() + player2.getAssists();

        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════\n");
        sb.append("           PLAYER COMPARISON\n");
        sb.append("═══════════════════════════════════════════\n\n");

        // Player 1 Info
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("  PLAYER 1: ").append(player1.getName()).append("\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append(String.format("  Club:            %s\n", player1.getClub()));
        sb.append(String.format("  Position:         %s\n", player1.getPosition()));
        sb.append(String.format("  Goals:            %d\n", player1.getGoals()));
        sb.append(String.format("  Assists:          %d\n", player1.getAssists()));
        sb.append(String.format("  Total G/A:        %d\n", statsP1));
        sb.append(String.format("  Market Value:     £%.2f million\n", player1.getMarketValue()));

        sb.append("\n");

        // Player 2 Info
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("  PLAYER 2: ").append(player2.getName()).append("\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append(String.format("  Club:            %s\n", player2.getClub()));
        sb.append(String.format("  Position:         %s\n", player2.getPosition()));
        sb.append(String.format("  Goals:            %d\n", player2.getGoals()));
        sb.append(String.format("  Assists:          %d\n", player2.getAssists()));
        sb.append(String.format("  Total G/A:        %d\n", statsP2));
        sb.append(String.format("  Market Value:     £%.2f million\n", player2.getMarketValue()));

        sb.append("\n");

        // Comparison Result
        sb.append("═══════════════════════════════════════════\n");
        sb.append("           COMPARISON RESULT\n");
        sb.append("═══════════════════════════════════════════\n");
        if (statsP1 > statsP2) {
            sb.append(String.format("\n  🏆 %s leads!\n", player1.getName()));
            sb.append(String.format("     Total G/A: %d vs %d\n", statsP1, statsP2));
            sb.append(String.format("     Difference: +%d\n", statsP1 - statsP2));
        } else if (statsP2 > statsP1) {
            sb.append(String.format("\n  🏆 %s leads!\n", player2.getName()));
            sb.append(String.format("     Total G/A: %d vs %d\n", statsP2, statsP1));
            sb.append(String.format("     Difference: +%d\n", statsP2 - statsP1));
        } else {
            sb.append(String.format("\n  ⚖ It's a tie!\n"));
            sb.append(String.format("     Both players have %d total G/A\n", statsP1));
        }
        sb.append("\n═══════════════════════════════════════════\n");

        txtResult.setText(sb.toString());
        txtP1.clear();
        txtP2.clear();
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
