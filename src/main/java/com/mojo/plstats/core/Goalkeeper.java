package com.mojo.plstats.core;

public class Goalkeeper extends Player {
    private int cleanSheets;
    public Goalkeeper(String name, int age, String club, String position, int goals, int assists, boolean isAvailable, int cleanSheets) {
        super(name, age, club, position, goals, assists, isAvailable);
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
        return super.displayInfo() + "\nClean Sheets: " + cleanSheets;
    }

}
