package com.example.gourn.buzztracker;

public enum UserType {
  USER("User"),
  LOCATION_EMPLOYEE("Location Employee"),
  ADMIN("Admin");

  private String val;

  UserType(String val) {
    this.val = val;
  }

  public String toString() {
    return this.val;
  }
}