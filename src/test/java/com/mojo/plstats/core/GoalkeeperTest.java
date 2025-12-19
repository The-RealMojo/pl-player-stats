package com.mojo.plstats.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Goalkeeper class.
 */
@DisplayName("Goalkeeper Tests")
class GoalkeeperTest {

    private Goalkeeper goalkeeper;

    @BeforeEach
    void setUp() {
        goalkeeper = new Goalkeeper(
                "David Raya", 28, "Arsenal", "GK",
                0, 1, true, 183, "Spain", "R", 28.0, 12
        );
    }

    @Test
    @DisplayName("Should create goalkeeper with valid data")
    void testCreateGoalkeeper() {
        assertNotNull(goalkeeper);
        assertEquals("David Raya", goalkeeper.getName());
        assertEquals("GK", goalkeeper.getPosition());
        assertEquals(12, goalkeeper.getCleanSheets());
    }

    @Test
    @DisplayName("Should get clean sheets correctly")
    void testGetCleanSheets() {
        assertEquals(12, goalkeeper.getCleanSheets());
    }

    @Test
    @DisplayName("Should increment clean sheets")
    void testUpdateCleanSheets() {
        int initialCleanSheets = goalkeeper.getCleanSheets();
        goalkeeper.updateCleanSheets();
        assertEquals(initialCleanSheets + 1, goalkeeper.getCleanSheets());
    }

    @Test
    @DisplayName("Should display info including clean sheets")
    void testDisplayInfo() {
        String info = goalkeeper.displayInfo();
        assertTrue(info.contains("David Raya"));
        assertTrue(info.contains("Clean Sheets: 12"));
        assertTrue(info.contains("GK"));
    }

    @Test
    @DisplayName("Should create goalkeeper with zero clean sheets")
    void testGoalkeeperWithZeroCleanSheets() {
        Goalkeeper gk = new Goalkeeper("Test GK", 25, "Arsenal", "GK", 0, 0, true, 185, "England", "R", 20.0, 0);
        assertEquals(0, gk.getCleanSheets());
    }

    @Test
    @DisplayName("Should be instance of Player")
    void testIsInstanceOfPlayer() {
        assertTrue(goalkeeper instanceof Player);
    }
}

