package com.mojo.plstats.core;

public class Goalkeeper extends Player {
    private int cleanSheets;
    public Goalkeeper(String name, int age, String club, String position, int goals, int assists, boolean isAvailable,
                      int height, String nationality, String preferredFoot, double marketValue, int cleanSheets) {
        super(name, age, club, position, goals, assists, isAvailable, height, nationality, preferredFoot, marketValue);
        this.cleanSheets = cleanSheets;
    }

    public int getCleanSheets() {
        return cleanSheets;
    }

    public void updateCleanSheets() {
        cleanSheets++;
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() +
                String.format("Clean Sheets: %d\n", cleanSheets);
    }

}
