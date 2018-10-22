package com.example.gourn.buzztracker.Model;

import android.media.Image;

import java.sql.Timestamp;

public class Donation {
    Timestamp timestamp;
    Location location;
    String shortDescription;
    String fullDescription;
    int value;
    DefaultDonationCategories category;
    String comments;
    Image picture;

    public Donation(Timestamp timestamp, Location location, String shortDescription,
                    String fullDescription, int value, DefaultDonationCategories category,
                    String comments, Image picture) {
        this.timestamp = timestamp;
        this.location = location;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.value = value;
        this.category = category;
        this.comments = comments;
        this.picture = picture;
    }
    public Donation(Donation donation) {
        this.timestamp = donation.getTimestamp();
        this.location = donation.getLocation();
        this.shortDescription = donation.getShortDescription();
        this.fullDescription = donation.getFullDescription();
        this.value = donation.getValue();
        this.category = donation.getCategory();
        this.comments = donation.getComments();
        this.picture = donation.getPicture();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public DefaultDonationCategories getCategory() {
        return category;
    }

    public void setCategory(DefaultDonationCategories category) {
        this.category = category;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
