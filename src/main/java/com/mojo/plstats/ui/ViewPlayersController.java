package com.mojo.plstats.ui;

import com.mojo.plstats.application.PlayerManager;
import com.mojo.plstats.core.Player;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ViewPlayersController {

    @FXML
    private TableView<Player> playersTable;

    @FXML
    private TableColumn<Player, String> colName;
    @FXML
    private TableColumn<Player, Integer> colAge;
    @FXML
    private TableColumn<Player, String> colClub;
    @FXML
    private TableColumn<Player, String> colPosition;
    @FXML
    private TableColumn<Player, Integer> colGoals;
    @FXML
    private TableColumn<Player, Integer> colAssists;
    @FXML
    private Button btnBack;

    @FXML
    public void initialize() {

        // Setup table columns
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colClub.setCellValueFactory(new PropertyValueFactory<>("club"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colGoals.setCellValueFactory(new PropertyValueFactory<>("goals"));
        colAssists.setCellValueFactory(new PropertyValueFactory<>("assists"));

        // Load data from PlayerManager (singleton!)
        PlayerManager pm = PlayerManager.getInstance();

        playersTable.setItems(FXCollections.observableArrayList(pm.getPlayers()));

        // Wire back button
        btnBack.setOnAction(e -> goBack());
    }

    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
