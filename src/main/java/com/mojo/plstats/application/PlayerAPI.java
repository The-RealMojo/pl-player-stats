package com.mojo.plstats.application;

import com.mojo.plstats.core.Player;

import java.util.List;

public interface PlayerAPI {
    boolean addPlayer(Player player);
    boolean removePlayer(String name);
    Player searchPlayer(String name);
    List<Player> filterPlayers(String club, String position, Integer age);
    List<Player> highestScorers();
    List<Player> mostAssists();
    boolean transferPlayer(String name, String newClub);
}
