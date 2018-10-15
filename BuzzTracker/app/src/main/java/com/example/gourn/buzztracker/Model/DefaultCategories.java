package com.example.gourn.buzztracker.Model;

public enum DefaultCategories {
    CLOTHING ("Clothing"),
    HAT ("Hat"),
    KITCHEN ("Kitchen"),
    ELECTRONICS ("Electronics"),
    HOUSEHOLD ("Household"),
    OTHER ("Other");

    private String stringRepresentation;

    private DefaultCategories(String s) {
        stringRepresentation = s;
    }

    public String toString() {
        return this.stringRepresentation;
    }
}
