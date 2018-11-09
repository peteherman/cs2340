package com.example.gourn.buzztracker;

//import android.support.test.runner.AndroidJUnit4;

import com.example.gourn.buzztracker.Model.DefaultDonationCategories;
import com.example.gourn.buzztracker.Model.LocationEmployee;
import com.example.gourn.buzztracker.Model.UserType;
import com.example.gourn.buzztracker.Model.Location;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

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
    public void testAddEmptyString() {
        assert (! validLE.addToCategoryList(""));
    }

    @Test
    public void testAddCategoryListNull() {

        assert(!validLE.addToCategoryList(null));
    }

    @Test
    public void testAddAlreadyInList() {
        DefaultDonationCategories[] dCats = DefaultDonationCategories.values();
        for (DefaultDonationCategories d : dCats) {
            assert (!validLE.addToCategoryList(d.toString()));
        }
        for (int i = 0; i < 10; i++) {
            assert(validLE.addToCategoryList("cat" + i));
            assert(! validLE.addToCategoryList("cat" + i));
        }

    }

    @Test
    public void testDefaultCategoryList() {
        DefaultDonationCategories[] defaultCategories = DefaultDonationCategories.values();
        for (int i = 0; i < defaultCategories.length; i++) {
            assert(validLE.getCategories().get(i).equalsIgnoreCase(defaultCategories[i].toString()));
        }
    }


    @Test
    public void testAddValid() {
        DefaultDonationCategories[] dCats = DefaultDonationCategories.values();
        List<String> cats = validLE.getCategories();
        for (int i = 0; i < 10; i++) {
            assert(validLE.addToCategoryList("cat" + i));
        }
        for (int i = 0; i < dCats.length; i++) {
            assert(cats.get(i).equalsIgnoreCase(dCats[i].toString()));
        }
        for (int i = dCats.length; i < dCats.length + 10; i++) {
            assert (cats.get(i).equalsIgnoreCase("cat" + (i - dCats.length)));
        }
    }
}
