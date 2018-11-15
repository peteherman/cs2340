package com.example.gourn.buzztracker.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Location implements Parcelable {
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

    public Location(String name, String latitude, String longitude, String address,
                    String type, String phoneNum, String website, Iterable<Donation> donations) {
        if ((name == null) || (latitude == null) || (longitude == null) || (address == null) || (type == null) || (phoneNum == null) || (website == null)) {
            throw new IllegalArgumentException("Cannot initialize Location with null args");
        } else {
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
                    // this.donations.add(new Donation(d));
                    this.addDonation(new Donation(d)); // uses the addDonation method instead
                }
            }
        }

    }
    public Location(String name, String latitude, String longitude, String address,
                    String type, String phoneNum, String website) {
        this(name, latitude, longitude, address, type, phoneNum, website, null);
    }

    public Location(Parcel in) {
        String[] data = new String[7];

        in.readStringArray(data);
        this.name = data[0];
        this.latitude = data[1];
        this.longitude = data[2];
        this.address = data[3];
        this.type = data[4];
        this.phoneNum = data[5];
        this.website = data[6];
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.name,
                this.latitude,
                this.longitude,
                this.address,
                this.type,
                this.phoneNum,
                this.website
        });
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

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

    public List<Donation> getDonations() { return donations;}

    public void addDonation(Donation donation) {
        if (donation == null) {
            return;
        }
        donations.add(donation);
    }

}
