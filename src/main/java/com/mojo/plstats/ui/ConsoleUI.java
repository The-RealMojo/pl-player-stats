package com.mojo.plstats.ui;

import com.mojo.plstats.application.PlayerManager;
import com.mojo.plstats.core.Goalkeeper;
import com.mojo.plstats.core.OutfieldPlayer;
import com.mojo.plstats.core.Player;
import com.mojo.plstats.infrastructure.PlayerFileHandler;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private PlayerManager manager;
    private Scanner scanner;

    public ConsoleUI() {
        manager = new PlayerManager();
        scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== PREMIER LEAGUE PLAYER SYSTEM ===");
            System.out.println("1. Add Player");
            System.out.println("2. Show All Players");
            System.out.println("3. Search Player");
            System.out.println("4. Filter Players");
            System.out.println("5. Highest Scorers");
            System.out.println("6. Most Assists");
            System.out.println("7. Transfer Player");
            System.out.println("8. Compare Players");
            System.out.println("9. Save to File");
            System.out.println("10. Load from File");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addPlayerUI();
                case 2 -> showAllPlayersUI();
                case 3 -> searchPlayerUI();
                case 4 -> filterPlayersUI();
                case 5 -> highestScorersUI();
                case 6 -> mostAssistsUI();
                case 7 -> transferPlayerUI();
                case 8 -> comparePlayersUI();
                case 9 -> saveUI();
                case 10 -> loadUI();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void addPlayerUI() {
        // TODO: ask user for name, age, club, etc.
        System.out.println("\n=== ADD PLAYER ===");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Club: ");
        String club = scanner.nextLine();

        System.out.print("Position (GK, CB, LB, RB, CM, DM, AM, LW, RW, CF): ");
        String position = scanner.nextLine();

        System.out.print("Goals: ");
        int goals = scanner.nextInt();

        System.out.print("Assists: ");
        int assists = scanner.nextInt();

        System.out.print("Available? (true/false): ");
        boolean isAvailable = scanner.nextBoolean();

        System.out.print("Height (cm): ");
        int height = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nationality: ");
        String nationality = scanner.nextLine();

        System.out.print("Preferred Foot (L/R): ");
        String preferredFoot = scanner.nextLine().trim().toUpperCase();

        System.out.print("Market Value (million): ");
        double marketValue = scanner.nextDouble();
        scanner.nextLine();

        int cleanSheets = 0;

        if (position.equalsIgnoreCase("GK")) {
            System.out.print("Clean Sheets: ");
            cleanSheets = scanner.nextInt();
        }
        scanner.nextLine(); // clear newline

        Player newPlayer;
        if (position.equalsIgnoreCase("GK")) {
            newPlayer = new Goalkeeper(name, age, club, position, goals, assists, isAvailable, height, nationality, preferredFoot, marketValue, cleanSheets);
        } else {
            newPlayer = new OutfieldPlayer(name, age, club, position, goals, assists, isAvailable, height, nationality, preferredFoot, marketValue);
        }

        if (manager.addPlayer(newPlayer)) {
            System.out.println("Player added successfully!");
        } else {
            System.out.println("Failed to add player (duplicate or invalid).");
        }
    }

    private void showAllPlayersUI() {
        System.out.println("\n=== ALL PLAYERS ===");

        List<Player> players = manager.getAllPlayers();

        if (players.isEmpty()) {
            System.out.println("No players found.");
            return;
        }

        for (Player p : players) {
            System.out.println("---------------------------------------------------");
            System.out.println(p.displayInfo());
            System.out.println("---------------------------------------------------\n");
        }
    }

    private void searchPlayerUI() {
        // TODO
        System.out.println("\n=== SEARCH PLAYER ===");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        Player p = manager.searchPlayer(name);

        if (p == null) {
            System.out.println("Player not found.");
        } else {
            System.out.println(p.displayInfo());
        }
    }

    private void filterPlayersUI() {
        // TODO
        System.out.println("\n=== FILTER PLAYERS ===");
        System.out.println("(Leave blank to ignore a filter)");

        System.out.print("Club: ");
        String club = scanner.nextLine();
        if (club.isEmpty()) club = null;

        System.out.print("Position: ");
        String position = scanner.nextLine();
        if (position.isEmpty()) position = null;

        System.out.print("Age: ");
        String ageInput = scanner.nextLine();
        Integer age = ageInput.isEmpty() ? null : Integer.parseInt(ageInput);

        List<Player> results = manager.filterPlayers(club, position, age);

        if (results.isEmpty()) {
            System.out.println("No players match your filters.");
            return;
        }

        System.out.println("\n=== FILTERED PLAYERS ===");
        for (Player p : results) {
            System.out.println(p.displayInfo());
            System.out.println("--------------------------------");
        }
    }

    private void highestScorersUI() {
        // TODO
        System.out.println("\n=== TOP SCORERS ===");

        List<Player> list = manager.highestScorers();

        for (Player p : list) {
            System.out.println(p.getName() + " - Goals: " + p.getGoals());
        }
    }

    private void mostAssistsUI() {
        System.out.println("\n=== MOST ASSISTS ===");

        List<Player> list = manager.mostAssists();

        for (Player p : list) {
            System.out.println(p.getName() + " - Assists: " + p.getAssists());
        }
    }

    private void transferPlayerUI() {
        System.out.println("\n=== TRANSFER PLAYER ===");

        System.out.print("Player name: ");
        String name = scanner.nextLine();

        System.out.print("New club: ");
        String newClub = scanner.nextLine();

        if (manager.transferPlayer(name, newClub)) {
            System.out.println("Transfer completed!");
        } else {
            System.out.println("Transfer failed — player not found.");
        }
    }

    private void comparePlayersUI() {
        System.out.println("\n=== COMPARE PLAYERS ===");

        System.out.print("First player: ");
        String p1 = scanner.nextLine();

        System.out.print("Second player: ");
        String p2 = scanner.nextLine();

        manager.comparePlayers(p1, p2);
    }



    private void saveUI() {
        // TODO
        System.out.println("\n=== SAVE PLAYERS ===");

        System.out.print("File path to save (ex: players.txt): ");
        String path = scanner.nextLine();

        PlayerFileHandler.savePlayersToFile(manager.getAllPlayers(), path);

    }

    private void loadUI() {
        // TODO
        System.out.println("\n=== LOAD PLAYERS ===");

        System.out.print("File path to load (ex: players.txt): ");
        String path = scanner.nextLine();

        try {
            List<Player> loaded = PlayerFileHandler.loadPlayersFromFile(path);

            for (Player p : loaded) {
                manager.addPlayer(p);
            }

            System.out.println("Players loaded and added to system.");
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
