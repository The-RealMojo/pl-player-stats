package com.mojo.plstats.infrastructure;

import com.mojo.plstats.core.Goalkeeper;
import com.mojo.plstats.core.OutfieldPlayer;
import com.mojo.plstats.core.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerFileHandler {

    // ========= SAVE PLAYERS =========
    public static void savePlayersToFile(List<Player> players, String filePath) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {

            for (Player p : players) {

                String type = (p instanceof Goalkeeper) ? "GK" : "OUT";

                int cleanSheets = (p instanceof Goalkeeper)
                        ? ((Goalkeeper) p).getCleanSheets()
                        : 0;

                String line =
                        type + "," +
                                p.getName() + "," +
                                p.getAge() + "," +
                                p.getClub() + "," +
                                p.getPosition() + "," +
                                p.getGoals() + "," +
                                p.getAssists() + "," +
                                p.isAvailable() + "," +
                                p.getHeight() + "," +
                                p.getNationality() + "," +
                                p.getPreferredFoot() + "," +
                                p.getMarketValue() + "," +
                                cleanSheets;

                fileWriter.write(line);
                fileWriter.newLine();
            }

            System.out.println("Players saved successfully to " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ========= LOAD PLAYERS =========
    public static List<Player> loadPlayersFromFile(String filePath) throws IOException {
        List<Player> loadedPlayers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();

            while (line != null) {

                if (line.trim().isEmpty()) {
                    line = reader.readLine();
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length != 13) {
                    System.out.println("Skipping invalid line: " + line);
                    line = reader.readLine();
                    continue;
                }

                try {
                    String type = parts[0];          // GK or OUT
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String club = parts[3];
                    String position = parts[4];
                    int goals = Integer.parseInt(parts[5]);
                    int assists = Integer.parseInt(parts[6]);
                    boolean isAvailable = Boolean.parseBoolean(parts[7]);
                    int height = Integer.parseInt(parts[8]);
                    String nationality = parts[9];
                    String preferredFoot = parts[10];
                    double marketValue = Double.parseDouble(parts[11]);
                    int cleanSheets = Integer.parseInt(parts[12]);

                    Player p;

                    if (type.equals("GK")) {
                        p = new Goalkeeper(
                                name, age, club, position, goals, assists, isAvailable,
                                height, nationality, preferredFoot, marketValue, cleanSheets
                        );
                    } else {
                        p = new OutfieldPlayer(
                                name, age, club, position, goals, assists, isAvailable,
                                height, nationality, preferredFoot, marketValue
                        );
                    }

                    loadedPlayers.add(p);

                } catch (Exception e) {
                    System.out.println("Error parsing line: " + line);
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

