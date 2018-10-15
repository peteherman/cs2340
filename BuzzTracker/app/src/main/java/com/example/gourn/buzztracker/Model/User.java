package com.example.gourn.buzztracker.Model;

import com.example.gourn.buzztracker.Model.Person;

public class User extends Person {
  public User(String name, String email, String password, UserType userType) {
    super(name, email, password, userType);
  }
}