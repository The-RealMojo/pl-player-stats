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
        if (searchPlayer(name) != null) {
            searchPlayer(name).setClub(newClub);
            return true;
        }
        return false;
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

    public void comparePlayers(String player1, String player2) {
        int statsP1 = searchPlayer(player1).getGoals() + searchPlayer(player1).getAssists();
        int statsP2 = searchPlayer(player2).getGoals() + searchPlayer(player2).getAssists();

        if(statsP1 > statsP2) {
            System.out.println(player1 + " leads. Total G/A: " + statsP1);
            System.out.println(player2 + " Total G/A: " + statsP2);
        } else if (statsP2 > statsP1) {
            System.out.println(player2 + " leads. Total G/A: " + statsP2);
            System.out.println(player1 + " Total G/A: " + statsP1);
        } else {
            System.out.println("Tie. Both players have " + statsP1 + " G/A.");
        }

    }

    public List<Player> getAllPlayers() {
        return players;
    }


}