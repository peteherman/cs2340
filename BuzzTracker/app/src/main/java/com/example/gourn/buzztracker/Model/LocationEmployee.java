package com.example.gourn.buzztracker.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationEmployee extends Person {
  Location location;
  List<String> categories;

  public LocationEmployee(String name, String email, String password, UserType userType,
                          Location location, List<String> categories) {
      super(name, email, userType);
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

  public boolean addToCategoryList(String category) {
      if (category == null) {
          return false;
      }
      if (category.length() <= 0) {
          return false;
      }
      for (int i = 0; i < categories.size(); i++) {
          if (categories.get(i).equalsIgnoreCase(category)) {
              return false;
          }
      }
      categories.add(category);
      return true;
  }
}