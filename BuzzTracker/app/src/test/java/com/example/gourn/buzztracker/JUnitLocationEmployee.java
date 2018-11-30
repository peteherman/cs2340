package com.example.gourn.buzztracker;

//import android.support.test.runner.AndroidJUnit4;

import com.example.gourn.buzztracker.Model.DefaultDonationCategories;
import com.example.gourn.buzztracker.Model.LocationEmployee;
import com.example.gourn.buzztracker.Model.UserType;
import com.example.gourn.buzztracker.Model.Location;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@RunWith(AndroidJUnit4.class)
public class JUnitLocationEmployee {
    final LocationEmployee validLE = new LocationEmployee("name", "email",
            UserType.LOCATION_EMPLOYEE, new Location());

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullTesting() {
        //Test null location (Other params should be covered by Person JUnit
        LocationEmployee le = new LocationEmployee("name", "email",
                UserType.LOCATION_EMPLOYEE, null);

    }

    @Test
    public void constructorGeneralTest() {
        List<String> catlist = new ArrayList<>(Arrays.asList("cat1", "cat2", "cat3"));
        Location testloc = new Location();
        testloc.setName("locname");
        testloc.setAddress("1600 Pennsylvania");
        testloc.setWebsite("website.com");
        LocationEmployee le = new LocationEmployee("locempl", "email@email.com", UserType.LOCATION_EMPLOYEE, testloc, catlist);
        List<String> getList = le.getCategories();
        List<String> newCatList = new ArrayList<>(Arrays.asList("Clothing", "Hat", "Kitchen", "Electronics", "Household", "Other"));
        newCatList.addAll(catlist);
        System.out.println(getList.size());
        for (int i = 0; i < getList.size(); i++) {
            assertEquals(newCatList.get(i), getList.get(i));
        }

        assertEquals("locempl", le.getName());
        assertEquals("email@email.com", le.getEmail());
        assertEquals(UserType.LOCATION_EMPLOYEE.ordinal(), le.getUserType().ordinal());
        assertEquals(le.getLocation().getName(), "locname");
        assertEquals(le.getLocation().getAddress(), "1600 Pennsylvania");
        assertEquals(le.getLocation().getWebsite(), "website.com");

        try {
            LocationEmployee le2 = new LocationEmployee(null, "email@email.com", UserType.LOCATION_EMPLOYEE, testloc, catlist);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            LocationEmployee le2 = new LocationEmployee("locempl", null, UserType.LOCATION_EMPLOYEE, testloc, catlist);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            LocationEmployee le2 = new LocationEmployee("locempl", "email@email.com", null, testloc, catlist);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        LocationEmployee le3 = new LocationEmployee("locempl", "email@email.com", UserType.LOCATION_EMPLOYEE, testloc, null);
        assertEquals(le3.getCategories().size(), 6); // if no list is provided, only the defaults are added
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
        for (int i = dCats.length; i < (dCats.length + 10); i++) {
            assert (cats.get(i).equalsIgnoreCase("cat" + (i - dCats.length)));
        }
    }
}
