package com.example.gourn.buzztracker.Model;

public enum UserType {
  USER("USER"),
  LOCATION_EMPLOYEE("LOCATION_EMPLOYEE"),
  ADMIN("ADMIN");

  private final String val;

  UserType(String val) {
    this.val = val;
  }

  public String toString() {
    return this.val;
  }

  public static int getOrdinalFromValue(String val) {
      if (val == null) {
          return -1;
      }
      for (UserType u : UserType.values()) {
          if (u.val.equalsIgnoreCase(val)) {
              return u.ordinal();
          }
      }
      return -1;
  }
}