package com.mojo.plstats.infrastructure;

import com.mojo.plstats.core.Goalkeeper;
import com.mojo.plstats.core.OutfieldPlayer;
import com.mojo.plstats.core.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PlayerFileHandler class.
 */
@DisplayName("PlayerFileHandler Tests")
class PlayerFileHandlerTest {

    @TempDir
    Path tempDir;

    private List<Player> testPlayers;
    private String testFilePath;

    @BeforeEach
    void setUp() {
        testPlayers = new ArrayList<>();
        testPlayers.add(new OutfieldPlayer(
                "Harry Kane", 30, "Tottenham", "CF",
                20, 8, true, 188, "England", "R", 100.0
        ));
        testPlayers.add(new Goalkeeper(
                "David Raya", 28, "Arsenal", "GK",
                0, 1, true, 183, "Spain", "R", 28.0, 12
        ));
        testPlayers.add(new OutfieldPlayer(
                "Bukayo Saka", 23, "Arsenal", "RW",
                12, 9, true, 178, "England", "L", 120.0
        ));

        testFilePath = tempDir.resolve("test_players.txt").toString();
    }

    @Test
    @DisplayName("Should save players to file")
    void testSavePlayersToFile() {
        PlayerFileHandler.savePlayersToFile(testPlayers, testFilePath);
        
        File file = new File(testFilePath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    @DisplayName("Should load players from file")
    void testLoadPlayersFromFile() throws IOException {
        PlayerFileHandler.savePlayersToFile(testPlayers, testFilePath);
        List<Player> loaded = PlayerFileHandler.loadPlayersFromFile(testFilePath);

        assertEquals(3, loaded.size());
        assertEquals("Harry Kane", loaded.get(0).getName());
        assertEquals("David Raya", loaded.get(1).getName());
        assertEquals("Bukayo Saka", loaded.get(2).getName());
    }

    @Test
    @DisplayName("Should preserve player data when saving and loading")
    void testPreservePlayerData() throws IOException {
        PlayerFileHandler.savePlayersToFile(testPlayers, testFilePath);
        List<Player> loaded = PlayerFileHandler.loadPlayersFromFile(testFilePath);

        Player loadedKane = loaded.get(0);
        assertEquals("Harry Kane", loadedKane.getName());
        assertEquals(30, loadedKane.getAge());
        assertEquals("Tottenham", loadedKane.getClub());
        assertEquals("CF", loadedKane.getPosition());
        assertEquals(20, loadedKane.getGoals());
        assertEquals(8, loadedKane.getAssists());
        assertEquals(188, loadedKane.getHeight());
        assertEquals("England", loadedKane.getNationality());
        assertEquals("R", loadedKane.getPreferredFoot());
        assertEquals(100.0, loadedKane.getMarketValue());
        assertTrue(loadedKane.isAvailable());
    }

    @Test
    @DisplayName("Should preserve goalkeeper clean sheets")
    void testPreserveGoalkeeperData() throws IOException {
        PlayerFileHandler.savePlayersToFile(testPlayers, testFilePath);
        List<Player> loaded = PlayerFileHandler.loadPlayersFromFile(testFilePath);

        Player loadedRaya = loaded.get(1);
        assertTrue(loadedRaya instanceof Goalkeeper);
        Goalkeeper gk = (Goalkeeper) loadedRaya;
        assertEquals(12, gk.getCleanSheets());
    }

    @Test
    @DisplayName("Should handle empty player list")
    void testSaveEmptyList() {
        List<Player> emptyList = new ArrayList<>();
        PlayerFileHandler.savePlayersToFile(emptyList, testFilePath);
        
        File file = new File(testFilePath);
        assertTrue(file.exists());
    }

    @Test
    @DisplayName("Should handle loading from non-existent file")
    void testLoadFromNonExistentFile() {
        assertThrows(IOException.class, () -> {
            PlayerFileHandler.loadPlayersFromFile("non_existent_file.txt");
        });
    }

    @Test
    @DisplayName("Should skip invalid lines when loading")
    void testSkipInvalidLines() throws IOException {
        // Create a file with valid and invalid lines
        String invalidFilePath = tempDir.resolve("invalid_players.txt").toString();
        java.io.FileWriter writer = new java.io.FileWriter(invalidFilePath);
        writer.write("OUT,Harry Kane,30,Tottenham,CF,20,8,true,188,England,R,100.0,0\n");
        writer.write("INVALID_LINE\n");
        writer.write("OUT,Bukayo Saka,23,Arsenal,RW,12,9,true,178,England,L,120.0,0\n");
        writer.write("OUT,Incomplete Line,25\n");
        writer.close();

        List<Player> loaded = PlayerFileHandler.loadPlayersFromFile(invalidFilePath);
        // Should load 2 valid players, skip 2 invalid lines
        assertEquals(2, loaded.size());
    }

    @Test
    @DisplayName("Should handle empty lines when loading")
    void testHandleEmptyLines() throws IOException {
        String fileWithEmptyLines = tempDir.resolve("empty_lines.txt").toString();
        java.io.FileWriter writer = new java.io.FileWriter(fileWithEmptyLines);
        writer.write("OUT,Harry Kane,30,Tottenham,CF,20,8,true,188,England,R,100.0,0\n");
        writer.write("\n");
        writer.write("  \n");
        writer.write("OUT,Bukayo Saka,23,Arsenal,RW,12,9,true,178,England,L,120.0,0\n");
        writer.close();

        List<Player> loaded = PlayerFileHandler.loadPlayersFromFile(fileWithEmptyLines);
        assertEquals(2, loaded.size());
    }

    @Test
    @DisplayName("Should round-trip save and load correctly")
    void testRoundTrip() throws IOException {
        // Save original players
        PlayerFileHandler.savePlayersToFile(testPlayers, testFilePath);
        
        // Load them back
        List<Player> loaded = PlayerFileHandler.loadPlayersFromFile(testFilePath);
        
        // Save again
        String secondFilePath = tempDir.resolve("second_save.txt").toString();
        PlayerFileHandler.savePlayersToFile(loaded, secondFilePath);
        
        // Load again and verify
        List<Player> reloaded = PlayerFileHandler.loadPlayersFromFile(secondFilePath);
        assertEquals(3, reloaded.size());
        assertEquals("Harry Kane", reloaded.get(0).getName());
    }

    @Test
    @DisplayName("Should handle special characters in player names")
    void testSpecialCharacters() throws IOException {
        List<Player> specialPlayers = new ArrayList<>();
        specialPlayers.add(new OutfieldPlayer(
                "José Mourinho", 25, "Arsenal", "CM", 5, 3, true, 175, "Portugal", "R", 50.0
        ));
        
        PlayerFileHandler.savePlayersToFile(specialPlayers, testFilePath);
        List<Player> loaded = PlayerFileHandler.loadPlayersFromFile(testFilePath);
        
        assertEquals(1, loaded.size());
        assertEquals("José Mourinho", loaded.get(0).getName());
    }
}

