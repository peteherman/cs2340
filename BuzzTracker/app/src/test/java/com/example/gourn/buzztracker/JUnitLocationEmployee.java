package com.example.gourn.buzztracker;

//import android.support.test.runner.AndroidJUnit4;

import com.example.gourn.buzztracker.Model.DefaultDonationCategories;
import com.example.gourn.buzztracker.Model.LocationEmployee;
import com.example.gourn.buzztracker.Model.UserType;
import com.example.gourn.buzztracker.Model.Location;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

//@RunWith(AndroidJUnit4.class)
public class JUnitLocationEmployee {
    LocationEmployee validLE = new LocationEmployee("name", "email",
            UserType.LOCATION_EMPLOYEE, new Location());
    @Test(expected = IllegalArgumentException.class)
    public void constructorNullTesting() {
        //Test null location (Other params should be covered by Person JUnit
        LocationEmployee le = new LocationEmployee("name", "email",
                UserType.LOCATION_EMPLOYEE, null);
    }

    @Test
    public void testDefaultCategoryList() {
        DefaultDonationCategories[] defaultCategories = DefaultDonationCategories.values();
        for (int i = 0; i < defaultCategories.length; i++) {
            System.out.printf(defaultCategories[i].toString());
            System.out.println(validLE.getCategories().get(i).toString());
            assert(validLE.getCategories().get(i).equalsIgnoreCase(defaultCategories[i].toString()));
        }
    }

    @Test
    public void testAddCategoryListNull() {

        assert(!validLE.addToCategoryList(null));
    }

    



}
