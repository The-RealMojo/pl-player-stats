package com.mojo.plstats.ui;

import com.mojo.plstats.application.PlayerManager;
import com.mojo.plstats.core.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class SearchPlayerController {

    @FXML
    private TextField txtName;
    @FXML
    private Button btnSearch;
    @FXML
    private TextArea txtResult;
    @FXML
    private Button btnBack;

    private final PlayerManager manager = PlayerManager.getInstance();

    @FXML
    public void initialize() {
        btnSearch.setOnAction(e -> search());
        btnBack.setOnAction(e -> goBack());
    }

    private void search() {
        String name = txtName.getText();
        Player p = manager.searchPlayer(name);
        if (p == null) {
            txtResult.setText("❌ Player not found: " + name);
        } else {
            txtResult.setText(formatPlayerInfo(p));
            txtName.clear();
        }
    }

    private String formatPlayerInfo(Player p) {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════\n");
        sb.append("           PLAYER INFORMATION\n");
        sb.append("═══════════════════════════════════════════\n\n");
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

        sb.append("\n═══════════════════════════════════════════\n");
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
