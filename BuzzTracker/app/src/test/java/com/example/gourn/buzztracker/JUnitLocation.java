package com.example.gourn.buzztracker;

import com.example.gourn.buzztracker.Model.DefaultDonationCategories;
import com.example.gourn.buzztracker.Model.Donation;
import com.example.gourn.buzztracker.Model.Location;

import org.junit.Test;
import org.junit.Assert;


import java.sql.Timestamp;
import java.util.Date;

public class JUnitLocation {
    Location validLocation = new Location("Location", "55.55", "44.44",
            "1 gatech dr.", "donation center", "555-5555", "www.donate.com");

    @Test
    public void testAddNullDonation(){
        assert(validLocation.getDonations().size() == 0);

        validLocation.addDonation(null);

        assert(validLocation.getDonations().size() == 0);

    }

    @Test
    public void testAddMultipleNulls() {
        assert(validLocation.getDonations().size() == 0);
        for (int i = 0; i < 10; i++) {
            validLocation.addDonation(null);
        }

        assert(validLocation.getDonations().size() == 0);
    }

    @Test
    public void testAddSingleDonation(){
        assert(validLocation.getDonations().size() == 0);

        Donation d = new Donation();

        validLocation.addDonation(d);

        assert (validLocation.getDonations().size() == 1);

    }

    @Test
    public void testAddAndCheckSingleDonation() {
        assert(validLocation.getDonations().size() == 0);
        Date date = new Date();

        Donation d = new Donation(new Timestamp(date.getTime()), "location", "short desc", "full desc",
                10.00, DefaultDonationCategories.CLOTHING, -1);

        validLocation.addDonation(d);

        assert (validLocation.getDonations().size() == 1);

        assert (validLocation.getDonations().get(0).getLocation().equalsIgnoreCase("location"));
    }

    @Test
    public void addMultipleDonations() {
        assert(validLocation.getDonations().size() == 0);
        Date date = new Date();

        Donation[] donations = new Donation[10];
        for (int i = 0; i < donations.length; i++) {
            donations[i] = new Donation(new Timestamp(date.getTime()), "location" + i, "short desc", "full desc",
                    10.00, DefaultDonationCategories.CLOTHING, -1);

            validLocation.addDonation(donations[i]);
        }


        assert (validLocation.getDonations().size() == donations.length);

        for (int i = 0; i < donations.length; i++) {
            assert (validLocation.getDonations().get(i).getLocation().equalsIgnoreCase("location" + i));
        }


    }


}
