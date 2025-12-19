package com.mojo.plstats.application;

import com.mojo.plstats.core.Goalkeeper;
import com.mojo.plstats.core.OutfieldPlayer;
import com.mojo.plstats.core.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PlayerManager class.
 */
@DisplayName("PlayerManager Tests")
class PlayerManagerTest {

    private PlayerManager manager;
    private OutfieldPlayer player1;
    private OutfieldPlayer player2;
    private Goalkeeper goalkeeper1;

    @BeforeEach
    void setUp() {
        // Reset singleton instance for each test
        resetSingleton();
        manager = PlayerManager.getInstance();

        player1 = new OutfieldPlayer(
                "Harry Kane", 30, "Tottenham", "CF",
                20, 8, true, 188, "England", "R", 100.0
        );

        player2 = new OutfieldPlayer(
                "Bukayo Saka", 23, "Arsenal", "RW",
                12, 9, true, 178, "England", "L", 120.0
        );

        goalkeeper1 = new Goalkeeper(
                "David Raya", 28, "Arsenal", "GK",
                0, 1, true, 183, "Spain", "R", 28.0, 12
        );
    }

    private void resetSingleton() {
        try {
            Field instance = PlayerManager.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Should add player successfully")
    void testAddPlayer() {
        assertTrue(manager.addPlayer(player1));
        assertEquals(1, manager.getAllPlayers().size());
        assertTrue(manager.getAllPlayers().contains(player1));
    }

    @Test
    @DisplayName("Should not add null player")
    void testAddNullPlayer() {
        assertFalse(manager.addPlayer(null));
        assertEquals(0, manager.getAllPlayers().size());
    }

    @Test
    @DisplayName("Should not add duplicate player")
    void testAddDuplicatePlayer() {
        manager.addPlayer(player1);
        OutfieldPlayer duplicate = new OutfieldPlayer(
                "Harry Kane", 30, "Tottenham", "CF",
                20, 8, true, 188, "England", "R", 100.0
        );
        assertFalse(manager.addPlayer(duplicate));
        assertEquals(1, manager.getAllPlayers().size());
    }

    @Test
    @DisplayName("Should remove player by name")
    void testRemovePlayer() {
        manager.addPlayer(player1);
        manager.addPlayer(player2);
        assertTrue(manager.removePlayer("Harry Kane"));
        assertEquals(1, manager.getAllPlayers().size());
        assertFalse(manager.getAllPlayers().contains(player1));
    }

    @Test
    @DisplayName("Should remove player case-insensitively")
    void testRemovePlayerCaseInsensitive() {
        manager.addPlayer(player1);
        assertTrue(manager.removePlayer("HARRY KANE"));
        assertEquals(0, manager.getAllPlayers().size());
    }

    @Test
    @DisplayName("Should return false when removing non-existent player")
    void testRemoveNonExistentPlayer() {
        assertFalse(manager.removePlayer("Non Existent"));
    }

    @Test
    @DisplayName("Should search player by name")
    void testSearchPlayer() {
        manager.addPlayer(player1);
        manager.addPlayer(player2);

        Player found = manager.searchPlayer("Harry Kane");
        assertNotNull(found);
        assertEquals("Harry Kane", found.getName());
    }

    @Test
    @DisplayName("Should search player case-insensitively")
    void testSearchPlayerCaseInsensitive() {
        manager.addPlayer(player1);
        Player found = manager.searchPlayer("HARRY KANE");
        assertNotNull(found);
        assertEquals("Harry Kane", found.getName());
    }

    @Test
    @DisplayName("Should return null when player not found")
    void testSearchPlayerNotFound() {
        assertNull(manager.searchPlayer("Non Existent"));
    }

    @Test
    @DisplayName("Should transfer player to new club")
    void testTransferPlayer() {
        manager.addPlayer(player1);
        assertTrue(manager.transferPlayer("Harry Kane", "Manchester United"));
        Player transferred = manager.searchPlayer("Harry Kane");
        assertEquals("Manchester United", transferred.getClub());
    }

    @Test
    @DisplayName("Should return false when transferring non-existent player")
    void testTransferNonExistentPlayer() {
        assertFalse(manager.transferPlayer("Non Existent", "Arsenal"));
    }

    @Test
    @DisplayName("Should get highest scorers")
    void testHighestScorers() {
        OutfieldPlayer highScorer = new OutfieldPlayer("Top Scorer", 25, "Arsenal", "CF", 30, 5, true, 180, "England", "R", 100.0);
        manager.addPlayer(player1);
        manager.addPlayer(player2);
        manager.addPlayer(highScorer);

        List<Player> scorers = manager.highestScorers();
        assertEquals(3, scorers.size());
        assertEquals("Top Scorer", scorers.get(0).getName());
        assertEquals(30, scorers.get(0).getGoals());
    }

    @Test
    @DisplayName("Should get most assists")
    void testMostAssists() {
        OutfieldPlayer topAssister = new OutfieldPlayer("Top Assister", 25, "Arsenal", "AM", 5, 20, true, 175, "England", "R", 80.0);
        manager.addPlayer(player1);
        manager.addPlayer(player2);
        manager.addPlayer(topAssister);

        List<Player> assisters = manager.mostAssists();
        assertEquals(3, assisters.size());
        assertEquals("Top Assister", assisters.get(0).getName());
        assertEquals(20, assisters.get(0).getAssists());
    }

    @Test
    @DisplayName("Should filter players by club")
    void testFilterByClub() {
        manager.addPlayer(player1);
        manager.addPlayer(player2);
        manager.addPlayer(goalkeeper1);

        List<Player> arsenalPlayers = manager.filterPlayers("Arsenal", null, null);
        assertEquals(2, arsenalPlayers.size());
        assertTrue(arsenalPlayers.stream().allMatch(p -> p.getClub().equalsIgnoreCase("Arsenal")));
    }

    @Test
    @DisplayName("Should filter players by position")
    void testFilterByPosition() {
        manager.addPlayer(player1);
        manager.addPlayer(player2);
        manager.addPlayer(goalkeeper1);

        List<Player> goalkeepers = manager.filterPlayers(null, "GK", null);
        assertEquals(1, goalkeepers.size());
        assertEquals("GK", goalkeepers.get(0).getPosition());
    }

    @Test
    @DisplayName("Should filter players by age")
    void testFilterByAge() {
        manager.addPlayer(player1);
        manager.addPlayer(player2);
        manager.addPlayer(goalkeeper1);

        List<Player> age23 = manager.filterPlayers(null, null, 23);
        assertEquals(1, age23.size());
        assertEquals(23, age23.get(0).getAge());
    }

    @Test
    @DisplayName("Should filter players by multiple criteria")
    void testFilterByMultipleCriteria() {
        manager.addPlayer(player1);
        manager.addPlayer(player2);
        manager.addPlayer(goalkeeper1);

        List<Player> filtered = manager.filterPlayers("Arsenal", "RW", 23);
        assertEquals(1, filtered.size());
        assertEquals("Bukayo Saka", filtered.get(0).getName());
    }

    @Test
    @DisplayName("Should return empty list when no players match filter")
    void testFilterNoMatches() {
        manager.addPlayer(player1);
        List<Player> filtered = manager.filterPlayers("Non Existent Club", null, null);
        assertTrue(filtered.isEmpty());
    }

    @Test
    @DisplayName("Should sort players by age")
    void testSortByAge() {
        manager.addPlayer(player1); // age 30
        manager.addPlayer(player2); // age 23
        manager.addPlayer(goalkeeper1); // age 28

        List<Player> sorted = manager.sortByAge();
        assertEquals(23, sorted.get(0).getAge());
        assertEquals(28, sorted.get(1).getAge());
        assertEquals(30, sorted.get(2).getAge());
    }

    @Test
    @DisplayName("Should sort players by goals descending")
    void testSortByGoals() {
        manager.addPlayer(player1); // 20 goals
        manager.addPlayer(player2); // 12 goals
        manager.addPlayer(goalkeeper1); // 0 goals

        List<Player> sorted = manager.sortByGoals();
        assertEquals(20, sorted.get(0).getGoals());
        assertEquals(12, sorted.get(1).getGoals());
        assertEquals(0, sorted.get(2).getGoals());
    }

    @Test
    @DisplayName("Should sort players by market value descending")
    void testSortByMarketValue() {
        manager.addPlayer(player1); // 100.0
        manager.addPlayer(player2); // 120.0
        manager.addPlayer(goalkeeper1); // 28.0

        List<Player> sorted = manager.sortByMarketValue();
        assertEquals(120.0, sorted.get(0).getMarketValue());
        assertEquals(100.0, sorted.get(1).getMarketValue());
        assertEquals(28.0, sorted.get(2).getMarketValue());
    }

    @Test
    @DisplayName("Should compare players correctly")
    void testComparePlayers() {
        manager.addPlayer(player1); // 20 goals, 8 assists = 28 total
        manager.addPlayer(player2); // 12 goals, 9 assists = 21 total

        String result = manager.comparePlayers("Harry Kane", "Bukayo Saka");
        assertTrue(result.contains("Harry Kane"));
        assertTrue(result.contains("leads"));
    }

    @Test
    @DisplayName("Should handle comparison when player not found")
    void testComparePlayerNotFound() {
        manager.addPlayer(player1);
        String result = manager.comparePlayers("Harry Kane", "Non Existent");
        assertTrue(result.contains("not found"));
    }

    @Test
    @DisplayName("Should return empty list for highest scorers when no players")
    void testHighestScorersEmpty() {
        List<Player> scorers = manager.highestScorers();
        assertTrue(scorers.isEmpty());
    }

    @Test
    @DisplayName("Should limit highest scorers to 10")
    void testHighestScorersLimit() {
        // Add 15 players
        for (int i = 0; i < 15; i++) {
            OutfieldPlayer p = new OutfieldPlayer(
                    "Player " + i, 25, "Arsenal", "CF", i, 0, true, 175, "England", "R", 50.0
            );
            manager.addPlayer(p);
        }

        List<Player> scorers = manager.highestScorers();
        assertEquals(10, scorers.size());
    }
}

