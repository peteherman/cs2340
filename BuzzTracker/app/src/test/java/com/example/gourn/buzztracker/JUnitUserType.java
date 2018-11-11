package com.example.gourn.buzztracker;

import com.example.gourn.buzztracker.Model.DefaultDonationCategories;
import com.example.gourn.buzztracker.Model.LocationEmployee;
import com.example.gourn.buzztracker.Model.UserType;
import com.example.gourn.buzztracker.Model.Location;
import com.example.gourn.buzztracker.Model.Donation;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//@RunWith(AndroidJUnit4.class)
public class JUnitUserType {

    @Test // JUnit test for getOrdinalFromValue method in UserType enum
    public void testGetOrdinalFromValue() {
        String test1 = null;
        String test2 = "this should not work";
        assertEquals(-1, UserType.getOrdinalFromValue(test1)); // testing if -1 is returned if null is passed
        assertEquals(-1, UserType.getOrdinalFromValue(null));

        UserType user = UserType.USER;
        UserType locEmployee = UserType.LOCATION_EMPLOYEE;
        UserType admin = UserType.ADMIN;

        assertEquals(user.ordinal(), UserType.getOrdinalFromValue("USER"));
        assertEquals(locEmployee.ordinal(), UserType.getOrdinalFromValue("LOCATION_EMPLOYEE"));
        assertEquals(admin.ordinal(), UserType.getOrdinalFromValue("ADMIN"));

        assertEquals(0, UserType.getOrdinalFromValue("USER"));
        assertEquals(1, UserType.getOrdinalFromValue("LOCATION_EMPLOYEE"));
        assertEquals(2, UserType.getOrdinalFromValue("ADMIN"));

        assertEquals(0, UserType.getOrdinalFromValue("user"));
        assertEquals(1, UserType.getOrdinalFromValue("location_employee")); // equalsIgnoreCase
        assertEquals(2, UserType.getOrdinalFromValue("admin"));

        assertEquals(-1, UserType.getOrdinalFromValue(test2)); // testing if the if statement in the loop doesn't get called, so should return -1

        // tested for branch coverage
    }

    @Test
    public void testCreatingLocations() {
        // testing for null first
        try {
            Location l = new Location(null, null, null, null, null, null, null, null);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        List<Donation> donationList = new ArrayList<>();
        Donation d1 = new Donation(new Timestamp(System.currentTimeMillis()), "AFD Station 4", "short",
                                    "full", 1.7, DefaultDonationCategories.CLOTHING, 5);
        Donation d2 = new Donation(new Timestamp(System.currentTimeMillis()), "PAVILION OF HOPE INC", "short desc",
                "full desc", 100.0, DefaultDonationCategories.CLOTHING, 500);
        Donation d3 = new Donation(new Timestamp(System.currentTimeMillis()), "PATHWAY UPPER ROOM CHRISTIAN MINISTRIES", "short desc 2",
                "full desc 2", 150.0345649, DefaultDonationCategories.ELECTRONICS, 1);

        donationList.add(d1);
        donationList.add(d2);
        donationList.add(d3);

        Location l1 = new Location("testloc1", "-30.123456", "34.64356", "address", "type",
                                    "123-123-1234", "google.com", donationList);

        for (int i = 0; i < donationList.size(); i++) {
            Donation loopD;
            if (i == 0) {
                loopD = d1;
            } else if (i == 1) {
                loopD = d2;
            } else {
                loopD = d3;
            }

            // test that all donation properties are correctly set while creating the Location
            assertEquals(loopD.getTimestamp(), l1.getDonations().get(i).getTimestamp());
            assertEquals(loopD.getLocation(), l1.getDonations().get(i).getLocation());
            assertEquals(loopD.getShortDescription(), l1.getDonations().get(i).getShortDescription());
            assertEquals(loopD.getFullDescription(), l1.getDonations().get(i).getFullDescription());
            assertEquals(loopD.getValue(), l1.getDonations().get(i).getValue());
            assertEquals(loopD.getQuantity(), l1.getDonations().get(i).getQuantity());
            assertEquals(loopD.getCategory().toString(), l1.getDonations().get(i).getCategory().toString());
        }

        // now test if the location's other properties were set properly
        assertEquals(l1.getName(), "testloc1");
        assertEquals(l1.getLatitude(), "-30.123456");
        assertEquals(l1.getLongitude(), "34.64356");
        assertEquals(l1.getAddress(), "address");
        assertEquals(l1.getType(), "type");
        assertEquals(l1.getPhoneNum(), "123-123-1234");
        assertEquals(l1.getWebsite(), "google.com");
    }
}