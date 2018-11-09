package com.example.gourn.buzztracker.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationEmployee extends Person {
  private Location location;
  private List<String> categories;

  public LocationEmployee(String name, String email, UserType userType,
                          Location location, Iterable<String> categories) {
      super(name, email, userType);
      this.location = location;
      this.categories = new ArrayList<>();
      for (String c : categories) {
          this.categories.add(c);
      }

  }
  public LocationEmployee(String name, String email, UserType userType,
                          Location location) {
    this(name, email, userType, location,
            new ArrayList<>(Arrays.asList(DefaultDonationCategories.values()
                    .toString())));
  }

  public LocationEmployee(String name, String email,  UserType userType) {
    this(name, email, userType, null);
  }

  public boolean addToCategoryList(String category) {
      if (category == null) {
          return false;
      }
      if (category.length() <= 0) {
          return false;
      }
      for (int i = 0; i < categories.size(); i++) {
          if (categories.get(i)
                  .equalsIgnoreCase(category)) {
              return false;
          }
      }
      categories.add(category);
      return true;
  }
}