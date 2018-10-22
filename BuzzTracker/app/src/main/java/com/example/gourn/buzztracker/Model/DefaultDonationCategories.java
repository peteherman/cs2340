package com.example.gourn.buzztracker.Model;

public enum DefaultDonationCategories {
    CLOTHING ("Clothing"),
    HAT ("Hat"),
    KITCHEN ("Kitchen"),
    ELECTRONICS ("Electronics"),
    HOUSEHOLD ("Household"),
    OTHER ("Other");

    private String val;

    DefaultDonationCategories(String s) {
        val = s;
    }

    public String toString() {
        return this.val;
    }
}
