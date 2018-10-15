package com.example.gourn.buzztracker.Model;

public enum DefaultDonationCategories {
    CLOTHING ("Clothing"),
    HAT ("Hat"),
    KITCHEN ("Kitchen"),
    ELECTRONICS ("Electronics"),
    HOUSEHOLD ("Household"),
    OTHER ("Other");

    private String stringRepresentation;

    private DefaultDonationCategories(String s) {
        stringRepresentation = s;
    }

    public String toString() {
        return this.stringRepresentation;
    }
}
