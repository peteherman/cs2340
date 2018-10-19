package com.example.gourn.buzztracker.Model;



public class Person {

  private String name;

  private String email;

  private String password;

  private UserType userType;


  public Person(String name, String email, String password, UserType userType) {

    this.name = name;

    this.email = email;

    this.password = password;

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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUserType(UserType userType) {
    this.userType = userType;
  }
}