package com.mojo.plstats.application;

import com.mojo.plstats.core.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlayerManager implements PlayerAPI {
    private ArrayList<Player> players;

    public PlayerManager() {
        players = new ArrayList<>();
    }

    public boolean addPlayer(Player player) {
        if (player == null || players.contains(player)) {
            return false;
        }
        players.add(player);
        return true;
    }
    public boolean removePlayer(String name) {
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).getName().equalsIgnoreCase(name)) {
                players.remove(i);
                return true;
            }
        }
        return false;
    }

    public Player searchPlayer(String name) {
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).getName().equalsIgnoreCase(name)) {
                return players.get(i);
            }
        }
        return null;
    }

    public boolean transferPlayer(String name, String newClub) {
        Player p = searchPlayer(name);
        if (p == null) {
            return false;
        }

        p.setClub(newClub);
        return true;
    }

    public List<Player> highestScorers() {
        List<Player> highestScorers = new ArrayList<>();
        highestScorers.addAll(players);
        highestScorers.sort(Comparator.comparing(Player::getGoals).reversed());
        return highestScorers.subList(0, Math.min(10, highestScorers.size()));
    }

    public List<Player> mostAssists() {
        List<Player> mostAssists = new ArrayList<>();
        mostAssists.addAll(players);
        mostAssists.sort(Comparator.comparing(Player::getAssists).reversed());
        return mostAssists.subList(0, Math.min(10, mostAssists.size()));
    }

    public List<Player> filterPlayers(String club, String position, Integer age) {
        List<Player> filteredPlayers = new ArrayList<>();
        for (Player player : players) {
            if (club == null || club.equalsIgnoreCase(player.getClub())) {
                if (position == null || position.equalsIgnoreCase(player.getPosition())) {
                    if (age == null || age == player.getAge()){
                        filteredPlayers.add(player);
                    }
                }
            }
        }

        return filteredPlayers;
    }

    public String comparePlayers(String player1, String player2) {
        Player p1 = searchPlayer(player1);
        Player p2 = searchPlayer(player2);

        if (p1 == null && p2 == null) {
            return "Both players not found: " + player1 + " and " + player2;
        } else if (p1 == null) {
            return "Player not found: " + player1;
        } else if (p2 == null) {
            return "Player not found: " + player2;
        }

        int statsP1 = p1.getGoals() + p1.getAssists();
        int statsP2 = p2.getGoals() + p2.getAssists();

        StringBuilder result = new StringBuilder();
        if(statsP1 > statsP2) {
            result.append(player1).append(" leads. Total G/A: ").append(statsP1).append("\n");
            result.append(player2).append(" Total G/A: ").append(statsP2);
        } else if (statsP2 > statsP1) {
            result.append(player2).append(" leads. Total G/A: ").append(statsP2).append("\n");
            result.append(player1).append(" Total G/A: ").append(statsP1);
        } else {
            result.append("Tie. Both players have ").append(statsP1).append(" G/A.");
        }

        return result.toString();
    }

    public List<Player> getAllPlayers() {
        return players;
    }

    public List<Player> sortByAge() {
        List<Player> sorted = new ArrayList<>(players);
        sorted.sort(Comparator.comparing(Player::getAge));
        return sorted;
    }

    public List<Player> sortByHeight() {
        List<Player> sorted = new ArrayList<>(players);
        sorted.sort(Comparator.comparing(Player::getHeight).reversed());
        return sorted;
    }

    public List<Player> sortByGoals() {
        List<Player> sorted = new ArrayList<>(players);
        sorted.sort(Comparator.comparing(Player::getGoals).reversed());
        return sorted;
    }

    public List<Player> sortByAssists() {
        List<Player> sorted = new ArrayList<>(players);
        sorted.sort(Comparator.comparing(Player::getAssists).reversed());
        return sorted;
    }

    public List<Player> sortByName() {
        List<Player> sorted = new ArrayList<>(players);
        sorted.sort(Comparator.comparing(Player::getName).reversed());
        return sorted;
    }

    public List<Player> sortByMarketValue() {
        List<Player> sorted = new ArrayList<>(players);
        sorted.sort(Comparator.comparing(Player::getMarketValue).reversed());
        return sorted;
    }

    public List<Player> sortByNationality() {
        List<Player> sorted = new ArrayList<>(players);
        sorted.sort(Comparator.comparing(Player::getNationality).reversed());
        return sorted;
    }

    public List<Player> sortByPosition() {
        List<Player> sorted = new ArrayList<>(players);
        sorted.sort(Comparator.comparing(Player::getPosition).reversed());
        return sorted;
    }

    private static PlayerManager instance;

    public static PlayerManager getInstance() {
        if (instance == null) instance = new PlayerManager();
        return instance;
    }

    public List<Player> getPlayers() {
        return players;
    }


}