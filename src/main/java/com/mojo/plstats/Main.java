package com.mojo.plstats;

import com.mojo.plstats.application.PlayerManager;
import com.mojo.plstats.core.Player;
import com.mojo.plstats.infrastructure.PlayerFileHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Main extends Application {

    private static final String DEFAULT_DATABASE_FILE = "players.txt";

    @Override
    public void start(Stage stage) throws Exception {
        // Auto-load database on startup
        loadDatabaseOnStartup();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainView.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setTitle("Premier League Player System");
        stage.setScene(scene);

        // Auto-save database when window closes
        stage.setOnCloseRequest(e -> saveDatabaseOnExit());

        stage.show();
    }

    /**
     * Automatically loads player data from the default database file on startup.
     * If the file doesn't exist, the app starts with an empty database.
     */
    private void loadDatabaseOnStartup() {
        try {
            File dbFile = new File(DEFAULT_DATABASE_FILE);

            if (dbFile.exists() && dbFile.length() > 0) {
                PlayerManager manager = PlayerManager.getInstance();
                List<Player> loadedPlayers = PlayerFileHandler.loadPlayersFromFile(DEFAULT_DATABASE_FILE);

                int loadedCount = 0;
                for (Player p : loadedPlayers) {
                    if (manager.addPlayer(p)) {
                        loadedCount++;
                    }
                }

                System.out.println(
                        "✓ Database loaded: " + loadedCount + " player(s) loaded from " + DEFAULT_DATABASE_FILE);
            } else {
                System.out.println("ℹ No database file found. Starting with empty database.");
                System.out.println("  (Database will be saved to: " + DEFAULT_DATABASE_FILE + ")");
            }
        } catch (Exception e) {
            System.err.println("⚠ Error loading database on startup: " + e.getMessage());
            System.err.println("  Starting with empty database.");
        }
    }

    /**
     * Automatically saves player data to the default database file when the app
     * closes.
     */
    private void saveDatabaseOnExit() {
        try {
            PlayerManager manager = PlayerManager.getInstance();
            List<Player> players = manager.getAllPlayers();

            if (!players.isEmpty()) {
                PlayerFileHandler.savePlayersToFile(players, DEFAULT_DATABASE_FILE);
                System.out.println(
                        "✓ Database saved: " + players.size() + " player(s) saved to " + DEFAULT_DATABASE_FILE);
            }
        } catch (Exception e) {
            System.err.println("⚠ Error saving database on exit: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
