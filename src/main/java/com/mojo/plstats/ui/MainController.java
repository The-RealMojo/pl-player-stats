package com.mojo.plstats.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private Button btnShowAll;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnFilter;
    @FXML
    private Button btnHighestScorers;
    @FXML
    private Button btnMostAssists;
    @FXML
    private Button btnTransfer;
    @FXML
    private Button btnCompare;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnLoad;

    @FXML
    public void initialize() {
        btnShowAll.setOnAction(e -> loadScreen("ViewPlayersView.fxml"));
        btnAdd.setOnAction(e -> loadScreen("AddPlayerView.fxml"));
        btnSearch.setOnAction(e -> loadScreen("SearchPlayerView.fxml"));
        btnFilter.setOnAction(e -> loadScreen("FilterPlayersView.fxml"));
        btnHighestScorers.setOnAction(e -> loadScreen("HighestScorersView.fxml"));
        btnMostAssists.setOnAction(e -> loadScreen("MostAssistsView.fxml"));
        btnTransfer.setOnAction(e -> loadScreen("TransferPlayerView.fxml"));
        btnCompare.setOnAction(e -> loadScreen("ComparePlayersView.fxml"));
        btnSave.setOnAction(e -> loadScreen("SavePlayersView.fxml"));
        btnLoad.setOnAction(e -> loadScreen("LoadPlayersView.fxml"));
    }

    private void loadScreen(String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlName));
            Parent root = loader.load();
            Scene scene = btnShowAll.getScene(); // use an injected button that exists
            scene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
