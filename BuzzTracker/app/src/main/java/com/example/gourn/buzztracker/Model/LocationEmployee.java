package com.example.gourn.buzztracker.Model;

import java.util.ArrayList;
import java.util.List;

public class LocationEmployee extends Person {
  private List<String> categories;
  private Location location;

  public LocationEmployee(String name, String email, UserType userType,
                           Location location, Iterable<String> categories) {
      super(name, email, userType);
      if (location == null) {
          throw new IllegalArgumentException("Cannot initialize Location Employee" +
                  " with null location");
      }
      this.categories = new ArrayList<>();
      this.location = location;
      addDefaultCategories();
      if (categories != null) {
          for (String c : categories) {
              this.categories.add(c);
          }
      }

  }
  public LocationEmployee(String name, String email, UserType userType,
                          Location location) {
    this(name, email, userType, location, null);
  }

  public boolean addToCategoryList(String category) {
      if (category == null) {
          return false;
      }
      if (category.length() <= 0) {
          return false;
      }
      for (String c : categories) {
          if (c.equalsIgnoreCase(category)) {
              return false;
          }
      }
      categories.add(category);
      return true;
  }
  public List<String> getCategories() {
      return categories;
  }
  public Location getLocation() {
      return this.location;
  }

  private void addDefaultCategories() {
      DefaultDonationCategories[] d = DefaultDonationCategories.values();
      for (DefaultDonationCategories dc : d) {
          categories.add(dc.toString());
      }
  }
}