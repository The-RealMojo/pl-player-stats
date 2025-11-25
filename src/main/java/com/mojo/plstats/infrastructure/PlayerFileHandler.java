package com.mojo.plstats.infrastructure;

import com.mojo.plstats.core.Goalkeeper;
import com.mojo.plstats.core.OutfieldPlayer;
import com.mojo.plstats.core.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerFileHandler {
    public static void savePlayersToFile(List<Player> players, String filePath) {
        // TODO: write players to file
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (Player p : players) {


                int cleanSheets = 0;
                if (p instanceof Goalkeeper) {
                    cleanSheets = ((Goalkeeper) p).getCleanSheets();
                }

                String line = p.getName() + "," +
                        p.getAge() + "," +
                        p.getClub() + "," +
                        p.getPosition() + "," +
                        p.getGoals() + "," +
                        p.getAssists() + "," +
                        p.isAvailable() + "," +
                        cleanSheets;

                fileWriter.write(line);
                fileWriter.newLine();
            }
            System.out.println("Players saved successfully to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Player> loadPlayersFromFile(String filePath) throws IOException {
        // TODO: read players from file and return them
        List<Player> loadedPlayers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();

            while (line != null) {
                String[] parts = line.split(",");

                if (parts.length >= 8) {
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    String club = parts[2];
                    String position = parts[3];
                    int goals = Integer.parseInt(parts[4]);
                    int assists = Integer.parseInt(parts[5]);
                    boolean isAvailable = Boolean.parseBoolean(parts[6]);
                    int cleanSheets = Integer.parseInt(parts[7]);

                    Player p;

                    // If GK → create Goalkeeper
                    if (position.equalsIgnoreCase("GK")) {
                        p = new Goalkeeper(name, age, club, position, goals, assists, isAvailable, cleanSheets);
                    } else {
                        p = new OutfieldPlayer(name, age, club, position, goals, assists, isAvailable);
                    }

                    loadedPlayers.add(p);
                }

                line = reader.readLine();
            }

            System.out.println("Players loaded successfully from file.");

        } catch (IOException e) {
            System.out.println("Error loading players: " + e.getMessage());
        }

        return loadedPlayers;
    }
}

