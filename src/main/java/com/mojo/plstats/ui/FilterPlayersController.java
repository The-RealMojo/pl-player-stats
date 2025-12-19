package com.mojo.plstats.ui;

import com.mojo.plstats.application.PlayerManager;
import com.mojo.plstats.core.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.util.List;

public class FilterPlayersController {

    @FXML
    private TextField txtClub;
    @FXML
    private TextField txtPosition;
    @FXML
    private TextField txtAge;
    @FXML
    private Button btnFilter;
    @FXML
    private TextArea txtResult;
    @FXML
    private Button btnBack;

    private final PlayerManager manager = PlayerManager.getInstance();

    @FXML
    public void initialize() {
        btnFilter.setOnAction(e -> applyFilters());
        btnBack.setOnAction(e -> goBack());
    }

    private void applyFilters() {
        try {
            String club = txtClub.getText().isBlank() ? null : txtClub.getText();
            String position = txtPosition.getText().isBlank() ? null : txtPosition.getText();

            Integer age = null;
            if (!txtAge.getText().isBlank()) {
                age = Integer.parseInt(txtAge.getText());
            }

            List<Player> results = manager.filterPlayers(club, position, age);

            if (results.isEmpty()) {
                txtResult.setText("No players match your filters.");
                txtClub.clear();
                txtPosition.clear();
                txtAge.clear();
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("═══════════════════════════════════════════\n");
            sb.append(String.format("  FILTERED RESULTS: %d player(s) found\n", results.size()));
            sb.append("═══════════════════════════════════════════\n\n");

            for (int i = 0; i < results.size(); i++) {
                Player p = results.get(i);
                sb.append(String.format("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n"));
                sb.append(String.format("  Player #%d\n", i + 1));
                sb.append(String.format("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n"));
                sb.append(String.format("  Name:            %s\n", p.getName()));
                sb.append(String.format("  Age:             %d years\n", p.getAge()));
                sb.append(String.format("  Club:            %s\n", p.getClub()));
                sb.append(String.format("  Position:        %s\n", p.getPosition()));
                sb.append(String.format("  Goals:           %d  |  Assists: %d\n", p.getGoals(), p.getAssists()));
                sb.append(String.format("  Height:          %d cm  |  Nationality: %s\n", p.getHeight(),
                        p.getNationality()));
                sb.append(String.format("  Market Value:    £%.2f million\n", p.getMarketValue()));
                sb.append(String.format("  Available:       %s\n", p.isAvailable() ? "Yes ✓" : "No ✗"));

                if (p instanceof com.mojo.plstats.core.Goalkeeper) {
                    sb.append(String.format("  Clean Sheets:    %d\n",
                            ((com.mojo.plstats.core.Goalkeeper) p).getCleanSheets()));
                }

                if (i < results.size() - 1) {
                    sb.append("\n");
                }
            }
            txtResult.setText(sb.toString());
            txtClub.clear();
            txtPosition.clear();
            txtAge.clear();
        } catch (NumberFormatException ex) {
            txtResult.setText("Error: Invalid age format. Please enter a number.");
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
