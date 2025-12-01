package com.mojo.plstats.core;

import java.util.List;

public abstract class Player {
    private String name;
    private int age;
    private String club;
    private String position;
    private int goals;
    private int assists;

    private boolean isAvailable;
    private int height;
    private String nationality;
    private String preferredFoot;
    private double marketValue;

    public Player(String name, int age, String club, String position, int goals, int assists, boolean isAvailable,
                  int height, String nationality, String preferredFoot, double marketValue) {
        this.name = validateName(name);
        this.age = validateAge(age);
        this.club = validateClub(club);
        this.position = validatePosition(position);
        this.goals = validateGoals(goals);
        this.assists = validateAssists(assists);
        this.isAvailable = isAvailable;
        this.height = validateHeight(height);
        this.nationality = validateNationality(nationality);
        this.marketValue = validateMarketValue(marketValue);
        this.preferredFoot = validatePreferredFoot(preferredFoot);
    }

    private static final List<String> VALID_POSITIONS = List.of("GK","CB","LB","RB","CM","DM","AM","LW","RW","CF");

    private int validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative.");
        }
        if (age < 14) {
            throw new IllegalArgumentException("Player is not old enough to play in the Prem");
        }
        return age;
    }

    private String validateName(String name){
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Enter a valid name!");
        }
        return name;
    }
    private String validateClub(String club){
        if (club == null || club.isEmpty()) {
            throw new IllegalArgumentException("Enter a valid club!");
        }
        return club;
    }
    private String validatePosition(String position) {
        if (!VALID_POSITIONS.contains(position.toUpperCase())) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }
        return position.toUpperCase();
    }

    private int validateGoals(int goals){
        if  (goals < 0) {
            throw new IllegalArgumentException("Goals cannot be negative!");
        }
        return goals;
    }
    private int validateAssists(int assists){
        if (assists < 0) {
            throw new IllegalArgumentException("Assists cannot be negative!");
        }
        return assists;
    }

    private String validateNationality(String nationality) {
        if (nationality == null || nationality.isEmpty()) {
            throw new IllegalArgumentException("Enter a valid country");
        }
        return nationality;
    }

    private int validateHeight(int height) {
        if (height < 100 || height > 250) {
            throw new IllegalArgumentException("Enter a valid height");
        }
        return height; //minor change
    }

    private String validatePreferredFoot(String preferredFoot) {
        if (!preferredFoot.equalsIgnoreCase("L") && !preferredFoot.equalsIgnoreCase("R")) {
            throw new IllegalArgumentException("Preferred foot can only be Left(L) or Right(R)");
        }
        return preferredFoot;
    }

    private double validateMarketValue(double marketValue){
        if (marketValue < 0) {
            throw new IllegalArgumentException("Cannot be negative");
        }
        return marketValue;
    }

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getClub() {
        return club;
    }
    public String getPosition() {
        return position;
    }
    public int getGoals() {
        return goals;
    }
    public int getAssists() {
        return assists;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public int getHeight() {
        return height;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPreferredFoot() {
        return preferredFoot;
    }

    public void setClub(String newClub) {
        this.club = validateClub(newClub);
    }

    public String displayInfo() {
        return String.format(
                """
                Name: %s
                Age: %d
                Club: %s
                Position: %s
                Goals: %d
                Assists: %d
                Available: %b
                Height: %d cm
                Nationality: %s
                Preferred Foot: %s
                Market Value: %.2f million
                """,
                name, age, club, position, goals, assists, isAvailable,
                height, nationality, preferredFoot, marketValue
        );
    }


    public void updateGoals() {
        goals++;
    }
    public void updateAssists() {
        assists++;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Player)) return false;

        Player other = (Player) obj;
        return this.getName().equalsIgnoreCase(other.getName())
                && this.getClub().equalsIgnoreCase(other.getClub());
    }

    @Override
    public int hashCode() {
        return (getName().toLowerCase() + getClub().toLowerCase()).hashCode();
    }
}
