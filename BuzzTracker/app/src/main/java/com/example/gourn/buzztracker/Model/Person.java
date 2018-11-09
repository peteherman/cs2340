package com.example.gourn.buzztracker.Model;



public class Person {
  String name;
  String email;
  UserType userType;


  public Person(String name, String email, UserType userType) {
    if (name == null || email == null || userType == null) {
      throw new IllegalArgumentException("Cannot initialize person with null args");
    }
    this.name = name;
    this.email = email;
    this.userType = userType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }
  public UserType getUserType() {
    return userType;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public void setUserType(UserType userType) {
    this.userType = userType;
  }
}