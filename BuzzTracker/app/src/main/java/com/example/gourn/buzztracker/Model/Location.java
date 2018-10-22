package com.example.gourn.buzztracker.Model;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private String name;
    private String latitude;
    private String longitude;
    private String address;
    private String type;
    private String phoneNum;
    private String website;
    private List<Donation> donations;


    public Location() {
        this("","","","","","","");
    }

    public Location(String name, String latitude, String longitude, String address, String type, String phoneNum, String website, List<Donation> donations) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.type = type;
        this.phoneNum = phoneNum;
        this.website = website;
        this.donations = new ArrayList<>();
        if (donations != null) {
            for (Donation d : donations) {
                this.donations.add(new Donation(d));
            }
        }
    }
    public Location(String name, String latitude, String longitude, String address, String type, String phoneNum, String website) {
        this(name, latitude, longitude, address, type, phoneNum, website, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Donation> getDonations() { return donations;};

    public void addDonation(Donation donation) {
        donations.add(donation);
    }

}
