package com.example.gourn.buzztracker.Model;

import java.util.ArrayList;
import java.util.List;

public class LocationEmployee extends Person {
  Location location;
  List<String> categories;

  public LocationEmployee(String name, String email, String password,
                          Location location, List<String> categories) {
      super(name, email, password);
      this.location = location;
      this.categories = new ArrayList<>();
      for (String c : categories) {
          this.categories.add(c);
      }

  }
  public LocationEmployee(String name, String email, String password, Location location) {
    super(name, email, password);
    this.location = location;
    categories = new ArrayList<>();
    for (DefaultCategories c: DefaultCategories.values()) {
        categories.add(c.toString());
    }
  }

  public LocationEmployee(String name, String email, String password) {
    this(name, email, password, null);
  }
}