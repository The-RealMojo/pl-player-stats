package com.mojo.plstats.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Player abstract class validation and behavior.
 */
@DisplayName("Player Tests")
class PlayerTest {

    private OutfieldPlayer validPlayer;

    @BeforeEach
    void setUp() {
        validPlayer = new OutfieldPlayer(
                "Harry Kane", 30, "Tottenham", "CF",
                20, 8, true, 188, "England", "R", 100.0
        );
    }

    @Test
    @DisplayName("Should create player with valid data")
    void testCreatePlayerWithValidData() {
        assertNotNull(validPlayer);
        assertEquals("Harry Kane", validPlayer.getName());
        assertEquals(30, validPlayer.getAge());
        assertEquals("Tottenham", validPlayer.getClub());
        assertEquals("CF", validPlayer.getPosition());
    }

    @Test
    @DisplayName("Should throw exception for null name")
    void testNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OutfieldPlayer(null, 25, "Arsenal", "CM", 5, 3, true, 175, "England", "R", 50.0);
        });
    }

    @Test
    @DisplayName("Should throw exception for empty name")
    void testEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OutfieldPlayer("", 25, "Arsenal", "CM", 5, 3, true, 175, "England", "R", 50.0);
        });
    }

    @Test
    @DisplayName("Should throw exception for negative age")
    void testNegativeAge() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OutfieldPlayer("John Doe", -1, "Arsenal", "CM", 5, 3, true, 175, "England", "R", 50.0);
        });
    }

    @Test
    @DisplayName("Should throw exception for age below 14")
    void testAgeBelowMinimum() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OutfieldPlayer("John Doe", 13, "Arsenal", "CM", 5, 3, true, 175, "England", "R", 50.0);
        });
    }

    @Test
    @DisplayName("Should accept valid age (14+)")
    void testValidAge() {
        OutfieldPlayer player = new OutfieldPlayer("John Doe", 14, "Arsenal", "CM", 5, 3, true, 175, "England", "R", 50.0);
        assertEquals(14, player.getAge());
    }

    @Test
    @DisplayName("Should throw exception for null club")
    void testNullClub() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OutfieldPlayer("John Doe", 25, null, "CM", 5, 3, true, 175, "England", "R", 50.0);
        });
    }

    @Test
    @DisplayName("Should throw exception for empty club")
    void testEmptyClub() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OutfieldPlayer("John Doe", 25, "", "CM", 5, 3, true, 175, "England", "R", 50.0);
        });
    }

    @Test
    @DisplayName("Should throw exception for invalid position")
    void testInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OutfieldPlayer("John Doe", 25, "Arsenal", "ST", 5, 3, true, 175, "England", "R", 50.0);
        });
    }

    @Test
    @DisplayName("Should accept valid positions")
    void testValidPositions() {
        String[] validPositions = {"GK", "CB", "LB", "RB", "CM", "DM", "AM", "LW", "RW", "CF"};
        for (String pos : validPositions) {
            OutfieldPlayer player = new OutfieldPlayer("Test", 25, "Arsenal", pos, 5, 3, true, 175, "England", "R", 50.0);
            assertEquals(pos.toUpperCase(), player.getPosition());
        }
    }

    @Test
    @DisplayName("Should throw exception for negative goals")
    void testNegativeGoals() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OutfieldPlayer("John Doe", 25, "Arsenal", "CM", -1, 3, true, 175, "England", "R", 50.0);
        });
    }

    @Test
    @DisplayName("Should throw exception for negative assists")
    void testNegativeAssists() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OutfieldPlayer("John Doe", 25, "Arsenal", "CM", 5, -1, true, 175, "England", "R", 50.0);
        });
    }

    @Test
    @DisplayName("Should throw exception for height below 100cm")
    void testHeightTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OutfieldPlayer("John Doe", 25, "Arsenal", "CM", 5, 3, true, 99, "England", "R", 50.0);
        });
    }

    @Test
    @DisplayName("Should throw exception for height above 250cm")
    void testHeightTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OutfieldPlayer("John Doe", 25, "Arsenal", "CM", 5, 3, true, 251, "England", "R", 50.0);
        });
    }

    @Test
    @DisplayName("Should accept valid height range")
    void testValidHeight() {
        OutfieldPlayer player = new OutfieldPlayer("John Doe", 25, "Arsenal", "CM", 5, 3, true, 180, "England", "R", 50.0);
        assertEquals(180, player.getHeight());
    }

    @Test
    @DisplayName("Should throw exception for null nationality")
    void testNullNationality() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OutfieldPlayer("John Doe", 25, "Arsenal", "CM", 5, 3, true, 175, null, "R", 50.0);
        });
    }

    @Test
    @DisplayName("Should throw exception for invalid preferred foot")
    void testInvalidPreferredFoot() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OutfieldPlayer("John Doe", 25, "Arsenal", "CM", 5, 3, true, 175, "England", "B", 50.0);
        });
    }

    @Test
    @DisplayName("Should accept Left or Right foot (case insensitive)")
    void testValidPreferredFoot() {
        OutfieldPlayer playerL = new OutfieldPlayer("John Doe", 25, "Arsenal", "CM", 5, 3, true, 175, "England", "L", 50.0);
        OutfieldPlayer playerR = new OutfieldPlayer("John Doe", 25, "Arsenal", "CM", 5, 3, true, 175, "England", "r", 50.0);
        assertEquals("L", playerL.getPreferredFoot());
        assertEquals("R", playerR.getPreferredFoot());
    }

    @Test
    @DisplayName("Should throw exception for negative market value")
    void testNegativeMarketValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OutfieldPlayer("John Doe", 25, "Arsenal", "CM", 5, 3, true, 175, "England", "R", -1.0);
        });
    }

    @Test
    @DisplayName("Should accept zero market value")
    void testZeroMarketValue() {
        OutfieldPlayer player = new OutfieldPlayer("John Doe", 25, "Arsenal", "CM", 5, 3, true, 175, "England", "R", 0.0);
        assertEquals(0.0, player.getMarketValue());
    }

    @Test
    @DisplayName("Should update goals correctly")
    void testUpdateGoals() {
        int initialGoals = validPlayer.getGoals();
        validPlayer.setGoals(25);
        assertEquals(25, validPlayer.getGoals());
    }

    @Test
    @DisplayName("Should update assists correctly")
    void testUpdateAssists() {
        validPlayer.setAssists(10);
        assertEquals(10, validPlayer.getAssists());
    }

    @Test
    @DisplayName("Should update market value correctly")
    void testUpdateMarketValue() {
        validPlayer.setMarketValue(150.5);
        assertEquals(150.5, validPlayer.getMarketValue());
    }

    @Test
    @DisplayName("Should update availability")
    void testUpdateAvailability() {
        validPlayer.setAvailable(false);
        assertFalse(validPlayer.isAvailable());
        validPlayer.setAvailable(true);
        assertTrue(validPlayer.isAvailable());
    }

    @Test
    @DisplayName("Should increment goals")
    void testIncrementGoals() {
        int initialGoals = validPlayer.getGoals();
        validPlayer.updateGoals();
        assertEquals(initialGoals + 1, validPlayer.getGoals());
    }

    @Test
    @DisplayName("Should increment assists")
    void testIncrementAssists() {
        int initialAssists = validPlayer.getAssists();
        validPlayer.updateAssists();
        assertEquals(initialAssists + 1, validPlayer.getAssists());
    }

    @Test
    @DisplayName("Should update club correctly")
    void testUpdateClub() {
        validPlayer.setClub("Manchester United");
        assertEquals("Manchester United", validPlayer.getClub());
    }

    @Test
    @DisplayName("Should throw exception when setting invalid club")
    void testSetInvalidClub() {
        assertThrows(IllegalArgumentException.class, () -> {
            validPlayer.setClub(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            validPlayer.setClub("");
        });
    }

    @Test
    @DisplayName("Should test equals method - same name and club")
    void testEqualsSameNameAndClub() {
        OutfieldPlayer player1 = new OutfieldPlayer("John Doe", 25, "Arsenal", "CM", 5, 3, true, 175, "England", "R", 50.0);
        OutfieldPlayer player2 = new OutfieldPlayer("john doe", 30, "arsenal", "AM", 10, 5, false, 180, "France", "L", 100.0);
        assertEquals(player1, player2);
    }

    @Test
    @DisplayName("Should test equals method - different name")
    void testEqualsDifferentName() {
        OutfieldPlayer player1 = new OutfieldPlayer("John Doe", 25, "Arsenal", "CM", 5, 3, true, 175, "England", "R", 50.0);
        OutfieldPlayer player2 = new OutfieldPlayer("Jane Doe", 25, "Arsenal", "CM", 5, 3, true, 175, "England", "R", 50.0);
        assertNotEquals(player1, player2);
    }

    @Test
    @DisplayName("Should test hashCode consistency")
    void testHashCodeConsistency() {
        OutfieldPlayer player1 = new OutfieldPlayer("John Doe", 25, "Arsenal", "CM", 5, 3, true, 175, "England", "R", 50.0);
        OutfieldPlayer player2 = new OutfieldPlayer("john doe", 30, "arsenal", "AM", 10, 5, false, 180, "France", "L", 100.0);
        assertEquals(player1.hashCode(), player2.hashCode());
    }

    @Test
    @DisplayName("Should display player info correctly")
    void testDisplayInfo() {
        String info = validPlayer.displayInfo();
        assertTrue(info.contains("Harry Kane"));
        assertTrue(info.contains("Tottenham"));
        assertTrue(info.contains("CF"));
    }
}

