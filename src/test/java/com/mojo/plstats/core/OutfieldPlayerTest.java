package com.mojo.plstats.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the OutfieldPlayer class.
 */
@DisplayName("OutfieldPlayer Tests")
class OutfieldPlayerTest {

    private OutfieldPlayer outfieldPlayer;

    @BeforeEach
    void setUp() {
        outfieldPlayer = new OutfieldPlayer(
                "Bukayo Saka", 23, "Arsenal", "RW",
                12, 9, true, 178, "England", "L", 120.0
        );
    }

    @Test
    @DisplayName("Should create outfield player with valid data")
    void testCreateOutfieldPlayer() {
        assertNotNull(outfieldPlayer);
        assertEquals("Bukayo Saka", outfieldPlayer.getName());
        assertEquals("RW", outfieldPlayer.getPosition());
        assertEquals(12, outfieldPlayer.getGoals());
        assertEquals(9, outfieldPlayer.getAssists());
    }

    @Test
    @DisplayName("Should be instance of Player")
    void testIsInstanceOfPlayer() {
        assertTrue(outfieldPlayer instanceof Player);
    }


    @Test
    @DisplayName("Should create player with different positions")
    void testDifferentPositions() {
        String[] positions = {"CB", "LB", "RB", "CM", "DM", "AM", "LW", "RW", "CF"};
        for (String pos : positions) {
            OutfieldPlayer player = new OutfieldPlayer("Test", 25, "Arsenal", pos, 5, 3, true, 175, "England", "R", 50.0);
            assertEquals(pos.toUpperCase(), player.getPosition());
        }
    }
}

