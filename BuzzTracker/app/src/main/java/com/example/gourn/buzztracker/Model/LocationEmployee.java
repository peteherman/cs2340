package com.example.gourn.buzztracker.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationEmployee extends Person {
  Location location;
  List<String> categories;

  public LocationEmployee(String name, String email, String password, UserType userType,
                          Location location, List<String> categories) {
      super(name, email, password, userType);
      this.location = location;
      this.categories = new ArrayList<>();
      for (String c : categories) {
          this.categories.add(c);
      }

  }
  public LocationEmployee(String name, String email, String password, UserType userType,
                          Location location) {
    this(name, email, password, userType, location, new ArrayList<String>(Arrays.asList(DefaultDonationCategories.values().toString())));
  }

  public LocationEmployee(String name, String email, String password, UserType userType) {
    this(name, email, password, userType, null);
  }

  public void addToCategoryList(String category) {
      if (category.length() > 0) {
          categories.add(category);
      }
  }
}